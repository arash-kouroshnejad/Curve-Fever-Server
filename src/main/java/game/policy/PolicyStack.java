package game.policy;

import java.util.ArrayList;
import java.util.List;

public class PolicyStack {
    private static final PolicyStack instance = new PolicyStack();

    private PolicyStack() {}


    public static PolicyStack getInstance() {return instance;}

    private final List<CommandPolicy> commandPolicies = new ArrayList<>();

    private final List<CommandPolicy> defaultStack = new ArrayList<>();


    public List<CommandPolicy> getCommandPolicies() {
        return commandPolicies;
    }



    public synchronized void disableCommands() {
        defaultStack.clear();
        defaultStack.addAll(commandPolicies);
        commandPolicies.clear();
    }

    public synchronized void resetCommands() {
        commandPolicies.clear();
        commandPolicies.addAll(defaultStack);
    }
}
