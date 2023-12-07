/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import connect.ConnectDB;
import controllers.GD_QLKhachHangController;
import java.sql.Connection;
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
public final class PhieuDatPhong {

    private String maPhieuDat;
    private KhachHang khachHang;
    private Phong phong;
    private NhanVien nhanVienLap;
    private LocalDateTime thoiGianLap;
    private LocalDateTime thoiGianNhan;
    private String ghiChu;

    public PhieuDatPhong() {
    }

    public PhieuDatPhong(String maPhieuDat, KhachHang khachHang, Phong phong, NhanVien nhanVienLap, LocalDateTime thoiGianLap, LocalDateTime thoiGianNhan, String ghiChu) throws Exception {
        setMaPhieuDat(maPhieuDat);
        setKhachHang(khachHang);
        setPhong(phong);
        setNhanVienLap(nhanVienLap);
        setThoiGianLap(thoiGianLap);
        setThoiGianNhan(thoiGianNhan);
        setGhiChu(ghiChu);
    }

    public PhieuDatPhong(String maPhieuDat) throws Exception {
        setMaPhieuDat(maPhieuDat);
    }

    public String getMaPhieuDat() {
        return maPhieuDat;
    }

    public void setMaPhieuDat(String maPhieuDat) throws Exception {
        if (maPhieuDat == null || maPhieuDat.trim().equals("")) {
            throw new Exception("Mã phiếu đặt không được trống");
        } else {
            this.maPhieuDat = maPhieuDat;
        }
    }

    public KhachHang getKhachHang() {
        return khachHang;
    }

    public void setKhachHang(KhachHang khachHang) throws Exception {
        if (khachHang == null) {
            throw new Exception("Khách hàng đặt phòng không được trống");
        } else {
            this.khachHang = khachHang;
        }
    }

    public Phong getPhong() {
        return phong;
    }

    public void setPhong(Phong phong) throws Exception {
        if (phong == null) {
            throw new Exception("Phòng không được trống");
        } else {
            this.phong = phong;
        }
    }

    public NhanVien getNhanVienLap() {
        return nhanVienLap;
    }

    public void setNhanVienLap(NhanVien nhanVienLap) throws Exception {
        if (nhanVienLap == null) {
            throw new Exception("Nhân viên lập phiếu không được trống");
        } else {
            this.nhanVienLap = nhanVienLap;
        }
    }

    public LocalDateTime getThoiGianLap() {
        return thoiGianLap;
    }

    public void setThoiGianLap(LocalDateTime thoiGianLap) throws Exception {
        if (thoiGianLap == null) {
            throw new Exception("Thời gian lập phiếu không được trống");
        } else {
            this.thoiGianLap = thoiGianLap;
        }
    }

    public LocalDateTime getThoiGianNhan() {
        return thoiGianNhan;
    }

    public void setThoiGianNhan(LocalDateTime thoiGianNhan) throws Exception {
        if (thoiGianNhan == null) {
            throw new Exception("Thời gian nhận phòng không được trống");
        } else {
            this.thoiGianNhan = thoiGianNhan;
        }
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.maPhieuDat);
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
        final PhieuDatPhong other = (PhieuDatPhong) obj;
        return Objects.equals(this.maPhieuDat, other.maPhieuDat);
    }

    @Override
    public String toString() {
        return "PhieuDatPhong{" + "maPhieuDat=" + maPhieuDat + ", khachHang=" + khachHang + ", phong=" + phong + ", nhanVienLap=" + nhanVienLap + ", thoiGianLap=" + thoiGianLap + ", thoiGianNhan=" + thoiGianNhan + ", ghiChu=" + ghiChu + '}';
    }

    public static ObservableList<PhieuDatPhong> getAllBookingTicket() throws Exception {
        ObservableList<PhieuDatPhong> dsPhieu = FXCollections.observableArrayList();
        Connection conn = ConnectDB.getConnection();
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            String sql = "SELECT * FROM PhieuDatPhong";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String maPhieu = rs.getString("MaPhieuDat");
                String maPhong = rs.getString("MaPhong");
                String maKH = rs.getString("MaKhachHang");
                String maNV = rs.getString("MaNhanVien");
                LocalDateTime thoiGianLap = rs.getTimestamp("ThoiGianLap").toLocalDateTime();
                LocalDateTime thoiGianNhan = rs.getTimestamp("ThoiGianNhan").toLocalDateTime();
                String ghiChu = rs.getString("GhiChu");
                dsPhieu.add(new PhieuDatPhong(maPhieu, new KhachHang(maKH), new Phong(maPhong), new NhanVien(maNV), thoiGianLap, thoiGianNhan, ghiChu));
            }
        } catch (SQLException ex) {
            Logger.getLogger(GD_QLKhachHangController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                stmt.close();
            } catch (SQLException ex) {
                Logger.getLogger(GD_QLKhachHangController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return dsPhieu;
    }

    public static ObservableList<PhieuDatPhong> getAllBookingTicketByIDKhachHang(String maKhachHang) throws Exception {
        ObservableList<PhieuDatPhong> dsPhieu = FXCollections.observableArrayList();
        Connection conn = ConnectDB.getConnection();
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            String sql = "SELECT * FROM PhieuDatPhong WHERE TinhTrang = 1 AND MaKhachHang = '" +maKhachHang+"' ";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String maPhieu = rs.getString("MaPhieuDat");
                String maPhong = rs.getString("MaPhong");
                String maKH = rs.getString("MaKhachHang");
                String maNV = rs.getString("MaNhanVien");
                LocalDateTime thoiGianLap = rs.getTimestamp("ThoiGianLap").toLocalDateTime();
                LocalDateTime thoiGianNhan = rs.getTimestamp("ThoiGianNhan").toLocalDateTime();
                String ghiChu = rs.getString("GhiChu");
                dsPhieu.add(new PhieuDatPhong(maPhieu, new KhachHang(maKH), new Phong(maPhong), new NhanVien(maNV), thoiGianLap, thoiGianNhan, ghiChu));
            }
        } catch (SQLException ex) {
            Logger.getLogger(GD_QLKhachHangController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                stmt.close();
            } catch (SQLException ex) {
                Logger.getLogger(GD_QLKhachHangController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return dsPhieu;
    }

    public static PhieuDatPhong getBookingTicketOfRoom(String roomID) throws Exception {
        Connection conn = ConnectDB.getConnection();
        Statement stmt = null;
        PhieuDatPhong phieu = null;
        try {
            stmt = conn.createStatement();
            String sql = "SELECT TOP 1 * FROM PhieuDatPhong WHERE MaPhong = '" + roomID + "' ORDER BY ThoiGianLap DESC";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String maPhieu = rs.getString("MaPhieuDat");
                String maPhong = rs.getString("MaPhong");
                String maKH = rs.getString("MaKhachHang");
                String maNV = rs.getString("MaNhanVien");
                LocalDateTime thoiGianLap = rs.getTimestamp("ThoiGianLap").toLocalDateTime();
                LocalDateTime thoiGianNhan = rs.getTimestamp("ThoiGianNhan").toLocalDateTime();
                String ghiChu = rs.getString("GhiChu");
                phieu = new PhieuDatPhong(maPhieu, new KhachHang(maKH), new Phong(maPhong), new NhanVien(maNV), thoiGianLap, thoiGianNhan, ghiChu);
            }
        } catch (SQLException ex) {
            Logger.getLogger(GD_QLKhachHangController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                stmt.close();
            } catch (SQLException ex) {
                Logger.getLogger(GD_QLKhachHangController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return phieu;
    }

    public static boolean themPhieDat(PhieuDatPhong phieu) {
        ConnectDB.getInstance();
        Connection conn = ConnectDB.getConnection();
        PreparedStatement pstm = null;
        int n = 0;
        String sql = "INSERT INTO PhieuDatPhong (MaPhieuDat, MaPhong, MaKhachHang, MaNhanVien, ThoiGianLap, ThoiGianNhan, GhiChu) VALUES(?, ?, ?, ?, ?, ?, ?)";
        try {
            pstm = conn.prepareStatement(sql);
            pstm.setString(1, phieu.getMaPhieuDat());
            pstm.setString(2, phieu.getPhong().getMaPhong());
            pstm.setString(3, phieu.getKhachHang().getMaKhachHang());
            pstm.setString(4, phieu.getNhanVienLap().getMaNhanVien());
            pstm.setTimestamp(5, Timestamp.valueOf(phieu.getThoiGianLap()));
            pstm.setTimestamp(6, Timestamp.valueOf(phieu.getThoiGianNhan()));
            pstm.setString(7, phieu.ghiChu);
            n = pstm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(GD_QLKhachHangController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                pstm.close();
            } catch (SQLException ex) {
                Logger.getLogger(GD_QLKhachHangController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return n > 0;
    }

    public static int countBookingTicketInDay() throws Exception {
        int quantity = 0;
        Connection conn = ConnectDB.getConnection();
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            String sql = "SELECT COUNT(MaPhieuDat) AS QTY FROM PhieuDatPhong";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                quantity = rs.getInt("QTY");
            }
        } catch (SQLException ex) {
            Logger.getLogger(GD_QLKhachHangController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                stmt.close();
            } catch (SQLException ex) {
                Logger.getLogger(GD_QLKhachHangController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return quantity;
    }

    public static KhachHang getCustomerInfoByRoomID(String roomID) {
        KhachHang customer = null;
        Connection conn = ConnectDB.getConnection();
        PreparedStatement preparedStatement = null;

        try {
            String sql = "SELECT KhachHang.* FROM PhieuDatPhong "
                    + "JOIN KhachHang ON PhieuDatPhong.MaKhachHang = KhachHang.MaKhachHang "
                    + "WHERE PhieuDatPhong.MaPhong = ?";
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, roomID);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                String maKH = rs.getString("MaKhachHang");
                String tenKH = rs.getString("TenKhachHang");
                String sdt = rs.getString("SoDienThoai");
                int namSinh = rs.getInt("NamSinh");
                boolean gioiTinh = rs.getBoolean("GioiTinh");

                customer = new KhachHang(maKH, tenKH, sdt, namSinh, gioiTinh);
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

        return customer;
    }

}
