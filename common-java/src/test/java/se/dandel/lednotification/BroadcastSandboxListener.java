package se.dandel.lednotification;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

public class BroadcastSandboxListener {
    private final Logger logger = Logger.getLogger(getClass());

    public static void main(String[] args) {
        BroadcastSandboxListener listener = new BroadcastSandboxListener();
        try {
            listener.listen();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void listen() throws Exception {
        DatagramSocket socket = null;
        try {
            socket = new DatagramSocket(5001, InetAddress.getByName("127.0.0.1"));
            byte[] buf = new byte[512];
            DatagramPacket packet = new DatagramPacket(buf, buf.length);
            while (true) {
                logger.info("Waiting for data");
                socket.receive(packet);
                String message = new String(buf, "UTF8");
                logger.info("Data received: " + message);
            }
        } finally {
            IOUtils.closeQuietly(socket);
        }
    }

}
