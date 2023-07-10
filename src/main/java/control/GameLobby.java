package control;

import common.util.CommandFactory;
import server.Server;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class GameLobby {

    public GameLobby(Server server) {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                for (var player : PlayerPool.getPlayers()) {
                    if (server.isAvailable(player.entity())) {
                        var list = new ArrayList<String>();
                        PlayerPool.getPlayers().forEach(player1 -> list.add(player1.name()));
                        server.send(CommandFactory.list(player.entity()).addHeader("users", list.toString()));
                    }
                    else {
                        PlayerPool.removePlayer(player);
                    }
                }
            }
        }, 0, 1000);
    }
}
