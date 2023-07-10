package common.gfx.editor;

import common.gfx.render.GameEngine;
import common.util.Routine;

public class MovementThread extends Routine {
    public MovementThread(GameEngine engine) {
        super(50, () -> {
            var element = engine.getViewPort().getLockedElement();
            if (element != null)
                element.move();
        });
    }
}
