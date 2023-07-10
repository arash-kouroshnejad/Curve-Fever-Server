package common.command;

import common.net.data.Command;
import common.net.data.Entity;
import control.GameManager;

import java.io.Serial;

public class Register extends Command {

    @Serial
    private static final long serialVersionUID = -1501585029953646666L;
    public Register (Entity recipient) {
        super(recipient);
        addHeader("connection-type", "tcp");
        addHeader("type", "register");
    }

    public Register(Command incoming, Entity sender) {
        super(sender);
    }

    @Override
    public boolean isValid(Command command) {
        return "register".equals(command.getHeader("type"));
    }

    @Override
    public void run() {

    }
}
