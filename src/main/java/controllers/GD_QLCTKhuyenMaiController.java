/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
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
    private TableColumn<CT_KhuyenMai, LocalDateTime> col_ngayBatDau;

    @FXML
    private TableColumn<CT_KhuyenMai, LocalDateTime> col_ngayKetThuc;

    @FXML
    private TableColumn<CT_KhuyenMai, Integer> col_luotSuDungConLai;

    @FXML
    private TableColumn<CT_KhuyenMai, Integer> col_chietKhau;

    ObservableList<CT_KhuyenMai> danhSachCT_KhuyenMai;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        col_sttKhuyenMai.setCellFactory(col -> {
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
        col_maKhuyenMai.setCellValueFactory(new PropertyValueFactory<>("maKhuyenMai"));
        col_tenKhuyenMai.setCellValueFactory(new PropertyValueFactory<>("tenKhuyenMai"));
        col_ngayBatDau.setCellValueFactory(new PropertyValueFactory<>("ngayBatDau"));
        col_ngayKetThuc.setCellValueFactory(new PropertyValueFactory<>("ngayKetThuc"));
        col_luotSuDungConLai.setCellValueFactory(new PropertyValueFactory<>("luotSuDungConLai"));
        col_chietKhau.setCellValueFactory(new PropertyValueFactory<>("chietKhau"));

        danhSachCT_KhuyenMai = CT_KhuyenMai.getListCT_KhuyenMai();
        // cột số thứ tự chưa được gán --> ko lấy dữ liệu lên table được
        tableView_CTKhuyenMai.setItems(danhSachCT_KhuyenMai);
    }
}
