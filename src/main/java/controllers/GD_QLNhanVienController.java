/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controllers;

import enums.Enum_ChucVu;
import enums.Enum_TrangThaiLamViec;
import java.io.File;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.NhanVien;

/**
 * FXML Controller class
 *
 * @author fil
 */
public class GD_QLNhanVienController implements Initializable {

    @FXML
    private TableView<NhanVien> table;

    @FXML
    private ImageView imgNV;
    @FXML
    private TextField txtMaNhanVien;
    @FXML
    private TextField txtCCCD;
    @FXML
    private TextField txtHoTen;
    @FXML
    private DatePicker dateNgaySinh;
    @FXML
    private TextField txtSoDienThoaiNV;
    @FXML
    private TextField txtDiaChi;
    @FXML
    private ComboBox cbbChucVu;

    @FXML
    private TableColumn<NhanVien, String> colMaNV;
    @FXML
    private TableColumn<NhanVien, String> colCCCD;
    @FXML
    private TableColumn<NhanVien, String> colHoTen;
    @FXML
    private TableColumn<NhanVien, String> colNgaySinh;
    @FXML
    private TableColumn<NhanVien, String> colSoDienThoai;
    @FXML
    private TableColumn<NhanVien, String> colDiaChi;
    @FXML
    private TableColumn<NhanVien, String> colChucVu;
    @FXML
    private TableColumn<NhanVien, String> colGioiTinh;
    @FXML
    private ToggleGroup genderGroup;
    @FXML
    private RadioButton radioButtonNam;
    @FXML
    private RadioButton radioButtonNu;

    @FXML
    private Button btnRefresh;
    @FXML
    private Button btnAddNV;
    @FXML
    private Button btnUpdate;

    private String currentMaNhanVien;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        genderGroup = new ToggleGroup();
        radioButtonNam.setToggleGroup(genderGroup);
        radioButtonNu.setToggleGroup(genderGroup);

        dateNgaySinh.valueProperty().addListener((observable, oldValue, newValue) -> {
            try {
                NhanVien nv = table.getSelectionModel().getSelectedItem();
                if (nv == null) {
                    currentMaNhanVien = phatSinhMaNhanVien();
                    txtMaNhanVien.setText(currentMaNhanVien);
                }
            } catch (SQLException ex) {
                Logger.getLogger(GD_QLNhanVienController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        colMaNV.setCellValueFactory(new PropertyValueFactory<>("maNhanVien"));
        colCCCD.setCellValueFactory(new PropertyValueFactory<>("cccd"));
        colHoTen.setCellValueFactory(new PropertyValueFactory<>("hoTen"));
        colNgaySinh.setCellValueFactory(cellData -> {
            LocalDate ngaySinh = cellData.getValue().getNgaySinh();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String ngaySinhFormatted = ngaySinh.format(formatter);
            return new ReadOnlyStringWrapper(ngaySinhFormatted);
        });

        colSoDienThoai.setCellValueFactory(new PropertyValueFactory<>("soDienThoai"));
        colDiaChi.setCellValueFactory(new PropertyValueFactory<>("diaChi"));
        //colChucVu.setCellValueFactory(new PropertyValueFactory<>("chucVu"));
        colChucVu.setCellValueFactory(cellData -> {
            Enum_ChucVu chucVu = cellData.getValue().getChucVu();
            String chucVuString;
            if (chucVu == Enum_ChucVu.BAOVE) {
                chucVuString = "Bảo vệ";
            } else if (chucVu == Enum_ChucVu.NHANVIENPHUCVU) {
                chucVuString = "NV phục vụ";
            } else if (chucVu == Enum_ChucVu.NHANVIENTIEPTAN) {
                chucVuString = "NV tiếp tân";
            } else {
                chucVuString = "Quản lý";
            }
            return new ReadOnlyStringWrapper(chucVuString);
        });
        colGioiTinh.setCellValueFactory(cellData -> {
            boolean gioiTinh = cellData.getValue().isGioiTinh();
            String gioiTinhString;
            if (gioiTinh) {
                gioiTinhString = "Nam";
            } else {
                gioiTinhString = "Nữ";
            }
            return new ReadOnlyStringWrapper(gioiTinhString);

        });

        try {
            table.setItems(NhanVien.getAllNhanVien());
        } catch (Exception ex) {
            Logger.getLogger(GD_QLNhanVienController.class.getName()).log(Level.SEVERE, null, ex);
        }

        table.requestFocus();
        table.getSelectionModel().select(0);
        table.getSelectionModel().focus(0);
        docDuLieuTuTable();
        handleEventInTable();
        handleEventChangesImgNV();

        btnRefresh.setOnAction(this::handleLamMoiButtonAction);
        btnAddNV.setOnAction(this::handleThemNhanVienButtonAction);
        btnUpdate.setOnAction(this::handleCapNhatNhanVienButtonAction);
    }

    public void docDuLieuTuTable() {
        NhanVien nv = table.getSelectionModel().getSelectedItem();
        if (nv == null) {
            return;
        }
        txtMaNhanVien.setText(nv.getMaNhanVien());
        txtCCCD.setText(nv.getCccd());
        txtHoTen.setText(nv.getHoTen());
        dateNgaySinh.setValue(nv.getNgaySinh());
//        cbbChucVu.getItems().clear();
//        cbbChucVu.getItems().addAll(Enum_ChucVu.values());
//        cbbChucVu.setValue(nv.getChucVu());
        cbbChucVu.getItems().clear();
        for (Enum_ChucVu chucVu : Enum_ChucVu.values()) {
            cbbChucVu.getItems().add(chucVuToString(chucVu));
        }
        cbbChucVu.setValue(chucVuToString(nv.getChucVu()));
        txtSoDienThoaiNV.setText(nv.getSoDienThoai());
        txtDiaChi.setText(nv.getDiaChi());
        if (nv.isGioiTinh()) {
            genderGroup.selectToggle(genderGroup.getToggles().get(0));
        } else {
            genderGroup.selectToggle(genderGroup.getToggles().get(1));
        }
        imgNV.setImage(new Image("file:src/main/resources/image/avt_nv/" + nv.getAnhDaiDien()));
    }

    public void handleLamMoiButtonAction(ActionEvent event) {
        try {
            xuLyLamMoiThongTinNhanVien();
        } catch (Exception ex) {
            Logger.getLogger(GD_QLNhanVienController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void handleThemNhanVienButtonAction(ActionEvent event) {
        try {
            if(!xuLyThemNhanVien()){
                showAlert("Thông báo", "Thêm nhân viên không thành công");
            };
        } catch (Exception ex) {
            Logger.getLogger(GD_QLNhanVienController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void handleCapNhatNhanVienButtonAction(ActionEvent event) {
        try {
            if(!xuLySuaThongTinNhanVien()){
                showAlert("Thông báo", "Cập nhật không thành công");
            };
        } catch (Exception ex) {
            Logger.getLogger(GD_QLNhanVienController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void handleEventChangesImgNV() {
        imgNV.setOnMouseClicked((event) -> {
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
                openFileChooser();
            }
        });
    }

    public void openFileChooser() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Chọn ảnh");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Ảnh Files", "*.png", "*.jpg", "*.gif")
        );

        String initialDirectoryPath = "src/main/resources/image/avt_nv/";
        Path initialDirectory = Paths.get(initialDirectoryPath).toAbsolutePath();

        fileChooser.setInitialDirectory(initialDirectory.toFile());
        Stage stage = new Stage();
        File selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
            Image image = new Image(selectedFile.toURI().toString());
            imgNV.setImage(image);
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

    public void xuLyLamMoiThongTinNhanVien() throws Exception {
        txtMaNhanVien.setText("");
        txtCCCD.setText("");
        txtHoTen.setText("");
        dateNgaySinh.setValue(LocalDate.now());
        txtSoDienThoaiNV.setText("");
        txtDiaChi.setText("");
        genderGroup.getToggles().get(0).setSelected(true);
        cbbChucVu.setValue(null);
        imgNV.setImage(new Image("file:src/main/resources/image/employe.png"));
        table.getSelectionModel().clearSelection();
        cbbChucVu.getItems().clear();
        //cbbChucVu.getItems().addAll(Enum_ChucVu.values());
//        cbbChucVu.getItems().clear();
        for (Enum_ChucVu chucVu : Enum_ChucVu.values()) {
            cbbChucVu.getItems().add(chucVuToString(chucVu));
        }
        cbbChucVu.getSelectionModel().selectFirst();
        //cbbChucVu.setValue(chucVuToString(nv.getChucVu()));
    }

    public boolean kiemTraDuLieu() throws Exception {

        if (txtMaNhanVien.getText().equals("")) {
            showAlert("Lỗi nhập dữ liệu", "Mã nhân viên hiện chưa thể tạo!Bạn vui lòng kiểm tra lại ngày sinh");
            dateNgaySinh.requestFocus();
            return false;
        }
        if (!txtCCCD.getText().matches("\\d{12}")) {
            showAlert("Lỗi nhập dữ liệu", "CCCD là một dãy gồm 12 số");
            txtCCCD.selectAll();
            txtCCCD.requestFocus();
            return false;
        }
        if (txtCCCD.getText().equals("")) {
            showAlert("Lỗi nhập dữ liệu", "CCCD nhân viên không được rỗng");
            txtCCCD.selectAll();
            txtCCCD.requestFocus();
            return false;
        }

        if (txtHoTen.getText().equals("")) {
            showAlert("Lỗi nhập dữ liệu", "Họ tên nhân viên không được rỗng");
            txtHoTen.selectAll();
            txtHoTen.requestFocus();
            return false;
        }
        if (!isNameFormatValid(txtHoTen.getText())) {
            showAlert("Lỗi nhập dữ liệu", "Họ tên nhân viên phải in hoa ký tự đầu");
            txtHoTen.selectAll();
            txtHoTen.requestFocus();
            return false;
        }
        if (dateNgaySinh.getValue() == null) {
            showAlert("Lỗi nhập dữ liệu", "Ngày sinh không được rỗng");
            dateNgaySinh.requestFocus();
            return false;
        }

        if ((LocalDate.now().getYear() - dateNgaySinh.getValue().getYear()) < 18) {
            showAlert("Lỗi nhập dữ liệu", "Nhân viên phải từ 18 trở lên");
            dateNgaySinh.requestFocus();
            return false;
        }

        if (txtSoDienThoaiNV.getText().equals("")) {
            showAlert("Lỗi nhập dữ liệu", "Số điện thoại nhân viên không được rỗng");
            txtSoDienThoaiNV.selectAll();
            txtSoDienThoaiNV.requestFocus();
            return false;
        }
        if (!txtSoDienThoaiNV.getText().matches("0[23789]\\d{8}")) {
            showAlert("Lỗi nhập dữ liệu", "Số điện thoại nhân viên là dãy gồm 10 ký số. 2 ký số đầu là {02, 03, 05, 07, 08, 09}");
            txtSoDienThoaiNV.selectAll();
            txtSoDienThoaiNV.requestFocus();
            return false;
        }

        if (txtDiaChi.getText().equals("")) {
            showAlert("Lỗi nhập dữ liệu", "Địa chỉ nhân viên không được rỗng");
            txtDiaChi.selectAll();
            txtDiaChi.requestFocus();
            return false;
        }

        if (cbbChucVu.getValue() == null) {
            showAlert("Lỗi nhập dữ liệu", "Vui lòng chọn chức vụ");
            cbbChucVu.requestFocus();
            return false;
        }

        Image img = new Image("file:src/main/resources/image/employe.png");

        if (imgNV.getImage() != null && imgNV.getImage().getUrl().equals(img.getUrl())) {
            showAlert("Lỗi nhập dữ liệu", "Vui lòng nhấn đúp chuột trái vào ảnh nhân viên minh họa để chọn ảnh!");
            return false;
        }

        return true;
    }

    public boolean xuLyThemNhanVien() throws Exception {
        if (!kiemTraDuLieu()) {
            return false;
        }
        NhanVien nv = null;
//        String maNhanVien = phatSinhMaNhanVien();
        String maNhanVien = currentMaNhanVien;
        String hoTen = txtHoTen.getText();
        String cccd = txtCCCD.getText();
        String soDienThoai = txtSoDienThoaiNV.getText();
        LocalDate ngaySinh = (LocalDate) dateNgaySinh.getValue();
        String diaChi = txtDiaChi.getText();
        boolean gioiTinh = true;
        if (genderGroup.getToggles().get(1).isSelected()) {
            gioiTinh = false;
        }
//        Enum_ChucVu chucVu = (Enum_ChucVu) cbbChucVu.getValue();
        String chucVuString = (String) cbbChucVu.getValue();
        Enum_ChucVu chucVu = (Enum_ChucVu) chucVuToStringDB(chucVuString);
        Enum_TrangThaiLamViec trangThai = Enum_TrangThaiLamViec.CONLAMVIEC;
        //String anhDaiDien = imgNV.getImage().getUrl();
        String anhDaiDienUrl = imgNV.getImage().getUrl();
        File file = new File(anhDaiDienUrl);
        String anhDaiDien = file.getName();

        if (!kiemTraTrungCCCD(cccd)) {
            showAlert("Lỗi nhập dữ liệu", "Mã căn cước công dân không được phép trùng!");
            txtCCCD.selectAll();
            txtCCCD.requestFocus();
            return false;
        }

        if (!kiemTraTrungSDT(soDienThoai)) {
            showAlert("Lỗi nhập dữ liệu", "Số điện thoại không được phép trùng!");
            txtSoDienThoaiNV.selectAll();
            txtSoDienThoaiNV.requestFocus();
            return false;
        }

        nv = new NhanVien(maNhanVien, cccd, hoTen, diaChi, ngaySinh, soDienThoai, chucVu, gioiTinh, anhDaiDien, trangThai);
        NhanVien.themNhanVien(nv);
        table.setItems(NhanVien.getAllNhanVien());
        showAlert("Thông báo", "Thêm nhân viên thành công");
        return true;
    }

    public boolean xuLySuaThongTinNhanVien() throws SQLException, Exception {
        if (!kiemTraDuLieu()) {
            return false;
        }
        String maNhanVien = txtMaNhanVien.getText();
        String hoTen = txtHoTen.getText();
        String cccd = txtCCCD.getText();
        String soDienThoai = txtSoDienThoaiNV.getText();
        LocalDate ngaySinh = (LocalDate) dateNgaySinh.getValue();
        String diaChi = txtDiaChi.getText();
        boolean gioiTinh = true;
        if (genderGroup.getToggles().get(1).isSelected()) {
            gioiTinh = false;
        }

        String chucVuString = (String) cbbChucVu.getValue();
        Enum_ChucVu chucVu = (Enum_ChucVu) chucVuToStringDB(chucVuString);

        Enum_TrangThaiLamViec trangThai = Enum_TrangThaiLamViec.CONLAMVIEC;

//        String anhDaiDien = imgNV.getStyle();
        String anhDaiDienUrl = imgNV.getImage().getUrl();
        File file = new File(anhDaiDienUrl);
        String anhDaiDien = file.getName();
        System.out.println(anhDaiDien);
        NhanVien nv = new NhanVien(maNhanVien, cccd, hoTen, diaChi, ngaySinh, soDienThoai, chucVu, gioiTinh, anhDaiDien, trangThai);
        NhanVien.capNhatThongTinNhanVien(nv);

        table.setItems(NhanVien.getAllNhanVien());
        table.refresh();
        showAlert("Thông báo", "Đã cập nhật");
        return true;
    }

    public String phatSinhMaNhanVien() throws SQLException {
        String maNhanVien = "NV";
        LocalDate ngaySinh = (LocalDate) dateNgaySinh.getValue();
        int namSinh = ngaySinh.getYear();
        int totalEmployees = NhanVien.demSLNhanVien(namSinh);
        DecimalFormat dfSoThuTu = new DecimalFormat("00");
        String soThuTuFormatted = dfSoThuTu.format(totalEmployees + 1);
        String namSinhSuffix = String.valueOf(namSinh).substring(2);
        maNhanVien = maNhanVien.concat(soThuTuFormatted).concat(namSinhSuffix);
        return maNhanVien;
    }

    public boolean kiemTraTrungCCCD(String cc) throws Exception {
        ObservableList<NhanVien> dsNhanVien = NhanVien.getAllNhanVien();
        for (NhanVien NV : dsNhanVien) {
            if (cc.trim().equals(NV.getCccd())) {
                return false;
            }
        }
        return true;
    }

    public boolean kiemTraTrungSDT(String dt) throws Exception {
        ObservableList<NhanVien> dsNhanVien = NhanVien.getAllNhanVien();
        for (NhanVien NV : dsNhanVien) {
            if (dt.equals(NV.getSoDienThoai())) {
                return false;
            }
        }
        return true;
    }

    public boolean kiemTraTrungMaNV(String maNV) throws Exception {
        ObservableList<NhanVien> dsNhanVien = NhanVien.getAllNhanVien();
        for (NhanVien NV : dsNhanVien) {
            if (maNV.equals(NV.getMaNhanVien())) {
                return false;
            }
        }
        return true;
    }

    public String chucVuToString(Enum_ChucVu chucVu) {
        switch (chucVu) {
            case BAOVE:
                return "Bảo vệ";
            case NHANVIENPHUCVU:
                return "Nhân viên phục vụ";
            case NHANVIENTIEPTAN:
                return "Nhân viên tiếp tân";
            case QUANLY:
                return "Quản lý";
            default:
                return "";
        }
    }

    public Enum_ChucVu chucVuToStringDB(String chucVu) {
        switch (chucVu) {
            case "Bảo vệ":
                return Enum_ChucVu.BAOVE;
            case "Nhân viên phục vụ":
                return Enum_ChucVu.NHANVIENPHUCVU;
            case "Nhân viên tiếp tân":
                return Enum_ChucVu.NHANVIENTIEPTAN;
            case "Quản lý":
                return Enum_ChucVu.QUANLY;
            default:
                return null;
        }
    }

    public void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
		alert.getDialogPane().setStyle("-fx-font-family: 'sans-serif';");
        alert.showAndWait();
    }

    public boolean isNameFormatValid(String name) {
        String[] words = name.split("\\s+");
        for (String word : words) {
            if (!word.matches("\\p{Lu}\\p{Ll}*")) {
                return false;
            }
        }
        return true;
    }

}
