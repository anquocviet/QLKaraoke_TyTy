/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import main.App;
import model.ChiTietHD_DichVu;

/**
 * FXML Controller class
 *
 * @author fil
 */
public class GD_ThanhToanController implements Initializable {

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        col_tenDichVu.setCellValueFactory(new PropertyValueFactory<>("tenDichVu"));
        col_soLuong.setCellValueFactory(new PropertyValueFactory<>("soLuong"));
        col_donViTinh.setCellValueFactory(new PropertyValueFactory<>("donViTinh"));
        col_thanhTien.setCellValueFactory(new PropertyValueFactory<>("thanhTien"));

        docDuLieuTuTable2();
        handleEventInTable();
    }

    public void handleEventInTable() {
        table_CTDichVu.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                docDuLieuTuTable2();
            }

        });
        table_CTDichVu.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.UP || event.getCode() == KeyCode.DOWN) {
                    docDuLieuTuTable2();
                }
            }

        });
    }
    
    public void docDuLieuTuTable2() {
        ChiTietHD_DichVu ctdv = table_CTDichVu.getSelectionModel().getSelectedItem();
        if (ctdv == null) {
            return;
        }
        txtTenDichVu.setText(ctdv.getDichVu().getTenDichVu());
        txtSoLuong.setText(String.valueOf(ctdv.getSoLuong()));
        txtDonViTinh.setText(ctdv.getDichVu().getDonViTinh());
        

    }

    @FXML
    private TextField txtTenDichVu;
    @FXML
    private TextField txtSoLuong;
    @FXML
    private TextField txtDonViTinh;
    @FXML
    private TextField txtThanhTien;

    @FXML
    private TableView<ChiTietHD_DichVu> table_CTDichVu;
    @FXML
    private TableColumn<ChiTietHD_DichVu, String> col_tenDichVu;
    @FXML
    private TableColumn<ChiTietHD_DichVu, Integer> col_soLuong;
    @FXML
    private TableColumn<ChiTietHD_DichVu, String> col_donViTinh;
    @FXML
    private TableColumn<ChiTietHD_DichVu, Long> col_thanhTien;

    ObservableList<ChiTietHD_DichVu> danhSach_CTDichVu;
}
