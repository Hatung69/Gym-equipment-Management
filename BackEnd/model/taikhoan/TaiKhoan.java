package model.taikhoan;

public class TaiKhoan {
	private String tenTaiKhoan, matKhau, gmail, maNhanVien, maQuyen;

//-----------------------

	public String getTenTaiKhoan() {
		return tenTaiKhoan;
	}

	public void setTenTaiKhoan(String tenTaiKhoan) {
		this.tenTaiKhoan = tenTaiKhoan;
	}

	public String getMatKhau() {
		return matKhau;
	}

	public void setMatKhau(String matKhau) {
		this.matKhau = matKhau;
	}

	public String getGmail() {
		return gmail;
	}

	public void setGmail(String gmail) {
		this.gmail = gmail;
	}

	public String getMaNhanVien() {
		return maNhanVien;
	}

	public void setMaNhanVien(String maNhanVien) {
		this.maNhanVien = maNhanVien;
	}

	public String getMaQuyen() {
		return maQuyen;
	}

	public void setMaQuyen(String maQuyen) {
		this.maQuyen = maQuyen;
	}

//---------------------
	public TaiKhoan( String tenTaiKhoan, String matKhau, String gmail, String maNhanVien, String maQuyen) {
		super();
		this.tenTaiKhoan = tenTaiKhoan;
		this.matKhau = matKhau;
		this.gmail = gmail;
		this.maNhanVien = maNhanVien;
		this.maQuyen = maQuyen;
	}

	public TaiKhoan() {
		// TODO Auto-generated constructor stub
	}

	public TaiKhoan(String tenTaiKhoan, String gmail, String maNhanVien, String maQuyen) {
		super();
		this.tenTaiKhoan = tenTaiKhoan;
		this.gmail = gmail;
		this.maNhanVien = maNhanVien;
		this.maQuyen = maQuyen;
	}
	
	
}
