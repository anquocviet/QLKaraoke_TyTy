/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controllers;

import enums.Enum_LoaiPhong;
import enums.Enum_ChucVu;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import model.KhachHang;
import model.Phong;

/**
 * FXML Controller class
 *
 * @author thach
 */
public class GD_QLPhongController implements Initializable {

    @FXML
    private Button btnAdd;
    @FXML
    private Button btnClear;
    @FXML
    private Button btnUpdate;
    @FXML
    private ComboBox<String> cbbTinhTrang;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cbbTinhTrang.getItems().addAll("PHÒNG TRỐNG", "ĐÃ THUÊ", "ĐÃ ĐẶT");
        cbbLoaiPhong.setItems(Enum_LoaiPhong.getListLoaiPhong());
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
        handleEventInTable();
        docDuLieuTuTable();


        btnAdd.setOnAction(e -> {
            String maPhong = txtMaPhong.getText();
            int sucChua = Integer.parseInt(txtSucChua.getText());
            String tinhTrang = cbbTinhTrang.getValue();
            float giaPhong = Float.parseFloat(txtGiaPhong.getText());
            String loaiPhong = cbbLoaiPhong.getValue().toString();

            String loaiPhongDb = loaiPhong.equals("VIP") ? "1" : "0";
            int tinhTrangDb = tinhTrang.equals("PHÒNG TRỐNG") ? 0 : tinhTrang.equals("ĐÃ THUÊ") ? 1 : 2;

            Phong.addPhong(maPhong, loaiPhongDb, tinhTrangDb, sucChua, giaPhong);
            table.setItems(Phong.getAllPhong());

        });

        btnClear.setOnAction(e -> {
            txtMaPhong.setText("");
            txtSucChua.setText("");
            cbbTinhTrang.setValue("");
            txtGiaPhong.setText("");
            cbbLoaiPhong.setValue("");
            table.getSelectionModel().clearSelection();
        });

        btnUpdate.setOnAction(e->{
            String maPhong = txtMaPhong.getText();
            int sucChua = Integer.parseInt(txtSucChua.getText());
            String tinhTrang = cbbTinhTrang.getValue();
            float giaPhong = Float.parseFloat(txtGiaPhong.getText());
            String loaiPhong = cbbLoaiPhong.getValue().toString();

            String loaiPhongDb = loaiPhong.equals("VIP") ? "1" : "0";
            int tinhTrangDb = tinhTrang.equals("PHÒNG TRỐNG") ? 0 : tinhTrang.equals("ĐÃ THUÊ") ? 1 : 2;

            Phong.updatePhong(maPhong, loaiPhongDb, tinhTrangDb, sucChua, giaPhong);
            table.setItems(Phong.getAllPhong());
            table.refresh();
        });

    }

    public void handleEventInTable() {
        table.setOnMouseClicked(event -> docDuLieuTuTable());
        table.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.UP || event.getCode() == KeyCode.DOWN) {
                    docDuLieuTuTable();
                }
            }


        });
    }

    //  Render and handle in View'
    public void docDuLieuTuTable() {
        Phong p = table.getSelectionModel().getSelectedItem();
        if (p == null) {
            return;
        }
        txtMaPhong.setText(p.getMaPhong());
        String emptyString = "";
        txtSucChua.setText((emptyString + p.getSucChua()));

        if (p.getTinhTrang() == 0) {
            cbbTinhTrang.setValue("Phòng Trống");
        } else if (p.getTinhTrang() == 1) {
            cbbTinhTrang.setValue("Đã Thuê");
        } else if (p.getTinhTrang() == 2) {
            cbbTinhTrang.setValue("Đã Đặt");
        } else {
            cbbTinhTrang.getSelectionModel().clearSelection();
        }


        txtGiaPhong.setText(p.getGiaPhong() + emptyString);

        cbbLoaiPhong.getItems().clear();
        cbbLoaiPhong.getItems().addAll(Enum_LoaiPhong.values());

        if (p.getLoaiPhong() == 0) {
            cbbLoaiPhong.getSelectionModel().select(0);
        } else {
            cbbLoaiPhong.getSelectionModel().select(1);
        }


    }
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

    @FXML
    private TextField txtMaPhong;
    @FXML
    private TextField txtSucChua;

    @FXML
    private ComboBox cbbLoaiPhong;
    @FXML
    private TextField txtGiaPhong;

}

