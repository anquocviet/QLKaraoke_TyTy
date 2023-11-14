/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import connect.ConnectDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDateTime;

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

}
