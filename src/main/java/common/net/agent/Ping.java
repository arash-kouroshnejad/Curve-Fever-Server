package common.net.agent;

import common.gfx.util.Routine;

public class Ping extends Routine {
    public Ping(AbstractAgent agent, PolicyStack policies) {
        super(0.5, () -> {
            for (var entity : agent.entities) {
                var packet = policies.generateBeat(entity);
                agent.outbound.add(packet);
                agent.broadcast.cast();
            }
        });
    }
}
