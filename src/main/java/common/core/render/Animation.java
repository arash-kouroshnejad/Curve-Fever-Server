package common.core.render;


import common.core.util.Routine;

public class Animation extends Routine {
    public Animation(int FPS) {
        super(FPS, ViewPort.getInstance()::update);
    }
}
