/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controllers;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.skin.TableViewSkin;
import javafx.scene.control.skin.VirtualFlow;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import model.KhachHang;

/**
 * FXML Controller class
 *
 * @author vie
 */
public class GD_QLKhachHangController implements Initializable {

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		genderGroup = new ToggleGroup();
		txtMaKhachHang.setText(phatSinhMaKhachHang());
		radioButtonNam.setToggleGroup(genderGroup);
		radioButtonNu.setToggleGroup(genderGroup);

//        Tạo index column
		sttCol.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(table.getItems().indexOf(param.getValue()) + 1));
		maKHCol.setCellValueFactory(new PropertyValueFactory<>("maKhachHang"));
		tenKHCol.setCellValueFactory(new PropertyValueFactory<>("tenKhachHang"));
		sdtCol.setCellValueFactory(new PropertyValueFactory<>("soDienThoai"));
		namSinhCol.setCellValueFactory(new PropertyValueFactory<>("namSinh"));
		gioiTinhCol.setCellValueFactory(cellData -> {
			boolean gioiTinh = cellData.getValue().isGioiTinh();
			String gioiTinhString;
			if (gioiTinh) {
				gioiTinhString = "Nam";
			} else {
				gioiTinhString = "Nữ";
			}
			return new ReadOnlyStringWrapper(gioiTinhString);

		});
		spinnerNamSinh.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1000, 3000, 2000));
		table.setItems(KhachHang.getAllKhachHang());
		table.requestFocus();
		table.getSelectionModel().select(0);
		table.getSelectionModel().focus(0);
		docDuLieuTuTable();
		handleEventInTextField();
		handleEventInTable();
	}

	public void handleEventInTable() {
		table.setOnMouseClicked((MouseEvent event) -> {
			docDuLieuTuTable();
		});
		table.setOnKeyPressed((KeyEvent event) -> {
			if (event.getCode() == KeyCode.UP || event.getCode() == KeyCode.DOWN) {
				docDuLieuTuTable();
			}
		});
	}

	public void handleEventInTextField() {
		txtSDT.setOnKeyTyped((event) -> {
			if (txtSDT.getText().trim().isEmpty()) {
				return;
			}
			if (!Pattern.matches("[\\d]*", txtSDT.getText().trim())) {
				txtSDT.setText(txtSDT.getText().trim().replaceAll("[^\\d]", ""));
				txtSDT.positionCaret(txtSDT.getText().length());
			}
		});
		spinnerNamSinh.getEditor().setOnKeyTyped((event) -> {
			TextField txtNamSinh = spinnerNamSinh.getEditor();
			if (!Pattern.matches("[\\d]*", txtNamSinh.getText().trim())) {
				txtNamSinh.setText(txtNamSinh.getText().trim().replaceAll("[^\\d]", ""));
			}
			if (txtNamSinh.getText().trim().isEmpty()) {
				txtNamSinh.setText("1000");
				return;
			}
			txtNamSinh.positionCaret(txtNamSinh.getText().length());
		});
		inputTimKiem.setOnKeyPressed((event) -> {
			if (event.getCode() == KeyCode.ENTER) {
				table.setItems(KhachHang.fillterCustomerByID_NameOrPhoneNumber(inputTimKiem.getText().trim()));
				table.refresh();
			}
		});
		inputTimKiem.focusedProperty().addListener((obs, oldVal, newVal) -> {
			if (!newVal) {
				table.setItems(KhachHang.fillterCustomerByID_NameOrPhoneNumber(inputTimKiem.getText().trim()));
				table.refresh();
			}
		});
	}

	public boolean validateData() {
		if (txtSDT.getText().trim().length() != 10) {
			Alert alert = new Alert(Alert.AlertType.ERROR, "", ButtonType.OK);
			alert.getDialogPane().setStyle("-fx-font-family: 'sans-serif';");
			alert.setTitle("Số điện thoại không phù hợp");
			alert.setHeaderText("Số điện thoại phải là số bao gồm 10 chữ số");
			alert.showAndWait();
			txtSDT.requestFocus();
			return false;
		}
		return true;
	}

//  Render and handle in View'
	public void docDuLieuTuTable() {
		KhachHang kh = table.getSelectionModel().getSelectedItem();
		if (kh == null) {
			return;
		}
		txtMaKhachHang.setText(kh.getMaKhachHang());
		txtTenKhachHang.setText(kh.getTenKhachHang());
		txtSDT.setText(kh.getSoDienThoai());
		spinnerNamSinh.getValueFactory().setValue(kh.getNamSinh());
		if (kh.isGioiTinh()) {
			genderGroup.getToggles().get(0).setSelected(true);
		} else {
			genderGroup.getToggles().get(1).setSelected(true);
		}
	}

	public void xuLyThemKhachHang() {
		String maKH = txtMaKhachHang.getText();
		String tenKH = txtTenKhachHang.getText().trim();
		String sdt = txtSDT.getText().trim();
		int namSinh = (Integer) spinnerNamSinh.getValue();
		boolean gioiTinh = true;
		if (genderGroup.getToggles().get(1).isSelected()) {
			gioiTinh = false;
		}
		if (KhachHang.getKhachHangTheoMaKhachHang(maKH) != null) {
			Alert alert = new Alert(Alert.AlertType.ERROR, "Vui lòng kiểm tra lại thông tin khách hàng", ButtonType.OK);
			alert.getDialogPane().setStyle("-fx-font-family: 'sans-serif';");
			alert.setTitle("Thêm khách hàng thất bại");
			alert.setHeaderText("Đã có thông tin khách hàng trong hệ thống");
			alert.showAndWait();
			return;
		}
		if (!validateData()) {
			return;
		}
		KhachHang kh = new KhachHang(maKH, tenKH, sdt, namSinh, gioiTinh);
		KhachHang.themKhachHang(kh);
		table.setItems(KhachHang.getAllKhachHang());
		VirtualFlow<?> vf = ((VirtualFlow<?>) ((TableViewSkin<?>) table.getSkin()).getChildren().get(1));
		vf.scrollTo(vf.getLastVisibleCell().getIndex());
		table.getSelectionModel().select(kh);
	}

	public void xuLySuaThongTinKhachHang() {
		String maKH = txtMaKhachHang.getText();
		String tenKH = txtTenKhachHang.getText().trim();
		String sdt = txtSDT.getText().trim();
		int namSinh = (Integer) spinnerNamSinh.getValue();
		boolean gioiTinh = true;
		if (genderGroup.getToggles().get(1).isSelected()) {
			gioiTinh = false;
		}
		if (!validateData()) {
			return;
		}
		KhachHang kh = new KhachHang(maKH, tenKH, sdt, namSinh, gioiTinh);
		KhachHang.suaKhachHang(kh);
		table.setItems(KhachHang.getAllKhachHang());
		table.refresh();
	}

	public void xuLyLamMoiThongTinKhachHang() {
		txtMaKhachHang.setText(phatSinhMaKhachHang());
		txtTenKhachHang.setText("");
		txtSDT.setText("");
		spinnerNamSinh.getValueFactory().setValue(2000);
		genderGroup.getToggles().get(0).setSelected(true);
		table.getSelectionModel().clearSelection();
	}

	public boolean kiemTraThongTinNhapVao() {
//		String 
		return true;
	}

	public String phatSinhMaKhachHang() {
		String maKH = "KH";
		DecimalFormat df = new DecimalFormat("0000");
		maKH = maKH.concat(df.format(KhachHang.demSoLuongKhachHang() + 1));
		return maKH;
	}

//    Variable
	@FXML
	private TextField txtMaKhachHang;
	@FXML
	private TextField txtTenKhachHang;
	@FXML
	private TextField txtSDT;
	@FXML
	private Spinner spinnerNamSinh;
	@FXML
	private RadioButton radioButtonNam;
	@FXML
	private RadioButton radioButtonNu;
	@FXML
	private ToggleGroup genderGroup;
	@FXML
	private TableView<KhachHang> table;
	@FXML
	private TableColumn<String, Integer> sttCol;
	@FXML
	private TableColumn<KhachHang, String> maKHCol;
	@FXML
	private TableColumn<KhachHang, String> tenKHCol;
	@FXML
	private TableColumn<KhachHang, String> sdtCol;
	@FXML
	private TableColumn<KhachHang, Integer> namSinhCol;
	@FXML
	private TableColumn<KhachHang, String> gioiTinhCol;
	@FXML
	private TextField inputTimKiem;
}
