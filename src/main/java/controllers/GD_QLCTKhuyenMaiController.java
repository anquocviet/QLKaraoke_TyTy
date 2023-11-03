/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import connect.ConnectDB;
import entity.CT_KhuyenMai;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 *
 * @author Admin
 */
public class GD_QLCTKhuyenMaiController implements Initializable{


    
    @FXML
    private TableView<CT_KhuyenMai> tableView_CTKhuyenMai;
    
    @FXML
    private TableColumn<CT_KhuyenMai, String> col_sttKhuyenMai; 
    
    @FXML
    private TableColumn<CT_KhuyenMai, String> col_maKhuyenMai; 
    
    @FXML
    private TableColumn<CT_KhuyenMai, String> col_tenKhuyenMai; 
    
    @FXML
    private TableColumn<CT_KhuyenMai, Date> col_ngayBatDau; 
    
    @FXML
    private TableColumn<CT_KhuyenMai, Date> col_ngayKetThuc; 
    
    @FXML
    private TableColumn<CT_KhuyenMai, Integer> col_luotSuDungConLai;
    
    @FXML
    private TableColumn<CT_KhuyenMai, Long> col_chietKhau;
    
    ObservableList<CT_KhuyenMai> danhSachCT_KhuyenMai;
    
    Connection con = null;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        col_maKhuyenMai.setCellValueFactory(new PropertyValueFactory<CT_KhuyenMai, String>("Mã khuyến mãi"));
        col_tenKhuyenMai.setCellValueFactory(new PropertyValueFactory<CT_KhuyenMai, String>("Tên khuyến mãi"));
        col_ngayBatDau.setCellValueFactory(new PropertyValueFactory<CT_KhuyenMai, Date>("Ngày bắt đầu"));
        col_ngayKetThuc.setCellValueFactory(new PropertyValueFactory<CT_KhuyenMai, Date>("Ngày kết thúc"));
        col_luotSuDungConLai.setCellValueFactory(new PropertyValueFactory<CT_KhuyenMai, Integer>("Lượt sử dụng"));
        col_chietKhau.setCellValueFactory(new PropertyValueFactory<CT_KhuyenMai, Long>("Chiết khấu"));
        
        danhSachCT_KhuyenMai = getListCT_KhuyenMai();
        tableView_CTKhuyenMai.setItems(danhSachCT_KhuyenMai);
    }
    
    public  ObservableList<CT_KhuyenMai> getListCT_KhuyenMai(){
        ConnectDB.getInstance();
        con = ConnectDB.getConnection();
    
        ObservableList<CT_KhuyenMai> list = FXCollections.observableArrayList();
        try{
            PreparedStatement ps = con.prepareStatement("select * form CT_KhuyenMai");
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()){
                list.add(new CT_KhuyenMai(rs.getString("maKhuyenMai"), rs.getString("tenKhuyenMai"), rs.getDate("ngayBatDau"), rs.getDate("ngayKetThuc"), rs.getInt("luotSuDungConLai"), rs.getLong("chietKhau")));
            }
            rs.close();
            con.close();
        } catch (Exception e){
            
        }
        return null;    
    }
}
