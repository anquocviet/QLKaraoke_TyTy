/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import connect.ConnectDB;
import controllers.GD_TraCuuHoaDonController;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author vie
 */
public class HoaDonThanhToan {

    private String maHoaDon;
    private NhanVien nhanVienLap;
    private KhachHang khachHang;
    private CT_KhuyenMai khuyenMai;
    private LocalDateTime ngayLap;
    private long tongTien;

    public HoaDonThanhToan(String maHoaDon, NhanVien nhanVienLap, KhachHang khachHang, CT_KhuyenMai khuyenMai, LocalDateTime ngayLap) {
        setMaHoaDon(maHoaDon);
        setNhanVienLap(nhanVienLap);
        setKhachHang(khachHang);
        setKhuyenMai(khuyenMai);
        setNgayLap(ngayLap);
    }

    public HoaDonThanhToan() {
    }

    public HoaDonThanhToan(String maHoaDon) {
        this.maHoaDon = maHoaDon;
    }

    public String getMaHoaDon() {
        return maHoaDon;
    }

    public void setMaHoaDon(String maHoaDon) throws IllegalArgumentException {
        if (maHoaDon == null || maHoaDon.isEmpty()) {
            throw new IllegalArgumentException("Mã hóa đơn không được rỗng");
        } else {
            this.maHoaDon = maHoaDon;
        }
    }

    public NhanVien getNhanVienLap() {
        return nhanVienLap;
    }

    public void setNhanVienLap(NhanVien nhanVienLap) throws IllegalArgumentException {
        if (nhanVienLap == null || maHoaDon.isEmpty()) {
            throw new IllegalArgumentException("không được rỗng");
        } else {
            this.nhanVienLap = nhanVienLap;
        }
    }

    public KhachHang getKhachHang() {
        return khachHang;
    }

    public void setKhachHang(KhachHang khachHang) throws IllegalArgumentException {
        if (khachHang == null) {
            throw new IllegalArgumentException("Khách hàng không được rỗng");

        } else {
            this.khachHang = khachHang;
        }
    }

    public CT_KhuyenMai getKhuyenMai() {
        return khuyenMai;
    }

    public void setKhuyenMai(CT_KhuyenMai khuyenMai) {
        this.khuyenMai = khuyenMai;
    }

    public LocalDateTime getNgayLap() {
        return ngayLap;
    }

    public void setNgayLap(LocalDateTime ngayLap) throws IllegalArgumentException {
        if (ngayLap == null) {
            throw new IllegalArgumentException("Ngày lập không được rỗng");
        } else {
            this.ngayLap = ngayLap;
        }
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.maHoaDon);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final HoaDonThanhToan other = (HoaDonThanhToan) obj;
        return Objects.equals(this.maHoaDon, other.maHoaDon);
    }

    @Override
    public String toString() {
        return "HoaDonThanhToan{" + "maHoaDon=" + maHoaDon + ", nhanVienLap=" + nhanVienLap + ", khachHang=" + khachHang + ", khuyenMai=" + khuyenMai + ", ngayLap=" + ngayLap + '}';
    }

    public void tinhTongTien() {
    }

    //    Get Data From DB
    public static ObservableList<HoaDonThanhToan> getAllHoaDon() {
        ObservableList<HoaDonThanhToan> dsHoaDon = FXCollections.observableArrayList();
        Connection conn = ConnectDB.getConnection();
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            String sql = "SELECT * FROM HoaDonThanhToan "
                    + "JOIN KhachHang ON HoaDonThanhToan.MaKhachHang = KhachHang.MaKhachHang ";

            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String maHD = rs.getString("MaHoaDon");
                String maKH = rs.getString("MaKhachHang");
                String maNV = rs.getString("MaNhanVien");
                String maKM = rs.getString("MaKhuyenMai");
                LocalDateTime ngayLap = rs.getTimestamp("NgayLap").toLocalDateTime();
                String tenKH = rs.getString("TenKhachHang");
                String sdtKH = rs.getString("SoDienThoai");
				int namSinhKH = rs.getInt("NamSinh");
				boolean gioiTinhKH = rs.getBoolean("GioiTinh");
                
                dsHoaDon = (ObservableList<HoaDonThanhToan>) new HoaDonThanhToan(maHD,
                        NhanVien.getNhanVienTheoMaNhanVien(maNV),
                        new KhachHang(maKH, tenKH, sdtKH, namSinhKH, gioiTinhKH),
                        new CT_KhuyenMai(maKM), ngayLap);                
            }
        } catch (SQLException ex) {
            Logger.getLogger(GD_TraCuuHoaDonController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(HoaDonThanhToan.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                assert stmt != null;
                stmt.close();
            } catch (SQLException ex) {
                Logger.getLogger(GD_TraCuuHoaDonController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return dsHoaDon;
    }

    public static HoaDonThanhToan getBillByID(String billID) {
        HoaDonThanhToan bill = null;
        Connection conn = ConnectDB.getConnection();
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            String sql = "SELECT * FROM HoaDonThanhToan JOIN KhachHang "
                    + "ON HoaDonThanhToan.MaKhachHang = KhachHang.MaKhachHang "
                    + "WHERE HoaDonThanhToan.MaHoaDon = '" + billID + "'";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String maKH = rs.getString("MaKhachHang");
                String maNV = rs.getString("MaNhanVien");
                String maKM = rs.getString("MaKhuyenMai");
                LocalDateTime ngayLap = rs.getTimestamp("NgayLap").toLocalDateTime();
                String tenKH = rs.getString("TenKhachHang");
                String sdt = rs.getString("SoDienThoai");
                int namSinh = rs.getInt("NamSinh");
                boolean gioiTinh = rs.getBoolean("GioiTinh");
                bill = new HoaDonThanhToan(billID,
                        NhanVien.getNhanVienTheoMaNhanVien(maNV),
                        new KhachHang(maKH, tenKH, sdt, namSinh, gioiTinh),
                        new CT_KhuyenMai(maKM), ngayLap);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                assert stmt != null;
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return bill;
    }

    public static String getBillIDByRoomID(String roomID) {
        Connection conn = ConnectDB.getConnection();
        Statement stmt = null;
        String billID = "";
        try {
            stmt = conn.createStatement();
            String sql = String.format("SELECT TOP 1 * FROM ChiTietHD_Phong WHERE MaPhong = '%s' ORDER BY GioVao DESC", roomID);
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                billID = rs.getString("MaHoaDon");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                assert stmt != null;
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return billID;
    }

    public static String getBillIDByRoomIDtoHDPhong(String roomID) {
        Connection conn = ConnectDB.getConnection();
        String billID = "";

        try {
            String sql = "SELECT DISTINCT MaHoaDon FROM ChiTietHD_Phong WHERE MaPhong = ?";
            try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
                preparedStatement.setString(1, roomID);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        billID = resultSet.getString("MaHoaDon");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return billID;
    }

    public static int getDemSoLuongHoaDonTheoNgay(LocalDateTime ngay) {
        int soLuong = 0;
        Connection conn = ConnectDB.getConnection();
        PreparedStatement preparedStatement = null;

        try {
            String sql = "SELECT COUNT(*) AS SoLuong FROM HoaDonThanhToan WHERE CAST(NgayLap AS DATE) = CAST(? AS DATE)";
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setTimestamp(1, Timestamp.valueOf(ngay));

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    soLuong = resultSet.getInt("SoLuong");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return soLuong;
    }

    public static boolean themHoaDonThanhToan(HoaDonThanhToan hoaDon) {
        Connection conn = ConnectDB.getConnection();
        PreparedStatement preparedStatement = null;

        try {
            String sql = "INSERT INTO HoaDonThanhToan (MaHoaDon, MaNhanVien, MaKhachHang, MaKhuyenMai, NgayLap) VALUES (?, ?, ?, ?, ?)";
            preparedStatement = conn.prepareStatement(sql);

            preparedStatement.setString(1, hoaDon.getMaHoaDon());
            preparedStatement.setString(2, hoaDon.getNhanVienLap().getMaNhanVien());
            preparedStatement.setString(3, hoaDon.getKhachHang().getMaKhachHang());
            preparedStatement.setString(4, null);
            preparedStatement.setTimestamp(5, Timestamp.valueOf(hoaDon.getNgayLap()));

            int rowCount = preparedStatement.executeUpdate();

            if (rowCount > 0) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean updateHoaDonThanhToan(HoaDonThanhToan hd) {
        Connection conn = ConnectDB.getConnection();
        PreparedStatement pstm = null;
        int n = 0;
        String sql = "UPDATE HoaDonThanhToan SET MaKhachHang = ?, MaNhanVien = ?, MaKhuyenMai = ?, NgayLap = ? WHERE MaHoaDon = ?";
        try {
            pstm = conn.prepareStatement(sql);
            pstm.setString(1, hd.getKhachHang().getMaKhachHang());
            pstm.setString(2, hd.getNhanVienLap().getMaNhanVien());
            System.out.println(hd.getKhuyenMai().getMaKhuyenMai());
            pstm.setString(3, hd.getKhuyenMai().getMaKhuyenMai());
            pstm.setTimestamp(4, Timestamp.valueOf(hd.getNgayLap()));
            pstm.setString(5, hd.getMaHoaDon());
            n = pstm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(HoaDonThanhToan.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                assert pstm != null;
                pstm.close();
            } catch (SQLException ex) {
                Logger.getLogger(HoaDonThanhToan.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return n > 0;
    }

}
