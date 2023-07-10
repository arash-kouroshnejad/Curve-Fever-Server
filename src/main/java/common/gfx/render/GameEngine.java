package common.gfx.render;

import common.gfx.objects.Layer;
import common.gfx.objects.Layers;
import common.gfx.objects.Map;
import common.gfx.util.Logic;
import common.gfx.util.Semaphore;

import java.awt.*;


public class GameEngine {
    GameFrame gameFrame;

    private boolean started;

    private boolean editorMode;

    protected final ViewPort viewPort = new ViewPort();


    protected Layers layers = new Layers();

    private Map map;

    private final Semaphore mutex = Semaphore.getMutex();

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    private Logic gameLogic;

    public Logic getGameLogic() {
        return gameLogic;
    }

    public void setGameLogic(Logic gameLogic) {
        this.gameLogic = gameLogic;
    }

    private Animation animationAgent;

    private boolean customPainting;

    public void init(Logic gameLogic) {
        this.gameLogic = gameLogic;
        gameFrame = new GameFrame(this);
        viewPort.setFrame(gameFrame);
        animationAgent = new Animation(50, this);
        animationAgent.start();
    }

    public void startGame() {
        started = true;
    }

    public void closeGame() {
        started = false;
        gameFrame.setVisible(false);
        animationAgent.kill();
        customPainting = false;
        editorMode = false;
    }

    public void enableEditorMode() {
        editorMode = true;
    }

    public void pauseAnimation() {
        animationAgent.pause();
    }

    public void resumeAnimation() {
        animationAgent.restart();
    }

    public void paint(Graphics g) {
        mutex.acquire();
        java.util.List<Layer> allLayers = layers.getALL_LAYERS();
        if (allLayers != null) {
            for (Layer layer : allLayers) {
                for (var element : layer.getStaticElements()) {
                    if (viewPort.inView(element) && !element.isHidden()) {
                        g.drawImage(element.getImage(), element.getX() - viewPort.getX(), element.getY() - viewPort.getY(),
                                element.getWidth(), element.getHeight(), gameFrame);
                    }
                }
                for (var element : layer.getDynamicElements()) {
                    if (viewPort.inView(element) && !element.isHidden()) {
                        g.drawImage(element.getImage(), element.getX() - viewPort.getX(), element.getY() - viewPort.getY(),
                                element.getWidth(), element.getHeight(), gameFrame);
                    }
                }
            }
            if (started && customPainting) {
                gameLogic.paint(g);
            }
        }
        mutex.release();
    }

    protected void resize(Dimension dim) {
        viewPort.setWidth(dim.width);
        viewPort.setHeight(dim.height);
    }

    public void enableCustomPainting() {
        customPainting = true;
    }

    public Layers getLayers() {
        return layers;
    }

    public ViewPort getViewPort() {
        return viewPort;
    }
}
