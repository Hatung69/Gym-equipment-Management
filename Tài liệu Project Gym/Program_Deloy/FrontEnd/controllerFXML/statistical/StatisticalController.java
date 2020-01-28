package controllerFXML.statistical;

import java.net.URL;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.text.Text;
import model.baitap.BaiTap;
import model.baitap.BaiTapDAO;
import model.thietbi.ThietBiDAO;

public class StatisticalController implements Initializable {
	@FXML
	private PieChart pieChart, pieChart2;
    @FXML
    private JFXButton btnReLoad;
	@FXML
	private Text txt1, txt2, txt3, txt4, txt5,txt6;
	private int tongSoLuong = 0, soLuongBaoTri = 0, soLuongBinhThuong = 0, tongGiaTri = 0, tbHetHan = 0,klNangNhat=0;
	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	Date date = new Date();

	final NumberFormat format = NumberFormat.getNumberInstance();
	ObservableList<PieChart.Data> pieChartData2 = FXCollections.observableArrayList();
	final NumberFormat formatter = new DecimalFormat("#0");

	@FXML
	void restData(ActionEvent event) {
		loadData();
	}

	public void loadData() {
		//-----------------
		Alert alertL = new Alert(AlertType.INFORMATION);
		alertL.setTitle("Thông báo");
		alertL.setHeaderText(null);
		alertL.setContentText("Khởi tạo lại dữ liệu.....Vui lòng chờ !");
		alertL.getDialogPane().setStyle(
				"-fx-border-color: black;-fx-background-color: Orange; -fx-font-size: 16px;-fx-font-weight: bold;");
		alertL.initOwner(btnReLoad.getScene().getWindow());
		alertL.getDialogPane().lookupButton(ButtonType.OK).setDisable(true);
		alertL.show();
		//-----------------
		tongSoLuong = ThietBiDAO.getTongSoLuongTB();
		soLuongBaoTri = ThietBiDAO.getThietBiBaoTri();
		soLuongBinhThuong = ThietBiDAO.getThietBiBinhThuong();
		tongGiaTri = ThietBiDAO.getTongGiaTriTb();
		tbHetHan = ThietBiDAO.getSoTBHetHSD(dateFormat.format(date));
		klNangNhat=ThietBiDAO.getKhoiLuongNangNhat();
		

		pieChart.getData().clear();
		pieChart2.getData().clear();

		ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
				new PieChart.Data("Bảo trì", soLuongBaoTri), new PieChart.Data("Bình thường", soLuongBinhThuong));

		PieChart.Data dataCh;
		String tenBT;
		for (BaiTap baiTap : BaiTapDAO.getListBaiTap()) {
			tenBT = baiTap.getTenBaiTap();
			dataCh = new PieChart.Data(tenBT, ThietBiDAO.getTongSoLuongTBtheoBT(tenBT));
			pieChartData2.add(dataCh);
		}

		
		alertL.close();
		
		txt1.setText(String.valueOf(tongSoLuong));
		txt2.setText(String.valueOf(soLuongBaoTri));
		txt3.setText(String.valueOf(soLuongBinhThuong));
		txt4.setText(format.format(tongGiaTri) + " VNĐ");
		txt5.setText(String.valueOf(tbHetHan));
		txt6.setText(format.format(klNangNhat) + " Kg");
		pieChart.setData(pieChartData);
		pieChart2.setData(pieChartData2);
		
		pieChartData2.forEach(data -> data.nameProperty()
				.bind(Bindings.concat(data.getName(), " : ", formatter.format(data.getPieValue()))));

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		tongSoLuong = ThietBiDAO.getTongSoLuongTB();
		soLuongBaoTri = ThietBiDAO.getThietBiBaoTri();
		soLuongBinhThuong = ThietBiDAO.getThietBiBinhThuong();
		tongGiaTri = ThietBiDAO.getTongGiaTriTb();
		tbHetHan = ThietBiDAO.getSoTBHetHSD(dateFormat.format(date));
		klNangNhat=ThietBiDAO.getKhoiLuongNangNhat();
		

		pieChart.getData().clear();
		pieChart2.getData().clear();

		ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
				new PieChart.Data("Bảo trì", soLuongBaoTri), new PieChart.Data("Bình thường", soLuongBinhThuong));

		PieChart.Data dataCh;
		String tenBT;
		for (BaiTap baiTap : BaiTapDAO.getListBaiTap()) {
			tenBT = baiTap.getTenBaiTap();
			dataCh = new PieChart.Data(tenBT, ThietBiDAO.getTongSoLuongTBtheoBT(tenBT));
			pieChartData2.add(dataCh);
		}

		
		
		pieChart.setData(pieChartData);
		pieChart2.setData(pieChartData2);
		txt1.setText(String.valueOf(tongSoLuong));
		txt2.setText(String.valueOf(soLuongBaoTri));
		txt3.setText(String.valueOf(soLuongBinhThuong));
		txt4.setText(format.format(tongGiaTri) + " VNĐ");
		txt5.setText(String.valueOf(tbHetHan));
		txt6.setText(format.format(klNangNhat) + " Kg");
		
		pieChartData2.forEach(data -> data.nameProperty()
				.bind(Bindings.concat(data.getName(), " : ", formatter.format(data.getPieValue()))));

	}

}
