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
import model.thuonghieu.ThuongHieu;
import model.thuonghieu.ThuongHieuDAO;

public class dialogThuongHieuController implements Initializable {

	@FXML
	private JFXListView<Label> listView;

	@FXML
	private Text text;

	@FXML
	void chooseAction(ActionEvent event) {
		if (listView.getSelectionModel().isEmpty()) {
			text.setText("Xin hãy chọn 1 Thương hiệu!");
		}else {
			if (EquipmentController.getInstance().paneThemMoi.isVisible()) {
				EquipmentController.getInstance().txtThuongHieu.setText(listView.getSelectionModel().getSelectedItem().getText());
				EquipmentController.getInstance().dialogThuongHieu.close();
			}else if (EquipmentController.getInstance().paneChinhSua.isVisible()) {
				EquipmentController.getInstance().txtUpdThuongHieu.setText(listView.getSelectionModel().getSelectedItem().getText());
				EquipmentController.getInstance().dialogThuongHieu.close();
			}
		}
	}

	// ===== Tắt dialog =====
	@FXML
	void closeDialog(MouseEvent event) {
		EquipmentController.getInstance().dialogThuongHieu.close();
	}

	//===== Lấy data double Click =====
	@FXML
	void getItem(MouseEvent event) {
		if (event.getClickCount() == 2) {
			if (EquipmentController.getInstance().paneThemMoi.isVisible()) {
				EquipmentController.getInstance().txtThuongHieu.setText(listView.getSelectionModel().getSelectedItem().getText());
				EquipmentController.getInstance().dialogThuongHieu.close();
			}else if (EquipmentController.getInstance().paneChinhSua.isVisible()) {
				EquipmentController.getInstance().txtUpdThuongHieu.setText(listView.getSelectionModel().getSelectedItem().getText());
				EquipmentController.getInstance().dialogThuongHieu.close();
			}
		}
	}

	//===== Chuyển qua Tab Loại TB =====
	@FXML
	void seeDetailAction(ActionEvent event) {
		EquipmentController.getInstance().tabPane.getSelectionModel().select(2);
		EquipmentController.getInstance().dialogThuongHieu.close();
	}

	//===== * Khởi tạo controller * =====
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		for (ThuongHieu index : ThuongHieuDAO.getListThuongHieu()) {
			try {
				Label label = new Label(index.getMaThuongHieu() + "-" + index.getTenThuongHieu());
				label.setStyle("  -fx-text-fill: Black; -fx-font-size: 16.0;-fx-font-weight: Bold;");
				label.setGraphic(new ImageView(
						new Image(new FileInputStream("FrontEnd/Images/Home/icons8_warranty_card_30px.png"))));
				listView.getItems().add(label);
			} catch (FileNotFoundException e) {
				JOptionPane.showMessageDialog(null, e.getMessage(), "Đã xảy vấn đề rồi", JOptionPane.ERROR_MESSAGE);
			}
		}

	}

}
