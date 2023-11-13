/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controllers;

import enums.Enum_ChucVu;
import enums.Enum_TrangThaiLamViec;
import java.net.URL;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javax.swing.JOptionPane;
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
    
    @FXML
    private Button btnRefresh;
    @FXML
    private Button btnAddNV;
    @FXML
    private Button btnUpdate;

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
        //colChucVu.setCellValueFactory(new PropertyValueFactory<>("chucVu"));
        colChucVu.setCellValueFactory(cellData -> {
            Enum_ChucVu chucVu = cellData.getValue().getChucVu();
            String chucVuString;
            if(chucVu == Enum_ChucVu.BAOVE)
                chucVuString = "Bảo vệ";
            else if(chucVu == Enum_ChucVu.NHANVIENPHUCVU)
                chucVuString = "NV phục vụ";
            else if(chucVu == Enum_ChucVu.NHANVIENTIEPTAN)
                chucVuString = "NV tiếp tân";
            else
                chucVuString = "Quản lý";
            return new ReadOnlyStringWrapper(chucVuString);

        });
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
        
        table.requestFocus();
        table.getSelectionModel().select(0);
        table.getSelectionModel().focus(0);
        docDuLieuTuTable();
        handleEventInTable();
        
        btnRefresh.setOnAction(this::handleLamMoiButtonAction);
        btnAddNV.setOnAction(this::handleThemNhanVienButtonAction);
        btnUpdate.setOnAction(this::handleCapNhatNhanVienButtonAction);
    }
    
    public void docDuLieuTuTable() {
        NhanVien nv = table.getSelectionModel().getSelectedItem();
        if (nv == null) {
            return;
        }
        txtMaNhanVien.setText(nv.getMaNhanVien());
        txtCCCD.setText(nv.getCccd());
        txtHoTen.setText(nv.getHoTen());
        dateNgaySinh.setValue(nv.getNgaySinh());
        cbbChucVu.getItems().clear();
        cbbChucVu.getItems().addAll(Enum_ChucVu.values());
        cbbChucVu.setValue(nv.getChucVu());
        txtSoDienThoaiNV.setText(nv.getSoDienThoai());
        txtDiaChi.setText(nv.getDiaChi());
        if (nv.isGioiTinh()) {
            genderGroup.selectToggle(genderGroup.getToggles().get(0));
        } else {
            genderGroup.selectToggle(genderGroup.getToggles().get(1));
        }
    }
    
    public void handleLamMoiButtonAction(ActionEvent event) {
        try {
            xuLyLamMoiThongTinNhanVien();
        } catch (Exception ex) {
            Logger.getLogger(GD_QLNhanVienController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void handleThemNhanVienButtonAction(ActionEvent event) {
        try {
            xuLyThemNhanVien();
        } catch (Exception ex) {
            Logger.getLogger(GD_QLNhanVienController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void handleCapNhatNhanVienButtonAction(ActionEvent event) {
        try {
            xuLySuaThongTinNhanVien();
        } catch (Exception ex) {
            Logger.getLogger(GD_QLNhanVienController.class.getName()).log(Level.SEVERE, null, ex);
        }
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
    
    public void xuLyLamMoiThongTinNhanVien() throws Exception {
        txtMaNhanVien.setText("");
        txtCCCD.setText("");
        txtHoTen.setText("");
        dateNgaySinh.setValue(null);
        txtSoDienThoaiNV.setText("");
        txtDiaChi.setText("");
        genderGroup.getToggles().get(0).setSelected(true);
        cbbChucVu.setValue(null);
        table.getSelectionModel().clearSelection();

        // Cập nhật dữ liệu trong ComboBox
        cbbChucVu.getItems().clear();
        cbbChucVu.getItems().addAll(Enum_ChucVu.values());
        cbbChucVu.getSelectionModel().selectFirst();
}
    
    private boolean kiemTraRong() throws Exception {
        String cccd = txtCCCD.getText();
        String soDienThoai = txtSoDienThoaiNV.getText();
        
        
        if (txtCCCD.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "CCCD nhân viên không được rỗng");
            txtCCCD.selectAll();
            txtCCCD.requestFocus();
            return false;
        }

        if (txtHoTen.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Họ tên nhân viên không được rỗng");
            txtHoTen.selectAll();
            txtHoTen.requestFocus();
            return false;
        }
        if (dateNgaySinh.getValue() == null) {
            JOptionPane.showMessageDialog(null, "Ngày sinh không được rỗng");
            dateNgaySinh.requestFocus();
            return false;
        }
        
        if (txtSoDienThoaiNV.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Số điện thoại nhân viên không được rỗng");
            txtSoDienThoaiNV.selectAll();
            txtSoDienThoaiNV.requestFocus();
            return false;
        }
        

        if (txtDiaChi.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Địa chỉ nhân viên không được rỗng");
            txtDiaChi.selectAll();
            txtDiaChi.requestFocus();
            return false;
        }

        if (cbbChucVu.getValue() == null) {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn chức vụ");
            cbbChucVu.requestFocus();
            return false;
        }

        return true;
    }
    
    public void xuLyThemNhanVien() throws Exception {
        
        if (!kiemTraRong()) {
            return;
        }
        NhanVien nv = null;
        String maNhanVien = phatSinhMaNhanVien();
        String hoTen = txtHoTen.getText();
        String cccd = txtCCCD.getText();
        String soDienThoai = txtSoDienThoaiNV.getText();
        LocalDate ngaySinh = (LocalDate) dateNgaySinh.getValue();
        String diaChi = txtDiaChi.getText();
        boolean gioiTinh = true;
        if (genderGroup.getToggles().get(1).isSelected()) {
            gioiTinh = false;
        }
        Enum_ChucVu chucVu = (Enum_ChucVu) cbbChucVu.getValue(); 
        Enum_TrangThaiLamViec trangThai = Enum_TrangThaiLamViec.CONLAMVIEC; 
        String anhDaiDien = "duong_dan_anh_dai_dien";
        
        if (!kiemTraTrungCCCD(cccd)){
            JOptionPane.showMessageDialog(null, "Mã căn cước công dân không được phép trùng!");
            txtCCCD.selectAll();
            txtCCCD.requestFocus();
            return;
        }
        
        if (!kiemTraTrungSDT(soDienThoai)){
            JOptionPane.showMessageDialog(null, "Số điện thoại không được phép trùng!");
            txtSoDienThoaiNV.selectAll();
            txtSoDienThoaiNV.requestFocus();
            return ;
        }
        
        nv = new NhanVien(maNhanVien, cccd, hoTen, diaChi, ngaySinh, soDienThoai, chucVu, gioiTinh, anhDaiDien, trangThai);
        NhanVien.themNhanVien(nv);
        table.setItems(NhanVien.getAllNhanVien());
        
    }

    public void xuLySuaThongTinNhanVien() throws SQLException, Exception {
        String maNhanVien = txtMaNhanVien.getText();
        String hoTen = txtHoTen.getText();
        String cccd = txtCCCD.getText();
        String soDienThoai = txtSoDienThoaiNV.getText();
        LocalDate ngaySinh = (LocalDate) dateNgaySinh.getValue();
        String diaChi = txtDiaChi.getText();
        boolean gioiTinh = true;
        if (genderGroup.getToggles().get(1).isSelected()) {
            gioiTinh = false;
        }
        Enum_ChucVu chucVu = (Enum_ChucVu) cbbChucVu.getValue(); 
        
        Enum_TrangThaiLamViec trangThai = Enum_TrangThaiLamViec.CONLAMVIEC; 
        String anhDaiDien = "duong_dan_anh_dai_dien";
        
        NhanVien nv = new NhanVien(maNhanVien, cccd, hoTen, diaChi, ngaySinh, soDienThoai, chucVu, gioiTinh, anhDaiDien, trangThai);
        NhanVien.capNhatThongTinNhanVien(nv);
        
        table.setItems(NhanVien.getAllNhanVien());
        table.refresh();
    }

//    public String phatSinhMaNhanVien() throws SQLException {
//        String maNhanVien = "NV";
//        int totalEmployees = NhanVien.demSLNhanVien();
//        DecimalFormat dfSoThuTu = new DecimalFormat("00");
//        String soThuTuFormatted = dfSoThuTu.format(totalEmployees + 1);
//        String namSinhSuffix = LocalDate.now().format(DateTimeFormatter.ofPattern("yy"));
//        maNhanVien = maNhanVien.concat(soThuTuFormatted).concat(namSinhSuffix);
//        return maNhanVien;
//    }
    
    public String phatSinhMaNhanVien() throws SQLException {
        String maNhanVien = "NV";
        LocalDate ngaySinh = (LocalDate) dateNgaySinh.getValue();
        int namSinh = ngaySinh.getYear();
        int totalEmployees = NhanVien.demSLNhanVien(namSinh);
        DecimalFormat dfSoThuTu = new DecimalFormat("00");
        String soThuTuFormatted = dfSoThuTu.format(totalEmployees + 1);
        String namSinhSuffix = String.valueOf(namSinh).substring(2);
        maNhanVien = maNhanVien.concat(soThuTuFormatted).concat(namSinhSuffix);
        return maNhanVien;
    }
    public boolean kiemTraTrungCCCD(String cc) throws Exception {
        ObservableList<NhanVien> dsNhanVien = NhanVien.getAllNhanVien();
        for (NhanVien NV : dsNhanVien) {
            if (cc.trim().equals(NV.getCccd())) {
                return false ; 
            }
        }
        return true; 
    }
    
    public boolean kiemTraTrungSDT(String dt) throws Exception {
        ObservableList<NhanVien> dsNhanVien = NhanVien.getAllNhanVien();
        for (NhanVien NV : dsNhanVien) {
            if (dt.equals(NV.getSoDienThoai())) {
                return false ; 
            }
        }
        return true; 
    }
    
}
