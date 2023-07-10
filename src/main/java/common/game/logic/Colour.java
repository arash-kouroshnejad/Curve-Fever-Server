package common.game.logic;

import java.util.Arrays;
import java.util.Optional;

public enum Colour {
    Magenta,
    Yellow;

    public String getType() {
        return this.toString();
    }

    public static Colour getPlayer1() {return Magenta;}

    public static Colour getPlayer2() {return Yellow;}

    public static Optional<Colour> getColor(String colourName) {
        return Arrays.stream(Colour.values()).filter(colour -> colour.getType().equals(colourName)).findAny();
    }
}
