/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controllers;

import entities.NhanVien;
import entities.TaiKhoan;
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
import javafx.scene.input.KeyEvent;
import services.NhanVienService;
import socket.ClientSocket;

import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * FXML Controller class
 *
 * @author thach
 */
public class GD_QLTaiKhoanController implements Initializable {
   @FXML
   private Button btnUpdate;
   @FXML
   private Button btnClear;
   @FXML
   private TableColumn<TaiKhoan, String> colChucVu;
   @FXML
   private ComboBox<String> cbMaNhanVien;

   DataInputStream dis = ClientSocket.getDis();
   DataOutputStream dos = ClientSocket.getDos();
   ObjectInputStream in = ClientSocket.getIn();
   ObjectOutputStream out = ClientSocket.getOut();
   private List<TaiKhoan> allTaiKhoan;
   private String currentName = "";

   @Override
   public void initialize(URL location, ResourceBundle resources) {
      try {
         // Gửi yêu cầu để lấy danh sách nhân viên từ server
         dos.writeUTF("employee-find-all-employee");
         dos.flush();

         // Nhận danh sách nhân viên từ server
         List<NhanVien> listNhanVien = (List<NhanVien>) in.readObject();
         // Xử lý dữ liệu nhân viên, ví dụ: đặt danh sách mã nhân viên vào ComboBox
         List<String> maNhanVienList = new ArrayList<>();
         for (NhanVien nhanVien : listNhanVien) {
            maNhanVienList.add(nhanVien.getMaNhanVien());
         }
         // Đặt danh sách mã nhân viên vào ComboBox
         cbMaNhanVien.setItems(FXCollections.observableArrayList(maNhanVienList));
      } catch (IOException | ClassNotFoundException e) {
         e.printStackTrace();
         // Xử lý lỗi nếu cần
      }

      sttCol.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(table.getItems().indexOf(param.getValue()) + 1));
      tenCol.setCellValueFactory(cellData -> {
         TaiKhoan tk = cellData.getValue();
         return new ReadOnlyStringWrapper(tk.getNhanVien().getHoTen());
      });
      tenDangNhapCol.setCellValueFactory(new PropertyValueFactory<>("tenDangNhap"));
      matKhauCol.setCellValueFactory(new PropertyValueFactory<>("matKhau"));
      colChucVu.setCellValueFactory(cellData -> {
         TaiKhoan tk = cellData.getValue();
         return new ReadOnlyStringWrapper(tk.getNhanVien().getChucVu().getTenChucVu());

      });
      // get all tai khoan
       try {
           ClientSocket.getDos().writeUTF("account-all-account");
          // Nhận danh sách tài khoản từ máy chủ
          allTaiKhoan = (List<TaiKhoan>) ClientSocket.getIn().readObject();

      table.setItems(FXCollections.observableArrayList(allTaiKhoan));
       } catch (IOException | ClassNotFoundException e) {
           throw new RuntimeException(e);
       }

       btnThem.setOnAction(event -> {
         try {
            addDuLieuVaoTable();
         } catch (Exception ex) {
            Logger.getLogger(GD_QLTaiKhoanController.class.getName()).log(Level.SEVERE, null, ex);
         }
      });


      btnUpdate.setOnAction(event -> {
         try {
            updateDuLieuVaoTable();
         } catch (Exception ex) {
            Logger.getLogger(GD_QLTaiKhoanController.class.getName()).log(Level.SEVERE, null, ex);
         }
      });

      handleEventInTable();

      btnClear.setOnAction(event -> {
         clear();
      });

   }

   private void clear() {
      txtHoVaTen.setText("");
      txtTenTaiKhoan.setText("");
      pwMatKhau.setText("");
      pwNhapLaiMatKhau.setText("");
      cbMaNhanVien.setValue(null);
   }


   public void handleEventInTable() {
      table.setOnMouseClicked(event -> {
         docDuLieuTuTable();
         currentName = txtTenTaiKhoan.getText();
         // set cbMaNhanVien
         TaiKhoan tk = table.getSelectionModel().getSelectedItem();
         if (tk == null) {
            return;
         }else{
            cbMaNhanVien.setValue(tk.getNhanVien().getMaNhanVien());
         }
   });

      table.setOnKeyPressed(new EventHandler<KeyEvent>() {
         @Override
         public void handle(KeyEvent event) {
         }
//         @Override
//         public void handle(KeyEvent event) {
//            if (event.getCode() == KeyCode.UP || event.getCode() == KeyCode.DOWN) {
//               docDuLieuTuTable();
//            }
//         }

      });
   }

   @FXML
   public void xuLyDangKy() throws Exception {

   }

   //  Render and handle in View
   public void docDuLieuTuTable() {
      TaiKhoan tk = table.getSelectionModel().getSelectedItem();
      if (tk == null) {
         return;
      }
      txtHoVaTen.setText((tk.getNhanVien().getHoTen()));
      txtTenTaiKhoan.setText(tk.getTenDangNhap());
      pwMatKhau.setText(tk.getMatKhau());
      pwNhapLaiMatKhau.setText(tk.getMatKhau());

//      cbMaNhanVien.setValue(tk.getNhanVien().getMaNhanVien());
//        if (p.getLoaiPhong()==0){
//            cbbLoaiPhong.getSelectionModel().select(0);
//        }else{
//            cbbLoaiPhong.getSelectionModel().select(1);
//        }
   }

   public void updateDuLieuVaoTable() throws Exception {

      if (txtHoVaTen.getText().isEmpty() || txtTenTaiKhoan.getText().isEmpty() || pwMatKhau.getText().isEmpty() || pwNhapLaiMatKhau.getText().isEmpty() || cbMaNhanVien.getValue() == null) {
         AlterErr("Vui lòng nhập đầy đủ thông tin");
         return;
      }

      if (pwMatKhau.getText().length() < 8) {
         AlterErr("Mật khẩu phải có ít nhất 8 ký tự");
         return;
      }

      if (checkIsExisted(txtTenTaiKhoan.getText()) && currentName.equals(txtTenTaiKhoan.getText())==false){
         AlterErr("Tên tài khoản đã tồn tại");
         return;
      }

      String hoTen = txtHoVaTen.getText();
      String tenTaiKhoan = txtTenTaiKhoan.getText();
      String matKhau = pwMatKhau.getText();
      String nhapLaiMK = pwNhapLaiMatKhau.getText();
      String maNhanVien = cbMaNhanVien.getValue();

      if (!Objects.equals(matKhau, nhapLaiMK)) {
         AlterErr("Mật khẩu không khớp");
         return;
      }


      TaiKhoan tk = new TaiKhoan();
      NhanVien nhanVien = getNhanVienByID(maNhanVien);
      tk.setNhanVien(nhanVien);
      tk.setTenDangNhap(tenTaiKhoan);
      tk.setMatKhau(matKhau);
      int index = table.getSelectionModel().getSelectedIndex();
      tk.setMaTaiKhoan(allTaiKhoan.get(index).getMaTaiKhoan());

      boolean isSuccess =  update(tk);
        if (isSuccess){
             System.out.println("Cập nhật tài khoản thành công");
             table.refresh();
             clear();
           // edit row table
           allTaiKhoan.set(index, tk);
           table.setItems(FXCollections.observableArrayList(allTaiKhoan));
        } else {
             System.out.println("Cập nhật tài khoản thất bại");
             AlterErr("Cập nhật tài khoản thất bại");
        }


      table.refresh();
   }

   private boolean update(TaiKhoan tk){
        try {
             // Gửi yêu cầu cập nhật tài khoản đến server
             dos.writeUTF("account-update-account");
             dos.flush();
             // Gửi đối tượng tài khoản cần cập nhật lên server
             out.writeObject(tk);
             out.flush();
             // Nhận kết quả từ server
             boolean isSuccess = dis.readBoolean();
             if (isSuccess){
                System.out.println("Cập nhật tài khoản thành công");
                return true;
             } else {
                System.out.println("Cập nhật tài khoản thất bại");
                return false;
             }
        } catch (IOException e) {
             e.printStackTrace();
             return false;
        }
   }

   @FXML
   public void addDuLieuVaoTable() throws Exception {
      if (txtHoVaTen.getText().isEmpty() || txtTenTaiKhoan.getText().isEmpty() || pwMatKhau.getText().isEmpty() || pwNhapLaiMatKhau.getText().isEmpty() || cbMaNhanVien.getValue() == null) {
         AlterErr("Vui lòng nhập đầy đủ thông tin");
         return;
      }

      if (pwMatKhau.getText().length() < 8) {
         AlterErr("Mật khẩu phải có ít nhất 8 ký tự");
         return;
      }

      String hoTen = txtHoVaTen.getText();
      String tenTaiKhoan = txtTenTaiKhoan.getText();
      String matKhau = pwMatKhau.getText();
      String nhapLaiMK = pwNhapLaiMatKhau.getText();
      String maNhanVien = cbMaNhanVien.getValue();

      if (checkIsExisted(tenTaiKhoan)) {
         AlterErr("Tên tài khoản đã tồn tại");
         return;
      }

      if (!Objects.equals(matKhau, nhapLaiMK)) {
         AlterErr("Mật khẩu không khớp");
         return;
      }

      if (checkIsExisted(tenTaiKhoan)) {
         AlterErr("Nhân viên đã có tài khoản");
         return;
      }

      TaiKhoan tk = new TaiKhoan();
      NhanVien nhanVien = getNhanVienByID(maNhanVien);
      tk.setNhanVien(nhanVien);
      tk.setTenDangNhap(tenTaiKhoan);
      tk.setMatKhau(matKhau);
      tk.setMaTaiKhoan("TK000" + (table.getItems().size() + 1));
      // Gửi yêu cầu thêm tài khoản đến server
      dos.writeUTF("account-add-account");
      dos.flush();
      // Gửi đối tượng TaiKhoan lên server
      out.writeObject(tk);
      out.flush();
      // Nhận kết quả từ server
      boolean isSuccess = dis.readBoolean();
      if (isSuccess){
            System.out.println("Thêm tài khoản thành công");
            allTaiKhoan.add(tk);
            table.getItems().add(tk);
            table.refresh();
            clear();
      } else {
            System.out.println("Thêm tài khoản thất bại");
         AlterErr("Thêm tài khoản thất bại");
      }

   }

   private static void AlterErr(String s) {
      Alert alert = new Alert(Alert.AlertType.ERROR);
      alert.setTitle("Lỗi");
      alert.setHeaderText(s);
      alert.showAndWait();
   }

   private boolean checkIsExisted(String tenTaiKhoan) {
      List<TaiKhoan> danhSachTaiKhoan = table.getItems();
      for (TaiKhoan taiKhoan : danhSachTaiKhoan) {
         if (taiKhoan.getTenDangNhap().equals(tenTaiKhoan)) {
            return true; // Tên tài khoản đã tồn tại
         }
      }
      return false; // Tên tài khoản không tồn tại
   }

   @FXML
   public void onSelected(ActionEvent actionEvent) {
      // get ma nhan vien
      String maNhanVien = cbMaNhanVien.getValue();
      if (maNhanVien == null) {
         return;
      }else {
         // get ten nhan vien by ma
         NhanVien nhanVien = getNhanVienByID(maNhanVien);

         String tenNhanVienByMa = Objects.requireNonNull(nhanVien.getHoTen());
         txtHoVaTen.setText(tenNhanVienByMa);
         txtTenTaiKhoan.setText("");
      }
   }

   private NhanVien getNhanVienByID(String maNhanVien) {
      try {
         // Gửi yêu cầu tìm kiếm nhân viên theo mã nhân viên đến server
         dos.writeUTF("employee-find-employee,"+maNhanVien);
         dos.flush();
         // Gửi mã nhân viên cần tìm kiếm đến server
         dos.flush();
         // Nhận danh sách nhân viên từ server
         List<NhanVien> danhSachNhanVien = (List<NhanVien>) in.readObject();

         // Trả về nhân viên đầu tiên nếu danh sách không rỗng
         if (!danhSachNhanVien.isEmpty()) {
            return danhSachNhanVien.get(0);
         } else {
            // Trả về null nếu không tìm thấy nhân viên
            return null;
         }
      } catch (IOException | ClassNotFoundException e) {
         e.printStackTrace();
         // Xử lý ngoại lệ khi gửi hoặc nhận dữ liệu
         // Có thể trả về một giá trị mặc định hoặc hiển thị thông báo lỗi
         return null;
      }
   }


   @FXML
   private TableView<TaiKhoan> table;
   @FXML
   private TableColumn<String, Integer> sttCol;
   @FXML
   private TableColumn<TaiKhoan, String> tenCol;
   @FXML
   private TableColumn<TaiKhoan, String> tenDangNhapCol;
   @FXML
   private TableColumn<TaiKhoan, String> matKhauCol;

   @FXML
   private TextField txtHoVaTen;
   @FXML
   private TextField txtTenTaiKhoan;
   @FXML
   private PasswordField pwMatKhau;
   @FXML
   private PasswordField pwNhapLaiMatKhau;

   @FXML
   private Button btnThem;


}
