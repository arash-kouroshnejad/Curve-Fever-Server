package common.command;

import common.net.data.Command;
import common.net.data.Entity;

public class Info extends Command {
    public Info(Entity entity) {
        super(entity);
        addHeader("connection-type", "tcp");
        addHeader("type", "info");
    }
    public Info(Command incoming, Entity sender) {
        super(sender);
        addHeader("info", getHeader("info"));
    }

    @Override
    public boolean isValid(Command command) {
        return "info".equals(getHeader("type")) && getHeader("info") instanceof String;
    }

    @Override
    public void run() {
        System.out.println(getHeader("info"));
    }
}
