package controllerFXML.equipment;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

import controllerFXML.home.HomePageController;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import model.baitap.BaiTap;
import model.baitap.BaiTapDAO;

public class BaiTapController implements Initializable {
	@FXML
	private TableView<BaiTap> tableBaiTap;
	@FXML
	private TableColumn<BaiTap, String> colMaBaiTap, colTenBaiTap, colMoTa;
	@FXML
	private JFXTextField txtTimKiem;
	@FXML
	private AnchorPane paneThongTinChiTiet, paneThemBaiTap, paneChinhSua;
	// ---Infor
	@FXML
	private JFXTextField txtInforMaBaiTap, txtInforTenBaiTap;
	@FXML
	private JFXTextArea txtInforMoTa;
	// ---Add
	@FXML
	private JFXTextField txtAddMaBaiTap, txtAddTenBaiTap;
	@FXML
	private JFXTextArea txtAddMoTa;
	// ---Update
	@FXML
	private JFXTextField txtUpdMaBaiTap, txtUpdTenBaiTap;
	@FXML
	private JFXTextArea txtUpdMoTa;

	@FXML
	private JFXButton btnThemMoi, btnChinhSua;

	private ObservableList<BaiTap> listBaiTap;

	// ===== Lấy data từ table =====
	@FXML
	void clickGetItem(MouseEvent event) {
		if (event.getClickCount() == 1) {
			if (tableBaiTap.getSelectionModel().getSelectedItem() == null) {
				return;
			} else {
				if (paneThongTinChiTiet.isVisible()) {
					txtInforMaBaiTap.setText(tableBaiTap.getSelectionModel().getSelectedItem().getMaBaiTap());
					txtInforTenBaiTap.setText(tableBaiTap.getSelectionModel().getSelectedItem().getTenBaiTap());
					txtInforMoTa.setText(tableBaiTap.getSelectionModel().getSelectedItem().getMoTa());
				} else if (paneChinhSua.isVisible()) {
					txtUpdMaBaiTap.setText(tableBaiTap.getSelectionModel().getSelectedItem().getMaBaiTap());
					txtUpdTenBaiTap.setText(tableBaiTap.getSelectionModel().getSelectedItem().getTenBaiTap());
					txtUpdMoTa.setText(tableBaiTap.getSelectionModel().getSelectedItem().getMoTa());
				}
			}
		}
	}

	// ===== Trở lại pane Thông tin =====
	@FXML
	void backPaneThongTin(ActionEvent event) {
		if (paneThemBaiTap.isVisible() || paneChinhSua.isVisible()) {
			paneThemBaiTap.setVisible(false);
			paneChinhSua.setVisible(false);
			paneThongTinChiTiet.setVisible(true);
			btnChinhSua.setDisable(false);
			btnThemMoi.setDisable(false);
		} else {
			return;
		}
	}

	// ===== Làm sạch txt add =====
	@FXML
	void clearTextAddAction(ActionEvent event) {
		txtAddMaBaiTap.clear();
		txtAddTenBaiTap.clear();
		txtAddMoTa.clear();
	}

	// ===== Làm sạch txt update =====
	@FXML
	void clearTextUpdAction(ActionEvent event) {
		txtUpdMaBaiTap.clear();
		txtUpdTenBaiTap.clear();
		txtUpdMoTa.clear();
	}

	// ooooooooooooo THÊM MỚI ooooooo
	@FXML
	void themVaoAction(ActionEvent event) {
		if (BaiTapDAO.maLBTIsExist(txtAddMaBaiTap.getText().trim())) {
			JFXDialogLayout dialogLayout = new JFXDialogLayout();
			dialogLayout.setStyle(
					"-fx-background-color: Orange;-fx-border-color: Black;-fx-border-width: 5 ;-fx-border-color:Black ;-fx-text-fill: White;-fx-effect: dropshadow(three-pass-box, DarkRed, 10, 0, 0, 0);");
			Image image = new Image(getClass().getResourceAsStream("/Images/JFXDialog/information.png"));
			Label label = new Label("Mã bài tập đã tồn tại", new ImageView(image));
			label.setStyle("-fx-text-fill: DarkRed;-fx-font: 20 System; -fx-font-weight: Bold;");
			dialogLayout.setHeading(label);
			Text text = new Text("Vui lòng nhập mã khác !");
			text.setStyle("-fx-font: 15 System");
			dialogLayout.setBody(text);
			JFXDialog dialog = new JFXDialog(HomePageController.getInstance().mainStackPane, dialogLayout,
					JFXDialog.DialogTransition.CENTER);
			dialog.show();
			return;
		} else if (txtAddMaBaiTap.getText().trim().isEmpty() || txtAddTenBaiTap.getText().trim().isEmpty()) {
			JFXDialogLayout dialogLayout = new JFXDialogLayout();
			dialogLayout.setStyle(
					"-fx-background-color: Orange;-fx-border-color: Black;-fx-border-width: 5 ;-fx-border-color:Black ;-fx-text-fill: White;-fx-effect: dropshadow(three-pass-box, DarkRed, 10, 0, 0, 0);");
			Image image = new Image(getClass().getResourceAsStream("/Images/JFXDialog/warning.png"));
			Label label = new Label("Kiểm tra lại thông tin", new ImageView(image));
			label.setStyle("-fx-text-fill: DarkRed;-fx-font: 20 System; -fx-font-weight: Bold;");
			dialogLayout.setHeading(label);
			Text text = new Text("Vui lòng cung cấp đầy đủ thông tin các giá trị!");
			text.setStyle("-fx-font: 15 System");
			dialogLayout.setBody(text);
			JFXDialog dialog = new JFXDialog(HomePageController.getInstance().mainStackPane, dialogLayout,
					JFXDialog.DialogTransition.CENTER);
			dialog.show();
			return;
		} else {
			if (txtAddMoTa.getText().trim().isEmpty()) {
				txtAddMoTa.setText("Chưa xác định");
			}
			BaiTapDAO.themBaiTap(txtAddMaBaiTap.getText().trim(), txtAddTenBaiTap.getText().trim(),
					txtAddMoTa.getText().trim());
			txtAddMaBaiTap.clear();
			// Load data..........
			listBaiTap = BaiTapDAO.getListBTObser();
			tableBaiTap.setItems(listBaiTap);
			searchRealTime();
		}
	}

	// ooooooooooooo CẬP NHẬT Loại thiết bị ooooooo
	@FXML
	void capNhatAction(ActionEvent event) {
		if (txtUpdMaBaiTap.getText().trim().isEmpty()) {
			JFXDialogLayout dialogLayout = new JFXDialogLayout();
			dialogLayout.setStyle(
					"-fx-background-color: Orange;-fx-border-color: Black;-fx-border-width: 5 ;-fx-border-color:Black ;-fx-text-fill: White;-fx-effect: dropshadow(three-pass-box, DarkRed, 10, 0, 0, 0);");
			Image image = new Image(getClass().getResourceAsStream("/Images/JFXDialog/warning.png"));
			Label label = new Label("Chọn Bài tập cần cập nhật", new ImageView(image));
			label.setStyle("-fx-text-fill: DarkRed;-fx-font: 20 System; -fx-font-weight: Bold;");
			dialogLayout.setHeading(label);
			Text text = new Text("Bạn có thể Click vào Danh sách trên để chọn Bài tập cần sửa !");
			text.setStyle("-fx-font: 15 System");
			dialogLayout.setBody(text);
			JFXDialog dialog = new JFXDialog(HomePageController.getInstance().mainStackPane, dialogLayout,
					JFXDialog.DialogTransition.CENTER);
			dialog.show();
			return;
		} else if (txtUpdTenBaiTap.getText().trim().isEmpty()) {
			JFXDialogLayout dialogLayout = new JFXDialogLayout();
			dialogLayout.setStyle(
					"-fx-background-color: Orange;-fx-border-color: Black;-fx-border-width: 5 ;-fx-border-color:Black ;-fx-text-fill: White;-fx-effect: dropshadow(three-pass-box, DarkRed, 10, 0, 0, 0);");
			Image image = new Image(getClass().getResourceAsStream("/Images/JFXDialog/warning.png"));
			Label label = new Label("Kiểm tra lại thông tin", new ImageView(image));
			label.setStyle("-fx-text-fill: DarkRed;-fx-font: 20 System; -fx-font-weight: Bold;");
			dialogLayout.setHeading(label);
			Text text = new Text("Vui lòng cung cấp đầy đủ thông tin các giá trị!");
			text.setStyle("-fx-font: 15 System");
			dialogLayout.setBody(text);
			JFXDialog dialog = new JFXDialog(HomePageController.getInstance().mainStackPane, dialogLayout,
					JFXDialog.DialogTransition.CENTER);
			dialog.show();
			return;
		} else {
			if (txtUpdMoTa.getText().trim().isEmpty() || txtUpdMoTa == null) {
				txtUpdMoTa.setText("Chưa xác định");
			}
			BaiTapDAO.capNhatBaiTap(txtUpdMaBaiTap.getText().trim(), txtUpdTenBaiTap.getText().trim(),
					txtUpdMoTa.getText().trim());
			// Load data..........
			listBaiTap = BaiTapDAO.getListBTObser();
			tableBaiTap.setItems(listBaiTap);
			searchRealTime();
		}
	}

	// oooooooooooo XÓA BỎ oooooooooooo
	@FXML
	void xoaBoAction(ActionEvent event) {
		if (tableBaiTap.getSelectionModel().getSelectedItem() == null) {
			JFXDialogLayout dialogLayout = new JFXDialogLayout();
			dialogLayout.setStyle(
					"-fx-background-color: Orange;-fx-border-color: Black;-fx-border-width: 5 ;-fx-border-color:Black ;-fx-text-fill: White;-fx-effect: dropshadow(three-pass-box, DarkRed, 10, 0, 0, 0);");
			Image image = new Image(getClass().getResourceAsStream("/Images/JFXDialog/warning.png"));
			Label label = new Label("Chọn thiết bị", new ImageView(image));
			label.setStyle("-fx-text-fill: DarkRed;-fx-font: 20 System; -fx-font-weight: Bold;");
			dialogLayout.setHeading(label);
			Text text = new Text("Vui lòng chọn 1 bài tập danh sách để Xóa!");
			text.setStyle("-fx-font: 15 System");
			dialogLayout.setBody(text);
			JFXDialog dialog = new JFXDialog(HomePageController.getInstance().mainStackPane, dialogLayout,
					JFXDialog.DialogTransition.CENTER);
			dialog.show();
			return;
		} else {
			JFXDialogLayout dialogLayout = new JFXDialogLayout();
			dialogLayout.setStyle(
					"-fx-background-color: Orange;-fx-border-color: Black;-fx-border-width: 5 ;-fx-border-color:Black ;-fx-text-fill: White;-fx-effect: dropshadow(three-pass-box, DarkRed, 10, 0, 0, 0);");
			Image image = new Image(getClass().getResourceAsStream("/Images/JFXDialog/warning.png"));
			Label label = new Label("Xóa bỏ", new ImageView(image));
			label.setStyle("-fx-text-fill: DarkRed;-fx-font: 20 System; -fx-font-weight: Bold;");
			dialogLayout.setHeading(label);
			Text text = new Text(
					"Bạn muốn Xóa bài tập : " + tableBaiTap.getSelectionModel().getSelectedItem().getTenBaiTap());
			text.setStyle("-fx-font: 15 System");
			dialogLayout.setBody(text);
			JFXDialog dialog = new JFXDialog(HomePageController.getInstance().mainStackPane, dialogLayout,
					JFXDialog.DialogTransition.CENTER);
			JFXButton btnExit = new JFXButton("Xóa bỏ");
			btnExit.setCursor(Cursor.HAND);
			btnExit.setStyle(
					"-fx-background-color: Black;-fx-background-radius: 5.0;-fx-text-fill: Red;-fx-font: 13 System; -fx-font-weight: Bold;");
			btnExit.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					BaiTapDAO.xoaBaiTap(tableBaiTap.getSelectionModel().getSelectedItem().getMaBaiTap());
					dialog.close();
					// Load data..........
					listBaiTap = BaiTapDAO.getListBTObser();
					tableBaiTap.setItems(listBaiTap);
					searchRealTime();
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
	}

	// oooooooooooooooo TÌM KIẾM Thiết bị oooooooooooooo
	public void searchRealTime() {
		FilteredList<BaiTap> filteredList = new FilteredList<BaiTap>(listBaiTap, b -> true);
		txtTimKiem.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredList.setPredicate(baiTap -> {
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}
				String lowerCasefilter = newValue.toLowerCase();
				if (baiTap.getTenBaiTap().toLowerCase().indexOf(lowerCasefilter) != -1) {
					return true;
				} else {
					return false;
				}
			});
		});
		SortedList<BaiTap> sortedList = new SortedList<BaiTap>(filteredList);
		sortedList.comparatorProperty().bind(tableBaiTap.comparatorProperty());
		tableBaiTap.setItems(sortedList);
	}

	// ===== DashBoard =====
	@FXML
	void chinhsuaAction(ActionEvent event) {
		if (paneThongTinChiTiet.isVisible() || paneThemBaiTap.isVisible()) {
			paneThongTinChiTiet.setVisible(false);
			paneThemBaiTap.setVisible(false);
			paneChinhSua.setVisible(true);
			btnChinhSua.setDisable(true);
			btnThemMoi.setDisable(false);
		} else {
			return;
		}
	}

	@FXML
	void themMoiAction(ActionEvent event) {
		if (paneThongTinChiTiet.isVisible() || paneChinhSua.isVisible()) {
			paneThongTinChiTiet.setVisible(false);
			paneChinhSua.setVisible(false);
			paneThemBaiTap.setVisible(true);
			btnThemMoi.setDisable(true);
			btnChinhSua.setDisable(false);
		} else {
			return;
		}
	}

	// =====* Khởi tạo Controller *=====
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// -Load data Loại thiết bị lên Table
		tableBaiTap.setPlaceholder(new Text("Không có dữ liệu !!!"));
		colMaBaiTap.setCellValueFactory(new PropertyValueFactory<>("maBaiTap"));
		colTenBaiTap.setCellValueFactory(new PropertyValueFactory<>("tenBaiTap"));
		colMoTa.setCellValueFactory(new PropertyValueFactory<>("moTa"));

		listBaiTap = BaiTapDAO.getListBTObser();
		tableBaiTap.setItems(listBaiTap);

		// -Tìm kiếm
		searchRealTime();
	}

}
