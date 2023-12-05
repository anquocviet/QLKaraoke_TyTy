/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import connect.ConnectDB;
import controllers.GD_DangNhapController;
import controllers.GD_QLKhachHangController;
import enums.Enum_ChucVu;
import enums.Enum_TrangThaiLamViec;

import java.sql.Connection;
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
 * @author vie
 */
public final class TaiKhoan {

    private String maTaiKhoan;
    private String tenDangNhap;
    private String matKhau;
    private NhanVien nhanVien;

    public TaiKhoan() {
    }

    public TaiKhoan(String maTaiKhoan, String tenDangNhap, String matKhau, NhanVien nhanVien) {
		setMaTaiKhoan(maTaiKhoan);
		setTenDangNhap(tenDangNhap);
		setMatKhau(matKhau);
		setNhanVien(nhanVien);
    }

    public TaiKhoan(String maTaiKhoan, String tenDangNhap, String matKhau) {
		setMaTaiKhoan(maTaiKhoan);
		setTenDangNhap(tenDangNhap);
		setMatKhau(matKhau);

    }

    public TaiKhoan(String maTaiKhoan) {
        this.maTaiKhoan = maTaiKhoan;
    }




    public String getMaTaiKhoan() {
        return maTaiKhoan;
    }

    public void setMaTaiKhoan(String maTaiKhoan) {
        this.maTaiKhoan = maTaiKhoan;
    }

    public String getTenDangNhap() {
        return tenDangNhap;
    }

    public void setTenDangNhap(String tenDangNhap) {
        this.tenDangNhap = tenDangNhap;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public NhanVien getNhanVien() {
        return nhanVien;
    }

    public void setNhanVien(NhanVien nhanVien) {
        this.nhanVien = nhanVien;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + Objects.hashCode(this.maTaiKhoan);
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
        final TaiKhoan other = (TaiKhoan) obj;
        return Objects.equals(this.maTaiKhoan, other.maTaiKhoan);
    }

    @Override
    public String toString() {
        return "TaiKhoan{" + "maTaiKhoan=" + maTaiKhoan + ", tenDangNhap=" + tenDangNhap + ", matKhau=" + matKhau + ", nhanVien=" + nhanVien + '}';
    }

    //    Get data from DB
    public static ObservableList<TaiKhoan> getAllTaiKhoan() {
        ObservableList<TaiKhoan> dsTaiKhoan = FXCollections.observableArrayList();
        Connection conn = ConnectDB.getInstance().getConnection();
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            String sql = "SELECT * FROM TaiKhoan";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String maTaiKhoan = rs.getString("MaTaiKhoan");
                String tenDangNhap = rs.getString("TenDangNhap");
                String matKhau = rs.getString("MatKhau");
                String maNhanVien = rs.getString("MaNhanVien");
                dsTaiKhoan.add(new TaiKhoan(maTaiKhoan, tenDangNhap, matKhau, new NhanVien(maNhanVien)));
            }
        } catch (SQLException ex) {
            Logger.getLogger(GD_QLKhachHangController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(GD_DangNhapController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                stmt.close();
            } catch (SQLException ex) {
                Logger.getLogger(GD_QLKhachHangController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return dsTaiKhoan;
    }

    public static int demSoLuongTaiKhoan(){
        int soLuong = 0;
        Connection conn = ConnectDB.getInstance().getConnection();
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            String sql = "SELECT COUNT(*) AS SoLuong FROM TaiKhoan";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                soLuong = rs.getInt("SoLuong");
            }
        } catch (SQLException ex) {
            Logger.getLogger(GD_QLKhachHangController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(GD_DangNhapController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return soLuong+1;
    }

    public static TaiKhoan getTaiKhoanTheoUserNameAndPassword(String tenDangNhap, String matKhau) {
        Connection conn = ConnectDB.getInstance().getConnection();
        Statement stmt = null;
        TaiKhoan tk = null;
        try {
            stmt = conn.createStatement();
            String sql = String.format("SELECT * FROM TaiKhoan WHERE TenDangNhap = '%s' AND MatKhau = '%s'", tenDangNhap, matKhau);
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String maTaiKhoan = rs.getString("MaTaiKhoan");
                String maNhanVien = rs.getString("MaNhanVien");
                tk = new TaiKhoan(maTaiKhoan, tenDangNhap, matKhau, new NhanVien(maNhanVien));
            }
        } catch (SQLException ex) {
            Logger.getLogger(GD_QLKhachHangController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(GD_DangNhapController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                assert stmt != null;
                stmt.close();
            } catch (SQLException ex) {
                Logger.getLogger(GD_QLKhachHangController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return tk;
    }

    public static TaiKhoan save(TaiKhoan tk) {
        Connection conn = ConnectDB.getInstance().getConnection();
        Statement stmt = null;

        try {
            stmt = conn.createStatement();

            String insertQuery = String.format("INSERT INTO TaiKhoan (MaTaiKhoan, TenDangNhap, MatKhau, MaNhanVien) VALUES ('%s', '%s', '%s', '%s')",
                    "TK00"+demSoLuongTaiKhoan(), tk.getTenDangNhap(), tk.getMatKhau(), tk.getNhanVien().getMaNhanVien());
            stmt.executeUpdate(insertQuery);


            System.out.println("Thêm tài khoản thành công!");
        } catch (SQLException ex) {
            Logger.getLogger(TaiKhoan.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }

            } catch (SQLException ex) {
                Logger.getLogger(TaiKhoan.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return tk;
    }

    public static ObservableList<TaiKhoan> getAllTaiKhoanFull() {
        ObservableList<TaiKhoan> dsTaiKhoan = FXCollections.observableArrayList();
        Connection conn = ConnectDB.getInstance().getConnection();
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            String sql = "SELECT * FROM TaiKhoan  JOIN NhanVien ON TaiKhoan.MaNhanVien = NhanVien.MaNhanVien";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String maTaiKhoan = rs.getString("MaTaiKhoan");
                String tenDangNhap = rs.getString("TenDangNhap");
                String matKhau = rs.getString("MatKhau");
                String maNhanVien = rs.getString("MaNhanVien");
                String hoTen = rs.getString("HoTen");
                String cccd = rs.getString("CCCD");
                String soDienThoai = rs.getString("SoDienThoai");
//                LocalDate ngaySinh = rs.getDate("NgaySinh").toLocalDate();
                LocalDate ngaySinh = LocalDate.of(2000, Month.MARCH, 1);
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
                dsTaiKhoan.add(new TaiKhoan(maTaiKhoan, tenDangNhap, matKhau, new NhanVien(maNhanVien, cccd, hoTen, diaChi, ngaySinh, soDienThoai, chucVu, gioiTinh, anhDaiDien, trangThaiLV)));
            }
        } catch (SQLException ex) {
            Logger.getLogger(GD_QLKhachHangController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(GD_DangNhapController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(GD_QLKhachHangController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return dsTaiKhoan;
    }
    public static boolean isExisted(String tenTaiKhoan) {
        Connection conn = ConnectDB.getInstance().getConnection();
        Statement stmt = null;
        boolean isExisted = false;
        try {
            stmt = conn.createStatement();
            String sql = String.format("SELECT * FROM TaiKhoan WHERE TenDangNhap = '%s'", tenTaiKhoan);
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                isExisted = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(GD_QLKhachHangController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(GD_DangNhapController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                assert stmt != null;
                stmt.close();
            } catch (SQLException ex) {
                Logger.getLogger(GD_QLKhachHangController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return isExisted;
    }

    public static boolean isExistedMaNhanVien(String maNhanVien) {
        Connection conn = ConnectDB.getInstance().getConnection();
        Statement stmt = null;
        boolean isExisted = false;
        try {
            stmt = conn.createStatement();
            String sql = String.format("SELECT * FROM TaiKhoan WHERE MaNhanVien = '%s'", maNhanVien);
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                isExisted = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(GD_QLKhachHangController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(GD_DangNhapController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                assert stmt != null;
                stmt.close();
            } catch (SQLException ex) {
                Logger.getLogger(GD_QLKhachHangController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return isExisted;
    }
}
