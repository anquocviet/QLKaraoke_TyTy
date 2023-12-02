/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controllers;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.event.EventHandler;
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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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
        table.setItems(KhachHang.getAllKhachHang());
        table.requestFocus();
        table.getSelectionModel().select(0);
        table.getSelectionModel().focus(0);
        docDuLieuTuTable();
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

    
//  Render and handle in View'
    public void docDuLieuTuTable() {
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
        KhachHang kh = new KhachHang(maKH, tenKH);
        KhachHang.themKhachHang(kh);
        table.setItems(KhachHang.getAllKhachHang());
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
        KhachHang kh = new KhachHang(maKH, tenKH);
        KhachHang.suaKhachHang(kh);
        table.setItems(KhachHang.getAllKhachHang());
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
        maKH = maKH.concat(df.format(KhachHang.demSoLuongKhachHang() + 1));
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
