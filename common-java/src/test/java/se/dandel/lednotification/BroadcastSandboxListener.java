package se.dandel.lednotification;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BroadcastSandboxListener {
    private Logger LOGGER = LoggerFactory.getLogger(getClass());
    private static final String GROUP_IPADDRESS = "224.0.0.1";
    private static final int PORT = 5001;
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
            LOGGER.info("Waiting for data");
            socket.receive(packet);
            String message = new String(buf, "UTF8");
            LOGGER.info("Data received: " + message);
        }
    }

}
