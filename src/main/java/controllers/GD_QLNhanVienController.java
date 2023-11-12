/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controllers;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import model.NhanVien;

/**
 * FXML Controller class
 *
 * @author fil
 */
public class GD_QLNhanVienController implements Initializable {
    
    @FXML
    private TableView<NhanVien> table;
    
    @FXML
    private TextField txtMaNhanVien;
    @FXML
    private TextField txtCCCD;
    @FXML
    private TextField txtHoTen;
    @FXML
    private DatePicker dateNgaySinh;
    @FXML
    private TextField txtSoDienThoaiNV;
    @FXML
    private TextField txtDiaChi;
    @FXML
    private ComboBox cbbChucVu;
    
    @FXML
    private TableColumn<NhanVien, String> colMaNV;
    @FXML
    private TableColumn<NhanVien, String> colCCCD;
    @FXML
    private TableColumn<NhanVien, String> colHoTen;
    @FXML
    private TableColumn<String, Integer> colNgaySinh;
    @FXML
    private TableColumn<NhanVien, String> colSoDienThoai;
    @FXML
    private TableColumn<NhanVien, Integer> colDiaChi;
    @FXML
    private TableColumn<NhanVien, String> colChucVu;
    @FXML
    private TableColumn<NhanVien, String> colGioiTinh;
    @FXML
    private ToggleGroup genderGroup;
    @FXML
    private RadioButton radioButtonNam;
    @FXML
    private RadioButton radioButtonNu;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        genderGroup = new ToggleGroup();
        radioButtonNam.setToggleGroup(genderGroup);
        radioButtonNu.setToggleGroup(genderGroup);
//        try {
//            txtMaNhanVien.setText( phatSinhMaNhanVien());
//        } catch (Exception ex) {
//            Logger.getLogger(GD_QLNhanVienController.class.getName()).log(Level.SEVERE, null, ex);
//        }
        
        colMaNV.setCellValueFactory(new PropertyValueFactory<>("maNhanVien"));
        colCCCD.setCellValueFactory(new PropertyValueFactory<>("cccd"));
        colHoTen.setCellValueFactory(new PropertyValueFactory<>("hoTen"));
        colNgaySinh.setCellValueFactory(new PropertyValueFactory<>("ngaySinh"));
        colSoDienThoai.setCellValueFactory(new PropertyValueFactory<>("soDienThoai"));
        colDiaChi.setCellValueFactory(new PropertyValueFactory<>("diaChi"));
        colChucVu.setCellValueFactory(new PropertyValueFactory<>("chucVu"));
        colGioiTinh.setCellValueFactory(cellData -> {
            boolean gioiTinh = cellData.getValue().isGioiTinh();
            String gioiTinhString;
            if (gioiTinh) {
                gioiTinhString = "Nam";
            } else {
                gioiTinhString = "Nữ";
            }
            return new ReadOnlyStringWrapper(gioiTinhString);

        });
        
        try {
            table.setItems(NhanVien.getAllNhanVien());
        } catch (Exception ex) {
            Logger.getLogger(GD_QLNhanVienController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void docDuLieuTuTable(MouseEvent event) {
//        NhanVien nv = table.getSelectionModel().getSelectedItem();
//        if (nv == null) {
//            return;
//        }
//        txtMaNhanVien.setText(nv.getMaNhanVien());
//        txtCCCD.setText(nv.getCccd());
//        txtHoTen.setText(nv.getHoTen());
//        DateTimeFormatter df = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss");
////        dateNgaySinh
////        txtSoDienThoaiNV.setText(nv.getSoDienThoai());
//        txtDiaChi.setText(nv.getDiaChi());
//        
//        if (nv.isGioiTinh()) {
//            genderGroup.getToggles().get(0).setSelected(true);
//        } else {
//            genderGroup.getToggles().get(1).setSelected(true);
//        }
    }
    public String phatSinhMaNhanVien() throws Exception {
        // Lấy tổng số nhân viên hiện có
        int totalEmployees = NhanVien.demSLNhanVien();

        // Lấy 2 số cuối năm sinh của nhân viên mới
        LocalDate ngaySinh = LocalDate.now();
        int namSinh = ngaySinh.getYear();
        int haiSoCuoiNamSinh = (namSinh % 100);

        // Tạo mã nhân viên
        String maNhanVien = "NV";
        String soThuTu = String.format("%02d", totalEmployees + 1);
        maNhanVien += soThuTu;
        maNhanVien += String.format("%02d", haiSoCuoiNamSinh);

        return maNhanVien;
    }

}
