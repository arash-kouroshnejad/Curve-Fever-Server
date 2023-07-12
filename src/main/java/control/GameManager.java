package control;


import common.command.KeyCommand;
import common.game.logic.Colour;
import common.net.data.Command;
import common.net.data.Entity;
import common.persistence.Config;
import common.policies.RetrievalBehaviour;
import common.policies.Validator;
import common.util.CommandFactory;
import game.GameContainer;
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


    public void start() {
        Config.getInstance().setPath("./src/main/resources/Config.properties");
        server.setRetrievalAction(new RetrievalBehaviour(server, new Validator()));
        server.init();
        server.start();
        new GameLobby(server);
    }

    public void execute(Command command) {executors.submit(command);}

    public void admitClient(Entity entity) {
        server.send(CommandFactory.register(entity));
    }

    public void offerPlayer(String playerName, Entity from) {
        var sender = PlayerPool.getPlayers().stream().filter(player -> player.entity() == from).findFirst();
        if (sender.isEmpty())
            return;
        PlayerPool.getPlayers().stream().filter(player -> player.name().equals(playerName)).
                forEach(player -> server.send(CommandFactory.Offer(player.entity(), sender.get().name())));
    }

    public void registerPlayer(String name, Entity entity) {
        if (PlayerPool.isValidName(name)) {
            PlayerPool.addPlayer(name, entity);
        }
    }

    public void allocateContainer(String player1Name, Entity player2Entity) {
        var player1 = PlayerPool.getPlayers().stream().filter(player -> player.name().
                equals(player1Name)).findAny();
        if (player1.isEmpty())
            return;
        var player2 = PlayerPool.getPlayers().stream().filter(player -> player.entity()
                == player2Entity).findAny();
        if (player2.isEmpty())
            return;
        PlayerPool.removePlayer(player1.get());
        PlayerPool.removePlayer(player2.get());
        // server.send(CommandFactory.begin(player2Entity, Colour.Magenta));
        server.send(CommandFactory.begin(player1.get().entity(), Colour.getPlayer1()));
        server.send(CommandFactory.begin(player2.get().entity(), Colour.getPlayer2()));
        var container = new GameContainer(player1.get(), player2.get(), server);
        ContainerPool.getPool().add(container);
    }

    public void terminateGame(GameContainer container) {
        ContainerPool.getPool().remove(container);
        server.send(CommandFactory.terminate(container.getPlayer1().entity()));
        server.send(CommandFactory.terminate(container.getPlayer2().entity()));
    }

    public void notifyLogic(KeyCommand command) {
        var container = ContainerPool.getContainerFor(command.getEntity());
        if (container.isEmpty())
            return;
        container.get().executeCommand(command);
    }
}
