package controllerFXML.account;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;

public class dialogThongTinNhanVienController implements Initializable {
    @FXML
    private JFXTextField txtMaNhanVien;

    @FXML
    private JFXTextField txtTenNhanVien;

    @FXML
    private JFXTextField txtNgaySinh;

    @FXML
    private JFXTextField txtGioiTinh;

    @FXML
    private JFXTextArea txtDiaChi;

    @FXML
    private JFXTextField txtSoDienThoai;

    @FXML
    private JFXTextField txtMaQuyen;

    @FXML
    void closeDialog(MouseEvent event) {
    	AccountController.getInstance().dialogThongTinNhanVien.close();
    }
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		txtMaNhanVien.setText(AccountController.getInstance().tableNhanVien.getSelectionModel().getSelectedItem().getMaNhanVien());
		txtTenNhanVien.setText(AccountController.getInstance().tableNhanVien.getSelectionModel().getSelectedItem().getTenNhanVien());
		final SimpleDateFormat formatDate = new SimpleDateFormat("dd-MM-yyyy");
		txtNgaySinh.setText(formatDate.format(AccountController.getInstance().tableNhanVien.getSelectionModel().getSelectedItem().getNgaySinh()));
		txtGioiTinh.setText(AccountController.getInstance().tableNhanVien.getSelectionModel().getSelectedItem().getGioiTinh());
		txtDiaChi.setText(AccountController.getInstance().tableNhanVien.getSelectionModel().getSelectedItem().getDiaChi());
		txtSoDienThoai.setText(AccountController.getInstance().tableNhanVien.getSelectionModel().getSelectedItem().getSoDienThoai());
		txtMaQuyen.setText(AccountController.getInstance().tableNhanVien.getSelectionModel().getSelectedItem().getMaQuyen());
	}

}
