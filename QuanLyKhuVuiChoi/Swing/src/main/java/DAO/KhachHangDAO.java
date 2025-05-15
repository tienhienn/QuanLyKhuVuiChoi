package DAO;

import Model.KhachHang;
import java.sql.*;
import java.util.*;

public class KhachHangDAO {
    private Connection conn;

    public KhachHangDAO(Connection conn) {
        this.conn = conn;
    }

    public List<KhachHang> getAllKhachHang() {
        List<KhachHang> list = new ArrayList<>();
        try {
            String sql = "SELECT * FROM khachhang";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                KhachHang kh = new KhachHang(
                        rs.getString("maKhachHang"),
                        rs.getString("tenKhachHang"),
                        rs.getString("matkhau"),
                        rs.getString("SDT"),
                        rs.getString("email"),
                        rs.getString("diaChi"),
                        rs.getString("gioiTinh"),
                        rs.getDate("ngaySinh"),
                        rs.getString("quocTich")
                );
                list.add(kh);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean addKhachHang(KhachHang kh) {
        try {
            String sql = "INSERT INTO khachhang VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, kh.getMaKhachHang());
            ps.setString(2, kh.getTenKhachHang());
            ps.setString(3, kh.getMatKhau());
            ps.setString(4, kh.getSdt());
            ps.setString(5, kh.getEmail());
            ps.setString(6, kh.getDiaChi());
            ps.setString(7, kh.getGioiTinh());
            ps.setDate(8, kh.getNgaySinh());
            ps.setString(9, kh.getQuocTich());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteKhachHang(String maKH) {
        try {
            String sql = "DELETE FROM khachhang WHERE maKhachHang = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, maKH);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateKhachHang(KhachHang kh) {
        try {
            String sql = "UPDATE khachhang SET tenKhachHang=?, matkhau=?, SDT=?, email=?, diaChi=?, gioiTinh=?, ngaySinh=?, quocTich=? WHERE maKhachHang=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, kh.getTenKhachHang());
            ps.setString(2, kh.getMatKhau());
            ps.setString(3, kh.getSdt());
            ps.setString(4, kh.getEmail());
            ps.setString(5, kh.getDiaChi());
            ps.setString(6, kh.getGioiTinh());
            ps.setDate(7, kh.getNgaySinh());
            ps.setString(8, kh.getQuocTich());
            ps.setString(9, kh.getMaKhachHang());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<String> getAllMaKhachHang() {
        List<String> list = new ArrayList<>();
        String sql = "SELECT maKhachHang FROM khachhang";

        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(rs.getString("maKhachHang"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
    public KhachHang getById(String maKH) {
        try {
            String sql = "SELECT * FROM khachhang WHERE maKhachHang = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, maKH);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new KhachHang(
                    rs.getString("maKhachHang"),
                    rs.getString("tenKhachHang"),
                    rs.getString("matkhau"),
                    rs.getString("SDT"),
                    rs.getString("email"),
                    rs.getString("diaChi"),
                    rs.getString("gioiTinh"),
                    rs.getDate("ngaySinh"),
                    rs.getString("quocTich")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public List<KhachHang> timKiem(String keyword) {
        List<KhachHang> list = new ArrayList<>();
        String sql = "SELECT * FROM khachhang WHERE tenKhachHang LIKE ? OR maKhachHang LIKE ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + keyword + "%");
            ps.setString(2, "%" + keyword + "%");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                KhachHang kh = new KhachHang(
                    rs.getString("maKhachHang"),
                    rs.getString("tenKhachHang"),
                    rs.getString("matkhau"),
                    rs.getString("SDT"),
                    rs.getString("email"),
                    rs.getString("diaChi"),
                    rs.getString("gioiTinh"),
                    rs.getDate("ngaySinh"),
                    rs.getString("quocTich")
                );
                list.add(kh);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
}
