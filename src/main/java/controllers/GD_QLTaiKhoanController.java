/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controllers;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import model.NhanVien;
import model.TaiKhoan;


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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cbMaNhanVien.setItems(NhanVien.getAllMaNhanVien());
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
        table.setItems(TaiKhoan.getAllTaiKhoanFull());
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
                table.setItems(TaiKhoan.getAllTaiKhoanFull());
            } catch (Exception ex) {
                Logger.getLogger(GD_QLTaiKhoanController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        handleEventInTable();

        btnClear.setOnAction(event -> {
            clear();
        });

    }

    private void clear(){
        txtHoVaTen.setText("");
        txtTenTaiKhoan.setText("");
        pwMatKhau.setText("");
        pwNhapLaiMatKhau.setText("");
        cbMaNhanVien.setValue(null);
    }



    public void handleEventInTable() {
        table.setOnMouseClicked(event -> docDuLieuTuTable());
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
    public void xuLyDangKy() throws Exception {

    }

    //  Render and handle in View'
    public void docDuLieuTuTable() {
        TaiKhoan tk = table.getSelectionModel().getSelectedItem();
        if (tk == null) {
            return;
        }
        txtHoVaTen.setText((tk.getNhanVien().getHoTen()));
        txtTenTaiKhoan.setText(tk.getTenDangNhap());
        pwMatKhau.setText(tk.getMatKhau());
        pwNhapLaiMatKhau.setText(tk.getMatKhau());

        cbMaNhanVien.setValue(tk.getNhanVien().getMaNhanVien());
        /*
        if (p.getLoaiPhong()==0){
            cbbLoaiPhong.getSelectionModel().select(0);
        }else{
            cbbLoaiPhong.getSelectionModel().select(1);
        }
         */

    }

    public void updateDuLieuVaoTable() throws Exception {
        if(txtHoVaTen.getText().isEmpty() || txtTenTaiKhoan.getText().isEmpty() || pwMatKhau.getText().isEmpty() || pwNhapLaiMatKhau.getText().isEmpty() || cbMaNhanVien.getValue() == null){
            AlterErr("Vui lòng nhập đầy đủ thông tin");
            return;
        }

        if(pwMatKhau.getText().length() < 8){
            AlterErr("Mật khẩu phải có ít nhất 8 ký tự");
            return;
        }

        if(TaiKhoan.isExistedUsername(txtTenTaiKhoan.getText())){
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
        tk.setNhanVien(new NhanVien(hoTen));
        tk.setTenDangNhap(tenTaiKhoan);
        tk.setMatKhau(matKhau);
        tk.setNhanVien(new NhanVien(maNhanVien));

        TaiKhoan tkupdate = TaiKhoan.update(tk);

        table.setItems(TaiKhoan.getAllTaiKhoanFull());
        table.refresh();



    }

    @FXML
    public void addDuLieuVaoTable() throws Exception {
        if(txtHoVaTen.getText().isEmpty() || txtTenTaiKhoan.getText().isEmpty() || pwMatKhau.getText().isEmpty() || pwNhapLaiMatKhau.getText().isEmpty() || cbMaNhanVien.getValue() == null){
            AlterErr("Vui lòng nhập đầy đủ thông tin");
            return;
        }

        if(pwMatKhau.getText().length() < 8){
            AlterErr("Mật khẩu phải có ít nhất 8 ký tự");
            return;
        }

        String hoTen = txtHoVaTen.getText();
        String tenTaiKhoan = txtTenTaiKhoan.getText();
        String matKhau = pwMatKhau.getText();
        String nhapLaiMK = pwNhapLaiMatKhau.getText();
        String maNhanVien = cbMaNhanVien.getValue();



        if (TaiKhoan.isExisted(tenTaiKhoan)) {
            AlterErr("Tên tài khoản đã tồn tại");
            return;
        }

        if (!Objects.equals(matKhau, nhapLaiMK)) {
            AlterErr("Mật khẩu không khớp");
            return;

        }

        if(TaiKhoan.isExistedMaNhanVien(maNhanVien)){
            AlterErr("Nhân viên đã có tài khoản");
            return;
        }

        TaiKhoan tk = new TaiKhoan();
        tk.setNhanVien(new NhanVien(hoTen));
        tk.setTenDangNhap(tenTaiKhoan);
        tk.setMatKhau(matKhau);
        tk.setNhanVien(new NhanVien(maNhanVien));

        TaiKhoan tksave = TaiKhoan.save(tk);
        System.out.println("thanh cong");

        table.setItems(TaiKhoan.getAllTaiKhoanFull());
    }

    private static void AlterErr(String s) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Lỗi");
        alert.setHeaderText(s);
        alert.showAndWait();
    }

    @FXML
    public void onSelected(ActionEvent actionEvent) {
        String tenNhanVienByMa = Objects.requireNonNull(NhanVien.getNhanVienTheoMaNhanVien(cbMaNhanVien.getValue())).getHoTen();
        txtHoVaTen.setText(tenNhanVienByMa);
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
