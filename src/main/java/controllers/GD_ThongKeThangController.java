/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controllers;

import java.net.URL;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.util.StringConverter;
import model.HoaDonThanhToan;
import model.KhachHang;

/**
 * FXML Controller class
 *
 * @author nktng
 */
public class GD_ThongKeThangController implements Initializable {

	/**
	 * Initializes the controller class.
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		datePicker.setValue(LocalDate.now());
		datePicker.setConverter(new StringConverter<LocalDate>() {
			private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

			@Override
			public String toString(LocalDate date) {
				if (date != null) {
					return dateFormatter.format(date);
				} else {
					return "";
				}
			}

			@Override
			public LocalDate fromString(String string) {
				if (string != null && !string.isEmpty()) {
					return LocalDate.parse(string, dateFormatter);
				} else {
					return null;
				}
			}
		});
		txtTongHoaDon.setText(HoaDonThanhToan.countBill() + "");
		txtTongDoanhThu.setText(df.format(HoaDonThanhToan.calcTotalMoneyOfBill()) + " VNĐ");
		txtTongKhachHang.setText(KhachHang.demSoLuongKhachHang() + "");
		xAxis.setTickLabelFormatter(new StringConverter<Number>() {
			@Override
			public String toString(Number object) {
				if (object.intValue() != object.doubleValue()) {
					return "";
				}
				return "" + (object.intValue());
			}

			@Override
			public Number fromString(String string) {
				Number val = Double.valueOf(string);
				return val.intValue();
			}
		});
		loadDataWhenChangeDate();
		handleInDatePicker();
	}
	
	public void handleInDatePicker() {
		datePicker.setOnAction((event) -> {
			loadDataWhenChangeDate();
		});
	}

	public void loadDataWhenChangeDate() {
		txtTongHDThang.setText(HoaDonThanhToan.countBillByMonth(datePicker.getValue()) + "");
		txtTongDoanhThuThang.setText(df.format(HoaDonThanhToan.calcTotalMoneyOfBillByMonth(datePicker.getValue())) + " VNĐ");
		// Create data
		XYChart.Series series = new XYChart.Series();
		series.setName("Doanh thu trong ngày");
		ObservableList arr = HoaDonThanhToan.statisticalByMonth(datePicker.getValue());
		for (int i = 0; i < arr.size(); i = i + 2) {
			series.getData().add(new XYChart.Data(arr.get(i), arr.get(i + 1)));
		}
		chart.getData().clear();
		chart.getData().add(series);
	}
	
	DecimalFormat df = new DecimalFormat("#,###,###,##0");
	@FXML
	private Label txtTongHDThang;
	@FXML
	private Label txtTongDoanhThuThang;
	@FXML
	private Label txtTongKhachHang;
	@FXML
	private Label txtTongHoaDon;
	@FXML
	private Label txtTongDoanhThu;
	@FXML
	private DatePicker datePicker;
	@FXML
	private LineChart chart;
	@FXML
	private NumberAxis xAxis;
	@FXML
	private NumberAxis yAxis;

}