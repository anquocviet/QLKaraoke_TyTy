/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controllers;

import entities.KhachHang;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.skin.TableViewSkin;
import javafx.scene.control.skin.VirtualFlow;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import socket.ClientSocket;

import java.io.*;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

/**
 * FXML Controller class
 *
 * @author vie
 */
public class GD_QLKhachHangController implements Initializable {
   DataInputStream dis = ClientSocket.getDis();
   DataOutputStream dos = ClientSocket.getDos();
   ObjectOutputStream out = ClientSocket.getOut();
   ObjectInputStream in = ClientSocket.getIn();
   private List<KhachHang> khachHangs;

   @Override
   public void initialize(URL location, ResourceBundle resources) {
      genderGroup = new ToggleGroup();
      txtMaKhachHang.setText(phatSinhMaKhachHang());
      radioButtonNam.setToggleGroup(genderGroup);
      radioButtonNu.setToggleGroup(genderGroup);

//        Tạo index column
      sttCol.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(table.getItems().indexOf(param.getValue()) + 1));
      maKHCol.setCellValueFactory(new PropertyValueFactory<>("maKhachHang"));
      tenKHCol.setCellValueFactory(new PropertyValueFactory<>("tenKhachHang"));
//      sdtCol.setCellValueFactory(new PropertyValueFactory<>("soDienThoai"));
      sdtCol.setCellValueFactory(cellData -> {
         Integer soDienThoai = cellData.getValue().getSoDienThoai();
         String soDienThoaiString = "0" + soDienThoai;
         return new ReadOnlyStringWrapper(soDienThoaiString);
      });
      namSinhCol.setCellValueFactory(new PropertyValueFactory<>("namSinh"));
      gioiTinhCol.setCellValueFactory(cellData -> {
         boolean gioiTinh = cellData.getValue().getGioiTinh() == 1;
         String gioiTinhString;
         if (gioiTinh) {
            gioiTinhString = "Nam";
         } else {
            gioiTinhString = "Nữ";
         }
         return new ReadOnlyStringWrapper(gioiTinhString);
      });
      spinnerNamSinh.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1000, 3000, 2000));
      khachHangs = getAllKhachHang();
      FXCollections.observableArrayList(khachHangs);
      table.getItems().addAll(khachHangs);

      table.requestFocus();
      table.getSelectionModel().select(0);
      table.getSelectionModel().focus(0);
      docDuLieuTuTable();
      handleEventInTextField();
      handleEventInTable();
   }

   private List<KhachHang> getAllKhachHang()  {
      try {
         dos.writeUTF("customer-find-all-customer");
         return (List<KhachHang>) in.readObject();
      } catch (IOException | ClassNotFoundException e) {
         e.printStackTrace();
     }
       return null;
   }

   public void handleEventInTable() {
      table.setOnMouseClicked((MouseEvent event) -> {
         docDuLieuTuTable();
      });
      table.setOnKeyPressed((KeyEvent event) -> {
         if (event.getCode() == KeyCode.UP || event.getCode() == KeyCode.DOWN) {
            docDuLieuTuTable();
         }
      });
   }

   public void handleEventInTextField() {
      txtSDT.setOnKeyTyped((event) -> {
         if (txtSDT.getText().trim().isEmpty()) {
            return;
         }
         if (!Pattern.matches("[\\d]*", txtSDT.getText().trim())) {
            txtSDT.setText(txtSDT.getText().trim().replaceAll("[^\\d]", ""));
            txtSDT.positionCaret(txtSDT.getText().length());
         }
      });
      spinnerNamSinh.getEditor().setOnKeyTyped((event) -> {
         TextField txtNamSinh = spinnerNamSinh.getEditor();
         if (!Pattern.matches("[\\d]*", txtNamSinh.getText().trim())) {
            txtNamSinh.setText(txtNamSinh.getText().trim().replaceAll("[^\\d]", ""));
         }
         if (txtNamSinh.getText().trim().isEmpty()) {
            txtNamSinh.setText("1000");
            return;
         }
         txtNamSinh.positionCaret(txtNamSinh.getText().length());
      });
      inputTimKiem.setOnKeyPressed((event) -> {
         if (event.getCode() == KeyCode.ENTER) {
//            table.setItems(KhachHang.fillterCustomerByID_NameOrPhoneNumber(inputTimKiem.getText().trim()));
            table.setItems(FXCollections.observableArrayList(fillterCustomerByID_NameOrPhoneNumber(inputTimKiem.getText().trim())));
            table.refresh();
         }
      });
//      inputTimKiem.focusedProperty().addListener((obs, oldVal, newVal) -> {
//         if (!newVal) {
////            table.setItems(KhachHang.fillterCustomerByID_NameOrPhoneNumber(inputTimKiem.getText().trim()));
//            table.setItems(FXCollections.observableArrayList(fillterCustomerByID_NameOrPhoneNumber(inputTimKiem.getText().trim())));
//            table.refresh();
//         }
//      });
   }

   public boolean validateData() {
      if (txtSDT.getText().trim().length() != 10) {
         Alert alert = new Alert(Alert.AlertType.ERROR, "", ButtonType.OK);
         alert.getDialogPane().setStyle("-fx-font-family: 'sans-serif';");
         alert.setTitle("Số điện thoại không phù hợp");
         alert.setHeaderText("Số điện thoại phải là số bao gồm 10 chữ số");
         alert.showAndWait();
         txtSDT.requestFocus();
         return false;
      }
      return true;
   }

   private List<KhachHang> fillterCustomerByID_NameOrPhoneNumber(String text) {
      try {
         List<KhachHang> khachHangsIsFilter = new ArrayList<>();
         // filter
         khachHangs.forEach(khachHang -> {
            if (khachHang.getMaKhachHang().contains(text) || khachHang.getTenKhachHang().contains(text) || (khachHang.getSoDienThoai()+"").contains(text)) {
               khachHangsIsFilter.add(khachHang);
            }
         });
            return khachHangsIsFilter;
      } catch (Exception e) {
         e.printStackTrace();
      }
      return null;
   }

   //  Render and handle in View'
   public void docDuLieuTuTable() {
      KhachHang kh = table.getSelectionModel().getSelectedItem();
      if (kh == null) {
         return;
      }
      txtMaKhachHang.setText(kh.getMaKhachHang());
      txtTenKhachHang.setText(kh.getTenKhachHang());
      txtSDT.setText("0"+kh.getSoDienThoai());
      spinnerNamSinh.getValueFactory().setValue(kh.getNamSinh());
      if (kh.getGioiTinh() == 1) {
         genderGroup.getToggles().get(0).setSelected(true);
      } else {
         genderGroup.getToggles().get(1).setSelected(true);
      }
   }

   public void xuLyThemKhachHang() throws IOException, ClassNotFoundException {
      String maKH = txtMaKhachHang.getText();
      String tenKH = txtTenKhachHang.getText().trim();
      String sdt = txtSDT.getText().trim();
      int namSinh = (Integer) spinnerNamSinh.getValue();
      boolean gioiTinh = true;
      if (genderGroup.getToggles().get(1).isSelected()) {
         gioiTinh = false;
      }
      // check
       if (kiemTraHoaKyTuDau(tenKH) == false) {
           Alert alert = new Alert(Alert.AlertType.ERROR, "Vui lòng kiểm tra lại thông tin khách hàng", ButtonType.OK);
           alert.getDialogPane().setStyle("-fx-font-family: 'sans-serif';");
           alert.setTitle("Thêm khách hàng thất bại");
           alert.setHeaderText("Tên khách hàng phải viết hoa ký tự đầu");
           alert.showAndWait();
           return;
       }

      dos.writeUTF("customer-find-customer,"+maKH);
      dos.flush();
      List<KhachHang> khachHangExist = (List<KhachHang>) in.readObject();

      if (khachHangExist.size() > 0){
         Alert alert = new Alert(Alert.AlertType.ERROR, "Vui lòng kiểm tra lại thông tin khách hàng", ButtonType.OK);
         alert.getDialogPane().setStyle("-fx-font-family: 'sans-serif';");
         alert.setTitle("Thêm khách hàng thất bại");
         alert.setHeaderText("Đã có thông tin khách hàng trong hệ thống");
         alert.showAndWait();
         return;
      }
      if (!validateData()) {
         return;
      }
      KhachHang kh = new KhachHang(maKH, tenKH, Integer.parseInt(sdt), namSinh, gioiTinh==true?1:0);

        dos.writeUTF("customer-add-customer");
        dos.flush();
        out.writeObject(kh);
        boolean isAdd = dis.readBoolean();
        if (isAdd){
         khachHangs.add(kh);
            table.setItems(FXCollections.observableArrayList(khachHangs));
            VirtualFlow<?> vf = ((VirtualFlow<?>) ((TableViewSkin<?>) table.getSkin()).getChildren().get(1));
            vf.scrollTo(vf.getLastVisibleCell().getIndex());
            table.getSelectionModel().select(kh);
           xuLyLamMoiThongTinKhachHang();
        }else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Vui lòng kiểm tra lại thông tin khách hàng", ButtonType.OK);
            alert.getDialogPane().setStyle("-fx-font-family: 'sans-serif';");
            alert.setTitle("Thêm khách hàng thất bại");
            alert.setHeaderText("Thêm khách hàng thất bại");
            alert.showAndWait();
        }
   }

   public void xuLySuaThongTinKhachHang() throws IOException {
      String maKH = txtMaKhachHang.getText();
      String tenKH = txtTenKhachHang.getText().trim();
      String sdt = txtSDT.getText().trim();
      int namSinh = (Integer) spinnerNamSinh.getValue();
      boolean gioiTinh = true;
      if (genderGroup.getToggles().get(1).isSelected()) {
         gioiTinh = false;
      }
//      if (!validateData()){
//         return;
//      }
      if (kiemTraHoaKyTuDau(tenKH) == false) {
         Alert alert = new Alert(Alert.AlertType.ERROR, "Vui lòng kiểm tra lại thông tin khách hàng", ButtonType.OK);
         alert.getDialogPane().setStyle("-fx-font-family: 'sans-serif';");
         alert.setTitle("Thêm khách hàng thất bại");
         alert.setHeaderText("Tên khách hàng phải viết hoa ký tự đầu");
         alert.showAndWait();
         return;
      }

      KhachHang kh = new KhachHang(maKH, tenKH, Integer.parseInt(sdt), namSinh, gioiTinh==true ? 1 : 0);

      dos.writeUTF("customer-update-customer");
        dos.flush();
        out.writeObject(kh);
        boolean isUpdate = dis.readBoolean();
        if (isUpdate){
            int index = khachHangs.indexOf(kh);
            khachHangs.set(index, kh);
            table.setItems(FXCollections.observableArrayList(khachHangs));
            table.getSelectionModel().select(kh);
           table.refresh();
           xuLyLamMoiThongTinKhachHang();
        }else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Vui lòng kiểm tra lại thông tin khách hàng", ButtonType.OK);
            alert.getDialogPane().setStyle("-fx-font-family: 'sans-serif';");
            alert.setTitle("Sửa thông tin khách hàng thất bại");
            alert.setHeaderText("Sửa thông tin khách hàng thất bại");
            alert.showAndWait();
        }

   }

   public void xuLyLamMoiThongTinKhachHang() {
      txtMaKhachHang.setText(phatSinhMaKhachHang());
      txtTenKhachHang.setText("");
      txtSDT.setText("");
      spinnerNamSinh.getValueFactory().setValue(2000);
      genderGroup.getToggles().get(0).setSelected(true);
      table.getSelectionModel().clearSelection();
   }
   public boolean kiemTraHoaKyTuDau(String ten) {
      // Kiểm tra xem chuỗi có rỗng không
      if (ten.isEmpty()) {
         return false;
      }

      // Tách chuỗi thành các từ bằng dấu cách
      String[] tu = ten.split(" ");

      // Kiểm tra từng từ trong chuỗi
      for (String word : tu) {
         // Kiểm tra xem từ có ít nhất một kí tự không phải là chữ hoa không
         if (!Character.isUpperCase(word.charAt(0))) {
            return false;
         }
      }

      // Nếu tất cả các từ đều viết hoa kí tự đầu, trả về true
      return true;
   }


   public String phatSinhMaKhachHang(){
      String maKH = "KH";
      DecimalFormat df = new DecimalFormat("0000");
      // generate random number
       try {
           dos.writeUTF("customer-find-all-customer");
          dos.flush();
          List<KhachHang> list = (List<KhachHang>) in.readObject();
          KhachHang lasted = list.get(list.size() - 1);
          String maKHLasted = lasted.getMaKhachHang();
          int number = Integer.parseInt(maKHLasted.substring(2)) + 1;
          maKH += df.format(number);
       } catch (IOException | ClassNotFoundException e) {
           throw new RuntimeException(e);
       }

       return maKH;
   }

   //    Variable
   @FXML
   private TextField txtMaKhachHang;
   @FXML
   private TextField txtTenKhachHang;
   @FXML
   private TextField txtSDT;
   @FXML
   private Spinner spinnerNamSinh;
   @FXML
   private RadioButton radioButtonNam;
   @FXML
   private RadioButton radioButtonNu;
   @FXML
   private ToggleGroup genderGroup;
   @FXML
   private TableView<KhachHang> table;
   @FXML
   private TableColumn<String, Integer> sttCol;
   @FXML
   private TableColumn<KhachHang, String> maKHCol;
   @FXML
   private TableColumn<KhachHang, String> tenKHCol;
   @FXML
   private TableColumn<KhachHang, String> sdtCol;
   @FXML
   private TableColumn<KhachHang, Integer> namSinhCol;
   @FXML
   private TableColumn<KhachHang, String> gioiTinhCol;
   @FXML
   private TextField inputTimKiem;
}
