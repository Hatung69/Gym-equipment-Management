package model.loaiBT;

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

public class LoaiBaoTriDAO {
	private static Connection connection;
	private static PreparedStatement preparedStatement;
	private static ResultSet resultSet;

	// ===== Lấy dữ liệu lên trả về ArrayList của Loại bảo trì
	public static ArrayList<LoaiBaoTri> getListLoaiBT() {
		ArrayList<LoaiBaoTri> listLoaiBT = new ArrayList<LoaiBaoTri>();
		LoaiBaoTri loaiBaoTri;
		try {
			String sqlQuery = "SELECT * FROM LoaiBaoTri";
			connection = DbUtil.getConnection();
			preparedStatement = connection.prepareStatement(sqlQuery);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				loaiBaoTri = new LoaiBaoTri(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3));
				listLoaiBT.add(loaiBaoTri);
			}
			return listLoaiBT;
		} catch (SQLException e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Thông báo");
			alert.setContentText(e.getMessage());
			alert.getDialogPane().setStyle(
					"-fx-border-color: black;-fx-background-color: Orange; -fx-font-size: 14px;-fx-font-weight: bold;");
			alert.initOwner(EquipmentController.getInstance().lablSnack.getScene().getWindow());
			alert.showAndWait();;
		} finally {
			DbUtil.disConnAndPrepAndResul(connection, preparedStatement, resultSet);
		}
		return null;
	}
	
	// ===== Lấy dữ liệu lên trả về ObservableList của Loại bảo trì
		public static ObservableList<LoaiBaoTri> getListBTObser() {
			ObservableList<LoaiBaoTri> listLoaiBaoTri = FXCollections.observableArrayList();
			LoaiBaoTri loaiBaoTri;
			try {
				String sqlQuery = "SELECT * FROM LoaiBaoTri";
				connection = DbUtil.getConnection();
				preparedStatement = connection.prepareStatement(sqlQuery);
				resultSet = preparedStatement.executeQuery();

				while (resultSet.next()) {
					loaiBaoTri = new LoaiBaoTri(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3));
					listLoaiBaoTri.add(loaiBaoTri);
				}
				return listLoaiBaoTri;
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

		// ===== Insert Loại Loại bảo trì
		public static void themLoaiBaoTri(String maLoaiBT, String tenLoaiBT, String moTa) {
			try {
				String sqlQuery = "INSERT INTO LoaiBaoTri VALUES(?,?,?)";
				connection = DbUtil.getConnection();
				preparedStatement = connection.prepareStatement(sqlQuery);
				preparedStatement.setString(1, maLoaiBT);
				preparedStatement.setString(2, tenLoaiBT);
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
		
		// ===== Update Loai bao tri
		public static void capNhatLoaiBaoTri(String maLoai,String tenLoai,String moTa ) {
			try {
				String sqlQuery = "UPDATE LoaiBaoTri SET TenLoaiBaoTri=?,MoTa=? WHERE MaLoaiBaoTri=?";
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
		
		// ===== Delete Loại Bao tri
			public static void xoaLoaiBaoTri(String maloai) {
				try {
					String sqlQuery = "DELETE FROM LoaiBaoTri WHERE MaLoaiBaoTri=?";
					connection = DbUtil.getConnection();
					preparedStatement = connection.prepareStatement(sqlQuery);
					preparedStatement.setString(1, maloai);

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
					alert.setContentText("Vui lòng kiểm tra kỹ trước khi xóa. Có thể Loại bảo trì này đang thuộc 1 Bảo trì !");
					alert.getDialogPane().setStyle(
							"-fx-border-color: black;-fx-background-color: Orange; -fx-font-size: 14px;-fx-font-weight: bold;");
					alert.initOwner(EquipmentController.getInstance().lablSnack.getScene().getWindow());
					alert.showAndWait();
				}
			}
		
		// ===== Kiểm tra xem Mã Loại hiết bị tồn tại hay chưa
			public static Boolean maLBTIsExist(String maLoai) {
				try {
					String sqlQuery = "SELECT MaLoaiBaoTri FROM LoaiBaoTri WHERE MaLoaiBaoTri=?";
					connection = DbUtil.getConnection();
					preparedStatement = connection.prepareStatement(sqlQuery);
					preparedStatement.setString(1, maLoai);

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
