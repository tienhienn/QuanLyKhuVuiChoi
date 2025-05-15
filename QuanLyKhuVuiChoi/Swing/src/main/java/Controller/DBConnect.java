package Controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnect {
    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=QUANLYKHUVUICHOI;encrypt=true;trustServerCertificate=true";
    private static final String USER = "sa";  // Thay bằng user SQL của bạn
    private static final String PASSWORD = "12345";  // Thay bằng mật khẩu SQL của bạn

    public static Connection getConnection() {
        Connection conn = null;
        try {
            // Load driver (Không cần thiết nếu dùng JDBC 4.0 trở lên)
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

            // Kết nối
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Kết nối thành công!");
        } catch (ClassNotFoundException e) {
            System.err.println("Không tìm thấy driver JDBC!");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Lỗi kết nối CSDL!");
            e.printStackTrace();
        }
        return conn;
    }

    public static void main(String[] args) {
        getConnection();  // Test kết nối
    }
}