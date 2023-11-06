/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author fil
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

    public void setHoaDon(HoaDonThanhToan hoaDon) throws IllegalArgumentException{
        if (hoaDon == null) {
            throw new IllegalArgumentException("Hóa đơn không được rỗng");
        }else {
            this.hoaDon = hoaDon;
        }
    }

    public DichVu getDichVu() {
        return dichVu;
    }

    public void setDichVu(DichVu dichVu) throws IllegalArgumentException{
        if (dichVu == null) {
        throw new IllegalArgumentException("Dịch vụ không được rỗng");
        }else {
            this.dichVu = dichVu;
        }
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) throws IllegalArgumentException{
        if (soLuong <= 0) {
        throw new IllegalArgumentException("Số lượng không được rỗng và phải lớn hơn 0");
        } else {
            this.soLuong = soLuong;
        }
    }

    public long tinhThanhTien() {
        return this.soLuong * this.dichVu.getDonGia();
    }

    @Override
    public String toString() {
        return "ChiTietHD_DichVu{" + "hoaDon=" + hoaDon + ", dichVu=" + dichVu + ", soLuong=" + soLuong + '}';
    }

}
