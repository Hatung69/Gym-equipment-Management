package connectionDb;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class DbUtil {
	private static Connection connection;
	private static final String dbName = "GymEquipment";

	// ===== Trả về 1 connetion đã kết nối đến Db
	public static Connection getConnection() {
		String URL = "jdbc:sqlserver://localhost;databaseName=" + dbName + ";user=sa;password=123;";
		try {
			connection = DriverManager.getConnection(URL);
		} catch (SQLException e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Thông báo");
			alert.setHeaderText("Kêt nối Cơ sở dữ liệu không thành công !");
			alert.setContentText(e.toString());
			alert.showAndWait();
		}
		return connection;
	}

	// ===== Truyền connnection cần đóng
	public static void disConnection(Connection connection) {
		try {
			if (connection != null && !connection.isClosed()) {
				connection.close();
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}

	// ===== Truyền preparedStatement cần đóng
	public static void disPreparedStatement(PreparedStatement preparedStatement) {
		try {
			if (preparedStatement != null && !preparedStatement.isClosed()) {
				preparedStatement.close();
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}

	// ===== Truyền connnection và preparedStatement cần đóng
	public static void disConnAndPrep(Connection connection, PreparedStatement preparedStatement) {
		try {
			if (connection != null && !connection.isClosed()) {
				connection.close();
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
		try {
			if (preparedStatement != null && !preparedStatement.isClosed()) {
				preparedStatement.close();
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}

	// ===== Truyền callableStatement cần đóng
	public static void disCallableStatement(CallableStatement callableStatement) {
		try {
			if (callableStatement != null && !callableStatement.isClosed()) {
				callableStatement.close();
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}

	// ===== Truyền connnection và callableStatement cần đóng
	public static void disConnAndCall(Connection connection, CallableStatement callableStatement) {
		try {
			if (connection != null && !connection.isClosed()) {
				connection.close();
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
		try {
			if (callableStatement != null && !callableStatement.isClosed()) {
				callableStatement.close();
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}

	// ===== Truyền connnection, callableStatement và result cần đóng
	public static void disConnAndCallAndResult(Connection connection, CallableStatement callableStatement,
			ResultSet resultSet) {
		try {
			if (connection != null && !connection.isClosed()) {
				connection.close();
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
		try {
			if (callableStatement != null && !callableStatement.isClosed()) {
				callableStatement.close();
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
		try {
			if (resultSet != null && !resultSet.isClosed()) {
				resultSet.close();
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}

	// ===== Truyền resultSet cần đóng
	public static void diSresultSet(ResultSet resultSet) {
		try {
			if (resultSet != null && !resultSet.isClosed()) {
				resultSet.close();
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}

	// ===== Truyền connnection, preparedStatement và resultSet cần đóng
	public static void disConnAndPrepAndResul(Connection connection, PreparedStatement preparedStatement,
			ResultSet resultSet) {
		try {
			if (connection != null && !connection.isClosed()) {
				connection.close();
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
		try {
			if (preparedStatement != null && !preparedStatement.isClosed()) {
				preparedStatement.close();
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
		try {
			if (resultSet != null && !resultSet.isClosed()) {
				resultSet.close();
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}
}
