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
import javafx.scene.Node;
import javafx.stage.Stage;
import main.App;

/**
 * FXML Controller class
 *
 * @author thach
 */
public class GD_DangNhapController implements Initializable {

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
    
    @FXML
    private void dangNhap(ActionEvent event) throws IOException, Exception {
        Stage stage = (Stage) ((Node) event.getTarget()).getScene().getWindow();
        stage.close();
    }
}
