package DAO;

import Model.Tour;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TourDAO {
    private Connection conn;

    public TourDAO(Connection conn) {
        this.conn = conn;
    }

    // Lấy danh sách tất cả các tour
    public List<Tour> getAllTours() {
        List<Tour> list = new ArrayList<>();
        String sql = "SELECT * FROM Tour";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Tour tour = new Tour(
                        rs.getString("maTour"),
                        rs.getString("tenTour"),
                        rs.getString("moTa"),
                        rs.getDouble("giaTour"),
                        rs.getDate("tg_batDau"),
                        rs.getDate("tg_ketThuc"),
                        rs.getInt("soLuongMax")
                );
                list.add(tour);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Thêm một tour mới
    public boolean themTour(Tour tour) {
        String sql = "INSERT INTO Tour (maTour, tenTour, moTa, giaTour, tg_batDau, tg_ketThuc, soLuongMax) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, tour.getMaTour());
            ps.setString(2, tour.getTenTour());
            ps.setString(3, tour.getMoTa());
            ps.setDouble(4, tour.getGiaTour());
            ps.setDate(5, tour.getTgBatDau());
            ps.setDate(6, tour.getTgKetThuc());
            ps.setInt(7, tour.getSoLuongMax());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Cập nhật thông tin tour
    public boolean suaTour(Tour tour) {
        String sql = "UPDATE Tour SET tenTour=?, moTa=?, giaTour=?, tg_batDau=?, tg_ketThuc=?, soLuongMax=? WHERE maTour=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, tour.getTenTour());
            ps.setString(2, tour.getMoTa());
            ps.setDouble(3, tour.getGiaTour());
            ps.setDate(4, tour.getTgBatDau());
            ps.setDate(5, tour.getTgKetThuc());
            ps.setInt(6, tour.getSoLuongMax());
            ps.setString(7, tour.getMaTour());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Xóa một tour
    public boolean xoaTour(String maTour) {
        String sql = "DELETE FROM Tour WHERE maTour=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maTour);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Tìm kiếm tour theo từ khóa
    public List<Tour> timKiemTour(String keyword) {
        List<Tour> list = new ArrayList<>();
        String sql = "SELECT * FROM Tour WHERE maTour LIKE ? OR tenTour LIKE ? OR moTa LIKE ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            String searchPattern = "%" + keyword + "%";
            ps.setString(1, searchPattern); // Tìm trong maTour
            ps.setString(2, searchPattern); // Tìm trong tenTour
            ps.setString(3, searchPattern); // Tìm trong moTa

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Tour tour = new Tour(
                            rs.getString("maTour"),
                            rs.getString("tenTour"),
                            rs.getString("moTa"),
                            rs.getDouble("giaTour"),
                            rs.getDate("tg_batDau"),
                            rs.getDate("tg_ketThuc"),
                            rs.getInt("soLuongMax")
                    );
                    list.add(tour);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    public int tongVeTourDaDat(String maTour) {
        String sql = "SELECT T.maTour, SUM(DV.SoLuong) AS TongSoLuongDat " +
                "FROM Tour T " +
                "JOIN VeTour VT ON T.maTour = VT.maTour " +
                "JOIN DatVe DV ON VT.MaDatVe = DV.MaDatVe " +
                "WHERE T.maTour = ? " +
                "GROUP BY T.maTour";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maTour);  // Đặt mã tour vào câu lệnh SQL

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("TongSoLuongDat");  // Trả về tổng số vé đã đặt
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;  // Trả về 0 nếu không có vé đã đặt
    }
}
