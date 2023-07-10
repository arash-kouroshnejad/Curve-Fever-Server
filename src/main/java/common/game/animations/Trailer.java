package common.game.animations;

import common.gfx.editor.LevelEditor;
import common.gfx.objects.DynamicElement;
import common.persistence.Config;
import common.util.Routine;

public class Trailer extends Routine {
    public Trailer (LevelEditor editor, DynamicElement toFollow, int layerIndex) {
        super(10, () -> editor.staticInsert(toFollow.getType() + "Trail", toFollow.getX(),
                toFollow.getY(), 0, layerIndex)); // what FPS ??
    }
}
