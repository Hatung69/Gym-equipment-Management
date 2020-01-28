package controllerFXML.account;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import controllerFXML.home.HomePageController;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import model.nhanvien.NhanVien;
import model.nhanvien.NhanVienDAO;
import model.taikhoan.TaiKhoanDAO;

public class dialogThemTaiKhoanController implements Initializable {
	@FXML
	private JFXTextField txtTenTaiKhoan;

	@FXML
	private JFXPasswordField txtPassword;

	@FXML
	private JFXTextField txtPasswordShow;

	@FXML
	private JFXCheckBox checkBox;

	@FXML
	private JFXTextField txtGmail;

	@FXML
	private JFXComboBox<NhanVien> cbbMaNhanVien;
	public ObservableList<NhanVien> listNV;

	@FXML
	void clearTextAction(ActionEvent event) {
		txtTenTaiKhoan.clear();
		txtPassword.clear();
		txtGmail.clear();
		cbbMaNhanVien.getSelectionModel().select(null);
	}

	@FXML
	void closeDialog(MouseEvent event) {
		AccountController.getInstance().dialogThemTaiKhoan.close();
	}

	@FXML
	void taoTaiKhoan(ActionEvent event) {
		if (TaiKhoanDAO.maTenTKIsExist(txtTenTaiKhoan.getText().trim())) {
			JFXDialogLayout dialogLayout = new JFXDialogLayout();
			dialogLayout.setStyle(
					"-fx-background-color: Orange;-fx-border-color: Black;-fx-border-width: 5 ;-fx-border-color:Black ;-fx-text-fill: White;-fx-effect: dropshadow(three-pass-box, DarkRed, 10, 0, 0, 0);");
			Image image = new Image(getClass().getResourceAsStream("/Images/JFXDialog/information.png"));
			Label label = new Label("Tên tài khoản đã tồn tại", new ImageView(image));
			label.setStyle("-fx-text-fill: DarkRed;-fx-font: 20 System; -fx-font-weight: Bold;");
			dialogLayout.setHeading(label);
			Text text = new Text("Vui lòng nhập Tên tài khoản khác !");
			text.setStyle("-fx-font: 15 System");
			dialogLayout.setBody(text);
			JFXDialog dialog = new JFXDialog(HomePageController.getInstance().mainStackPane, dialogLayout,
					JFXDialog.DialogTransition.CENTER);
			dialog.show();
			return;
		}else if (txtTenTaiKhoan.getText().trim().isEmpty()||txtPassword.getText().trim().isEmpty()||txtGmail.getText().trim().isEmpty()||cbbMaNhanVien.getSelectionModel().getSelectedItem()==null) {
			JFXDialogLayout dialogLayout = new JFXDialogLayout();
			dialogLayout.setStyle(
					"-fx-background-color: Orange;-fx-border-color: Black;-fx-border-width: 5 ;-fx-border-color:Black ;-fx-text-fill: White;-fx-effect: dropshadow(three-pass-box, DarkRed, 10, 0, 0, 0);");
			Image image = new Image(getClass().getResourceAsStream("/Images/JFXDialog/information.png"));
			Label label = new Label("Kiểm tra lại thông tin", new ImageView(image));
			label.setStyle("-fx-text-fill: DarkRed;-fx-font: 20 System; -fx-font-weight: Bold;");
			dialogLayout.setHeading(label);
			Text text = new Text("Vui lòng cung cấp đầy đủ thông tin cho Tài khoản!");
			text.setStyle("-fx-font: 15 System");
			dialogLayout.setBody(text);
			JFXDialog dialog = new JFXDialog(HomePageController.getInstance().mainStackPane, dialogLayout,
					JFXDialog.DialogTransition.CENTER);
			dialog.show();
			return;
		}else if (txtTenTaiKhoan.getText().trim().isEmpty()||txtPassword.getText().trim().isEmpty()||txtGmail.getText().trim().isEmpty()||cbbMaNhanVien.getSelectionModel().getSelectedItem()==null) {
			JFXDialogLayout dialogLayout = new JFXDialogLayout();
			dialogLayout.setStyle(
					"-fx-background-color: Orange;-fx-border-color: Black;-fx-border-width: 5 ;-fx-border-color:Black ;-fx-text-fill: White;-fx-effect: dropshadow(three-pass-box, DarkRed, 10, 0, 0, 0);");
			Image image = new Image(getClass().getResourceAsStream("/Images/JFXDialog/information.png"));
			Label label = new Label("Kiểm tra lại thông tin", new ImageView(image));
			label.setStyle("-fx-text-fill: DarkRed;-fx-font: 20 System; -fx-font-weight: Bold;");
			dialogLayout.setHeading(label);
			Text text = new Text("Vui lòng cung cấp đầy đủ thông tin cho Tài khoản!");
			text.setStyle("-fx-font: 15 System");
			dialogLayout.setBody(text);
			JFXDialog dialog = new JFXDialog(HomePageController.getInstance().mainStackPane, dialogLayout,
					JFXDialog.DialogTransition.CENTER);
			dialog.show();
			return;
		}else if (TaiKhoanDAO.maNhanVienTKIsExist(cbbMaNhanVien.getSelectionModel().getSelectedItem().getMaNhanVien())) {
			JFXDialogLayout dialogLayout = new JFXDialogLayout();
			dialogLayout.setStyle(
					"-fx-background-color: Orange;-fx-border-color: Black;-fx-border-width: 5 ;-fx-border-color:Black ;-fx-text-fill: White;-fx-effect: dropshadow(three-pass-box, DarkRed, 10, 0, 0, 0);");
			Image image = new Image(getClass().getResourceAsStream("/Images/JFXDialog/information.png"));
			Label label = new Label("Nhân viên này đã có tài khoản", new ImageView(image));
			label.setStyle("-fx-text-fill: DarkRed;-fx-font: 20 System; -fx-font-weight: Bold;");
			dialogLayout.setHeading(label);
			Text text = new Text("Vui lòng chọn Nhân viên khác !");
			text.setStyle("-fx-font: 15 System");
			dialogLayout.setBody(text);
			JFXDialog dialog = new JFXDialog(HomePageController.getInstance().mainStackPane, dialogLayout,
					JFXDialog.DialogTransition.CENTER);
			dialog.show();
			return;
		}
		else {
			TaiKhoanDAO.themTaiKhoan(txtTenTaiKhoan.getText().trim(), txtPassword.getText().trim(), txtGmail.getText().trim(), cbbMaNhanVien.getSelectionModel().getSelectedItem().getMaNhanVien());
			txtTenTaiKhoan.clear();
			AccountController.getInstance().listtaiKhoan = TaiKhoanDAO.getListTaiKhoan();
			AccountController.getInstance().tableTaiKhoan.setItems(AccountController.getInstance().listtaiKhoan);
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// - Hiện mật khẩu ( dùng txtPass và txt thường để switch cho nhau tùy checkBox)
				txtPasswordShow.managedProperty().bind(checkBox.selectedProperty());
				txtPasswordShow.visibleProperty().bind(checkBox.selectedProperty());
				txtPasswordShow.textProperty().bindBidirectional(txtPassword.textProperty());
				txtPassword.managedProperty().bind(checkBox.selectedProperty().not());
				txtPassword.visibleProperty().bind(checkBox.selectedProperty().not());

		//- Khởi tạo dữ liệu Combobox
				listNV=NhanVienDAO.getListNhanVien();
				cbbMaNhanVien.setItems(listNV);
	}

}
