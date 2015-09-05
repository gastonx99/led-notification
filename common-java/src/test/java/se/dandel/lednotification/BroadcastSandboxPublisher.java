package se.dandel.lednotification;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

public class BroadcastSandboxPublisher {
    private final Logger logger = Logger.getLogger(getClass());

    public static void main(String[] args) {

        BroadcastSandboxPublisher publisher = new BroadcastSandboxPublisher();
        try {
            publisher.send("Howdy pardner");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void send(String message) throws Exception {
        logger.info("Sending message: " + message);

        byte[] buf = message.getBytes("UTF8");

        DatagramSocket socket = null;
        try {
            socket = new DatagramSocket(5001);
            InetAddress sendAddress = InetAddress.getByName("127.0.0.1");
            DatagramPacket packet = new DatagramPacket(buf, buf.length, sendAddress, socket.getLocalPort());
            socket.send(packet);

        } finally {
            IOUtils.closeQuietly(socket);
        }
    }
}
