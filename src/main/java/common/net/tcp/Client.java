package common.net.tcp;

import java.io.IOException;

public class Client {
    public static void main(String[] args) throws IOException {
        var connection = new TCPConnection();
        connection.connectTo("localhost", 9000, "localhost", 8000);
        System.out.println(connection.fetch());
    }
}
