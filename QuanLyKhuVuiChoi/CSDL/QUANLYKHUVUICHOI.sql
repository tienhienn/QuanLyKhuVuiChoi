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
	loaiNhaHang nvarchar(50)
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
create table calamViec
(
	maCa char(5) primary key,
	tenca nvarchar(50),
	gio_batdau date,
	gio_ketthuc date
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
    email VARCHAR(100) unique
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
    moTa nvarchar(100),
    giaTour DECIMAL(10,2),
    tg_batDau DATE,
    tg_ketThuc DATE,
    soLuongMax INT
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
    LoaiVe nVARCHAR(50),
    SoLuong INT,
    NgayDi DATE,
    FOREIGN KEY (MaKhachHang) REFERENCES KhachHang(maKhachHang)
			on update
				cascade
			on delete
				cascade
)

CREATE TABLE VeTour 
(
	Mavetour char(5) primary key,
    MaDatVe char(5),
    maTour char(5),
    thoiGian date,
    PhuongTien nVARCHAR(100),
    GioDi time,
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
create table vele
(
	maVele char(5) primary key,
	MaDatVe char(5)
	FOREIGN KEY (MaDatVe) REFERENCES DatVe(MaDatVe)
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
-- Bảng DatVe
ALTER TABLE DatVe 
ADD CONSTRAINT chk_DatVe_SoLuong CHECK (SoLuong > 0),
    CONSTRAINT chk_DatVe_Ngay CHECK (NgayDat <= NgayDi);
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
		('CA001', N'Ca sáng', '01-01-2025', '01-01-2025'),
		('CA002', N'Ca chiều', '01-01-2025', '01-01-2025'),
		('CA003', N'Ca tối', '01-01-2025', '01-01-2025'),
		('CA004', N'Ca linh hoạt 1', '01-01-2025', '01-01-2025'),
		('CA005', N'Ca linh hoạt 2', '01-01-2025', '01-01-2025')

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
		('KH001', N'Nguyễn Văn A','matkhau123', '0901234567', 'a@gmail.com'),
		('KH002', N'Trần Thị B','matkhau345', '0912345678', 'b@gmail.com'),
		('KH003', N'Lê Văn C','matkhau723', '0923456789', 'c@gmail.com'),
		('KH004', N'Phạm Thị D','matkhfsd123', '0934567890', 'd@gmail.com'),
		('KH005', N'Hoàng Văn E','matkhdfsgdsfu123', '0945678901', 'e@gmail.com'),
		('KH006', N'Đỗ Thị F','matgsdhau123', '0957890012', 'f@gmail.com'),
		('KH007', N'Bùi Văn G','matkhdfgsau123', '0967890123', 'g@gmail.com'),
		('KH008', N'Vũ Thị H','matkh21au123', '0978901234', 'h@gmail.com'),
		('KH009', N'Tô Văn I','matkha2234u123', '0989012345', 'i@gmail.com'),
		('KH010', N'Phan Thị J','matkxchdhau123', '0990123456', 'j@gmail.com')

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
		('TO001', N'Tour Hà Nội', N'Tham quan phố cổ', 1000.00, '10-04-2025', '15-04-2025', 30),
		('TO002', N'Tour Hạ Long', N'Du lịch vịnh biển', 1200.00, '12-04-2025', '18-04-2025', 40),
		('TO003', N'Tour Huế', N'Thăm lăng tẩm', 900.00, '20-04-2025', '25-04-2025', 25),
		('TO004', N'Tour Đà Nẵng', N'Biển Mỹ Khê', 1100.00, '15-04-2025', '20-04-2025', 35),
		('TO005', N'Tour Hội An', N'Phố cổ đèn lồng', 950.00, '01-04-2025', '10-04-2025', 20),
		('TO006', N'Tour Nha Trang', N'Thăm đảo Vinpearl', 1150.00, '25-04-2025', '30-04-2025', 30),
		('TO007', N'Tour Đà Lạt', N'Khí hậu mát mẻ', 1050.00, '22-04-2025', '27-04-2025', 30),
		('TO008', N'Tour Cần Thơ', N'Chợ nổi Cái Răng', 980.00, '28-04-2025', '02-05-2025', 25),
		('TO009', N'Tour Phú Quốc', N'Tắm biển nghỉ dưỡng', 1300.00, '30-04-2025', '05-05-2025', 40),
		('TO010', N'Tour Sapa', N'Leo Fansipan', 1020.00, '26-04-2025', '01-05-2025', 30)

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

SET DATEFORMAT dmy
INSERT INTO DatVe VALUES
		('DV001', 'KH001', '01-04-2025', N'Người lớn', 2, '10-04-2025'),
		('DV002', 'KH002', '02-04-2025', N'Trẻ em', 1, '12-04-2025'),
		('DV003', 'KH003', '03-04-2025', N'Người lớn', 3, '20-04-2025'),
		('DV004', 'KH004', '04-04-2025', N'Trẻ em', 1, '15-04-2025'),
		('DV005', 'KH005', '05-04-2025', N'Người lớn', 2, '18-04-2025'),
		('DV006', 'KH006', '06-04-2025', N'Người lớn', 1, '25-04-2025'),
		('DV007', 'KH007', '07-04-2025', N'Người lớn', 4, '22-04-2025'),
		('DV008', 'KH008', '08-04-2025', N'Người lớn', 2, '28-04-2025'),
		('DV009', 'KH009', '09-04-2025', N'Trẻ em', 2, '30-04-2025'),
		('DV010', 'KH010', '10-04-2025', N'Người lớn', 3, '26-04-2025')

SET DATEFORMAT dmy;
INSERT INTO VeTour VALUES
	('VT001', 'DV001', 'TO001', '10-04-2025', N'Xe buýt', '08:00:00'),
	('VT002', 'DV002', 'TO002', '12-04-2025', N'Tàu hỏa', '09:00:00'),
	('VT003', 'DV003', 'TO003', '20-04-2025', N'Máy bay', '07:30:00'),
	('VT004', 'DV004', 'TO004', '15-04-2025', N'Xe khách', '06:45:00'),
	('VT005', 'DV005', 'TO005', '18-04-2025', N'Xe buýt', '10:00:00'),
	('VT006', 'DV006', 'TO006', '25-04-2025', N'Máy bay', '11:00:00'),
	('VT007', 'DV007', 'TO007', '22-04-2025', N'Xe khách', '08:15:00'),
	('VT008', 'DV008', 'TO008', '28-04-2025', N'Tàu thủy', '09:45:00'),
	('VT009', 'DV009', 'TO009', '30-04-2025', N'Máy bay', '07:00:00'),
	('VT010', 'DV010', 'TO010', '26-04-2025', N'Tàu hỏa', '06:30:00');


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
INSERT INTO vele VALUES
		('VL001', 'DV001'),
		('VL002', 'DV002'),
		('VL003', 'DV003'),
		('VL004', 'DV004'),
		('VL005', 'DV005'),
		('VL006', 'DV006'),
		('VL007', 'DV007'),
		('VL008', 'DV008'),
		('VL009', 'DV009'),
		('VL010', 'DV010')

-- Danh sách các tour sắp diễn ra
SELECT * FROM Tour
WHERE tg_batDau >= GETDATE()
ORDER BY tg_batDau

--Thống kê số lượng vé đã đặt theo tour
SELECT T.maTour, T.tenTour, SUM(DV.SoLuong) AS TongSoLuongDat
FROM Tour T
JOIN VeTour VT ON T.maTour = VT.maTour
JOIN DatVe DV ON VT.MaDatVe = DV.MaDatVe
GROUP BY T.maTour, T.tenTour
ORDER BY TongSoLuongDat DESC

--Danh sách khách hàng cùng số voucher còn lại
SELECT KH.maKhachHang, KH.tenKhachHang, V.tenVoucher, KHV.soLuong - KHV.SoLuongDaDung AS SoLuongConLai
FROM KhachHang KH
JOIN KhachHang_Voucher KHV ON KH.maKhachHang = KHV.maKhachHang
JOIN Voucher V ON KHV.MaVoucher = V.MaVoucher
ORDER BY SoLuongConLai DESC

--Chi tiết đặt vé của 1 khách hàng
SELECT KH.tenKhachHang, DV.MaDatVe, DV.LoaiVe, DV.SoLuong, DV.NgayDi, T.tenTour
FROM KhachHang KH
JOIN DatVe DV ON KH.maKhachHang = DV.maKhachHang
JOIN VeTour VT ON DV.MaDatVe = VT.MaDatVe
JOIN Tour T ON VT.maTour = T.maTour
WHERE KH.maKhachHang = 'KH001'

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

--Top 5 tour có nhiều người đặt nhất
SELECT TOP 5 T.maTour, T.tenTour, SUM(DV.SoLuong) AS TongSoLuong
FROM Tour T
JOIN VeTour VT ON T.maTour = VT.maTour
JOIN DatVe DV ON DV.MaDatVe = VT.MaDatVe
GROUP BY T.maTour, T.tenTour
ORDER BY TongSoLuong DESC

--Trigger kiểm tra số lượng khách không vượt quá số lượng tour cho phép
-- Xem giới hạn tour
SELECT soLuongMax FROM Tour WHERE maTour = 'TO001'
-- Xem tổng vé đã đặt
SELECT T.maTour, SUM(DV.SoLuong) AS TongSoLuongDat
FROM Tour T
JOIN VeTour VT ON T.maTour = VT.maTour
JOIN DatVe DV ON VT.MaDatVe = DV.MaDatVe
WHERE T.maTour = 'TO001'
GROUP BY T.maTour
go

CREATE TRIGGER trg_KiemTraSoLuongVeTour
ON VeTour
AFTER INSERT
AS
BEGIN
    SET NOCOUNT ON

    IF EXISTS (
        SELECT 1
        FROM (
            SELECT VT.maTour, SUM(DV.SoLuong) AS soLuongMoi
            FROM Inserted VT
            JOIN DatVe DV ON VT.MaDatVe = DV.MaDatVe
            GROUP BY VT.maTour
        ) AS Moi
        JOIN (
            SELECT VT.maTour, SUM(DV.SoLuong) AS soLuongDaCo
            FROM VeTour VT
            JOIN DatVe DV ON VT.MaDatVe = DV.MaDatVe
            GROUP BY VT.maTour
        ) AS DaCo ON Moi.maTour = DaCo.maTour
        JOIN Tour T ON T.maTour = Moi.maTour
        WHERE DaCo.soLuongDaCo + Moi.soLuongMoi > T.soLuongMax
    )
    BEGIN
        RAISERROR(N'Số lượng vé đặt vượt quá giới hạn của tour!', 16, 1)
        ROLLBACK
    END
END
-- Giả sử KH001 đã có rồi
-- Chọn mã DatVe mới (ví dụ chưa dùng: DV011)
go -- Đặt quá số lượng max
INSERT INTO DatVe VALUES ('DV019', 'KH001', '13-04-2005', N'Người lớn', 10 , '15-04-2005')

-- Gắn DatVe mới này vào tour TO001
INSERT INTO VeTour
VALUES 	('VT011', 'DV019', 'TO008', '04-04-2025', N'Tàu hỏa', '06:15:00');
-- Xem giới hạn tour
SELECT soLuongMax FROM Tour WHERE maTour = 'TO001'
-- Xem tổng vé đã đặt
SELECT T.maTour, SUM(DV.SoLuong) AS TongSoLuongDat
FROM Tour T
JOIN VeTour VT ON T.maTour = VT.maTour
JOIN DatVe DV ON VT.MaDatVe = DV.MaDatVe
WHERE T.maTour = 'TO001'
GROUP BY T.maTour
--Cập nhật số lượng sử dụng vocher của từng khách hàng
go
CREATE TRIGGER trg_CapNhatSoLuongVoucher
ON DatVe
AFTER INSERT
AS
BEGIN
    SET NOCOUNT ON;

    UPDATE KV
    SET SoLuongDaDung = 
        CASE 
            WHEN KV.SoLuong - KV.SoLuongDaDung >= I.SoLuong 
                THEN KV.SoLuongDaDung + I.SoLuong
            ELSE KV.SoLuong  -- chỉ cộng tới mức tối đa
        END
    FROM Inserted I
    JOIN KhachHang_Voucher KV ON KV.MaKhachHang = I.MaKhachHang
    WHERE KV.SoLuong > KV.SoLuongDaDung
END
--Đặt quá số lượng vé thì nó cộng cộng lên số lượng vc đã sử dụng của khách hàng đó
go
INSERT INTO DatVe VALUES ('DV020', 'KH001', '2025-04-13', N'Trẻ em', 6, '2025-04-20')
go
--Khi khách hàng đặt quá số lượng vocher đã cho phép thì sẽ thông báo
CREATE TRIGGER trg_KiemTraVoucherDatVe
ON DatVe
AFTER INSERT
AS
BEGIN
    SET NOCOUNT ON;

    IF EXISTS (
        SELECT 1
        FROM Inserted I
        JOIN KhachHang_Voucher KV ON I.MaKhachHang = KV.MaKhachHang
        WHERE I.SoLuong > (KV.SoLuong - KV.SoLuongDaDung)
    )
    BEGIN
        RAISERROR(N'Khách hàng đã dùng vượt quá số lượng voucher cho phép!', 16, 1)
        ROLLBACK;
    END
END
go
--INSERT INTO DatVe VALUES ('DV025', 'KH001', '2025-04-13', N'Người lớn', 2, '2025-04-20')
go
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

