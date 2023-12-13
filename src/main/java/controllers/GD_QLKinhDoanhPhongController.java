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
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Duration;
import main.App;
import model.PhieuDatPhong;
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
        spinnerSucChua.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 20, 1));

        createClockView();
        renderArrayPhong(Phong.getAllPhong());

        String id = Phong.getAllPhong().get(itemChoosed).getMaPhong();
        txtMaPhong.setText(id);
        gridPane.getChildren().get(itemChoosed).getStyleClass().remove("itemRoomActive");
        roomID = id;
        gridPane.getChildren().get(itemChoosed).getStyleClass().add("itemRoomActive");

        txtPhongTrong.setText(String.format("Phòng trống(%s)", Phong.countStatusRoom(0)));
        txtPhongCho.setText(String.format("Phòng chờ(%s)", Phong.countStatusRoom(2)));
        txtPhongDangSD.setText(String.format("Phòng đang sử dụng(%s)", Phong.countStatusRoom(1)));
        txtPhongVIP.setText(String.format("Phòng VIP(%s)", Phong.countTypeRoom(1)));

        handleEventInRadioButton();
        handleEventInSpinner();
        handleEventInButton();
    }

    public void renderArrayPhong(ObservableList<Phong> listPhong) {
        for (int i = 0; i < listPhong.size(); i++) {
            Phong phong = listPhong.get(i);
            gridPane.add(createViewRoomItem(phong), i % 4, i / 4);
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

    public Pane createViewRoomItem(Phong phong) {
        String linkAnhPhong;
        switch (phong.getTinhTrang()) {
            case 0:
                linkAnhPhong = phong.getLoaiPhong() == 0 ? "Blue-screen.png" : "Blue-screen-vip.png";
                break;
            case 1:
                linkAnhPhong = phong.getLoaiPhong() == 0 ? "Red-screen.png" : "Red-screen-vip.png";
                break;
            default:
                linkAnhPhong = phong.getLoaiPhong() == 0 ? "Orange-screen.png" : "Orange-screen-vip.png";
                break;
        }

        VBox roomItem = new VBox();
        roomItem.setCursor(Cursor.HAND);
        ImageView imgView = new ImageView();
        Image img = new Image("file:src/main/resources/image/" + linkAnhPhong);
        imgView.setImage(img);
        imgView.setFitWidth(120);
        imgView.setFitHeight(100);
        roomItem.getChildren().add(imgView);

        Label lblMaPhong = new Label(phong.getMaPhong());
        lblMaPhong.setStyle("-fx-font-size: 18; -fx-font-weight: 700");
        lblMaPhong.setPadding(new Insets(0, 0, 8, 0));
        roomItem.getChildren().add(lblMaPhong);

        Label lblSucChua = new Label("Tối đa: " + phong.getSucChua() + " người");
        lblSucChua.setStyle("-fx-font-size: 18; -fx-font-weight: 600");
        lblSucChua.setPadding(new Insets(0, 0, 8, 0));
        roomItem.getChildren().add(lblSucChua);
//		Kiểm tra thêm thông tin của phòng chờ
        if (phong.getTinhTrang() == 2) {
            try {
                PhieuDatPhong phieu = PhieuDatPhong.getBookingTicketOfRoom(phong.getMaPhong());
                if (phieu != null) {
                    Label lblGioNhan = new Label("Giờ nhận: " + dtf.format(phieu.getThoiGianNhan()));
                    lblGioNhan.setStyle("-fx-font-size: 16; -fx-font-weight: 600");
                    lblGioNhan.setPadding(new Insets(0, 0, 8, 0));
                    roomItem.getChildren().add(lblGioNhan);
                }
            } catch (Exception ex) {
                Logger.getLogger(GD_QLKinhDoanhPhongController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        String strBtnLeft = phong.getTinhTrang() == 0 ? "Thuê phòng"
                : phong.getTinhTrang() == 1 ? "Đặt dịch vụ" : "Hủy phòng";
        String strBtnRight = phong.getTinhTrang() == 0 ? "Đặt phòng"
                : phong.getTinhTrang() == 1 ? "Thanh toán" : "Nhận phòng";
        Button btnLeft = new Button(strBtnLeft);
        Button btnRight = new Button(strBtnRight);
        switch (phong.getTinhTrang()) {
            case 0:
                btnLeft.setStyle("-fx-background-color: #FBFF16; -fx-font-size: 16");
                btnLeft.setOnAction((event) -> {
                    roomID = phong.getMaPhong();
                    moGDThuePhong();
                });
                btnRight.setOnAction((event) -> {
                    roomID = phong.getMaPhong();
                    moGDDatPhongCho();
                });
                break;
            case 1:
                btnLeft.setStyle("-fx-background-color: #4078E3; -fx-text-fill: #fff; -fx-font-size: 16");
                btnLeft.setOnAction(((event) -> {
                    roomID = phong.getMaPhong();
                    moGDDatDichVu();
                }));
                btnRight.setOnAction((event) -> {
                    roomID = phong.getMaPhong();
                    moGDThanhToan();
                });
                break;
            default:
                btnLeft.setStyle("-fx-background-color: #CF2A27; -fx-text-fill: #fff; -fx-font-size: 16");
                btnLeft.setOnAction((event) -> {
                    roomID = phong.getMaPhong();
                    huyPhongCho();
                });
                btnRight.setOnAction((event) -> {
                    roomID = phong.getMaPhong();
                    moGDNhanPhongCho();
                });
                 {
                    try {
                        if (PhieuDatPhong.getBookingTicketOfRoom(phong.getMaPhong()) == null) {
                            btnRight.setDisable(true);
                        }
                    } catch (Exception ex) {
                        Logger.getLogger(GD_QLKinhDoanhPhongController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                break;

        }
        btnRight.setStyle("-fx-background-color: #379F10; -fx-text-fill: #fff; -fx-font-size: 16");
        HBox hbox = new HBox(btnLeft, btnRight);
        hbox.setSpacing(30);
        hbox.setPadding(new Insets(0, 0, 8, 0));
        hbox.setAlignment(Pos.CENTER);
        hbox.setVisible(false);
        roomItem.getChildren().add(hbox);

        roomItem.setAlignment(Pos.CENTER);
        roomItem.getStyleClass().add("itemRoom");

        ((Pane) roomItem).setOnMouseClicked(evt -> {
            txtMaPhong.setText(phong.getMaPhong());
            gridPane.getChildren().get(itemChoosed).getStyleClass().remove("itemRoomActive");
            itemChoosed = (short) gridPane.getChildren().indexOf(roomItem);
            roomID = phong.getMaPhong();
            roomItem.getStyleClass().add("itemRoomActive");
        });

        ((Pane) roomItem).hoverProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                hbox.setVisible(true);
            } else {
                hbox.setVisible(false);
            }
        });

        return roomItem;
    }

    public void handleEventInRadioButton() {
        typeRoomGroup.selectedToggleProperty().addListener((ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) -> {
            int arrStatus[] = statusRoomGroup.getSelectedToggle().equals(radioStatusAll)
                    ? new int[]{0, 1, 2}
                    : statusRoomGroup.getSelectedToggle().equals(radioStatusEmpty)
                    ? new int[]{0, 0, 0}
                    : statusRoomGroup.getSelectedToggle().equals(radioStatusUsing)
                    ? new int[]{1, 1, 1}
                    : new int[]{2, 2, 2};
            ObservableList<Phong> listRoom;
            int capacity = spinnerSucChua.getValue();
            if (newValue.equals(radioTypeAll)) {
                gridPane.getChildren().clear();
                listRoom = Phong.getListPhongByType_Status_Capacity(new int[]{0, 1}, arrStatus, capacity);
            } else if (newValue.equals(radioTypeNormal)) {
                gridPane.getChildren().clear();
                listRoom = Phong.getListPhongByType_Status_Capacity(new int[]{0, 0}, arrStatus, capacity);
            } else {
                gridPane.getChildren().clear();
                listRoom = Phong.getListPhongByType_Status_Capacity(new int[]{1, 1}, arrStatus, capacity);
            }
            renderArrayPhong(listRoom);
            if (!listRoom.isEmpty()) {
                gridPane.getChildren().get(0).getStyleClass().add("itemRoomActive");
                txtMaPhong.setText(listRoom.get(0).getMaPhong());
            }
            itemChoosed = 0;
        });
        statusRoomGroup.selectedToggleProperty().addListener((ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) -> {
            int arrType[] = typeRoomGroup.getSelectedToggle().equals(radioTypeAll)
                    ? new int[]{0, 1}
                    : typeRoomGroup.getSelectedToggle().equals(radioTypeNormal)
                    ? new int[]{0, 0}
                    : new int[]{1, 1};
            ObservableList<Phong> listRoom;
            int capacity = spinnerSucChua.getValue();
            if (newValue.equals(radioStatusAll)) {
                gridPane.getChildren().clear();
                listRoom = Phong.getListPhongByType_Status_Capacity(arrType, new int[]{0, 1, 2}, capacity);
            } else if (newValue.equals(radioStatusUsing)) {
                gridPane.getChildren().clear();
                listRoom = Phong.getListPhongByType_Status_Capacity(arrType, new int[]{1, 1, 1}, capacity);
            } else if (newValue.equals(radioStatusEmpty)) {
                gridPane.getChildren().clear();
                listRoom = Phong.getListPhongByType_Status_Capacity(arrType, new int[]{0, 0, 0}, capacity);
            } else {
                gridPane.getChildren().clear();
                listRoom = Phong.getListPhongByType_Status_Capacity(arrType, new int[]{2, 2, 2}, capacity);
            }
            renderArrayPhong(listRoom);
            if (!listRoom.isEmpty()) {
                gridPane.getChildren().get(0).getStyleClass().add("itemRoomActive");
                txtMaPhong.setText(listRoom.get(0).getMaPhong());
            }
            itemChoosed = 0;
        });
    }

    public void handleEventInSpinner() {
        spinnerSucChua.valueProperty().addListener((obs, oldVal, newVal) -> {
            int arrType[] = typeRoomGroup.getSelectedToggle().equals(radioTypeAll)
                    ? new int[]{0, 1}
                    : typeRoomGroup.getSelectedToggle().equals(radioTypeNormal)
                    ? new int[]{0, 0}
                    : new int[]{1, 1};
            int arrStatus[] = statusRoomGroup.getSelectedToggle().equals(radioStatusAll)
                    ? new int[]{0, 1, 2}
                    : statusRoomGroup.getSelectedToggle().equals(radioStatusEmpty)
                    ? new int[]{0, 0, 0}
                    : statusRoomGroup.getSelectedToggle().equals(radioStatusUsing)
                    ? new int[]{1, 1, 1}
                    : new int[]{2, 2, 2};
            gridPane.getChildren().clear();
            ObservableList<Phong> listRoom;
            listRoom = Phong.getListPhongByType_Status_Capacity(arrType, arrStatus, newVal);
            renderArrayPhong(listRoom);
            if (!listRoom.isEmpty()) {
                gridPane.getChildren().get(0).getStyleClass().add("itemRoomActive");
                txtMaPhong.setText(listRoom.get(0).getMaPhong());
            }
            itemChoosed = 0;
        });
        spinnerSucChua.getEditor().setOnKeyTyped((event) -> {
            TextField txtSucChua = spinnerSucChua.getEditor();
            if (!Pattern.matches("[\\d]*", txtSucChua.getText().trim())) {
                txtSucChua.setText(txtSucChua.getText().trim().replaceAll("[^\\d]", ""));
            }
            if (txtSucChua.getText().trim().isEmpty()) {
                txtSucChua.setText("1");
                return;
            }
            txtSucChua.positionCaret(txtSucChua.getText().length());
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
            spinnerSucChua.getValueFactory().setValue(1);
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
    private void moGDThuePhong() {
        try {
            LocalDateTime gioMoCua = LocalDateTime.of(LocalDate.now(), LocalTime.of(8, 0)); // Giờ mở cửa là 8AM
            LocalDateTime gioDongCua = LocalDateTime.of(LocalDate.now(), LocalTime.of(23, 30)); // Giờ đóng cửa là 11:30PM

            LocalDateTime thoiGianHienTai = LocalDateTime.now();

            if (thoiGianHienTai.isAfter(gioMoCua) && thoiGianHienTai.isBefore(gioDongCua)) {
                if (Phong.getListPhongByStatus(1).contains(new Phong(roomID))) {
                    showAlert("Phòng không phù hợp!", "Vui lòng chọn phòng trống thuê để thuê phòng!");
                } else if (Phong.getListPhongByStatus(0).contains(new Phong(roomID))) {
                    App.openModal("GD_ThuePhong", App.widthModal, App.heightModal);
                } else {
                    PhieuDatPhong phieuDat = PhieuDatPhong.getBookingTicketOfRoom(roomID);
                    if (phieuDat != null) {
                        LocalDateTime thoiGianNhan = phieuDat.getThoiGianNhan();
                        if (Phong.getListPhongByStatus(2).contains(new Phong(roomID))) {
                            // Kiểm tra nếu thời gian hiện tại cách thời gian nhận phòng ít nhất 4 giờ
                            if (thoiGianHienTai.isAfter(thoiGianNhan.plusHours(4))) {
                                App.openModal("GD_ThuePhong", App.widthModal, App.heightModal);
                            } else {
                                showAlert("Không thể thuê phòng!", "Phòng chờ sắp đến giờ nhận khách. Vui lòng chọn phòng khác!");
                            }
                        }
                    } else {
                        showAlert("Phòng đang dọn vệ sinh!", "Vui lòng quay lại sau ít phút.");
                    }
                }
            } else {
                showAlert("Ngoài giờ hoạt động!", "Quán không mở cửa trong khoảng thời gian này.");
            }
        } catch (Exception ex) {
            Logger.getLogger(GD_QLKinhDoanhPhongController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void moGDDatPhongCho() {
        try {
            if (!Phong.getListPhongByStatus(0).contains(new Phong(roomID))) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Vui lòng chọn phòng trống để đặt.", ButtonType.OK);
                alert.getDialogPane().setStyle("-fx-font-family: 'sans-serif';");
                alert.setTitle("Có lỗi xảy ra");
                alert.setHeaderText("Phòng đang được sử dụng hoặc đang là phòng chờ!");
                alert.showAndWait();
            } else {
                App.openModal("GD_DatPhongCho", App.widthModal, App.heightModal);

            }
        } catch (Exception ex) {
            Logger.getLogger(GD_QLKinhDoanhPhongController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void moGDNhanPhongCho() {
        try {
            if (!Phong.getListPhongByStatus(2).contains(new Phong(roomID))) {
                showAlert("Phòng không phù hợp!", "Vui lòng chọn phòng chờ để nhận phòng!");
            } else {
                App.openModal("GD_NhanPhongCho", App.widthModal, App.heightModal);
                gridPane.getChildren().clear();
                renderArrayPhong(Phong.getAllPhong());
            }
        } catch (Exception ex) {
            Logger.getLogger(GD_QLKinhDoanhPhongController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void huyPhongCho() {
        try {
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
                    PhieuDatPhong phieu = PhieuDatPhong.getBookingTicketOfRoom(roomID);
                    if (phieu != null) {
                        PhieuDatPhong.updateTrangThaiPhieuDat(phieu.getMaPhieuDat(), true);
                    }
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
        } catch (Exception ex) {
            Logger.getLogger(GD_QLKinhDoanhPhongController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void moGDChuyenPhong() {
        try {
            if (!Phong.getListPhongByStatus(1).contains(new Phong(roomID))) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Vui lòng chọn phòng đang được sử dụng hoặc phòng chờ để chuyển", ButtonType.OK);
                alert.getDialogPane().setStyle("-fx-font-family: 'sans-serif';");
                alert.setTitle("Có lỗi xảy ra");
                alert.setHeaderText("Phòng không thể chuyển!");
                alert.showAndWait();
            } else {
                App.openModal("GD_ChuyenPhong", App.widthModal, App.heightModal);
                gridPane.getChildren().clear();
                renderArrayPhong(Phong.getAllPhong());
            }
        } catch (Exception ex) {
            Logger.getLogger(GD_QLKinhDoanhPhongController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void moGDDatDichVu() {
        try {
            if (!Phong.getListPhongByStatus(1).contains(new Phong(roomID))) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Vui lòng chọn phòng đang được sử dụng để đặt dịch vụ", ButtonType.OK);
                alert.getDialogPane().setStyle("-fx-font-family: 'sans-serif';");
                alert.setTitle("Có lỗi xảy ra");
                alert.setHeaderText("Phòng không thể đặt dịch vụ!");
                alert.showAndWait();
            } else {
                App.setRoot("GD_DatDichVu");
            }
        } catch (Exception ex) {
            Logger.getLogger(GD_QLKinhDoanhPhongController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void moGDThanhToan() {
        try {
            if (!Phong.getListPhongByStatus(1).contains(new Phong(roomID))) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Vui lòng chọn phòng đang được sử dụng để thanh toán", ButtonType.OK);
                alert.getDialogPane().setStyle("-fx-font-family: 'sans-serif';");
                alert.setTitle("Có lỗi xảy ra");
                alert.setHeaderText("Phòng này không thể đặt thanht toán!");
                alert.showAndWait();
            } else {
                App.setRoot("GD_ThanhToan");
            }
        } catch (Exception ex) {
            Logger.getLogger(GD_QLKinhDoanhPhongController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.getDialogPane().setStyle("-fx-font-family: 'sans-serif';");
        alert.showAndWait();
    }

    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy");

    private static short itemChoosed = 0;
    public static String roomID;
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
    Spinner<Integer> spinnerSucChua;
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
