/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import java.net.URL;
import java.sql.Date;
import java.text.SimpleDateFormat;

import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import model.CT_KhuyenMai;

/**
 *
 * @author Admin
 */
public class GD_QLCTKhuyenMaiController implements Initializable {

    @FXML
    private TableView<CT_KhuyenMai> tableView_CTKhuyenMai;

    @FXML
    private TableColumn<String, Integer> col_sttKhuyenMai;

    @FXML
    private TableColumn<CT_KhuyenMai, String> col_maKhuyenMai;

    @FXML
    private TableColumn<CT_KhuyenMai, String> col_tenKhuyenMai;

    @FXML
    private TableColumn<CT_KhuyenMai, String> col_ngayBatDau;

    @FXML
    private TableColumn<CT_KhuyenMai, String> col_ngayKetThuc;

    @FXML
    private TableColumn<CT_KhuyenMai, Integer> col_luotSuDungConLai;

    @FXML
    private TableColumn<CT_KhuyenMai, Integer> col_chietKhau;
    
    @FXML
    private TextField txtMaKhuyenMai;
     
    @FXML
    private TextField txtTenKhuyenMai;
    
    @FXML
    private TextField txtNgayBatDau;
    
    ObservableList<CT_KhuyenMai> danhSachCT_KhuyenMai;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        col_sttKhuyenMai.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(tableView_CTKhuyenMai.getItems().indexOf(param.getValue()) + 1));
        col_maKhuyenMai.setCellValueFactory(new PropertyValueFactory<>("maKhuyenMai"));
        col_tenKhuyenMai.setCellValueFactory(new PropertyValueFactory<>("tenKhuyenMai"));
        col_ngayBatDau.setCellValueFactory(new PropertyValueFactory<>("ngayBatDau"));
        col_ngayKetThuc.setCellValueFactory(new PropertyValueFactory<>("ngayKetThuc"));
        col_luotSuDungConLai.setCellValueFactory(new PropertyValueFactory<>("luotSuDungConLai"));
        col_chietKhau.setCellValueFactory(new PropertyValueFactory<>("chietKhau"));

        danhSachCT_KhuyenMai = CT_KhuyenMai.getListCT_KhuyenMai();
        // cột số thứ tự chưa được gán --> ko lấy dữ liệu lên table được
        tableView_CTKhuyenMai.setItems(danhSachCT_KhuyenMai);
    }
    
//    public void handleEventInTable() {
//        tableView_CTKhuyenMai.setOnMouseClicked(new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent event) {
//                docDuLieuTuTable();
//            }
//            
//        });
//        tableView_CTKhuyenMai.setOnKeyPressed(new EventHandler<KeyEvent>() {
//            @Override
//            public void handle(KeyEvent event) {
//                if (event.getCode() == KeyCode.UP || event.getCode() == KeyCode.DOWN) {
//                    docDuLieuTuTable();
//                }
//            }
//            
//        });
//    }
//    
//    public void docDuLieuTuTable() {
//        CT_KhuyenMai kh = tableView_CTKhuyenMai.getSelectionModel().getSelectedItem();
//        if (kh == null) {
//            return;
//        }
//        txtMa.setText(kh.getMaKhachHang());
//        txtTenKhachHang.setText(kh.getTenKhachHang());
//        txtSDT.setText(kh.getSoDienThoai());
//        spinnerNamSinh.getValueFactory().setValue(kh.getNamSinh());
//        if (kh.isGioiTinh()) {
//            genderGroup.getToggles().get(0).setSelected(true);
//        } else {
//            genderGroup.getToggles().get(1).setSelected(true);
//        }
//    }
}
