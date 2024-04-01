/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controllers;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.ChiTietHD_Phong;
import model.HoaDonThanhToan;
import model.KhachHang;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;

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
      colTongHD.setCellValueFactory((param) -> {
         String maKH = param.getValue().getMaKhachHang();
         int slHD = HoaDonThanhToan.countBillOfCustomer(maKH);
         return new ReadOnlyObjectWrapper<>(slHD + "");
      });
      colSTT.setCellValueFactory(column -> new ReadOnlyObjectWrapper<>(tableTKKH.getItems().indexOf(column.getValue()) + 1));
      colTenKH.setCellValueFactory(new PropertyValueFactory<>("tenKhachHang"));
      colSDT.setCellValueFactory(new PropertyValueFactory<>("soDienThoai"));
      colTongTG.setCellValueFactory((param) -> {
         String maKH = param.getValue().getMaKhachHang();
         float tongGio = ChiTietHD_Phong.calcTotalHoursOfUseOfCustomer(maKH);
         return new ReadOnlyObjectWrapper<>(df.format(tongGio));
      });
      colTongTien.setCellValueFactory((param) -> {
         String maKH = param.getValue().getMaKhachHang();
         long tongTien = HoaDonThanhToan.calcTotalMoneyOfBillOfCustomer(maKH);
         return new ReadOnlyObjectWrapper<>(df.format(tongTien));
      });

      danhSachKH = FXCollections.observableArrayList();
      KhachHang.getAllKhachHang().forEach((kh) -> {
         if (HoaDonThanhToan.countBillOfCustomer(kh.getMaKhachHang()) != 0) {
            danhSachKH.add(kh);
         }
      });
      tableTKKH.setItems(danhSachKH);
   }


   @FXML
   private Label txtTongKH;
   @FXML
   private Label txtTongHD;
   @FXML
   private Label txtTongDoanhThu;
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

   ObservableList<KhachHang> danhSachKH;


}
