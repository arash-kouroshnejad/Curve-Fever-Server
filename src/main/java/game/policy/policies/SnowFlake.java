package game.policy.policies;

import common.gfx.objects.DynamicElement;
import game.logic.GameLogic;
import game.model.Rewards;
import game.policy.PolicyReference;
import game.policy.PowerupPolicy;

import java.util.Timer;
import java.util.TimerTask;

public class SnowFlake extends PowerupPolicy {

    public SnowFlake(GameLogic logic, PolicyReference policyReference) {
        super(logic, policyReference);
    }

    Timer timer = new Timer();
    DynamicElement affected;

    @Override
    public void enforce(DynamicElement element1, DynamicElement element2) {
        var element = element1.getType().equals("Snowflake") ? element1 : element2;
        policyReference.getEditor().removeElement(element);
        var holder = element == element1 ? element2 : element1;
        affected = policyReference.getPlayer1Character() == holder ? policyReference.getPlayer2Character() :
                policyReference.getPlayer1Character();
        enableEffect();
    }

    @Override
    public void clear() {
        try {
            timer.cancel();
        } catch (Exception e) {
            e.printStackTrace();
        }
        disableEffect();
    }

    private void enableEffect() {
        PowerupPolicy last;
        if (affected == policyReference.getPlayer1Character()) {
            last = policyReference.getPlayer1Effect();
            policyReference.setPlayer1State(Rewards.Snowflake);
            policyReference.setPlayer1Effect(this);
        }
        else {
            last = policyReference.getPlayer2Effect();
            policyReference.setPlayer2State(Rewards.Snowflake);
            policyReference.setPlayer2Effect(this);
        }
        if (last != null)
            last.clear();
        affected.setSpeedY(affected.getSpeedY() / 2);
        affected.setSpeedX(affected.getSpeedX() / 2);
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
        }
        else {
            policyReference.setPlayer2State(null);
            policyReference.setPlayer2Effect(null);
        }
        affected.setSpeedX(affected.getSpeedX() * 2);
        affected.setSpeedY(affected.getSpeedY() * 2);
    }

    @Override
    public boolean isEnforceable(DynamicElement element1, DynamicElement element2) {
        return "Snowflake".equals(element1.getType()) || "Snowflake".equals(element2.getType());
    }
}
