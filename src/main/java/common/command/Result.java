package common.command;

import common.net.data.Command;
import common.net.data.Entity;

public class Result extends Command {

    public Result(Entity recipient) {
        super(recipient);
        addHeader("connection-type", "udp");
        addHeader("type", "result");
    }

    public Result(Command incoming, Entity sender) {
        super(sender);
        addHeader("result", incoming.getHeader("result"));
    }

    @Override
    public boolean isValid(Command command) {
        var result = command.getHeader("result");
        return "result".equals(command.getHeader("type")) && ( "acc".equals(result) || "rej".equals(result));
    }

    @Override
    public void run() {

    }
}
