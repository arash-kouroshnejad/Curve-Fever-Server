package common.command;

import common.net.data.Command;
import common.net.data.Entity;

public class List extends Command {

    public List(Entity recipient) {
        super(recipient);
        addHeader("connection-type", "udp");
        addHeader("type", "list");
    }

    public List(Command incoming, Entity sender) {
        super(sender);
        addHeader("users", incoming.getHeader("users"));
    }

    @Override
    public boolean isValid(Command command) {
        return "list".equals(command.getHeader("type")) && command.getHeader("users") instanceof String;
    }

    @Override
    public void run() {

    }
}
