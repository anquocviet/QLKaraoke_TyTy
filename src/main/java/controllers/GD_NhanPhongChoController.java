/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controllers;

import static controllers.GD_QLKinhDoanhPhongController.roomID;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
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
import model.CT_KhuyenMai;
import model.ChiTietHD_Phong;
import model.HoaDonThanhToan;
import model.KhachHang;
import model.NhanVien;
import model.PhieuDatPhong;
import model.Phong;

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
    @FXML
    private Button btnRefresh;
    @FXML
    private Button btnNhanPhong;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        txtSoPhong.setText(roomID);

    }

    @FXML
    public void handleKiemTraSDT(ActionEvent event) throws Exception {
        String soDienThoai = txtSDTKhachHang.getText().trim();
        if (!isValidPhoneNumber(soDienThoai)) {
            showAlert("Số điện thoại không hợp lệ", "Vui lòng nhập số điện thoại hợp lệ.");
            return;
        }

        KhachHang khachHang = KhachHang.getKhachHangTheoSoDienThoai(soDienThoai);
        if (khachHang != null) {
            PhieuDatPhong phieu = null;
            ObservableList<PhieuDatPhong> listPhieu = PhieuDatPhong.getAllBookingTicketByIDKhachHang(khachHang.getMaKhachHang());
            Phong phong = Phong.getPhongTheoMaPhong(roomID);
            for (PhieuDatPhong phieuDatPhong : listPhieu) {
                if (phieuDatPhong.getPhong().equals(phong)) {
                    phieu = PhieuDatPhong.getBookingTicketByID(phieuDatPhong.getMaPhieuDat());
                    break;
                }
            }
            txtMaPhieuDat.setText(phieu.getMaPhieuDat());
            LocalDate ngayNhanTmp = phieu.getThoiGianNhan().toLocalDate();
            dateNhanPhong.setValue(ngayNhanTmp);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH 'giờ':mm 'phút'");
            String formattedTime = phieu.getThoiGianNhan().format(formatter);
            timeNhanPhong.setText(formattedTime);
            txtTenKhachHang.setText(khachHang.getTenKhachHang());
            txtNamSinh.setText(String.valueOf(khachHang.getNamSinh()));
            ccbGender.setValue(khachHang.isGioiTinh() ? "Nam" : "Nữ");
            btnKiemTra.setDisable(true);
        } else {
            showAlert("Không tìm thấy khách hàng", "Không có thông tin khách hàng cho số điện thoại này. Vui lòng thêm khách hàng trước khi đặt phòng!");
        }
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
        ccbGender.getItems().clear();
        btnKiemTra.setDisable(false);
        Stage stage = (Stage) btnExit.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void handleNhanPhong(ActionEvent event) throws Exception {
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
        } else if (ngayNhanPhong.isAfter(LocalDateTime.now())) {
            showAlert("Chưa đến giờ nhận phòng", "Vui lòng chờ đến giờ nhận phòng.");
            return;
        }

        Phong.updateStatusRoom(soPhong, 1);
        PhieuDatPhong.updateTrangThaiPhieuDat(maPhieuDat, false);
        String maHoaDon;
        CT_KhuyenMai khuyenMai = CT_KhuyenMai.getCT_KhuyenMaiTheoMaKM("DEFAULT");
        String maNV = App.user;
        NhanVien nhanVienLap = NhanVien.getNhanVienTheoMaNhanVien(maNV);
        KhachHang khachHang = KhachHang.getKhachHangTheoSoDienThoai(soDienThoai);

        int slHoaDon = HoaDonThanhToan.getDemSoLuongHoaDonTheoNgay(ngayNhanPhong);
        maHoaDon = phatSinhMaHoaDon(slHoaDon);
        HoaDonThanhToan hoaDon = new HoaDonThanhToan(maHoaDon, nhanVienLap, khachHang, khuyenMai, LocalDateTime.now());
        Phong p = Phong.getPhongTheoMaPhong(roomID);

        ChiTietHD_Phong ctP = new ChiTietHD_Phong(hoaDon, p, LocalDateTime.now(), LocalDateTime.now().plusSeconds(1));
        System.out.println("chưa có hoa don thanh toan");
        ChiTietHD_Phong.themChiTietHoaDon(ctP);
        HoaDonThanhToan.themHoaDonThanhToan(hoaDon);
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
