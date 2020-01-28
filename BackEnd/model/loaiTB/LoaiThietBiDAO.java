package model.loaiTB;

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

public class LoaiThietBiDAO {
	private static Connection connection;
	private static PreparedStatement preparedStatement;
	private static ResultSet resultSet;

	// ===== Lấy dữ liệu lên trả về ArrayList của Loại thiết bị
	public static ArrayList<LoaiThietBi> getListLoaiTB() {
		ArrayList<LoaiThietBi> listLoaiTB = new ArrayList<LoaiThietBi>();
		LoaiThietBi loaiTB;
		try {
			String sqlQuery = "SELECT * FROM LoaiThietBi";
			connection = DbUtil.getConnection();
			preparedStatement = connection.prepareStatement(sqlQuery);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				loaiTB = new LoaiThietBi(resultSet.getString("MaLoai"), resultSet.getString("TenLoai"),
						resultSet.getString("MoTa"));
				listLoaiTB.add(loaiTB);
			}
			return listLoaiTB;
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

	// ===== Lấy dữ liệu lên trả về ObservableList của Loại thiết bị
	public static ObservableList<LoaiThietBi> getListTBObser() {
		ObservableList<LoaiThietBi> listLoaiThietBi = FXCollections.observableArrayList();
		LoaiThietBi loaiThietBi;
		try {
			String sqlQuery = "SELECT * FROM LoaiThietBi";
			connection = DbUtil.getConnection();
			preparedStatement = connection.prepareStatement(sqlQuery);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				loaiThietBi = new LoaiThietBi(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3));
				listLoaiThietBi.add(loaiThietBi);
			}
			return listLoaiThietBi;
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

	// ===== Insert Loại thiết bị
	public static void themLoaiThietBi(String maLoaiTB, String tenLoaiTB, String moTa) {
		try {
			String sqlQuery = "INSERT INTO LoaiThietBi VALUES(?,?,?)";
			connection = DbUtil.getConnection();
			preparedStatement = connection.prepareStatement(sqlQuery);
			preparedStatement.setString(1, maLoaiTB);
			preparedStatement.setString(2, tenLoaiTB);
			preparedStatement.setString(3, moTa);

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
	
	// ===== Update thiết bị
	public static void capNhatLoaiTB(String maLoai,String tenLoai,String moTa ) {
		try {
			String sqlQuery = "UPDATE LoaiThietBi SET TenLoai=?,MoTa=? WHERE MaLoai=?";
			connection = DbUtil.getConnection();
			preparedStatement = connection.prepareStatement(sqlQuery);
			preparedStatement.setString(1, tenLoai);
			preparedStatement.setString(2, moTa);
			preparedStatement.setString(3, maLoai);

			int x = preparedStatement.executeUpdate();
			if (x > 0) {
				EquipmentController.getInstance().snack=new JFXSnackbar(HomePageController.getInstance().stackPaneLoadLayout);
				EquipmentController.getInstance().lablSnack.setText("Cập nhật thành công !");
				EquipmentController.getInstance().snack.enqueue(new SnackbarEvent(EquipmentController.getInstance().lablSnack));
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
	
	// ===== Delete Loại thiết bị
		public static void xoaLoaiTB(String maloaiTB) {
			try {
				String sqlQuery = "DELETE FROM LoaiThietBi WHERE MaLoai=?";
				connection = DbUtil.getConnection();
				preparedStatement = connection.prepareStatement(sqlQuery);
				preparedStatement.setString(1, maloaiTB);

				int x = preparedStatement.executeUpdate();
				if (x > 0) {
					EquipmentController.getInstance().snack=new JFXSnackbar(HomePageController.getInstance().stackPaneLoadLayout);
					EquipmentController.getInstance().lablSnack.setText("Xóa thành công !");
					EquipmentController.getInstance().snack.enqueue(new SnackbarEvent(EquipmentController.getInstance().lablSnack));
				}
			} catch (SQLException e) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Thông báo");
				alert.setHeaderText("Không thể xóa");
				alert.setContentText("Vui lòng kiểm tra kỹ trước khi xóa.Có thể Loại thiết bị này đang thuộc 1 Thiết bị !");
				alert.getDialogPane().setStyle(
						"-fx-border-color: black;-fx-background-color: Orange; -fx-font-size: 14px;-fx-font-weight: bold;");
				alert.initOwner(EquipmentController.getInstance().lablSnack.getScene().getWindow());
				alert.showAndWait();
			}finally {
				DbUtil.disConnAndPrep(connection, preparedStatement);
			}
		}
	
	// ===== Kiểm tra xem Mã Loại hiết bị tồn tại hay chưa
		public static Boolean maLTBIsExist(String maLoaiTB) {
			try {
				String sqlQuery = "SELECT MaLoai FROM LoaiThietBi WHERE MaLoai=?";
				connection = DbUtil.getConnection();
				preparedStatement = connection.prepareStatement(sqlQuery);
				preparedStatement.setString(1, maLoaiTB);

				resultSet = preparedStatement.executeQuery();
				while (resultSet.next()) {
					return true;
				}
			} catch (SQLException e	) {
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
