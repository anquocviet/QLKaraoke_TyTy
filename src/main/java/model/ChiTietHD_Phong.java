/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;


import java.time.Duration;
import java.time.LocalDateTime;
import javax.swing.JOptionPane;

/**
 *
 * @author vie
 */
public class ChiTietHD_Phong {

    private HoaDonThanhToan hoaDon;
    private Phong phong;
    private LocalDateTime gioVao;
    private LocalDateTime gioRa;

    public ChiTietHD_Phong() {
    }

    public ChiTietHD_Phong(HoaDonThanhToan hoaDon, Phong phong, LocalDateTime gioVao, LocalDateTime gioRa) throws Exception {
        setHoaDon(hoaDon);
        setPhong(phong);
        setGioVao(gioVao);
        setGioRa(gioRa);
    }

    public HoaDonThanhToan getHoaDon() {
        return hoaDon;
    }

    public void setHoaDon(HoaDonThanhToan hoaDon) throws Exception {
        if (hoaDon != null)
            this.hoaDon = hoaDon;
        else
            throw new Exception("Hóa đơn không được rỗng");
    }

    public Phong getPhong() {
        return phong;
    }

    public void setPhong(Phong phong) throws Exception {
        if (phong != null){
            this.phong = phong;
        } else {
            throw new Exception("Phòng không được rỗng");
        }
    }

    public LocalDateTime getGioVao() {
        return gioVao;
    }

    public void setGioVao(LocalDateTime gioVao) throws Exception {
        if (gioVao != null){
            this.gioVao = gioVao;
        } else {
            throw new Exception("Giờ vào không được rỗng");
        }
            
    }

    public LocalDateTime getGioRa() {
        return gioRa;
    }

    public void setGioRa(LocalDateTime gioRa) throws Exception {
        if (gioRa.isAfter(gioVao)){
            this.gioRa = gioRa;
        } else {
            throw new Exception("Giờ ra phải lớn hơn giờ vào");
        }
    }

    public long tinhThanhTien() {
        return (long) (tinhTongGioSuDung() * phong.getGiaPhong());
    }

    public float tinhTongGioSuDung() {
        float hour = 0;
        int minute = 0;
        
        LocalDateTime tmp;
        Duration duration = Duration.ofHours(gioVao.getHour()).plusMinutes(gioVao.getMinute());
        tmp = gioRa.minus(duration);
        
        hour = tmp.getHour();
        minute = tmp.getMinute();
        
        if (minute < 10){
            minute = 0;
        } else if (minute < 40){
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

}
