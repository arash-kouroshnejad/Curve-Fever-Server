package game.util;

import common.game.logic.Colour;
import common.gfx.objects.Bounds;
import common.gfx.objects.DynamicElement;
import common.gfx.objects.StaticElement;
import common.util.Routine;
import game.GameContainer;

import java.util.List;

import static game.util.Geometrics.dotProduct;

public class Collision extends Routine {
    static String player1 = Colour.getPlayer1().getType();
    static String player2 = Colour.getPlayer2().getType();

    public Collision(List<DynamicElement> dynamics, List<StaticElement> statics, GameContainer container, Bounds bounds) {
        super(10, () -> {
            for (var dynamic : dynamics) {
                if (dynamic.getX() < bounds.LEFT || dynamic.getX() > bounds.RIGHT || dynamic.getY() > bounds.BOTTOM ||
                        dynamic.getY() < bounds.TOP)
                    container.killGame();
                if (collides(dynamic, statics) && (player1.equals(dynamic.getType()) || player2.equals(dynamic.getType())))
                    container.killGame();
            }
        });
    }

    public static boolean collides(DynamicElement element, List<? extends StaticElement> statics) {
        for (var staticElement : statics)
            if (Math.abs(staticElement.getX() - element.getX()) < 70 && Math.abs(staticElement.getY() -
                    element.getY()) < 70)
                if (element.collidesWith(staticElement))
                    if (dotProduct(new double[]{staticElement.getX() - element.getX(), staticElement.getY()
                            - element.getY()}, new double[]{element.getSpeedX(), element.getSpeedY()}) > 0)
                        return true;
        return false;
    }
}
