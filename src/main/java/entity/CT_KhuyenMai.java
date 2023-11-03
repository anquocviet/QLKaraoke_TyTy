/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.util.Date;
import java.util.Objects;

/**
 *
 * @author Admin
 */
public class CT_KhuyenMai {
    private String maKhuyenMai;
    private String tenKuyenMai;
    private Date ngayBatDau;
    private Date ngayKetThuc;
    private int luotSuDungConLai;
    private long chietKhau;

    public CT_KhuyenMai() {
    }

    public CT_KhuyenMai(String maKhuyenMai, String tenKuyenMai, Date ngayBatDau, Date ngayKetThuc, int luotSuDungConLai, long chietKhau) {
        this.maKhuyenMai = maKhuyenMai;
        this.tenKuyenMai = tenKuyenMai;
        this.ngayBatDau = ngayBatDau;
        this.ngayKetThuc = ngayKetThuc;
        this.luotSuDungConLai = luotSuDungConLai;
        this.chietKhau = chietKhau;
    }

    public String getMaKhuyenMai() {
        return maKhuyenMai;
    }

    public String getTenKuyenMai() {
        return tenKuyenMai;
    }

    public Date getNgayBatDau() {
        return ngayBatDau;
    }

    public Date getNgayKetThuc() {
        return ngayKetThuc;
    }

    public int getLuotSuDungConLai() {
        return luotSuDungConLai;
    }

    public long getChietKhau() {
        return chietKhau;
    }

    public void setMaKhuyenMai(String maKhuyenMai) {
        this.maKhuyenMai = maKhuyenMai;
    }

    public void setTenKuyenMai(String tenKuyenMai) {
        this.tenKuyenMai = tenKuyenMai;
    }

    public void setNgayBatDau(Date ngayBatDau) {
        this.ngayBatDau = ngayBatDau;
    }

    public void setNgayKetThuc(Date ngayKetThuc) {
        this.ngayKetThuc = ngayKetThuc;
    }

    public void setLuotSuDungConLai(int luotSuDungConLai) {
        this.luotSuDungConLai = luotSuDungConLai;
    }

    public void setChietKhau(long chietKhau) {
        this.chietKhau = chietKhau;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + Objects.hashCode(this.maKhuyenMai);
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
        final CT_KhuyenMai other = (CT_KhuyenMai) obj;
        return Objects.equals(this.maKhuyenMai, other.maKhuyenMai);
    }

    @Override
    public String toString() {
        return "CT_KhuyenMai{" + "maKhuyenMai=" + maKhuyenMai + ", tenKuyenMai=" + tenKuyenMai + ", ngayBatDau=" + ngayBatDau + ", ngayKetThuc=" + ngayKetThuc + ", luotSuDungConLai=" + luotSuDungConLai + ", chietKhau=" + chietKhau + '}';
    }
}
