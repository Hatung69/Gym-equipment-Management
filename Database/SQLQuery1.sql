--Lấy danh sách thiết bị
CREATE PROC GetAllEquipment
As
	Begin
		SELECT MaThietbi,TenThietbi,LoaiThietbi.TenLoai,KhoiLuong,ChatLieu.TenChatLieu,TriGia,ThuongHieu.TenThuongHieu,BaiTap.TenBaiTap,TinhTrang.TenTinhTrang,NhanVien.TenNhanVien,NgayNhap,NamSX,HanSuDung,HinhAnh
		FROM ThietBi,LoaiThietbi,ChatLieu,ThuongHieu,BaiTap,TinhTrang,NhanVien
		WHERE ThietBi.MaLoai=LoaiThietbi.MaLoai AND ThietBi.MaChatLieu=ChatLieu.MaChatLieu AND ThietBi.MaThuongHieu=ThuongHieu.MaThuongHieu AND ThietBi.MaBaiTap=BaiTap.MaBaiTap AND ThietBi.MaTinhTrang=TinhTrang.MaTinhTrang AND ThietBi.MaNhanVien=NhanVien.MaNhanVien
	End
GO

--Lấy Mã Tên Nhân viên
CREATE PROC getMaVaTenNV @tenTK varchar(50)
As
	Begin
	SELECT NhanVien.MaNhanVien,NhanVien.TenNhanVien FROM TaiKhoan,NhanVien WHERE TaiKhoan.MaNhanVien=NhanVien.MaNhanVien AND ( TaiKhoan.TenTaiKhoan=@tenTK OR TaiKhoan.Gmail=@tenTK)
	End
GO
EXEC getMaVaTenNV @tenTK='TaiKhoanCuaChuoi'

GO
--Tìm kiếm Thiết bị theo Tên-Khối lượng-Bài tập
CREATE PROC SearchTenTB @tenTB nvarchar(100)
As
	Begin
		SELECT MaThietbi,TenThietbi,LoaiThietbi.TenLoai,KhoiLuong,ChatLieu.TenChatLieu,TriGia,ThuongHieu.TenThuongHieu,BaiTap.TenBaiTap,TinhTrang.TenTinhTrang,NhanVien.TenNhanVien,NgayNhap,NamSX,HanSuDung,HinhAnh
		FROM ThietBi,LoaiThietbi,ChatLieu,ThuongHieu,BaiTap,TinhTrang,NhanVien
		WHERE ThietBi.MaLoai=LoaiThietbi.MaLoai AND ThietBi.MaChatLieu=ChatLieu.MaChatLieu AND ThietBi.MaThuongHieu=ThuongHieu.MaThuongHieu AND ThietBi.MaBaiTap=BaiTap.MaBaiTap AND ThietBi.MaTinhTrang=TinhTrang.MaTinhTrang AND ThietBi.MaNhanVien=NhanVien.MaNhanVien AND ThietBi.TenThietbi  LIKE '%'+@tenTB+'%'
	End
GO
EXEC SearchTenTB @tenTB='g'
GO
ALTER PROC SearchKhoiLuong @khoiLuong int
As
	Begin
		SELECT MaThietbi,TenThietbi,LoaiThietbi.TenLoai,KhoiLuong,ChatLieu.TenChatLieu,TriGia,ThuongHieu.TenThuongHieu,BaiTap.TenBaiTap,TinhTrang.TenTinhTrang,NhanVien.TenNhanVien,NgayNhap,NamSX,HanSuDung,HinhAnh
		FROM ThietBi,LoaiThietbi,ChatLieu,ThuongHieu,BaiTap,TinhTrang,NhanVien
		WHERE ThietBi.MaLoai=LoaiThietbi.MaLoai AND ThietBi.MaChatLieu=ChatLieu.MaChatLieu AND ThietBi.MaThuongHieu=ThuongHieu.MaThuongHieu AND ThietBi.MaBaiTap=BaiTap.MaBaiTap AND ThietBi.MaTinhTrang=TinhTrang.MaTinhTrang AND ThietBi.MaNhanVien=NhanVien.MaNhanVien AND ThietBi.KhoiLuong = '%'+@khoiLuong+'%'
	End
GO
ALTER PROC SearchBaiTap @baiTap nvarchar(100)
As
	Begin
		SELECT MaThietbi,TenThietbi,LoaiThietbi.TenLoai,KhoiLuong,ChatLieu.TenChatLieu,TriGia,ThuongHieu.TenThuongHieu,BaiTap.TenBaiTap,TinhTrang.TenTinhTrang,NhanVien.TenNhanVien,NgayNhap,NamSX,HanSuDung,HinhAnh
		FROM ThietBi,LoaiThietbi,ChatLieu,ThuongHieu,BaiTap,TinhTrang,NhanVien
		WHERE ThietBi.MaLoai=LoaiThietbi.MaLoai AND ThietBi.MaChatLieu=ChatLieu.MaChatLieu AND ThietBi.MaThuongHieu=ThuongHieu.MaThuongHieu AND ThietBi.MaBaiTap=BaiTap.MaBaiTap AND ThietBi.MaTinhTrang=TinhTrang.MaTinhTrang AND ThietBi.MaNhanVien=NhanVien.MaNhanVien AND BaiTap.TenBaiTap LIKE '%'+@baiTap+'%'
	End
GO
EXEC SearchKhoiLuong @khoiLuong=35

--Lấy danh sách thiết bị
CREATE PROC GetAllMaintenance
As
	Begin
		SELECT ThongTinBaoTri.MaBaoTri,ThongTinBaoTri.TenBaoTri,ThietBi.TenThietbi,LoaiBaoTri.TenLoaiBaoTri,ThongTinBaoTri.NgayBaoTri,ThongTinBaoTri.NgayHoanTat,ThongTinBaoTri.MoTa
		FROM ThongTinBaoTri,ThietBi,LoaiBaoTri
		WHERE ThongTinBaoTri.MaThietBi=ThietBi.MaThietbi AND ThongTinBaoTri.MaLoaiBaoTri=LoaiBaoTri.MaLoaiBaoTri
	End
GO
EXEC GetAllMaintenance
--Lấy danh sách nhân viên có quyền từ tài khoản là Q2
CREATE PROC GetALLEmployee
As
	Begin
SELECT NhanVien.MaNhanVien,NhanVien.TenNhanVien,NhanVien.NgaySinh,NhanVien.GioiTinh,NhanVien.DiaChi,NhanVien.SDT FROM NhanVien,TaiKhoan,QuyenHan WHERE NhanVien.MaNhanVien=TaiKhoan.MaNhanVien AND TaiKhoan.MaQuyen=QuyenHan.MaQuyen AND QuyenHan.MaQuyen='Q2'
	End
Go
SELECT TenTaiKhoan,Gmail,MaNhanVien,MaQuyen FROM TaiKhoan WHERE MaQuyen='Q2'

