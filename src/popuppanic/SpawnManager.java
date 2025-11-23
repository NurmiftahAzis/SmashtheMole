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
    }

    public void stop() {
        // TODO: menghentikan proses spawn
    }

    @Override
    public void run() {
        // TODO: loop utama spawning selama game berjalan
    }

    private void spawnRandom() {
        // TODO: memilih objek secara acak untuk dimunculkan
    }
}
