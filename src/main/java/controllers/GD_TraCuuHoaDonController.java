/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controllers;

import entities.ChiTietHD_DichVu;
import entities.ChiTietHD_Phong;
import entities.HoaDonThanhToan;
import entities.Phong;
import enums.Enum_LoaiPhong;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.skin.TableViewSkin;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import main.App;
import socket.ClientSocket;

import java.io.*;
import java.net.URL;
import java.text.DecimalFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * FXML Controller class
 *
 * @author fil
 */
public class GD_TraCuuHoaDonController implements Initializable {

      @FXML
   private TableView<ChiTietHD_DichVu> tableDichVu;
   @FXML
   private TableColumn<ChiTietHD_DichVu, String> tenDichVuCol;
   @FXML
   private TableColumn<ChiTietHD_DichVu, Integer> soLuongCol;
   @FXML
   private TableColumn<ChiTietHD_DichVu, String> donViTinhCol;
   @FXML
   private TableColumn<ChiTietHD_DichVu, Long> thanhTienDVCol;

   @FXML
   private TableView<ChiTietHD_Phong> tablePhong;
   @FXML
   private TableColumn<ChiTietHD_Phong, String> maPhongCol;
   @FXML
   private TableColumn<ChiTietHD_Phong, String> loaiPhongCol;
   @FXML
   private TableColumn<ChiTietHD_Phong, String> gioVaoCol;
   @FXML
   private TableColumn<ChiTietHD_Phong, String> gioRaCol;
   @FXML
   private TableColumn<ChiTietHD_Phong, String> gioSuDungCol;
   @FXML
   private TableColumn<ChiTietHD_Phong, String> donGiaCol;
   @FXML
   private TableColumn<ChiTietHD_Phong, String> thanhTienCol;

   @FXML
   private TableView<HoaDonThanhToan> tableHoaDon;
   @FXML
   private TableColumn<HoaDonThanhToan, String> maHoaDonCol;
   @FXML
   private TableColumn<HoaDonThanhToan, String> tenKHCol;
   @FXML
   private TableColumn<HoaDonThanhToan, String> sdtCol;
   @FXML
   private TableColumn<HoaDonThanhToan, String> ngayLapCol;
   @FXML
   private TableColumn<HoaDonThanhToan, String> tongTienCol;

   @FXML
   private TextField txtMaHoaDon;
   @FXML
   private TextField txtSDT;
   @FXML
   private TextField txtTenKH;
   @FXML
   private DatePicker txtNgayLap;
   @FXML
   private Button btnTimKiem;
   @FXML
   private Button btnXoaTrangLamMoi;

   DataInputStream dis = ClientSocket.getDis();
   DataOutputStream dos = ClientSocket.getDos();
   ObjectInputStream in = ClientSocket.getIn();
   ObjectOutputStream out = ClientSocket.getOut();

   ObservableList<HoaDonThanhToan> danhSach_HoaDon;


   private DecimalFormat df = new DecimalFormat("#,###,###,##0.##");

   @Override
   public void initialize(URL location, ResourceBundle resources) {
      maHoaDonCol.setCellValueFactory(new PropertyValueFactory<>("maHoaDon"));
      tenKHCol.setCellValueFactory(cellData -> {
         String tenKhachHang = cellData.getValue().getKhachHang().getTenKhachHang();
         return new ReadOnlyStringWrapper(tenKhachHang);
      });
      sdtCol.setCellValueFactory(cellData -> {
         String soDienThoai = "0" + String.valueOf(cellData.getValue().getKhachHang().getSoDienThoai());
         return new ReadOnlyStringWrapper(soDienThoai);
      });
      ngayLapCol.setCellValueFactory(new PropertyValueFactory<>("ngayLap"));
      tongTienCol.setCellValueFactory(new PropertyValueFactory<>("tongTien"));


       try {
           dos.writeUTF("bill-find-all-bill");
          ArrayList<HoaDonThanhToan> arrayList = (ArrayList<HoaDonThanhToan>) in.readObject();
          danhSach_HoaDon = FXCollections.observableArrayList(arrayList);
           tableHoaDon.setItems(danhSach_HoaDon);
       } catch (ClassNotFoundException e) {
           throw new RuntimeException(e);
       } catch (IOException e) {
           throw new RuntimeException(e);
       }

//       danhSach_HoaDon = HoaDonThanhToan.getAllHoaDon();
      tableHoaDon.setItems(danhSach_HoaDon);
      handleEventInButton();
      handleEventInTable();
      tableHoaDon.getSelectionModel().select(0);
      tableDichVu.setSkin(new TableViewSkin<ChiTietHD_DichVu>(tableDichVu) {
         @Override
         public int getItemCount() {
            int r = super.getItemCount();
            return r == 0 ? 1 : r;
         }
      });
   }

   private void xuLyTimHoaDon() throws IOException, Exception {
      // Lấy thông tin từ các TextField
      String maHoaDon = txtMaHoaDon.getText().trim();
      String tenKH = txtTenKH.getText().trim();
      String sdt = txtSDT.getText().trim();
      LocalDate ngayLap = txtNgayLap.getValue();

      // Gửi yêu cầu tìm hóa đơn theo mã hóa đơn
      if (!maHoaDon.isEmpty()) {
         dos.writeUTF("bill-find-bill," + maHoaDon);
      } else if (!tenKH.isEmpty()) {
         dos.writeUTF("bill-find-bill-by-name-customer," + tenKH);
      } else if (!sdt.isEmpty()) {
         if (sdt.charAt(0) == '0') {
            sdt = sdt.substring(1);
         }
         dos.writeUTF("bill-find-bill-by-phone-customer," + sdt);
      } else if (ngayLap != null) {
         Instant ngayLapInstant = ngayLap.atStartOfDay(ZoneId.of("UTC")).toInstant();
         System.out.println(ngayLapInstant);
         dos.writeUTF("bill-find-bill-by-date," + ngayLapInstant.toString());
      }
      Object obj = in.readObject();
      if (obj != null) {
         danhSach_HoaDon = FXCollections.observableArrayList((ArrayList<HoaDonThanhToan>) obj);
         tableHoaDon.setItems(danhSach_HoaDon);
         tableHoaDon.refresh();
      } else {
         // Xử lý trường hợp không nhận được dữ liệu từ server
         System.out.println("Không nhận được dữ liệu từ server");
      }

   }

   private void xoaTrangVaHienDanhSach() throws IOException, ClassNotFoundException {
      // Xóa trắng các TextField
      txtMaHoaDon.clear();
      txtTenKH.clear();
      txtSDT.clear();
      txtNgayLap.setValue(null);
      // Lấy lại toàn bộ danh sách hóa đơn và hiển thị
      dos.writeUTF("bill-find-all-bill");
      danhSach_HoaDon = FXCollections.observableArrayList((ArrayList<HoaDonThanhToan>) in.readObject());
      tableHoaDon.setItems(danhSach_HoaDon);
      tableHoaDon.refresh();
   }

   public void loadDataToForm() {
      HoaDonThanhToan hd = tableHoaDon.getSelectionModel().getSelectedItem();
      txtMaHoaDon.setText(hd.getMaHoaDon());
      txtTenKH.setText(hd.getKhachHang().getTenKhachHang());
      // Thêm số 0 vào đầu số điện thoại khi đặt giá trị cho TextField txtSDT
      txtSDT.setText("0" + String.valueOf(hd.getKhachHang().getSoDienThoai()));
      ZonedDateTime zdt = hd.getNgayLap().atZone(ZoneId.systemDefault());
      LocalDate localDate = zdt.toLocalDate();
      txtNgayLap.setValue(localDate);
   }

   public void handleEventInButton() {
      btnTimKiem.setOnAction(new EventHandler<ActionEvent>() {
         @Override
         public void handle(ActionEvent event) {
            try {
               xuLyTimHoaDon();
            } catch (Exception ex) {
               Logger.getLogger(GD_TraCuuHoaDonController.class.getName()).log(Level.SEVERE, null, ex);
            }
         }
      });

      btnXoaTrangLamMoi.setOnAction(new EventHandler<ActionEvent>() {
         @Override
         public void handle(ActionEvent event) {
            // Gọi phương thức xóa trắng và hiển thị lại danh sách hóa đơn
             try {
                 xoaTrangVaHienDanhSach();
             } catch (IOException e) {
                 throw new RuntimeException(e);
             } catch (ClassNotFoundException e) {
                 throw new RuntimeException(e);
             }
         }
      });
   }


   public void handleEventInTable() {
      tableHoaDon.setOnMouseClicked(new EventHandler<MouseEvent>() {
         @Override
         public void handle(MouseEvent event) {
            loadDataToForm();
         }
      });

      tableHoaDon.setOnKeyPressed(new EventHandler<KeyEvent>() {
         @Override
         public void handle(KeyEvent event) {
            if (event.getCode() == KeyCode.UP || event.getCode() == KeyCode.DOWN) {
               loadDataToForm();
            }
         }
      });

      tableHoaDon.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
         if (newSelection != null) {
            String maHD = newSelection.getMaHoaDon();
            //loadTableDichVu(maHD);
            try {
               tenDichVuCol.setCellValueFactory(cellData -> {
                  String tenDichVu = cellData.getValue().getDichVu().getTenDichVu();
                  return new ReadOnlyStringWrapper(tenDichVu);
               });
               soLuongCol.setCellValueFactory(new PropertyValueFactory<>("soLuong"));
               donViTinhCol.setCellValueFactory(cellData -> {
                  String donViTinh = cellData.getValue().getDichVu().getDonViTinh();
                  return new ReadOnlyStringWrapper(donViTinh);
               });
               thanhTienDVCol.setCellValueFactory(cellData -> {
                  long thanhTien = cellData.getValue().getThanhTien();
                  return new ReadOnlyObjectWrapper<>(thanhTien);
               });


               dos.writeUTF("serviceDetail-find-by-bill-id," + maHD);
                ObservableList<ChiTietHD_DichVu> dsDichVu = FXCollections.observableArrayList((List<ChiTietHD_DichVu>) in.readObject());
                tableDichVu.setItems(dsDichVu);
               tableDichVu.refresh();

            } catch (Exception ex) {
               Logger.getLogger(GD_TraCuuHoaDonController.class.getName()).log(Level.SEVERE, null, ex);
            }

            try {
               maPhongCol.setCellValueFactory((param) -> {
                  String maPhong = param.getValue().getPhong().getMaPhong();
                  return new ReadOnlyObjectWrapper<>(maPhong);
               });
               loaiPhongCol.setCellValueFactory((param) -> {
                  if (param.getValue().getPhong() == null) {
                     return new ReadOnlyObjectWrapper<>();
                  }
                  String loaiPhong = param.getValue().getPhong().getLoaiPhong() == Enum_LoaiPhong.VIP ? "VIP" : "Thường";
                  return new ReadOnlyObjectWrapper<>(loaiPhong);
               });

               gioVaoCol.setCellValueFactory((param) -> {
                  if (param.getValue().getGioVao() == null) {
                     return new ReadOnlyObjectWrapper<>();
                  }
                  LocalDateTime gioVao = LocalDateTime.ofInstant(param.getValue().getGioVao(), ZoneId.systemDefault());
                  String gioVaoFormatted = gioVao.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
                  return new ReadOnlyObjectWrapper<>(gioVaoFormatted);
               });
               gioRaCol.setCellValueFactory((param) -> {
                  if (param.getValue().getGioRa() == null) {
                     return new ReadOnlyObjectWrapper<>();
                  }
                  LocalDateTime gioRa = LocalDateTime.ofInstant(param.getValue().getGioRa(), ZoneId.systemDefault());
                  String gioRaFormatted = gioRa.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
                  return new ReadOnlyObjectWrapper<>(gioRaFormatted);
               });
               gioSuDungCol.setCellValueFactory((param) -> {
                  if (param.getValue().getGioVao() == null || param.getValue().getGioRa() == null) {
                     return new ReadOnlyObjectWrapper<>();
                  }
                  float time = ((float) Duration.between(param.getValue().getGioVao(), param.getValue().getGioRa()).toMillis()) / 1000 / 3600;
                  String gioSuDung = df.format(time);
                  return new ReadOnlyObjectWrapper<>(gioSuDung);
               });
               donGiaCol.setCellValueFactory((param) -> {
                  if (param.getValue().getPhong() == null || param.getValue().getGioRa() == null) {
                     return new ReadOnlyObjectWrapper<>();
                  }
                  long donGia = param.getValue().getPhong().getGiaPhong();
                  return new ReadOnlyObjectWrapper<>(df.format(donGia));
               });
               thanhTienCol.setCellValueFactory((param) -> {
                  if (param.getValue().getGioVao() == null || param.getValue().getGioRa() == null) {
                     return new ReadOnlyObjectWrapper<>();
                  }
                  float time = ((float) Duration.between(param.getValue().getGioVao(), param.getValue().getGioRa()).toMillis()) / 1000 / 3600;
                  long thanhTien;
                  if (LocalDateTime.ofInstant(param.getValue().getGioRa(), ZoneId.systemDefault()).toLocalTime().isAfter(LocalTime.of(18, 0, 0))) {
                     thanhTien = (long) (time * (param.getValue().getPhong().getGiaPhong() + App.TIENPHONGTHEMDEM));
                  } else {
                     thanhTien = (long) (time * param.getValue().getPhong().getGiaPhong());
                  }
                  return new ReadOnlyObjectWrapper<>(df.format(thanhTien));
               });

                dos.writeUTF("roomDetail-find-by-bill-id," + maHD);
               ObservableList<ChiTietHD_Phong> dsPhong = FXCollections.observableArrayList((List<ChiTietHD_Phong>) in.readObject());
               tablePhong.setItems(dsPhong);
            } catch (Exception ex) {
               Logger.getLogger(GD_TraCuuHoaDonController.class.getName()).log(Level.SEVERE, null, ex);
            }
         }
      });
   }

}

