package DAO;

import Model.VeTour;
import java.sql.*;
import java.util.*;

public class VeTourDAO {
    private Connection conn;

    public VeTourDAO(Connection conn) {
        this.conn = conn;
    }

    public List<VeTour> getAllVeTour() {
        List<VeTour> list = new ArrayList<>();
        String sql = "SELECT * FROM VeTour";
        try (PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                VeTour vt = new VeTour(
                        rs.getString("maVeTour"),
                        rs.getString("maDatVe"),
                        rs.getString("maTour"),
                        rs.getInt("soLuongNguoi"),
                        rs.getString("phuongTien"),
                        rs.getString("gioDi")
                );
                list.add(vt);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean insert(VeTour vt) {
        String sql = "INSERT INTO VeTour VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, vt.getMaVeTour());
            ps.setString(2, vt.getMaDatVe());
            ps.setString(3, vt.getMaTour());
            ps.setInt(4, vt.getSoLuongNguoi());
            ps.setString(5, vt.getPhuongTien());
            ps.setString(6, vt.getGioDi());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(VeTour vt) {
        String sql = "UPDATE VeTour SET maDatVe=?, maTour=?, soLuongNguoi=?, phuongTien=?, gioDi=? WHERE maVeTour=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, vt.getMaDatVe());
            ps.setString(2, vt.getMaTour());
            ps.setInt(3, vt.getSoLuongNguoi());
            ps.setString(4, vt.getPhuongTien());
            ps.setString(5, vt.getGioDi());
            ps.setString(6, vt.getMaVeTour());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(String maVeTour) {
        String sql = "DELETE FROM VeTour WHERE maVeTour=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maVeTour);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public VeTour findById(String maVeTour) {
        String sql = "SELECT * FROM VeTour WHERE maVeTour=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maVeTour);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new VeTour(
                        rs.getString("maVeTour"),
                        rs.getString("maDatVe"),
                        rs.getString("maTour"),
                        rs.getInt("soLuongNguoi"),
                        rs.getString("phuongTien"),
                        rs.getString("gioDi")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
