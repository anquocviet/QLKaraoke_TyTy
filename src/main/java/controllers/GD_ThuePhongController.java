/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controllers;

import entities.ChiTietHD_Phong;
import entities.ChiTietHD_PhongId;
import entities.HoaDonThanhToan;
import entities.KhachHang;
import entities.NhanVien;
import entities.Phong;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import main.App;
import socket.ClientSocket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * FXML Controller class
 *
 * @author fil
 */
public class GD_ThuePhongController implements Initializable {

   @FXML
   private TextField txtSoPhong;
   @FXML
   private TextField txtSDTKhachHang;
   @FXML
   private TextField txtTenKhachHang;
   @FXML
   private TextField txtNamSinh;
   @FXML
   private Text timeThue;

   @FXML
   private ComboBox ccbGender;
   @FXML
   private DatePicker dateThue;

   @FXML
   private Button btnKiemTraSĐT;
   @FXML
   private Button btnExit;
   @FXML
   private Button btnRefresh;
   @FXML
   private Button btnThue;

   DataInputStream dis = ClientSocket.getDis();
   DataOutputStream dos = ClientSocket.getDos();
   ObjectInputStream in = ClientSocket.getIn();
   ObjectOutputStream out = ClientSocket.getOut();

   @Override
   public void initialize(URL location, ResourceBundle resources) {
      txtSoPhong.setText(GD_QLKinhDoanhPhongController.roomID);

//        try {
//            if (Phong.getListPhongByStatus(2).contains(new Phong(GD_QLKinhDoanhPhongController.roomID))) {
//                KhachHang khachHang = PhieuDatPhong.getCustomerInfoByRoomID(GD_QLKinhDoanhPhongController.roomID);
//                txtSDTKhachHang.setText(khachHang.getSoDienThoai());
//                txtTenKhachHang.setText(khachHang.getTenKhachHang());
//                txtNamSinh.setText(String.valueOf(khachHang.getNamSinh()));
//                ccbGender.setValue(khachHang.isGioiTinh() ? "Nam" : "Nữ");
//                // Cập nhật dateThue với ngày hiện tại
//                dateThue.setValue(LocalDate.now());
//                // Cập nhật timeThue với thời gian hiện tại
//                LocalTime currentTime = LocalTime.now();
//                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
//                String formattedTime = currentTime.format(formatter);
//                timeThue.setText(formattedTime);
//            }
//        } catch (Exception ex) {
//            Logger.getLogger(GD_QLKinhDoanhPhongController.class.getName()).log(Level.SEVERE, null, ex);
//        }

      txtSDTKhachHang.setOnAction(this::handleKiemTraSDT);
      btnKiemTraSĐT.setOnAction(this::handleKiemTraSDT);
      btnExit.setOnAction(this::handleExit);
      btnRefresh.setOnAction(this::handleRefresh);
//        btnThue.setOnAction(this::handleThue);
   }

   @FXML
   public void handleKiemTraSDT(ActionEvent event) {
      try {
         String soDienThoai = txtSDTKhachHang.getText().trim().substring(1);
         if (!isValidPhoneNumber(0 + soDienThoai)) {
            showAlert("Số điện thoại không hợp lệ", "Vui lòng nhập số điện thoại hợp lệ.");
            return;
         }
         dos.writeUTF("customer-find-customer-by-phone," + soDienThoai);
         //chi lay khach hang dau tien
         List<KhachHang> list = (List<KhachHang>) in.readObject();
         ObservableList<KhachHang> khachHangs = FXCollections.observableArrayList(list);
         if (khachHangs.size() > 1) {
            return;
         }
         KhachHang khachHang = khachHangs.getFirst();
         if (khachHang != null) {
            txtTenKhachHang.setText(khachHang.getTenKhachHang());
            txtNamSinh.setText(String.valueOf(khachHang.getNamSinh()));
            ccbGender.setValue(khachHang.getGioiTinh() == 1 ? "Nam" : "Nữ");
            // Cập nhật dateThue với ngày hiện tại
            dateThue.setValue(LocalDate.now());
            // Cập nhật timeThue với thời gian hiện tại
            LocalTime currentTime = LocalTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
            String formattedTime = currentTime.format(formatter);
            timeThue.setText(formattedTime);
         } else {
            showAlert("Không tìm thấy khách hàng", "Không có thông tin khách hàng cho số điện thoại này.Vui lòng thêm khách hàng trước khi đặt phòng!");
         }
      } catch (IOException | ClassNotFoundException e) {
         e.printStackTrace();
      }
   }

   @FXML
   public void handleRefresh(ActionEvent event) {
      txtSDTKhachHang.clear();
      txtTenKhachHang.clear();
      txtNamSinh.clear();
      ccbGender.setValue("");
      dateThue.setValue(LocalDate.now());
      LocalTime currentTime = LocalTime.now();
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
      String formattedTime = currentTime.format(formatter);
      timeThue.setText(formattedTime);
   }

   @FXML
   public void handleExit(ActionEvent event) {
      Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
      stage.close();
   }

   @FXML
   public void handleThue(ActionEvent event) throws Exception {
      // Lấy thông tin từ giao diện
      String soDienThoai = txtSDTKhachHang.getText().substring(1);
      if (soDienThoai.isEmpty()) {
         showAlert("Lỗi!", "Không được để trống Số điện thoại");
         return;
      } else if (!isValidPhoneNumber(0 + soDienThoai)) {
         showAlert("Số điện thoại không hợp lệ", "Vui lòng nhập số điện thoại hợp lệ.");
         return;
      } else {
         dos.writeUTF("room-find-room," + GD_QLKinhDoanhPhongController.roomID);
         Phong roomThue = ((List<Phong>) in.readObject()).getFirst();
         dos.writeUTF("room-update-room");
         roomThue.setTinhTrang(1);
         out.writeObject(roomThue);
         if (!dis.readBoolean()) {
            showAlert("Lỗi!", "Không thể cập nhật trạng thái phòng");
            return;
         }

         String maHoaDon;
         NhanVien nhanVienLap = App.user;
         dos.writeUTF("customer-find-customer-by-phone," + soDienThoai);
         KhachHang khachHang = ((List<KhachHang>) in.readObject()).getFirst();
         dos.writeUTF("bill-find-by-ma-khach-hang-not-pay," + khachHang.getMaKhachHang());
         List<HoaDonThanhToan> listHD = (List<HoaDonThanhToan>) in.readObject();
         if (!listHD.isEmpty()) {
            HoaDonThanhToan hoaDon = listHD.getFirst();
            ChiTietHD_Phong ctP = new ChiTietHD_Phong(new ChiTietHD_PhongId(), hoaDon, roomThue, Instant.now(), Instant.now().plusSeconds(1), 0.0, 0);
            dos.writeUTF("roomDetail-add-roomDetail");
            out.writeObject(ctP);
            if (!dis.readBoolean()) {
               showAlert("Lỗi!", "Đã xảy ra lỗi khi thêm thuê phòng!");
               return;
            }
         } else {
            dos.writeUTF("bill-count-bill-by-date");
            out.writeObject(Instant.now());
            long slHoaDon = dis.readLong();
            maHoaDon = phatSinhMaHoaDon(slHoaDon);
            HoaDonThanhToan hoaDon = new HoaDonThanhToan(maHoaDon, khachHang, nhanVienLap, null, Instant.now(), 0);
            dos.writeUTF("bill-add-bill");
            out.writeObject(hoaDon);
            if (!dis.readBoolean()) {
               showAlert("Lỗi!", "Đã xảy ra lỗi khi thuê phòng!");
               return;
            }
            ChiTietHD_Phong ctP = new ChiTietHD_Phong(new ChiTietHD_PhongId(), hoaDon, roomThue, Instant.now(), Instant.now().plusSeconds(1), 0.0, 0);
            dos.writeUTF("roomDetail-add-roomDetail");
            out.writeObject(ctP);
            if (!dis.readBoolean()) {
               showAlert("Lỗi!", "Đã xảy ra lỗi khi thuê phòng!");
               return;
            }
         }
      }

      showAlert("Thông báo", "Đã thực hiện tác vụ thuê phòng!");
      Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();

      try {
         App.setRoot("GD_QLKinhDoanhPhong");
      } catch (IOException ex) {
         Logger.getLogger(GD_ThuePhongController.class.getName()).log(Level.SEVERE, null, ex);
      }
      stage.close();
   }

   public boolean isValidPhoneNumber(String phoneNumber) {
      return phoneNumber.matches("\\d{10}");
   }

   public void showAlert(String title, String content) {
      Alert alert = new Alert(AlertType.INFORMATION);
      alert.setTitle(title);
      alert.setHeaderText(null);
      alert.setContentText(content);
      alert.getDialogPane().setStyle("-fx-font-family: 'sans-serif';");
      alert.showAndWait();
   }

   public String phatSinhMaHoaDon(Long stt) {
      Date ngayLap = new Date();
      SimpleDateFormat dateFormat = new SimpleDateFormat("ddMM");
      String ngayThangNam = dateFormat.format(ngayLap);

      String strSTT = String.format("%02d", stt + 1);

      SimpleDateFormat yearFormat = new SimpleDateFormat("yy");
      String namCuoi = yearFormat.format(ngayLap);

      String maHoaDon = "HD" + strSTT + ngayThangNam + namCuoi;

      return maHoaDon;
   }

}
