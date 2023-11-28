/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controllers;

import java.net.URL;
import java.text.DecimalFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Text;
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
			maPhongCol.setCellValueFactory((param) -> {
				String maHoaDon = param.getValue().getValue().getPhong().getMaPhong();
				return new ReadOnlyObjectWrapper<>(maHoaDon);
			});
			loaiPhongCol.setCellValueFactory((param) -> {
				if (param.getValue().getValue().getPhong() == null) {
					return new ReadOnlyObjectWrapper<>();
				}
				String loaiPhong = param.getValue().getValue().getPhong().getLoaiPhong() == 1 ? "VIP" : "Thường";
				return new ReadOnlyObjectWrapper<>(loaiPhong);
			});
			gioVaoCol.setCellValueFactory((param) -> {
				if (param.getValue().getValue().getGioVao() == null) {
					return new ReadOnlyObjectWrapper<>();
				}
				String gioVao = param.getValue().getValue().getGioVao().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
				return new ReadOnlyObjectWrapper<>(gioVao);
			});
			gioRaCol.setCellValueFactory((param) -> {
				if (param.getValue().getValue().getGioRa() == null) {
					return new ReadOnlyObjectWrapper<>();
				}
				String gioRa = param.getValue().getValue().getGioRa().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
				return new ReadOnlyObjectWrapper<>(gioRa);
			});
			gioSuDungCol.setCellValueFactory((param) -> {
				if (param.getValue().getValue().getGioVao() == null || param.getValue().getValue().getGioRa() == null) {
					return new ReadOnlyObjectWrapper<>();
				}
				float gioSuDung = param.getValue().getValue().tinhTongGioSuDung();
				return new ReadOnlyObjectWrapper<>(gioSuDung);
			});
			donGiaCol.setCellValueFactory((param) -> {
				if (param.getValue().getValue().getPhong() == null || param.getValue().getValue().getGioRa() == null) {
					return new ReadOnlyObjectWrapper<>();
				}
				long donGia = param.getValue().getValue().getPhong().getGiaPhong();
				return new ReadOnlyObjectWrapper<>(df.format(donGia));
			});
			thanhTienCol.setCellValueFactory((param) -> {
				if (param.getValue().getValue().getThanhTien() == 0) {
					return new ReadOnlyObjectWrapper<>();
				}
				float time = Duration.between(param.getValue().getValue().getGioVao(), param.getValue().getValue().getGioRa()).toMillis() / 1000;
				System.out.println(param.getValue().getValue().tinhTongGioSuDung());
				System.out.println(time);
				long giaPhong = param.getValue().getValue().getPhong().getGiaPhong();
				return new ReadOnlyObjectWrapper<String>(df.format((long) (time * giaPhong)));
			});
			ChiTietHD_Phong.getCT_PhongTheoMaHD(maHD).forEach(ct -> {
				try {
					TreeItem root = new TreeItem(new ChiTietHD_Phong(hd, new Phong(GD_QLKinhDoanhPhongController.roomID)));
					ct.setGioRa(LocalDateTime.now());
					TreeItem room = new TreeItem(ct);
					root.getChildren().add(room);
					tablePhong.setRoot(root);
				} catch (Exception ex) {
					Logger.getLogger(GD_ThanhToanController.class.getName()).log(Level.SEVERE, null, ex);
				}
			});
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
		for (TreeItem<ChiTietHD_Phong> ct : tablePhong.getRoot().getChildren()) {
			if (ct.getValue().getGioRa() == null) {
				continue;
			}
			float time = Duration.between(ct.getValue().getGioVao(), ct.getValue().getGioRa()).toMillis() / 1000;
			long giaPhong = ct.getValue().getPhong().getGiaPhong();
			tienPhong += time * giaPhong;
		}
		txtTienDichVu.setText(df.format(tienDV) + " VNĐ");
		txtTienPhong.setText(df.format(tienPhong) + " VNĐ");
		txtTongTien.setText(df.format(tienPhong + tienDV) + " VNĐ");
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

	DecimalFormat df = new DecimalFormat("#,###,###,##0.##");
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
	private TreeTableView<ChiTietHD_Phong> tablePhong;
	@FXML
	private TreeTableColumn<ChiTietHD_Phong, String> maPhongCol;
	@FXML
	private TreeTableColumn<ChiTietHD_Phong, String> loaiPhongCol;
	@FXML
	private TreeTableColumn<ChiTietHD_Phong, String> gioVaoCol;
	@FXML
	private TreeTableColumn<ChiTietHD_Phong, String> gioRaCol;
	@FXML
	private TreeTableColumn<ChiTietHD_Phong, Float> gioSuDungCol;
	@FXML
	private TreeTableColumn<ChiTietHD_Phong, String> donGiaCol;
	@FXML
	private TreeTableColumn<ChiTietHD_Phong, String> thanhTienCol;
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
