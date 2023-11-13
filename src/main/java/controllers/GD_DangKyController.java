/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.KhachHang;
import model.TaiKhoan;

/**
 * FXML Controller class
 *
 * @author thach
 */
public class GD_DangKyController implements Initializable {

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        sttCol.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(table.getItems().indexOf(param.getValue()) + 1));
        tenCol.setCellValueFactory(cellData -> {
            String hoTen = cellData.getValue().getNhanVien().getHoTen();
            return new ReadOnlyStringWrapper(hoTen);

        });
        tenDangNhapCol.setCellValueFactory(new PropertyValueFactory<>("tenDangNhap"));
        matKhauCol.setCellValueFactory(new PropertyValueFactory<>("matKhau"));
        table.setItems(TaiKhoan.getAllTaiKhoanFull());

    }

    @FXML
    private TableView<TaiKhoan> table;
    @FXML
    private TableColumn<String, Integer> sttCol;
    @FXML
    private TableColumn<TaiKhoan, String> tenCol;
    @FXML
    private TableColumn<TaiKhoan, String> tenDangNhapCol;
    @FXML
    private TableColumn<TaiKhoan, String> matKhauCol;

}
