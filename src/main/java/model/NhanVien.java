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
public class NhanVien {

    private String maNhanVien;
    private String cccd;
    private String hoTen;
    private String diaChi;
    private LocalDate ngaySinh;
    private String soDienThoai;
    private String chucVu;
    private boolean gioiTinh;       // true: Nam - false: Nữ
    private String anhDaiDien;
    private String trangThai;

    public NhanVien() {
    }

    public NhanVien(String maNhanVien, String cccd, String hoTen, String diaChi, LocalDate ngaySinh, String soDienThoai, String chucVu, boolean gioiTinh, String anhDaiDien, String trangThai) {
        this.maNhanVien = maNhanVien;
        this.cccd = cccd;
        this.hoTen = hoTen;
        this.diaChi = diaChi;
        this.ngaySinh = ngaySinh;
        this.soDienThoai = soDienThoai;
        this.chucVu = chucVu;
        this.gioiTinh = gioiTinh;
        this.anhDaiDien = anhDaiDien;
        this.trangThai = trangThai;
    }

    public NhanVien(String maNhanVien) {
        this.maNhanVien = maNhanVien;
    }

    public String getMaNhanVien() {
        return maNhanVien;
    }

    public void setMaNhanVien(String maNhanVien) {
        this.maNhanVien = maNhanVien;
    }

    public String getCccd() {
        return cccd;
    }

    public void setCccd(String cccd) {
        this.cccd = cccd;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public LocalDate getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(LocalDate ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public String getChucVu() {
        return chucVu;
    }

    public void setChucVu(String chucVu) {
        this.chucVu = chucVu;
    }

    public boolean isGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(boolean gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getAnhDaiDien() {
        return anhDaiDien;
    }

    public void setAnhDaiDien(String anhDaiDien) {
        this.anhDaiDien = anhDaiDien;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.maNhanVien);
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
        final NhanVien other = (NhanVien) obj;
        return Objects.equals(this.maNhanVien, other.maNhanVien);
    }

    @Override
    public String toString() {
        return "NhanVien{" + "maNhanVien=" + maNhanVien + ", cccd=" + cccd + ", hoTen=" + hoTen + ", diaChi=" + diaChi + ", ngaySinh=" + ngaySinh + ", soDienThoai=" + soDienThoai + ", chucVu=" + chucVu + ", gioiTinh=" + gioiTinh + ", anhDaiDien=" + anhDaiDien + ", trangThai=" + trangThai + '}';
    }

}
