package DAO;

import Model.TroChoi;
import java.sql.*;
import java.util.ArrayList;

public class TroChoiDAO {
    private Connection conn;

    public TroChoiDAO(Connection conn) {
        this.conn = conn;
    }

    
    public ArrayList<TroChoi> getAll() {
        ArrayList<TroChoi> list = new ArrayList<>();
        String sql = "SELECT tc.*, dv.tenDichVu " +
                     "FROM TroChoi tc " +
                     "JOIN DichVu dv ON tc.maDichVu = dv.maDichVu";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                TroChoi tc = new TroChoi();
                tc.setMaTroChoi(rs.getString("maTroChoi"));
                tc.setMaDichVu(rs.getString("maDichVu"));
                tc.setTenTroChoi(rs.getString("tenTroChoi"));
                tc.setMoTa(rs.getString("mota"));
                tc.setGioiHanDoTuoi(rs.getString("gioiHanDoTuoi"));
                tc.setSucChua(rs.getInt("sucChua"));
                tc.setThoiGianHoatDong(rs.getString("thoiGianHoatDong")); // Chuyển Date -> String
                tc.setTenDichVu(rs.getString("tenDichVu"));
                list.add(tc);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean add(TroChoi tc) {
        String sql = "INSERT INTO TroChoi VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, tc.getMaTroChoi());
            ps.setString(2, tc.getMaDichVu());
            ps.setString(3, tc.getTenTroChoi());
            ps.setString(4, tc.getMoTa());
            ps.setString(5, tc.getGioiHanDoTuoi());
            ps.setInt(6, tc.getSucChua());
            ps.setString(7, tc.getThoiGianHoatDong()); // Lưu string thời gian
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean update(TroChoi tc) {
        String sql = "UPDATE TroChoi SET maDichVu=?, tenTroChoi=?, mota=?, gioiHanDoTuoi=?, sucChua=?, thoiGianHoatDong=? WHERE maTroChoi=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, tc.getMaDichVu());
            ps.setString(2, tc.getTenTroChoi());
            ps.setString(3, tc.getMoTa());
            ps.setString(4, tc.getGioiHanDoTuoi());
            ps.setInt(5, tc.getSucChua());
            ps.setString(6, tc.getThoiGianHoatDong());
            ps.setString(7, tc.getMaTroChoi());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean delete(String maTroChoi) {
        String sql = "DELETE FROM TroChoi WHERE maTroChoi=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maTroChoi);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public ArrayList<TroChoi> timKiem(String keyword) {
        ArrayList<TroChoi> list = new ArrayList<>();
        String sql = "SELECT tc.*, dv.tenDichVu " +
                     "FROM TroChoi tc " +
                     "JOIN DichVu dv ON tc.maDichVu = dv.maDichVu " +
                     "WHERE tc.maTroChoi LIKE ? OR tc.tenTroChoi LIKE ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            String likeKeyword = "%" + keyword + "%";
            ps.setString(1, likeKeyword);
            ps.setString(2, likeKeyword);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    TroChoi tc = new TroChoi();
                    tc.setMaTroChoi(rs.getString("maTroChoi"));
                    tc.setMaDichVu(rs.getString("maDichVu"));
                    tc.setTenTroChoi(rs.getString("tenTroChoi"));
                    tc.setMoTa(rs.getString("mota"));
                    tc.setGioiHanDoTuoi(rs.getString("gioiHanDoTuoi"));
                    tc.setSucChua(rs.getInt("sucChua"));
                    tc.setThoiGianHoatDong(rs.getString("thoiGianHoatDong"));
                    tc.setTenDichVu(rs.getString("tenDichVu"));
                    list.add(tc);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
