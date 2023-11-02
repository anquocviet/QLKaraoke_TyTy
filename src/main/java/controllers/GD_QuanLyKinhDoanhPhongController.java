/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import main.App;

/**
 *
 * @author Admin
 */
public class GD_QuanLyKinhDoanhPhongController implements Initializable{

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
    
    @FXML
    private void openGUICustomerManager(ActionEvent event) throws IOException {
        App.setRoot("GD_QuanLyKinhDoanhPhong");
    }
}
