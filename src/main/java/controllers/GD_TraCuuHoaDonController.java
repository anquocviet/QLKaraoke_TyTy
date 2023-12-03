/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controllers;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.text.DecimalFormat;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import main.App;
import model.ChiTietHD_DichVu;
import model.ChiTietHD_Phong;
import model.HoaDonThanhToan;

/**
 * FXML Controller class
 *
 * @author fil
 */
public class GD_TraCuuHoaDonController implements Initializable {

    private DecimalFormat df = new DecimalFormat("#,###,###,##0.##");

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        maHoaDonCol.setCellValueFactory(new PropertyValueFactory<>("maHoaDon"));
        tenKHCol.setCellValueFactory(cellData -> {
            String tenKhachHang = cellData.getValue().getKhachHang().getTenKhachHang();
            return new ReadOnlyStringWrapper(tenKhachHang);
        });
        sdtCol.setCellValueFactory(cellData -> {
            String soDienThoai = cellData.getValue().getKhachHang().getSoDienThoai();
            return new ReadOnlyStringWrapper(soDienThoai);
        });
        ngayLapCol.setCellValueFactory(new PropertyValueFactory<>("ngayLap"));

        danhSach_HoaDon = HoaDonThanhToan.getAllHoaDon();
        tableHoaDon.setItems(danhSach_HoaDon);
        tableHoaDon.requestFocus();
        handleEventInTable();

        tableHoaDon.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                String maHD = newSelection.getMaHoaDon();
                try {
                    tenDichVuCol.setCellValueFactory(cellData -> {
                        String tenDichVu = cellData.getValue().getDichVu().getTenDichVu();
                        return new ReadOnlyStringWrapper(tenDichVu);
                    });
                    soLuongCol.setCellValueFactory(new PropertyValueFactory<>("soLuong"));
                    donViTinhCol.setCellValueFactory(cellData -> {
                        String donViTinh = cellData.getValue().getDichVu().getDonViTinh();
                        return new ReadOnlyStringWrapper(donViTinh);
                    });
                    thanhTienDVCol.setCellValueFactory(cellData -> {
                        long thanhTien = cellData.getValue().getThanhTien();
                        return new ReadOnlyObjectWrapper<>(thanhTien);
                    });

                    tableDichVu.setItems(ChiTietHD_DichVu.getCTDichVuTheoMaHD(maHD));
                } catch (Exception ex) {
                    Logger.getLogger(GD_TraCuuHoaDonController.class.getName()).log(Level.SEVERE, null, ex);
                }

                try {
                    maPhongCol.setCellValueFactory((param) -> {
                        String maPhong = param.getValue().getPhong().getMaPhong();
                        return new ReadOnlyObjectWrapper<>(maPhong);
                    });
                    loaiPhongCol.setCellValueFactory((param) -> {
                        if (param.getValue().getPhong() == null) {
                            return new ReadOnlyObjectWrapper<>();
                        }
                        String loaiPhong = param.getValue().getPhong().getLoaiPhong() == 1 ? "VIP" : "Thường";
                        return new ReadOnlyObjectWrapper<>(loaiPhong);
                    });
                    gioVaoCol.setCellValueFactory((param) -> {
                        if (param.getValue().getGioVao() == null) {
                            return new ReadOnlyObjectWrapper<>();
                        }
                        String gioVao = param.getValue().getGioVao().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
                        return new ReadOnlyObjectWrapper<>(gioVao);
                    });
                    gioRaCol.setCellValueFactory((param) -> {
                        if (param.getValue().getGioRa() == null) {
                            return new ReadOnlyObjectWrapper<>();
                        }
                        String gioRa = param.getValue().getGioRa().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
                        return new ReadOnlyObjectWrapper<>(gioRa);
                    });
                    gioSuDungCol.setCellValueFactory((param) -> {
                        if (param.getValue().getGioVao() == null || param.getValue().getGioRa() == null) {
                            return new ReadOnlyObjectWrapper<>();
                        }
                        String gioSuDung = df.format(param.getValue().tinhTongGioSuDung());
                        return new ReadOnlyObjectWrapper<>(gioSuDung);
                    });
                    donGiaCol.setCellValueFactory((param) -> {
                        if (param.getValue().getPhong() == null || param.getValue().getGioRa() == null) {
                            return new ReadOnlyObjectWrapper<>();
                        }
                        long donGia = param.getValue().getPhong().getGiaPhong();
                        return new ReadOnlyObjectWrapper<>(df.format(donGia));
                    });
                    thanhTienCol.setCellValueFactory((param) -> {
                        return new ReadOnlyObjectWrapper<>(df.format(param.getValue().tinhThanhTien()));
                    });

                    ObservableList<ChiTietHD_Phong> dsPhong = ChiTietHD_Phong.getCT_PhongTheoMaHD(maHD);
                    dsPhong.forEach(ct -> {
                        try {
                            ct.setGioRa(LocalDateTime.now());
                        } catch (Exception ex) {
                            Logger.getLogger(GD_TraCuuHoaDonController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    });
                    tablePhong.setItems(dsPhong);
                } catch (Exception ex) {
                    Logger.getLogger(GD_TraCuuHoaDonController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    public void handleEventInTable() {
        tableHoaDon.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
            }
        });
        tableHoaDon.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.UP || event.getCode() == KeyCode.DOWN) {
                }
            }
        });
    }

    @FXML
    private TableView<ChiTietHD_DichVu> tableDichVu;
    @FXML
    private TableColumn<ChiTietHD_DichVu, String> tenDichVuCol;
    @FXML
    private TableColumn<ChiTietHD_DichVu, Integer> soLuongCol;
    @FXML
    private TableColumn<ChiTietHD_DichVu, String> donViTinhCol;
    @FXML
    private TableColumn<ChiTietHD_DichVu, Long> thanhTienDVCol;

    @FXML
    private TableView<ChiTietHD_Phong> tablePhong;
    @FXML
    private TableColumn<ChiTietHD_Phong, String> maPhongCol;
    @FXML
    private TableColumn<ChiTietHD_Phong, String> loaiPhongCol;
    @FXML
    private TableColumn<ChiTietHD_Phong, String> gioVaoCol;
    @FXML
    private TableColumn<ChiTietHD_Phong, String> gioRaCol;
    @FXML
    private TableColumn<ChiTietHD_Phong, String> gioSuDungCol;
    @FXML
    private TableColumn<ChiTietHD_Phong, String> donGiaCol;
    @FXML
    private TableColumn<ChiTietHD_Phong, String> thanhTienCol;

    @FXML
    private TableView<HoaDonThanhToan> tableHoaDon;
    @FXML
    private TableColumn<HoaDonThanhToan, String> maHoaDonCol;
    @FXML
    private TableColumn<HoaDonThanhToan, String> tenKHCol;
    @FXML
    private TableColumn<HoaDonThanhToan, String> sdtCol;
    @FXML
    private TableColumn<HoaDonThanhToan, String> ngayLapCol;

    @FXML
    private TextField txtMaHoaDon;
    @FXML
    private TextField txtSDT;
    @FXML
    private TextField txtTenKH;
    @FXML
    private Button btnTimKiem;
    @FXML
    private Button btnXoaTrang;

    ObservableList<HoaDonThanhToan> danhSach_HoaDon;

}
