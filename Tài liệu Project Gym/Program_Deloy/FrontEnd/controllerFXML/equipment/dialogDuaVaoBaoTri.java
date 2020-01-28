package controllerFXML.equipment;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Locale;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

import controllerFXML.home.HomePageController;
import controllerFXML.maintenance.MaintenanceController;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.util.StringConverter;
import model.baotri.BaoTriDAO;
import model.loaiBT.LoaiBaoTri;
import model.loaiBT.LoaiBaoTriDAO;
import model.thietbi.ThietBiDAO;

public class dialogDuaVaoBaoTri implements Initializable {
	private static dialogDuaVaoBaoTri instance;

	public static dialogDuaVaoBaoTri getInstance() {
		return instance;
	}

	public dialogDuaVaoBaoTri() {
		instance = this;
	}

	// ---------------------------------
	@FXML
	private JFXComboBox<LoaiBaoTri> cbbLoaiBT;
	@FXML
	public JFXTextField txtMaBaoTri, txtTenBaoTri, txtMaThietBi, txtTenThietBi;
	@FXML
	private JFXDatePicker pickNgayBT, pickNgayHoanTat;
	@FXML
	private JFXTextArea txtMoTa;

	public AnchorPane anchorLoaiTB;
	public JFXDialog dialogLoaiBaoTri;
	ObservableList<LoaiBaoTri> listLBT;

	// ===== Bảo trì =====
	@FXML
	void baoTriAction(ActionEvent event) {
		if (txtMaBaoTri.getText().trim().isEmpty() || txtTenBaoTri.getText().trim().isEmpty()
				|| txtMoTa.getText().trim().isEmpty() || cbbLoaiBT.getSelectionModel().getSelectedItem() == null
				|| pickNgayHoanTat.getValue() == null) {
			JFXDialogLayout dialogLayout = new JFXDialogLayout();
			dialogLayout.setStyle(
					"-fx-background-color: Orange;-fx-border-color: Black;-fx-border-width: 5 ;-fx-border-color:Black ;-fx-text-fill: White;-fx-effect: dropshadow(three-pass-box, DarkRed, 10, 0, 0, 0);");
			Image image = new Image(getClass().getResourceAsStream("/Images/JFXDialog/information.png"));
			Label label = new Label("Kiểm tra lại thông tin", new ImageView(image));
			label.setStyle("-fx-text-fill: DarkRed;-fx-font: 20 System; -fx-font-weight: Bold;");
			dialogLayout.setHeading(label);
			Text text = new Text("Vui lòng cung cấp đầy đủ thông tin!");
			text.setStyle("-fx-font: 15 System");
			dialogLayout.setBody(text);
			JFXDialog dialog = new JFXDialog(HomePageController.getInstance().mainStackPane, dialogLayout,
					JFXDialog.DialogTransition.CENTER);
			dialog.show();
			return;
		} else if (BaoTriDAO.maBaoTriIsExist(txtMaBaoTri.getText().trim())) {
			JFXDialogLayout dialogLayout = new JFXDialogLayout();
			dialogLayout.setStyle(
					"-fx-background-color: Orange;-fx-border-color: Black;-fx-border-width: 5 ;-fx-border-color:Black ;-fx-text-fill: White;-fx-effect: dropshadow(three-pass-box, DarkRed, 10, 0, 0, 0);");
			Image image = new Image(getClass().getResourceAsStream("/Images/JFXDialog/information.png"));
			Label label = new Label("Mã bảo trì đã tồn tại", new ImageView(image));
			label.setStyle("-fx-text-fill: DarkRed;-fx-font: 20 System; -fx-font-weight: Bold;");
			dialogLayout.setHeading(label);
			Text text = new Text("Vui lòng nhập mã khác !");
			text.setStyle("-fx-font: 15 System");
			dialogLayout.setBody(text);
			JFXDialog dialog = new JFXDialog(HomePageController.getInstance().mainStackPane, dialogLayout,
					JFXDialog.DialogTransition.CENTER);
			dialog.show();
			return;
		} else {
			java.sql.Date sqlDate = java.sql.Date.valueOf(pickNgayBT.getValue());
			java.sql.Date sqlDate2 = java.sql.Date.valueOf(pickNgayHoanTat.getValue());
			BaoTriDAO.themBaoTri(txtMaBaoTri.getText().trim(), txtTenBaoTri.getText().trim(),
					txtMaThietBi.getText().trim(), cbbLoaiBT.getSelectionModel().getSelectedItem().getMaLoaiBT(),
					sqlDate, sqlDate2, txtMoTa.getText().trim());
			ThietBiDAO.capNhatTinhTrangTb("TT2", txtMaThietBi.getText().trim());

			EquipmentController.getInstance().listThietBi = ThietBiDAO.getListThietBi();
			EquipmentController.getInstance().tableThietBi.setItems(EquipmentController.getInstance().listThietBi);
			MaintenanceController.getInstance().listBaoTri = BaoTriDAO.getListBaoTri();
			MaintenanceController.getInstance().tableBaoTri.setItems(MaintenanceController.getInstance().listBaoTri);
			MaintenanceController.getInstance().searchRealTimeBT();
			EquipmentController.getInstance().searchRealTime();
			EquipmentController.getInstance().dialogBaoTri.close();
		}
	}

	// ===== Clear text =====
	@FXML
	void clearTextUpdAction(ActionEvent event) {
		txtMaBaoTri.clear();
		txtTenBaoTri.clear();
		cbbLoaiBT.getSelectionModel().select(null);
		pickNgayHoanTat.setValue(null);
		txtMoTa.clear();
	}

	// ===== Đóng dialog =====
	@FXML
	void closeDialog(MouseEvent event) {
		EquipmentController.getInstance().dialogBaoTri.close();
	}

	// =====* Khởi tạo Controller *=======
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		listLBT = LoaiBaoTriDAO.getListBTObser();
		cbbLoaiBT.setItems(listLBT);
		txtMaThietBi.setText(
				EquipmentController.getInstance().tableThietBi.getSelectionModel().getSelectedItem().getMaThietBi());
		txtTenThietBi.setText(
				EquipmentController.getInstance().tableThietBi.getSelectionModel().getSelectedItem().getTenThietBi());

		// - Set ngày Việt Nam cho DatePicker
		Locale.setDefault(new Locale("vi", "VN"));

		// - Format ngày tháng năm trên DatePicker
		StringConverter<LocalDate> converter = new StringConverter<LocalDate>() {
			DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

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
		};
		pickNgayBT.setValue(NOW_LOCAL_DATE());
		pickNgayBT.setConverter(converter);
		pickNgayHoanTat.setConverter(converter);

	}

	public static final LocalDate NOW_LOCAL_DATE() {
		String date = new SimpleDateFormat("dd-MM-yyyy").format(Calendar.getInstance().getTime());
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		LocalDate localDate = LocalDate.parse(date, formatter);
		return localDate;
	}

}
