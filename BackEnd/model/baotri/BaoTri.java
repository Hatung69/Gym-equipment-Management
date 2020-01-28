package model.baotri;

import java.util.Date;

public class BaoTri {
	String maBaoTri, tenBaoTri, tenThietBi, loaiBaoTri, moTa;
	Date ngayBaoTri, ngayHoanTat;

	//-----------------------------
	public String getMaBaoTri() {
		return maBaoTri;
	}

	public void setMaBaoTri(String maBaoTri) {
		this.maBaoTri = maBaoTri;
	}

	public String getTenBaoTri() {
		return tenBaoTri;
	}

	public void setTenBaoTri(String tenBaoTri) {
		this.tenBaoTri = tenBaoTri;
	}

	public String getTenThietBi() {
		return tenThietBi;
	}

	public void setTenThietBi(String tenThietBi) {
		this.tenThietBi = tenThietBi;
	}

	public String getLoaiBaoTri() {
		return loaiBaoTri;
	}

	public void setLoaiBaoTri(String loaiBaoTri) {
		this.loaiBaoTri = loaiBaoTri;
	}

	public String getMoTa() {
		return moTa;
	}

	public void setMoTa(String moTa) {
		this.moTa = moTa;
	}

	public Date getNgayBaoTri() {
		return ngayBaoTri;
	}

	public void setNgayBaoTri(Date ngayBaoTri) {
		this.ngayBaoTri = ngayBaoTri;
	}

	public Date getNgayHoanTat() {
		return ngayHoanTat;
	}

	public void setNgayHoanTat(Date ngayHoanTat) {
		this.ngayHoanTat = ngayHoanTat;
	}

	//--------------------------------------
	public BaoTri(String maBaoTri, String tenBaoTri, String tenThietBi, String loaiBaoTri, Date ngayBaoTri,
			Date ngayHoanTat, String moTa) {
		super();
		this.maBaoTri = maBaoTri;
		this.tenBaoTri = tenBaoTri;
		this.tenThietBi = tenThietBi;
		this.loaiBaoTri = loaiBaoTri;
		this.moTa = moTa;
		this.ngayBaoTri = ngayBaoTri;
		this.ngayHoanTat = ngayHoanTat;
	}

	public BaoTri() {
		// TODO Auto-generated constructor stub
	}
}
