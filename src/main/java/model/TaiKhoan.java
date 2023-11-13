/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import connect.ConnectDB;
import controllers.GD_DangNhapController;
import controllers.GD_QLKhachHangController;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author vie
 */
public class TaiKhoan {

    private String maTaiKhoan;
    private String tenDangNhap;
    private String matKhau;
    private NhanVien nhanVien;

    public TaiKhoan() {
    }

    public TaiKhoan(String maTaiKhoan, String tenDangNhap, String matKhau, NhanVien nhanVien) {
        this.maTaiKhoan = maTaiKhoan;
        this.tenDangNhap = tenDangNhap;
        this.matKhau = matKhau;
        this.nhanVien = nhanVien;
    }

    public TaiKhoan(String maTaiKhoan) {
        this.maTaiKhoan = maTaiKhoan;
    }

    public String getMaTaiKhoan() {
        return maTaiKhoan;
    }

    public void setMaTaiKhoan(String maTaiKhoan) {
        this.maTaiKhoan = maTaiKhoan;
    }

    public String getTenDangNhap() {
        return tenDangNhap;
    }

    public void setTenDangNhap(String tenDangNhap) {
        this.tenDangNhap = tenDangNhap;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public NhanVien getNhanVien() {
        return nhanVien;
    }

    public void setNhanVien(NhanVien nhanVien) {
        this.nhanVien = nhanVien;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + Objects.hashCode(this.maTaiKhoan);
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
        final TaiKhoan other = (TaiKhoan) obj;
        return Objects.equals(this.maTaiKhoan, other.maTaiKhoan);
    }

    @Override
    public String toString() {
        return "TaiKhoan{" + "maTaiKhoan=" + maTaiKhoan + ", tenDangNhap=" + tenDangNhap + ", matKhau=" + matKhau + ", nhanVien=" + nhanVien + '}';
    }

    //    Get data from DB
    public static ObservableList<TaiKhoan> getAllTaiKhoan() {
        ObservableList<TaiKhoan> dsTaiKhoan = FXCollections.observableArrayList();
        Connection conn = ConnectDB.getInstance().getConnection();
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            String sql = "SELECT * FROM TaiKhoan";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String maTaiKhoan = rs.getString("MaTaiKhoan");
                String tenDangNhap = rs.getString("TenDangNhap");
                String matKhau = rs.getString("MatKhau");
                String maNhanVien = rs.getString("MaNhanVien");
                dsTaiKhoan.add(new TaiKhoan(maTaiKhoan, tenDangNhap, matKhau, new NhanVien(maNhanVien)));
            }
        } catch (SQLException ex) {
            Logger.getLogger(GD_QLKhachHangController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(GD_DangNhapController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                stmt.close();
            } catch (SQLException ex) {
                Logger.getLogger(GD_QLKhachHangController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return dsTaiKhoan;
    }

    public static TaiKhoan getTaiKhoanTheoUserNameAndPassword(String tenDangNhap, String matKhau) {
        Connection conn = ConnectDB.getInstance().getConnection();
        Statement stmt = null;
        TaiKhoan tk = null;
        try {
            stmt = conn.createStatement();
            String sql = String.format("SELECT * FROM TaiKhoan WHERE TenDangNhap = '%s' AND MatKhau = '%s'", tenDangNhap, matKhau);
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String maTaiKhoan = rs.getString("MaTaiKhoan");
                String maNhanVien = rs.getString("MaNhanVien");
                tk = new TaiKhoan(maTaiKhoan, tenDangNhap, matKhau, new NhanVien(maNhanVien));
            }
        } catch (SQLException ex) {
            Logger.getLogger(GD_QLKhachHangController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(GD_DangNhapController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                stmt.close();
            } catch (SQLException ex) {
                Logger.getLogger(GD_QLKhachHangController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return tk;
    }
    

}
