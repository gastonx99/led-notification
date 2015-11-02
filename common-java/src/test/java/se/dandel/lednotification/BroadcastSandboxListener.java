package se.dandel.lednotification;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

import org.apache.log4j.Logger;

public class BroadcastSandboxListener {
    private static final String GROUP_IPADDRESS = "224.6.0.0";
    private static final int PORT = 5001;
    private final Logger logger = Logger.getLogger(getClass());
    private MulticastSocket socket;

    // @formatter:off
String query1 =
            "SELECT FOO, BAR, BAZ" +
            "  FROM ABC          " +
            " WHERE BAR > 4      ";
    // @formatter:on

    String query =
            "SELECT FOO, BAR, BAZ" +
            "  FROM ABC          " +
            " WHERE BAR > 4      ";

public static void main(String[] args) {
try {
            BroadcastSandboxListener listener = new BroadcastSandboxListener();
            listener.listen();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public BroadcastSandboxListener() throws Exception {
        socket = new MulticastSocket(PORT);
        InetAddress group = InetAddress.getByName(GROUP_IPADDRESS);
        socket.joinGroup(group);
    }

    private void listen() throws Exception {
        byte[] buf = new byte[512];
        DatagramPacket packet = new DatagramPacket(buf, buf.length);
        while (true) {
            logger.info("Waiting for data");
            socket.receive(packet);
            String message = new String(buf, "UTF8");
            logger.info("Data received: " + message);
        }
    }

}
