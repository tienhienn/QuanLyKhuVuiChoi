--drop database if exists QUANLYKHUVUICHOI
IF EXISTS (SELECT * FROM sys.databases WHERE name = 'QUANLYKHUVUICHOI')
BEGIN
    USE master; -- Chuyển sang cơ sở dữ liệu master để có thể xóa được cơ sở dữ liệu khác
    ALTER DATABASE QUANLYKHUVUICHOI SET SINGLE_USER WITH ROLLBACK IMMEDIATE; -- Ngắt mọi kết nối
    DROP DATABASE QUANLYKHUVUICHOI; -- Xóa cơ sở dữ liệu
END
-- Lệnh tạo database QLBH_2216
create database QUANLYKHUVUICHOI
go 
-- Sử dụng database QUANLYKHUVUICHOI
use QUANLYKHUVUICHOI

create table Khu
(
	maKhu char(5) primary key,
	tenKhu nvarchar(50),
	mota nvarchar(100)
)
create table dichvu
(
	maDichVu char(5) primary key,
	makhu char(5),
	tenDichVu nvarchar(50),
	mota nvarchar(100),
	Foreign key (makhu) references Khu(makhu)
		on update 
			cascade 
		on delete 
			cascade
)
create table TroChoi
(
	maTroChoi char(5) primary key,
	maDichVu char(5),
	tenTrochoi nvarchar(50),
	mota nvarchar(100),
	gioiHanDoTuoi int,
	sucChua int,
	thoiGianHoatDong Date,
	FOREIGN KEY (maDichVu) REFERENCES dichvu(maDichVu)
			on update
				cascade
			on delete
				cascade
)
create table SuKien
(	
	masukien char(5) primary key,
	maDichVu char(5),
	tenSuKien nvarchar(50),
	TGianBatDau date,
	TgianKetThuc date,
	TrangThaiHoatDong nvarchar(10),
	mucDoCuonHut nvarchar(50),
	gioiHanDoTuoi int,
	SucChua int,
	FOREIGN KEY (maDichVu) REFERENCES dichvu(maDichVu)
			on update
				cascade
			on delete
				cascade
)
create table NhaHang
(
	maNhaHang char(5) primary key,
	maDichVu char(5),
	TGianMoCua date,
	TgianDongCua date,
	loaiNhaHang nvarchar(50),
	FOREIGN KEY (maDichVu) REFERENCES dichvu(maDichVu)
			on update
				cascade
			on delete
				cascade
)
create table ChucVu
(
	maChucVu char(5) primary key,
	tenChucVu nvarchar(50)
)
create table NhanVien
(
	maNhanVien char(5) primary key,
	tenNhanVien nvarchar(50),
	NgaySinh date,
	SDT varchar(10),
	email varchar(50),
	ngayBatDauLam date,
	luong decimal(10,0)
)
create table nhanvien_chucvu
(
	maNhanVien char(5),
	maChucVu char(5),
	tgian_batdau date,
	tgian_ketthuc date,
	primary key (maNhanVien, maChucVu),
	foreign key (maNhanVien) references NhanVien(maNhanVien)
			on update
				cascade
			on delete
				cascade,
	foreign key (maChucVu) references ChucVu(machucvu)
			on update
				cascade
			on delete
				cascade
)
CREATE TABLE calamViec (
    maCa CHAR(5) PRIMARY KEY,
    tenca NVARCHAR(50),
    gio_batdau TIME,
    gio_ketthuc TIME
)
create table phanCongCa
(
	maNhanVien char(5),
	maCa char(5),
	ngayLamViec date,
	primary key (maNhanVien, maCa),
	foreign key (maNhanVien) references NhanVien(maNhanVien)
			on update
				cascade
			on delete
				cascade,
	foreign key (maCa) references calamViec(maCa)
			on update
				cascade
			on delete
				cascade
)
create table nhanvien_dichvu
(
	maNhanVien char(5),
	maDichVu char(5),
	primary key (maNhanVien, maDichVu),
	foreign key (maNhanVien) references NhanVien(maNhanVien)
			on update
				cascade
			on delete
				cascade,
	foreign key (maDichVu) references dichvu(maDichVu)
			on update
				cascade
			on delete
				cascade
)
CREATE TABLE khachhang (
    maKhachHang char(5) PRIMARY KEY,
    tenKhachHang nVARCHAR(100),
	matkhau varchar(50) unique,
    SDT VARCHAR(10) unique,
    email VARCHAR(100) unique,
	diaChi nvarchar(100),
	gioiTinh nvarchar(10),
	ngaySinh date,
	quocTich nvarchar(100)
)

-- Bảng Voucher
CREATE TABLE voucher (
    MaVoucher char(5) PRIMARY KEY,
    tenVoucher NVARCHAR(100),
    TGianHieuLuc DATE,
    TGianKetThuc DATE,
    GiaTriUuDai DECIMAL(10,2),
    SoLuong INT
)

-- Bảng KhachHang_Voucher
CREATE TABLE KhachHang_Voucher (
    MaKhachHang char(5),
    MaVoucher char(5),
    soLuong INT,
    SoLuongDaDung INT,
    NgayNhan DATE,
    PRIMARY KEY (MaKhachHang, MaVoucher),
    FOREIGN KEY (MaKhachHang) REFERENCES khachhang(maKhachHang)
			on update
				cascade
			on delete
				cascade,
    FOREIGN KEY (MaVoucher) REFERENCES voucher(MaVoucher)
			on update
				cascade
			on delete
				cascade
)
-- Bảng Tour
CREATE TABLE Tour (
    maTour char(5) PRIMARY KEY,
    tenTour nVARCHAR(100),
    moTa nvarchar(1000),
    giaTour DECIMAL(10,2),
    tg_batDau DATE,
    tg_ketThuc DATE,
    soLuongMax INT,
	soLuongConLai int
)

-- Bảng NhanVien_Tour
CREATE TABLE NhanVien_Tour (
    maNhanVien char(5),
    maTour char(5),
    PRIMARY KEY (maNhanVien, maTour),
    FOREIGN KEY (maNhanVien) REFERENCES NhanVien(maNhanVien)
			on update
				cascade
			on delete
				cascade,
    FOREIGN KEY (maTour) REFERENCES Tour(maTour)
			on update
				cascade
			on delete
				cascade
)

-- Bảng DatVe
CREATE TABLE DatVe (
    MaDatVe char(5) PRIMARY KEY,
    maKhachHang char(5),
    NgayDat DATE,
    NgayDi DATE,
	TongTien DECIMAL(18,2) DEFAULT 0,
    PhuongThuc NVARCHAR(20) CHECK (PhuongThuc IN (N'Tiền mặt', N'Online')) DEFAULT N'Tiền mặt',
    FOREIGN KEY (MaKhachHang) REFERENCES KhachHang(maKhachHang)
			on update
				cascade
			on delete
				cascade
)

CREATE TABLE VeTour 
(
	MaVeTour CHAR(5) PRIMARY KEY,
    MaDatVe CHAR(5),
    MaTour CHAR(5),
    NguoiLon INT,
    TreEm INT,
    NguoiGia INT,
	TongTien DECIMAL(10,2),
    FOREIGN KEY (MaDatVe) REFERENCES DatVe(MaDatVe)
			on update
				cascade
			on delete
				cascade,
    FOREIGN KEY (maTour) REFERENCES Tour(maTour)
			on update
				cascade
			on delete
				cascade
)
create table datve_dichvu
(
	MaDatVe char(5),
	maDichVu char(5)
	PRIMARY KEY (MaDatVe, maDichVu),
    FOREIGN KEY (MaDatVe) REFERENCES DatVe(MaDatVe)
			on update
				cascade
			on delete
				cascade,
    FOREIGN KEY (maDichVu) REFERENCES dichvu(maDichVu)
			on update
				cascade
			on delete
				cascade
)
go
--rằng buộc
alter table khachhang
	add constraint CK_khachhang_SDT 
			check(SDT like '[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]'),
		constraint CK_KhachHang_Email
			check(email like '[A-Za-z]%@gmail.com')
-- Số lượng phải > 0
ALTER TABLE KhachHang_Voucher 
	ADD CONSTRAINT chk_KHVoucher_SoLuong 
			CHECK (soLuong >= 0 AND SoLuongDaDung >= 0 AND SoLuongDaDung <= SoLuong);
-- Bảng voucher
ALTER TABLE voucher 
ADD CONSTRAINT chk_Voucher_SoLuong CHECK (SoLuong >= 0);
-- Bảng NhanVien
ALTER TABLE NhanVien 
ADD CONSTRAINT chk_NhanVien_Luong CHECK (luong >= 0),
	CONSTRAINT CK_nhienvien_SDT 
			check(SDT like '[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]'),
	CONSTRAINT CK_NhanVien_Email
			check(email like '[A-Za-z]%@gmail.com')
-- Bảng TroChoi
ALTER TABLE TroChoi 
ADD CONSTRAINT chk_TroChoi_Tuoi CHECK (gioiHanDoTuoi >= 0),
    CONSTRAINT chk_TroChoi_SucChua CHECK (sucChua > 0);
-- Bảng SuKien
ALTER TABLE SuKien 
ADD CONSTRAINT chk_SuKien_Tuoi CHECK (gioiHanDoTuoi >= 0),
    CONSTRAINT chk_SuKien_SucChua CHECK (SucChua > 0),
    CONSTRAINT chk_SuKien_ThoiGian CHECK (TGianBatDau <= TgianKetThuc);

-- Bảng Tour
ALTER TABLE Tour 
ADD CONSTRAINT chk_Tour_SoLuongMax CHECK (soLuongMax > 0),
    CONSTRAINT chk_Tour_ThoiGian CHECK (tg_batDau <= tg_ketThuc);

go
--insert
INSERT INTO Khu 
	VALUES 
		('KH001', N'Khu A', N'Khu vui chơi'),
		('KH002', N'Khu B', N'Khu sự kiện'),
		('KH003', N'Khu C', N'Khu ẩm thực')

-- Insert into dichvu
INSERT INTO dichvu VALUES 
		('DV001', 'KH001', N'Trò chơi mạo hiểm', N'Khu trò chơi dành cho người ưa thích cảm giác mạnh'),
		('DV002', 'KH001', N'Trò chơi cho trẻ em', N'Khu vui chơi giải trí an toàn cho trẻ nhỏ'),
		('DV003', 'KH002', N'Sự kiện âm nhạc', N'Buổi biểu diễn ca nhạc tổ chức vào mỗi cuối tuần'),
		('DV004', 'KH002', N'Lễ hội ánh sáng', N'Sự kiện đặc biệt tổ chức vào buổi tối'),
		('DV005', 'KH003', N'Nhà hàng ẩm thực Việt', N'Phục vụ các món ăn truyền thống Việt Nam'),
		('DV006', 'KH003', N'Nhà hàng BBQ ngoài trời', N'Thu hút thực khách với các món nướng hấp dẫn'),
		('DV007', 'KH001', N'Tiệm đồ ăn nhanh', N'Bán thức ăn nhanh và nước uống mang đi'),
		('DV008', 'KH003', N'Quầy giải khát', N'Phục vụ nước uống và đồ ăn nhẹ'),
		('DV009', 'KH002', N'Phòng y tế', N'Hỗ trợ sơ cứu và chăm sóc y tế cơ bản cho khách'),
		('DV010', 'KH001', N'WiFi miễn phí', N'Kết nối internet miễn phí trong toàn khuôn viên')

SET DATEFORMAT dmy;
INSERT INTO TroChoi VALUES 
	('TC001', 'DV001', N'Tàu lượn', N'Tàu lượn siêu tốc', 12, 20, '01-01-2023'),
	('TC002', 'DV001', N'Đu quay', N'Đu quay khổng lồ', 6, 30, '01-01-2023'),
	('TC003', 'DV001', N'Thuyền ma', N'Trò chơi kinh dị', 16, 15, '01-01-2023'),
	('TC004', 'DV001', N'Xe điện', N'Xe điện đụng', 8, 25, '01-01-2023'),
	('TC005', 'DV001', N'Vòng xoay', N'Vòng xoay gia đình', 4, 40, '01-01-2023'),
	('TC006', 'DV001', N'Thác nước', N'Trò chơi nước', 10, 20, '01-01-2023'),
	('TC007', 'DV001', N'Khinh khí cầu', N'Khinh khí cầu mini', 8, 10, '01-01-2023'),
	('TC008', 'DV001', N'Tường leo', N'Leo núi trong nhà', 10, 15, '01-01-2023'),
	('TC009', 'DV001', N'Bắn súng', N'Bắn súng nước', 16, 20, '01-01-2023'),
	('TC010', 'DV001', N'Đua xe', N'Đua xe mô hình', 8, 12, '01-01-2023');


-- Insert into SuKien
SET DATEFORMAT dmy;
INSERT INTO SuKien VALUES 
	('SK001', 'DV004', N'Lễ hội ánh sáng', '01-12-2023', '31-12-2023', N'Hoạt động', N'Cao', 0, 500),
	('SK002', 'DV003', N'Ngày gia đình', '01-06-2023', '02-06-2023', N'Kết thúc', N'Trung bình', 0, 300),
	('SK003', 'DV004', N'Halloween', '31-10-2023', '31-10-2023', N'Chuẩn bị', N'Cao', 12, 200),
	('SK004', 'DV004', N'Tết thiếu nhi', '01-06-2023', '01-06-2023', N'Hoạt động', N'Cao', 0, 400),
	('SK005', 'DV004', N'Giáng sinh', '24-12-2023', '25-12-2023', N'Chuẩn bị', N'Cao', 0, 500),
	('SK006', 'DV003', N'Hội chợ ẩm thực', '15-05-2023', '17-05-2023', N'Kết thúc', N'Trung bình', 0, 300),
	('SK007', 'DV003', N'Biểu diễn xiếc', '01-04-2023', '03-04-2023', N'Hoạt động', N'Trung bình', 0, 200),
	('SK008', 'DV003', N'Triển lãm nghệ thuật', '01-03-2023', '10-03-2023', N'Kết thúc', N'Thấp', 0, 150),
	('SK009', 'DV004', N'Ngày Valentine', '14-02-2023', '14-02-2023', N'Kết thúc', N'Trung bình', 12, 200),
	('SK010', 'DV004', N'Lễ hội hoa', '08-03-2023', '09-03-2023', N'Hoạt động', N'Thấp', 0, 250);


-- Insert into NhaHang
SET DATEFORMAT dmy;
INSERT INTO NhaHang VALUES 
	('NH001', 'DV005', '01-01-2023', '01-01-2023', N'Nhà hàng gia đình'),
	('NH002', 'DV006', '01-01-2023', '01-01-2023', N'Nhà hàng buffet'),
	('NH003', 'DV007', '01-01-2023', '01-01-2023', N'Quán ăn nhanh'),
	('NH004', 'DV008', '01-01-2023', '01-01-2023', N'Quán cà phê'),
	('NH005', 'DV009', '01-01-2023', '01-01-2023', N'Nhà hàng hải sản'),
	('NH006', 'DV010', '01-01-2023', '01-01-2023', N'Nhà hàng chay'),
	('NH007', 'DV007', '01-01-2023', '01-01-2023', N'Quán bar'),
	('NH008', 'DV010', '01-01-2023', '01-01-2023', N'Nhà hàng Âu'),
	('NH009', 'DV001', '01-01-2023', '01-01-2023', N'Nhà hàng Á'),
	('NH010', 'DV004', '01-01-2023', '01-01-2023', N'Nhà hàng Việt');


-- Insert into ChucVu
INSERT INTO ChucVu VALUES 
		('CV001', N'Quản lý'),
		('CV002', N'Nhân viên bán vé'),
		('CV003', N'Nhân viên an ninh'),
		('CV004', N'Nhân viên vệ sinh'),
		('CV005', N'Hướng dẫn viên'),
		('CV006', N'Nhân viên kỹ thuật'),
		('CV007', N'Nhân viên cứu hộ'),
		('CV008', N'Nhân viên bếp'),
		('CV009', N'Nhân viên phục vụ'),
		('CV010', N'Nhân viên bảo trì');

-- Insert into NhanVien
SET DATEFORMAT dmy;
INSERT INTO NhanVien VALUES 
		('NV001', N'Nguyễn Văn A', '01-01-1990', '0901234567', 'nva@gmail.com', '01-01-2020', 10000000),
		('NV002', N'Trần Thị B', '02-02-1992', '0902345678', 'ttb@gmail.com', '01-02-2020', 8000000),
		('NV003', N'Lê Văn C', '03-03-1988', '0903456789', 'lvc@gmail.com', '01-05-2019', 12000000),
		('NV004', N'Phạm Thị D', '04-04-1995', '0904567890', 'ptd@gmail.com', '01-01-2021', 7000000),
		('NV005', N'Hoàng Văn E', '05-05-1993', '0905678901', 'hve@gmail.com', '01-07-2020', 9000000),
		('NV006', N'Vũ Thị F', '06-06-1991', '0906789012', 'vtf@gmail.com', '01-11-2019', 8500000),
		('NV007', N'Đặng Văn G', '07-07-1989', '0907890123', 'dvg@gmail.com', '01-03-2018', 11000000),
		('NV008', N'Bùi Thị H', '08-08-1994', '0908901234', 'bth@gmail.com', '01-02-2021', 7500000),
		('NV009', N'Ngô Văn I', '09-09-1990', '0909012345', 'nvi@gmail.com', '01-05-2020', 9500000),
		('NV010', N'Đỗ Thị K', '10-10-1992', '0900123456', 'dtk@gmail.com', '01-03-2021', 8200000)

SET DATEFORMAT dmy;
INSERT INTO nhanvien_chucvu VALUES 
		('NV001', 'CV001', '01-01-2020', '31-12-2022'),
		('NV002', 'CV002', '15-03-2020', '30-06-2023'),
		('NV003', 'CV003', '01-07-2021', NULL),
		('NV004', 'CV004', '01-01-2022', NULL),
		('NV005', 'CV001', '01-05-2019', '31-12-2021'),
		('NV006', 'CV002', '01-01-2023', NULL),
		('NV007', 'CV005', '01-08-2022', NULL),
		('NV008', 'CV004', '15-09-2021', NULL),
		('NV009', 'CV003', '01-04-2020', '31-10-2022'),
		('NV010', 'CV002', '01-11-2021', NULL)

SET DATEFORMAT dmy;
INSERT INTO calamViec VALUES 
    ('CA001', N'Ca sáng', '07:00:00', '11:00:00'),
    ('CA002', N'Ca chiều', '13:00:00', '17:00:00'),
    ('CA003', N'Ca tối', '18:00:00', '22:00:00'),
    ('CA004', N'Ca linh hoạt 1', '10:00:00', '14:00:00'),
    ('CA005', N'Ca linh hoạt 2', '15:00:00', '19:00:00');

SET DATEFORMAT dmy;
INSERT INTO phanCongCa VALUES 
		('NV001', 'CA001', '01-04-2025'),
		('NV002', 'CA002', '01-04-2025'),
		('NV003', 'CA003', '01-04-2025'),
		('NV004', 'CA001', '01-04-2025'),
		('NV005', 'CA002', '01-04-2025'),
		('NV006', 'CA003', '01-04-2025'),
		('NV007', 'CA001', '01-04-2025'),
		('NV008', 'CA002', '01-04-2025'),
		('NV009', 'CA003', '01-04-2025'),
		('NV010', 'CA001', '01-04-2025')

INSERT INTO nhanvien_dichvu VALUES 
		('NV001', 'DV001'),
		('NV002', 'DV002'),
		('NV003', 'DV003'),
		('NV004', 'DV001'),
		('NV005', 'DV004'),
		('NV006', 'DV005'),
		('NV007', 'DV001'),
		('NV008', 'DV002'),
		('NV009', 'DV003'),
		('NV010', 'DV004')

SET DATEFORMAT dmy
INSERT INTO khachhang 
VALUES 
    ('KH001', N'Nguyễn Văn A', 'matkhau123', '0901234567', 'a@gmail.com', N'123 Lê Lợi', N'Nam', '01/01/1990', N'Việt Nam'),
    ('KH002', N'Trần Thị B', 'matkhau345', '0912345678', 'b@gmail.com', N'234 Trần Hưng Đạo', N'Nữ', '02/02/1991', N'Việt Nam'),
    ('KH003', N'Lê Văn C', 'matkhau723', '0923456789', 'c@gmail.com', N'345 Nguyễn Huệ', N'Nam', '03/03/1992', N'Việt Nam'),
    ('KH004', N'Phạm Thị D', 'matkhfsd123', '0934567890', 'd@gmail.com', N'456 Hai Bà Trưng', N'Nữ', '04/04/1993', N'Việt Nam'),
    ('KH005', N'Hoàng Văn E', 'matkhdfsgdsfu123', '0945678901', 'e@gmail.com', N'567 Lý Thường Kiệt', N'Nam', '05/05/1994', N'Việt Nam'),
    ('KH006', N'Đỗ Thị F', 'matgsdhau123', '0957890012', 'f@gmail.com', N'678 Nguyễn Trãi', N'Nữ', '06/06/1995', N'Việt Nam'),
    ('KH007', N'Bùi Văn G', 'matkhdfgsau123', '0967890123', 'g@gmail.com', N'789 Phan Đình Phùng', N'Nam', '07/07/1996', N'Việt Nam'),
    ('KH008', N'Vũ Thị H', 'matkh21au123', '0978901234', 'h@gmail.com', N'890 Trường Chinh', N'Nữ', '08/08/1997', N'Việt Nam'),
    ('KH009', N'Tô Văn I', 'matkha2234u123', '0989012345', 'i@gmail.com', N'901 Cách Mạng Tháng 8', N'Nam', '09/09/1998', N'Việt Nam'),
    ('KH010', N'Phan Thị J', 'matkxchdhau123', '0990123456', 'j@gmail.com', N'012 Điện Biên Phủ', N'Nữ', '10/10/1999', N'Việt Nam');


SET DATEFORMAT dmy
INSERT INTO voucher VALUES 
		('VC001', N'Giảm 10%', '01-01-2025', '30-06-2025', 10.00, 100),
		('VC002', N'Giảm 20%', '01-03-2025', '31-08-2025', 20.00, 50),
		('VC003', N'Miễn phí vé vào cổng', '01-04-2025', '30-04-2025', 100.00, 30),
		('VC004', N'Tặng kèm đồ uống', '15-01-2025', '15-07-2025', 5.00, 200),
		('VC005', N'Combo trò chơi', '10-02-2025', '10-09-2025', 15.00, 150),
		('VC006', N'Giảm 5% cho nhóm 3 người', '01-05-2025', '01-08-2025', 5.00, 80),
		('VC007', N'Ưu đãi thành viên mới', '01-04-2025', '31-12-2025', 12.50, 70),
		('VC008', N'Tặng vé buffet trưa', '20-03-2025', '20-06-2025', 50.00, 60),
		('VC009', N'Combo gia đình', '01-02-2025', '31-07-2025', 25.00, 40),
		('VC010', N'Miễn phí gửi xe', '01-01-2025', '31-12-2025', 3.00, 500)

SET DATEFORMAT dmy
INSERT INTO KhachHang_Voucher VALUES
		('KH001', 'VC001', 3, 1, '01-06-2024'),
		('KH002', 'VC002', 2, 0, '01-07-2024'),
		('KH003', 'VC003', 5, 2, '15-08-2024'),
		('KH004', 'VC004', 1, 0, '05-09-2024'),
		('KH005', 'VC005', 4, 1, '12-10-2024'),
		('KH006', 'VC006', 3, 2, '03-11-2024'),
		('KH007', 'VC007', 2, 1, '25-11-2024'),
		('KH008', 'VC008', 1, 0, '15-12-2024'),
		('KH009', 'VC009', 2, 0, '10-01-2025'),
		('KH010', 'VC010', 3, 1, '01-02-2025')

SET DATEFORMAT dmy
INSERT INTO Tour VALUES
-- Vé đi thuyền thiên nga
('TO001', N'Vé đi thuyền thiên nga', 
 N'► Vé đi thuyền thiên nga dành cho 01 người lớn hoặc trẻ em. Vé chỉ có hiệu lực trong ngày ghi trên vé. Bao gồm: áo phao, hướng dẫn an toàn. Không áp dụng cho trẻ em dưới 3 tuổi không có người lớn đi cùng', 
 800000, '10-05-2025', '12-09-2026', 40, 40),

-- Vé tham quan khu khủng long 4D
('TO002', N'Vé tham quan khu khủng long 4D', 
 N'► Vé tham quan khu trưng bày mô hình khủng long kết hợp hiệu ứng âm thanh và chuyển động 4D. Trẻ em dưới 1m miễn phí. Vé có hiệu lực 1 lần trong ngày. Mỗi suất tham quan kéo dài khoảng 30 phút', 
 120000, '10-05-2025', '06-05-2026', 50, 50),

-- Vé trải nghiệm thực tế ảo (VR)
('TO003', N'Vé trải nghiệm thực tế ảo (VR)', 
 N'► Truy cập 5 khu vực trò chơi VR cao cấp: đua xe, phi hành gia, zombie, cảm giác mạnh, vũ trụ. Trẻ em từ 7 tuổi trở lên mới được tham gia. Vé có hiệu lực trong ngày, giới hạn 1 lần mỗi trò chơi. Miễn phí mượn kính VR và tay cầm', 
 150000, '10-05-2025', '10-08-2026', 30, 30),

-- Vé xem biểu diễn nhạc nước
('TO004', N'Vé xem biểu diễn nhạc nước', 
 N'► Vé xem chương trình nhạc nước kéo dài 25 phút tại quảng trường trung tâm. Chỗ ngồi khu VIP gần sân khấu. Có hiệu lực đúng giờ ghi trên vé, không hoàn/trả nếu đến muộn. Miễn phí cho trẻ em dưới 90cm', 
 500000, '10-05-2025', '11-09-2026', 100, 100),

-- Combo vui chơi trẻ em toàn khu
('TO005', N'Combo vui chơi trẻ em toàn khu', 
 N'► Vé trọn gói cho trẻ em bao gồm: nhà banh, cầu trượt, xe điện đụng, tô tượng, lớp vẽ. Miễn phí nước suối 1 chai, mượn đồng phục chơi. Trẻ dưới 3 tuổi phải có người lớn đi kèm. Vé có giá trị sử dụng trong 1 ngày', 
 180000, '10-05-2025', '02-06-2026', 60, 60),

 ('TO006', N'Vé đi tàu cổ tích xuyên rừng', 
 N'► Hành trình tàu đi qua các mô hình rừng cổ tích, động vật hoạt hình, và cảnh đêm phát sáng. Dành cho mọi lứa tuổi, trẻ em dưới 6 tuổi cần người lớn đi kèm. Vé có hiệu lực trong ngày ghi trên vé. Miễn phí ảnh lưu niệm 1 tấm/khách', 
 90000, '10-05-2025', '01-10-2026', 40, 40),

-- Vé khu Game Center (máy chơi điện tử)
('TO007', N'Vé khu Game Center', 
 N'► Bao gồm 10 lượt chơi máy điện tử tự chọn: bắn súng, đua xe, nhảy nhạc, gắp thú, bắn bóng\n► Không áp dụng đổi trò chơi giữa chừng. Có hiệu lực trong ngày, không hoàn trả nếu không sử dụng hết lượt\n► Tặng 1 món quà bất ngờ nếu tích lũy đủ điểm', 
 130000, '07-05-2025', '07-09-2026', 50, 50),

-- Vé tham quan thủy cung mini
('TO008', N'Vé tham quan thuỷ cung mini', 
 N'► Tham quan hệ sinh thái biển thu nhỏ, cá mập con, cá hề, san hô sống, rùa nước. Có khu vực chạm tay tương tác (touch tank). Vé có hiệu lực trong 1 ngày, không giới hạn thời gian tham quan. Miễn phí hướng dẫn viên nếu đi nhóm trên 10 người', 
 140000, '10-05-2025', '10-05-2026', 35, 35),

-- Vé combo người lớn (trọn gói toàn khu)
('TO009', N'Combo vui chơi người lớn', 
 N'► Bao gồm: vé khu VR, khủng long 4D, tàu cổ tích, biểu diễn nhạc nước. Tặng kèm voucher ăn uống 50k tại khu ẩm thực. Sử dụng trong ngày, mỗi dịch vụ 1 lần. Áp dụng cho khách từ 16 tuổi trở lên', 
 250000, '10-05-2025', '10-08-2026', 45, 45),

-- Vé đêm lửa trại & pháo hoa
('TO010', N'Vé đêm lửa trại & pháo hoa', 
 N'► Vé vào khu vực tổ chức đêm lửa trại, chơi trò chơi dân gian, thưởng thức tiệc nhẹ\n► Bao gồm suất ngồi xem pháo hoa tại bãi cỏ trung tâm\n► Không hoàn lại nếu trời mưa nhưng được đổi vé sang hôm khác\n► Số lượng giới hạn 1 ngày chỉ 100 vé', 
 460000, '10-05-2025', '10-04-2026', 100, 100);


INSERT INTO NhanVien_Tour VALUES
		('NV001', 'TO001'),
		('NV002', 'TO002'),
		('NV003', 'TO003'),
		('NV004', 'TO004'),
		('NV005', 'TO005'),
		('NV006', 'TO006'),
		('NV007', 'TO007'),
		('NV008', 'TO008'),
		('NV009', 'TO009'),
		('NV010', 'TO010')

SET DATEFORMAT dmy;
INSERT INTO DatVe (MaDatVe, maKhachHang, NgayDat, NgayDi, TongTien, PhuongThuc) VALUES
    ('DV001', 'KH001', '01-04-2025', '10-04-2025', 2240000.00, N'Tiền mặt'),
    ('DV002', 'KH002', '02-04-2025', '12-04-2025', 444000.00, N'Online'),
    ('DV003', 'KH003', '03-04-2025', '20-04-2025', 150000.00, N'Tiền mặt'),
    ('DV004', 'KH004', '04-04-2025', '15-04-2025', 1800000.00, N'Online'),
    ('DV005', 'KH005', '05-04-2025', '18-04-2025', 486000.00, N'Tiền mặt'),
    ('DV006', 'KH006', '06-04-2025', '25-04-2025', 162000.00, N'Online'),
    ('DV007', 'KH007', '07-04-2025', '22-04-2025', 702000.00, N'Tiền mặt'),
    ('DV008', 'KH008', '08-04-2025', '28-04-2025', 770000.00, N'Online'),
    ('DV009', 'KH009', '09-04-2025', '30-04-2025', 1900000.00, N'Tiền mặt'),
    ('DV010', 'KH010', '10-04-2025', '26-04-2025', 2070000.00, N'Online');


SET DATEFORMAT dmy;
INSERT INTO VeTour (MaVeTour, MaDatVe, MaTour, NguoiLon, TreEm, NguoiGia, TongTien)
VALUES
    ('VT001', 'DV001', 'TO001', 2, 1, 0, 2240000.00),
    ('VT002', 'DV002', 'TO002', 3, 0, 1, 444000.00),
    ('VT003', 'DV003', 'TO003', 1, 0, 0, 150000.00),
    ('VT004', 'DV004', 'TO004', 2, 2, 0, 1800000.00),
    ('VT005', 'DV005', 'TO005', 2, 0, 1, 486000.00),
    ('VT006', 'DV006', 'TO006', 1, 1, 0, 162000.00),
    ('VT007', 'DV007', 'TO007', 4, 0, 2, 702000.00),
    ('VT008', 'DV008', 'TO008', 4, 1, 1, 770000.00),
    ('VT009', 'DV009', 'TO009', 6, 2, 0, 1900000.00),
    ('VT010', 'DV010', 'TO010', 3, 1, 1, 2070000.00);
	

INSERT INTO datve_dichvu VALUES
		('DV001', 'DV001'),
		('DV002', 'DV002'),
		('DV003', 'DV003'),
		('DV004', 'DV004'),
		('DV005', 'DV005'),
		('DV006', 'DV006'),
		('DV007', 'DV007'),
		('DV008', 'DV008'),
		('DV009', 'DV009'),
		('DV010', 'DV010')
--Danh sách khách hàng cùng số voucher còn lại
SELECT KH.maKhachHang, KH.tenKhachHang, V.tenVoucher, KHV.soLuong - KHV.SoLuongDaDung AS SoLuongConLai
FROM KhachHang KH
JOIN KhachHang_Voucher KHV ON KH.maKhachHang = KHV.maKhachHang
JOIN Voucher V ON KHV.MaVoucher = V.MaVoucher
ORDER BY SoLuongConLai DESC


--Danh sách nhân viên, chức vụ hiện tại
SELECT NV.maNhanVien, NV.tenNhanVien, CV.tenChuCVu, NCV.tgian_batdau, NCV.tgian_ketthuc
FROM NhanVien NV
JOIN nhanvien_chucvu NCV ON NV.maNhanVien = NCV.maNhanVien
JOIN ChucVu CV ON NCV.maChucVu = CV.maChucVu
WHERE NCV.tgian_ketthuc IS NULL OR NCV.tgian_ketthuc >= GETDATE()

--Danh sách dịch vụ theo khu vực
SELECT DV.maDichVu, DV.tenDichVu, DV.mota, K.tenKhu
FROM dichvu DV
JOIN Khu K ON DV.makhu = K.maKhu
--Các trò chơi có giới hạn độ tuổi < 10
SELECT * FROM TroChoi
WHERE gioiHanDoTuoi < 10
--Các voucher còn hiệu lực
SELECT * FROM Voucher
WHERE TGianHieuLuc <= GETDATE() AND TGianKetThuc >= GETDATE()
-- Xem giới hạn tour
SELECT soLuongMax FROM Tour WHERE maTour = 'TO001'
GO
CREATE TRIGGER trg_KiemTraNgayDatTour
ON VeTour
AFTER INSERT
AS
BEGIN
    IF EXISTS (
        SELECT 1
        FROM Inserted I
        JOIN DatVe DV ON I.MaDatVe = DV.MaDatVe
        JOIN Tour T ON I.maTour = T.maTour
        WHERE DV.NgayDi > T.tg_ketThuc
    )
    BEGIN
        RAISERROR(N'Không thể đặt vé cho tour đã kết thúc!', 16, 1)
        ROLLBACK
    END
END
go
-- Bước 1: Tạo tour TO005 có tg_ketThuc trước ngày muốn đặt
--INSERT INTO Tour VALUES ('TO999', N'Tour miền Trung', N'Tour Đà Nẵng - Huế', 3000000, '2025-04-01', '2025-04-10', 30)
-- Bước 2: Tạo đặt vé với ngày đi vượt qua ngày kết thúc tour
--INSERT INTO DatVe VALUES ('DV999', 'KH002', '2025-04-13', N'Người lớn', 1 , '2025-04-13')
-- Bước 3: Thêm VeTour sẽ bị lỗi
--INSERT INTO VeTour VALUES ('VT999', 'DV999', 'TO999', N'Đà Nẵng', N'Xe khách', '2025-04-13')
go
--tự động cập nhật số lượng vé còn lại khi có người mua vé
--CREATE TRIGGER UpdateSoLuongConLai
--ON VeTour
--AFTER INSERT, DELETE
--AS
--BEGIN
    --DECLARE @maTour VARCHAR(10);
    --DECLARE @soLuongNguoi INT;

    -- Lấy thông tin từ bảng VeTour
    --SELECT @maTour = maTour, @soLuongNguoi = soLuongNguoi FROM inserted;

    -- Cập nhật số lượng vé còn lại trong bảng Tour
    --UPDATE Tour
    --SET soLuongConLai = soLuongConLai - @soLuongNguoi
    --WHERE maTour = @maTour;

    -- Nếu xóa, giảm lại số lượng
    --IF EXISTS (SELECT * FROM deleted)
    --BEGIN
        --SELECT @soLuongNguoi = soLuongNguoi FROM deleted;
        --UPDATE Tour
        --SET soLuongConLai = soLuongConLai + @soLuongNguoi
        --WHERE maTour = @maTour;
    --END
--END
go
CREATE TRIGGER CalculateTongTien
ON VeTour
AFTER INSERT
AS
BEGIN
    -- Cập nhật TongTien cho các dòng vừa chèn
    UPDATE VeTour
    SET TongTien = (
        i.NguoiLon * t.giaTour + 
        i.TreEm * t.giaTour * 0.8 + 
        i.NguoiGia * t.giaTour * 0.7
    )
    FROM VeTour v
    INNER JOIN inserted i ON v.MaVeTour = i.MaVeTour
    INNER JOIN Tour t ON i.MaTour = t.maTour;
END;
select * from Tour
select * from khachhang
select * from datve
select * from vetour
