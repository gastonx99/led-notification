package se.dandel.lednotification;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class BroadcastingNotificationSender implements NotificationSender {
    private static final String GROUP_IPADDRESS = "224.6.0.0";
    private static final int PORT = 5001;

    private Logger LOGGER = LoggerFactory.getLogger(getClass());
    private DatagramSocket socket;
    private InetAddress group;

    @Override
    public void send(Notification notification) {
        StringBuilder builder = new StringBuilder();
        builder.append("source=" + notification.getSource().getSource());
        builder.append(";");
        builder.append("type=" + notification.getNotificationType());
        if(notification.getPriority() != null) {
            builder.append(";");
            builder.append("priority=" + notification.getPriority());
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
            DatagramPacket packet = new DatagramPacket(buf, buf.length, group, PORT);
            socket.send(packet);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }

    }

    private void initialize() {
        try {
            socket = new DatagramSocket(PORT - 1);
            group = InetAddress.getByName(GROUP_IPADDRESS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
