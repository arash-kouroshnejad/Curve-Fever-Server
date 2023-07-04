package common.net.udp;

import common.net.Connection;
import common.net.data.Command;
import common.net.data.Packet;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.InetAddress;

public class Server extends Thread{
    public void run () {
        var listener = new UDPListener();
        Connection connection;
        try {
            connection = listener.listen(9000);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        /*Class<?> clazz = Class.forName("common.net.udp.UDPListener$ConnectionImpl");
        var f1 = clazz.getDeclaredField("remoteAddress");
        f1.setAccessible(true);
        var f2 = clazz.getDeclaredField("remotePort");
        f2.setAccessible(true);
        f1.set(connection, InetAddress.getByName("localhost"));
        f2.set(connection, 100);
        connection.send(new Packet(new Command(), 10));*/
        while (true) {
            Packet packet;
            try {
                packet = connection.fetch();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            var k = packet.command.headers.keySet().toArray()[0];
            var v = (double) packet.command.getHeader(k);
            System.out.println("server : " + v);
            var newPacket = new Packet(new Command(null).addHeader(k, v + 1), 10);
            try {
                connection.send(newPacket);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
