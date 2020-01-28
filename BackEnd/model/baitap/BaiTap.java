package model.baitap;

public class BaiTap {
	private String maBaiTap, tenBaiTap, moTa;

	// ---------------------------
	public String getMaBaiTap() {
		return maBaiTap;
	}

	public void setMaBaiTap(String maBaiTap) {
		this.maBaiTap = maBaiTap;
	}

	public String getTenBaiTap() {
		return tenBaiTap;
	}

	public void setTenBaiTap(String tenBaiTap) {
		this.tenBaiTap = tenBaiTap;
	}

	public String getMoTa() {
		return moTa;
	}

	public void setMoTa(String moTa) {
		this.moTa = moTa;
	}

	// ----------------------------------------------
	public BaiTap(String maBaiTap, String tenBaiTap, String moTa) {
		super();
		this.maBaiTap = maBaiTap;
		this.tenBaiTap = tenBaiTap;
		this.moTa = moTa;
	}

	public BaiTap() {
		// TODO Auto-generated constructor stub
	}
}
