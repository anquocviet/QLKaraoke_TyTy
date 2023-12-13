/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controllers;

import java.net.URL;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import model.HoaDonThanhToan;
import model.KhachHang;

/**
 * FXML Controller class
 *
 * @author nktng
 */
public class GD_ThongKeNamController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
		for (int i = 0; i < 10; i++) {
			comboBoxNam.getItems().add(LocalDate.now().getYear() - i);
		}
		comboBoxNam.getSelectionModel().select(0);
		txtTongDoanhThu.setText(df.format(HoaDonThanhToan.calcTotalMoneyOfBill()) + " VNĐ");
		txtTongHoaDon.setText(df.format(HoaDonThanhToan.countBill()));
		txtTongKhachHang.setText(KhachHang.demSoLuongKhachHang() + "");
		setMaxCategoryWidth(50, 10);
		chart.widthProperty().addListener((obs, b, b1) -> {
			Platform.runLater(() -> setMaxCategoryWidth(40, 10));
		});
		loadDataWhenChangeYear();
		handleEventInCombomBox();
    }
	
	public void loadDataWhenChangeYear() {
		txtTongHDTheoNam.setText(HoaDonThanhToan.countBillByYear(comboBoxNam.getValue()) + "");
		chart.setAnimated(true);
		chart.getData().clear();
		ObservableList arr = HoaDonThanhToan.statisticalByYear(comboBoxNam.getValue());
		for (int i = 0; i < arr.size(); i = i + 2) {
			XYChart.Series series = new XYChart.Series();
			series.setName("Tháng " + arr.get(i));
			series.getData().add(new XYChart.Data(String.valueOf(arr.get(i)), arr.get(i + 1)));
			chart.getData().add(series);
		}
		chart.setAnimated(false);
	}
	
	public void handleEventInCombomBox() {
		comboBoxNam.setOnAction((event) -> {
			loadDataWhenChangeYear();
		});
	}
	
	private void setMaxCategoryWidth(double maxCategoryWidth, double minCategoryGap) {
		double catSpace = xAxis.getCategorySpacing();
		chart.setCategoryGap(catSpace - Math.min(maxCategoryWidth, catSpace - minCategoryGap));
	}
	
	DecimalFormat df = new DecimalFormat("#,###,###,##0");
	@FXML
	private Label txtTongHDTheoNam;
	@FXML
	private Label tongDTTheoNam;
	@FXML
	private Label txtTongKhachHang;
	@FXML
	private Label txtTongHoaDon;
	@FXML
	private Label txtTongDoanhThu;
	@FXML
	private StackedBarChart chart;
	@FXML
	private CategoryAxis xAxis;
	@FXML
	private NumberAxis yAxis;
	@FXML
	private ComboBox<Integer> comboBoxNam;

}
