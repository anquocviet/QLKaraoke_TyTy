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
import java.io.ObjectInput;
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
   @FXML
   private TextField txtUsername;
   @FXML
   private TextField txtPassword;

   DataInputStream dis = ClientSocket.getDis();
   DataOutputStream dos = ClientSocket.getDos();
   ObjectOutputStream out = ClientSocket.getOut();
   ObjectInputStream in = ClientSocket.getIn();

   @SneakyThrows
   @Override
   public void initialize(URL location, ResourceBundle resources) {
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
      dos.writeUTF("account-login");
      TaiKhoan tk = new TaiKhoan();
      tk.setTenDangNhap(username);
      tk.setMatKhau(password);
      out.writeObject(tk);

      String line = dis.readUTF();
      if (line.equals("login-success")) {
         if (in == null)
            in = ClientSocket.getIn();
         tk.setNhanVien(((TaiKhoan) in.readObject()).getNhanVien());
         App.user = tk.getNhanVien();
//         System.out.println(App.user);
         Stage stage = (Stage) ((Node) event.getTarget()).getScene().getWindow();
         App.openMainGUI();
         stage.close();
      } else {
         Alert alert = new Alert(Alert.AlertType.ERROR, "Vui lòng kiểm tra lại tài khoản và mật khẩu của bạn!", ButtonType.OK);
         alert.getDialogPane().setStyle("-fx-font-family: 'sans-serif';");
         alert.setTitle("Đăng nhập thất bại");
         alert.setHeaderText("Sai tài khoản hoặc mật khẩu");
         alert.showAndWait();
      }
   }
}
