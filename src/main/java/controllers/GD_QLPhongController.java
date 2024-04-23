/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controllers;

import enums.Enum_LoaiPhong;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

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

   private static final String regexPhong = "^P([1-9]\\d{0,1})0([1-9]\\d{0,1})$";

   @Override
   public void initialize(URL location, ResourceBundle resources) {
      cbbTinhTrang.getItems().addAll("PHÒNG TRỐNG", "PHÒNG ĐANG SỬ DỤNG", "PHÒNG CHỜ");
//      cbbLoaiPhong.setItems(Enum_LoaiPhong.getListLoaiPhong());
//      sttCol.setCellValueFactory(param ->new ReadOnlyObjectWrapper<>(table.getItems().indexOf(param.getValue()) + 1));
//      maPhongCol.setCellValueFactory(new PropertyValueFactory<>("maPhong"));
//      sucChuaCol.setCellValueFactory(new PropertyValueFactory<>("sucChua"));
//      tinhTrangCol.setCellValueFactory(cellData -> {
//         int tinhTrang = cellData.getValue().getTinhTrang();
         String tinhTrangString;
//         if (tinhTrang == 0) {
//            tinhTrangString = "PHÒNG TRỐNG";
//         } else if (tinhTrang == 1) {
//            tinhTrangString = "PHÒNG ĐANG SỬ DỤNG";
//         } else {
//            tinhTrangString = "PHÒNG CHỜ";
//         }
//         return new ReadOnlyStringWrapper(tinhTrangString);
//      });


//      giaPhongCol.setCellValueFactory(cellData -> {
//         // hỉen thị giá phòng theo định dạng 100.000đ/h
//         float giaPhong = cellData.getValue().getGiaPhong();
//         String giaPhongString = String.format("%,.0f", giaPhong);
//         return new ReadOnlyStringWrapper(giaPhongString + "đ/h");
//
//      });
//      loaiPhongCol.setCellValueFactory(cellData -> {
//         int loaiPhong = cellData.getValue().getLoaiPhong();
//         String loaiPhongString;
//         if (loaiPhong == 1) {
//            loaiPhongString = "VIP";
//         } else {
//            loaiPhongString = "THƯỜNG";
//         }
//         return new ReadOnlyStringWrapper(loaiPhongString);
//      });
//
//      table.setItems(Phong.getAllPhong());
//      table.getSelectionModel().select(0);
//      handleEventInTable();
//      docDuLieuTuTable();
//
//
//      btnAdd.setOnAction(e -> {
//         if (checkEmptyField()) {
//            AlterErr("Vui lòng nhập đầy đủ thông tin");
//            return;
//         }
//
//         String maPhong = txtMaPhong.getText();
//
//         if (!checkMaPhong(maPhong.trim())) {
//            AlterErr("Mã phòng không hợp lệ phải theo định dạng Pxx0xx");
//            return;
//         }
//
//         if (checkIsExist(maPhong.trim())) {
//            AlterErr("Mã phòng đã tồn tại");
//            return;
//         }
//         if (!checkKieuSoNguyenDuong(txtSucChua.getText())) {
//            AlterErr("Sức chứa phải là số nguyên dương");
//            return;
//         }
//
//         if (!checkKieuSoNguyenDuong(txtGiaPhong.getText())) {
//            AlterErr("Giá phòng phải là số nguyên dương");
//            return;
//         }
//
//         int sucChua = Integer.parseInt(txtSucChua.getText());
//         String tinhTrang = cbbTinhTrang.getValue();
//         float giaPhong = Float.parseFloat(txtGiaPhong.getText());
//         String loaiPhong = cbbLoaiPhong.getValue().toString();
//
//         String loaiPhongDb = loaiPhong.equals("VIP") ? "1" : "0";
//         int tinhTrangDb = tinhTrang.equals("PHÒNG TRỐNG") ? 0 : tinhTrang.equals("PHÒNG ĐANG SỬ DỤNG") ? 1 : 2;
//
//
//         if (loaiPhongDb.equals("1")) {
//            maPhong = maPhong + "VIP";
//         }
//
//         Phong.addPhong(maPhong, loaiPhongDb, tinhTrangDb, sucChua, giaPhong);
//         table.setItems(Phong.getAllPhong());
//
//      });
//
//      btnClear.setOnAction(e -> {
//         txtMaPhong.setText("");
//         txtSucChua.setText("");
//         cbbTinhTrang.setValue("");
//         txtGiaPhong.setText("");
//         cbbLoaiPhong.setValue("");
//         table.getSelectionModel().clearSelection();
//      });
//
//      btnUpdate.setOnAction(e -> {
//
//         if (checkEmptyField()) {
//            AlterErr("Vui lòng nhập đầy đủ thông tin");
//            return;
//         }
//
//         if (!checkKieuSoNguyenDuong(txtSucChua.getText())) {
//            AlterErr("Sức chứa phải là số nguyên dương");
//            return;
//         }
//
//         if (!checkKieuSoNguyenDuong(txtGiaPhong.getText())) {
//            AlterErr("Giá phòng phải là số nguyên dương");
//            return;
//         }
//
//         Phong p = table.getSelectionModel().getSelectedItem();
//
//
//         String maPhong = txtMaPhong.getText();
//         int sucChua = Integer.parseInt(txtSucChua.getText());
//         String tinhTrang = cbbTinhTrang.getValue();
//         float giaPhong = Float.parseFloat(txtGiaPhong.getText());
//         String loaiPhong = cbbLoaiPhong.getValue().toString();
//
//         String loaiPhongDb = loaiPhong.equals("VIP") ? "1" : "0";
//         int tinhTrangDb = tinhTrang.equals("PHÒNG TRỐNG") ? 0 : tinhTrang.equals("PHÒNG ĐANG SỬ DỤNG") ? 1 : 2;
//         String maPhongCu = p.getMaPhong();
//
//         if (loaiPhongDb.equals("1")) {
//            maPhong = maPhong + "VIP";
//         }
//
//         if (!maPhong.equals(maPhongCu) && checkIsExist(maPhong.trim())) {
//            AlterErr("Mã phòng đã tồn tại");
//            return;
//         }
//
//
//         Phong.updatePhong(maPhong, loaiPhongDb, tinhTrangDb, sucChua, giaPhong, maPhongCu);
//         table.setItems(Phong.getAllPhong());
//         table.refresh();
//      });

   }

//   private static void AlterErr(String s) {
//      Alert alert = new Alert(Alert.AlertType.ERROR);
//      alert.setTitle("Lỗi");
//      alert.setHeaderText(s);
//      alert.showAndWait();
//   }
//
//
//   public void handleEventInTable() {
//      table.setOnMouseClicked(event -> docDuLieuTuTable());
//      table.setOnKeyPressed(new EventHandler<KeyEvent>() {
//         @Override
//         public void handle(KeyEvent event) {
//            if (event.getCode() == KeyCode.UP || event.getCode() == KeyCode.DOWN) {
//               docDuLieuTuTable();
//            }
//         }
//
//
//      });
//   }
//
//   private boolean checkEmptyField() {
//      return txtMaPhong.getText().isEmpty() || txtSucChua.getText().isEmpty() || cbbTinhTrang.getValue().isEmpty() || txtGiaPhong.getText().isEmpty() || cbbLoaiPhong.getValue().toString().isEmpty();
//   }
//
//   private boolean checkMaPhong(String maPhong) {
//      Pattern pattern = Pattern.compile(regexPhong);
//      Matcher matcher = pattern.matcher(maPhong);
//      return matcher.matches();
//   }
//
//   private boolean checkIsExist(String maPhong) {
//      return Phong.isExisted(maPhong);
//   }
//
//   private boolean checkKieuSoNguyenDuong(String so) {
//      Pattern pattern = Pattern.compile("^[1-9]\\d*$");
//      Matcher matcher = pattern.matcher(so);
//      return matcher.matches();
//   }
//
//   //  Render and handle in View'
//   public void docDuLieuTuTable() {
//      Phong p = table.getSelectionModel().getSelectedItem();
//      if (p == null) {
//         return;
//      }
//      txtMaPhong.setText(p.getMaPhong());
//      String emptyString = "";
//      txtSucChua.setText((emptyString + p.getSucChua()));
//
//      if (p.getTinhTrang() == 0) {
//         cbbTinhTrang.setValue("PHÒNG TRỐNG");
//      } else if (p.getTinhTrang() == 1) {
//         cbbTinhTrang.setValue("PHÒNG ĐANG SỬ DỤNG");
//      } else if (p.getTinhTrang() == 2) {
//         cbbTinhTrang.setValue("PHÒNG CHỜ");
//      } else {
//         cbbTinhTrang.getSelectionModel().clearSelection();
//      }
//
//
//      txtGiaPhong.setText(p.getGiaPhong() + emptyString);
//
//      cbbLoaiPhong.getItems().clear();
//      cbbLoaiPhong.getItems().addAll(Enum_LoaiPhong.values());
//
//      if (p.getLoaiPhong() == 0) {
//         cbbLoaiPhong.getSelectionModel().select(0);
//      } else {
//         cbbLoaiPhong.getSelectionModel().select(1);
//      }
//
//
//   }
//fxml QL Phòng

//   @FXML
//   private TableView<Phong> table;
//   @FXML
//   private TableColumn<String, Integer> sttCol;
//   @FXML
//   private TableColumn<Phong, String> maPhongCol;
//   @FXML
//   private TableColumn<Phong, Integer> sucChuaCol;
//   @FXML
//   private TableColumn<Phong, String> tinhTrangCol;
//   @FXML
//   private TableColumn<Phong, String> loaiPhongCol;
//   @FXML
//   private TableColumn<Phong, String> giaPhongCol;

   @FXML
   private TextField txtMaPhong;
   @FXML
   private TextField txtSucChua;

   @FXML
   private ComboBox cbbLoaiPhong;
   @FXML
   private TextField txtGiaPhong;

}

