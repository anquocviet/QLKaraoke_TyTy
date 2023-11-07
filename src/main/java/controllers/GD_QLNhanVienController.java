/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controllers;

import connect.ConnectDB;
import enums.Enum_ChucVu;
import enums.Enum_TrangThaiLamViec;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.Month;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.Initializable;
import javafx.scene.control.ToggleGroup;
import model.NhanVien;


/**
 * FXML Controller class
 *
 * @author fil
 */
public class GD_QLNhanVienController implements Initializable {
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ToggleGroup genderGroup = new ToggleGroup();
    }
    
    public NhanVien getNhanVienTheoMaNhanVien(String maNhanVien) {
        Connection conn = ConnectDB.getInstance().getConnection();
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            String sql = String.format("SELECT * FROM NhanVien WHERE MaNhanVien = '%s'", maNhanVien);
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String hoTen = rs.getString("HoTen");
                String cccd = rs.getString("CCCD");
                String soDienThoai = rs.getString("SoDienThoai");
//                LocalDate ngaySinh = rs.getDate("NgaySinh").toLocalDate();
                LocalDate ngaySinh = LocalDate.of(2000, Month.MARCH, 1);
                String diaChi = rs.getString("DiaChi");
                boolean gioiTinh = rs.getBoolean("GioiTinh");
                String chucVuStr = rs.getString("ChucVu");
                Enum_ChucVu chucVu = Enum_ChucVu.QUANLY;
                if (chucVuStr.equals("Nhân viên tiếp tân")) chucVu = Enum_ChucVu.NHANVIENTIEPTAN;
                if (chucVuStr.equals("Nhân viên phục vụ")) chucVu = Enum_ChucVu.NHANVIENPHUCVU;
                if (chucVuStr.equals("Bảo vệ")) chucVu = Enum_ChucVu.BAOVE;
                String trangThaiLVStr = rs.getString("TrangThai");
                Enum_TrangThaiLamViec trangThaiLV = Enum_TrangThaiLamViec.CONLAMVIEC;
                if (trangThaiLVStr.equals("Đã nghỉ việc")) trangThaiLV = Enum_TrangThaiLamViec.DANGHI;
                return new NhanVien(maNhanVien, cccd, hoTen, diaChi, ngaySinh, soDienThoai, chucVu, gioiTinh, maNhanVien, trangThaiLV);
            }
        } catch (SQLException ex) {
            Logger.getLogger(GD_QLKhachHangController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(GD_QLNhanVienController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                stmt.close();
            } catch (SQLException ex) {
                Logger.getLogger(GD_QLKhachHangController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

}
