package common.core.editor;

import common.core.render.ViewPort;
import common.core.util.Routine;

public class MovementThread extends Routine {
    public MovementThread() {
        super(50, () -> {
            var element = ViewPort.getInstance().getLockedElement();
            if (element != null)
                element.move();
        });
    }
}
