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
public class DichVu {

    private String maDichVu;
    private String tenDichVu;
    private int soLuong;
    private long donGia;
    private String donViTinh;
    private String anhMinhHoa;

    public DichVu() {
    }

    public DichVu(String maDichVu, String tenDichVu, int soLuong, long donGia, String donViTinh, String anhMinhHoa) throws Exception {
        setMaDichVu(maDichVu);
        setTenDichVu(tenDichVu);
        setSoLuong(soLuong);
        setDonViTinh(donViTinh);
        setAnhMinhHoa(anhMinhHoa);
    }

    public DichVu(String maDichVu) {
        this.maDichVu = maDichVu;
    }

    public String getMaDichVu() {
        return maDichVu;
    }

    public void setMaDichVu(String maDichVu) throws Exception {
        if (maDichVu != null && maDichVu.trim().equals("") && maDichVu.matches("^(DV)\\\\d{3}") == false){
            this.maDichVu = maDichVu;
        } else {
            throw new Exception("Mã dịch vụ là dãy gồm 5 kí tự 2 ký tự là DV 3 ký tự sau là số");
        }
        
    }

    public String getTenDichVu() {
        return tenDichVu;
    }

    public void setTenDichVu(String tenDichVu) throws Exception {
        if (tenDichVu == null || tenDichVu.trim().equals("")){
            throw new Exception("Tên Dịch Vụ không được rỗng");            
        } else{
            this.tenDichVu = tenDichVu;
        }
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) throws Exception {
        if (soLuong <= 0){
           throw new Exception("Số lượng không được rỗng và phải lớn hơn 0"); 
        } else {
            this.soLuong = soLuong;  
        }
        
    }

    public long getDonGia() {
        return donGia;
    }

    public void setDonGia(long donGia) throws Exception {
        if (donGia <= 0){
            throw new Exception("Đơn giá của dịch vụ  không được rỗng và phải lớn hơn 0");
        } else {
            this.donGia = donGia;
        }
    }

    public String getDonViTinh() {
        return donViTinh;
    }

    public void setDonViTinh(String donViTinh) throws Exception {
        if (donViTinh == null || donViTinh.trim().equals("")){
            throw new Exception("Đơn vị tính của dịch vụ không được rỗng");
        } else{
            this.donViTinh = donViTinh;
        }
        
    }

    public String getAnhMinhHoa() {
        return anhMinhHoa;
    }

    public void setAnhMinhHoa(String anhMinhHoa) throws Exception {
        if (anhMinhHoa == null || anhMinhHoa.trim().equals("")){
            throw new Exception("Ảnh minh họa của dịch vụ  không được rỗng");
        } else{
            this.anhMinhHoa = anhMinhHoa;
        }
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + Objects.hashCode(this.maDichVu);
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
        final DichVu other = (DichVu) obj;
        return Objects.equals(this.maDichVu, other.maDichVu);
    }

    @Override
    public String toString() {
        return "DichVu{" + "maDichVu=" + maDichVu + ", tenDichVu=" + tenDichVu + ", soLuong=" + soLuong + ", donGia=" + donGia + ", donViTinh=" + donViTinh + ", anhMinhHoa=" + anhMinhHoa + '}';
    }
}
