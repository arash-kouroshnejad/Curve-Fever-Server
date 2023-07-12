package game.policy.policies;

import common.gfx.objects.DynamicElement;
import game.logic.GameLogic;
import game.policy.PolicyReference;
import game.policy.PowerupPolicy;

public class Clear extends PowerupPolicy {
    public Clear(GameLogic logic, PolicyReference policyReference) {
        super(logic, policyReference);
    }

    @Override
    public void enforce(DynamicElement element1, DynamicElement element2) {

    }

    @Override
    public boolean isEnforceable(DynamicElement element1, DynamicElement element2) {
        return false;
    }

    @Override
    public void clear() {

    }
}
