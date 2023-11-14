/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controllers;

import enums.Enum_ChucVu;
import enums.Enum_Nvien;
import enums.Enum_LoaiPhong;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import model.KhachHang;
import model.NhanVien;
import model.TaiKhoan;

/**
 * FXML Controller class
 *
 * @author thach
 */
public class GD_DangKyController implements Initializable {

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        sttCol.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(table.getItems().indexOf(param.getValue()) + 1));
        tenCol.setCellValueFactory(cellData -> {
            String hoTen = cellData.getValue().getNhanVien().getHoTen();
            return new ReadOnlyStringWrapper(hoTen);

        });
        tenDangNhapCol.setCellValueFactory(new PropertyValueFactory<>("tenDangNhap"));
        matKhauCol.setCellValueFactory(new PropertyValueFactory<>("matKhau"));
        table.setItems(TaiKhoan.getAllTaiKhoanFull());
        btnThem.setOnAction(event -> {
            try {
                addDuLieuVaoTable();
            } catch (Exception ex) {
                Logger.getLogger(GD_DangKyController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
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
    public void xuLyDangKy() throws Exception {
        cbbNvien.getItems().clear();
        cbbNvien.getItems().addAll(Enum_Nvien.values());
        cbbNvien.getSelectionModel().selectFirst();
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
        cbbNvien.getItems().clear();
        cbbNvien.getItems().addAll(Enum_Nvien.values());
        Enum_Nvien nvien = (Enum_Nvien) cbbNvien.getValue();
        /*
        if (p.getLoaiPhong()==0){
            cbbLoaiPhong.getSelectionModel().select(0);
        }else{
            cbbLoaiPhong.getSelectionModel().select(1);
        }
         */

    }

    @FXML
    public void addDuLieuVaoTable() throws Exception {
        String hoTen = txtHoVaTen.getText();
        String tenTaiKhoan = txtTenTaiKhoan.getText();
        String matKhau = pwMatKhau.getText();
        String nhapLaiMK = pwNhapLaiMatKhau.getText();

        if (matKhau != nhapLaiMK) {
            System.err.println("MK Khong trung khop, nhap lai");
        }

        TaiKhoan tk = new TaiKhoan();
        tk.setNhanVien(new NhanVien(hoTen));
        tk.setTenDangNhap(tenTaiKhoan);
        tk.setMatKhau(matKhau);

        TaiKhoan tksave = TaiKhoan.save(tk);

        if (tksave != null) {
            table.getItems().add(tksave);
            System.out.println("save TC");
        } else {
            System.err.println("Save TB");
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
    private ComboBox cbbNvien;
    @FXML
    private Button btnThem;

}
