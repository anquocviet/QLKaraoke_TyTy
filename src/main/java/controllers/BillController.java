/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controllers;

import entities.ChiTietHD_DichVu;
import entities.ChiTietHD_Phong;
import entities.HoaDonThanhToan;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import lombok.SneakyThrows;
import main.App;
import socket.ClientSocket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.text.DecimalFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author vie
 */
public class BillController implements Initializable {


   DecimalFormat df = new DecimalFormat("#,###,###,###");
   DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

   @FXML
   private Text txtHoaDon;
   @FXML
   private Text txtThoiGianLap;
   @FXML
   private Text txtNhanVien;
   @FXML
   private Text txtKhachHang;
   @FXML
   private TableView<ChiTietHD_Phong> tablePhong;
   @FXML
   private TableColumn<ChiTietHD_Phong, String> phongCol;
   @FXML
   private TableColumn<ChiTietHD_Phong, String> gioPhongCol;
   @FXML
   private TableColumn<ChiTietHD_Phong, String> giaPhongCol;
   @FXML
   private TableColumn<ChiTietHD_Phong, String> tienPhongCol;
   @FXML
   private Text txtTongTienPhong;
   @FXML
   private TableView<ChiTietHD_DichVu> tableDichVu;
   @FXML
   private TableColumn<ChiTietHD_DichVu, String> tenDVCol;
   @FXML
   private TableColumn<ChiTietHD_DichVu, String> soLuongDVCol;
   @FXML
   private TableColumn<ChiTietHD_DichVu, String> giaDVCol;
   @FXML
   private TableColumn<ChiTietHD_DichVu, String> tienDichVuCol;
   @FXML
   private Text txtTongTienDichVu;
   @FXML
   private Text txtTongTien;
   @FXML
   private Text txtTienThueVAT;
   @FXML
   private Text txtLuongGiamGia;
   @FXML
   private Text txtGiamGia;
   @FXML
   private Text txtThanhToan;
   @FXML
   private Text txtTienKhach;
   @FXML
   private Text txtTienThua;

   DataInputStream dis = ClientSocket.getDis();
   DataOutputStream dos = ClientSocket.getDos();
   ObjectInputStream in = ClientSocket.getIn();
   ObjectOutputStream out = ClientSocket.getOut();

   @SneakyThrows
   @Override
   public void initialize(URL url, ResourceBundle rb) {
      HoaDonThanhToan hd = GD_ThanhToanController.bill;
      txtHoaDon.setText(hd.getMaHoaDon());
//      txtThoiGianLap.setText(dtf.format(hd.getNgayLap()));
      txtNhanVien.setText(hd.getNhanVien().getHoTen());
      txtKhachHang.setText(hd.getKhachHang().getTenKhachHang());

//		TableView Phong
      phongCol.setCellValueFactory((param) -> {
         String maHoaDon = param.getValue().getPhong().getMaPhong();
         return new ReadOnlyObjectWrapper<>(maHoaDon);
      });
      gioPhongCol.setCellValueFactory((param) -> {
         Instant gioVao = param.getValue().getGioVao();
         Instant gioRa = param.getValue().getGioRa();
         float gioSD = (gioRa.toEpochMilli() - gioVao.toEpochMilli()) / 3600000.0f;
         int hours = (int) gioSD;
         float minutesFloat = (gioSD - hours) * 60;
         int minutes = Math.round(minutesFloat);
         String kqHienThi = String.format("%s\n - %s\n=> %s\n\n",
               dtf.format(gioVao.atZone(ZoneId.systemDefault()).toLocalDateTime()),
               dtf.format(gioRa.atZone(ZoneId.systemDefault()).toLocalDateTime()),
               String.format("%dgiờ:%dphút", hours, minutes));
         return new ReadOnlyObjectWrapper<>(kqHienThi);
      });
      giaPhongCol.setCellValueFactory((param) -> {
         if (param.getValue().getPhong() == null || param.getValue().getGioRa() == null) {
            return new ReadOnlyObjectWrapper<>();
         }
         long donGia = param.getValue().getPhong().getGiaPhong();
         return new ReadOnlyObjectWrapper<>(df.format(donGia));
      });
      tienPhongCol.setCellValueFactory((param) -> new ReadOnlyObjectWrapper<>(df.format(param.getValue().getThanhTien())));
      dos.writeUTF("roomDetail-find-by-bill-id," + hd.getMaHoaDon());
      ObservableList<ChiTietHD_Phong> dsPhong = FXCollections.observableArrayList((List<ChiTietHD_Phong>) in.readObject());
      tablePhong.setItems(dsPhong);
      long tienPhong = 0;
      for (ChiTietHD_Phong chiTietHD_Phong : dsPhong) {
         tienPhong += chiTietHD_Phong.getThanhTien();
      }
      txtTongTienPhong.setText(df.format(tienPhong) + "đ");

//		TableView DichVu
      tenDVCol.setCellValueFactory(cellData -> {
         String tenDichVu = cellData.getValue().getDichVu().getTenDichVu();
         return new ReadOnlyStringWrapper(tenDichVu);
      });
      soLuongDVCol.setCellValueFactory(new PropertyValueFactory<>("soLuong"));
      giaDVCol.setCellValueFactory(cellData -> {
         long giaDV = cellData.getValue().getDichVu().getDonGia();
         return new ReadOnlyStringWrapper(df.format(giaDV));
      });
      tienDichVuCol.setCellValueFactory(cellData -> {
         long thanhTien = cellData.getValue().getThanhTien();
         return new ReadOnlyObjectWrapper<>(df.format(thanhTien));
      });
      dos.writeUTF("serviceDetail-find-by-bill-id," + hd.getMaHoaDon());
      ObservableList<ChiTietHD_DichVu> dsDV = FXCollections.observableArrayList((List<ChiTietHD_DichVu>) in.readObject());
      tableDichVu.setItems(dsDV);
      long tienDV = 0;
      for (ChiTietHD_DichVu chiTietHD_DichVu : dsDV) {
         tienDV += chiTietHD_DichVu.getThanhTien();
      }
      txtTongTienDichVu.setText(df.format(tienDV) + "đ");

//		Tinh tien
      long tongTien = tienPhong + tienDV;
      txtTongTien.setText(df.format(tongTien) + "đ");
      long tienVAT = (long) (tongTien * (App.VAT / 100.0));
      txtTienThueVAT.setText(df.format(tienVAT) + "đ");
      txtLuongGiamGia.setText(String.format("Giảm giá (%s%%):", hd.getKhuyenMai().getChietKhau()));
      long tienGiamGia = (long) (tongTien * hd.getKhuyenMai().getChietKhau() / 100.0);
      txtGiamGia.setText(df.format(tienGiamGia) + "đ");
      long thanhToan = tongTien + tienVAT - tienGiamGia;
      txtThanhToan.setText(df.format(thanhToan) + "đ");
      txtTienKhach.setText(df.format(GD_ThanhToanController.tienNhan) + "đ");
      txtTienThua.setText(df.format(GD_ThanhToanController.tienNhan - thanhToan) + "đ");
   }
}
