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
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.util.Duration;
import main.App;

/**
 * FXML Controller class
 *
 * @author thangnood
 */
public class GD_QLKinhDoanhPhongController implements Initializable {
    @FXML
    private Label clockLabel;
    
    @FXML
    private Label dateLabel;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ToggleGroup typeRoomGroup = new ToggleGroup();
        ToggleGroup statusRoomGroup = new ToggleGroup();
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
    
    @FXML
    private void moGDThuePhong(ActionEvent event) throws IOException {
        App.openModal("GD_ThuePhong", App.widthModal, App.heightModal);
    }

    @FXML
    private void moGDChuyenPhong(ActionEvent event) throws IOException {
        App.openModal("GD_ChuyenPhong", App.widthModal, App.heightModal);
    }
    
    @FXML
    private void moGDDatDichVu(ActionEvent event) throws IOException {
        App.setRoot("GD_DatDichVu");
    }
    
    @FXML
    private void moGDDatPhongCho(ActionEvent event) throws IOException {
        App.openModal("GD_DatPhongCho", App.widthModal, App.heightModal);
    }
    
    @FXML
    private void moGDThanhToan(ActionEvent event) throws IOException {
        App.setRoot("GD_ThanhToan");
    }

    
}

