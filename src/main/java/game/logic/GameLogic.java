package game.logic;

import common.game.logic.AbstractLogic;
import common.game.logic.Colour;
import common.gfx.editor.LevelEditor;
import common.gfx.objects.DynamicElement;
import common.net.data.Command;
import common.net.data.Entity;
import common.persistence.Config;
import common.util.Routine;
import game.model.Player;
import game.policy.CommandPolicy;
import game.policy.PolicyReference;
import game.policy.PolicyStack;
import game.policy.PowerupPolicy;
import game.util.BoostGenerator;
import game.util.RotationHandler;

import java.util.List;

public class GameLogic extends AbstractLogic {
    final Player player1;
    final Player player2;
    DynamicElement player1Character;
    DynamicElement player2Character;
    int dynamicsLayer = Config.getInstance().getProperty("DynamicsLayer", Integer.class);
    RotationHandler player1Rotation;
    RotationHandler player2Rotation;
    List<CommandPolicy> commandPolicies;
    List<PowerupPolicy> collisionsPolicies;
    PolicyReference policyReference;
    Routine boostGenerator;


    public GameLogic(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
    }


    public void init(LevelEditor editor, PolicyStack policyStack) {
        super.init(editor);
        player1Character = editor.getDynamicElement(Colour.getPlayer1().getType(), dynamicsLayer, 0).get();
        player2Character = editor.getDynamicElement(Colour.getPlayer2().getType(), dynamicsLayer, 0).get();
        player1Rotation = new RotationHandler(player1Character);
        player1Rotation.start();
        player2Rotation = new RotationHandler(player2Character);
        player2Rotation.start();
        commandPolicies = policyStack.getCommandPolicies();
        collisionsPolicies = policyStack.getCollisionsPolicies();
        policyReference = policyStack.getPolicyReference();
        policyReference.setPlayer1Character(player1Character);
        policyReference.setPlayer2Character(player2Character);
        policyReference.setEditor(editor);
        boostGenerator = new BoostGenerator(editor);
        boostGenerator.start();
    }

    public void rotatePlayer(Entity entity, boolean clockwise) {
        var handler = (entity == player1.entity()) ? player1Rotation : player2Rotation;
        if (clockwise)
            handler.rotateRight();
        else
            handler.rotateLeft();
    }

    public void pauseRotation(Entity entity) {
        (entity == player1.entity() ? player1Rotation : player2Rotation).pause();
    }

    public void handleCommand(Command command) {
        for (var policy : commandPolicies)
            if (policy.isEnforceable(command)) {
                policy.enforce(command);
                return;
            }
    }

    @Override
    public void killGame() {
        super.killGame();
        boostGenerator.kill();
    }

    @Override
    public void check() {
        super.check();
        int index = 1;
        for (var dynamic : dynamics) {
            for (var iter = dynamics.listIterator(index);
                 iter.hasNext() && index + 1 <= dynamics.size(); ) {
                var dynamic2 = iter.next();
                if (dynamic.collidesWith(dynamic2)) {
                    for (var policy : collisionsPolicies)
                        if (policy.isEnforceable(dynamic, dynamic2)) {
                            policy.enforce(dynamic, dynamic2);
                            return;
                        }
                }
            }
            index++;
        }
    }
}
