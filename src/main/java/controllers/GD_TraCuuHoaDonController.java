/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.cell.PropertyValueFactory;
import main.App;
import model.HoaDonThanhToan;

/**
 * FXML Controller class
 *
 * @author fil
 */
public class GD_TraCuuHoaDonController implements Initializable {

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        /*
        col_maHoaDon.setCellValueFactory(new PropertyValueFactory<>("maHoaDon"));
        col_tenKhachHang.setCellValueFactory(new PropertyValueFactory<>("tenKhachHang"));
        col_sdtKhachHang.setCellValueFactory(new PropertyValueFactory<>("soDienThoai"));
        col_ngayLap.setCellValueFactory(new PropertyValueFactory<>("ngayLap"));
        col_thoiGian.setCellValueFactory(new PropertyValueFactory<>("tongGioSuDung"));

        danhSach_HoaDon = HoaDonThanhToan.class();
        tableView_HoaDon.setItems(danhSach_HoaDon);
        
        docDuLieuTuTable();
        handleEventInTable();
        */
    }
/*
    public void handleEventInTable() {
        tableView_DichVu.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                docDuLieuTuTable();
            }
            
        });
    tableView_DichVu.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.UP || event.getCode() == KeyCode.DOWN) {
                    docDuLieuTuTable();
                }
            }
            
        });
    }
    
    public void docDuLieuTuTable() {
        DichVu dv = tableView_DichVu.getSelectionModel().getSelectedItem();
        if (dv == null) {
            return;
        }
        txtMaHoaDon.setText(dv.getMaDichVu());
        txtTenKhachHang.setText(dv.getTenDichVu());
        txtSDTKhachHang.setText(String.valueOf(dv.getSoLuong()));
        txtNgayLap.setText(String.valueOf(dv.getDonGia()));
        txtThoiGian.setText(dv.getDonViTinh());
    }
    @FXML
    private TextField txtMaHoaDon;        
    @FXML
    private TextField txtTenKhachHang;
    @FXML
    private TextField txtSDTKhachHang;
    @FXML
    private TextField txtNgayLap;
    @FXML
    private TextField txtThoiGian;
    
    @FXML
    private TableView<HoaDonThanhToan> tableView_HoaDon;
    @FXML
    private TableColumn<HoaDonThanhToan, String> col_maHoaDon;
    @FXML
    private TableColumn<HoaDonThanhToan, String> col_tenKhachHang;
    @FXML
    private TableColumn<HoaDonThanhToan, Integer> col_sdtKhachHang;
    @FXML
    private TableColumn<HoaDonThanhToan, Long> col_ngayLap;
    @FXML
    private TableColumn<HoaDonThanhToan, String> col_thoiGian;
   
    ObservableList<HoaDonThanhToan> danhSach_HoaDon;
    //ObservableList<DichVu> danhSach_DichVu
    */
}
