package popuppanic;

import java.util.List;
import java.util.Random;

public class SpawnManager implements Runnable {

    private List<Mole> moles;
    private List<Bomb> bombs;

    private volatile boolean running = true;
    private Random random = new Random();

    public SpawnManager(List<Mole> moles, List<Bomb> bombs) {
        // TODO: menyimpan daftar mole dan bomb
        this.moles = moles;
        this.bombs = bombs;
    }

    public void stop() {
        // TODO: menghentikan proses spawn
        running = false;
    }

    @Override
    public void run() {
        // TODO: loop utama spawning selama game berjalan
        while (running) {
            spawnRandom();    // pilih dan muncul objek

            try {
                Thread.sleep(700 + random.nextInt(500)); 
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private void spawnRandom() {
        // TODO: memilih objek secara acak untuk dimunculkan

        int choice = random.nextInt(2); // 0 = mole, 1 = bomb

        if (choice == 0 && !moles.isEmpty()) {
            Mole mole = moles.get(random.nextInt(moles.size()));
            mole.spawn(); // method spawn sudah ada pada class Mole
        } 
        else if (!bombs.isEmpty()) {
            Bomb bomb = bombs.get(random.nextInt(bombs.size()));
            bomb.spawn(); // method spawn sudah ada pada class Bomb
        }
    }
}
