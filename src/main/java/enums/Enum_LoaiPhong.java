/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package enums;

import javafx.collections.ObservableList;

/**
 * @author Thạch
 */
public enum Enum_LoaiPhong {

   THUONG,
   VIP;

   public static ObservableList getListLoaiPhong() {
      return javafx.collections.FXCollections.observableArrayList(Enum_LoaiPhong.values());
   }
}
