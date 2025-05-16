
package DAO;

import Model.DichVu;
import java.sql.*;
import java.util.ArrayList;

public class DichVuDAO {
    private Connection conn;

    public DichVuDAO(Connection conn) {
        this.conn = conn;
    }

    public ArrayList<DichVu> getAll() {
        ArrayList<DichVu> list = new ArrayList<>();
        String sql = "SELECT MaDichVu, TenDichVu FROM DichVu";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                String ma = rs.getString("MaDichVu");
                String ten = rs.getString("TenDichVu");
                list.add(new DichVu(ma, ten));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean addDichVu(DichVu dv) {
        String sql = "INSERT INTO DichVu (MaDichVu, TenDichVu) VALUES (?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, dv.getMaDichVu());
            ps.setString(2, dv.getTenDichVu());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateDichVu(DichVu dv) {
        String sql = "UPDATE DichVu SET TenDichVu = ? WHERE MaDichVu = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, dv.getTenDichVu());
            ps.setString(2, dv.getMaDichVu());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteDichVu(String maDichVu) {
        String sql = "DELETE FROM DichVu WHERE MaDichVu = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maDichVu);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public DichVu getById(String maDichVu) {
        String sql = "SELECT MaDichVu, TenDichVu FROM DichVu WHERE MaDichVu = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maDichVu);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new DichVu(rs.getString("MaDichVu"), rs.getString("TenDichVu"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
