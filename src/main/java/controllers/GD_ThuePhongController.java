/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controllers;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
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
import main.App;
import model.ChiTietHD_Phong;
import model.HoaDonThanhToan;
import model.KhachHang;
import model.NhanVien;
import model.PhieuDatPhong;
import model.Phong;

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
        String soDienThoai = txtSDTKhachHang.getText().trim();
        if (!isValidPhoneNumber(soDienThoai)) {
            showAlert("Số điện thoại không hợp lệ", "Vui lòng nhập số điện thoại hợp lệ.");
            return;
        }

        KhachHang khachHang = KhachHang.getKhachHangTheoSoDienThoai(soDienThoai);

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
            showAlert("Không tìm thấy khách hàng", "Không có thông tin khách hàng cho số điện thoại này.Vui lòng thêm khách hàng trước khi đặt phòng!");
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
        String soPhong = txtSoPhong.getText();
        String soDienThoai = txtSDTKhachHang.getText();
        String tenKhachHang = txtTenKhachHang.getText();
        String namSinh = txtNamSinh.getText();
        String gioiTinh = ccbGender.getValue().toString();
        LocalDateTime ngayThue = dateThue.getValue().atStartOfDay();
        String gioThue = timeThue.getText();
        if (soDienThoai.equals(null)) {
            showAlert("Lỗi!", "Không được để trống Số điện thoại");
            return;
        } else if (!isValidPhoneNumber(soDienThoai)) {
            showAlert("Số điện thoại không hợp lệ", "Vui lòng nhập số điện thoại hợp lệ.");
            return;
        } else {
            Phong.updateStatusRoom(soPhong, 1);
        }

        String maHoaDon;
        String maNV = App.user;
        NhanVien nhanVienLap = NhanVien.getNhanVienTheoMaNhanVien(maNV);
        KhachHang khachHang = KhachHang.getKhachHangTheoSoDienThoai(soDienThoai);

        HoaDonThanhToan existingHoaDon = HoaDonThanhToan.getBillByCustomer(khachHang.getMaKhachHang());
        System.out.println(existingHoaDon);
        if (existingHoaDon != null) {
            // Nếu đã có hóa đơn, sử dụng mã hóa đơn đã có
            maHoaDon = existingHoaDon.getMaHoaDon();
            HoaDonThanhToan hoaDon = new HoaDonThanhToan(maHoaDon, nhanVienLap, khachHang, null, LocalDateTime.now());
            Phong p = Phong.getPhongTheoMaPhong(soPhong);

            ChiTietHD_Phong ctP = new ChiTietHD_Phong(hoaDon, p, LocalDateTime.now(), LocalDateTime.now().plusSeconds(1));
            System.out.println("da có hoa don thanh toan");
            ChiTietHD_Phong.themChiTietHoaDon(ctP);
        } else {
            // Nếu chưa có hóa đơn, tạo mới mã hóa đơn
            int slHoaDon = HoaDonThanhToan.getDemSoLuongHoaDonTheoNgay(ngayThue);
            maHoaDon = phatSinhMaHoaDon(slHoaDon);
            HoaDonThanhToan hoaDon = new HoaDonThanhToan(maHoaDon, nhanVienLap, khachHang, null, LocalDateTime.now());
            Phong p = Phong.getPhongTheoMaPhong(soPhong);

            ChiTietHD_Phong ctP = new ChiTietHD_Phong(hoaDon, p, LocalDateTime.now(), LocalDateTime.now().plusSeconds(1));
            System.out.println("chưa có hoa don thanh toan");
            ChiTietHD_Phong.themChiTietHoaDon(ctP);
            HoaDonThanhToan.themHoaDonThanhToan(hoaDon);
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
        alert.showAndWait();
    }

    public String phatSinhMaHoaDon(int stt) {
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
