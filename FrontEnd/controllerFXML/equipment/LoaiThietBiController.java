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
import model.loaiTB.LoaiThietBi;
import model.loaiTB.LoaiThietBiDAO;

public class LoaiThietBiController implements Initializable {
	@FXML
	private TableView<LoaiThietBi> tableLoaiThietBi;
	@FXML
	private TableColumn<LoaiThietBi, String> colMaLoaiTB, colTenLoaiTB, colMoTa;
	@FXML
	private JFXTextField txtTimKiem;
	@FXML
	private AnchorPane paneThongTinChiTiet, paneThemLoaiTB, paneChinhSua;
	// ---Infor
	@FXML
	private JFXTextField txtInforMaLoaiTB, txtInforTenLoaiTB;
	@FXML
	private JFXTextArea txtInforMoTa;
	// ---Add
	@FXML
	private JFXTextField txtAddMaLoaiTB, txtAddTenLoaiTB;
	@FXML
	private JFXTextArea txtAddMoTa;
	// ---Update
	@FXML
	private JFXTextField txtUpdMaLoaiTB, txtUpdTenLoaiTB;
	@FXML
	private JFXTextArea txtUpdMoTa;

	@FXML
	private JFXButton btnThemMoi, btnChinhSua;

	private ObservableList<LoaiThietBi> listLoaiThietBi;

	// ===== Lấy data từ table =====
	@FXML
	void clickGetItem(MouseEvent event) {
		if (event.getClickCount() == 1) {
			if (tableLoaiThietBi.getSelectionModel().getSelectedItem() == null) {
				return;
			} else {
				if (paneThongTinChiTiet.isVisible()) {
					txtInforMaLoaiTB.setText(tableLoaiThietBi.getSelectionModel().getSelectedItem().getMaLoai());
					txtInforTenLoaiTB.setText(tableLoaiThietBi.getSelectionModel().getSelectedItem().getTenLoai());
					txtInforMoTa.setText(tableLoaiThietBi.getSelectionModel().getSelectedItem().getMoTa());
				} else if (paneChinhSua.isVisible()) {
					txtUpdMaLoaiTB.setText(tableLoaiThietBi.getSelectionModel().getSelectedItem().getMaLoai());
					txtUpdTenLoaiTB.setText(tableLoaiThietBi.getSelectionModel().getSelectedItem().getTenLoai());
					txtUpdMoTa.setText(tableLoaiThietBi.getSelectionModel().getSelectedItem().getMoTa());
				}
			}
		}
	}

	// ===== Trở lại pane Thông tin =====
	@FXML
	void backPaneThongTin(ActionEvent event) {
		if (paneThemLoaiTB.isVisible() || paneChinhSua.isVisible()) {
			paneThemLoaiTB.setVisible(false);
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
		txtAddMaLoaiTB.clear();
		txtAddTenLoaiTB.clear();
		txtAddMoTa.clear();
	}

	// ===== Làm sạch txt update =====
	@FXML
	void clearTextUpdAction(ActionEvent event) {
		txtUpdMaLoaiTB.clear();
		txtUpdTenLoaiTB.clear();
		txtUpdMoTa.clear();
	}

	// ooooooooooooo THÊM MỚI ooooooo
	@FXML
	void themVaoAction(ActionEvent event) {
		if (LoaiThietBiDAO.maLTBIsExist(txtAddMaLoaiTB.getText().trim())) {
			JFXDialogLayout dialogLayout = new JFXDialogLayout();
			dialogLayout.setStyle(
					"-fx-background-color: Orange;-fx-border-color: Black;-fx-border-width: 5 ;-fx-border-color:Black ;-fx-text-fill: White;-fx-effect: dropshadow(three-pass-box, DarkRed, 10, 0, 0, 0);");
			Image image = new Image(getClass().getResourceAsStream("/Images/JFXDialog/information.png"));
			Label label = new Label("Mã loại đã tồn tại", new ImageView(image));
			label.setStyle("-fx-text-fill: DarkRed;-fx-font: 20 System; -fx-font-weight: Bold;");
			dialogLayout.setHeading(label);
			Text text = new Text("Vui lòng nhập mã khác !");
			text.setStyle("-fx-font: 15 System");
			dialogLayout.setBody(text);
			JFXDialog dialog = new JFXDialog(HomePageController.getInstance().mainStackPane, dialogLayout,
					JFXDialog.DialogTransition.CENTER);
			dialog.show();
			return;
		} else if (txtAddMaLoaiTB.getText().trim().isEmpty() || txtAddTenLoaiTB.getText().trim().isEmpty()) {
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
			LoaiThietBiDAO.themLoaiThietBi(txtAddMaLoaiTB.getText().trim(), txtAddTenLoaiTB.getText().trim(),
					txtAddMoTa.getText().trim());
			txtAddMaLoaiTB.clear();
			// Load data..........
			listLoaiThietBi = LoaiThietBiDAO.getListTBObser();
			tableLoaiThietBi.setItems(listLoaiThietBi);
			searchRealTime();
		}
	}

	// ooooooooooooo CẬP NHẬT  ooooooo
	@FXML
	void capNhatAction(ActionEvent event) {
		if (txtUpdMaLoaiTB.getText().trim().isEmpty()) {
			JFXDialogLayout dialogLayout = new JFXDialogLayout();
			dialogLayout.setStyle(
					"-fx-background-color: Orange;-fx-border-color: Black;-fx-border-width: 5 ;-fx-border-color:Black ;-fx-text-fill: White;-fx-effect: dropshadow(three-pass-box, DarkRed, 10, 0, 0, 0);");
			Image image = new Image(getClass().getResourceAsStream("/Images/JFXDialog/information.png"));
			Label label = new Label("Chọn Loại thiết bị cần cập nhật", new ImageView(image));
			label.setStyle("-fx-text-fill: DarkRed;-fx-font: 20 System; -fx-font-weight: Bold;");
			dialogLayout.setHeading(label);
			Text text = new Text("Bạn có thể Click vào Danh sách trên để chọn Loại thiết bị cần sửa !");
			text.setStyle("-fx-font: 15 System");
			dialogLayout.setBody(text);
			JFXDialog dialog = new JFXDialog(HomePageController.getInstance().mainStackPane, dialogLayout,
					JFXDialog.DialogTransition.CENTER);
			dialog.show();
			return;
		} else if (txtUpdMaLoaiTB.getText().trim().isEmpty() || txtUpdTenLoaiTB.getText().trim().isEmpty()) {
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
			if (txtUpdMoTa.getText().trim().isEmpty()) {
				txtUpdMoTa.setText("Chưa xác định");
			}
			LoaiThietBiDAO.capNhatLoaiTB(txtUpdMaLoaiTB.getText().trim(), txtUpdTenLoaiTB.getText().trim(),
					txtUpdMoTa.getText().trim());
			// Load data..........
			listLoaiThietBi = LoaiThietBiDAO.getListTBObser();
			tableLoaiThietBi.setItems(listLoaiThietBi);
			searchRealTime();
		}
	}

	// oooooooooooo XÓA BỎ oooooooooooo
	@FXML
	void xoaBoAction(ActionEvent event) {
		if (tableLoaiThietBi.getSelectionModel().getSelectedItem() == null) {
			JFXDialogLayout dialogLayout = new JFXDialogLayout();
			dialogLayout.setStyle(
					"-fx-background-color: Orange;-fx-border-color: Black;-fx-border-width: 5 ;-fx-border-color:Black ;-fx-text-fill: White;-fx-effect: dropshadow(three-pass-box, DarkRed, 10, 0, 0, 0);");
			Image image = new Image(getClass().getResourceAsStream("/Images/JFXDialog/warning.png"));
			Label label = new Label("Chọn thiết bị", new ImageView(image));
			label.setStyle("-fx-text-fill: DarkRed;-fx-font: 20 System; -fx-font-weight: Bold;");
			dialogLayout.setHeading(label);
			Text text = new Text("Vui lòng chọn 1 loại thiết bị từ danh sách để Xóa!");
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
			Text text = new Text("Bạn muốn Xóa loại thiết bị : "
					+ tableLoaiThietBi.getSelectionModel().getSelectedItem().getTenLoai());
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
					LoaiThietBiDAO.xoaLoaiTB(tableLoaiThietBi.getSelectionModel().getSelectedItem().getMaLoai());
					dialog.close();
					// Load data..........
					listLoaiThietBi = LoaiThietBiDAO.getListTBObser();
					tableLoaiThietBi.setItems(listLoaiThietBi);
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

	// oooooooooooooooo TÌM KIẾM oooooooooooooo
	public void searchRealTime() {
		FilteredList<LoaiThietBi> filteredList = new FilteredList<LoaiThietBi>(listLoaiThietBi, b -> true);
		txtTimKiem.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredList.setPredicate(Loaithietbi -> {
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}
				String lowerCasefilter = newValue.toLowerCase();
				if (Loaithietbi.getTenLoai().toLowerCase().indexOf(lowerCasefilter) != -1) {
					return true;
				} else {
					return false;
				}
			});
		});
		SortedList<LoaiThietBi> sortedList = new SortedList<LoaiThietBi>(filteredList);
		sortedList.comparatorProperty().bind(tableLoaiThietBi.comparatorProperty());
		tableLoaiThietBi.setItems(sortedList);
	}

	// ===== DashBoard =====
	@FXML
	void chinhsuaAction(ActionEvent event) {
		if (paneThongTinChiTiet.isVisible() || paneThemLoaiTB.isVisible()) {
			paneThongTinChiTiet.setVisible(false);
			paneThemLoaiTB.setVisible(false);
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
			paneThemLoaiTB.setVisible(true);
			btnThemMoi.setDisable(true);
			btnChinhSua.setDisable(false);
		} else {
			return;
		}
	}

	// ===== * Khởi tạo controller *=====
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		tableLoaiThietBi.setPlaceholder(new Text("Không có dữ liệu !!!"));
		colMaLoaiTB.setCellValueFactory(new PropertyValueFactory<>("maLoai"));
		colTenLoaiTB.setCellValueFactory(new PropertyValueFactory<>("tenLoai"));
		colMoTa.setCellValueFactory(new PropertyValueFactory<>("moTa"));

		listLoaiThietBi = LoaiThietBiDAO.getListTBObser();
		tableLoaiThietBi.setItems(listLoaiThietBi);

		// -Tìm kiếm
		searchRealTime();
	}

}
