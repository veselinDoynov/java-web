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



CREATE DATABASE IF NOT EXISTS `java-instructor` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `java-instructor`;


CREATE TABLE IF NOT EXISTS `course` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(128) DEFAULT NULL,
  `instructor_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `TITLE_UNIQUE` (`title`),
  KEY `FK_INSTRUCTOR_idx` (`instructor_id`),
  CONSTRAINT `FK_INSTRUCTOR` FOREIGN KEY (`instructor_id`) REFERENCES `instructor` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=331 DEFAULT CHARSET=latin1;


DELETE FROM `course`;
/*!40000 ALTER TABLE `course` DISABLE KEYS */;
INSERT INTO `course` (`id`, `title`, `instructor_id`) VALUES
	(24, 'Machine learning fundamentals - Monday 21.02', 12),
	(25, 'Udemy Java Lessons - Innovation Sprint', 13),
	(28, 'New java spring boot', NULL),
	(30, 'New java spring boot with hibernate and mysql docker container', 13),
	(197, 'Some new for multiple db', 130);
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
) ENGINE=InnoDB AUTO_INCREMENT=224 DEFAULT CHARSET=latin1;


DELETE FROM `instructor`;
/*!40000 ALTER TABLE `instructor` DISABLE KEYS */;
INSERT INTO `instructor` (`id`, `first_name`, `last_name`, `email`, `instructor_detail_id`) VALUES
	(12, 'Updated first name', 'Doinov', 'veselin_doynov@abv.bg', NULL),
	(13, 'Udemy', 'Degiro', 'udemy@flatexdegiro.com', NULL),
	(49, 'Post11', 'Man2', 'post@man.com', NULL),
	(130, 'Some New', 'New', 'new@new.com', NULL);
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


CREATE TABLE IF NOT EXISTS `jobrunr_backgroundjobservers` (
  `id` char(36) CHARACTER SET utf8 NOT NULL,
  `workerPoolSize` int(11) NOT NULL,
  `pollIntervalInSeconds` int(11) NOT NULL,
  `firstHeartbeat` datetime(6) NOT NULL,
  `lastHeartbeat` datetime(6) NOT NULL,
  `running` int(11) NOT NULL,
  `systemTotalMemory` bigint(20) NOT NULL,
  `systemFreeMemory` bigint(20) NOT NULL,
  `systemCpuLoad` decimal(3,2) NOT NULL,
  `processMaxMemory` bigint(20) NOT NULL,
  `processFreeMemory` bigint(20) NOT NULL,
  `processAllocatedMemory` bigint(20) NOT NULL,
  `processCpuLoad` decimal(3,2) NOT NULL,
  `deleteSucceededJobsAfter` varchar(32) DEFAULT NULL,
  `permanentlyDeleteJobsAfter` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `jobrunr_bgjobsrvrs_fsthb_idx` (`firstHeartbeat`),
  KEY `jobrunr_bgjobsrvrs_lsthb_idx` (`lastHeartbeat`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


DELETE FROM `jobrunr_backgroundjobservers`;
/*!40000 ALTER TABLE `jobrunr_backgroundjobservers` DISABLE KEYS */;
INSERT INTO `jobrunr_backgroundjobservers` (`id`, `workerPoolSize`, `pollIntervalInSeconds`, `firstHeartbeat`, `lastHeartbeat`, `running`, `systemTotalMemory`, `systemFreeMemory`, `systemCpuLoad`, `processMaxMemory`, `processFreeMemory`, `processAllocatedMemory`, `processCpuLoad`, `deleteSucceededJobsAfter`, `permanentlyDeleteJobsAfter`) VALUES
	('79e5954b-531c-4e69-a1be-7a6790f0c74f', 128, 15, '2022-02-27 11:29:27.586329', '2022-02-27 11:29:27.626818', 1, 68019343360, 46403358720, 0.00, 17012097024, 16943013736, 69083288, 0.41, 'PT36H', 'PT72H'),
	('b9ea319f-50b3-40cc-86cc-8d55dad6f8f0', 128, 15, '2022-02-27 11:29:29.898320', '2022-02-27 11:29:29.901021', 1, 68019343360, 46372036608, 0.40, 17012097024, 16945341936, 66755088, 0.38, 'PT36H', 'PT72H'),
	('bf5403ed-c02b-4da5-8f14-a60938ffe0d4', 128, 15, '2022-02-27 11:26:09.303640', '2022-02-27 11:30:09.307339', 1, 68019343360, 46691577856, 0.03, 17012097024, 16975064000, 37033024, 0.03, 'PT36H', 'PT72H');
/*!40000 ALTER TABLE `jobrunr_backgroundjobservers` ENABLE KEYS */;


CREATE TABLE IF NOT EXISTS `jobrunr_jobs` (
  `id` char(36) CHARACTER SET utf8 NOT NULL,
  `version` int(11) NOT NULL,
  `jobAsJson` mediumtext,
  `jobSignature` varchar(512) NOT NULL,
  `state` varchar(36) NOT NULL,
  `createdAt` datetime(6) NOT NULL,
  `updatedAt` datetime(6) NOT NULL,
  `scheduledAt` datetime(6) DEFAULT NULL,
  `recurringJobId` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `jobrunr_state_idx` (`state`),
  KEY `jobrunr_job_signature_idx` (`jobSignature`),
  KEY `jobrunr_job_created_at_idx` (`createdAt`),
  KEY `jobrunr_job_updated_at_idx` (`updatedAt`),
  KEY `jobrunr_job_scheduled_at_idx` (`scheduledAt`),
  KEY `jobrunr_job_rci_idx` (`recurringJobId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


DELETE FROM `jobrunr_jobs`;
/*!40000 ALTER TABLE `jobrunr_jobs` DISABLE KEYS */;
/*!40000 ALTER TABLE `jobrunr_jobs` ENABLE KEYS */;


CREATE TABLE `jobrunr_jobs_stats` (
	`total` BIGINT(21) NOT NULL,
	`awaiting` BIGINT(21) NULL,
	`scheduled` BIGINT(21) NULL,
	`enqueued` BIGINT(21) NULL,
	`processing` BIGINT(21) NULL,
	`failed` BIGINT(21) NULL,
	`succeeded` BIGINT(21) NULL,
	`allTimeSucceeded` DECIMAL(10,0) NULL,
	`deleted` BIGINT(21) NULL,
	`nbrOfBackgroundJobServers` BIGINT(21) NULL,
	`nbrOfRecurringJobs` BIGINT(21) NULL
) ENGINE=MyISAM;


CREATE TABLE IF NOT EXISTS `jobrunr_metadata` (
  `id` varchar(156) NOT NULL,
  `name` varchar(92) NOT NULL,
  `owner` varchar(64) NOT NULL,
  `value` text NOT NULL,
  `createdAt` datetime(6) NOT NULL,
  `updatedAt` datetime(6) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


DELETE FROM `jobrunr_metadata`;
/*!40000 ALTER TABLE `jobrunr_metadata` DISABLE KEYS */;
INSERT INTO `jobrunr_metadata` (`id`, `name`, `owner`, `value`, `createdAt`, `updatedAt`) VALUES
	('succeeded-jobs-counter-cluster', 'succeeded-jobs-counter', 'cluster', '0', '2022-02-27 09:24:35.000000', '2022-02-27 09:24:35.000000');
/*!40000 ALTER TABLE `jobrunr_metadata` ENABLE KEYS */;


CREATE TABLE IF NOT EXISTS `jobrunr_migrations` (
  `id` char(36) CHARACTER SET utf8 NOT NULL,
  `script` varchar(64) NOT NULL,
  `installedOn` varchar(29) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


DELETE FROM `jobrunr_migrations`;
/*!40000 ALTER TABLE `jobrunr_migrations` DISABLE KEYS */;
/*!40000 ALTER TABLE `jobrunr_migrations` ENABLE KEYS */;


CREATE TABLE IF NOT EXISTS `jobrunr_recurring_jobs` (
  `id` char(128) CHARACTER SET utf8 NOT NULL,
  `version` int(11) NOT NULL,
  `jobAsJson` text NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


DELETE FROM `jobrunr_recurring_jobs`;
/*!40000 ALTER TABLE `jobrunr_recurring_jobs` DISABLE KEYS */;
/*!40000 ALTER TABLE `jobrunr_recurring_jobs` ENABLE KEYS */;


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
) ENGINE=InnoDB AUTO_INCREMENT=154 DEFAULT CHARSET=latin1;


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


DROP TABLE IF EXISTS `jobrunr_jobs_stats`;
CREATE ALGORITHM=UNDEFINED SQL SECURITY DEFINER VIEW `jobrunr_jobs_stats` AS select count(0) AS `total`,(select count(0) from `jobrunr_jobs` `jobs` where (`jobs`.`state` = 'AWAITING')) AS `awaiting`,(select count(0) from `jobrunr_jobs` `jobs` where (`jobs`.`state` = 'SCHEDULED')) AS `scheduled`,(select count(0) from `jobrunr_jobs` `jobs` where (`jobs`.`state` = 'ENQUEUED')) AS `enqueued`,(select count(0) from `jobrunr_jobs` `jobs` where (`jobs`.`state` = 'PROCESSING')) AS `processing`,(select count(0) from `jobrunr_jobs` `jobs` where (`jobs`.`state` = 'FAILED')) AS `failed`,(select count(0) from `jobrunr_jobs` `jobs` where (`jobs`.`state` = 'SUCCEEDED')) AS `succeeded`,(select cast(cast(`jm`.`value` as char(10) charset utf8mb4) as decimal(10,0)) from `jobrunr_metadata` `jm` where (`jm`.`id` = 'succeeded-jobs-counter-cluster')) AS `allTimeSucceeded`,(select count(0) from `jobrunr_jobs` `jobs` where (`jobs`.`state` = 'DELETED')) AS `deleted`,(select count(0) from `jobrunr_backgroundjobservers`) AS `nbrOfBackgroundJobServers`,(select count(0) from `jobrunr_recurring_jobs`) AS `nbrOfRecurringJobs` from `jobrunr_jobs` `j`;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
