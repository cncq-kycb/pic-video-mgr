SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for pic
-- ----------------------------
DROP TABLE IF EXISTS `pic`;
CREATE TABLE `pic` (
  `pic_id` int unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `pic_name_id` int NOT NULL COMMENT '图片名称id',
  `pic_path` varchar(100) NOT NULL COMMENT '图片保存路径',
  `pic_url` varchar(255) NOT NULL COMMENT '图片访问url',
  PRIMARY KEY (`pic_id`),
  KEY `pic_name_id` (`pic_name_id`),
  CONSTRAINT `pic_name_id` FOREIGN KEY (`pic_name_id`) REFERENCES `pic_name` (`pic_name_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for pic_name
-- ----------------------------
DROP TABLE IF EXISTS `pic_name`;
CREATE TABLE `pic_name` (
  `pic_name_id` int NOT NULL AUTO_INCREMENT COMMENT '图片名称id',
  `pic_name` varchar(255) NOT NULL COMMENT '图片名称',
  PRIMARY KEY (`pic_name_id`),
  UNIQUE KEY `unique_name` (`pic_name`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- View structure for pic_view
-- ----------------------------
DROP VIEW IF EXISTS `pic_view`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`%` SQL SECURITY DEFINER VIEW `pic_view` AS select `p`.`pic_id` AS `pic_id`,`n`.`pic_name` AS `pic_name`,`p`.`pic_path` AS `pic_path`,`p`.`pic_url` AS `pic_url` from (`pic` `p` join `pic_name` `n`) where (`p`.`pic_name_id` = `n`.`pic_name_id`);

SET FOREIGN_KEY_CHECKS = 1;
