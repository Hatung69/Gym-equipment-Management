package application;

import java.io.IOException;

import javax.swing.JOptionPane;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Program_Gym extends Application {
	@Override
	public void start(Stage primaryStage) {
		Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("/viewFXML/login/LoginForm.fxml"));
			Scene scene = new Scene(root);
			scene.setFill(Color.TRANSPARENT);
			primaryStage.setScene(scene);
			primaryStage.initStyle(StageStyle.TRANSPARENT);
			primaryStage.getIcons().add(new Image("file:logoApp.png"));
			primaryStage.setTitle("Quản lý thiết bị Gym");
			primaryStage.show();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Đã xảy vấn đề", JOptionPane.ERROR_MESSAGE);
		}
	}
	public static void main(String[] args) {
		launch(args);
	}
}
