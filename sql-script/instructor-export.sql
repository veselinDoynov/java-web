
CREATE DATABASE IF NOT EXISTS `java-instructor`
USE `java-instructor`;


CREATE TABLE IF NOT EXISTS `course` (
  `id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(128) DEFAULT NULL,
  `instructor_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `TITLE_UNIQUE` (`title`),
  KEY `FK_INSTRUCTOR_idx` (`instructor_id`),
  CONSTRAINT `FK_INSTRUCTOR` FOREIGN KEY (`instructor_id`) REFERENCES `instructor` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=latin1;

-- Дъмп данни за таблица java-instructor.course: ~2 rows (приблизително)
DELETE FROM `course`;
/*!40000 ALTER TABLE `course` DISABLE KEYS */;
INSERT INTO `course` (`id`, `title`, `instructor_id`) VALUES
	(24, 'Machine learning fundamentals - Monday 21.02', 12),
	(25, 'Udemy Java Lessons - Innovation Sprint', 13);
/*!40000 ALTER TABLE `course` ENABLE KEYS */;


CREATE TABLE IF NOT EXISTS `course_student` (
  `course_id` int NOT NULL,
  `student_id` int NOT NULL,
  PRIMARY KEY (`course_id`,`student_id`),
  KEY `FK_STUDENT_idx` (`student_id`),
  CONSTRAINT `FK_COURSE_05` FOREIGN KEY (`course_id`) REFERENCES `course` (`id`),
  CONSTRAINT `FK_STUDENT` FOREIGN KEY (`student_id`) REFERENCES `student` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Дъмп данни за таблица java-instructor.course_student: ~8 rows (приблизително)
DELETE FROM `course_student`;
/*!40000 ALTER TABLE `course_student` DISABLE KEYS */;
INSERT INTO `course_student` (`course_id`, `student_id`) VALUES
	(25, 18),
	(24, 19),
	(24, 20),
	(24, 21),
	(24, 22),
	(24, 23),
	(24, 24),
	(25, 24);
/*!40000 ALTER TABLE `course_student` ENABLE KEYS */;

-- Дъмп структура за таблица java-instructor.instructor
CREATE TABLE IF NOT EXISTS `instructor` (
  `id` int NOT NULL AUTO_INCREMENT,
  `first_name` varchar(45) DEFAULT NULL,
  `last_name` varchar(45) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `instructor_detail_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_DETAIL_idx` (`instructor_detail_id`),
  CONSTRAINT `FK_DETAIL` FOREIGN KEY (`instructor_detail_id`) REFERENCES `instructor_detail` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=latin1;

-- Дъмп данни за таблица java-instructor.instructor: ~2 rows (приблизително)
DELETE FROM `instructor`;
/*!40000 ALTER TABLE `instructor` DISABLE KEYS */;
INSERT INTO `instructor` (`id`, `first_name`, `last_name`, `email`, `instructor_detail_id`) VALUES
	(12, 'Veselin', 'Doinov', 'veselin_doynov@abv.bg', NULL),
	(13, 'Udemy', 'Degiro', 'udemy@flatexdegiro.com', NULL);
/*!40000 ALTER TABLE `instructor` ENABLE KEYS */;

-- Дъмп структура за таблица java-instructor.instructor_detail
CREATE TABLE IF NOT EXISTS `instructor_detail` (
  `id` int NOT NULL AUTO_INCREMENT,
  `youtube_channel` varchar(128) DEFAULT NULL,
  `hobby` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;

-- Дъмп данни за таблица java-instructor.instructor_detail: ~1 rows (приблизително)
DELETE FROM `instructor_detail`;
/*!40000 ALTER TABLE `instructor_detail` DISABLE KEYS */;
/*!40000 ALTER TABLE `instructor_detail` ENABLE KEYS */;

-- Дъмп структура за таблица java-instructor.review
CREATE TABLE IF NOT EXISTS `review` (
  `id` int NOT NULL AUTO_INCREMENT,
  `comment` varchar(256) DEFAULT NULL,
  `course_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_COURSE_ID_idx` (`course_id`),
  CONSTRAINT `FK_COURSE` FOREIGN KEY (`course_id`) REFERENCES `course` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Дъмп данни за таблица java-instructor.review: ~0 rows (приблизително)
DELETE FROM `review`;
/*!40000 ALTER TABLE `review` DISABLE KEYS */;
/*!40000 ALTER TABLE `review` ENABLE KEYS */;

-- Дъмп структура за таблица java-instructor.student
CREATE TABLE IF NOT EXISTS `student` (
  `id` int NOT NULL AUTO_INCREMENT,
  `first_name` varchar(45) DEFAULT NULL,
  `last_name` varchar(45) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=latin1;

-- Дъмп данни за таблица java-instructor.student: ~7 rows (приблизително)
DELETE FROM `student`;
/*!40000 ALTER TABLE `student` DISABLE KEYS */;
INSERT INTO `student` (`id`, `first_name`, `last_name`, `email`) VALUES
	(18, 'Vesco', 'Doynov', 'veselin_doynov@abv.bg'),
	(19, 'Olga', '', ''),
	(20, 'Yelena', '', ''),
	(21, 'Drago', '', ''),
	(22, 'Maria', '', ''),
	(23, 'Jivko', '', ''),
	(24, 'Hristo', '', '');
/*!40000 ALTER TABLE `student` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
