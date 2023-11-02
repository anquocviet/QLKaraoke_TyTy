/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ToggleGroup;
import main.App;

/**
 * FXML Controller class
 *
 * @author thangnood
 */
public class GD_QLKinhDoanhPhongController implements Initializable {

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //ToggleGroup 
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

