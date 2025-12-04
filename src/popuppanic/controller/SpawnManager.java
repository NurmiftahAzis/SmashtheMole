package popuppanic.controller;

import java.util.List;
import java.util.Random;

import popuppanic.model.Bomb;
import popuppanic.model.Mole;

public class SpawnManager implements Runnable {

    // list lubang tikus
    private List<Mole> moles;

    // list lokasi bom
    private List<Bomb> bombs;

    // flag untuk kontrol thread spawn, volatile biar aman dibaca banyak thread
    private volatile boolean running = true;

    // random generator buat spawn acak
    private Random random = new Random();

    // constructor untuk nerima list mole dan bomb
    public SpawnManager(List<Mole> moles, List<Bomb> bombs) {
        this.moles = moles;
        this.bombs = bombs;
    }

    // method buat berhentiin spawner secara halus
    public void stop() {
        running = false;
    }

    @Override
    public void run() {
        // loop utama spawning, jalan terus selama game aktif
        while (running) {

            try {
                // jeda spawn tiap 0.8 detik (800ms)
                // kalo mau game lebih sulit, kecilin duration biar spawn lebih sering
                Thread.sleep(800);
            } catch (InterruptedException e) {
                break; // keluar dari loop kalo thread dipaksa berhenti
            }

            // spawn objek secara acak
            spawnRandom();
        }
    }

    // method buat munculin objek random di lubang yang tersedia
    private void spawnRandom() {

        // pilih lubang acak berdasarkan jumlah mole yang ada
        int index = random.nextInt(moles.size());

        Mole m = moles.get(index);
        Bomb b = bombs.get(index);

        // kalau posisi itu lagi dipakai (tikus atau bom lagi muncul), skip supaya tidak
        // numpuk
        if (m.isVisible() || b.isVisible())
            return;

        // sistem probabilitas: 25% muncul bom, 75% muncul tikus
        if (random.nextInt(100) < 25)
            b.popUp(900); // bom muncul selama 0.9 detik
        else
            m.popUp(900); // tikus muncul selama 0.9 detik
    }
}