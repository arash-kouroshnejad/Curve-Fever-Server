package common.gfx.util;

import common.gfx.objects.DynamicElement;

import java.awt.*;

public abstract class Logic {
    protected final static int RIGHT = 2;
    protected final static int LEFT = -2;
    protected final static int DOWN = 2;
    protected final static int UP = -2;
    protected DynamicElement lockedElement;
    public void setLockedElement(DynamicElement lockedElement) {
        this.lockedElement = lockedElement;
    }
    public abstract void handleKeyPress(int keyCode);
    public abstract void handleKeyRelease(int keyCode);
    public abstract void handleMouseClick(int x, int y);
    public abstract void check();
    public abstract void stop();
    public abstract void resume();
    public abstract  void reset();
    public abstract void pauseElementManagers();
    public abstract void resumeElementManagers();
    public abstract void paint(Graphics g);
    public abstract void saveGame();
}
