package common.policies;

import common.net.agent.PacketValidator;
import server.Server;

public class RetrievalBehaviour implements Runnable {
    Server server;
    PacketValidator validator;

    public RetrievalBehaviour(Server server, PacketValidator validator) {
        this.server = server;
        this.validator = validator;
    }

    @Override
    public void run() {
        var optional = server.readPacket();
        optional.ifPresent(packet -> validator.validate(packet));
    }
}
