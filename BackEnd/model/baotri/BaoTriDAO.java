package model.baotri;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXSnackbar.SnackbarEvent;

import connectionDb.DbUtil;
import controllerFXML.equipment.EquipmentController;
import controllerFXML.home.HomePageController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class BaoTriDAO {
	private static Connection connection;
	private static CallableStatement callableStatement;
	private static PreparedStatement preparedStatement;
	private static ResultSet resultSet;

	// ===== Lấy dữ liệu lên trả về ObservableList của Bảo trì
	public static ObservableList<BaoTri> getListBaoTri() {
		ObservableList<BaoTri> listBaoTri = FXCollections.observableArrayList();
		BaoTri baoTri;
		try {
			String sqlQuery = "{Call GetAllMaintenance}";
			connection = DbUtil.getConnection();
			callableStatement = connection.prepareCall(sqlQuery);
			resultSet = callableStatement.executeQuery();

			while (resultSet.next()) {
				baoTri = new BaoTri(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3),
						resultSet.getString(4), resultSet.getDate(5), resultSet.getDate(6), resultSet.getString(7));
				listBaoTri.add(baoTri);
			}
			return listBaoTri;
		} catch (SQLException e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Thông báo");
			alert.setContentText(e.getMessage());
			alert.getDialogPane().setStyle(
					"-fx-border-color: black;-fx-background-color: Orange; -fx-font-size: 14px;-fx-font-weight: bold;");
			alert.initOwner(EquipmentController.getInstance().lablSnack.getScene().getWindow());
			alert.showAndWait();
		} finally {
			DbUtil.disConnAndCallAndResult(connection, callableStatement, resultSet);
		}
		return null;
	}

	// ===== Kiểm tra xem Mã Bảo trì tồn tại hay chưa
	public static Boolean maBaoTriIsExist(String mabaoTri) {
		try {
			String sqlQuery = "SELECT MaBaoTri FROM ThongTinBaoTri WHERE MaBaoTri=?";
			connection = DbUtil.getConnection();
			preparedStatement = connection.prepareStatement(sqlQuery);
			preparedStatement.setString(1, mabaoTri);

			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				return true;
			}
		} catch (SQLException e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Thông báo");
			alert.setContentText(e.getMessage());
			alert.getDialogPane().setStyle(
					"-fx-border-color: black;-fx-background-color: Orange; -fx-font-size: 14px;-fx-font-weight: bold;");
			alert.initOwner(EquipmentController.getInstance().lablSnack.getScene().getWindow());
			alert.showAndWait();
		} finally {
			DbUtil.disConnAndPrep(connection, preparedStatement);
		}
		return false;
	}

	// ===== Lấy Mã Loại bảo trì cho click TableView
	public static String getMaLoaiBaoTri(String tenBaoTri) {
		String maTb = "";
		try {
			String sqlQuery = "SELECT MaLoaiBaoTri FROM LoaiBaoTri WHERE TenLoaiBaoTri=?";
			connection = DbUtil.getConnection();
			preparedStatement = connection.prepareStatement(sqlQuery);
			preparedStatement.setString(1, tenBaoTri);

			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				maTb = resultSet.getString(1);
				return maTb;
			}
		} catch (SQLException e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Thông báo");
			alert.setContentText(e.getMessage());
			alert.getDialogPane().setStyle(
					"-fx-border-color: black;-fx-background-color: Orange; -fx-font-size: 14px;-fx-font-weight: bold;");
			alert.initOwner(EquipmentController.getInstance().lablSnack.getScene().getWindow());
			alert.showAndWait();
		}
		return maTb;
	}

	// ===== Insert Bảo trì ======
	public static void themBaoTri(String maBaoTri, String tenBaoTri, String maThietBi, String loaiBaoTri,
			java.sql.Date ngayBaoTri, java.sql.Date ngayHoanTat, String moTa) {
		try {
			String sqlQuery = "INSERT INTO ThongTinBaoTri VALUES(?,?,?,?,?,?,?)";
			connection = DbUtil.getConnection();
			preparedStatement = connection.prepareStatement(sqlQuery);

			preparedStatement.setString(1, maBaoTri);
			preparedStatement.setString(2, tenBaoTri);
			preparedStatement.setString(3, maThietBi);
			preparedStatement.setString(4, loaiBaoTri);
			preparedStatement.setDate(5, ngayBaoTri);
			preparedStatement.setDate(6, ngayHoanTat);
			preparedStatement.setString(7, moTa);

			int x = preparedStatement.executeUpdate();
			if (x > 0) {
				EquipmentController.getInstance().snack = new JFXSnackbar(
						HomePageController.getInstance().stackPaneLoadLayout);
				EquipmentController.getInstance().lablSnack.setText("Đã thêm vào Bảo trì !");
				EquipmentController.getInstance().snack
						.enqueue(new SnackbarEvent(EquipmentController.getInstance().lablSnack));
			}
		} catch (SQLException e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Thông báo");
			alert.setContentText(e.getMessage());
			alert.getDialogPane().setStyle(
					"-fx-border-color: black;-fx-background-color: Orange; -fx-font-size: 14px;-fx-font-weight: bold;");
			alert.initOwner(EquipmentController.getInstance().lablSnack.getScene().getWindow());
			alert.showAndWait();
		} finally {
			DbUtil.disConnAndPrep(connection, preparedStatement);
		}
	}

	// ===== Update bảo trì
	public static void capNhatBaoTri(String maBaoTri, String tenBaoTri, String loaiBaoTri,
			java.sql.Date ngayBaoTri, java.sql.Date ngayHoanTat, String moTa) {
		try {
			String sqlQuery = "UPDATE ThongTinBaoTri SET TenBaoTri=?,MaLoaiBaoTri=?,NgayBaoTri=?,NgayHoanTat=?,MoTa=? WHERE MaBaoTri=?";
			connection = DbUtil.getConnection();
			preparedStatement = connection.prepareStatement(sqlQuery);

			preparedStatement.setString(1, tenBaoTri);
			preparedStatement.setString(2, loaiBaoTri);
			preparedStatement.setDate(3, ngayBaoTri);
			preparedStatement.setDate(4, ngayHoanTat);
			preparedStatement.setString(5, moTa);
			preparedStatement.setString(6, maBaoTri);

			int x = preparedStatement.executeUpdate();
			if (x > 0) {
				EquipmentController.getInstance().snack = new JFXSnackbar(
						HomePageController.getInstance().stackPaneLoadLayout);
				EquipmentController.getInstance().lablSnack.setText("Cập nhật thành công !");
				EquipmentController.getInstance().snack
						.enqueue(new SnackbarEvent(EquipmentController.getInstance().lablSnack));
			}
		} catch (SQLException e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Thông báo");
			alert.setContentText(e.getMessage());
			alert.getDialogPane().setStyle(
					"-fx-border-color: black;-fx-background-color: Orange; -fx-font-size: 14px;-fx-font-weight: bold;");
			alert.initOwner(EquipmentController.getInstance().lablSnack.getScene().getWindow());
			alert.showAndWait();
		} finally {
			DbUtil.disConnAndPrep(connection, preparedStatement);
		}
	}
	
	// ===== Delete bảo trì để Hoàn tất bảo trì
		public static void xoaBaoTri(String maMaBaoTri) {
			try {
				String sqlQuery = "DELETE FROM ThongTinBaoTri WHERE MaBaoTri=?";
				connection = DbUtil.getConnection();
				preparedStatement = connection.prepareStatement(sqlQuery);
				preparedStatement.setString(1, maMaBaoTri);

				int x = preparedStatement.executeUpdate();
				if (x > 0) {
					EquipmentController.getInstance().snack = new JFXSnackbar(
							HomePageController.getInstance().stackPaneLoadLayout);
					EquipmentController.getInstance().lablSnack.setText("Bảo trì hoàn tất!");
					EquipmentController.getInstance().snack
							.enqueue(new SnackbarEvent(EquipmentController.getInstance().lablSnack));
				}
			} catch (SQLException e) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Thông báo");
				alert.setContentText(e.getMessage());
				alert.getDialogPane().setStyle(
						"-fx-border-color: black;-fx-background-color: Orange; -fx-font-size: 14px;-fx-font-weight: bold;");
				alert.initOwner(EquipmentController.getInstance().lablSnack.getScene().getWindow());
				alert.showAndWait();
			}
		}
}
