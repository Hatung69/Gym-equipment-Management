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
import model.loaiTB.LoaiThietBi;
import model.loaiTB.LoaiThietBiDAO;

public class dialogLoaiTBController implements Initializable {
	private static dialogLoaiTBController instance;

	public static dialogLoaiTBController getInstance() {
		return instance;
	}

	public dialogLoaiTBController() {
		instance = this;
	}

	// -------------------------------------------------------
	@FXML
	private JFXListView<Label> listView;

	@FXML
	private Text text;

	@FXML
	void chooseAction(ActionEvent event) {
		if (listView.getSelectionModel().isEmpty()) {
			text.setText("Xin hãy chọn 1 Loại thiết bị!");
		} else {
			if (EquipmentController.getInstance().paneThemMoi.isVisible()) {
				EquipmentController.getInstance().txtLoaiThietBi
						.setText(listView.getSelectionModel().getSelectedItem().getText());
				EquipmentController.getInstance().dialogLoaiThietBi.close();
			} else if (EquipmentController.getInstance().paneChinhSua.isVisible()) {
				EquipmentController.getInstance().txtupdLoaiThietBi
						.setText(listView.getSelectionModel().getSelectedItem().getText());
				EquipmentController.getInstance().dialogLoaiThietBi.close();
			}
		}
	}

	// ===== Tắt dialog =====
	@FXML
	void closeDialog(MouseEvent event) {
		EquipmentController.getInstance().dialogLoaiThietBi.close();
	}

	// ===== Lấy data double Click =====
	@FXML
	void getItem(MouseEvent event) {
		if (event.getClickCount() == 2) {
			if (EquipmentController.getInstance().paneThemMoi.isVisible()) {
				EquipmentController.getInstance().txtLoaiThietBi
						.setText(listView.getSelectionModel().getSelectedItem().getText());
				EquipmentController.getInstance().dialogLoaiThietBi.close();
			} else if (EquipmentController.getInstance().paneChinhSua.isVisible()) {
				EquipmentController.getInstance().txtupdLoaiThietBi
						.setText(listView.getSelectionModel().getSelectedItem().getText());
				EquipmentController.getInstance().dialogLoaiThietBi.close();
			}
		}
	}

	// ===== Chuyển qua Tab Loại TB =====
	@FXML
	void seeDetailAction(ActionEvent event) {
		EquipmentController.getInstance().tabPane.getSelectionModel().select(1);
		EquipmentController.getInstance().dialogLoaiThietBi.close();
	}
	
	//===== Load data lên ListView =====
	public void loadDataLoaiTB() {
		for (LoaiThietBi index : LoaiThietBiDAO.getListLoaiTB()) {
			try {
				Label label = new Label(index.getMaLoai() + "-" + index.getTenLoai());
				label.setStyle("  -fx-text-fill: Black; -fx-font-size: 16.0;-fx-font-weight: Bold;");
				label.setGraphic(
						new ImageView(new Image(new FileInputStream("FrontEnd/Images/Home/icons8_barbell_30px.png"))));
				listView.getItems().add(label);
			} catch (FileNotFoundException e) {
				JOptionPane.showMessageDialog(null, e.getMessage(), "Đã xảy vấn đề rồi", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	// ===== * Khởi tạo controller * =====
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		loadDataLoaiTB();
	}
}
