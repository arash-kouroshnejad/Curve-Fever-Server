package common.util;

import common.command.*;
import common.net.data.Command;
import common.net.data.Entity;

public class CommandFactory {
    public Command join(Entity recipient) {
        return new Join(recipient);
    }

    public Command register(Entity recipient) {
        return new Register(recipient);
    }

    public Command setName(Entity recipient, String name) {
        return new SetName(recipient);
    }

    public Command list(Entity recipient) {
        return new List(recipient);
    }

    public Command Offer(Entity recipient, String from) {
        return new Offer(recipient).addHeader("from", from);
    }

    public Command result(Entity recipient, String result, String name) {
        return new Result(recipient).addHeader("result", result).addHeader("name", name);
    }

    public Command invite(Entity recipient, String to) {
        return new Invite(recipient).addHeader("to", to);
    }

    public Command info(Entity recipient, String message) {
        return new Info(recipient).addHeader("info", message);
    }
}
