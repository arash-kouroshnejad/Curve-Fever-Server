package common.net.agent;

import common.net.data.Command;
import common.net.data.Packet;

import java.util.ArrayList;
import java.util.List;

public abstract class PacketValidator {
    protected final List<Command> commandList = new ArrayList<>();

    public abstract void validate(Packet incoming);
}
