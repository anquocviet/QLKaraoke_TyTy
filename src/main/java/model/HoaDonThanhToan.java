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
public class HoaDonThanhToan {

    private String maHoaDon;
    private NhanVien nhanVienLap;
    private KhachHang khachHang;
    private CT_KhuyenMai khuyenMai;
    private LocalDate ngayLap;

    public HoaDonThanhToan(String maHoaDon, NhanVien nhanVienLap, KhachHang khachHang, CT_KhuyenMai khuyenMai, LocalDate ngayLap) {
        this.maHoaDon = maHoaDon;
        this.nhanVienLap = nhanVienLap;
        this.khachHang = khachHang;
        this.khuyenMai = khuyenMai;
        this.ngayLap = ngayLap;
    }

    public HoaDonThanhToan() {
    }

    public HoaDonThanhToan(String maHoaDon) {
        this.maHoaDon = maHoaDon;
    }

    public String getMaHoaDon() {
        return maHoaDon;
    }

    public void setMaHoaDon(String maHoaDon) throws IllegalArgumentException{
        if(maHoaDon == null || maHoaDon.isEmpty()){
            throw new IllegalArgumentException("Mã hóa đơn không được rỗng");
        }
        this.maHoaDon = maHoaDon;
    }

    public NhanVien getNhanVienLap() {
        return nhanVienLap;
    }

    public void setNhanVienLap(NhanVien nhanVienLap) throws IllegalArgumentException{
        if( nhanVienLap == null || maHoaDon.isEmpty()){
            throw new IllegalArgumentException("không được rỗng");
        }
        this.nhanVienLap = nhanVienLap;
    }

    public KhachHang getKhachHang() {
        return khachHang;
    }

    public void setKhachHang(KhachHang khachHang)throws IllegalArgumentException{
        if(khachHang != null){
            throw new IllegalArgumentException("Khách hàng không được rỗng");
            
        }
        this.khachHang = khachHang;
    }

    public CT_KhuyenMai getKhuyenMai() {
        return khuyenMai;
    }

    public void setKhuyenMai(CT_KhuyenMai khuyenMai){
        this.khuyenMai = khuyenMai;
    }

    public LocalDate getNgayLap() {
        return ngayLap;
    }

    public void setNgayLap(LocalDate ngayLap) throws IllegalArgumentException{
        if(ngayLap == null){
            throw  new IllegalArgumentException("Ngày lập không được rỗng");
        }
        this.ngayLap = ngayLap;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.maHoaDon);
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
        final HoaDonThanhToan other = (HoaDonThanhToan) obj;
        return Objects.equals(this.maHoaDon, other.maHoaDon);
    }

    @Override
    public String toString() {
        return "HoaDonThanhToan{" + "maHoaDon=" + maHoaDon + ", nhanVienLap=" + nhanVienLap + ", khachHang=" + khachHang + ", khuyenMai=" + khuyenMai + ", ngayLap=" + ngayLap + '}';
    }

}
