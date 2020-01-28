package javaMail;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class JavaMailUtil {

	// ===== Gửi mail
	public static void sendMail(String recepient, String code) {
		Properties properties = new Properties();
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.port", "587");

		String myAccountEmail = "quanlythietbigym@gmail.com";
		String password = "quanlygym";

		Session session = Session.getInstance(properties, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(myAccountEmail, password);
			}
		});
		Message message = prepareMessage(session, myAccountEmail, recepient, code);
		try {
			Transport.send(message);
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Thông báo");
			alert.setHeaderText(e.getMessage());
			alert.showAndWait();
		}
	}

	private static Message prepareMessage(Session session, String myAccountEmail, String recepient, String codeString) {
		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(myAccountEmail));
			message.setRecipient(Message.RecipientType.TO, new InternetAddress(recepient));
			message.setSubject("Xin chào ! Quản lý phòng Gym gửi bạn Mã xác nhận.");
			message.setText("Mã xác nhận của bạn là : " + codeString);
			return message;
		} catch (Exception ex) {
			ex.getStackTrace();
		}
		return null;
	}
	
	//===== Random mã xác nhận
	public static String getAlphaNumericString() {
		String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789" + "abcdefghijklmnopqrstuvxyz";
		StringBuilder sb = new StringBuilder(10);//Mã 10 char thôi
		for (int i = 0; i < 10; i++) {
			int index = (int) (AlphaNumericString.length() * Math.random());
			sb.append(AlphaNumericString.charAt(index));
		}
		return sb.toString();
	}
}
