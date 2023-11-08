/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.TaiKhoan;
import main.App;

/**
 * FXML Controller class
 *
 * @author thach
 */
public class GD_DangNhapController implements Initializable {

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

        TaiKhoan tk = TaiKhoan.getTaiKhoanTheoUserNameAndPassword(username, password);
        if (tk == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Vui lòng kiểm tra lại tài khoản và mật khẩu của bạn!", ButtonType.OK);
            alert.getDialogPane().setStyle("-fx-font-family: 'sans-serif';");
            alert.setTitle("Đăng nhập thất bại");
            alert.setHeaderText("Sai tài khoản hoặc mật khẩu");
            alert.showAndWait();
        } else {
            App.user = tk.getNhanVien().getMaNhanVien();
            Stage stage = (Stage) ((Node) event.getTarget()).getScene().getWindow();
            stage.close();
        }

    }

//    Variable
    @FXML
    private TextField txtUsername;
    @FXML
    private TextField txtPassword;
    @FXML
    private Text lblForgotPassword;
    @FXML
    private Button btnDangNhap;
}
