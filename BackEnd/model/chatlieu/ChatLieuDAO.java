package model.chatlieu;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import connectionDb.DbUtil;
import controllerFXML.equipment.EquipmentController;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class ChatLieuDAO {
	private static Connection connection;
	private static PreparedStatement preparedStatement;
	private static ResultSet resultSet;

	// ===== Lấy dữ liệu lên trả về ArrayList của Chất liệu
	public static ArrayList<ChatLieu> getListChatLieu() {
		 ArrayList<ChatLieu>  listChatLieu = new ArrayList<ChatLieu>();
		ChatLieu chatLieu;
		try {
			String sqlQuery = "SELECT * FROM ChatLieu";
			connection=DbUtil.getConnection();
			preparedStatement = connection.prepareStatement(sqlQuery);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				chatLieu = new ChatLieu(resultSet.getString("MaChatLieu"), resultSet.getString("TenChatLieu"),
						resultSet.getString("MoTa"));
				listChatLieu.add(chatLieu);
			}
			return listChatLieu; 
		} catch (SQLException e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Thông báo");
			alert.setContentText(e.getMessage());
			alert.getDialogPane().setStyle(
					"-fx-border-color: black;-fx-background-color: Orange; -fx-font-size: 14px;-fx-font-weight: bold;");
			alert.initOwner(EquipmentController.getInstance().lablSnack.getScene().getWindow());
			alert.showAndWait();
		}
		finally {
			DbUtil.disConnAndPrepAndResul(connection, preparedStatement, resultSet);
		}
		return null;
	}

}
