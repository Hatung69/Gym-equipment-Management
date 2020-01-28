package controllerFXML.maintenance;

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
import model.loaiBT.LoaiBaoTri;
import model.loaiBT.LoaiBaoTriDAO;

public class LoaiBaoTriController implements Initializable {

	@FXML
	private TableView<LoaiBaoTri> tableLoaiBaoTri;
	@FXML
	private TableColumn<LoaiBaoTri, String> colMaLoaiBT, colTenLoaiBT, colMoTa;
	@FXML
	private JFXTextField txtTimKiem;
	@FXML
	private AnchorPane paneThongTinChiTiet, paneThemLoaiTB, paneChinhSua;
	// ---Infor
	@FXML
	private JFXTextField txtInforMaLoaiBT, txtInforTenLoaiBT;
	@FXML
	private JFXTextArea txtInforMoTa;
	// ---Add
	@FXML
	private JFXTextField txtAddMaLoaiBT, txtAddTenLoaiBT;
	@FXML
	private JFXTextArea txtAddMoTa;
	// ---Update
	@FXML
	private JFXTextField txtUpdMaLoaiBT, txtUpdTenLoaiBT;
	@FXML
	private JFXTextArea txtUpdMoTa;

	@FXML
	private JFXButton btnThemMoi, btnChinhSua;

	private ObservableList<LoaiBaoTri> listLoaiBaoTri;

	// ===== Lấy data từ table =====
	@FXML
	void clickGetItem(MouseEvent event) {
		if (event.getClickCount() == 1) {
			if (tableLoaiBaoTri.getSelectionModel().getSelectedItem() == null) {
				return;
			} else {
				if (paneThongTinChiTiet.isVisible()) {
					txtInforMaLoaiBT.setText(tableLoaiBaoTri.getSelectionModel().getSelectedItem().getMaLoaiBT());
					txtInforTenLoaiBT.setText(tableLoaiBaoTri.getSelectionModel().getSelectedItem().getTenLoaiBT());
					txtInforMoTa.setText(tableLoaiBaoTri.getSelectionModel().getSelectedItem().getMoTa());
				} else if (paneChinhSua.isVisible()) {
					txtUpdMaLoaiBT.setText(tableLoaiBaoTri.getSelectionModel().getSelectedItem().getMaLoaiBT());
					txtUpdTenLoaiBT.setText(tableLoaiBaoTri.getSelectionModel().getSelectedItem().getTenLoaiBT());
					txtUpdMoTa.setText(tableLoaiBaoTri.getSelectionModel().getSelectedItem().getMoTa());
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
		txtAddMaLoaiBT.clear();
		txtAddTenLoaiBT.clear();
		txtAddMoTa.clear();
	}

	// ===== Làm sạch txt update =====
	@FXML
	void clearTextUpdAction(ActionEvent event) {
		txtUpdMaLoaiBT.clear();
		txtUpdTenLoaiBT.clear();
		txtUpdMoTa.clear();
	}

	// ooooooooooooo THÊM MỚI Loại Bảo trì ooooooo
	@FXML
	void themVaoAction(ActionEvent event) {
		// Kiểm tra Mã thiết bị
		if (LoaiBaoTriDAO.maLBTIsExist(txtAddMaLoaiBT.getText().trim())) {
			JFXDialogLayout dialogLayout = new JFXDialogLayout();
			dialogLayout.setStyle(
					"-fx-background-color: Orange;-fx-border-color: Black;-fx-border-width: 5 ;-fx-border-color:Black ;-fx-text-fill: White;-fx-effect: dropshadow(three-pass-box, DarkRed, 10, 0, 0, 0);");
			Image image = new Image(getClass().getResourceAsStream("/Images/JFXDialog/warning.png"));
			Label label = new Label("Mã loại đã tồn tại", new ImageView(image));
			label.setStyle("-fx-text-fill: DarkRed;-fx-font: 20 System; -fx-font-weight: Bold;");
			dialogLayout.setHeading(label);
			Text text = new Text("Mã loại đã tồn tại. Vui lòng nhập mã khác !");
			text.setStyle("-fx-font: 15 System");
			dialogLayout.setBody(text);
			JFXDialog dialog = new JFXDialog(HomePageController.getInstance().mainStackPane, dialogLayout,
					JFXDialog.DialogTransition.CENTER);
			dialog.show();
			return;
		} else if (txtAddMaLoaiBT.getText().trim().isEmpty() || txtAddTenLoaiBT.getText().trim().isEmpty()) {
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
			LoaiBaoTriDAO.themLoaiBaoTri(txtAddMaLoaiBT.getText().trim(), txtAddTenLoaiBT.getText().trim(),
					txtAddMoTa.getText().trim());
			txtAddMaLoaiBT.clear();
			// Load data..........
			listLoaiBaoTri = LoaiBaoTriDAO.getListBTObser();
			tableLoaiBaoTri.setItems(listLoaiBaoTri);
			searchRealTime();
		}
	}

	// ooooooooooooo CẬP NHẬT Loại bảo trì ooooooo
	@FXML
	void capNhatAction(ActionEvent event) {
		if (txtUpdMaLoaiBT.getText().trim().isEmpty()) {
			JFXDialogLayout dialogLayout = new JFXDialogLayout();
			dialogLayout.setStyle(
					"-fx-background-color: Orange;-fx-border-color: Black;-fx-border-width: 5 ;-fx-border-color:Black ;-fx-text-fill: White;-fx-effect: dropshadow(three-pass-box, DarkRed, 10, 0, 0, 0);");
			Image image = new Image(getClass().getResourceAsStream("/Images/JFXDialog/warning.png"));
			Label label = new Label("Chọn Loại bảo trì cần cập nhật", new ImageView(image));
			label.setStyle("-fx-text-fill: DarkRed;-fx-font: 20 System; -fx-font-weight: Bold;");
			dialogLayout.setHeading(label);
			Text text = new Text("Bạn có thể Click vào Danh sách trên để chọn Loại bảo trì cần sửa !");
			text.setStyle("-fx-font: 15 System");
			dialogLayout.setBody(text);
			JFXDialog dialog = new JFXDialog(HomePageController.getInstance().mainStackPane, dialogLayout,
					JFXDialog.DialogTransition.CENTER);
			dialog.show();
			return;
		} else if (txtUpdMaLoaiBT.getText().trim().isEmpty() || txtUpdTenLoaiBT.getText().trim().isEmpty()) {
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
			LoaiBaoTriDAO.capNhatLoaiBaoTri(txtUpdMaLoaiBT.getText().trim(), txtUpdTenLoaiBT.getText().trim(),
					txtUpdMoTa.getText().trim());
			// Load data..........
			listLoaiBaoTri = LoaiBaoTriDAO.getListBTObser();
			tableLoaiBaoTri.setItems(listLoaiBaoTri);
			searchRealTime();
		}
	}

	// oooooooooooo XÓA BỎ oooooooooooo
	@FXML
	void xoaBoAction(ActionEvent event) {
		if (tableLoaiBaoTri.getSelectionModel().getSelectedItem() == null) {
			JFXDialogLayout dialogLayout = new JFXDialogLayout();
			dialogLayout.setStyle(
					"-fx-background-color: Orange;-fx-border-color: Black;-fx-border-width: 5 ;-fx-border-color:Black ;-fx-text-fill: White;-fx-effect: dropshadow(three-pass-box, DarkRed, 10, 0, 0, 0);");
			Image image = new Image(getClass().getResourceAsStream("/Images/JFXDialog/warning.png"));
			Label label = new Label("Chọn thiết bị", new ImageView(image));
			label.setStyle("-fx-text-fill: DarkRed;-fx-font: 20 System; -fx-font-weight: Bold;");
			dialogLayout.setHeading(label);
			Text text = new Text("Vui lòng chọn 1 Loại bảo trì từ danh sách để xóa!");
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
			Text text = new Text("Bạn muốn Xóa loại bảo trì : "
					+ tableLoaiBaoTri.getSelectionModel().getSelectedItem().getTenLoaiBT());
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
				LoaiBaoTriDAO.xoaLoaiBaoTri(tableLoaiBaoTri.getSelectionModel().getSelectedItem().getMaLoaiBT());
					dialog.close();
					// Load data..........
					listLoaiBaoTri = LoaiBaoTriDAO.getListBTObser();
					tableLoaiBaoTri.setItems(listLoaiBaoTri);
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

	// oooooooooooooooo TÌM KIẾM Loại bảo trì oooooooooooooo
	public void searchRealTime() {
		FilteredList<LoaiBaoTri> filteredList = new FilteredList<LoaiBaoTri>(listLoaiBaoTri, b -> true);
		txtTimKiem.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredList.setPredicate(loaiBaoTri -> {
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}
				String lowerCasefilter = newValue.toLowerCase();
				if (loaiBaoTri.getTenLoaiBT().toLowerCase().indexOf(lowerCasefilter) != -1) {
					return true;
				} else {
					return false;
				}
			});
		});
		SortedList<LoaiBaoTri> sortedList = new SortedList<LoaiBaoTri>(filteredList);
		sortedList.comparatorProperty().bind(tableLoaiBaoTri.comparatorProperty());
		tableLoaiBaoTri.setItems(sortedList);
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
		// -Load data Loại thiết bị lên Table
		tableLoaiBaoTri.setPlaceholder(new Text("Không có dữ liệu !!!"));
		colMaLoaiBT.setCellValueFactory(new PropertyValueFactory<>("maLoaiBT"));
		colTenLoaiBT.setCellValueFactory(new PropertyValueFactory<>("tenLoaiBT"));
		colMoTa.setCellValueFactory(new PropertyValueFactory<>("moTa"));

		listLoaiBaoTri = LoaiBaoTriDAO.getListBTObser();
		tableLoaiBaoTri.setItems(listLoaiBaoTri);

		// -Tìm kiếm
		searchRealTime();
	}

}
