/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.DichVu;

/**
 * FXML Controller class
 *
 * @author fil
 */
public class GD_QLDichVuController implements Initializable {

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        col_maDichVu.setCellValueFactory(new PropertyValueFactory<>("maDichVu"));
        col_tenDichVu.setCellValueFactory(new PropertyValueFactory<>("tenDichVu"));
        col_soLuong.setCellValueFactory(new PropertyValueFactory<>("soLuong"));
        col_donGia.setCellValueFactory(new PropertyValueFactory<>("donGia"));
        col_donViTinh.setCellValueFactory(new PropertyValueFactory<>("donViTinh"));

        danhSach_DichVu = DichVu.getAllDichVu();
        tableView_DichVu.setItems(danhSach_DichVu);
    }
    
   @FXML
   private TableView<DichVu> tableView_DichVu;
   @FXML
   private TableColumn<DichVu, String> col_maDichVu;
   @FXML
   private TableColumn<DichVu, String> col_tenDichVu;
   @FXML
   private TableColumn<DichVu, Integer> col_soLuong;
   @FXML
   private TableColumn<DichVu, Long> col_donGia;
   @FXML
   private TableColumn<DichVu, String> col_donViTinh;
   
   ObservableList<DichVu> danhSach_DichVu;

}
