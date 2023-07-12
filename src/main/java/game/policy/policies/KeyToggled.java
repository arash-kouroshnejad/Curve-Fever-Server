package game.policy.policies;

import common.net.data.Command;
import game.logic.GameLogic;
import game.model.Rewards;
import game.policy.CommandPolicy;
import game.policy.PolicyReference;

public class KeyToggled extends CommandPolicy{

    public KeyToggled(GameLogic gameLogic, PolicyReference policyReference) {
        super(gameLogic, policyReference);
    }

    @Override
    public void enforce(Command command) {
        var code = (int) command.getHeader("key");
        var pressed = ("pressed".equals(command.getHeader("change")));
        var player1 = command.getEntity() == policyReference.getPlayer1().entity();
        boolean inverted;
        if (player1)
            inverted = policyReference.getPlayer1State() == Rewards.Confuse;
        else
            inverted = policyReference.getPlayer2State() == Rewards.Confuse;
        if (pressed)
            gameLogic.rotatePlayer(command.getEntity(), inverted == (code != RIGHT));
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
