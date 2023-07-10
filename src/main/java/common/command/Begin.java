package common.command;

import common.net.data.Command;
import common.net.data.Entity;

import java.io.Serial;

public class Begin extends Command {
    @Serial
    private static final long serialVersionUID = -7952553533562148062L;
    public Begin(Entity entity) {
        super(entity);
        addHeader("connection-type", "tcp");
        addHeader("type", "begin");
    }
}
