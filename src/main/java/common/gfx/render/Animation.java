package common.gfx.render;


import common.util.Routine;

public class Animation extends Routine {
    public Animation(int FPS, GameEngine engine) {
        super(FPS, engine.viewPort::update);
    }
}
