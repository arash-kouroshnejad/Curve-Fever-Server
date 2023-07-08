package common.net.tcp;

import common.net.data.Command;
import common.net.data.Packet;

import java.io.IOException;

public class Server extends Thread{
    public void run() {
        try {
            var listener = new TCPListener();
            var connection = listener.listen(9000);
            while (true) {
                connection.send(new Packet(new Command(null).addHeader("shit", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed eu porta augue, a placerat est. Sed elementum nulla vitae vulputate euismod. Curabitur elit arcu, eleifend sed scelerisque in, ornare convallis leo. Donec fermentum venenatis leo, vitae sodales orci dictum non. Suspendisse cursus ligula mauris, at malesuada eros elementum vel. Suspendisse interdum sodales elit. Duis tempus orci commodo vulputate sodales. Donec sit amet volutpat eros, id vehicula orci. Suspendisse in leo at nisl condimentum tempor quis eget mauris. Vivamus faucibus nulla a quam condimentum, vitae vulputate eros efficitur. Maecenas laoreet dolor vitae nulla elementum tincidunt. Vivamus eget est elit. Sed gravida maximus gravida. Nam posuere porttitor tristique. Quisque urna nisl, molestie vel urna in, rutrum luctus nibh."), 1));
                connection.send(new Packet(new Command(null).addHeader("shit", "Cras ut enim dignissim, condimentum neque eget, rhoncus tortor. Quisque sapien libero, mattis id eleifend cursus, tristique at sem. Curabitur scelerisque venenatis porttitor. Duis viverra sit amet nibh a accumsan. Nam varius enim urna, et tempus ante semper et. Nullam at nulla ac est elementum malesuada eu at nisi. Suspendisse ultrices luctus mollis. Morbi ac pharetra diam. Mauris quis magna aliquet, scelerisque diam sed, dapibus nulla. Phasellus quis viverra velit. Fusce commodo ex eu erat blandit laoreet. Ut ligula risus, efficitur eget hendrerit id, iaculis quis arcu. Suspendisse at accumsan elit."), 1));
                connection.send(new Packet(new Command(null).addHeader("shit", "Etiam vitae tellus quis ipsum luctus sagittis non eu dui. Integer cursus neque ut ex accumsan pharetra. Nullam fringilla odio lectus, ac congue neque efficitur et. Sed id imperdiet orci, eu cursus dui. Aliquam fermentum pulvinar orci, et blandit massa elementum non. Donec elementum tempus massa, quis suscipit nunc semper nec. Cras eget purus ut mauris tempor congue."), 1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
