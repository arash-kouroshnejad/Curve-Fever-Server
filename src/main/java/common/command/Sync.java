package common.command;

import common.net.data.Command;
import common.net.data.Entity;

import java.io.Serial;

public class Sync extends Command {

    @Serial
    private static final long serialVersionUID = 7508476361235375817L;

    public Sync(Entity entity) {
        super(entity);
        addHeader("connection-type", "udp");
        addHeader("type", "sync");
    }
}
