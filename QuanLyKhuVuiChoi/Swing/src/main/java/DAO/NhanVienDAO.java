package DAO;

import Model.NhanVien;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NhanVienDAO {
    private Connection conn;

    public NhanVienDAO(Connection conn) {
        this.conn = conn;
    }

    public List<NhanVien> getAllNhanVien() {
        List<NhanVien> list = new ArrayList<>();
        String sql = "SELECT * FROM NhanVien";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                NhanVien nv = new NhanVien(
                        rs.getString("maNhanVien"),
                        rs.getString("tenNhanVien"),
                        rs.getDate("ngaySinh"),
                        rs.getString("SDT"),
                        rs.getString("email"),
                        rs.getDate("ngayBatDauLam"),
                        rs.getDouble("luong")
                );
                list.add(nv);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean themNhanVien(NhanVien nv) {
        String sql = "INSERT INTO NhanVien (maNhanVien, tenNhanVien, ngaySinh, SDT, email, ngayBatDauLam, luong) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nv.getMaNhanVien());
            ps.setString(2, nv.getTenNhanVien());
            ps.setDate(3, nv.getNgaySinh());
            ps.setString(4, nv.getSdt());
            ps.setString(5, nv.getEmail());
            ps.setDate(6, nv.getNgayBatDauLam());
            ps.setDouble(7, nv.getLuong());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean capNhatNhanVien(NhanVien nv) {
        String sql = "UPDATE NhanVien SET tenNhanVien=?, ngaySinh=?, SDT=?, email=?, ngayBatDauLam=?, luong=? WHERE maNhanVien=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nv.getTenNhanVien());
            ps.setDate(2, nv.getNgaySinh());
            ps.setString(3, nv.getSdt());
            ps.setString(4, nv.getEmail());
            ps.setDate(5, nv.getNgayBatDauLam());
            ps.setDouble(6, nv.getLuong());
            ps.setString(7, nv.getMaNhanVien());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean xoaNhanVien(String maNhanVien) {
        String sql = "DELETE FROM NhanVien WHERE maNhanVien=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maNhanVien);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<NhanVien> timKiemNhanVien(String keyword) {
        List<NhanVien> list = new ArrayList<>();
        String sql = "SELECT * FROM NhanVien WHERE maNhanVien LIKE ? OR tenNhanVien LIKE ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            String kw = "%" + keyword + "%";
            ps.setString(1, kw);
            ps.setString(2, kw);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                NhanVien nv = new NhanVien(
                        rs.getString("maNhanVien"),
                        rs.getString("tenNhanVien"),
                        rs.getDate("ngaySinh"),
                        rs.getString("SDT"),
                        rs.getString("email"),
                        rs.getDate("ngayBatDauLam"),
                        rs.getDouble("luong")
                );
                list.add(nv);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
