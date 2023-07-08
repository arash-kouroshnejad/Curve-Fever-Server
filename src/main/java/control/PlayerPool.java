package control;

import common.net.data.Entity;
import lombok.Getter;
import model.Player;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class PlayerPool {

    private static final PlayerPool instance = new PlayerPool();

    private PlayerPool() {}


    public static PlayerPool getInstance() {return instance;}

    @Getter
    private final Set<Player> players = Collections.newSetFromMap(new ConcurrentHashMap<>());


    public boolean isValidName(String name) {
        return players.stream().noneMatch(player -> player.name().equals(name));
    }


    public void addPlayer(String name, Entity entity) {
        players.add(new Player(entity, name));
    }


    public void removePlayer(Player player) {
        players.remove(player);
    }
}
