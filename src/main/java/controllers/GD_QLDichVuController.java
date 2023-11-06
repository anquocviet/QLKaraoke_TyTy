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
import model.DichVu;
import model.KhachHang;

/**
 * FXML Controller class
 *
 * @author fil
 */
public class GD_QLDichVuController implements Initializable {

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
    
    public ObservableList<DichVu> getAllDichVu() {
        ObservableList<DichVu> dsDichVu = FXCollections.observableArrayList();
        Connection conn = ConnectDB.getInstance().getConnection();
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            String sql = "SELECT * FROM DichVu";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String maDichVu = rs.getString("MaDichVu");
                String tenDichVu = rs.getString("TenDichVu");
                int soLuongTon = rs.getInt("SoLuongTon");
                String donViTinh = rs.getString("DonViTinh");
                long donGia = rs.getLong("DonGia");
                String anhMinhHoa = rs.getString("AnhMinhHoa");
                dsDichVu.add(new DichVu(maDichVu, tenDichVu, soLuongTon, donGia, donViTinh, anhMinhHoa));
            }
        } catch (SQLException ex) {
            Logger.getLogger(GD_QLKhachHangController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(GD_QLDichVuController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                stmt.close();
            } catch (SQLException ex) {
                Logger.getLogger(GD_QLKhachHangController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return dsDichVu;
    }

}
