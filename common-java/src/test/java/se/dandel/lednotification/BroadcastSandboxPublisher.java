package se.dandel.lednotification;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

public class BroadcastSandboxPublisher {
    private static final String GROUP_IPADDRESS = "224.6.0.0";
    private static final int PORT = 5001;
    private final Logger logger = Logger.getLogger(getClass());
    private DatagramSocket socket;
    private InetAddress group;

    public static void main(String[] args) {
        try {
            BroadcastSandboxPublisher publisher = new BroadcastSandboxPublisher();
            while (true) {
                publisher.send("Howdy pardner " + DateTimeFormat.mediumDateTime().print(DateTime.now()));
                Thread.sleep(3000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public BroadcastSandboxPublisher() throws Exception {
        socket = new DatagramSocket(PORT - 1);
        group = InetAddress.getByName(GROUP_IPADDRESS);
    }

    private void send(String message) throws Exception {
        logger.info("Sending message: " + message);

        byte[] buf = message.getBytes("UTF8");
        DatagramPacket packet = new DatagramPacket(buf, buf.length, group, PORT);
        socket.send(packet);

    }
}
