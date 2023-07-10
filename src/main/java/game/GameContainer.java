package game;

import common.game.load.GameLoader;
import common.game.logic.LogicLoop;
import common.gfx.editor.LevelEditor;
import common.gfx.render.GameEngine;
import common.net.agent.AbstractAgent;
import common.net.data.Command;
import common.persistence.Config;
import common.util.Routine;
import game.logic.GameLogic;
import game.model.Player;
import game.policy.PolicyStack;
import game.policy.policies.KeyToggled;
import game.util.Collision;
import game.util.Sync;


public class GameContainer {
    Player player1;
    Player player2;
    GameLoader gameLoader;
    GameLogic logic;
    LevelEditor levelEditor;
    Routine logicLoop;
    Collision collision;
    int dynamicsLayer = Config.getInstance().getProperty("DynamicsLayer", Integer.class);
    int staticLayer = Config.getInstance().getProperty("StaticsLayer", Integer.class);
    Routine player1SyncThread;
    Routine player2SyncThread;




    public void init(Player player1, Player player2, AbstractAgent server) {
        this.player1 = player1;
        this.player2 = player2;
        var engine = new GameEngine();
        logic = new GameLogic(player1, player2);
        gameLoader = new GameLoader(Config.getInstance().getProperty("DefaultMapsDir"), engine);
        levelEditor = new LevelEditor();
        levelEditor.enableHeadless(gameLoader, engine);
        gameLoader.getMap(0, 0).init(engine);
        logic.init(levelEditor);
        logicLoop = new LogicLoop(logic);
        logicLoop.start();
        var dynamics = levelEditor.getLayers().getALL_LAYERS().
                get(dynamicsLayer).getDynamicElements();
        var statics = levelEditor.getLayers().getALL_LAYERS().
                get(staticLayer).getStaticElements();
        collision = new Collision(dynamics, statics); // TODO : when to start the thread
        collision.start();
        player1SyncThread = new Sync(dynamics, player1.entity(), server);
        player2SyncThread = new Sync(dynamics, player2.entity(), server);
        player1SyncThread.start();
        player2SyncThread.start();
        var stack = PolicyStack.getInstance().getCommandPolicies();
        stack.add(new KeyToggled(logic));
    }

    public void killGame() {
        collision.kill();
        logicLoop.kill();
    }

    public void executeCommand(Command command) {
        logic.handleCommand(command);
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }
}
