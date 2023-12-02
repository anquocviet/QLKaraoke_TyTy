/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controllers;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.text.DecimalFormat;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import main.App;
import model.ChiTietHD_DichVu;
import model.ChiTietHD_Phong;
import model.HoaDonThanhToan;

/**
 * FXML Controller class
 *
 * @author fil
 */
public class GD_TraCuuHoaDonController implements Initializable {

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        maHoaDonCol.setCellValueFactory(new PropertyValueFactory<>("maHoaDon"));
        tenKHCol.setCellValueFactory(new PropertyValueFactory<>("tenKhachHang"));
        sdtCol.setCellValueFactory(new PropertyValueFactory<>("soDienThoai"));
        ngayLapCol.setCellValueFactory(new PropertyValueFactory<>("ngayLap"));
    
        danhSach_HoaDon = HoaDonThanhToan.getAllHoaDon();
        tableHoaDon.setItems(danhSach_HoaDon);
        tableHoaDon.requestFocus();
        handleEventInTable();
    }
    
    public void handleEventInTable() {
        tableHoaDon.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
            }

        });
        tableHoaDon.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.UP || event.getCode() == KeyCode.DOWN) {
                }
            }

        });
    }

    @FXML
    private TableView<ChiTietHD_DichVu> tableDichVu;
    @FXML
    private TableColumn<ChiTietHD_DichVu, String> tenDichVuCol;
    @FXML
    private TableColumn<ChiTietHD_DichVu, Integer> soLuongCol;
    @FXML
    private TableColumn<ChiTietHD_DichVu, String> donViTinhCol;
    @FXML
    private TableColumn<ChiTietHD_DichVu, Long> thanhTienDVCol;

    @FXML
    private TableView<ChiTietHD_Phong> tablePhong;
    @FXML
    private TableColumn<ChiTietHD_Phong, String> maPhongCol;
    @FXML
    private TableColumn<ChiTietHD_Phong, String> loaiPhongCol;
    @FXML
    private TableColumn<ChiTietHD_Phong, String> gioVaoCol;
    @FXML
    private TableColumn<ChiTietHD_Phong, String> gioRaCol;
    @FXML
    private TableColumn<ChiTietHD_Phong, String> gioSuDungCol;
    @FXML
    private TableColumn<ChiTietHD_Phong, String> donGiaCol;
    @FXML
    private TableColumn<ChiTietHD_Phong, String> thanhTienCol;

    @FXML
    private TableView<HoaDonThanhToan> tableHoaDon;
    @FXML
    private TableColumn<HoaDonThanhToan, String> maHoaDonCol;
    @FXML
    private TableColumn<HoaDonThanhToan, String> tenKHCol;
    @FXML
    private TableColumn<HoaDonThanhToan, String> sdtCol;
    @FXML
    private TableColumn<HoaDonThanhToan, String> ngayLapCol;

    @FXML
    private TextField txtMaHoaDon;
    @FXML
    private TextField txtSDT;
    @FXML
    private TextField txtTenKH;
    @FXML
    private Button btnTimKiem;
    @FXML
    private Button btnXoaTrang;
    
    ObservableList<HoaDonThanhToan> danhSach_HoaDon;

}
