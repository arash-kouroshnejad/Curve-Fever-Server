package common.command;

import common.net.data.Command;
import common.net.data.Entity;

import java.io.Serial;

public class Offer extends Command {
    @Serial
    private static final long serialVersionUID = 4622476813573410587L;

    public Offer(Entity recipient) {
        super(recipient);
        addHeader("connection-type", "tcp");
        addHeader("type", "offer");
    }

    public Offer(Command incoming, Entity sender) {
        super(sender);
        addHeader("from", incoming.getHeader("from"));
    }

    @Override
    public boolean isValid(Command command) {
        return "offer".equals(command.getHeader("type")) && command.getHeader("from") instanceof String;
    }

    @Override
    public void run() {

    }
}
