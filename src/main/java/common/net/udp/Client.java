package common.net.udp;


import common.net.data.Command;
import common.net.data.Packet;


public class Client extends Thread{
    public void run() {
        try {
            var connection = new UDPConnection();
            connection.connectTo("localhost", 9000, "localhost", 8000);
            connection.send(new Packet(new Command(null).addHeader("shit", 1), 10));
            while (true) {
                var packet = connection.fetch();
                var k = packet.command.headers.keySet().toArray()[0];
                var v = (double) packet.command.getHeader(k);
                System.out.println("client : " + v);
                var newPacket = new Packet(new Command(null).addHeader(k, v + 1), 10);
                connection.send(newPacket);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
