package model.nhanvien;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
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

public class NhanVienDAO {
	private static Connection connection;
	private static CallableStatement callableStatement;
	private static PreparedStatement preparedStatement;
	private static ResultSet resultSet;

	// ===== Lấy Tên Nhân viên
	public static String getTenNV(String tenTK) {
		String tenNhanVien = "";
		try {
			String sqlQuery = "{Call getMaVaTenNV(?)}";
			connection = DbUtil.getConnection();
			callableStatement = connection.prepareCall(sqlQuery);
			callableStatement.setString(1, tenTK);
			resultSet = callableStatement.executeQuery();
			while (resultSet.next()) {
				return resultSet.getString("TenNhanVien");
			}
			return tenNhanVien;
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
		return "";
	}

	// ===== Lấy Mã Nhân viên
	public static String getMaNV(String tenTK) {
		String tenNhanVien = "";
		try {
			String sqlQuery = "{Call getMaVaTenNV(?)}";
			connection = DbUtil.getConnection();
			callableStatement = connection.prepareCall(sqlQuery);
			callableStatement.setString(1, tenTK);
			resultSet = callableStatement.executeQuery();
			while (resultSet.next()) {
				return resultSet.getString("MaNhanVien");
			}
			return tenNhanVien;
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
		return "";
	}

	// ===== Kiểm tra xem Mã Nhân viên tồn tại hay chưa
	public static Boolean maNVIsExist(String maNV) {
		try {
			String sqlQuery = "SELECT MaNhanVien FROM NhanVien WHERE MaNhanVien=?";
			connection = DbUtil.getConnection();
			preparedStatement = connection.prepareStatement(sqlQuery);
			preparedStatement.setString(1, maNV);

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

	// ===== Lấy dữ liệu lên trả về ObservableList của nhân viên
	public static ObservableList<NhanVien> getListNhanVien() {
		ObservableList<NhanVien> listNhanVien = FXCollections.observableArrayList();
		NhanVien nhanVien;
		try {
			String sqlQuery = "SELECT * FROM NhanVien WHERE MaQuyen=?";
			connection = DbUtil.getConnection();
			preparedStatement = connection.prepareStatement(sqlQuery);
			preparedStatement.setString(1, "Q2");
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				nhanVien = new NhanVien(resultSet.getString(1), resultSet.getString(2), resultSet.getDate(3),
						resultSet.getString(4), resultSet.getString(5), resultSet.getString(6), resultSet.getString(7));
				listNhanVien.add(nhanVien);
			}
			return listNhanVien;
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

	// ===== Insert nhân viên
	public static void themNhanVien(String maNV, String tenNV, Date ngaySinh, String gioiTinh, String diaChi,
			String SDT) {
		try {
			String sqlQuery = "INSERT INTO NhanVien VALUES(?,?,?,?,?,?,?)";
			connection = DbUtil.getConnection();
			preparedStatement = connection.prepareStatement(sqlQuery);

			preparedStatement.setString(1, maNV);
			preparedStatement.setString(2, tenNV);
			preparedStatement.setDate(3, ngaySinh);
			preparedStatement.setString(4, gioiTinh);
			preparedStatement.setString(5, diaChi);
			preparedStatement.setString(6, SDT);
			preparedStatement.setString(7, "Q2");

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

	// ===== Delete nhân viên
	public static void xoaNhanVien(String maNV) {
		try {
			String sqlQuery = "DELETE FROM NhanVien WHERE MaNhanVien=?";
			connection = DbUtil.getConnection();
			preparedStatement = connection.prepareStatement(sqlQuery);
			preparedStatement.setString(1, maNV);

			int x = preparedStatement.executeUpdate();
			if (x > 0) {
				EquipmentController.getInstance().snack = new JFXSnackbar(
						HomePageController.getInstance().stackPaneLoadLayout);
				EquipmentController.getInstance().lablSnack.setText("Xóa thành công !");
				EquipmentController.getInstance().snack
						.enqueue(new SnackbarEvent(EquipmentController.getInstance().lablSnack));
			}
		} catch (SQLException e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Thông báo");
			alert.setHeaderText("Không thể xóa");
			alert.setContentText(
					"Nhân viên này đang giữ 1 Tài khoản, vui lòng xóa Tài khoản của Nhân viên này trước khi Xóa họ khỏi Hệ thống của bạn !");
			alert.getDialogPane().setStyle(
					"-fx-border-color: black;-fx-background-color: Orange; -fx-font-size: 14px;-fx-font-weight: bold;");
			alert.initOwner(EquipmentController.getInstance().lablSnack.getScene().getWindow());
			alert.showAndWait();
		}
	}

	// ===== Update lại Nhân viên
	public static void capNhatNhanVien(String tenNhanVien, Date ngaySinh, String gioiTinh, String diaChi, String SDT,
			String maNV) {
		try {
			String sqlQuery = "UPDATE NhanVien SET TenNhanVien=?,NgaySinh=?,GioiTinh=?,DiaChi=?,SDT=? WHERE MaNhanVien=?";
			connection = DbUtil.getConnection();
			preparedStatement = connection.prepareStatement(sqlQuery);

			preparedStatement.setString(1, tenNhanVien);
			preparedStatement.setDate(2, ngaySinh);
			preparedStatement.setString(3, gioiTinh);
			preparedStatement.setString(4, diaChi);
			preparedStatement.setString(5, SDT);
			preparedStatement.setString(6, maNV);
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
}
