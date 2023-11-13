/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import connect.ConnectDB;
import controllers.GD_QLCTKhuyenMaiController;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author fil
 */
public class CT_KhuyenMai {

    private String maKhuyenMai;
    private String tenKhuyenMai;
    private Date ngayBatDau;
    private Date ngayKetThuc;
    private int luotSuDungConLai;
    private int chietKhau;

    public CT_KhuyenMai() {
    }

    public CT_KhuyenMai(String maKhuyenMai, String tenKhuyenMai, Date ngayBatDau, Date ngayKetThuc, int luotSuDungConLai, int chietKhau) {
        setMaKhuyenMai(maKhuyenMai);
        setTenKhuyenMai(tenKhuyenMai);
        setNgayBatDau(ngayBatDau);
        setNgayKetThuc(ngayKetThuc);
        setLuotSuDungConLai(luotSuDungConLai);
        setChietKhau(chietKhau);
    }

    public CT_KhuyenMai(String maKhuyenMai) {
        this.maKhuyenMai = maKhuyenMai;
    }

    public String getMaKhuyenMai() {
        return maKhuyenMai;
    }

    public void setMaKhuyenMai(String maKhuyenMai) throws IllegalArgumentException {
        if (maKhuyenMai == null || maKhuyenMai.isEmpty()) {
            throw new IllegalArgumentException("Mã khuyến mãi không được rỗng");
        } else {
            this.maKhuyenMai = maKhuyenMai;
        }
    }

    public String getTenKhuyenMai() {
        return tenKhuyenMai;
    }

    public void setTenKhuyenMai(String tenKhuyenMai) throws IllegalArgumentException {
        if (tenKhuyenMai == null || tenKhuyenMai.isEmpty()) {
            throw new IllegalArgumentException("Tên khuyến mãi không được rỗng");
        } else {
            this.tenKhuyenMai = tenKhuyenMai;
        }
    }

    public Date getNgayBatDau() {
        return ngayBatDau;
    }

    public void setNgayBatDau(Date ngayBatDau) throws IllegalArgumentException {
        if (ngayBatDau == null) {
            throw new IllegalArgumentException("Ngày bắt đầu không được rỗng");
        } else {
            this.ngayBatDau = ngayBatDau;
        }
    }

    public Date getNgayKetThuc() {
        return ngayKetThuc;
    }

    public void setNgayKetThuc(Date ngayKetThuc) throws IllegalArgumentException {
        if (ngayKetThuc == null) {
            throw new IllegalArgumentException("Ngày kết thúc không được rỗng");
        } else if (ngayKetThuc.before(ngayBatDau)) {
            throw new IllegalArgumentException("Ngày kết thúc phải lớn hơn ngày bắt đầu");
        } else {
            this.ngayKetThuc = ngayKetThuc;
        }
    }

    public int getLuotSuDungConLai() {
        return luotSuDungConLai;
    }

    public void setLuotSuDungConLai(int luotSuDungConLai) throws IllegalArgumentException {
        if (luotSuDungConLai < 0) {
            throw new IllegalArgumentException("Lượt sử dụng khuyến mãi phải lớn hơn hoặc bằng 0");
        } else {
            this.luotSuDungConLai = luotSuDungConLai;
        }
    }

    public int getChietKhau() {
        return chietKhau;
    }

    public void setChietKhau(int chietKhau) throws IllegalArgumentException {
        if (chietKhau < 0 || chietKhau > 50) {
            throw new IllegalArgumentException("Chiết khấu phải lớn hơn 0 và nhỏ hơn hoặc bằng 50");
        } else {
            this.chietKhau = chietKhau;
        }
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

//    Get Data From DB
    public static ObservableList<CT_KhuyenMai> getListCT_KhuyenMai() {
        ConnectDB.getInstance();
        Connection con = ConnectDB.getConnection();

        ObservableList<CT_KhuyenMai> list = FXCollections.observableArrayList();
        try {
            PreparedStatement ps = con.prepareStatement("select * from CT_KhuyenMai");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String maKhuyenMai = rs.getString("MaKhuyenMai");
                String tenKhuyenMai = rs.getString("TenKhuyenMai");
                /// lỗi ko lấy được dữ liệu
                //String ngayTmp = rs.getString("NgayBatDau");
                //String xulyNgay = processString(ngayTmp);
                Date ngayBatDau = rs.getDate("NgayBatDau");
                //ngayTmp = rs.getString("NgayKetThuc");
                //xulyNgay = processString(ngayTmp);
                Date ngayKetThuc = rs.getDate("NgayKetThuc");
                Integer soLuotSuDungConLai = rs.getInt("LuotSuDungConLai");
                Integer chietKhau = rs.getInt("ChietKhau");
                CT_KhuyenMai tmp = new CT_KhuyenMai(maKhuyenMai, tenKhuyenMai, ngayBatDau, ngayKetThuc, soLuotSuDungConLai, chietKhau);
                list.add(tmp);
            }
            rs.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public static String processString(String input) {
        // Tìm khoảng trống và thay thế nó bằng chữ 'T'
        String replacedSpace = input.replace(" ", "T");

        // Tìm dấu "." và loại bỏ tất cả phần sau dấu "."
        int dotIndex = replacedSpace.indexOf(".");
        if (dotIndex != -1) {
            return replacedSpace.substring(0, dotIndex);
        } else {
            return replacedSpace;
        }
    }
    public static boolean themCTKhuyenMai(CT_KhuyenMai km) {
        ConnectDB.getInstance();
        Connection conn = ConnectDB.getInstance().getConnection();
        PreparedStatement pstm = null;
        int n = 0;
        String sql = "INSERT INTO CT_KhuyenMai (MaKhuyenMai, TenKhuyenMai, NgayBatDau, NgayKetThuc, LuotSuDungConLai, ChietKhau) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";
        try {
            pstm = conn.prepareStatement(sql);
            pstm.setString(1, km.getMaKhuyenMai());
            pstm.setString(2, km.getTenKhuyenMai());
            pstm.setDate(3,km.getNgayBatDau());
            pstm.setDate(4, km.getNgayKetThuc());
            pstm.setInt(5, km.getLuotSuDungConLai());
            pstm.setInt(6, km.getChietKhau());
            n = pstm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(GD_QLCTKhuyenMaiController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                pstm.close();
            } catch (SQLException ex) {
                Logger.getLogger(GD_QLCTKhuyenMaiController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return n > 0;
    }
    
}
    
    /*
    
*/
