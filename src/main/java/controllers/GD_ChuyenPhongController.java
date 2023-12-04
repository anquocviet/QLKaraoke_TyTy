/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controllers;

import connect.ConnectDB;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.ReadOnlyStringWrapper;
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
                tinhTrangString = "PHÒNG TRỐNG";
            } else if (tinhTrang == 1) {
                tinhTrangString = "ĐÃ THUÊ";
            } else {
                tinhTrangString = "ĐÃ ĐẶT";
            }

            return new ReadOnlyStringWrapper(tinhTrangString);

        });
        giaTienMoiGioCol.setCellValueFactory(cellData -> {
            long giaPhong = cellData.getValue().getGiaPhong();
            return new ReadOnlyStringWrapper(giaPhong + "K/H");
        });
        // giaTienMoiGioCol.setCellValueFactory(new PropertyValueFactory<>("giaPhong"));
        //  table.setItems(layTatCaPhong());
        listPhong = Phong.getListPhongByStatus(0);
        table.setItems(listPhong);
        // ActionEvent event = null;
        //handleRefresh(event);
        handleEventInTable();
    }

    @FXML
    void handleRefresh(ActionEvent event) {
        table.setItems(Phong.getListPhongByStatus(0));
        // handleEventInTable();
        docDuLieuTuTable();
        lblPhongMoi.setText("");
    }

    @FXML
    void handleExit(ActionEvent event) {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    void handleChuyenPhong(ActionEvent event) throws Exception {
        Phong phongDuocChon = table.getSelectionModel().getSelectedItem();
        String maHoaDon = HoaDonThanhToan.getBillIDByRoomID(roomID);
        System.out.println(maHoaDon);
        ObservableList<Phong> list = Phong.getListPhongByID(roomID);
        Phong phongHienTai = list.get(0);

        if (phongDuocChon != null) {
            phongDuocChon.updateStatusRoom(phongDuocChon.getMaPhong(), 1);
            phongHienTai.updateStatusRoom(phongHienTai.getMaPhong(), 2);
            ChiTietHD_Phong hdPhongHienTai = ChiTietHD_Phong.getChiTietHD_PhongTheoMaPhongVaMaHoaDon(maHoaDon, phongHienTai.getMaPhong());
            System.out.println(hdPhongHienTai);

            hdPhongHienTai.setGioRa(LocalDateTime.now());
            ChiTietHD_Phong.updateCTHD_Phong(hdPhongHienTai);
            System.out.println(hdPhongHienTai);
            ChiTietHD_Phong hdPhongMoi = new ChiTietHD_Phong(hdPhongHienTai.getHoaDon(), phongDuocChon, LocalDateTime.now(), LocalDateTime.of(2024, Month.JANUARY, 1, 0, 0));
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
        Phong cp = table.getSelectionModel().getSelectedItem();
        if (cp == null) {
            return;
        }
        lblPhongMoi.setText(cp.getMaPhong());
        
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

    @FXML
    public void handleSearch(ActionEvent event) {

        String searchCode = txtSearch.getText().trim();
        List<String> danhSachMaPhong = new ArrayList<>();
        for (Phong phong : Phong.getListPhongByStatus(0)) {
            String maPhong = phong.getMaPhong();
            danhSachMaPhong.add(maPhong);
        }
        boolean isValidCode = danhSachMaPhong.contains(searchCode);
        System.out.println(searchCode);
        System.out.println(danhSachMaPhong);
        if (isValidCode) {
            table.setItems(Phong.getListPhongByID(searchCode));
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Vui lòng Nhập mã lại ", ButtonType.OK);
            alert.getDialogPane().setStyle("-fx-font-family: 'sans-serif';");
            alert.setTitle("Có lỗi xảy ra");
            alert.setHeaderText("Có vẻ mã phòng đã sai cú pháp hoặc phòng cần tìm không được phép chuyển!");
            alert.showAndWait();

        }
    }

}
