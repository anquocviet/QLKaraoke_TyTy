/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.time.LocalDate;

/**
 *
 * @author vie
 */
public class ChiTietHD_Phong {

    private HoaDonThanhToan hoaDon;
    private Phong phong;
    private LocalDate gioVao;
    private LocalDate gioRa;

    public ChiTietHD_Phong() {
    }

    public ChiTietHD_Phong(HoaDonThanhToan hoaDon, Phong phong, LocalDate gioVao, LocalDate gioRa) {
        this.hoaDon = hoaDon;
        this.phong = phong;
        this.gioVao = gioVao;
        this.gioRa = gioRa;
    }

    public HoaDonThanhToan getHoaDon() {
        return hoaDon;
    }

    public void setHoaDon(HoaDonThanhToan hoaDon) {
        this.hoaDon = hoaDon;
    }

    public Phong getPhong() {
        return phong;
    }

    public void setPhong(Phong phong) {
        this.phong = phong;
    }

    public LocalDate getGioVao() {
        return gioVao;
    }

    public void setGioVao(LocalDate gioVao) {
        this.gioVao = gioVao;
    }

    public LocalDate getGioRa() {
        return gioRa;
    }

    public void setGioRa(LocalDate gioRa) {
        this.gioRa = gioRa;
    }

    public void tinhThanhTien() {

    }

    public void tinhTongGioSuDung() {

    }

    @Override
    public String toString() {
        return "ChiTietHD_Phong{" + "hoaDon=" + hoaDon + ", phong=" + phong + ", gioVao=" + gioVao + ", gioRa=" + gioRa + '}';
    }

}
