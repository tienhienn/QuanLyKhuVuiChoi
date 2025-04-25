package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class KhachHangDAO {

    // Phương thức đăng ký khách hàng vào cơ sở dữ liệu
    public static boolean registerCustomer(Connection conn, String fullname, String email, String phone, String password) {
        String sql = "INSERT INTO khachhang (maKhachHang, tenKhachHang, email, SDT, matkhau) VALUES (?, ?, ?, ?, ?)";
        
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            // Tạo mã khách hàng theo số thứ tự tăng dần
            String maKhachHang = generateCustomerCode(conn);

            stmt.setString(1, maKhachHang);
            stmt.setString(2, fullname);
            stmt.setString(3, email);
            stmt.setString(4, phone);
            stmt.setString(5, password);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Hàm tạo mã khách hàng tự động
    private static String generateCustomerCode(Connection conn) throws SQLException {
        String query = "SELECT COUNT(*) FROM khachhang";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                // Tạo mã khách hàng theo số thứ tự (KH001, KH002, ...)
                return "KH" + String.format("%03d", count + 1);  // KH001, KH002, ...
            }
        }
        return "KH001";  // Nếu chưa có khách hàng, trả về mã mặc định
    }
}
