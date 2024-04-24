/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controllers;

import entities.Phong;
import enums.Enum_LoaiPhong;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import socket.ClientSocket;

import java.io.*;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * FXML Controller class
 *
 * @author thach
 */
public class GD_QLPhongController implements Initializable {

   @FXML
   private Button btnAdd;
   @FXML
   private Button btnClear;
   @FXML
   private Button btnUpdate;
   @FXML
   private ComboBox<String> cbbTinhTrang;
   DataInputStream dis = ClientSocket.getDis();
   DataOutputStream dos = ClientSocket.getDos();
   ObjectInputStream in = ClientSocket.getIn();
   ObjectOutputStream out = ClientSocket.getOut();
   private static final String regexPhong = "^P([1-9]\\d{0,1})0([1-9]\\d{0,1})$";

   @Override
   public void initialize(URL location, ResourceBundle resources) {
      cbbTinhTrang.getItems().addAll("PHÒNG TRỐNG", "PHÒNG ĐANG SỬ DỤNG", "PHÒNG CHỜ");
<<<<<<< HEAD

      // Khởi tạo một ObservableList để chứa danh sách các loại phòng
      ObservableList<Enum_LoaiPhong> loaiPhongList = FXCollections.observableArrayList();
// Thêm tất cả các giá trị của enum vào danh sách
      loaiPhongList.addAll(Arrays.asList(Enum_LoaiPhong.values()));
      cbbLoaiPhong.setItems(loaiPhongList);

      sttCol.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(table.getItems().indexOf(param.getValue()) + 1));
      maPhongCol.setCellValueFactory(new PropertyValueFactory<>("maPhong"));
      sucChuaCol.setCellValueFactory(new PropertyValueFactory<>("sucChua"));
      tinhTrangCol.setCellValueFactory(cellData -> {
         int tinhTrang = cellData.getValue().getTinhTrang();
=======
//      cbbLoaiPhong.setItems(Enum_LoaiPhong.getListLoaiPhong());
//      sttCol.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(table.getItems().indexOf(param.getValue()) + 1));
//      maPhongCol.setCellValueFactory(new PropertyValueFactory<>("maPhong"));
//      sucChuaCol.setCellValueFactory(new PropertyValueFactory<>("sucChua"));
//      tinhTrangCol.setCellValueFactory(cellData -> {
//         int tinhTrang = cellData.getValue().getTinhTrang();
>>>>>>> 2a823cc7a1829cf4b9d0bf974d4879c776daea4b
         String tinhTrangString;
         if (tinhTrang == 0) {
            tinhTrangString = "PHÒNG TRỐNG";
         } else if (tinhTrang == 1) {
            tinhTrangString = "PHÒNG ĐANG SỬ DỤNG";
         } else {
            tinhTrangString = "PHÒNG CHỜ";
         }
         return new ReadOnlyStringWrapper(tinhTrangString);
      });


      giaPhongCol.setCellValueFactory(cellData -> {
         // hỉen thị giá phòng theo định dạng 100.000đ/h
         float giaPhong = cellData.getValue().getGiaPhong();
         String giaPhongString = String.format("%,.0f", giaPhong);
         return new ReadOnlyStringWrapper(giaPhongString + "đ/h");

      });
      loaiPhongCol.setCellValueFactory(cellData -> {
         int loaiPhong = cellData.getValue().getLoaiPhong().ordinal();
         String loaiPhongString;
         if (loaiPhong == 1) {
            loaiPhongString = "VIP";
         } else {
            loaiPhongString = "THƯỜNG";
         }
         return new ReadOnlyStringWrapper(loaiPhongString);
      });
      table.setItems(FXCollections.observableArrayList(getAllRoom()));
      table.getSelectionModel().select(0);
      handleEventInTable();
      docDuLieuTuTable();
//
//
      btnAdd.setOnAction(e -> {
         if (checkEmptyField()) {
            AlterErr("Vui lòng nhập đầy đủ thông tin");
            return;
         }

         String maPhong = txtMaPhong.getText();

         if (!checkMaPhong(maPhong.trim())) {
            AlterErr("Mã phòng không hợp lệ phải theo định dạng Pxx0xx");
            return;
         }

         if (checkIsExist(maPhong.trim())) {
            AlterErr("Mã phòng đã tồn tại");
            return;
         }
         if (!checkKieuSoNguyenDuong(txtSucChua.getText())) {
            AlterErr("Sức chứa phải là số nguyên dương");
            return;
         }

         if (!checkKieuSoNguyenDuong(txtGiaPhong.getText())) {
            AlterErr("Giá phòng phải là số nguyên dương");
            return;
         }

         int sucChua = Integer.parseInt(txtSucChua.getText());
         String tinhTrang = cbbTinhTrang.getValue();
         int giaPhong = Integer.parseInt(txtGiaPhong.getText());
         String loaiPhong = cbbLoaiPhong.getValue().toString();

         String loaiPhongDb = loaiPhong.equals("VIP") ? "1" : "0";
         int tinhTrangDb = tinhTrang.equals("PHÒNG TRỐNG") ? 0 : tinhTrang.equals("PHÒNG ĐANG SỬ DỤNG") ? 1 : 2;

         if (loaiPhongDb.equals("1")) {
            maPhong = maPhong + "VIP";
         }

           Phong phong = new Phong(maPhong, cbbLoaiPhong.getValue().toString().equals("VIP")
                   ? Enum_LoaiPhong.VIP : Enum_LoaiPhong.THUONG
                   , tinhTrangDb, sucChua, giaPhong);
              if (savePhongToDb(phong)) {
                  table.setItems(FXCollections.observableArrayList(getAllRoom()));
                  table.refresh();
              } else {
                  AlterErr("Thêm phòng thất bại");
              }
      });
//
      btnClear.setOnAction(e -> {
         txtMaPhong.setText("");
         txtSucChua.setText("");
         cbbTinhTrang.setValue("");
         txtGiaPhong.setText("");
         cbbLoaiPhong.setValue("");
         table.getSelectionModel().clearSelection();
      });
//
      btnUpdate.setOnAction(e -> {

         if (checkEmptyField()) {
            AlterErr("Vui lòng nhập đầy đủ thông tin");
            return;
         }

         if (!checkKieuSoNguyenDuong(txtSucChua.getText())) {
            AlterErr("Sức chứa phải là số nguyên dương");
            return;
         }

         if (!checkKieuSoNguyenDuong(txtGiaPhong.getText())) {
            AlterErr("Giá phòng phải là số nguyên dương");
            return;
         }

         Phong p = table.getSelectionModel().getSelectedItem();


         String maPhong = txtMaPhong.getText();
         int sucChua = Integer.parseInt(txtSucChua.getText());
         String tinhTrang = cbbTinhTrang.getValue();
         int giaPhong = Integer.parseInt(txtGiaPhong.getText());
         String loaiPhong = cbbLoaiPhong.getValue().toString();

         String loaiPhongDb = loaiPhong.equals("VIP") ? "1" : "0";
         int tinhTrangDb = tinhTrang.equals("PHÒNG TRỐNG") ? 0 : tinhTrang.equals("PHÒNG ĐANG SỬ DỤNG") ? 1 : 2;
         String maPhongCu = p.getMaPhong();

         if (!maPhong.equals(maPhongCu) && checkIsExist(maPhong.trim())) {
            AlterErr("Mã phòng đã tồn tại");
            return;
         }

         Phong phong = new Phong(maPhong, loaiPhong.equals("VIP") ? Enum_LoaiPhong.VIP : Enum_LoaiPhong.THUONG
                 , tinhTrangDb, sucChua, giaPhong);

         boolean isSuccess = updateRoomToDb(phong);
            if (isSuccess) {
               int index = table.getSelectionModel().getSelectedIndex();
                table.getItems().set(index, phong);
                table.refresh();
            } else {
                AlterErr("Cập nhật phòng thất bại");
            }
      });

   }

   private boolean updateRoomToDb(Phong phong){
        try {
             dos.writeUTF("room-update-room");
             out.writeObject(phong);
             return dis.readBoolean();
        } catch (IOException e) {
             e.printStackTrace();
             return false;
        }
   }

   private static void AlterErr(String s) {
      Alert alert = new Alert(Alert.AlertType.ERROR);
      alert.setTitle("Lỗi");
      alert.setHeaderText(s);
      alert.showAndWait();
   }

   public void handleEventInTable() {
      table.setOnMouseClicked(event -> {
         docDuLieuTuTable();
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
//
   private boolean checkEmptyField() {
      return txtMaPhong.getText().isEmpty() || txtSucChua.getText().isEmpty() || cbbTinhTrang.getValue().isEmpty() || txtGiaPhong.getText().isEmpty() || cbbLoaiPhong.getValue().toString().isEmpty();
   }
//
   private boolean checkMaPhong(String maPhong) {
      Pattern pattern = Pattern.compile(regexPhong);
      Matcher matcher = pattern.matcher(maPhong);
      return matcher.matches();
   }
//
   private boolean checkIsExist(String maPhong) {
        try {
             dos.writeUTF("room-find-room,"+maPhong);
             dos.writeUTF(maPhong);
             List<Phong> phongList = (List<Phong>) in.readObject();
             return phongList.size() > 0;
        } catch (IOException | ClassNotFoundException e) {
             e.printStackTrace();
             return false;
        }
   }

   private boolean savePhongToDb(Phong phong){
        try {
             dos.writeUTF("room-add-room");
             out.writeObject(phong);
             return dis.readBoolean();
        } catch (IOException e) {
             e.printStackTrace();
             return false;
        }
   }
//
   private boolean checkKieuSoNguyenDuong(String so) {
      Pattern pattern = Pattern.compile("^[1-9]\\d*$");
      Matcher matcher = pattern.matcher(so);
      return matcher.matches();
   }
//
//   //  Render and handle in View
   public void docDuLieuTuTable() {
      Phong p = table.getSelectionModel().getSelectedItem();
      if (p == null) {
         return;
      }
      txtMaPhong.setText(p.getMaPhong());
      String emptyString = "";
      txtSucChua.setText((emptyString + p.getSucChua()));

      if (p.getTinhTrang() == 0) {
         cbbTinhTrang.setValue("PHÒNG TRỐNG");
      } else if (p.getTinhTrang() == 1) {
         cbbTinhTrang.setValue("PHÒNG ĐANG SỬ DỤNG");
      } else if (p.getTinhTrang() == 2) {
         cbbTinhTrang.setValue("PHÒNG CHỜ");
      } else {
         cbbTinhTrang.getSelectionModel().clearSelection();
      }


      txtGiaPhong.setText(p.getGiaPhong() + emptyString);

      cbbLoaiPhong.getItems().clear();
      cbbLoaiPhong.getItems().addAll(Enum_LoaiPhong.values());

      if (p.getLoaiPhong() == Enum_LoaiPhong.THUONG) {
         cbbLoaiPhong.getSelectionModel().select(0);
      } else {
         cbbLoaiPhong.getSelectionModel().select(1);
      }


   }

   private List<Phong> getAllRoom() {
      try {
         // Gửi yêu cầu lấy danh sách các phòng
         dos.writeUTF("room-find-all-room");

         // Đọc danh sách các phòng từ server
         List<Phong> phongList = (List<Phong>) in.readObject();
         return phongList;
      } catch (IOException | ClassNotFoundException e) {
         e.printStackTrace();
         return null;
      }
   }

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
   private ComboBox cbbLoaiPhong;
   @FXML
   private TextField txtGiaPhong;

}

