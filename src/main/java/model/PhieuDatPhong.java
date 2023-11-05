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

    public PhieuDatPhong(String maPhieuDat, KhachHang khachHang, Phong phong, NhanVien nhanVienLap, LocalDate thoiGianLap, LocalDate thoiGianNhan, String ghiChu) throws Exception {
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

    public LocalDate getThoiGianLap() {
        return thoiGianLap;
    }

    public void setThoiGianLap(LocalDate thoiGianLap) throws Exception {
        if (thoiGianLap == null) {
            throw new Exception("Thời gian lập phiếu không được trống");
        } else {
            this.thoiGianLap = thoiGianLap;
        }
    }

    public LocalDate getThoiGianNhan() {
        return thoiGianNhan;
    }

    public void setThoiGianNhan(LocalDate thoiGianNhan) throws Exception {
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

}
