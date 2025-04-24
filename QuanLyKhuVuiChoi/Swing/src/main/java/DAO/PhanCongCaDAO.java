package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PhanCongCaDAO {
    private Connection conn;

    public PhanCongCaDAO(Connection conn) {
        this.conn = conn;
    }

    public List<Object[]> getCaDaPhanCongTheoNhanVien(String maNhanVien) {
        List<Object[]> result = new ArrayList<>();
        String sql = "SELECT p.maNhanVien, c.tenca, p.ngayLamViec, c.gio_batdau, c.gio_ketthuc " +
                "FROM phanCongCa p " +
                "JOIN calamViec c ON p.maCa = c.maCa " +
                "WHERE p.maNhanVien = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maNhanVien);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Object[] row = {
                        rs.getString("maNhanVien"),
                        rs.getString("tenca"),
                        rs.getDate("ngayLamViec"),
                        rs.getTimestamp("gio_batdau"),
                        rs.getTimestamp("gio_ketthuc")
                };

                result.add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public boolean phanCongCa(String maNV, String maCa, String ngayLamViec) {
        String sql = "INSERT INTO phanCongCa (maNhanVien, maCa, ngayLamViec) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maNV);
            ps.setString(2, maCa);
            ps.setDate(3, Date.valueOf(ngayLamViec));
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
