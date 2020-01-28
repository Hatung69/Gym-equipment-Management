package controllerFXML.equipment;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import com.jfoenix.controls.JFXListView;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import model.chatlieu.ChatLieu;
import model.chatlieu.ChatLieuDAO;

public class dialogChatLieuController implements Initializable {
	private static dialogChatLieuController instance;

	public static dialogChatLieuController getInstance() {
		return instance;
	}

	public dialogChatLieuController() {
		instance = this;
	}

	// -------------------------------------------------------

	@FXML
	private JFXListView<Label> listView;
	@FXML
	private Text text;

	// ===== Chọn =====
	@FXML
	void chooseAction(ActionEvent event) {
		if (listView.getSelectionModel().isEmpty()) {
			text.setText("Xin hãy chọn 1 Chất liệu cho thiết bị!");
		} else {
			if (EquipmentController.getInstance().paneThemMoi.isVisible()) {
				EquipmentController.getInstance().txtChatLieu.setText(listView.getSelectionModel().getSelectedItem().getText());
				EquipmentController.getInstance().dialogChatLieu.close();
			}else if (EquipmentController.getInstance().paneChinhSua.isVisible()) {
				EquipmentController.getInstance().txtupdChatLieu.setText(listView.getSelectionModel().getSelectedItem().getText());
				EquipmentController.getInstance().dialogChatLieu.close();
			}
		}
	}

	// ===== Tắt dialog =====
	@FXML
	void closeDialog(MouseEvent event) {
		EquipmentController.getInstance().dialogChatLieu.close();
	}

	// ===== Lấy data double Click =====
	@FXML
	void getItem(MouseEvent event) {
		if (event.getClickCount() == 2) {
			if (EquipmentController.getInstance().paneThemMoi.isVisible()) {
				EquipmentController.getInstance().txtChatLieu.setText(listView.getSelectionModel().getSelectedItem().getText());
				EquipmentController.getInstance().dialogChatLieu.close();
			}else if (EquipmentController.getInstance().paneChinhSua.isVisible()) {
				EquipmentController.getInstance().txtupdChatLieu.setText(listView.getSelectionModel().getSelectedItem().getText());
				EquipmentController.getInstance().dialogChatLieu.close();
			}
		}
	}

	//===== Load data lên ListView =====
		public void loadDataChatLieu() {
			for (ChatLieu index : ChatLieuDAO.getListChatLieu()) {
				try {
					Label label = new Label(index.getMaChatLieu() + "-" + index.getTenChatLieu());
					label.setStyle("  -fx-text-fill: Black; -fx-font-size: 16.0;-fx-font-weight: Bold;");
					label.setGraphic(
							new ImageView(new Image(new FileInputStream("FrontEnd/Images/Home/icons8_puzzle_30px.png"))));
					listView.getItems().add(label);
				} catch (FileNotFoundException e) {
					JOptionPane.showMessageDialog(null, e.getMessage(), "Đã xảy vấn đề rồi", JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	
	// =====* Khởi tạo Controller *=====
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		loadDataChatLieu();
	}

}
