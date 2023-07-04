package common.core.editor;

import common.core.objects.*;
import common.core.render.ViewPort;
import common.core.util.Loader;
import common.core.util.Semaphore;
import common.persistence.Config;
import common.core.objects.*;
import common.core.render.GameEngine;
import common.core.util.Logic;

import java.awt.*;
import java.util.ArrayList;
import java.util.Optional;

public class LevelEditor extends GameEngine {
    private final static LevelEditor instance = new LevelEditor();

    protected LevelEditor(){}

    public static LevelEditor getInstance() {
        return instance;
    }

    private Loader loader;

    private SpritePicker spritesFrame;

    private Logic gameLogic;

    private MapCreator creator;

    private DynamicElement lastAdded;

    private Semaphore mutex = Semaphore.getMutex();

    public void init(MapLoader loader, Logic gameLogic, MapCreator creator) {
        this.loader = loader;
        this.creator = creator;
        this.gameLogic = gameLogic;
        GameEngine.getInstance().enableEditorMode();
        loader.loadMap(Config.getInstance().getProperty("EditorInputMap", Integer.class));
        gameLogic.setLockedElement(ViewPort.getInstance().getLockedElement());
        GameEngine.getInstance().init(gameLogic);
        spritesFrame = new SpritePicker();
        new MovementThread().start();
    }

    public void createMap() {
        int id = Config.getInstance().getProperty("EditorOutputMap", Integer.class);
        Map map = new Map(Layers.getInstance().getALL_LAYERS(), id);
        creator.saveMap(map, id);
    }

    public Loader getLoader() {
        return loader;
    }

    protected synchronized void insertAt(String type, int x, int y, int state, int layerIndex) {
        insertAt(type, x, y, state, spritesFrame.getSpeedX(), spritesFrame.getSpeedY(), layerIndex);
    }
    // static element insertion

    public synchronized void staticInsert(String type, int x, int y, int state, int layerIndex) {
        insertAt(type, x, y, state, 0, 0, layerIndex);
    }

    public synchronized void insertAt(String type, int x, int y, int state, int speedX, int speedY, int layerIndex) {
        mutex.acquire();
        // ugly code, layers is redundant really !!
        java.util.List<Layer> layers = this.layers.getALL_LAYERS();
        if (layers.size() <= layerIndex) {
            for (int i = layers.size(); i < layerIndex + 1; i++)
                layers.add(i, new Layer(new ArrayList<>(), new ArrayList<>(), layerIndex));
        }
        Layer layer = layers.get(layerIndex);
        Dimension d = loader.getDimension(type);
        if (loader.isDynamic(type)) {
            DynamicElement element = new DynamicElement(x, y, d.width, d.height, speedX, speedY,
                    type);
            if (loader.isLocked(type)) {
                element.setLockedCharacter();
                ViewPort.getInstance().setLockedElement(element);
                GameEngine.getInstance().getGameLogic().setLockedElement(element);
                layer.addDynamicElement(element, 0); // TODO : funky logic depends on the order fix it and restore level editor sanity!
            }
            else
                layer.addDynamicElement(element);
            element.setImages(loader.getSprite(type));
            element.swapImage(state);
            lastAdded = element;
        }
        else {
            StaticElement element = new StaticElement(x, y, d.width, d.height, type);
            layer.addStaticElement(element);
            element.setImages(loader.getSprite(type));
            element.swapImage(state);
        }
        mutex.release();
    }
    // dynamic element insertion

    public synchronized void attachManager(Class<? extends ElementManager> c) {
        mutex.acquire();
        try {
            var manager = c.getConstructor(DynamicElement.class).newInstance(lastAdded);
            lastAdded.setManager(manager);
            manager.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
        mutex.release();
    }

    public synchronized void removeElement(String type, int layerIndex) {
        mutex.acquire();
        getDynamicElement(type, layerIndex, 0).ifPresent(this::removeElement);
        getStaticElement(type, layerIndex, 0).ifPresent(this::removeElement);
        mutex.release();
    }

    public synchronized void removeElement(StaticElement element) {
        mutex.acquire();
        element.setHidden(true);
        for (var layer : layers.getALL_LAYERS()) {
            if (element instanceof DynamicElement) {
                layer.getDynamicElements().remove((DynamicElement) element);
                if (((DynamicElement) element).getManager() != null)
                    ((DynamicElement) element).getManager().kill();
            }
            else
                layer.getStaticElements().remove(element);
        }
        mutex.release();
    }

    public Optional<DynamicElement> getDynamicElement(String type, int layerIndex, int index) { // TODO : integrate all these methods
        if (layers.getALL_LAYERS().size() > layerIndex) {
            var elements = layers.getALL_LAYERS().get(layerIndex).getDynamicElements();
            if (elements.size() > index && index != -1) {
                int current = -1;
                for (var element : elements) {
                    if (element.getType().equals(type))
                        current++;
                    if (current == index)
                        return Optional.of(element);
                }
                return Optional.empty();
            }
            else if (elements.size() != 0) {
                for (var element : elements)
                    if (element.getType().equals(type))
                        return Optional.of(element);
            }
        }
        return Optional.empty();
    }

    public Optional<StaticElement> getStaticElement(String type, int layerIndex, int index) {
        if (layers.getALL_LAYERS().size() > layerIndex) {
            var elements = layers.getALL_LAYERS().get(layerIndex).getStaticElements();
            if (elements.size() > index && index != -1) {
                int current = -1;
                for (var element : elements) {
                    if (element.getType().equals(type))
                        current++;
                    if (current == index)
                        return Optional.of(element);
                }
                return Optional.empty();
            }
            else if (elements.size() > 0)
                for (var element : elements)
                    if (element.getType().equals(type))
                        return Optional.of(element);
        }
        return Optional.empty();
    }

    public void setLoader(Loader loader) {
        this.loader = loader;
    }
}