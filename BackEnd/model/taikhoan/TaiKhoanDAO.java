package model.taikhoan;

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

public class TaiKhoanDAO {
	private static Connection connection;
	private static PreparedStatement preparedStatement;
	private static ResultSet resultSet;

	// Xác nhận UserName và PassWord để Đăng nhập
	public static boolean isLogin(String user, String gmail, String pass) {
		String sqlQuery = "SELECT TenTaiKhoan,MatKhau FROM TaiKhoan WHERE (TenTaiKhoan=? OR Gmail=?) AND MatKhau=?";
		connection = DbUtil.getConnection();
		try {
			preparedStatement = connection.prepareStatement(sqlQuery);
			preparedStatement.setString(1, user);
			preparedStatement.setString(2, gmail);
			preparedStatement.setString(3, pass);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				return true;
			}
		} catch (SQLException e) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Thông báo");
			alert.setHeaderText(e.getMessage());
			alert.showAndWait();
		} finally {
			DbUtil.disConnAndPrepAndResul(connection, preparedStatement, resultSet);
		}
		return false;
	}

	// ===== Kiểm tra Gmail có tồn tại hay không
	public static boolean gmaiIsExist(String gmail) {
		try {
			String sqlQuery = "SELECT Gmail FROM TaiKhoan WHERE Gmail=?";
			connection = DbUtil.getConnection();
			preparedStatement = connection.prepareStatement(sqlQuery);
			preparedStatement.setString(1, gmail);

			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				return true;
			}
		} catch (SQLException e) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Thông báo");
			alert.setHeaderText(e.getMessage());
			alert.showAndWait();
		} finally {
			DbUtil.disConnAndPrep(connection, preparedStatement);
		}
		return false;
	}

	// ===== Cập nhật lại mật khẩu với Gmai
	public static boolean updatePass(String pass, String gmail) {
		try {
			String sqlQuery = "UPDATE TaiKhoan SET MatKhau=? WHERE Gmail=?";
			connection = DbUtil.getConnection();
			preparedStatement = connection.prepareStatement(sqlQuery);
			preparedStatement.setString(1, pass);
			preparedStatement.setString(2, gmail);

			int x = preparedStatement.executeUpdate();
			if (x > 0) {
				return true;
			}
		} catch (SQLException e) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Thông báo");
			alert.setHeaderText(e.getMessage());
			alert.showAndWait();
		} finally {
			DbUtil.disConnAndPrep(connection, preparedStatement);
		}
		return false;
	}

	// ===== Lấy thông tin tài Khoản trả về 1 Datatype là TaiKhoan
	public static TaiKhoan getInfor(String maNhanVien) {
		TaiKhoan taiKhoan = null;

		try {
			String sqlQuery = "SELECT TaiKhoan.TenTaiKhoan,TaiKhoan.MatKhau,TaiKhoan.Gmail,TaiKhoan.MaNhanVien,NhanVien.MaQuyen FROM TaiKhoan,NhanVien WHERE TaiKhoan.MaNhanVien=NhanVien.MaNhanVien AND NhanVien.MaNhanVien=?";
			connection = DbUtil.getConnection();
			preparedStatement = connection.prepareStatement(sqlQuery);
			preparedStatement.setString(1, maNhanVien);

			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				taiKhoan = new TaiKhoan(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3),
						resultSet.getString(4), resultSet.getString(5));
				return taiKhoan;
			}
		} catch (SQLException e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Thông báo");
			alert.setContentText(e.getMessage());
			alert.getDialogPane().setStyle(
					"-fx-border-color: black;-fx-background-color: Orange; -fx-font-size: 14px;-fx-font-weight: bold;");
			alert.initOwner(EquipmentController.getInstance().lablSnack.getScene().getWindow());
			alert.showAndWait();
		}finally {
			DbUtil.disConnAndPrepAndResul(connection, preparedStatement, resultSet);
		}
		return taiKhoan;
	}

	// ===== Kiểm tra xem Tên tài khoản tồn tại hay chưa
	public static Boolean maTenTKIsExist(String tenTK) {
		try {
			String sqlQuery = "SELECT TenTaiKhoan FROM TaiKhoan WHERE TenTaiKhoan=?";
			connection = DbUtil.getConnection();
			preparedStatement = connection.prepareStatement(sqlQuery);
			preparedStatement.setString(1, tenTK);

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

	// ===== Kiểm tra xem Mã nhân viên này có Tài khoản chưa
	public static Boolean maNhanVienTKIsExist(String maNV) {
		try {
			String sqlQuery = "SELECT MaNhanVien FROM TaiKhoan WHERE MaNhanVien=?";
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

	// ===== Cập nhật lại thông tin tài khoản với Mã Nhân viên
	public static void updateInfor(String TenTk, String pass, String gmail, String MaNhanVien) {
		try {
			String sqlQuery = "UPDATE TaiKhoan SET TenTaiKhoan=?,MatKhau=?, Gmail=? WHERE MaNhanVien=?";
			connection = DbUtil.getConnection();
			preparedStatement = connection.prepareStatement(sqlQuery);
			preparedStatement.setString(1, TenTk);
			preparedStatement.setString(2, pass);
			preparedStatement.setString(3, gmail);
			preparedStatement.setString(4, MaNhanVien);

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

	// ===== Lấy dữ liệu lên trả về ObservableList của Tài khoản
	public static ObservableList<TaiKhoan> getListTaiKhoan() {
		ObservableList<TaiKhoan> listTaiKhoan = FXCollections.observableArrayList();
		TaiKhoan taiKhoan;
		try {
			String sqlQuery = "SELECT TaiKhoan.TenTaiKhoan,TaiKhoan.Gmail,TaiKhoan.MaNhanVien,NhanVien.MaQuyen FROM TaiKhoan,NhanVien WHERE TaiKhoan.MaNhanVien=NhanVien.MaNhanVien AND NhanVien.MaQuyen=?";
			connection = DbUtil.getConnection();
			preparedStatement = connection.prepareStatement(sqlQuery);
			preparedStatement.setString(1, "Q2");
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				taiKhoan = new TaiKhoan(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3),
						resultSet.getString(4));
				listTaiKhoan.add(taiKhoan);
			}
			return listTaiKhoan;
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

	// ===== Insert Tài khoản
	public static void themTaiKhoan(String tenTK, String matKhau, String gmail, String maNhanVien) {
		try {
			String sqlQuery = "INSERT INTO TaiKhoan VALUES(?,?,?,?)";
			connection = DbUtil.getConnection();
			preparedStatement = connection.prepareStatement(sqlQuery);

			preparedStatement.setString(1, tenTK);
			preparedStatement.setString(2, matKhau);
			preparedStatement.setString(3, gmail);
			preparedStatement.setString(4, maNhanVien);

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

	// ===== Delete tài khoản
	public static void xoaTaiKhoan(String tentaKhoan) {
		try {
			String sqlQuery = "DELETE FROM TaiKhoan WHERE TenTaiKhoan=?";
			connection = DbUtil.getConnection();
			preparedStatement = connection.prepareStatement(sqlQuery);
			preparedStatement.setString(1, tentaKhoan);

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
			alert.setContentText(e.getMessage());
			alert.getDialogPane().setStyle(
					"-fx-border-color: black;-fx-background-color: Orange; -fx-font-size: 14px;-fx-font-weight: bold;");
			alert.initOwner(EquipmentController.getInstance().lablSnack.getScene().getWindow());
			alert.showAndWait();
		}
	}

	// ===== Update lại Tai khoan
	public static void capNhatTaiKhoan(String tenTK, String gmail, String maNV) {
		try {
			String sqlQuery = "UPDATE TaiKhoan SET TenTaiKhoan=?,Gmail=? WHERE MaNhanVien=?";
			connection = DbUtil.getConnection();
			preparedStatement = connection.prepareStatement(sqlQuery);

			preparedStatement.setString(1, tenTK);
			preparedStatement.setString(2, gmail);
			preparedStatement.setString(3, maNV);
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
