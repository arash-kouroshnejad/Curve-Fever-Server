package common.gfx.objects;


public class DynamicElement extends StaticElement {
    double speedX, speedY;

    private boolean lockedCharacter;

    private transient ElementManager manager;

    public void setLockedCharacter() {
        lockedCharacter = true;
    }

    public boolean isLockedCharacter() {
        return lockedCharacter;
    }

    public DynamicElement(int x, int y, int width, int height, int speedX, int speedY, String type) {
        super(x, y, width, height, type);
        this.speedX = speedX;
        this.speedY = speedY;
    }

    public double getSpeedX() {
        return speedX;
    }

    public void setSpeedX(double speedX) {
        this.speedX = speedX;
    }

    public double getSpeedY() {
        return speedY;
    }

    public void setSpeedY(double speedY) {
        this.speedY = speedY;
    }

    public void move() {
        x += speedX;
        y += speedY;
    }

    public ElementManager getManager() {
        return manager;
    }

    public void setManager(ElementManager manager) {
        this.manager = manager;
    }
}
