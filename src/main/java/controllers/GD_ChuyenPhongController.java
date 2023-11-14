/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyStringWrapper;
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
import javafx.stage.Stage;
import model.KhachHang;
import model.Phong;

/**
 * FXML Controller class
 *
 * @author thach
 */
public class GD_ChuyenPhongController implements Initializable {

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        maPhongCol.setCellValueFactory(new PropertyValueFactory<>("maPhong"));
        //loaiPhongCol.setCellValueFactory(new PropertyValueFactory<>("loaiPhong"));
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
        soNguoiCol.setCellValueFactory(new PropertyValueFactory<>("sucChua"));
        //trangThaiCol.setCellValueFactory(new PropertyValueFactory<>("tinhTrang"));
        trangThaiCol.setCellValueFactory(cellData -> {
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
        giaTienMoiGioCol.setCellValueFactory(cellData -> {
            long giaPhong = cellData.getValue().getGiaPhong();
            return new ReadOnlyStringWrapper(giaPhong + " K/H");
        });
        // giaTienMoiGioCol.setCellValueFactory(new PropertyValueFactory<>("giaPhong"));
        //  table.setItems(layTatCaPhong());
        table.setItems(Phong.layTatCaPhong());
        ActionEvent event = null;
        handleRefresh(event);
       
    }

    @FXML
    void handleRefresh(ActionEvent event) {
        table.setItems(Phong.layTatCaPhong());
        handleEventInTable();
        docDuLieuTuTable();

    }

   
    @FXML
    void handleExit(ActionEvent event) {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close(); 
    }
    
    @FXML
    void handleChuyenPhong(ActionEvent event){
        Phong phongDuocChon = table.getSelectionModel().getSelectedItem();
        if(phongDuocChon !=null){
            table.setItems(Phong.layTatCaPhong());
        }else{
    }
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
        Phong cp = table.getSelectionModel().getSelectedItem();
        if (cp == null) {
            return;
        }
        txtMaPhong.setText(cp.getMaPhong());

    }

    //fxml Chuyển Phòng
    @FXML
    private TableView<Phong> table;
    @FXML
    private TableColumn<Phong, String> maPhongCol;
    @FXML
    private TableColumn<Phong, String> loaiPhongCol;
    @FXML
    private TableColumn<Phong, Integer> soNguoiCol;
    @FXML
    private TableColumn<Phong, String> trangThaiCol;
    @FXML
    private TableColumn<Phong, String> giaTienMoiGioCol;

    @FXML
    private TextField txtMaPhong;

    @FXML
    private Button refreshButton;
    
    @FXML
    private Button exitButton;
    
    @FXML
    private Button chuyenPhongButton;

}
