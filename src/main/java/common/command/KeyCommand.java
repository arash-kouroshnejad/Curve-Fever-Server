package common.command;

import common.net.data.Command;
import common.net.data.Entity;
import control.GameManager;

import java.io.Serial;

public class KeyCommand extends Command {
    @Serial
    private static final long serialVersionUID = 4005191642270925226L;
    public KeyCommand(Entity entity) {
        super(entity);
        addHeader("connection-type", "tcp");
        addHeader("type", "key-pressed");
    }

    public KeyCommand(Command incoming, Entity sender) {
        super(sender);
        addHeader("key", incoming.getHeader("key"));
        addHeader("change", incoming.getHeader("change"));
    }

    @Override
    public boolean isValid(Command command) {
        return "key-pressed".equals(command.getHeader("type")) && command.getHeader("key") instanceof  Integer &&
                command.getHeader("change") instanceof String;
    }

    @Override
    public void run() {
        GameManager.getInstance().notifyLogic(this);
    }
}
