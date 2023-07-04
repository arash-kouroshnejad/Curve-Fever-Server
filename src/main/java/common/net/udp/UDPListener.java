package common.net.udp;

import common.net.Connection;
import common.net.Listener;
import common.net.data.Command;
import common.net.data.Packet;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPListener extends Listener {
    protected DatagramSocket socket;
    @Override
    public Connection listen(int portNumber) throws IOException {
        if (!started)
            init(portNumber);
        return new ConnectionImpl(socket);
    }

    @Override
    protected void init(int portNumber) throws IOException {
        socket = new DatagramSocket(portNumber);
        started = true;
    }

    @Override
    public void close() throws IOException {}

    private static class ConnectionImpl extends UDPConnection {
        InetAddress remoteAddress;
        int remotePort;
        public ConnectionImpl(DatagramSocket socket) throws IOException {
            this.socket = socket;
            handShake();
        }

        @Override
        public void send(Packet data) throws IOException {
            if (!connected)
                throw new RuntimeException("Address not set!");
            var bytes = Packet.toBytes(data);
            /*int lastIndex = 0;
            int iteration = (int) Math.ceil(bytes.length / 1024f);
            for (int i = 0; i < iteration; i++) {
                System.arraycopy(bytes, lastIndex, buffer, 0, Math.min(1024, bytes.length - lastIndex));
                var packet = new DatagramPacket(buffer, Math.min(1024, bytes.length - lastIndex), remoteAddress, remotePort);
                socket.send(packet);
                lastIndex += 1024;
            }*/
            byte[] length = toBytes(bytes.length);
            System.arraycopy(length, 0, buffer, 0, 2);
            System.arraycopy(bytes, 0, buffer, 2, bytes.length);
            var datagramPacket = new DatagramPacket(buffer, bytes.length + 2, remoteAddress, remotePort);
            socket.send(datagramPacket);
        }

        protected void init(DatagramPacket firstPacket) {
            remoteAddress = firstPacket.getAddress();
            remotePort = firstPacket.getPort();
            connected = true;
        }

        /*@Override
        protected DatagramPacket readPacket() throws IOException {
            if (!connected) {
                var packet = super.readPacket();
                init(packet);
                return packet;
            }
            return super.readPacket();
        }*/

        @Override
        protected void handShake() throws IOException {
            var datagramPacket = readPacket();
            var universalPacket = generateUniversalPacket(datagramPacket);
            while (universalPacket.id != -1 || !universalPacket.command.headers.containsKey("ping")) {
                datagramPacket = readPacket();
                universalPacket = generateUniversalPacket(datagramPacket);
            }
            init(datagramPacket);
            for (int i = 0; i< 10; i++) {
                send(new Packet(new Command(null).addHeader("ack", "ack"), -1));
            }
        }
    }
}
