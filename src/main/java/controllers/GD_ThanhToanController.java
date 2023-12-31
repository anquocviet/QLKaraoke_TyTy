/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controllers;

import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.chrono.ChronoLocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.skin.TableViewSkin;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import main.App;
import model.CT_KhuyenMai;
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
			tableDichVu.setSkin(new TableViewSkin<ChiTietHD_DichVu>(tableDichVu) {
				@Override
				public int getItemCount() {
					int r = super.getItemCount();
					return r == 0 ? 1 : r;
				}
			});
		} catch (Exception ex) {
			Logger.getLogger(GD_ThanhToanController.class.getName()).log(Level.SEVERE, null, ex);
		}

		try {
			maPhongCol.setCellValueFactory((param) -> {
				String maHoaDon = param.getValue().getPhong().getMaPhong();
				return new ReadOnlyObjectWrapper<>(maHoaDon);
			});
			loaiPhongCol.setCellValueFactory((param) -> {
				String loaiPhong = param.getValue().getPhong().getLoaiPhong() == 1 ? "VIP" : "Thường";
				return new ReadOnlyObjectWrapper<>(loaiPhong);
			});
			gioVaoCol.setCellValueFactory((param) -> {
				String gioVao = param.getValue().getGioVao().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
				return new ReadOnlyObjectWrapper<>(gioVao);
			});
			gioRaCol.setCellValueFactory((param) -> {
				String gioRa = param.getValue().getGioRa().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
				return new ReadOnlyObjectWrapper<>(gioRa);
			});
			gioSuDungCol.setCellValueFactory((param) -> {
				String gioSuDung = df.format(param.getValue().tinhTongGioSuDung());
				return new ReadOnlyObjectWrapper<>(gioSuDung);
			});
			donGiaCol.setCellValueFactory((param) -> {
				long donGia = param.getValue().getPhong().getGiaPhong();
				LocalDateTime gioVao = param.getValue().getGioVao();
				if (gioVao.isAfter(LocalDateTime.of(gioVao.toLocalDate(), LocalTime.of(18, 0)))) {
					donGia += 50000;
				}
				return new ReadOnlyObjectWrapper<>(df.format(donGia));
			});
			thanhTienCol.setCellValueFactory((param) -> {
				return new ReadOnlyObjectWrapper<>(df.format(param.getValue().tinhThanhTien()));
			});

			ObservableList<ChiTietHD_Phong> dsPhong = ChiTietHD_Phong.getCT_PhongTheoMaHD(maHD);
			dsPhong.forEach(ct -> {
				try {
					ct.setGioRa(LocalDateTime.now());
				} catch (Exception ex) {
					Logger.getLogger(GD_ThanhToanController.class.getName()).log(Level.SEVERE, null, ex);
				}
			});
			tablePhong.setItems(dsPhong);
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
			tienPhong += ct.tinhThanhTien();
		}
		txtTienDichVu.setText(df.format(tienDV) + " VNĐ");
		txtTienPhong.setText(df.format(tienPhong) + " VNĐ");

		tongTien = tienPhong + tienDV;
		long tienVAT = (long) (tongTien * (App.VAT / 100.0));
//		long tienThueTTDB = (long) (tongTien - (tongTien / (1 + App.TTDB / 100.0)));
//		tongTien = tongTien + tienVAT + tienThueTTDB;
//		txtTienThue.setText(df.format(tienVAT + tienThueTTDB) + " VNĐ");
		tongTien = tongTien + tienVAT;
		txtTienThue.setText(df.format(tienVAT) + " VNĐ");
		txtTongTien.setText(df.format(tongTien) + " VNĐ");
		handleEventInInput();
		handleEventInBtn();
	}

	public void handleEventInInput() {
		txtTienNhan.setOnKeyReleased(evt -> {
			if (txtTienNhan.getText().trim().isEmpty()) {
				return;
			}
			if (!Pattern.matches("[\\d,]*", txtTienNhan.getText().trim())) {
				txtTienNhan.setText(txtTienNhan.getText().trim().replaceAll("[^\\d,]", ""));
				txtTienNhan.positionCaret(txtTienNhan.getText().length());
			}
			try {
				tienNhan = Long.parseLong(txtTienNhan.getText().trim());
			} catch (NumberFormatException nf) {
				try {
					tienNhan = df.parse(txtTienNhan.getText().trim()).longValue();
				} catch (ParseException ex) {
					Logger.getLogger(GD_ThanhToanController.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
			long tienThua = tienNhan - tongTien;
			if (tienThua > 0) {
				btnThanhToan.setDisable(false);
				txtTienThua.setText(df.format(tienThua) + " VND");
			} else {
				btnThanhToan.setDisable(true);
				txtTienThua.setText("0 VND");
			}
		});
		txtMaKhuyenMai.setOnKeyReleased((evt) -> {
			CT_KhuyenMai ctkm = CT_KhuyenMai.getCT_KhuyenMaiTheoMaKM(txtMaKhuyenMai.getText().trim());
			long tienDV = 0;
			long tienPhong = 0;
			for (ChiTietHD_DichVu ct : tableDichVu.getItems()) {
				tienDV += ct.getThanhTien();
			}
			for (ChiTietHD_Phong ct : tablePhong.getItems()) {
				tienPhong += ct.tinhThanhTien();
			}
			long tong = tienPhong + tienDV;
			long tienVAT = (long) (tong * (App.VAT / 100.0));
			tong += tienVAT;
			tongTien = tong;
			if (checkUseVoucher(ctkm)) {
				tienGiam = tong * ctkm.getChietKhau() / 100;
				txtTienDaGiam.setText(df.format(tienGiam) + " VND");
				imgCheckKM.setImage(new Image("file:src/main/resources/image/check.png"));
				tongTien = tong - tienGiam;
			} else {
				imgCheckKM.setImage(new Image("file:src/main/resources/image/check_false.png"));
				txtTienDaGiam.setText(0 + " VND");
			}
			txtTongTien.setText(df.format(tongTien) + " VND");
		});
	}

	public void handleEventInBtn() {
		btnThanhToan.setOnAction(evt -> {
			try {
				if (txtTienThua.getText().equals("0")) {
					Alert alert = new Alert(Alert.AlertType.ERROR, "Vui lòng kiểm tra lại tiền nhận!", ButtonType.OK);
					alert.getDialogPane().setStyle("-fx-font-family: 'sans-serif';");
					alert.setTitle("Lỗi");
					alert.setHeaderText("Tiền nhận không phù hợp");
					alert.showAndWait();
					return;
				}
				CT_KhuyenMai km = CT_KhuyenMai.getCT_KhuyenMaiTheoMaKM(txtMaKhuyenMai.getText().trim().toUpperCase());
				if (checkUseVoucher(km)) {
					CT_KhuyenMai.capNhatLuotSuDungKhuyenMai(km.getMaKhuyenMai(), km.getLuotSuDungConLai() - 1);
				} else {
					km = new CT_KhuyenMai("DEFAULT");
				}
				tablePhong.getItems().forEach((ct) -> {
					ChiTietHD_Phong.updateCTHD_Phong(ct);
					Phong.updateStatusRoom(ct.getPhong().getMaPhong(), 2);
				});
				String maHD = HoaDonThanhToan.getBillIDByRoomID(GD_QLKinhDoanhPhongController.roomID);
				HoaDonThanhToan hd = HoaDonThanhToan.getBillByID(maHD);
				hd.setKhuyenMai(km);
				hd.setNgayLap(LocalDateTime.now());
				HoaDonThanhToan.updateHoaDonThanhToan(hd, tongTien);
				Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.OK);
				alert.getDialogPane().setStyle("-fx-font-family: 'sans-serif';");
				alert.setTitle("Thanh toán phòng thành công");
				alert.setHeaderText("Bạn đã thanh toán phòng thành công!");
				alert.showAndWait();

//				Xuat hoa don
				if (checkBoxInHD.isSelected()) {
					App.openModal("Bill", App.widthModalBill, App.heightModalBill);
				}

				App.setRoot("GD_QLKinhDoanhPhong");
			} catch (IOException | IllegalArgumentException ex) {
				Logger.getLogger(GD_ThanhToanController.class.getName()).log(Level.SEVERE, null, ex);
			}
		});
		btnBack.setOnAction(evt -> {
			try {
				App.setRoot("GD_QLKinhDoanhPhong");
			} catch (IOException ex) {
				Logger.getLogger(GD_DatDichVuController.class.getName()).log(Level.SEVERE, null, ex);
			}
		});
	}

	public boolean checkUseVoucher(CT_KhuyenMai km) {
		return km != null && km.getLuotSuDungConLai() > 0 && km.getNgayKetThuc().isAfter(LocalDate.now());
	}

	private long tongTien;
	public static long tienNhan = 0;
	public static long tienGiam = 0;
	DecimalFormat df = new DecimalFormat("#,###,###,##0.##");

	@FXML
	private Button btnBack;
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
	private TableView<ChiTietHD_Phong> tablePhong;
	@FXML
	private TableColumn<ChiTietHD_Phong, String> maPhongCol;
	@FXML
	private TableColumn<ChiTietHD_Phong, String> loaiPhongCol;
	@FXML
	private TableColumn<ChiTietHD_Phong, String> gioVaoCol;
	@FXML
	private TableColumn<ChiTietHD_Phong, String> gioRaCol;
	@FXML
	private TableColumn<ChiTietHD_Phong, String> gioSuDungCol;
	@FXML
	private TableColumn<ChiTietHD_Phong, String> donGiaCol;
	@FXML
	private TableColumn<ChiTietHD_Phong, String> thanhTienCol;
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
	private Text txtTienThue;
	@FXML
	private Text txtTongTien;
	@FXML
	private Text txtTienDaGiam;
	@FXML
	private Text txtTienThua;
	@FXML
	private ImageView imgCheckKM;
	@FXML
	private CheckBox checkBoxInHD;
}
