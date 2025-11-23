package popuppanic;

import java.awt.*;

public abstract class GameObject implements GameEntity {

    protected int x, y, width, height;

    public GameObject(int x, int y, int w, int h) {
        // TODO: inisialisasi posisi dan ukuran objek
    }

    public Rectangle getBounds() {
        // TODO: mengembalikan kotak hitbox objek
        return null;
    }
}
