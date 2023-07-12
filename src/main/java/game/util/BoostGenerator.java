package game.util;

import common.gfx.editor.LevelEditor;
import common.gfx.objects.StaticElement;
import common.persistence.Config;
import common.util.Routine;
import game.model.Rewards;

import java.awt.*;
import java.util.List;

import static game.util.Collision.collides;

public class BoostGenerator extends Routine {

    public BoostGenerator(LevelEditor editor) {
        super(0.001, new Generator(editor));
    }

    private static String getRandomItem() {
        var rand = Math.random();
        var index = (int) (rand * Rewards.values().length);
        return Rewards.values()[index].getType();
    }


    private static class Generator implements Runnable {
        LevelEditor levelEditor;
        final Dimension screenDimensions; // todo : get the client side dimensions not the server side !!
        int dynamicsLayer = Config.getInstance().getProperty("DynamicsLayer", Integer.class);
        int staticsLayer = Config.getInstance().getProperty("StaticsLayer", Integer.class);
        final List<StaticElement> statics;

        public Generator(LevelEditor levelEditor) {
            this.levelEditor = levelEditor;
            screenDimensions = levelEditor.getScreenDimensions();
            statics = levelEditor.getLayers().getALL_LAYERS().get(staticsLayer).getStaticElements();
        }

        @Override
        public void run() {
            var x = (screenDimensions.width - 10) * Math.random() + 10;
            var y = (screenDimensions.height - 10) * Math.random() + 10;
            var type = getRandomItem();
            levelEditor.insertAt(type, (int) x, (int) y, 0, 0, 0, dynamicsLayer);
            var object = levelEditor.getDynamicElement(type, dynamicsLayer, 0).get();
            if (collides(object, statics)) {
                levelEditor.removeElement(object);
                run();
            }
        }
    }
}
