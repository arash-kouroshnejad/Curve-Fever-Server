package common.net.udp;

import common.net.Connection;
import common.net.data.Command;
import common.net.data.Packet;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Timer;
import java.util.TimerTask;

/* id -1 is reserved */
public class UDPConnection extends Connection {
    protected DatagramSocket socket;
    protected final byte[] buffer = new byte[65000];

    @Override
    public void connectTo(String remoteAddress, int remotePort, String localAddress, int localPort) throws IOException {
        socket = new DatagramSocket();
        socket.connect(InetAddress.getByName(remoteAddress), remotePort);
        handShake();
        connected = true;
    }

    @Override
    public void disconnect() throws IOException {
        if (connected) {
            socket.close();
            connected = false;
        } else
            throw new RuntimeException("Socket already closed!");
    }

    @Override
    public void send(Packet data) throws IOException {
        var bytes = Packet.toBytes(data);
        byte[] length = toBytes(bytes.length);
        System.arraycopy(length, 0, buffer, 0, 2);
        System.arraycopy(bytes, 0, buffer, 2, bytes.length);
        var datagramPacket = new DatagramPacket(buffer, bytes.length + 2);
        socket.send(datagramPacket);
    }

    protected DatagramPacket readPacket() throws IOException {
        var input = new DatagramPacket(buffer, buffer.length);
        socket.receive(input);
        return input;
    }

    @Override
    public synchronized Packet fetch() throws IOException {
        var packet = generateUniversalPacket(readPacket());
        if (connected)
            while (packet.id == -1)
                packet = generateUniversalPacket(readPacket());
        return packet;
    }

    protected void handShake() throws IOException {
        var timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                try {
                    send(new Packet(new Command(null).addHeader("ping", "ping"), -1));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        },0, 500);
        var universalPacket = fetch();
        while (universalPacket.id != -1 || !universalPacket.command.headers.containsKey("ack"))
            universalPacket = fetch();
        timer.cancel();
    }

    protected Packet generateUniversalPacket(DatagramPacket datagramPacket) {
        return Packet.toPacket(datagramPacket.getData(), toHex(datagramPacket.getData()));
    }

    protected byte[] toBytes(int length) {
        byte[] out = new byte[2];
        int ind1 = length / 256;
        int ind2 = length - 256 * ind1;
        out[0] = (byte) ind1;
        out[1] = (byte) ind2;
        return out;
    }

    protected int toHex(byte[] input) {
        return (input[0] & 0xFF) * 256 + (input[1] & 0xFF);
    }

    public void setTimeout(int millis) throws SocketException {
        socket.setSoTimeout(millis);
    }
}
