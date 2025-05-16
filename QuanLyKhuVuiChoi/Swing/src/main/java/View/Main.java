//package View;
//import com.formdev.flatlaf.FlatLightLaf;
//import java.sql.Connection;
//import javax.swing.JFrame;
//import javax.swing.UIManager;
//
//public class Main {
//    private static Connection conn;
//    public static void main(String[] args) {
//        try {
//            UIManager.setLookAndFeel(new FlatLightLaf()); // Có thể đổi sang FlatDarkLaf
//        } catch (Exception ex) {
//            System.err.println("Không thể cài đặt FlatLaf");
//        }
//
//        JFrame f = new JFrame("Khu quản lý");
//        f.setContentPane(new KhuPanel(conn));
//        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        f.setSize(900, 600);
//        f.setLocationRelativeTo(null);
//        f.setVisible(true);
//    }
//}
