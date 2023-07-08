package control;


import common.net.data.Command;
import common.net.data.Entity;
import common.policies.RetrievalBehaviour;
import common.policies.Validator;
import common.util.CommandFactory;
import server.Server;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GameManager {

    private final static GameManager instance = new GameManager();

    private GameManager() {}

    public static GameManager getInstance() {
        return instance;
    }

    private final ExecutorService executors = Executors.newFixedThreadPool(20);

    private final Server server = new Server();

    private final CommandFactory commandFactory = new CommandFactory();


    public void start() {
        server.setRetrievalAction(new RetrievalBehaviour(server, new Validator()));
        server.init();
        server.start();
        new GameLobby(server, commandFactory);
    }

    public void execute(Command command) {executors.submit(command);}

    public void admitClient(Entity entity) {
        server.send(commandFactory.register(entity));
    }

    public void offerPlayer(String playerName, Entity from) {
        var players = PlayerPool.getInstance().getPlayers();
        var sender = players.stream().filter(player -> player.entity() == from).findFirst();
        if (sender.isEmpty())
            return;
        players.stream().filter(player -> player.name().equals(playerName)).
                forEach(player -> server.send(commandFactory.Offer(player.entity(), sender.get().name())));
    }

    public void registerPlayer(String name, Entity entity) {
        if (PlayerPool.getInstance().isValidName(name)) {
            PlayerPool.getInstance().addPlayer(name, entity);
        }
    }
}
