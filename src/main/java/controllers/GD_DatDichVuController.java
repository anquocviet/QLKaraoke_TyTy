/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.util.Callback;
import model.DichVu;
import main.App;

/**
 * FXML Controller class
 *
 * @author vie
 */
public class GD_DatDichVuController implements Initializable {

    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        Table Thong tin Dich vu
        ttMaDichVuCol.setCellValueFactory(new PropertyValueFactory<>("maDichVu"));
        ttTenDichVuCol.setCellValueFactory(new PropertyValueFactory<>("tenDichVu"));
        ttDonGiaCol.setCellValueFactory(new PropertyValueFactory<>("donGia"));
        ttSoLuongConLaiCol.setCellValueFactory(new PropertyValueFactory<>("soLuong"));
        ttDonViTinhCol.setCellValueFactory(new PropertyValueFactory<>("donViTinh"));
        ttThemCol.setCellValueFactory(new PropertyValueFactory<>(""));
        ttThemCol.setCellFactory(handleBtnAddTableThongTin());
        tableThongTinDichVu.setItems(DichVu.getAllDichVu());
        tableThongTinDichVu.requestFocus();
        tableThongTinDichVu.getSelectionModel().select(0);
        tableThongTinDichVu.getSelectionModel().focus(0);
        loadDataFromTableToForm();
        handleEventInTableThongTin();

//        Table Dich vu da them
        dtSttCol.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(tableDichVuDaThem.getItems().indexOf(param.getValue()) + 1));
        dtTenDichVuCol.setCellValueFactory(new PropertyValueFactory<>("tenDichVu"));
        dtDonGiaCol.setCellValueFactory(new PropertyValueFactory<>("donGia"));
        dtDaThemCol.setCellValueFactory(new PropertyValueFactory<>(""));
        dtThanhTienCol.setCellValueFactory(new PropertyValueFactory<>(""));
        dtThemCol.setCellValueFactory(new PropertyValueFactory<>(""));
        dtThemCol.setCellFactory(handleBtnAddTableDatDV());
        dtBotCol.setCellValueFactory(new PropertyValueFactory<>(""));
        dtBotCol.setCellFactory(handleBtnReduceTableDatDV());
        dtXoaCol.setCellValueFactory(new PropertyValueFactory<>(""));
        dtXoaCol.setCellFactory(handleBtnRemoveTableDatDV());
        tableDichVuDaThem.setItems(dsDichVuDaDat);
    }

    public Callback<TableColumn<DichVu, String>, TableCell<DichVu, String>> handleBtnAddTableThongTin() {
        return (new Callback<TableColumn<DichVu, String>, TableCell<DichVu, String>>() {
            @Override
            public TableCell call(final TableColumn<DichVu, String> param) {
                final TableCell<DichVu, String> cell = new TableCell<DichVu, String>() {

                    Button btn = new Button();

                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                            setText(null);
                        } else {
                            btn.setOnAction(event -> {
                                try {
                                    DichVu dv = getTableView().getItems().get(getIndex());
                                    if (!dsDichVuDaDat.contains(dv)) {
                                        dsDichVuDaDat.add(dv);
//                                        dtDaThemCol
//                                        dsDichVuDaDat.get(dsDichVuDaDat.indexOf(dv)).setSoLuong(1);
//                                        dtDaThemCol.setCellFactory(c -> new ReadOnlyObjectWrapper(c.));
                                    } else {
                                        int index = tableDichVuDaThem.getItems().indexOf(dv);
                                        int soLuongCu = dtDaThemCol.getCellData(index);

//                                        long thanhTien = (++soLuongCu) * dv.getDonGia();
//                                        dsDichVuDaDat.get(dsDichVuDaDat.indexOf(dv)).setSoLuong(soLuongCu + 1);
//                                        dsDichVuDaDat.get(dsDichVuDaDat.indexOf(dv)).setDonGia((soLuongCu + 1) * dv.getDonGia());
//                                        dtDonGiaCol.getCellValueFactory() 
//                                        tableDichVuDaThem.refresh();
                                    }
                                    dv.setSoLuong(dv.getSoLuong() - 1);
                                    tableThongTinDichVu.refresh();
                                } catch (Exception ex) {
                                    Alert alert = new Alert(Alert.AlertType.ERROR, "Vui lòng chọn dịch vụ khác", ButtonType.OK);
                                    alert.getDialogPane().setStyle("-fx-font-family: 'sans-serif';");
                                    alert.setTitle("Hết hàng");
                                    alert.setHeaderText("Dịch vụ này đã hết");
                                    alert.showAndWait();
                                }
                            });
                            setGraphic(btn);
                            setText(null);
                        }
                    }
                };
                return cell;
            }
        });
    }

    public Callback<TableColumn<DichVu, String>, TableCell<DichVu, String>> handleBtnAddTableDatDV() {
        return (new Callback<TableColumn<DichVu, String>, TableCell<DichVu, String>>() {
            @Override
            public TableCell call(final TableColumn<DichVu, String> param) {
                final TableCell<DichVu, String> cell = new TableCell<DichVu, String>() {

                    final Button btn = new Button("+");

                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                            setText(null);
                        } else {
                            btn.setOnAction(event -> {
                                DichVu dv = getTableView().getItems().get(getIndex());
                                System.out.println(dv.toString());
                            });
                            setGraphic(btn);
                            setText(null);
                        }
                    }
                };
                return cell;
            }
        });
    }

    public Callback<TableColumn<DichVu, String>, TableCell<DichVu, String>> handleBtnReduceTableDatDV() {
        return (new Callback<TableColumn<DichVu, String>, TableCell<DichVu, String>>() {
            @Override
            public TableCell call(final TableColumn<DichVu, String> param) {
                final TableCell<DichVu, String> cell = new TableCell<DichVu, String>() {

                    final Button btn = new Button("-");

                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                            setText(null);
                        } else {
                            btn.setOnAction(event -> {
                                try {
                                    if (false) {

                                    } else {
                                        DichVu dv = getTableView().getItems().get(getIndex());
                                        getTableView().getItems().remove(dv);
                                        dv.setSoLuong(dv.getSoLuong() + 1);
                                        tableThongTinDichVu.refresh();
                                    }
                                } catch (Exception ex) {
                                    Logger.getLogger(GD_DatDichVuController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            });
                            setGraphic(btn);
                            setText(null);
                        }
                    }
                };
                return cell;
            }
        });
    }

    public Callback<TableColumn<DichVu, String>, TableCell<DichVu, String>> handleBtnRemoveTableDatDV() {
        return (new Callback<TableColumn<DichVu, String>, TableCell<DichVu, String>>() {
            @Override
            public TableCell call(final TableColumn<DichVu, String> param) {
                final TableCell<DichVu, String> cell = new TableCell<DichVu, String>() {

                    final Button btn = new Button("x");

                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                            setText(null);
                        } else {
                            btn.setOnAction(event -> {
                                try {
                                    DichVu dv = getTableView().getItems().get(getIndex());
                                    getTableView().getItems().remove(dv);
                                    dv.setSoLuong(dv.getSoLuong() + 1);
                                    tableThongTinDichVu.refresh();
                                } catch (Exception ex) {
                                    Logger.getLogger(GD_DatDichVuController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            });
                            setGraphic(btn);
                            setText(null);
                        }
                    }
                };
                return cell;
            }
        });
    }

    

    public void handleEventInTableThongTin() {
        tableThongTinDichVu.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                loadDataFromTableToForm();
            }

        });
        tableThongTinDichVu.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.UP || event.getCode() == KeyCode.DOWN) {
                    loadDataFromTableToForm();
                }
            }

        });
    }

    public void loadDataFromTableToForm() {
        DichVu dv = tableThongTinDichVu.getSelectionModel().getSelectedItem();
        txtTenDichVu.setText(dv.getTenDichVu());
        txtGiaDichVu.setText(dv.getDonGia() + "");
        Image img = new Image("file:src/main/resources/image/dich-vu/" + dv.getAnhMinhHoa());

        imageView.setImage(img);
    }
    
    @FXML
    public void handleBtnBack() throws IOException {
        App.setRoot("GD_QLKinhDoanhPhong");
    }

//    Variable
    private ObservableList<DichVu> dsDichVuDaDat = FXCollections.observableArrayList();
    @FXML
    private Button btnBack;
    @FXML
    private ComboBox cbPhong;
    @FXML
    private TextField txtMaKhachHang;
    @FXML
    private TextField txtTenKhachHang;
    @FXML
    private TextField txtMaHoaDon;
    @FXML
    private TextField txtTenDichVu;
    @FXML
    private TextField txtGiaDichVu;
    @FXML
    private ImageView imageView;
    @FXML
    private Button btnLamMoi;
    @FXML
    private TableView<DichVu> tableThongTinDichVu;
    @FXML
    private TableColumn<DichVu, String> ttMaDichVuCol;
    @FXML
    private TableColumn<DichVu, String> ttTenDichVuCol;
    @FXML
    private TableColumn<DichVu, Long> ttDonGiaCol;
    @FXML
    private TableColumn<DichVu, Integer> ttSoLuongConLaiCol;
    @FXML
    private TableColumn<DichVu, String> ttDonViTinhCol;
    @FXML
    private TableColumn ttThemCol;
    @FXML
    private TableView<DichVu> tableDichVuDaThem;
    @FXML
    private TableColumn<Integer, Integer> dtSttCol;
    @FXML
    private TableColumn<DichVu, String> dtTenDichVuCol;
    @FXML
    private TableColumn<DichVu, String> dtDonGiaCol;
    @FXML
    private TableColumn<Integer, Integer> dtDaThemCol;
    @FXML
    private TableColumn<Long, Integer> dtThanhTienCol;
    @FXML
    private TableColumn dtThemCol;
    @FXML
    private TableColumn dtBotCol;
    @FXML
    private TableColumn dtXoaCol;
    @FXML
    private Text lbTongTienl;
    @FXML
    private Button btnDatDichVu;

}
