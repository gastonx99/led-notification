package se.dandel.lednotification;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;

public class BroadcastingAlertSender implements AlertSender {
    private static final String GROUP_IPADDRESS = "224.0.0.1";
    private static final int SENDING_PORT = 5001;
    private static final int RECEIVING_PORT = 5002;

    private Logger LOGGER = LoggerFactory.getLogger(getClass());
    private DatagramSocket socket;
    private InetAddress group;

    @Override
    public void send(Event event) {
        StringBuilder builder = new StringBuilder();
        builder.append("source=" + event.getSource().getSource());
        builder.append(";");
        builder.append("type=" + event.getType());
        if(event.getPriority() != null) {
            builder.append(";");
            builder.append("priority=" + event.getPriority());
        }
        send(builder.toString());
    }

    @Override
    public Event read(int timeout) {
        if (socket == null) {
            initialize();
        }
        try {
            byte[] buffer = new byte[512];
            DatagramPacket p = new DatagramPacket(buffer, buffer.length);
            socket.setSoTimeout(timeout);
            socket.receive(p);
            String eventStr = new String(buffer, 0, p.getLength(), "UTF8");
            return parseEvent(eventStr);
        } catch (SocketTimeoutException e) {
            LOGGER.debug("Timeout while waiting for a read");
            return null;
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    private Event parseEvent(String message) {
        String[] split = message.split(";");
        EventSource source = new EventSource(split[0].split("=")[1]);
        EventType type = EventType.valueOf(split[1].split("=")[1]);
        EventPriority priority = null;
        if(split.length > 2) {
            priority = EventPriority.valueOf(split[2].split("=")[1]);
        }
        return new Event(source, type, priority);
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
            socket = new DatagramSocket(RECEIVING_PORT);
            group = InetAddress.getByName(GROUP_IPADDRESS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
