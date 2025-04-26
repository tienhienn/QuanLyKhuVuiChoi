package DAO;

import Model.Khu;
import java.sql.*;
import java.util.ArrayList;

public class KhuDAO {
    private Connection conn;

    public KhuDAO(Connection conn) {
        this.conn = conn;
    }

    public ArrayList<Khu> getAllKhu() {
        ArrayList<Khu> list = new ArrayList<>();
        String sql = "SELECT * FROM Khu";
        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                String maKhu = rs.getString("maKhu");
                String tenKhu = rs.getString("tenKhu");
                String moTa = rs.getString("mota");

                list.add(new Khu(maKhu, tenKhu, moTa));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean insertKhu(Khu khu) {
        String sql = "INSERT INTO Khu(maKhu, tenKhu, mota) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, khu.getMaKhu());
            ps.setString(2, khu.getTenKhu());
            ps.setString(3, khu.getMoTa());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateKhu(Khu khu) {
        String sql = "UPDATE Khu SET tenKhu = ?, mota = ? WHERE maKhu = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, khu.getTenKhu());
            ps.setString(2, khu.getMoTa());
            ps.setString(3, khu.getMaKhu());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteKhu(String maKhu) {
        String sql = "DELETE FROM Khu WHERE maKhu = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maKhu);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}

