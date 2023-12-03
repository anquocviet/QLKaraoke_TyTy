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
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
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

		String id = Phong.getAllPhong().get(itemChoosed).getMaPhong();
		txtMaPhong.setText(id);
		gridPane.getChildren().get(itemChoosed).getStyleClass().remove("itemRoomActive");
		roomID = id;
		gridPane.getChildren().get(itemChoosed).getStyleClass().add("itemRoomActive");

		handleEventInRadioButton();
		handleEventInButton();
		
		txtPhongTrong.setText(String.format("Phòng trống(%s)", Phong.countStatusRoom(0)));
		txtPhongCho.setText(String.format("Phòng chờ(%s)", Phong.countStatusRoom(2)));
		txtPhongDangSD.setText(String.format("Phòng đang sử dụng(%s)", Phong.countStatusRoom(1)));
		txtPhongVIP.setText(String.format("Phòng VIP(%s)", Phong.countTypeRoom(1)));
	}

	public void renderArrayPhong(ObservableList<Phong> listPhong) {
		for (int i = 0; i < listPhong.size(); i++) {
			Phong phong = listPhong.get(i);
			gridPane.add(createViewRoomItem(
					phong.getLoaiPhong(), phong.getTinhTrang(), phong.getMaPhong()
			), i % 4, i / 4);
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
			gridPane.getChildren().get(itemChoosed).getStyleClass().remove("itemRoomActive");
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
				ObservableList<Phong> listRoom;
				if (newValue.equals(radioTypeAll)) {
					gridPane.getChildren().clear();
					listRoom = Phong.getListPhongByTypeAndStatus(new int[]{0, 1}, arrStatus);
				} else if (newValue.equals(radioTypeNormal)) {
					gridPane.getChildren().clear();
					listRoom = Phong.getListPhongByTypeAndStatus(new int[]{0, 0}, arrStatus);
				} else {
					gridPane.getChildren().clear();
					listRoom = Phong.getListPhongByTypeAndStatus(new int[]{1, 1}, arrStatus);
				}
				renderArrayPhong(listRoom);
				gridPane.getChildren().get(0).getStyleClass().add("itemRoomActive");
				txtMaPhong.setText(listRoom.get(0).getMaPhong());
				itemChoosed = 0;
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
				ObservableList<Phong> listRoom;
				if (newValue.equals(radioStatusAll)) {
					gridPane.getChildren().clear();
					listRoom = Phong.getListPhongByTypeAndStatus(arrType, new int[]{0, 1, 2});
				} else if (newValue.equals(radioStatusUsing)) {
					gridPane.getChildren().clear();
					listRoom = Phong.getListPhongByTypeAndStatus(arrType, new int[]{1, 1, 1});
				} else if (newValue.equals(radioStatusEmpty)) {
					gridPane.getChildren().clear();
					listRoom = Phong.getListPhongByTypeAndStatus(arrType, new int[]{0, 0, 0});
				} else {
					gridPane.getChildren().clear();
					listRoom = Phong.getListPhongByTypeAndStatus(arrType, new int[]{2, 2, 2});
				}
				renderArrayPhong(listRoom);
				gridPane.getChildren().get(0).getStyleClass().add("itemRoomActive");
				txtMaPhong.setText(listRoom.get(0).getMaPhong());
				itemChoosed = 0;

			}

		});
	}

	public void handleEventInButton() {
		btnRefresh.setOnAction(evt -> {
			typeRoomGroup.getToggles().get(0).setSelected(true);
			statusRoomGroup.getToggles().get(0).setSelected(true);

			gridPane.getChildren().clear();
			ObservableList<Phong> listRoom = Phong.getAllPhong();
			renderArrayPhong(listRoom);
			String id = listRoom.get(0).getMaPhong();
			txtMaPhong.setText(id);
			gridPane.getChildren().get(0).getStyleClass().add("itemRoomActive");
			roomID = id;
			itemChoosed = 0;
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
			moGDDatDichVu();
		}
		if (ke.getCode() == KeyCode.F5) {
			moGDChuyenPhong();
		}
		if (ke.getCode() == KeyCode.F6) {
			huyPhongCho();
		}
		if (ke.getCode() == KeyCode.F7) {
			moGDThanhToan();
		}
	}

	@FXML
	private void moGDThuePhong() throws IOException, Exception {
		if (!Phong.getListPhongByStatus(0).contains(new Phong(roomID))) {
			Alert alert = new Alert(Alert.AlertType.ERROR, "Vui lòng chọn một phòng phù hợp để thuê", ButtonType.OK);
			alert.getDialogPane().setStyle("-fx-font-family: 'sans-serif';");
			alert.setTitle("Có lỗi xảy ra");
			alert.setHeaderText("Bạn chưa chọn phòng trống để thuê!");
			alert.showAndWait();
		} else {
			App.openModal("GD_ThuePhong", App.widthModal, App.heightModal);
		}
	}

	@FXML
	private void moGDDatPhongCho() throws IOException, Exception {
		if (!Phong.getListPhongByStatus(0).contains(new Phong(roomID))) {
			Alert alert = new Alert(Alert.AlertType.ERROR, "Vui lòng chọn phòng trống để đặt.", ButtonType.OK);
			alert.getDialogPane().setStyle("-fx-font-family: 'sans-serif';");
			alert.setTitle("Có lỗi xảy ra");
			alert.setHeaderText("Phòng đang được sử dụng hoặc đang là phòng chờ!");
			alert.showAndWait();
		} else {
			App.openModal("GD_DatPhongCho", App.widthModal, App.heightModal);

		}
	}

	@FXML
	private void moGDNhanPhongCho() throws IOException {
	}

	@FXML
	private void huyPhongCho() throws IOException, Exception {
		if (!Phong.getListPhongByStatus(2).contains(new Phong(roomID))) {
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
				Alert alertSucces = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.OK);
				alertSucces.getDialogPane().setStyle("-fx-font-family: 'sans-serif';");
				alertSucces.setTitle("Thành công");
				alertSucces.setHeaderText("Hủy phòng chờ thành công!");
				alertSucces.showAndWait();
				txtPhongTrong.setText(String.format("Phòng trống(%s)", Phong.countStatusRoom(0)));
				txtPhongCho.setText(String.format("Phòng chờ(%s)", Phong.countStatusRoom(2)));
			}
		}
	}

	@FXML
	private void moGDChuyenPhong() throws IOException, Exception {
		if (!Phong.getListPhongByStatus(1).contains(new Phong(roomID))) {
			Alert alert = new Alert(Alert.AlertType.ERROR, "Vui lòng chọn phòng đang được sử dụng hoặc phòng chờ để chuyển", ButtonType.OK);
			alert.getDialogPane().setStyle("-fx-font-family: 'sans-serif';");
			alert.setTitle("Có lỗi xảy ra");
			alert.setHeaderText("Phòng không thể chuyển!");
			alert.showAndWait();
		} else {
			App.openModal("GD_ChuyenPhong", App.widthModal, App.heightModal);
		}
	}

	@FXML
	private void moGDDatDichVu() throws IOException, Exception {
		if (!Phong.getListPhongByStatus(1).contains(new Phong(roomID))) {
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
	private void moGDThanhToan() throws IOException, Exception {
		if (!Phong.getListPhongByStatus(1).contains(new Phong(roomID))) {
			Alert alert = new Alert(Alert.AlertType.ERROR, "Vui lòng chọn phòng đang được sử dụng để thanh toán", ButtonType.OK);
			alert.getDialogPane().setStyle("-fx-font-family: 'sans-serif';");
			alert.setTitle("Có lỗi xảy ra");
			alert.setHeaderText("Phòng này không thể đặt thanht toán!");
			alert.showAndWait();
		} else {
			App.setRoot("GD_ThanhToan");
		}
	}

	private static short itemChoosed = 0;
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
	private GridPane gridPane;
	@FXML
	private Text txtMaPhong;
	@FXML
	private Text txtPhongTrong;
	@FXML
	private Text txtPhongCho;
	@FXML
	private Text txtPhongDangSD;
	@FXML
	private Text txtPhongVIP;
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
