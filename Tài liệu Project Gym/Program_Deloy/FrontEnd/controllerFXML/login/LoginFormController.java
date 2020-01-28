package controllerFXML.login;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public class LoginFormController implements Initializable {
	private static LoginFormController instance;
	public static LoginFormController getInstance() {
		return instance;
	}
	public LoginFormController() {
		instance = this;
	}
	// --------------------------
	@FXML
	protected ImageView imageLogo1,imageLogo2;
	@FXML
	protected StackPane stackPane;
	@FXML
	public StackPane stackDialog;

	protected Parent fxml;

	// =====* Khởi tạo controller *=====
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			fxml = FXMLLoader.load(getClass().getResource("/viewFXML/login/Login.fxml"));
			stackPane.getChildren().clear();
			stackPane.getChildren().setAll(fxml);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null,e.getMessage(),"Đã xảy vấn đề",JOptionPane.ERROR_MESSAGE);
		}
	}
}
