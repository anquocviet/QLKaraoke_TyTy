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
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author vie
 */
public class KhachHang {

	private String maKhachHang;
	private String tenKhachHang;
	private String soDienThoai;
	private int namSinh;
	private boolean gioiTinh;       // true: Nam - false: Nữ

	public KhachHang() {
	}

	public KhachHang(String maKhachHang, String tenKhachHang) {
		this.maKhachHang = maKhachHang;
		this.tenKhachHang = tenKhachHang;
		this.soDienThoai = soDienThoai;
		this.namSinh = namSinh;
		this.gioiTinh = gioiTinh;
	}

	public KhachHang(String maKhachHang) {
		this.maKhachHang = maKhachHang;
	}

	public String getMaKhachHang() {
		return maKhachHang;
	}

	public void setMaKhachHang(String maKhachHang) {
		this.maKhachHang = maKhachHang;
	}

	public String getTenKhachHang() {
		return tenKhachHang;
	}

	public void setTenKhachHang(String tenKhachHang) throws IllegalArgumentException {
		if (tenKhachHang == null || tenKhachHang.isEmpty()) {
			throw new IllegalArgumentException("Tên khách hàng không được rỗng");
		} else {
			this.tenKhachHang = tenKhachHang;
		}
	}

	public String getSoDienThoai() {
		return soDienThoai;
	}

	public void setSoDienThoai(String soDienThoai) throws IllegalArgumentException {
		if (soDienThoai == null || soDienThoai.isEmpty()) {
			throw new IllegalArgumentException("Số điện thoại không được rỗng");
		} else {
			this.soDienThoai = soDienThoai;
		}
	}

	public int getNamSinh() {
		return namSinh;
	}

	public void setNamSinh(int namSinh) throws IllegalArgumentException {
		if (namSinh < 1000) {
			throw new IllegalArgumentException("Năm sinh không được rỗng");
		} else {
			this.namSinh = namSinh;
		}
	}

	public boolean isGioiTinh() {
		return gioiTinh;
	}

	public void setGioiTinh(boolean gioiTinh) {

		this.gioiTinh = gioiTinh;
	}

	@Override
	public int hashCode() {
		int hash = 3;
		hash = 79 * hash + Objects.hashCode(this.maKhachHang);
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
		final KhachHang other = (KhachHang) obj;
		return Objects.equals(this.maKhachHang, other.maKhachHang);
	}

//<<<<<<< HEAD

//    Thuc used for thuePhong
    public static KhachHang getKhachHangTheoSoDienThoai(String soDienThoai) {
        ObservableList<KhachHang> dsKhachHang = FXCollections.observableArrayList();
        Connection conn = ConnectDB.getInstance().getConnection();
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            String sql = String.format("SELECT * FROM KhachHang WHERE SoDienThoai = '%s'", soDienThoai);
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String maKhachhang = rs.getString("MaKhachHang");
                String tenKhachhang = rs.getString("TenKhachHang");
                int namSinh = rs.getInt("NamSinh");
                boolean gioiTinh = rs.getBoolean("GioiTinh");
                KhachHang kh = new KhachHang(maKhachhang, tenKhachhang);
                return kh;
            }
        } catch (SQLException ex) {
            Logger.getLogger(GD_QLKhachHangController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                assert stmt != null;
                stmt.close();
            } catch (SQLException ex) {
                Logger.getLogger(GD_QLKhachHangController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }
	
    //    Get data from DB
    public static ObservableList<KhachHang> getAllKhachHang() {
        ObservableList<KhachHang> dsKhachHang = FXCollections.observableArrayList();
        Connection conn = ConnectDB.getInstance().getConnection();
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            String sql = "SELECT * FROM KhachHang";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String maKhachhang = rs.getString("MaKhachHang");
                String tenKhachhang = rs.getString("TenKhachHang");
                String soDienThoai = rs.getString("SoDienThoai");
                int namSinh = rs.getInt("NamSinh");
                boolean gioiTinh = rs.getBoolean("GioiTinh");
                dsKhachHang.add(new KhachHang(maKhachhang, tenKhachhang));
            }
        } catch (SQLException ex) {
            Logger.getLogger(GD_QLKhachHangController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                assert stmt != null;
                stmt.close();
            } catch (SQLException ex) {
                Logger.getLogger(GD_QLKhachHangController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return dsKhachHang;
    }

	@Override
	public String toString() {
		return "KhachHang{" + "maKhachHang=" + maKhachHang + ", tenKhachHang=" + tenKhachHang + ", soDienThoai=" + soDienThoai + ", namSinh=" + namSinh + ", gioiTinh=" + gioiTinh + '}';
	}

	//    Get data from DB

	public static KhachHang getKhachHangTheoMaKhachHang(String maKH) {
		Connection conn = ConnectDB.getInstance().getConnection();
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
			String sql = String.format("SELECT * FROM KhachHang WHERE MaKhachHang = '%s'", maKH);
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				String maKhachhang = rs.getString("MaKhachHang");
				String tenKhachhang = rs.getString("TenKhachHang");
				String soDienThoai = rs.getString("SoDienThoai");
				int namSinh = rs.getInt("NamSinh");
				boolean gioiTinh = rs.getBoolean("GioiTinh");
				KhachHang kh = new KhachHang(maKhachhang, tenKhachhang);
				return kh;
			}
		} catch (SQLException ex) {
			Logger.getLogger(GD_QLKhachHangController.class.getName()).log(Level.SEVERE, null, ex);
		} finally {
			try {
                assert stmt != null;
                stmt.close();
			} catch (SQLException ex) {
				Logger.getLogger(GD_QLKhachHangController.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
		return null;
	}

//	

	public static int demSoLuongKhachHang() {
		int soLuongKH = 0;
		Connection conn = ConnectDB.getInstance().getConnection();
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
			String sql = "SELECT COUNT(MaKhachHang) AS SoLuongKH FROM KhachHang";
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				soLuongKH = rs.getInt("SoLuongKH");
			}
		} catch (SQLException ex) {
			Logger.getLogger(GD_QLKhachHangController.class.getName()).log(Level.SEVERE, null, ex);
		} finally {
			try {
                assert stmt != null;
                stmt.close();
			} catch (SQLException ex) {
				Logger.getLogger(GD_QLKhachHangController.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
		return soLuongKH;
	}

	public static boolean themKhachHang(KhachHang kh) {
		ConnectDB.getInstance();
		Connection conn = ConnectDB.getInstance().getConnection();
		PreparedStatement pstm = null;
		int n = 0;
		String sql = "INSERT INTO KhachHang ( MaKhachHang, TenKhachHang, SoDienThoai, NamSinh, GioiTinh ) VALUES(?,?,?,?,?)";
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, kh.getMaKhachHang());
			pstm.setString(2, kh.getTenKhachHang());
			pstm.setString(3, kh.getSoDienThoai());
			pstm.setInt(4, kh.getNamSinh());
			pstm.setBoolean(5, kh.isGioiTinh());
			n = pstm.executeUpdate();
		} catch (SQLException ex) {
			Logger.getLogger(GD_QLKhachHangController.class.getName()).log(Level.SEVERE, null, ex);
		} finally {
			try {
                assert pstm != null;
                pstm.close();
			} catch (SQLException ex) {
				Logger.getLogger(GD_QLKhachHangController.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
		return n > 0;
	}

	public static boolean suaKhachHang(KhachHang kh) {
		ConnectDB.getInstance();
		Connection conn = ConnectDB.getInstance().getConnection();
		PreparedStatement pstm = null;
		int n = 0;
		String sql = "UPDATE KhachHang SET TenKhachHang = ?, SoDienThoai = ?, NamSinh = ?, GioiTinh = ? WHERE MaKhachHang = ?";
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, kh.getTenKhachHang());
			pstm.setString(2, kh.getSoDienThoai());
			pstm.setInt(3, kh.getNamSinh());
			pstm.setBoolean(4, kh.isGioiTinh());
			pstm.setString(5, kh.getMaKhachHang());
			n = pstm.executeUpdate();
		} catch (SQLException ex) {
			Logger.getLogger(GD_QLKhachHangController.class.getName()).log(Level.SEVERE, null, ex);
		} finally {
			try {
                assert pstm != null;
                pstm.close();
			} catch (SQLException ex) {
				Logger.getLogger(GD_QLKhachHangController.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
		return n > 0;
	}

}
