-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               10.4.32-MariaDB - mariadb.org binary distribution
-- Server OS:                    Win64
-- HeidiSQL Version:             12.6.0.6765
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Dumping database structure for humosoft_cnpm
CREATE DATABASE IF NOT EXISTS `humosoft_cnpm` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci */;
USE `humosoft_cnpm`;

-- Dumping structure for table humosoft_cnpm.addresses
CREATE TABLE IF NOT EXISTS `addresses` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `city` varchar(255) DEFAULT NULL,
  `commune` varchar(255) DEFAULT NULL,
  `country` varchar(255) DEFAULT NULL,
  `district` varchar(255) DEFAULT NULL,
  `house_number` varchar(255) DEFAULT NULL,
  `postal_code` varchar(255) DEFAULT NULL,
  `state` varchar(255) DEFAULT NULL,
  `street` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table humosoft_cnpm.addresses: ~0 rows (approximately)

-- Dumping structure for table humosoft_cnpm.department
CREATE TABLE IF NOT EXISTS `department` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `department_name` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table humosoft_cnpm.department: ~0 rows (approximately)

-- Dumping structure for table humosoft_cnpm.holiday
CREATE TABLE IF NOT EXISTS `holiday` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `holiday_date` datetime(6) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `holiday_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table humosoft_cnpm.holiday: ~0 rows (approximately)

-- Dumping structure for table humosoft_cnpm.interview
CREATE TABLE IF NOT EXISTS `interview` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `interviewer_id` int(11) DEFAULT NULL,
  `recruitment_id` int(11) DEFAULT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `interview_date` datetime(6) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `location` varchar(255) DEFAULT NULL,
  `notes` varchar(255) DEFAULT NULL,
  `result` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKjmqtlltaxi4rarklyn1bmxgpr` (`interviewer_id`),
  KEY `FKcpwhy2fxr0ac2mgirpyba8p1y` (`recruitment_id`),
  CONSTRAINT `FKcpwhy2fxr0ac2mgirpyba8p1y` FOREIGN KEY (`recruitment_id`) REFERENCES `recruitment` (`id`),
  CONSTRAINT `FKjmqtlltaxi4rarklyn1bmxgpr` FOREIGN KEY (`interviewer_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table humosoft_cnpm.interview: ~0 rows (approximately)

-- Dumping structure for table humosoft_cnpm.leave_request
CREATE TABLE IF NOT EXISTS `leave_request` (
  `approved_by` int(11) DEFAULT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `leave_balances` int(11) NOT NULL,
  `leave_type_id` int(11) DEFAULT NULL,
  `total_days` int(11) NOT NULL,
  `user_id` int(11) DEFAULT NULL,
  `approved_at` datetime(6) DEFAULT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `end_date` datetime(6) DEFAULT NULL,
  `start_date` datetime(6) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `reason` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKov29a5to67r9wce7ce87hg8o9` (`approved_by`),
  KEY `FKbsy0iudb8fxpkpoat8bjr29xl` (`leave_type_id`),
  KEY `FK28ykte0n73edocnb1phrnqo3s` (`user_id`),
  CONSTRAINT `FK28ykte0n73edocnb1phrnqo3s` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKbsy0iudb8fxpkpoat8bjr29xl` FOREIGN KEY (`leave_type_id`) REFERENCES `leave_type` (`id`),
  CONSTRAINT `FKov29a5to67r9wce7ce87hg8o9` FOREIGN KEY (`approved_by`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table humosoft_cnpm.leave_request: ~0 rows (approximately)

-- Dumping structure for table humosoft_cnpm.leave_type
CREATE TABLE IF NOT EXISTS `leave_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `is_paid` bit(1) NOT NULL,
  `max_days` int(11) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `leave_type_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table humosoft_cnpm.leave_type: ~0 rows (approximately)

-- Dumping structure for table humosoft_cnpm.paygrade
CREATE TABLE IF NOT EXISTS `paygrade` (
  `base_salary` int(11) NOT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `paygrade_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table humosoft_cnpm.paygrade: ~0 rows (approximately)

-- Dumping structure for table humosoft_cnpm.position
CREATE TABLE IF NOT EXISTS `position` (
  `department_id` int(11) DEFAULT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `paygrade_id` int(11) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `position_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKm3sbyitwagg27v6n7kobokau5` (`department_id`),
  KEY `FKkbhewy4pgt07hmbk68o1i06er` (`paygrade_id`),
  CONSTRAINT `FKkbhewy4pgt07hmbk68o1i06er` FOREIGN KEY (`paygrade_id`) REFERENCES `paygrade` (`id`),
  CONSTRAINT `FKm3sbyitwagg27v6n7kobokau5` FOREIGN KEY (`department_id`) REFERENCES `department` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table humosoft_cnpm.position: ~0 rows (approximately)

-- Dumping structure for table humosoft_cnpm.recruitment
CREATE TABLE IF NOT EXISTS `recruitment` (
  `department_id` int(11) DEFAULT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `position_id` int(11) DEFAULT NULL,
  `candidate_name` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKrmbdxp7unhhx0uahlmbevnc3h` (`department_id`),
  KEY `FKt79y78vfm6sffu5lquu1ehxd2` (`position_id`),
  CONSTRAINT `FKrmbdxp7unhhx0uahlmbevnc3h` FOREIGN KEY (`department_id`) REFERENCES `department` (`id`),
  CONSTRAINT `FKt79y78vfm6sffu5lquu1ehxd2` FOREIGN KEY (`position_id`) REFERENCES `position` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table humosoft_cnpm.recruitment: ~0 rows (approximately)

-- Dumping structure for table humosoft_cnpm.role
CREATE TABLE IF NOT EXISTS `role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `role_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table humosoft_cnpm.role: ~0 rows (approximately)

-- Dumping structure for table humosoft_cnpm.salary
CREATE TABLE IF NOT EXISTS `salary` (
  `base_salary` int(11) NOT NULL,
  `bonuses` int(11) NOT NULL,
  `deductions` int(11) NOT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `net_salary` int(11) NOT NULL,
  `paygrade_id` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `salary_month` datetime(6) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKe4butiwjigtdancjka9we4tq6` (`paygrade_id`),
  KEY `FKe8hdt7omll7234r03bwmv8q07` (`user_id`),
  CONSTRAINT `FKe4butiwjigtdancjka9we4tq6` FOREIGN KEY (`paygrade_id`) REFERENCES `paygrade` (`id`),
  CONSTRAINT `FKe8hdt7omll7234r03bwmv8q07` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table humosoft_cnpm.salary: ~0 rows (approximately)

-- Dumping structure for table humosoft_cnpm.task
CREATE TABLE IF NOT EXISTS `task` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `end_date` datetime(6) DEFAULT NULL,
  `start_date` datetime(6) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `priority` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `task_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table humosoft_cnpm.task: ~0 rows (approximately)

-- Dumping structure for table humosoft_cnpm.user
CREATE TABLE IF NOT EXISTS `user` (
  `address_id` int(11) DEFAULT NULL,
  `department_id` int(11) DEFAULT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `position_id` int(11) DEFAULT NULL,
  `role_id` int(11) DEFAULT NULL,
  `status` bit(1) NOT NULL,
  `created_at` timestamp NULL DEFAULT current_timestamp(),
  `date_of_birth` datetime(6) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `full_name` varchar(255) DEFAULT NULL,
  `gender` varchar(255) DEFAULT NULL,
  `image` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKdhlcfg8h1drrgu0irs1ro3ohb` (`address_id`),
  KEY `FKgkh2fko1e4ydv1y6vtrwdc6my` (`department_id`),
  KEY `FK4ej0twvfqwwu5xdcns6u2qne3` (`position_id`),
  KEY `FKn82ha3ccdebhokx3a8fgdqeyy` (`role_id`),
  CONSTRAINT `FK4ej0twvfqwwu5xdcns6u2qne3` FOREIGN KEY (`position_id`) REFERENCES `position` (`id`),
  CONSTRAINT `FK5b1djfwe6asrt02n2xssq7adq` FOREIGN KEY (`address_id`) REFERENCES `addresses` (`id`),
  CONSTRAINT `FKgkh2fko1e4ydv1y6vtrwdc6my` FOREIGN KEY (`department_id`) REFERENCES `department` (`id`),
  CONSTRAINT `FKn82ha3ccdebhokx3a8fgdqeyy` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table humosoft_cnpm.user: ~0 rows (approximately)

-- Dumping structure for table humosoft_cnpm.user_tasks
CREATE TABLE IF NOT EXISTS `user_tasks` (
  `task_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  KEY `FKqgw406rca7wgi6lsvcli7vltl` (`task_id`),
  KEY `FK6kovin1hf8cbtxg2lealptr6m` (`user_id`),
  CONSTRAINT `FK6kovin1hf8cbtxg2lealptr6m` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKqgw406rca7wgi6lsvcli7vltl` FOREIGN KEY (`task_id`) REFERENCES `task` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table humosoft_cnpm.user_tasks: ~0 rows (approximately)

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
