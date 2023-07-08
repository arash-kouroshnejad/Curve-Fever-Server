package common.command;

import common.net.data.Command;
import common.net.data.Entity;
import control.GameManager;

public class Register extends Command {

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
