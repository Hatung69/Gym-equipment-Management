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
import model.baitap.BaiTap;
import model.baitap.BaiTapDAO;

public class dialogBaiTapController implements Initializable {

	@FXML
	private JFXListView<Label> listView;

	@FXML
	private Text text;

	@FXML
	void chooseAction(ActionEvent event) {
		if (listView.getSelectionModel().isEmpty()) {
			text.setText("Vui lòng chọn 1 Bài tập");
		} else {
			if (EquipmentController.getInstance().paneThemMoi.isVisible()) {
				EquipmentController.getInstance().txtBaiTap
						.setText(listView.getSelectionModel().getSelectedItem().getText());
				EquipmentController.getInstance().dialogBaiTap.close();
			} else if (EquipmentController.getInstance().paneChinhSua.isVisible()) {
				EquipmentController.getInstance().txtUpdBaiTap
						.setText(listView.getSelectionModel().getSelectedItem().getText());
				EquipmentController.getInstance().dialogBaiTap.close();
			}
		}
	}

	// ===== Tắt dialog =====
	@FXML
	void closeDialog(MouseEvent event) {
		EquipmentController.getInstance().dialogBaiTap.close();
	}

	// ===== Lấy data double Click =====
	@FXML
	void getItem(MouseEvent event) {
		if (event.getClickCount() == 2) {
			if (EquipmentController.getInstance().paneThemMoi.isVisible()) {
				EquipmentController.getInstance().txtBaiTap
						.setText(listView.getSelectionModel().getSelectedItem().getText());
				EquipmentController.getInstance().dialogBaiTap.close();
			} else if (EquipmentController.getInstance().paneChinhSua.isVisible()) {
				EquipmentController.getInstance().txtUpdBaiTap
						.setText(listView.getSelectionModel().getSelectedItem().getText());
				EquipmentController.getInstance().dialogBaiTap.close();
			}
		}
	}

	// ===== Chuyển qua Tab Loại TB =====
	@FXML
	void seeDetailAction(ActionEvent event) {
		EquipmentController.getInstance().tabPane.getSelectionModel().select(3);
		EquipmentController.getInstance().dialogBaiTap.close();
	}

	// ===== * Khởi tạo controller * =====
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		for (BaiTap index : BaiTapDAO.getListBaiTap()) {
			try {
				Label label = new Label(index.getMaBaiTap() + "-" + index.getTenBaiTap());
				label.setStyle("  -fx-text-fill: Black; -fx-font-size: 16.0;-fx-font-weight: Bold;");
				label.setGraphic(new ImageView(
						new Image(new FileInputStream("FrontEnd/Images/Home/icons8_two_tickets_30px.png"))));
				listView.getItems().add(label);
			} catch (FileNotFoundException e) {
				JOptionPane.showMessageDialog(null, e.getMessage(), "Đã xảy vấn đề rồi", JOptionPane.ERROR_MESSAGE);
			}
		}

	}

}
