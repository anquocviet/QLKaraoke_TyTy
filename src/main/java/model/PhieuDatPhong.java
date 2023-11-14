/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import connect.ConnectDB;
import controllers.GD_QLKhachHangController;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author vie
 */
public class PhieuDatPhong {

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
				LocalDateTime thoiGianLap = rs.getDate("ThoiGianLap").toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
				LocalDateTime thoiGianNhan = rs.getDate("ThoiGianNhan").toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
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
			pstm.setDate(5, Date.valueOf(phieu.getThoiGianLap().toLocalDate()));
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

}
