/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controllers;

import connect.ConnectDB;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import model.KhachHang;

/**
 * FXML Controller class
 *
 * @author vie
 */
public class GD_QLKhachHangController implements Initializable {

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
        sdtCol.setCellValueFactory(new PropertyValueFactory<>("soDienThoai"));
        namSinhCol.setCellValueFactory(new PropertyValueFactory<>("namSinh"));
        gioiTinhCol.setCellValueFactory(cellData -> {
            boolean gioiTinh = cellData.getValue().isGioiTinh();
            String gioiTinhString;
            if (gioiTinh) {
                gioiTinhString = "Nam";
            } else {
                gioiTinhString = "Nữ";
            }
            return new ReadOnlyStringWrapper(gioiTinhString);

        });
        spinnerNamSinh.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1000, 3000, 2000));
        table.setItems(getAllKhachHang());
    }

//    Get data from DB
    public ObservableList<KhachHang> getAllKhachHang() {
        ObservableList<KhachHang> dsKhachHang = FXCollections.observableArrayList();
        Connection conn = ConnectDB.getInstance().getConnection();
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            String sql = "SELECT * FROM KhachHang";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String maKhachhang = rs.getString("MaKhachHang");
                String tenKhachhang = rs.getString("TenKhachHang");
                String soDienThoai = rs.getString("SoDienThoai");
                int namSinh = rs.getInt("NamSinh");
                boolean gioiTinh = rs.getBoolean("GioiTinh");
                dsKhachHang.add(new KhachHang(maKhachhang, tenKhachhang, soDienThoai, namSinh, gioiTinh));
            }
        } catch (SQLException ex) {
            Logger.getLogger(GD_QLKhachHangController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                stmt.close();
            } catch (SQLException ex) {
                Logger.getLogger(GD_QLKhachHangController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return dsKhachHang;
    }

    public KhachHang getKhachHangTheoMaKhachHang(String maKH) {
        ObservableList<KhachHang> dsKhachHang = FXCollections.observableArrayList();
        Connection conn = ConnectDB.getInstance().getConnection();
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            String sql = String.format("SELECT * FROM KhachHang WHERE MaKhachHang = '%s'", maKH);
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String maKhachhang = rs.getString("MaKhachHang");
                String tenKhachhang = rs.getString("TenKhachHang");
                String soDienThoai = rs.getString("SoDienThoai");
                int namSinh = rs.getInt("NamSinh");
                boolean gioiTinh = rs.getBoolean("GioiTinh");
                KhachHang kh = new KhachHang(maKhachhang, tenKhachhang, soDienThoai, namSinh, gioiTinh);
                return kh;
            }
        } catch (SQLException ex) {
            Logger.getLogger(GD_QLKhachHangController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                stmt.close();
            } catch (SQLException ex) {
                Logger.getLogger(GD_QLKhachHangController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    public int demSoLuongKhachHang() {
        int soLuongKH = 0;
        Connection conn = ConnectDB.getInstance().getConnection();
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            String sql = "SELECT COUNT(MaKhachHang) AS SoLuongKH FROM KhachHang";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                soLuongKH = rs.getInt("SoLuongKH");
            }
        } catch (SQLException ex) {
            Logger.getLogger(GD_QLKhachHangController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                stmt.close();
            } catch (SQLException ex) {
                Logger.getLogger(GD_QLKhachHangController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return soLuongKH;
    }

    public boolean themKhachHang(KhachHang kh) {
        ConnectDB.getInstance();
        Connection conn = ConnectDB.getInstance().getConnection();
        PreparedStatement pstm = null;
        int n = 0;
        String sql = "INSERT INTO KhachHang ( MaKhachHang, TenKhachHang, SoDienThoai, NamSinh, GioiTinh ) VALUES(?,?,?,?,?)";
        try {
            pstm = conn.prepareStatement(sql);
            pstm.setString(1, kh.getMaKhachHang());
            pstm.setString(2, kh.getTenKhachHang());
            pstm.setString(3, kh.getSoDienThoai());
            pstm.setInt(4, kh.getNamSinh());
            pstm.setBoolean(5, kh.isGioiTinh());
            n = pstm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(GD_QLKhachHangController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                pstm.close();
            } catch (SQLException ex) {
                Logger.getLogger(GD_QLKhachHangController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return n > 0;
    }

    public boolean suaKhachHang(KhachHang kh) {
        ConnectDB.getInstance();
        Connection conn = ConnectDB.getInstance().getConnection();
        PreparedStatement pstm = null;
        int n = 0;
        String sql = "UPDATE KhachHang SET TenKhachHang = ?, SoDienThoai = ?, NamSinh = ?, GioiTinh = ? WHERE MaKhachHang = ?";
        try {
            pstm = conn.prepareStatement(sql);
            pstm.setString(1, kh.getTenKhachHang());
            pstm.setString(2, kh.getSoDienThoai());
            pstm.setInt(3, kh.getNamSinh());
            pstm.setBoolean(4, kh.isGioiTinh());
            pstm.setString(5, kh.getMaKhachHang());
            n = pstm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(GD_QLKhachHangController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                pstm.close();
            } catch (SQLException ex) {
                Logger.getLogger(GD_QLKhachHangController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return n > 0;
    }
    
//  Render and handle in View'
    public void docDuLieuTuTable(MouseEvent event) {
        KhachHang kh = table.getSelectionModel().getSelectedItem();
        if (kh == null) {
            return;
        }
        txtMaKhachHang.setText(kh.getMaKhachHang());
        txtTenKhachHang.setText(kh.getTenKhachHang());
        txtSDT.setText(kh.getSoDienThoai());
        spinnerNamSinh.getValueFactory().setValue(kh.getNamSinh());
        if (kh.isGioiTinh()) {
            genderGroup.getToggles().get(0).setSelected(true);
        } else {
            genderGroup.getToggles().get(1).setSelected(true);
        }
    }

    public void xuLyThemKhachHang() {
        String maKH = txtMaKhachHang.getText();
        String tenKH = txtTenKhachHang.getText().trim();
        String sdt = txtSDT.getText().trim();
        int namSinh = (Integer) spinnerNamSinh.getValue();
        boolean gioiTinh = true;
        if (genderGroup.getToggles().get(1).isSelected()) {
            gioiTinh = false;
        }
        KhachHang kh = new KhachHang(maKH, tenKH, sdt, namSinh, gioiTinh);
        themKhachHang(kh);
        table.setItems(getAllKhachHang());
    }

    public void xuLySuaThongTinKhachHang() {
        String maKH = txtMaKhachHang.getText();
        String tenKH = txtTenKhachHang.getText().trim();
        String sdt = txtSDT.getText().trim();
        int namSinh = (Integer) spinnerNamSinh.getValue();
        boolean gioiTinh = true;
        if (genderGroup.getToggles().get(1).isSelected()) {
            gioiTinh = false;
        }
        KhachHang kh = new KhachHang(maKH, tenKH, sdt, namSinh, gioiTinh);
        suaKhachHang(kh);
        table.setItems(getAllKhachHang());
        table.refresh();
    }

    public void xuLyLamMoiThongTinKhachHang() {
        txtMaKhachHang.setText(phatSinhMaKhachHang());
        txtTenKhachHang.setText("");
        txtSDT.setText("");
        spinnerNamSinh.getValueFactory().setValue(2000);
        genderGroup.getToggles().get(0).setSelected(true);
        table.getSelectionModel().clearSelection();
    }

    public String phatSinhMaKhachHang() {
        String maKH = "KH";
        DecimalFormat df = new DecimalFormat("0000");
        maKH = maKH.concat(df.format(demSoLuongKhachHang() + 1));
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
    private Button btnThem;
    @FXML
    private Button btnSua;
    @FXML
    private Button btnLamMoi;
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
