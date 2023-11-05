/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import main.App;

/**
 * FXML Controller class
 *
 * @author vie
 */
public class GD_QLKhachHangController implements Initializable {

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        genderGroup = new ToggleGroup();
    }

    @FXML
    private TextField txtMaKhachHang;
    @FXML
    private TextField txtTenKhachHang;
    @FXML
    private TextField txtSDT;
    @FXML
    private Spinner spinnerNamSinh;
    @FXML
    private ToggleGroup genderGroup;
    @FXML
    private Button btnThem;
    @FXML
    private Button btnSua;
    @FXML
    private Button btnLamMoi;
    @FXML
    private TableView table;
    @FXML
    private TextField inputTimKiem;
    
}
