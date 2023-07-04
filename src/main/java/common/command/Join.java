package common.command;

import common.net.data.Command;
import common.net.data.Entity;
import control.GameManager;

public class Join extends Command {

    public Join(Entity recipient) {
        super(recipient);
        addHeader("connection-type", "udp");
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
