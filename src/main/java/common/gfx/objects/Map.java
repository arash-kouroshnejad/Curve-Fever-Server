package common.gfx.objects;

import common.gfx.render.GameEngine;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Map {
    private static int currentId;
    private final CopyOnWriteArrayList<Layer> LAYERS;
    private final int ID;
    private DynamicElement lockedCharacter;
    public void setLockedCharacter(DynamicElement lockedCharacter) {
        this.lockedCharacter = lockedCharacter;
    }

    public Map(CopyOnWriteArrayList<Layer> LAYERS, int ID) {
        this.LAYERS = LAYERS;
        this.ID = ID;
    }

    public List<Layer> getLAYERS() {
        return LAYERS;
    }
    public int getID() {
        return ID;
    }
    public void init(GameEngine engine) {
        engine.getLayers().setALL_LAYERS(LAYERS);
        engine.getViewPort().setLockedElement(lockedCharacter);
        engine.setMap(this);
        currentId = ID;
    }

    public static int getCurrentId() {
        return currentId;
    }
}
