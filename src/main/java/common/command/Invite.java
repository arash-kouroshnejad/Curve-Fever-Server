package common.command;

import common.net.data.Command;
import common.net.data.Entity;
import control.GameManager;

import java.io.Serial;

public class Invite extends Command {
    @Serial
    private static final long serialVersionUID = 5279783614427568530L;
    public Invite(Entity recipient) {
        super(recipient);
        addHeader("connection-type", "tcp");
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
        GameManager.getInstance().offerPlayer((String) getHeader("to"), entity);
    }
}
