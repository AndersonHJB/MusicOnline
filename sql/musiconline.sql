/*
 Navicat Premium Data Transfer

 Source Server         : MYSQL8
 Source Server Type    : MySQL
 Source Server Version : 80030 (8.0.30)
 Source Host           : localhost:3308
 Source Schema         : musiconline

 Target Server Type    : MySQL
 Target Server Version : 80030 (8.0.30)
 File Encoding         : 65001

 Date: 14/03/2025 01:00:33
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `user_id` bigint NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `user_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '用户账号',
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '用户邮箱',
  `phone_number` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '手机号码',
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '密码',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `create_by` bigint NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint NULL DEFAULT NULL COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `admin` int NULL DEFAULT 0 COMMENT '是否管理员 （0代表普通用户 1代表管理员）',
  `enable` int NULL DEFAULT 0 COMMENT '活跃状态（0代表禁用 1代表活跃）',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '用户信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 'admin', '123@163.com', '15888888888', 'admin', '0', 1, '2024-06-24 15:31:32', 1, '2024-07-15 14:48:09', 1, 1);
INSERT INTO `sys_user` VALUES (2, 'music', '123@qq.com', '15666666666', 'music', '0', 1, '2024-06-24 15:31:32', NULL, NULL, 0, 1);
INSERT INTO `sys_user` VALUES (3, 'music2', '', '', 'music2', '0', NULL, NULL, NULL, NULL, 0, 1);

-- ----------------------------
-- Table structure for vinyl
-- ----------------------------
DROP TABLE IF EXISTS `vinyl`;
CREATE TABLE `vinyl`  (
  `id` bigint(20) UNSIGNED ZEROFILL NOT NULL AUTO_INCREMENT COMMENT '黑胶唱片id',
  `single_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '单曲名称',
  `vinyl_title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '黑胶唱片标题',
  `artist` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '艺术家',
  `album_id` bigint NULL DEFAULT NULL COMMENT '专辑id',
  `issue_date` date NULL DEFAULT NULL COMMENT '发行日期',
  `price` decimal(10, 2) NULL DEFAULT NULL COMMENT '价格',
  `update_by` bigint NULL DEFAULT NULL COMMENT '修改者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `create_by` bigint NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of vinyl
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
