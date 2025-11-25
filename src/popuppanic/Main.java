package popuppanic;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        // menjalankan aplikasi di Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            // inisialisasi JFrame utama
            JFrame frame = new JFrame("Popup Panic - Smash the Mole");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 600);
            frame.setLocationRelativeTo(null);
            frame.setResizable(false);
            
            // tambah LoginPage sebagai starting panel
            frame.setContentPane(new LoginPage(frame));
            
            // tampilkan frame
            frame.setVisible(true);
        });
    }
}
