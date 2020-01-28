package model.thuonghieu;

public class ThuongHieu {
	private String maThuongHieu, TenThuongHieu, quocGia, moTa;

//----------------------------------------------
	public String getMaThuongHieu() {
		return maThuongHieu;
	}

	public void setMaThuongHieu(String maThuongHieu) {
		this.maThuongHieu = maThuongHieu;
	}

	public String getTenThuongHieu() {
		return TenThuongHieu;
	}

	public void setTenThuongHieu(String tenThuongHieu) {
		TenThuongHieu = tenThuongHieu;
	}

	public String getQuocGia() {
		return quocGia;
	}

	public void setQuocGia(String quocGia) {
		this.quocGia = quocGia;
	}

	public String getMoTa() {
		return moTa;
	}

	public void setMoTa(String moTa) {
		this.moTa = moTa;
	}

//-----------------------------------
	public ThuongHieu(String maThuongHieu, String tenThuongHieu, String quocGia, String moTa) {
		super();
		this.maThuongHieu = maThuongHieu;
		TenThuongHieu = tenThuongHieu;
		this.quocGia = quocGia;
		this.moTa = moTa;
	}

	public ThuongHieu() {
		// TODO Auto-generated constructor stub
	}
}
