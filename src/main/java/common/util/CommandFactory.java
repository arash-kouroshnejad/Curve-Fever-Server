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

    public Command setName(Entity recipient) {
        return new SetName(recipient);
    }

    public Command list(Entity recipient) {
        return new List(recipient);
    }

    public Command Offer(Entity recipient) {
        return new Offer(recipient);
    }

    public Command result(Entity recipient) {
        return new Result(recipient);
    }

    public Command invite(Entity recipient) {
        return new Invite(recipient);
    }
}
