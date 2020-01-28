package model.tinhtrang;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import connectionDb.DbUtil;
import controllerFXML.equipment.EquipmentController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class TinhTrangDAO {
	private static Connection connection;
	private static PreparedStatement preparedStatement;
	private static ResultSet resultSet;

	// ===== Lấy dữ liệu lên trả về ObservableList của Thiết bị
	public static ObservableList<TinhTrang> getListTinhTrang() {
		ObservableList<TinhTrang> listTinhTrang = FXCollections.observableArrayList();
		TinhTrang tinhTrang;
		try {
			String sqlQuery = "SELECT * FROM TinhTrang";
			connection = DbUtil.getConnection();
			preparedStatement = connection.prepareCall(sqlQuery);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				tinhTrang = new TinhTrang(resultSet.getString("MaTinhTrang"), resultSet.getString("TenTinhTrang"));
				listTinhTrang.add(tinhTrang);
			}
			return listTinhTrang;
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
}
