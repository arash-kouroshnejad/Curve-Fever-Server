package game.policy;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class PolicyStack {
    @Getter
    final PolicyReference policyReference;

    public PolicyStack(PolicyReference policyReference) {
        this.policyReference = policyReference;
    }

    @Getter
    private final List<CommandPolicy> commandPolicies = new ArrayList<>();

    private final List<CommandPolicy> defaultCommand = new ArrayList<>();

    @Getter
    private final List<PowerupPolicy> collisionsPolicies = new ArrayList<>();

    private final List<PowerupPolicy> defaultCollisionPolicies = new ArrayList<>();


    public synchronized void disableCollisionPolicies() {
        defaultCollisionPolicies.clear();
        defaultCollisionPolicies.addAll(collisionsPolicies);
        collisionsPolicies.clear();
    }

    public synchronized void resetCollisionPolicies() {
        collisionsPolicies.clear();
        collisionsPolicies.addAll(defaultCollisionPolicies);
    }

    public synchronized void disableCommands() {
        defaultCommand.clear();
        defaultCommand.addAll(commandPolicies);
        commandPolicies.clear();
    }

    public synchronized void resetCommands() {
        commandPolicies.clear();
        commandPolicies.addAll(defaultCommand);
    }
}
