package model.baitap;

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

public class BaiTapDAO {
	private static Connection connection;
	private static PreparedStatement preparedStatement;
	private static ResultSet resultSet;

	// ===== Lấy dữ liệu lên trả về ArrayList của Bài tập
	public static ArrayList<BaiTap> getListBaiTap() {
		 ArrayList<BaiTap>  listBaiTap = new ArrayList<BaiTap>();
		 BaiTap baiTap;
		try {
			String sqlQuery = "SELECT * FROM BaiTap";
			connection=DbUtil.getConnection();
			preparedStatement = connection.prepareStatement(sqlQuery);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				baiTap = new BaiTap(resultSet.getString("MabaiTap"), resultSet.getString("TenBaiTap"),
						resultSet.getString("MoTa"));
				listBaiTap.add(baiTap);
			}
			return listBaiTap; 
		} catch (SQLException e) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Thông báo");
			alert.setHeaderText(e.getMessage());
			alert.showAndWait();
		}
		finally {
			DbUtil.disConnAndPrepAndResul(connection, preparedStatement, resultSet);
		}
		return null;
	}
	
	// ===== Lấy dữ liệu lên trả về ObservableList của Bài tập
		public static ObservableList<BaiTap> getListBTObser() {
			ObservableList<BaiTap> listBaiTap = FXCollections.observableArrayList();
			BaiTap baiTap;
			try {
				String sqlQuery = "SELECT * FROM BaiTap";
				connection = DbUtil.getConnection();
				preparedStatement = connection.prepareStatement(sqlQuery);
				resultSet = preparedStatement.executeQuery();

				while (resultSet.next()) {
					baiTap = new BaiTap(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3));
					listBaiTap.add(baiTap);
				}
				return listBaiTap;
			} catch (SQLException e) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Thông báo");
				alert.setHeaderText(e.getMessage());
				alert.showAndWait();
			} finally {
				DbUtil.disConnAndPrepAndResul(connection, preparedStatement, resultSet);
			}
			return null;
		}

		// ===== Insert Bài tập
		public static void themBaiTap(String ma, String ten, String moTa) {
			try {
				String sqlQuery = "INSERT INTO BaiTap VALUES(?,?,?)";
				connection = DbUtil.getConnection();
				preparedStatement = connection.prepareStatement(sqlQuery);
				preparedStatement.setString(1, ma);
				preparedStatement.setString(2, ten);
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
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Thông báo");
				alert.setHeaderText(e.getMessage());
				alert.showAndWait();
			} finally {
				DbUtil.disConnAndPrep(connection, preparedStatement);
			}
		}
		
		// ===== Update Bài tập
		public static void capNhatBaiTap(String ma,String ten,String moTa ) {
			try {
				String sqlQuery = "UPDATE BaiTap SET TenBaiTap=?,MoTa=? WHERE MaBaiTap=?";
				connection = DbUtil.getConnection();
				preparedStatement = connection.prepareStatement(sqlQuery);
				preparedStatement.setString(1, ten);
				preparedStatement.setString(2, moTa);
				preparedStatement.setString(3, ma);

				int x = preparedStatement.executeUpdate();
				if (x > 0) {
					EquipmentController.getInstance().snack=new JFXSnackbar(HomePageController.getInstance().stackPaneLoadLayout);
					EquipmentController.getInstance().lablSnack.setText("Cập nhật thành công !");
					EquipmentController.getInstance().snack.enqueue(new SnackbarEvent(EquipmentController.getInstance().lablSnack));
				}
			} catch (SQLException e) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Thông báo");
				alert.setHeaderText(e.getMessage());
				alert.showAndWait();
			} finally {
				DbUtil.disConnAndPrep(connection, preparedStatement);
			}
		}
		
		// ===== Delete Bài tập
			public static void xoaBaiTap(String maBT) {
				try {
					String sqlQuery = "DELETE FROM BaiTap WHERE MaBaiTap=?";
					connection = DbUtil.getConnection();
					preparedStatement = connection.prepareStatement(sqlQuery);
					preparedStatement.setString(1, maBT);

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
					alert.setContentText("Vui lòng kiểm tra kỹ trước khi xóa. Có thể Bài tập này đang thuộc 1 thiết bị !");
					alert.getDialogPane().setStyle(
							"-fx-border-color: black;-fx-background-color: Orange; -fx-font-size: 14px;-fx-font-weight: bold;");
					alert.initOwner(EquipmentController.getInstance().lablSnack.getScene().getWindow());
					alert.showAndWait();
				}finally {
					DbUtil.disConnAndPrep(connection, preparedStatement);
				}
			}
		
		// ===== Kiểm tra xem Mã Loại hiết bị tồn tại hay chưa
			public static Boolean maLBTIsExist(String maBT) {
				try {
					String sqlQuery = "SELECT MaBaiTap FROM BaiTap WHERE MaBaiTap=?";
					connection = DbUtil.getConnection();
					preparedStatement = connection.prepareStatement(sqlQuery);
					preparedStatement.setString(1, maBT);

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
					DbUtil.disConnAndPrep(connection, preparedStatement);
				}
				return false;
			}
			
			//----------------------------------
			// ===== Lấy tổng số lượng bài tập
			public static int getTongSoLuongBaiTap() {
				int count=0;
				try {
					String sqlQuery="SELECT COUNT(*) FROM BaiTap";
					connection=DbUtil.getConnection();
					preparedStatement=connection.prepareStatement(sqlQuery);
					resultSet=preparedStatement.executeQuery();
					
					while(resultSet.next()) {
							count=resultSet.getInt(1);
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
				return count;
			}
}
