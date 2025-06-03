-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1
-- Thời gian đã tạo: Th3 31, 2025 lúc 02:28 PM
-- Phiên bản máy phục vụ: 10.4.32-MariaDB
-- Phiên bản PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Cơ sở dữ liệu: `clothing_store_db`
--

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `blogs`
--

CREATE TABLE `blogs` (
  `id` int(11) NOT NULL,
  `authorId` int(11) NOT NULL,
  `title` varchar(200) NOT NULL,
  `content` text NOT NULL,
  `thumbnailImage` varchar(255) NOT NULL,
  `createdDate` datetime NOT NULL,
  `updatedDate` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `cart`
--

CREATE TABLE `cart` (
  `id` int(11) NOT NULL,
  `userId` int(11) NOT NULL,
  `createdDate` datetime NOT NULL DEFAULT current_timestamp(),
  `updatedDate` datetime NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `cartdetail`
--

CREATE TABLE `cartdetail` (
  `id` int(11) NOT NULL,
  `cartId` int(11) NOT NULL,
  `couponId` int(11) NOT NULL,
  `quantity` int(11) NOT NULL,
  `unitPrice` decimal(10,2) NOT NULL,
  `productDetailsId` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `categories`
--

CREATE TABLE `categories` (
  `id` int(11) NOT NULL,
  `typeId` int(11) NOT NULL,
  `name` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC;

--
-- Đang đổ dữ liệu cho bảng `categories`
--

INSERT INTO `categories` (`id`, `typeId`, `name`) VALUES
(1, 1, 'Nam'),
(2, 1, 'Nữ'),
(3, 1, 'Trẻ em'),
(4, 2, 'Nam'),
(5, 2, 'Nữ'),
(6, 2, 'Trẻ em');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `collections`
--

CREATE TABLE `collections` (
  `id` int(11) NOT NULL,
  `collectionsName` varchar(100) NOT NULL,
  `collectionsDescription` text NOT NULL,
  `startDate` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `collections_product`
--

CREATE TABLE `collections_product` (
  `productId` int(11) NOT NULL,
  `collectionId` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `comment`
--

CREATE TABLE `comment` (
  `id` int(11) NOT NULL,
  `userId` int(11) NOT NULL,
  `productId` int(11) NOT NULL,
  `content` text NOT NULL,
  `rating` varchar(5) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC;

--
-- Đang đổ dữ liệu cho bảng `comment`
--

INSERT INTO `comment` (`id`, `userId`, `productId`, `content`, `rating`) VALUES
(1, 1, 2, 'shop hay.', '5');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `coupons`
--

CREATE TABLE `coupons` (
  `id` int(11) NOT NULL,
  `code` varchar(10) NOT NULL,
  `remainingCount` int(11) NOT NULL,
  `discount` float NOT NULL,
  `decription` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC;

--
-- Đang đổ dữ liệu cho bảng `coupons`
--

INSERT INTO `coupons` (`id`, `code`, `remainingCount`, `discount`, `decription`) VALUES
(1, 'abc', 10, 0.2, 'Ưu đãi, giảm giá đặc biệt'),
(2, 'bcd', 10, 0.1, 'Ưu đãi'),
(3, 'cde', 10, 0.3, 'Ưu đãi');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `favourite`
--

CREATE TABLE `favourite` (
  `id` int(11) NOT NULL,
  `userId` int(11) NOT NULL,
  `productId` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `notifications`
--

CREATE TABLE `notifications` (
  `id` int(11) NOT NULL,
  `userId` int(11) NOT NULL,
  `message` text NOT NULL,
  `notificationDate` datetime NOT NULL DEFAULT current_timestamp(),
  `isRead` bit(1) NOT NULL DEFAULT b'0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `orderitem`
--

CREATE TABLE `orderitem` (
  `id` int(11) NOT NULL,
  `orderId` int(11) NOT NULL,
  `productId` int(11) NOT NULL,
  `quantity` int(11) NOT NULL,
  `unitPrice` decimal(10,2) NOT NULL,
  `discount` float NOT NULL DEFAULT 0,
  `productDetailId` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC;

--
-- Đang đổ dữ liệu cho bảng `orderitem`
--

INSERT INTO `orderitem` (`id`, `orderId`, `productId`, `quantity`, `unitPrice`, `discount`, `productDetailId`) VALUES
(6, 10, 1, 3, 823650.00, 0.2, 1),
(7, 11, 2, 2, 560000.00, 0, 7),
(8, 11, 3, 1, 470000.00, 0, 13),
(9, 11, 4, 2, 980000.00, 0, 21),
(10, 12, 1, 1, 274550.00, 0, 1),
(11, 13, 1, 2, 549100.00, 0, 1),
(12, 13, 2, 5, 1400000.00, 0, 7);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `orders`
--

CREATE TABLE `orders` (
  `id` int(11) NOT NULL,
  `userId` int(11) NOT NULL,
  `paymentId` int(11) NOT NULL,
  `couponId` int(11) DEFAULT NULL,
  `orderDate` datetime NOT NULL DEFAULT current_timestamp(),
  `totalPrice` decimal(10,2) NOT NULL,
  `status` int(11) NOT NULL,
  `cancelReason` varchar(255) DEFAULT NULL COMMENT 'Lý do hủy đơn hàng'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC;

--
-- Đang đổ dữ liệu cho bảng `orders`
--

INSERT INTO `orders` (`id`, `userId`, `paymentId`, `couponId`, `orderDate`, `totalPrice`, `status`, `cancelReason`) VALUES
(7, 2, 1, 2, '2025-01-06 00:00:00', 250000.00, 5, NULL),
(8, 3, 2, 2, '2025-01-15 00:00:00', 250000.00, 4, NULL),
(10, 2, 1, 1, '2025-01-14 00:00:00', 823650.00, 2, 'bruh'),
(11, 2, 1, 1, '2025-01-15 00:00:00', 2010000.00, 0, 'E muốn đấm thầy'),
(12, 1, 1, 1, '2025-01-16 00:00:00', 274550.00, 0, NULL),
(13, 1, 1, 1, '2025-01-17 00:00:00', 1949100.00, 0, NULL),
(14, 1, 1, 1, '2025-03-12 00:00:00', 1070000.00, 5, NULL);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `payments`
--

CREATE TABLE `payments` (
  `id` int(11) NOT NULL,
  `paymentMethod` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC;

--
-- Đang đổ dữ liệu cho bảng `payments`
--

INSERT INTO `payments` (`id`, `paymentMethod`) VALUES
(1, 'Tiền mặt'),
(2, 'Chuyển khoản'),
(3, 'Thẻ tín dụng');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `products`
--

CREATE TABLE `products` (
  `id` int(11) NOT NULL,
  `typeId` int(11) NOT NULL,
  `categoryId` int(11) NOT NULL,
  `supplierId` int(11) NOT NULL,
  `productName` text NOT NULL,
  `description` text NOT NULL,
  `releaseDate` date NOT NULL,
  `unitSold` int(11) NOT NULL DEFAULT 0,
  `unitPrice` decimal(10,2) NOT NULL,
  `status` tinyint(1) NOT NULL DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC;

--
-- Đang đổ dữ liệu cho bảng `products`
--

INSERT INTO `products` (`id`, `typeId`, `categoryId`, `supplierId`, `productName`, `description`, `releaseDate`, `unitSold`, `unitPrice`, `status`) VALUES
(1, 2, 4, 1, 'Quần tây PEALO form cạp cao có xếp li', 'Mô tả sản phẩm: Quần kaki ống suông nam PEALO casual pants trơn basic chất liệu mới Loose Pants phong cách Minimalism Menswear... (Rút gọn nếu cần)', '2024-12-15', 50, 274550.00, 0),
(2, 2, 4, 1, 'Quần kaki ống suông nam PEALO', 'Mô tả sản phẩm: Quần kaki ống suông nam PEALO casual pants trơn basic chất liệu mới Loose Pants phong cách Minimalism Menswear... (Rút gọn nếu cần)', '2024-12-17', 40, 280000.00, 1),
(3, 2, 4, 2, 'Quần tây quần vải cạp ống rộng', 'Quần tây quần vải cạp ống rộng - B Brown Wide Fit Trousers... (Rút gọn nếu cần)', '2024-10-14', 100, 470000.00, 2),
(4, 2, 4, 3, 'Quần Dài Ống Rộng Hàn Quốc Hàng Cao Cấp', 'Tên sản phẩm: Quần Dài Ống Rộng Hàn Quốc Hàng Cao Cấp BBrand... (Rút gọn nếu cần)', '2024-11-19', 80, 490000.00, 1),
(5, 2, 4, 4, 'QUẦN DÁNG XUÔNG XẾP LY BY RUYCH.', 'Size: M, L, XL...\r\n... (Rút gọn nếu cần)', '2024-10-07', 110, 369000.00, 1),
(6, 2, 4, 3, 'Quần jean nam ống rộng JBAGY dáng suông cạp chun - JJ0107', 'Mô tả sản phẩm: Quần kaki ống suông nam PEALO casual pants trơn basic chất liệu mới Loose Pants phong cách Minimalism Menswear... (Rút gọn nếu cần)', '2024-12-02', 70, 200500.00, 1),
(7, 2, 4, 1, 'Quần Jean Quần Bò Nam Ống Suông Phong Cách Retro Siêu Đẹp - Cam Kết Hàng Y Hình', 'Mô tả sản phẩm: Quần kaki ống suông nam PEALO casual pants trơn basic chất liệu mới Loose Pants phong cách Minimalism Menswear... (Rút gọn nếu cần)', '2024-11-12', 90, 150000.00, 1),
(8, 2, 4, 3, 'Quần Jeans Dài Wash Phối Raglan Y2K Unisex Nam Nữ - QS06', 'Size: M, L, XL...\r\n... (Rút gọn nếu cần)', '2024-11-11', 60, 350000.00, 1),
(9, 1, 1, 2, 'Áo nỉ nam phong cách Hàn Quốc mẫu mới áo màu đơn giản', 'Size: M, L, XL...\r\n... (Rút gọn nếu cần)', '2024-11-05', 75, 100000.00, 1),
(10, 1, 1, 2, 'Áo Sơ Mi Tay Ngắn Dekace Premium Oxford Thêu LA', 'Tên sản phẩm: Quần Dài Ống Rộng Hàn Quốc Hàng Cao Cấp BBrand... (Rút gọn nếu cần)', '2024-11-26', 40, 140000.00, 1),
(11, 1, 1, 1, 'Áo Thun Oversize Scarlet - Local Brand Revvour', 'Quần tây quần vải cạp ống rộng - B Brown Wide Fit Trousers... (Rút gọn nếu cần)', '2024-10-23', 67, 120000.00, 1),
(12, 2, 4, 1, 'Nhật Bản Retro Rửa Đau Khổ Quần Jean Nam ins Đường Phố Cao Cấp Mặc Rời Thẳng Quần Ống Rộng Quần Thường Ngày', 'Quần tây quần vải cạp ống rộng - B Brown Wide Fit Trousers... (Rút gọn nếu cần)', '2024-12-09', 55, 300000.00, 1),
(13, 2, 5, 1, 'Chân Váy Dạ Chữ A Dáng Ngắn BlinkBae Họa Tiết Caro Chất Liệu Dạ Cao Cấp Cạp Chun Dễ Phối Đồ Siêu Cá Tính', 'Quần tây quần vải cạp ống rộng - B Brown Wide Fit Trousers... (Rút gọn nếu cần)', '2024-12-10', 34, 200000.00, 1),
(14, 2, 5, 1, 'Cardigan Dài Tay Mỏng Ruby Cách Điệu, Áo Khoác Bó Sát Có Khóa Kéo Gợi Cảm Quyến Rũ', 'Quần tây quần vải cạp ống rộng - B Brown Wide Fit Trousers... (Rút gọn nếu cần)', '2024-11-11', 67, 150000.00, 1),
(15, 2, 5, 1, 'Set váy kèm áo croptop nữ tay dài kiểu trễ vai VENOM họa tiết kẻ sọc phong cách trẻ trung cá tính', 'Quần tây quần vải cạp ống rộng - B Brown Wide Fit Trousers... (Rút gọn nếu cần)', '2024-12-10', 89, 250000.00, 1),
(16, 1, 2, 1, 'Áo croptop nữ CLOVER kiểu tay ngắn khoá kéo 2 đầu phối đinh phong cách cá tính', 'Quần tây quần vải cạp ống rộng - B Brown Wide Fit Trousers... (Rút gọn nếu cần)', '2024-12-11', 100, 280000.00, 1),
(17, 1, 2, 1, 'Áo croptop nữ kiểu tay dài CRIMSON phối ren lượn sóng tua rua phong cách sexy quyến rũ cá tính', 'Mô tả sản phẩm: Quần kaki ống suông nam PEALO casual pants trơn basic chất liệu mới Loose Pants phong cách Minimalism Menswear... (Rút gọn nếu cần)', '2024-12-02', 54, 170000.00, 1),
(18, 1, 2, 1, 'Áo thun T-shirt Local Brand BlinkBae RETRO In Họa Tiết Chữ Chất Liệu Cotton Cao Cấp', 'Size: M, L, XL...\r\n... (Rút gọn nếu cần)', '2025-01-13', 87, 220000.00, 1),
(19, 1, 3, 1, 'Áo sơ mi bé trai Yuunie dài tay kẻ xanh phong cách Hàn Quốc chất liệu somi cotton mềm mại', 'Quần tây quần vải cạp ống rộng - B Brown Wide Fit Trousers... (Rút gọn nếu cần)', '2025-01-14', 100, 190000.00, 1),
(20, 1, 3, 1, 'Áo Sweater dài tay bé trai，Lớp lót lông cừu phong cách thu đông dày dặn, có in họa tiết phi hành gia', 'Quần tây quần vải cạp ống rộng - B Brown Wide Fit Trousers... (Rút gọn nếu cần)', '2024-12-10', 80, 240000.00, 1),
(21, 1, 3, 1, 'Áo thun bé trai dài tay size đại 50kg Magickids áo cotton in chữ mềm mịn giữ nhiệt Quần áo trẻ em thu đông', 'Size: M, L, XL...\n... (Rút gọn nếu cần)', '2024-12-18', 110, 210000.00, 2);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `product_details`
--

CREATE TABLE `product_details` (
  `id` int(11) NOT NULL,
  `productId` int(11) DEFAULT NULL,
  `size` varchar(255) DEFAULT NULL,
  `stock` int(11) DEFAULT NULL,
  `image` varchar(255) DEFAULT NULL,
  `color` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC;

--
-- Đang đổ dữ liệu cho bảng `product_details`
--

INSERT INTO `product_details` (`id`, `productId`, `size`, `stock`, `image`, `color`) VALUES
(1, 1, 'M', 10, 'p1n1.webp', 'Nâu dạ'),
(2, 1, 'L', 11, 'p1n2.webp', 'Nâu dạ'),
(3, 1, 'XL', 7, 'p1n5.webp', 'Nâu dạ'),
(4, 1, 'XXL', 4, 'p1n7.webp', 'Nâu dạ'),
(5, 1, 'M', 8, 'p1n3.webp', 'Đen xám'),
(6, 1, 'L', 5, 'p1n6.webp', 'Đen xám'),
(7, 2, 'M', 12, 'p2n1.webp', 'Nâu nhạt'),
(8, 2, 'L', 11, 'p2n2.webp', 'Trắng'),
(9, 2, 'XL', 13, 'p2n3.webp', 'Trắng'),
(10, 2, 'XXL', 4, 'p2n4.webp', 'Xanh rêu'),
(11, 2, 'M', 12, 'p2n5.webp', 'Xanh rêu'),
(12, 2, 'L', 13, 'p2n6.webp', 'Xanh rêu'),
(13, 3, 'M', 11, 'p3n1.webp', 'Đen'),
(14, 3, 'L', 7, 'p3n2.webp', 'Đen'),
(15, 3, 'XL', 8, 'p3n3.webp', 'Đen'),
(16, 3, 'XXL', 9, 'p3n4.webp', 'Đen'),
(17, 3, '3XL', 14, 'p3n5.webp', 'Đen'),
(18, 3, '4XL', 12, 'p3n6.webp', 'Đen'),
(19, 3, 'M', 12, 'p3n7.webp', 'Nâu'),
(20, 3, 'L', 5, 'p3n8.webp', 'Nâu'),
(21, 4, 'M', 7, 'p4n1.webp', 'Xám'),
(22, 4, 'L', 9, 'p4n2.webp', 'Trắng'),
(23, 4, 'XL', 17, 'p4n3.webp', 'Nâu'),
(24, 4, 'XXL', 16, 'p4n4.webp', 'Kem'),
(25, 4, '3XL', 13, 'p4n5.webp', 'Đen'),
(26, 4, '4XL', 11, 'p4n6.webp', 'Trắng'),
(27, 4, 'M', 3, 'p4n7.webp', 'Trắng'),
(28, 5, 'M', 6, 'p5n1.webp', 'Xám'),
(29, 5, 'L', 8, 'p5n2.webp', 'Đen'),
(30, 5, 'XL', 16, 'p5n3.webp', 'Xám'),
(31, 5, 'XXL', 15, 'p5n4.webp', 'Xám'),
(32, 5, 'M', 12, 'p5n5.webp', 'Đen'),
(33, 5, 'L', 11, 'p5n6.webp', 'Xám'),
(34, 6, 'M', 7, 'p6n1.webp', 'Đen'),
(35, 6, 'L', 9, 'p6n2.webp', 'Đen'),
(36, 6, 'XL', 18, 'p6n3.webp', 'Đen'),
(37, 6, 'XXL', 9, 'p6n4.webp', 'Đen'),
(38, 6, '3XL', 12, 'p6n1.webp', 'Đen'),
(39, 6, '4XL', 9, 'p6n1.webp', 'Đen'),
(40, 7, 'M', 12, 'p7n1.webp', 'Nâu'),
(41, 7, 'L', 11, 'p7n2.webp', 'Nâu'),
(42, 7, 'XL', 7, 'p7n3.webp', 'Nâu'),
(43, 7, 'XXL', 8, 'p7n4.webp', 'Nâu'),
(44, 7, '3XL', 9, 'p7n1.webp', 'Nâu'),
(45, 7, '4XL', 11, 'p7n1.webp', 'Nâu'),
(46, 8, 'M', 15, 'p8n1.webp', 'Xanh'),
(47, 8, 'L', 11, 'p8n2.webp', 'Xanh'),
(48, 8, 'XL', 12, 'p8n3.webp', 'Xanh'),
(49, 8, 'XXL', 13, 'p8n4.webp', 'Xanh'),
(50, 9, 'M', 11, 'p9n1.webp', 'Đen'),
(51, 9, 'L', 7, 'p9n2.webp', 'Đen'),
(52, 9, 'XL', 9, 'p9n3.webp', 'Đen'),
(53, 9, 'XXL', 10, 'p9n4.webp', 'Đen'),
(54, 10, 'M', 19, 'p10n1.webp', 'Trắng'),
(55, 10, 'L', 12, 'p10n2.webp', 'Trắng'),
(56, 10, 'XL', 13, 'p10n3.webp', 'Trắng'),
(57, 10, 'XXL', 9, 'p10n1.webp', 'Trắng'),
(58, 11, 'M', 10, 'p11n1.webp', 'Đen'),
(59, 11, 'L', 11, 'p11n2.webp', 'Đen'),
(60, 11, 'XL', 13, 'p11n3.webp', 'Đen'),
(61, 11, 'XXL', 14, 'p11n4.webp', 'Đen'),
(62, 12, 'M', 8, 'p12n1.webp', 'Xám Đen'),
(63, 12, 'L', 10, 'p12n2.webp', 'Xám Đen'),
(64, 12, 'XL', 11, 'p12n3.webp', 'Xám Đen'),
(65, 12, 'XXL', 7, 'p12n4.webp', 'Xám Đen'),
(66, 13, 'M', 8, 'p13n1.webp', 'Nâu'),
(67, 13, 'L', 18, 'p13n2.webp', 'Đen'),
(68, 13, 'XL', 14, 'p13n3.webp', 'Nâu'),
(69, 13, 'S', 12, 'p13n4.webp', 'Đen'),
(70, 14, 'S', 9, 'p14n1.webp', 'Đen'),
(71, 14, 'M', 7, 'p14n2.webp', 'Đen'),
(72, 14, 'XL', 18, 'p14n3.webp', 'Đen'),
(73, 14, 'L', 15, 'p14n4.webp', 'Đen'),
(74, 15, 'S', 12, 'p15n1.webp', 'Trắng'),
(75, 15, 'M', 11, 'p15n2.webp', 'Trắng'),
(76, 15, 'L', 14, 'p15n3.webp', 'Trắng'),
(77, 15, 'XL', 18, 'p15n4.webp', 'Trắng'),
(78, 16, 'L', 16, 'p16n1.webp', 'Đen'),
(79, 16, 'S', 5, 'p16n2.webp', 'Đen'),
(80, 16, 'M', 14, 'p16n3.webp', 'Đen'),
(81, 16, 'XL', 15, 'p16n4.webp', 'Đen'),
(82, 17, 'L', 6, 'p17n1.webp', 'Trắng'),
(83, 17, 'S', 7, 'p17n2.webp', 'Đen'),
(84, 17, 'M', 16, 'p17n3.webp', 'Trắng'),
(85, 17, 'XL', 4, 'p17n4.webp', 'Đen'),
(86, 18, 'L', 7, 'p18n1.webp', 'Trắng'),
(87, 18, 'S', 16, 'p18n2.webp', 'Trắng'),
(88, 18, 'M', 5, 'p18n3.webp', 'Trắng'),
(89, 18, 'XL', 14, 'p18n4.webp', 'Trắng'),
(90, 19, 'L', 13, 'p19n1.webp', 'Trắng'),
(91, 19, 'S', 15, 'p19n2.webp', 'Trắng'),
(92, 19, 'M', 12, 'p19n3.webp', 'Trắng'),
(93, 19, 'XL', 4, 'p19n4.webp', 'Trắng'),
(94, 20, 'L', 7, 'p20n1.webp', 'Xám'),
(95, 20, 'S', 18, 'p20n2.webp', 'Đen'),
(96, 20, 'M', 14, 'p20n3.webp', 'Xanh'),
(97, 20, 'XL', 15, 'p20n4.webp', 'Xanh'),
(98, 21, 'L', 6, 'p21n1.webp', 'Đen'),
(99, 21, 'S', 8, 'p21n2.webp', 'Đỏ'),
(100, 21, 'M', 19, 'p21n3.webp', 'Nâu'),
(101, 21, 'XL', 17, 'p21n4.webp', 'Nâu');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `resetpasswordtokens`
--

CREATE TABLE `resetpasswordtokens` (
  `id` int(11) NOT NULL,
  `userId` int(11) NOT NULL,
  `token` varchar(255) NOT NULL,
  `createdAt` datetime NOT NULL DEFAULT current_timestamp(),
  `expiredAt` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC;

--
-- Đang đổ dữ liệu cho bảng `resetpasswordtokens`
--

INSERT INTO `resetpasswordtokens` (`id`, `userId`, `token`, `createdAt`, `expiredAt`) VALUES
(1, 4, '1a79fc00-2ad2-48be-841d-55c753516d2c', '2025-01-17 07:46:59', '2025-01-17 07:56:59'),
(2, 4, 'f374d2e1-5063-4403-b400-5c5d7f5bb79a', '2025-01-17 08:24:09', '2025-01-17 08:34:09');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `resource`
--

CREATE TABLE `resource` (
  `id` int(11) NOT NULL,
  `resource_name` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `resource`
--

INSERT INTO `resource` (`id`, `resource_name`) VALUES
(4, 'Cart'),
(2, 'Order'),
(6, 'Payment'),
(1, 'Product'),
(5, 'Profile'),
(3, 'UserManagement');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `roles`
--

CREATE TABLE `roles` (
  `id` int(11) NOT NULL,
  `roleName` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC;

--
-- Đang đổ dữ liệu cho bảng `roles`
--

INSERT INTO `roles` (`id`, `roleName`) VALUES
(1, 'ADMIN'),
(2, 'USER');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `role_resource`
--

CREATE TABLE `role_resource` (
  `role_id` int(11) NOT NULL,
  `resource_id` int(11) NOT NULL,
  `permission` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `role_resource`
--

INSERT INTO `role_resource` (`role_id`, `resource_id`, `permission`) VALUES
(1, 1, 7),
(1, 2, 7),
(1, 3, 7),
(1, 4, 7),
(1, 5, 7),
(1, 6, 7),
(2, 1, 4),
(2, 2, 6),
(2, 4, 6),
(2, 5, 6),
(2, 6, 2);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `sales`
--

CREATE TABLE `sales` (
  `id` int(11) NOT NULL,
  `salesName` varchar(100) NOT NULL,
  `salesDescription` text NOT NULL,
  `startDate` date NOT NULL,
  `endDate` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC;

--
-- Đang đổ dữ liệu cho bảng `sales`
--

INSERT INTO `sales` (`id`, `salesName`, `salesDescription`, `startDate`, `endDate`) VALUES
(1, 'Black Friday', 'Ngày lễ mua sắm', '2025-01-17', '2025-01-31');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `sales_product`
--

CREATE TABLE `sales_product` (
  `productId` int(11) NOT NULL,
  `saleId` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC;

--
-- Đang đổ dữ liệu cho bảng `sales_product`
--

INSERT INTO `sales_product` (`productId`, `saleId`) VALUES
(1, 1),
(2, 1),
(3, 1),
(4, 1),
(5, 1),
(1, 1),
(2, 1),
(3, 1),
(4, 1),
(5, 1);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `suppliers`
--

CREATE TABLE `suppliers` (
  `id` int(11) NOT NULL,
  `supplierName` varchar(100) NOT NULL,
  `supplierImg` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC;

--
-- Đang đổ dữ liệu cho bảng `suppliers`
--

INSERT INTO `suppliers` (`id`, `supplierName`, `supplierImg`) VALUES
(1, 'PEALO', ''),
(2, 'B Brown Studio', ''),
(3, 'BBRAND', ''),
(4, 'RUYCH STUDIO', '');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `types`
--

CREATE TABLE `types` (
  `id` int(11) NOT NULL,
  `name` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC;

--
-- Đang đổ dữ liệu cho bảng `types`
--

INSERT INTO `types` (`id`, `name`) VALUES
(1, 'Áo'),
(2, 'Quần');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `userName` varchar(30) NOT NULL,
  `password` varchar(64) NOT NULL,
  `firstName` text DEFAULT NULL,
  `lastName` text DEFAULT NULL,
  `email` text DEFAULT NULL,
  `avatar` varchar(200) DEFAULT NULL,
  `address` text DEFAULT NULL,
  `phone` text DEFAULT NULL,
  `status` tinyint(1) NOT NULL COMMENT '0: Inactive (Chưa kích hoạt), 1: Active (Hoạt động), 2: Suspended (Bị khóa), 3: Banned (Bị cấm), 4: Deleted (Đã xóa)',
  `createdAt` datetime DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC;

--
-- Đang đổ dữ liệu cho bảng `users`
--

INSERT INTO `users` (`id`, `userName`, `password`, `firstName`, `lastName`, `email`, `avatar`, `address`, `phone`, `status`, `createdAt`) VALUES
(1, 'admin', '$2a$12$rXmhHS9ZGL7yWgXO4TQnZuJCuJDTWqHGU2HDUe4pNz7XuEebRy89y', 'Mạnh', 'Hoàng Lê Nguyên', 'admin@gmail.com', 'anhday', 'DakNong', '099999999', 1, '2024-12-24 22:34:35'),
(2, 'user1', '$2a$12$mr/Jje.hfP186bYtSBusHOdhOvLQrIhgTSFaUtUlGMrP7jEaz4jMC', 'Luật', 'Hoàng Quốc', '22130157@hcmuaf.edu.vn', '', 'SaiGon', '11111111', 1, '2024-12-24 00:00:00'),
(3, 'user2', 'user2', 'Nam', 'Phạm Quốc Phương', 'namday@gmai.com', '', 'SaiGon', '22222222', 1, '2024-12-24 00:00:00'),
(4, 'Manh', 'user3', 'Mạnh', 'Hoàng Lê Nguyên', '22130163@st.hcmuaf.edu.vn', 'manhday', 'DakNong', '097777777', 1, '2025-01-17 03:50:34'),
(6, 'anhmanhdeptrai', 'manh', 'Hoàng Lê Nguyên', 'Mạnh', 'manh2522004@gmail.com', 'bruh', 'Đak Nong', '0', 1, '2025-03-18 15:27:08');

--
-- Bẫy `users`
--
DELIMITER $$
CREATE TRIGGER `Trg_afterInsert` AFTER INSERT ON `users` FOR EACH ROW BEGIN
    INSERT INTO user_roles (userId, roleId)
    VALUES (NEW.id, 2); -- Lấy id vừa được chèn
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `user_roles`
--

CREATE TABLE `user_roles` (
  `userId` int(11) NOT NULL,
  `roleId` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `user_roles`
--

INSERT INTO `user_roles` (`userId`, `roleId`) VALUES
(1, 1),
(2, 2);

--
-- Chỉ mục cho các bảng đã đổ
--

--
-- Chỉ mục cho bảng `blogs`
--
ALTER TABLE `blogs`
  ADD PRIMARY KEY (`id`) USING BTREE,
  ADD KEY `authorId` (`authorId`) USING BTREE;

--
-- Chỉ mục cho bảng `cart`
--
ALTER TABLE `cart`
  ADD PRIMARY KEY (`id`) USING BTREE,
  ADD KEY `userId` (`userId`) USING BTREE;

--
-- Chỉ mục cho bảng `cartdetail`
--
ALTER TABLE `cartdetail`
  ADD PRIMARY KEY (`id`) USING BTREE,
  ADD KEY `cartId` (`cartId`) USING BTREE,
  ADD KEY `couponId` (`couponId`) USING BTREE,
  ADD KEY `fk_productDetails_cartdetail` (`productDetailsId`);

--
-- Chỉ mục cho bảng `categories`
--
ALTER TABLE `categories`
  ADD PRIMARY KEY (`id`) USING BTREE,
  ADD KEY `typeId` (`typeId`) USING BTREE;

--
-- Chỉ mục cho bảng `collections`
--
ALTER TABLE `collections`
  ADD PRIMARY KEY (`id`) USING BTREE;

--
-- Chỉ mục cho bảng `collections_product`
--
ALTER TABLE `collections_product`
  ADD KEY `productId` (`productId`) USING BTREE,
  ADD KEY `collectionId` (`collectionId`) USING BTREE;

--
-- Chỉ mục cho bảng `comment`
--
ALTER TABLE `comment`
  ADD PRIMARY KEY (`id`) USING BTREE,
  ADD KEY `userId` (`userId`) USING BTREE,
  ADD KEY `productId` (`productId`) USING BTREE;

--
-- Chỉ mục cho bảng `coupons`
--
ALTER TABLE `coupons`
  ADD PRIMARY KEY (`id`) USING BTREE;

--
-- Chỉ mục cho bảng `favourite`
--
ALTER TABLE `favourite`
  ADD PRIMARY KEY (`id`) USING BTREE,
  ADD KEY `userId` (`userId`) USING BTREE,
  ADD KEY `productId` (`productId`) USING BTREE;

--
-- Chỉ mục cho bảng `notifications`
--
ALTER TABLE `notifications`
  ADD PRIMARY KEY (`id`) USING BTREE,
  ADD KEY `userId` (`userId`) USING BTREE;

--
-- Chỉ mục cho bảng `orderitem`
--
ALTER TABLE `orderitem`
  ADD PRIMARY KEY (`id`) USING BTREE,
  ADD KEY `orderId` (`orderId`) USING BTREE,
  ADD KEY `productId` (`productId`) USING BTREE,
  ADD KEY `fk_orderitem_productDetail` (`productDetailId`);

--
-- Chỉ mục cho bảng `orders`
--
ALTER TABLE `orders`
  ADD PRIMARY KEY (`id`) USING BTREE,
  ADD KEY `userId` (`userId`) USING BTREE,
  ADD KEY `paymentId` (`paymentId`) USING BTREE,
  ADD KEY `couponId` (`couponId`) USING BTREE;

--
-- Chỉ mục cho bảng `payments`
--
ALTER TABLE `payments`
  ADD PRIMARY KEY (`id`) USING BTREE;

--
-- Chỉ mục cho bảng `products`
--
ALTER TABLE `products`
  ADD PRIMARY KEY (`id`) USING BTREE,
  ADD KEY `typeId` (`typeId`) USING BTREE,
  ADD KEY `categoryId` (`categoryId`) USING BTREE,
  ADD KEY `products_ibfk_3` (`supplierId`) USING BTREE;

--
-- Chỉ mục cho bảng `product_details`
--
ALTER TABLE `product_details`
  ADD PRIMARY KEY (`id`) USING BTREE,
  ADD KEY `productId_fk` (`productId`) USING BTREE;

--
-- Chỉ mục cho bảng `resetpasswordtokens`
--
ALTER TABLE `resetpasswordtokens`
  ADD PRIMARY KEY (`id`) USING BTREE,
  ADD KEY `userId` (`userId`) USING BTREE;

--
-- Chỉ mục cho bảng `resource`
--
ALTER TABLE `resource`
  ADD PRIMARY KEY (`id`) USING BTREE,
  ADD UNIQUE KEY `resource_name` (`resource_name`);

--
-- Chỉ mục cho bảng `roles`
--
ALTER TABLE `roles`
  ADD PRIMARY KEY (`id`) USING BTREE;

--
-- Chỉ mục cho bảng `role_resource`
--
ALTER TABLE `role_resource`
  ADD PRIMARY KEY (`role_id`,`resource_id`),
  ADD KEY `resource_id` (`resource_id`);

--
-- Chỉ mục cho bảng `sales`
--
ALTER TABLE `sales`
  ADD PRIMARY KEY (`id`) USING BTREE;

--
-- Chỉ mục cho bảng `sales_product`
--
ALTER TABLE `sales_product`
  ADD KEY `productId` (`productId`) USING BTREE,
  ADD KEY `saleId` (`saleId`) USING BTREE;

--
-- Chỉ mục cho bảng `suppliers`
--
ALTER TABLE `suppliers`
  ADD PRIMARY KEY (`id`) USING BTREE;

--
-- Chỉ mục cho bảng `types`
--
ALTER TABLE `types`
  ADD PRIMARY KEY (`id`) USING BTREE;

--
-- Chỉ mục cho bảng `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`) USING BTREE,
  ADD UNIQUE KEY `userName` (`userName`);

--
-- Chỉ mục cho bảng `user_roles`
--
ALTER TABLE `user_roles`
  ADD PRIMARY KEY (`userId`,`roleId`),
  ADD KEY `userroles_ibfk_2` (`roleId`);

--
-- AUTO_INCREMENT cho các bảng đã đổ
--

--
-- AUTO_INCREMENT cho bảng `blogs`
--
ALTER TABLE `blogs`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `cart`
--
ALTER TABLE `cart`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `cartdetail`
--
ALTER TABLE `cartdetail`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `categories`
--
ALTER TABLE `categories`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT cho bảng `collections`
--
ALTER TABLE `collections`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `comment`
--
ALTER TABLE `comment`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT cho bảng `coupons`
--
ALTER TABLE `coupons`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT cho bảng `favourite`
--
ALTER TABLE `favourite`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `notifications`
--
ALTER TABLE `notifications`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `orderitem`
--
ALTER TABLE `orderitem`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT cho bảng `orders`
--
ALTER TABLE `orders`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;

--
-- AUTO_INCREMENT cho bảng `payments`
--
ALTER TABLE `payments`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT cho bảng `products`
--
ALTER TABLE `products`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=22;

--
-- AUTO_INCREMENT cho bảng `product_details`
--
ALTER TABLE `product_details`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=102;

--
-- AUTO_INCREMENT cho bảng `resetpasswordtokens`
--
ALTER TABLE `resetpasswordtokens`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT cho bảng `resource`
--
ALTER TABLE `resource`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT cho bảng `roles`
--
ALTER TABLE `roles`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT cho bảng `sales`
--
ALTER TABLE `sales`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT cho bảng `suppliers`
--
ALTER TABLE `suppliers`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT cho bảng `types`
--
ALTER TABLE `types`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT cho bảng `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- Các ràng buộc cho các bảng đã đổ
--

--
-- Các ràng buộc cho bảng `blogs`
--
ALTER TABLE `blogs`
  ADD CONSTRAINT `blogs_ibfk_1` FOREIGN KEY (`authorId`) REFERENCES `users` (`id`);

--
-- Các ràng buộc cho bảng `cart`
--
ALTER TABLE `cart`
  ADD CONSTRAINT `cart_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `users` (`id`);

--
-- Các ràng buộc cho bảng `cartdetail`
--
ALTER TABLE `cartdetail`
  ADD CONSTRAINT `cartdetail_ibfk_1` FOREIGN KEY (`cartId`) REFERENCES `cart` (`id`),
  ADD CONSTRAINT `cartdetail_ibfk_3` FOREIGN KEY (`couponId`) REFERENCES `coupons` (`id`),
  ADD CONSTRAINT `fk_productDetails_cartdetail` FOREIGN KEY (`productDetailsId`) REFERENCES `product_details` (`id`);

--
-- Các ràng buộc cho bảng `categories`
--
ALTER TABLE `categories`
  ADD CONSTRAINT `categories_ibfk_1` FOREIGN KEY (`typeId`) REFERENCES `types` (`id`);

--
-- Các ràng buộc cho bảng `collections_product`
--
ALTER TABLE `collections_product`
  ADD CONSTRAINT `collections_product_ibfk_1` FOREIGN KEY (`productId`) REFERENCES `products` (`id`),
  ADD CONSTRAINT `collections_product_ibfk_2` FOREIGN KEY (`collectionId`) REFERENCES `collections` (`id`);

--
-- Các ràng buộc cho bảng `comment`
--
ALTER TABLE `comment`
  ADD CONSTRAINT `comment_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `users` (`id`),
  ADD CONSTRAINT `comment_ibfk_2` FOREIGN KEY (`productId`) REFERENCES `products` (`id`);

--
-- Các ràng buộc cho bảng `favourite`
--
ALTER TABLE `favourite`
  ADD CONSTRAINT `favourite_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `users` (`id`),
  ADD CONSTRAINT `favourite_ibfk_2` FOREIGN KEY (`productId`) REFERENCES `products` (`id`);

--
-- Các ràng buộc cho bảng `notifications`
--
ALTER TABLE `notifications`
  ADD CONSTRAINT `notifications_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `users` (`id`);

--
-- Các ràng buộc cho bảng `orderitem`
--
ALTER TABLE `orderitem`
  ADD CONSTRAINT `fk_orderitem_productDetail` FOREIGN KEY (`productDetailId`) REFERENCES `product_details` (`id`),
  ADD CONSTRAINT `orderitem_ibfk_1` FOREIGN KEY (`orderId`) REFERENCES `orders` (`id`),
  ADD CONSTRAINT `orderitem_ibfk_2` FOREIGN KEY (`productId`) REFERENCES `products` (`id`);

--
-- Các ràng buộc cho bảng `orders`
--
ALTER TABLE `orders`
  ADD CONSTRAINT `orders_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `users` (`id`),
  ADD CONSTRAINT `orders_ibfk_2` FOREIGN KEY (`paymentId`) REFERENCES `payments` (`id`),
  ADD CONSTRAINT `orders_ibfk_3` FOREIGN KEY (`couponId`) REFERENCES `coupons` (`id`);

--
-- Các ràng buộc cho bảng `products`
--
ALTER TABLE `products`
  ADD CONSTRAINT `products_ibfk_1` FOREIGN KEY (`typeId`) REFERENCES `types` (`id`),
  ADD CONSTRAINT `products_ibfk_2` FOREIGN KEY (`categoryId`) REFERENCES `categories` (`id`),
  ADD CONSTRAINT `products_ibfk_3` FOREIGN KEY (`supplierId`) REFERENCES `suppliers` (`id`);

--
-- Các ràng buộc cho bảng `product_details`
--
ALTER TABLE `product_details`
  ADD CONSTRAINT `proid_fk` FOREIGN KEY (`productId`) REFERENCES `products` (`id`) ON DELETE CASCADE;

--
-- Các ràng buộc cho bảng `resetpasswordtokens`
--
ALTER TABLE `resetpasswordtokens`
  ADD CONSTRAINT `resetpasswordtokens_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `users` (`id`);

--
-- Các ràng buộc cho bảng `role_resource`
--
ALTER TABLE `role_resource`
  ADD CONSTRAINT `role_resource_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `role_resource_ibfk_2` FOREIGN KEY (`resource_id`) REFERENCES `resource` (`id`) ON DELETE CASCADE;

--
-- Các ràng buộc cho bảng `sales_product`
--
ALTER TABLE `sales_product`
  ADD CONSTRAINT `sales_product_ibfk_1` FOREIGN KEY (`productId`) REFERENCES `products` (`id`),
  ADD CONSTRAINT `sales_product_ibfk_2` FOREIGN KEY (`saleId`) REFERENCES `sales` (`id`);

--
-- Các ràng buộc cho bảng `user_roles`
--
ALTER TABLE `user_roles`
  ADD CONSTRAINT `user_roles_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `users` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `user_roles_ibfk_2` FOREIGN KEY (`roleId`) REFERENCES `roles` (`id`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
