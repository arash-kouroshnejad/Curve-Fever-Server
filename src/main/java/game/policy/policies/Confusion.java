package game.policy.policies;

import common.gfx.objects.DynamicElement;
import game.logic.GameLogic;
import game.model.Rewards;
import game.policy.PolicyReference;
import game.policy.PowerupPolicy;

import java.util.Timer;
import java.util.TimerTask;

public class Confusion extends PowerupPolicy {

    DynamicElement affected;
    Timer timer = new Timer();

    public Confusion(GameLogic logic, PolicyReference policyReference) {
        super(logic, policyReference);
    }

    @Override
    public void enforce(DynamicElement element1, DynamicElement element2) {
        var element = element1.getType().equals("Confuse") ? element1 : element2;
        policyReference.getEditor().removeElement(element);
        var holder = element == element1 ? element2 : element1;
        affected = policyReference.getPlayer1Character() == holder ? policyReference.getPlayer2Character() :
                policyReference.getPlayer1Character();
        enableEffect();
    }

    @Override
    public void clear() {
        timer.cancel();
        disableEffect();
    }

    private void enableEffect() {
        PowerupPolicy last;
        if (affected == policyReference.getPlayer1Character()) {
            last = policyReference.getPlayer1Effect();
            policyReference.setPlayer1State(Rewards.Confuse);
            policyReference.setPlayer1Effect(this);
        }
        else {
            last = policyReference.getPlayer2Effect();
            policyReference.setPlayer2State(Rewards.Confuse);
            policyReference.setPlayer2Effect(this);
        }
        if (last != null)
            last.clear();
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                disableEffect();
            }
        }, 5000);
    }

    private void disableEffect() {
        if (affected == policyReference.getPlayer1Character()) {
            policyReference.setPlayer1State(null);
            policyReference.setPlayer1Effect(null);
        } else {
            policyReference.setPlayer2State(null);
            policyReference.setPlayer2Effect(null);
        }
    }

    @Override
    public boolean isEnforceable(DynamicElement element1, DynamicElement element2) {
        return "Confuse".equals(element1.getType()) || "Confuse".equals(element2.getType());
    }
}
