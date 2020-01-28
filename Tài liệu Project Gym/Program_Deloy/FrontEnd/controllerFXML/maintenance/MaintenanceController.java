package controllerFXML.maintenance;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
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
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXSnackbar.SnackbarEvent;

import controllerFXML.equipment.EquipmentController;
import controllerFXML.home.HomePageController;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
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
import model.baotri.BaoTri;
import model.baotri.BaoTriDAO;
import model.thietbi.ThietBiDAO;

public class MaintenanceController implements Initializable {
	private static MaintenanceController instance;

	public static MaintenanceController getInstance() {
		return instance;
	}

	public MaintenanceController() {
		instance = this;
	}

	// ---------------------------------
	@FXML
	protected JFXTabPane tabPane;
	@FXML
	public TableView<BaoTri> tableBaoTri;
	@FXML
	private TableColumn<BaoTri, String> colMaBT, colTenBT, colTenTBi, colLoaiBT, colMoTa;
	@FXML
	private TableColumn<BaoTri, Date> ColNgayBT, colNgayHTat;
	@FXML
	private JFXTextField txtTimKiem;
	@FXML
	private JFXButton btnChinhSua;

	// ---Infor
	@FXML
	private JFXTextField txtInforMaBT, txtInforTenBT, txtInforTenTBi, txtInforLoaiBT, txtInforNgayBT, txtInforNgayHTat;
	@FXML
	private JFXTextArea txtInforMoTa;

	// ---Update
	@FXML
	protected JFXTextField txtUpdMaBT, txtUpdTenBT, txtUpdTenTBi, txtUpdLoaiBT;
	@FXML
	private JFXDatePicker pickerUpdNgayBT;
	@FXML
	private JFXDatePicker pickerUpdNgayHT;
	@FXML
	private JFXTextArea txtUpdMoTa;
	@FXML
	protected AnchorPane panThongTin, paneChinhSua,anchorLoaiBaoTri;

	public AnchorPane anchorLoaiBT;
	public JFXDialog dialogLoaiBaoTri;

	public ObservableList<BaoTri> listBaoTri;

	// ===== Lấy data từ Table =====
	@FXML
	void clickGetItem(MouseEvent event) {
		try {
			if (event.getClickCount() == 1) {
				if (tableBaoTri.getSelectionModel().getSelectedItem() == null) {
					return;
				} else {
					if (panThongTin.isVisible()) {
						final SimpleDateFormat formatDate = new SimpleDateFormat("dd-MM-yyyy");
						txtInforMaBT.setText(tableBaoTri.getSelectionModel().getSelectedItem().getMaBaoTri());
						txtInforTenBT.setText(tableBaoTri.getSelectionModel().getSelectedItem().getTenBaoTri());
						txtInforTenTBi.setText(tableBaoTri.getSelectionModel().getSelectedItem().getTenThietBi());
						txtInforLoaiBT.setText(tableBaoTri.getSelectionModel().getSelectedItem().getLoaiBaoTri());
						txtInforNgayBT.setText(
								formatDate.format(tableBaoTri.getSelectionModel().getSelectedItem().getNgayBaoTri()));
						txtInforNgayHTat.setText(
								formatDate.format(tableBaoTri.getSelectionModel().getSelectedItem().getNgayHoanTat()));
						txtInforMoTa.setText(tableBaoTri.getSelectionModel().getSelectedItem().getMoTa());
					} else if (paneChinhSua.isVisible()) {
						txtUpdMaBT.setText(tableBaoTri.getSelectionModel().getSelectedItem().getMaBaoTri());
						txtUpdTenBT.setText(tableBaoTri.getSelectionModel().getSelectedItem().getTenBaoTri());
						txtUpdTenTBi.setText(tableBaoTri.getSelectionModel().getSelectedItem().getTenThietBi());
						txtUpdLoaiBT.setText(BaoTriDAO
								.getMaLoaiBaoTri(tableBaoTri.getSelectionModel().getSelectedItem().getLoaiBaoTri())
								+ "-" + tableBaoTri.getSelectionModel().getSelectedItem().getLoaiBaoTri());
						pickerUpdNgayBT.setValue(LOCAL_DATE(
								tableBaoTri.getSelectionModel().getSelectedItem().getNgayBaoTri().toString()));
						pickerUpdNgayHT.setValue(LOCAL_DATE(
								tableBaoTri.getSelectionModel().getSelectedItem().getNgayHoanTat().toString()));
						txtUpdMoTa.setText(tableBaoTri.getSelectionModel().getSelectedItem().getMoTa());
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

	// ===== Trở lại xem thông tin thiết bị =====
	@FXML
	void backPaneThongTinAction(ActionEvent event) {
		if (paneChinhSua.isVisible()) {
			paneChinhSua.setVisible(false);
			panThongTin.setVisible(true);
			btnChinhSua.setDisable(false);
		} else {
			return;
		}
	}

	// ===== Mở pane chỉnh sửa =====
	@FXML
	void chinhsuaAction(ActionEvent event) {
		if (panThongTin.isVisible()) {
			panThongTin.setVisible(false);
			paneChinhSua.setVisible(true);
			btnChinhSua.setDisable(true);
		} else {
			return;
		}
	}

	// ===== Mở loại bảo trì =====
	@FXML
	void openLoaiBTAction(ActionEvent event) throws IOException {
		anchorLoaiBT = FXMLLoader.load(getClass().getResource("/viewFXML/maintenance/dialogLoaiBaoTri.fxml"));
		dialogLoaiBaoTri = new JFXDialog(HomePageController.getInstance().stackPaneLoadLayout, anchorLoaiBT,
				JFXDialog.DialogTransition.CENTER);
		dialogLoaiBaoTri.show();
	}

	// ===== Làm sạch txt =====
	@FXML
	void clearTextUpdAction(ActionEvent event) {
		txtUpdMaBT.clear();
		txtUpdTenBT.clear();
		txtUpdTenTBi.clear();
		txtUpdLoaiBT.clear();
		txtUpdMoTa.clear();
		pickerUpdNgayBT.setValue(null);
		pickerUpdNgayHT.setValue(null);
	}

	// ooooooooooooooooo CẬP NHẬT ====================
	@FXML
	void capNhatAction(ActionEvent event) {
		if (txtUpdMaBT.getText().trim().isEmpty()) {
			JFXDialogLayout dialogLayout = new JFXDialogLayout();
			dialogLayout.setStyle(
					"-fx-background-color: Orange;-fx-border-color: Black;-fx-border-width: 5 ;-fx-border-color:Black ;-fx-text-fill: White;-fx-effect: dropshadow(three-pass-box, DarkRed, 10, 0, 0, 0);");
			Image image = new Image(getClass().getResourceAsStream("/Images/JFXDialog/warning.png"));
			Label label = new Label("Chọn thiết bị", new ImageView(image));
			label.setStyle("-fx-text-fill: DarkRed;-fx-font: 20 System; -fx-font-weight: Bold;");
			dialogLayout.setHeading(label);
			Text text = new Text("Bạn có thể Click vào Danh sách trên để chọn thiết bị đang bảo trì cần sửa !");
			text.setStyle("-fx-font: 15 System");
			dialogLayout.setBody(text);
			JFXDialog dialog = new JFXDialog(HomePageController.getInstance().mainStackPane, dialogLayout,
					JFXDialog.DialogTransition.CENTER);
			dialog.show();
		} else if (txtUpdTenBT.getText().trim().isEmpty() || txtUpdTenTBi.getText().trim().isEmpty()
				|| txtUpdLoaiBT.getText().trim().isEmpty() || txtUpdMoTa.getText().trim().isEmpty()
				|| pickerUpdNgayBT.getValue() == null || pickerUpdNgayHT.getValue() == null) {
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
			java.sql.Date sqlDate = java.sql.Date.valueOf(pickerUpdNgayBT.getValue());
			java.sql.Date sqlDate2 = java.sql.Date.valueOf(pickerUpdNgayHT.getValue());

			BaoTriDAO.capNhatBaoTri(txtUpdMaBT.getText().trim(), txtUpdTenBT.getText().trim(),
					txtUpdLoaiBT.getText().split("-")[0], sqlDate, sqlDate2, txtUpdMoTa.getText().trim());
			listBaoTri = BaoTriDAO.getListBaoTri();
			tableBaoTri.setItems(listBaoTri);
			searchRealTimeBT();
		}
	}

	// oooooooooooooooooo HOÀN TẤT BT oooooooooooooooooooooo
	@FXML
	void hoanTatBTAction(ActionEvent event) {
		if (tableBaoTri.getSelectionModel().getSelectedItem() == null) {
			JFXDialogLayout dialogLayout = new JFXDialogLayout();
			dialogLayout.setStyle(
					"-fx-background-color: Orange;-fx-border-color: Black;-fx-border-width: 5 ;-fx-border-color:Black ;-fx-text-fill: White;-fx-effect: dropshadow(three-pass-box, DarkRed, 10, 0, 0, 0);");
			Image image = new Image(getClass().getResourceAsStream("/Images/JFXDialog/warning.png"));
			Label label = new Label("Chọn thiết bị", new ImageView(image));
			label.setStyle("-fx-text-fill: DarkRed;-fx-font: 20 System; -fx-font-weight: Bold;");
			dialogLayout.setHeading(label);
			Text text = new Text("Vui lòng chọn 1 thiết bị cần kết thúc bảo trì");
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
			Image image = new Image(getClass().getResourceAsStream("/Images/JFXDialog/information.png"));
			Label label = new Label("Xác nhận", new ImageView(image));
			label.setStyle("-fx-text-fill: DarkRed;-fx-font: 20 System; -fx-font-weight: Bold;");
			dialogLayout.setHeading(label);
			Text text = new Text("Bạn muốn hoàn tất việc bảo trì thiết bị : "
					+ tableBaoTri.getSelectionModel().getSelectedItem().getTenThietBi());
			text.setStyle("-fx-font: 15 System");
			dialogLayout.setBody(text);
			JFXDialog dialog = new JFXDialog(HomePageController.getInstance().mainStackPane, dialogLayout,
					JFXDialog.DialogTransition.CENTER);
			JFXButton btnExit = new JFXButton("Hoàn tất");
			btnExit.setCursor(Cursor.HAND);
			btnExit.setStyle(
					"-fx-background-color: Black;-fx-background-radius: 5.0;-fx-text-fill: Green;-fx-font: 13 System; -fx-font-weight: Bold;");
			btnExit.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					ThietBiDAO.capNhatTinhTrangTb("TT1",
							ThietBiDAO.getMaThietBi(tableBaoTri.getSelectionModel().getSelectedItem().getTenThietBi()));
					BaoTriDAO.xoaBaoTri(tableBaoTri.getSelectionModel().getSelectedItem().getMaBaoTri());
					listBaoTri = BaoTriDAO.getListBaoTri();
					tableBaoTri.setItems(listBaoTri);
					EquipmentController.getInstance().listThietBi = ThietBiDAO.getListThietBi();
					EquipmentController.getInstance().tableThietBi
							.setItems(EquipmentController.getInstance().listThietBi);
					EquipmentController.getInstance().searchRealTime();
					searchRealTimeBT();
					dialog.close();
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
	
	// ooooooooooooooooooooooooo XUẤT DS oooooooooooooooooooooooo
		@FXML
		void xuatDanhSach(ActionEvent event) throws IOException {
			Workbook workbook = new HSSFWorkbook();
			Sheet spreadsheet = workbook.createSheet("sample");
			Row row = spreadsheet.createRow(0);
			for (int j = 0; j < tableBaoTri.getColumns().size(); j++) {
				row.createCell(j).setCellValue(tableBaoTri.getColumns().get(j).getText());
			}
			for (int i = 0; i < tableBaoTri.getItems().size(); i++) {
				row = spreadsheet.createRow(i + 1);
				for (int j = 0; j < tableBaoTri.getColumns().size(); j++) {
					if (tableBaoTri.getColumns().get(j).getCellData(i) != null) {
						row.createCell(j).setCellValue(tableBaoTri.getColumns().get(j).getCellData(i).toString());
					} else {
						row.createCell(j).setCellValue("");
					}
				}
			}
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Xuất Danh sách thiết bị bảo trì");
			fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("XLSX files (*.xls)", "*.xls"));
			
			File saveFile = fileChooser.showSaveDialog(tableBaoTri.getScene().getWindow());
			if (saveFile == null) {
				return;
			} else {
				FileOutputStream fileOut = new FileOutputStream(saveFile);
				workbook.write(fileOut);
				fileOut.close();
				
				EquipmentController.getInstance().snack = new JFXSnackbar(
						HomePageController.getInstance().stackPaneLoadLayout);
				EquipmentController.getInstance().lablSnack.setText("Xuất danh sách thành công !");
				EquipmentController.getInstance().snack
				.enqueue(new SnackbarEvent(EquipmentController.getInstance().lablSnack));
			}
			
		}

	// oooooooooooooooo TÌM KIẾM Thiết bị oooooooooooooo
	public void searchRealTimeBT() {
		FilteredList<BaoTri> filteredList = new FilteredList<BaoTri>(listBaoTri, b -> true);
		txtTimKiem.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredList.setPredicate(baotri -> {
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}
				String lowerCasefilter = newValue.toLowerCase();
				if (baotri.getMaBaoTri().toLowerCase().indexOf(lowerCasefilter) != -1) {
					return true;
				} else if (baotri.getTenBaoTri().toLowerCase().indexOf(lowerCasefilter) != -1) {
					return true;
				} else if (baotri.getTenThietBi().toLowerCase().indexOf(lowerCasefilter) != -1) {
					return true;
				} else if (baotri.getLoaiBaoTri().toLowerCase().indexOf(lowerCasefilter) != -1) {
					return true;
				} else {
					return false;
				}
			});
		});
		SortedList<BaoTri> sortedList = new SortedList<BaoTri>(filteredList);
		sortedList.comparatorProperty().bind(tableBaoTri.comparatorProperty());
		tableBaoTri.setItems(sortedList);
	}

	// ===== Khởi tạo Controller =====
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// - Khởi tạo Data cho Table
		tableBaoTri.setPlaceholder(new Text("Không có dữ liệu !!!"));
		colMaBT.setCellValueFactory(new PropertyValueFactory<>("maBaoTri"));
		colTenBT.setCellValueFactory(new PropertyValueFactory<>("tenBaoTri"));
		colTenTBi.setCellValueFactory(new PropertyValueFactory<>("tenThietBi"));
		colLoaiBT.setCellValueFactory(new PropertyValueFactory<>("loaiBaoTri"));
		ColNgayBT.setCellFactory(TextFieldTableCell.<BaoTri, Date>forTableColumn(new StringConverter<Date>() {
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
		ColNgayBT.setCellValueFactory(new PropertyValueFactory<>("ngayBaoTri"));
		colNgayHTat.setCellFactory(TextFieldTableCell.<BaoTri, Date>forTableColumn(new StringConverter<Date>() {
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
		colNgayHTat.setCellValueFactory(new PropertyValueFactory<>("ngayHoanTat"));
		colMoTa.setCellValueFactory(new PropertyValueFactory<>("moTa"));

		listBaoTri = BaoTriDAO.getListBaoTri();
		tableBaoTri.setItems(listBaoTri);

		// - Set ngày Việt Nam cho DatePicker
		Locale.setDefault(new Locale("vi", "VN"));

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
		pickerUpdNgayBT.setConverter(converter);
		pickerUpdNgayHT.setConverter(converter);
		
		//- Tìm kiếm
		searchRealTimeBT();
		
		// ------------LOADING..Layout Tabpane
		try {
			Parent parent = FXMLLoader.load(getClass().getResource("/viewFXML/maintenance/LoaiBaoTri.fxml"));
			anchorLoaiBaoTri.getChildren().addAll(parent);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Đã xảy vấn đề", JOptionPane.ERROR_MESSAGE);
		}
	}

}
