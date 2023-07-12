package control;

import common.net.data.Entity;
import game.GameContainer;
import lombok.Getter;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ContainerPool {
    @Getter
    private static final Set<GameContainer> pool = Collections.newSetFromMap(new ConcurrentHashMap<>());

    public static Optional<GameContainer> getContainerFor(Entity entity) {
        return pool.stream().filter(gameContainer -> gameContainer.getPlayer1().entity() == entity ||
                gameContainer.getPlayer2().entity() == entity).findAny();
    }
}
