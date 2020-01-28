package controllerFXML.maintenance;

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
import model.loaiBT.LoaiBaoTri;
import model.loaiBT.LoaiBaoTriDAO;

public class dialobLoaiBaoTriController implements Initializable {
	@FXML
	private JFXListView<Label> listView;

	@FXML
	private Text text;

	@FXML
	void chooseAction(ActionEvent event) {
		if (listView.getSelectionModel().isEmpty()) {
			text.setText("Xin hãy chọn 1 Loại bảo trì");
		} else {
			MaintenanceController.getInstance().txtUpdLoaiBT.setText(listView.getSelectionModel().getSelectedItem().getText());
			MaintenanceController.getInstance().dialogLoaiBaoTri.close();
		}
	}

	// ===== Tắt dialog =====
	@FXML
	void closeDialog(MouseEvent event) {
		MaintenanceController.getInstance().dialogLoaiBaoTri.close();
	}

	// ===== Lấy data double Click =====
	@FXML
	void getItem(MouseEvent event) {
		if (event.getClickCount() == 2) {
			MaintenanceController.getInstance().txtUpdLoaiBT.setText(listView.getSelectionModel().getSelectedItem().getText());
			MaintenanceController.getInstance().dialogLoaiBaoTri.close();
		}
	}

	// ===== Chuyển qua Tab Loại TB =====
	@FXML
	void seeDetailAction(ActionEvent event) {
		MaintenanceController.getInstance().tabPane.getSelectionModel().select(1);
		MaintenanceController.getInstance().dialogLoaiBaoTri.close();
	}

	// ===== * Khởi tạo controller * =====
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		for (LoaiBaoTri index : LoaiBaoTriDAO.getListLoaiBT()) {
			try {
				Label label = new Label(index.getMaLoaiBT() + "-" + index.getTenLoaiBT());
				label.setStyle("  -fx-text-fill: Black; -fx-font-size: 16.0;-fx-font-weight: Bold;");
				label.setGraphic(new ImageView(
						new Image(new FileInputStream("FrontEnd/Images/Home/icons8_maintenance_30px.png"))));
				listView.getItems().add(label);
			} catch (FileNotFoundException e) {
				JOptionPane.showMessageDialog(null, e.getMessage(), "Đã xảy vấn đề rồi", JOptionPane.ERROR_MESSAGE);
			}
		}

	}
}
