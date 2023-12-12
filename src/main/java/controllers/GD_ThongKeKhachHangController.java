/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controllers;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import model.HoaDonThanhToan;
import model.KhachHang;

/**
 * FXML Controller class
 *
 * @author nktng
 */
public class GD_ThongKeKhachHangController implements Initializable {

    /**
     * Initializes the controller class.
     */
    DecimalFormat df = new DecimalFormat("#,###,###,##0");

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        txtTongHD.setText(HoaDonThanhToan.countBill() + "");
        
        
        txtTongDoanhThu.setText(df.format(HoaDonThanhToan.calcTotalMoneyOfBill()) + " VNĐ");
        txtTongKH.setText(KhachHang.demSoLuongKhachHang() + "");

        colSTT.setCellValueFactory(column -> new ReadOnlyObjectWrapper<>(tableTKKH.getItems().indexOf(column.getValue()) + 1));
        colTenKH.setCellValueFactory(new PropertyValueFactory<>("tenKhachHang"));
	colSDT.setCellValueFactory(new PropertyValueFactory<>("soDienThoai"));
        
        danhSach_HoaDon = HoaDonThanhToan.getAllHoaDon();
        danhSachSDT = KhachHang.getAllKhachHang();
        tableTKKH.setItems(danhSachSDT);
    }
    
    
    @FXML
    private Label txtTongKH;
    @FXML
    private Label txtTongHD;
    @FXML
    private Label txtTongDoanhThu;
    @FXML
    private ComboBox<Integer> comboBoxSDTvaKH;

    @FXML
    private TableView<KhachHang> tableTKKH;
    @FXML
    private TableColumn<KhachHang, Integer> colSTT;
    @FXML
    private TableColumn<KhachHang, String> colTongHD;
    @FXML
    private TableColumn<KhachHang, String> colSDT;
    @FXML
    private TableColumn<KhachHang, String> colTenKH;
    @FXML
    private TableColumn<KhachHang, String> colTongTG;
    @FXML
    private TableColumn<KhachHang, String> colTongTien;
    
    ObservableList<HoaDonThanhToan> danhSach_HoaDon;
    ObservableList<KhachHang> danhSachSDT;
    
    

}
