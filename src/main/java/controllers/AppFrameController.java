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
import connect.ConnectDB;
import javafx.scene.control.MenuItem;

/**
 * FXML Controller class
 *
 * @author vie
 */
public class AppFrameController implements Initializable {

    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        ConnectDB.getInstance().connect();
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
//       App.openModal("GD_ChuyenPhong", App.widthModal, App.heightModal);
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

    @FXML
    private void moGDQLCTKhuyenMai(ActionEvent event) throws IOException {
        App.setRoot("GD_QLCTKhuyenMai");
    }

    @FXML
    private void moGDThongKe(ActionEvent event) throws IOException {
        App.setRoot("GD_ThongKe");
    }

    @FXML
    private void moGDThongKeThang(ActionEvent event) throws IOException {
        App.setRoot("GD_ThongKeThang");
    }

    @FXML
    private void moGDThongKeQuy(ActionEvent event) throws IOException {
        App.setRoot("GD_ThongKeQuy");
    }

    @FXML
    private void moGDThongKeNam(ActionEvent event) throws IOException {
        App.setRoot("GD_ThongKeNam");
    }

    @FXML
    private void moGDThongKeKhachHang(ActionEvent event) throws IOException {
        App.setRoot("GD_ThongKeKhachHang");
    }
}
