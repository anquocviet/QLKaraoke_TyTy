/*
 Navicat Premium Data Transfer

 Source Server         : MariaDB
 Source Server Type    : MariaDB
 Source Server Version : 110302 (11.3.2-MariaDB)
 Source Host           : localhost:3306
 Source Schema         : karaoke

 Target Server Type    : MariaDB
 Target Server Version : 110302 (11.3.2-MariaDB)
 File Encoding         : 65001

 Date: 01/04/2024 22:26:16
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for CT_KhuyenMai
-- ----------------------------
DROP TABLE IF EXISTS `CT_KhuyenMai`;
CREATE TABLE `CT_KhuyenMai` (
  `MaKhuyenMai` varchar(255) NOT NULL,
  `TenKhuyenMai` varchar(255) DEFAULT NULL,
  `NgayBatDau` datetime DEFAULT NULL,
  `NgayKetThuc` datetime DEFAULT NULL,
  `LuotSuDungConLai` int(11) DEFAULT NULL,
  `ChietKhau` int(11) DEFAULT NULL,
  PRIMARY KEY (`MaKhuyenMai`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of CT_KhuyenMai
-- ----------------------------
BEGIN;
INSERT INTO `CT_KhuyenMai` (`MaKhuyenMai`, `TenKhuyenMai`, `NgayBatDau`, `NgayKetThuc`, `LuotSuDungConLai`, `ChietKhau`) VALUES ('DEFAULT', 'DEFAULT', '2000-01-01 00:00:00', '3000-01-01 00:00:00', 0, 0);
INSERT INTO `CT_KhuyenMai` (`MaKhuyenMai`, `TenKhuyenMai`, `NgayBatDau`, `NgayKetThuc`, `LuotSuDungConLai`, `ChietKhau`) VALUES ('SALES1212', 'SALES THÁNG 12', '2023-12-12 15:41:41', '2023-12-19 15:41:47', 1, 10);
INSERT INTO `CT_KhuyenMai` (`MaKhuyenMai`, `TenKhuyenMai`, `NgayBatDau`, `NgayKetThuc`, `LuotSuDungConLai`, `ChietKhau`) VALUES ('SALES30/11', 'SALES CHO CUỐI THÁNG 11', '2023-11-25 00:00:00', '2023-12-01 00:00:00', 49, 10);
INSERT INTO `CT_KhuyenMai` (`MaKhuyenMai`, `TenKhuyenMai`, `NgayBatDau`, `NgayKetThuc`, `LuotSuDungConLai`, `ChietKhau`) VALUES ('TUANLEVANG', 'Sales Tuần lễ vàng', '2023-11-01 00:00:00', '2023-11-08 00:00:00', 14, 5);
COMMIT;

-- ----------------------------
-- Table structure for ChiTietHD_DichVu
-- ----------------------------
DROP TABLE IF EXISTS `ChiTietHD_DichVu`;
CREATE TABLE `ChiTietHD_DichVu` (
  `MaHoaDon` varchar(255) NOT NULL,
  `MaDichVu` varchar(255) NOT NULL,
  `SoLuong` int(11) DEFAULT NULL,
  `ThanhTien` int(11) DEFAULT NULL,
  PRIMARY KEY (`MaHoaDon`,`MaDichVu`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of ChiTietHD_DichVu
-- ----------------------------
BEGIN;
INSERT INTO `ChiTietHD_DichVu` (`MaHoaDon`, `MaDichVu`, `SoLuong`, `ThanhTien`) VALUES ('HD01021223', 'DV003', 1, 100000);
INSERT INTO `ChiTietHD_DichVu` (`MaHoaDon`, `MaDichVu`, `SoLuong`, `ThanhTien`) VALUES ('HD01021223', 'DV018', 1, 370000);
INSERT INTO `ChiTietHD_DichVu` (`MaHoaDon`, `MaDichVu`, `SoLuong`, `ThanhTien`) VALUES ('HD01041223', 'DV001', 2, 60000);
INSERT INTO `ChiTietHD_DichVu` (`MaHoaDon`, `MaDichVu`, `SoLuong`, `ThanhTien`) VALUES ('HD01041223', 'DV014', 2, 60000);
INSERT INTO `ChiTietHD_DichVu` (`MaHoaDon`, `MaDichVu`, `SoLuong`, `ThanhTien`) VALUES ('HD01060623', 'DV001', 5, 150000);
INSERT INTO `ChiTietHD_DichVu` (`MaHoaDon`, `MaDichVu`, `SoLuong`, `ThanhTien`) VALUES ('HD01060623', 'DV003', 2, 200000);
INSERT INTO `ChiTietHD_DichVu` (`MaHoaDon`, `MaDichVu`, `SoLuong`, `ThanhTien`) VALUES ('HD01081223', 'DV013', 1, 24000);
INSERT INTO `ChiTietHD_DichVu` (`MaHoaDon`, `MaDichVu`, `SoLuong`, `ThanhTien`) VALUES ('HD01081223', 'DV014', 1, 30000);
INSERT INTO `ChiTietHD_DichVu` (`MaHoaDon`, `MaDichVu`, `SoLuong`, `ThanhTien`) VALUES ('HD01131223', 'DV002', 8, 192000);
INSERT INTO `ChiTietHD_DichVu` (`MaHoaDon`, `MaDichVu`, `SoLuong`, `ThanhTien`) VALUES ('HD01131223', 'DV003', 2, 200000);
INSERT INTO `ChiTietHD_DichVu` (`MaHoaDon`, `MaDichVu`, `SoLuong`, `ThanhTien`) VALUES ('HD01161222', 'DV003', 2, 240000);
INSERT INTO `ChiTietHD_DichVu` (`MaHoaDon`, `MaDichVu`, `SoLuong`, `ThanhTien`) VALUES ('HD01161222', 'DV004', 2, 144000);
INSERT INTO `ChiTietHD_DichVu` (`MaHoaDon`, `MaDichVu`, `SoLuong`, `ThanhTien`) VALUES ('HD01161222', 'DV006', 3, 68400);
INSERT INTO `ChiTietHD_DichVu` (`MaHoaDon`, `MaDichVu`, `SoLuong`, `ThanhTien`) VALUES ('HD01161222', 'DV013', 1, 24000);
INSERT INTO `ChiTietHD_DichVu` (`MaHoaDon`, `MaDichVu`, `SoLuong`, `ThanhTien`) VALUES ('HD01171123', 'DV022', 2, 48000);
INSERT INTO `ChiTietHD_DichVu` (`MaHoaDon`, `MaDichVu`, `SoLuong`, `ThanhTien`) VALUES ('HD01171123', 'DV023', 2, 144000);
INSERT INTO `ChiTietHD_DichVu` (`MaHoaDon`, `MaDichVu`, `SoLuong`, `ThanhTien`) VALUES ('HD01200223', 'DV001', 2, 149296);
INSERT INTO `ChiTietHD_DichVu` (`MaHoaDon`, `MaDichVu`, `SoLuong`, `ThanhTien`) VALUES ('HD01251123', 'DV003', 1, 100000);
INSERT INTO `ChiTietHD_DichVu` (`MaHoaDon`, `MaDichVu`, `SoLuong`, `ThanhTien`) VALUES ('HD01251123', 'DV008', 1, 8000);
INSERT INTO `ChiTietHD_DichVu` (`MaHoaDon`, `MaDichVu`, `SoLuong`, `ThanhTien`) VALUES ('HD01251123', 'DV010', 1, 24000);
INSERT INTO `ChiTietHD_DichVu` (`MaHoaDon`, `MaDichVu`, `SoLuong`, `ThanhTien`) VALUES ('HD01291122', 'DV001', 2, 86400);
INSERT INTO `ChiTietHD_DichVu` (`MaHoaDon`, `MaDichVu`, `SoLuong`, `ThanhTien`) VALUES ('HD01291122', 'DV004', 1, 60000);
INSERT INTO `ChiTietHD_DichVu` (`MaHoaDon`, `MaDichVu`, `SoLuong`, `ThanhTien`) VALUES ('HD01291122', 'DV009', 1, 28800);
INSERT INTO `ChiTietHD_DichVu` (`MaHoaDon`, `MaDichVu`, `SoLuong`, `ThanhTien`) VALUES ('HD01291122', 'DV010', 1, 24000);
INSERT INTO `ChiTietHD_DichVu` (`MaHoaDon`, `MaDichVu`, `SoLuong`, `ThanhTien`) VALUES ('HD02081223', 'DV009', 1, 24000);
INSERT INTO `ChiTietHD_DichVu` (`MaHoaDon`, `MaDichVu`, `SoLuong`, `ThanhTien`) VALUES ('HD02081223', 'DV012', 1, 20000);
INSERT INTO `ChiTietHD_DichVu` (`MaHoaDon`, `MaDichVu`, `SoLuong`, `ThanhTien`) VALUES ('HD02180123', 'DV001', 15, 777600);
INSERT INTO `ChiTietHD_DichVu` (`MaHoaDon`, `MaDichVu`, `SoLuong`, `ThanhTien`) VALUES ('HD02180123', 'DV007', 2, 63360);
INSERT INTO `ChiTietHD_DichVu` (`MaHoaDon`, `MaDichVu`, `SoLuong`, `ThanhTien`) VALUES ('HD02180123', 'DV014', 2, 76032);
INSERT INTO `ChiTietHD_DichVu` (`MaHoaDon`, `MaDichVu`, `SoLuong`, `ThanhTien`) VALUES ('HD02180123', 'DV017', 1, 36000);
INSERT INTO `ChiTietHD_DichVu` (`MaHoaDon`, `MaDichVu`, `SoLuong`, `ThanhTien`) VALUES ('HD02180123', 'DV023', 2, 144000);
INSERT INTO `ChiTietHD_DichVu` (`MaHoaDon`, `MaDichVu`, `SoLuong`, `ThanhTien`) VALUES ('HD02180123', 'DV024', 2, 48000);
INSERT INTO `ChiTietHD_DichVu` (`MaHoaDon`, `MaDichVu`, `SoLuong`, `ThanhTien`) VALUES ('HD02200223', 'DV015', 8, 304128);
INSERT INTO `ChiTietHD_DichVu` (`MaHoaDon`, `MaDichVu`, `SoLuong`, `ThanhTien`) VALUES ('HD02200223', 'DV017', 20, 864000);
INSERT INTO `ChiTietHD_DichVu` (`MaHoaDon`, `MaDichVu`, `SoLuong`, `ThanhTien`) VALUES ('HD02200223', 'DV018', 2, 1382400);
INSERT INTO `ChiTietHD_DichVu` (`MaHoaDon`, `MaDichVu`, `SoLuong`, `ThanhTien`) VALUES ('HD02291122', 'DV018', 1, 576000);
INSERT INTO `ChiTietHD_DichVu` (`MaHoaDon`, `MaDichVu`, `SoLuong`, `ThanhTien`) VALUES ('HD02291122', 'DV023', 2, 120000);
INSERT INTO `ChiTietHD_DichVu` (`MaHoaDon`, `MaDichVu`, `SoLuong`, `ThanhTien`) VALUES ('HD03060623', 'DV016', 1, 40000);
INSERT INTO `ChiTietHD_DichVu` (`MaHoaDon`, `MaDichVu`, `SoLuong`, `ThanhTien`) VALUES ('HD03060623', 'DV017', 2, 86400);
INSERT INTO `ChiTietHD_DichVu` (`MaHoaDon`, `MaDichVu`, `SoLuong`, `ThanhTien`) VALUES ('HD03060623', 'DV022', 4, 96000);
INSERT INTO `ChiTietHD_DichVu` (`MaHoaDon`, `MaDichVu`, `SoLuong`, `ThanhTien`) VALUES ('HD03090623', 'DV002', 2, 48000);
INSERT INTO `ChiTietHD_DichVu` (`MaHoaDon`, `MaDichVu`, `SoLuong`, `ThanhTien`) VALUES ('HD03090623', 'DV004', 2, 144000);
INSERT INTO `ChiTietHD_DichVu` (`MaHoaDon`, `MaDichVu`, `SoLuong`, `ThanhTien`) VALUES ('HD03131223', 'DV001', 3, 90000);
INSERT INTO `ChiTietHD_DichVu` (`MaHoaDon`, `MaDichVu`, `SoLuong`, `ThanhTien`) VALUES ('HD03131223', 'DV002', 1, 24000);
INSERT INTO `ChiTietHD_DichVu` (`MaHoaDon`, `MaDichVu`, `SoLuong`, `ThanhTien`) VALUES ('HD03180123', 'DV001', 2, 124416);
INSERT INTO `ChiTietHD_DichVu` (`MaHoaDon`, `MaDichVu`, `SoLuong`, `ThanhTien`) VALUES ('HD03180123', 'DV002', 2, 48000);
INSERT INTO `ChiTietHD_DichVu` (`MaHoaDon`, `MaDichVu`, `SoLuong`, `ThanhTien`) VALUES ('HD03180123', 'DV003', 2, 288000);
INSERT INTO `ChiTietHD_DichVu` (`MaHoaDon`, `MaDichVu`, `SoLuong`, `ThanhTien`) VALUES ('HD03291122', 'DV009', 2, 69120);
INSERT INTO `ChiTietHD_DichVu` (`MaHoaDon`, `MaDichVu`, `SoLuong`, `ThanhTien`) VALUES ('HD03291122', 'DV014', 1, 31680);
INSERT INTO `ChiTietHD_DichVu` (`MaHoaDon`, `MaDichVu`, `SoLuong`, `ThanhTien`) VALUES ('HD03291122', 'DV015', 1, 31680);
INSERT INTO `ChiTietHD_DichVu` (`MaHoaDon`, `MaDichVu`, `SoLuong`, `ThanhTien`) VALUES ('HD04081223', 'DV001', 1, 30000);
INSERT INTO `ChiTietHD_DichVu` (`MaHoaDon`, `MaDichVu`, `SoLuong`, `ThanhTien`) VALUES ('HD04131223', 'DV001', 1, 30000);
INSERT INTO `ChiTietHD_DichVu` (`MaHoaDon`, `MaDichVu`, `SoLuong`, `ThanhTien`) VALUES ('HD04131223', 'DV002', 1, 24000);
INSERT INTO `ChiTietHD_DichVu` (`MaHoaDon`, `MaDichVu`, `SoLuong`, `ThanhTien`) VALUES ('HD04200223', 'DV008', 1, 8000);
INSERT INTO `ChiTietHD_DichVu` (`MaHoaDon`, `MaDichVu`, `SoLuong`, `ThanhTien`) VALUES ('HD04200223', 'DV009', 1, 24000);
INSERT INTO `ChiTietHD_DichVu` (`MaHoaDon`, `MaDichVu`, `SoLuong`, `ThanhTien`) VALUES ('HD05131223', 'DV001', 1, 30000);
INSERT INTO `ChiTietHD_DichVu` (`MaHoaDon`, `MaDichVu`, `SoLuong`, `ThanhTien`) VALUES ('HD05131223', 'DV003', 1, 100000);
INSERT INTO `ChiTietHD_DichVu` (`MaHoaDon`, `MaDichVu`, `SoLuong`, `ThanhTien`) VALUES ('HD05200223', 'DV001', 2, 72000);
INSERT INTO `ChiTietHD_DichVu` (`MaHoaDon`, `MaDichVu`, `SoLuong`, `ThanhTien`) VALUES ('HD05200223', 'DV009', 3, 86400);
INSERT INTO `ChiTietHD_DichVu` (`MaHoaDon`, `MaDichVu`, `SoLuong`, `ThanhTien`) VALUES ('HD07080423', 'DV022', 2, 48000);
COMMIT;

-- ----------------------------
-- Table structure for ChiTietHD_Phong
-- ----------------------------
DROP TABLE IF EXISTS `ChiTietHD_Phong`;
CREATE TABLE `ChiTietHD_Phong` (
  `MaHoaDon` varchar(255) NOT NULL,
  `MaPhong` varchar(255) NOT NULL,
  `GioVao` datetime DEFAULT NULL,
  `GioRa` datetime DEFAULT NULL,
  `TongGioSuDung` double DEFAULT NULL,
  `ThanhTien` int(11) DEFAULT NULL,
  PRIMARY KEY (`MaHoaDon`,`MaPhong`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of ChiTietHD_Phong
-- ----------------------------
BEGIN;
INSERT INTO `ChiTietHD_Phong` (`MaHoaDon`, `MaPhong`, `GioVao`, `GioRa`, `TongGioSuDung`, `ThanhTien`) VALUES ('HD01021223', 'P103VIP', '2023-12-02 16:02:44', '2023-12-02 22:03:36', 6.01450634002686, 1503626);
INSERT INTO `ChiTietHD_Phong` (`MaHoaDon`, `MaPhong`, `GioVao`, `GioRa`, `TongGioSuDung`, `ThanhTien`) VALUES ('HD01021223', 'P204VIP', '2023-12-02 16:02:50', '2023-12-02 22:03:36', 6.01263761520386, 1503159);
INSERT INTO `ChiTietHD_Phong` (`MaHoaDon`, `MaPhong`, `GioVao`, `GioRa`, `TongGioSuDung`, `ThanhTien`) VALUES ('HD01041223', 'P401VIP', '2023-12-04 09:05:24', '2023-12-04 13:05:57', 4.00909280776978, 801818);
INSERT INTO `ChiTietHD_Phong` (`MaHoaDon`, `MaPhong`, `GioVao`, `GioRa`, `TongGioSuDung`, `ThanhTien`) VALUES ('HD01060623', 'P101', '2023-06-06 08:08:52', '2023-06-06 13:09:50', 5.01611566543579, 501611);
INSERT INTO `ChiTietHD_Phong` (`MaHoaDon`, `MaPhong`, `GioVao`, `GioRa`, `TongGioSuDung`, `ThanhTien`) VALUES ('HD01060623', 'P104VIP', '2023-06-06 08:09:13', '2023-06-06 13:09:50', 5.0103611946106, 1002072);
INSERT INTO `ChiTietHD_Phong` (`MaHoaDon`, `MaPhong`, `GioVao`, `GioRa`, `TongGioSuDung`, `ThanhTien`) VALUES ('HD01080423', 'P303VIP', '2023-04-08 10:03:42', '2023-04-08 14:05:27', 4.02915716171265, 805831);
INSERT INTO `ChiTietHD_Phong` (`MaHoaDon`, `MaPhong`, `GioVao`, `GioRa`, `TongGioSuDung`, `ThanhTien`) VALUES ('HD01081223', 'P101', '2023-12-08 12:09:50', '2023-12-08 18:11:48', 6.03287220001221, 904930);
INSERT INTO `ChiTietHD_Phong` (`MaHoaDon`, `MaPhong`, `GioVao`, `GioRa`, `TongGioSuDung`, `ThanhTien`) VALUES ('HD01090623', 'P203', '2023-06-09 09:11:29', '2023-06-09 15:12:34', 6.01814794540405, 601814);
INSERT INTO `ChiTietHD_Phong` (`MaHoaDon`, `MaPhong`, `GioVao`, `GioRa`, `TongGioSuDung`, `ThanhTien`) VALUES ('HD01090623', 'P304', '2023-06-09 09:11:36', '2023-06-09 15:12:34', 6.01610374450684, 601610);
INSERT INTO `ChiTietHD_Phong` (`MaHoaDon`, `MaPhong`, `GioVao`, `GioRa`, `TongGioSuDung`, `ThanhTien`) VALUES ('HD01131223', 'P104VIP', '2023-12-13 08:13:47', '2023-12-13 08:14:58', 0.0198180563747883, 3963);
INSERT INTO `ChiTietHD_Phong` (`MaHoaDon`, `MaPhong`, `GioVao`, `GioRa`, `TongGioSuDung`, `ThanhTien`) VALUES ('HD01161222', 'P103VIP', '2022-12-16 09:05:47', '2022-12-16 12:06:14', 3.00762963294983, 601525);
INSERT INTO `ChiTietHD_Phong` (`MaHoaDon`, `MaPhong`, `GioVao`, `GioRa`, `TongGioSuDung`, `ThanhTien`) VALUES ('HD01171123', 'P201', '2023-11-17 09:18:51', '2023-11-17 15:20:06', 6.02081632614136, 602081);
INSERT INTO `ChiTietHD_Phong` (`MaHoaDon`, `MaPhong`, `GioVao`, `GioRa`, `TongGioSuDung`, `ThanhTien`) VALUES ('HD01180123', 'P403', '2023-01-18 10:05:53', '2023-01-18 16:08:16', 6.03955698013306, 603955);
INSERT INTO `ChiTietHD_Phong` (`MaHoaDon`, `MaPhong`, `GioVao`, `GioRa`, `TongGioSuDung`, `ThanhTien`) VALUES ('HD01200223', 'P101', '2023-02-20 08:49:23', '2023-02-20 11:30:48', 2.69026899337769, 269026);
INSERT INTO `ChiTietHD_Phong` (`MaHoaDon`, `MaPhong`, `GioVao`, `GioRa`, `TongGioSuDung`, `ThanhTien`) VALUES ('HD01200223', 'P202', '2023-02-20 08:49:28', '2023-02-20 11:30:48', 2.68890047073364, 268890);
INSERT INTO `ChiTietHD_Phong` (`MaHoaDon`, `MaPhong`, `GioVao`, `GioRa`, `TongGioSuDung`, `ThanhTien`) VALUES ('HD01251123', 'P101', '2023-11-25 09:16:18', '2023-11-25 14:17:36', 5.02174520492554, 502174);
INSERT INTO `ChiTietHD_Phong` (`MaHoaDon`, `MaPhong`, `GioVao`, `GioRa`, `TongGioSuDung`, `ThanhTien`) VALUES ('HD01281123', 'P202', '2023-11-28 15:58:02', '2023-11-28 18:01:12', 2.05261564254761, 307892);
INSERT INTO `ChiTietHD_Phong` (`MaHoaDon`, `MaPhong`, `GioVao`, `GioRa`, `TongGioSuDung`, `ThanhTien`) VALUES ('HD01291122', 'P101', '2022-11-29 14:35:03', '2022-11-29 16:02:08', 1.45136368274689, 145136);
INSERT INTO `ChiTietHD_Phong` (`MaHoaDon`, `MaPhong`, `GioVao`, `GioRa`, `TongGioSuDung`, `ThanhTien`) VALUES ('HD01291122', 'P102', '2022-11-29 14:35:08', '2022-11-29 16:02:08', 1.45003974437714, 145003);
INSERT INTO `ChiTietHD_Phong` (`MaHoaDon`, `MaPhong`, `GioVao`, `GioRa`, `TongGioSuDung`, `ThanhTien`) VALUES ('HD02021223', 'P302', '2023-12-02 18:03:02', '2023-12-02 23:04:04', 5.0173020362854, 752595);
INSERT INTO `ChiTietHD_Phong` (`MaHoaDon`, `MaPhong`, `GioVao`, `GioRa`, `TongGioSuDung`, `ThanhTien`) VALUES ('HD02041223', 'P402', '2023-12-04 09:05:34', '2023-12-04 13:06:11', 4.01023960113525, 401023);
INSERT INTO `ChiTietHD_Phong` (`MaHoaDon`, `MaPhong`, `GioVao`, `GioRa`, `TongGioSuDung`, `ThanhTien`) VALUES ('HD02041223', 'P403', '2023-12-04 09:05:42', '2023-12-04 13:06:11', 4.00812482833862, 400812);
INSERT INTO `ChiTietHD_Phong` (`MaHoaDon`, `MaPhong`, `GioVao`, `GioRa`, `TongGioSuDung`, `ThanhTien`) VALUES ('HD02060623', 'P202', '2023-06-06 08:09:02', '2023-06-06 14:10:04', 6.01703977584839, 601704);
INSERT INTO `ChiTietHD_Phong` (`MaHoaDon`, `MaPhong`, `GioVao`, `GioRa`, `TongGioSuDung`, `ThanhTien`) VALUES ('HD02080423', 'P204VIP', '2023-04-08 10:03:57', '2023-04-08 12:04:19', 2.00614047050476, 401228);
INSERT INTO `ChiTietHD_Phong` (`MaHoaDon`, `MaPhong`, `GioVao`, `GioRa`, `TongGioSuDung`, `ThanhTien`) VALUES ('HD02081223', 'P103VIP', '2023-12-08 12:10:06', '2023-12-08 18:12:08', 6.03369665145874, 1508424);
INSERT INTO `ChiTietHD_Phong` (`MaHoaDon`, `MaPhong`, `GioVao`, `GioRa`, `TongGioSuDung`, `ThanhTien`) VALUES ('HD02090623', 'P301', '2023-06-09 09:11:48', '2023-06-09 15:12:53', 6.0180082321167, 601800);
INSERT INTO `ChiTietHD_Phong` (`MaHoaDon`, `MaPhong`, `GioVao`, `GioRa`, `TongGioSuDung`, `ThanhTien`) VALUES ('HD02131223', 'P203', '2023-12-13 08:14:23', '2023-12-13 16:15:23', 8.01663684844971, 801663);
INSERT INTO `ChiTietHD_Phong` (`MaHoaDon`, `MaPhong`, `GioVao`, `GioRa`, `TongGioSuDung`, `ThanhTien`) VALUES ('HD02131223', 'P302', '2023-12-13 08:14:13', '2023-12-13 16:15:23', 8.01936340332031, 801936);
INSERT INTO `ChiTietHD_Phong` (`MaHoaDon`, `MaPhong`, `GioVao`, `GioRa`, `TongGioSuDung`, `ThanhTien`) VALUES ('HD02171123', 'P301', '2023-11-17 09:19:23', '2023-11-17 15:20:16', 6.01462984085083, 601463);
INSERT INTO `ChiTietHD_Phong` (`MaHoaDon`, `MaPhong`, `GioVao`, `GioRa`, `TongGioSuDung`, `ThanhTien`) VALUES ('HD02180123', 'P404', '2023-01-18 10:06:06', '2023-01-18 15:07:34', 5.02444124221802, 502444);
INSERT INTO `ChiTietHD_Phong` (`MaHoaDon`, `MaPhong`, `GioVao`, `GioRa`, `TongGioSuDung`, `ThanhTien`) VALUES ('HD02200223', 'P104VIP', '2023-02-20 08:49:38', '2023-02-20 11:33:03', 2.72349977493286, 544699);
INSERT INTO `ChiTietHD_Phong` (`MaHoaDon`, `MaPhong`, `GioVao`, `GioRa`, `TongGioSuDung`, `ThanhTien`) VALUES ('HD02200223', 'P303VIP', '2023-02-20 08:49:44', '2023-02-20 11:33:03', 2.72185349464417, 544370);
INSERT INTO `ChiTietHD_Phong` (`MaHoaDon`, `MaPhong`, `GioVao`, `GioRa`, `TongGioSuDung`, `ThanhTien`) VALUES ('HD02200223', 'P304', '2023-02-20 08:49:50', '2023-02-20 11:33:03', 2.72032046318054, 272032);
INSERT INTO `ChiTietHD_Phong` (`MaHoaDon`, `MaPhong`, `GioVao`, `GioRa`, `TongGioSuDung`, `ThanhTien`) VALUES ('HD02251123', 'P102', '2023-11-25 09:16:27', '2023-11-25 14:17:46', 5.0218243598938, 502182);
INSERT INTO `ChiTietHD_Phong` (`MaHoaDon`, `MaPhong`, `GioVao`, `GioRa`, `TongGioSuDung`, `ThanhTien`) VALUES ('HD02281123', 'P104VIP', '2023-11-28 08:58:35', '2023-11-28 18:00:56', 9.0391845703125, 2259796);
INSERT INTO `ChiTietHD_Phong` (`MaHoaDon`, `MaPhong`, `GioVao`, `GioRa`, `TongGioSuDung`, `ThanhTien`) VALUES ('HD02291122', 'P203', '2022-11-29 15:50:33', '2022-11-29 17:40:00', 1.82408213615417, 182408);
INSERT INTO `ChiTietHD_Phong` (`MaHoaDon`, `MaPhong`, `GioVao`, `GioRa`, `TongGioSuDung`, `ThanhTien`) VALUES ('HD03021223', 'P304', '2023-12-02 18:03:10', '2023-12-02 22:03:48', 4.01046800613403, 601570);
INSERT INTO `ChiTietHD_Phong` (`MaHoaDon`, `MaPhong`, `GioVao`, `GioRa`, `TongGioSuDung`, `ThanhTien`) VALUES ('HD03041223', 'P404', '2023-12-04 16:06:34', '2023-12-04 23:06:51', 7.00488233566284, 1050732);
INSERT INTO `ChiTietHD_Phong` (`MaHoaDon`, `MaPhong`, `GioVao`, `GioRa`, `TongGioSuDung`, `ThanhTien`) VALUES ('HD03060623', 'P302', '2023-06-06 09:09:43', '2023-06-06 17:10:29', 8.0128870010376, 801288);
INSERT INTO `ChiTietHD_Phong` (`MaHoaDon`, `MaPhong`, `GioVao`, `GioRa`, `TongGioSuDung`, `ThanhTien`) VALUES ('HD03080423', 'P201', '2023-04-08 10:04:04', '2023-04-08 14:04:31', 4.00755500793457, 400755);
INSERT INTO `ChiTietHD_Phong` (`MaHoaDon`, `MaPhong`, `GioVao`, `GioRa`, `TongGioSuDung`, `ThanhTien`) VALUES ('HD03081223', 'P202', '2023-12-08 14:10:19', '2023-12-08 18:12:31', 4.03666973114014, 605500);
INSERT INTO `ChiTietHD_Phong` (`MaHoaDon`, `MaPhong`, `GioVao`, `GioRa`, `TongGioSuDung`, `ThanhTien`) VALUES ('HD03090623', 'P403', '2023-06-09 12:12:00', '2023-06-09 18:13:29', 6.02468299865723, 903702);
INSERT INTO `ChiTietHD_Phong` (`MaHoaDon`, `MaPhong`, `GioVao`, `GioRa`, `TongGioSuDung`, `ThanhTien`) VALUES ('HD03131223', 'P101', '2023-12-13 08:52:36', '2023-12-13 14:54:56', 6.03901195526123, 603901);
INSERT INTO `ChiTietHD_Phong` (`MaHoaDon`, `MaPhong`, `GioVao`, `GioRa`, `TongGioSuDung`, `ThanhTien`) VALUES ('HD03171123', 'P303VIP', '2023-11-17 09:19:44', '2023-11-17 15:57:30', 6.62928438186646, 1325856);
INSERT INTO `ChiTietHD_Phong` (`MaHoaDon`, `MaPhong`, `GioVao`, `GioRa`, `TongGioSuDung`, `ThanhTien`) VALUES ('HD03180123', 'P104VIP', '2023-01-18 10:06:21', '2023-01-18 16:45:49', 6.65782737731934, 1331565);
INSERT INTO `ChiTietHD_Phong` (`MaHoaDon`, `MaPhong`, `GioVao`, `GioRa`, `TongGioSuDung`, `ThanhTien`) VALUES ('HD03200223', 'P102', '2023-02-20 10:50:10', '2023-02-20 14:33:27', 3.72124338150024, 372124);
INSERT INTO `ChiTietHD_Phong` (`MaHoaDon`, `MaPhong`, `GioVao`, `GioRa`, `TongGioSuDung`, `ThanhTien`) VALUES ('HD03251123', 'P203', '2023-11-25 09:16:35', '2023-11-25 16:18:01', 7.02392673492432, 702392);
INSERT INTO `ChiTietHD_Phong` (`MaHoaDon`, `MaPhong`, `GioVao`, `GioRa`, `TongGioSuDung`, `ThanhTien`) VALUES ('HD03291122', 'P302', '2022-11-29 16:01:52', '2022-11-29 19:41:15', 3.65639209747314, 548458);
INSERT INTO `ChiTietHD_Phong` (`MaHoaDon`, `MaPhong`, `GioVao`, `GioRa`, `TongGioSuDung`, `ThanhTien`) VALUES ('HD04080423', 'P102', '2023-04-08 10:04:12', '2023-04-08 14:05:04', 4.0144362449646, 401443);
INSERT INTO `ChiTietHD_Phong` (`MaHoaDon`, `MaPhong`, `GioVao`, `GioRa`, `TongGioSuDung`, `ThanhTien`) VALUES ('HD04081223', 'P204VIP', '2023-12-08 14:10:29', '2023-12-08 18:12:57', 4.04095792770386, 1010239);
INSERT INTO `ChiTietHD_Phong` (`MaHoaDon`, `MaPhong`, `GioVao`, `GioRa`, `TongGioSuDung`, `ThanhTien`) VALUES ('HD04090623', 'P404', '2023-06-09 12:12:20', '2023-06-09 18:13:41', 6.02245330810547, 903368);
INSERT INTO `ChiTietHD_Phong` (`MaHoaDon`, `MaPhong`, `GioVao`, `GioRa`, `TongGioSuDung`, `ThanhTien`) VALUES ('HD04131223', 'P102', '2023-12-13 14:30:56', '2023-12-13 14:49:49', 0.314577490091324, 31457);
INSERT INTO `ChiTietHD_Phong` (`MaHoaDon`, `MaPhong`, `GioVao`, `GioRa`, `TongGioSuDung`, `ThanhTien`) VALUES ('HD04131223', 'P103VIP', '2023-12-13 14:42:40', '2023-12-13 14:49:49', 0.118996948003769, 23799);
INSERT INTO `ChiTietHD_Phong` (`MaHoaDon`, `MaPhong`, `GioVao`, `GioRa`, `TongGioSuDung`, `ThanhTien`) VALUES ('HD04131223', 'P201', '2023-12-13 14:48:48', '2023-12-13 14:49:49', 0.0168183334171772, 1681);
INSERT INTO `ChiTietHD_Phong` (`MaHoaDon`, `MaPhong`, `GioVao`, `GioRa`, `TongGioSuDung`, `ThanhTien`) VALUES ('HD04180123', 'P201', '2023-01-18 10:06:34', '2023-01-18 16:08:34', 6.03349685668945, 603349);
INSERT INTO `ChiTietHD_Phong` (`MaHoaDon`, `MaPhong`, `GioVao`, `GioRa`, `TongGioSuDung`, `ThanhTien`) VALUES ('HD04200223', 'P302', '2023-02-20 10:50:21', '2023-02-20 15:58:12', 5.13098001480103, 513098);
INSERT INTO `ChiTietHD_Phong` (`MaHoaDon`, `MaPhong`, `GioVao`, `GioRa`, `TongGioSuDung`, `ThanhTien`) VALUES ('HD05080423', 'P103VIP', '2023-04-08 14:04:59', '2023-04-08 17:05:50', 3.01420903205872, 602841);
INSERT INTO `ChiTietHD_Phong` (`MaHoaDon`, `MaPhong`, `GioVao`, `GioRa`, `TongGioSuDung`, `ThanhTien`) VALUES ('HD05131223', 'P102', '2023-12-13 14:52:43', '2023-12-13 15:01:49', 0.15170556306839, 15170);
INSERT INTO `ChiTietHD_Phong` (`MaHoaDon`, `MaPhong`, `GioVao`, `GioRa`, `TongGioSuDung`, `ThanhTien`) VALUES ('HD05131223', 'P104VIP', '2023-12-13 15:00:59', '2023-12-13 15:01:00', 0.000277777784503996, 55);
INSERT INTO `ChiTietHD_Phong` (`MaHoaDon`, `MaPhong`, `GioVao`, `GioRa`, `TongGioSuDung`, `ThanhTien`) VALUES ('HD05131223', 'P204VIP', '2023-12-13 15:01:49', '2050-01-01 00:00:00', NULL, NULL);
INSERT INTO `ChiTietHD_Phong` (`MaHoaDon`, `MaPhong`, `GioVao`, `GioRa`, `TongGioSuDung`, `ThanhTien`) VALUES ('HD05200223', 'P201', '2023-02-20 11:30:34', '2023-02-20 14:41:06', 3.17579507827759, 317579);
INSERT INTO `ChiTietHD_Phong` (`MaHoaDon`, `MaPhong`, `GioVao`, `GioRa`, `TongGioSuDung`, `ThanhTien`) VALUES ('HD06080423', 'P401VIP', '2023-04-08 17:06:11', '2023-04-08 22:06:34', 5.00632953643799, 1251582);
INSERT INTO `ChiTietHD_Phong` (`MaHoaDon`, `MaPhong`, `GioVao`, `GioRa`, `TongGioSuDung`, `ThanhTien`) VALUES ('HD06131223', 'P202', '2023-12-13 15:07:03', '2023-12-13 15:07:04', 0.000277777784503996, 27);
INSERT INTO `ChiTietHD_Phong` (`MaHoaDon`, `MaPhong`, `GioVao`, `GioRa`, `TongGioSuDung`, `ThanhTien`) VALUES ('HD07080423', 'P402', '2023-04-08 18:06:27', '2023-04-08 22:07:12', 4.01241159439087, 601861);
COMMIT;

-- ----------------------------
-- Table structure for DichVu
-- ----------------------------
DROP TABLE IF EXISTS `DichVu`;
CREATE TABLE `DichVu` (
  `MaDichVu` varchar(255) NOT NULL,
  `TenDichVu` varchar(255) DEFAULT NULL,
  `SoLuongTon` int(11) DEFAULT NULL,
  `DonViTinh` varchar(255) DEFAULT NULL,
  `DonGia` int(11) DEFAULT NULL,
  `AnhMinhHoa` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`MaDichVu`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of DichVu
-- ----------------------------
BEGIN;
INSERT INTO `DichVu` (`MaDichVu`, `TenDichVu`, `SoLuongTon`, `DonViTinh`, `DonGia`, `AnhMinhHoa`) VALUES ('DV001', 'Bia Heneiken', 99, 'Lon', 30000, 'bia-heineken.png');
INSERT INTO `DichVu` (`MaDichVu`, `TenDichVu`, `SoLuongTon`, `DonViTinh`, `DonGia`, `AnhMinhHoa`) VALUES ('DV002', 'Bia Tiger', 30, 'Lon', 24000, 'bia_tiger.png');
INSERT INTO `DichVu` (`MaDichVu`, `TenDichVu`, `SoLuongTon`, `DonViTinh`, `DonGia`, `AnhMinhHoa`) VALUES ('DV003', 'Bưởi', 15, 'Dĩa', 100000, 'buoi.png');
INSERT INTO `DichVu` (`MaDichVu`, `TenDichVu`, `SoLuongTon`, `DonViTinh`, `DonGia`, `AnhMinhHoa`) VALUES ('DV004', 'Xoài', 12, 'Dĩa', 72000, 'xoai.png');
INSERT INTO `DichVu` (`MaDichVu`, `TenDichVu`, `SoLuongTon`, `DonViTinh`, `DonGia`, `AnhMinhHoa`) VALUES ('DV005', 'Cocacola', 87, 'Lon', 20000, 'coca-cola.png');
INSERT INTO `DichVu` (`MaDichVu`, `TenDichVu`, `SoLuongTon`, `DonViTinh`, `DonGia`, `AnhMinhHoa`) VALUES ('DV006', 'Sting', 37, 'Lon', 18000, 'sting.png');
INSERT INTO `DichVu` (`MaDichVu`, `TenDichVu`, `SoLuongTon`, `DonViTinh`, `DonGia`, `AnhMinhHoa`) VALUES ('DV007', 'Mèo đen', 32, 'Gói', 22000, 'thuoc-la-meo-den.png');
INSERT INTO `DichVu` (`MaDichVu`, `TenDichVu`, `SoLuongTon`, `DonViTinh`, `DonGia`, `AnhMinhHoa`) VALUES ('DV008', 'Bánh mì ngọt', 34, 'Bịch', 8000, 'banh-mi-ngot.png');
INSERT INTO `DichVu` (`MaDichVu`, `TenDichVu`, `SoLuongTon`, `DonViTinh`, `DonGia`, `AnhMinhHoa`) VALUES ('DV009', 'Khăn giấy', 483, 'Bịch', 24000, 'khan-giay.png');
INSERT INTO `DichVu` (`MaDichVu`, `TenDichVu`, `SoLuongTon`, `DonViTinh`, `DonGia`, `AnhMinhHoa`) VALUES ('DV010', 'Bò húc', 76, 'Lon', 24000, 'bo-huc.png');
INSERT INTO `DichVu` (`MaDichVu`, `TenDichVu`, `SoLuongTon`, `DonViTinh`, `DonGia`, `AnhMinhHoa`) VALUES ('DV011', 'Trà ô long', 39, 'Chai', 18000, 'tra-o-long.png');
INSERT INTO `DichVu` (`MaDichVu`, `TenDichVu`, `SoLuongTon`, `DonViTinh`, `DonGia`, `AnhMinhHoa`) VALUES ('DV012', 'Mirinda xá xị', 29, 'Lon', 20000, 'mirinda-xa-xi.png');
INSERT INTO `DichVu` (`MaDichVu`, `TenDichVu`, `SoLuongTon`, `DonViTinh`, `DonGia`, `AnhMinhHoa`) VALUES ('DV013', 'Mirinda soda kem', 15, 'Lon', 24000, 'mirinda-soda-kem.png');
INSERT INTO `DichVu` (`MaDichVu`, `TenDichVu`, `SoLuongTon`, `DonViTinh`, `DonGia`, `AnhMinhHoa`) VALUES ('DV014', 'Strongbow nho', 10, 'Chai', 30000, 'strongbow-nho.png');
INSERT INTO `DichVu` (`MaDichVu`, `TenDichVu`, `SoLuongTon`, `DonViTinh`, `DonGia`, `AnhMinhHoa`) VALUES ('DV015', 'Strongbow dâu', 1, 'Chai', 30000, 'strongbow-dau.png');
INSERT INTO `DichVu` (`MaDichVu`, `TenDichVu`, `SoLuongTon`, `DonViTinh`, `DonGia`, `AnhMinhHoa`) VALUES ('DV016', 'Jet', 34, 'Gói', 40000, 'thuoc-la-jet.png');
INSERT INTO `DichVu` (`MaDichVu`, `TenDichVu`, `SoLuongTon`, `DonViTinh`, `DonGia`, `AnhMinhHoa`) VALUES ('DV017', 'Khăn lạnh', 289, 'Bịch', 43200, 'khan-lanh.png');
INSERT INTO `DichVu` (`MaDichVu`, `TenDichVu`, `SoLuongTon`, `DonViTinh`, `DonGia`, `AnhMinhHoa`) VALUES ('DV018', 'Bia Saigon Special', 22, 'Thùng', 370000, 'bia-saigon-special.png');
INSERT INTO `DichVu` (`MaDichVu`, `TenDichVu`, `SoLuongTon`, `DonViTinh`, `DonGia`, `AnhMinhHoa`) VALUES ('DV019', 'Oishi ớt', 41, 'Bịch', 18000, 'oshi-ot.png');
INSERT INTO `DichVu` (`MaDichVu`, `TenDichVu`, `SoLuongTon`, `DonViTinh`, `DonGia`, `AnhMinhHoa`) VALUES ('DV020', 'Snack Poca bò lúc lắc', 36, 'Bịch', 10000, 'poca-bo-luc-lac.png');
INSERT INTO `DichVu` (`MaDichVu`, `TenDichVu`, `SoLuongTon`, `DonViTinh`, `DonGia`, `AnhMinhHoa`) VALUES ('DV021', 'Dừa', 7, 'Trái', 15000, 'dua.png');
INSERT INTO `DichVu` (`MaDichVu`, `TenDichVu`, `SoLuongTon`, `DonViTinh`, `DonGia`, `AnhMinhHoa`) VALUES ('DV022', 'Bia Tiger Bạc', 126, 'Lon', 24000, 'bia-tiger-bac.png');
INSERT INTO `DichVu` (`MaDichVu`, `TenDichVu`, `SoLuongTon`, `DonViTinh`, `DonGia`, `AnhMinhHoa`) VALUES ('DV023', 'Mực nướng', 19, 'Con', 72000, 'muc-nuong.png');
INSERT INTO `DichVu` (`MaDichVu`, `TenDichVu`, `SoLuongTon`, `DonViTinh`, `DonGia`, `AnhMinhHoa`) VALUES ('DV024', 'Táo', 7, 'Trái', 24000, 'buoi.png');
COMMIT;

-- ----------------------------
-- Table structure for HoaDonThanhToan
-- ----------------------------
DROP TABLE IF EXISTS `HoaDonThanhToan`;
CREATE TABLE `HoaDonThanhToan` (
  `MaHoaDon` varchar(255) NOT NULL,
  `MaKhachHang` varchar(255) DEFAULT NULL,
  `MaNhanVien` varchar(255) DEFAULT NULL,
  `MaKhuyenMai` varchar(255) DEFAULT NULL,
  `NgayLap` datetime DEFAULT NULL,
  `TongTien` int(11) DEFAULT NULL,
  PRIMARY KEY (`MaHoaDon`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of HoaDonThanhToan
-- ----------------------------
BEGIN;
INSERT INTO `HoaDonThanhToan` (`MaHoaDon`, `MaKhachHang`, `MaNhanVien`, `MaKhuyenMai`, `NgayLap`, `TongTien`) VALUES ('HD01021223', 'KH0015', 'NV0103', 'DEFAULT', '2023-12-02 22:03:43', 3650624);
INSERT INTO `HoaDonThanhToan` (`MaHoaDon`, `MaKhachHang`, `MaNhanVien`, `MaKhuyenMai`, `NgayLap`, `TongTien`) VALUES ('HD01041223', 'KH0023', 'NV0103', 'DEFAULT', '2023-12-04 13:06:03', 967908);
INSERT INTO `HoaDonThanhToan` (`MaHoaDon`, `MaKhachHang`, `MaNhanVien`, `MaKhuyenMai`, `NgayLap`, `TongTien`) VALUES ('HD01060623', 'KH0017', 'NV0103', 'DEFAULT', '2023-06-06 13:09:59', 1946367);
INSERT INTO `HoaDonThanhToan` (`MaHoaDon`, `MaKhachHang`, `MaNhanVien`, `MaKhuyenMai`, `NgayLap`, `TongTien`) VALUES ('HD01080423', 'KH0010', 'NV0103', 'DEFAULT', '2023-04-08 14:05:38', 846122);
INSERT INTO `HoaDonThanhToan` (`MaHoaDon`, `MaKhachHang`, `MaNhanVien`, `MaKhuyenMai`, `NgayLap`, `TongTien`) VALUES ('HD01081223', 'KH0006', 'NV0103', 'DEFAULT', '2023-12-08 18:11:53', 1006876);
INSERT INTO `HoaDonThanhToan` (`MaHoaDon`, `MaKhachHang`, `MaNhanVien`, `MaKhuyenMai`, `NgayLap`, `TongTien`) VALUES ('HD01090623', 'KH0020', 'NV0103', 'DEFAULT', '2023-06-09 15:12:45', 1263595);
INSERT INTO `HoaDonThanhToan` (`MaHoaDon`, `MaKhachHang`, `MaNhanVien`, `MaKhuyenMai`, `NgayLap`, `TongTien`) VALUES ('HD01131223', 'KH0020', 'NV0103', 'DEFAULT', '2023-12-13 08:15:06', 415761);
INSERT INTO `HoaDonThanhToan` (`MaHoaDon`, `MaKhachHang`, `MaNhanVien`, `MaKhuyenMai`, `NgayLap`, `TongTien`) VALUES ('HD01161222', 'KH0019', 'NV0103', 'DEFAULT', '2022-12-16 12:06:22', 1131821);
INSERT INTO `HoaDonThanhToan` (`MaHoaDon`, `MaKhachHang`, `MaNhanVien`, `MaKhuyenMai`, `NgayLap`, `TongTien`) VALUES ('HD01171123', 'KH0003', 'NV0103', 'DEFAULT', '2023-11-17 15:20:11', 833785);
INSERT INTO `HoaDonThanhToan` (`MaHoaDon`, `MaKhachHang`, `MaNhanVien`, `MaKhuyenMai`, `NgayLap`, `TongTien`) VALUES ('HD01180123', 'KH0001', 'NV0103', 'DEFAULT', '2023-01-18 16:08:28', 634152);
INSERT INTO `HoaDonThanhToan` (`MaHoaDon`, `MaKhachHang`, `MaNhanVien`, `MaKhuyenMai`, `NgayLap`, `TongTien`) VALUES ('HD01200223', 'KH0005', 'NV0103', 'DEFAULT', '2023-02-20 11:31:53', 721572);
INSERT INTO `HoaDonThanhToan` (`MaHoaDon`, `MaKhachHang`, `MaNhanVien`, `MaKhuyenMai`, `NgayLap`, `TongTien`) VALUES ('HD01251123', 'KH0024', 'NV0103', 'DEFAULT', '2023-11-25 14:17:43', 665882);
INSERT INTO `HoaDonThanhToan` (`MaHoaDon`, `MaKhachHang`, `MaNhanVien`, `MaKhuyenMai`, `NgayLap`, `TongTien`) VALUES ('HD01281123', 'KH0011', 'NV0103', 'DEFAULT', '2023-11-28 18:01:21', 323286);
INSERT INTO `HoaDonThanhToan` (`MaHoaDon`, `MaKhachHang`, `MaNhanVien`, `MaKhuyenMai`, `NgayLap`, `TongTien`) VALUES ('HD01291122', 'KH0008', 'NV0103', 'DEFAULT', '2022-11-29 16:02:22', 513805);
INSERT INTO `HoaDonThanhToan` (`MaHoaDon`, `MaKhachHang`, `MaNhanVien`, `MaKhuyenMai`, `NgayLap`, `TongTien`) VALUES ('HD02021223', 'KH0020', 'NV0103', 'DEFAULT', '2023-12-02 23:04:13', 790224);
INSERT INTO `HoaDonThanhToan` (`MaHoaDon`, `MaKhachHang`, `MaNhanVien`, `MaKhuyenMai`, `NgayLap`, `TongTien`) VALUES ('HD02041223', 'KH0013', 'NV0103', 'DEFAULT', '2023-12-04 13:06:16', 841926);
INSERT INTO `HoaDonThanhToan` (`MaHoaDon`, `MaKhachHang`, `MaNhanVien`, `MaKhuyenMai`, `NgayLap`, `TongTien`) VALUES ('HD02060623', 'KH0018', 'NV0103', 'DEFAULT', '2023-06-06 14:10:10', 631789);
INSERT INTO `HoaDonThanhToan` (`MaHoaDon`, `MaKhachHang`, `MaNhanVien`, `MaKhuyenMai`, `NgayLap`, `TongTien`) VALUES ('HD02080423', 'KH0011', 'NV0103', 'DEFAULT', '2023-04-08 12:04:24', 421289);
INSERT INTO `HoaDonThanhToan` (`MaHoaDon`, `MaKhachHang`, `MaNhanVien`, `MaKhuyenMai`, `NgayLap`, `TongTien`) VALUES ('HD02081223', 'KH0008', 'NV0103', 'DEFAULT', '2023-12-08 18:12:17', 1630045);
INSERT INTO `HoaDonThanhToan` (`MaHoaDon`, `MaKhachHang`, `MaNhanVien`, `MaKhuyenMai`, `NgayLap`, `TongTien`) VALUES ('HD02090623', 'KH0021', 'NV0103', 'DEFAULT', '2023-06-09 15:13:03', 631890);
INSERT INTO `HoaDonThanhToan` (`MaHoaDon`, `MaKhachHang`, `MaNhanVien`, `MaKhuyenMai`, `NgayLap`, `TongTien`) VALUES ('HD02131223', 'KH0016', 'NV0103', 'DEFAULT', '2023-12-13 16:15:31', 1683778);
INSERT INTO `HoaDonThanhToan` (`MaHoaDon`, `MaKhachHang`, `MaNhanVien`, `MaKhuyenMai`, `NgayLap`, `TongTien`) VALUES ('HD02171123', 'KH0006', 'NV0103', 'DEFAULT', '2023-11-17 15:20:23', 631536);
INSERT INTO `HoaDonThanhToan` (`MaHoaDon`, `MaKhachHang`, `MaNhanVien`, `MaKhuyenMai`, `NgayLap`, `TongTien`) VALUES ('HD02180123', 'KH0002', 'NV0103', 'DEFAULT', '2023-01-18 15:07:56', 1729807);
INSERT INTO `HoaDonThanhToan` (`MaHoaDon`, `MaKhachHang`, `MaNhanVien`, `MaKhuyenMai`, `NgayLap`, `TongTien`) VALUES ('HD02200223', 'KH0006', 'NV0103', 'DEFAULT', '2023-02-20 11:33:14', 4107210);
INSERT INTO `HoaDonThanhToan` (`MaHoaDon`, `MaKhachHang`, `MaNhanVien`, `MaKhuyenMai`, `NgayLap`, `TongTien`) VALUES ('HD02251123', 'KH0001', 'NV0103', 'DEFAULT', '2023-11-25 14:17:53', 527291);
INSERT INTO `HoaDonThanhToan` (`MaHoaDon`, `MaKhachHang`, `MaNhanVien`, `MaKhuyenMai`, `NgayLap`, `TongTien`) VALUES ('HD02281123', 'KH0012', 'NV0103', 'DEFAULT', '2023-11-28 18:01:08', 2372785);
INSERT INTO `HoaDonThanhToan` (`MaHoaDon`, `MaKhachHang`, `MaNhanVien`, `MaKhuyenMai`, `NgayLap`, `TongTien`) VALUES ('HD02291122', 'KH0005', 'NV0103', 'DEFAULT', '2022-11-29 17:40:09', 922328);
INSERT INTO `HoaDonThanhToan` (`MaHoaDon`, `MaKhachHang`, `MaNhanVien`, `MaKhuyenMai`, `NgayLap`, `TongTien`) VALUES ('HD03021223', 'KH0023', 'NV0103', 'DEFAULT', '2023-12-02 22:03:56', 631648);
INSERT INTO `HoaDonThanhToan` (`MaHoaDon`, `MaKhachHang`, `MaNhanVien`, `MaKhuyenMai`, `NgayLap`, `TongTien`) VALUES ('HD03041223', 'KH0006', 'NV0103', 'DEFAULT', '2023-12-04 23:07:01', 1103268);
INSERT INTO `HoaDonThanhToan` (`MaHoaDon`, `MaKhachHang`, `MaNhanVien`, `MaKhuyenMai`, `NgayLap`, `TongTien`) VALUES ('HD03060623', 'KH0019', 'NV0103', 'DEFAULT', '2023-06-06 17:10:42', 1074872);
INSERT INTO `HoaDonThanhToan` (`MaHoaDon`, `MaKhachHang`, `MaNhanVien`, `MaKhuyenMai`, `NgayLap`, `TongTien`) VALUES ('HD03080423', 'KH0012', 'NV0103', 'DEFAULT', '2023-04-08 14:04:42', 420792);
INSERT INTO `HoaDonThanhToan` (`MaHoaDon`, `MaKhachHang`, `MaNhanVien`, `MaKhuyenMai`, `NgayLap`, `TongTien`) VALUES ('HD03081223', 'KH0013', 'NV0103', 'DEFAULT', '2023-12-08 18:12:38', 635775);
INSERT INTO `HoaDonThanhToan` (`MaHoaDon`, `MaKhachHang`, `MaNhanVien`, `MaKhuyenMai`, `NgayLap`, `TongTien`) VALUES ('HD03090623', 'KH0022', 'NV0103', 'DEFAULT', '2023-06-09 18:13:35', 1150487);
INSERT INTO `HoaDonThanhToan` (`MaHoaDon`, `MaKhachHang`, `MaNhanVien`, `MaKhuyenMai`, `NgayLap`, `TongTien`) VALUES ('HD03131223', 'KH0019', 'NV0103', 'SALES1212', '2023-12-13 14:56:07', 678417);
INSERT INTO `HoaDonThanhToan` (`MaHoaDon`, `MaKhachHang`, `MaNhanVien`, `MaKhuyenMai`, `NgayLap`, `TongTien`) VALUES ('HD03171123', 'KH0011', 'NV0103', 'DEFAULT', '2023-11-17 15:57:36', 1392148);
INSERT INTO `HoaDonThanhToan` (`MaHoaDon`, `MaKhachHang`, `MaNhanVien`, `MaKhuyenMai`, `NgayLap`, `TongTien`) VALUES ('HD03180123', 'KH0003', 'NV0103', 'DEFAULT', '2023-01-18 16:46:05', 1881580);
INSERT INTO `HoaDonThanhToan` (`MaHoaDon`, `MaKhachHang`, `MaNhanVien`, `MaKhuyenMai`, `NgayLap`, `TongTien`) VALUES ('HD03200223', 'KH0007', 'NV0103', 'DEFAULT', '2023-02-20 14:33:36', 390730);
INSERT INTO `HoaDonThanhToan` (`MaHoaDon`, `MaKhachHang`, `MaNhanVien`, `MaKhuyenMai`, `NgayLap`, `TongTien`) VALUES ('HD03251123', 'KH0003', 'NV0103', 'DEFAULT', '2023-11-25 16:18:09', 737511);
INSERT INTO `HoaDonThanhToan` (`MaHoaDon`, `MaKhachHang`, `MaNhanVien`, `MaKhuyenMai`, `NgayLap`, `TongTien`) VALUES ('HD03291122', 'KH0003', 'NV0103', 'DEFAULT', '2022-11-29 19:41:20', 714984);
INSERT INTO `HoaDonThanhToan` (`MaHoaDon`, `MaKhachHang`, `MaNhanVien`, `MaKhuyenMai`, `NgayLap`, `TongTien`) VALUES ('HD04080423', 'KH0013', 'NV0103', 'DEFAULT', '2023-04-08 14:05:15', 421515);
INSERT INTO `HoaDonThanhToan` (`MaHoaDon`, `MaKhachHang`, `MaNhanVien`, `MaKhuyenMai`, `NgayLap`, `TongTien`) VALUES ('HD04081223', 'KH0016', 'NV0103', 'DEFAULT', '2023-12-08 18:13:10', 1092250);
INSERT INTO `HoaDonThanhToan` (`MaHoaDon`, `MaKhachHang`, `MaNhanVien`, `MaKhuyenMai`, `NgayLap`, `TongTien`) VALUES ('HD04090623', 'KH0023', 'NV0103', 'DEFAULT', '2023-06-09 18:13:50', 948536);
INSERT INTO `HoaDonThanhToan` (`MaHoaDon`, `MaKhachHang`, `MaNhanVien`, `MaKhuyenMai`, `NgayLap`, `TongTien`) VALUES ('HD04131223', 'KH0009', 'NV0103', 'DEFAULT', '2023-12-13 14:49:57', 116483);
INSERT INTO `HoaDonThanhToan` (`MaHoaDon`, `MaKhachHang`, `MaNhanVien`, `MaKhuyenMai`, `NgayLap`, `TongTien`) VALUES ('HD04180123', 'KH0004', 'NV0103', 'DEFAULT', '2023-01-18 16:08:42', 633516);
INSERT INTO `HoaDonThanhToan` (`MaHoaDon`, `MaKhachHang`, `MaNhanVien`, `MaKhuyenMai`, `NgayLap`, `TongTien`) VALUES ('HD04200223', 'KH0008', 'NV0103', 'DEFAULT', '2023-02-20 15:58:19', 572352);
INSERT INTO `HoaDonThanhToan` (`MaHoaDon`, `MaKhachHang`, `MaNhanVien`, `MaKhuyenMai`, `NgayLap`, `TongTien`) VALUES ('HD05080423', 'KH0014', 'NV0103', 'DEFAULT', '2023-04-08 17:05:59', 632983);
INSERT INTO `HoaDonThanhToan` (`MaHoaDon`, `MaKhachHang`, `MaNhanVien`, `MaKhuyenMai`, `NgayLap`, `TongTien`) VALUES ('HD05131223', 'KH0009', 'NV0103', NULL, '2023-12-13 14:52:43', NULL);
INSERT INTO `HoaDonThanhToan` (`MaHoaDon`, `MaKhachHang`, `MaNhanVien`, `MaKhuyenMai`, `NgayLap`, `TongTien`) VALUES ('HD05200223', 'KH0009', 'NV0103', 'DEFAULT', '2023-02-20 14:41:12', 472057);
INSERT INTO `HoaDonThanhToan` (`MaHoaDon`, `MaKhachHang`, `MaNhanVien`, `MaKhuyenMai`, `NgayLap`, `TongTien`) VALUES ('HD06080423', 'KH0015', 'NV0103', 'DEFAULT', '2023-04-08 22:06:49', 1314161);
INSERT INTO `HoaDonThanhToan` (`MaHoaDon`, `MaKhachHang`, `MaNhanVien`, `MaKhuyenMai`, `NgayLap`, `TongTien`) VALUES ('HD06131223', 'KH0009', 'NV0103', NULL, '2023-12-13 15:07:03', NULL);
INSERT INTO `HoaDonThanhToan` (`MaHoaDon`, `MaKhachHang`, `MaNhanVien`, `MaKhuyenMai`, `NgayLap`, `TongTien`) VALUES ('HD07080423', 'KH0016', 'NV0103', 'DEFAULT', '2023-04-08 22:07:20', 682354);
COMMIT;

-- ----------------------------
-- Table structure for KhachHang
-- ----------------------------
DROP TABLE IF EXISTS `KhachHang`;
CREATE TABLE `KhachHang` (
  `MaKhachHang` varchar(255) NOT NULL,
  `TenKhachHang` varchar(255) DEFAULT NULL,
  `SoDienThoai` int(11) DEFAULT NULL,
  `NamSinh` int(11) DEFAULT NULL,
  `GioiTinh` int(11) DEFAULT NULL,
  PRIMARY KEY (`MaKhachHang`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of KhachHang
-- ----------------------------
BEGIN;
INSERT INTO `KhachHang` (`MaKhachHang`, `TenKhachHang`, `SoDienThoai`, `NamSinh`, `GioiTinh`) VALUES ('KH0001', 'Nguyễn Vân Anh', 346598761, 1997, 0);
INSERT INTO `KhachHang` (`MaKhachHang`, `TenKhachHang`, `SoDienThoai`, `NamSinh`, `GioiTinh`) VALUES ('KH0002', 'Ngyễn Quý Hà', 393357382, 1992, 1);
INSERT INTO `KhachHang` (`MaKhachHang`, `TenKhachHang`, `SoDienThoai`, `NamSinh`, `GioiTinh`) VALUES ('KH0003', 'Trần Bình Quý', 971756347, 2002, 1);
INSERT INTO `KhachHang` (`MaKhachHang`, `TenKhachHang`, `SoDienThoai`, `NamSinh`, `GioiTinh`) VALUES ('KH0004', 'Lê Bảo Minh', 862015761, 2001, 1);
INSERT INTO `KhachHang` (`MaKhachHang`, `TenKhachHang`, `SoDienThoai`, `NamSinh`, `GioiTinh`) VALUES ('KH0005', 'Nguyễn Long Thái', 856485761, 1998, 1);
INSERT INTO `KhachHang` (`MaKhachHang`, `TenKhachHang`, `SoDienThoai`, `NamSinh`, `GioiTinh`) VALUES ('KH0006', 'Hoàng Xuân Được', 342521761, 1999, 1);
INSERT INTO `KhachHang` (`MaKhachHang`, `TenKhachHang`, `SoDienThoai`, `NamSinh`, `GioiTinh`) VALUES ('KH0007', 'Phạm Văn Đồng', 918237411, 1999, 1);
INSERT INTO `KhachHang` (`MaKhachHang`, `TenKhachHang`, `SoDienThoai`, `NamSinh`, `GioiTinh`) VALUES ('KH0008', 'Nguyễn Thị Lam', 258995957, 2003, 0);
INSERT INTO `KhachHang` (`MaKhachHang`, `TenKhachHang`, `SoDienThoai`, `NamSinh`, `GioiTinh`) VALUES ('KH0009', 'Phạm Trọng Hiếu', 192837431, 1997, 1);
INSERT INTO `KhachHang` (`MaKhachHang`, `TenKhachHang`, `SoDienThoai`, `NamSinh`, `GioiTinh`) VALUES ('KH0010', 'Nguyễn Xuân Diệu', 854240923, 1990, 1);
INSERT INTO `KhachHang` (`MaKhachHang`, `TenKhachHang`, `SoDienThoai`, `NamSinh`, `GioiTinh`) VALUES ('KH0011', 'Trần Văn Lộc', 882346571, 1992, 1);
INSERT INTO `KhachHang` (`MaKhachHang`, `TenKhachHang`, `SoDienThoai`, `NamSinh`, `GioiTinh`) VALUES ('KH0012', 'Đặng Việt Hùng', 982151290, 1996, 1);
INSERT INTO `KhachHang` (`MaKhachHang`, `TenKhachHang`, `SoDienThoai`, `NamSinh`, `GioiTinh`) VALUES ('KH0013', 'Đỗ Văn Tuấn', 355241428, 1995, 1);
INSERT INTO `KhachHang` (`MaKhachHang`, `TenKhachHang`, `SoDienThoai`, `NamSinh`, `GioiTinh`) VALUES ('KH0014', 'Cao Trọng Tín', 336548521, 2001, 1);
INSERT INTO `KhachHang` (`MaKhachHang`, `TenKhachHang`, `SoDienThoai`, `NamSinh`, `GioiTinh`) VALUES ('KH0015', 'Cao Nguyễn Tiến Đạt', 795477345, 1993, 1);
INSERT INTO `KhachHang` (`MaKhachHang`, `TenKhachHang`, `SoDienThoai`, `NamSinh`, `GioiTinh`) VALUES ('KH0016', 'Nguyễn Hồ Nhật Thiện', 332589746, 2003, 1);
INSERT INTO `KhachHang` (`MaKhachHang`, `TenKhachHang`, `SoDienThoai`, `NamSinh`, `GioiTinh`) VALUES ('KH0017', 'Phạm Thị Đài Gương', 379359006, 1989, 0);
INSERT INTO `KhachHang` (`MaKhachHang`, `TenKhachHang`, `SoDienThoai`, `NamSinh`, `GioiTinh`) VALUES ('KH0018', 'Lê Hồng Phúc', 845698725, 2001, 1);
INSERT INTO `KhachHang` (`MaKhachHang`, `TenKhachHang`, `SoDienThoai`, `NamSinh`, `GioiTinh`) VALUES ('KH0019', 'Nguyễn Anh Thi', 393504301, 2002, 0);
INSERT INTO `KhachHang` (`MaKhachHang`, `TenKhachHang`, `SoDienThoai`, `NamSinh`, `GioiTinh`) VALUES ('KH0020', 'Huỳnh Tâm Tú', 339871226, 2002, 1);
INSERT INTO `KhachHang` (`MaKhachHang`, `TenKhachHang`, `SoDienThoai`, `NamSinh`, `GioiTinh`) VALUES ('KH0021', 'Nguyễn Châu Pha', 987223143, 1993, 0);
INSERT INTO `KhachHang` (`MaKhachHang`, `TenKhachHang`, `SoDienThoai`, `NamSinh`, `GioiTinh`) VALUES ('KH0022', 'Võ Văn Ngân', 997863612, 2003, 0);
INSERT INTO `KhachHang` (`MaKhachHang`, `TenKhachHang`, `SoDienThoai`, `NamSinh`, `GioiTinh`) VALUES ('KH0023', 'Lê Văn Thắng', 399379987, 1986, 1);
INSERT INTO `KhachHang` (`MaKhachHang`, `TenKhachHang`, `SoDienThoai`, `NamSinh`, `GioiTinh`) VALUES ('KH0024', 'Lê Thiên Long', 987998883, 2002, 1);
COMMIT;

-- ----------------------------
-- Table structure for NhanVien
-- ----------------------------
DROP TABLE IF EXISTS `NhanVien`;
CREATE TABLE `NhanVien` (
  `MaNhanVien` varchar(255) NOT NULL,
  `HoTen` varchar(255) DEFAULT NULL,
  `CCCD` varchar(255) DEFAULT NULL,
  `SoDienThoai` int(11) DEFAULT NULL,
  `NgaySinh` datetime DEFAULT NULL,
  `DiaChi` varchar(255) DEFAULT NULL,
  `GioiTinh` int(11) DEFAULT NULL,
  `ChucVu` varchar(255) DEFAULT NULL,
  `TrangThai` varchar(255) DEFAULT NULL,
  `AnhDaiDien` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`MaNhanVien`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of NhanVien
-- ----------------------------
BEGIN;
INSERT INTO `NhanVien` (`MaNhanVien`, `HoTen`, `CCCD`, `SoDienThoai`, `NgaySinh`, `DiaChi`, `GioiTinh`, `ChucVu`, `TrangThai`, `AnhDaiDien`) VALUES ('NV0102', 'Ngô Gia Đạt', '987654321012', 901234567, '2001-11-12 00:00:00', '48 Nguyễn Tuân, P3, Gò Vấp, TP HCM', 1, 'Quản lý', 'CONLAMVIEC', 'avt_employee4.jpg');
INSERT INTO `NhanVien` (`MaNhanVien`, `HoTen`, `CCCD`, `SoDienThoai`, `NgaySinh`, `DiaChi`, `GioiTinh`, `ChucVu`, `TrangThai`, `AnhDaiDien`) VALUES ('NV0103', 'An Quốc Việt', '035456789012', 912345678, '2003-11-25 00:00:00', '12 Nguyễn Văn Bảo, P4, Gò Vấp, TP HCM', 1, 'Quản lý', 'Còn làm việc', 'avt_employee3.jpg');
INSERT INTO `NhanVien` (`MaNhanVien`, `HoTen`, `CCCD`, `SoDienThoai`, `NgaySinh`, `DiaChi`, `GioiTinh`, `ChucVu`, `TrangThai`, `AnhDaiDien`) VALUES ('NV0190', 'Nguyễn Anh Khải', '678901234567', 976543210, '1991-03-30 00:00:00', '512 Phạm Văn Đồng, P3, Gò Vấp, TP HCM', 1, 'Bảo vệ', 'CONLAMVIEC', 'avt_employee4.jpg');
INSERT INTO `NhanVien` (`MaNhanVien`, `HoTen`, `CCCD`, `SoDienThoai`, `NgaySinh`, `DiaChi`, `GioiTinh`, `ChucVu`, `TrangThai`, `AnhDaiDien`) VALUES ('NV0198', 'Lê Thị Mơ', '567890123456', 909876543, '1998-12-12 00:00:00', '127 Phạm Văn Đồng, Hiệp Bình Chánh, Thủ Đức', 0, 'Nhân viên tiếp tân', 'Đã nghỉ việc', 'avt_employee.jpg');
INSERT INTO `NhanVien` (`MaNhanVien`, `HoTen`, `CCCD`, `SoDienThoai`, `NgaySinh`, `DiaChi`, `GioiTinh`, `ChucVu`, `TrangThai`, `AnhDaiDien`) VALUES ('NV0203', 'Nguyễn Kiến Thức', '456123789012', 976543210, '2003-12-04 00:00:00', '14 Nguyễn Văn Công, P3, Gò Vấp, TP HCM', 1, 'Nhân viên tiếp tân', 'Còn làm việc', 'avt_employee5.jpg');
INSERT INTO `NhanVien` (`MaNhanVien`, `HoTen`, `CCCD`, `SoDienThoai`, `NgaySinh`, `DiaChi`, `GioiTinh`, `ChucVu`, `TrangThai`, `AnhDaiDien`) VALUES ('NV0290', 'Nguyễn Thị Thu Hồng', '789012345678', 987654321, '1990-04-05 00:00:00', '69 Nguyễn Kiệm, P3, Gò Vấp, TP HCM', 0, 'Nhân viên phục vụ', 'Còn làm việc', 'avt_employee10.jpg');
INSERT INTO `NhanVien` (`MaNhanVien`, `HoTen`, `CCCD`, `SoDienThoai`, `NgaySinh`, `DiaChi`, `GioiTinh`, `ChucVu`, `TrangThai`, `AnhDaiDien`) VALUES ('NV0303', 'Nguyễn Duy Thanh', '789321456012', 987654321, '2003-03-17 00:00:00', '224 Nguyễn Xí, P23, Bình Thạnh, TP HCM', 1, 'Nhân viên phục vụ', 'Đã nghỉ việc', 'avt_employee6.jpg');
INSERT INTO `NhanVien` (`MaNhanVien`, `HoTen`, `CCCD`, `SoDienThoai`, `NgaySinh`, `DiaChi`, `GioiTinh`, `ChucVu`, `TrangThai`, `AnhDaiDien`) VALUES ('NV0403', 'Nguyễn Đức Thắng', '234567890123', 954321098, '2003-06-19 00:00:00', '16 Đường 17, Phường Linh Trung, TP Thủ Đức ', 1, 'Nhân viên phục vụ', 'Còn làm việc', 'avt_employee7.jpg');
INSERT INTO `NhanVien` (`MaNhanVien`, `HoTen`, `CCCD`, `SoDienThoai`, `NgaySinh`, `DiaChi`, `GioiTinh`, `ChucVu`, `TrangThai`, `AnhDaiDien`) VALUES ('NV0503', 'Nguyễn Văn Thạch', '345678901234', 912345678, '2003-02-23 00:00:00', '12/24 Cộng Hòa, Tân Bình, TP HCM', 1, 'Nhân viên tiếp tân', 'Còn làm việc', 'avt_employee8.jpg');
COMMIT;

-- ----------------------------
-- Table structure for PhieuDatPhong
-- ----------------------------
DROP TABLE IF EXISTS `PhieuDatPhong`;
CREATE TABLE `PhieuDatPhong` (
  `MaPhieuDat` varchar(255) NOT NULL,
  `MaPhong` varchar(255) DEFAULT NULL,
  `MaKhachHang` varchar(255) DEFAULT NULL,
  `MaNhanVien` varchar(255) DEFAULT NULL,
  `ThoiGianLap` datetime DEFAULT NULL,
  `ThoiGianNhan` datetime DEFAULT NULL,
  `GhiChu` varchar(255) DEFAULT NULL,
  `TinhTrang` int(11) DEFAULT NULL,
  PRIMARY KEY (`MaPhieuDat`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of PhieuDatPhong
-- ----------------------------
BEGIN;
INSERT INTO `PhieuDatPhong` (`MaPhieuDat`, `MaPhong`, `MaKhachHang`, `MaNhanVien`, `ThoiGianLap`, `ThoiGianNhan`, `GhiChu`, `TinhTrang`) VALUES ('PD01111523', 'P101', 'KH0011', 'NV0103', '2023-12-11 17:28:08', '2023-12-14 17:28:12', NULL, 1);
INSERT INTO `PhieuDatPhong` (`MaPhieuDat`, `MaPhong`, `MaKhachHang`, `MaNhanVien`, `ThoiGianLap`, `ThoiGianNhan`, `GhiChu`, `TinhTrang`) VALUES ('PD02111523', 'P101', 'KH0011', 'NV0103', '2023-12-11 17:28:08', '2023-12-14 17:28:12', NULL, 1);
INSERT INTO `PhieuDatPhong` (`MaPhieuDat`, `MaPhong`, `MaKhachHang`, `MaNhanVien`, `ThoiGianLap`, `ThoiGianNhan`, `GhiChu`, `TinhTrang`) VALUES ('PD03111523', 'P102', 'KH0002', 'NV0103', '2023-11-15 00:00:00', '2023-11-15 00:00:00', '', 1);
INSERT INTO `PhieuDatPhong` (`MaPhieuDat`, `MaPhong`, `MaKhachHang`, `MaNhanVien`, `ThoiGianLap`, `ThoiGianNhan`, `GhiChu`, `TinhTrang`) VALUES ('PD04111523', 'P201', 'KH0002', 'NV0103', '2023-11-15 00:00:00', '2023-11-15 00:00:00', '', 1);
INSERT INTO `PhieuDatPhong` (`MaPhieuDat`, `MaPhong`, `MaKhachHang`, `MaNhanVien`, `ThoiGianLap`, `ThoiGianNhan`, `GhiChu`, `TinhTrang`) VALUES ('PD05111523', 'P201', 'KH0002', 'NV0103', '2023-11-15 00:00:00', '2023-11-16 00:00:00', '', 1);
INSERT INTO `PhieuDatPhong` (`MaPhieuDat`, `MaPhong`, `MaKhachHang`, `MaNhanVien`, `ThoiGianLap`, `ThoiGianNhan`, `GhiChu`, `TinhTrang`) VALUES ('PD06112123', 'P102', 'KH0001', 'NV0103', '2023-11-21 00:00:00', '2023-11-22 00:00:00', '', 1);
INSERT INTO `PhieuDatPhong` (`MaPhieuDat`, `MaPhong`, `MaKhachHang`, `MaNhanVien`, `ThoiGianLap`, `ThoiGianNhan`, `GhiChu`, `TinhTrang`) VALUES ('PD07112923', 'P202', 'KH0011', 'NV0103', '2023-11-29 00:00:00', '2023-11-29 00:00:00', '', 1);
INSERT INTO `PhieuDatPhong` (`MaPhieuDat`, `MaPhong`, `MaKhachHang`, `MaNhanVien`, `ThoiGianLap`, `ThoiGianNhan`, `GhiChu`, `TinhTrang`) VALUES ('PD08120123', 'P203', 'KH0019', 'NV0103', '2023-12-01 00:00:00', '2023-12-01 00:00:00', '', 1);
INSERT INTO `PhieuDatPhong` (`MaPhieuDat`, `MaPhong`, `MaKhachHang`, `MaNhanVien`, `ThoiGianLap`, `ThoiGianNhan`, `GhiChu`, `TinhTrang`) VALUES ('PD09120423', 'P102', 'KH0019', 'NV0103', '2023-12-04 00:00:00', '2023-12-04 00:00:00', '', 1);
INSERT INTO `PhieuDatPhong` (`MaPhieuDat`, `MaPhong`, `MaKhachHang`, `MaNhanVien`, `ThoiGianLap`, `ThoiGianNhan`, `GhiChu`, `TinhTrang`) VALUES ('PD10120423', 'P103VIP', 'KH0019', 'NV0103', '2023-12-04 18:28:06', '2023-12-04 02:01:00', '', 1);
INSERT INTO `PhieuDatPhong` (`MaPhieuDat`, `MaPhong`, `MaKhachHang`, `MaNhanVien`, `ThoiGianLap`, `ThoiGianNhan`, `GhiChu`, `TinhTrang`) VALUES ('PD11120723', 'P104VIP', 'KH0014', 'NV0103', '2023-12-07 00:53:53', '2023-12-07 06:06:00', '', 1);
INSERT INTO `PhieuDatPhong` (`MaPhieuDat`, `MaPhong`, `MaKhachHang`, `MaNhanVien`, `ThoiGianLap`, `ThoiGianNhan`, `GhiChu`, `TinhTrang`) VALUES ('PD12120723', 'P101', 'KH0019', 'NV0103', '2023-12-07 13:20:44', '2023-12-08 18:06:00', '', 1);
INSERT INTO `PhieuDatPhong` (`MaPhieuDat`, `MaPhong`, `MaKhachHang`, `MaNhanVien`, `ThoiGianLap`, `ThoiGianNhan`, `GhiChu`, `TinhTrang`) VALUES ('PD13120723', 'P102', 'KH0008', 'NV0103', '2023-12-07 13:43:03', '2023-12-07 12:06:00', '', 1);
INSERT INTO `PhieuDatPhong` (`MaPhieuDat`, `MaPhong`, `MaKhachHang`, `MaNhanVien`, `ThoiGianLap`, `ThoiGianNhan`, `GhiChu`, `TinhTrang`) VALUES ('PD14120923', 'P102', 'KH0019', 'NV0103', '2023-12-09 14:39:52', '2023-12-09 04:55:00', '', 1);
INSERT INTO `PhieuDatPhong` (`MaPhieuDat`, `MaPhong`, `MaKhachHang`, `MaNhanVien`, `ThoiGianLap`, `ThoiGianNhan`, `GhiChu`, `TinhTrang`) VALUES ('PD15120923', 'P103VIP', 'KH0019', 'NV0103', '2023-12-09 14:43:50', '2023-12-09 16:00:00', '', 1);
INSERT INTO `PhieuDatPhong` (`MaPhieuDat`, `MaPhong`, `MaKhachHang`, `MaNhanVien`, `ThoiGianLap`, `ThoiGianNhan`, `GhiChu`, `TinhTrang`) VALUES ('PD16121323', 'P104VIP', 'KH0017', 'NV0103', '2023-12-13 13:35:15', '2023-12-15 00:00:00', '', 1);
INSERT INTO `PhieuDatPhong` (`MaPhieuDat`, `MaPhong`, `MaKhachHang`, `MaNhanVien`, `ThoiGianLap`, `ThoiGianNhan`, `GhiChu`, `TinhTrang`) VALUES ('PD17121323', 'P202', 'KH0017', 'NV0103', '2023-12-13 13:35:47', '2023-12-22 00:00:00', '', 1);
INSERT INTO `PhieuDatPhong` (`MaPhieuDat`, `MaPhong`, `MaKhachHang`, `MaNhanVien`, `ThoiGianLap`, `ThoiGianNhan`, `GhiChu`, `TinhTrang`) VALUES ('PD18121323', 'P203', 'KH0017', 'NV0103', '2023-12-13 13:38:21', '2023-12-14 00:00:00', '', 1);
INSERT INTO `PhieuDatPhong` (`MaPhieuDat`, `MaPhong`, `MaKhachHang`, `MaNhanVien`, `ThoiGianLap`, `ThoiGianNhan`, `GhiChu`, `TinhTrang`) VALUES ('PD19121323', 'P103VIP', 'KH0018', 'NV0103', '2023-12-13 16:48:27', '2023-12-13 19:30:00', '', 1);
INSERT INTO `PhieuDatPhong` (`MaPhieuDat`, `MaPhong`, `MaKhachHang`, `MaNhanVien`, `ThoiGianLap`, `ThoiGianNhan`, `GhiChu`, `TinhTrang`) VALUES ('PD20121323', 'P104VIP', 'KH0018', 'NV0103', '2023-12-13 17:01:41', '2023-12-13 19:00:00', '', 1);
INSERT INTO `PhieuDatPhong` (`MaPhieuDat`, `MaPhong`, `MaKhachHang`, `MaNhanVien`, `ThoiGianLap`, `ThoiGianNhan`, `GhiChu`, `TinhTrang`) VALUES ('PD21121323', 'P103VIP', 'KH0017', 'NV0103', '2023-12-13 17:32:13', '2023-12-13 19:00:00', '', 1);
INSERT INTO `PhieuDatPhong` (`MaPhieuDat`, `MaPhong`, `MaKhachHang`, `MaNhanVien`, `ThoiGianLap`, `ThoiGianNhan`, `GhiChu`, `TinhTrang`) VALUES ('PD22121323', 'P104VIP', 'KH0024', 'NV0103', '2023-12-13 17:33:53', '2023-12-13 22:00:00', '', 1);
INSERT INTO `PhieuDatPhong` (`MaPhieuDat`, `MaPhong`, `MaKhachHang`, `MaNhanVien`, `ThoiGianLap`, `ThoiGianNhan`, `GhiChu`, `TinhTrang`) VALUES ('PD23121323', 'P104VIP', 'KH0009', 'NV0103', '2023-12-13 08:53:18', '2023-12-13 15:00:00', '', 1);
INSERT INTO `PhieuDatPhong` (`MaPhieuDat`, `MaPhong`, `MaKhachHang`, `MaNhanVien`, `ThoiGianLap`, `ThoiGianNhan`, `GhiChu`, `TinhTrang`) VALUES ('PD24121323', 'P104VIP', 'KH0009', 'NV0103', '2023-12-13 14:51:19', '2023-12-13 08:00:00', '', 0);
INSERT INTO `PhieuDatPhong` (`MaPhieuDat`, `MaPhong`, `MaKhachHang`, `MaNhanVien`, `ThoiGianLap`, `ThoiGianNhan`, `GhiChu`, `TinhTrang`) VALUES ('PD25121323', 'P103VIP', 'KH0009', 'NV0103', '2023-12-13 14:57:26', '2023-12-13 20:30:00', '', 1);
INSERT INTO `PhieuDatPhong` (`MaPhieuDat`, `MaPhong`, `MaKhachHang`, `MaNhanVien`, `ThoiGianLap`, `ThoiGianNhan`, `GhiChu`, `TinhTrang`) VALUES ('PD26121323', 'P202', 'KH0009', 'NV0103', '2023-12-13 15:04:11', '2023-12-13 15:05:00', '', 1);
INSERT INTO `PhieuDatPhong` (`MaPhieuDat`, `MaPhong`, `MaKhachHang`, `MaNhanVien`, `ThoiGianLap`, `ThoiGianNhan`, `GhiChu`, `TinhTrang`) VALUES ('PD27121323', 'P202', 'KH0009', 'NV0103', '2023-12-13 15:06:24', '2023-12-13 15:07:00', '', 1);
INSERT INTO `PhieuDatPhong` (`MaPhieuDat`, `MaPhong`, `MaKhachHang`, `MaNhanVien`, `ThoiGianLap`, `ThoiGianNhan`, `GhiChu`, `TinhTrang`) VALUES ('PD28121423', 'P201', 'KH0003', 'NV0103', '2023-12-14 12:38:48', '2023-12-14 13:00:00', '', 1);
COMMIT;

-- ----------------------------
-- Table structure for Phong
-- ----------------------------
DROP TABLE IF EXISTS `Phong`;
CREATE TABLE `Phong` (
  `MaPhong` varchar(255) NOT NULL,
  `LoaiPhong` int(11) DEFAULT NULL,
  `TinhTrang` int(11) DEFAULT NULL,
  `SucChua` int(11) DEFAULT NULL,
  `GiaPhong` int(11) DEFAULT NULL,
  PRIMARY KEY (`MaPhong`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of Phong
-- ----------------------------
BEGIN;
INSERT INTO `Phong` (`MaPhong`, `LoaiPhong`, `TinhTrang`, `SucChua`, `GiaPhong`) VALUES ('P101', 0, 0, 5, 100000);
INSERT INTO `Phong` (`MaPhong`, `LoaiPhong`, `TinhTrang`, `SucChua`, `GiaPhong`) VALUES ('P102', 0, 0, 7, 100000);
INSERT INTO `Phong` (`MaPhong`, `LoaiPhong`, `TinhTrang`, `SucChua`, `GiaPhong`) VALUES ('P103VIP', 1, 0, 5, 200000);
INSERT INTO `Phong` (`MaPhong`, `LoaiPhong`, `TinhTrang`, `SucChua`, `GiaPhong`) VALUES ('P104VIP', 1, 1, 4, 200000);
INSERT INTO `Phong` (`MaPhong`, `LoaiPhong`, `TinhTrang`, `SucChua`, `GiaPhong`) VALUES ('P201', 0, 0, 8, 100000);
INSERT INTO `Phong` (`MaPhong`, `LoaiPhong`, `TinhTrang`, `SucChua`, `GiaPhong`) VALUES ('P202', 0, 1, 4, 100000);
INSERT INTO `Phong` (`MaPhong`, `LoaiPhong`, `TinhTrang`, `SucChua`, `GiaPhong`) VALUES ('P203', 0, 0, 5, 100000);
INSERT INTO `Phong` (`MaPhong`, `LoaiPhong`, `TinhTrang`, `SucChua`, `GiaPhong`) VALUES ('P204VIP', 1, 1, 6, 200000);
INSERT INTO `Phong` (`MaPhong`, `LoaiPhong`, `TinhTrang`, `SucChua`, `GiaPhong`) VALUES ('P301', 0, 0, 6, 100000);
INSERT INTO `Phong` (`MaPhong`, `LoaiPhong`, `TinhTrang`, `SucChua`, `GiaPhong`) VALUES ('P302', 0, 2, 5, 100000);
INSERT INTO `Phong` (`MaPhong`, `LoaiPhong`, `TinhTrang`, `SucChua`, `GiaPhong`) VALUES ('P303VIP', 1, 0, 7, 200000);
INSERT INTO `Phong` (`MaPhong`, `LoaiPhong`, `TinhTrang`, `SucChua`, `GiaPhong`) VALUES ('P304', 0, 0, 4, 100000);
INSERT INTO `Phong` (`MaPhong`, `LoaiPhong`, `TinhTrang`, `SucChua`, `GiaPhong`) VALUES ('P401VIP', 1, 0, 8, 200000);
INSERT INTO `Phong` (`MaPhong`, `LoaiPhong`, `TinhTrang`, `SucChua`, `GiaPhong`) VALUES ('P402', 0, 0, 6, 100000);
INSERT INTO `Phong` (`MaPhong`, `LoaiPhong`, `TinhTrang`, `SucChua`, `GiaPhong`) VALUES ('P403', 0, 0, 6, 100000);
INSERT INTO `Phong` (`MaPhong`, `LoaiPhong`, `TinhTrang`, `SucChua`, `GiaPhong`) VALUES ('P404', 0, 0, 5, 100000);
COMMIT;

-- ----------------------------
-- Table structure for TaiKhoan
-- ----------------------------
DROP TABLE IF EXISTS `TaiKhoan`;
CREATE TABLE `TaiKhoan` (
  `MaTaiKhoan` varchar(255) NOT NULL,
  `TenDangNhap` varchar(255) DEFAULT NULL,
  `MatKhau` varchar(255) DEFAULT NULL,
  `MaNhanVien` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`MaTaiKhoan`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of TaiKhoan
-- ----------------------------
BEGIN;
INSERT INTO `TaiKhoan` (`MaTaiKhoan`, `TenDangNhap`, `MatKhau`, `MaNhanVien`) VALUES ('TK0001', 'anquocviet', '123', 'NV0103');
INSERT INTO `TaiKhoan` (`MaTaiKhoan`, `TenDangNhap`, `MatKhau`, `MaNhanVien`) VALUES ('TK0002', 'thimo98', '1234', 'NV0198');
INSERT INTO `TaiKhoan` (`MaTaiKhoan`, `TenDangNhap`, `MatKhau`, `MaNhanVien`) VALUES ('TK0003', 'nkt03', '12345', 'NV0203');
INSERT INTO `TaiKhoan` (`MaTaiKhoan`, `TenDangNhap`, `MatKhau`, `MaNhanVien`) VALUES ('TK0004', 'thachla12', 'thach12', 'NV0503');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
