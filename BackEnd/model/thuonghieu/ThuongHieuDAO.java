package model.thuonghieu;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXSnackbar.SnackbarEvent;

import connectionDb.DbUtil;
import controllerFXML.equipment.EquipmentController;
import controllerFXML.home.HomePageController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class ThuongHieuDAO {
	private static Connection connection;
	private static PreparedStatement preparedStatement;
	private static ResultSet resultSet;

	// ===== Lấy dữ liệu lên trả về ArrayList của Thương hiệu
	public static ArrayList<ThuongHieu> getListThuongHieu() {
		ArrayList<ThuongHieu> listThuongHieu = new ArrayList<ThuongHieu>();
		ThuongHieu thuongHieu;
		try {
			String sqlQuery = "SELECT * FROM ThuongHieu";
			connection = DbUtil.getConnection();
			preparedStatement = connection.prepareStatement(sqlQuery);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				thuongHieu = new ThuongHieu(resultSet.getString("MaThuongHieu"), resultSet.getString("TenThuongHieu"),
						resultSet.getString("QuocGia"), resultSet.getString("MoTa"));
				listThuongHieu.add(thuongHieu);
			}
			return listThuongHieu;
		} catch (SQLException e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Thông báo");
			alert.setContentText(e.getMessage());
			alert.getDialogPane().setStyle(
					"-fx-border-color: black;-fx-background-color: Orange; -fx-font-size: 14px;-fx-font-weight: bold;");
			alert.initOwner(EquipmentController.getInstance().lablSnack.getScene().getWindow());
			alert.showAndWait();
		} finally {
			DbUtil.disConnAndPrepAndResul(connection, preparedStatement, resultSet);
		}
		return null;
	}

	// ===== Lấy dữ liệu lên trả về ObservableList của Thương hiệu
	public static ObservableList<ThuongHieu> getListTHObser() {
		ObservableList<ThuongHieu> listThuongHieu = FXCollections.observableArrayList();
		ThuongHieu thuongHieu;
		try {
			String sqlQuery = "SELECT * FROM ThuongHieu";
			connection = DbUtil.getConnection();
			preparedStatement = connection.prepareStatement(sqlQuery);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				thuongHieu = new ThuongHieu(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3),
						resultSet.getString(4));
				listThuongHieu.add(thuongHieu);
			}
			return listThuongHieu;
		} catch (SQLException e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Thông báo");
			alert.setContentText(e.getMessage());
			alert.getDialogPane().setStyle(
					"-fx-border-color: black;-fx-background-color: Orange; -fx-font-size: 14px;-fx-font-weight: bold;");
			alert.initOwner(EquipmentController.getInstance().lablSnack.getScene().getWindow());
			alert.showAndWait();
		} finally {
			DbUtil.disConnAndPrepAndResul(connection, preparedStatement, resultSet);
		}
		return null;
	}

	// ===== Insert Thương hiệu
	public static void themLoaiThuongHieu(String ma, String ten, String quocGia, String moTa) {
		try {
			String sqlQuery = "INSERT INTO ThuongHieu VALUES(?,?,?,?)";
			connection = DbUtil.getConnection();
			preparedStatement = connection.prepareStatement(sqlQuery);
			preparedStatement.setString(1, ma);
			preparedStatement.setString(2, ten);
			preparedStatement.setString(3, quocGia);
			preparedStatement.setString(4, moTa);

			int x = preparedStatement.executeUpdate();
			if (x > 0) {
				EquipmentController.getInstance().snack = new JFXSnackbar(
						HomePageController.getInstance().stackPaneLoadLayout);
				EquipmentController.getInstance().lablSnack.setText("Thêm thành công !");
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

	// ===== Update Thương hiệu
	public static void capNhatThuongHieu(String maTh, String tenTh, String quocGia, String moTa) {
		try {
			String sqlQuery = "UPDATE ThuongHieu SET TenThuongHieu=?,QuocGia=?,MoTa=? WHERE MaThuongHieu=?";
			connection = DbUtil.getConnection();
			preparedStatement = connection.prepareStatement(sqlQuery);
			preparedStatement.setString(1, tenTh);
			preparedStatement.setString(2, quocGia);
			preparedStatement.setString(3, moTa);
			preparedStatement.setString(4, maTh);

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

	// ===== Delete Thương hiệu
	public static void xoaThuongHieu(String maTH) {
		try {
			String sqlQuery = "DELETE FROM ThuongHieu WHERE MaThuongHieu=?";
			connection = DbUtil.getConnection();
			preparedStatement = connection.prepareStatement(sqlQuery);
			preparedStatement.setString(1, maTH);

			int x = preparedStatement.executeUpdate();
			if (x > 0) {
				EquipmentController.getInstance().snack = new JFXSnackbar(
						HomePageController.getInstance().stackPaneLoadLayout);
				EquipmentController.getInstance().lablSnack.setText("Xóa thành công !");
				EquipmentController.getInstance().snack
						.enqueue(new SnackbarEvent(EquipmentController.getInstance().lablSnack));
			}
		} catch (SQLException e) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Thông báo");
			alert.setHeaderText("Không thể xóa");
			alert.setContentText("Vui lòng kiểm tra kỹ trước khi xóa.Có thể Thương hiệu này đang thuộc 1 Thiết bị !");
			alert.getDialogPane().setStyle(
					"-fx-border-color: black;-fx-background-color: Orange; -fx-font-size: 14px;-fx-font-weight: bold;");
			alert.initOwner(EquipmentController.getInstance().lablSnack.getScene().getWindow());
			alert.showAndWait();
		}finally {
			DbUtil.disConnAndPrep(connection, preparedStatement);
		}
	}

	// ===== Kiểm tra xem Mã Loại hiết bị tồn tại hay chưa
	public static Boolean maLTHIsExist(String maLoaiTB) {
		try {
			String sqlQuery = "SELECT MaThuongHieu FROM ThuongHieu WHERE MaThuongHieu=?";
			connection = DbUtil.getConnection();
			preparedStatement = connection.prepareStatement(sqlQuery);
			preparedStatement.setString(1, maLoaiTB);

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
			DbUtil.disConnAndPrepAndResul(connection, preparedStatement, resultSet);
		}
		return false;
	}
}
