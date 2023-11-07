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
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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
    
    

    
}

