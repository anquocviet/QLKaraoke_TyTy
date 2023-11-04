/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controllers;

import connect.ConnectDB;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
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
        radioButtonNam.setToggleGroup(genderGroup);
        radioButtonNu.setToggleGroup(genderGroup);

//        Tạo index column
        sttCol.setCellFactory(col -> {
            TableCell<String, Integer> indexCell = new TableCell<>();
            ReadOnlyObjectProperty<TableRow<String>> rowProperty = indexCell.tableRowProperty();
            ObjectBinding<String> rowBinding = Bindings.createObjectBinding(() -> {
                TableRow<String> row = rowProperty.get();
                if (row != null) {
                    int rowIndex = row.getIndex();
                    if (rowIndex < row.getTableView().getItems().size()) {
                        return Integer.toString(rowIndex + 1);
                    }
                }
                return null;
            }, rowProperty);
            indexCell.textProperty().bind(rowBinding);
            return indexCell;
        });
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
        table.setItems(layTatCaKhachHang());
    }

//    Get data from DB
    public ObservableList<KhachHang> layTatCaKhachHang() {
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
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return dsKhachHang;
    }

//  Render and handle in View'
    public void docDuLieuTuTable(MouseEvent event) {
        KhachHang kh = table.getSelectionModel().getSelectedItem();
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
