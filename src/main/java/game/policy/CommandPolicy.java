package game.policy;

import common.game.logic.AbstractPolicy;
import common.net.data.Command;
import game.logic.GameLogic;

public abstract class CommandPolicy extends AbstractPolicy {
    protected static final int RIGHT = 39;
    protected static final int LEFT =37;

    protected final GameLogic gameLogic;

    public CommandPolicy(GameLogic gameLogic) {
        this.gameLogic = gameLogic;
    }

    public abstract void enforce(Command command);
    public abstract boolean isEnforceable(Command command);
}
