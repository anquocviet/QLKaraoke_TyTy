/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
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

    @FXML
    private Label lblErrorId;

    @FXML
    private Label lblErrorTen;

    @FXML
    private Label lblErrorLuotSuDung;

    @FXML
    private Label lblErrorChietKhau;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        col_sttKhuyenMai.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(tableView_CTKhuyenMai.getItems().indexOf(param.getValue()) + 1));
        col_maKhuyenMai.setCellValueFactory(new PropertyValueFactory<>("maKhuyenMai"));
        col_tenKhuyenMai.setCellValueFactory(new PropertyValueFactory<>("tenKhuyenMai"));
        col_ngayBatDau.setCellValueFactory(cellData -> {
            LocalDate ngaySinh = cellData.getValue().getNgayBatDau();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String ngaySinhFormatted = ngaySinh.format(formatter);
            return new ReadOnlyStringWrapper(ngaySinhFormatted);
        });
        col_ngayKetThuc.setCellValueFactory(cellData -> {
            LocalDate ngaySinh = cellData.getValue().getNgayKetThuc();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String ngaySinhFormatted = ngaySinh.format(formatter);
            return new ReadOnlyStringWrapper(ngaySinhFormatted);
        });
        
        col_luotSuDungConLai.setCellValueFactory(new PropertyValueFactory<>("luotSuDungConLai"));
        col_chietKhau.setCellValueFactory(new PropertyValueFactory<>("chietKhau"));

        tableView_CTKhuyenMai.setItems(CT_KhuyenMai.getListCT_KhuyenMai());

        tableView_CTKhuyenMai.getSelectionModel().select(0);
        tableView_CTKhuyenMai.getSelectionModel().focus(0);
        txtMaKhuyenMai.setEditable(false);
        txtMaKhuyenMai.setDisable(true);
        docDuLieuTuTable();
        handleEventInTable();

        batLoiNhapDuLieu();
        tableView_CTKhuyenMai.requestFocus();
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
            thongBao("Mã khuyến mãi đã bị trùng!");
            txtMaKhuyenMai.selectAll();
            txtMaKhuyenMai.requestFocus();
            return;
        }

        if (!kiemTraTrungTenKhuyenMai(tenKhuyenMai)) {
            thongBao("Tên khuyến mãi đã bị trùng!");
            txtTenKhuyenMai.selectAll();
            txtTenKhuyenMai.requestFocus();
            return;
        }
        try {
            km = new CT_KhuyenMai(maKhuyenMai, tenKhuyenMai, ngayBatDau, ngayKetThuc, luotSuDungConLai, chietKhau);
            CT_KhuyenMai.themCTKhuyenMai(km);
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
            thongBao(exception);

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
        int count = 0;
        for (CT_KhuyenMai km : dsKhuyenMai) {
            if (cc.trim().equals(km.getTenKhuyenMai())) {
                count++;

            }
        }
        if (count == 2) {
            return false;
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
//        String dateString = dateNgayBatDau.getEditor().getText();
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
//        LocalDate ngayBatDau = LocalDate.parse(dateString, formatter);
//        dateString = dateNgayKetThuc.getEditor().getText();
//        LocalDate ngayKetThuc = LocalDate.parse(dateString, formatter);
        LocalDate ngayBatDau = dateNgayBatDau.getValue();
        LocalDate ngayKetThuc = dateNgayKetThuc.getValue();
        if (!kiemTraTrungTenKhuyenMai(tenKhuyenMai)) {
            thongBao("Tên khuyến mãi đã bị trùng!");
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
                dateNgayBatDau.getEditor().selectAll();
            } else if (exception.equals("Lượt sử dụng khuyến mãi phải lớn hơn 0")) {
                txtLuotSuDung.selectAll();
                txtLuotSuDung.requestFocus();
            } else if (exception.equals("Chiết khấu phải lớn hơn 0 và nhỏ hơn hoặc bằng 50")) {
                txtChietKhau.selectAll();
                txtChietKhau.requestFocus();
            }
            thongBao(exception);
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

    private void xuLyXoaKhuyenMai() throws IOException, Exception {
        if (!kiemTraRong()) {
            return;
        }

        Alert alert = new Alert(Alert.AlertType.ERROR, "", ButtonType.YES, ButtonType.NO);
        alert.getDialogPane().setStyle("-fx-font-family: 'sans-serif';");
        alert.setTitle("Vui lòng xác nhận");
        alert.setHeaderText("Bạn có muốn xóa dữ liệu này không ?");
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.YES) {
                CT_KhuyenMai.xoaCTKhuyenMai(txtMaKhuyenMai.getText());
                tableView_CTKhuyenMai.setItems(CT_KhuyenMai.getListCT_KhuyenMai());
                xuLyLamMoiKhuyenMai();
            } else {
                return;
            }
        });
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
        txtMaKhuyenMai.setEditable(true);
        txtMaKhuyenMai.setDisable(false);
        txtTenKhuyenMai.setText("");
        txtLuotSuDung.setText("");
        dateNgayBatDau.setValue(null);
        dateNgayKetThuc.setValue(null);
        txtChietKhau.setText("");
        txtTimMaKhuyenMai.setText("");
        tableView_CTKhuyenMai.setItems(CT_KhuyenMai.getListCT_KhuyenMai());
        tableView_CTKhuyenMai.refresh();
        tableView_CTKhuyenMai.getSelectionModel().clearSelection();
    }
//

    public void handleTimKhuyenMaiButtonAction(ActionEvent event) {
        try {
            xuLyTimKhuyenMai();
        } catch (Exception ex) {
            Logger.getLogger(GD_QLNhanVienController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void xuLyTimKhuyenMai() throws IOException, Exception {
        String maTimKiem = txtTimMaKhuyenMai.getText();
        if (!kiemTraRong()) {
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
                txtMaKhuyenMai.setEditable(false);
                txtMaKhuyenMai.setDisable(true);
                docDuLieuTuTable();
            }

        });
        tableView_CTKhuyenMai.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.UP || event.getCode() == KeyCode.DOWN) {
                    txtMaKhuyenMai.setEditable(false);
                    txtMaKhuyenMai.setDisable(true);
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
        if (txtMaKhuyenMai.getText().equals("") || !validateId(txtMaKhuyenMai.getText())) {
            thongBao("Mã khuyến mãi không được rỗng hoặc cú pháp không hợp lệ");
            txtMaKhuyenMai.selectAll();
            txtMaKhuyenMai.requestFocus();
            return false;
        }

        if (txtTenKhuyenMai.getText().equals("") || !validateName(txtTenKhuyenMai.getText())) {
            thongBao("Tên khuyến mãi không được rỗng hoặc cú pháp không hợp lệ");
            txtTenKhuyenMai.selectAll();
            txtTenKhuyenMai.requestFocus();
            return false;
        }
        if (dateNgayBatDau.getEditor().getText() == null) {
            thongBao("Ngày bắt đầu không được rỗng hoặc cú pháp không hợp lệ");
            dateNgayBatDau.requestFocus();
            dateNgayBatDau.getEditor().selectAll();
            return false;
        }

        if (dateNgayKetThuc.getValue() == null) {
            thongBao("Ngày kết thúc không được rỗng hoặc cú pháp không hợp lệ");
            dateNgayKetThuc.requestFocus();
            dateNgayKetThuc.getEditor().selectAll();
            return false;
        }

        if (txtLuotSuDung.getText().equals("") || !validateNumber(txtLuotSuDung.getText())) {
            thongBao("Lượt sử dụng không được rỗng hoặc cú pháp không hợp lệ");
            txtLuotSuDung.selectAll();
            txtLuotSuDung.requestFocus();
            return false;
        }

        if (txtChietKhau.getText().equals("") || !validateNumber(txtChietKhau.getText())) {
            thongBao("Chiết khấu không được rỗng hoặc cú pháp không hợp lệ");
            txtChietKhau.selectAll();
            txtChietKhau.requestFocus();
            return false;
        }

        return true;
    }

    public void thongBao(String noiDung) throws IOException {
        Alert alert = new Alert(Alert.AlertType.ERROR, "Vui lòng nhập lại thông tin!", ButtonType.OK);
        alert.getDialogPane().setStyle("-fx-font-family: 'sans-serif';");
        alert.setTitle("Có lỗi xảy ra");
        alert.setHeaderText(noiDung);
        alert.showAndWait();
    }

    public void batLoiNhapDuLieu() {
        txtMaKhuyenMai.setOnKeyTyped((event) -> {
            String text = txtMaKhuyenMai.getText();
            if (text.isEmpty()) {
                lblErrorId.setText("");
            } else if (validateId(text)) {
                lblErrorId.setText("");
            } else {
                lblErrorId.setText("Chuỗi chỉ chấp nhận chữ cái (không có dấu) hoặc số!");
            }
        });

        txtTenKhuyenMai.setOnKeyTyped((event) -> {
            String text = txtTenKhuyenMai.getText();
            if (text.isEmpty()) {
                lblErrorTen.setText("");
            } else if (validateName(text)) {
                lblErrorTen.setText("");
            } else {
                lblErrorTen.setText("Chuỗi chỉ chấp nhận chữ cái, số và dấu cách!");
            }

        });
		
        txtLuotSuDung.setOnKeyTyped((event) -> {
            String text = txtLuotSuDung.getText();
            if (text.isEmpty()) {
                lblErrorLuotSuDung.setText("");
            } else if (validateNumber(text)) {
                lblErrorLuotSuDung.setText("");
            } else {
                lblErrorLuotSuDung.setText("Chuỗi chỉ chấp nhận là số!");
            }
        });
        txtChietKhau.setOnKeyTyped((event) -> {
            String text = txtChietKhau.getText();
            if (text.isEmpty()) {
                lblErrorChietKhau.setText("");
            } else if (validateNumber(text)) {
                lblErrorChietKhau.setText("");
            } else {
                lblErrorChietKhau.setText("Chuỗi chỉ chấp nhận là số!");
            }
        });
    }

    // check Id va Ten
    private boolean validateId(String input) {
        String regexPattern = "^[a-zA-Z0-9]+$";
        Pattern pattern = Pattern.compile(regexPattern);
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }

    private boolean validateName(String input) {
        String regexPattern = "^[\\p{L}0-9 ]+$";
        Pattern pattern = Pattern.compile(regexPattern);
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }
// check Ngay 

//    private boolean validateDate(String input) {
//
//        String regexPattern = "^(0[1-9]|1[0-2])/(0[1-9]|[12][0-9]|3[01])/(\\d{4})$";
//        Pattern pattern = Pattern.compile(regexPattern);
//        Matcher matcher = pattern.matcher(input);
//        return matcher.matches();
//    }
// check Luot su dung va Chiet khau
    private boolean validateNumber(String input) {
        String regexPattern = "^[0-9]+$";
        Pattern pattern = Pattern.compile(regexPattern);
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }
}
