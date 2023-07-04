package common.command;

import common.net.data.Command;
import common.net.data.Entity;

public class Invite extends Command {

    public Invite(Entity recipient) {
        super(recipient);
        addHeader("connection-type", "udp");
        addHeader("type", "invite");
    }

    public Invite (Command incoming, Entity sender) {
        super(sender);
        addHeader("to", incoming.getHeader("to"));
    }

    @Override
    public boolean isValid(Command command) {
        return "invite".equals(command.getHeader("type")) && command.getHeader("to") instanceof String;
    }

    @Override
    public void run() {

    }
}
