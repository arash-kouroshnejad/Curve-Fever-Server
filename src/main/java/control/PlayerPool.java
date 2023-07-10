package control;

import common.net.data.Entity;
import lombok.Getter;
import game.model.Player;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class PlayerPool {

    @Getter
    private static final Set<Player> players = Collections.newSetFromMap(new ConcurrentHashMap<>());


    public static boolean isValidName(String name) {
        return players.stream().noneMatch(player -> player.name().equals(name));
    }


    public static void addPlayer(String name, Entity entity) {
        players.add(new Player(entity, name));
    }


    public static void removePlayer(Player player) {
        players.remove(player);
    }
}
