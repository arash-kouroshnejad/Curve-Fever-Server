package common.command;

import common.net.data.Command;
import common.net.data.Entity;
import control.GameManager;

import java.io.Serial;

public class Result extends Command {
    @Serial
    private static final long serialVersionUID = 1880671560898363549L;

    public Result(Entity recipient) {
        super(recipient);
        addHeader("connection-type", "tcp");
        addHeader("type", "result");
    }

    public Result(Command incoming, Entity sender) {
        super(sender);
        addHeader("result", incoming.getHeader("result"));
        addHeader("name", incoming.getHeader("name"));
    }

    @Override
    public boolean isValid(Command command) {
        var result = command.getHeader("result");
        return "result".equals(command.getHeader("type")) && ( "acc".equals(result) || "rej".equals(result))
                && command.getHeader("name") instanceof String;
    }

    @Override
    public void run() {
        try {
            GameManager.getInstance().allocateContainer((String) getHeader("name"), entity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
