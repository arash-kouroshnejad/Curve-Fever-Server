package control;

import common.util.CommandFactory;
import server.Server;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class GameLobby {
    private final PlayerPool playerPool = PlayerPool.getInstance();

    public GameLobby(Server server, CommandFactory factory) {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                for (var player : playerPool.getPlayers()) {
                    if (server.isAvailable(player.entity())) {
                        var list = new ArrayList<String>();
                        playerPool.getPlayers().forEach(player1 -> list.add(player1.name()));
                        server.send(factory.list(player.entity()).addHeader("users", list.toString()));
                    }
                    else {
                        playerPool.removePlayer(player);
                    }
                }
            }
        }, 0, 1000);
    }
}
