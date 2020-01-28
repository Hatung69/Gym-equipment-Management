package controllerFXML.equipment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXSnackbar.SnackbarEvent;

import controllerFXML.home.HomePageController;
import controllerFXML.login.LoginController;
import controllerFXML.maintenance.MaintenanceController;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.util.StringConverter;
import model.baotri.BaoTriDAO;
import model.nhanvien.NhanVienDAO;
import model.thietbi.ThietBi;
import model.thietbi.ThietBiDAO;

public class EquipmentController implements Initializable {
	private static EquipmentController instance;

	public static EquipmentController getInstance() {
		return instance;
	}

	public EquipmentController() {
		instance = this;
	}

	// -------------------------------------------------------
	@FXML
	protected JFXTabPane tabPane;
	@FXML
	public TableView<ThietBi> tableThietBi;
	@FXML
	private TableColumn<ThietBi, String> colMaThietBi, colTenThietBi, colLoai, colChatLieu, colThuongHieu, colBaiTap,
			colTinhTrang, colNhanVien;
	@FXML
	private TableColumn<ThietBi, Integer> colKhoiLuong, colTriGia;
	@FXML
	private TableColumn<ThietBi, Date> colNgayNhap, colNamSX, colHanDung;
	// ---Infor
	@FXML
	private JFXTextField txtInforMaTB, txtInforTenTB, txtInforLoaiTB, txtInforKhoiLuong, txtInforChatLieu,
			txtInforTriGia, txtInforThuongHieu, txtInforBaiTap, txtInforTinhTrang, txtInforNhanVien, txtInforNgayNhap,
			txtInforNSX, txtInforHSD;
	@FXML
	private ImageView imageViewInfor;
	// ---Add
	@FXML
	protected JFXTextField txtMaThietBi, txtLoaiThietBi, txtKhoiLuong, txtChatLieu, txtTriGia, txtThuongHieu, txtBaiTap,
			txtTinhTrang, txtNhanVien;
	public JFXTextField txtTenThietBi;
	@FXML
	protected JFXDatePicker pickerNgayNhap, pickerNamSX, pickerHanSD;
	@FXML
	private ImageView imageViewAdd;
	// ---Update
	@FXML
	protected JFXTextField txtUpdMaThietBi, txtupdLoaiThietBi, txtUpdKhoiLuong, txtupdChatLieu, txtUpdTriGia,
			txtUpdThuongHieu, txtUpdBaiTap, txtUpdTinhTrang, txtUpdNhaVien;
	public JFXTextField txtUpdTenThietBi;
	@FXML
	private JFXDatePicker pickerUpdNgayNhap, pickerUpdNamSX, pickerUpdHanSD;
	@FXML
	private ImageView imageViewUpd;

	@FXML
	protected AnchorPane paneThongTinChiTiet, paneThemMoi, paneChinhSua;
	@FXML
	public JFXSnackbar snack;
	@FXML
	public Label lablSnack;
	@FXML
	private JFXTextField txtTimKiem;
	@FXML
	public AnchorPane anchorThietBi, anchorThuongHieu, anchorBaiTap;
	@FXML
	private JFXButton btnThemMoi, btnChinhSua;

	private AnchorPane anchorPaneChatLieu, anchorPaneLoaiTB, anchorPaneThuongHieu, anchorPaneBaiTap, anchorPaneBaoTri;
	protected JFXDialog dialogLoaiThietBi, dialogChatLieu, dialogThuongHieu, dialogBaiTap, dialogBaoTri;
	public ObservableList<ThietBi> listThietBi;
	private File pic = new File("FrontEnd/Images/Home/DefaultHinhAnh.png");
	private FileInputStream fileInputStreamAdd, fileInputStreamUpdate;
	private InputStream inputStream, inputStreamUpdate;

	// ===== Lấy data từ Table Thiết bị =====
	@FXML
	void clickItemThietBi(MouseEvent event) throws SQLException, IOException {
		try {
			if (event.getClickCount() == 1) {
				if (tableThietBi.getSelectionModel().getSelectedItem() == null) {
					return;
				} else {
					inputStream = tableThietBi.getSelectionModel().getSelectedItem().getHinhAnh().getBinaryStream();
					Image image = new Image(inputStream);
					if (paneThongTinChiTiet.isVisible()) {
						final SimpleDateFormat formatDate = new SimpleDateFormat("dd-MM-yyyy");
						txtInforMaTB.setText(tableThietBi.getSelectionModel().getSelectedItem().getMaThietBi());
						txtInforTenTB.setText(tableThietBi.getSelectionModel().getSelectedItem().getTenThietBi());
						txtInforLoaiTB.setText(tableThietBi.getSelectionModel().getSelectedItem().getLoai());
						txtInforKhoiLuong.setText(String
								.valueOf(tableThietBi.getSelectionModel().getSelectedItem().getKhoiLuong() + " Kg"));
						txtInforChatLieu.setText(tableThietBi.getSelectionModel().getSelectedItem().getChatLieu());
						final NumberFormat format = NumberFormat.getNumberInstance();
						txtInforTriGia.setText(String.valueOf(
								format.format(tableThietBi.getSelectionModel().getSelectedItem().getTriGia()) + " ₫"));
						txtInforThuongHieu
								.setText(tableThietBi.getSelectionModel().getSelectedItem().getTenThuongHieu());
						txtInforBaiTap.setText(tableThietBi.getSelectionModel().getSelectedItem().getTenBaiTap());
						txtInforTinhTrang.setText(tableThietBi.getSelectionModel().getSelectedItem().getTenTinhTrang());
						txtInforNhanVien.setText(
								String.valueOf(tableThietBi.getSelectionModel().getSelectedItem().getTenNhanVien()));
						txtInforNgayNhap.setText(
								formatDate.format(tableThietBi.getSelectionModel().getSelectedItem().getNgayNhap()));
						txtInforNSX.setText(
								formatDate.format(tableThietBi.getSelectionModel().getSelectedItem().getNamSX()));
						txtInforHSD.setText(
								formatDate.format(tableThietBi.getSelectionModel().getSelectedItem().getHanSuDung()));
						imageViewInfor.setImage(image);
						inputStream.close();
					} else if (paneChinhSua.isVisible()) {
						// -----
						txtUpdMaThietBi.setText(tableThietBi.getSelectionModel().getSelectedItem().getMaThietBi());
						txtUpdTenThietBi.setText(tableThietBi.getSelectionModel().getSelectedItem().getTenThietBi());
						txtupdLoaiThietBi.setText(
								ThietBiDAO.getMaLoaiTB(tableThietBi.getSelectionModel().getSelectedItem().getLoai())
										+ "-" + tableThietBi.getSelectionModel().getSelectedItem().getLoai());
						txtUpdKhoiLuong.setText(
								String.valueOf(tableThietBi.getSelectionModel().getSelectedItem().getKhoiLuong()));
						txtupdChatLieu.setText(ThietBiDAO
								.getMaChatLieu(tableThietBi.getSelectionModel().getSelectedItem().getChatLieu()) + "-"
								+ tableThietBi.getSelectionModel().getSelectedItem().getChatLieu());
						txtUpdTriGia.setText(
								String.valueOf(tableThietBi.getSelectionModel().getSelectedItem().getTriGia()));
						txtUpdThuongHieu.setText(ThietBiDAO
								.getMaTH(tableThietBi.getSelectionModel().getSelectedItem().getTenThuongHieu()) + "-"
								+ tableThietBi.getSelectionModel().getSelectedItem().getTenThuongHieu());
						txtUpdBaiTap.setText(
								ThietBiDAO.getMaBT(tableThietBi.getSelectionModel().getSelectedItem().getTenBaiTap())
										+ "-" + tableThietBi.getSelectionModel().getSelectedItem().getTenBaiTap());
						txtUpdTinhTrang.setText(tableThietBi.getSelectionModel().getSelectedItem().getTenTinhTrang());
						txtUpdNhaVien.setText(NhanVienDAO.getTenNV(LoginController.getInstance().txtUser.getText()));
						pickerUpdNgayNhap.setValue(LOCAL_DATE(
								tableThietBi.getSelectionModel().getSelectedItem().getNgayNhap().toString()));
						pickerUpdNamSX.setValue(
								LOCAL_DATE(tableThietBi.getSelectionModel().getSelectedItem().getNamSX().toString()));
						pickerUpdHanSD.setValue(LOCAL_DATE(
								tableThietBi.getSelectionModel().getSelectedItem().getHanSuDung().toString()));
						imageViewUpd.setImage(image);
						inputStream.close();
					}
				}
			}
		} catch (Exception e) {
			return;
		}
	}

	public static final LocalDate LOCAL_DATE(String dateString) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate localDate = LocalDate.parse(dateString, formatter);

		return localDate;
	}

	// ===== Mở danh sách Loại thiết bị=====
	@FXML
	void openLoaiTBAction(ActionEvent event) throws IOException {
		anchorPaneLoaiTB = FXMLLoader.load(getClass().getResource("/viewFXML/equipment/dialogLoaiTB.fxml"));
		dialogLoaiThietBi = new JFXDialog(HomePageController.getInstance().stackPaneLoadLayout, anchorPaneLoaiTB,
				JFXDialog.DialogTransition.CENTER);
		dialogLoaiThietBi.show();
	}

	// ===== Mở danh sách Chất liệu =====
	@FXML
	void openChatLieuAction(ActionEvent event) throws IOException {
		anchorPaneChatLieu = FXMLLoader.load(getClass().getResource("/viewFXML/equipment/dialogChatChieu.fxml"));
		dialogChatLieu = new JFXDialog(HomePageController.getInstance().stackPaneLoadLayout, anchorPaneChatLieu,
				JFXDialog.DialogTransition.CENTER);
		dialogChatLieu.show();
	}

	// ===== Mở danh sách Thuong hiệu =====
	@FXML
	void openThuongHieuAction(ActionEvent event) throws IOException {
		anchorPaneThuongHieu = FXMLLoader.load(getClass().getResource("/viewFXML/equipment/dialogThuongHieu.fxml"));
		dialogThuongHieu = new JFXDialog(HomePageController.getInstance().stackPaneLoadLayout, anchorPaneThuongHieu,
				JFXDialog.DialogTransition.CENTER);
		dialogThuongHieu.show();
	}

	// ===== Mở danh sách Bài tập =====
	@FXML
	void openBaiTapAction(ActionEvent event) throws IOException {
		anchorPaneBaiTap = FXMLLoader.load(getClass().getResource("/viewFXML/equipment/dialogBaiTap.fxml"));
		dialogBaiTap = new JFXDialog(HomePageController.getInstance().stackPaneLoadLayout, anchorPaneBaiTap,
				JFXDialog.DialogTransition.CENTER);
		dialogBaiTap.show();
	}

	// ===== Trở lại xem thông tin thiết bị =====
	@FXML
	void backPaneThongTinAction(ActionEvent event) {
		if (paneThemMoi.isVisible() || paneChinhSua.isVisible()) {
			paneThemMoi.setVisible(false);
			paneChinhSua.setVisible(false);
			paneThongTinChiTiet.setVisible(true);
			btnThemMoi.setDisable(false);
			btnChinhSua.setDisable(false);
		} else {
			return;
		}
	}

	// ===== Mở bảng Thêm mới Thiết bị =====
	@FXML
	void themMoiAction(ActionEvent event) {
		if (paneThongTinChiTiet.isVisible() || paneChinhSua.isVisible()) {
			paneThongTinChiTiet.setVisible(false);
			paneChinhSua.setVisible(false);
			paneThemMoi.setVisible(true);
			btnThemMoi.setDisable(true);
			btnChinhSua.setDisable(false);
		} else {
			return;
		}
	}

	// ===== Mở bảng Chỉnh sửa Thiết bị =====
	@FXML
	void chinhsuaAction(ActionEvent event) {
		if (paneThongTinChiTiet.isVisible() || paneThemMoi.isVisible()) {
			paneThongTinChiTiet.setVisible(false);
			paneThemMoi.setVisible(false);
			txtUpdMaThietBi.clear();
			paneChinhSua.setVisible(true);
			btnChinhSua.setDisable(true);
			btnThemMoi.setDisable(false);
		} else {
			return;
		}
	}

	// ===== Làm sạch giá trị Tìm kiếm =====
	@FXML
	void clearSeachText(ActionEvent event) {
		txtTimKiem.clear();
		searchRealTime();
	}

	// ===== Làm sạch giá trị Chỉnh sửa =====
	@FXML
	void clearTextUpdAction(ActionEvent event) {
		claerUpd();
	}

	private void claerUpd() {
		txtUpdMaThietBi.clear();
		txtUpdTenThietBi.clear();
		txtupdLoaiThietBi.clear();
		txtUpdKhoiLuong.clear();
		txtupdChatLieu.clear();
		txtUpdTriGia.clear();
		txtUpdThuongHieu.clear();
		txtUpdBaiTap.clear();
		pickerUpdNgayNhap.setValue(null);
		pickerUpdNamSX.setValue(null);
		pickerUpdHanSD.setValue(null);
		imageViewUpd.setImage(null);
	}

	// ===== Làm sạch giá trị Thêm mới =====
	@FXML
	void clearTextAction(ActionEvent event) {
		clearAdd();
		imageViewAdd.setImage(null);
	}

	private void clearAdd() {
		txtMaThietBi.clear();
		txtTenThietBi.clear();
		txtLoaiThietBi.clear();
		txtKhoiLuong.clear();
		txtChatLieu.clear();
		txtTriGia.clear();
		txtThuongHieu.clear();
		txtBaiTap.clear();
		pickerNgayNhap.setValue(null);
		pickerNamSX.setValue(null);
		pickerHanSD.setValue(null);
	}

	// oooooooooooooo THÊM MỚI Thiết bị ooooooooooo
	@FXML
	void themVaoAction(ActionEvent event) throws ParseException, IOException {
		// Kiểm tra Mã thiết bị
		if (ThietBiDAO.maTBIsExist(txtMaThietBi.getText())) {
			JFXDialogLayout dialogLayout = new JFXDialogLayout();
			dialogLayout.setStyle(
					"-fx-background-color: Orange;-fx-border-color: Black;-fx-border-width: 5 ;-fx-border-color:Black ;-fx-text-fill: White;-fx-effect: dropshadow(three-pass-box, DarkRed, 10, 0, 0, 0);");
			Image image = new Image(getClass().getResourceAsStream("/Images/JFXDialog/information.png"));
			Label label = new Label("Mã thiết bị đã tồn tại", new ImageView(image));
			label.setStyle("-fx-text-fill: DarkRed;-fx-font: 20 System; -fx-font-weight: Bold;");
			dialogLayout.setHeading(label);
			Text text = new Text("Vui lòng nhập mã khác !");
			text.setStyle("-fx-font: 15 System");
			dialogLayout.setBody(text);
			JFXDialog dialog = new JFXDialog(HomePageController.getInstance().mainStackPane, dialogLayout,
					JFXDialog.DialogTransition.CENTER);
			dialog.show();
			return;
		} else if (txtMaThietBi.getText().trim().isEmpty() || txtTenThietBi.getText().trim().isEmpty()
				|| txtLoaiThietBi.getText().trim().isEmpty() || txtKhoiLuong.getText().trim().isEmpty()
				|| txtChatLieu.getText().trim().isEmpty() || txtTriGia.getText().trim().isEmpty()
				|| txtThuongHieu.getText().trim().isEmpty() || txtBaiTap.getText().trim().isEmpty()
				|| pickerNgayNhap.getValue() == null || pickerNamSX.getValue() == null
				|| pickerHanSD.getValue() == null) {
			JFXDialogLayout dialogLayout = new JFXDialogLayout();
			dialogLayout.setStyle(
					"-fx-background-color: Orange;-fx-border-color: Black;-fx-border-width: 5 ;-fx-border-color:Black ;-fx-text-fill: White;-fx-effect: dropshadow(three-pass-box, DarkRed, 10, 0, 0, 0);");
			Image image = new Image(getClass().getResourceAsStream("/Images/JFXDialog/information.png"));
			Label label = new Label("Kiểm tra lại thông tin", new ImageView(image));
			label.setStyle("-fx-text-fill: DarkRed;-fx-font: 20 System; -fx-font-weight: Bold;");
			dialogLayout.setHeading(label);
			Text text = new Text("Vui lòng cung cấp đầy đủ thông tin của thiết bị!");
			text.setStyle("-fx-font: 15 System");
			dialogLayout.setBody(text);
			JFXDialog dialog = new JFXDialog(HomePageController.getInstance().mainStackPane, dialogLayout,
					JFXDialog.DialogTransition.CENTER);
			dialog.show();
			return;
		} else if (!checkNumber(txtKhoiLuong.getText()) || !checkNumber(txtTriGia.getText())) {
			JFXDialogLayout dialogLayout = new JFXDialogLayout();
			dialogLayout.setStyle(
					"-fx-background-color: Orange;-fx-border-color: Black;-fx-border-width: 5 ;-fx-border-color:Black ;-fx-text-fill: White;-fx-effect: dropshadow(three-pass-box, DarkRed, 10, 0, 0, 0);");
			Image image = new Image(getClass().getResourceAsStream("/Images/JFXDialog/information.png"));
			Label label = new Label("Kiểm tra lại thông tin", new ImageView(image));
			label.setStyle("-fx-text-fill: DarkRed;-fx-font: 20 System; -fx-font-weight: Bold;");
			dialogLayout.setHeading(label);
			Text text = new Text("Vui lòng điền đúng giá trị số của Khối lượng và Trị giá !");
			text.setStyle("-fx-font: 15 System");
			dialogLayout.setBody(text);
			JFXDialog dialog = new JFXDialog(HomePageController.getInstance().mainStackPane, dialogLayout,
					JFXDialog.DialogTransition.CENTER);
			dialog.show();
			return;
		} else {
			if (fileInputStreamAdd == null) {
				fileInputStreamAdd = new FileInputStream(pic);
			}
			java.sql.Date sqlDate = java.sql.Date.valueOf(pickerNgayNhap.getValue());
			java.sql.Date sqlDate2 = java.sql.Date.valueOf(pickerNamSX.getValue());
			java.sql.Date sqlDate3 = java.sql.Date.valueOf(pickerHanSD.getValue());

			ThietBiDAO.themThietBi(txtMaThietBi.getText().trim(), txtTenThietBi.getText().trim(),
					txtLoaiThietBi.getText().split("-")[0], Integer.valueOf(txtKhoiLuong.getText().trim()),
					txtChatLieu.getText().split("-")[0], Integer.valueOf(txtTriGia.getText().trim()),
					txtThuongHieu.getText().split("-")[0], txtBaiTap.getText().split("-")[0], "TT1",
					NhanVienDAO.getMaNV(LoginController.getInstance().txtUser.getText()), sqlDate, sqlDate2, sqlDate3,
					fileInputStreamAdd);
			fileInputStreamAdd = new FileInputStream(pic);
			InputStream inputStream = new FileInputStream(pic);
			Image image = new Image(inputStream);
			imageViewAdd.setImage(image);
			txtMaThietBi.clear();
			// Load data..........
			listThietBi = ThietBiDAO.getListThietBi();
			tableThietBi.setItems(listThietBi);
			searchRealTime();
		}
	}

	public static boolean checkNumber(String s) {
		for (int i = 0; i < s.length(); i++) {
			if (Character.isDigit(s.charAt(i))) {

			} else
				return false;
		}
		return true;
	}

	@FXML
	void openChonAnhAction(ActionEvent event) throws IOException {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Chọn File Ảnh cho Thiết bị");
		String[] filter = { "*.png", "*.jpg" };
		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PNG,JPG", filter));
		File file = fileChooser.showOpenDialog(((Node) event.getTarget()).getScene().getWindow());
		if (file == null) {
			return;
		} else {
			fileInputStreamAdd = new FileInputStream(file);
			InputStream inputStream = new FileInputStream(file);
			Image image = new Image(inputStream);
			imageViewAdd.setImage(image);
		}
	}

	// oooooooooooooooo CẬP NHẬT Thiết bị ooooooooooooo
	@FXML
	void capNhatAction(ActionEvent event) throws FileNotFoundException, SQLException {
		if (txtUpdMaThietBi.getText().trim().isEmpty() || tableThietBi.getSelectionModel().getSelectedItem() == null
				|| tableThietBi.getSelectionModel().getSelectedItem().getTenBaiTap().isEmpty()) {
			JFXDialogLayout dialogLayout = new JFXDialogLayout();
			dialogLayout.setStyle(
					"-fx-background-color: Orange;-fx-border-color: Black;-fx-border-width: 5 ;-fx-border-color:Black ;-fx-text-fill: White;-fx-effect: dropshadow(three-pass-box, DarkRed, 10, 0, 0, 0);");
			Image image = new Image(getClass().getResourceAsStream("/Images/JFXDialog/warning.png"));
			Label label = new Label("Chọn thiết bị", new ImageView(image));
			label.setStyle("-fx-text-fill: DarkRed;-fx-font: 20 System; -fx-font-weight: Bold;");
			dialogLayout.setHeading(label);
			Text text = new Text("Bạn có thể Click vào Danh sách trên để chọn Thiết bị cần sửa !");
			text.setStyle("-fx-font: 15 System");
			dialogLayout.setBody(text);
			JFXDialog dialog = new JFXDialog(HomePageController.getInstance().mainStackPane, dialogLayout,
					JFXDialog.DialogTransition.CENTER);
			dialog.show();
			return;
		} else if (txtUpdTenThietBi.getText().trim().isEmpty() || txtupdLoaiThietBi.getText().trim().isEmpty()
				|| txtUpdKhoiLuong.getText().trim().isEmpty() || txtupdChatLieu.getText().trim().isEmpty()
				|| txtUpdTriGia.getText().trim().isEmpty() || txtUpdThuongHieu.getText().trim().isEmpty()
				|| txtUpdBaiTap.getText().trim().isEmpty() || pickerUpdNgayNhap.getValue() == null
				|| pickerUpdNamSX.getValue() == null || pickerUpdHanSD.getValue() == null) {
			JFXDialogLayout dialogLayout = new JFXDialogLayout();
			dialogLayout.setStyle(
					"-fx-background-color: Orange;-fx-border-color: Black;-fx-border-width: 5 ;-fx-border-color:Black ;-fx-text-fill: White;-fx-effect: dropshadow(three-pass-box, DarkRed, 10, 0, 0, 0);");
			Image image = new Image(getClass().getResourceAsStream("/Images/JFXDialog/information.png"));
			Label label = new Label("Kiểm tra lại thông tin", new ImageView(image));
			label.setStyle("-fx-text-fill: DarkRed;-fx-font: 20 System; -fx-font-weight: Bold;");
			dialogLayout.setHeading(label);
			Text text = new Text("Vui lòng cung cấp đầy đủ thông tin để cập nhật!");
			text.setStyle("-fx-font: 15 System");
			dialogLayout.setBody(text);
			JFXDialog dialog = new JFXDialog(HomePageController.getInstance().mainStackPane, dialogLayout,
					JFXDialog.DialogTransition.CENTER);
			dialog.show();
			return;
		} else if (!checkNumber(txtUpdKhoiLuong.getText()) || !checkNumber(txtUpdTriGia.getText())) {
			JFXDialogLayout dialogLayout = new JFXDialogLayout();
			dialogLayout.setStyle(
					"-fx-background-color: Orange;-fx-border-color: Black;-fx-border-width: 5 ;-fx-border-color:Black ;-fx-text-fill: White;-fx-effect: dropshadow(three-pass-box, DarkRed, 10, 0, 0, 0);");
			Image image = new Image(getClass().getResourceAsStream("/Images/JFXDialog/information.png"));
			Label label = new Label("Kiểm tra lại thông tin", new ImageView(image));
			label.setStyle("-fx-text-fill: DarkRed;-fx-font: 20 System; -fx-font-weight: Bold;");
			dialogLayout.setHeading(label);
			Text text = new Text("Vui lòng điền đúng giá trị số của Khối lượng và Trị giá !");
			text.setStyle("-fx-font: 15 System");
			dialogLayout.setBody(text);
			JFXDialog dialog = new JFXDialog(HomePageController.getInstance().mainStackPane, dialogLayout,
					JFXDialog.DialogTransition.CENTER);
			dialog.show();
			return;
		} else {
			java.sql.Date sqlDate = java.sql.Date.valueOf(pickerUpdNgayNhap.getValue());
			java.sql.Date sqlDate2 = java.sql.Date.valueOf(pickerUpdNamSX.getValue());
			java.sql.Date sqlDate3 = java.sql.Date.valueOf(pickerUpdHanSD.getValue());
			String maTinhTrang = "";
			if (txtUpdTinhTrang.getText().equals("Bình thường")) {
				maTinhTrang = "TT1";
			} else if (txtUpdTinhTrang.getText().equals("Đang bảo trì")) {
				maTinhTrang = "TT2";
			}
			if (fileInputStreamUpdate == null) {
				inputStreamUpdate = tableThietBi.getSelectionModel().getSelectedItem().getHinhAnh().getBinaryStream();
				ThietBiDAO.capNhatThietBi(txtUpdMaThietBi.getText().trim(), txtUpdTenThietBi.getText().trim(),
						txtupdLoaiThietBi.getText().split("-")[0], Integer.valueOf(txtUpdKhoiLuong.getText().trim()),
						txtupdChatLieu.getText().split("-")[0], Integer.valueOf(txtUpdTriGia.getText().trim()),
						txtUpdThuongHieu.getText().split("-")[0], txtUpdBaiTap.getText().split("-")[0], maTinhTrang,
						NhanVienDAO.getMaNV(LoginController.getInstance().txtUser.getText()), sqlDate, sqlDate2,
						sqlDate3, inputStreamUpdate);
				txtUpdMaThietBi.clear();
				MaintenanceController.getInstance().listBaoTri = BaoTriDAO.getListBaoTri();
				MaintenanceController.getInstance().tableBaoTri
						.setItems(MaintenanceController.getInstance().listBaoTri);
			} else {
				ThietBiDAO.capNhatThietBi(txtUpdMaThietBi.getText().trim(), txtUpdTenThietBi.getText().trim(),
						txtupdLoaiThietBi.getText().split("-")[0], Integer.valueOf(txtUpdKhoiLuong.getText().trim()),
						txtupdChatLieu.getText().split("-")[0], Integer.valueOf(txtUpdTriGia.getText().trim()),
						txtUpdThuongHieu.getText().split("-")[0], txtUpdBaiTap.getText().split("-")[0], maTinhTrang,
						NhanVienDAO.getMaNV(LoginController.getInstance().txtUser.getText()), sqlDate, sqlDate2,
						sqlDate3, fileInputStreamUpdate);
				txtUpdMaThietBi.clear();
				MaintenanceController.getInstance().listBaoTri = BaoTriDAO.getListBaoTri();
				MaintenanceController.getInstance().tableBaoTri
						.setItems(MaintenanceController.getInstance().listBaoTri);
				fileInputStreamUpdate = null;
			}
			// Load data..........
			listThietBi = ThietBiDAO.getListThietBi();
			tableThietBi.setItems(listThietBi);
			searchRealTime();
		}
	}

	@FXML
	void doiAnhAction(ActionEvent event) throws FileNotFoundException {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Chọn File Ảnh cần đổi");
		String[] filter = { "*.png", "*.jpg" };
		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PNG,JPG", filter));
		File file = fileChooser.showOpenDialog(((Node) event.getTarget()).getScene().getWindow());
		if (file == null) {
			return;
		} else {
			fileInputStreamUpdate = new FileInputStream(file);
			InputStream inputStream = new FileInputStream(file);
			Image image = new Image(inputStream);
			imageViewUpd.setImage(image);
		}
	}

	// ooooooooooooooooo XÓA BỎ Thiết bị oooooooooooo
	@FXML
	void xoaBoAction(ActionEvent event) {
		if (tableThietBi.getSelectionModel().getSelectedItem() == null) {
			JFXDialogLayout dialogLayout = new JFXDialogLayout();
			dialogLayout.setStyle(
					"-fx-background-color: Orange;-fx-border-color: Black;-fx-border-width: 5 ;-fx-border-color:Black ;-fx-text-fill: White;-fx-effect: dropshadow(three-pass-box, DarkRed, 10, 0, 0, 0);");
			Image image = new Image(getClass().getResourceAsStream("/Images/JFXDialog/information.png"));
			Label label = new Label("Chọn thiết bị", new ImageView(image));
			label.setStyle("-fx-text-fill: DarkRed;-fx-font: 20 System; -fx-font-weight: Bold;");
			dialogLayout.setHeading(label);
			Text text = new Text("Vui lòng chọn 1 thiết bị từ danh sách để Xóa!");
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
			Text text = new Text("Bạn muốn Xóa thiết bị : " + EquipmentController.getInstance().tableThietBi
					.getSelectionModel().getSelectedItem().getTenThietBi());
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
					ThietBiDAO.xoaThietBi(tableThietBi.getSelectionModel().getSelectedItem().getMaThietBi());
					dialog.close();
					// Load data..........
					listThietBi = ThietBiDAO.getListThietBi();
					tableThietBi.setItems(listThietBi);
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
		FilteredList<ThietBi> filteredList = new FilteredList<ThietBi>(listThietBi, b -> true);
		txtTimKiem.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredList.setPredicate(thietbi -> {
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}
				String lowerCasefilter = newValue.toLowerCase();
				if (thietbi.getMaThietBi().toLowerCase().indexOf(lowerCasefilter) != -1) {
					return true;
				} else if (thietbi.getTenThietBi().toLowerCase().indexOf(lowerCasefilter) != -1) {
					return true;
				} else if (thietbi.getLoai().toLowerCase().indexOf(lowerCasefilter) != -1) {
					return true;
				} else if (thietbi.getChatLieu().toLowerCase().indexOf(lowerCasefilter) != -1) {
					return true;
				} else if (thietbi.getTenThuongHieu().toLowerCase().indexOf(lowerCasefilter) != -1) {
					return true;
				} else if (thietbi.getTenBaiTap().toLowerCase().indexOf(lowerCasefilter) != -1) {
					return true;
				} else if (thietbi.getTenTinhTrang().toLowerCase().indexOf(lowerCasefilter) != -1) {
					return true;
				} else if (String.valueOf(thietbi.getKhoiLuong()).indexOf(lowerCasefilter) != -1) {
					return true;
				} else {
					return false;
				}
			});
		});
		SortedList<ThietBi> sortedList = new SortedList<ThietBi>(filteredList);
		sortedList.comparatorProperty().bind(tableThietBi.comparatorProperty());
		tableThietBi.setItems(sortedList);
	}

	// ooooooooooooooooooooooooo XUẤT DS oooooooooooooooooooooooo
	@FXML
	void xuatDanhSach(ActionEvent event) throws IOException {
		Workbook workbook = new HSSFWorkbook();
		Sheet spreadsheet = workbook.createSheet("sample");
		Row row = spreadsheet.createRow(0);
		for (int j = 0; j < tableThietBi.getColumns().size(); j++) {
			row.createCell(j).setCellValue(tableThietBi.getColumns().get(j).getText());
		}
		for (int i = 0; i < tableThietBi.getItems().size(); i++) {
			row = spreadsheet.createRow(i + 1);
			for (int j = 0; j < tableThietBi.getColumns().size(); j++) {
				if (tableThietBi.getColumns().get(j).getCellData(i) != null) {
					row.createCell(j).setCellValue(tableThietBi.getColumns().get(j).getCellData(i).toString());
				} else {
					row.createCell(j).setCellValue("");
				}
			}
		}
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Xuất Danh sách thiết bị");
		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("XLSX files (*.xls)", "*.xls"));
		File saveFile = fileChooser.showSaveDialog(tableThietBi.getScene().getWindow());
		if (saveFile == null) {
			return;
		} else {
			FileOutputStream fileOut = new FileOutputStream(saveFile);
			workbook.write(fileOut);
			fileOut.close();

			snack = new JFXSnackbar(HomePageController.getInstance().stackPaneLoadLayout);
			lablSnack.setText("Xuất danh sách thành công !");
			snack.enqueue(new SnackbarEvent(EquipmentController.getInstance().lablSnack));
		}
	}

	// oooooooooooooooooooooooo ĐƯA VÀO BẢO TRÌ ooooooooooooooooooooooooo
	@FXML
	void baoTriAction(ActionEvent event) throws IOException {
		if (tableThietBi.getSelectionModel().getSelectedItem() == null) {
			JFXDialogLayout dialogLayout = new JFXDialogLayout();
			dialogLayout.setStyle(
					"-fx-background-color: Orange;-fx-border-color: Black;-fx-border-width: 5 ;-fx-border-color:Black ;-fx-text-fill: White;-fx-effect: dropshadow(three-pass-box, DarkRed, 10, 0, 0, 0);");
			Image image = new Image(getClass().getResourceAsStream("/Images/JFXDialog/warning.png"));
			Label label = new Label("Chọn thiết bị", new ImageView(image));
			label.setStyle("-fx-text-fill: DarkRed;-fx-font: 20 System; -fx-font-weight: Bold;");
			dialogLayout.setHeading(label);
			Text text = new Text("Vui lòng chọn 1 thiết bị từ danh sách để Bảo trì!");
			text.setStyle("-fx-font: 15 System");
			dialogLayout.setBody(text);
			JFXDialog dialog = new JFXDialog(HomePageController.getInstance().mainStackPane, dialogLayout,
					JFXDialog.DialogTransition.CENTER);
			dialog.show();
			return;
		} else if (tableThietBi.getSelectionModel().getSelectedItem().getTenTinhTrang().equals("Đang bảo trì")) {
			JFXDialogLayout dialogLayout = new JFXDialogLayout();
			dialogLayout.setStyle(
					"-fx-background-color: Orange;-fx-border-color: Black;-fx-border-width: 5 ;-fx-border-color:Black ;-fx-text-fill: White;-fx-effect: dropshadow(three-pass-box, DarkRed, 10, 0, 0, 0);");
			Image image = new Image(getClass().getResourceAsStream("/Images/JFXDialog/information.png"));
			Label label = new Label("Thiết bị hiện đang bảo trì", new ImageView(image));
			label.setStyle("-fx-text-fill: DarkRed;-fx-font: 20 System; -fx-font-weight: Bold;");
			dialogLayout.setHeading(label);
			Text text = new Text("Vui lòng vào xem Bảo trì để biết thêm chi tiết");
			text.setStyle("-fx-font: 15 System");
			dialogLayout.setBody(text);
			JFXDialog dialog = new JFXDialog(HomePageController.getInstance().mainStackPane, dialogLayout,
					JFXDialog.DialogTransition.CENTER);
			dialog.show();
			return;
		} else {
			anchorPaneBaoTri = FXMLLoader.load(getClass().getResource("/viewFXML/equipment/dialogDuaVaoBaoTri.fxml"));
			dialogBaoTri = new JFXDialog(HomePageController.getInstance().stackPaneLoadLayout, anchorPaneBaoTri,
					JFXDialog.DialogTransition.CENTER);
			dialogBaoTri.show();
		}
	}

	// ===== * Khởi tạo Controller * =====
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// -Load data Thiết bị lên Table
		tableThietBi.setPlaceholder(new Text("Không có dữ liệu !!!"));
		colMaThietBi.setCellValueFactory(new PropertyValueFactory<>("maThietBi"));
		colTenThietBi.setCellValueFactory(new PropertyValueFactory<>("tenThietBi"));
		colTenThietBi.setStyle(
				"-fx-text-fill:linear-gradient(to right bottom, #000000, #331019, #620f24, #920728, #c20023, #c20023, #c20023, #c20023, #920728, #620f24, #331019, #000000);-fx-font: 13px \"System\"; -fx-font-weight: Bold;");
		colLoai.setCellValueFactory(new PropertyValueFactory<>("loai"));
		colKhoiLuong.setCellFactory(TextFieldTableCell.<ThietBi, Integer>forTableColumn(new StringConverter<Integer>() {
			@Override
			public String toString(final Integer value) {
				return value + " Kg";
			}

			@Override
			public Integer fromString(final String s) {
				return null;
			}
		}));
		colKhoiLuong.setCellValueFactory(new PropertyValueFactory<>("khoiLuong"));
		colTriGia.setCellFactory(TextFieldTableCell.<ThietBi, Integer>forTableColumn(new StringConverter<Integer>() {
			private final NumberFormat nf = NumberFormat.getNumberInstance();

			@Override
			public String toString(final Integer value) {
				return nf.format(value) + " ₫";
			}

			@Override
			public Integer fromString(final String s) {
				return null;
			}
		}));
		colChatLieu.setCellValueFactory(new PropertyValueFactory<>("chatLieu"));
		colTriGia.setCellValueFactory(new PropertyValueFactory<>("triGia"));
		colThuongHieu.setCellValueFactory(new PropertyValueFactory<>("tenThuongHieu"));
		colBaiTap.setCellValueFactory(new PropertyValueFactory<>("tenBaiTap"));
		colTinhTrang.setCellValueFactory(new PropertyValueFactory<>("tenTinhTrang"));
//		colTinhTrang.setCellFactory(new Callback<TableColumn<ThietBi, String>, TableCell<ThietBi, String>>() {
//			@Override
//			public TableCell<ThietBi, String> call(TableColumn<ThietBi, String> param) {
//				return new TableCell<ThietBi, String>() {
//					@Override
//					protected void updateItem(String item, boolean empty) {
//						if (!empty) {
//							this.setTextFill(Color.RED);
//							 if(item.equals("Đang bảo trì")) 
//		                            setTextFill(Color.BLUEVIOLET);
//		                        setText(item);
//						}
//					}
//				};
//			}
//		});

		// ----
		colNhanVien.setCellValueFactory(new PropertyValueFactory<>("tenNhanVien"));
		colNgayNhap.setCellFactory(TextFieldTableCell.<ThietBi, Date>forTableColumn(new StringConverter<Date>() {
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
		colNgayNhap.setCellValueFactory(new PropertyValueFactory<>("ngayNhap"));
		colNamSX.setCellFactory(TextFieldTableCell.<ThietBi, Date>forTableColumn(new StringConverter<Date>() {
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
		colNamSX.setCellValueFactory(new PropertyValueFactory<>("namSX"));
		colHanDung.setCellFactory(TextFieldTableCell.<ThietBi, Date>forTableColumn(new StringConverter<Date>() {
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
		colHanDung.setCellValueFactory(new PropertyValueFactory<>("hanSuDung"));

		listThietBi = ThietBiDAO.getListThietBi();
		tableThietBi.setItems(listThietBi);

		// - Load tìm kiếm
		searchRealTime();
		// - Set ngày Việt Nam cho DatePicker
		Locale.setDefault(new Locale("vi", "VN"));

		// - Khởi tạo lấy tên Nhân viên
		txtNhanVien.setText(NhanVienDAO.getTenNV(LoginController.getInstance().txtUser.getText()));
		txtUpdNhaVien.setText(NhanVienDAO.getTenNV(LoginController.getInstance().txtUser.getText()));

		// - Format ngày tháng năm trên DatePicker
		StringConverter<LocalDate> converter = new StringConverter<LocalDate>() {
			DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

			@Override
			public String toString(LocalDate date) {
				if (date != null) {
					return dateFormatter.format(date);
				} else {
					return "";
				}
			}

			@Override
			public LocalDate fromString(String string) {
				if (string != null && !string.isEmpty()) {
					return LocalDate.parse(string, dateFormatter);
				} else {
					return null;
				}
			}
		};
		pickerNgayNhap.setConverter(converter);
		pickerNamSX.setConverter(converter);
		pickerHanSD.setConverter(converter);
		pickerUpdNgayNhap.setConverter(converter);
		pickerUpdNamSX.setConverter(converter);
		pickerUpdHanSD.setConverter(converter);

		// ------------LOADING..Layout Tabpane
		try {
			Parent parent = FXMLLoader.load(getClass().getResource("/viewFXML/equipment/LoaiThietBi.fxml"));
			anchorThietBi.getChildren().addAll(parent);
			Parent parent2 = FXMLLoader.load(getClass().getResource("/viewFXML/equipment/ThuongHieu.fxml"));
			anchorThuongHieu.getChildren().addAll(parent2);
			Parent parent3 = FXMLLoader.load(getClass().getResource("/viewFXML/equipment/BaiTap.fxml"));
			anchorBaiTap.getChildren().addAll(parent3);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Đã xảy vấn đề", JOptionPane.ERROR_MESSAGE);
		}
	}

}
