package model.chatlieu;

public class ChatLieu {
	private String maChatLieu, tenChatLieu, moTa;

	// -----------------------------------------
	public String getMaChatLieu() {
		return maChatLieu;
	}

	public void setMaChatLieu(String maChatLieu) {
		this.maChatLieu = maChatLieu;
	}

	public String getTenChatLieu() {
		return tenChatLieu;
	}

	public void setTenChatLieu(String tenChatLieu) {
		this.tenChatLieu = tenChatLieu;
	}

	public String getMoTa() {
		return moTa;
	}

	public void setMoTa(String moTa) {
		this.moTa = moTa;
	}

	// -----------------------------------------
	public ChatLieu(String maChatLieu, String tenChatLieu, String moTa) {
		super();
		this.maChatLieu = maChatLieu;
		this.tenChatLieu = tenChatLieu;
		this.moTa = moTa;
	}

	public ChatLieu() {
		// TODO Auto-generated constructor stub
	}
}
