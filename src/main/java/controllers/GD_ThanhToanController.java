/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controllers;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyProperty;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javax.swing.JOptionPane;
import main.App;
import model.ChiTietHD_DichVu;
import model.ChiTietHD_Phong;
import model.HoaDonThanhToan;
import model.Phong;

/**
 * FXML Controller class
 *
 * @author fil
 */
public class GD_ThanhToanController implements Initializable {

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		String maHD = HoaDonThanhToan.getBillIDByRoomID(GD_QLKinhDoanhPhongController.roomID);
		HoaDonThanhToan hd = HoaDonThanhToan.getBillByID(maHD);
		try {
			tenDichVuCol.setCellValueFactory(cellData -> {
				String tenDichVu = cellData.getValue().getDichVu().getTenDichVu();
				return new ReadOnlyStringWrapper(tenDichVu);
			});
			soLuongCol.setCellValueFactory(new PropertyValueFactory<>("soLuong"));
			donViTinhCol.setCellValueFactory(cellData -> {
				String donViTinh = cellData.getValue().getDichVu().getDonViTinh();
				return new ReadOnlyStringWrapper(donViTinh);
			});
			thanhTienDVCol.setCellValueFactory(cellData -> {
				long thanhTien = cellData.getValue().getThanhTien();
				return new ReadOnlyObjectWrapper<>(thanhTien);
			});

			tableDichVu.setItems(ChiTietHD_DichVu.getCTDichVuTheoMaHD(maHD));
		} catch (Exception ex) {
			Logger.getLogger(GD_ThanhToanController.class.getName()).log(Level.SEVERE, null, ex);
		}

		try {
			maPhongCol.setCellValueFactory(cellData -> {
				String maPhong = cellData.getValue().getPhong().getMaPhong();
				return new ReadOnlyStringWrapper(maPhong);
			});
			gioVaoCol.setCellValueFactory(cellData -> {
				String gioVao = cellData.getValue().getGioVao().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
				return new ReadOnlyObjectWrapper<String>(gioVao);
			});
			gioRaCol.setCellValueFactory(cellData -> {
				String gioRa = cellData.getValue().getGioRa().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
				return new ReadOnlyObjectWrapper<String>(gioRa);
			});
			thanhTienPCol.setCellValueFactory(cellData -> {
				float time = Duration.between(cellData.getValue().getGioVao(), cellData.getValue().getGioRa()).toMillis() / 1000;
				long giaPhong = cellData.getValue().getPhong().getGiaPhong();

				return new ReadOnlyObjectWrapper<Long>((long) (time * giaPhong));
			});

			tablePhong.setItems(ChiTietHD_Phong.getCT_PhongTheoMaHD(maHD));
		} catch (Exception ex) {
			Logger.getLogger(GD_ThanhToanController.class.getName()).log(Level.SEVERE, null, ex);
		}

		txtMaHoaDon.setText(maHD);
		txtNhanVien.setText(hd.getNhanVienLap().getHoTen());
		txtKhachHang.setText(hd.getKhachHang().getTenKhachHang());
		txtNgayLap.setText(hd.getNgayLap().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		long tienDV = 0;
		long tienPhong = 0;
		for (ChiTietHD_DichVu ct : tableDichVu.getItems()) {
			tienDV += ct.getThanhTien();
		}
		for (ChiTietHD_Phong ct : tablePhong.getItems()) {
			float time = Duration.between(ct.getGioVao(), ct.getGioRa()).toMillis() / 1000;
			long giaPhong = ct.getPhong().getGiaPhong();
			tienPhong += time * giaPhong;
		}
		txtTienDichVu.setText(tienDV + "");
		txtTienPhong.setText(tienPhong + "");
		txtTongTien.setText(Integer.parseInt(txtTienDichVu.getText()) + Integer.parseInt(txtTienPhong.getText()) + "");
		handleEventInputInput();
		handleEventInBtn();
	}

	public void handleEventInputInput() {
		txtTienNhan.setOnKeyPressed(evt -> {
			if (evt.getCode() == KeyCode.ENTER) {
				String tienNhan = txtTienNhan.getText().trim();
				String tongTien = txtTongTien.getText().trim();
				long tienThua = Integer.parseInt(tienNhan) - Integer.parseInt(tongTien);
				if (tienThua < 0) {
//					JOptionPane.showConfirmDialog(null, "Tiền nhận được ít hơn tổng tiền cần thanh toán! Vui lòng kiểm tra lại.");
				} else {
					txtTienThua.setText(tienThua + "");
				}
			}
		});
	}
	
	public void handleEventInBtn() {
		btnThanhToan.setOnAction(evt -> {
			try {
				LocalDateTime gioRa = LocalDateTime.now();
				ChiTietHD_Phong.suaChiTietHD_Phong(new ChiTietHD_Phong(
						HoaDonThanhToan.getBillByID(txtMaHoaDon.getText()),
						Phong.getPhongTheoMaPhong(txtMaPhong.getText()),
						LocalDateTime.now(),
						gioRa));
				Phong.updateStatusRoom(txtMaPhong.getText(), 0);
				Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.OK);
					alert.getDialogPane().setStyle("-fx-font-family: 'sans-serif';");
					alert.setTitle("Thanh toán phòng thành công");
					alert.setHeaderText("Bạn đã thanh toán phòng thành công!");
					alert.showAndWait();
				App.setRoot("GD_QLKinhDoanhPhong");
			} catch (Exception ex) {
				Logger.getLogger(GD_ThanhToanController.class.getName()).log(Level.SEVERE, null, ex);
			}
		});
	}

	//Bảng phòng
	@FXML
	private TextField txtMaPhong;
	@FXML
	private TextField txtNgay;
	@FXML
	private TextField txtGioVao;
	@FXML
	private TextField txtGioRa;
	@FXML
	private TextField txtTongGioSuDung;
	@FXML
	private TextField txtThanhTienP;

	//Bảng Dịch vụ
	@FXML
	private TextField txtTenDichVu;
	@FXML
	private TextField txtSoLuong;
	@FXML
	private TextField txtDonViTinh;
	@FXML
	private TextField txtThanhTienDV;

	@FXML
	private TableView<ChiTietHD_DichVu> tableDichVu;
	@FXML
	private TableColumn<ChiTietHD_DichVu, String> tenDichVuCol;
	@FXML
	private TableColumn<ChiTietHD_DichVu, Integer> soLuongCol;
	@FXML
	private TableColumn<ChiTietHD_DichVu, String> donViTinhCol;
	@FXML
	private TableColumn<ChiTietHD_DichVu, Long> thanhTienDVCol;

	@FXML
	private TextField txtMaKhuyenMai;
	@FXML
	private TextField txtTienNhan;
	@FXML
	private SplitMenuButton cbPhong;
	@FXML
	private TableView<ChiTietHD_Phong> tablePhong;
	@FXML
	private TableColumn<ChiTietHD_Phong, String> maPhongCol;
	@FXML
	private TableColumn<ChiTietHD_Phong, String> gioVaoCol;
	@FXML
	private TableColumn<ChiTietHD_Phong, String> gioRaCol;
	@FXML
	private TableColumn<ChiTietHD_Phong, Long> thanhTienPCol;
	@FXML
	private Button btnThanhToan;
	@FXML
	private Text txtMaHoaDon;
	@FXML
	private Text txtNhanVien;
	@FXML
	private Text txtKhachHang;
	@FXML
	private Text txtNgayLap;
	@FXML
	private Text txtTienPhong;
	@FXML
	private Text txtTienDichVu;
	@FXML
	private Text txtTongTien;
	@FXML
	private Text txtTienThua;
}
