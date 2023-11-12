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
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
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
import model.Phong;

/**
 * FXML Controller class
 *
 * @author thangnood
 */
public class GD_QLKinhDoanhPhongController implements Initializable {

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ToggleGroup typeRoomGroup = new ToggleGroup();
        ToggleGroup statusRoomGroup = new ToggleGroup();
        createClockView();
        ObservableList<Phong> dsPhong = Phong.layTatCaPhong();
        
        for (int i = 0; i < dsPhong.size(); i++) {
            Phong phong = dsPhong.get(i);
            gridPane.add(createViewRoomItem(
                    phong.getLoaiPhong(), phong.getTinhTrang(), phong.getMaPhong()
            ), i % 3, i / 3);
        }
    }

    @FXML
    public void handleKeyboardEvent(KeyEvent ke) throws IOException {
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
        App.openModal("GD_ThuePhong", App.widthModal, App.heightModal);
    }

    @FXML
    private void moGDDatPhongCho() throws IOException {
        App.openModal("GD_DatPhongCho", App.widthModal, App.heightModal);
    }

    @FXML
    private void moGDNhanPhongCho() throws IOException {
    }

    @FXML
    private void huyPhongCho() throws IOException {
    }

    @FXML
    private void moGDChuyenPhong() throws IOException {
        App.openModal("GD_ChuyenPhong", App.widthModal, App.heightModal);
    }

    @FXML
    private void moGDDatDichVu() throws IOException {
        App.setRoot("GD_DatDichVu");
    }

    @FXML
    private void moGDThanhToan() throws IOException {
        App.setRoot("GD_ThanhToan");
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
//      private int tinhTrang;      // 0: trống - 1: đã thuê - 2: được đặt
//      private int loaiPhong;      // 1: VIP - 0: thường
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
        imgView.setFitWidth(150);
        imgView.setFitHeight(130);
        Label lblMaPhong = new Label(maPhong);
        lblMaPhong.setStyle("-fx-font-size: 18; -fx-font-weight: 600");
        lblMaPhong.setPadding(new Insets(0, 0, 8, 0));
        roomItem.getChildren().add(imgView);
        roomItem.getChildren().add(lblMaPhong);
        roomItem.setAlignment(Pos.CENTER);
        roomItem.getStyleClass().add("itemRoom");
        return roomItem;
    }

    @FXML
    private Label clockLabel;
    @FXML
    private Label dateLabel;
    @FXML
    private GridPane gridPane;

}
