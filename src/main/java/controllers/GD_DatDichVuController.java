/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controllers;

import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.skin.TableViewSkin;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.util.Callback;
import model.DichVu;
import main.App;
import model.ChiTietHD_DichVu;
import model.HoaDonThanhToan;
import model.Phong;

/**
 * FXML Controller class
 *
 * @author vie
 */
public class GD_DatDichVuController implements Initializable {

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		String roomID = GD_QLKinhDoanhPhongController.roomID;
		ObservableList<String> listRoomID = FXCollections.observableArrayList();
		Phong.getListPhongByStatus(1).forEach(phong -> {
			listRoomID.add(phong.getMaPhong());
		});
		cbPhong.setItems(listRoomID);
		cbPhong.getSelectionModel().select(GD_QLKinhDoanhPhongController.roomID);

//        Table Thong tin Dich vu
		ttMaDichVuCol.setCellValueFactory(new PropertyValueFactory<>("maDichVu"));
		ttTenDichVuCol.setCellValueFactory(new PropertyValueFactory<>("tenDichVu"));
		ttDonGiaCol.setCellValueFactory(new PropertyValueFactory<>("donGia"));
		ttSoLuongConLaiCol.setCellValueFactory(new PropertyValueFactory<>("soLuong"));
		ttDonViTinhCol.setCellValueFactory(new PropertyValueFactory<>("donViTinh"));
		ttThemCol.setCellValueFactory(new PropertyValueFactory<>(""));
		ttThemCol.setCellFactory(handleBtnAddTableThongTin());
		tableThongTinDichVu.setItems(DichVu.getAllDichVu());
		tableThongTinDichVu.requestFocus();
		tableThongTinDichVu.getSelectionModel().select(0);
		tableThongTinDichVu.getSelectionModel().focus(0);
		loadDataFromTableToForm();
		handleEventInTableThongTin();

//        Table Dich vu da them
		dtSttCol.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(tableDichVuDaThem.getItems().indexOf(param.getValue()) + 1));
		dtTenDichVuCol.setCellValueFactory(cellData -> {
			return new ReadOnlyObjectWrapper<>(cellData.getValue().getDichVu().getTenDichVu());
		});
		dtDonGiaCol.setCellValueFactory(cellData -> {
			long donGia = cellData.getValue().getDichVu().getDonGia();
			DecimalFormat df = new DecimalFormat("#,###,###,##0.## VND");
			return new ReadOnlyObjectWrapper<>(df.format(donGia));
		});
		dtDaThemCol.setCellValueFactory(new PropertyValueFactory<>("soLuong"));
		dtThanhTienCol.setCellValueFactory(cellData -> {
			int daThem = cellData.getValue().getSoLuong();
			long donGia = cellData.getValue().getDichVu().getDonGia();
			DecimalFormat df = new DecimalFormat("#,###,###,##0.##");
			return new ReadOnlyObjectWrapper<>(df.format(daThem * donGia));
		});
		dtThemCol.setCellValueFactory(new PropertyValueFactory<>(""));
		dtThemCol.setCellFactory(handleBtnAddTableDatDV());
		dtBotCol.setCellValueFactory(new PropertyValueFactory<>(""));
		dtBotCol.setCellFactory(handleBtnReduceTableDatDV());
		dtXoaCol.setCellValueFactory(new PropertyValueFactory<>(""));
		dtXoaCol.setCellFactory(handleBtnRemoveTableDatDV());

//        Hiển thị khung table khi không có dữ liệu
		tableDichVuDaThem.setSkin(new TableViewSkin<ChiTietHD_DichVu>(tableDichVuDaThem) {
			@Override
			public int getItemCount() {
				int r = super.getItemCount();
				return r == 0 ? 1 : r;
			}
		});
		renderInfoInGuiWithRoomID(roomID);

		calcMoney();
		handleEventInButton();
		handleEventInComboBox();
	}

	public Callback<TableColumn<DichVu, String>, TableCell<DichVu, String>> handleBtnAddTableThongTin() {
		return (new Callback<TableColumn<DichVu, String>, TableCell<DichVu, String>>() {
			@Override
			public TableCell call(final TableColumn<DichVu, String> param) {
				ImageView imgPlus = new ImageView(new Image("file:src/main/resources/image/circle-plus.png"));
				imgPlus.setFitWidth(25);
				imgPlus.setFitHeight(25);
				Button btn = new Button("", imgPlus);
				btn.getStyleClass().add("btnTable");
				final TableCell<DichVu, String> cell = new TableCell<DichVu, String>() {

					final Button btnAdd = btn;

					@Override
					public void updateItem(String item, boolean empty) {
						super.updateItem(item, empty);
						if (empty) {
							setGraphic(null);
							setText(null);
						} else {
							btn.setOnAction(event -> {
								try {
									boolean flag = false;
									DichVu dv = getTableView().getItems().get(getIndex());
									if (dv.getSoLuong() == 0) {
										Alert alert = new Alert(Alert.AlertType.ERROR, "Vui lòng chọn dịch vụ khác", ButtonType.OK);
										alert.getDialogPane().setStyle("-fx-font-family: 'sans-serif';");
										alert.setTitle("Hết hàng");
										alert.setHeaderText("Dịch vụ này đã hết");
										alert.showAndWait();
										return;
									}
									for (ChiTietHD_DichVu ct : dsDichVuDaDat) {
										if (ct.getDichVu().equals(dv)) {
											try {
												flag = true;
												ct.setSoLuong(ct.getSoLuong() + 1);
											} catch (IllegalArgumentException ex) {
												Logger.getLogger(GD_DatDichVuController.class.getName()).log(Level.SEVERE, null, ex);
											}
										}
									}
									if (!flag) {
										dsDichVuDaDat.add(new ChiTietHD_DichVu(bill, dv, 1));
									}
									dv.setSoLuong(dv.getSoLuong() - 1);
									getTableView().refresh();
									tableDichVuDaThem.refresh();
									getTableView().requestFocus();
									getTableView().getSelectionModel().select(getIndex());
									loadDataFromTableToForm();

								} catch (Exception ex) {
									Alert alert = new Alert(Alert.AlertType.ERROR, "Vui lòng chọn dịch vụ khác", ButtonType.OK);
									alert.getDialogPane().setStyle("-fx-font-family: 'sans-serif';");
									alert.setTitle("Hết hàng");
									alert.setHeaderText("Dịch vụ này đã hết");
									alert.showAndWait();
								}
								calcMoney();
							});
							setGraphic(btn);
							setText(null);
						}
					}
				};
				return cell;
			}
		});
	}

	public Callback<TableColumn<ChiTietHD_DichVu, String>, TableCell<ChiTietHD_DichVu, String>> handleBtnAddTableDatDV() {
		return (new Callback<TableColumn<ChiTietHD_DichVu, String>, TableCell<ChiTietHD_DichVu, String>>() {
			@Override
			public TableCell call(final TableColumn<ChiTietHD_DichVu, String> param) {
				ImageView imgPlus = new ImageView(new Image("file:src/main/resources/image/circle-plus.png"));
				imgPlus.setFitWidth(25);
				imgPlus.setFitHeight(25);
				Button btn = new Button("", imgPlus);
				btn.getStyleClass().add("btnTable");

				final TableCell<ChiTietHD_DichVu, String> cell = new TableCell<ChiTietHD_DichVu, String>() {

					final Button btnAdd = btn;

					@Override
					public void updateItem(String item, boolean empty) {
						super.updateItem(item, empty);
						if (empty) {
							setGraphic(null);
							setText(null);
						} else {
							btn.setOnAction(event -> {
								ChiTietHD_DichVu ct = getTableView().getItems().get(getIndex());
								ObservableList<DichVu> dsThongTinDV = tableThongTinDichVu.getItems();
								DichVu ttDichVu = dsThongTinDV.get(dsThongTinDV.indexOf(ct.getDichVu()));
								if (ttDichVu.getSoLuong() > 0) {
									try {
										ct.setSoLuong(ct.getSoLuong() + 1);
										ttDichVu.setSoLuong(ttDichVu.getSoLuong() - 1);
										getTableView().refresh();
										tableThongTinDichVu.refresh();
									} catch (Exception ex) {
										Logger.getLogger(GD_DatDichVuController.class.getName()).log(Level.SEVERE, null, ex);
									}
								} else {
									Alert alert;
									alert = new Alert(Alert.AlertType.ERROR, "Vui lòng chọn dịch vụ khác", ButtonType.OK);
									alert.getDialogPane().setStyle("-fx-font-family: 'sans-serif';");
									alert.setTitle("Hết hàng");
									alert.setHeaderText("Dịch vụ này đã hết");
									alert.showAndWait();
								}
								calcMoney();
							});
							setGraphic(btn);
							setText(null);
						}
					}
				};
				return cell;
			}
		});
	}

	public Callback<TableColumn<ChiTietHD_DichVu, String>, TableCell<ChiTietHD_DichVu, String>> handleBtnReduceTableDatDV() {
		return (new Callback<TableColumn<ChiTietHD_DichVu, String>, TableCell<ChiTietHD_DichVu, String>>() {
			@Override
			public TableCell call(final TableColumn<ChiTietHD_DichVu, String> param) {
				ImageView imgMinus = new ImageView(new Image("file:src/main/resources/image/minus.png"));
				imgMinus.setFitWidth(25);
				imgMinus.setFitHeight(25);
				Button btn = new Button("", imgMinus);
				btn.getStyleClass().add("btnTable");

				final TableCell<ChiTietHD_DichVu, String> cell = new TableCell<ChiTietHD_DichVu, String>() {
					final Button btnMinus = btn;

					@Override
					public void updateItem(String item, boolean empty) {
						super.updateItem(item, empty);
						if (empty) {
							setGraphic(null);
							setText(null);
						} else {
							btn.setOnAction(event -> {
								ChiTietHD_DichVu ct = getTableView().getItems().get(getIndex());
								ObservableList<DichVu> dsThongTinDV = tableThongTinDichVu.getItems();
								DichVu ttDichVu = dsThongTinDV.get(dsThongTinDV.indexOf(ct.getDichVu()));
								try {
									if (ct.getSoLuong() > 1) {
										ttDichVu.setSoLuong(ttDichVu.getSoLuong() + 1);
										ct.setSoLuong(ct.getSoLuong() - 1);
									} else {
										Alert alert = new Alert(Alert.AlertType.WARNING, "Nhấn YES để xác nhận, NO để hủy", ButtonType.YES, ButtonType.NO);
										alert.getDialogPane().setStyle("-fx-font-family: 'sans-serif';");
										alert.setTitle("Thông báo");
										alert.setHeaderText("Bạn có chắc muốn bỏ " + ct.getDichVu().getTenDichVu() + " ra khỏi danh sách đặt của mình không?");
										alert.showAndWait();
										if (alert.getResult() == ButtonType.YES) {
											getTableView().getItems().remove(ct);
											ttDichVu.setSoLuong(ttDichVu.getSoLuong() + ct.getSoLuong());
										} else {
											return;
										}
									}
									getTableView().refresh();
									tableThongTinDichVu.refresh();
								} catch (Exception ex) {
									Logger.getLogger(GD_DatDichVuController.class.getName()).log(Level.SEVERE, null, ex);
								}
								calcMoney();
							});
							setGraphic(btn);
							setText(null);
						}
					}
				};
				return cell;
			}
		});
	}

	public Callback<TableColumn<ChiTietHD_DichVu, String>, TableCell<ChiTietHD_DichVu, String>> handleBtnRemoveTableDatDV() {
		return (new Callback<TableColumn<ChiTietHD_DichVu, String>, TableCell<ChiTietHD_DichVu, String>>() {
			@Override
			public TableCell call(final TableColumn<ChiTietHD_DichVu, String> param) {
				ImageView imgRemove = new ImageView(new Image("file:src/main/resources/image/remove.png"));
				imgRemove.setFitWidth(25);
				imgRemove.setFitHeight(25);
				Button btn = new Button("", imgRemove);
				btn.getStyleClass().add("btnTable");

				final TableCell<ChiTietHD_DichVu, String> cell = new TableCell<ChiTietHD_DichVu, String>() {

					final Button btnRemove = btn;

					@Override
					public void updateItem(String item, boolean empty) {
						super.updateItem(item, empty);
						if (empty) {
							setGraphic(null);
							setText(null);
						} else {
							btn.setOnAction(event -> {
								try {
									ChiTietHD_DichVu ct = getTableView().getItems().get(getIndex());
									ObservableList<DichVu> dsThongTinDV = tableThongTinDichVu.getItems();
									DichVu ttDichVu = dsThongTinDV.get(dsThongTinDV.indexOf(ct.getDichVu()));

									Alert alert = new Alert(Alert.AlertType.WARNING, "Nhấn YES để xác nhận, NO để hủy", ButtonType.YES, ButtonType.NO);
									alert.getDialogPane().setStyle("-fx-font-family: 'sans-serif';");
									alert.setTitle("Thông báo");
									alert.setHeaderText("Bạn có chắc muốn bỏ " + ct.getDichVu().getTenDichVu() + " ra khỏi danh sách đặt của mình không?");
									alert.showAndWait();
									if (alert.getResult() == ButtonType.YES) {
										getTableView().getItems().remove(ct);
										ttDichVu.setSoLuong(ttDichVu.getSoLuong() + ct.getSoLuong());
										tableThongTinDichVu.refresh();
									} else {
										return;
									}

								} catch (Exception ex) {
									Logger.getLogger(GD_DatDichVuController.class.getName()).log(Level.SEVERE, null, ex);
								}
								calcMoney();
							});
							setGraphic(btn);
							setText(null);
						}
					}
				};
				return cell;
			}
		});
	}

	public void handleEventInTableThongTin() {
		tableThongTinDichVu.setOnMouseClicked((MouseEvent event) -> {
			loadDataFromTableToForm();
		});
		tableThongTinDichVu.setOnKeyPressed((KeyEvent event) -> {
			if (event.getCode() == KeyCode.UP || event.getCode() == KeyCode.DOWN) {
				loadDataFromTableToForm();
			}
		});
	}

	public void loadDataFromTableToForm() {
		DichVu dv = tableThongTinDichVu.getSelectionModel().getSelectedItem();
		txtTenDichVu.setText(dv.getTenDichVu());
		txtGiaDichVu.setText(dv.getDonGia() + "");
		Image img = new Image("file:src/main/resources/image/dich-vu/" + dv.getAnhMinhHoa());

		imageView.setImage(img);
	}

	public void handleEventInButton() {
		btnBack.setOnAction(evt -> {
			try {
				App.setRoot("GD_QLKinhDoanhPhong");
			} catch (IOException ex) {
				Logger.getLogger(GD_DatDichVuController.class.getName()).log(Level.SEVERE, null, ex);
			}
		});
		btnLamMoi.setOnAction(evt -> {
			txtTenDichVu.setText("");
			txtGiaDichVu.setText("");
		});
		btnDatDichVu.setOnAction(evt -> {
			String orderID = txtMaHoaDon.getText();
			dsDichVuDaDat.forEach(ct -> {
				try {
					DichVu.capNhatThongTinDichVu(tableThongTinDichVu.getItems().get(tableThongTinDichVu.getItems().indexOf(ct.getDichVu())));
					if (ChiTietHD_DichVu.getCTDichVuTheoMaHD(orderID).contains(ct)) {
						ChiTietHD_DichVu.updateChiTietHD_DichVu(ct);
					} else {
						ChiTietHD_DichVu.addChiTietHD_DichVu(ct);
					}
				} catch (Exception ex) {
					Logger.getLogger(GD_DatDichVuController.class.getName()).log(Level.SEVERE, null, ex);
				}
			});
			ChiTietHD_DichVu.getCTDichVuTheoMaHD(orderID).forEach(ct -> {
				if (!dsDichVuDaDat.contains(ct)) {
					ChiTietHD_DichVu.deleteChiTietHD_DichVu(ct);
				}
			});
			Alert alertSucces = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.OK);
			alertSucces.getDialogPane().setStyle("-fx-font-family: 'sans-serif';");
			alertSucces.setTitle("Thành công");
			alertSucces.setHeaderText("Đặt dịch vụ cho khách hàng thành công!");
			alertSucces.showAndWait();
		});
	}

	public void handleEventInComboBox() {
		cbPhong.setOnAction(evt -> {
			String roomID = cbPhong.getSelectionModel().getSelectedItem().toString();
			renderInfoInGuiWithRoomID(roomID);
		});
	}

	public void renderInfoInGuiWithRoomID(String roomID) {
		txtMaHoaDon.setText(HoaDonThanhToan.getBillIDByRoomID(roomID));
		bill = HoaDonThanhToan.getBillByID(txtMaHoaDon.getText().trim());
		txtMaKhachHang.setText(bill.getKhachHang().getMaKhachHang());
		txtTenKhachHang.setText(bill.getKhachHang().getTenKhachHang());
		try {
			dsDichVuDaDat = ChiTietHD_DichVu.getCTDichVuTheoMaHD(txtMaHoaDon.getText());
		} catch (Exception ex) {
			dsDichVuDaDat = FXCollections.observableArrayList();
			Logger.getLogger(GD_DatDichVuController.class.getName()).log(Level.SEVERE, null, ex);
		}
		tableDichVuDaThem.setItems(dsDichVuDaDat);
	}

	public void calcMoney() {
		long money = 0;
		for (ChiTietHD_DichVu ct : dsDichVuDaDat) {
			money += ct.getThanhTien();
		}
		DecimalFormat df = new DecimalFormat("#,###,###,##0.## VND");
		lbTongTien.setText(df.format(money));
	}

//    Variable
	private ObservableList<ChiTietHD_DichVu> dsDichVuDaDat;
	HoaDonThanhToan bill;
	@FXML
	private Button btnBack;
	@FXML
	private ComboBox cbPhong;
	@FXML
	private TextField txtMaKhachHang;
	@FXML
	private TextField txtTenKhachHang;
	@FXML
	private TextField txtMaHoaDon;
	@FXML
	private TextField txtTenDichVu;
	@FXML
	private TextField txtGiaDichVu;
	@FXML
	private ImageView imageView;
	@FXML
	private Button btnLamMoi;
	@FXML
	private TableView<DichVu> tableThongTinDichVu;
	@FXML
	private TableColumn<DichVu, String> ttMaDichVuCol;
	@FXML
	private TableColumn<DichVu, String> ttTenDichVuCol;
	@FXML
	private TableColumn<DichVu, Long> ttDonGiaCol;
	@FXML
	private TableColumn<DichVu, Integer> ttSoLuongConLaiCol;
	@FXML
	private TableColumn<DichVu, String> ttDonViTinhCol;
	@FXML
	private TableColumn ttThemCol;
	@FXML
	private TableView<ChiTietHD_DichVu> tableDichVuDaThem;
	@FXML
	private TableColumn<Integer, Integer> dtSttCol;
	@FXML
	private TableColumn<ChiTietHD_DichVu, String> dtTenDichVuCol;
	@FXML
	private TableColumn<ChiTietHD_DichVu, String> dtDonGiaCol;
	@FXML
	private TableColumn<ChiTietHD_DichVu, Integer> dtDaThemCol;
	@FXML
	private TableColumn<ChiTietHD_DichVu, String> dtThanhTienCol;
	@FXML
	private TableColumn dtThemCol;
	@FXML
	private TableColumn dtBotCol;
	@FXML
	private TableColumn dtXoaCol;
	@FXML
	private Text lbTongTien;
	@FXML
	private Button btnDatDichVu;

}
