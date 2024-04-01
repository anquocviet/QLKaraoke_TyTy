/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controllers;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.StringConverter;
import model.ChiTietHD_Phong;
import model.HoaDonThanhToan;
import model.KhachHang;

import java.net.URL;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * FXML Controller class
 *
 * @author nktng
 */
public class GD_ThongKeNgayController implements Initializable {

   /**
    * Initializes the controller class.
    */
   @FXML
   private Label txtTongHDNgay;
   @FXML
   private Label txtTongDoanhThuNgay;
   @FXML
   private Label txtTongKH;
   @FXML
   private Label txtTongHD;
   @FXML
   private Label txtTongDoanhThu;
   @FXML
   private DatePicker DateNgay;

   @FXML
   private TableView<ChiTietHD_Phong> table;
   @FXML
   private TableColumn<ChiTietHD_Phong, String> colMaHD;
   @FXML
   private TableColumn<ChiTietHD_Phong, String> colMaPhong;
   @FXML
   private TableColumn<ChiTietHD_Phong, String> colSDT;
   @FXML
   private TableColumn<ChiTietHD_Phong, String> colTenKH;
   @FXML
   private TableColumn<ChiTietHD_Phong, String> colGioVao;
   @FXML
   private TableColumn<ChiTietHD_Phong, String> colGioRa;
   @FXML
   private TableColumn<ChiTietHD_Phong, String> colThanhTien;

   DecimalFormat df = new DecimalFormat("#,###,###,##0");

   @Override
   public void initialize(URL url, ResourceBundle rb) {

      DateNgay.setValue(LocalDate.now());
      DateNgay.setConverter(new StringConverter<LocalDate>() {
         private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

         @Override
         public String toString(LocalDate date) {
            if (date != null) {
               return dateFormatter.format(date);
            } else {
               return "";
            }
         }

         @Override
         public LocalDate fromString(String string) {
            if (string != null && !string.isEmpty()) {
               return LocalDate.parse(string, dateFormatter);
            } else {
               return null;
            }
         }
      });
      txtTongHD.setText(HoaDonThanhToan.countBill() + "");
      txtTongDoanhThu.setText(df.format(HoaDonThanhToan.calcTotalMoneyOfBill()) + " VNĐ");
      txtTongKH.setText(KhachHang.demSoLuongKhachHang() + "");

      colMaHD.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getHoaDon().getMaHoaDon()));
      colMaPhong.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPhong().getMaPhong()));
      colSDT.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getHoaDon().getKhachHang().getSoDienThoai()));
      colTenKH.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getHoaDon().getKhachHang().getTenKhachHang()));
      colGioVao.setCellValueFactory(cellData -> {
         LocalDateTime gioVao = cellData.getValue().getGioVao();
         return new ReadOnlyObjectWrapper<>(gioVao != null ? gioVao.format(DateTimeFormatter.ofPattern("HH:mm")) : "");
      });
      colGioRa.setCellValueFactory(cellData -> {
         LocalDateTime gioRa = cellData.getValue().getGioRa();
         return new ReadOnlyObjectWrapper<>(gioRa != null ? gioRa.format(DateTimeFormatter.ofPattern("HH:mm")) : "");
      });
      colThanhTien.setCellValueFactory((param) -> {
         String maHD = param.getValue().getHoaDon().getMaHoaDon();
         return new ReadOnlyObjectWrapper<>(df.format(HoaDonThanhToan.calcMoney(maHD)));
      });
      try {
         ObservableList<ChiTietHD_Phong> ds = ChiTietHD_Phong.getCT_PhongTheoNgay(LocalDateTime.now());
         table.setItems(ds);
      } catch (Exception ex) {
         Logger.getLogger(GD_ThongKeNgayController.class.getName()).log(Level.SEVERE, null, ex);
      }

      loadDataWhenChangeDate();
      handleInDatePicker();

   }

   public void handleInDatePicker() {
      DateNgay.setOnAction((event) -> {
         loadDataWhenChangeDate();
      });
   }

   public void loadDataWhenChangeDate() {
      txtTongHDNgay.setText(HoaDonThanhToan.countBillByDay(DateNgay.getValue()) + "");
      txtTongDoanhThuNgay.setText(df.format(HoaDonThanhToan.calcTotalMoneyOfBillByDay(DateNgay.getValue())) + " VNĐ");
      // Create
      LocalDateTime ngay = LocalDateTime.of(DateNgay.getValue(), LocalTime.now());
      ObservableList<ChiTietHD_Phong> ds;
      try {
         ds = ChiTietHD_Phong.getCT_PhongTheoNgay(ngay);
         table.setItems(ds);
      } catch (Exception ex) {
         Logger.getLogger(GD_ThongKeNgayController.class.getName()).log(Level.SEVERE, null, ex);
      }

   }

}
