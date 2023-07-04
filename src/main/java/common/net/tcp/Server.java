package common.net.tcp;

import common.net.data.Command;
import common.net.data.Packet;

import java.io.IOException;

public class Server {
    public static void main(String[] args) throws IOException {
        var listener = new TCPListener();
        var connection = listener.listen(9000);
        connection.send(new Packet(new Command(null), 1000));
    }
}
