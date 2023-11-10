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
        dtDaThemCol.setCellValueFactory(new PropertyValueFactory<>("soLuong"));
        dtThanhTienCol.setCellValueFactory(cellData -> {
            int daThem = cellData.getValue().getSoLuong();
            long donGia = cellData.getValue().getDonGia();
            return new ReadOnlyObjectWrapper<Long>(daThem * donGia);
        });
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
                ImageView imgPlus = new ImageView(new Image("file:src/main/resources/image/circle-plus.png"));
                imgPlus.setFitWidth(25);
                imgPlus.setFitHeight(25);
                Button btn = new Button("", imgPlus);
                btn.getStyleClass().add("btnTable");
                final TableCell<DichVu, String> cell = new TableCell<DichVu, String>() {

                    final Button btnAdd = btn;

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
                                    DichVu dvClone = new DichVu(dv.getMaDichVu(), dv.getTenDichVu(), 1, dv.getDonGia(), dv.getDonViTinh(), dv.getAnhMinhHoa());
                                    if (!dsDichVuDaDat.contains(dvClone)) {
                                        dsDichVuDaDat.add(dvClone);
                                    } else {
                                        DichVu dvProcessing = dsDichVuDaDat.get(dsDichVuDaDat.indexOf(dv));
                                        dvProcessing.setSoLuong(dvProcessing.getSoLuong() + 1);
                                    }
                                    dv.setSoLuong(dv.getSoLuong() - 1);
                                    getTableView().refresh();
                                    tableDichVuDaThem.refresh();
                                    getTableView().requestFocus();
                                    getTableView().requestFocus();
                                    getTableView().getSelectionModel().select(getIndex());
                                    getTableView().getSelectionModel().focus(getIndex());
                                    loadDataFromTableToForm();
                                    
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
                ImageView imgPlus = new ImageView(new Image("file:src/main/resources/image/circle-plus.png"));
                imgPlus.setFitWidth(25);
                imgPlus.setFitHeight(25);
                Button btn = new Button("", imgPlus);
                btn.getStyleClass().add("btnTable");
                
                final TableCell<DichVu, String> cell = new TableCell<DichVu, String>() {

                    final Button btnAdd = btn;

                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                            setText(null);
                        } else {
                            btn.setOnAction(event -> {

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
                ImageView imgMinus = new ImageView(new Image("file:src/main/resources/image/minus.png"));
                imgMinus.setFitWidth(25);
                imgMinus.setFitHeight(25);
                Button btn = new Button("", imgMinus);
                btn.getStyleClass().add("btnTable");
                
                final TableCell<DichVu, String> cell = new TableCell<DichVu, String>() {

                    final Button btnMinus = btn;

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
                ImageView imgRemove = new ImageView(new Image("file:src/main/resources/image/remove.png"));
                imgRemove.setFitWidth(25);
                imgRemove.setFitHeight(25);
                Button btn = new Button("", imgRemove);
                btn.getStyleClass().add("btnTable");
                
                final TableCell<DichVu, String> cell = new TableCell<DichVu, String>() {

                    final Button btnRemove = btn;

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
    private TableColumn<DichVu, Integer> dtDonGiaCol;
    @FXML
    private TableColumn<DichVu, Integer> dtDaThemCol;
    @FXML
    private TableColumn<DichVu, Long> dtThanhTienCol;
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
