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

-- Dumping structure for table humosoft_cnpm.attendance
CREATE TABLE IF NOT EXISTS `attendance` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `total_hours` double NOT NULL,
  `user_id` int(11) DEFAULT NULL,
  `date` datetime(6) DEFAULT NULL,
  `check_in` varchar(255) DEFAULT NULL,
  `check_out` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK46cuxphi3uh5quom51s6i2q8x` (`user_id`),
  CONSTRAINT `FK46cuxphi3uh5quom51s6i2q8x` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table humosoft_cnpm.attendance: ~0 rows (approximately)

-- Dumping structure for table humosoft_cnpm.department
CREATE TABLE IF NOT EXISTS `department` (
  `employees` int(11) NOT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `department_name` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table humosoft_cnpm.department: ~2 rows (approximately)
REPLACE INTO `department` (`employees`, `id`, `department_name`, `description`) VALUES
	(0, 1, 'Human Resources', 'Responsible for managing employee relations, benefits, and recruitment.'),
	(0, 2, 'Human Resources', 'Responsible for managing employee relations, benefits, and recruitment.');

-- Dumping structure for table humosoft_cnpm.holiday
CREATE TABLE IF NOT EXISTS `holiday` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `end` datetime(6) DEFAULT NULL,
  `start` datetime(6) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
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
  `base_salary` int(11) DEFAULT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `paygrade_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table humosoft_cnpm.paygrade: ~5 rows (approximately)
REPLACE INTO `paygrade` (`base_salary`, `id`, `description`, `paygrade_name`) VALUES
	(NULL, 1, 'Responsible for overseeing the operations of the department.', 'Manager'),
	(NULL, 2, 'Responsible for overseeing the operations of the department.', 'Manager'),
	(NULL, 3, 'Responsible for overseeing the operations of the department.', 'Manager'),
	(NULL, 4, 'Responsible for overseeing department operations and managing teams.', 'Manager'),
	(NULL, 5, 'Responsible for overseeing department operations and managing teams.', 'Manager');

-- Dumping structure for table humosoft_cnpm.position
CREATE TABLE IF NOT EXISTS `position` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `paygrade_id` int(11) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `position_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKkbhewy4pgt07hmbk68o1i06er` (`paygrade_id`),
  CONSTRAINT `FKkbhewy4pgt07hmbk68o1i06er` FOREIGN KEY (`paygrade_id`) REFERENCES `paygrade` (`id`)
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
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table humosoft_cnpm.role: ~8 rows (approximately)
REPLACE INTO `role` (`id`, `description`, `name`) VALUES
	(1, NULL, 'ADMIN'),
	(2, NULL, 'STAFF'),
	(3, NULL, 'USER'),
	(4, NULL, 'ROLE_ADMIN'),
	(5, NULL, 'STAFF'),
	(6, NULL, 'ROLE_STAFF'),
	(7, NULL, 'USER'),
	(8, NULL, 'ROLE_USER');

-- Dumping structure for table humosoft_cnpm.salary
CREATE TABLE IF NOT EXISTS `salary` (
  `base_salary` int(11) NOT NULL,
  `bonuses` int(11) NOT NULL,
  `deductions` int(11) NOT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `net_salary` int(11) NOT NULL,
  `paygrade_id` int(11) DEFAULT NULL,
  `status` bit(1) NOT NULL,
  `user_id` int(11) DEFAULT NULL,
  `salary_month` datetime(6) DEFAULT NULL,
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
  `department_id` int(11) DEFAULT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `position_id` int(11) DEFAULT NULL,
  `role_id` int(11) DEFAULT NULL,
  `status` bit(1) NOT NULL,
  `created_at` timestamp NULL DEFAULT current_timestamp(),
  `date_of_birth` datetime(6) DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  `commune` varchar(255) DEFAULT NULL,
  `country` varchar(255) DEFAULT NULL,
  `district` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `full_name` varchar(255) DEFAULT NULL,
  `gender` varchar(255) DEFAULT NULL,
  `house_number` varchar(255) DEFAULT NULL,
  `image` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `postal_code` varchar(255) DEFAULT NULL,
  `state` varchar(255) DEFAULT NULL,
  `street` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKgkh2fko1e4ydv1y6vtrwdc6my` (`department_id`),
  KEY `FK4ej0twvfqwwu5xdcns6u2qne3` (`position_id`),
  KEY `FKn82ha3ccdebhokx3a8fgdqeyy` (`role_id`),
  CONSTRAINT `FK4ej0twvfqwwu5xdcns6u2qne3` FOREIGN KEY (`position_id`) REFERENCES `position` (`id`),
  CONSTRAINT `FKgkh2fko1e4ydv1y6vtrwdc6my` FOREIGN KEY (`department_id`) REFERENCES `department` (`id`),
  CONSTRAINT `FKn82ha3ccdebhokx3a8fgdqeyy` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table humosoft_cnpm.user: ~2 rows (approximately)
REPLACE INTO `user` (`department_id`, `id`, `position_id`, `role_id`, `status`, `created_at`, `date_of_birth`, `city`, `commune`, `country`, `district`, `email`, `full_name`, `gender`, `house_number`, `image`, `password`, `phone`, `postal_code`, `state`, `street`, `username`) VALUES
	(NULL, 1, NULL, 8, b'1', NULL, '1985-06-15 00:00:00.000000', 'New York', 'Downtown', 'USA', 'Manhattan', 'john.doe@example.com', 'John Doe', 'Male', '123', 'profile.jpg', '$2a$10$//6GspGy2MOBz5mjkm8vlewhteAWQX06mfchlg2fM1aIs8soIq2zG', '123-456-7890', '10001', 'NY', 'Main Street', 'johndoe'),
	(NULL, 2, NULL, NULL, b'1', NULL, '1985-06-15 00:00:00.000000', 'New York', 'Downtown', 'USA', 'Manhattan', 'john.doe@example.com', 'John Doe', 'Male', '123', 'profile.jpg', '$2a$10$yzUEGg2Nrz1XYzwFQFrCxObiKy.4/UWYSbpD2dve1x2zY30jH2Hba', '123-456-7890', '10001', 'NY', 'Main Street', 'johndoe');

-- Dumping structure for table humosoft_cnpm.user_role
CREATE TABLE IF NOT EXISTS `user_role` (
  `user_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `FKa68196081fvovjhkek5m97n3y` (`role_id`),
  CONSTRAINT `FK859n2jvi8ivhui0rl0esws6o` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKa68196081fvovjhkek5m97n3y` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table humosoft_cnpm.user_role: ~1 rows (approximately)
REPLACE INTO `user_role` (`user_id`, `role_id`) VALUES
	(2, 8);

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
