package controllerFXML.account;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import controllerFXML.home.HomePageController;
import controllerFXML.login.LoginController;
import javafx.animation.FadeTransition;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.util.Duration;
import javafx.util.StringConverter;
import model.nhanvien.NhanVien;
import model.nhanvien.NhanVienDAO;
import model.taikhoan.TaiKhoan;
import model.taikhoan.TaiKhoanDAO;

public class AccountController implements Initializable {
	private static AccountController instance;

	public static AccountController getInstance() {
		return instance;
	}

	public AccountController() {
		instance = this;
	}

	// -------------------------------------------------------
	@FXML
	public TableView<NhanVien> tableNhanVien;
	@FXML
	private TableColumn<NhanVien, String> colMaNhanVien, colTenNhanVien, colGioiTinh, colDiaChi, colSDT, colMaQuyen;
	@FXML
	private TableColumn<NhanVien, Date> colNgaySinh;
	// ---------------------
	@FXML
	public TableView<TaiKhoan> tableTaiKhoan;
	@FXML
	private TableColumn<TaiKhoan, String> colTenTaiKhoan, colGmail, colMaNhanVienTK;

	@FXML
	private ImageView imageCheQuanLy;
	@FXML
	private AnchorPane paneInfor, paneChinhSua;
	@FXML
	private JFXTextField txtTenTaiKhoan, txtGmail, txtMaNhanVien, txtQuyenHan;
	@FXML
	private JFXPasswordField txtMatKhau;

	@FXML
	private JFXTextField txtUpdTenTaiKhoan, txtUpdGmail, txtUpdMaNhanVien, txtUpdMaQuyenHan;
	@FXML
	private JFXPasswordField txtUpdPassword;
	@FXML
	private JFXTextField txtUpdPasswordShow;
	@FXML
	private JFXCheckBox checkBox;
	@FXML
	private StackPane panBtnQuanLy;
	@FXML
	private Text textUserName;

	TaiKhoan taiKhoan = null;
	public ObservableList<NhanVien> listNhanVien;
	public ObservableList<TaiKhoan> listtaiKhoan;
	private AnchorPane anchorThongTinNhanVien,anchorThemNhanVien,anchorCapNhatNV,anchorThemTaiKhoan,anchorCapNhatTaiKhoan;
	protected JFXDialog dialogThongTinNhanVien,dialogThemNhanVien,dialogCapNhatNV,dialogThemTaiKhoan,dialogCapNhatTaiKhoan;

	// ===== Get item Nhân viên =====
	@FXML
	void clickGetItemNV(MouseEvent event) {

	}

	// ===== Get item Tài khoản =====
	@FXML
	void clickGetItemTK(MouseEvent event) {

	}

	// ===== Hoàn tất chỉnh sửa =====
	@FXML
	void hoanTatChinhSua(ActionEvent event) {
		if (txtUpdTenTaiKhoan.getText().trim().isEmpty() || txtUpdPassword.getText().trim().isEmpty()
				|| txtUpdGmail.getText().trim().isEmpty()) {
			JFXDialogLayout dialogLayout = new JFXDialogLayout();
			dialogLayout.setStyle(
					"-fx-background-color: Orange;-fx-border-color: Black;-fx-border-width: 5 ;-fx-border-color:Black ;-fx-text-fill: White;-fx-effect: dropshadow(three-pass-box, DarkRed, 10, 0, 0, 0);");
			Image image = new Image(getClass().getResourceAsStream("/Images/JFXDialog/warning.png"));
			Label label = new Label("Kiểm tra lại thông tin", new ImageView(image));
			label.setStyle("-fx-text-fill: DarkRed;-fx-font: 20 System; -fx-font-weight: Bold;");
			dialogLayout.setHeading(label);
			Text text = new Text("Vui lòng cung cấp đầy đủ thông tin");
			text.setStyle("-fx-font: 15 System");
			dialogLayout.setBody(text);
			JFXDialog dialog = new JFXDialog(HomePageController.getInstance().mainStackPane, dialogLayout,
					JFXDialog.DialogTransition.CENTER);
			dialog.show();
			return;
		} else {
			TaiKhoanDAO.updateInfor(txtUpdTenTaiKhoan.getText().trim(), txtUpdPassword.getText().trim(),
					txtUpdGmail.getText().trim(), txtUpdMaNhanVien.getText());
			loadInfor();
			paneChinhSua.setVisible(false);
			paneInfor.setVisible(true);
			// Load lại list cho quản lý tài khoản
		}
	}

	// ===== Hủy chỉnh sửa =====
	@FXML
	void huy(ActionEvent event) {
		if (paneChinhSua.isVisible()) {
			paneChinhSua.setVisible(false);
			paneInfor.setVisible(true);
		} else {
			return;
		}
	}

	// ===== Chỉnh sửa lại thông tin tài khoản =====
	@FXML
	void chinhSuaAction(ActionEvent event) {
		if (paneInfor.isVisible()) {
			paneInfor.setVisible(false);
			paneChinhSua.setVisible(true);
		} else {
			return;
		}
	}

	// ===== Mở layout quản lý nhân viên =====
	@FXML
	void openQuanLyNhanVien(ActionEvent event) {
		if (imageCheQuanLy.isVisible()) {
			FadeTransition fade2 = new FadeTransition();
			fade2.setDuration(Duration.millis(1000));
			fade2.setNode(imageCheQuanLy);
			fade2.setFromValue(1);
			fade2.setToValue(0);
			fade2.setCycleCount(1);
			fade2.setAutoReverse(true);
			fade2.play();
			fade2.setOnFinished(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					imageCheQuanLy.setVisible(false);
				}
			});
		} else if (!imageCheQuanLy.isVisible()) {
			imageCheQuanLy.setVisible(true);
			FadeTransition fade2 = new FadeTransition();
			fade2.setDuration(Duration.millis(1000));
			fade2.setNode(imageCheQuanLy);
			fade2.setFromValue(0);
			fade2.setToValue(1);
			fade2.play();
			fade2.setOnFinished(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					imageCheQuanLy.setVisible(true);
				}
			});
		}
	}

	// ooooooooo THÔNG TIN nhân vien oooooooooooo
	@FXML
	void thongTinNhanVien(ActionEvent event) throws IOException {
		if (tableNhanVien.getSelectionModel().getSelectedItem() == null) {
			JFXDialogLayout dialogLayout = new JFXDialogLayout();
			dialogLayout.setStyle(
					"-fx-background-color: Orange;-fx-border-color: Black;-fx-border-width: 5 ;-fx-border-color:Black ;-fx-text-fill: White;-fx-effect: dropshadow(three-pass-box, DarkRed, 10, 0, 0, 0);");
			Image image = new Image(getClass().getResourceAsStream("/Images/JFXDialog/information.png"));
			Label label = new Label("Chọn nhân viên", new ImageView(image));
			label.setStyle("-fx-text-fill: DarkRed;-fx-font: 20 System; -fx-font-weight: Bold;");
			dialogLayout.setHeading(label);
			Text text = new Text("Vui lòng chọn 1 Nhân viên từ danh sách để Xem thông tin!");
			text.setStyle("-fx-font: 15 System");
			dialogLayout.setBody(text);
			JFXDialog dialog = new JFXDialog(HomePageController.getInstance().mainStackPane, dialogLayout,
					JFXDialog.DialogTransition.CENTER);
			dialog.show();
			return;
		} else {
			anchorThongTinNhanVien = FXMLLoader.load(getClass().getResource("/viewFXML/account/dialogThongTinNhanVien.fxml"));
			dialogThongTinNhanVien = new JFXDialog(HomePageController.getInstance().stackPaneLoadLayout, anchorThongTinNhanVien,
					JFXDialog.DialogTransition.CENTER);
			dialogThongTinNhanVien.show();
		}
	}
	// ooooooooo THÊM nhân vien oooooooooooo
	@FXML
	void themMoiNhanVien(ActionEvent event) throws IOException {
		anchorThemNhanVien = FXMLLoader.load(getClass().getResource("/viewFXML/account/dialogThemNhanVien.fxml"));
		dialogThemNhanVien = new JFXDialog(HomePageController.getInstance().stackPaneLoadLayout, anchorThemNhanVien,
				JFXDialog.DialogTransition.CENTER);
		dialogThemNhanVien.show();
	}

	// ooooooooo XÓA nhân viên oooooooooooooo
	@FXML
	void xoaBoNhanVien(ActionEvent event) {
		if (tableNhanVien.getSelectionModel().getSelectedItem() == null) {
			JFXDialogLayout dialogLayout = new JFXDialogLayout();
			dialogLayout.setStyle(
					"-fx-background-color: Orange;-fx-border-color: Black;-fx-border-width: 5 ;-fx-border-color:Black ;-fx-text-fill: White;-fx-effect: dropshadow(three-pass-box, DarkRed, 10, 0, 0, 0);");
			Image image = new Image(getClass().getResourceAsStream("/Images/JFXDialog/information.png"));
			Label label = new Label("Chọn nhân viên", new ImageView(image));
			label.setStyle("-fx-text-fill: DarkRed;-fx-font: 20 System; -fx-font-weight: Bold;");
			dialogLayout.setHeading(label);
			Text text = new Text("Vui lòng chọn 1 Nhân viên từ danh sách để xóa!");
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
			Text text = new Text("Bạn muốn Xóa nhân viên : " + tableNhanVien
					.getSelectionModel().getSelectedItem().getTenNhanVien());
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
					NhanVienDAO.xoaNhanVien(tableNhanVien.getSelectionModel().getSelectedItem().getMaNhanVien());
					dialog.close();
					// Load data..........
					listNhanVien = NhanVienDAO.getListNhanVien();
					tableNhanVien.setItems(listNhanVien);
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

	// ooooooooo SỬA nhân viên oooooooooooooo
	@FXML
	void chinhSuaNhanVien(ActionEvent event) throws IOException {
		if (tableNhanVien.getSelectionModel().getSelectedItem() == null) {
			JFXDialogLayout dialogLayout = new JFXDialogLayout();
			dialogLayout.setStyle(
					"-fx-background-color: Orange;-fx-border-color: Black;-fx-border-width: 5 ;-fx-border-color:Black ;-fx-text-fill: White;-fx-effect: dropshadow(three-pass-box, DarkRed, 10, 0, 0, 0);");
			Image image = new Image(getClass().getResourceAsStream("/Images/JFXDialog/information.png"));
			Label label = new Label("Chọn nhân viên", new ImageView(image));
			label.setStyle("-fx-text-fill: DarkRed;-fx-font: 20 System; -fx-font-weight: Bold;");
			dialogLayout.setHeading(label);
			Text text = new Text("Vui lòng chọn 1 Nhân viên từ danh sách để Sửa lại thông tin!");
			text.setStyle("-fx-font: 15 System");
			dialogLayout.setBody(text);
			JFXDialog dialog = new JFXDialog(HomePageController.getInstance().mainStackPane, dialogLayout,
					JFXDialog.DialogTransition.CENTER);
			dialog.show();
			return;
		} else {
			anchorCapNhatNV = FXMLLoader.load(getClass().getResource("/viewFXML/account/dialogSuaNhanVien.fxml"));
			dialogCapNhatNV = new JFXDialog(HomePageController.getInstance().stackPaneLoadLayout, anchorCapNhatNV,
					JFXDialog.DialogTransition.CENTER);
			dialogCapNhatNV.show();
		}
	}

	// -------------
	// ooooooooo THÊM Tài khoản oooooooo
	@FXML
	void themMoiTaiKhoan(ActionEvent event) throws IOException {
		anchorThemTaiKhoan = FXMLLoader.load(getClass().getResource("/viewFXML/account/dialogThemTaiKhoan.fxml"));
		dialogThemTaiKhoan = new JFXDialog(HomePageController.getInstance().stackPaneLoadLayout, anchorThemTaiKhoan,
				JFXDialog.DialogTransition.CENTER);
		dialogThemTaiKhoan.show();
	}

	// ooooooooo XÓA tài khoản oooooooooooooo
	@FXML
	void xoaBoTaiKhoan(ActionEvent event) {
		if (tableTaiKhoan.getSelectionModel().getSelectedItem() == null) {
			JFXDialogLayout dialogLayout = new JFXDialogLayout();
			dialogLayout.setStyle(
					"-fx-background-color: Orange;-fx-border-color: Black;-fx-border-width: 5 ;-fx-border-color:Black ;-fx-text-fill: White;-fx-effect: dropshadow(three-pass-box, DarkRed, 10, 0, 0, 0);");
			Image image = new Image(getClass().getResourceAsStream("/Images/JFXDialog/information.png"));
			Label label = new Label("Chọn tài khoản", new ImageView(image));
			label.setStyle("-fx-text-fill: DarkRed;-fx-font: 20 System; -fx-font-weight: Bold;");
			dialogLayout.setHeading(label);
			Text text = new Text("Vui lòng chọn 1 tài khoản từ danh sách để xóa!");
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
			Text text = new Text("Bạn muốn Xóa tài khoản : " + tableTaiKhoan.getSelectionModel().getSelectedItem().getTenTaiKhoan());
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
					TaiKhoanDAO.xoaTaiKhoan(tableTaiKhoan.getSelectionModel().getSelectedItem().getTenTaiKhoan());
					dialog.close();
					// Load data..........
					listtaiKhoan = TaiKhoanDAO.getListTaiKhoan();
					tableTaiKhoan.setItems(listtaiKhoan);
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

	// ooooooooo CHỈNH SỬA tài khoản oooooooooooooo
	@FXML
	void chinhSuaTaiKhoan(ActionEvent event) throws IOException {
		if (tableTaiKhoan.getSelectionModel().getSelectedItem() == null) {
			JFXDialogLayout dialogLayout = new JFXDialogLayout();
			dialogLayout.setStyle(
					"-fx-background-color: Orange;-fx-border-color: Black;-fx-border-width: 5 ;-fx-border-color:Black ;-fx-text-fill: White;-fx-effect: dropshadow(three-pass-box, DarkRed, 10, 0, 0, 0);");
			Image image = new Image(getClass().getResourceAsStream("/Images/JFXDialog/information.png"));
			Label label = new Label("Chọn tài khoản", new ImageView(image));
			label.setStyle("-fx-text-fill: DarkRed;-fx-font: 20 System; -fx-font-weight: Bold;");
			dialogLayout.setHeading(label);
			Text text = new Text("Vui lòng chọn 1 tài khoản từ danh sách để Sửa lại thông tin!");
			text.setStyle("-fx-font: 15 System");
			dialogLayout.setBody(text);
			JFXDialog dialog = new JFXDialog(HomePageController.getInstance().mainStackPane, dialogLayout,
					JFXDialog.DialogTransition.CENTER);
			dialog.show();
			return;
		} else {
			anchorCapNhatTaiKhoan = FXMLLoader.load(getClass().getResource("/viewFXML/account/dialogSuaTaiKhoan.fxml"));
			dialogCapNhatTaiKhoan = new JFXDialog(HomePageController.getInstance().stackPaneLoadLayout, anchorCapNhatTaiKhoan,
					JFXDialog.DialogTransition.CENTER);
			dialogCapNhatTaiKhoan.show();
		}
	}

	void loadInfor() {

		taiKhoan = TaiKhoanDAO.getInfor(txtMaNhanVien.getText().trim());
		txtTenTaiKhoan.setText(taiKhoan.getTenTaiKhoan());
		txtMatKhau.setText(taiKhoan.getMatKhau());
		txtGmail.setText(taiKhoan.getGmail());
		txtMaNhanVien.setText(taiKhoan.getMaNhanVien());
		txtQuyenHan.setText(taiKhoan.getMaQuyen());
	}

	// =====* Khởi tạo controller *===========
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// - Hiện mật khẩu ( dùng txtPass và txt thường để switch cho nhau tùy checkBox)
		txtUpdPasswordShow.managedProperty().bind(checkBox.selectedProperty());
		txtUpdPasswordShow.visibleProperty().bind(checkBox.selectedProperty());
		txtUpdPasswordShow.textProperty().bindBidirectional(txtUpdPassword.textProperty());
		txtUpdPassword.managedProperty().bind(checkBox.selectedProperty().not());
		txtUpdPassword.visibleProperty().bind(checkBox.selectedProperty().not());

		// - Khởi tạo Thông tin nhân viên
		textUserName.setText(NhanVienDAO.getTenNV(LoginController.getInstance().txtUser.getText()));
		taiKhoan = TaiKhoanDAO.getInfor(NhanVienDAO.getMaNV(LoginController.getInstance().txtUser.getText()));
		txtTenTaiKhoan.setText(taiKhoan.getTenTaiKhoan());
		txtMatKhau.setText(taiKhoan.getMatKhau());
		txtGmail.setText(taiKhoan.getGmail());
		txtMaNhanVien.setText(taiKhoan.getMaNhanVien());
		txtQuyenHan.setText(taiKhoan.getMaQuyen());

		// O*O**** Khởi tạo nút Quản lý tắt hay mở tùy vào Quyền hạn của nhân viên
		if (taiKhoan.getMaQuyen().equalsIgnoreCase("Q2")) {
			panBtnQuanLy.setVisible(false);
		}

		// - Khởi tạo data cho Thông tin chỉnh sửa
		txtUpdTenTaiKhoan.setText(taiKhoan.getTenTaiKhoan());
		txtUpdPassword.setText(taiKhoan.getMatKhau());
		txtUpdGmail.setText(taiKhoan.getGmail());
		txtUpdMaNhanVien.setText(taiKhoan.getMaNhanVien());
		txtUpdMaQuyenHan.setText(taiKhoan.getMaQuyen());

		// -Khởi tạo data cho 2 table
		tableNhanVien.setPlaceholder(new Text("Không có dữ liệu !!!"));
		tableTaiKhoan.setPlaceholder(new Text("Không có dữ liệu !!!"));
		colMaNhanVien.setCellValueFactory(new PropertyValueFactory<>("maNhanVien"));
		colTenNhanVien.setCellValueFactory(new PropertyValueFactory<>("tenNhanVien"));
		colNgaySinh.setCellFactory(TextFieldTableCell.<NhanVien, Date>forTableColumn(new StringConverter<Date>() {
			private final SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");

			@Override
			public String toString(final Date value) {
				return format.format(value);
			}

			@Override
			public Date fromString(String string) {
				return null;
			}
		}));
		colNgaySinh.setCellValueFactory(new PropertyValueFactory<>("ngaySinh"));
		colGioiTinh.setCellValueFactory(new PropertyValueFactory<>("gioiTinh"));
		colDiaChi.setCellValueFactory(new PropertyValueFactory<>("diaChi"));
		colSDT.setCellValueFactory(new PropertyValueFactory<>("soDienThoai"));
		colMaQuyen.setCellValueFactory(new PropertyValueFactory<>("maQuyen"));
		listNhanVien = NhanVienDAO.getListNhanVien();
		tableNhanVien.setItems(listNhanVien);

		colTenTaiKhoan.setCellValueFactory(new PropertyValueFactory<>("tenTaiKhoan"));
		colGmail.setCellValueFactory(new PropertyValueFactory<>("gmail"));
		colMaNhanVienTK.setCellValueFactory(new PropertyValueFactory<>("maNhanVien"));
		listtaiKhoan = TaiKhoanDAO.getListTaiKhoan();
		tableTaiKhoan.setItems(listtaiKhoan);

	}

}
