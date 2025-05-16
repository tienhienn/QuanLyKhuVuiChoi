package DAO;

import Model.SuKien;
import java.sql.*;
import java.util.ArrayList;

public class SuKienDAO {
    private Connection conn;

    public SuKienDAO(Connection conn) {
        this.conn = conn;
    }

    public ArrayList<SuKien> getAll() {
        ArrayList<SuKien> list = new ArrayList<>();
        String sql = "SELECT sk.*, dv.tenDichVu " +
                     "FROM SuKien sk " +
                     "JOIN DichVu dv ON sk.maDichVu = dv.maDichVu";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                SuKien sk = new SuKien();
                sk.setMaSuKien(rs.getString("maSuKien"));
                sk.setMaDichVu(rs.getString("maDichVu"));
                sk.setTenSuKien(rs.getString("tenSuKien"));
                sk.setThoiGianBatDau(rs.getString("TGianBatDau"));   // String
                sk.setThoiGianKetThuc(rs.getString("TgianKetThuc")); // String
                sk.setTrangThaiHoatDong(rs.getString("TrangThaiHoatDong"));
                sk.setMucDoCuonHut(rs.getString("mucDoCuonHut"));
                sk.setGioiHanDoTuoi(rs.getString("gioiHanDoTuoi"));  // String
                sk.setSucChua(rs.getInt("sucChua"));
                sk.setTenDichVu(rs.getString("tenDichVu"));
                list.add(sk);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean add(SuKien sk) {
        String sql = "INSERT INTO SuKien VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, sk.getMaSuKien());
            ps.setString(2, sk.getMaDichVu());
            ps.setString(3, sk.getTenSuKien());
            ps.setString(4, sk.getThoiGianBatDau());
            ps.setString(5, sk.getThoiGianKetThuc());
            ps.setString(6, sk.getTrangThaiHoatDong());
            ps.setString(7, sk.getMucDoCuonHut());
            ps.setString(8, sk.getGioiHanDoTuoi());
            ps.setInt(9, sk.getSucChua());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean update(SuKien sk) {
        String sql = "UPDATE SuKien SET maDichVu=?, tenSuKien=?, TGianBatDau=?, TgianKetThuc=?, TrangThaiHoatDong=?, mucDoCuonHut=?, gioiHanDoTuoi=?, sucChua=? WHERE maSuKien=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, sk.getMaDichVu());
            ps.setString(2, sk.getTenSuKien());
            ps.setString(3, sk.getThoiGianBatDau());
            ps.setString(4, sk.getThoiGianKetThuc());
            ps.setString(5, sk.getTrangThaiHoatDong());
            ps.setString(6, sk.getMucDoCuonHut());
            ps.setString(7, sk.getGioiHanDoTuoi());
            ps.setInt(8, sk.getSucChua());
            ps.setString(9, sk.getMaSuKien());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean delete(String maSuKien) {
        String sql = "DELETE FROM SuKien WHERE maSuKien=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maSuKien);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public ArrayList<SuKien> timKiem(String keyword) {
        ArrayList<SuKien> list = new ArrayList<>();
        String sql = "SELECT sk.*, dv.tenDichVu " +
                     "FROM SuKien sk " +
                     "JOIN DichVu dv ON sk.maDichVu = dv.maDichVu " +
                     "WHERE sk.maSuKien LIKE ? OR sk.tenSuKien LIKE ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            String likeKeyword = "%" + keyword + "%";
            ps.setString(1, likeKeyword);
            ps.setString(2, likeKeyword);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    SuKien sk = new SuKien();
                    sk.setMaSuKien(rs.getString("maSuKien"));
                    sk.setMaDichVu(rs.getString("maDichVu"));
                    sk.setTenSuKien(rs.getString("tenSuKien"));
                    sk.setThoiGianBatDau(rs.getString("TGianBatDau"));
                    sk.setThoiGianKetThuc(rs.getString("TgianKetThuc"));
                    sk.setTrangThaiHoatDong(rs.getString("TrangThaiHoatDong"));
                    sk.setMucDoCuonHut(rs.getString("mucDoCuonHut"));
                    sk.setGioiHanDoTuoi(rs.getString("gioiHanDoTuoi"));
                    sk.setSucChua(rs.getInt("sucChua"));
                    sk.setTenDichVu(rs.getString("tenDichVu"));
                    list.add(sk);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
