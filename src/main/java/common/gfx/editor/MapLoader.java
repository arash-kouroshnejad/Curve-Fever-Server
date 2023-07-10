package common.gfx.editor;

import com.google.gson.Gson;
import common.gfx.objects.DynamicElement;
import common.gfx.objects.Layer;
import common.gfx.objects.Map;
import common.gfx.objects.StaticElement;
import common.gfx.render.GameEngine;
import common.gfx.util.SpriteLoader;

import java.io.FileReader;
import java.util.List;

public class MapLoader extends SpriteLoader {
    public MapLoader(String PathToMaps, GameEngine engine) {
        super(PathToMaps, engine);
    }

    public void loadMap(int ID) {
        Gson gson = new Gson();
        try {
            FileReader reader = new FileReader(PathToMaps + ID + ".map");
            Map map = gson.fromJson(reader, Map.class);
            List<Layer> layers = map.getLAYERS();
            for (Layer layer : layers) {
                for (StaticElement element : layer.getStaticElements()) {
                    element.setImages(getSprite(element.getType()));
                }
                for (DynamicElement de : layer.getDynamicElements()) {
                    de.setImages(getSprite(de.getType()));
                    if (de.isLockedCharacter()) {
                        map.setLockedCharacter(de);
                    }
                }
            }
            map.init(engine);
        } catch (Exception e) {
            System.out.println("Error reading Maps");
        }
    }
}
