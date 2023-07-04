package common.command;

import common.net.data.Command;
import common.net.data.Entity;

public class Offer extends Command {

    public Offer(Entity recipient) {
        super(recipient);
        addHeader("connection-type", "udp");
        addHeader("type", "offer");
    }

    public Offer(Command incoming, Entity sender) {
        super(sender);
        addHeader("from", incoming.getHeader("from"));
    }

    @Override
    public boolean isValid(Command command) {
        return "offer".equals(command.getHeader("type")) && command.getHeader("from") instanceof String;
    }

    @Override
    public void run() {

    }
}
