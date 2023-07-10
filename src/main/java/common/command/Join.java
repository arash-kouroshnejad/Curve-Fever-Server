package common.command;

import common.net.data.Command;
import common.net.data.Entity;
import control.GameManager;

import java.io.Serial;

public class Join extends Command {
    @Serial
    private static final long serialVersionUID = 7190921986862550958L;
    public Join(Entity recipient) {
        super(recipient);
        addHeader("connection-type", "tcp");
        addHeader("type", "join");
    }

    public Join(Command incoming, Entity sender) {
        super(sender);
    }

    @Override
    public boolean isValid(Command command) {
        return "join".equals(command.getHeader("type"));
    }

    @Override
    public void run() {
        GameManager.getInstance().admitClient(entity);
    }
}
