/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import main.App;

/**
 * FXML Controller class
 *
 * @author vie
 */
public class AppFrameController implements Initializable {

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    @FXML
    private void moTrangChu(ActionEvent event) throws IOException {
        App.setRoot("TrangChu");
    }

    @FXML
    private void moGDQLKhachHang(ActionEvent event) throws IOException {
        App.setRoot("GD_QLKhachHang");
    }

    @FXML
    private void moGDQLNhanVien(ActionEvent event) throws IOException {
        App.setRoot("GD_QLNhanVien");
    }

    @FXML
    private void moGDDatDichVu(ActionEvent event) throws IOException {
        App.setRoot("GD_DatDichVu");
    }

    @FXML
    private void moGDQLPhong(ActionEvent event) throws IOException {
        App.setRoot("GD_QLPhong");
    }
    
    @FXML
    private void moGDQuanLyKinhDoanhPhong(ActionEvent event) throws IOException {
        App.setRoot("GD_QLKinhDoanhPhong");
    }
    
    @FXML
    private void moGDTraCuuHoaDon(ActionEvent event) throws IOException {
        App.setRoot("GD_TraCuuHoaDon");
    }

    @FXML
    private void moGDDangKy(ActionEvent event) throws IOException {
        App.setRoot("GD_DangKy");
//        App.openModal("GD_ThuePhong", App.widthModal, App.heightModal);
    }

    @FXML
    private void dongUngDung(ActionEvent event) throws IOException, Exception {
        Platform.exit();
        System.exit(0);
    }
    
    @FXML
    private void moGDQLKinhDoanhPhong(ActionEvent event) throws IOException {
        App.setRoot("GD_QuanLyKinhDoanhPhong");
    }
}

