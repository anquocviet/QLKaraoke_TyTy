package controllers;

import enums.Enum_ChucVu;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import lombok.SneakyThrows;
import main.App;
import socket.ClientSocket;

import java.awt.Desktop;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author vie
 */
public class AppFrameController implements Initializable {
   @FXML
   private Circle circleAvt;
   @FXML
   private Text txtNhanVien;
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

   @SneakyThrows
   @Override
   public void initialize(URL location, ResourceBundle resources) {
      if (App.user.getChucVu().equals(Enum_ChucVu.QUANLY)) {
         taiKhoanMenuItem.setDisable(false);
         qlNhanVienMenuItem.setDisable(false);
         qlPhongMenuItem.setDisable(false);
         qlDichVuMenuItem.setDisable(false);
         qlCTKhuyenMaiMenuItem.setDisable(false);
         thongKeMenu.setDisable(false);
         txtNhanVien.setText("QL: " + App.user.getHoTen());
      } else {
         txtNhanVien.setText("NV: " + App.user.getHoTen());
      }
      circleAvt.setFill(new ImagePattern(new Image("file:src/main/resources/image/avt_nv/" + App.user.getAnhDaiDien())));
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
   private void moGDQLTaiKhoan(ActionEvent event) throws IOException {
      App.setRoot("GD_QLTaiKhoan");
   }

   @FXML
   private void moHuongDanSuDung(ActionEvent event) throws IOException {
      String initial = "src/main/resources/huongdansudung/HuongDanSuDung.pdf";
      Path initialDirectory = Paths.get(initial).toAbsolutePath();
      File file = new File(initial);

      try {
         Desktop desktop = Desktop.getDesktop();
         desktop.open(file);
      } catch (IOException e) {
         e.printStackTrace();
      }
   }


   @FXML
   private void dangXuat(ActionEvent event) throws IOException {
      App.user = null;
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
}
