package popuppanic;

import java.awt.*;

public abstract class GameObject implements GameEntity {

    protected int x, y, width, height;

    public GameObject(int x, int y, int w, int h) {
        this.x = x;
        this.y = y;
        this.width = w;
        this.height = h;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    public abstract void render(Graphics g);
    public abstract void update();

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
