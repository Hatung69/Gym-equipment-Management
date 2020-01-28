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

import javaMail.JavaMailUtil;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.taikhoan.TaiKhoanDAO;

public class ForgotController implements Initializable {
	@FXML
	private Text txtBanner;
	@FXML
	private ImageView imageLogo;
	@FXML
	private Label exit, minus;
	@FXML
	private JFXButton btnXacNhan;
	@FXML
	private JFXCheckBox checkBox, checkBox1;
	@FXML
	private JFXPasswordField txtPassword, txtPassword1;
	@FXML
	private JFXTextField txtGmail, txtCodeXacNhan, txtPasswordShow, txtPasswordShow1, txtUserName;
	@FXML
	private AnchorPane paneGmail, paneMaXN, paneMatKhau;

	double x = 0, y = 0;
	private static String codeRandom;

	// ===== Gửi mail =====
	@FXML
	void guiMailAction(ActionEvent event) {
		if (txtGmail.getText().trim().isEmpty()) {
			JFXDialogLayout dialogLayout = new JFXDialogLayout();
			dialogLayout.setStyle(
					"-fx-background-color: Orange;-fx-border-color: Black;-fx-border-width: 5 ;-fx-border-color:Black ;-fx-text-fill: White;-fx-effect: dropshadow(three-pass-box, DarkRed, 10, 0, 0, 0);");
			Image image = new Image(getClass().getResourceAsStream("/Images/JFXDialog/warning.png"));
			Label label = new Label("Kiểm tra lại thông tin", new ImageView(image));
			label.setStyle("-fx-text-fill: DarkRed;-fx-font: 20 System; -fx-font-weight: Bold;");
			dialogLayout.setHeading(label);
			Text text = new Text("Vui lòng điền Gmail đã đăng ký để lấy lại mật khẩu !");
			text.setStyle("-fx-font: 15 System");
			dialogLayout.setBody(text);
			JFXDialog dialog = new JFXDialog(LoginFormController.getInstance().stackDialog, dialogLayout,
					JFXDialog.DialogTransition.CENTER);
			dialog.show();
		} else if (!TaiKhoanDAO.gmaiIsExist(txtGmail.getText().trim())) {
			JFXDialogLayout dialogLayout = new JFXDialogLayout();
			dialogLayout.setStyle(
					"-fx-background-color: Orange;-fx-border-color: Black;-fx-border-width: 5 ;-fx-border-color:Black ;-fx-text-fill: White;-fx-effect: dropshadow(three-pass-box, DarkRed, 10, 0, 0, 0);");
			Image image = new Image(getClass().getResourceAsStream("/Images/JFXDialog/warning.png"));
			Label label = new Label("Không tồn tại Gmail", new ImageView(image));
			label.setStyle("-fx-text-fill: DarkRed;-fx-font: 20 System; -fx-font-weight: Bold;");
			dialogLayout.setHeading(label);
			Text text = new Text("Vui lòng kiểm tra lại, Gmail chưa được đăng ký !");
			text.setStyle("-fx-font: 15 System");
			dialogLayout.setBody(text);
			JFXDialog dialog = new JFXDialog(LoginFormController.getInstance().stackDialog, dialogLayout,
					JFXDialog.DialogTransition.CENTER);
			dialog.show();
		} else {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Thông báo");
			alert.setHeaderText("Đang gửi mã...Vui lòng chờ !");
			alert.setContentText("Sau khi gửi mã thành công sẽ tự động chuyển tiếp đến nhập Mã xác nhận !");
			alert.getDialogPane().lookupButton(ButtonType.OK).setDisable(true);
			alert.getDialogPane().setStyle(
					"-fx-border-color: black;-fx-background-color: Orange; -fx-font-size: 14px;-fx-font-weight: bold;");
			alert.initOwner(btnXacNhan.getScene().getWindow());
			alert.show();
			codeRandom = JavaMailUtil.getAlphaNumericString();
			JavaMailUtil.sendMail(txtGmail.getText().trim(), codeRandom);
			paneGmail.setVisible(false);
			paneMaXN.setVisible(true);
			alert.close();
		}
	}

	// ===== Xác nhận code =====
	@FXML
	void xacNhanCodeAction(ActionEvent event) {
		if (txtCodeXacNhan.getText().trim().isEmpty()) {
			JFXDialogLayout dialogLayout = new JFXDialogLayout();
			dialogLayout.setStyle(
					"-fx-background-color: Orange;-fx-border-color: Black;-fx-border-width: 5 ;-fx-border-color:Black ;-fx-text-fill: White;-fx-effect: dropshadow(three-pass-box, DarkRed, 10, 0, 0, 0);");
			Image image = new Image(getClass().getResourceAsStream("/Images/JFXDialog/warning.png"));
			Label label = new Label("Kiểm tra lại thông tin", new ImageView(image));
			label.setStyle("-fx-text-fill: DarkRed;-fx-font: 20 System; -fx-font-weight: Bold;");
			dialogLayout.setHeading(label);
			Text text = new Text("Vui lòng điền Mã xác nhận đã được gửi về Gmail của bạn !");
			text.setStyle("-fx-font: 15 System");
			dialogLayout.setBody(text);
			JFXDialog dialog = new JFXDialog(LoginFormController.getInstance().stackDialog, dialogLayout,
					JFXDialog.DialogTransition.CENTER);
			dialog.show();
		} else {
			if (txtCodeXacNhan.getText().trim().equals(codeRandom)) {
				paneMaXN.setVisible(false);
				paneMatKhau.setVisible(true);
				btnXacNhan.setDisable(false);
			} else {
				JFXDialogLayout dialogLayout = new JFXDialogLayout();
				dialogLayout.setStyle(
						"-fx-background-color: Orange;-fx-border-color: Black;-fx-border-width: 5 ;-fx-border-color:Black ;-fx-text-fill: White;-fx-effect: dropshadow(three-pass-box, DarkRed, 10, 0, 0, 0);");
				Image image = new Image(getClass().getResourceAsStream("/Images/JFXDialog/warning.png"));
				Label label = new Label("Không hợp lệ", new ImageView(image));
				label.setStyle("-fx-text-fill: DarkRed;-fx-font: 20 System; -fx-font-weight: Bold;");
				dialogLayout.setHeading(label);
				Text text = new Text("Mã xác nhận không đúng !");
				text.setStyle("-fx-font: 15 System");
				dialogLayout.setBody(text);
				JFXDialog dialog = new JFXDialog(LoginFormController.getInstance().stackDialog, dialogLayout,
						JFXDialog.DialogTransition.CENTER);
				dialog.show();
			}
		}
	}

	// ===== Xác nhận làm lại mật khẩu mới =====
	@FXML
	void confirmAction(ActionEvent event) {
		if (!txtPassword.getText().trim().equals(txtPassword1.getText().trim())) {
			JFXDialogLayout dialogLayout = new JFXDialogLayout();
			dialogLayout.setStyle(
					"-fx-background-color: Orange;-fx-border-color: Black;-fx-border-width: 5 ;-fx-border-color:Black ;-fx-text-fill: White;-fx-effect: dropshadow(three-pass-box, DarkRed, 10, 0, 0, 0);");
			Image image = new Image(getClass().getResourceAsStream("/Images/JFXDialog/warning.png"));
			Label label = new Label("Không hợp lệ", new ImageView(image));
			label.setStyle("-fx-text-fill: DarkRed;-fx-font: 20 System; -fx-font-weight: Bold;");
			dialogLayout.setHeading(label);
			Text text = new Text("Vui lòng nhập Mật khẩu mới và nhập lại Mật khẩu mới phải khớp với nhau !!");
			text.setStyle("-fx-font: 15 System");
			dialogLayout.setBody(text);
			JFXDialog dialog = new JFXDialog(LoginFormController.getInstance().stackDialog, dialogLayout,
					JFXDialog.DialogTransition.CENTER);
			dialog.show();
		} else {
			if (TaiKhoanDAO.updatePass(txtPassword.getText().trim(), txtGmail.getText().trim())) {
				JFXDialogLayout dialogLayout = new JFXDialogLayout();
				dialogLayout.setStyle(
						"-fx-background-color: Orange;-fx-border-color: Black;-fx-border-width: 5 ;-fx-border-color:Black ;-fx-text-fill: White;-fx-effect: dropshadow(three-pass-box, DarkRed, 10, 0, 0, 0);");
				Image image = new Image(getClass().getResourceAsStream("/Images/JFXDialog/tinhtinh.png"));
				Label label = new Label("Thành công", new ImageView(image));
				label.setStyle("-fx-text-fill: DarkRed;-fx-font: 20 System; -fx-font-weight: Bold;");
				dialogLayout.setHeading(label);
				Text text = new Text("Đã cập nhật lại mật khẩu !");
				text.setStyle("-fx-font: 15 System");
				dialogLayout.setBody(text);
				JFXDialog dialog = new JFXDialog(LoginFormController.getInstance().stackDialog, dialogLayout,
						JFXDialog.DialogTransition.CENTER);
				dialog.show();
			} else {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Thật vại");
				alert.setHeaderText("Không thể cập nhật thành công mật khẩu mới.");
				alert.showAndWait();
			}
		}
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

	// ===== Về màn đăng nhập =====
	@FXML
	void returnAction(ActionEvent event) {
		TranslateTransition transition = new TranslateTransition(Duration.seconds(1),
				LoginFormController.getInstance().stackPane);
		transition.setToX(0);
		transition.play();
		transition.setOnFinished(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				try {
					LoginFormController.getInstance().fxml = FXMLLoader
							.load(getClass().getResource("/viewFXML/login/Login.fxml"));
					LoginFormController.getInstance().stackPane.getChildren().clear();
					LoginFormController.getInstance().stackPane.getChildren()
							.setAll(LoginFormController.getInstance().fxml);
					LoginFormController.getInstance().imageLogo1.setVisible(true);
					LoginFormController.getInstance().imageLogo2.setVisible(false);
				} catch (IOException e) {
					JOptionPane.showMessageDialog(null, e.getMessage(), "Đã xảy vấn đề", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		// - Làm mờ tấm hình Logo
		LoginFormController.getInstance().imageLogo1.setVisible(true);
		FadeTransition fade = new FadeTransition();
		fade.setDuration(Duration.millis(1000));
		fade.setNode(LoginFormController.getInstance().imageLogo1);
		fade.setFromValue(0);
		fade.setToValue(1);
		fade.setCycleCount(1);
		fade.setAutoReverse(true);
		fade.play();
		fade.setOnFinished(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				LoginFormController.getInstance().imageLogo2.setVisible(false);
			}
		});
		FadeTransition fade2 = new FadeTransition();
		fade2.setDuration(Duration.millis(1000));
		fade2.setNode(LoginFormController.getInstance().imageLogo2);
		fade2.setFromValue(1);
		fade2.setToValue(0);
		fade2.setCycleCount(1);
		fade2.setAutoReverse(true);
		fade2.play();
		fade2.setOnFinished(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				LoginFormController.getInstance().imageLogo1.setVisible(true);
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

		txtPasswordShow1.managedProperty().bind(checkBox1.selectedProperty());
		txtPasswordShow1.visibleProperty().bind(checkBox1.selectedProperty());
		txtPasswordShow1.textProperty().bindBidirectional(txtPassword1.textProperty());
		txtPassword1.managedProperty().bind(checkBox1.selectedProperty().not());
		txtPassword1.visibleProperty().bind(checkBox1.selectedProperty().not());
	}

}
