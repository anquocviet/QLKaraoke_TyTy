/* * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
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
import enums.Enum_ChucVu;
import javafx.scene.Node;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import static main.App.heightModalLogin;
import static main.App.openModal;
import static main.App.widthModalLogin;
import model.NhanVien;

/**
 * FXML Controller class
 *
 * @author vie
 */
public class AppFrameController implements Initializable {

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        NhanVien nv = NhanVien.getNhanVienTheoMaNhanVien(App.user);
        if (nv.getChucVu().equals(Enum_ChucVu.QUANLY)) {
            taiKhoanMenuItem.setDisable(false);
            qlNhanVienMenuItem.setDisable(false);
            qlPhongMenuItem.setDisable(false);
            qlDichVuMenuItem.setDisable(false);
            qlCTKhuyenMaiMenuItem.setDisable(false);
            thongKeMenu.setDisable(false);
        }
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
    private void moGDQLDichVu(ActionEvent event) throws IOException {
        App.setRoot("GD_QLDichVu");
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
    }
	
	@FXML 
	private void dangXuat(ActionEvent event) throws IOException {
		App.user = "";
		Stage stage = (Stage) ((MenuItem) event.getTarget()).getParentPopup().getOwnerWindow();
		stage.close();
		App.openModal("GD_DangNhap", App.widthModalLogin, App.heightModalLogin);
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

//    Variable
    @FXML
    private MenuItem taiKhoanMenuItem;
    @FXML
    private MenuItem qlNhanVienMenuItem;
    @FXML
    private MenuItem qlPhongMenuItem;
    @FXML
    private MenuItem qlDichVuMenuItem;
    @FXML
    private MenuItem qlCTKhuyenMaiMenuItem;
    @FXML
    private Menu thongKeMenu;
}
