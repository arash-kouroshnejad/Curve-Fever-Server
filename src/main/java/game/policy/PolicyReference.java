package game.policy;


public class PolicyReference {
    private final static PolicyReference instance = new PolicyReference();
    private PolicyReference() {}

    public static PolicyReference getInstance() {return instance;}
}
