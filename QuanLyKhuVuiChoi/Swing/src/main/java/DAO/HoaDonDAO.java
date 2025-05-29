package DAO;

import Model.HoaDon;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HoaDonDAO {
    private Connection conn;

    public HoaDonDAO(Connection conn) {
        this.conn = conn;
    }

    public List<HoaDon> getAll() {
        List<HoaDon> list = new ArrayList<>();

        // Query lấy thông tin DatVe, khách hàng, vé tour, tổng tiền dịch vụ, tổng tiền
        String sql = 
            "SELECT dv.MaDatVe, kh.tenKhachHang, dv.NgayDat, dv.NgayDi, " +
            "SUM(vt.NguoiLon) as NguoiLon, SUM(vt.TreEm) as TreEm, SUM(vt.NguoiGia) as NguoiGia, " +
            "t.giaTour, " +
            "SUM((vt.NguoiLon + vt.TreEm + vt.NguoiGia) * t.giaTour) as ThanhTienTour, " +
            "ISNULL( (" +
            "    SELECT SUM(dv2.gia) FROM (" +
            "       SELECT d.maDichVu, 100000 as gia FROM dichvu d WHERE d.maDichVu IN ( " +
            "           SELECT maDichVu FROM datve_dichvu WHERE MaDatVe = dv.MaDatVe " +
            "       ) " +
            "    ) dv2 " +
            "), 0) as ThanhTienDichVu, " +
            "(SUM((vt.NguoiLon + vt.TreEm + vt.NguoiGia) * t.giaTour) + " +
            " ISNULL( (" +
            "    SELECT SUM(dv2.gia) FROM (" +
            "       SELECT d.maDichVu, 100000 as gia FROM dichvu d WHERE d.maDichVu IN ( " +
            "           SELECT maDichVu FROM datve_dichvu WHERE MaDatVe = dv.MaDatVe " +
            "       ) " +
            "    ) dv2 " +
            "), 0)) as TongTien " +
            "FROM DatVe dv " +
            "INNER JOIN KhachHang kh ON dv.maKhachHang = kh.maKhachHang " +
            "INNER JOIN VeTour vt ON dv.MaDatVe = vt.MaDatVe " +
            "INNER JOIN Tour t ON vt.MaTour = t.maTour " +
            "GROUP BY dv.MaDatVe, kh.tenKhachHang, dv.NgayDat, dv.NgayDi, t.giaTour";

        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                HoaDon hd = new HoaDon();

                hd.setMaDatVe(rs.getString("MaDatVe"));
                hd.setTenKhachHang(rs.getString("tenKhachHang"));
                hd.setNgayDat(rs.getDate("NgayDat"));
                hd.setNgayDi(rs.getDate("NgayDi"));
                hd.setNguoiLon(rs.getInt("NguoiLon"));
                hd.setTreEm(rs.getInt("TreEm"));
                hd.setNguoiGia(rs.getInt("NguoiGia"));
                hd.setGiaTour(rs.getDouble("giaTour"));
                hd.setThanhTienTour(rs.getDouble("ThanhTienTour"));
                hd.setThanhTienDichVu(rs.getDouble("ThanhTienDichVu"));
                hd.setTongTien(rs.getDouble("TongTien"));

                list.add(hd);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
