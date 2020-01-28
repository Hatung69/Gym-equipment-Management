package controllerFXML.login;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import model.taikhoan.TaiKhoanDAO;

public class LoginController implements Initializable {
	private static LoginController instance;

	public static LoginController getInstance() {
		return instance;
	}

	public LoginController() {
		instance = this;
	}

	// -----------------------------
	@FXML
	private Text txtBanner;
	@FXML
	private ImageView imageLogo, imageLoading;
	@FXML
	private Label exit, minus;
	@FXML
	private JFXCheckBox checkBox;
	@FXML
	private JFXPasswordField txtPassword;
	@FXML
	protected JFXTextField txtPasswordShow;
	@FXML
	public JFXTextField txtUser;
	@FXML
	private Hyperlink hyperLink;
	@FXML
	private JFXButton btnLogin;

	double x = 0, y = 0;

	public static Parent root;

	// ===== Đăng nhập =====
	@FXML
	void loginAction(ActionEvent event) throws IOException, InterruptedException {
		imageLoading.setVisible(true);
		Alert alertL = new Alert(AlertType.INFORMATION);
		if (!TaiKhoanDAO.isLogin(txtUser.getText().trim(),txtUser.getText().trim(), txtPassword.getText().trim())) {
			imageLoading.setVisible(false);
			JFXDialogLayout dialogLayout = new JFXDialogLayout();
			dialogLayout.setStyle(
					"-fx-background-color: Orange;-fx-border-color: Black;-fx-border-width: 5 ;-fx-border-color:Black ;-fx-text-fill: White;-fx-effect: dropshadow(three-pass-box, DarkRed, 10, 0, 0, 0);");
			Image image = new Image(getClass().getResourceAsStream("/Images/JFXDialog/error.png"));
			Label label = new Label("Không hợp lệ", new ImageView(image));
			label.setStyle("-fx-text-fill: DarkRed;-fx-font: 20 System; -fx-font-weight: Bold;");
			dialogLayout.setHeading(label);
			Text text = new Text("Không thể đăng nhập! Vui lòng kiểm tra lại thông tin!");
			text.setStyle("-fx-font: 15 System");
			dialogLayout.setBody(text);
			JFXDialog dialog = new JFXDialog(LoginFormController.getInstance().stackDialog, dialogLayout,
					JFXDialog.DialogTransition.CENTER);
			dialog.show();
			return;
		} else {
			try {
				alertL.setTitle("Thông báo");
				alertL.setHeaderText("Đăng nhập thành công!");
				alertL.setContentText("Khởi tạo dữ liệu.....Vui lòng chờ !");
				alertL.getDialogPane().setStyle(
						"-fx-border-color: black;-fx-background-color: Orange; -fx-font-size: 14px;-fx-font-weight: bold;");
				alertL.initOwner(btnLogin.getScene().getWindow());
				alertL.getDialogPane().lookupButton(ButtonType.OK).setDisable(true);
				alertL.show();

				root = FXMLLoader.load(getClass().getResource("/viewFXML/home/HomePage.fxml"));
			} catch (IOException e) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Thông báo");
				alert.setHeaderText(e.getMessage());
				alert.showAndWait();
			}

		}
		alertL.close();
		PauseTransition pauseTransition = new PauseTransition(Duration.seconds(1));
		pauseTransition.setOnFinished(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				Stage stage = (Stage) btnLogin.getScene().getWindow();
				stage.close();
				Stage homeStage = new Stage(StageStyle.TRANSPARENT);
				Scene scene = new Scene(root);
				scene.setFill(Color.TRANSPARENT);
				homeStage.setScene(scene);
				homeStage.getIcons().add(new Image("file:logoApp.png"));
				homeStage.setTitle("Quản lý thiết bị Gym");
				homeStage.show();

				pauseTransition.stop();
				imageLoading.setVisible(false);
			}
		});
		pauseTransition.play();
	}

	// ===== Di chuyển khung Window khi kéo chuột từ Logo =====
	@FXML
	void dragged(MouseEvent event) {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setX(event.getScreenX() - x);
		stage.setY(event.getScreenY() - y);
	}

	@FXML
	void pressed(MouseEvent event) {
		x = event.getSceneX();
		y = event.getSceneY();
		imageLogo.setOpacity(0.8);
	}

	@FXML
	void resetOpacity(MouseEvent event) {
		imageLogo.setOpacity(1);
	}

	// ===== Tắt form Login và ẩn xuống =====
	@FXML
	void exitAction(MouseEvent event) {
		JFXDialogLayout dialogLayout = new JFXDialogLayout();
		dialogLayout.setStyle(
				"-fx-background-color: Orange;-fx-border-color: Black;-fx-border-width: 5 ;-fx-border-color:Black ;-fx-text-fill: White;-fx-effect: dropshadow(three-pass-box, DarkRed, 10, 0, 0, 0);");
		Image image = new Image(getClass().getResourceAsStream("/Images/JFXDialog/warning.png"));
		Label label = new Label("Thoát chương trình", new ImageView(image));
		label.setStyle("-fx-text-fill: DarkRed;-fx-font: 20 System; -fx-font-weight: Bold;");
		dialogLayout.setHeading(label);
		Text text = new Text("Bạn muốn thoát Chương trình?");
		text.setStyle("-fx-font: 15 System");
		dialogLayout.setBody(text);
		JFXDialog dialog = new JFXDialog(LoginFormController.getInstance().stackDialog, dialogLayout,
				JFXDialog.DialogTransition.CENTER);
		JFXButton btnExit = new JFXButton("Thoát");
		btnExit.setCursor(Cursor.HAND);
		btnExit.setStyle(
				"-fx-background-color: Black;-fx-background-radius: 5.0;-fx-text-fill: Red;-fx-font: 13 System; -fx-font-weight: Bold;");
		btnExit.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Platform.exit();
			}
		});
		JFXButton btnCancel = new JFXButton("Hủy");
		btnCancel.setCursor(Cursor.HAND);
		btnCancel.setStyle(
				"-fx-background-color: Black;-fx-background-radius: 5.0;-fx-text-fill: White;-fx-font: 13 System; -fx-font-weight: Bold;");
		btnCancel.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				dialog.close();
			}
		});
		dialogLayout.setActions(btnCancel, btnExit);
		dialog.show();
	}

	@FXML
	void minusAction(MouseEvent event) {
		Stage stage = (Stage) minus.getScene().getWindow();
		stage.setIconified(true);
	}

	// ===== Chuyển form Quên mật khẩu =====
	@FXML
	void switchForgotForm(ActionEvent event) {
		TranslateTransition transition = new TranslateTransition(Duration.seconds(1),
				LoginFormController.getInstance().stackPane);
		transition.setToX(-440);
		transition.play();
		transition.setOnFinished(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				try {
					LoginFormController.getInstance().fxml = FXMLLoader
							.load(getClass().getResource("/viewFXML/login/Forgot.fxml"));
					LoginFormController.getInstance().stackPane.getChildren().clear();
					LoginFormController.getInstance().stackPane.getChildren()
							.setAll(LoginFormController.getInstance().fxml);
				} catch (IOException e) {
					JOptionPane.showMessageDialog(null, e.getMessage(), "Đã xảy vấn đề", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		// - Làm mờ tấm hình Logo
		LoginFormController.getInstance().imageLogo2.setVisible(true);
		FadeTransition fade2 = new FadeTransition();
		fade2.setDuration(Duration.millis(1000));
		fade2.setNode(LoginFormController.getInstance().imageLogo2);
		fade2.setFromValue(0);
		fade2.setToValue(1);
		fade2.setCycleCount(1);
		fade2.setAutoReverse(true);
		fade2.play();
		fade2.setOnFinished(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				LoginFormController.getInstance().imageLogo1.setVisible(false);
			}
		});
		FadeTransition fade = new FadeTransition();
		fade.setDuration(Duration.millis(1000));
		fade.setNode(LoginFormController.getInstance().imageLogo1);
		fade.setFromValue(1);
		fade.setToValue(0);
		fade.setCycleCount(2);
		fade.setAutoReverse(true);
		fade.play();
		fade.setOnFinished(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				LoginFormController.getInstance().imageLogo1.setVisible(false);
				LoginFormController.getInstance().imageLogo2.setVisible(true);
			}
		});
	}

	// =====* Khởi tạo controller *=====
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// - Animation cho Text banner
		TranslateTransition translate = new TranslateTransition();
		translate.setByX(50);
		translate.setDuration(Duration.millis(1200));
		translate.setCycleCount(500);
		translate.setAutoReverse(true);
		translate.setNode(txtBanner);
		translate.play();

		// - Hiện mật khẩu ( dùng txtPass và txt thường để switch cho nhau tùy checkBox)
		txtPasswordShow.managedProperty().bind(checkBox.selectedProperty());
		txtPasswordShow.visibleProperty().bind(checkBox.selectedProperty());
		txtPasswordShow.textProperty().bindBidirectional(txtPassword.textProperty());
		txtPassword.managedProperty().bind(checkBox.selectedProperty().not());
		txtPassword.visibleProperty().bind(checkBox.selectedProperty().not());
	}
}
