/*
 Navicat Premium Data Transfer

 Source Server         : mysql
 Source Server Type    : MySQL
 Source Server Version : 80030
 Source Host           : localhost:3306
 Source Schema         : didilogistics

 Target Server Type    : MySQL
 Target Server Version : 80030
 File Encoding         : 65001

 Date: 22/12/2022 17:49:08
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for driver
-- ----------------------------
DROP TABLE IF EXISTS `driver`;
CREATE TABLE `driver`  (
  `user_id` int NOT NULL COMMENT '用户id',
  `cur_city` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '司机当前位置',
  `des_city` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '司机即将前往',
  `state` bit(1) NULL DEFAULT NULL COMMENT '司机状态，0表示暂停接单，1表示可接单',
  `car_length` double NULL DEFAULT NULL COMMENT '车辆长度',
  `car_weight` double NULL DEFAULT NULL COMMENT '车辆可承载重量',
  `income` int NOT NULL DEFAULT 0 COMMENT '司机累计收入',
  PRIMARY KEY (`user_id`) USING BTREE,
  CONSTRAINT `user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of driver
-- ----------------------------
INSERT INTO `driver` VALUES (4, '呼和浩特市', '长春市', b'1', 6, 900, 11000);
INSERT INTO `driver` VALUES (9, '北京市', '太原市', b'1', 12, 900, 5000);
INSERT INTO `driver` VALUES (10, '北京市', '石家庄市', b'1', 5, 600, 0);

-- ----------------------------
-- Table structure for message
-- ----------------------------
DROP TABLE IF EXISTS `message`;
CREATE TABLE `message`  (
  `message_id` int NOT NULL AUTO_INCREMENT COMMENT '信息ID',
  `order_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '信息关联订单',
  `user_id` int NULL DEFAULT NULL COMMENT '信息关联用户',
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '信息内容',
  `time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '信息发送时间',
  PRIMARY KEY (`message_id`) USING BTREE,
  INDEX `user`(`user_id` ASC) USING BTREE,
  INDEX `order`(`order_id` ASC) USING BTREE,
  CONSTRAINT `order` FOREIGN KEY (`order_id`) REFERENCES `order` (`order_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `user` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 71 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of message
-- ----------------------------
INSERT INTO `message` VALUES (2, '1671094115363', 3, '您 操作了取消订单', '2022-12-15 16:38:24');
INSERT INTO `message` VALUES (5, '1671099755771', 3, '司机 lisizc 完成了接单，快去看看吧', '2022-12-15 18:20:10');
INSERT INTO `message` VALUES (7, '1671099755771', 3, '订单已完成发货，货物即将启程，请耐心等待', '2022-12-15 18:20:33');
INSERT INTO `message` VALUES (8, '1671099755771', 4, '您已完成发货，货物描述：橘子，即将送往：安徽省省合肥市市瑶海区区', '2022-12-15 18:20:33');
INSERT INTO `message` VALUES (9, '1671099755771', 3, '订单已完成，请尽快完成支付', '2022-12-15 18:20:59');
INSERT INTO `message` VALUES (10, '1671099755771', 4, '订单已完成，系统已通知货主进行支付', '2022-12-15 18:20:59');
INSERT INTO `message` VALUES (11, '1671099755771', 3, '订单已完成支付，感谢您的使用', '2022-12-15 20:05:10');
INSERT INTO `message` VALUES (12, '1671099755771', 4, '订单已完成支付，3000元已入账，感谢您的使用', '2022-12-15 20:05:10');
INSERT INTO `message` VALUES (13, '1671108168609', 4, '货主 张三 向你推送了一个订单，货物内容：phone，由 河北省石家庄市长安区 发往 新疆维吾尔自治区乌鲁木齐市天山区，请尽快查看', '2022-12-15 20:43:39');
INSERT INTO `message` VALUES (17, '1671107920450', 4, '货主 张三 向你推送了一个订单，货物内容：computer，由 辽宁省沈阳市和平区 发往 青海省西宁市城东区，请尽快查看', '2022-12-15 20:55:38');
INSERT INTO `message` VALUES (18, '1671107920450', 3, '您已向司机 lisi432 推送了该笔订单，等待司机接单', '2022-12-15 20:55:38');
INSERT INTO `message` VALUES (19, '1671107920450', 4, '货主 张三 向你推送了一个订单，货物内容：computer，由 辽宁省沈阳市和平区 发往 青海省西宁市城东区，请尽快查看', '2022-12-16 20:43:02');
INSERT INTO `message` VALUES (20, '1671107920450', 3, '您已向司机 lisi432 推送了该笔订单，等待司机接单', '2022-12-16 20:43:02');
INSERT INTO `message` VALUES (21, '1671108168609', 3, '司机 张三 完成了接单，快去看看吧', '2022-12-16 21:11:08');
INSERT INTO `message` VALUES (23, '1671108168609', 3, '订单已完成发货，货物即将启程，请耐心等待', '2022-12-16 21:17:54');
INSERT INTO `message` VALUES (24, '1671108168609', 9, '您已完成发货，货物描述：phone，即将送往：新疆维吾尔自治区乌鲁木齐市天山区', '2022-12-16 21:17:54');
INSERT INTO `message` VALUES (25, '1671108168609', 3, '订单已完成，请尽快完成支付', '2022-12-16 21:21:54');
INSERT INTO `message` VALUES (26, '1671108168609', 9, '订单已完成，系统已通知货主进行支付', '2022-12-16 21:21:54');
INSERT INTO `message` VALUES (27, '1671107920450', 3, '司机 张三 完成了接单，快去看看吧', '2022-12-16 21:22:27');
INSERT INTO `message` VALUES (33, '1671108168609', 3, '订单已完成支付，感谢您的使用', '2022-12-18 17:54:50');
INSERT INTO `message` VALUES (34, '1671108168609', 9, '订单已完成支付，5000元已入账，感谢您的使用', '2022-12-18 17:54:50');
INSERT INTO `message` VALUES (35, '1671371110800', 9, '货主 老六 向你推送了一个订单，货物内容：沙发，由 河北省石家庄市长安区 发往 广东省广州市天河区，请尽快查看', '2022-12-18 21:33:29');
INSERT INTO `message` VALUES (36, '1671371110800', 3, '您已向司机 王五 推送了该笔订单，等待司机接单', '2022-12-18 21:33:29');
INSERT INTO `message` VALUES (37, '1671349768883', 3, '司机 老六 完成了接单，快去看看吧', '2022-12-18 21:36:49');
INSERT INTO `message` VALUES (39, '1671349768883', 3, '订单已完成发货，货物即将启程，请耐心等待', '2022-12-18 21:37:36');
INSERT INTO `message` VALUES (40, '1671349768883', 4, '您已完成发货，货物描述：茶几，即将送往：广东省广州市天河区', '2022-12-18 21:37:36');
INSERT INTO `message` VALUES (41, '1671349768883', 3, '订单已完成，请尽快完成支付', '2022-12-18 21:37:42');
INSERT INTO `message` VALUES (42, '1671349768883', 4, '订单已完成，系统已通知货主进行支付', '2022-12-18 21:37:42');
INSERT INTO `message` VALUES (43, '1671349768883', 3, '订单已完成支付，感谢您的使用', '2022-12-18 21:38:58');
INSERT INTO `message` VALUES (44, '1671349768883', 4, '订单已完成支付，2000元已入账，感谢您的使用', '2022-12-18 21:38:58');
INSERT INTO `message` VALUES (45, '1671371110800', 3, '司机 老六 完成了接单，快去看看吧', '2022-12-18 22:30:43');
INSERT INTO `message` VALUES (47, '1671371110800', 3, '您 操作了取消订单', '2022-12-18 22:30:49');
INSERT INTO `message` VALUES (48, '1671371110800', 4, '货主 操作了取消订单', '2022-12-18 22:30:49');
INSERT INTO `message` VALUES (49, '1671418806135', 3, '司机 老六 完成了接单，快去看看吧', '2022-12-19 10:52:43');
INSERT INTO `message` VALUES (51, '1671418806135', 3, '订单已完成发货，货物即将启程，请耐心等待', '2022-12-19 10:52:52');
INSERT INTO `message` VALUES (52, '1671418806135', 4, '您已完成发货，货物描述：苹果，即将送往：广东省广州市天河区', '2022-12-19 10:52:52');
INSERT INTO `message` VALUES (53, '1671418806135', 3, '订单已完成，请尽快完成支付', '2022-12-19 10:53:05');
INSERT INTO `message` VALUES (54, '1671418806135', 4, '订单已完成，系统已通知货主进行支付', '2022-12-19 10:53:05');
INSERT INTO `message` VALUES (55, '1671418806135', 3, '订单已完成支付，感谢您的使用', '2022-12-19 10:53:39');
INSERT INTO `message` VALUES (56, '1671418806135', 4, '订单已完成支付，3000元已入账，感谢您的使用', '2022-12-19 10:53:39');
INSERT INTO `message` VALUES (57, '1671516161639', 3, '司机 老六 完成了接单，快去看看吧', '2022-12-20 13:48:38');

-- ----------------------------
-- Table structure for order
-- ----------------------------
DROP TABLE IF EXISTS `order`;
CREATE TABLE `order`  (
  `order_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '订单ID',
  `order_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '下单时间，默认值为当前时间戳',
  `start_place_province` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '起点',
  `start_place_city` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '起点',
  `start_place_district` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '起点',
  `des_place_province` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '终点',
  `des_place_city` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '终点',
  `des_place_district` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '终点',
  `deliver_time` date NOT NULL COMMENT '发货日期，用户下单时确定',
  `length` double NOT NULL COMMENT '长度',
  `weight` double NOT NULL COMMENT '重量',
  `price` int NOT NULL COMMENT '价格',
  `owner_id` int NOT NULL COMMENT '货主ID，外键',
  `driver_id` int NULL DEFAULT NULL COMMENT '司机ID，外键',
  `state` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '待接单' COMMENT '订单状态，包括：待接单，已接单，进行中，待支付，已完成，已取消',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '货物描述',
  `start_place_detail` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `des_place_detail` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `distance` double NULL DEFAULT NULL COMMENT '起终点距离，单位公里',
  `recommend_price` double NULL DEFAULT NULL COMMENT '系统建议最低价格',
  PRIMARY KEY (`order_id`) USING BTREE,
  INDEX `owner`(`owner_id` ASC) USING BTREE,
  INDEX `driver`(`driver_id` ASC) USING BTREE,
  CONSTRAINT `driver` FOREIGN KEY (`driver_id`) REFERENCES `user` (`user_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `owner` FOREIGN KEY (`owner_id`) REFERENCES `user` (`user_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of order
-- ----------------------------
INSERT INTO `order` VALUES ('1670917216767', '2022-12-13 15:31:06', '北京市', '北京市', '房山区', '湖南省', '长沙市', '雨花区', '2022-10-23', 1.5, 200, 5000, 2, NULL, '已取消', '电脑', '翻斗花园二号楼', '恒大未来城西区', NULL, NULL);
INSERT INTO `order` VALUES ('1670999606959', '2022-12-14 14:20:20', '福建省', '福州市', '鼓楼区', '黑龙江省', '哈尔滨市', '道里区', '2022-12-30', 2, 300, 3888, 3, NULL, '已取消', 'sofa', '翻斗花园二号楼', '恒大未来城西区', NULL, NULL);
INSERT INTO `order` VALUES ('1671089087058', '2022-12-15 15:12:00', '上海市', '上海市', '黄浦区', '辽宁省', '沈阳市', '和平区', '2022-12-17', 6, 1000, 3000, 3, NULL, '已取消', '一箱苹果', '翻斗花园二号楼', '恒大未来城西区', NULL, NULL);
INSERT INTO `order` VALUES ('1671094115363', '2022-12-15 16:34:29', '北京市', '北京市', '海淀区', '广东省', '广州市', '天河区', '2022-12-31', 2, 2, 100, 3, NULL, '已取消', '电脑', '翻斗花园二号楼', '恒大未来城西区', NULL, NULL);
INSERT INTO `order` VALUES ('1671099755771', '2022-12-15 18:19:57', '青海省', '西宁市', '城东区', '安徽省', '合肥市', '瑶海区', '2022-12-17', 6, 500, 3000, 3, 4, '已完成', '橘子', '翻斗花园二号楼', '恒大未来城西区', NULL, NULL);
INSERT INTO `order` VALUES ('1671107920450', '2022-12-15 20:26:14', '辽宁省', '沈阳市', '和平区', '青海省', '西宁市', '城东区', '2022-12-30', 3, 700, 6000, 3, 9, '已接单', 'computer', '翻斗花园二号楼', '恒大未来城西区', NULL, NULL);
INSERT INTO `order` VALUES ('1671108168609', '2022-12-15 20:30:19', '河北省', '石家庄市', '长安区', '新疆维吾尔自治区', '乌鲁木齐市', '天山区', '2022-12-23', 6, 300, 5000, 3, 9, '已完成', 'phone', '翻斗花园二号楼', '恒大未来城西区', NULL, NULL);
INSERT INTO `order` VALUES ('1671349768883', '2022-12-18 15:38:11', '北京市', '北京市', '海淀区', '广东省', '广州市', '天河区', '2022-12-21', 2, 280, 2000, 3, 4, '已完成', '茶几', '翻斗花园2号楼', '恒大未来城西区', NULL, NULL);
INSERT INTO `order` VALUES ('1671371110800', '2022-12-18 21:33:00', '河北省', '石家庄市', '长安区', '广东省', '广州市', '天河区', '2022-12-28', 2, 300, 3000, 3, 4, '已取消', '沙发', '翻斗花园二号楼', '恒大绿洲1栋', NULL, NULL);
INSERT INTO `order` VALUES ('1671374594730', '2022-12-18 22:33:37', '山西省', '太原市', '小店区', '黑龙江省', '哈尔滨市', '道里区', '2022-12-28', 2, 200, 3000, 2, NULL, '待接单', '抗疫物资', '翻斗花园', '恒大', NULL, NULL);
INSERT INTO `order` VALUES ('1671374628126', '2022-12-18 22:32:46', '北京市', '北京市', '海淀区', '广东省', '广州市', '天河区', '2022-12-29', 2, 300, 2000, 2, NULL, '待接单', '窗帘', '翻斗花园', '恒大', NULL, NULL);
INSERT INTO `order` VALUES ('1671374642548', '2022-12-18 22:35:50', '北京市', '北京市', '海淀区', '广东省', '广州市', '天河区', '2022-12-22', 5, 600, 9999, 5, NULL, '待接单', 'N95口罩（急送）', '翻斗花园', '恒大', NULL, NULL);
INSERT INTO `order` VALUES ('1671374729693', '2022-12-18 22:34:12', '北京市', '北京市', '海淀区', '辽宁省', '沈阳市', '和平区', '2022-12-28', 5, 600, 6000, 2, NULL, '待接单', '口罩', '翻斗花园', '恒大', NULL, NULL);
INSERT INTO `order` VALUES ('1671375002608', '2022-12-18 22:34:59', '北京市', '北京市', '海淀区', '广东省', '广州市', '天河区', '2022-12-28', 6, 700, 6000, 5, NULL, '待接单', '医用防护服', '翻斗花园', '恒大', NULL, NULL);
INSERT INTO `order` VALUES ('1671418806135', '2022-12-19 10:50:32', '北京市', '北京市', '海淀区', '广东省', '广州市', '天河区', '2022-12-28', 3, 300, 3000, 3, 4, '已完成', '苹果', '翻斗花园', '恒大绿洲', NULL, NULL);
INSERT INTO `order` VALUES ('1671516161639', '2022-12-20 13:48:27', '北京市', '北京市', '海淀区', '广东省', '广州市', '天河区', '2022-12-23', 3, 300, 3000, 3, 4, '已接单', '新年礼包', '恒大', '碧桂园', NULL, NULL);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `user_id` int NOT NULL AUTO_INCREMENT COMMENT '用户ID，主键',
  `nickname` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '昵称',
  `name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '姓名',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '密码',
  `type` bit(1) NOT NULL DEFAULT b'0' COMMENT '用户类型，0表示货主，1表示司机',
  `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户手机号',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (2, '', '张三', '25D55AD283AA400AF464C76D713C07AD', b'0', '15622335566');
INSERT INTO `user` VALUES (3, NULL, '小明', '25D55AD283AA400AF464C76D713C07AD', b'0', '17855667788');
INSERT INTO `user` VALUES (4, NULL, '李四', '25D55AD283AA400AF464C76D713C07AD', b'1', '16755556666');
INSERT INTO `user` VALUES (5, NULL, '康康', '25D55AD283AA400AF464C76D713C07AD', b'0', '13322226666');
INSERT INTO `user` VALUES (9, NULL, '王五', '25D55AD283AA400AF464C76D713C07AD', b'1', '18911112222');
INSERT INTO `user` VALUES (10, NULL, '王麻子', '25D55AD283AA400AF464C76D713C07AD', b'1', '18955552211');

-- ----------------------------
-- Triggers structure for table user
-- ----------------------------
DROP TRIGGER IF EXISTS `driver`;
delimiter ;;
CREATE TRIGGER `driver` AFTER INSERT ON `user` FOR EACH ROW if new.type=1 then
    INSERT INTO driver(user_id) VALUES(new.user_id);
end if
;;
delimiter ;

SET FOREIGN_KEY_CHECKS = 1;
