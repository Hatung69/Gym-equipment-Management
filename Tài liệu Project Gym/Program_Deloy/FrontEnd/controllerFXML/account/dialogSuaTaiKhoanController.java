package controllerFXML.account;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXTextField;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import model.taikhoan.TaiKhoanDAO;

public class dialogSuaTaiKhoanController implements Initializable {
	@FXML
	private JFXTextField txtTenTaiKhoan;

	@FXML
	private JFXTextField txtGmail;

	@FXML
	private JFXTextField txtMaNhanVien;

	@FXML
	void capNhatTaiKhoan(ActionEvent event) {
		TaiKhoanDAO.capNhatTaiKhoan(txtTenTaiKhoan.getText().trim(), txtGmail.getText().trim(),
				AccountController.getInstance().tableTaiKhoan.getSelectionModel().getSelectedItem().getMaNhanVien());
		AccountController.getInstance().dialogCapNhatTaiKhoan.close();
		AccountController.getInstance().listtaiKhoan = TaiKhoanDAO.getListTaiKhoan();
		AccountController.getInstance().tableTaiKhoan.setItems(AccountController.getInstance().listtaiKhoan);
	}

	@FXML
	void clearTextAction(ActionEvent event) {
		txtTenTaiKhoan.clear();
		txtGmail.clear();
	}

	@FXML
	void closeDialog(MouseEvent event) {
		AccountController.getInstance().dialogCapNhatTaiKhoan.close();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		txtTenTaiKhoan.setText(
				AccountController.getInstance().tableTaiKhoan.getSelectionModel().getSelectedItem().getTenTaiKhoan());
		txtGmail.setText(
				AccountController.getInstance().tableTaiKhoan.getSelectionModel().getSelectedItem().getGmail());
		txtMaNhanVien.setText(
				AccountController.getInstance().tableTaiKhoan.getSelectionModel().getSelectedItem().getMaNhanVien());
	}

}
