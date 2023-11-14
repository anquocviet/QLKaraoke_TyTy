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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
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
        handleEventInTable();
        docDuLieuTuTable();

        table.setItems(Phong.getAllPhong());

    }

    @FXML
    public void xuLyLamMoiThongTinPhong() throws Exception {
        cbbLoaiPhong.getItems().clear();
        cbbLoaiPhong.getItems().addAll(Enum_LoaiPhong.values());
        cbbLoaiPhong.getSelectionModel().selectFirst();
    }

    public void handleEventInTable() {
        table.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                docDuLieuTuTable();
            }

        });
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
        
        if(p.getTinhTrang() == 0){
         txtTinhTrang.setText(" Phòng Trống ");   
        }else if(p.getTinhTrang() == 1){
         txtTinhTrang.setText(" Đã Thuê");      
        }else if(p.getTinhTrang() == 2){
          txtTinhTrang.setText(" Đã Đặt");   
        }
         
         
         
         
        txtGiaPhong.setText(p.getGiaPhong() + emptyString);
        
        cbbLoaiPhong.getItems().clear();
        cbbLoaiPhong.getItems().addAll(Enum_LoaiPhong.values());
       
        if (p.getLoaiPhong()==0){
            cbbLoaiPhong.getSelectionModel().select(0);
        }else{
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
    private TextField txtTinhTrang;
    @FXML
    private ComboBox cbbLoaiPhong;
    @FXML
    private TextField txtGiaPhong;

}

