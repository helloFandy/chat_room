
DROP TABLE IF EXISTS `chat_messages`;
CREATE TABLE `chat_messages` (
                                 `id` int NOT NULL AUTO_INCREMENT,
                                 `content` varchar(450) DEFAULT NULL COMMENT '消息内容',
                                 `status` tinyint(1) DEFAULT NULL COMMENT '接收状态：1已接收，2未接收',
                                 `from_username` varchar(100) DEFAULT NULL COMMENT '发送方账号',
                                 `to_username` varchar(100) DEFAULT NULL COMMENT '接收方账号',
                                 `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '发送时间',
                                 PRIMARY KEY (`id`),
                                 UNIQUE KEY `id_UNIQUE` (`id`),
                                 KEY `idx_create_time` (`create_time`) USING BTREE,
                                 KEY `idx_f_username` (`from_username`) USING BTREE,
                                 KEY `idx_t_username` (`to_username`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8mb3;

LOCK TABLES `chat_messages` WRITE;
INSERT INTO `chat_messages` VALUES (28,'1',1,'admin','meng','2023-02-10 01:39:04'),(29,'2',1,'admin','meng','2023-02-10 01:39:43'),(30,'1',1,'admin','meng','2023-02-10 01:50:04'),(31,'1',1,'meng','admin','2023-02-10 01:53:55');
UNLOCK TABLES;



DROP TABLE IF EXISTS `email`;
CREATE TABLE `email` (
                         `id` int NOT NULL AUTO_INCREMENT,
                         `title` varchar(200) DEFAULT NULL COMMENT '标题',
                         `content` text,
                         `to_username` varchar(45) DEFAULT NULL COMMENT '接收者',
                         `from_username` varchar(45) DEFAULT NULL COMMENT '发送方',
                         `status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '状态：1未读，2已读',
                         `create_date` datetime DEFAULT CURRENT_TIMESTAMP,
                         `admin_status` tinyint(1) DEFAULT '1' COMMENT '管理员状态：1未读，2已读',
                         PRIMARY KEY (`id`),
                         KEY `idx_from` (`from_username`) USING BTREE,
                         KEY `idx_to` (`to_username`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb3;


LOCK TABLES `email` WRITE;
INSERT INTO `email` VALUES (1,'111','111111','admin','meng',2,'2023-02-15 21:30:09',1),(2,'222','222','admin','zhang',1,'2023-02-15 23:04:54',1),(3,'22222','<p>44444</p>',NULL,'meng',2,'2023-02-15 23:25:53',1),(4,'5555','<p>55555</p>','admin','meng',2,'2023-02-15 23:27:50',2);
UNLOCK TABLES;



DROP TABLE IF EXISTS `friend`;
CREATE TABLE `friend` (
                          `id` int NOT NULL AUTO_INCREMENT,
                          `username` varchar(100) DEFAULT NULL COMMENT '用户账号',
                          `friend_username` varchar(100) DEFAULT NULL COMMENT '朋友账号',
                          `remark` varchar(45) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '备注',
                          `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
                          `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日期',
                          `is_deleted` tinyint(1) DEFAULT '2' COMMENT '逻辑删除',
                          PRIMARY KEY (`id`),
                          UNIQUE KEY `unq_username_f` (`username`,`friend_username`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb3;

LOCK TABLES `friend` WRITE;
INSERT INTO `friend` VALUES (1,'admin','meng',NULL,'2023-02-06 23:06:32','2023-02-07 22:17:33',2),(2,'admin','zhang',NULL,'2023-02-06 23:42:01','2023-02-07 22:40:27',2),(19,'admin','li',NULL,'2023-02-08 00:11:28','2023-02-08 00:11:28',2),(20,'li','admin',NULL,'2023-02-08 00:11:28','2023-02-08 00:11:28',2),(21,'zhang','admin',NULL,'2023-02-08 00:11:28','2023-02-08 00:11:28',2),(22,'meng','admin',NULL,'2023-02-08 00:11:28','2023-02-08 00:11:28',2);
UNLOCK TABLES;





DROP TABLE IF EXISTS `message`;
CREATE TABLE `message` (
                           `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
                           `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci COMMENT '内容',
                           `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '评论人',
                           `time` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '评论时间',
                           `parent_id` bigint DEFAULT NULL COMMENT '父ID',
                           `foreign_id` bigint DEFAULT '0' COMMENT '关联id',
                           PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='留言表';

LOCK TABLES `message` WRITE;
INSERT INTO `message` VALUES (20,'哈哈哈','admin','2021-05-22 10:48:55',NULL,0),(21,'哦豁','admin','2021-05-22 10:49:48',NULL,0),(22,'老弟','admin','2021-05-22 10:51:07',21,0),(23,'哈哈哈','zhang','2021-05-24 17:13:45',22,0),(24,'我们都爱吃大西瓜','zhang','2021-05-24 17:13:58',NULL,0),(25,'1232','admin','2021-10-31 16:46:39',NULL,0),(26,'2345','admin','2021-10-31 16:47:00',25,0),(28,'哈哈哈','admin','2021-05-22 10:48:55',NULL,4),(29,'ooo','admin','2023-02-13 22:08:53',NULL,4),(30,'oooo','admin','2023-02-13 22:19:34',26,0),(31,'1111','admin','2023-02-13 22:19:55',30,0),(32,'mmmm','admin','2023-02-13 22:24:32',29,4),(33,'xixixixi','admin','2023-02-13 22:26:57',32,4),(34,'uuuu','admin','2023-02-13 22:43:36',32,4);
UNLOCK TABLES;


DROP TABLE IF EXISTS `message`;
CREATE TABLE `message` (
                           `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
                           `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci COMMENT '内容',
                           `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '评论人',
                           `time` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '评论时间',
                           `parent_id` bigint DEFAULT NULL COMMENT '父ID',
                           `foreign_id` bigint DEFAULT '0' COMMENT '关联id',
                           PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='留言表';

LOCK TABLES `message` WRITE;
INSERT INTO `message` VALUES (20,'哈哈哈','admin','2021-05-22 10:48:55',NULL,0),(21,'哦豁','admin','2021-05-22 10:49:48',NULL,0),(22,'老弟','admin','2021-05-22 10:51:07',21,0),(23,'哈哈哈','zhang','2021-05-24 17:13:45',22,0),(24,'我们都爱吃大西瓜','zhang','2021-05-24 17:13:58',NULL,0),(25,'1232','admin','2021-10-31 16:46:39',NULL,0),(26,'2345','admin','2021-10-31 16:47:00',25,0),(28,'哈哈哈','admin','2021-05-22 10:48:55',NULL,4),(29,'ooo','admin','2023-02-13 22:08:53',NULL,4),(30,'oooo','admin','2023-02-13 22:19:34',26,0),(31,'1111','admin','2023-02-13 22:19:55',30,0),(32,'mmmm','admin','2023-02-13 22:24:32',29,4),(33,'xixixixi','admin','2023-02-13 22:26:57',32,4),(34,'uuuu','admin','2023-02-13 22:43:36',32,4);
UNLOCK TABLES;




DROP TABLE IF EXISTS `news`;
CREATE TABLE `news` (
                        `id` int NOT NULL AUTO_INCREMENT COMMENT 'ID',
                        `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '标题',
                        `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '内容',
                        `author` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '作者',
                        `time` datetime DEFAULT NULL COMMENT '发布时间',
                        PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC;


LOCK TABLES `news` WRITE;
INSERT INTO `news` VALUES (4,'4444','<p>444444</p><p><br/></p><p>44444</p><p><br/></p><p><br/></p><p>555555<img src=\"https://img1.baidu.com/it/u=4110196045,3829597861&amp;fm=26&amp;fmt=auto&amp;gp=0.jpg\" contenteditable=\"false\" style=\"font-size: 14px; max-width: 100%;\"/></p>','管理员','2021-07-17 18:55:27'),(5,'77777','<p>7777</p><p><br/></p><p><img src=\"http://localhost:9090/files/c536f6f2f0e94983951240d73d740601\" style=\"max-width:100%;\" contenteditable=\"false\" width=\"388\" height=\"388\"/><br/></p><p>8888</p><p><br/></p><p><img src=\"http://localhost:9090/files/b344314319f047cf9192ce64ca454674\" style=\"max-width:100%;\" contenteditable=\"false\"/></p>','管理员','2021-07-17 19:14:14'),(6,'带你从0搭建一个Springboot+vue前后端分离项目，真的很简单！','<p><iframe src=\"//player.bilibili.com/player.html?aid=803885504&amp;bvid=BV14y4y1M7Nc&amp;cid=361690131&amp;page=1\" scrolling=\"no\" border=\"0\" frameborder=\"no\" framespacing=\"0\" allowfullscreen=\"true\" width=\"600\" height=\"400\"> </iframe></p>','管理员','2021-09-05 22:28:25'),(7,'我擦！妹子给我整害羞了⁄(⁄ ⁄ ⁄ω⁄ ⁄ ⁄)⁄……','<p><iframe src=\"//player.bilibili.com/player.html?aid=717601909&amp;bvid=BV1sQ4y1174g&amp;cid=396410411&amp;page=1\" scrolling=\"no\" border=\"0\" frameborder=\"no\" framespacing=\"0\" allowfullscreen=\"true\" width=\"600\" height=\"400\"> </iframe></p>','管理员','2021-09-05 22:31:40'),(8,'11111','<p>哈哈哈</p><p><br/></p><p><img src=\"http://localhost:9090/files/abbac1162e3948dcbb5df85f752f27f3\" style=\"max-width:100%;\" contenteditable=\"false\"/></p><p></p><p></p>','管理员','2021-09-29 23:17:27');
UNLOCK TABLES;




DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission` (
                              `id` int NOT NULL AUTO_INCREMENT COMMENT 'id',
                              `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '名称',
                              `path` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '资源路径',
                              `comment` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注',
                              `icon` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '图标',
                              PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC;


LOCK TABLES `permission` WRITE;
INSERT INTO `permission` VALUES (1,'Home','/home','主页','el-icon-house'),(2,'Book','/book','书籍管理','el-icon-files'),(3,'Category','/category','分类管理','el-icon-menu'),(4,'Order','/order','我的订单','el-icon-s-order'),(5,'News','/news','新闻管理','el-icon-news'),(6,'Map','/map','百度地图','el-icon-map-location'),(7,'Im','/im','聊天室','el-icon-chat-round'),(8,'Message','/message','在线留言','el-icon-message'),(9,'User','/user','用户管理','el-icon-user'),(10,'Permission','/permisssion','权限菜单','el-icon-menu'),(11,'Role','/role','角色管理','el-icon-s-custom'),(12,'Donate','/donate','请作者喝杯咖啡','el-icon-coffee'),(13,'Person','/person','个人信息',''),(14,'Password','/password','修改密码',NULL),(15,'Email','/email','邮件','el-icon-message');
UNLOCK TABLES;




DROP TABLE IF EXISTS `role_permission`;
CREATE TABLE `role_permission` (
                                   `role_id` int NOT NULL COMMENT '角色id',
                                   `permission_id` int NOT NULL COMMENT '资源id',
                                   PRIMARY KEY (`role_id`,`permission_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC;

LOCK TABLES `role_permission` WRITE;
INSERT INTO `role_permission` VALUES (1,1),(1,2),(1,3),(1,4),(1,5),(1,6),(1,7),(1,8),(1,9),(1,10),(1,11),(1,12),(1,13),(1,14),(1,15),(2,1),(2,2),(2,3),(2,4),(2,5),(2,6),(2,7),(2,8),(2,12),(2,13),(2,14),(2,15);
UNLOCK TABLES;




DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
                        `id` int NOT NULL AUTO_INCREMENT COMMENT 'ID',
                        `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '名称',
                        `comment` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注',
                        PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC;


LOCK TABLES `role` WRITE;
INSERT INTO `role` VALUES (1,'admin','管理员'),(2,'user','普通用户');
UNLOCK TABLES;



DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
                             `user_id` int NOT NULL COMMENT '用户id',
                             `role_id` int NOT NULL COMMENT '角色id',
                             PRIMARY KEY (`user_id`,`role_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC;

LOCK TABLES `user_role` WRITE;
INSERT INTO `user_role` VALUES (1,1),(13,2),(14,2),(15,2),(16,2),(17,2);
UNLOCK TABLES;




DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
                        `id` int NOT NULL AUTO_INCREMENT COMMENT 'ID',
                        `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '用户名',
                        `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '密码',
                        `nick_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '昵称',
                        `age` int DEFAULT NULL COMMENT '年龄',
                        `sex` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '性别',
                        `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '地址',
                        `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '头像',
                        `account` decimal(10,2) DEFAULT '0.00' COMMENT '账户余额',
                        PRIMARY KEY (`id`) USING BTREE,
                        UNIQUE KEY `unq_username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='用户信息表';


LOCK TABLES `user` WRITE;
INSERT INTO `user` VALUES (1,'admin','$2a$10$.cxJ637DYA1PRSzfPmBO0efS1W2eTQefS51b83Nr5xgRNAW0ffgjO','管理员',32,'男','火星','http://localhost:9090/files/888f2d39d0724816a738a716d56ad58a',100000.00),(13,'zhang','$2a$10$h6gfZRMvGFjjQH6HrQf.Q.fvu3jjMc0cv/sGpSanobht5iwCFbucS','张三',20,'男','木星','http://localhost:9090/files/d227473b758e4915a3e7c866d2d494cf',200.00),(14,'wang','$2a$10$h6gfZRMvGFjjQH6HrQf.Q.fvu3jjMc0cv/sGpSanobht5iwCFbucS','王梦晨',24,'女','地球',NULL,300.00),(15,'li','$2a$10$h6gfZRMvGFjjQH6HrQf.Q.fvu3jjMc0cv/sGpSanobht5iwCFbucS','李雪',22,'女','银河系',NULL,500.00),(16,'qian','$2a$10$h6gfZRMvGFjjQH6HrQf.Q.fvu3jjMc0cv/sGpSanobht5iwCFbucS','钱江',22,'男','地球',NULL,700.00),(17,'meng','$2a$10$Z4HIpxkgdLrT1KsDM/wAL.Dq4y.Zd9BCHtfy/CtcKuKwpsN.kdFE6','蒙蒙',11,NULL,NULL,NULL,0.00);
UNLOCK TABLES;
