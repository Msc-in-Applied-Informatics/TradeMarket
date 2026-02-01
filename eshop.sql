-- phpMyAdmin SQL Dump
-- version 5.0.3
-- https://www.phpmyadmin.net/
--
-- Φιλοξενητής: 127.0.0.1
-- Χρόνος δημιουργίας: 01 Φεβ 2026 στις 14:43:36
-- Έκδοση διακομιστή: 10.4.14-MariaDB
-- Έκδοση PHP: 7.2.34

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Βάση δεδομένων: `eshop`
--

-- --------------------------------------------------------

--
-- Δομή πίνακα για τον πίνακα `cart`
--

CREATE TABLE `cart` (
  `id` bigint(20) NOT NULL,
  `total_price` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Άδειασμα δεδομένων του πίνακα `cart`
--

INSERT INTO `cart` (`id`, `total_price`) VALUES
(1, 0),
(4, 0),
(5, 7.32),
(6, 3.69);

-- --------------------------------------------------------

--
-- Δομή πίνακα για τον πίνακα `cart_products`
--

CREATE TABLE `cart_products` (
  `cart_id` bigint(20) NOT NULL,
  `product_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Άδειασμα δεδομένων του πίνακα `cart_products`
--

INSERT INTO `cart_products` (`cart_id`, `product_id`) VALUES
(5, 2),
(5, 2),
(5, 2),
(5, 2),
(6, 1),
(6, 1),
(6, 1);

-- --------------------------------------------------------

--
-- Δομή πίνακα για τον πίνακα `citizen`
--

CREATE TABLE `citizen` (
  `afm` varchar(255) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `cart_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Άδειασμα δεδομένων του πίνακα `citizen`
--

INSERT INTO `citizen` (`afm`, `email`, `name`, `password`, `role`, `surname`, `cart_id`) VALUES
('123456', 'kara@test.com', 'Νίκος', '123456', 'CITIZEN', 'Καρανικόλας', 6),
('125796632', 'karanik@arx.gr', 'NIKOLAOS', '123456', 'CITIZEN', 'KARANIKOLAS', 5),
('2222', 'kara@test.com', 'Νίκος', '123456', 'CITIZEN', 'Καρανικόλας', 1),
('6666', 'eleni@test.com', 'Ελένη', '123456', 'CITIZEN', 'Παπά', 4);

-- --------------------------------------------------------

--
-- Δομή πίνακα για τον πίνακα `orders`
--

CREATE TABLE `orders` (
  `id` bigint(20) NOT NULL,
  `order_date` datetime DEFAULT NULL,
  `total_amount` double DEFAULT NULL,
  `citizen_afm` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Άδειασμα δεδομένων του πίνακα `orders`
--

INSERT INTO `orders` (`id`, `order_date`, `total_amount`, `citizen_afm`) VALUES
(1, '2025-12-31 18:29:28', 3.06, '2222'),
(2, '2026-01-02 20:30:05', 210, '2222'),
(3, '2026-01-02 20:40:39', 0.95, '2222'),
(4, '2026-01-02 20:41:49', 1.1, '2222'),
(5, '2026-01-03 16:16:04', 0.84, '2222'),
(6, '2026-01-03 18:10:51', 224.5, '2222');

-- --------------------------------------------------------

--
-- Δομή πίνακα για τον πίνακα `order_item`
--

CREATE TABLE `order_item` (
  `id` bigint(20) NOT NULL,
  `price_at_purchase` double DEFAULT NULL,
  `order_id` bigint(20) DEFAULT NULL,
  `product_id` bigint(20) DEFAULT NULL,
  `shop_afm` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Άδειασμα δεδομένων του πίνακα `order_item`
--

INSERT INTO `order_item` (`id`, `price_at_purchase`, `order_id`, `product_id`, `shop_afm`) VALUES
(1, 1.23, 1, 1, '1111'),
(2, 1.83, 1, 2, '1111'),
(3, 210, 2, 7, '3333'),
(4, 0.95, 3, 3, '1111'),
(5, 1.1, 4, 4, '1111'),
(6, 0.84, 5, 14, '1111'),
(7, 210, 6, 7, '3333'),
(8, 14.5, 6, 12, '4444');

-- --------------------------------------------------------

--
-- Δομή πίνακα για τον πίνακα `product`
--

CREATE TABLE `product` (
  `id` bigint(20) NOT NULL,
  `brand` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `price` double NOT NULL,
  `stock` int(11) NOT NULL,
  `type` varchar(255) DEFAULT NULL,
  `shop_afm` varchar(255) DEFAULT NULL,
  `active` bit(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Άδειασμα δεδομένων του πίνακα `product`
--

INSERT INTO `product` (`id`, `brand`, `description`, `price`, `stock`, `type`, `shop_afm`, `active`) VALUES
(1, 'Melissa', 'Μακαρόνια νο.10', 1.23, 19, 'Μακαρόνια', '1111', b'1'),
(2, 'Barilla', 'Μακαρόνια νο.10', 1.83, 19, 'Μακαρόνια', '1111', b'1'),
(3, 'Misko', 'Κριθαράκι μέτριο 500γρ', 0.95, 49, 'Ζυμαρικά', '1111', b'1'),
(4, 'Kyknos', 'Τοματοπολτός διπλής συμπύκνωσης', 1.1, 29, 'Κονσέρβες', '1111', b'1'),
(5, 'Papadopoulou', 'Πτι-Μπερ μπισκότα 225γρ', 1.45, 100, 'Μπισκότα', '1111', b'1'),
(6, 'Logitech', 'Ασύρματο ποντίκι M185', 15.9, 15, 'Περιφερειακά', '3333', b'1'),
(7, 'Samsung', 'Galaxy Tab A8 10.5\"', 210, 6, 'Tablets', '3333', b'1'),
(8, 'SanDisk', 'USB Flash Drive 64GB', 8.5, 40, 'Αποθήκευση', '3333', b'1'),
(9, 'TP-Link', 'Wi-Fi Extender AC750', 25, 12, 'Δικτυακά', '3333', b'1'),
(10, 'Arla', 'Φρέσκο Βούτυρο 250γρ', 4.2, 20, 'Γαλακτοκομικά', '4444', b'1'),
(11, 'Nescafe', 'Classic 200γρ', 7.8, 25, 'Καφέδες', '4444', b'1'),
(12, 'Ariel', 'Υγρό Απορρυπαντικό 70 μεζούρες', 14.5, 9, 'Καθαριστικά', '4444', b'1'),
(13, 'Terra Creta', 'Εξαιρετικό Παρθένο Ελαιόλαδο 1L', 9.3, 15, 'Λάδια', '4444', b'1'),
(14, 'Isco', 'no.06 παστιτσιο', 0.84, 0, 'Μακαρόνια', '1111', b'1'),
(15, 'TEST', 'TEST', 20, 20, 'TEST', '125796631', b'1');

-- --------------------------------------------------------

--
-- Δομή πίνακα για τον πίνακα `shop`
--

CREATE TABLE `shop` (
  `afm` varchar(255) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL,
  `owner` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Άδειασμα δεδομένων του πίνακα `shop`
--

INSERT INTO `shop` (`afm`, `email`, `name`, `password`, `role`, `owner`) VALUES
('1111', 'info@lg.gr', 'Geo', '123456', 'SHOP', 'Polyzoidou'),
('125796631', 'nikolakis95@hotmail.com', 'STORE NK', '123456', 'SHOP', 'Karanikolas Nikolaos'),
('3333', 'contact@electronix.gr', 'ElectroWorld', '123456', 'SHOP', 'Παπαδόπουλος'),
('4444', 'sales@supermarket-alpha.gr', 'Alpha Market', '123456', 'SHOP', 'Δημητρίου'),
('654321', 'info@lg.gr', 'Geo', '123456', 'SHOP', 'τεστ');

--
-- Ευρετήρια για άχρηστους πίνακες
--

--
-- Ευρετήρια για πίνακα `cart`
--
ALTER TABLE `cart`
  ADD PRIMARY KEY (`id`);

--
-- Ευρετήρια για πίνακα `cart_products`
--
ALTER TABLE `cart_products`
  ADD KEY `FKh72x9g1expjjcnyxglwhbv4xu` (`product_id`),
  ADD KEY `FKnlhjc091rdu9k5c8u9xwp280w` (`cart_id`);

--
-- Ευρετήρια για πίνακα `citizen`
--
ALTER TABLE `citizen`
  ADD PRIMARY KEY (`afm`),
  ADD KEY `FKfwi1ck412ut6wwt7pvijig3kx` (`cart_id`);

--
-- Ευρετήρια για πίνακα `orders`
--
ALTER TABLE `orders`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK7xbv6vq2y3pgyp0oyyogbo4ib` (`citizen_afm`);

--
-- Ευρετήρια για πίνακα `order_item`
--
ALTER TABLE `order_item`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKt4dc2r9nbvbujrljv3e23iibt` (`order_id`),
  ADD KEY `FK551losx9j75ss5d6bfsqvijna` (`product_id`),
  ADD KEY `FKlqmi4m0sq221nu2x21luyp76r` (`shop_afm`);

--
-- Ευρετήρια για πίνακα `product`
--
ALTER TABLE `product`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKmc862r3eoi13648vm3qk4kjal` (`shop_afm`);

--
-- Ευρετήρια για πίνακα `shop`
--
ALTER TABLE `shop`
  ADD PRIMARY KEY (`afm`);

--
-- AUTO_INCREMENT για άχρηστους πίνακες
--

--
-- AUTO_INCREMENT για πίνακα `cart`
--
ALTER TABLE `cart`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT για πίνακα `orders`
--
ALTER TABLE `orders`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT για πίνακα `order_item`
--
ALTER TABLE `order_item`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT για πίνακα `product`
--
ALTER TABLE `product`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- Περιορισμοί για άχρηστους πίνακες
--

--
-- Περιορισμοί για πίνακα `cart_products`
--
ALTER TABLE `cart_products`
  ADD CONSTRAINT `FKh72x9g1expjjcnyxglwhbv4xu` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`),
  ADD CONSTRAINT `FKnlhjc091rdu9k5c8u9xwp280w` FOREIGN KEY (`cart_id`) REFERENCES `cart` (`id`);

--
-- Περιορισμοί για πίνακα `citizen`
--
ALTER TABLE `citizen`
  ADD CONSTRAINT `FKfwi1ck412ut6wwt7pvijig3kx` FOREIGN KEY (`cart_id`) REFERENCES `cart` (`id`);

--
-- Περιορισμοί για πίνακα `orders`
--
ALTER TABLE `orders`
  ADD CONSTRAINT `FK7xbv6vq2y3pgyp0oyyogbo4ib` FOREIGN KEY (`citizen_afm`) REFERENCES `citizen` (`afm`);

--
-- Περιορισμοί για πίνακα `order_item`
--
ALTER TABLE `order_item`
  ADD CONSTRAINT `FK551losx9j75ss5d6bfsqvijna` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`),
  ADD CONSTRAINT `FKlqmi4m0sq221nu2x21luyp76r` FOREIGN KEY (`shop_afm`) REFERENCES `shop` (`afm`),
  ADD CONSTRAINT `FKt4dc2r9nbvbujrljv3e23iibt` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`);

--
-- Περιορισμοί για πίνακα `product`
--
ALTER TABLE `product`
  ADD CONSTRAINT `FKmc862r3eoi13648vm3qk4kjal` FOREIGN KEY (`shop_afm`) REFERENCES `shop` (`afm`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
