/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author vie
 */
public class ChiTietHD_DichVu {

    private HoaDonThanhToan hoaDon;
    private DichVu dichVu;
    private int soLuong;

    public ChiTietHD_DichVu() {
    }

    public ChiTietHD_DichVu(HoaDonThanhToan hoaDon, DichVu dichVu, int soLuong) {
        this.hoaDon = hoaDon;
        this.dichVu = dichVu;
        this.soLuong = soLuong;
    }

    public ChiTietHD_DichVu(HoaDonThanhToan hoaDon) {
        this.hoaDon = hoaDon;
    }

    public HoaDonThanhToan getHoaDon() {
        return hoaDon;
    }

    public void setHoaDon(HoaDonThanhToan hoaDon) {
        this.hoaDon = hoaDon;
    }

    public DichVu getDichVu() {
        return dichVu;
    }

    public void setDichVu(DichVu dichVu) {
        this.dichVu = dichVu;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public void tinhThanhTien() {

    }

    @Override
    public String toString() {
        return "ChiTietHD_DichVu{" + "hoaDon=" + hoaDon + ", dichVu=" + dichVu + ", soLuong=" + soLuong + '}';
    }

}
