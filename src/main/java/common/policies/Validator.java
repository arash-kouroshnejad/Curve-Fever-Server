package common.policies;

import common.command.*;
import common.net.data.Command;
import common.net.data.Entity;
import common.net.data.Packet;
import common.net.agent.PacketValidator;
import control.GameManager;

public class Validator extends PacketValidator {
    private final GameManager manager = GameManager.getInstance();
    public Validator() {
        commandList.add(new Join(null));
        // commandList.add(new Register(null));
        commandList.add(new SetName(null));
        // commandList.add(new List(null));
        commandList.add(new Invite(null));
        // commandList.add(new Offer(null));
        commandList.add(new Result(null));
        commandList.add(new Info(null));
        commandList.add(new KeyCommand(null));
    }
    @Override
    public void validate(Packet incoming) {
        try {
            for (var command : commandList)
                if (command.isValid(incoming.command)) {
                    var clazz = command.getClass();
                    var constructor = clazz.getConstructor(Command.class, Entity.class);
                    var finalCommand = constructor.newInstance(incoming.command, incoming.entity);
                    manager.execute(finalCommand);
                }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
