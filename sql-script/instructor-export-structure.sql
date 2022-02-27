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


CREATE TABLE IF NOT EXISTS `course_student` (
  `course_id` int(11) NOT NULL,
  `student_id` int(11) NOT NULL,
  PRIMARY KEY (`course_id`,`student_id`),
  KEY `FK_STUDENT_idx` (`student_id`),
  CONSTRAINT `FK_COURSE_05` FOREIGN KEY (`course_id`) REFERENCES `course` (`id`),
  CONSTRAINT `FK_STUDENT` FOREIGN KEY (`student_id`) REFERENCES `student` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Изнасянето на данните беше деселектирано.

-- Дъмп структура за таблица java-instructor.instructor
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


CREATE TABLE IF NOT EXISTS `instructor_detail` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `youtube_channel` varchar(128) DEFAULT NULL,
  `hobby` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


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

-- Изнасянето на данните беше деселектирано.

-- Дъмп структура за таблица java-instructor.jobrunr_jobs
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


CREATE TABLE IF NOT EXISTS `jobrunr_migrations` (
  `id` char(36) CHARACTER SET utf8 NOT NULL,
  `script` varchar(64) NOT NULL,
  `installedOn` varchar(29) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


CREATE TABLE IF NOT EXISTS `jobrunr_recurring_jobs` (
  `id` char(128) CHARACTER SET utf8 NOT NULL,
  `version` int(11) NOT NULL,
  `jobAsJson` text NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


CREATE TABLE IF NOT EXISTS `review` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `comment` varchar(256) DEFAULT NULL,
  `course_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_COURSE_ID_idx` (`course_id`),
  CONSTRAINT `FK_COURSE` FOREIGN KEY (`course_id`) REFERENCES `course` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


CREATE TABLE IF NOT EXISTS `student` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `first_name` varchar(45) DEFAULT NULL,
  `last_name` varchar(45) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=154 DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `jobrunr_jobs_stats`;
CREATE ALGORITHM=UNDEFINED SQL SECURITY DEFINER VIEW `jobrunr_jobs_stats` AS select count(0) AS `total`,(select count(0) from `jobrunr_jobs` `jobs` where (`jobs`.`state` = 'AWAITING')) AS `awaiting`,(select count(0) from `jobrunr_jobs` `jobs` where (`jobs`.`state` = 'SCHEDULED')) AS `scheduled`,(select count(0) from `jobrunr_jobs` `jobs` where (`jobs`.`state` = 'ENQUEUED')) AS `enqueued`,(select count(0) from `jobrunr_jobs` `jobs` where (`jobs`.`state` = 'PROCESSING')) AS `processing`,(select count(0) from `jobrunr_jobs` `jobs` where (`jobs`.`state` = 'FAILED')) AS `failed`,(select count(0) from `jobrunr_jobs` `jobs` where (`jobs`.`state` = 'SUCCEEDED')) AS `succeeded`,(select cast(cast(`jm`.`value` as char(10) charset utf8mb4) as decimal(10,0)) from `jobrunr_metadata` `jm` where (`jm`.`id` = 'succeeded-jobs-counter-cluster')) AS `allTimeSucceeded`,(select count(0) from `jobrunr_jobs` `jobs` where (`jobs`.`state` = 'DELETED')) AS `deleted`,(select count(0) from `jobrunr_backgroundjobservers`) AS `nbrOfBackgroundJobServers`,(select count(0) from `jobrunr_recurring_jobs`) AS `nbrOfRecurringJobs` from `jobrunr_jobs` `j`;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
