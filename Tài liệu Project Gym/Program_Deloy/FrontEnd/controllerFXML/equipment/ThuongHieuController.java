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
import model.thuonghieu.ThuongHieu;
import model.thuonghieu.ThuongHieuDAO;

public class ThuongHieuController implements Initializable {
	@FXML
	private TableView<ThuongHieu> tableThuongHieu;

	@FXML
	private TableColumn<ThuongHieu, String> colMaTH, coltenTH, colQuocGia, colMoTa;

	@FXML
	private JFXTextField txtTimKiem;

	@FXML
	private AnchorPane paneThongTinChiTiet, paneThemTH, paneChinhSua;

	// ---Infor
	@FXML
	private JFXTextField txtInforMaTH, txtInforTenTH, txtInforQuocGia;
	@FXML
	private JFXTextArea txtInforMoTa;

	// ---Add
	@FXML
	private JFXTextField txtAddMaTH, txtAddTenTH, txtAddQuocGia;
	@FXML
	private JFXTextArea txtAddMoTa;

	// ---Update
	@FXML
	private JFXTextField txtUpdMaTH, txtUpdTenTH, txtUpdQuocGia;
	@FXML
	private JFXTextArea txtUpdMoTa;

	@FXML
	private JFXButton btnThemMoi, btnChinhSua;

	private ObservableList<ThuongHieu> listThuongHieu;

	// ===== Lấy data từ table =====
	@FXML
	void clickGetItem(MouseEvent event) {
		if (event.getClickCount() == 1) {
			if (tableThuongHieu.getSelectionModel().getSelectedItem() == null) {
				return;
			} else {
				if (paneThongTinChiTiet.isVisible()) {
					txtInforMaTH.setText(tableThuongHieu.getSelectionModel().getSelectedItem().getMaThuongHieu());
					txtInforTenTH.setText(tableThuongHieu.getSelectionModel().getSelectedItem().getTenThuongHieu());
					txtInforQuocGia.setText(tableThuongHieu.getSelectionModel().getSelectedItem().getQuocGia());
					txtInforMoTa.setText(tableThuongHieu.getSelectionModel().getSelectedItem().getMoTa());
				} else if (paneChinhSua.isVisible()) {
					txtUpdMaTH.setText(tableThuongHieu.getSelectionModel().getSelectedItem().getMaThuongHieu());
					txtUpdTenTH.setText(tableThuongHieu.getSelectionModel().getSelectedItem().getTenThuongHieu());
					txtUpdQuocGia.setText(tableThuongHieu.getSelectionModel().getSelectedItem().getQuocGia());
					txtUpdMoTa.setText(tableThuongHieu.getSelectionModel().getSelectedItem().getMoTa());
				}
			}
		}
	}

	// ===== Trở lại pane Thông tin =====
	@FXML
	void backPaneThongTin(ActionEvent event) {
		if (paneThemTH.isVisible() || paneChinhSua.isVisible()) {
			paneThemTH.setVisible(false);
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
		txtAddMaTH.clear();
		txtAddTenTH.clear();
		txtAddQuocGia.clear();
		txtAddMoTa.clear();
	}

	// ===== Làm sạch txt update =====
	@FXML
	void clearTextupdAction(ActionEvent event) {
		txtUpdMaTH.clear();
		txtUpdTenTH.clear();
		txtUpdQuocGia.clear();
		txtUpdMoTa.clear();
	}

	// ooooooooooooo THÊM MỚI Thương hiệu ooooooo
	@FXML
	void themVaoAction(ActionEvent event) {
		// Kiểm tra Mã thiết bị
		if (ThuongHieuDAO.maLTHIsExist(txtAddMaTH.getText().trim())) {
			JFXDialogLayout dialogLayout = new JFXDialogLayout();
			dialogLayout.setStyle(
					"-fx-background-color: Orange;-fx-border-color: Black;-fx-border-width: 5 ;-fx-border-color:Black ;-fx-text-fill: White;-fx-effect: dropshadow(three-pass-box, DarkRed, 10, 0, 0, 0);");
			Image image = new Image(getClass().getResourceAsStream("/Images/JFXDialog/information.png"));
			Label label = new Label("Mã thương hiệu đã tồn tại", new ImageView(image));
			label.setStyle("-fx-text-fill: DarkRed;-fx-font: 20 System; -fx-font-weight: Bold;");
			dialogLayout.setHeading(label);
			Text text = new Text("Vui lòng nhập mã khác !");
			text.setStyle("-fx-font: 15 System");
			dialogLayout.setBody(text);
			JFXDialog dialog = new JFXDialog(HomePageController.getInstance().mainStackPane, dialogLayout,
					JFXDialog.DialogTransition.CENTER);
			dialog.show();
			return;
		} else if (txtAddMaTH.getText().trim().isEmpty() || txtAddTenTH.getText().trim().isEmpty()
				|| txtAddQuocGia.getText().trim().isEmpty()) {
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
			ThuongHieuDAO.themLoaiThuongHieu(txtAddMaTH.getText().trim(), txtAddTenTH.getText().trim(),
					txtAddQuocGia.getText().trim(), txtAddMoTa.getText().trim());
			txtAddMaTH.clear();
			// Load data..........
			listThuongHieu = ThuongHieuDAO.getListTHObser();
			tableThuongHieu.setItems(listThuongHieu);
			searchRealTime();
		}
	}

	// ooooooooooooo CẬP NHẬT Loại thiết bị ooooooo
	@FXML
	void capNhatAction(ActionEvent event) {
		if (txtUpdMaTH.getText().trim().isEmpty()) {
			JFXDialogLayout dialogLayout = new JFXDialogLayout();
			dialogLayout.setStyle(
					"-fx-background-color: Orange;-fx-border-color: Black;-fx-border-width: 5 ;-fx-border-color:Black ;-fx-text-fill: White;-fx-effect: dropshadow(three-pass-box, DarkRed, 10, 0, 0, 0);");
			Image image = new Image(getClass().getResourceAsStream("/Images/JFXDialog/information.png"));
			Label label = new Label("Chọn Thương hiệu cần cập nhật", new ImageView(image));
			label.setStyle("-fx-text-fill: DarkRed;-fx-font: 20 System; -fx-font-weight: Bold;");
			dialogLayout.setHeading(label);
			Text text = new Text("Bạn có thể Click vào Danh sách trên để chọn Thương hiệu cần sửa !");
			text.setStyle("-fx-font: 15 System");
			dialogLayout.setBody(text);
			JFXDialog dialog = new JFXDialog(HomePageController.getInstance().mainStackPane, dialogLayout,
					JFXDialog.DialogTransition.CENTER);
			dialog.show();
			return;
		} else if (txtUpdMaTH.getText().trim().isEmpty() || txtUpdTenTH.getText().trim().isEmpty()
				|| txtUpdQuocGia.getText().trim().isEmpty()) {
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
			ThuongHieuDAO.capNhatThuongHieu(txtUpdMaTH.getText().trim(), txtUpdTenTH.getText().trim(),
					txtUpdQuocGia.getText().trim(), txtUpdMoTa.getText().trim());
			// Load data..........
			listThuongHieu = ThuongHieuDAO.getListTHObser();
			tableThuongHieu.setItems(listThuongHieu);
			searchRealTime();
		}
	}

	// oooooooooooo XÓA BỎ oooooooooooo
	@FXML
	void xoaBoAction(ActionEvent event) {
		if (tableThuongHieu.getSelectionModel().getSelectedItem() == null) {
			JFXDialogLayout dialogLayout = new JFXDialogLayout();
			dialogLayout.setStyle(
					"-fx-background-color: Orange;-fx-border-color: Black;-fx-border-width: 5 ;-fx-border-color:Black ;-fx-text-fill: White;-fx-effect: dropshadow(three-pass-box, DarkRed, 10, 0, 0, 0);");
			Image image = new Image(getClass().getResourceAsStream("/Images/JFXDialog/information.png"));
			Label label = new Label("Chọn thương hiệu", new ImageView(image));
			label.setStyle("-fx-text-fill: DarkRed;-fx-font: 20 System; -fx-font-weight: Bold;");
			dialogLayout.setHeading(label);
			Text text = new Text("Vui lòng chọn 1 thương hiệu từ danh sách để Xóa!");
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
			Text text = new Text("Bạn muốn Xóa thương hiệu : "
					+ tableThuongHieu.getSelectionModel().getSelectedItem().getTenThuongHieu());
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
					ThuongHieuDAO
							.xoaThuongHieu(tableThuongHieu.getSelectionModel().getSelectedItem().getMaThuongHieu());
					dialog.close();
					// Load data..........
					listThuongHieu = ThuongHieuDAO.getListTHObser();
					tableThuongHieu.setItems(listThuongHieu);
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
		FilteredList<ThuongHieu> filteredList = new FilteredList<ThuongHieu>(listThuongHieu, b -> true);
		txtTimKiem.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredList.setPredicate(thuongHieu -> {
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}
				String lowerCasefilter = newValue.toLowerCase();
				if (thuongHieu.getTenThuongHieu().toLowerCase().indexOf(lowerCasefilter) != -1) {
					return true;
				} else {
					return false;
				}
			});
		});
		SortedList<ThuongHieu> sortedList = new SortedList<ThuongHieu>(filteredList);
		sortedList.comparatorProperty().bind(tableThuongHieu.comparatorProperty());
		tableThuongHieu.setItems(sortedList);
	}

	// ===== DashBoard =====
	@FXML
	void chinhsuaAction(ActionEvent event) {
		if (paneThongTinChiTiet.isVisible() || paneThemTH.isVisible()) {
			paneThongTinChiTiet.setVisible(false);
			paneThemTH.setVisible(false);
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
			paneThemTH.setVisible(true);
			btnThemMoi.setDisable(true);
			btnChinhSua.setDisable(false);
		} else {
			return;
		}
	}

	// =====* Khởi tạo Controller =====
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// -Load data Loại thiết bị lên Table
		tableThuongHieu.setPlaceholder(new Text("Không có dữ liệu !!!"));
		colMaTH.setCellValueFactory(new PropertyValueFactory<>("maThuongHieu"));
		coltenTH.setCellValueFactory(new PropertyValueFactory<>("TenThuongHieu"));
		colQuocGia.setCellValueFactory(new PropertyValueFactory<>("quocGia"));
		colMoTa.setCellValueFactory(new PropertyValueFactory<>("moTa"));

		listThuongHieu = ThuongHieuDAO.getListTHObser();
		tableThuongHieu.setItems(listThuongHieu);

		// -Tìm kiếm
		searchRealTime();
	}

}
