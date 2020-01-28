package controllerFXML.home;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.util.Duration;

public class dashboardController implements Initializable {
	@FXML
	private Label hour, minute, seconds;
	@FXML
	private JFXButton btnThietBi;

	@FXML
	void openThietBi(ActionEvent event) {
		HomePageController.getInstance().stackPaneThietBi.setVisible(true);
		HomePageController.getInstance().stackPaneBaoTri.setVisible(false);
		HomePageController.getInstance().stackPaneThongKe.setVisible(false);
		HomePageController.getInstance().stackPaneTaiKhoan.setVisible(false);
		HomePageController.getInstance().anchorHomeLayout.setVisible(false);
	}

	@FXML
	void openBaoTri(ActionEvent event) {
		HomePageController.getInstance().stackPaneThietBi.setVisible(false);
		HomePageController.getInstance().stackPaneBaoTri.setVisible(true);
		HomePageController.getInstance().stackPaneThongKe.setVisible(false);
		HomePageController.getInstance().stackPaneTaiKhoan.setVisible(false);
		HomePageController.getInstance().anchorHomeLayout.setVisible(false);
	}

	@FXML
	void openthongKe(ActionEvent event) {
		HomePageController.getInstance().stackPaneThietBi.setVisible(false);
		HomePageController.getInstance().stackPaneBaoTri.setVisible(false);
		HomePageController.getInstance().stackPaneThongKe.setVisible(true);
		HomePageController.getInstance().stackPaneTaiKhoan.setVisible(false);
		HomePageController.getInstance().anchorHomeLayout.setVisible(false);
	}

	@FXML
	void openTaiKhoan(ActionEvent event) {
		HomePageController.getInstance().stackPaneThietBi.setVisible(false);
		HomePageController.getInstance().stackPaneBaoTri.setVisible(false);
		HomePageController.getInstance().stackPaneThongKe.setVisible(false);
		HomePageController.getInstance().stackPaneTaiKhoan.setVisible(true);
		HomePageController.getInstance().anchorHomeLayout.setVisible(false);
	}

	// =====* Khởi tạo Controller *=====
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
			DateTimeFormatter formatterHH = DateTimeFormatter.ofPattern("HH");
			DateTimeFormatter formattermm = DateTimeFormatter.ofPattern("mm");
			DateTimeFormatter formatterss = DateTimeFormatter.ofPattern("ss");
			hour.setText(LocalDateTime.now().format(formatterHH));
			minute.setText(LocalDateTime.now().format(formattermm));
			seconds.setText(LocalDateTime.now().format(formatterss));
		}), new KeyFrame(Duration.seconds(1)));
		clock.setCycleCount(Animation.INDEFINITE);
		clock.play();
	}

}
