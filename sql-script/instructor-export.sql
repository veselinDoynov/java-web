-- --------------------------------------------------------
-- Хост:                         127.0.0.1
-- Версия на сървъра:            5.7.37 - MySQL Community Server (GPL)
-- ОС на сървъра:                Linux
-- HeidiSQL Версия:              11.3.0.6295
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;



CREATE DATABASE IF NOT EXISTS `java-instructor` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;
USE `java-instructor`;


CREATE TABLE IF NOT EXISTS `course` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(128) DEFAULT NULL,
  `instructor_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `TITLE_UNIQUE` (`title`),
  KEY `FK_INSTRUCTOR_idx` (`instructor_id`),
  CONSTRAINT `FK_INSTRUCTOR` FOREIGN KEY (`instructor_id`) REFERENCES `instructor` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=latin1;


DELETE FROM `course`;
/*!40000 ALTER TABLE `course` DISABLE KEYS */;
INSERT INTO `course` (`id`, `title`, `instructor_id`) VALUES
	(24, 'Machine learning fundamentals - Monday 21.02', 12),
	(25, 'Udemy Java Lessons - Innovation Sprint', 13),
	(28, 'New java spring boot', NULL),
	(30, 'New java spring boot with hibernate and mysql docker container', 13);
/*!40000 ALTER TABLE `course` ENABLE KEYS */;


CREATE TABLE IF NOT EXISTS `course_student` (
  `course_id` int(11) NOT NULL,
  `student_id` int(11) NOT NULL,
  PRIMARY KEY (`course_id`,`student_id`),
  KEY `FK_STUDENT_idx` (`student_id`),
  CONSTRAINT `FK_COURSE_05` FOREIGN KEY (`course_id`) REFERENCES `course` (`id`),
  CONSTRAINT `FK_STUDENT` FOREIGN KEY (`student_id`) REFERENCES `student` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


DELETE FROM `course_student`;
/*!40000 ALTER TABLE `course_student` DISABLE KEYS */;
INSERT INTO `course_student` (`course_id`, `student_id`) VALUES
	(24, 18),
	(24, 19),
	(24, 20),
	(24, 21),
	(24, 22),
	(24, 23),
	(24, 24),
	(25, 24),
	(30, 27),
	(30, 28);
/*!40000 ALTER TABLE `course_student` ENABLE KEYS */;


CREATE TABLE IF NOT EXISTS `instructor` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `first_name` varchar(45) DEFAULT NULL,
  `last_name` varchar(45) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `instructor_detail_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_DETAIL_idx` (`instructor_detail_id`),
  CONSTRAINT `FK_DETAIL` FOREIGN KEY (`instructor_detail_id`) REFERENCES `instructor_detail` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=latin1;


DELETE FROM `instructor`;
/*!40000 ALTER TABLE `instructor` DISABLE KEYS */;
INSERT INTO `instructor` (`id`, `first_name`, `last_name`, `email`, `instructor_detail_id`) VALUES
	(12, 'Veselin', 'Doinov', 'veselin_doynov@abv.bg', NULL),
	(13, 'Udemy', 'Degiro', 'udemy@flatexdegiro.com', NULL),
	(49, 'Post11', 'Man2', 'post@man.com', NULL);
/*!40000 ALTER TABLE `instructor` ENABLE KEYS */;


CREATE TABLE IF NOT EXISTS `instructor_detail` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `youtube_channel` varchar(128) DEFAULT NULL,
  `hobby` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


DELETE FROM `instructor_detail`;
/*!40000 ALTER TABLE `instructor_detail` DISABLE KEYS */;
/*!40000 ALTER TABLE `instructor_detail` ENABLE KEYS */;


CREATE TABLE IF NOT EXISTS `review` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `comment` varchar(256) DEFAULT NULL,
  `course_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_COURSE_ID_idx` (`course_id`),
  CONSTRAINT `FK_COURSE` FOREIGN KEY (`course_id`) REFERENCES `course` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


DELETE FROM `review`;
/*!40000 ALTER TABLE `review` DISABLE KEYS */;
/*!40000 ALTER TABLE `review` ENABLE KEYS */;


CREATE TABLE IF NOT EXISTS `student` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `first_name` varchar(45) DEFAULT NULL,
  `last_name` varchar(45) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=latin1;


DELETE FROM `student`;
/*!40000 ALTER TABLE `student` DISABLE KEYS */;
INSERT INTO `student` (`id`, `first_name`, `last_name`, `email`) VALUES
	(18, 'Vesco', 'Doynov', 'veselin_doynov@abv.bg'),
	(19, 'Olga', '', ''),
	(20, 'Yelena', '', ''),
	(21, 'Drago', '', ''),
	(22, 'Maria', '', ''),
	(23, 'Jivko', '', ''),
	(24, 'Hristo', '', ''),
	(27, 'Student 1', 'Studentov', 'student@stu.com'),
	(28, 'Student 2', 'Studentov', 'student2@stu.com');
/*!40000 ALTER TABLE `student` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
