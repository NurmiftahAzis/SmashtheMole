package popuppanic;

import javax.swing.*;

import popuppanic.view.LoginPage;

public class Main {

    public static void main(String[] args) {

        // menjalankan GUI di Event Dispatch Thread (EDT) untuk mencegah crash
        SwingUtilities.invokeLater(() -> {

            // frame utama sebagai wadah semua panel game
            JFrame frame = new JFrame("Popup Panic - Smash the Mole!");

            // saat jendela ditutup, aplikasi ikut berhenti
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            // ukuran window tetap (800x600), tidak bisa di-resize
            frame.setSize(800, 600);
            frame.setResizable(false);

            // posisi window muncul di tengah layar
            frame.setLocationRelativeTo(null);

            // halaman pertama yang muncul adalah halaman login
            frame.setContentPane(new LoginPage(frame));

            // menampilkan window ke layar
            frame.setVisible(true);
        });
    }
}
