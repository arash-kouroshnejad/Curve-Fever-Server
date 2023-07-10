package common.command;

import common.net.data.Command;
import common.net.data.Entity;

import java.io.Serial;

public class List extends Command {
    @Serial
    private static final long serialVersionUID = -6818529734159577152L;
    public List(Entity recipient) {
        super(recipient);
        addHeader("connection-type", "tcp");
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
