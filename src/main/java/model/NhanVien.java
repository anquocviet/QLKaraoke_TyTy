/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import enums.Enum_ChucVu;
import enums.Enum_TrangThaiLamViec;
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
    private Enum_ChucVu chucVu;
    private boolean gioiTinh;       // true: Nam - false: Nữ
    private String anhDaiDien;
    private Enum_TrangThaiLamViec trangThai;

    public NhanVien() {
    }

    public NhanVien(String maNhanVien, String cccd, String hoTen, String diaChi, LocalDate ngaySinh, String soDienThoai, Enum_ChucVu chucVu, boolean gioiTinh, String anhDaiDien, Enum_TrangThaiLamViec trangThai) throws Exception {
        setMaNhanVien(maNhanVien);
        setCccd(cccd);
        setHoTen(hoTen);
        setDiaChi(diaChi);
        setNgaySinh(ngaySinh);
        setSoDienThoai(soDienThoai);
        setChucVu(chucVu);
        setGioiTinh(gioiTinh);
        setAnhDaiDien(anhDaiDien);
        setTrangThai(trangThai);
    }

    public NhanVien(String maNhanVien) throws Exception {
        setMaNhanVien(maNhanVien);
    }

    public String getMaNhanVien() {
        return maNhanVien;
    }

    public void setMaNhanVien(String maNhanVien) throws Exception {
        if (maNhanVien == null || maNhanVien.trim().equals("")) {
            throw new Exception("Mã nhân viên không được trống");
        } else {
            this.maNhanVien = maNhanVien;
        }
    }

    public String getCccd() {
        return cccd;
    }

    public void setCccd(String cccd) throws Exception {
        if (cccd == null || cccd.trim().equals("")) {
            throw new Exception("Căn cước công dân không được trống");
        } else {
            this.cccd = cccd;
        }
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) throws Exception {
        if (hoTen == null || hoTen.trim().equals("")) {
            throw new Exception("Họ tên không được trống");
        } else {
            this.hoTen = hoTen;
        }
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) throws Exception {
        if (diaChi == null || diaChi.trim().equals("")) {
            throw new Exception("Địa chỉ không được trống");
        } else {
            this.diaChi = diaChi;
        }
    }

    public LocalDate getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(LocalDate ngaySinh) throws Exception {
        if (ngaySinh == null) {
            throw new Exception("Ngày sinh không được trống");
        } else if (LocalDate.now().getYear() - ngaySinh.getYear() < 18) {
            throw new Exception("Nhân viên phải từ 18 tuổi trở lên");
        } else {
            this.ngaySinh = ngaySinh;
        }
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) throws Exception {
        if (soDienThoai == null) {
            throw new Exception("Số điện thoại không được trống");
        } else {
            this.soDienThoai = soDienThoai;
        }
    }

    public Enum_ChucVu getChucVu() {
        return chucVu;
    }

    public void setChucVu(Enum_ChucVu chucVu) throws Exception {
        if (chucVu == null) {
            throw new Exception("Chức vụ không được trống");
        } else {
            this.chucVu = chucVu;
        }
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

    public void setAnhDaiDien(String anhDaiDien) throws Exception {
        if (anhDaiDien == null || anhDaiDien.trim().equals("")) {
            throw new Exception("Ảnh đại diện không được trống");
        } else {
            this.anhDaiDien = anhDaiDien;
        }
    }

    public Enum_TrangThaiLamViec getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(Enum_TrangThaiLamViec trangThai) throws Exception {
        if (trangThai == null) {
            throw new Exception("Trạng thái làm việc không được trống");
        } else {
            this.trangThai = trangThai;
        }
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
