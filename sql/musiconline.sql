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

 Date: 15/03/2025 21:12:59
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for album
-- ----------------------------
DROP TABLE IF EXISTS `album`;
CREATE TABLE `album`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `album_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '专辑名称',
  `album_cover` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '专辑封面',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `create_by` bigint NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint NULL DEFAULT NULL COMMENT '修改者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of album
-- ----------------------------
INSERT INTO `album` VALUES (1, 'sdssss', 'http://localhost:8080/upload/20250315_12572480.jpg', '0', 1, '2025-03-15 01:25:07', 1, '2025-03-15 12:58:37');
INSERT INTO `album` VALUES (2, 'sdsssssdds', 'http://localhost:8080/upload/20250315_12575184.jpg', '2', 1, '2025-03-15 12:57:54', 1, '2025-03-15 12:59:02');
INSERT INTO `album` VALUES (3, 'rdsgfd', 'http://localhost:8080/upload/20250315_12584717.jpg', '2', 1, '2025-03-15 12:58:51', 1, '2025-03-15 12:58:51');
INSERT INTO `album` VALUES (4, '4444', 'http://localhost:8080/upload/20250315_12590858.jpg', '2', 1, '2025-03-15 12:59:14', 1, '2025-03-15 12:59:14');
INSERT INTO `album` VALUES (5, 'sd', '/admin/dist/img/rand/25.jpg', '0', 1, '2025-03-15 13:14:10', 1, '2025-03-15 13:14:10');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `user_id` bigint NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `user_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '用户账号',
  `user_email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '用户邮箱',
  `user_phone` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '手机号码',
  `user_password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '密码',
  `user_role` int NULL DEFAULT 0 COMMENT '用户角色 （0代表普通用户 1代表管理员）',
  `user_status` int NULL DEFAULT 0 COMMENT '活跃状态（0代表禁用 1代表活跃）',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `create_by` bigint NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint NULL DEFAULT NULL COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '用户信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 'admin', '123@163.com', '15888888885', 'admin_002', 1, 1, '0', 1, '2024-06-24 15:31:32', 1, '2025-03-15 20:49:30');
INSERT INTO `sys_user` VALUES (2, 'music1', '123@qq.com', '15666666666', 'music', 0, 1, '0', 1, '2024-06-24 15:31:32', 1, '2025-03-15 11:07:45');
INSERT INTO `sys_user` VALUES (3, 'music2', '123@qq.com', '15666666666', 'music2', 0, 1, '0', 1, '2025-03-14 22:35:13', 1, '2025-03-14 22:35:41');
INSERT INTO `sys_user` VALUES (4, 'sentinel', '123@qq.com', '15666666666', 'sentinel_musicOnline', 0, 1, '0', 1, '2025-03-14 22:35:13', 1, '2025-03-15 01:35:18');
INSERT INTO `sys_user` VALUES (5, 'admin2', '123@qq.com', '15666666666', 'admin2_musicOnline', 1, 1, '0', 1, '2025-03-14 22:51:11', 1, '2025-03-14 22:51:11');

-- ----------------------------
-- Table structure for vinyl
-- ----------------------------
DROP TABLE IF EXISTS `vinyl`;
CREATE TABLE `vinyl`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '黑胶唱片id',
  `single_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '单曲名称',
  `vinyl_title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '黑胶唱片标题',
  `vinyl_cover` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '唱片封面',
  `artist` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '艺术家',
  `album_id` bigint NULL DEFAULT NULL COMMENT '专辑id',
  `issue_date` date NULL DEFAULT NULL COMMENT '发行日期',
  `price` decimal(10, 2) NULL DEFAULT NULL COMMENT '价格',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `create_by` bigint NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint NULL DEFAULT NULL COMMENT '修改者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of vinyl
-- ----------------------------
INSERT INTO `vinyl` VALUES (1, '123', '2', 'http://localhost:8080/upload/20250315_12572480.jpg', '1', 1, '2025-03-15', 23.00, '2', NULL, NULL, 1, '2025-03-15 19:07:02');
INSERT INTO `vinyl` VALUES (2, '14', '14', 'http://localhost:8080/admin/dist/img/rand/27.jpg', '14', 5, NULL, 123.00, '2', 1, '2025-03-15 18:37:50', 1, '2025-03-15 18:37:50');
INSERT INTO `vinyl` VALUES (3, 'jkk', '14', 'http://localhost:8080/admin/dist/img/rand/22.jpg', '14', 5, '2025-03-15', 12.00, '2', 1, '2025-03-15 19:10:02', 1, '2025-03-15 19:16:31');
INSERT INTO `vinyl` VALUES (4, '12', '2', 'http://localhost:8080/admin/dist/img/rand/19.jpg', '14', 5, '2025-03-15', 12.00, '0', 1, '2025-03-15 19:17:30', 1, '2025-03-15 19:18:05');

SET FOREIGN_KEY_CHECKS = 1;
