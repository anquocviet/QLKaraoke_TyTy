/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controllers;

import entities.KhachHang;
import entities.NhanVien;
import entities.PhieuDatPhong;
import entities.Phong;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import main.App;
import socket.ClientSocket;

import java.io.*;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * FXML Controller class
 *
 * @author vie
 */
public class GD_DatPhongChoController implements Initializable {

   @FXML
   private Button btnClose;
   @FXML
   private Button btnRefresh;
   @FXML
   private Button btnBookWaitingRoom;
   @FXML
   private TextField txtMaPhong;
   @FXML
   private TextField txtSDT;
   @FXML
   private TextField txtTenKH;
   @FXML
   private DatePicker dpNgayNhan;
   @FXML
   private ComboBox<Integer> cbGioNhan;
   @FXML
   private ComboBox<Integer> cbPhutNhan;

   DataInputStream dis = ClientSocket.getDis();
   DataOutputStream dos = ClientSocket.getDos();
   ObjectInputStream in = ClientSocket.getIn();
   ObjectOutputStream out = ClientSocket.getOut();

   @Override
   public void initialize(URL location, ResourceBundle resources) {
      String roomID = GD_QLKinhDoanhPhongController.roomID;
      txtMaPhong.setText(roomID);
      for (int i = 8; i < 24; i++) {
         cbGioNhan.getItems().add(i);
      }
      for (int i = 0; i < 60; i++) {
         cbPhutNhan.getItems().add(i);
      }
      cbGioNhan.getSelectionModel().selectFirst();
      cbPhutNhan.getSelectionModel().selectFirst();
      dpNgayNhan.setValue(LocalDate.now());
      dpNgayNhan.setConverter(new StringConverter<LocalDate>() {
         final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

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
      handleEventInTextField();
      handleEventInButton();
   }

   public void handleEventInTextField() {
      txtSDT.setOnKeyPressed(evt -> {
         if (evt.getCode() == KeyCode.ENTER) {
            try {
               String sdt = txtSDT.getText().trim();
               dos.writeUTF("customer-find-customer-by-phone," + sdt);
               List<KhachHang> khachHangList = (List<KhachHang>) in.readObject();
               if (!khachHangList.isEmpty()) {
                  KhachHang kh = khachHangList.get(0);
                  txtTenKH.setText(kh.getTenKhachHang());
               } else {
                  txtTenKH.setText("");
               }
            } catch (IOException | ClassNotFoundException e) {
               throw new RuntimeException(e);
            }
         }
      });
   }

   public void handleEventInButton() {
      btnClose.setOnAction(evt -> {
         ((Stage) txtMaPhong.getScene().getWindow()).close();
      });
      btnRefresh.setOnAction(evt -> {
         txtSDT.setText("");
         txtTenKH.setText("");
         dpNgayNhan.setValue(LocalDate.now());
         cbGioNhan.getSelectionModel().selectFirst();
         cbPhutNhan.getSelectionModel().selectFirst();
      });

      btnBookWaitingRoom.setOnAction(evt -> {

      });
   }

   public String phatSinhMaPhieuDat(int stt) {
      Date ngayLap = new Date();
      SimpleDateFormat dateFormat = new SimpleDateFormat("MMdd");
      String ngayThangNam = dateFormat.format(ngayLap);

      String strSTT = String.format("%02d", stt + 1);

      SimpleDateFormat yearFormat = new SimpleDateFormat("yy");
      String namCuoi = yearFormat.format(ngayLap);
      String maHoaDon = "PD" + strSTT + ngayThangNam + namCuoi;

      return maHoaDon;
   }


}