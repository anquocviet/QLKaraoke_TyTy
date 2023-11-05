/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controllers;

import java.io.IOException;
import javafx.event.ActionEvent;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import main.App;

/**
 * FXML Controller class
 *
 * @author vie
 */
public class GD_DatDichVuController implements Initializable {
              
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
    
    @FXML
    private ComboBox cbPhong;
    @FXML
    private TextField txtMaKhachHang;
    @FXML
    private TextField txtTenKhachHang;
    @FXML
    private TextField txtMaHoaDon;
    @FXML
    private TextField txtTenDichVu;
    @FXML
    private TextField txtGiaDichVu;
    @FXML
    private Button btnLamMoi;
    @FXML
    private TableView tableThongTinDichVu;
    @FXML
    private TableView tableDichVuDaThem;
    @FXML
    private Text lbTongTienl;
    @FXML
    private Button btnDatDichVu;

}
