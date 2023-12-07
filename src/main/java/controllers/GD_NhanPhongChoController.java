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
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
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
public class GD_NhanPhongChoController implements Initializable {

    @FXML
    private TextField txtSoPhong;
    @FXML
    private TextField txtSDTKhachHang;
    @FXML
    private TextField txtTenKhachHang;
    @FXML
    private TextField txtNamSinh;
    @FXML
    private Text timeNhanPhong;

    @FXML
    private ComboBox ccbGender;
    @FXML
    private ComboBox ccbMaPhieuDat;
    @FXML
    private DatePicker dateNhanPhong;

    @FXML
    private Button btnKiemTra;
    @FXML
    private Button btnExit;
    @FXML
    private Button btnRefresh;
    @FXML
    private Button btnNhanPhong;
    private boolean isComboBoxEventEnabled = true;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        txtSDTKhachHang.setOnAction(this::handleKiemTraSDT);
        btnKiemTra.setOnAction(this::handleKiemTraSDT);
        btnExit.setOnAction(this::handleExit);
        btnRefresh.setOnAction(this::handleRefresh);
        btnNhanPhong.setOnAction(this::handleNhanPhong);
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
            try {
                ObservableList<PhieuDatPhong> listPhieu = PhieuDatPhong.getAllBookingTicketByIDKhachHang(khachHang.getMaKhachHang());
                if (listPhieu != null) {
                    for (PhieuDatPhong phieuDat : listPhieu) {
                        ccbMaPhieuDat.getItems().add(phieuDat.getMaPhieuDat());
                    }
                    loadDuLieuDau(listPhieu.get(0));
                    ccbMaPhieuDat.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            if (isComboBoxEventEnabled) {
                                String selectedValue = (String) ccbMaPhieuDat.getValue();
                                PhieuDatPhong phieuDat = getPhieuByMaPhieu(listPhieu, selectedValue);
                                loadPhieuDat(phieuDat);
                                System.out.println("Mục đã chọn: " + selectedValue);
                            }
                        }
                    });
                } else {
                    showAlert("Không tìm thấy phòng đặt", "Không có thông tin về phòng chờ đã đặt!");
                }
            } catch (Exception ex) {
                Logger.getLogger(GD_NhanPhongChoController.class.getName()).log(Level.SEVERE, null, ex);
            }

            txtTenKhachHang.setText(khachHang.getTenKhachHang());
            txtNamSinh.setText(String.valueOf(khachHang.getNamSinh()));
            ccbGender.setValue(khachHang.isGioiTinh() ? "Nam" : "Nữ");
        } else {
            showAlert("Không tìm thấy khách hàng", "Không có thông tin khách hàng cho số điện thoại này. Vui lòng thêm khách hàng trước khi đặt phòng!");
        }
    }

    public PhieuDatPhong getPhieuByMaPhieu(ObservableList<PhieuDatPhong> list, String maPhieu) {
        for (PhieuDatPhong phieu : list) {
            if (phieu.getMaPhieuDat().equals(maPhieu)) {
                return phieu;
            }
        }
        return null;
    }

    private void loadDuLieuDau(PhieuDatPhong phieu) {
        String maPhieuDatTmp = (String) phieu.getMaPhieuDat();
        String maPhongTmp = phieu.getPhong().getMaPhong();
        LocalDate ngayNhanTmp = phieu.getThoiGianNhan().toLocalDate();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH 'giờ':mm 'phút'");
        String formattedTime = phieu.getThoiGianNhan().format(formatter);
        timeNhanPhong.setText(formattedTime);
        txtSoPhong.setText(maPhongTmp);
        ccbMaPhieuDat.setValue(maPhieuDatTmp);
        dateNhanPhong.setValue(ngayNhanTmp);
    }

    public void loadPhieuDat(PhieuDatPhong phieuDat) {
        txtSoPhong.setText(phieuDat.getPhong().getMaPhong());
        dateNhanPhong.setValue(phieuDat.getThoiGianNhan().toLocalDate());
        LocalDateTime currentTime = phieuDat.getThoiGianNhan();
         DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH 'giờ':mm 'phút'");
        String formattedTime = phieuDat.getThoiGianNhan().format(formatter);
        timeNhanPhong.setText(formattedTime);
    }

    @FXML
    public void handleRefresh(ActionEvent event) {
        isComboBoxEventEnabled = false;
        txtSoPhong.setText("");
        txtSDTKhachHang.setText("");
        ccbMaPhieuDat.getItems().removeAll();
        ccbMaPhieuDat.setValue("");
        txtTenKhachHang.clear();
        txtNamSinh.clear();
        ccbGender.getItems().removeAll();
        ccbGender.setValue("");
        dateNhanPhong.setValue(null);
        timeNhanPhong.setText("00 : 00");
    }

    @FXML
    public void handleExit(ActionEvent event) {
        isComboBoxEventEnabled = false;
        ccbMaPhieuDat.getItems().removeAll();
        ccbGender.getItems().removeAll();
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    public void handleNhanPhong(ActionEvent event) {
        // Lấy thông tin từ giao diện

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
