/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import connect.ConnectDB;
import controllers.GD_QLDichVuController;
import controllers.GD_QLKhachHangController;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author vie
 */
public class DichVu {

    private String maDichVu;
    private String tenDichVu;
    private int soLuong;
    private long donGia;
    private String donViTinh;
    private String anhMinhHoa;

    public DichVu() {
    }

    public DichVu(String maDichVu, String tenDichVu, String donViTinh) throws Exception {
        setMaDichVu(maDichVu);
        setTenDichVu(tenDichVu);
        setDonViTinh(donViTinh);
    }

    public DichVu(String maDichVu, String tenDichVu, int soLuong, long donGia, String donViTinh, String anhMinhHoa) throws Exception {
        setMaDichVu(maDichVu);
        setTenDichVu(tenDichVu);
        setSoLuong(soLuong);
        setDonGia(donGia);
        setDonViTinh(donViTinh);
        setAnhMinhHoa(anhMinhHoa);
    }

    public DichVu(String maDichVu) {
        this.maDichVu = maDichVu;
    }

    public String getMaDichVu() {
        return maDichVu;
    }

    public void setMaDichVu(String maDichVu) throws Exception {
        if (!(maDichVu == null)) {
            this.maDichVu = maDichVu;
        } else {
            throw new Exception("Mã dịch vụ không được rỗng");
        }

    }

    public String getTenDichVu() {
        return tenDichVu;
    }

    public void setTenDichVu(String tenDichVu) throws Exception {
        if (tenDichVu == null || tenDichVu.trim().equals("")) {
            throw new Exception("Tên Dịch Vụ không được rỗng");
        } else {
            this.tenDichVu = tenDichVu;
        }
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) throws Exception {
        if (soLuong < 0) {
            throw new Exception("Số lượng không được rỗng");
        } else {
            this.soLuong = soLuong;
        }

    }

    public long getDonGia() {
        return donGia;
    }

    public void setDonGia(long donGia) throws Exception {
        if (donGia < 0) {
            throw new Exception("Đơn giá của dịch vụ  không được rỗng và phải lớn hơn 0");
        } else {
            this.donGia = donGia;
        }
    }

    public String getDonViTinh() {
        return donViTinh;
    }

    public void setDonViTinh(String donViTinh) throws Exception {
        if (donViTinh == null || donViTinh.trim().equals("")) {
            throw new Exception("Đơn vị tính của dịch vụ không được rỗng");
        } else {
            this.donViTinh = donViTinh;
        }

    }

    public String getAnhMinhHoa() {
        return anhMinhHoa;
    }

    public void setAnhMinhHoa(String anhMinhHoa) throws Exception {
        if (anhMinhHoa == null || anhMinhHoa.trim().equals("")) {
            throw new Exception("Ảnh minh họa của dịch vụ  không được rỗng");
        } else {
            this.anhMinhHoa = anhMinhHoa;
        }
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + Objects.hashCode(this.maDichVu);
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
        final DichVu other = (DichVu) obj;
        return Objects.equals(this.maDichVu, other.maDichVu);
    }

    @Override
    public String toString() {
        return "DichVu{" + "maDichVu=" + maDichVu + ", tenDichVu=" + tenDichVu + ", soLuong=" + soLuong + ", donGia=" + donGia + ", donViTinh=" + donViTinh + ", anhMinhHoa=" + anhMinhHoa + '}';
    }

//    Get Data From DB
    public static ObservableList<DichVu> getAllDichVu() {
        ObservableList<DichVu> dsDichVu = FXCollections.observableArrayList();
        Connection conn = ConnectDB.getConnection();
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            String sql = "SELECT * FROM DichVu";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String maDichVu = rs.getString("MaDichVu");
                String tenDichVu = rs.getString("TenDichVu");
                int soLuongTon = rs.getInt("SoLuongTon");
                String donViTinh = rs.getString("DonViTinh");
                long donGia = rs.getLong("DonGia");
                String anhMinhHoa = rs.getString("AnhMinhHoa");
                dsDichVu.add(new DichVu(maDichVu, tenDichVu, soLuongTon, donGia, donViTinh, anhMinhHoa));
            }
        } catch (SQLException ex) {
            Logger.getLogger(GD_QLDichVuController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(DichVu.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                stmt.close();
            } catch (SQLException ex) {
                Logger.getLogger(GD_QLDichVuController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return dsDichVu;
    }

    public static DichVu getDichVuTheoMaDichVu(String maDichVu) {
        Connection conn = ConnectDB.getConnection();
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            String sql = String.format("SELECT * FROM DichVu WHERE MaDichVu = '%s'", maDichVu);
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String tenDichVu = rs.getString("TenDichVu");
                int soLuongTon = rs.getInt("SoLuongTon");
                String donViTinh = rs.getString("DonViTinh");
                long donGia = rs.getLong("DonGia");
                String anhMinhHoa = rs.getString("AnhMinhHoa");
                return new DichVu(maDichVu, tenDichVu, soLuongTon, donGia, donViTinh, anhMinhHoa);
            }
        } catch (SQLException ex) {
            Logger.getLogger(GD_QLDichVuController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(GD_QLDichVuController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                stmt.close();
            } catch (SQLException ex) {
                Logger.getLogger(GD_QLDichVuController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    public static int demSLDichVu() throws SQLException {
        Connection conn = ConnectDB.getInstance().getConnection();
        Statement stmt = null;

        try {
            stmt = conn.createStatement();
            String sql = "SELECT MaDichVu, COUNT(*) AS SoLuongDichVu "
                    + "FROM DichVu "
                    + "GROUP BY MaDichVu";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                return rs.getInt("SoLuongDichVu");
            }
            return 0;
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }

    public static boolean themDichVu(DichVu dv) {
        ConnectDB.getInstance();
        Connection conn = ConnectDB.getInstance().getConnection();
        PreparedStatement pstm = null;
        int n = 0;

        String sql = "INSERT INTO DichVu (MaDichVu, TenDichVu, SoLuongTon, DonViTinh, DonGia, AnhMinhHoa) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        try {
            pstm = conn.prepareStatement(sql);
            pstm.setString(1, dv.getMaDichVu());
            pstm.setString(2, dv.getTenDichVu());
            pstm.setInt(3, dv.getSoLuong());
            pstm.setString(4, dv.getDonViTinh());
            pstm.setLong(5, dv.getDonGia());
            pstm.setString(6, dv.getAnhMinhHoa());

            n = pstm.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(GD_QLDichVuController.class
                    .getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (pstm != null) {
                    pstm.close();

                }
            } catch (SQLException ex) {
                Logger.getLogger(GD_QLDichVuController.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }

        return n > 0;
    }

    public static boolean capNhatThongTinDichVu(DichVu dv) {
        ConnectDB.getInstance();
        Connection conn = ConnectDB.getInstance().getConnection();
        PreparedStatement pstm = null;
        int n = 0;

        String sql = "UPDATE DichVu "
                + "SET TenDichVu = ?, SoLuongTon = ?, DonViTinh = ?, DonGia = ?, AnhMinhHoa = ? "
                + "WHERE MaDichVu = ?";

        try {
            pstm = conn.prepareStatement(sql);
            if (pstm != null) {
                pstm.setString(1, dv.getTenDichVu());
                pstm.setInt(2, dv.getSoLuong());
                pstm.setString(3, dv.getDonViTinh());
                pstm.setLong(4, dv.getDonGia());
                pstm.setString(5, dv.getAnhMinhHoa());
                pstm.setString(6, dv.getMaDichVu());

                n = pstm.executeUpdate();
            } else {
                System.out.println("PreparedStatement is null. Check your database connection.");

            }
        } catch (SQLException ex) {
            Logger.getLogger(GD_QLDichVuController.class
                    .getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (pstm != null) {
                    pstm.close();

                }
            } catch (SQLException ex) {
                Logger.getLogger(GD_QLDichVuController.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }

        return n > 0;
    }

}
