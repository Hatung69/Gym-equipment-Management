package model.thietbi;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
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

public class ThietBiDAO {
	private static Connection connection;
	private static CallableStatement callableStatement;
	private static PreparedStatement preparedStatement;
	private static ResultSet resultSet;

	// ===== Lấy dữ liệu lên trả về ObservableList của Thiết bị
	public static ObservableList<ThietBi> getListThietBi() {
		ObservableList<ThietBi> listThietBi = FXCollections.observableArrayList();
		ThietBi thietBi;
		try {
			String sqlQuery = "{Call GetAllEquipment}";
			connection = DbUtil.getConnection();
			callableStatement = connection.prepareCall(sqlQuery);
			resultSet = callableStatement.executeQuery();

			while (resultSet.next()) {
				thietBi = new ThietBi(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3),
						resultSet.getInt(4), resultSet.getString(5), resultSet.getInt(6), resultSet.getString(7),
						resultSet.getString(8), resultSet.getString(9), resultSet.getString(10), resultSet.getDate(11),
						resultSet.getDate(12), resultSet.getDate(13), resultSet.getBlob(14));
				listThietBi.add(thietBi);
			}
			return listThietBi;
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

	// ===== Insert thiết bị
	public static void themThietBi(String maThietBi, String tenThietBi, String loai, int khoiLuong, String chatLieu,
			int triGia, String tenThuongHieu, String tenBaiTap, String tenTinhTrang, String tenNhanVien,
			java.sql.Date ngayNhap, java.sql.Date namSX, java.sql.Date hanSuDung, FileInputStream hinhAnh) {
		try {
			String sqlQuery = "INSERT INTO ThietBi VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			connection = DbUtil.getConnection();
			preparedStatement = connection.prepareStatement(sqlQuery);

			preparedStatement.setString(1, maThietBi);
			preparedStatement.setString(2, tenThietBi);
			preparedStatement.setString(3, loai);
			preparedStatement.setInt(4, khoiLuong);
			preparedStatement.setString(5, chatLieu);
			preparedStatement.setInt(6, triGia);
			preparedStatement.setString(7, tenThuongHieu);
			preparedStatement.setString(8, tenBaiTap);
			preparedStatement.setString(9, tenTinhTrang);
			preparedStatement.setString(10, tenNhanVien);
			preparedStatement.setDate(11, ngayNhap);
			preparedStatement.setDate(12, namSX);
			preparedStatement.setDate(13, hanSuDung);
			preparedStatement.setBinaryStream(14, hinhAnh, hinhAnh.available());

			int x = preparedStatement.executeUpdate();
			if (x > 0) {
				EquipmentController.getInstance().snack = new JFXSnackbar(
						HomePageController.getInstance().stackPaneLoadLayout);
				EquipmentController.getInstance().lablSnack.setText("Thêm thành công !");
				EquipmentController.getInstance().snack
						.enqueue(new SnackbarEvent(EquipmentController.getInstance().lablSnack));
			}
		} catch (SQLException | IOException e) {
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
	public static void capNhatThietBi(String maThietBi, String tenThietBi, String loai, int khoiLuong, String chatLieu,
			int triGia, String tenThuongHieu, String tenBaiTap, String tenTinhTrang, String tenNhanVien,
			java.sql.Date ngayNhap, java.sql.Date namSX, java.sql.Date hanSuDung, InputStream hinhAnh) {
		try {
			String sqlQuery = "UPDATE ThietBi SET TenThietBi=?,MaLoai=?,KhoiLuong=?,MaChatLieu=?,TriGia=?,MaThuongHieu=?,MaBaiTap=?,MaTinhTrang=?,MaNhanVien=?,NgayNhap=?,NamSX=?,HanSuDung=?,HinhAnh=? WHERE MaThietBi=?";
			connection = DbUtil.getConnection();
			preparedStatement = connection.prepareStatement(sqlQuery);

			preparedStatement.setString(1, tenThietBi);
			preparedStatement.setString(2, loai);
			preparedStatement.setInt(3, khoiLuong);
			preparedStatement.setString(4, chatLieu);
			preparedStatement.setInt(5, triGia);
			preparedStatement.setString(6, tenThuongHieu);
			preparedStatement.setString(7, tenBaiTap);
			preparedStatement.setString(8, tenTinhTrang);
			preparedStatement.setString(9, tenNhanVien);
			preparedStatement.setDate(10, ngayNhap);
			preparedStatement.setDate(11, namSX);
			preparedStatement.setDate(12, hanSuDung);
			preparedStatement.setBinaryStream(13, hinhAnh, hinhAnh.available());
			preparedStatement.setString(14, maThietBi);

			int x = preparedStatement.executeUpdate();
			if (x > 0) {
				EquipmentController.getInstance().snack = new JFXSnackbar(
						HomePageController.getInstance().stackPaneLoadLayout);
				EquipmentController.getInstance().lablSnack.setText("Cập nhật thành công !");
				EquipmentController.getInstance().snack
						.enqueue(new SnackbarEvent(EquipmentController.getInstance().lablSnack));
			}
		} catch (SQLException | IOException e) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Thông báo");
			alert.setHeaderText(e.getMessage());
			alert.showAndWait();
		} finally {
			DbUtil.disConnAndPrep(connection, preparedStatement);
		}
	}

	// ===== Update lại cái Tình trạng thiết bị khi đưa vào bảo trì
	public static void capNhatTinhTrangTb(String tinhTrang, String maThietBi) {
		try {
			String sqlQuery = "UPDATE ThietBi SET MaTinhTrang=? WHERE MaThietBi=?";
			connection = DbUtil.getConnection();
			preparedStatement = connection.prepareStatement(sqlQuery);

			preparedStatement.setString(1, tinhTrang);
			preparedStatement.setString(2, maThietBi);
			int x = preparedStatement.executeUpdate();
			if (x > 0) {
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

	// ===== Delete thiết bị
	public static void xoaThietBi(String maThietBi) {
		try {
			String sqlQuery = "DELETE FROM ThietBi WHERE MaThietBi=?";
			connection = DbUtil.getConnection();
			preparedStatement = connection.prepareStatement(sqlQuery);
			preparedStatement.setString(1, maThietBi);

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
			alert.setContentText("Có thể thiết bị này đang được bảo trì! Cần hoàn tất bảo trì trước khi xóa !");
			alert.getDialogPane().setStyle(
					"-fx-border-color: black;-fx-background-color: Orange; -fx-font-size: 14px;-fx-font-weight: bold;");
			alert.initOwner(EquipmentController.getInstance().lablSnack.getScene().getWindow());
			alert.showAndWait();
		}finally {
			DbUtil.disConnAndPrep(connection, preparedStatement);
		}
	}

	// ===== Kiểm tra xem Mã Thiết bị tồn tại hay chưa
	public static Boolean maTBIsExist(String maThietBi) {
		try {
			String sqlQuery = "SELECT MaThietBi FROM ThietBi WHERE MaThietBi=?";
			connection = DbUtil.getConnection();
			preparedStatement = connection.prepareStatement(sqlQuery);
			preparedStatement.setString(1, maThietBi);

			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
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

	// ===== Lấy Mã thiết bị=====
	public static String getMaThietBi(String tenTB) {
		String maTb = "";
		try {
			String sqlQuery = "SELECT MaThietBi FROM ThietBi WHERE tenThietBi=?";
			connection = DbUtil.getConnection();
			preparedStatement = connection.prepareStatement(sqlQuery);
			preparedStatement.setString(1, tenTB);

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
		}finally {
			DbUtil.disConnAndPrepAndResul(connection, preparedStatement, resultSet);
		}
		return maTb;
	}

	// ===== Lấy Mã Loại thiết bị cho click TableView
	public static String getMaLoaiTB(String tenTB) {
		String maTb = "";
		try {
			String sqlQuery = "SELECT MaLoai FROM LoaiThietbi WHERE TenLoai=?";
			connection = DbUtil.getConnection();
			preparedStatement = connection.prepareStatement(sqlQuery);
			preparedStatement.setString(1, tenTB);

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
		}finally {
			DbUtil.disConnAndPrepAndResul(connection, preparedStatement, resultSet);
		}
		return maTb;
	}

	// ===== Lấy Mã chất liệu cho click TableView
	public static String getMaChatLieu(String tenCl) {
		String tenClieu = "";
		try {
			String sqlQuery = "SELECT MaChatLieu FROM ChatLieu WHERE TenChatLieu=?";
			connection = DbUtil.getConnection();
			preparedStatement = connection.prepareStatement(sqlQuery);
			preparedStatement.setString(1, tenCl);

			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				tenClieu = resultSet.getString(1);
				return tenClieu;
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
		return tenClieu;
	}

	// ===== Lấy Mã thương hiệu cho click TableView
	public static String getMaTH(String tenTH) {
		String tenTHieu = "";
		try {
			String sqlQuery = "SELECT MaThuongHieu FROM ThuongHieu WHERE TenThuongHieu=?";
			connection = DbUtil.getConnection();
			preparedStatement = connection.prepareStatement(sqlQuery);
			preparedStatement.setString(1, tenTH);

			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				tenTHieu = resultSet.getString(1);
				return tenTHieu;
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
		return tenTHieu;
	}

	// ===== Lấy Mã bài tập cho click TableView
	public static String getMaBT(String tenBT) {
		String tenBTap = "";
		try {
			String sqlQuery = "SELECT MaBaiTap FROM BaiTap WHERE TenBaiTap=?";
			connection = DbUtil.getConnection();
			preparedStatement = connection.prepareStatement(sqlQuery);
			preparedStatement.setString(1, tenBT);

			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				tenBTap = resultSet.getString(1);
				return tenBTap;
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
		return tenBTap;
	}

	// --------------------Thống kê ------------------------

	// ===== Lấy tổng số lượng thiết bị
	public static int getTongSoLuongTB() {
		int count = 0;
		try {
			String sqlQuery = "SELECT COUNT(*) FROM ThietBi";
			connection = DbUtil.getConnection();
			preparedStatement = connection.prepareStatement(sqlQuery);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				count = resultSet.getInt(1);
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

	// ===== Lấy tổng số lượng thiết bị Bt
	public static int getThietBiBaoTri() {
		int count = 0;
		try {
			String sqlQuery = "SELECT COUNT(*) FROM ThietBi WHERE MaTinhTrang=?";
			connection = DbUtil.getConnection();
			preparedStatement = connection.prepareStatement(sqlQuery);
			preparedStatement.setString(1, "TT2");
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				count = resultSet.getInt(1);
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

	// ===== Lấy tổng số lượng thiết bị BT
	public static int getThietBiBinhThuong() {
		int count = 0;
		try {
			String sqlQuery = "SELECT COUNT(*) FROM ThietBi WHERE MaTinhTrang=?";
			connection = DbUtil.getConnection();
			preparedStatement = connection.prepareStatement(sqlQuery);
			preparedStatement.setString(1, "TT1");
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				count = resultSet.getInt(1);
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

	// ===== Lấy tổng số lượng thiết bị theo Bài tập
	public static int getTongSoLuongTBtheoBT(String baiTap) {
		int count = 0;
		try {
			String sqlQuery = "SELECT COUNT(*) FROM ThietBi,BaiTap WHERE ThietBi.MaBaiTap=BaiTap.MaBaiTap AND BaiTap.TenBaiTap=?";
			connection = DbUtil.getConnection();
			preparedStatement = connection.prepareStatement(sqlQuery);
			preparedStatement.setString(1, baiTap);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				count = resultSet.getInt(1);
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

	// ===== Lấy tổng số lượng thiết bị theo Bài tập
	public static int getTongGiaTriTb() {
		int count = 0;
		try {
			String sqlQuery = "SELECT SUM(TriGia) FROM ThietBi";
			connection = DbUtil.getConnection();
			preparedStatement = connection.prepareStatement(sqlQuery);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				count = resultSet.getInt(1);
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
	
	// ===== Lấy tổng số lượng thiết bị hết hạn
	public static int getSoTBHetHSD(String dateNow) {
		int count = 0;
		try {
			String sqlQuery = "SELECT COUNT(*) From ThietBi Where HanSuDung<?";
			connection = DbUtil.getConnection();
			preparedStatement = connection.prepareStatement(sqlQuery);
			preparedStatement.setString(1, dateNow);
			resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next()) {
				count = resultSet.getInt(1);
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
	// ===== Lấy khối lượng nặng nhất của thiết bị
	public static int getKhoiLuongNangNhat() {
		int count = 0;
		try {
			String sqlQuery = "SELECT MAX(KhoiLuong) FROM ThietBi";
			connection = DbUtil.getConnection();
			preparedStatement = connection.prepareStatement(sqlQuery);
			resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next()) {
				count = resultSet.getInt(1);
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
