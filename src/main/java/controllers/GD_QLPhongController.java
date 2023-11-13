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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Phong;

/**
 * FXML Controller class
 *
 * @author thach
 */
public class GD_QLPhongController implements Initializable {

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        sttCol.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(table.getItems().indexOf(param.getValue()) + 1));
        maPhongCol.setCellValueFactory(new PropertyValueFactory<>("maPhong"));
        sucChuaCol.setCellValueFactory(new PropertyValueFactory<>("sucChua"));
        tinhTrangCol.setCellValueFactory(cellData -> {
            int tinhTrang = cellData.getValue().getTinhTrang();
            String tinhTrangString;
            if (tinhTrang == 0) {
                tinhTrangString = "PHÒNG TRỐNG";
            } else if (tinhTrang == 1) {
                tinhTrangString = "ĐÃ THUÊ";
            } else {
                tinhTrangString = "ĐÃ ĐẶT";
            }
            return new ReadOnlyStringWrapper(tinhTrangString);
        });

        giaPhongCol.setCellValueFactory(cellData -> {
            long giaPhong = cellData.getValue().getGiaPhong();
            return new ReadOnlyStringWrapper(giaPhong + " K/H");
        });
        loaiPhongCol.setCellValueFactory(cellData -> {
            int loaiPhong = cellData.getValue().getLoaiPhong();
            String loaiPhongString;
            if (loaiPhong == 1) {
                loaiPhongString = "VIP";
            } else {
                loaiPhongString = "THƯỜNG";
            }
            return new ReadOnlyStringWrapper(loaiPhongString);
        });
        table.setItems(Phong.getAllPhong());
    }
//  Render and handle in View'
    /* public void docDuLieuTuTable(MouseEvent event) {
        Phong ph = table.getSelectionModel().getSelectedItem();
        txtMaKhachHang.setText(ph.getMa());
        txtTenKhachHang.setText(ph.getTenKhachHang());
        txtSDT.setText(kh.getSoDienThoai());
        spinnerNamSinh.getValueFactory().setValue(kh.getNamSinh());
        if (kh.isGioiTinh()) {
            genderGroup.getToggles().get(0).setSelected(true);
        } else {
            genderGroup.getToggles().get(1).setSelected(true);
        }
    }
     */
//fxml QL Phòng
    
    @FXML
    private TableView<Phong> table;
    @FXML
    private TableColumn<String, Integer> sttCol;
    @FXML
    private TableColumn<Phong, String> maPhongCol;
    @FXML
    private TableColumn<Phong, Integer> sucChuaCol;
    @FXML
    private TableColumn<Phong, String> tinhTrangCol;
    @FXML
    private TableColumn<Phong, String> loaiPhongCol;
    @FXML
    private TableColumn<Phong, String> giaPhongCol;

}
