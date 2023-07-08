package common.command;

import common.net.data.Command;
import common.net.data.Entity;
import control.GameManager;

public class SetName extends Command {

    public SetName(Entity recipient) {
        super(recipient);
        addHeader("connection-type", "tcp");
        addHeader("type", "set-name");
    }

    public SetName(Command incoming, Entity sender) {
        super(sender);
        addHeader("name", incoming.getHeader("name"));
    }

    @Override
    public boolean isValid(Command command) {
        return "set-name".equals(command.getHeader("type")) && command.getHeader("name") instanceof String;
    }

    @Override
    public void run() {
        var name = (String) getHeader("name");
        GameManager.getInstance().registerPlayer(name, entity);
    }
}
