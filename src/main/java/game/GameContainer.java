package game;

import common.game.load.GameLoader;
import common.game.logic.LogicLoop;
import common.gfx.editor.LevelEditor;
import common.gfx.objects.Bounds;
import common.gfx.objects.DynamicElement;
import common.gfx.objects.StaticElement;
import common.gfx.render.GameEngine;
import common.net.agent.AbstractAgent;
import common.net.data.Command;
import common.persistence.Config;
import common.util.Routine;
import control.GameManager;
import game.logic.GameLogic;
import game.model.Player;
import game.policy.PolicyReference;
import game.policy.PolicyStack;
import game.policy.policies.*;
import game.util.Collision;
import game.util.ElementFactory;
import game.util.Sync;

import java.awt.*;
import java.util.List;


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
    PolicyStack gamePolicies;
    PolicyReference policyReference;




    public GameContainer (Player player1, Player player2, AbstractAgent server) {
        this.player1 = player1;
        this.player2 = player2;
        setUpGame(server);
        setUpLogic();
        setUpPolicies();
        var dynamics = levelEditor.getLayers().getALL_LAYERS().
                get(dynamicsLayer).getDynamicElements();
        var statics = levelEditor.getLayers().getALL_LAYERS().
                get(staticLayer).getStaticElements();
        var dim = levelEditor.getScreenDimensions();
        setUpCollision(dynamics, statics, dim);
        setUpSync(dynamics, server);
    }

    private void setUpGame(AbstractAgent server) {
        var engine = new GameEngine();
        logic = new GameLogic(player1, player2);
        gameLoader = new GameLoader(Config.getInstance().getProperty("DefaultMapsDir"), engine);
        levelEditor = new ElementFactory(server, player1, player2);
        levelEditor.enableHeadless(gameLoader, engine);
        gameLoader.getMap(0, 0).init(engine);
    }

    private void setUpLogic() {
        policyReference = new PolicyReference(player1, player2);
        gamePolicies = new PolicyStack(policyReference);
        logic.init(levelEditor, gamePolicies);
        logicLoop = new LogicLoop(logic);
        logicLoop.start();
    }

    private void setUpPolicies() {
        gamePolicies.getCommandPolicies().add(new KeyToggled(logic, policyReference));
        gamePolicies.getCollisionsPolicies().add(new Confusion(logic, policyReference));
        gamePolicies.getCollisionsPolicies().add(new Fireball(logic, policyReference));
        gamePolicies.getCollisionsPolicies().add(new SnowFlake(logic, policyReference));
        gamePolicies.getCollisionsPolicies().add(new Clear(logic, policyReference));
    }

    private void setUpCollision(List<DynamicElement> dynamics, List<StaticElement> statics, Dimension dim) {
        collision = new Collision(dynamics, statics, this, new Bounds(0, dim.height, dim.width, 0));
        collision.start();
    }

    private void setUpSync(List<DynamicElement> dynamics, AbstractAgent server) {
        player1SyncThread = new Sync(dynamics, player1.entity(), server);
        player2SyncThread = new Sync(dynamics, player2.entity(), server);
        player1SyncThread.start();
        player2SyncThread.start();
    }

    public void killGame() {
        logic.killGame();
        collision.kill();
        logicLoop.kill();
        player1SyncThread.kill();
        player2SyncThread.kill();
        GameManager.getInstance().terminateGame(this);
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
