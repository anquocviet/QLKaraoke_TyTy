/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.Objects;

/**
 *
 * @author vie
 */
public class Phong {

    private String maPhong;
    private int sucChua;
    private int tinhTrang;      // 0: trống - 1: đã thuê - 2: được đặt
    private int loaiPhong;      // 1: VIP - 0: thường
    private long giaPhong;

    public Phong() {
    }

    public Phong(String maPhong, int sucChua, int tinhTrang, int loaiPhong, long giaPhong) {
        this.maPhong = maPhong;
        this.sucChua = sucChua;
        this.tinhTrang = tinhTrang;
        this.loaiPhong = loaiPhong;
        this.giaPhong = giaPhong;
    }

    public Phong(String maPhong) {
        this.maPhong = maPhong;
    }

    public String getMaPhong() {
        return maPhong;
    }

    public void setMaPhong(String maPhong) {
        this.maPhong = maPhong;
    }

    public int getSucChua() {
        return sucChua;
    }

    public void setSucChua(int sucChua) {
        this.sucChua = sucChua;
    }

    public int getTinhTrang() {
        return tinhTrang;
    }

    public void setTinhTrang(int tinhTrang) {
        this.tinhTrang = tinhTrang;
    }

    public int getLoaiPhong() {
        return loaiPhong;
    }

    public void setLoaiPhong(int loaiPhong) {
        this.loaiPhong = loaiPhong;
    }

    public long getGiaPhong() {
        return giaPhong;
    }

    public void setGiaPhong(long giaPhong) {
        this.giaPhong = giaPhong;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + Objects.hashCode(this.maPhong);
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
        final Phong other = (Phong) obj;
        return Objects.equals(this.maPhong, other.maPhong);
    }

    @Override
    public String toString() {
        return "Phong{" + "maPhong=" + maPhong + ", sucChua=" + sucChua + ", tinhTrang=" + tinhTrang + ", loaiPhong=" + loaiPhong + ", giaPhong=" + giaPhong + '}';
    }

}
