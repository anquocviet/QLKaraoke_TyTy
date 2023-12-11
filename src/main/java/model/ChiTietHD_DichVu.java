/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import connect.ConnectDB;
import controllers.GD_QLDichVuController;
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
import main.App;

/**
 *
 * @author fil
 */
public final class ChiTietHD_DichVu {
	private HoaDonThanhToan hoaDon;
	private DichVu dichVu;
	private int soLuong;
	private long thanhTien;

	public ChiTietHD_DichVu() {
	}

	public ChiTietHD_DichVu(HoaDonThanhToan hoaDon, DichVu dichVu, int soLuong) {
		setHoaDon(hoaDon);
		setDichVu(dichVu);
		setSoLuong(soLuong);
		this.thanhTien = soLuong * dichVu.getDonGia();
	}

	public ChiTietHD_DichVu(HoaDonThanhToan hoaDon) {
		this.hoaDon = hoaDon;
	}

	public HoaDonThanhToan getHoaDon() {
		return hoaDon;
	}

	public void setHoaDon(HoaDonThanhToan hoaDon) throws IllegalArgumentException {
		if (hoaDon == null) {
			throw new IllegalArgumentException("Hóa đơn không được rỗng");
		} else {
			this.hoaDon = hoaDon;
		}
	}

	public DichVu getDichVu() {
		return dichVu;
	}

	public void setDichVu(DichVu dichVu) throws IllegalArgumentException {
		if (dichVu == null) {
			throw new IllegalArgumentException("Dịch vụ không được rỗng");
		} else {
			this.dichVu = dichVu;
		}
	}

	public int getSoLuong() {
		return soLuong;
	}

	public void setSoLuong(int soLuong) throws IllegalArgumentException {
		if (soLuong <= 0) {
			throw new IllegalArgumentException("Số lượng không được rỗng và phải lớn hơn 0");
		} else {
			this.soLuong = soLuong;
		}
	}

	public void tinhThanhTien() {
		thanhTien = soLuong * dichVu.getDonGia();
	}

	public long getThanhTien() {
		tinhThanhTien();
		return thanhTien;
	}

	@Override
	public String toString() {
		return "ChiTietHD_DichVu{" + "hoaDon=" + hoaDon + ", dichVu=" + dichVu + ", soLuong=" + soLuong + '}';
	}

	@Override
	public int hashCode() {
		int hash = 3;
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
		final ChiTietHD_DichVu other = (ChiTietHD_DichVu) obj;
		if (!Objects.equals(this.hoaDon, other.hoaDon)) {
			return false;
		}
		return Objects.equals(this.dichVu, other.dichVu);
	}

	// Get Data From DB
	public static ObservableList<ChiTietHD_DichVu> getCTDichVuTheoMaHD(String maHD) {
		ObservableList<ChiTietHD_DichVu> dsCTDichVu = FXCollections.observableArrayList();
		Connection conn = ConnectDB.getConnection();
		Statement stmt = null;

		try {
			stmt = conn.createStatement();
			String sql = String.format("SELECT * FROM ChiTietHD_DichVu JOIN DichVu ON ChiTietHD_DichVu.MaDichVu = DichVu.MaDichVu WHERE MaHoaDon = '%s'", maHD);
			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				// Lấy dữ liệu từ ResultSet
				String maHoaDon = rs.getString("MaHoaDon");
				String maDichVu = rs.getString("MaDichVu");
				int soLuong = rs.getInt("SoLuong");
				long thanhTien = rs.getLong("ThanhTien");

				// Lấy thêm dữ liệu từ bảng "DichVu"
				String tenDichVu = rs.getString("TenDichVu");
				String donViTinh = rs.getString("DonViTinh");
				long donGia = rs.getLong("DonGia");
				String anhMH = rs.getString("AnhMinhHoa");

				// Tạo đối tượng HoaDonThanhToan và DichVu
				HoaDonThanhToan hoaDon = new HoaDonThanhToan(maHoaDon);
				DichVu dichVu = new DichVu(maDichVu, tenDichVu, soLuong, donGia, donViTinh, anhMH);

				// Thêm vào danh sách
				dsCTDichVu.add(new ChiTietHD_DichVu(hoaDon, dichVu, soLuong));
			}
		} catch (SQLException ex) {
			Logger.getLogger(GD_QLKhachHangController.class.getName()).log(Level.SEVERE, null, ex);
		} catch (Exception ex) {
			Logger.getLogger(GD_QLDichVuController.class.getName()).log(Level.SEVERE, null, ex);
		} finally {
			try {
				if (stmt != null) {
					stmt.close();
				}
			} catch (SQLException ex) {
				Logger.getLogger(GD_QLKhachHangController.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
		return dsCTDichVu;
	}

	public static boolean addChiTietHD_DichVu(ChiTietHD_DichVu ct) {
		ConnectDB.getInstance();
		Connection conn = ConnectDB.getConnection();
		PreparedStatement pstm = null;
		int n = 0;
		String sql = "INSERT INTO ChiTietHD_DichVu ( MaHoaDon, MaDichVu, SoLuong, ThanhTien ) VALUES(?,?,?,?)";
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, ct.getHoaDon().getMaHoaDon());
			pstm.setString(2, ct.getDichVu().getMaDichVu());
			pstm.setInt(3, ct.getSoLuong());
			pstm.setLong(4, ct.getThanhTien());
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

	public static boolean updateChiTietHD_DichVu(ChiTietHD_DichVu ct) {
		ConnectDB.getInstance();
		Connection conn = ConnectDB.getConnection();
		PreparedStatement pstm = null;
		int n = 0;
		String sql = "UPDATE ChiTietHD_DichVu SET SoLuong = ?, ThanhTien = ? WHERE MaHoaDon = ? AND MaDichVu = ?";
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, ct.getSoLuong());
			pstm.setLong(2, ct.getThanhTien());
			pstm.setString(3, ct.getHoaDon().getMaHoaDon());
			pstm.setString(4, ct.getDichVu().getMaDichVu());
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

	public static boolean deleteChiTietHD_DichVu(ChiTietHD_DichVu ct) {
		ConnectDB.getInstance();
		Connection conn = ConnectDB.getConnection();
		PreparedStatement pstm = null;
		int n = 0;
		String sql = "DELETE ChiTietHD_DichVu WHERE MaHoaDon = ? AND MaDichVu = ?";
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, ct.getHoaDon().getMaHoaDon());
			pstm.setString(2, ct.getDichVu().getMaDichVu());
			
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
