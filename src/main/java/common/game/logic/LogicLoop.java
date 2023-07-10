package common.game.logic;

import common.util.Routine;

public class LogicLoop extends Routine {
    public LogicLoop(AbstractLogic logic) {
        super(50, logic::check);
    }
}
