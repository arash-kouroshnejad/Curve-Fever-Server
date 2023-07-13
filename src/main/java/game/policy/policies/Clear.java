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
        var element = "Eraser".equals(element1.getType()) ? element1 : element2;
        policyReference.getEditor().removeElement(element);
        policyReference.getEditor().dropLayer(1, false);
    }

    @Override
    public boolean isEnforceable(DynamicElement element1, DynamicElement element2) {
        return "Eraser".equals(element1.getType()) || "Eraser".equals(element2.getType());
    }

    @Override
    public void clear() {

    }
}
