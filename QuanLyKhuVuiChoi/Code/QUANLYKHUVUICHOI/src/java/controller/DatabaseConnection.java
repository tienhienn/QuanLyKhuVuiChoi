package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static Connection conn = null;

    // Phương thức lấy kết nối
    public static Connection getConnection() {
        if (conn == null) {
            try {
                // Load JDBC Driver
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                
                // Chuỗi kết nối
                String url = "jdbc:sqlserver://localhost:1433;databaseName=QUANLYKHUVUICHOI;user=sa;password=12345;trustServerCertificate=true";

                // Kết nối database
                conn = DriverManager.getConnection(url);
                System.out.println("Kết nối SQL thành công!");
            } catch (ClassNotFoundException e) {
                System.out.println("Không tìm thấy JDBC Driver!");
                e.printStackTrace();
            } catch (SQLException e) {
                System.out.println("Kết nối SQL thất bại!");
                e.printStackTrace();
            }
        }
        return conn;
    }
}
