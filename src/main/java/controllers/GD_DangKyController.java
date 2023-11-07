/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controllers;

import connect.ConnectDB;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import main.App;
import model.NhanVien;
import model.TaiKhoan;

/**
 * FXML Controller class
 *
 * @author thach
 */
public class GD_DangKyController implements Initializable {

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
    
    //    Get data from DB
    public ObservableList<TaiKhoan> getAllTaiKhoan() {
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
    
    //    Get data from DB
    public TaiKhoan getTaiKhoanTheoUserNameAndPassword(String tenDangNhap, String matKhau) {
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
