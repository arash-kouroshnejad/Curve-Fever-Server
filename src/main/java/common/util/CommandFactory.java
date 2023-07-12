package common.util;

import common.command.*;
import common.game.logic.Colour;
import common.net.data.Command;
import common.net.data.Entity;

public class CommandFactory {
    public static Command join(Entity recipient) {
        return new Join(recipient);
    }

    public static Command register(Entity recipient) {
        return new Register(recipient);
    }

    public static Command setName(Entity recipient, String name) {
        return new SetName(recipient);
    }

    public static Command list(Entity recipient) {
        return new List(recipient);
    }

    public static Command Offer(Entity recipient, String from) {
        return new Offer(recipient).addHeader("from", from);
    }

    public static Command result(Entity recipient, String result, String name) {
        return new Result(recipient).addHeader("result", result).addHeader("name", name);
    }

    public static Command invite(Entity recipient, String to) {
        return new Invite(recipient).addHeader("to", to);
    }

    public static Command info(Entity recipient, String message) {
        return new Info(recipient).addHeader("info", message);
    }

    public static Command sync(Entity recipient, String json) {return new Sync(recipient).addHeader("state", json);}

    public static Command keyPressed(Entity recipient, int keyCode, boolean pressed) {
        return new KeyCommand(recipient).addHeader("key", keyCode).addHeader("change", pressed ? "pressed" : "released");
    }

    public static Command begin(Entity recipient, Colour colour) {return new Begin(recipient).addHeader("colour", colour.getType());}

    public static Command terminate(Entity recipient) {return new Terminate(recipient);}

    public static Command dropLayer (Entity recipient, int layer) {return new Drop(recipient).addHeader("layer", layer);}

    public static Command dropElement(Entity recipient, String type) {return new Drop(recipient).addHeader("element", type);}
}
