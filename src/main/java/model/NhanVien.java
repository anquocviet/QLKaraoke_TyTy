/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import connect.ConnectDB;
import controllers.GD_QLKhachHangController;
import controllers.GD_QLNhanVienController;
import enums.Enum_ChucVu;
import enums.Enum_TrangThaiLamViec;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.Month;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author vie
 */
public class NhanVien {

    private String maNhanVien;
    private String cccd;
    private String hoTen;
    private String diaChi;
    private LocalDate ngaySinh;
    private String soDienThoai;
    private Enum_ChucVu chucVu;
    private boolean gioiTinh;       // true: Nam - false: Nữ
    private String anhDaiDien;
    private Enum_TrangThaiLamViec trangThai;

    public NhanVien() {
    }

    public NhanVien(String maNhanVien, String cccd, String hoTen, String diaChi, LocalDate ngaySinh, String soDienThoai, Enum_ChucVu chucVu, boolean gioiTinh, String anhDaiDien, Enum_TrangThaiLamViec trangThai) throws Exception {
        setMaNhanVien(maNhanVien);
        setCccd(cccd);
        setHoTen(hoTen);
        setDiaChi(diaChi);
        setNgaySinh(ngaySinh);
        setSoDienThoai(soDienThoai);
        setChucVu(chucVu);
        setGioiTinh(gioiTinh);
        setAnhDaiDien(anhDaiDien);
        setTrangThai(trangThai);
    }

    public NhanVien(String maNhanVien) throws Exception {
        setMaNhanVien(maNhanVien);
    }

    public String getMaNhanVien() {
        return maNhanVien;
    }

    public void setMaNhanVien(String maNhanVien) throws Exception {
        if (maNhanVien == null || maNhanVien.trim().equals("")) {
            throw new Exception("Mã nhân viên không được trống");
        } else {
            this.maNhanVien = maNhanVien;
        }
    }

    public String getCccd() {
        return cccd;
    }

    public void setCccd(String cccd) throws Exception {
        if (cccd == null || cccd.trim().equals("")) {
            throw new Exception("Căn cước công dân không được trống");
        } else {
            this.cccd = cccd;
        }
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) throws Exception {
        if (hoTen == null || hoTen.trim().equals("")) {
            throw new Exception("Họ tên không được trống");
        } else {
            this.hoTen = hoTen;
        }
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) throws Exception {
        if (diaChi == null || diaChi.trim().equals("")) {
            throw new Exception("Địa chỉ không được trống");
        } else {
            this.diaChi = diaChi;
        }
    }

    public LocalDate getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(LocalDate ngaySinh) throws Exception {
        if (ngaySinh == null) {
            throw new Exception("Ngày sinh không được trống");
        } else if (LocalDate.now().getYear() - ngaySinh.getYear() < 18) {
            throw new Exception("Nhân viên phải từ 18 tuổi trở lên");
        } else {
            this.ngaySinh = ngaySinh;
        }
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) throws Exception {
        if (soDienThoai == null) {
            throw new Exception("Số điện thoại không được trống");
        } else {
            this.soDienThoai = soDienThoai;
        }
    }

    public Enum_ChucVu getChucVu() {
        return chucVu;
    }

    public void setChucVu(Enum_ChucVu chucVu) throws Exception {
        if (chucVu == null) {
            throw new Exception("Chức vụ không được trống");
        } else {
            this.chucVu = chucVu;
        }
    }

    public boolean isGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(boolean gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getAnhDaiDien() {
        return anhDaiDien;
    }

    public void setAnhDaiDien(String anhDaiDien) throws Exception {
        if (anhDaiDien == null || anhDaiDien.trim().equals("")) {
            throw new Exception("Ảnh đại diện không được trống");
        } else {
            this.anhDaiDien = anhDaiDien;
        }
    }

    public Enum_TrangThaiLamViec getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(Enum_TrangThaiLamViec trangThai) throws Exception {
        if (trangThai == null) {
            throw new Exception("Trạng thái làm việc không được trống");
        } else {
            this.trangThai = trangThai;
        }
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.maNhanVien);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final NhanVien other = (NhanVien) obj;
        return Objects.equals(this.maNhanVien, other.maNhanVien);
    }

    @Override
    public String toString() {
        return "NhanVien{" + "maNhanVien=" + maNhanVien + ", cccd=" + cccd + ", hoTen=" + hoTen + ", diaChi=" + diaChi + ", ngaySinh=" + ngaySinh + ", soDienThoai=" + soDienThoai + ", chucVu=" + chucVu + ", gioiTinh=" + gioiTinh + ", anhDaiDien=" + anhDaiDien + ", trangThai=" + trangThai + '}';
    }

//    Get Data From DB
    public static ObservableList<NhanVien> getAllNhanVien() throws Exception {
        ObservableList<NhanVien> dsNhanVien = FXCollections.observableArrayList();
        Connection conn = ConnectDB.getConnection();
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            String sql = "SELECT * FROM NhanVien";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String maNhanVien = rs.getString("MaNhanVien");
                String hoTen = rs.getString("HoTen");
                String cccd = rs.getString("CCCD");
                String soDienThoai = rs.getString("SoDienThoai");
                Date ns = rs.getDate("NgaySinh");
                LocalDate ngaySinh = ns.toLocalDate();
                String diaChi = rs.getString("DiaChi");
                boolean gioiTinh = rs.getBoolean("GioiTinh");
                String chucVuStr = rs.getString("ChucVu");
                Enum_ChucVu chucVu = Enum_ChucVu.QUANLY;
                if (chucVuStr.equals("Nhân viên tiếp tân")) {
                    chucVu = Enum_ChucVu.NHANVIENTIEPTAN;
                }
                if (chucVuStr.equals("Nhân viên phục vụ")) {
                    chucVu = Enum_ChucVu.NHANVIENPHUCVU;
                }
                if (chucVuStr.equals("Bảo vệ")) {
                    chucVu = Enum_ChucVu.BAOVE;
                }
                String trangThaiLVStr = rs.getString("TrangThai");
                Enum_TrangThaiLamViec trangThaiLV = Enum_TrangThaiLamViec.CONLAMVIEC;
                if (trangThaiLVStr.equals("Đã nghỉ việc")) {
                    trangThaiLV = Enum_TrangThaiLamViec.DANGHI;
                }
                String anhDaiDien = rs.getString("AnhDaiDien");
                dsNhanVien.add(new NhanVien(maNhanVien, cccd, hoTen, diaChi, ngaySinh, soDienThoai, chucVu, gioiTinh, anhDaiDien, trangThaiLV));
            }
        } catch (SQLException ex) {
            Logger.getLogger(GD_QLNhanVienController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                stmt.close();
            } catch (SQLException ex) {
                Logger.getLogger(GD_QLNhanVienController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return dsNhanVien;
    }

    public static NhanVien getNhanVienTheoMaNhanVien(String maNhanVien) {
        Connection conn = ConnectDB.getConnection();
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            String sql = String.format("SELECT * FROM NhanVien WHERE MaNhanVien = '%s'", maNhanVien);
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String hoTen = rs.getString("HoTen");
                String cccd = rs.getString("CCCD");
                String soDienThoai = rs.getString("SoDienThoai");
                java.sql.Date ns = rs.getDate("NgaySinh");
                LocalDate ngaySinh = ns.toLocalDate();
                String diaChi = rs.getString("DiaChi");
                boolean gioiTinh = rs.getBoolean("GioiTinh");
                String chucVuStr = rs.getString("ChucVu");
                Enum_ChucVu chucVu = Enum_ChucVu.QUANLY;
                if (chucVuStr.equals("Nhân viên tiếp tân")) {
                    chucVu = Enum_ChucVu.NHANVIENTIEPTAN;
                }
                if (chucVuStr.equals("Nhân viên phục vụ")) {
                    chucVu = Enum_ChucVu.NHANVIENPHUCVU;
                }
                if (chucVuStr.equals("Bảo vệ")) {
                    chucVu = Enum_ChucVu.BAOVE;
                }
                String trangThaiLVStr = rs.getString("TrangThai");
                Enum_TrangThaiLamViec trangThaiLV = Enum_TrangThaiLamViec.CONLAMVIEC;
                if (trangThaiLVStr.equals("Đã nghỉ việc")) {
                    trangThaiLV = Enum_TrangThaiLamViec.DANGHI;
                }
                String anhDaiDien = rs.getString("AnhDaiDien");
                return new NhanVien(maNhanVien, cccd, hoTen, diaChi, ngaySinh, soDienThoai, chucVu, gioiTinh, anhDaiDien, trangThaiLV);
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

//    public static int demSLNhanVien() throws SQLException {
//        Connection conn = ConnectDB.getInstance().getConnection();
//        Statement stmt = null;
//        try {
//            stmt = conn.createStatement();
//            String sql = "SELECT COUNT(*) FROM NhanVien WHERE YEAR(NgaySinh) = YEAR(GETDATE())";
//            ResultSet rs = stmt.executeQuery(sql);
//            if (rs.next()) {
//                return rs.getInt(1);
//            } else {
//                return 0;
//            }
//        } finally {
//            if (stmt != null) {
//                stmt.close();
//            }
//        }
//    }
	
	
    public static int demSLNhanVien(int ns) throws SQLException {
        Connection conn = ConnectDB.getInstance().getConnection();
        Statement stmt = null;

        try {
            stmt = conn.createStatement();
            String sql = "SELECT YEAR(NgaySinh) AS NamSinh, COUNT(*) AS SoLuongNhanVien "
                    + "FROM NhanVien "
                    + "GROUP BY YEAR(NgaySinh)";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                if (rs.getInt("NamSinh") == ns) {
                    return rs.getInt("SoLuongNhanVien");
                }
            }
            return 0;
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }

    public static boolean themNhanVien(NhanVien nv) {
        ConnectDB.getInstance();
        Connection conn = ConnectDB.getInstance().getConnection();
        PreparedStatement pstm = null;
        int n = 0;

        String sql = "INSERT INTO NhanVien (MaNhanVien, HoTen, CCCD, SoDienThoai, NgaySinh, DiaChi, GioiTinh, ChucVu, TrangThai, AnhDaiDien) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            pstm = conn.prepareStatement(sql);
            pstm.setString(1, nv.getMaNhanVien());
            pstm.setString(2, nv.getHoTen());
            pstm.setString(3, nv.getCccd());
            pstm.setString(4, nv.getSoDienThoai());
            pstm.setDate(5, java.sql.Date.valueOf(nv.getNgaySinh()));
            pstm.setString(6, nv.getDiaChi());
            pstm.setBoolean(7, nv.isGioiTinh());
            pstm.setString(8, nv.getChucVu().name());
            pstm.setString(9, nv.getTrangThai().name());
            pstm.setString(10, nv.getAnhDaiDien());

            n = pstm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(GD_QLNhanVienController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (pstm != null) {
                    pstm.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(GD_QLNhanVienController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return n > 0;
    }

    public static boolean capNhatThongTinNhanVien(NhanVien nv) {
        ConnectDB.getInstance();
        Connection conn = ConnectDB.getInstance().getConnection();
        PreparedStatement pstm = null;
        int n = 0;

        String sql = "UPDATE NhanVien "
                + "SET HoTen = ?, CCCD = ?, SoDienThoai = ?, NgaySinh = ?, DiaChi = ?, GioiTinh = ?, ChucVu = ? , TrangThai = ?, AnhDaiDien = ? "
                + "WHERE MaNhanVien = ?";

        String chucVu = "Quản lý";
        if (nv.getChucVu() == Enum_ChucVu.BAOVE) {
            chucVu = "Bảo vệ";
        } else if (nv.getChucVu() == Enum_ChucVu.NHANVIENPHUCVU) {
            chucVu = "Nhân viên phục vụ";
        } else if (nv.getChucVu() == Enum_ChucVu.NHANVIENTIEPTAN) {
            chucVu = "Nhân viên tiếp tân";
        }

        try {
            pstm = conn.prepareStatement(sql);
            pstm.setString(1, nv.getHoTen());
            pstm.setString(2, nv.getCccd());
            pstm.setString(3, nv.getSoDienThoai());
            pstm.setDate(4, Date.valueOf(nv.getNgaySinh()));
            pstm.setString(5, nv.getDiaChi());
            pstm.setBoolean(6, nv.isGioiTinh());
            pstm.setString(7, chucVu);
            pstm.setString(8, nv.getTrangThai().toString());
            pstm.setString(9, nv.getAnhDaiDien());
            pstm.setString(10, nv.getMaNhanVien());

            n = pstm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(GD_QLNhanVienController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (pstm != null) {
                    pstm.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(GD_QLNhanVienController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return n > 0;
    }

}
