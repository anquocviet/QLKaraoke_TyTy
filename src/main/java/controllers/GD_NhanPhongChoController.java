/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controllers;

import entities.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import main.App;
import socket.ClientSocket;

import java.io.*;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import static controllers.GD_QLKinhDoanhPhongController.roomID;

//import static controllers.GD_QLKinhDoanhPhongController.roomID;

/**
 * FXML Controller class
 *
 * @author fil
 */
public class GD_NhanPhongChoController implements Initializable {

    @FXML
    private TextField txtSoPhong;
    @FXML
    private TextField txtMaPhieuDat;
    @FXML
    private TextField txtSDTKhachHang;
    @FXML
    private TextField txtTenKhachHang;
    @FXML
    private TextField txtNamSinh;
    @FXML
    private Text timeNhanPhong;

    @FXML
    private ComboBox ccbGender;
    @FXML
    private DatePicker dateNhanPhong;

    @FXML
    private Button btnKiemTra;
    @FXML
    private Button btnExit;

    private boolean kiemTra = false;

    PhieuDatPhong phieu = null;
    DataInputStream dis = ClientSocket.getDis();
    DataOutputStream dos = ClientSocket.getDos();
    ObjectInputStream in = ClientSocket.getIn();
    ObjectOutputStream out = ClientSocket.getOut();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
//      txtSoPhong.setText(roomID);

    }

    @FXML
    public void handleKiemTraSDT(ActionEvent event) throws Exception {
        String soDienThoai = txtSDTKhachHang.getText().trim();
        if (!isValidPhoneNumber(soDienThoai)) {
            showAlert("Số điện thoại không hợp lệ", "Vui lòng nhập số điện thoại hợp lệ.");
            return;
        }

        dos.writeUTF("customer-find-customer-by-phone," + soDienThoai.substring(1));
        List<KhachHang> listKhachHang = (List<KhachHang>) in.readObject();
        if (listKhachHang.isEmpty()) {
            showAlert("Không tìm thấy khách hàng", "Không có thông tin khách hàng cho số điện thoại này. Vui lòng thêm khách hàng trước khi đặt phòng!");
            return;
        }
        KhachHang khachHang = listKhachHang.getFirst();

        dos.writeUTF("bookingTicket-find-booking-ticket-by-customer," + khachHang.getMaKhachHang());
        List<PhieuDatPhong> list = (List<PhieuDatPhong>) in.readObject();
        ObservableList<PhieuDatPhong> listPhieu = FXCollections.observableArrayList(list);
        phieu = listPhieu.getFirst();
        txtSoPhong.setText(phieu.getPhong().getMaPhong());
        txtMaPhieuDat.setText(phieu.getMaPhieuDat());


        if (listPhieu == null || phieu == null) {
            showAlert("Không tìm thấy phòng đặt", "Vui lòng nhập chính xác số điện thoại");
            return;
        }
        txtMaPhieuDat.setText(phieu.getMaPhieuDat());
        LocalDate ngayNhanTmp = LocalDate.ofInstant(phieu.getThoiGianNhan(), ZoneId.systemDefault());
        dateNhanPhong.setValue(ngayNhanTmp);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH 'giờ':mm 'phút'");
        String formattedTime = formatter.format(phieu.getThoiGianNhan().atZone(ZoneId.systemDefault()));
        timeNhanPhong.setText(formattedTime);
        txtTenKhachHang.setText(khachHang.getTenKhachHang());
        txtNamSinh.setText(String.valueOf(khachHang.getNamSinh()));
        ccbGender.setValue(khachHang.getGioiTinh() == 1 ? "Nam" : "Nữ");
        btnKiemTra.setDisable(true);
        kiemTra = true;

    }

    public PhieuDatPhong getPhieuByMaPhieu(ObservableList<PhieuDatPhong> list, String maPhieu) {
        for (PhieuDatPhong phieu : list) {
            if (phieu.getMaPhieuDat().equals(maPhieu)) {
                return phieu;
            }
        }
        return null;
    }

    @FXML
    public void handleRefresh(ActionEvent event) {
        phieu = null;
        txtMaPhieuDat.setText("");
        txtSDTKhachHang.setText("");
        txtTenKhachHang.clear();
        txtNamSinh.clear();
        ccbGender.getItems().clear();
        ccbGender.setValue("");
        dateNhanPhong.setValue(null);
        timeNhanPhong.setText("00 : 00");
        btnKiemTra.setDisable(false);
    }

    @FXML
    public void handleExit(ActionEvent event) {
        phieu = null;
        ccbGender.getItems().clear();
        btnKiemTra.setDisable(false);
        Stage stage = (Stage) btnExit.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void handleNhanPhong(ActionEvent event) throws Exception {
        if (kiemTra == false) {
            showAlert("Lỗi!", "Vui lòng kiểm tra thông tin trước khi thực hiện Nhận phòng");
            return;
        }
        String soPhong = txtSoPhong.getText();
        String maPhieuDat = txtMaPhieuDat.getText();
        String soDienThoai = txtSDTKhachHang.getText();
        String tenKhachHang = txtTenKhachHang.getText();
        String namSinh = txtNamSinh.getText();
        String gioiTinh = ccbGender.getValue().toString();
        LocalDateTime ngayNhanPhong = dateNhanPhong.getValue().atStartOfDay();
        String gioNhan = timeNhanPhong.getText();

        if (soDienThoai.equals(null)) {
            showAlert("Lỗi!", "Không được để trống Số điện thoại");
            return;
        } else if (!isValidPhoneNumber(soDienThoai)) {
            showAlert("Số điện thoại không hợp lệ", "Vui lòng nhập số điện thoại hợp lệ.");
            return;
        } else if (Instant.now().isBefore(phieu.getThoiGianNhan()) == true) {
            showAlert("Chưa đến giờ nhận phòng", "Vui lòng chờ đến giờ nhận phòng.");
            return;
        }

        phieu.getPhong().setTinhTrang(1);
        dos.writeUTF("room-update-room");
        out.reset();
        out.writeObject(phieu.getPhong());
        if (!dis.readBoolean()) {
            showErrorAlert("Có lỗi xảy ra khi cập nhật trạng thái phòng!");
            return;
        }
        phieu.setTinhTrang(1);
        dos.writeUTF("bookingTicket-update-booking-ticket");
        out.reset();
        out.writeObject(phieu);
        if (!dis.readBoolean()) {
            showErrorAlert("Có lỗi xảy ra khi cập nhật phiếu đặt phòng!");
            return;
        }
        String maHoaDon;

        NhanVien nhanVienLap = App.user;
        KhachHang khachHang = phieu.getKhachHang();

        dos.writeUTF("bill-count-bill-by-date");
        out.writeObject(Instant.now());
        long count = dis.readLong();
        maHoaDon = phatSinhMaHoaDon((int) count);
        HoaDonThanhToan hoaDon = new HoaDonThanhToan(maHoaDon, khachHang, nhanVienLap, null, Instant.now(), 0);
        dos.writeUTF("bill-add-bill");
        out.writeObject(hoaDon);
        if (!dis.readBoolean()) {
            showErrorAlert("Có lỗi xảy ra khi tạo hóa đơn!");
            return;
        }

        ChiTietHD_Phong ctP = new ChiTietHD_Phong(new ChiTietHD_PhongId(), hoaDon, phieu.getPhong(), Instant.now(), Instant.now().plusSeconds(1), 0.0, 0);
        dos.writeUTF("roomDetail-add-roomDetail");
        out.writeObject(ctP);
        if (!dis.readBoolean()) {
            showErrorAlert("Có lỗi xảy ra khi thêm chi tiết hóa đơn!");
            return;
        }
        showSuccessAlert();
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();

        try {
            App.setRoot("GD_QLKinhDoanhPhong");
        } catch (IOException ex) {
            Logger.getLogger(GD_NhanPhongChoController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber.matches("\\d{10}");
    }

    public void showAlert(String title, String content) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.getDialogPane().setStyle("-fx-font-family: 'sans-serif';");
        alert.showAndWait();
    }

    private void showSuccessAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.OK);
        configureAlert(alert, "Nhận phòng thành công!");
        alert.showAndWait();
    }

    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
        configureAlert(alert, "Có lỗi xảy ra");
        alert.showAndWait();
    }

    private void configureAlert(Alert alert, String title) {
        alert.getDialogPane().setStyle("-fx-font-family: 'sans-serif';");
        alert.setTitle("Thông báo");
        alert.setHeaderText(title);

        // Xử lý sự kiện khi nút "OK" được nhấn
        ButtonType buttonTypeOK = alert.getButtonTypes().stream()
                .filter(buttonType -> buttonType.getButtonData() == ButtonBar.ButtonData.OK_DONE)
                .findFirst()
                .orElse(null);

        if (buttonTypeOK != null) {
            Button buttonOK = (Button) alert.getDialogPane().lookupButton(buttonTypeOK);
            buttonOK.setOnAction(event -> {
                handleExit(event);
                alert.close();
            });
        }
    }

    public String phatSinhMaHoaDon(int stt) {
        Date ngayLap = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("ddMM");
        String ngayThangNam = dateFormat.format(ngayLap);

        String strSTT = String.format("%02d", stt + 1);

        SimpleDateFormat yearFormat = new SimpleDateFormat("yy");
        String namCuoi = yearFormat.format(ngayLap);

        String maHoaDon = "HD" + strSTT + ngayThangNam + namCuoi;

        return maHoaDon;
    }

}
