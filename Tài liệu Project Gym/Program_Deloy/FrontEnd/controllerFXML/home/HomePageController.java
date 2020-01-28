package controllerFXML.home;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerSlideCloseTransition;

import controllerFXML.login.LoginController;
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
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import model.nhanvien.NhanVienDAO;

public class HomePageController implements Initializable {
	private static HomePageController instance;

	public static HomePageController getInstance() {
		return instance;
	}

	public HomePageController() {
		instance = this;
	}

	// -----------------------------------------------
	@FXML
	public StackPane mainStackPane, stackPaneLoadLayout;
	@FXML
	private Label minus, exit;
	@FXML
	private ImageView imageLogoHome;
	@FXML
	private JFXHamburger hamburger;
	@FXML
	private JFXDrawer drawer;
	@FXML
	public AnchorPane paneHome, anchorHomeLayout, titlePane;
	@FXML
	private Label lblTenNV, lblTenNV2;
	@FXML
	public StackPane stackPaneThietBi, stackPaneBaoTri, stackPaneThongKe, stackPaneTaiKhoan;

	double x = 0, y = 0;
	private AnchorPane anchorPane;

	// ===== NodetList Thoát =====
	@FXML
	void nodetListExitAction(ActionEvent event) {
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
		JFXDialog dialog = new JFXDialog(mainStackPane, dialogLayout, JFXDialog.DialogTransition.CENTER);
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

	// ===== NodeList Thông tin phần mềm =====
	@FXML
	void nodetListInforAction(ActionEvent event) throws IOException {
		anchorPane = FXMLLoader.load(getClass().getResource("/viewFXML/home/dialogInforApp.fxml"));
		new JFXDialog(mainStackPane, anchorPane, JFXDialog.DialogTransition.CENTER).show();
	}

	// ===== NodeList Đăng xuất =====
	@FXML
	void nodetListLogoutAction(ActionEvent event) {
		JFXDialogLayout dialogLayout = new JFXDialogLayout();
		dialogLayout.setStyle(
				"-fx-background-color: Orange;-fx-border-color: Black;-fx-border-width: 5 ;-fx-border-color:Black ;-fx-text-fill: White;-fx-effect: dropshadow(three-pass-box, DarkRed, 10, 0, 0, 0);");
		Image image = new Image(getClass().getResourceAsStream("/Images/JFXDialog/logout.png"));
		Label label = new Label("Đăng xuất", new ImageView(image));
		label.setStyle("-fx-text-fill: DarkRed;-fx-font: 20 System; -fx-font-weight: Bold;");
		dialogLayout.setHeading(label);
		Text text = new Text("Bạn muốn Đăng xuất tài khoản?");
		text.setStyle("-fx-font: 15 System");
		dialogLayout.setBody(text);
		JFXDialog dialog = new JFXDialog(mainStackPane, dialogLayout, JFXDialog.DialogTransition.CENTER);
		JFXButton btnExit = new JFXButton("Đồng ý");
		btnExit.setCursor(Cursor.HAND);
		btnExit.setStyle(
				"-fx-background-color: Black;-fx-background-radius: 5.0;-fx-text-fill: Red;-fx-font: 13 System; -fx-font-weight: Bold;");
		btnExit.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					Stage stage = (Stage) minus.getScene().getWindow();
					stage.close();
					Stage loginStage = new Stage(StageStyle.TRANSPARENT);
					Parent root;
					root = FXMLLoader.load(getClass().getResource("/viewFXML/login/LoginForm.fxml"));
					Scene scene = new Scene(root);
					scene.setFill(Color.TRANSPARENT);
					loginStage.setScene(scene);
					loginStage.initStyle(StageStyle.TRANSPARENT);
					loginStage.getIcons().add(new Image("file:logoApp.png"));
					loginStage.setTitle("Quản lý thiết bị Gym");
					loginStage.show();
				} catch (IOException e) {
					JOptionPane.showMessageDialog(null, e.getMessage(), "Đã xảy vấn đề", JOptionPane.ERROR_MESSAGE);
				}
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

	// ===== Về Home =====
	@FXML
	void backHome(MouseEvent event) throws IOException {
		stackPaneThietBi.setVisible(false);
		stackPaneBaoTri.setVisible(false);
		stackPaneThongKe.setVisible(false);
		stackPaneTaiKhoan.setVisible(false);
		anchorHomeLayout.setVisible(true);
	}

	// ===== Mở d.s thiết bị =====
	@FXML
	void openQLThietBi(ActionEvent event) {
		stackPaneThietBi.setVisible(true);
		stackPaneBaoTri.setVisible(false);
		stackPaneThongKe.setVisible(false);
		stackPaneTaiKhoan.setVisible(false);
		anchorHomeLayout.setVisible(false);
	}

	// ===== Mở d.s tài khoản =====
	@FXML
	void openTaiKhoan(MouseEvent event) {
		stackPaneThietBi.setVisible(false);
		stackPaneBaoTri.setVisible(false);
		stackPaneThongKe.setVisible(false);
		stackPaneTaiKhoan.setVisible(true);
		anchorHomeLayout.setVisible(false);
	}

	// ===== Tắt Form và ẩn xuống =====
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
		JFXDialog dialog = new JFXDialog(mainStackPane, dialogLayout, JFXDialog.DialogTransition.CENTER);
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
		imageLogoHome.setOpacity(0.6);
	}

	@FXML
	void resetOpacity(MouseEvent event) {
		imageLogoHome.setOpacity(1);
	}

	// =====* Khởi tạo Controller *=====
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// - Khởi tạo Label tên nhan viên
		lblTenNV.setText(NhanVienDAO.getTenNV(LoginController.getInstance().txtUser.getText()));
		lblTenNV2.setText(NhanVienDAO.getTenNV(LoginController.getInstance().txtUser.getText()));

		// - Khởi tạo cho DashBoard
		try {
			AnchorPane box = FXMLLoader.load(getClass().getResource("/viewFXML/home/dashBoard.fxml"));
			drawer.setSidePane(box);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Đã xảy vấn đề", JOptionPane.ERROR_MESSAGE);
		}

		// - Hamburger
		HamburgerSlideCloseTransition arrowBasicTransition = new HamburgerSlideCloseTransition(hamburger);
		arrowBasicTransition.setRate(-1);
		
		hamburger.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				arrowBasicTransition.setRate(arrowBasicTransition.getRate() * -1);
				arrowBasicTransition.play();
				if (drawer.isOpened()) {
					drawer.close();
				} else {
					drawer.open();
				}
			}
		});

		// - Animation cho Text banner
		TranslateTransition translate = new TranslateTransition();
		translate.setByX(350);
		translate.setDuration(Duration.millis(3000));
		translate.setCycleCount(500);
		translate.setAutoReverse(true);
		translate.setNode(titlePane);
		translate.play();

		// --------------- LOADING... StackPane Dashboard-----
		try {
			Parent parent = FXMLLoader.load(getClass().getResource("/viewFXML/equipment/Equipment.fxml"));
			stackPaneThietBi.getChildren().add(parent);
			Parent parent2=FXMLLoader.load(getClass().getResource("/viewFXML/maintenance/Maintenance.fxml"));
			stackPaneBaoTri.getChildren().add(parent2);
			Parent parent3=FXMLLoader.load(getClass().getResource("/viewFXML/account/Account.fxml"));
			stackPaneTaiKhoan.getChildren().add(parent3);
			Parent parent4=FXMLLoader.load(getClass().getResource("/viewFXML/statistical/Statistical.fxml"));
			stackPaneThongKe.getChildren().add(parent4);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Đã xảy vấn đề", JOptionPane.ERROR_MESSAGE);
		}
	}
}
