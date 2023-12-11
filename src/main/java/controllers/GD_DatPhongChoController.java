/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controllers;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import main.App;
import model.KhachHang;
import model.NhanVien;
import model.PhieuDatPhong;
import model.Phong;

/**
 * FXML Controller class
 *
 * @author vie
 */
public class GD_DatPhongChoController implements Initializable {

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String roomID = GD_QLKinhDoanhPhongController.roomID;
        txtMaPhong.setText(roomID);
        for (int i = 0; i < 23; i++) {
            cbGioNhan.getItems().add(i);
        }
        for (int i = 0; i < 60; i++) {
            cbPhutNhan.getItems().add(i);
        }
        cbGioNhan.getSelectionModel().selectFirst();
        cbPhutNhan.getSelectionModel().selectFirst();
        dpNgayNhan.setValue(LocalDate.now());
        dpNgayNhan.setConverter(new StringConverter<LocalDate>() {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }

            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }

        });
        handleEventInTextField();
        handleEventInButton();
    }

    public void handleEventInTextField() {
        txtSDT.setOnKeyPressed(evt -> {
            if (evt.getCode() == KeyCode.ENTER) {
                KhachHang kh = KhachHang.getKhachHangTheoSoDienThoai(txtSDT.getText().trim());
                txtTenKH.setText(kh.getTenKhachHang());
            }
        });
    }

	public void handleEventInButton() {
		btnClose.setOnAction(evt -> {
			((Stage) txtMaPhong.getScene().getWindow()).close();
		});
		btnRefresh.setOnAction(evt -> {
			txtSDT.setText("");
			txtTenKH.setText("");
			dpNgayNhan.setValue(LocalDate.now());
			cbGioNhan.getSelectionModel().selectFirst();
			cbPhutNhan.getSelectionModel().selectFirst();
		});
		btnBookWaitingRoom.setOnAction(evt -> {
			try {
				String maPhieuDat = phatSinhMaPhieuDat(PhieuDatPhong.countBookingTicketInDay());
				KhachHang khachHang = KhachHang.getKhachHangTheoSoDienThoai(txtSDT.getText().trim());
				Phong phong = new Phong(txtMaPhong.getText());
				NhanVien nv = NhanVien.getNhanVienTheoMaNhanVien(App.user);
				LocalDateTime thoiGianLap = LocalDateTime.now();
				LocalDateTime thoiGianNhan = LocalDateTime.of(dpNgayNhan.getValue(), LocalTime.of(cbGioNhan.getValue(), cbPhutNhan.getValue()));
				String ghiChu = "";

                boolean result = PhieuDatPhong.themPhieDat(new PhieuDatPhong(maPhieuDat, khachHang, phong, nv, thoiGianLap, thoiGianNhan, false, ghiChu));
                if (result == true) {
                    Phong.updateStatusRoom(GD_QLKinhDoanhPhongController.roomID, 2);
                    App.setRoot("GD_QLKinhDoanhPhong");
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.OK);
                    alert.getDialogPane().setStyle("-fx-font-family: 'sans-serif';");
                    alert.setTitle("Đặt phòng thành công");
                    alert.setHeaderText("Bạn đã đặt phòng thành công!");
                    alert.showAndWait();
                    Stage stage = (Stage) ((Button) evt.getSource()).getScene().getWindow();
                    stage.close();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "", ButtonType.OK);
                    alert.getDialogPane().setStyle("-fx-font-family: 'sans-serif';");
                    alert.setTitle("Có lỗi xảy ra");
                    alert.setHeaderText("Có lỗi xảy ra khi đặt phòng chờ!");
                    alert.showAndWait();
                }
            } catch (Exception ex) {
                Logger.getLogger(GD_DatPhongChoController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    public String phatSinhMaPhieuDat(int stt) {
        Date ngayLap = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMdd");
        String ngayThangNam = dateFormat.format(ngayLap);

        String strSTT = String.format("%02d", stt + 1);

        SimpleDateFormat yearFormat = new SimpleDateFormat("yy");
        String namCuoi = yearFormat.format(ngayLap);
        String maHoaDon = "PD" + strSTT + ngayThangNam + namCuoi;

        return maHoaDon;
    }

    @FXML
    private Button btnClose;
    @FXML
    private Button btnRefresh;
    @FXML
    private Button btnBookWaitingRoom;
    @FXML
    private CheckBox ckbPrintTicket;
    @FXML
    private TextField txtMaPhong;
    @FXML
    private TextField txtSDT;
    @FXML
    private TextField txtTenKH;
    @FXML
    private DatePicker dpNgayNhan;
    @FXML
    private ComboBox<Integer> cbGioNhan;
    @FXML
    private ComboBox<Integer> cbPhutNhan;

}
