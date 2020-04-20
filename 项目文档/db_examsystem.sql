/*
Navicat MySQL Data Transfer

Source Server         : ALIYUN
Source Server Version : 50728
Source Host           : 120.27.145.98:3306
Source Database       : db_examsystem

Target Server Type    : MYSQL
Target Server Version : 50728
File Encoding         : 65001

Date: 2020-04-13 17:43:24
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for tb_admin
-- ----------------------------
DROP TABLE IF EXISTS `tb_admin`;
CREATE TABLE `tb_admin` (
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  PRIMARY KEY (`username`,`password`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of tb_admin
-- ----------------------------

-- ----------------------------
-- Table structure for tb_copy
-- ----------------------------
DROP TABLE IF EXISTS `tb_copy`;
CREATE TABLE `tb_copy` (
  `copy_id` int(11) NOT NULL,
  `group_id` varchar(6) NOT NULL,
  `exam_id` int(11) NOT NULL,
  `open_id` varchar(255) NOT NULL,
  `status` int(10) NOT NULL,
  `judge` varchar(255) NOT NULL,
  `score` int(11) NOT NULL,
  PRIMARY KEY (`copy_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of tb_copy
-- ----------------------------

-- ----------------------------
-- Table structure for tb_copy_to_quetion
-- ----------------------------
DROP TABLE IF EXISTS `tb_copy_to_quetion`;
CREATE TABLE `tb_copy_to_quetion` (
  `copy_id` int(11) NOT NULL,
  `id` int(11) NOT NULL,
  `score` int(10) NOT NULL,
  `answer` varchar(255) NOT NULL,
  `already` tinyint(1) NOT NULL,
  PRIMARY KEY (`copy_id`,`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of tb_copy_to_quetion
-- ----------------------------

-- ----------------------------
-- Table structure for tb_exam
-- ----------------------------
DROP TABLE IF EXISTS `tb_exam`;
CREATE TABLE `tb_exam` (
  `name` varchar(255) NOT NULL,
  `id` varchar(6) NOT NULL,
  `group_id` int(255) NOT NULL,
  `type` tinyint(1) NOT NULL,
  `score` varchar(255) NOT NULL,
  `begin_time` datetime NOT NULL,
  `end_time` datetime NOT NULL,
  `publisher_id` int(11) NOT NULL,
  `status` int(10) NOT NULL,
  PRIMARY KEY (`id`,`group_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of tb_exam
-- ----------------------------

-- ----------------------------
-- Table structure for tb_exam_to_question
-- ----------------------------
DROP TABLE IF EXISTS `tb_exam_to_question`;
CREATE TABLE `tb_exam_to_question` (
  `score` int(10) NOT NULL,
  `title` varchar(255) NOT NULL,
  `type` int(10) NOT NULL,
  `current` varchar(255) NOT NULL,
  `answer_a` varchar(255) NOT NULL,
  `answer_b` varchar(255) NOT NULL,
  `answer_c` varchar(255) NOT NULL,
  `answer_d` varchar(255) NOT NULL,
  `exam_id` int(11) NOT NULL,
  `id` int(11) NOT NULL,
  PRIMARY KEY (`exam_id`,`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of tb_exam_to_question
-- ----------------------------

-- ----------------------------
-- Table structure for tb_group
-- ----------------------------
DROP TABLE IF EXISTS `tb_group`;
CREATE TABLE `tb_group` (
  `name` varchar(255) NOT NULL,
  `group_id` varchar(255) NOT NULL,
  `open_id` varchar(255) NOT NULL,
  `status` tinyint(1) NOT NULL,
  PRIMARY KEY (`group_id`,`open_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of tb_group
-- ----------------------------

-- ----------------------------
-- Table structure for tb_question
-- ----------------------------
DROP TABLE IF EXISTS `tb_question`;
CREATE TABLE `tb_question` (
  `title` varchar(255) NOT NULL,
  `type` int(10) NOT NULL,
  `category` varchar(255) NOT NULL,
  `current` varchar(255) NOT NULL,
  `answer_A` varchar(255) NOT NULL,
  `answer_B` varchar(255) NOT NULL,
  `answer_C` varchar(255) NOT NULL,
  `answer_D` varchar(255) NOT NULL,
  `id` int(11) NOT NULL,
  `open_id` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of tb_question
-- ----------------------------

-- ----------------------------
-- Table structure for tb_user
-- ----------------------------
DROP TABLE IF EXISTS `tb_user`;
CREATE TABLE `tb_user` (
  `nickName` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `open_id` varchar(255) NOT NULL,
  `avatarUrl` varchar(255) NOT NULL,
  `availble` tinyint(1) NOT NULL,
  PRIMARY KEY (`open_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of tb_user
-- ----------------------------
