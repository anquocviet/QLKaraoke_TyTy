/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.time.LocalDate;
import java.util.Objects;

/**
 *
 * @author vie
 */
public class PhieuDatPhong {

    private String maPhieuDat;
    private KhachHang khachHang;
    private Phong phong;
    private NhanVien nhanVienLap;
    private LocalDate thoiGianLap;
    private LocalDate thoiGianNhan;
    private String ghiChu;

    public PhieuDatPhong() {
    }

    public PhieuDatPhong(String maPhieuDat, KhachHang khachHang, Phong phong, NhanVien nhanVienLap, LocalDate thoiGianLap, LocalDate thoiGianNhan, String ghiChu) {
        this.maPhieuDat = maPhieuDat;
        this.khachHang = khachHang;
        this.phong = phong;
        this.nhanVienLap = nhanVienLap;
        this.thoiGianLap = thoiGianLap;
        this.thoiGianNhan = thoiGianNhan;
        this.ghiChu = ghiChu;
    }

    public PhieuDatPhong(String maPhieuDat) {
        this.maPhieuDat = maPhieuDat;
    }

    public String getMaPhieuDat() {
        return maPhieuDat;
    }

    public void setMaPhieuDat(String maPhieuDat) {
        this.maPhieuDat = maPhieuDat;
    }

    public KhachHang getKhachHang() {
        return khachHang;
    }

    public void setKhachHang(KhachHang khachHang) {
        this.khachHang = khachHang;
    }

    public Phong getPhong() {
        return phong;
    }

    public void setPhong(Phong phong) {
        this.phong = phong;
    }

    public NhanVien getNhanVienLap() {
        return nhanVienLap;
    }

    public void setNhanVienLap(NhanVien nhanVienLap) {
        this.nhanVienLap = nhanVienLap;
    }

    public LocalDate getThoiGianLap() {
        return thoiGianLap;
    }

    public void setThoiGianLap(LocalDate thoiGianLap) {
        this.thoiGianLap = thoiGianLap;
    }

    public LocalDate getThoiGianNhan() {
        return thoiGianNhan;
    }

    public void setThoiGianNhan(LocalDate thoiGianNhan) {
        this.thoiGianNhan = thoiGianNhan;
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

}
