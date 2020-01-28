package model.loaiBT;

public class LoaiBaoTri {
	private String maLoaiBT, tenLoaiBT, moTa;

	// ------------------------
	public String getMaLoaiBT() {
		return maLoaiBT;
	}

	public void setMaLoaiBT(String maLoaiBT) {
		this.maLoaiBT = maLoaiBT;
	}

	public String getTenLoaiBT() {
		return tenLoaiBT;
	}

	public void setTenLoaiBT(String tenLoaiBT) {
		this.tenLoaiBT = tenLoaiBT;
	}

	public String getMoTa() {
		return moTa;
	}

	public void setMoTa(String moTa) {
		this.moTa = moTa;
	}

	// --------------------------------
	public LoaiBaoTri(String maLoaiBT, String tenLoaiBT, String moTa) {
		super();
		this.maLoaiBT = maLoaiBT;
		this.tenLoaiBT = tenLoaiBT;
		this.moTa = moTa;
	}

	public LoaiBaoTri() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String toString() {
		return maLoaiBT + "-" + tenLoaiBT;
	}
}
