/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controllers;

import entities.CT_KhuyenMai;
import entities.ChiTietHD_DichVu;
import entities.ChiTietHD_Phong;
import entities.HoaDonThanhToan;
import entities.Phong;
import enums.Enum_LoaiPhong;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.skin.TableViewSkin;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import lombok.SneakyThrows;
import main.App;
import socket.ClientSocket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

/**
 * FXML Controller class
 *
 * @author fil
 */
public class GD_ThanhToanController implements Initializable {
   private long tongTien;
   public static long tienNhan = 0;
   public static long tienGiam = 0;
   DecimalFormat df = new DecimalFormat("#,###,###,##0.##");

   @FXML
   private Button btnBack;
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
   private TextField txtMaKhuyenMai;
   @FXML
   private TextField txtTienNhan;
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
   private Button btnThanhToan;
   @FXML
   private Text txtMaHoaDon;
   @FXML
   private Text txtNhanVien;
   @FXML
   private Text txtKhachHang;
   @FXML
   private Text txtNgayLap;
   @FXML
   private Text txtTienPhong;
   @FXML
   private Text txtTienDichVu;
   @FXML
   private Text txtTienThue;
   @FXML
   private Text txtTongTien;
   @FXML
   private Text txtTienDaGiam;
   @FXML
   private Text txtTienThua;
   @FXML
   private ImageView imgCheckKM;
   @FXML
   private CheckBox checkBoxInHD;

   DataInputStream dis = ClientSocket.getDis();
   DataOutputStream dos = ClientSocket.getDos();
   ObjectInputStream in = ClientSocket.getIn();
   ObjectOutputStream out = ClientSocket.getOut();

   @SneakyThrows
   @Override
   public void initialize(URL location, ResourceBundle resources) {
      dos.writeUTF(("bill-find-bill-by-room-id," + GD_QLKinhDoanhPhongController.roomID));
      HoaDonThanhToan hd = (HoaDonThanhToan) in.readObject();
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

         dos.writeUTF("serviceDetail-find-by-bill-id," + hd.getMaHoaDon());
         tableDichVu.setItems(FXCollections.observableArrayList((List<ChiTietHD_DichVu>) in.readObject()));
         tableDichVu.setSkin(new TableViewSkin(tableDichVu) {
            @Override
            public int getItemCount() {
               int r = super.getItemCount();
               return r == 0 ? 1 : r;
            }
         });
      } catch (Exception ex) {
         Logger.getLogger(GD_ThanhToanController.class.getName()).log(Level.SEVERE, null, ex);
      }

      try {
         maPhongCol.setCellValueFactory((param) -> {
            String maHoaDon = param.getValue().getPhong().getMaPhong();
            return new ReadOnlyObjectWrapper<>(maHoaDon);
         });
         loaiPhongCol.setCellValueFactory((param) -> {
            String loaiPhong = param.getValue().getPhong().getLoaiPhong() == Enum_LoaiPhong.THUONG ? "Thường" : "VIP";
            return new ReadOnlyObjectWrapper<>(loaiPhong);
         });
         gioVaoCol.setCellValueFactory((param) -> {
            String gioVao = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm").format(param.getValue().getGioVao().atZone(ZoneId.systemDefault()).toLocalDateTime());
            return new ReadOnlyObjectWrapper<>(gioVao);
         });
         gioRaCol.setCellValueFactory((param) -> {
            String gioRa = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm").format(param.getValue().getGioRa().atZone(ZoneId.systemDefault()).toLocalDateTime());
            return new ReadOnlyObjectWrapper<>(gioRa);
         });
         gioSuDungCol.setCellValueFactory((param) -> {
            float time = ((float) Duration.between(param.getValue().getGioVao(), param.getValue().getGioRa()).toMillis()) / 1000 / 3600;
            String gioSuDung = df.format(time);
            return new ReadOnlyObjectWrapper<>(gioSuDung);
         });
         donGiaCol.setCellValueFactory((param) -> {
            long donGia = param.getValue().getPhong().getGiaPhong();
            Instant gioVao = param.getValue().getGioVao();
            if (gioVao.atZone(ZoneId.systemDefault()).toLocalTime().isAfter(LocalTime.of(18, 0, 0))) {
               donGia += App.TIENPHONGTHEMDEM;
            }
            return new ReadOnlyObjectWrapper<>(df.format(donGia));
         });
         thanhTienCol.setCellValueFactory((param) -> new ReadOnlyObjectWrapper<>(
               df.format(tinhThanhTien(param.getValue().getGioVao(), param.getValue().getGioRa(), param.getValue().getPhong())))
         );

         dos.writeUTF("roomDetail-find-by-bill-id," + hd.getMaHoaDon());
         ObservableList<ChiTietHD_Phong> dsPhong = FXCollections.observableArrayList((List<ChiTietHD_Phong>) in.readObject());
         dsPhong.forEach(ct -> {
            try {
               ct.setGioRa(Instant.now());
            } catch (Exception ex) {
               Logger.getLogger(GD_ThanhToanController.class.getName()).log(Level.SEVERE, null, ex);
            }
         });
         tablePhong.setItems(dsPhong);
      } catch (Exception ex) {
         Logger.getLogger(GD_ThanhToanController.class.getName()).log(Level.SEVERE, null, ex);
      }

      txtMaHoaDon.setText(hd.getMaHoaDon());
      txtNhanVien.setText(hd.getNhanVien().getMaNhanVien());
      txtKhachHang.setText(hd.getKhachHang().getTenKhachHang());
      txtNgayLap.setText(DateTimeFormatter.ofPattern("dd/MM/yyyy").format(hd.getNgayLap().atZone(ZoneId.systemDefault()).toLocalDateTime()));
      long tienDV = 0;
      long tienPhong = 0;
      for (ChiTietHD_DichVu ct : tableDichVu.getItems()) {
         tienDV += ct.getThanhTien();
      }
      for (ChiTietHD_Phong ct : tablePhong.getItems()) {
         tienPhong += tinhThanhTien(ct.getGioVao(), ct.getGioRa(), ct.getPhong());
      }
      txtTienDichVu.setText(df.format(tienDV) + " VNĐ");
      txtTienPhong.setText(df.format(tienPhong) + " VNĐ");

      tongTien = tienPhong + tienDV;
      long tienVAT = (long) (tongTien * (App.VAT / 100.0));
//		long tienThueTTDB = (long) (tongTien - (tongTien / (1 + App.TTDB / 100.0)));
//		tongTien = tongTien + tienVAT + tienThueTTDB;
//		txtTienThue.setText(df.format(tienVAT + tienThueTTDB) + " VNĐ");
      tongTien = tongTien + tienVAT;
      txtTienThue.setText(df.format(tienVAT) + " VNĐ");
      txtTongTien.setText(df.format(tongTien) + " VNĐ");
      handleEventInInput();
      handleEventInBtn();
   }

   public void handleEventInInput() {
      txtTienNhan.setOnKeyReleased(evt -> {
         if (txtTienNhan.getText().trim().isEmpty()) {
            return;
         }
         if (!Pattern.matches("[\\d,]*", txtTienNhan.getText().trim())) {
            txtTienNhan.setText(txtTienNhan.getText().trim().replaceAll("[^\\d,]", ""));
            txtTienNhan.positionCaret(txtTienNhan.getText().length());
         }
         try {
            tienNhan = Long.parseLong(txtTienNhan.getText().trim());
         } catch (NumberFormatException nf) {
            try {
               tienNhan = df.parse(txtTienNhan.getText().trim()).longValue();
            } catch (ParseException ex) {
               Logger.getLogger(GD_ThanhToanController.class.getName()).log(Level.SEVERE, null, ex);
            }
         }
         long tienThua = tienNhan - tongTien;
         if (tienThua > 0) {
            btnThanhToan.setDisable(false);
            txtTienThua.setText(df.format(tienThua) + " VND");
         } else {
            btnThanhToan.setDisable(true);
            txtTienThua.setText("0 VND");
         }
      });
      txtMaKhuyenMai.setOnKeyReleased((evt) -> {
         try {
            dos.writeUTF("voucher-find-voucher," + txtMaKhuyenMai.getText().trim().toUpperCase());
            List<CT_KhuyenMai> listCTKM = (List<CT_KhuyenMai>) in.readObject();
            CT_KhuyenMai ctkm = null;
            if (listCTKM.size() == 1) {
               ctkm = listCTKM.getFirst();
            }
            long tienDV = 0;
            long tienPhong = 0;
            for (ChiTietHD_DichVu ct : tableDichVu.getItems()) {
               tienDV += ct.getThanhTien();
            }
            for (ChiTietHD_Phong ct : tablePhong.getItems()) {
               tienPhong += tinhThanhTien(ct.getGioVao(), ct.getGioRa(), ct.getPhong());
            }
            long tong = tienPhong + tienDV;
            long tienVAT = (long) (tong * (App.VAT / 100.0));
            tong += tienVAT;
            tongTien = tong;
            if (checkUseVoucher(ctkm)) {
               tienGiam = tong * ctkm.getChietKhau() / 100;
               txtTienDaGiam.setText(df.format(tienGiam) + " VND");
               imgCheckKM.setImage(new Image("file:src/main/resources/image/check.png"));
               tongTien = tong - tienGiam;
            } else {
               imgCheckKM.setImage(new Image("file:src/main/resources/image/check_false.png"));
               txtTienDaGiam.setText(0 + " VND");
            }
            txtTongTien.setText(df.format(tongTien) + " VND");
         } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
         }
      });
   }

   public void handleEventInBtn() {
      btnThanhToan.setOnAction(evt -> {
         try {
            if (txtTienThua.getText().equals("0")) {
               Alert alert = new Alert(Alert.AlertType.ERROR, "Vui lòng kiểm tra lại tiền nhận!", ButtonType.OK);
               alert.getDialogPane().setStyle("-fx-font-family: 'sans-serif';");
               alert.setTitle("Lỗi");
               alert.setHeaderText("Tiền nhận không phù hợp");
               alert.showAndWait();
               return;
            }
            dos.writeUTF("voucher-find-by-id," + txtMaKhuyenMai.getText().trim().toUpperCase());
            CT_KhuyenMai km = (CT_KhuyenMai) in.readObject();
            if (checkUseVoucher(km)) {
               dos.writeUTF("voucher-update-voucher," + km.getMaKhuyenMai());
               km.setLuotSuDungConLai(km.getLuotSuDungConLai() - 1);
               out.writeObject(km);
               if (!dis.readBoolean()) {
                  logAlertError();
                  return;
               }
            } else {
               km = new CT_KhuyenMai();
               km.setMaKhuyenMai("DEFAULT");
            }
            for (ChiTietHD_Phong ct : tablePhong.getItems()) {
               dos.writeUTF("roomDetail-update-roomDetail");
               out.writeObject(ct);
               if (!dis.readBoolean()) {
                  logAlertError();
                  return;
               }
               ct.getPhong().setTinhTrang(2);
               dos.writeUTF("room-update-room");
               out.writeObject(ct.getPhong());
               if (!dis.readBoolean()) {
                  logAlertError();
                  return;
               }
            }

            dos.writeUTF("bill-find-bill-by-room-id," + GD_QLKinhDoanhPhongController.roomID);
            HoaDonThanhToan hd = (HoaDonThanhToan) in.readObject();
            hd.setKhuyenMai(km);
            hd.setNgayLap(Instant.now());
            hd.setTongTien((int) tongTien);
            dos.writeUTF("bill-update-bill");
            out.writeObject(hd);
            if (!dis.readBoolean()) {
               logAlertError();
               return;
            }

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.OK);
            alert.getDialogPane().setStyle("-fx-font-family: 'sans-serif';");
            alert.setTitle("Thanh toán phòng thành công");
            alert.setHeaderText("Bạn đã thanh toán phòng thành công!");
            alert.showAndWait();

//				Xuat hoa don
            if (checkBoxInHD.isSelected()) {
               App.openModal("Bill", App.widthModalBill, App.heightModalBill);
            }

            App.setRoot("GD_QLKinhDoanhPhong");
         } catch (IOException | IllegalArgumentException | ClassNotFoundException ex) {
            Logger.getLogger(GD_ThanhToanController.class.getName()).log(Level.SEVERE, null, ex);
            logAlertError();
         }
      });
      btnBack.setOnAction(evt -> {
         try {
            App.setRoot("GD_QLKinhDoanhPhong");
         } catch (IOException ex) {
            Logger.getLogger(GD_DatDichVuController.class.getName()).log(Level.SEVERE, null, ex);
         }
      });
   }

   private void logAlertError() {
      Alert alert = new Alert(Alert.AlertType.ERROR, "Thanh toán thất bại!", ButtonType.OK);
      alert.getDialogPane().setStyle("-fx-font-family: 'sans-serif';");
      alert.setTitle("Lỗi");
      alert.setHeaderText("Thanh toán thất bại");
      alert.showAndWait();
   }

   public boolean checkUseVoucher(CT_KhuyenMai km) {
      return km != null && km.getLuotSuDungConLai() > 0 && km.getNgayKetThuc().isAfter(Instant.now());
   }

   public long tinhThanhTien(Instant gioVao, Instant gioRa, Phong phong) {
      long thanhTien;
      float time = ((float) Duration.between(gioVao, gioRa).toMillis()) / 1000 / 3600;
      if (LocalDateTime.ofInstant(gioRa, ZoneId.systemDefault()).toLocalTime().isAfter(LocalTime.of(18, 0, 0))) {
         thanhTien = (long) (time * (phong.getGiaPhong() + App.TIENPHONGTHEMDEM));
      } else {
         thanhTien = (long) (time * phong.getGiaPhong());
      }
      return thanhTien;
   }
}
