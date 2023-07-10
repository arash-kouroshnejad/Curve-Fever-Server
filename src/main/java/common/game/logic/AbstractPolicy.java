package common.game.logic;

import control.GameManager;
import game.policy.PolicyReference;

public abstract class AbstractPolicy {
    protected static final GameManager manager = GameManager.getInstance();
    protected static final PolicyReference policyReference = PolicyReference.getInstance();
}
