package game.util;

import com.google.gson.Gson;
import common.gfx.objects.DynamicElement;
import common.net.agent.AbstractAgent;
import common.net.data.Entity;
import common.util.CommandFactory;
import common.util.Routine;

import java.util.List;

public class Sync extends Routine {
    static Gson serializer = new Gson();
    public Sync(List<DynamicElement> dynamics, Entity player, AbstractAgent server) {
        super(10, () -> server.send(CommandFactory.sync(player, serializer.toJson(dynamics))));
    }
}
