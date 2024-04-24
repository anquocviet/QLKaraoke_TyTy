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
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * FXML Controller class
 *
 * @author fil
 */
public class GD_ThuePhongController implements Initializable {

    @FXML
    private TextField txtSoPhong;
    @FXML
    private TextField txtSDTKhachHang;
    @FXML
    private TextField txtTenKhachHang;
    @FXML
    private TextField txtNamSinh;
    @FXML
    private Text timeThue;

    @FXML
    private ComboBox ccbGender;
    @FXML
    private DatePicker dateThue;

    @FXML
    private Button btnKiemTraSĐT;
    @FXML
    private Button btnExit;
    @FXML
    private Button btnRefresh;
    @FXML
    private Button btnThue;

    DataInputStream dis = ClientSocket.getDis();
    DataOutputStream dos = ClientSocket.getDos();
    ObjectInputStream in = ClientSocket.getIn();
    ObjectOutputStream out = ClientSocket.getOut();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        txtSoPhong.setText(GD_QLKinhDoanhPhongController.roomID);

//        try {
//            if (Phong.getListPhongByStatus(2).contains(new Phong(GD_QLKinhDoanhPhongController.roomID))) {
//                KhachHang khachHang = PhieuDatPhong.getCustomerInfoByRoomID(GD_QLKinhDoanhPhongController.roomID);
//                txtSDTKhachHang.setText(khachHang.getSoDienThoai());
//                txtTenKhachHang.setText(khachHang.getTenKhachHang());
//                txtNamSinh.setText(String.valueOf(khachHang.getNamSinh()));
//                ccbGender.setValue(khachHang.isGioiTinh() ? "Nam" : "Nữ");
//                // Cập nhật dateThue với ngày hiện tại
//                dateThue.setValue(LocalDate.now());
//                // Cập nhật timeThue với thời gian hiện tại
//                LocalTime currentTime = LocalTime.now();
//                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
//                String formattedTime = currentTime.format(formatter);
//                timeThue.setText(formattedTime);
//            }
//        } catch (Exception ex) {
//            Logger.getLogger(GD_QLKinhDoanhPhongController.class.getName()).log(Level.SEVERE, null, ex);
//        }

        txtSDTKhachHang.setOnAction(this::handleKiemTraSDT);
        btnKiemTraSĐT.setOnAction(this::handleKiemTraSDT);
        btnExit.setOnAction(this::handleExit);
        btnRefresh.setOnAction(this::handleRefresh);
//        btnThue.setOnAction(this::handleThue);
    }

    @FXML
    public void handleKiemTraSDT(ActionEvent event) {
        try {
            String soDienThoai = txtSDTKhachHang.getText().trim().substring(1);
            if (!isValidPhoneNumber(0 + soDienThoai)) {
                showAlert("Số điện thoại không hợp lệ", "Vui lòng nhập số điện thoại hợp lệ.");
                return;
            }
            dos.writeUTF("customer-find-customer-by-phone," + soDienThoai);
            //chi lay khach hang dau tien
            List<KhachHang> list = (List<KhachHang>) in.readObject();
            ObservableList<KhachHang> khachHangs = FXCollections.observableArrayList(list);
            System.out.println(khachHangs);
            KhachHang khachHang = khachHangs.get(0);

            if (khachHang != null) {
                txtTenKhachHang.setText(khachHang.getTenKhachHang());
                txtNamSinh.setText(String.valueOf(khachHang.getNamSinh()));
                ccbGender.setValue(khachHang.getGioiTinh() == 1 ? "Nam" : "Nữ");
                // Cập nhật dateThue với ngày hiện tại
                dateThue.setValue(LocalDate.now());
                // Cập nhật timeThue với thời gian hiện tại
                LocalTime currentTime = LocalTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
                String formattedTime = currentTime.format(formatter);
                timeThue.setText(formattedTime);
            } else {
                showAlert("Không tìm thấy khách hàng", "Không có thông tin khách hàng cho số điện thoại này.Vui lòng thêm khách hàng trước khi đặt phòng!");
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleRefresh(ActionEvent event) {
        txtSDTKhachHang.clear();
        txtTenKhachHang.clear();
        txtNamSinh.clear();
        ccbGender.setValue("");
        dateThue.setValue(LocalDate.now());
        LocalTime currentTime = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        String formattedTime = currentTime.format(formatter);
        timeThue.setText(formattedTime);
    }

    @FXML
    public void handleExit(ActionEvent event) {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    public void handleThue(ActionEvent event) throws Exception {
        // Lấy thông tin từ giao diện
        String soPhong = txtSoPhong.getText();
        String soDienThoai = txtSDTKhachHang.getText().substring(1);
        String tenKhachHang = txtTenKhachHang.getText();
        String namSinh = txtNamSinh.getText();
        String gioiTinh = ccbGender.getValue().toString();
        LocalDateTime ngayThue = dateThue.getValue().atStartOfDay();
        String gioThue = timeThue.getText();
        if (soDienThoai.equals(null)) {
            showAlert("Lỗi!", "Không được để trống Số điện thoại");
            return;
        } else if (!isValidPhoneNumber(0 + soDienThoai)) {
            showAlert("Số điện thoại không hợp lệ", "Vui lòng nhập số điện thoại hợp lệ.");
            return;
        } else {
            dos.writeUTF("room-find-room," + GD_QLKinhDoanhPhongController.roomID);
            List<Phong> list = (List<Phong>) in.readObject();
            Phong roomThue = list.get(0);
            dos.writeUTF("room-update-room");
            roomThue.setTinhTrang(1);
            out.writeObject(roomThue);
            dis.readBoolean();

//         Phong.updateStatusRoom(soPhong, 1);
        }

        String maHoaDon;
        String maNV = App.user.getMaNhanVien();
        dos.writeUTF("employee-find-employee," + maNV);
        System.out.println(maNV);
        List<NhanVien> list = (List<NhanVien>) in.readObject();
        System.out.println(list);
        NhanVien nhanVienLap = list.get(0);
//        NhanVien nhanVienLap = (NhanVien) in.readObject();
        dos.writeUTF("customer-find-customer-by-phone," + soDienThoai);
        List<KhachHang> list1 = (List<KhachHang>) in.readObject();
        KhachHang khachHang = list1.get(0);
//        KhachHang khachHang = (KhachHang) in.readObject();

//      NhanVien nhanVienLap = NhanVien.getNhanVienTheoMaNhanVien(maNV);
//      KhachHang khachHang = KhachHang.getKhachHangTheoSoDienThoai(soDienThoai);

        dos.writeUTF("bill-find-bill-by-customer-id," + khachHang.getMaKhachHang());
        List<HoaDonThanhToan> list2 = (List<HoaDonThanhToan>) in.readObject();
        HoaDonThanhToan existingHoaDon = list2.get(0);
//        HoaDonThanhToan existingHoaDon = HoaDonThanhToan.getBillByCustomer(khachHang.getMaKhachHang());
        if (existingHoaDon != null) {
            // Nếu đã có hóa đơn, sử dụng mã hóa đơn đã có
            maHoaDon = existingHoaDon.getMaHoaDon();
            HoaDonThanhToan hoaDon = new HoaDonThanhToan();
            hoaDon.setMaHoaDon(maHoaDon);
            hoaDon.setNhanVien(nhanVienLap);
            hoaDon.setKhachHang(khachHang);
            hoaDon.setNgayLap(Instant.now());

//            Phong p = Phong.getPhongTheoMaPhong(soPhong);
            dos.writeUTF("room-find-room," + soPhong);
            List<Phong> list3 = (List<Phong>) in.readObject();
            Phong p = list3.get(0);
//            ChiTietHD_Phong ctP = new ChiTietHD_Phong(hoaDon, p, LocalDateTime.now(), LocalDateTime.now().plusSeconds(1));
            ChiTietHD_Phong ctP = new ChiTietHD_Phong();
            ctP.setHoaDon(hoaDon);
            ctP.setPhong(p);
            ctP.setGioRa(Instant.now());
            ctP.setGioRa(Instant.now().plusSeconds(1));

            dos.writeUTF("roomDetail-add-roomDetail");
            out.writeObject(ctP);
            dis.readBoolean();
//            ChiTietHD_Phong.themChiTietHoaDon(ctP);
        } else {
            // Nếu chưa có hóa đơn, tạo mới mã hóa đơn
            dos.writeUTF("bill-count-by-date," + ngayThue);
            Long slHoaDon = dis.readLong();
//            int slHoaDon = HoaDonThanhToan.getDemSoLuongHoaDonTheoNgay(ngayThue);
            maHoaDon = phatSinhMaHoaDon(slHoaDon);
            HoaDonThanhToan hoaDon = new HoaDonThanhToan();
            hoaDon.setMaHoaDon(maHoaDon);
            hoaDon.setNhanVien(nhanVienLap);
            hoaDon.setKhachHang(khachHang);
            hoaDon.setNgayLap(Instant.now());

//            Phong p = Phong.getPhongTheoMaPhong(soPhong);
            dos.writeUTF("room-find-room," + soPhong);
            Phong p = (Phong) in.readObject();
//            HoaDonThanhToan.themHoaDonThanhToan(hoaDon);
            dos.writeUTF("bill-add-bill");
            out.writeObject(hoaDon);
            dis.readBoolean();
//            ChiTietHD_Phong ctP = new ChiTietHD_Phong(hoaDon, p, LocalDateTime.now(), LocalDateTime.now().plusSeconds(1));
            ChiTietHD_Phong ctP = new ChiTietHD_Phong();
            ctP.setHoaDon(hoaDon);
            ctP.setPhong(p);
            ctP.setGioRa(Instant.now());
            ctP.setGioRa(Instant.now().plusSeconds(1));

//            ChiTietHD_Phong.themChiTietHoaDon(ctP);
            dos.writeUTF("roomDetail-add-roomDetail");
            out.writeObject(ctP);
            dis.readBoolean();
        }

        showAlert("Thông báo", "Đã thực hiện tác vụ thuê phòng!");
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();

        try {
            App.setRoot("GD_QLKinhDoanhPhong");
        } catch (IOException ex) {
            Logger.getLogger(GD_ThuePhongController.class.getName()).log(Level.SEVERE, null, ex);
        }
        stage.close();
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

    public String phatSinhMaHoaDon(Long stt) {
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
