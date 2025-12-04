package popuppanic.controller;

import javax.swing.*;

import popuppanic.core.GameObject;

import java.util.List;

public class GameLoop extends Thread {

    // flag buat kontrol status loop, true = jalan, false = berhenti
    private volatile boolean running = true;

    // daftar semua objek yang akan di-update tiap frame
    private List<GameObject> objects;

    // komponen tampilan tempat game digambar (panel atau canvas)
    private JComponent view;

    // constructor untuk nerima list objek dan komponen tampilan
    public GameLoop(List<GameObject> objects, JComponent view) {
        this.objects = objects;
        this.view = view;
    }

    // method buat menghentikan game loop dari luar
    public void terminate() {
        running = false; // ubah flag jadi false biar loop berhenti
        interrupt(); // hentikan thread kalau lagi sleep
    }

    @Override
    public void run() {
        long last = System.currentTimeMillis(); // waktu awal frame sebelumnya

        // loop utama game
        while (running) {
            long now = System.currentTimeMillis();
            long dt = now - last; // hitung selisih waktu antar frame (delta time)

            // update semua objek yang ada di list
            for (GameObject obj : objects)
                obj.update(dt);

            last = now; // update waktu terakhir
            view.repaint(); // trigger untuk gambar ulang layar

            try {
                // jeda sebentar supaya frame stabil (target ~60 fps -> 1000/60 ≈ 16)
                Thread.sleep(16);
            } catch (InterruptedException e) {
                break; // keluar loop kalau thread dipaksa berhenti
            }
        }
    }
}