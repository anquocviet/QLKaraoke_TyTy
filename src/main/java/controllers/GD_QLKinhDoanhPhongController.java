/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controllers;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import javafx.util.Duration;
import main.App;
import model.Phong;

/**
 * FXML Controller class
 *
 * @author thangnood
 */
public class GD_QLKinhDoanhPhongController implements Initializable {

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		typeRoomGroup = new ToggleGroup();
		radioTypeAll.setToggleGroup(typeRoomGroup);
		radioTypeNormal.setToggleGroup(typeRoomGroup);
		radioTypeVIP.setToggleGroup(typeRoomGroup);
		statusRoomGroup = new ToggleGroup();
		radioStatusAll.setToggleGroup(statusRoomGroup);
		radioStatusUsing.setToggleGroup(statusRoomGroup);
		radioStatusEmpty.setToggleGroup(statusRoomGroup);
		radioStatusWaiting.setToggleGroup(statusRoomGroup);
		popup = new Popup();
		createClockView();
		renderArrayPhong(Phong.getAllPhong());
		handleEventInRadioButton();
		handleEventInButton();
		handleEventInInput();
	}

	public void renderArrayPhong(ObservableList<Phong> listPhong) {
		for (int i = 0; i < listPhong.size(); i++) {
			Phong phong = listPhong.get(i);
			gridPane.add(createViewRoomItem(
					phong.getLoaiPhong(), phong.getTinhTrang(), phong.getMaPhong()
			), i % 3, i / 3);
		}
	}

	public void createClockView() {
		Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
			SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

			String time = timeFormat.format(new Date());
			String date = dateFormat.format(new Date());

			clockLabel.setText(time);
			dateLabel.setText(date);
		}), new KeyFrame(Duration.seconds(1)));
		clock.setCycleCount(Timeline.INDEFINITE);
		clock.play();
	}

	public Pane createViewRoomItem(int loaiPhong, int tinhTrang, String maPhong) {
		String linkAnhPhong;
		switch (tinhTrang) {
			case 0:
				linkAnhPhong = loaiPhong == 0 ? "Blue-screen.png" : "Blue-screen-vip.png";
				break;
			case 1:
				linkAnhPhong = loaiPhong == 0 ? "Red-screen.png" : "Red-screen-vip.png";
				break;
			default:
				linkAnhPhong = loaiPhong == 0 ? "Orange-screen.png" : "Orange-screen-vip.png";
				break;
		}

		VBox roomItem = new VBox();
		roomItem.setCursor(Cursor.HAND);
		ImageView imgView = new ImageView();
		Image img = new Image("file:src/main/resources/image/" + linkAnhPhong);
		imgView.setImage(img);
		imgView.setFitWidth(120);
		imgView.setFitHeight(100);
		Label lblMaPhong = new Label(maPhong);
		lblMaPhong.setStyle("-fx-font-size: 18; -fx-font-weight: 600");
		lblMaPhong.setPadding(new Insets(0, 0, 8, 0));
		roomItem.getChildren().add(imgView);
		roomItem.getChildren().add(lblMaPhong);
		roomItem.setAlignment(Pos.CENTER);
		roomItem.getStyleClass().add("itemRoom");

		((Pane) roomItem).setOnMouseClicked(evt -> {
			txtMaPhong.setText(maPhong);
			if (itemChoosed != -1) {
				gridPane.getChildren().get(itemChoosed).getStyleClass().remove("itemRoomActive");
			}
			itemChoosed = (short) gridPane.getChildren().indexOf(roomItem);
			roomID = maPhong;
			roomItem.getStyleClass().add("itemRoomActive");

		});
		((Pane) roomItem).hoverProperty().addListener((obs, oldVal, newVal) -> {
//            if (newVal) {
//                try {
//                    FXMLLoader fxml = new FXMLLoader(App.class.getResource("/fxml/PopupPhong.fxml"));
//                    popup.getContent().add(fxml.load());
//                } catch (IOException ex) {
//                    Logger.getLogger(GD_QLKinhDoanhPhongController.class.getName()).log(Level.SEVERE, null, ex);
//                }
//                Bounds bnds = roomItem.localToScreen(roomItem.getLayoutBounds());
//                double x = bnds.getMinX();
//                double y = bnds.getMinY();
//                popup.show(roomItem, x, y);
//            } else {
//                popup.hide();
//            }
		});

		return roomItem;
	}

	public void handleEventInRadioButton() {
		typeRoomGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
			@Override
			public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
				int arrStatus[] = statusRoomGroup.getSelectedToggle().equals(radioStatusAll)
						? new int[]{0, 1, 2}
						: statusRoomGroup.getSelectedToggle().equals(radioStatusEmpty)
						? new int[]{0, 0, 0}
						: statusRoomGroup.getSelectedToggle().equals(radioStatusUsing)
						? new int[]{1, 1, 1}
						: new int[]{2, 2, 2};
				String idRoom = txtMaPhong.getText().trim();

				if (newValue.equals(radioTypeAll)) {
					gridPane.getChildren().clear();
					renderArrayPhong(Phong.getListPhongByID_Type_Status(idRoom, new int[]{0, 1}, arrStatus));
				} else if (newValue.equals(radioTypeNormal)) {
					gridPane.getChildren().clear();
					renderArrayPhong(Phong.getListPhongByID_Type_Status(idRoom, new int[]{0, 0}, arrStatus));
				} else {
					gridPane.getChildren().clear();
					renderArrayPhong(Phong.getListPhongByID_Type_Status(idRoom, new int[]{1, 1}, arrStatus));
				}
				if (itemChoosed != -1) {
					gridPane.getChildren().get(itemChoosed).getStyleClass().remove("itemRoomActive");
					itemChoosed = -1;
				}
			}

		});
		statusRoomGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
			@Override
			public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
				int arrType[] = typeRoomGroup.getSelectedToggle().equals(radioTypeAll)
						? new int[]{0, 1}
						: typeRoomGroup.getSelectedToggle().equals(radioTypeNormal)
						? new int[]{0, 0}
						: new int[]{1, 1};
				String idRoom = txtMaPhong.getText().trim();

				if (newValue.equals(radioStatusAll)) {
					gridPane.getChildren().clear();
					renderArrayPhong(Phong.getListPhongByID_Type_Status(idRoom, arrType, new int[]{0, 1, 2}));
				} else if (newValue.equals(radioStatusUsing)) {
					gridPane.getChildren().clear();
					renderArrayPhong(Phong.getListPhongByID_Type_Status(idRoom, arrType, new int[]{1, 1, 1}));
				} else if (newValue.equals(radioStatusEmpty)) {
					gridPane.getChildren().clear();
					renderArrayPhong(Phong.getListPhongByID_Type_Status(idRoom, arrType, new int[]{0, 0, 0}));
				} else {
					gridPane.getChildren().clear();
					renderArrayPhong(Phong.getListPhongByID_Type_Status(idRoom, arrType, new int[]{2, 2, 2}));
				}
				if (itemChoosed != -1) {
					gridPane.getChildren().get(itemChoosed).getStyleClass().remove("itemRoomActive");
					itemChoosed = -1;
				}

			}

		});
	}

	public void handleEventInButton() {
		btnRefresh.setOnAction(evt -> {
			typeRoomGroup.getToggles().get(0).setSelected(true);
			statusRoomGroup.getToggles().get(0).setSelected(true);
			txtMaPhong.setText("");
			gridPane.getChildren().get(itemChoosed).getStyleClass().remove("itemRoomActive");
		});
		btnFindRoom.setOnAction(evt -> {
			String idRoom = txtMaPhong.getText().trim();
			gridPane.getChildren().clear();
			renderArrayPhong(Phong.getListPhongByID(idRoom));
			itemChoosed = -1;
		});
	}

	public void handleEventInInput() {
		txtMaPhong.setOnKeyPressed((evt) -> {
			if (evt.getCode() == KeyCode.ENTER) {
				String idRoom = txtMaPhong.getText().trim();
				gridPane.getChildren().clear();
				renderArrayPhong(Phong.getListPhongByID(idRoom));
				itemChoosed = -1;
			}
		});
	}

	@FXML
	public void handleKeyboardEvent(KeyEvent ke) throws IOException, Exception {
		if (ke.getCode() == KeyCode.F1) {
			moGDThuePhong();
		}
		if (ke.getCode() == KeyCode.F2) {
			moGDDatPhongCho();
		}
		if (ke.getCode() == KeyCode.F3) {
			moGDNhanPhongCho();
		}
		if (ke.getCode() == KeyCode.F4) {
			huyPhongCho();
		}
		if (ke.getCode() == KeyCode.F5) {
			moGDDatDichVu();
		}
		if (ke.getCode() == KeyCode.F6) {
			moGDChuyenPhong();
		}
		if (ke.getCode() == KeyCode.F7) {
			moGDThanhToan();
		}
	}

	@FXML
	private void moGDThuePhong() throws IOException {
		if (itemChoosed != -1) {
			App.openModal("GD_ThuePhong", App.widthModal, App.heightModal);
		} else {
			Alert alert = new Alert(Alert.AlertType.ERROR, "Vui lòng chọn một phòng để thuê", ButtonType.OK);
			alert.getDialogPane().setStyle("-fx-font-family: 'sans-serif';");
			alert.setTitle("Có lỗi xảy ra");
			alert.setHeaderText("Bạn chưa chọn phòng để thuê!");
			alert.showAndWait();
		}
	}

	@FXML
	private void moGDDatPhongCho() throws IOException {
		App.openModal("GD_DatPhongCho", App.widthModal, App.heightModal);
	}

	@FXML
	private void moGDNhanPhongCho() throws IOException {
	}

	@FXML
	private void huyPhongCho() throws IOException, Exception {
		if (itemChoosed == -1) {
			Alert alert = new Alert(Alert.AlertType.ERROR, "Vui lòng chọn một phòng chờ.", ButtonType.OK);
			alert.getDialogPane().setStyle("-fx-font-family: 'sans-serif';");
			alert.setTitle("Có lỗi xảy ra");
			alert.setHeaderText("Bạn chưa chọn phòng để hủy làm phòng chờ!");
			alert.showAndWait();
		} else if (!Phong.getListPhongByStatus(2).contains(new Phong(roomID))) {
			Alert alert = new Alert(Alert.AlertType.ERROR, "Vui lòng chọn phòng chờ để hủy làm phòng chờ.", ButtonType.OK);
			alert.getDialogPane().setStyle("-fx-font-family: 'sans-serif';");
			alert.setTitle("Có lỗi xảy ra");
			alert.setHeaderText("Bạn cần chọn phòng chờ để hủy làm phòng chờ!");
			alert.showAndWait();
		} else {
			Alert alert = new Alert(Alert.AlertType.ERROR, "Chọn YES để đồng ý hủy, NO để hủy thao tác.", ButtonType.YES, ButtonType.NO);
			alert.getDialogPane().setStyle("-fx-font-family: 'sans-serif';");
			alert.setTitle("Cảnh bảo");
			alert.setHeaderText("Bạn có chắc muốn hủy phòng chờ này không?");
			alert.showAndWait();
			if (alert.getResult() == ButtonType.YES) {
				Phong.updateStatusRoom(roomID, 0);
				gridPane.getChildren().clear();
				renderArrayPhong(Phong.getAllPhong());
			}
		}
	}

	@FXML
	private void moGDChuyenPhong() throws IOException {
		App.openModal("GD_ChuyenPhong", App.widthModal, App.heightModal);
	}

	@FXML
	private void moGDDatDichVu() throws IOException, Exception {
		if (itemChoosed == -1) {
			Alert alert = new Alert(Alert.AlertType.ERROR, "Vui lòng chọn một phòng để thuê", ButtonType.OK);
			alert.getDialogPane().setStyle("-fx-font-family: 'sans-serif';");
			alert.setTitle("Có lỗi xảy ra");
			alert.setHeaderText("Bạn chưa chọn phòng để thuê!");
			alert.showAndWait();
		} else if (!Phong.getListPhongByStatus(1).contains(new Phong(roomID))) {
			Alert alert = new Alert(Alert.AlertType.ERROR, "Vui lòng chọn phòng đang được sử dụng để đặt dịch vụ", ButtonType.OK);
			alert.getDialogPane().setStyle("-fx-font-family: 'sans-serif';");
			alert.setTitle("Có lỗi xảy ra");
			alert.setHeaderText("Phòng không thể đặt dịch vụ!");
			alert.showAndWait();
		} else {
			App.setRoot("GD_DatDichVu");
		}
	}

	@FXML
	private void moGDThanhToan() throws IOException {
		App.setRoot("GD_ThanhToan");
	}


	private short itemChoosed = -1;
	public static String roomID;
	private Popup popup;
	@FXML
	ToggleGroup typeRoomGroup;
	@FXML
	ToggleGroup statusRoomGroup;
	@FXML
	private Label clockLabel;
	@FXML
	private Label dateLabel;
	@FXML
	private Button btnRefresh;
	@FXML
	private Button btnFindRoom;
	@FXML
	private GridPane gridPane;
	@FXML
	private TextField txtMaPhong;
	@FXML
	private RadioButton radioTypeAll;
	@FXML
	private RadioButton radioTypeNormal;
	@FXML
	private RadioButton radioTypeVIP;
	@FXML
	private RadioButton radioStatusAll;
	@FXML
	private RadioButton radioStatusUsing;
	@FXML
	private RadioButton radioStatusEmpty;
	@FXML
	private RadioButton radioStatusWaiting;

}