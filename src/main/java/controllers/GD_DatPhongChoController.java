/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controllers;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
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
				String maPhieuDat = "P001";
				KhachHang khachHang = KhachHang.getKhachHangTheoSoDienThoai(txtSDT.getText().trim());
				Phong phong = new Phong(txtMaPhong.getText());
				NhanVien nv = NhanVien.getNhanVienTheoMaNhanVien(App.user);
				LocalDateTime thoiGianLap = LocalDateTime.now();
				LocalDateTime thoiGianNhan = LocalDateTime.of(dpNgayNhan.getValue(), LocalTime.of(cbGioNhan.getValue(), cbPhutNhan.getValue()));
				String ghiChu = "";

				PhieuDatPhong.themPhieDat(new PhieuDatPhong(maPhieuDat, khachHang, phong, nv, thoiGianLap, thoiGianNhan, ghiChu));
			} catch (Exception ex) {
				Logger.getLogger(GD_DatPhongChoController.class.getName()).log(Level.SEVERE, null, ex);
			}
		});
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
