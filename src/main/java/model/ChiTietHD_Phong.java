/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import connect.ConnectDB;
import controllers.GD_ChuyenPhongController;
import controllers.GD_QLKhachHangController;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author vie
 */
public class ChiTietHD_Phong {

    private HoaDonThanhToan hoaDon;
    private Phong phong;
    private LocalDateTime gioVao;
    private LocalDateTime gioRa;
    private long thanhTien;

    public ChiTietHD_Phong() {
    }

    public ChiTietHD_Phong(HoaDonThanhToan hoaDon, Phong phong, LocalDateTime gioVao, LocalDateTime gioRa) throws Exception {
        setHoaDon(hoaDon);
        setPhong(phong);
        setGioVao(gioVao);
        setGioRa(gioRa);
        tinhTongGioSuDung();
        tinhThanhTien();

    }

    public HoaDonThanhToan getHoaDon() {
        return hoaDon;
    }

    public void setHoaDon(HoaDonThanhToan hoaDon) throws Exception {
        if (hoaDon != null) {
            this.hoaDon = hoaDon;
        } else {
            throw new Exception("Hóa đơn không được rỗng");
        }
    }

    public Phong getPhong() {
        return phong;
    }

    public void setPhong(Phong phong) throws Exception {
        if (phong != null) {
            this.phong = phong;
        } else {
            throw new Exception("Phòng không được rỗng");
        }
    }

    public LocalDateTime getGioVao() {
        return gioVao;
    }

    public void setGioVao(LocalDateTime gioVao) throws Exception {
        if (gioVao != null) {
            this.gioVao = gioVao;
        } else {
            throw new Exception("Giờ vào không được rỗng");
        }

    }

    public LocalDateTime getGioRa() {
        return gioRa;
    }

    public void setGioRa(LocalDateTime gioRa) throws Exception {
        if (gioRa.isAfter(gioVao)) {
            this.gioRa = gioRa;
        } else {
            throw new Exception("Giờ ra phải lớn hơn giờ vào");
        }
    }

	public long getThanhTien() {
		return thanhTien;
	}

    public long tinhThanhTien() {
        thanhTien = (long) (tinhTongGioSuDung() * phong.getGiaPhong());
        return thanhTien;
    }

    public float tinhTongGioSuDung() {
        float hour = 0;
        int minute = 0;

        LocalDateTime tmp;
        Duration duration = Duration.ofHours(gioVao.getHour()).plusMinutes(gioVao.getMinute());
        tmp = gioRa.minus(duration);

        hour = tmp.getHour();
        minute = tmp.getMinute();

        if (minute < 10) {
            minute = 0;
        } else if (minute < 40) {
            hour += 0.5;
        } else {
            hour += 1;
        }
        return hour;
    }

    @Override
    public String toString() {
        return "ChiTietHD_Phong{" + "hoaDon=" + hoaDon + ", phong=" + phong + ", gioVao=" + gioVao + ", gioRa=" + gioRa + '}';
    }
	
	public static ObservableList<ChiTietHD_Phong> getCT_PhongTheoMaHD(String maHD) {
        ObservableList<ChiTietHD_Phong> dsChiTietHD_Phong = FXCollections.observableArrayList();
        Connection conn = ConnectDB.getConnection();
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            String sql = String.format("SELECT * FROM ChiTietHD_Phong WHERE ChiTietHD_Phong.MaHoaDon = '%s'", maHD);
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
				String maPhong = rs.getString("MaPhong");
//				LocalDateTime gioVao = rs.getDate("GioVao").toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
//				LocalDateTime gioRa = rs.getDate("GioRa").toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
				Phong p = Phong.getPhongTheoMaPhong(maPhong);
                dsChiTietHD_Phong.add(new ChiTietHD_Phong(new HoaDonThanhToan(maHD), p, LocalDateTime.now(), LocalDateTime.now().plusSeconds(1)));
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
        return dsChiTietHD_Phong;
    }

    public static boolean themChiTietHoaDon(ChiTietHD_Phong ctPhong) {
        Connection conn = ConnectDB.getConnection();
        PreparedStatement preparedStatement = null;

        try {
            String sql = "INSERT INTO ChiTietHD_Phong (MaHoaDon, MaPhong, GioVao, GioRa, TongGioSuDung, ThanhTien) VALUES (?, ?, ?, ?, ?, ?)";
            preparedStatement = conn.prepareStatement(sql);

            preparedStatement.setString(1, ctPhong.getHoaDon().getMaHoaDon());
            preparedStatement.setString(2, ctPhong.getPhong().getMaPhong());
            preparedStatement.setTimestamp(3, java.sql.Timestamp.valueOf(ctPhong.getGioVao()));
            preparedStatement.setTimestamp(4, java.sql.Timestamp.valueOf(ctPhong.getGioRa()));
            preparedStatement.setFloat(5, ctPhong.tinhTongGioSuDung());
            preparedStatement.setLong(6, ctPhong.tinhThanhTien());

            int rowsAffected = preparedStatement.executeUpdate();

            return rowsAffected > 0; // Trả về true nếu có ít nhất một dòng được ảnh hưởng (thêm mới thành công).
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

    public static ChiTietHD_Phong getChiTietHD_PhongTheoMaPhong(String maPhong) throws Exception {
        Connection conn = ConnectDB.getConnection();
        ObservableList<Phong> tmp = Phong.getListPhongByID(maPhong);
        Phong phong = tmp.get(0);

        Statement stmt = null;
        ChiTietHD_Phong hdP = null;
        try {
            stmt = conn.createStatement();
            String sql = String.format("SELECT * FROM ChiTietHD_Phong WHERE MaPhong = '%s'", maPhong);
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String maHoaDon = rs.getString("MaHoaDon");
                HoaDonThanhToan hoaDon = HoaDonThanhToan.getBillByID(maHoaDon);
                java.sql.Timestamp timestamp = rs.getTimestamp("GioVao");
                LocalDateTime gioVao = timestamp.toLocalDateTime();
                timestamp = rs.getTimestamp("GioRa");
                LocalDateTime gioRa = timestamp.toLocalDateTime();
                hdP = new ChiTietHD_Phong(hoaDon, phong, gioVao, gioRa);
            }
        } catch (SQLException ex) {
            Logger.getLogger(GD_ChuyenPhongController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                stmt.close();
            } catch (SQLException ex) {
                Logger.getLogger(GD_ChuyenPhongController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return hdP;
    }

    public static boolean suaChiTietHD_Phong(ChiTietHD_Phong hdP) {
        ConnectDB.getInstance();
        Connection conn = ConnectDB.getInstance().getConnection();
        PreparedStatement pstm = null;
        int n = 0;
        String sql = "UPDATE ChiTietHD_Phong "
                + "SET GioVao = ?, GioRa = ? "
                + "WHERE MaHoaDon = ? AND MaPhong = ?";
        try {
            pstm = conn.prepareStatement(sql);

            pstm.setTimestamp(1, Timestamp.valueOf(hdP.getGioVao()));
            pstm.setTimestamp(2, Timestamp.valueOf(hdP.getGioRa()));
            pstm.setString(3, hdP.getHoaDon().getMaHoaDon());
            pstm.setString(4, hdP.getPhong().getMaPhong());
            n = pstm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(GD_ChuyenPhongController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {

                assert pstm != null;
                pstm.close();
            } catch (SQLException ex) {
                Logger.getLogger(GD_ChuyenPhongController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return n > 0;
    }

    public static boolean themChiTietHD_Phong(ChiTietHD_Phong hdP) {
        ConnectDB.getInstance();
        Connection conn = ConnectDB.getInstance().getConnection();
        PreparedStatement pstm = null;
        int n = 0;

        String sql = "MERGE INTO ChiTietHD_Phong AS target " +
                 "USING (VALUES (?, ?, ?, ?)) AS source (MaHoaDon, MaPhong, GioVao, GioRa) " +
                 "ON target.MaHoaDon = source.MaHoaDon AND target.MaPhong = source.MaPhong " +
                 "WHEN MATCHED THEN " +
                 "UPDATE SET target.GioVao = source.GioVao, target.GioRa = source.GioRa " +
                 "WHEN NOT MATCHED THEN " +
                 "INSERT (MaHoaDon, MaPhong, GioVao, GioRa) VALUES (source.MaHoaDon, source.MaPhong, source.GioVao, source.GioRa);";

        try {
            pstm = conn.prepareStatement(sql);
            pstm.setString(1, hdP.getHoaDon().getMaHoaDon());
            pstm.setString(2, hdP.getPhong().getMaPhong());
            pstm.setTimestamp(3, Timestamp.valueOf(hdP.getGioVao()));
            pstm.setTimestamp(4, Timestamp.valueOf(hdP.getGioRa()));

            n = pstm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(GD_ChuyenPhongController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (pstm != null) {
                    pstm.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(GD_ChuyenPhongController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return n > 0;
    }
	
	public static boolean updateCTHD_Phong(ChiTietHD_Phong ct) {
		ConnectDB.getInstance();
		Connection conn = ConnectDB.getInstance().getConnection();
		PreparedStatement pstm = null;
		int n = 0;
		String sql = "UPDATE ChiTietHD_Phong SET GioRa = ?, TongGioSuDung = ?, ThanhTien = ? WHERE MaHoaDon = ? AND MaPhong = ?";
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setDate(1, Date.valueOf(ct.gioRa.toLocalDate()));
			pstm.setFloat(2, Duration.between(ct.getGioVao(), ct.getGioRa()).toMillis() / 1000);
			pstm.setLong(3, ct.thanhTien);
			pstm.setString(4, ct.getHoaDon().getMaHoaDon());
			pstm.setString(5, ct.getPhong().getMaPhong());
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
