/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 50720
 Source Host           : localhost:3306
 Source Schema         : project

 Target Server Type    : MySQL
 Target Server Version : 50720
 File Encoding         : 65001

 Date: 18/09/2019 16:51:56
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for administrator
-- ----------------------------
DROP TABLE IF EXISTS `administrator`;
CREATE TABLE `administrator`  (
  `admin_name` varchar(5) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '管理员姓名',
  `password` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码',
  `oid` int(10) NULL DEFAULT NULL COMMENT '部门Id',
  PRIMARY KEY (`admin_name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of administrator
-- ----------------------------
INSERT INTO `administrator` VALUES ('admin', 'admin', 0);

-- ----------------------------
-- Table structure for groups
-- ----------------------------
DROP TABLE IF EXISTS `groups`;
CREATE TABLE `groups`  (
  `project_id` int(10) NULL DEFAULT NULL COMMENT '项目id',
  `user_id` int(10) NULL DEFAULT NULL COMMENT '用户id'
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of groups
-- ----------------------------
INSERT INTO `groups` VALUES (1, 1);
INSERT INTO `groups` VALUES (1, 2);
INSERT INTO `groups` VALUES (15, 1);
INSERT INTO `groups` VALUES (15, 2);
INSERT INTO `groups` VALUES (15, 1);
INSERT INTO `groups` VALUES (15, 2);
INSERT INTO `groups` VALUES (17, 1);
INSERT INTO `groups` VALUES (19, 1);
INSERT INTO `groups` VALUES (19, 2);
INSERT INTO `groups` VALUES (19, 3);
INSERT INTO `groups` VALUES (19, 8);
INSERT INTO `groups` VALUES (2, 1);
INSERT INTO `groups` VALUES (3, 1);
INSERT INTO `groups` VALUES (4, 1);
INSERT INTO `groups` VALUES (6, 1);
INSERT INTO `groups` VALUES (7, 1);

-- ----------------------------
-- Table structure for keywords
-- ----------------------------
DROP TABLE IF EXISTS `keywords`;
CREATE TABLE `keywords`  (
  `project_id` int(10) NOT NULL COMMENT '项目id',
  `keywords` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '关键字',
  PRIMARY KEY (`project_id`) USING BTREE,
  CONSTRAINT `froegin_key_project_id` FOREIGN KEY (`project_id`) REFERENCES `project` (`project_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of keywords
-- ----------------------------
INSERT INTO `keywords` VALUES (19, '程序员,人员,程序');

-- ----------------------------
-- Table structure for organization
-- ----------------------------
DROP TABLE IF EXISTS `organization`;
CREATE TABLE `organization`  (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `pid` int(11) NULL DEFAULT NULL COMMENT '父Id',
  `name` varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '名字',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of organization
-- ----------------------------
INSERT INTO `organization` VALUES (1, 0, '上海理工大学');
INSERT INTO `organization` VALUES (2, 1, '光电信息与计算机工程学院');
INSERT INTO `organization` VALUES (3, 1, '机械工程学院');
INSERT INTO `organization` VALUES (4, 1, '能源与动力工程学院');
INSERT INTO `organization` VALUES (5, 1, '医疗器械与食品学院');
INSERT INTO `organization` VALUES (6, 1, '管理学院');
INSERT INTO `organization` VALUES (7, 1, '出版印刷与艺术设计学院');
INSERT INTO `organization` VALUES (8, 1, '外语学院');

-- ----------------------------
-- Table structure for project
-- ----------------------------
DROP TABLE IF EXISTS `project`;
CREATE TABLE `project`  (
  `project_id` int(10) NOT NULL AUTO_INCREMENT COMMENT '工程id',
  `project_name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '工程名称',
  `leader_id` int(10) NULL DEFAULT NULL COMMENT '负责人id',
  `start_time` date NULL DEFAULT NULL COMMENT '开始日期',
  `end_time` date NULL DEFAULT NULL COMMENT '竣工日期',
  `img` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图片',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `last_upd_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '最近修改时间',
  PRIMARY KEY (`project_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 20 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of project
-- ----------------------------
INSERT INTO `project` VALUES (1, '1a', 1, '2019-08-12', NULL, 'project/4a9ee87ac131458898fa80f4dbffef17.png', '2019-09-02 15:13:43', '2019-09-02 15:13:45');
INSERT INTO `project` VALUES (2, '2a', 2, '2019-08-13', NULL, 'project/4a9ee87ac131458898fa80f4dbffef17.png', '2019-09-02 15:13:45', '2019-09-02 15:13:47');
INSERT INTO `project` VALUES (3, '3a', 1, '2019-08-15', NULL, 'project/4a9ee87ac131458898fa80f4dbffef17.png', '2019-09-02 15:13:48', '2019-09-02 15:13:49');
INSERT INTO `project` VALUES (4, '4a', 2, '2019-08-15', NULL, 'project/4a9ee87ac131458898fa80f4dbffef17.png', '2019-09-02 15:13:50', '2019-09-02 15:13:52');
INSERT INTO `project` VALUES (5, '5a', 2, '2019-08-15', NULL, 'project/4a9ee87ac131458898fa80f4dbffef17.png', '2019-09-02 15:13:52', '2019-09-02 15:13:54');
INSERT INTO `project` VALUES (6, '6a', 2, '2019-08-15', NULL, 'project/4a9ee87ac131458898fa80f4dbffef17.png', '2019-09-02 15:13:55', '2019-09-02 15:13:56');
INSERT INTO `project` VALUES (7, '7a', 2, '2019-08-15', NULL, 'project/4a9ee87ac131458898fa80f4dbffef17.png', '2019-09-02 15:13:57', '2019-09-02 15:13:59');
INSERT INTO `project` VALUES (8, '8a', 2, '2019-08-15', NULL, 'project/4a9ee87ac131458898fa80f4dbffef17.png', '2019-09-02 15:14:00', '2019-09-02 15:14:01');
INSERT INTO `project` VALUES (9, '123', 1, NULL, NULL, '', '2019-09-02 15:14:04', '2019-09-02 15:14:06');
INSERT INTO `project` VALUES (10, 'aaa', 1, '2019-08-20', '2019-08-28', 'projectLog/191919a07bc3476ab1ea4796ca05976b.png projectLog/5a0df8eec048439e8f7f7d39c865b667.png projectLog/e168c502a4c74a36ab9ddfe2a12b27e4.png', '2019-09-02 15:14:06', '2019-09-02 15:14:08');
INSERT INTO `project` VALUES (11, 'aaa', 1, '2019-08-20', NULL, 'project/4640002a956a4eb094d34d3dd6035dc2.png', '2019-09-02 15:14:10', '2019-09-02 15:14:11');
INSERT INTO `project` VALUES (12, '666', 1, '2019-08-20', NULL, ' ', '2019-09-02 15:14:13', '2019-09-02 15:14:15');
INSERT INTO `project` VALUES (13, '132', 1, '2019-08-20', NULL, 'project/193a5f77f7484bd38bce4f3c83399d1e.png', '2019-09-02 15:14:15', '2019-09-02 15:14:17');
INSERT INTO `project` VALUES (14, '阿萨德', 3, '2019-08-21', NULL, '', '2019-09-02 15:14:18', '2019-09-02 15:14:19');
INSERT INTO `project` VALUES (15, 'aaas ', 3, '2019-08-21', NULL, 'project/9804b59871214c3fae755b5a55cbde25.png', '2019-09-02 15:14:20', '2019-09-02 15:14:22');
INSERT INTO `project` VALUES (16, '测试', 3, '2019-08-23', NULL, 'project/c15980457f234e6f8827f7d90058ff26.png', '2019-09-02 15:14:24', '2019-09-02 15:14:25');
INSERT INTO `project` VALUES (17, '测试 2019-08-23', 3, '2019-08-23', NULL, 'project/efff8d6d99534f718be760a681be7dd0.png', '2019-09-02 15:14:27', '2019-09-02 15:14:28');
INSERT INTO `project` VALUES (18, '123132', 3, '2019-09-02', '2019-09-02', '', '2019-09-02 15:14:29', '2019-09-02 15:14:33');
INSERT INTO `project` VALUES (19, '123124', 3, '2019-09-02', NULL, '', '2019-09-02 15:14:34', '2019-09-02 15:14:39');

-- ----------------------------
-- Table structure for project_log
-- ----------------------------
DROP TABLE IF EXISTS `project_log`;
CREATE TABLE `project_log`  (
  `log_id` int(10) NOT NULL AUTO_INCREMENT COMMENT '日志id',
  `project_id` int(10) NULL DEFAULT NULL COMMENT '项目id',
  `user_id` int(10) NULL DEFAULT NULL COMMENT '编写者',
  `date` date NULL DEFAULT NULL COMMENT '日志日期',
  `content` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '内容',
  `pics` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图片',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `last_upd_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '最近修改时间',
  `view_times` int(4) NULL DEFAULT 0 COMMENT '查阅次数',
  PRIMARY KEY (`log_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 55 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of project_log
-- ----------------------------
INSERT INTO `project_log` VALUES (7, 1, 1, '2019-06-16', '11111111', '/projectLog/1a999c61ab9f4b1792a0dcb613879f44.png', '2019-09-02 15:11:40', '2019-09-02 15:13:01', 0);
INSERT INTO `project_log` VALUES (8, 1, 1, '2019-06-16', '11111111', '/projectLog/fe6bc476d7e94dbf8280871395333f03.png', '2019-09-02 15:11:41', '2019-09-02 15:13:01', 0);
INSERT INTO `project_log` VALUES (29, 6, 1, '2019-08-19', '123', 'projectLog/191919a07bc3476ab1ea4796ca05976b.png,projectLog/5a0df8eec048439e8f7f7d39c865b667.png,projectLog/e168c502a4c74a36ab9ddfe2a12b27e4.png', '2019-09-02 15:12:02', '2019-09-06 10:11:00', 0);
INSERT INTO `project_log` VALUES (30, 10, 1, '2019-08-19', '12312412312222222222222222222222222222222222222222222222222222222222222222222222222222222222222', 'projectLog/d7a958f16e804a06a76afbf00f5cb641.png', '2019-09-02 15:12:03', '2019-09-02 15:13:01', 0);
INSERT INTO `project_log` VALUES (31, 4, 1, '2019-08-20', '23154978451231', '', '2019-09-02 15:12:05', '2019-09-06 13:41:52', 0);
INSERT INTO `project_log` VALUES (32, 10, 1, '2019-08-20', '123', 'projectLog/2eb2bb4742134a49a056f4b76664ae0a.png', '2019-09-02 15:12:05', '2019-09-02 15:13:01', 0);
INSERT INTO `project_log` VALUES (33, 13, 3, '2019-08-21', '12312', '', '2019-09-02 15:12:06', '2019-09-02 15:13:01', 0);
INSERT INTO `project_log` VALUES (34, 13, 3, '2019-08-21', '123', '', '2019-09-02 15:12:07', '2019-09-02 15:13:01', 0);
INSERT INTO `project_log` VALUES (35, 13, 3, '2019-08-21', 'das', '', '2019-09-02 15:12:08', '2019-09-02 15:13:01', 0);
INSERT INTO `project_log` VALUES (36, 12, 3, '2019-08-21', 'das', '', '2019-09-02 15:12:09', '2019-09-02 15:13:01', 0);
INSERT INTO `project_log` VALUES (37, 12, 3, '2019-08-21', 'das', '', '2019-09-02 15:12:10', '2019-09-02 15:13:01', 0);
INSERT INTO `project_log` VALUES (38, 10, 3, '2019-08-21', '123', '', '2019-09-02 15:12:10', '2019-09-02 15:13:01', 0);
INSERT INTO `project_log` VALUES (39, 10, 3, '2019-08-21', '13', '', '2019-09-02 15:12:11', '2019-09-02 15:13:01', 0);
INSERT INTO `project_log` VALUES (40, 10, 3, '2019-08-21', 'qwe', '', '2019-09-02 15:12:12', '2019-09-02 15:13:01', 0);
INSERT INTO `project_log` VALUES (41, 13, 3, '2019-08-21', '123', '', '2019-09-02 15:12:13', '2019-09-02 15:13:01', 0);
INSERT INTO `project_log` VALUES (42, 10, 3, '2019-08-22', '213', 'projectLog/a14ce8b1d1fc4f47953750b2ffb51f0c.png,projectLog/b77e1a51fc1043a8915b0c82718687d5.png,projectLog/bbac43db00a24b4bbbe97e4a3e84850a.png,projectLog/a14ce8b1d1fc4f47953750b2ffb51f0c.png', '2019-09-02 15:12:14', '2019-09-06 16:38:42', 0);
INSERT INTO `project_log` VALUES (43, 15, 3, '2019-08-22', 'asdf', 'projectLog/ea84e70f943a447caacf4405af32a732.png projectLog/0fd1207f0f4e46e8b4e591d0fb1600f1.png,projectLog/aaaeac5ca17e4a6a8c924deb65822208.png', '2019-09-02 15:12:15', '2019-09-10 14:15:53', 1);
INSERT INTO `project_log` VALUES (44, 15, 3, '2019-08-22', '123', '', '2019-09-02 15:12:16', '2019-09-10 14:16:20', 1);
INSERT INTO `project_log` VALUES (45, 14, 3, '2019-08-22', '123', '', '2019-09-02 15:12:17', '2019-09-02 15:13:01', 0);
INSERT INTO `project_log` VALUES (46, 15, 3, '2019-08-22', '1231', '', '2019-09-02 15:12:18', '2019-09-10 14:12:44', 1);
INSERT INTO `project_log` VALUES (48, 15, 3, '2019-08-22', '666', '', '2019-09-02 15:12:20', '2019-09-02 15:13:01', 0);
INSERT INTO `project_log` VALUES (49, 15, 3, '2019-08-22', '222', '', '2019-09-02 15:12:22', '2019-09-02 15:13:01', 0);
INSERT INTO `project_log` VALUES (50, 15, 3, '2019-08-22', '999', '', '2019-09-02 15:12:23', '2019-09-10 14:15:37', 1);
INSERT INTO `project_log` VALUES (51, 11, 3, '2019-08-23', '请问555', 'projectLog/8bf15f64012949dba135914a92275f14.png,projectLog/9a3c131eb2e6432d81b7fc85acb53cc9.png,projectLog/7f753aeee72348e697c487b8f8ab1918.png', '2019-09-02 15:12:24', '2019-09-06 10:10:25', 0);
INSERT INTO `project_log` VALUES (52, 17, 3, '2019-08-23', '555623', 'projectLog/932f4281a4ec4a24bc721201f8f6e621.png', '2019-09-02 15:12:25', '2019-09-02 15:13:01', 0);
INSERT INTO `project_log` VALUES (53, 19, 3, '2019-09-12', '21123123213123', '', '2019-09-02 15:12:26', '2019-09-10 13:46:33', 1);
INSERT INTO `project_log` VALUES (54, 19, 3, '2019-09-03', '程序员(英文Programmer)是从事程序开发、维护的专业人员。一般将程序员分为程序设计人员和程序编码人员，但两者的界限并不非常清楚，特别是在中国。软件从业人员分为初级程序员、高级程序员、系统分析员和项目经理四大类。', '', '2019-09-03 13:57:16', '2019-09-10 13:30:14', 1);

-- ----------------------------
-- Table structure for view_statis
-- ----------------------------
DROP TABLE IF EXISTS `view_statis`;
CREATE TABLE `view_statis`  (
  `log_id` int(10) NOT NULL COMMENT '日志id',
  `user_id` int(10) NOT NULL COMMENT '用户id',
  `view_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '查看日志时间'
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of view_statis
-- ----------------------------
INSERT INTO `view_statis` VALUES (51, 1, '2019-08-29 16:54:27');
INSERT INTO `view_statis` VALUES (51, 2, '2019-09-03 13:12:37');
INSERT INTO `view_statis` VALUES (53, 3, '2019-09-10 13:57:52');
INSERT INTO `view_statis` VALUES (53, 3, '2019-09-10 13:57:52');
INSERT INTO `view_statis` VALUES (54, 3, '2019-09-10 14:03:22');
INSERT INTO `view_statis` VALUES (46, 3, NULL);
INSERT INTO `view_statis` VALUES (50, 3, '2019-09-10 14:15:38');
INSERT INTO `view_statis` VALUES (43, 3, '2019-09-10 14:15:54');
INSERT INTO `view_statis` VALUES (44, 3, '2019-09-10 14:16:21');

-- ----------------------------
-- Table structure for wx_user
-- ----------------------------
DROP TABLE IF EXISTS `wx_user`;
CREATE TABLE `wx_user`  (
  `user_id` int(10) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `open_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '微信id',
  `name` varchar(5) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '姓名',
  `nickname` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '昵称',
  `head_image` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '头像',
  `authority` int(1) NULL DEFAULT 1 COMMENT '权限',
  `organization_id` int(10) NULL DEFAULT NULL COMMENT '部门id',
  `status` int(1) NULL DEFAULT 0 COMMENT '账号状态不可用为0，可用为1',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wx_user
-- ----------------------------
INSERT INTO `wx_user` VALUES (1, '123123123', '一二三四', '1231', '123123', 1, 2, 1);
INSERT INTO `wx_user` VALUES (2, '456456456', '456', '456456', '456456', 1, 2, 1);
INSERT INTO `wx_user` VALUES (3, 'o5N0c5P1V0c5-mPwc2QxAe_-oBMo', '哈哈哈', NULL, NULL, 2, 3, 1);

SET FOREIGN_KEY_CHECKS = 1;
