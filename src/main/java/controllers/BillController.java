/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;

import java.net.URL;
import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author vie
 */
public class BillController implements Initializable {

   /**
    * Initializes the controller class.
    */
   @Override
   public void initialize(URL url, ResourceBundle rb) {
//      String maHD = HoaDonThanhToan.getBillIDByRoomID(GD_QLKinhDoanhPhongController.roomID);
//      HoaDonThanhToan hd = HoaDonThanhToan.getBillByID(maHD);
//      txtHoaDon.setText(maHD);
//      txtThoiGianLap.setText(dtf.format(hd.getNgayLap()));
//      txtNhanVien.setText(hd.getNhanVienLap().getHoTen());
//      txtKhachHang.setText(hd.getKhachHang().getTenKhachHang());
//
////		TableView Phong
//      phongCol.setCellValueFactory((param) -> {
//         String maHoaDon = param.getValue().getPhong().getMaPhong();
//         return new ReadOnlyObjectWrapper<>(maHoaDon);
//      });
//      gioPhongCol.setCellValueFactory((param) -> {
//         LocalDateTime gioVao = param.getValue().getGioVao();
//         LocalDateTime gioRa = param.getValue().getGioRa();
//         float gioSD = param.getValue().tinhTongGioSuDung();
//         int hours = (int) gioSD;
//         float minutesFloat = (gioSD - hours) * 60;
//         int minutes = Math.round(minutesFloat);
//         String kqHienThi = String.format("%s\n - %s\n=> %s\n\n",
//               dtf.format(gioVao),
//               dtf.format(gioRa),
//               String.format("%dgiờ:%dphút", hours, minutes));
//         return new ReadOnlyObjectWrapper<>(kqHienThi);
//      });
//      giaPhongCol.setCellValueFactory((param) -> {
//         if (param.getValue().getPhong() == null || param.getValue().getGioRa() == null) {
//            return new ReadOnlyObjectWrapper<>();
//         }
//         long donGia = param.getValue().getPhong().getGiaPhong();
//         return new ReadOnlyObjectWrapper<>(df.format(donGia));
//      });
//      tienPhongCol.setCellValueFactory((param) -> {
//         return new ReadOnlyObjectWrapper<>(df.format(param.getValue().tinhThanhTien()));
//      });
//      ObservableList<ChiTietHD_Phong> dsPhong = ChiTietHD_Phong.getCT_PhongTheoMaHD(maHD);
//      tablePhong.setItems(dsPhong);
//      long tienPhong = 0;
//      for (ChiTietHD_Phong chiTietHD_Phong : dsPhong) {
//         tienPhong += chiTietHD_Phong.tinhThanhTien();
//      }
//      txtTongTienPhong.setText(df.format(tienPhong) + "đ");
//
////		TableView DichVu
//      tenDVCol.setCellValueFactory(cellData -> {
//         String tenDichVu = cellData.getValue().getDichVu().getTenDichVu();
//         return new ReadOnlyStringWrapper(tenDichVu);
//      });
//      soLuongDVCol.setCellValueFactory(new PropertyValueFactory<>("soLuong"));
//      giaDVCol.setCellValueFactory(cellData -> {
//         long giaDV = cellData.getValue().getDichVu().getDonGia();
//         return new ReadOnlyStringWrapper(df.format(giaDV));
//      });
//      tienDichVuCol.setCellValueFactory(cellData -> {
//         long thanhTien = cellData.getValue().getThanhTien();
//         return new ReadOnlyObjectWrapper<>(df.format(thanhTien));
//      });
//      ObservableList<ChiTietHD_DichVu> dsDV = ChiTietHD_DichVu.getCTDichVuTheoMaHD(maHD);
//      tableDichVu.setItems(dsDV);
//      long tienDV = 0;
//      for (ChiTietHD_DichVu chiTietHD_DichVu : dsDV) {
//         tienDV += chiTietHD_DichVu.getThanhTien();
//      }
//      txtTongTienDichVu.setText(df.format(tienDV) + "đ");
//
////		Tinh tien
//      long tongTien = tienPhong + tienDV;
//      txtTongTien.setText(df.format(tongTien) + "đ");
//      long tienVAT = (long) (tongTien * (App.VAT / 100.0));
//      txtTienThueVAT.setText(df.format(tienVAT) + "đ");
//      txtLuongGiamGia.setText(String.format("Giảm giá (%s%%):", hd.getKhuyenMai().getChietKhau()));
//      long tienGiamGia = (long) (tongTien * hd.getKhuyenMai().getChietKhau() / 100.0);
//      txtGiamGia.setText(df.format(tienGiamGia) + "đ");
//      long thanhToan = tongTien + tienVAT - tienGiamGia;
//      txtThanhToan.setText(df.format(thanhToan) + "đ");
//      txtTienKhach.setText(df.format(GD_ThanhToanController.tienNhan) + "đ");
//      txtTienThua.setText(df.format(GD_ThanhToanController.tienNhan - thanhToan) + "đ");
   }

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
//   @FXML
//   private TableView<ChiTietHD_Phong> tablePhong;
//   @FXML
//   private TableColumn<ChiTietHD_Phong, String> phongCol;
//   @FXML
//   private TableColumn<ChiTietHD_Phong, String> gioPhongCol;
//   @FXML
//   private TableColumn<ChiTietHD_Phong, String> giaPhongCol;
//   @FXML
//   private TableColumn<ChiTietHD_Phong, String> tienPhongCol;
//   @FXML
//   private Text txtTongTienPhong;
//   @FXML
//   private TableView<ChiTietHD_DichVu> tableDichVu;
//   @FXML
//   private TableColumn<ChiTietHD_DichVu, String> tenDVCol;
//   @FXML
//   private TableColumn<ChiTietHD_DichVu, String> soLuongDVCol;
//   @FXML
//   private TableColumn<ChiTietHD_DichVu, String> giaDVCol;
//   @FXML
//   private TableColumn<ChiTietHD_DichVu, String> tienDichVuCol;
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
}
