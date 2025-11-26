package popuppanic;

import java.awt.Graphics2D;
import java.awt.Point;

interface GameEntity {
    void update(long dt); // logika update waktu

    void render(Graphics2D g); // untuk menggambar object

    boolean contains(Point p); // deteksi klik mouse

    void onClick(); // aksi jika diklik
}