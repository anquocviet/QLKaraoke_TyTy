/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controllers;

import entities.ChiTietHD_Phong;
import entities.ChiTietHD_PhongId;
import entities.HoaDonThanhToan;
import entities.PhieuDatPhong;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import lombok.SneakyThrows;
import socket.ClientSocket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * FXML Controller class
 *
 * @author thach
 */
public class GD_ChuyenPhongController implements Initializable {

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
   private TableColumn<Phong, String> tongGioSuDungCol;

   @FXML
   private Label lblPhongMoi;

   @FXML
   private Label lblMaPhong;

   @FXML
   private TextField txtSearch;

   @FXML
   private Button exitButton;

   ObservableList<Phong> listPhong;

   String roomID = GD_QLKinhDoanhPhongController.roomID;

   DataInputStream dis = ClientSocket.getDis();
   DataOutputStream dos = ClientSocket.getDos();
   ObjectInputStream in = ClientSocket.getIn();
   ObjectOutputStream out = ClientSocket.getOut();

   @Override
   public void initialize(URL location, ResourceBundle resources) {

      lblMaPhong.setText(roomID);
      maPhongCol.setCellValueFactory(cellData -> {
         Phong phong = cellData.getValue();
         String maPhong = phong.getMaPhong();
         tongGioSuDungCol.setCellValueFactory(cellData2 -> {
            if (phong.getTinhTrang() == 2) {
               PhieuDatPhong phieu;
               try {
                  dos.writeUTF("bookingTicket-find-booking-ticket-by-room-id," + maPhong);
                  phieu = (PhieuDatPhong) in.readObject();
                  if (phieu.getTinhTrang() == 0) {
                     return new ReadOnlyStringWrapper(tinhTongGioSuDung(phong, phieu));
                  }
               } catch (Exception ex) {
                  Logger.getLogger(GD_ChuyenPhongController.class.getName()).log(Level.SEVERE, null, ex);
               }

            }
            return new ReadOnlyStringWrapper("Đến khi trả phòng hoặc đóng cửa");

         });
         return new ReadOnlyStringWrapper(maPhong);
      });
      tongGioSuDungCol.setCellFactory(column -> {
         return new javafx.scene.control.TableCell<Phong, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
               super.updateItem(item, empty);
               if (empty || item == null) {
                  setText(null);
               } else {
                  Phong phong = getTableView().getItems().get(getIndex());
                  if (phong.getTinhTrang() == 2) {
                     PhieuDatPhong phieu;
                     try {
                        dos.writeUTF("bookingTicket-find-booking-ticket-by-room-id," + phong.getMaPhong());
                        phieu = (PhieuDatPhong) in.readObject();
                        if (phieu.getTinhTrang() == 0) {
                           setText(tinhTongGioSuDung(phong, phieu));
                           return;
                        }
                     } catch (Exception ex) {
                        Logger.getLogger(GD_ChuyenPhongController.class.getName()).log(Level.SEVERE, null, ex);
                     }

                  }
                  setText("Đến khi trả phòng hoặc đóng cửa");
               }
            }
         };
      });
      loaiPhongCol.setCellValueFactory(cellData -> {
         Enum_LoaiPhong loaiPhong = cellData.getValue().getLoaiPhong();
         String loaiPhongString;
         if (loaiPhong == Enum_LoaiPhong.VIP) {
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
            tinhTrangString = "Phòng Trống";
         } else if (tinhTrang == 2) {
            tinhTrangString = "Phòng Chờ";
         } else {
            tinhTrangString = "Đang Sử Dụng";
         }

         return new ReadOnlyStringWrapper(tinhTrangString);

      });
      giaTienMoiGioCol.setCellValueFactory(cellData -> {
         long giaPhong = cellData.getValue().getGiaPhong();
         return new ReadOnlyStringWrapper(giaPhong + "K/H");
      });

      try {
         dataPhong();
      } catch (Exception ex) {
         Logger.getLogger(GD_ChuyenPhongController.class.getName()).log(Level.SEVERE, null, ex);
      }
      table.setItems(listPhong);
      handleEventInTable();
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

   @FXML
   void handleRefresh(ActionEvent event) throws Exception {
      dataPhong();
      table.setItems(listPhong);
      lblPhongMoi.setText("");
      txtSearch.setText("");
   }

   public void docDuLieuTuTable() {
      Phong cp = table.getSelectionModel().getSelectedItem();
      if (cp == null) {
         return;
      }
      lblPhongMoi.setText(cp.getMaPhong());
   }

   //  lấy dữ liệu phòng có thể chuyển
   public void dataPhong() throws Exception {
      dos.writeUTF("room-find-room-by-status,0");
      listPhong = FXCollections.observableArrayList((List<Phong>) in.readObject());
      dos.writeUTF("room-find-room-by-status,2");
      listPhong.addAll((List<Phong>) in.readObject());
      dos.writeUTF("bookingTicket-find-all-booking-ticket-not-used");
      ObservableList<PhieuDatPhong> listPhieuPhongDaDat = FXCollections.observableArrayList((List<PhieuDatPhong>) in.readObject());

      ObservableList<String> listTongGio = FXCollections.observableArrayList();
      ObservableList<Phong> listXoa = FXCollections.observableArrayList();
      for (Phong phong : listPhong) {
         boolean hasMatchingPhieuDatPhong = false;
         boolean checkXoa = false;
         for (PhieuDatPhong phieuDatPhong : listPhieuPhongDaDat) {
            if (phong.equals(phieuDatPhong.getPhong())) {
               float time = ((float) Duration.between(LocalDateTime.now(), phieuDatPhong.getThoiGianNhan()).toMillis()) / 1000 / 3600;
               if (time >= 2) {
                  float gioSuDung = ((float) Duration.between(LocalDateTime.now(), phieuDatPhong.getThoiGianNhan()).toMillis()) / 1000 / 60;
                  long minus = (long) gioSuDung % 60;
                  long hour = ((long) gioSuDung - minus) / 60;
                  listTongGio.add(hour + " giờ " + minus + " phút");
                  listPhieuPhongDaDat.remove(phieuDatPhong);
                  hasMatchingPhieuDatPhong = true;
                  break;
               } else {
                  checkXoa = true;
                  listXoa.add(phong);
                  break;
               }
            }
         }
         if (!hasMatchingPhieuDatPhong && checkXoa == false) {
            listTongGio.add("Đến khi trả phòng hoặc đóng cửa");
         }

      }
      listPhong.removeAll(listXoa);
      tongGioSuDungCol.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(listTongGio.get(table.getItems().indexOf(param.getValue()))));
   }

   private String tinhTongGioSuDung(Phong phong, PhieuDatPhong phieuDatPhong) {
      String result = "";
      float time = ((float) Duration.between(LocalDateTime.now(), phieuDatPhong.getThoiGianNhan()).toMillis()) / 1000 / 3600;
      if (time >= 2) {
         float gioSuDung = ((float) Duration.between(LocalDateTime.now(), phieuDatPhong.getThoiGianNhan()).toMillis()) / 1000 / 60;
         long minus = (long) gioSuDung % 60;
         long hour = ((long) gioSuDung - minus) / 60;
         result = hour + " giờ " + minus + " phút";
      }
      return result;
   }

   @FXML
   void handleExit(ActionEvent event
   ) {
      Stage stage = (Stage) exitButton.getScene().getWindow();
      stage.close();
   }

   @SneakyThrows
   @FXML
   public void handleSearch(ActionEvent event
   ) {

      String searchCode = txtSearch.getText().trim();
      dos.writeUTF("room-find-room," + searchCode);
      ObservableList<Phong> danhSachMaPhong = FXCollections.observableArrayList((List<Phong>) in.readObject());

      if (danhSachMaPhong.size() > 0) {
         table.setItems(danhSachMaPhong);
      } else {
         Alert alert = new Alert(Alert.AlertType.ERROR, "Vui lòng Nhập mã lại ", ButtonType.OK);
         alert.getDialogPane().setStyle("-fx-font-family: 'sans-serif';");
         alert.setTitle("Có lỗi xảy ra");
         alert.setHeaderText("Có vẻ mã phòng đã sai cú pháp hoặc phòng cần tìm không được phép chuyển!");
         alert.showAndWait();

      }
   }

   @FXML
   void handleChuyenPhong(ActionEvent event) throws Exception {
      Phong phongDuocChon = table.getSelectionModel().getSelectedItem();
      dos.writeUTF("bill-find-bill-by-room-id," + roomID);
      String maHoaDon = ((HoaDonThanhToan) in.readObject()).getMaHoaDon();
      dos.writeUTF("roomDetail-find-by-bill-id," + maHoaDon);
      List<ChiTietHD_Phong> listCTHD_Phong = (List<ChiTietHD_Phong>) in.readObject();
      Phong phongHienTai = listCTHD_Phong.getFirst().getPhong();

      if (phongDuocChon != null) {
         phongDuocChon.setTinhTrang(1);
         phongHienTai.setTinhTrang(2);
         dos.writeUTF("room-update-room");
         out.writeObject(phongDuocChon);
         dos.writeUTF("room-update-room");
         out.writeObject(phongHienTai);
         dos.writeUTF("roomDetail-find-by-room-bill-id," + phongHienTai.getMaPhong() + "_" + maHoaDon);
         ChiTietHD_Phong hdPhongHienTai = (ChiTietHD_Phong) in.readObject();

         hdPhongHienTai.setGioRa(Instant.from(LocalDateTime.now()));
         dos.writeUTF("roomDetail-update-roomDetail");
         out.writeObject(hdPhongHienTai);
         Instant gioVaoMoi = hdPhongHienTai.getGioRa();
         ChiTietHD_Phong hdPhongMoi = new ChiTietHD_Phong(new ChiTietHD_PhongId(), hdPhongHienTai.getHoaDon(), phongDuocChon, gioVaoMoi, Instant.parse("9999-12-31T23:59:59Z"), 0.0, 0);
         dos.writeUTF("roomDetail-add-roomDetail");
         out.writeObject(hdPhongMoi);
         showSuccessAlert();

      } else {
         showErrorAlert("Vui lòng chọn phòng trong danh sách");
      }
   }

   private void showSuccessAlert() {
      Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.OK);
      configureAlert(alert, "Chuyển phòng thành công!");
      alert.showAndWait();
   }

   private void showErrorAlert(String message) {
      Alert alert = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
      configureAlert(alert, "Có lỗi xảy ra");
      alert.showAndWait();
   }

   private void configureAlert(Alert alert, String title) {
      alert.getDialogPane().setStyle("-fx-font-family: 'sans-serif';");
      alert.setTitle("Thông báo");
      alert.setHeaderText(title);

      // Xử lý sự kiện khi nút "OK" được nhấn
      ButtonType buttonTypeOK = alert.getButtonTypes().stream()
                                      .filter(buttonType -> buttonType.getButtonData() == ButtonBar.ButtonData.OK_DONE)
                                      .findFirst()
                                      .orElse(null);

      if (buttonTypeOK != null) {
         Button buttonOK = (Button) alert.getDialogPane().lookupButton(buttonTypeOK);
         buttonOK.setOnAction(event -> {
            handleExit(event);
            alert.close();
         });
      }
   }

}
