package common.game.logic;

import common.game.animations.Trailer;
import common.gfx.editor.LevelEditor;
import common.gfx.objects.DynamicElement;
import common.gfx.util.Logic;
import common.persistence.Config;
import common.util.Routine;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class AbstractLogic extends Logic {
    protected List<DynamicElement> dynamics;
    protected LevelEditor editor;
    protected final List<Routine> trails = new ArrayList<>();


    public void init(LevelEditor editor) {
        this.editor = editor;
        int dynamicsLayer = Config.getInstance().getProperty("DynamicsLayer", Integer.class);
        dynamics = editor.getLayers().getALL_LAYERS().get(dynamicsLayer).getDynamicElements();
        setTrail();
    }

    public void killGame() {
        killTrails();
    }

    private void setTrail() {
        for (var dynamic : dynamics)
            trails.add(new Trailer(editor, dynamic, Config.getInstance().getProperty("StaticsLayer", Integer.class)));
        trails.forEach(Thread::start);
    }

    private void killTrails() {
        for (var routine : trails)
            routine.kill();
    }

    @Override
    public void handleKeyPress(int keyCode) {

    }

    @Override
    public void handleKeyRelease(int keyCode) {

    }

    @Override
    public void handleMouseClick(int x, int y) {

    }

    @Override
    public void check() {
        for (var element : dynamics)
            element.move();
    }

    @Override
    public void stop() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void reset() {

    }

    @Override
    public void pauseElementManagers() {

    }

    @Override
    public void resumeElementManagers() {

    }

    @Override
    public void paint(Graphics g) {

    }

    @Override
    public void saveGame() {

    }
}
