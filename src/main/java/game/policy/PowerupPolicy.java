package game.policy;

import common.game.logic.AbstractPolicy;
import common.gfx.objects.DynamicElement;
import game.logic.GameLogic;

public abstract class PowerupPolicy extends AbstractPolicy {
    protected final GameLogic logic;
    protected final PolicyReference policyReference;

    public PowerupPolicy(GameLogic logic, PolicyReference policyReference) {
        this.logic = logic;
        this.policyReference = policyReference;
    }

    public abstract void enforce(DynamicElement element1, DynamicElement element2);
    public abstract boolean isEnforceable(DynamicElement element1, DynamicElement element2);
    public abstract void clear();
}
