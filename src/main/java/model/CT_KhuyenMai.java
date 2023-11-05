/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;


import java.time.LocalDateTime;

import java.util.Objects;

/**
 *
 * @author fil
 */
public class CT_KhuyenMai {

    private String maKhuyenMai;
    private String tenKhuyenMai;
    private LocalDateTime ngayBatDau;
    private LocalDateTime ngayKetThuc;
    private int luotSuDungConLai;
    private int chietKhau;

    public CT_KhuyenMai() {
    }

    public CT_KhuyenMai(String maKhuyenMai, String tenKhuyenMai, LocalDateTime ngayBatDau, LocalDateTime ngayKetThuc, int luotSuDungConLai, int chietKhau) {
        this.maKhuyenMai = maKhuyenMai;
        this.tenKhuyenMai = tenKhuyenMai;
        this.ngayBatDau = ngayBatDau;
        this.ngayKetThuc = ngayKetThuc;
        this.luotSuDungConLai = luotSuDungConLai;
        this.chietKhau = chietKhau;
    }

    public CT_KhuyenMai(String maKhuyenMai) {
        this.maKhuyenMai = maKhuyenMai;
    }

    public String getMaKhuyenMai() {
        return maKhuyenMai;
    }

    public void setMaKhuyenMai(String maKhuyenMai) throws IllegalArgumentException{
        if (maKhuyenMai == null || maKhuyenMai.isEmpty()) {
        throw new IllegalArgumentException("Mã khuyến mãi không được rỗng");
        }
        this.maKhuyenMai = maKhuyenMai;
    }

    public String getTenKhuyenMai() {
        return tenKhuyenMai;
    }

    public void setTenKhuyenMai(String tenKhuyenMai) throws IllegalArgumentException{
        if (tenKhuyenMai == null || tenKhuyenMai.isEmpty()) {
        throw new IllegalArgumentException("Tên khuyến mãi không được rỗng");
        }
        this.tenKhuyenMai = tenKhuyenMai;
    }

    public LocalDateTime getNgayBatDau() {
        return ngayBatDau;
    }


    public void setNgayBatDau(LocalDateTime ngayBatDau) throws IllegalArgumentException{
        if (ngayBatDau == null) {
        throw new IllegalArgumentException("Ngày bắt đầu không được rỗng");
        }
        if (ngayBatDau.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Ngày bắt đầu phải lớn hơn hoặc bằng ngày hiện tại");
        }

        this.ngayBatDau = ngayBatDau;
    }

    public LocalDateTime getNgayKetThuc() {
        return ngayKetThuc;
    }

    public void setNgayKetThuc(LocalDateTime ngayKetThuc) throws IllegalArgumentException{
        if (ngayKetThuc == null) {
        throw new IllegalArgumentException("Ngày kết thúc không được rỗng");
        }
        if (ngayKetThuc.isBefore(ngayBatDau)) {
            throw new IllegalArgumentException("Ngày kết thúc phải lớn hơn ngày bắt đầu");
        }

        this.ngayKetThuc = ngayKetThuc;
    }

    public int getLuotSuDungConLai() {
        return luotSuDungConLai;
    }

    public void setLuotSuDungConLai(int luotSuDungConLai) throws IllegalArgumentException{
        if (luotSuDungConLai < 0) {
        throw new IllegalArgumentException("Lượt sử dụng khuyến mãi phải lớn hơn hoặc bằng 0");
        }
        this.luotSuDungConLai = luotSuDungConLai;
    }

    public int getChietKhau() {
        return chietKhau;
    }

    public void setChietKhau(int chietKhau) throws IllegalArgumentException{
        if (chietKhau < 0 || chietKhau > 50) {
        throw new IllegalArgumentException("Chiết khấu phải lớn hơn 0 và nhỏ hơn hoặc bằng 50");
        }
        this.chietKhau = chietKhau;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.maKhuyenMai);
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
        return "CT_KhuyenMai{" + "maKhuyenMai=" + maKhuyenMai + ", tenKhuyenMai=" + tenKhuyenMai + ", ngayBatDau=" + ngayBatDau + ", ngayKetThuc=" + ngayKetThuc + ", luotSuDungConLai=" + luotSuDungConLai + ", chietKhau=" + chietKhau + '}';
    }

}
