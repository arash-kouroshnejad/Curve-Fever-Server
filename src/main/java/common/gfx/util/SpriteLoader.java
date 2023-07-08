package common.gfx.util;

import common.gfx.render.GameEngine;
import common.persistence.Config;

import java.awt.*;
import java.util.Arrays;

public abstract class SpriteLoader extends Loader {

    private final String[] dynamicTypes;

    public SpriteLoader(String PathToMaps, GameEngine engine) {
        super(Config.getInstance().getProperty("SpritesDir"), PathToMaps, engine);
        Config c = Config.getInstance();
        TYPES = c.getProperty("TYPES").split(",");
        for (String type : TYPES) {
            String[] parsed = c.getProperty(type).split("-");
            references.put(type, parsed[0].split(","));
            parsed = parsed[1].split("x");
            int[] dims = new int[parsed.length];
            for ( int i = 0; i < parsed.length; i++) {
                dims[i] = Integer.parseInt(parsed[i]);
            }
            dimensions.put(type, new Dimension(dims[0], dims[1]));
        }
        dynamicTypes = c.getProperty("Dynamics").split(",");
        lockedElements = c.getProperty("LockedElements").split(",");
        loadSprites();
    }

    @Override
    public boolean isDynamic(String type) {
        return Arrays.asList(dynamicTypes).contains(type);
    }
}
