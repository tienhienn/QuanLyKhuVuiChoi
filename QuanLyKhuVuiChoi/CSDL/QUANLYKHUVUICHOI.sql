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
	maKhu char(5),
	tenTrochoi nvarchar(50),
	mota nvarchar(100),
	gioiHanDoTuoi int,
	sucChua int,
	hoiGianHoatDong Date,
	Foreign key (makhu) references Khu(makhu)
		on update 
			cascade 
		on delete 
			cascade
)
create table SuKien
(	
	masukien char(5) primary key,
	makhu char(5),
	tenSuKien nvarchar(50),
	TGianBatDau date,
	TgianKetThuc date,
	TrangThaiHoatDong nvarchar(10),
	mucDoCuonHut nvarchar(50),
	gioiHanDoTuoi int,
	SucChua int,
	Foreign key (makhu) references Khu(makhu)
		on update 
			cascade 
		on delete 
			cascade
)
create table GianHang
(	
	maGianHang char(5) primary key,
	makhu char(5),
	tenGianHang nvarchar(50),
	TGianMoCua date,
	TgianDongCua date,
	Foreign key (makhu) references Khu(makhu)
		on update 
			cascade 
		on delete 
			cascade
)
create table AmThuc
(
	maNhaHang char(5) primary key,
	makhu char(5),
	TGianMoCua date,
	TgianDongCua date,
	loaiNhaHang nvarchar(50),
	Foreign key (makhu) references Khu(makhu)
		on update 
			cascade 
		on delete 
			cascade
)
create table loaiNhaHang
(
	MaLoaiNhaHang char(5) primary key,
	loaiNhaHang nvarchar(10),
)
create table dichvu
(
	maDichVu char(5) primary key,
	tenDichVu nvarchar(50),
	mota nvarchar(100)
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