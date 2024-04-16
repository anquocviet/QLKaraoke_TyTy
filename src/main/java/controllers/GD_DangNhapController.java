/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controllers;

import entities.TaiKhoan;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import lombok.SneakyThrows;
import main.App;
import socket.ClientSocket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author thach
 */
public class GD_DangNhapController implements Initializable {

   @SneakyThrows
   @Override
   public void initialize(URL location, ResourceBundle resources) {
      out = new DataOutputStream(socket.getOutputStream());
      in = new DataInputStream(socket.getInputStream());
      oos = new ObjectOutputStream(socket.getOutputStream());
      ois = new ObjectInputStream(socket.getInputStream());
   }

   @FXML
   public void handleKeyboardEvent(KeyEvent ke) throws Exception {
      if (ke.getCode() == KeyCode.ENTER) {
         dangNhap(new ActionEvent(ke.getSource(), ke.getTarget()));
      }
   }

   //    Get data from DB
//    Handle and render data in View
   @FXML
   private void dangNhap(ActionEvent event) throws IOException, Exception {
      String username = txtUsername.getText().trim();
      String password = txtPassword.getText().trim();
      out.writeUTF("login");
      TaiKhoan tk = new TaiKhoan();
      tk.setTenDangNhap(username);
      tk.setMatKhau(password);
      oos.writeObject(tk);

      String line = in.readUTF();
      if (line.equals("login-success")) {
         tk.setNhanVien(((TaiKhoan) ois.readObject()).getNhanVien());
         App.user = tk.getNhanVien().getMaNhanVien();
         Stage stage = (Stage) ((Node) event.getTarget()).getScene().getWindow();
         App.openMainGUI();
         stage.close();
      } else if (line.equals("login-fail")) {
         Alert alert = new Alert(Alert.AlertType.ERROR, "Vui lòng kiểm tra lại tài khoản và mật khẩu của bạn!", ButtonType.OK);
         alert.getDialogPane().setStyle("-fx-font-family: 'sans-serif';");
         alert.setTitle("Đăng nhập thất bại");
         alert.setHeaderText("Sai tài khoản hoặc mật khẩu");
         alert.showAndWait();
      }
   }

//      if (tk == null)
//         Alert alert = new Alert(Alert.AlertType.ERROR, "Vui lòng kiểm tra lại tài khoản và mật khẩu của bạn!", ButtonType.OK);
//         alert.getDialogPane().setStyle("-fx-font-family: 'sans-serif';");
//         alert.setTitle("Đăng nhập thất bại");
//         alert.setHeaderText("Sai tài khoản hoặc mật khẩu");
//         alert.showAndWait();
//      } else {
//         App.user = tk.getNhanVien().getMaNhanVien();
//         Stage stage = (Stage) ((Node) event.getTarget()).getScene().getWindow();
//         App.openMainGUI();
//         stage.close();
//
//      }

   //    Variable
   @FXML
   private TextField txtUsername;
   @FXML
   private TextField txtPassword;

   Socket socket = ClientSocket.getSocket();
   //   TaiKhoan tk = null;
   DataOutputStream out = null;
   DataInputStream in = null;
   ObjectOutputStream oos = null;
   ObjectInputStream ois = null;
}
