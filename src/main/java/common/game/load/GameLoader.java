package common.game.load;

import com.google.gson.Gson;
import common.gfx.objects.Map;
import common.gfx.render.GameEngine;
import common.gfx.util.SpriteLoader;

import java.io.FileReader;
import java.io.IOException;

public class GameLoader extends SpriteLoader {
    public GameLoader(String PathToMaps, GameEngine engine) {
        super(PathToMaps, engine);
    }

    @Override
    public Map getMap(int ID, int level) {
        var gson = new Gson();
        try {
            return gson.fromJson(new FileReader(PathToMaps + "0.map"), Map.class);
        } catch (IOException e) {
            System.out.println("Error reading default map");
            throw new RuntimeException(e);
        }
    }
}
