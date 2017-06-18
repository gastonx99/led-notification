package se.dandel.lednotification.button;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketTimeoutException;

public class UdpAPI {

    private static final byte[] BUFFER = new byte[512];
    private MulticastSocket socket;

    public static final class IPAddress {
        private String address;

        public IPAddress(String address) {
            this.address = address;
        }

        public String getAddress() {
            return address;
        }
    }

    public void begin(int port) throws IOException {
        socket = new MulticastSocket(port);
    }

    public int available() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public void beginPacket(IPAddress remoteIP, int remotePort) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public void endPacket() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public void write(String message) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public void write(byte[] buffer, int size) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public int parsePacket() throws IOException {
        if (socket.getSoTimeout() == 0) {
            socket.setSoTimeout(1);
        }
        try {
            DatagramPacket packet = new DatagramPacket(BUFFER, BUFFER.length);

            socket.receive(packet);
            return packet.getLength();
        } catch (SocketTimeoutException e) {
            return 0;
        }
    }

    public int read() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public int read(byte[] packetBuffer, int maxSize) {
        for (int i = 0; i < maxSize; i++) {
            packetBuffer[i] = BUFFER[i];
        }
        return maxSize;
    }

    public void stop() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public void joinMulticast(IPAddress ipAddress) throws IOException {
        InetAddress group = InetAddress.getByName(ipAddress.getAddress());
        socket.joinGroup(group);
    }

    public void leaveMulticast(IPAddress ipAddress) throws IOException {
        InetAddress group = InetAddress.getByName(ipAddress.getAddress());
        socket.leaveGroup(group);
    }


}
