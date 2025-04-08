-- Xóa cơ sở dữ liệu nếu đã tồn tại
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
create table TroChoi
(
	maTroChoi char(5) primary key,
	tenTrochoi nvarchar(50),
	mota nvarchar(100),
	gioiHanDoTuoi int,
	sucChua int,
	hoiGianHoatDong Date,
)
create table SuKien
(	
	masukien char(5) primary key,
	tenSuKien nvarchar(50),
	TGianBatDau date,
	TgianKetThuc date,
	TrangThaiHoatDong nvarchar(10),
	mucDoCuonHut nvarchar(50),
	gioiHanDoTuoi int,
	SucChua int,
)
create table NhaHang
(
	maNhaHang char(5) primary key,
	TGianMoCua date,
	TgianDongCua date,
	loaiNhaHang nvarchar(50),
)
create table dichvu
(
	maDichVu char(5) primary key,
	makhu char(5),
	tenDichVu nvarchar(50),
	mota nvarchar(100)
	Foreign key (makhu) references Khu(makhu)
		on update 
			cascade 
		on delete 
			cascade
)
create table ChucVu
(
	maChucVu char(5) primary key,
	tenChuCVu nvarchar(50)
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
	gio_ketthuc date,
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
    ngaySinh DATE,
    gioiTinh nVARCHAR(10),
    diaChi nVARCHAR(255),
    SDT VARCHAR(10),
    email VARCHAR(100)
);

-- Bảng Voucher
CREATE TABLE voucher (
    MaVoucher char(5) PRIMARY KEY,
    tenVoucher VARCHAR(100),
    TGianHieuLuc DATE,
    TGianKetThuc DATE,
    GiaTriUuDai DECIMAL(10,2),
    SoLuong INT
);

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
);
-- Bảng Tour
CREATE TABLE Tour (
    maTour char(5) PRIMARY KEY,
    tenTour nVARCHAR(100),
    moTa nvarchar(100),
    giaTour DECIMAL(10,2),
    tg_batDau DATE,
    tg_ketThuc DATE,
    soLuongMax INT
);

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
);

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
);

CREATE TABLE VeTour 
(
	Mavetour char(5) primary key,
    MaDatVe char(5),
    maTour char(5),
    DiemDi nVARCHAR(100),
    PhuongTien nVARCHAR(100),
    thoigian date,
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
);
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
--rằng buộc
alter table nhanvien
	add constraint CK_nhienvien_SDT 
			check(SDT like '[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]')
alter table khachhang
	add constraint CK_khachhang_SDT 
			check(SDT like '[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]')
alter table khachhang
	add constraint CK_KhachHang_Email
			check(email like '[A-Za-z]%@gmail.com')
alter table nhanvien
	add constraint CK_NhanVien_Email
			check(email like '[A-Za-z]%@gmail.com')

--insert
INSERT INTO Khu 
	VALUES 
		('KH001', N'Khu A', N'Khu vui chơi'),
		('KH002', N'Khu B', N'Khu sự kiện'),
		('KH003', N'Khu C', N'Khu ẩm thực')

set dateformat ymd
INSERT INTO TroChoi VALUES 
		('TC001', N'Tàu lượn', N'Tàu lượn siêu tốc', 12, 20, '2023-01-01'),
		('TC002', N'Đu quay', N'Đu quay khổng lồ', 6, 30, '2023-01-01'),
		('TC003', N'Thuyền ma', N'Trò chơi kinh dị', 16, 15, '2023-01-01'),
		('TC004', N'Xe điện', N'Xe điện đụng', 8, 25, '2023-01-01'),
		('TC005', N'Vòng xoay', N'Vòng xoay gia đình', 4, 40, '2023-01-01'),
		('TC006', N'Thác nước', N'Trò chơi nước', 10, 20, '2023-01-01'),
		('TC007', N'Khinh khí cầu', N'Khinh khí cầu mini', 8, 10, '2023-01-01'),
		('TC008', N'Tường leo', N'Leo núi trong nhà', 10, 15, '2023-01-01'),
		('TC009', N'Bắn súng', N'Bắn súng nước', 6, 20, '2023-01-01'),
		('TC010', N'Đua xe', N'Đua xe mô hình', 8, 12, '2023-01-01');

-- Insert into SuKien
set dateformat ymd
INSERT INTO SuKien VALUES 
		('SK001', N'Lễ hội ánh sáng', '2023-12-01', '2023-12-31', N'Hoạt động', N'Cao', 0, 500),
		('SK002', N'Ngày gia đình', '2023-06-01', '2023-06-02', N'Kết thúc', N'Trung bình', 0, 300),
		('SK003', N'Halloween', '2023-10-31', '2023-10-31', N'Chuẩn bị', N'Cao', 12, 200),
		('SK004', N'Tết thiếu nhi', '2023-06-01', '2023-06-01', N'Hoạt động', N'Cao', 0, 400),
		('SK005', N'Giáng sinh', '2023-12-24', '2023-12-25', N'Chuẩn bị', N'Cao', 0, 500),
		('SK006', N'Hội chợ ẩm thực', '2023-05-15', '2023-05-17', N'Kết thúc', N'Trung bình', 0, 300),
		('SK007', N'Biểu diễn xiếc', '2023-04-01', '2023-04-03', N'Hoạt động', N'Trung bình', 0, 200),
		('SK008', N'Triển lãm nghệ thuật', '2023-03-01', '2023-03-10', N'Kết thúc', N'Thấp', 0, 150),
		('SK009', N'Ngày Valentine', '2023-02-14', '2023-02-14', N'Kết thúc', N'Trung bình', 12, 200),
		('SK010', N'Lễ hội hoa', '2023-03-08', '2023-03-09', N'Hoạt động', N'Thấp', 0, 250);

-- Insert into NhaHang
set dateformat ymd
INSERT INTO NhaHang VALUES 
		('NH001', '2023-01-01', '2023-01-01', N'Nhà hàng gia đình'),
		('NH002', '2023-01-01', '2023-01-01', N'Nhà hàng buffet'),
		('NH003', '2023-01-01', '2023-01-01', N'Quán ăn nhanh'),
		('NH004', '2023-01-01', '2023-01-01', N'Quán cà phê'),
		('NH005', '2023-01-01', '2023-01-01', N'Nhà hàng hải sản'),
		('NH006', '2023-01-01', '2023-01-01', N'Nhà hàng chay'),
		('NH007', '2023-01-01', '2023-01-01', N'Quán bar'),
		('NH008', '2023-01-01', '2023-01-01', N'Nhà hàng Âu'),
		('NH009', '2023-01-01', '2023-01-01', N'Nhà hàng Á'),
		('NH010', '2023-01-01', '2023-01-01', N'Nhà hàng Việt');

-- Insert into dichvu
set dateformat ymd
INSERT INTO dichvu VALUES 
		('DV001', 'KH001', N'Thuê xe điện', N'Dịch vụ thuê xe điện tham quan'),
		('DV002', 'KH002', N'Giữ đồ', N'Dịch vụ giữ đồ cá nhân'),
		('DV003', 'KH003', N'Xe lăn', N'Dịch vụ cho thuê xe lăn'),
		('DV004', 'KH001', N'Chụp ảnh', N'Dịch vụ chụp ảnh lưu niệm'),
		('DV005', 'KH002', N'Trông trẻ', N'Dịch vụ trông trẻ'),
		('DV006', 'KH002', N'Hướng dẫn viên', N'Dịch vụ hướng dẫn tham quan'),
		('DV007', 'KH003', N'Bảo vệ', N'Dịch vụ bảo vệ'),
		('DV008', 'KH001', N'Giao hàng', N'Dịch vụ giao hàng tận nơi'),
		('DV009', 'KH001', N'Y tế', N'Dịch vụ y tế'),
		('DV010', 'KH003', N'WiFi', N'Dịch vụ wifi miễn phí');

-- Insert into ChucVu
set dateformat ymd
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
set dateformat ymd
INSERT INTO NhanVien VALUES 
		('NV001', N'Nguyễn Văn A', '1990-01-01', '0901234567', 'nva@gmail.com', '2020-01-01', 10000000),
		('NV002', N'Trần Thị B', '1992-02-02', '0902345678', 'ttb@gmail.com', '2020-02-01', 8000000),
		('NV003', N'Lê Văn C', '1988-03-03', '0903456789', 'lvc@gmail.com', '2019-05-01', 12000000),
		('NV004', N'Phạm Thị D', '1995-04-04', '0904567890', 'ptd@gmail.com', '2021-01-01', 7000000),
		('NV005', N'Hoàng Văn E', '1993-05-05', '0905678901', 'hve@gmail.com', '2020-07-01', 9000000),
		('NV006', N'Vũ Thị F', '1991-06-06', '0906789012', 'vtf@gmail.com', '2019-11-01', 8500000),
		('NV007', N'Đặng Văn G', '1989-07-07', '0907890123', 'dvg@gmail.com', '2018-03-01', 11000000),
		('NV008', N'Bùi Thị H', '1994-08-08', '0908901234', 'bth@gmail.com', '2021-02-01', 7500000),
		('NV009', N'Ngô Văn I', '1990-09-09', '0909012345', 'nvi@gmail.com', '2020-05-01', 9500000),
		('NV010', N'Đỗ Thị K', '1992-10-10', '0900123456', 'dtk@gmail.com', '2021-03-01', 8200000)

set dateformat ymd
INSERT INTO nhanvien_chucvu 
	VALUES 
		('NV001', 'CV001', '2020-01-01', '2022-12-31'),
		('NV002', 'CV002', '2020-03-15', '2023-06-30'),
		('NV003', 'CV003', '2021-07-01', NULL),
		('NV004', 'CV004', '2022-01-01', NULL),
		('NV005', 'CV001', '2019-05-01', '2021-12-31'),
		('NV006', 'CV002', '2023-01-01', NULL),
		('NV007', 'CV005', '2022-08-01', NULL),
		('NV008', 'CV004', '2021-09-15', NULL),
		('NV009', 'CV003', '2020-04-01', '2022-10-31'),
		('NV010', 'CV002', '2021-11-01', NULL);

set dateformat ymd
INSERT INTO calamViec 
	VALUES 
		('CA001', N'Ca sáng', '2025-01-01', '2025-01-01'),
		('CA002', N'Ca chiều', '2025-01-01', '2025-01-01'),
		('CA003', N'Ca tối', '2025-01-01', '2025-01-01'),
		('CA004', N'Ca linh hoạt 1', '2025-01-01', '2025-01-01'),
		('CA005', N'Ca linh hoạt 2', '2025-01-01', '2025-01-01');

set dateformat ymd
INSERT INTO phanCongCa 
	VALUES 
		('NV001', 'CA001', '2025-04-01'),
		('NV002', 'CA002', '2025-04-01'),
		('NV003', 'CA003', '2025-04-01'),
		('NV004', 'CA001', '2025-04-01'),
		('NV005', 'CA002', '2025-04-01'),
		('NV006', 'CA003', '2025-04-01'),
		('NV007', 'CA001', '2025-04-01'),
		('NV008', 'CA002', '2025-04-01'),
		('NV009', 'CA003', '2025-04-01'),
		('NV010', 'CA001', '2025-04-01');

INSERT INTO nhanvien_dichvu 
	VALUES 
		('NV001', 'DV001'),
		('NV002', 'DV002'),
		('NV003', 'DV003'),
		('NV004', 'DV001'),
		('NV005', 'DV004'),
		('NV006', 'DV005'),
		('NV007', 'DV001'),
		('NV008', 'DV002'),
		('NV009', 'DV003'),
		('NV010', 'DV004');
set dateformat ymd
INSERT INTO khachhang 
	VALUES 
		('KH001', N'Nguyễn Văn A', '1990-01-01', N'Nam', N'Hà Nội', '0901234567', 'a@gmail.com'),
		('KH002', N'Trần Thị B', '1995-03-15', N'Nữ', N'HCM', '0912345678', 'b@gmail.com'),
		('KH003', N'Lê Văn C', '1988-12-25', N'Nam', N'Đà Nẵng', '0923456789', 'c@gmail.com'),
		('KH004', N'Phạm Thị D', '2000-07-20', N'Nữ', N'Hải Phòng', '0934567890', 'd@gmail.com'),
		('KH005', N'Hoàng Văn E', '1992-11-10', N'Nam', N'Cần Thơ', '0945678901', 'e@gmail.com'),
		('KH006', N'Đỗ Thị F', '1998-06-05', N'Nữ', N'Huế', '0956789012', 'f@gmail.com'),
		('KH007', N'Bùi Văn G', '1985-02-14', N'Nam', N'Nha Trang', '0967890123', 'g@gmail.com'),
		('KH008', N'Vũ Thị H', '1993-09-30', N'Nữ', N'Quảng Ninh', '0978901234', 'h@gmail.com'),
		('KH009', N'Tô Văn I', '1996-04-18', N'Nam', N'Bắc Ninh', '0989012345', 'i@gmail.com'),
		('KH010', N'Phan Thị J', '2002-12-12', N'Nữ', N'Bình Dương', '0990123456', 'j@gmail.com');
set dateformat ymd
INSERT INTO voucher 
	VALUES 
		('VC001', 'Giảm 10%', '2025-01-01', '2025-06-30', 10.00, 100),
		('VC002', 'Giảm 20%', '2025-03-01', '2025-08-31', 20.00, 50),
		('VC003', 'Miễn phí vé vào cổng', '2025-04-01', '2025-04-30', 100.00, 30),
		('VC004', 'Tặng kèm đồ uống', '2025-01-15', '2025-07-15', 5.00, 200),
		('VC005', 'Combo trò chơi', '2025-02-10', '2025-09-10', 15.00, 150),
		('VC006', 'Giảm 5% cho nhóm 3 người', '2025-05-01', '2025-08-01', 5.00, 80),
		('VC007', 'Ưu đãi thành viên mới', '2025-04-01', '2025-12-31', 12.50, 70),
		('VC008', 'Tặng vé buffet trưa', '2025-03-20', '2025-06-20', 50.00, 60),
		('VC009', 'Combo gia đình', '2025-02-01', '2025-07-31', 25.00, 40),
		('VC010', 'Miễn phí gửi xe', '2025-01-01', '2025-12-31', 3.00, 500);

set dateformat ymd
INSERT INTO KhachHang_Voucher VALUES
		('KH001', 'VC001', 3, 1, '2024-06-01'),
		('KH002', 'VC002', 2, 0, '2024-07-01'),
		('KH003', 'VC003', 5, 2, '2024-08-15'),
		('KH004', 'VC004', 1, 0, '2024-09-05'),
		('KH005', 'VC005', 4, 1, '2024-10-12'),
		('KH006', 'VC006', 3, 2, '2024-11-03'),
		('KH007', 'VC007', 2, 1, '2024-11-25'),
		('KH008', 'VC008', 1, 0, '2024-12-15'),
		('KH009', 'VC009', 2, 0, '2025-01-10'),
		('KH010', 'VC010', 3, 1, '2025-02-01');

set dateformat ymd
INSERT INTO Tour VALUES
		('TO001', N'Tour Hà Nội', N'Tham quan phố cổ', 1000.00, '2025-04-10', '2025-04-15', 30),
		('TO002', N'Tour Hạ Long', N'Du lịch vịnh biển', 1200.00, '2025-04-12', '2025-04-18', 40),
		('TO003', N'Tour Huế', N'Thăm lăng tẩm', 900.00, '2025-04-20', '2025-04-25', 25),
		('TO004', N'Tour Đà Nẵng', N'Biển Mỹ Khê', 1100.00, '2025-04-15', '2025-04-20', 35),
		('TO005', N'Tour Hội An', N'Phố cổ đèn lồng', 950.00, '2025-04-18', '2025-04-23', 20),
		('TO006', N'Tour Nha Trang', N'Thăm đảo Vinpearl', 1150.00, '2025-04-25', '2025-04-30', 30),
		('TO007', N'Tour Đà Lạt', N'Khí hậu mát mẻ', 1050.00, '2025-04-22', '2025-04-27', 30),
		('TO008', N'Tour Cần Thơ', N'Chợ nổi Cái Răng', 980.00, '2025-04-28', '2025-05-02', 25),
		('TO009', N'Tour Phú Quốc', N'Tắm biển nghỉ dưỡng', 1300.00, '2025-04-30', '2025-05-05', 40),
		('TO010', N'Tour Sapa', N'Leo Fansipan', 1020.00, '2025-04-26', '2025-05-01', 30);

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
		('NV010', 'TO010');

set dateformat ymd
INSERT INTO DatVe VALUES
		('DV001', 'KH001', '2025-04-01', N'Người lớn', 2, '2025-04-10'),
		('DV002', 'KH002', '2025-04-02', N'Trẻ em', 1, '2025-04-12'),
		('DV003', 'KH003', '2025-04-03', N'Người lớn', 3, '2025-04-20'),
		('DV004', 'KH004', '2025-04-04', N'Trẻ em', 1, '2025-04-15'),
		('DV005', 'KH005', '2025-04-05', N'Người lớn', 2, '2025-04-18'),
		('DV006', 'KH006', '2025-04-06', N'Người lớn', 1, '2025-04-25'),
		('DV007', 'KH007', '2025-04-07', N'Người lớn', 4, '2025-04-22'),
		('DV008', 'KH008', '2025-04-08', N'Người lớn', 2, '2025-04-28'),
		('DV009', 'KH009', '2025-04-09', N'Trẻ em', 2, '2025-04-30'),
		('DV010', 'KH010', '2025-04-10', N'Người lớn', 3, '2025-04-26');

set dateformat ymd
INSERT INTO VeTour VALUES
		('VT001', 'DV001', 'TO001', N'Hà Nội', N'Xe buýt', '2025-04-10'),
		('VT002', 'DV002', 'TO002', N'Hà Nội', N'Tàu hỏa', '2025-04-12'),
		('VT003', 'DV003', 'TO003', N'Huế', N'Máy bay', '2025-04-20'),
		('VT004', 'DV004', 'TO004', N'Hồ Chí Minh', N'Xe khách', '2025-04-15'),
		('VT005', 'DV005', 'TO005', N'Hội An', N'Xe buýt', '2025-04-18'),
		('VT006', 'DV006', 'TO006', N'Nha Trang', N'Máy bay', '2025-04-25'),
		('VT007', 'DV007', 'TO007', N'Đà Lạt', N'Xe khách', '2025-04-22'),
		('VT008', 'DV008', 'TO008', N'Cần Thơ', N'Tàu thủy', '2025-04-28'),
		('VT009', 'DV009', 'TO009', N'Phú Quốc', N'Máy bay', '2025-04-30'),
		('VT010', 'DV010', 'TO010', N'Sapa', N'Tàu hỏa', '2025-04-26');

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
		('DV010', 'DV010');
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
		('VL010', 'DV010');