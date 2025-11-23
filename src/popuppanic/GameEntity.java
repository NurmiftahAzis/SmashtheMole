package popuppanic;

import java.awt.Graphics2D;
import java.awt.Point;

interface GameEntity {
    void update(long dt); // logika update waktu

    void render(Graphics2D g); // buat nampilin gambar

    boolean contains(Point p); // buat deteksi klik mouse

    void onClick(); // aksi pas diklik
}