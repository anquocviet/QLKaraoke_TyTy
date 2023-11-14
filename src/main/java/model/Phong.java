/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import connect.ConnectDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author vie
 */
public class Phong {

	private String maPhong;
	private int sucChua;
	private int tinhTrang;      // 0: trống - 1: đã thuê - 2: được đặt
	private int loaiPhong;      // 1: VIP - 0: thường
	private long giaPhong;

	public Phong() {
	}

	public Phong(String maPhong, int sucChua, int tinhTrang, int loaiPhong, long giaPhong) throws Exception {
		setMaPhong(maPhong);
		setSucChua(sucChua);
		setTinhTrang(tinhTrang);
		setLoaiPhong(loaiPhong);
		setGiaPhong(giaPhong);
	}

	public Phong(String maPhong) throws Exception {
		setMaPhong(maPhong);
	}

	public String getMaPhong() {
		return maPhong;
	}

	//Ràng buộc mã phòng (khóa chính)
	public void setMaPhong(String maPhong) throws Exception {
		if (maPhong == null) {
			throw new Exception("Mã phòng không được trống");
		} else {
			this.maPhong = maPhong;
		}
	}

	public int getSucChua() {
		return sucChua;
	}

	//Ràng buộc Sức chứa > 0
	public void setSucChua(int sucChua) throws Exception {
		if (sucChua <= 0) {
			throw new Exception("Sức chứa không được nhỏ hơn 0");
		} else {
			this.sucChua = sucChua;
		}
	}

	public int getTinhTrang() {
		return tinhTrang;
	}

	//Ràng buộc Tình trạng sử dụng phòng : 0, 1, 2
	public void setTinhTrang(int tinhTrang) throws Exception {
		if (tinhTrang != 0 && tinhTrang != 1 && tinhTrang != 2) {
			throw new Exception("Tình trạng phòng là: 0: Phòng trống, 1: Phòng đã thuê và 2: Phòng được đặt");
		} else {
			this.tinhTrang = tinhTrang;
		}
	}

	public int getLoaiPhong() {
		return loaiPhong;
	}

	//Ràng buộc Loại phòng thường or VIP: 0,1
	public void setLoaiPhong(int loaiPhong) throws Exception {
		if (loaiPhong != 0 && loaiPhong != 1) {
			throw new Exception("Loại phòng là: 0: Phòng thường, 1: Phòng VIP");
		} else {
			this.loaiPhong = loaiPhong;
		}
	}

	public long getGiaPhong() {
		return giaPhong;
	}

	//Ràng buộc Giá phòng > 0
	public void setGiaPhong(long giaPhong) throws Exception {
		if (giaPhong <= 0) {
			throw new Exception("Giá phòng không được nhỏ hơn 0");
		} else {
			this.giaPhong = giaPhong;
		}
	}

	@Override
	public int hashCode() {
		int hash = 3;
		hash = 89 * hash + Objects.hashCode(this.maPhong);
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
		final Phong other = (Phong) obj;
		return Objects.equals(this.maPhong, other.maPhong);
	}

	@Override
	public String toString() {
		return "Phong{" + "maPhong=" + maPhong + ", sucChua=" + sucChua + ", tinhTrang=" + tinhTrang + ", loaiPhong=" + loaiPhong + ", giaPhong=" + giaPhong + '}';
	}
	//   Get data from DB

	public static ObservableList<Phong> getAllPhong() {
		ObservableList<Phong> dsPhong = FXCollections.observableArrayList();
		Connection conn = ConnectDB.getConnection();
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
			String sql = "SELECT * FROM Phong";
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				String maPhong = rs.getString("Maphong");
				int loaiPhong = rs.getInt("LoaiPhong");
				int tinhTrang = rs.getInt("TinhTrang");
				long giaPhong = rs.getLong("GiaPhong");
				int sucChua = rs.getInt("SucChua");

				dsPhong.add(new Phong(maPhong, sucChua, tinhTrang, loaiPhong, giaPhong));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return dsPhong;
	}

	public static ObservableList<Phong> getListPhongByType(int type) {
		ObservableList<Phong> dsPhong = FXCollections.observableArrayList();
		Connection conn = ConnectDB.getConnection();
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
			String sql = String.format("SELECT * FROM Phong WHERE LoaiPhong = %d", type);
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				String maPhong = rs.getString("Maphong");
				int loaiPhong = rs.getInt("LoaiPhong");
				int tinhTrang = rs.getInt("TinhTrang");
				long giaPhong = rs.getLong("GiaPhong");
				int sucChua = rs.getInt("SucChua");

				dsPhong.add(new Phong(maPhong, sucChua, tinhTrang, loaiPhong, giaPhong));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return dsPhong;
	}

        // 0 phòng trống 1 đang sử dụng 2 phòng chờ
	public static ObservableList<Phong> getListPhongByStatus(int status) {
		ObservableList<Phong> dsPhong = FXCollections.observableArrayList();
		Connection conn = ConnectDB.getConnection();
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
			String sql = String.format("SELECT * FROM Phong WHERE TinhTrang = %d", status);
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				String maPhong = rs.getString("Maphong");
				int loaiPhong = rs.getInt("LoaiPhong");
				int tinhTrang = rs.getInt("TinhTrang");
				long giaPhong = rs.getLong("GiaPhong");
				int sucChua = rs.getInt("SucChua");

				dsPhong.add(new Phong(maPhong, sucChua, tinhTrang, loaiPhong, giaPhong));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return dsPhong;
	}

	public static ObservableList<Phong> getListPhongByID_Type_Status(String id, int[] arrType, int[] arrStatus) {
		ObservableList<Phong> dsPhong = FXCollections.observableArrayList();
		Connection conn = ConnectDB.getConnection();
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
			String sql = String.format("SELECT * FROM Phong WHERE MaPhong LIKE '%%" + id + "%%' "
					+ "AND LOAIPHONG IN (%d, %d) "
					+ "AND TINHTRANG IN (%d, %d, %d)", arrType[0], arrType[1], arrStatus[0], arrStatus[1], arrStatus[2]);
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				String maPhong = rs.getString("Maphong");
				int loaiPhong = rs.getInt("LoaiPhong");
				int tinhTrang = rs.getInt("TinhTrang");
				long giaPhong = rs.getLong("GiaPhong");
				int sucChua = rs.getInt("SucChua");

				dsPhong.add(new Phong(maPhong, sucChua, tinhTrang, loaiPhong, giaPhong));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return dsPhong;
	}

	public static ObservableList<Phong> getListPhongByID(String id) {
		ObservableList<Phong> dsPhong = FXCollections.observableArrayList();
		Connection conn = ConnectDB.getConnection();
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
			String sql = "SELECT * FROM Phong WHERE MaPhong LIKE '%" + id + "%'";
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				String maPhong = rs.getString("Maphong");
				int loaiPhong = rs.getInt("LoaiPhong");
				int tinhTrang = rs.getInt("TinhTrang");
				long giaPhong = rs.getLong("GiaPhong");
				int sucChua = rs.getInt("SucChua");

				dsPhong.add(new Phong(maPhong, sucChua, tinhTrang, loaiPhong, giaPhong));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return dsPhong;
	}

	public static boolean updateStatusRoom(String roomID, int status) {
		ConnectDB.getInstance();
		Connection conn = ConnectDB.getConnection();
		PreparedStatement pstm = null;
		int n = 0;
		String sql = "UPDATE Phong SET TinhTrang = ? WHERE MaPhong = ?";
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, status);
			pstm.setString(2, roomID);
			n = pstm.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				pstm.close();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
		return n > 0;
	}

}
