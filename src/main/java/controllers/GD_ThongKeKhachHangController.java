/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controllers;

import entities.HoaDonThanhToan;
import entities.KhachHang;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.SneakyThrows;
import socket.ClientSocket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.List;
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
   DataInputStream dis = ClientSocket.getDis();
   DataOutputStream dos = ClientSocket.getDos();
   ObjectInputStream in = ClientSocket.getIn();
   ObjectOutputStream out = ClientSocket.getOut();

   @SneakyThrows
   @Override
   public void initialize(URL url, ResourceBundle rb) {
      dos.writeUTF("bill-count-bill");
      txtTongHD.setText(String.valueOf(dis.readLong()));


      dos.writeUTF("bill-calc-money");
      txtTongDoanhThu.setText(df.format(dis.readLong() + " VNĐ"));
      dos.writeUTF("customer-find-all-customer");
      List<KhachHang> list = (List<KhachHang>) in.readObject();
      txtTongKH.setText(list.size() + "");
      colTongHD.setCellValueFactory((param) -> {
         String maKH = param.getValue().getMaKhachHang();
         try {
            dos.writeUTF("bill-count-bill-by-customer-id," + maKH);
            long slHD = dis.readLong();
            return new ReadOnlyObjectWrapper<>(slHD + "");
         } catch (IOException e) {
            throw new RuntimeException(e);
         }
      });
      colSTT.setCellValueFactory(column -> new ReadOnlyObjectWrapper<>(tableTKKH.getItems().indexOf(column.getValue()) + 1));
      colTenKH.setCellValueFactory(new PropertyValueFactory<>("tenKhachHang"));
      colSDT.setCellValueFactory(new PropertyValueFactory<>("soDienThoai"));
      colTongTG.setCellValueFactory((param) -> {
         String maKH = param.getValue().getMaKhachHang();
         try {
            dos.writeUTF("roomDetail-calc-total-hours-use-of-customer," + maKH);
            long tongGio = dis.readLong();
            return new ReadOnlyObjectWrapper<>(df.format(tongGio));
         } catch (IOException e) {
            throw new RuntimeException(e);
         }
      });
      colTongTien.setCellValueFactory((param) -> {
         String maKH = param.getValue().getMaKhachHang();
         try {
            dos.writeUTF("bill-calc-money-by-customer-id," + maKH);
            long tongTien = dis.readLong();
            return new ReadOnlyObjectWrapper<>(df.format(tongTien));
         } catch (IOException e) {
            throw new RuntimeException(e);
         }
      });

      danhSachKH = FXCollections.observableArrayList();
      dos.writeUTF("customer-find-all-customer");
      ((List<KhachHang>) in.readObject()).forEach((kh) -> {
         try {
            dos.writeUTF("bill-find-bill-by-customer-id," + kh.getMaKhachHang());
//            List<HoaDonThanhToan> listBill = (List<HoaDonThanhToan>) in.readObject();
//            if (listBill.size() != 0) {
//               danhSachKH.add(kh);
//            }
         } catch (IOException e) {
            throw new RuntimeException(e);
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
