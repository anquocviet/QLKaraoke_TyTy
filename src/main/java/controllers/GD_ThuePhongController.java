/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controllers;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.KhachHang;
import static model.KhachHang.getKhachHangTheoSoDienThoai;

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
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
       txtSoPhong.setText(GD_QLKinhDoanhPhongController.roomID);
       
       
       btnKiemTraSĐT.setOnAction(this::handleKiemTraSDT);
       btnExit.setOnAction(this::handleExit);
       btnRefresh.setOnAction(this::handleRefresh);
       btnThue.setOnAction(this::handleThue);
    }

    @FXML
    public void handleKiemTraSDT(ActionEvent event) {
        String soDienThoai = txtSDTKhachHang.getText().trim();
        if (!isValidPhoneNumber(soDienThoai)) {
            showAlert("Số điện thoại không hợp lệ", "Vui lòng nhập số điện thoại hợp lệ.");
            return;
        }

        KhachHang khachHang = getKhachHangTheoSoDienThoai(soDienThoai);

        if (khachHang != null) {
            txtTenKhachHang.setText(khachHang.getTenKhachHang());
            txtNamSinh.setText(String.valueOf(khachHang.getNamSinh()));
            ccbGender.setValue(khachHang.isGioiTinh() ? "Nam" : "Nữ");
            // Cập nhật dateThue với ngày hiện tại
            dateThue.setValue(LocalDate.now());
            // Cập nhật timeThue với thời gian hiện tại
            LocalTime currentTime = LocalTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
            String formattedTime = currentTime.format(formatter);
            timeThue.setText(formattedTime);
        } else {
            showAlert("Không tìm thấy khách hàng", "Không có thông tin khách hàng cho số điện thoại này.");
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
    public void handleThue(ActionEvent event) {
//        // Lấy thông tin từ giao diện
//        String soPhong = txtSoPhong.getText();
//        String soDienThoai = txtSDTKhachHang.getText();
//        String tenKhachHang = txtTenKhachHang.getText();
//        String namSinh = txtNamSinh.getText();
//        String gioiTinh = ccbGender.getValue().toString();
//        LocalDate ngayThue = dateThue.getValue();
//        String gioThue = timeThue.getText();
//
//        // Thực hiện các tác vụ thêm thông tin vào cơ sở dữ liệu hoặc xử lý theo yêu cầu của bạn
//        // ...
//
//        // Hiển thị thông báo hoặc thực hiện các hành động khác cần thiết
//        showAlert("Thông báo", "Đã thực hiện tác vụ thuê phòng!");
    }
    
    public boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber.matches("\\d{10}");
    }

    public void showAlert(String title, String content) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

}
