package model.thietbi;

import java.sql.Blob;
import java.util.Date;

public class ThietBi {
	private String maThietBi, tenThietBi, loai;
	private int khoiLuong;
	private String chatLieu;
	private int triGia;
	private String tenThuongHieu, tenBaiTap, tenTinhTrang, tenNhanVien;
	private Date ngayNhap, namSX, hanSuDung;
	private Blob hinhAnh;

	// ----------------------------------
	public String getMaThietBi() {
		return maThietBi;
	}

	public void setMaThietBi(String maThietBi) {
		this.maThietBi = maThietBi;
	}

	public String getTenThietBi() {
		return tenThietBi;
	}

	public void setTenThietBi(String tenThietBi) {
		this.tenThietBi = tenThietBi;
	}

	public String getLoai() {
		return loai;
	}

	public void setLoai(String loai) {
		this.loai = loai;
	}

	public int getKhoiLuong() {
		return khoiLuong;
	}

	public void setKhoiLuong(int khoiLuong) {
		this.khoiLuong = khoiLuong;
	}

	public String getChatLieu() {
		return chatLieu;
	}

	public void setChatLieu(String chatLieu) {
		this.chatLieu = chatLieu;
	}

	public int getTriGia() {
		return triGia;
	}

	public void setTriGia(int triGia) {
		this.triGia = triGia;
	}

	public String getTenThuongHieu() {
		return tenThuongHieu;
	}

	public void setTenThuongHieu(String tenThuongHieu) {
		this.tenThuongHieu = tenThuongHieu;
	}

	public String getTenBaiTap() {
		return tenBaiTap;
	}

	public void setTenBaiTap(String tenBaiTap) {
		this.tenBaiTap = tenBaiTap;
	}

	public String getTenTinhTrang() {
		return tenTinhTrang;
	}

	public void setTenTinhTrang(String tenTinhTrang) {
		this.tenTinhTrang = tenTinhTrang;
	}

	public String getTenNhanVien() {
		return tenNhanVien;
	}

	public void setTenNhanVien(String tenNhanVien) {
		this.tenNhanVien = tenNhanVien;
	}

	public Date getNgayNhap() {
		return ngayNhap;
	}

	public void setNgayNhap(Date ngayNhap) {
		this.ngayNhap = ngayNhap;
	}

	public Date getNamSX() {
		return namSX;
	}

	public void setNamSX(Date namSX) {
		this.namSX = namSX;
	}

	public Date getHanSuDung() {
		return hanSuDung;
	}

	public void setHanSuDung(Date hanSuDung) {
		this.hanSuDung = hanSuDung;
	}

	public Blob getHinhAnh() {
		return hinhAnh;
	}

	public void setHinhAnh(Blob hinhAnh) {
		this.hinhAnh = hinhAnh;
	}

	// ------------------------------------
	public ThietBi(String maThietBi, String tenThietBi, String loai, int khoiLuong, String chatLieu, int triGia,
			String tenThuongHieu, String tenBaiTap, String tenTinhTrang, String tenNhanVien, Date ngayNhap, Date namSX,
			Date hanSuDung, Blob hinhAnh) {
		super();
		this.maThietBi = maThietBi;
		this.tenThietBi = tenThietBi;
		this.loai = loai;
		this.khoiLuong = khoiLuong;
		this.chatLieu = chatLieu;
		this.triGia =triGia;
		this.tenThuongHieu = tenThuongHieu;
		this.tenBaiTap = tenBaiTap;
		this.tenTinhTrang = tenTinhTrang;
		this.tenNhanVien = tenNhanVien;
		this.ngayNhap = ngayNhap;
		this.namSX = namSX;
		this.hanSuDung = hanSuDung;
		this.hinhAnh = hinhAnh;
	}

	public ThietBi() {
		// TODO Auto-generated constructor stub
	}
}
