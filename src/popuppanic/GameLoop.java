package popuppanic;

import javax.swing.*;
import java.util.List;

public class GameLoop extends Thread {

    private volatile boolean running = true;
    private List<GameObject> objects;
    private JComponent view;

    public GameLoop(List<GameObject> objects, JComponent view) {
        this.objects = objects;
        this.view = view;
    }

    public void terminate() {
        running = false;
    }

    @Override
    public void run() {
        final int TARGET_FPS = 60;
        final int FRAME_TIME = 1000 / TARGET_FPS; // ~16ms per frame

        while (running) {
            long start = System.currentTimeMillis();

            // 1. UPDATE semua object
            for (GameObject obj : objects) {
                obj.update();
            }

            // 2. REPAINT STAGE
            view.repaint();

            // 3. FRAME CONTROL
            long elapsed = System.currentTimeMillis() - start;
            long wait = FRAME_TIME - elapsed;

            if (wait > 0) {
                try {
                    Thread.sleep(wait);
                } catch (InterruptedException e) { }
            }
        }
    }
}

