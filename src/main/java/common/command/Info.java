package common.command;

import common.net.data.Command;
import common.net.data.Entity;

import java.io.Serial;

public class Info extends Command {
    @Serial
    private static final long serialVersionUID = -5580762030705563565L;
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
