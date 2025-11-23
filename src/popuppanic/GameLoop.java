package popuppanic;

import javax.swing.*;
import java.util.List;

public class GameLoop extends Thread {

    private volatile boolean running = true;
    private List<GameObject> objects;
    private JComponent view;

    public GameLoop(List<GameObject> objects, JComponent view) {
        // TODO: inisialisasi daftar objek dan komponen view
    }

    public void terminate() {
        // TODO: menghentikan loop permainan
    }

    @Override
    public void run() {
        // TODO: menjalankan loop utama permainan
    }
}
