package server;

import common.net.agent.Heartbeat;
import common.net.data.Packet;
import common.policies.NetworkPolicies;
import server.tasks.Connect;
import common.net.agent.AbstractAgent;
import common.net.data.Entity;


import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.Executors;

public class Server extends AbstractAgent {
    int portNumber = 9000;
    boolean started;

    public Server() {
        super(new NetworkPolicies(), 20);
    }

    public void init() {
        pool = Executors.newFixedThreadPool(capacity);
    }


    private void startListening() {
        System.out.println("Server started on port " + portNumber + " with capacity " + capacity);
        for (int i = 0; i < capacity; i++) {
            pool.submit(new Connect(this, portNumber, tcpListener, udpListener));
        }
        started = true;
        enableReceiving();
    }
    public void start() {
        if (!started)
            startListening();
        else
            throw new RuntimeException("Server already started!");
    }

    @Override
    public void syncID(Entity entity) throws IOException {
        var packet = new Packet(null, entity.getId());
        entity.getTcp().send(packet);
    }

    @Override
    public void registerEntity(Entity entity) {
        super.registerEntity(entity);
        new Heartbeat(this, entity).start();
    }
}
