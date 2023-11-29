/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javax.swing.JOptionPane;
import model.CT_KhuyenMai;

/**
 *
 * @author Admin
 */
public class GD_QLCTKhuyenMaiController implements Initializable {

    @FXML
    private TableView<CT_KhuyenMai> tableView_CTKhuyenMai;

    @FXML
    private TableColumn<String, Integer> col_sttKhuyenMai;

    @FXML
    private TableColumn<CT_KhuyenMai, String> col_maKhuyenMai;

    @FXML
    private TableColumn<CT_KhuyenMai, String> col_tenKhuyenMai;

    @FXML
    private TableColumn<CT_KhuyenMai, String> col_ngayBatDau;

    @FXML
    private TableColumn<CT_KhuyenMai, String> col_ngayKetThuc;

    @FXML
    private TableColumn<CT_KhuyenMai, Integer> col_luotSuDungConLai;

    @FXML
    private TableColumn<CT_KhuyenMai, Integer> col_chietKhau;

    @FXML
    private TextField txtMaKhuyenMai;

    @FXML
    private TextField txtTenKhuyenMai;

    @FXML
    private DatePicker dateNgayBatDau;

    @FXML
    private DatePicker dateNgayKetThuc;

    @FXML
    private TextField txtLuotSuDung;

    @FXML
    private TextField txtChietKhau;

    @FXML
    private TextField txtTimMaKhuyenMai;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnUpdate;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnRefresh;

    @FXML
    private Button btnSearch;

    ObservableList<CT_KhuyenMai> danhSachCT_KhuyenMai;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        col_sttKhuyenMai.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(tableView_CTKhuyenMai.getItems().indexOf(param.getValue()) + 1));
        col_maKhuyenMai.setCellValueFactory(new PropertyValueFactory<>("maKhuyenMai"));
        col_tenKhuyenMai.setCellValueFactory(new PropertyValueFactory<>("tenKhuyenMai"));
        col_ngayBatDau.setCellValueFactory(new PropertyValueFactory<>("ngayBatDau"));
        col_ngayKetThuc.setCellValueFactory(new PropertyValueFactory<>("ngayKetThuc"));
        col_luotSuDungConLai.setCellValueFactory(new PropertyValueFactory<>("luotSuDungConLai"));
        col_chietKhau.setCellValueFactory(new PropertyValueFactory<>("chietKhau"));

        danhSachCT_KhuyenMai = CT_KhuyenMai.getListCT_KhuyenMai();
        tableView_CTKhuyenMai.setItems(danhSachCT_KhuyenMai);
        tableView_CTKhuyenMai.requestFocus();
        docDuLieuTuTable();
        handleEventInTable();

        btnAdd.setOnAction(this::handleThemKhuyenMaiButtonAction);
        btnUpdate.setOnAction(this::handleCapNhatKhuyenMaiButtonAction);
        btnDelete.setOnAction(this::handleXoaKhuyenMaiButtonAction);
        btnRefresh.setOnAction(this::handleLamMoiButtonAction);
        btnSearch.setOnAction(this::handleTimKhuyenMaiButtonAction);

    }

    public void handleThemKhuyenMaiButtonAction(ActionEvent event) {
        try {
            xuLyThemKhuyenMai();
        } catch (Exception ex) {
            Logger.getLogger(GD_QLNhanVienController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void xuLyThemKhuyenMai() throws Exception {
        if (!kiemTraRong()) {
            return;
        }
        CT_KhuyenMai km = null;
        String maKhuyenMai = txtMaKhuyenMai.getText();
        String tenKhuyenMai = txtTenKhuyenMai.getText();

        Integer luotSuDungConLai = Integer.parseInt(txtLuotSuDung.getText());
        Integer chietKhau = Integer.parseInt(txtChietKhau.getText());
        LocalDate ngayBatDau = (LocalDate) dateNgayBatDau.getValue();
        LocalDate ngayKetThuc = (LocalDate) dateNgayKetThuc.getValue();

        if (!kiemTraTrungMaKhuyenMai(maKhuyenMai)) {
            JOptionPane.showMessageDialog(null, "Mã khuyến mãi đã bị trùng!");
            txtMaKhuyenMai.selectAll();
            txtMaKhuyenMai.requestFocus();
            return;
        }

        if (!kiemTraTrungTenKhuyenMai(tenKhuyenMai)) {
            JOptionPane.showMessageDialog(null, "Tên khuyến mãi đã bị trùng!");
            txtTenKhuyenMai.selectAll();
            txtTenKhuyenMai.requestFocus();
            return;
        }
        try {
            km = new CT_KhuyenMai(maKhuyenMai, tenKhuyenMai, ngayBatDau, ngayKetThuc, luotSuDungConLai, chietKhau);
            CT_KhuyenMai.themCTKhuyenMai(km);
            tableView_CTKhuyenMai.setItems(CT_KhuyenMai.getListCT_KhuyenMai());
        } catch (Exception e) {
            String exception = e.getMessage();
            if (exception.equals("Ngày kết thúc phải lớn hơn ngày bắt đầu")) {
                dateNgayBatDau.requestFocus();
                dateNgayBatDau.setValue(null);
            } else if (exception.equals("Lượt sử dụng khuyến mãi phải lớn hơn 0")) {
                txtLuotSuDung.selectAll();
                txtLuotSuDung.requestFocus();
            } else if (exception.equals("Chiết khấu phải lớn hơn 0 và nhỏ hơn hoặc bằng 50")) {
                txtChietKhau.selectAll();
                txtChietKhau.requestFocus();
            }
            JOptionPane.showMessageDialog(null, exception);

        }

    }

    public boolean kiemTraTrungMaKhuyenMai(String cc) throws Exception {
        ObservableList<CT_KhuyenMai> dsKhuyenMai = CT_KhuyenMai.getListCT_KhuyenMai();
        for (CT_KhuyenMai km : dsKhuyenMai) {
            if (cc.trim().equals(km.getMaKhuyenMai())) {
                return false;
            }
        }
        return true;
    }

    public boolean kiemTraTrungTenKhuyenMai(String cc) throws Exception {
        ObservableList<CT_KhuyenMai> dsKhuyenMai = CT_KhuyenMai.getListCT_KhuyenMai();
        for (CT_KhuyenMai km : dsKhuyenMai) {
            if (cc.trim().equals(km.getTenKhuyenMai())) {
                return false;
            }
        }
        return true;
    }
//

    public void handleCapNhatKhuyenMaiButtonAction(ActionEvent event) {
        try {
            xuLyCapNhatThongTinKhuyenMai();
        } catch (Exception ex) {
            Logger.getLogger(GD_QLNhanVienController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void xuLyCapNhatThongTinKhuyenMai() throws Exception {
        if (!kiemTraRong()) {
            return;
        }
        CT_KhuyenMai km = null;
        String maKhuyenMai = txtMaKhuyenMai.getText();
        String tenKhuyenMai = txtTenKhuyenMai.getText();

        Integer luotSuDungConLai = Integer.parseInt(txtLuotSuDung.getText());
        Integer chietKhau = Integer.parseInt(txtChietKhau.getText());
        LocalDate ngayBatDau = (LocalDate) dateNgayBatDau.getValue();
        LocalDate ngayKetThuc = (LocalDate) dateNgayKetThuc.getValue();

        if (!kiemTraTrungTenKhuyenMai(tenKhuyenMai)) {
            JOptionPane.showMessageDialog(null, "Tên khuyến mãi đã bị trùng!");
            txtTenKhuyenMai.selectAll();
            txtTenKhuyenMai.requestFocus();
            return;
        }
        try {
            km = new CT_KhuyenMai(maKhuyenMai, tenKhuyenMai, ngayBatDau, ngayKetThuc, luotSuDungConLai, chietKhau);
            CT_KhuyenMai.capNhatThongTinKhuyenMai(km);
            xuLyLamMoiKhuyenMai();
        } catch (Exception e) {
            String exception = e.getMessage();
            if (exception.equals("Ngày kết thúc phải lớn hơn ngày bắt đầu")) {
                dateNgayBatDau.requestFocus();
                dateNgayBatDau.setValue(null);
            } else if (exception.equals("Lượt sử dụng khuyến mãi phải lớn hơn 0")) {
                txtLuotSuDung.selectAll();
                txtLuotSuDung.requestFocus();
            } else if (exception.equals("Chiết khấu phải lớn hơn 0 và nhỏ hơn hoặc bằng 50")) {
                txtChietKhau.selectAll();
                txtChietKhau.requestFocus();
            }
            JOptionPane.showMessageDialog(null, exception);
        }
    }
//

    public void handleXoaKhuyenMaiButtonAction(ActionEvent event) {
        try {
            xuLyXoaKhuyenMai();
        } catch (Exception ex) {
            Logger.getLogger(GD_QLNhanVienController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void xuLyXoaKhuyenMai() {
        String maKhuyenMai = txtMaKhuyenMai.getText();
        if (maKhuyenMai == null) {
            JOptionPane.showMessageDialog(null, "Hãy chọn khuyến mãi để thực hiện xóa");
            return;
        }

        CT_KhuyenMai.xoaCTKhuyenMai(maKhuyenMai);
        tableView_CTKhuyenMai.setItems(CT_KhuyenMai.getListCT_KhuyenMai());
        xuLyLamMoiKhuyenMai();

    }
//
    public void handleLamMoiButtonAction(ActionEvent event) {
        try {
            xuLyLamMoiKhuyenMai();
        } catch (Exception ex) {
            Logger.getLogger(GD_QLNhanVienController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void xuLyLamMoiKhuyenMai() {
        txtMaKhuyenMai.setText("");
        txtTenKhuyenMai.setText("");
        txtLuotSuDung.setText("");
        dateNgayBatDau.setValue(null);
        dateNgayKetThuc.setValue(null);
        txtChietKhau.setText("");
        txtTimMaKhuyenMai.setText("");
        tableView_CTKhuyenMai.setItems(CT_KhuyenMai.getListCT_KhuyenMai());
        tableView_CTKhuyenMai.refresh();
    }
//

    public void handleTimKhuyenMaiButtonAction(ActionEvent event) {
        try {
            xuLyTimKhuyenMai();
        } catch (Exception ex) {
            Logger.getLogger(GD_QLNhanVienController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void xuLyTimKhuyenMai() {
        String maTimKiem = txtTimMaKhuyenMai.getText();
        System.out.println(maTimKiem);
        if (txtTimMaKhuyenMai == null) {
            JOptionPane.showMessageDialog(null, "Hãy nhập mã khuyến mãi để thực hiện tìm kiếm!");
            return;
        }
        xuLyLamMoiKhuyenMai();
        tableView_CTKhuyenMai.setItems(CT_KhuyenMai.getKhuyenMaiTheoMa(maTimKiem));
        tableView_CTKhuyenMai.refresh();
    }

    public void handleEventInTable() {
        tableView_CTKhuyenMai.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                docDuLieuTuTable();
            }

        });
        tableView_CTKhuyenMai.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.UP || event.getCode() == KeyCode.DOWN) {
                    docDuLieuTuTable();
                }
            }
        });
    }

    public void docDuLieuTuTable() {
        CT_KhuyenMai km = tableView_CTKhuyenMai.getSelectionModel().getSelectedItem();
        if (km == null) {
            return;
        }
        txtMaKhuyenMai.setText(km.getMaKhuyenMai());
        txtTenKhuyenMai.setText(km.getTenKhuyenMai());
        dateNgayBatDau.setValue(km.getNgayBatDau());
        dateNgayKetThuc.setValue(km.getNgayKetThuc());
        txtLuotSuDung.setText(String.valueOf(km.getLuotSuDungConLai()));
        txtChietKhau.setText(String.valueOf(km.getChietKhau()));
    }

    private boolean kiemTraRong() throws Exception {
        if (txtMaKhuyenMai.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Mã khuyến mãi không được rỗng");
            txtMaKhuyenMai.selectAll();
            txtMaKhuyenMai.requestFocus();
            return false;
        }

        if (txtTenKhuyenMai.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Tên khuyến mãi không được rỗng");
            txtTenKhuyenMai.selectAll();
            txtTenKhuyenMai.requestFocus();
            return false;
        }
        if (dateNgayBatDau.getValue() == null) {
            JOptionPane.showMessageDialog(null, "Ngày bắt đầu không được rỗng");
            dateNgayBatDau.requestFocus();
            return false;
        }

        if (dateNgayKetThuc.getValue() == null) {
            JOptionPane.showMessageDialog(null, "Ngày kết thúc không được rỗng");
            dateNgayKetThuc.requestFocus();
            return false;
        }

        if (txtLuotSuDung.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Lượt sử dụng không được rỗng");
            txtLuotSuDung.selectAll();
            txtLuotSuDung.requestFocus();
            return false;
        }

        if (txtChietKhau.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Chiết khấu không được rỗng");
            txtChietKhau.selectAll();
            txtChietKhau.requestFocus();
            return false;
        }

        return true;
    }
}
