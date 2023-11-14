/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import connect.ConnectDB;
import controllers.GD_QLDichVuController;
import controllers.GD_QLKhachHangController;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author fil
 */
public class ChiTietHD_DichVu {

    private HoaDonThanhToan hoaDon;
    private DichVu dichVu;
    private int soLuong;
    private long thanhTien;

    public ChiTietHD_DichVu() {
    }

    public ChiTietHD_DichVu(HoaDonThanhToan hoaDon, DichVu dichVu, int soLuong) {
        setHoaDon(hoaDon);
        setDichVu(dichVu);
        setSoLuong(soLuong);
        this.thanhTien = soLuong * dichVu.getDonGia();
    }

    public ChiTietHD_DichVu(HoaDonThanhToan hoaDon) {
        this.hoaDon = hoaDon;
    }

    public HoaDonThanhToan getHoaDon() {
        return hoaDon;
    }

    public void setHoaDon(HoaDonThanhToan hoaDon) throws IllegalArgumentException {
        if (hoaDon == null) {
            throw new IllegalArgumentException("Hóa đơn không được rỗng");
        } else {
            this.hoaDon = hoaDon;
        }
    }

    public DichVu getDichVu() {
        return dichVu;
    }

    public void setDichVu(DichVu dichVu) throws IllegalArgumentException {
        if (dichVu == null) {
            throw new IllegalArgumentException("Dịch vụ không được rỗng");
        } else {
            this.dichVu = dichVu;
        }
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) throws IllegalArgumentException {
        if (soLuong <= 0) {
            throw new IllegalArgumentException("Số lượng không được rỗng và phải lớn hơn 0");
        } else {
            this.soLuong = soLuong;
        }
    }

    public void tinhThanhTien() {
        thanhTien = soLuong * dichVu.getDonGia();
    }

    public long getThanhTien() {
        return thanhTien;
    }

    @Override
    public String toString() {
        return "ChiTietHD_DichVu{" + "hoaDon=" + hoaDon + ", dichVu=" + dichVu + ", soLuong=" + soLuong + '}';
    }

    // Get Data From DB
    public static ObservableList<ChiTietHD_DichVu> getCTDichVuTheoMaHD(String maHD) throws SQLException, Exception {
        ObservableList<ChiTietHD_DichVu> dsCTDichVu = FXCollections.observableArrayList();
        Connection conn = ConnectDB.getConnection();
        Statement stmt = null;

        try {
            stmt = conn.createStatement();
            String sql = "SELECT * FROM ChiTietHD_DichVu JOIN DichVu ON ChiTietHD_DichVu.MaDichVu = DichVu.MaDichVu WHERE MaHoaDon = " + maHD + "'";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                // Lấy dữ liệu từ ResultSet
                String maHoaDon = rs.getString("MaHoaDon");
                String maDichVu = rs.getString("MaDichVu");
                int soLuong = rs.getInt("SoLuong");
                long thanhTien = rs.getLong("ThanhTien");

                // Lấy thêm dữ liệu từ bảng "DichVu"
                String tenDichVu = rs.getString("TenDichVu");
                String donViTinh = rs.getString("DonViTinh");

                // Tạo đối tượng HoaDonThanhToan và DichVu
                HoaDonThanhToan hoaDon = new HoaDonThanhToan(maHoaDon);
                DichVu dichVu = new DichVu(maDichVu, tenDichVu, donViTinh);

                // Thêm vào danh sách
                dsCTDichVu.add(new ChiTietHD_DichVu(hoaDon, dichVu, soLuong));
            }
        } catch (SQLException ex) {
            Logger.getLogger(GD_QLKhachHangController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(GD_QLDichVuController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(GD_QLKhachHangController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return dsCTDichVu;
    }
}
