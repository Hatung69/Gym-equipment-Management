package model.loaiTB;

public class LoaiThietBi {
	private String maLoai, tenLoai, moTa;

	//--------------------------------------------
	public String getMaLoai() {
		return maLoai;
	}

	public void setMaLoai(String maLoai) {
		this.maLoai = maLoai;
	}

	public String getTenLoai() {
		return tenLoai;
	}

	public void setTenLoai(String tenLoai) {
		this.tenLoai = tenLoai;
	}

	public String getMoTa() {
		return moTa;
	}

	public void setMoTa(String moTa) {
		this.moTa = moTa;
	}

	//------------------------------------------------------
	public LoaiThietBi(String maLoai, String tenLoai, String moTa) {
		super();
		this.maLoai = maLoai;
		this.tenLoai = tenLoai;
		this.moTa = moTa;
	}
	
	public LoaiThietBi() {
		// TODO Auto-generated constructor stub
	}
}
