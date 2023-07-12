package common.command;

import common.net.data.Command;
import common.net.data.Entity;

import java.io.Serial;

public class Drop extends Command {

    @Serial
    private static final long serialVersionUID = 3065269331400959563L;

    public Drop(Entity entity) {
        super(entity);
        addHeader("connection-type", "tcp");
        addHeader("type", "drop");
    }
}
