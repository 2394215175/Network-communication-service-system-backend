/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50729
Source Host           : localhost:3306
Source Database       : netservice_boot

Target Server Type    : MYSQL
Target Server Version : 50729
File Encoding         : 65001

Date: 2020-10-14 09:18:12
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for antenna
-- ----------------------------
DROP TABLE IF EXISTS `antenna`;
CREATE TABLE `antenna` (
  `id_` int(11) NOT NULL AUTO_INCREMENT,
  `lowAngle_` int(6) NOT NULL,
  `directionAngle_` int(6) NOT NULL,
  `sign_` char(2) NOT NULL,
  `differ_` int(4) NOT NULL,
  `sum_` int(4) NOT NULL,
  `locking_` int(4) NOT NULL,
  `datatime_` char(20) NOT NULL,
  `terminalid_` int(11) DEFAULT NULL,
  PRIMARY KEY (`id_`) USING BTREE,
  KEY `terminalid_` (`terminalid_`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of antenna
-- ----------------------------
INSERT INTO `antenna` VALUES ('13', '36', '15', '卫星', '1', '2', '2', '2020-10-10 08:51:21', '0');
INSERT INTO `antenna` VALUES ('14', '36', '15', '卫星', '1', '2', '2', '2020-10-10 08:54:34', '48');
INSERT INTO `antenna` VALUES ('15', '36', '15', '卫星', '1', '2', '2', '2020-10-10 08:57:34', '116');
INSERT INTO `antenna` VALUES ('16', '36', '15', '卫星', '1', '2', '2', '2020-10-10 09:12:51', '20');
INSERT INTO `antenna` VALUES ('17', '36', '15', '卫星', '1', '2', '2', '2020-10-10 09:52:51', '20');
INSERT INTO `antenna` VALUES ('18', '36', '15', '卫星', '1', '2', '2', '2020-10-10 09:56:07', '29');
INSERT INTO `antenna` VALUES ('19', '36', '15', '卫星', '1', '2', '2', '2020-10-10 10:08:21', '109');
INSERT INTO `antenna` VALUES ('20', '36', '15', '卫星', '1', '2', '2', '2020-10-10 10:14:16', '96');

-- ----------------------------
-- Table structure for terminal
-- ----------------------------
DROP TABLE IF EXISTS `terminal`;
CREATE TABLE `terminal` (
  `id_` int(11) NOT NULL AUTO_INCREMENT,
  `name_` varchar(50) NOT NULL,
  `ip_` varchar(50) NOT NULL,
  `port_` smallint(6) NOT NULL,
  PRIMARY KEY (`id_`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of terminal
-- ----------------------------
INSERT INTO `terminal` VALUES ('1', '123', '123', '123');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id_` int(11) NOT NULL AUTO_INCREMENT,
  `username_` varchar(50) NOT NULL,
  `password_` varchar(50) NOT NULL,
  `telephone_` varchar(11) DEFAULT NULL,
  `address_` varchar(50) DEFAULT NULL,
  `permission_` int(4) NOT NULL,
  PRIMARY KEY (`id_`) USING BTREE,
  UNIQUE KEY `username_` (`username_`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'abcdef', 'e10adc3949ba59abbe56e057f20f883e', '123456789', '123456', '0');
INSERT INTO `user` VALUES ('2', 'zhangsan', '96e79218965eb72c92a549dd5a330112', null, null, '1');
INSERT INTO `user` VALUES ('3', 'wangwu', 'f379eaf3c831b04de153469d1bec345e', null, null, '1');
INSERT INTO `user` VALUES ('4', '123456', 'e10adc3949ba59abbe56e057f20f883e', null, null, '1');
INSERT INTO `user` VALUES ('5', '123', '123', null, null, '1');

-- ----------------------------
-- Table structure for weather
-- ----------------------------
DROP TABLE IF EXISTS `weather`;
CREATE TABLE `weather` (
  `id_` int(11) NOT NULL AUTO_INCREMENT,
  `temperature_` int(6) NOT NULL,
  `humidity_` int(6) NOT NULL,
  `illumination_` int(11) NOT NULL,
  `pressure_` int(4) NOT NULL,
  `velocity_` int(6) NOT NULL,
  `direction_` int(4) NOT NULL,
  `rainfall_` int(6) NOT NULL,
  `datatime_` char(20) NOT NULL,
  `terminalid_` int(11) DEFAULT NULL,
  PRIMARY KEY (`id_`) USING BTREE,
  KEY `terminalid_` (`terminalid_`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of weather
-- ----------------------------
INSERT INTO `weather` VALUES ('15', '90', '60', '30', '100', '20', '1', '50', '2020-10-10 09:53:02', '0');
INSERT INTO `weather` VALUES ('16', '90', '60', '30', '100', '20', '1', '50', '2020-10-10 09:53:12', '0');
INSERT INTO `weather` VALUES ('17', '90', '60', '30', '100', '20', '1', '50', '2020-10-10 09:56:07', '29');
INSERT INTO `weather` VALUES ('18', '90', '60', '30', '100', '20', '1', '50', '2020-10-10 10:08:21', '109');
INSERT INTO `weather` VALUES ('19', '90', '60', '30', '100', '20', '1', '50', '2020-10-10 10:14:15', '96');
