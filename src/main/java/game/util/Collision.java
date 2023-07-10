package game.util;

import common.gfx.objects.DynamicElement;
import common.gfx.objects.StaticElement;
import common.util.Routine;

import java.util.List;

public class Collision extends Routine {
    public Collision(List<DynamicElement> dynamics, List<StaticElement> statics) {
        super(1, () -> {
            for (var dynamic : dynamics)
                for (var staticElement : statics)
                    if (Math.abs(staticElement.getX() - dynamic.getX()) < 70 && Math.abs(staticElement.getY() -
                            dynamic.getY()) < 70)
                        if (dynamic.collidesWith(staticElement))
                                if ((staticElement.getX() - dynamic.getX()) / (double)(staticElement.getY() -
                                        dynamic.getY()) == -dynamic.getSpeedX() / dynamic.getSpeedY())
                                    System.out.println(dynamic.getType() + " loses");
        });
    }
}
