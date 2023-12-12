/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controllers;

import java.io.File;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
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
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.DichVu;

/**
 * FXML Controller class
 *
 * @author fil
 */
public class GD_QLDichVuController implements Initializable {

    private String tenAnhMinhHoa;

    /**
     *
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        col_maDichVu.setCellValueFactory(new PropertyValueFactory<>("maDichVu"));
        col_tenDichVu.setCellValueFactory(new PropertyValueFactory<>("tenDichVu"));
        col_soLuong.setCellValueFactory(new PropertyValueFactory<>("soLuong"));
        col_donGia.setCellValueFactory(new PropertyValueFactory<>("donGia"));
        col_donGia.setCellFactory(column -> {
            return new TableCell<DichVu, Long>() {
                @Override
                protected void updateItem(Long donGia, boolean empty) {
                    super.updateItem(donGia, empty);
                    if (donGia == null || empty) {
                        setText(null);
                    } else {
                        setText(String.format("%,d VND", donGia));
                    }
                }
            };
        });

        col_donViTinh.setCellValueFactory(new PropertyValueFactory<>("donViTinh"));
        try {
            tableView_DichVu.setItems(DichVu.getAllDichVu());
        } catch (Exception ex) {
            Logger.getLogger(GD_QLDichVuController.class.getName()).log(Level.SEVERE, null, ex);
        }
        danhSach_DichVu = DichVu.getAllDichVu();
        tableView_DichVu.setItems(danhSach_DichVu);
		tableView_DichVu.getSelectionModel().select(0);
        tableView_DichVu.requestFocus();
        docDuLieuTuTable();
        handleEventInTable();
		
        btnThemDichVu.setOnAction(this::handleThemDichVuButtonAction);
        btnSuaDichVu.setOnAction(this::handleCapNhatDichVuButtonAction);
        btnXoaTrangDichVu.setOnAction(this::handleLamMoiButtonAction);

    }

    public void handleEventInTable() {
        tableView_DichVu.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                docDuLieuTuTable();
            }

        });
        tableView_DichVu.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.UP || event.getCode() == KeyCode.DOWN) {
                    docDuLieuTuTable();
                }
            }

        });
    }

    public void handleLamMoiButtonAction(ActionEvent event) {
        try {
            xuLyLamMoiThongTinDichVu();
        } catch (Exception ex) {
            Logger.getLogger(GD_QLDichVuController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void handleThemDichVuButtonAction(ActionEvent event) {
        try {
            xuLyThemDichVu();
        } catch (Exception ex) {
            Logger.getLogger(GD_QLDichVuController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void handleCapNhatDichVuButtonAction(ActionEvent event) {
        try {
            xuLySuaThongTinDichVu();
        } catch (Exception ex) {
            Logger.getLogger(GD_QLDichVuController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void docDuLieuTuTable() {
        DichVu dv = tableView_DichVu.getSelectionModel().getSelectedItem();
        if (dv == null) {
            return;
        }
        txtMaDichVu.setText(dv.getMaDichVu());
        txtTenDichVu.setText(dv.getTenDichVu());
        txtSoLuong.setText(String.valueOf(dv.getSoLuong()));
        txtDonGia.setText(String.valueOf(dv.getDonGia()));
        txtDonViTinh.setText(dv.getDonViTinh());
        imgDichVu.setImage(new Image("file:src/main/resources/image/dich-vu/" + dv.getAnhMinhHoa()));
        String imagePath = "file:src/main/resources/image/dich-vu/" + dv.getAnhMinhHoa();
        tenAnhMinhHoa = dv.getAnhMinhHoa();
    }

    private boolean kiemTraRong() throws Exception {
        if (txtTenDichVu.getText().equals("")) {
            showAlert("Tên dịch vụ không được rỗng", AlertType.ERROR);
            txtTenDichVu.selectAll();
            txtTenDichVu.requestFocus();
            return false;
        }

        Integer tmp1 = Integer.parseInt(txtSoLuong.getText());
        if (txtSoLuong.getText().equals("") || tmp1 < 0) {
            showAlert("Số lượng không được rỗng và phải lớn hơn 0", AlertType.ERROR);
            txtSoLuong.selectAll();
            txtSoLuong.requestFocus();
            return false;
        }

        String donViTinh = txtDonViTinh.getText().trim();
        if (donViTinh.isEmpty() || !kiemTraDinhDangDonViTinh(donViTinh)) {
            showAlert("Đơn vị tính của dịch vụ không được rỗng và phải đúng định dạng", AlertType.ERROR);
            txtDonViTinh.selectAll();
            txtDonViTinh.requestFocus();
            return false;
        }

        long tmp2 = Long.parseLong(txtDonGia.getText());
        if (txtDonGia.getText().equals("") || tmp2 < 0) {
            showAlert("Đơn giá của dịch vụ  không được rỗng và phải lớn hơn 0", AlertType.ERROR);
            txtDonGia.selectAll();
            txtDonGia.requestFocus();
            return false;
        }

        return true;
    }

    private boolean kiemTraDinhDangDonViTinh(String donViTinh) {
        // Danh sách các đơn vị tính cho phép
        String[] allowedUnits = {"Dĩa", "Thùng", "Lon", "Chai", "Bịch", "Gói", "Trái", "Con"};

        // Kiểm tra xem đơn vị tính có trong danh sách cho phép hay không
        for (String allowedUnit : allowedUnits) {
            if (allowedUnit.equalsIgnoreCase(donViTinh)) {
                return true;
            }
        }

        return false;
    }

    public String phatSinhMaDichVu() throws SQLException {
        String maDichVu = "DV";
        int totalDichVu = DichVu.demSLDichVu();
        DecimalFormat dfSoThuTu = new DecimalFormat("000");
        String soThuTuFormatted = dfSoThuTu.format(totalDichVu + 1);
        maDichVu = maDichVu.concat(soThuTuFormatted);
        return maDichVu;
    }

    public void xuLyThemDichVu() throws Exception {
        if (!kiemTraRong()) {
            return;
        }
        DichVu dv = null;
        String maDichVu = phatSinhMaDichVu();
        String tenDichVu = txtTenDichVu.getText();
        int soLuongTon = Integer.parseInt(txtSoLuong.getText());
        String donViTinh = txtDonViTinh.getText();
        long donGia = Long.parseLong(txtDonGia.getText());
        String anhMinhHoa = tenAnhMinhHoa;

        if (!kiemTraTrungDichVu(tenDichVu, donViTinh)) {
            showAlert("Dịch vụ này đã có trên hệ thống!", AlertType.ERROR);
            txtTenDichVu.selectAll();
            txtTenDichVu.requestFocus();
            return;
        }

        dv = new DichVu(maDichVu, tenDichVu, soLuongTon, donGia, donViTinh, anhMinhHoa);
        DichVu.themDichVu(dv);
        tableView_DichVu.setItems(DichVu.getAllDichVu());

        showAlert("Thêm thông tin dịch vụ thành công", AlertType.NONE);
    }

    public boolean kiemTraTrungDichVu(String tenDichVu, String donViTinh) throws Exception {
        ObservableList<DichVu> dsDichVu = DichVu.getAllDichVu();
        for (DichVu dv : dsDichVu) {
            if (tenDichVu.trim().equals(dv.getTenDichVu()) && donViTinh.trim().equals(dv.getDonViTinh())) {
                return false;
            }
        }
        return true;
    }

    public void xuLyLamMoiThongTinDichVu() {
        txtMaDichVu.setText("");
        txtTenDichVu.setText("");
        txtSoLuong.setText("");
        txtDonGia.setText("");
        txtDonViTinh.setText("");
        tableView_DichVu.getSelectionModel().clearSelection();
    }

    public void xuLySuaThongTinDichVu() throws SQLException, Exception {
        if (!kiemTraRong()) {
            return;
        }
        String maDichVu = txtMaDichVu.getText();
        String tenDV = txtTenDichVu.getText();
        int soLuong = Integer.parseInt(txtSoLuong.getText());
        long donGia = Long.parseLong(txtDonGia.getText());
        String donViTinh = txtDonViTinh.getText();

        String anhMinhHoa = tenAnhMinhHoa;
        DichVu dv = new DichVu(maDichVu, tenDV, soLuong, donGia, donViTinh, anhMinhHoa);
        DichVu.capNhatThongTinDichVu(dv);

        tableView_DichVu.setItems(DichVu.getAllDichVu());
        tableView_DichVu.refresh();

        showAlert("Cập nhật thông tin dịch vụ thành công", AlertType.INFORMATION);

    }

    @FXML
    private TextField txtMaDichVu;
    @FXML
    private TextField txtTenDichVu;
    @FXML
    private TextField txtSoLuong;
    @FXML
    private TextField txtDonGia;
    @FXML
    private TextField txtDonViTinh;

    @FXML
    private Button btnChonAnh;

    @FXML
    private void chonAnh(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Ảnh Files", "*.png", "*.jpg", "*.gif")
        );

        String initialDirectoryPath = "src/main/resources/image/dich-vu/";
        Path initialDirectory = Paths.get(initialDirectoryPath).toAbsolutePath();

        fileChooser.setInitialDirectory(initialDirectory.toFile());

        try {
            Stage stage = new Stage();
            File selectedFile = fileChooser.showOpenDialog(stage);

            if (selectedFile != null) {
                String imagePath = "file:///" + selectedFile.getAbsolutePath().replace("\\", "/");
                Image image = new Image(imagePath);
                imgDichVu.setImage(image);
                tenAnhMinhHoa = selectedFile.getName();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Phương thức showAlert
    private void showAlert(String message, AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle("Thông báo");
        alert.setHeaderText(null);
        alert.setContentText(message);
		alert.getDialogPane().setStyle("-fx-font-family: 'sans-serif';");
        ButtonType buttonTypeOK = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        alert.getButtonTypes().setAll(buttonTypeOK);

        // Đợi người dùng nhấn OK
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == buttonTypeOK) {
            // Người dùng đã nhấn OK, bạn có thể thực hiện các hành động sau khi đóng Alert ở đây
            // Ví dụ: stage.close(); // Đóng cửa sổ chứa Alert
        }
    }

    @FXML
    private ImageView imgDichVu;
    @FXML
    private Button btnThemDichVu;
    @FXML
    private Button btnSuaDichVu;
    @FXML
    private Button btnXoaTrangDichVu;

    @FXML
    private TableView<DichVu> tableView_DichVu;
    @FXML
    private TableColumn<DichVu, String> col_maDichVu;
    @FXML
    private TableColumn<DichVu, String> col_tenDichVu;
    @FXML
    private TableColumn<DichVu, Integer> col_soLuong;
    @FXML
    private TableColumn<DichVu, Long> col_donGia;
    @FXML
    private TableColumn<DichVu, String> col_donViTinh;

    ObservableList<DichVu> danhSach_DichVu;

}
