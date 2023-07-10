package game.policy.policies;

import common.net.data.Command;
import game.logic.GameLogic;
import game.policy.CommandPolicy;

public class KeyToggled extends CommandPolicy{

    public KeyToggled(GameLogic gameLogic) {
        super(gameLogic);
    }

    @Override
    public void enforce(Command command) {
        var code = (int) command.getHeader("key");
        var pressed = ("pressed".equals(command.getHeader("change")));
        if (pressed)
            gameLogic.rotatePlayer(command.getEntity(), code == RIGHT);
        else
            gameLogic.pauseRotation(command.getEntity());
    }

    @Override
    public boolean isEnforceable(Command command) {
        try {
            var code = (int) command.getHeader("key");
            var change = (String) command.getHeader("change");
            return code == RIGHT || code == LEFT && ("pressed".equals(change) || "released".equals(change));
        } catch (ClassCastException e) {
            return false;
        }
    }
}
