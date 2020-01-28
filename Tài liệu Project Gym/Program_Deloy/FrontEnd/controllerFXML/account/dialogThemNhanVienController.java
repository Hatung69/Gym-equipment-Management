package controllerFXML.account;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

import controllerFXML.home.HomePageController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.util.StringConverter;
import model.nhanvien.NhanVienDAO;

public class dialogThemNhanVienController implements Initializable {
	@FXML
	private JFXTextField txtMaNhanVien, txtTenNhanVien, txtSoDienThoai;
	@FXML
	private ToggleGroup gender;
	@FXML
	private JFXTextArea txtDiaChi;

	@FXML
	private JFXDatePicker pickNgaySinh;

	@FXML
	void clearTextAction(ActionEvent event) {
		txtMaNhanVien.clear();
		txtTenNhanVien.clear();
		pickNgaySinh.setValue(null);
		txtDiaChi.clear();
		txtSoDienThoai.clear();
	}

	@FXML
	void closeDialog(MouseEvent event) {
		AccountController.getInstance().dialogThemNhanVien.close();
	}

	// oooooooooooooooooo THÊM ooooooooooooooooo
	@FXML
	void themVao(ActionEvent event) {
		// Kiểm tra Mã Nhân viên
		if (NhanVienDAO.maNVIsExist(txtMaNhanVien.getText())) {
			JFXDialogLayout dialogLayout = new JFXDialogLayout();
			dialogLayout.setStyle(
					"-fx-background-color: Orange;-fx-border-color: Black;-fx-border-width: 5 ;-fx-border-color:Black ;-fx-text-fill: White;-fx-effect: dropshadow(three-pass-box, DarkRed, 10, 0, 0, 0);");
			Image image = new Image(getClass().getResourceAsStream("/Images/JFXDialog/information.png"));
			Label label = new Label("Mã nhân viên đã tồn tại", new ImageView(image));
			label.setStyle("-fx-text-fill: DarkRed;-fx-font: 20 System; -fx-font-weight: Bold;");
			dialogLayout.setHeading(label);
			Text text = new Text("Mã nhân viên đã tồn tại. Vui lòng nhập mã khác !");
			text.setStyle("-fx-font: 15 System");
			dialogLayout.setBody(text);
			JFXDialog dialog = new JFXDialog(HomePageController.getInstance().mainStackPane, dialogLayout,
					JFXDialog.DialogTransition.CENTER);
			dialog.show();
			return;
		} else if (txtMaNhanVien.getText().trim().isEmpty() || txtTenNhanVien.getText().trim().isEmpty()
				|| pickNgaySinh.getValue() == null || txtDiaChi.getText().trim().isEmpty()
				|| txtSoDienThoai.getText().trim().isEmpty()) {
			JFXDialogLayout dialogLayout = new JFXDialogLayout();
			dialogLayout.setStyle(
					"-fx-background-color: Orange;-fx-border-color: Black;-fx-border-width: 5 ;-fx-border-color:Black ;-fx-text-fill: White;-fx-effect: dropshadow(three-pass-box, DarkRed, 10, 0, 0, 0);");
			Image image = new Image(getClass().getResourceAsStream("/Images/JFXDialog/information.png"));
			Label label = new Label("Kiểm tra lại thông tin", new ImageView(image));
			label.setStyle("-fx-text-fill: DarkRed;-fx-font: 20 System; -fx-font-weight: Bold;");
			dialogLayout.setHeading(label);
			Text text = new Text("Vui lòng cung cấp đầy đủ thông tin cho Nhân viên!");
			text.setStyle("-fx-font: 15 System");
			dialogLayout.setBody(text);
			JFXDialog dialog = new JFXDialog(HomePageController.getInstance().mainStackPane, dialogLayout,
					JFXDialog.DialogTransition.CENTER);
			dialog.show();
			return;
		} else if (!checkNumber(txtSoDienThoai.getText())) {
			JFXDialogLayout dialogLayout = new JFXDialogLayout();
			dialogLayout.setStyle(
					"-fx-background-color: Orange;-fx-border-color: Black;-fx-border-width: 5 ;-fx-border-color:Black ;-fx-text-fill: White;-fx-effect: dropshadow(three-pass-box, DarkRed, 10, 0, 0, 0);");
			Image image = new Image(getClass().getResourceAsStream("/Images/JFXDialog/information.png"));
			Label label = new Label("Kiểm tra lại thông tin", new ImageView(image));
			label.setStyle("-fx-text-fill: DarkRed;-fx-font: 20 System; -fx-font-weight: Bold;");
			dialogLayout.setHeading(label);
			Text text = new Text("Vui lòng điền đúng Số điện thoại của Nhân viên");
			text.setStyle("-fx-font: 15 System");
			dialogLayout.setBody(text);
			JFXDialog dialog = new JFXDialog(HomePageController.getInstance().mainStackPane, dialogLayout,
					JFXDialog.DialogTransition.CENTER);
			dialog.show();
			return;
		} else {
			java.sql.Date sqlDate = java.sql.Date.valueOf(pickNgaySinh.getValue());
			RadioButton button = (RadioButton) gender.getSelectedToggle();

			NhanVienDAO.themNhanVien(txtMaNhanVien.getText().trim(), txtTenNhanVien.getText().trim(), sqlDate, button.getText(), txtDiaChi.getText().trim(), txtSoDienThoai.getText().trim());
			// Load data..........
			txtMaNhanVien.clear();
			AccountController.getInstance().listNhanVien = NhanVienDAO.getListNhanVien();
			AccountController.getInstance().tableNhanVien.setItems(	AccountController.getInstance().listNhanVien);
		}
	}

	public static boolean checkNumber(String s) {
		for (int i = 0; i < s.length(); i++) {
			if (Character.isDigit(s.charAt(i))) {

			} else
				return false;
		}
		return true;
	}

	// =====* Khởi tạo controller *=====
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
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
		pickNgaySinh.setConverter(converter);
	}

}
