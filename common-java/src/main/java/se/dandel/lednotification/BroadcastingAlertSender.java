package se.dandel.lednotification;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rx.Observable;

import javax.inject.Inject;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BroadcastingAlertSender implements AlertSender {
    private static final String GROUP_IPADDRESS = "239.255.0.0";
    private static final int RECEIVING_PORT = 5002;
    private static final int SENDING_PORT = 5001;

    private Logger LOGGER = LoggerFactory.getLogger(getClass());
    private MulticastSocket socket;
    private InetAddress group;
    private ExecutorService eventReaderExecutor;

    @Inject
    private EventBus eventBus;

    @Override
    public void send(Event event) {
        StringBuilder builder = new StringBuilder();
        builder.append("source=" + event.getSource().getSource());
        builder.append(";");
        builder.append("type=" + event.getType());
        if (event.getPriority() != null) {
            builder.append(";");
            builder.append("priority=" + event.getPriority());
        }
        send(builder.toString());
    }

    private void send(String message) {
        LOGGER.debug("Sending message: " + message);
        if (socket == null) {
            initialize();
        }
        try {
            String charset = "UTF8";
            byte[] buf = message.getBytes(charset);
            LOGGER.debug("Sending message using charset {}", charset);
            DatagramPacket packet = new DatagramPacket(buf, buf.length, group, SENDING_PORT);
            socket.send(packet);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    private void initialize() {
        try {
            socket = new MulticastSocket(RECEIVING_PORT);
            group = InetAddress.getByName(GROUP_IPADDRESS);
            socket.joinGroup(group);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public void startReading() {
        if (eventReaderExecutor != null) {
            return;
        }
        if (socket == null) {
            initialize();
        }
        LOGGER.debug("Setting up for reading events");
        eventReaderExecutor = Executors.newSingleThreadExecutor();
        eventReaderExecutor.submit(() -> {
            try {
                while (!socket.isClosed()) {
                    byte[] buffer = new byte[512];
                    DatagramPacket p = new DatagramPacket(buffer, buffer.length);
                    socket.receive(p);
                    String eventStr = new String(buffer, 0, p.getLength(), "UTF8");
                    Event event = parseEvent(eventStr);
                    LOGGER.debug("Received event {}", event);
                    eventBus.send(event);
                }
                LOGGER.debug("End reading events");
            } catch (IOException e) {
                if (socket.isClosed()) {
                    LOGGER.debug("Exception due to socket closed");
                } else {
                    LOGGER.error(e.getMessage(), e);
                    throw new RuntimeException(e.getMessage(), e);
                }
            }
        });
    }

    @Override
    public void stopReading() {
        if (eventReaderExecutor != null) {
            LOGGER.debug("Shutting down executor");
            socket.close();
            eventReaderExecutor.shutdownNow();
        }
    }

    private Event parseEvent(String message) {
        String[] split = message.split(";");
        EventSource source = new EventSource(split[0].split("=")[1]);
        EventType type = EventType.valueOf(split[1].split("=")[1]);
        EventPriority priority = null;
        if (split.length > 2) {
            priority = EventPriority.valueOf(split[2].split("=")[1]);
        }
        return new Event(source, type, priority);
    }


}
