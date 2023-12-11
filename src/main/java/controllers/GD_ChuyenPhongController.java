/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controllers;

import java.net.URL;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.ChiTietHD_Phong;
import model.HoaDonThanhToan;
import model.PhieuDatPhong;

import model.Phong;

/**
 * FXML Controller class
 *
 * @author thach
 */
public class GD_ChuyenPhongController implements Initializable {

    String roomID = GD_QLKinhDoanhPhongController.roomID;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        lblMaPhong.setText(roomID);
        maPhongCol.setCellValueFactory(new PropertyValueFactory<>("maPhong"));
        //loaiPhongCol.setCellValueFactory(new PropertyValueFactory<>("loaiPhong"));
        loaiPhongCol.setCellValueFactory(cellData -> {
            int loaiPhong = cellData.getValue().getLoaiPhong();
            String loaiPhongString;
            if (loaiPhong == 1) {
                loaiPhongString = "VIP";
            } else {
                loaiPhongString = "THƯỜNG";
            }
            return new ReadOnlyStringWrapper(loaiPhongString);
        });
        soNguoiCol.setCellValueFactory(new PropertyValueFactory<>("sucChua"));
        //trangThaiCol.setCellValueFactory(new PropertyValueFactory<>("tinhTrang"));
        trangThaiCol.setCellValueFactory(cellData -> {
            int tinhTrang = cellData.getValue().getTinhTrang();
            String tinhTrangString;
            if (tinhTrang == 0) {
                tinhTrangString = "Phòng Trống";
            } else if (tinhTrang == 2) {
                tinhTrangString = "Phòng Chờ";
            } else {
                tinhTrangString = "Đang Sử Dụng";
            }

            return new ReadOnlyStringWrapper(tinhTrangString);

        });
        giaTienMoiGioCol.setCellValueFactory(cellData -> {
            long giaPhong = cellData.getValue().getGiaPhong();
            return new ReadOnlyStringWrapper(giaPhong + "K/H");
        });

        try {
            dataPhong();
        } catch (Exception ex) {
            Logger.getLogger(GD_ChuyenPhongController.class.getName()).log(Level.SEVERE, null, ex);
        }
        table.setItems(listPhong);
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
    void handleRefresh(ActionEvent event) throws Exception {
        dataPhong();
        table.setItems(listPhong);
        lblPhongMoi.setText("");
        txtSearch.setText("");
    }
    
    public void docDuLieuTuTable() {
        Phong cp = table.getSelectionModel().getSelectedItem();
        if (cp == null) {
            return;
        }
        lblPhongMoi.setText(cp.getMaPhong());
    }

//  lấy dữ liệu phòng có thể chuyển 
    public void dataPhong() throws Exception {
        listPhong = Phong.getListPhongByStatus(0);
        listPhong.addAll(Phong.getListPhongByStatus(2));
        ObservableList<PhieuDatPhong> listPhieuPhongDaDat = PhieuDatPhong.getAllBookingTicket();

        ObservableList<String> listTongGio = FXCollections.observableArrayList();
        ObservableList<Phong> listXoa = FXCollections.observableArrayList();
        for (Phong phong : listPhong) {
            boolean hasMatchingPhieuDatPhong = false;
            boolean checkXoa = false;
            for (PhieuDatPhong phieuDatPhong : listPhieuPhongDaDat) {
                if (phong.equals(phieuDatPhong.getPhong())) {
                    float time = ((float) Duration.between(LocalDateTime.now(), phieuDatPhong.getThoiGianNhan()).toMillis()) / 1000 / 3600;
                    if (time >= 2) {
                        float gioSuDung = ((float) Duration.between(LocalDateTime.now(), phieuDatPhong.getThoiGianNhan()).toMillis()) / 1000 / 60;
                        long minus = (long) gioSuDung % 60;
                        long hour = ((long) gioSuDung - minus) / 60;
                        listTongGio.add(hour + " giờ " + minus + " phút");
                        listPhieuPhongDaDat.remove(phieuDatPhong);
                        hasMatchingPhieuDatPhong = true;
                        break;
                    } else {
                        checkXoa = true;
                        listXoa.add(phong);
                        break;
                    }
                }
            }
            if (!hasMatchingPhieuDatPhong && checkXoa == false) {
                listTongGio.add("Đến khi trả phòng hoặc đóng cửa");
            }

        }
        listPhong.removeAll(listXoa);
        tongGioSuDungCol.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(listTongGio.get(table.getItems().indexOf(param.getValue()))));
    }

    @FXML
    void handleExit(ActionEvent event
    ) {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void handleSearch(ActionEvent event
    ) {

        String searchCode = txtSearch.getText().trim();
        ObservableList<Phong> danhSachMaPhong = Phong.getListPhongByID(searchCode);

        if (danhSachMaPhong != null) {
            table.setItems(danhSachMaPhong);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Vui lòng Nhập mã lại ", ButtonType.OK);
            alert.getDialogPane().setStyle("-fx-font-family: 'sans-serif';");
            alert.setTitle("Có lỗi xảy ra");
            alert.setHeaderText("Có vẻ mã phòng đã sai cú pháp hoặc phòng cần tìm không được phép chuyển!");
            alert.showAndWait();

        }
    }

    @FXML
    void handleChuyenPhong(ActionEvent event) throws Exception {
        Phong phongDuocChon = table.getSelectionModel().getSelectedItem();
        String maHoaDon = HoaDonThanhToan.getBillIDByRoomID(roomID);
        System.out.println(maHoaDon);
        Phong phongHienTai = Phong.getPhongTheoMaPhong(roomID);

        if (phongDuocChon != null) {
            phongDuocChon.updateStatusRoom(phongDuocChon.getMaPhong(), 1);
            phongHienTai.updateStatusRoom(phongHienTai.getMaPhong(), 2);
            ChiTietHD_Phong hdPhongHienTai = ChiTietHD_Phong.getChiTietHD_PhongTheoMaPhongVaMaHoaDon(maHoaDon, phongHienTai.getMaPhong());
            System.out.println(hdPhongHienTai);

            hdPhongHienTai.setGioRa(LocalDateTime.now());
            ChiTietHD_Phong.updateCTHD_Phong(hdPhongHienTai);
            System.out.println(hdPhongHienTai);
            LocalDateTime gioVaoMoi = hdPhongHienTai.getGioRa().plus(5, ChronoUnit.MINUTES);
            ChiTietHD_Phong hdPhongMoi = new ChiTietHD_Phong(hdPhongHienTai.getHoaDon(), phongDuocChon, gioVaoMoi, LocalDateTime.of(2050, Month.JANUARY, 1, 0, 0));
            ChiTietHD_Phong.themChiTietHD_Phong(hdPhongMoi);
            System.out.println(hdPhongMoi);
            showSuccessAlert();

        } else {
            showErrorAlert("Vui lòng chọn phòng trong danh sách");
        }
    }

    private void showSuccessAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.OK);
        configureAlert(alert, "Chuyển phòng thành công!");
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

    //fxml Chuyển Phòng
    @FXML
    private TableView<Phong> table;
    @FXML
    private TableColumn<Phong, String> maPhongCol;
    @FXML
    private TableColumn<Phong, String> loaiPhongCol;
    @FXML
    private TableColumn<Phong, Integer> soNguoiCol;
    @FXML
    private TableColumn<Phong, String> trangThaiCol;
    @FXML
    private TableColumn<Phong, String> giaTienMoiGioCol;
    @FXML
    private TableColumn<Phong, String> tongGioSuDungCol;

    @FXML
    private Label lblPhongMoi;

    @FXML
    private Label lblMaPhong;

    @FXML
    private TextField txtSearch;

    @FXML
    private Button refreshButton;

    @FXML
    private Button exitButton;

    @FXML
    private Button chuyenPhongButton;

    @FXML
    private Button searchButton;

    ObservableList<Phong> listPhong;

}
