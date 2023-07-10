package game.util;

import game.util.Rotation;
import common.gfx.objects.DynamicElement;
import common.util.Routine;


public class RotationHandler extends Routine {

    public RotationHandler(DynamicElement head) {
        super(50, new Rotator(head));
    }

    public void rotateRight() {
        ((Rotator)task).setAngle(0.05);
        restart();
    }

    public void rotateLeft() {
        ((Rotator)task).setAngle(-0.05);
        restart();
    }

    private static class Rotator implements Runnable {
        private final DynamicElement head;
        private double angle;

        public Rotator(DynamicElement head) {
            this.head = head;
        }

        @Override
        public void run() {
            var current = new double[]{head.getSpeedX(), head.getSpeedY()};
            var finalSpeed = Rotation.rotate(current, angle);
            head.setSpeedX(finalSpeed[0]);
            head.setSpeedY(finalSpeed[1]);
        }

        public void setAngle(double angle) {
            this.angle = angle;
        }
    }
}
