/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controllers;

import java.io.IOException;
import java.net.URL;
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
public class GD_ThanhToanController implements Initializable {

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String maHD = HoaDonThanhToan.getBillIDByRoomID(GD_QLKinhDoanhPhongController.roomID);
        try {
            col_tenDichVu.setCellValueFactory(cellData -> {
                String tenDichVu = cellData.getValue().getDichVu().getTenDichVu();
                return new ReadOnlyStringWrapper(tenDichVu);
            });
            col_soLuong.setCellValueFactory(new PropertyValueFactory<>("soLuong"));
            col_donViTinh.setCellValueFactory(cellData -> {
                String donViTinh = cellData.getValue().getDichVu().getDonViTinh();
                return new ReadOnlyStringWrapper(donViTinh);
            });
            col_thanhTien.setCellValueFactory(cellData -> {
                long thanhTien = cellData.getValue().getThanhTien();
                return new ReadOnlyObjectWrapper<>(thanhTien);
            });
            
            danhSach_CTDichVu = ChiTietHD_DichVu.getCTDichVuTheoMaHD(maHD);
            table_CTDichVu.setItems(danhSach_CTDichVu);
            docDuLieuTuTable2();
            handleEventInTable();
        } catch (Exception ex) {
            Logger.getLogger(GD_ThanhToanController.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        txtThanhTienDV.setText(String.valueOf(ctdv.getThanhTien()));

    }

    //Bảng phòng
    @FXML
    private TextField txtMaPhong;
    @FXML
    private TextField txtNgay;
    @FXML
    private TextField txtGioVao;
    @FXML
    private TextField txtGioRa;
    @FXML
    private TextField txtTongGioSuDung;
    @FXML
    private TextField txtThanhTienP;

    //Bảng Dịch vụ
    @FXML
    private TextField txtTenDichVu;
    @FXML
    private TextField txtSoLuong;
    @FXML
    private TextField txtDonViTinh;
    @FXML
    private TextField txtThanhTienDV;

    @FXML
    private TableView<ChiTietHD_Phong> table_CTPhong;

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
