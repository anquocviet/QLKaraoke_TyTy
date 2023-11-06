/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controllers;

import connect.ConnectDB;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import main.App;
import model.KhachHang;
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
        table.setItems(layTatCaPhong());
    }
//   Get data from DB

    public ObservableList<Phong> layTatCaPhong() {
        ObservableList<Phong> dsPhong = FXCollections.observableArrayList();
        Connection conn = ConnectDB.getInstance().getConnection();
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            String sql = "SELECT * FROM Phong";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String maPhong = rs.getString("Maphong");
                int loaiPhong = rs.getInt("LoaiPhong");
                int tinhTrang = rs.getInt("TinhTrang");
                long giaPhong = rs.getLong("GiaPhong");
                int sucChua = rs.getInt("SucChua");

                dsPhong.add(new Phong(maPhong, sucChua, tinhTrang, loaiPhong, giaPhong));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return dsPhong;
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
