/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import connect.ConnectDB;
import controllers.GD_TraCuuHoaDonController;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.IsoFields;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author vie
 */
public final class HoaDonThanhToan {

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

    public HoaDonThanhToan(String maHoaDon, NhanVien nhanVienLap, KhachHang khachHang, CT_KhuyenMai khuyenMai, LocalDateTime ngayLap, long tongTien) {
        setMaHoaDon(maHoaDon);
        setNhanVienLap(nhanVienLap);
        setKhachHang(khachHang);
        setKhuyenMai(khuyenMai);
        setNgayLap(ngayLap);
        this.tongTien = tongTien;
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

    public long getTongTien() {
        return tongTien;
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
                long tongTien = rs.getLong("TongTien");

                dsHoaDon.add(new HoaDonThanhToan(maHD,
                        NhanVien.getNhanVienTheoMaNhanVien(maNV),
                        new KhachHang(maKH, tenKH, sdtKH, namSinhKH, gioiTinhKH),
                        new CT_KhuyenMai(maKM), ngayLap, tongTien));
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

    public static ObservableList<HoaDonThanhToan> getBillByDate(LocalDateTime ngay) {
        ObservableList<HoaDonThanhToan> dsHoaDon = FXCollections.observableArrayList();
        Connection conn = ConnectDB.getConnection();
        PreparedStatement preparedStatement = null;

        try {
            String sql = "SELECT * FROM HoaDonThanhToan "
                    + "JOIN KhachHang ON HoaDonThanhToan.MaKhachHang = KhachHang.MaKhachHang "
                    + "WHERE CAST(HoaDonThanhToan.NgayLap AS DATE) = CAST(? AS DATE)";
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setTimestamp(1, Timestamp.valueOf(ngay));

            try (ResultSet rs = preparedStatement.executeQuery()) {
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

                    dsHoaDon.add(new HoaDonThanhToan(maHD,
                            NhanVien.getNhanVienTheoMaNhanVien(maNV),
                            new KhachHang(maKH, tenKH, sdtKH, namSinhKH, gioiTinhKH),
                            new CT_KhuyenMai(maKM), ngayLap));
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

        return dsHoaDon;
    }

    public static ObservableList statisticalByMonth(LocalDate date) {
        Connection conn = ConnectDB.getConnection();
        Statement stmt = null;
        ObservableList arr = FXCollections.observableArrayList();
        try {
            stmt = conn.createStatement();
            String sql = String.format("SELECT DAY(NgayLap) AS Ngay, SUM(TongTien) AS TongTien "
                    + "FROM HoaDonThanhToan WHERE YEAR(NgayLap) = %d AND MONTH(NgayLap) = %d "
                    + "GROUP BY DAY(NgayLap)", date.getYear(), date.getMonthValue());

            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                int ngay = rs.getInt("Ngay");
                long tongTien = rs.getLong("TongTien");
                arr.add(ngay);
                arr.add(tongTien);

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
        return arr;
    }

    public static ObservableList statisticalByQuarter(int year) {
        Connection conn = ConnectDB.getConnection();
        Statement stmt = null;
        ObservableList arr = FXCollections.observableArrayList();
        try {
            stmt = conn.createStatement();
            String sql = String.format("SELECT DATEPART(QUARTER, NgayLap) AS Quy, SUM(TongTien) AS TongTien "
                    + "FROM HoaDonThanhToan WHERE YEAR(NgayLap) = %d "
                    + "GROUP BY DATEPART(QUARTER, NgayLap)", year);

            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                int quy = rs.getInt("Quy");
                long tongTien = rs.getLong("TongTien");
                arr.add(quy);
                arr.add(tongTien);

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
        return arr;
    }

    public static ObservableList statisticalByYear(int year) {
        Connection conn = ConnectDB.getConnection();
        Statement stmt = null;
        ObservableList arr = FXCollections.observableArrayList();
        try {
            stmt = conn.createStatement();
            String sql = String.format("SELECT MONTH(NgayLap) AS Thang, SUM(TongTien) AS TongTien "
                    + "FROM HoaDonThanhToan WHERE YEAR(NgayLap) = %d "
                    + "GROUP BY MONTH(NgayLap)", year);

            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                int thang = rs.getInt("Thang");
                long tongTien = rs.getLong("TongTien");
                arr.add(thang);
                arr.add(tongTien);

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
        return arr;
    }

    public static ObservableList<HoaDonThanhToan> timHoaDon(String maHoaDon, String tenKH, String sdt, LocalDateTime ngayLap) {
        ObservableList<HoaDonThanhToan> ketQuaTimKiem = FXCollections.observableArrayList();
        Connection conn = ConnectDB.getConnection();
        PreparedStatement preparedStatement = null;

        try {
            // Xây dựng câu truy vấn tìm kiếm dựa trên thông tin nhập vào
            String sql = "SELECT * FROM HoaDonThanhToan "
                    + "JOIN KhachHang ON HoaDonThanhToan.MaKhachHang = KhachHang.MaKhachHang "
                    + "WHERE MaHoaDon LIKE ? AND TenKhachHang LIKE ? AND SoDienThoai LIKE ? "
                    + "AND (NgayLap = COALESCE(?, NgayLap) OR ? IS NULL)";

            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, "%" + maHoaDon + "%");
            preparedStatement.setString(2, "%" + tenKH + "%");
            preparedStatement.setString(3, "%" + sdt + "%");
            if (ngayLap != null) {
                preparedStatement.setTimestamp(4, Timestamp.valueOf(ngayLap));
                preparedStatement.setTimestamp(5, Timestamp.valueOf(ngayLap));
            } else {
                preparedStatement.setNull(4, java.sql.Types.TIMESTAMP);
                preparedStatement.setNull(5, java.sql.Types.TIMESTAMP);
            }

            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                String maHD = rs.getString("MaHoaDon");
                String maKH = rs.getString("MaKhachHang");
                String maNV = rs.getString("MaNhanVien");
                String maKM = rs.getString("MaKhuyenMai");
                LocalDateTime ngayLapResult = rs.getTimestamp("NgayLap").toLocalDateTime();
                String tenKHResult = rs.getString("TenKhachHang");
                String sdtResult = rs.getString("SoDienThoai");
                int namSinhKH = rs.getInt("NamSinh");
                boolean gioiTinhKH = rs.getBoolean("GioiTinh");
                long tongTien = rs.getLong("TongTien");

                ketQuaTimKiem.add(new HoaDonThanhToan(maHD,
                        NhanVien.getNhanVienTheoMaNhanVien(maNV),
                        new KhachHang(maKH, tenKHResult, sdtResult, namSinhKH, gioiTinhKH),
                        new CT_KhuyenMai(maKM), ngayLapResult, tongTien));
            }
        } catch (SQLException ex) {
            Logger.getLogger(GD_TraCuuHoaDonController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(HoaDonThanhToan.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(GD_TraCuuHoaDonController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return ketQuaTimKiem;
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

    public static int countBill() {
        Connection conn = ConnectDB.getConnection();
        Statement stmt = null;
        int soLuong = 0;
        String sql = "SELECT COUNT(MaHoaDon) AS SoLuong FROM HoaDonThanhToan";
        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                soLuong = rs.getInt("SoLuong");
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
        return soLuong;
    }

    public static int countBillByMonth(LocalDate date) {
        Connection conn = ConnectDB.getConnection();
        Statement stmt = null;
        int soLuong = 0;
        String sql = String.format("SELECT COUNT(MaHoaDon) AS SoLuong "
                + "FROM HoaDonThanhToan WHERE MONTH(NgayLap) = %d AND YEAR(NgayLap) = %d", date.getMonthValue(), date.getYear());
        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                soLuong = rs.getInt("SoLuong");
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
        return soLuong;
    }

    public static int countBillByDay(LocalDate date) {
        Connection conn = ConnectDB.getConnection();
        Statement stmt = null;
        int soLuong = 0;
        String sql = String.format("SELECT COUNT(MaHoaDon) AS SoLuong "
                + "FROM HoaDonThanhToan WHERE DAY(NgayLap) = %d AND MONTH(NgayLap) = %d AND YEAR(NgayLap) = %d", date.getDayOfMonth(), date.getMonthValue(), date.getYear());
        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                soLuong = rs.getInt("SoLuong");
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
        return soLuong;
    }

    public static int countBillByQuarterInYear(LocalDate date) {
        Connection conn = ConnectDB.getConnection();
        Statement stmt = null;
        int soLuong = 0;
        int quarter = date.get(IsoFields.QUARTER_OF_YEAR);
        int year = date.getYear();
        String sql = String.format("SELECT COUNT(MaHoaDon) AS SoLuong "
                + "FROM HoaDonThanhToan WHERE DATEPART(QUARTER, NgayLap) = %d AND YEAR(NgayLap) = %d", quarter, year);
        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                soLuong = rs.getInt("SoLuong");
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
        return soLuong;
    }

    public static int countBillByYear(int year) {
        Connection conn = ConnectDB.getConnection();
        Statement stmt = null;
        int soLuong = 0;
        String sql = String.format("SELECT COUNT(MaHoaDon) AS SoLuong "
                + "FROM HoaDonThanhToan WHERE YEAR(NgayLap) = %d", year);
        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                soLuong = rs.getInt("SoLuong");
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
        return soLuong;
    }

    public static long calcTotalMoneyOfBill() {
        Connection conn = ConnectDB.getConnection();
        Statement stmt = null;
        long tongTien = 0;
        String sql = "SELECT SUM(TongTien) AS Tien FROM HoaDonThanhToan";
        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                tongTien = rs.getInt("Tien");
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
        return tongTien;
    }

    public static long calcTotalMoneyOfBillByDay(LocalDate date) {
        Connection conn = ConnectDB.getConnection();
        Statement stmt = null;
        long tongTien = 0;
        String sql = String.format("SELECT SUM(TongTien) AS Tien FROM HoaDonThanhToan "
                + "WHERE DAY(NgayLap) = %d AND MONTH(NgayLap) = %d AND YEAR(NgayLap) = %d", date.getDayOfMonth(), date.getMonthValue(), date.getYear());
        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                tongTien = rs.getInt("Tien");
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
        return tongTien;
    }

    public static long calcTotalMoneyOfBillByMonth(LocalDate date) {
        Connection conn = ConnectDB.getConnection();
        Statement stmt = null;
        long tongTien = 0;
        String sql = String.format("SELECT SUM(TongTien) AS Tien FROM HoaDonThanhToan "
                + "WHERE MONTH(NgayLap) = %d AND YEAR(NgayLap) = %d", date.getMonthValue(), date.getYear());
        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                tongTien = rs.getInt("Tien");
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
        return tongTien;
    }

    public static long calcTotalMoneyOfBillByQuarterInYear(LocalDate date) {
        Connection conn = ConnectDB.getConnection();
        Statement stmt = null;
        long tongTien = 0;
        int quarter = date.get(IsoFields.QUARTER_OF_YEAR);
        int year = date.getYear();
        String sql = String.format("SELECT SUM(TongTien) AS Tien FROM HoaDonThanhToan "
                + "WHERE DATEPART(QUARTER, NgayLap) = %d AND YEAR(NgayLap) = %d", quarter, year);
        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                tongTien = rs.getInt("Tien");
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
        return tongTien;
    }

    public static long calcTotalMoneyOfBillByYear(int year) {
        Connection conn = ConnectDB.getConnection();
        Statement stmt = null;
        long tongTien = 0;
        String sql = String.format("SELECT SUM(TongTien) AS Tien "
                + "FROM HoaDonThanhToan WHERE YEAR(NgayLap) = %d", year);
        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                tongTien = rs.getInt("Tien");
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
        return tongTien;
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

    public static boolean updateHoaDonThanhToan(HoaDonThanhToan hd, long tongTien) {
        Connection conn = ConnectDB.getConnection();
        PreparedStatement pstm = null;
        int n = 0;
        String sql = "UPDATE HoaDonThanhToan SET MaKhachHang = ?, MaNhanVien = ?, MaKhuyenMai = ?, NgayLap = ?, TongTien = ? WHERE MaHoaDon = ?";
        try {
            pstm = conn.prepareStatement(sql);
            pstm.setString(1, hd.getKhachHang().getMaKhachHang());
            pstm.setString(2, hd.getNhanVienLap().getMaNhanVien());
            System.out.println(hd.getKhuyenMai().getMaKhuyenMai());
            pstm.setString(3, hd.getKhuyenMai().getMaKhuyenMai());
            pstm.setTimestamp(4, Timestamp.valueOf(hd.getNgayLap()));
            pstm.setLong(5, tongTien);
            pstm.setString(6, hd.getMaHoaDon());
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

    public static HoaDonThanhToan getBillByCustomer(String customerID) {
        HoaDonThanhToan bill = null;
        Connection conn = ConnectDB.getConnection();

        try (PreparedStatement preparedStatement = conn.prepareStatement(
                "SELECT * FROM HoaDonThanhToan "
                + "JOIN KhachHang ON HoaDonThanhToan.MaKhachHang = KhachHang.MaKhachHang "
                + "WHERE HoaDonThanhToan.MaKhachHang = ? AND MaKhuyenMai IS NULL")) {

            preparedStatement.setString(1, customerID);

            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    String maHD = rs.getString("MaHoaDon");
                    String maKH = rs.getString("MaKhachHang");
                    String maNV = rs.getString("MaNhanVien");
                    String maKM = rs.getString("MaKhuyenMai");
                    LocalDateTime ngayLap = rs.getTimestamp("NgayLap").toLocalDateTime();
                    String tenKH = rs.getString("TenKhachHang");
                    String sdtKH = rs.getString("SoDienThoai");
                    int namSinhKH = rs.getInt("NamSinh");
                    boolean gioiTinhKH = rs.getBoolean("GioiTinh");

                    bill = new HoaDonThanhToan(maHD,
                            NhanVien.getNhanVienTheoMaNhanVien(maNV),
                            new KhachHang(maKH, tenKH, sdtKH, namSinhKH, gioiTinhKH),
                            new CT_KhuyenMai(maKM), ngayLap);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return bill;
    }

}
