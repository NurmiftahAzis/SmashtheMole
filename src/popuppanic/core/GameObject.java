package popuppanic.core;

import java.awt.*;

public abstract class GameObject {

    // posisi dan ukuran objek di game
    protected int x, y, width, height;

    // status objek lagi tampil atau engga
    protected boolean visible = false;

    // constructor buat set posisi & ukuran awal
    public GameObject(int x, int y, int w, int h) {
        this.x = x;
        this.y = y;
        this.width = w;
        this.height = h;
    }

    // menggambar objek pada layar permainan
    // dipanggil setiap frame oleh GameLoop untuk menampilkan visual objek
    public abstract void render(Graphics2D g);

    // memperbarui logika internal objek berdasarkan waktu yang berlalu
    // dt (delta time) digunakan untuk menghitung durasi dan perubahan state objek
    public abstract void update(long dt);

    // memeriksa apakah titik klik mouse berada di dalam area objek
    // digunakan untuk mendeteksi interaksi pemain terhadap objek
    public abstract boolean contains(Point p);

    // menjalankan aksi saat objek menerima interaksi klik dari pemain
    // setiap objek memiliki respons berbeda (contoh: Mole menambah skor, Bomb
    // mengakhiri permainan)
    public abstract void onClick();

    // buat kotak batas objek, dipakai buat deteksi klik / collision
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    // cek objek lagi tampil atau disembunyiin
    public boolean isVisible() {
        return visible;
    }

    // atur objek mau ditampilin atau engga
    public void setVisible(boolean v) {
        this.visible = v;
    }

    // ambil posisi X objek
    public int getX() {
        return x;
    }

    // ambil posisi Y objek
    public int getY() {
        return y;
    }
}