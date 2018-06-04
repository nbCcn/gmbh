CREATE DATABASE `book` CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_general_ci';
-- 用户表
CREATE TABLE `sys_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(150) DEFAULT NULL COMMENT '账户名',
  `user_pass` varchar(128) DEFAULT NULL COMMENT '密码',
  `first_name` varchar(30) DEFAULT NULL COMMENT '用户名',
  `second_name` varchar(30) DEFAULT NULL,
  `email` varchar(128) DEFAULT NULL,
  `is_staff` tinyint(3) DEFAULT NULL,
  `is_active` tinyint(3) DEFAULT '1' COMMENT '是否启用',
  `is_superuser` tinyint(3) DEFAULT NULL COMMENT '是否管理员用户',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `last_login_time` datetime DEFAULT NULL COMMENT '最后登录时间',
  `joined_time` datetime DEFAULT NULL COMMENT '入职时间',
  `phone` varchar(150) DEFAULT NULL COMMENT '联系电话',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 角色表
CREATE TABLE `sys_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `role_name` varchar(128) DEFAULT NULL COMMENT '角色名',
  `role_level` int(5) DEFAULT '1' COMMENT '角色等级',
  `create_date` datetime DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


-- 钉钉表
CREATE TABLE `sys_user_ding` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `ding_user` varchar(32) DEFAULT NULL,
  `ding_id` varchar(64) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL COMMENT '账户id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 菜单表
CREATE TABLE `sys_menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `menu_name` varchar(50) DEFAULT NULL COMMENT '菜单名称',
  `menu_code` varchar(50) DEFAULT NULL COMMENT '菜单code',
  `menu_url` varchar(255) DEFAULT NULL COMMENT '菜单路径',
  `pid` varchar(50) DEFAULT NULL COMMENT '菜单父级菜单',
  `menu_img` varchar(255) DEFAULT NULL COMMENT '菜单图标',
  `menu_order` int(10) DEFAULT NULL COMMENT '排序',
  `create_date` datetime DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `is_valid` tinyint(3) DEFAULT '1' COMMENT '是否启用',
  `i18n_menu_name` varchar(50) DEFAULT NULL COMMENT '国际化菜单名',
  `operation_type` varchar(50) DEFAULT NULL COMMENT '该菜单所包含的操作类型(以，分割）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO `book`.`sys_menu`(`id`, `menu_name`, `menu_code`, `menu_url`, `pid`, `menu_img`, `menu_order`, `create_date`, `update_date`, `is_valid`, `i18n_menu_name`, `operation_type`) VALUES (1, '报货单管理', '001', '/orders', '', 'book', 1, '2018-04-10 11:18:33', '2018-04-10 11:18:35', 1, 'menu.orders', '1,2,3,4');
INSERT INTO `book`.`sys_menu`(`id`, `menu_name`, `menu_code`, `menu_url`, `pid`, `menu_img`, `menu_order`, `create_date`, `update_date`, `is_valid`, `i18n_menu_name`, `operation_type`) VALUES (2, '送货管理', '002', NULL, NULL, 'calendar', 4, '2018-04-10 11:30:13', '2018-04-10 11:30:16', 1, 'menu.plan', '');
INSERT INTO `book`.`sys_menu`(`id`, `menu_name`, `menu_code`, `menu_url`, `pid`, `menu_img`, `menu_order`, `create_date`, `update_date`, `is_valid`, `i18n_menu_name`, `operation_type`) VALUES (3, '送货安排', '002001', '/logistical/deliveryPlan', '002', NULL, 1, '2018-04-10 11:32:59', '2018-04-10 11:33:03', 1, 'menu.arrange', '1,3,4,6');
INSERT INTO `book`.`sys_menu`(`id`, `menu_name`, `menu_code`, `menu_url`, `pid`, `menu_img`, `menu_order`, `create_date`, `update_date`, `is_valid`, `i18n_menu_name`, `operation_type`) VALUES (4, '路线规划', '002002', '/logistical/deliveryLineConfigTextModel', '002', NULL, 2, '2018-04-10 11:34:30', '2018-04-10 11:34:32', 1, 'menu.routes', '2,3,4,6');
INSERT INTO `book`.`sys_menu`(`id`, `menu_name`, `menu_code`, `menu_url`, `pid`, `menu_img`, `menu_order`, `create_date`, `update_date`, `is_valid`, `i18n_menu_name`, `operation_type`) VALUES (5, '地图规划', '002003', '/logistical/deliveryLineConfig', '002', NULL, 3, '2018-05-02 15:18:30', '2018-05-02 15:18:32', 1, 'menu.map', NULL);
INSERT INTO `book`.`sys_menu`(`id`, `menu_name`, `menu_code`, `menu_url`, `pid`, `menu_img`, `menu_order`, `create_date`, `update_date`, `is_valid`, `i18n_menu_name`, `operation_type`) VALUES (6, '送货路线', '002004', '/logistical/deliveryLine', '002', '', 4, '2018-04-10 13:01:06', '2018-04-10 13:01:08', 1, 'menu.route.delivery', '1,2,3,4');
INSERT INTO `book`.`sys_menu`(`id`, `menu_name`, `menu_code`, `menu_url`, `pid`, `menu_img`, `menu_order`, `create_date`, `update_date`, `is_valid`, `i18n_menu_name`, `operation_type`) VALUES (7, '商品管理', '003', NULL, NULL, 'coffee', 3, '2018-04-10 11:35:18', '2018-04-10 11:35:21', 1, 'menu.products', '');
INSERT INTO `book`.`sys_menu`(`id`, `menu_name`, `menu_code`, `menu_url`, `pid`, `menu_img`, `menu_order`, `create_date`, `update_date`, `is_valid`, `i18n_menu_name`, `operation_type`) VALUES (8, '商品列表', '003001', '/commodity/goods', '003', NULL, 1, '2018-04-10 11:38:25', '2018-04-10 11:38:28', 1, 'menu.product', '1,2,3,4,7');
INSERT INTO `book`.`sys_menu`(`id`, `menu_name`, `menu_code`, `menu_url`, `pid`, `menu_img`, `menu_order`, `create_date`, `update_date`, `is_valid`, `i18n_menu_name`, `operation_type`) VALUES (9, '商品分类', '003002', '/commodity/goodsClassify', '003', NULL, 2, '2018-04-10 11:40:17', '2018-04-10 11:40:19', 1, 'menu.product.classification', '1,2,3,4');
INSERT INTO `book`.`sys_menu`(`id`, `menu_name`, `menu_code`, `menu_url`, `pid`, `menu_img`, `menu_order`, `create_date`, `update_date`, `is_valid`, `i18n_menu_name`, `operation_type`) VALUES (10, '订单模板', '003004', '/commodity/orderTemplate', '003', NULL, 4, '2018-04-10 11:41:17', '2018-04-10 11:41:20', 1, 'menu.product.order', '1,2,3,4');
INSERT INTO `book`.`sys_menu`(`id`, `menu_name`, `menu_code`, `menu_url`, `pid`, `menu_img`, `menu_order`, `create_date`, `update_date`, `is_valid`, `i18n_menu_name`, `operation_type`) VALUES (11, '店铺管理', '005', NULL, NULL, 'shop', 2, '2018-04-10 13:07:29', '2018-04-10 13:07:32', 1, 'menu.shops', '');
INSERT INTO `book`.`sys_menu`(`id`, `menu_name`, `menu_code`, `menu_url`, `pid`, `menu_img`, `menu_order`, `create_date`, `update_date`, `is_valid`, `i18n_menu_name`, `operation_type`) VALUES (12, '店铺列表', '005001', '/shop/shops', '005', NULL, 1, '2018-04-10 13:07:55', '2018-04-10 13:07:58', 1, 'menu.shops.list', '1,2,3,4,5');
INSERT INTO `book`.`sys_menu`(`id`, `menu_name`, `menu_code`, `menu_url`, `pid`, `menu_img`, `menu_order`, `create_date`, `update_date`, `is_valid`, `i18n_menu_name`, `operation_type`) VALUES (13, '店铺等级', '005002', '/shop/shopLevel', '005', NULL, 2, '2018-04-10 13:11:24', '2018-04-10 13:11:26', 1, 'menu.shops.level', '1,2,3,4');
INSERT INTO `book`.`sys_menu`(`id`, `menu_name`, `menu_code`, `menu_url`, `pid`, `menu_img`, `menu_order`, `create_date`, `update_date`, `is_valid`, `i18n_menu_name`, `operation_type`) VALUES (14, '系统配置', '006', NULL, '', 'setting', 6, '2018-04-10 13:12:50', '2018-04-10 13:12:52', 1, 'menu.system.config', '');
INSERT INTO `book`.`sys_menu`(`id`, `menu_name`, `menu_code`, `menu_url`, `pid`, `menu_img`, `menu_order`, `create_date`, `update_date`, `is_valid`, `i18n_menu_name`, `operation_type`) VALUES (15, '用户', '006001', '/authority/user', '006', NULL, 1, '2018-04-10 13:24:52', '2018-04-10 13:24:54', 1, 'menu.user', '1,2,3,4');
INSERT INTO `book`.`sys_menu`(`id`, `menu_name`, `menu_code`, `menu_url`, `pid`, `menu_img`, `menu_order`, `create_date`, `update_date`, `is_valid`, `i18n_menu_name`, `operation_type`) VALUES (16, '角色', '006002', '/authority/role', '006', '', 2, '2018-04-10 13:30:22', '2018-04-10 13:30:25', 1, 'menu.role', '1,2,3,4');
INSERT INTO `book`.`sys_menu`(`id`, `menu_name`, `menu_code`, `menu_url`, `pid`, `menu_img`, `menu_order`, `create_date`, `update_date`, `is_valid`, `i18n_menu_name`, `operation_type`) VALUES (17, '仓库设置', '006003', '/logistical/warehouse', '006', NULL, 3, '2018-04-10 12:48:01', '2018-04-10 12:48:03', 1, 'menu.warehouse', '1,2,3,4');
INSERT INTO `book`.`sys_menu`(`id`, `menu_name`, `menu_code`, `menu_url`, `pid`, `menu_img`, `menu_order`, `create_date`, `update_date`, `is_valid`, `i18n_menu_name`, `operation_type`) VALUES (18, '基础设置', '006004', '/logistical/baseConfig', '006', NULL, 4, '2018-04-19 18:41:16', '2018-04-19 18:41:19', 1, 'menu.configuration.basic', '2,4');
INSERT INTO `book`.`sys_menu`(`id`, `menu_name`, `menu_code`, `menu_url`, `pid`, `menu_img`, `menu_order`, `create_date`, `update_date`, `is_valid`, `i18n_menu_name`, `operation_type`) VALUES (19, '通知栏', '006005', NULL, '006', NULL, 5, '2018-04-10 13:05:43', '2018-04-10 13:05:44', 0, 'menu.notice.board', '1,2,3,4');

-- 角色权限表
CREATE TABLE `sys_role_menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色id',
  `menu_code` varchar(50) DEFAULT NULL COMMENT '菜单code',
  `menu_operation` int(4) DEFAULT NULL COMMENT '菜单细节操作（增删改查，导入导出（1.add、2.edit、3.delete、4.look、5.import、6.export））',
  PRIMARY KEY (`id`),
  KEY `FKkeitxsgxwayackgqllio4ohn5` (`role_id`),
  CONSTRAINT `FKkeitxsgxwayackgqllio4ohn5` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;

-- 商品分類表
CREATE TABLE `sys_products_classify` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(16) DEFAULT NULL COMMENT '类型名',
  `sort` bigint(20) DEFAULT NULL COMMENT '排序',
  `created_time` datetime DEFAULT NULL,
  `updated_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 商品表
CREATE TABLE `sys_products` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(32) DEFAULT NULL COMMENT '商品编码',
  `name` varchar(32) DEFAULT NULL COMMENT '商品名',
  `spec` varchar(32) DEFAULT NULL COMMENT '规格',
  `unit` varchar(32) DEFAULT NULL COMMENT '单位',
  `stock` int(11) DEFAULT NULL COMMENT '换算率',
  `price` decimal(13,4) DEFAULT NULL COMMENT '价格',
  `limit` int(11) DEFAULT NULL COMMENT '限购数量',
  `step` int(11) DEFAULT NULL COMMENT '步长',
  `is_up` tinyint(3) DEFAULT NULL COMMENT '是否上架',
  `order` bigint(20) DEFAULT NULL COMMENT '排序',
  `need_tag_lines` tinyint(3) DEFAULT NULL COMMENT '是否过滤线路',
  `is_valid` tinyint(3) DEFAULT NULL COMMENT '是否开启',
  `created_time` datetime DEFAULT NULL,
  `updated_time` datetime DEFAULT NULL,
  `classify_id` bigint(20) DEFAULT NULL COMMENT '分类id',
  `content` varchar(128) DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 商品历史价格表
CREATE TABLE `sys_products_history` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `name` varchar(32) DEFAULT NULL COMMENT '商品名',
  `price` decimal(13,4) DEFAULT NULL COMMENT '价格',
  `product_id` bigint(20) DEFAULT NULL COMMENT '商品id',
  `version` varchar(255) DEFAULT NULL COMMENT '編碼',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 定单模板表
CREATE TABLE `sys_templates` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(32) DEFAULT NULL COMMENT '模板名称',
  `temp_type_id` bigint(20) DEFAULT NULL COMMENT '模板类型id',
  `is_valid` tinyint(3) DEFAULT NULL COMMENT '是否启用',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_time` datetime DEFAULT NULL COMMENT '更新时间',
  `tag_warehouse_id` bigint(20) DEFAULT NULL COMMENT '仓库id',
  `is_active` tinyint(3) DEFAULT NULL COMMENT '是否有效',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 定单模板类型表
CREATE TABLE `sys_templates_type` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(32) DEFAULT NULL COMMENT '类型名',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO `book`.`sys_templates_type` (`id`, `name`) VALUES ('1', '筹备模板');
INSERT INTO `book`.`sys_templates_type` (`id`, `name`) VALUES ('2', '柠檬模板');
INSERT INTO `book`.`sys_templates_type` (`id`, `name`) VALUES ('3', '正常模板');

-- 模板商品
CREATE TABLE `sys_template_products` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `product_id` bigint(20) DEFAULT NULL COMMENT '商品id',
  `template_id` bigint(20) DEFAULT NULL COMMENT '模板id',
  `product_count` int(11) DEFAULT NULL COMMENT '对应商品数量',
  `sort` bigint(20) DEFAULT NULL COMMENT '排序码',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 审核和发货状态的订单表
CREATE TABLE `sys_order_auditing` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(32) NOT NULL,
  `temp_type_id` bigint(20) DEFAULT NULL COMMENT '订单信息模板id',
  `status` int(10) DEFAULT NULL COMMENT '订单状态',
  `send_time` datetime DEFAULT NULL COMMENT '送货时间',
  `total_price` decimal(13,0) DEFAULT NULL COMMENT '总价',
  `sort` bigint(20) DEFAULT NULL,
  `is_valid` tinyint(3) DEFAULT NULL COMMENT '是否启用',
  `content` varchar(255) DEFAULT NULL COMMENT '描述',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `shop_id` bigint(20) DEFAULT NULL COMMENT '商店id',
  `temp_id` bigint(20) DEFAULT NULL COMMENT '订单商品购买模板id',
  `maker_id` bigint(20) DEFAULT NULL COMMENT '下单人id',
  `plan_id` bigint(20) DEFAULT NULL COMMENT '计划id',
  `shop_name` varchar(32) DEFAULT NULL COMMENT '店铺名',
  `temp_name` varchar(32) DEFAULT NULL COMMENT '模板名',
  `warehouse_name` varchar(32) DEFAULT NULL COMMENT '所属仓库名',
  `line_name` varchar(32) DEFAULT NULL COMMENT '线路名',
  `warehouse_id` bigint(20) DEFAULT NULL COMMENT '仓库id',
  `line_id` bigint(20) DEFAULT NULL COMMENT '线路id',
  `user_name` varchar(150) DEFAULT NULL COMMENT '下单账户名',
  `first_name` varchar(30) DEFAULT NULL COMMENT '下单用户名',
  `phone` varchar(150) DEFAULT NULL COMMENT '下单人联系电话',
  `province` varchar(32) DEFAULT NULL COMMENT '省',
  `city` varchar(32) DEFAULT NULL COMMENT '市',
  `district` varchar(32) DEFAULT NULL COMMENT '县',
  `address` varchar(64) DEFAULT NULL COMMENT '地址',
  `shop_code` varchar(32) DEFAULT NULL COMMENT '商铺编号',
  `send_shop_id` bigint(20) DEFAULT NULL COMMENT '送货店铺的id',
  `distribution_type` int(11) DEFAULT NULL COMMENT '配送方式',
  `distribution_phone` varchar(30) DEFAULT NULL COMMENT '配送手机号',
   `distribution_people` varchar(150) DEFAULT NULL COMMENT '配送人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 未审核的订单数据表
CREATE TABLE `sys_order_submission` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(32) NOT NULL,
  `temp_type_id` bigint(20) DEFAULT NULL COMMENT '订单信息模板id',
  `status` int(10) DEFAULT NULL COMMENT '订单状态',
  `send_time` datetime DEFAULT NULL COMMENT '送货时间',
  `total_price` decimal(13,0) DEFAULT NULL COMMENT '总价',
  `sort` bigint(20) DEFAULT NULL,
  `is_valid` tinyint(3) DEFAULT NULL COMMENT '是否启用',
  `content` varchar(255) DEFAULT NULL COMMENT '描述',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `shop_id` bigint(20) DEFAULT NULL COMMENT '商店id',
  `temp_id` bigint(20) DEFAULT NULL COMMENT '订单商品购买模板id',
  `maker_id` bigint(20) DEFAULT NULL COMMENT '下单人id',
  `plan_id` bigint(20) DEFAULT NULL COMMENT '计划id',
  `send_shop_id` bigint(20) DEFAULT NULL COMMENT '收货商店id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 完成的订单表
CREATE TABLE `sys_order_finish` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(32) NOT NULL,
  `temp_type_id` bigint(20) DEFAULT NULL COMMENT '订单信息模板id',
  `status` int(10) DEFAULT NULL COMMENT '订单状态',
  `send_time` datetime DEFAULT NULL COMMENT '送货时间',
  `total_price` decimal(13,0) DEFAULT NULL COMMENT '总价',
  `sort` bigint(20) DEFAULT NULL,
  `is_valid` tinyint(3) DEFAULT NULL COMMENT '是否启用',
  `content` varchar(255) DEFAULT NULL COMMENT '描述',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `shop_id` bigint(20) DEFAULT NULL COMMENT '商店id',
  `temp_id` bigint(20) DEFAULT NULL COMMENT '订单商品购买模板id',
  `maker_id` bigint(20) DEFAULT NULL COMMENT '下单人id',
  `plan_id` bigint(20) DEFAULT NULL COMMENT '计划id',
  `shop_name` varchar(32) DEFAULT NULL COMMENT '店铺名',
  `temp_name` varchar(32) DEFAULT NULL COMMENT '模板名',
  `warehouse_name` varchar(32) DEFAULT NULL COMMENT '所属仓库名',
  `line_name` varchar(32) DEFAULT NULL COMMENT '线路名',
  `warehouse_id` bigint(20) DEFAULT NULL COMMENT '仓库id',
  `line_id` bigint(20) DEFAULT NULL COMMENT '线路id',
  `user_name` varchar(150) DEFAULT NULL COMMENT '下单账户名',
  `first_name` varchar(30) DEFAULT NULL COMMENT '下单用户名',
  `phone` varchar(150) DEFAULT NULL COMMENT '下单人联系电话',
  `province` varchar(32) DEFAULT NULL COMMENT '省',
  `city` varchar(32) DEFAULT NULL COMMENT '市',
  `district` varchar(32) DEFAULT NULL COMMENT '县',
  `address` varchar(64) DEFAULT NULL COMMENT '地址',
  `shop_code` varchar(32) DEFAULT NULL COMMENT '商铺编号',
  `send_shop_id` bigint(20) DEFAULT NULL COMMENT '送货店铺的id',
  `distribution_type` int(11) DEFAULT NULL COMMENT '配送方式',
  `distribution_phone` varchar(30) DEFAULT NULL COMMENT '配送手机号',
  `distribution_people` varchar(150) DEFAULT NULL COMMENT '配送人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 删除的订单数据表
CREATE TABLE `sys_order_delete` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(32) NOT NULL,
  `temp_type_id` bigint(20) DEFAULT NULL COMMENT '订单信息模板id',
  `status` int(10) DEFAULT NULL COMMENT '订单状态',
  `send_time` datetime DEFAULT NULL COMMENT '送货时间',
  `total_price` decimal(13,0) DEFAULT NULL COMMENT '总价',
  `sort` bigint(20) DEFAULT NULL,
  `is_valid` tinyint(3) DEFAULT NULL COMMENT '是否启用',
  `content` varchar(255) DEFAULT NULL COMMENT '描述',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `shop_id` bigint(20) DEFAULT NULL COMMENT '商店id',
  `temp_id` bigint(20) DEFAULT NULL COMMENT '订单商品购买模板id',
  `maker_id` bigint(20) DEFAULT NULL COMMENT '下单人id',
  `plan_id` bigint(20) DEFAULT NULL COMMENT '计划id',
  `shop_name` varchar(32) DEFAULT NULL COMMENT '店铺名',
  `temp_name` varchar(32) DEFAULT NULL COMMENT '模板名',
  `warehouse_name` varchar(32) DEFAULT NULL COMMENT '仓库名',
  `line_name` varchar(32) DEFAULT NULL COMMENT '配送方',
  `warehouse_id` bigint(20) DEFAULT NULL COMMENT '仓库id',
  `line_id` bigint(20) DEFAULT NULL COMMENT '线路id',
  `user_name` varchar(150) DEFAULT NULL COMMENT '下单账户名',
  `first_name` varchar(30) DEFAULT NULL COMMENT '下单用户名',
  `phone` varchar(150) DEFAULT NULL COMMENT '下单人联系电话',
  `province` varchar(32) DEFAULT NULL COMMENT '省',
  `city` varchar(32) DEFAULT NULL COMMENT '市',
  `district` varchar(32) DEFAULT NULL COMMENT '县',
  `address` varchar(64) DEFAULT NULL COMMENT '地址',
  `shop_code` varchar(32) DEFAULT NULL COMMENT '商铺编号',
  `send_shop_id` bigint(20) DEFAULT NULL COMMENT '送货店铺的id',
  `distribution_type` int(11) DEFAULT NULL COMMENT '配送方式',
  `distribution_phone` varchar(30) DEFAULT NULL COMMENT '配送手机号',
  `distribution_people` varchar(150) DEFAULT NULL COMMENT '配送人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 审核和发货状态的订单表详情
CREATE TABLE `sys_order_templates_auditing` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(16) DEFAULT NULL COMMENT '编号',
  `product_name` varchar(32) DEFAULT NULL,
  `product_price` decimal(13,0) DEFAULT NULL COMMENT '商品单价',
  `amount` int(11) DEFAULT NULL COMMENT '数量',
  `is_valid` tinyint(3) DEFAULT NULL COMMENT '是否启用',
  `pre_edited` int(11) DEFAULT NULL,
  `order_id` bigint(20) DEFAULT NULL COMMENT '订单id',
  `product_id` bigint(20) DEFAULT NULL COMMENT '商品id',
  `spec` varchar(32) DEFAULT NULL COMMENT '规格',
  `unit` varchar(32) DEFAULT NULL COMMENT '单位',
  `stock` int(11) DEFAULT NULL COMMENT '换算率',
  `stock_unit` varchar(32) DEFAULT NULL COMMENT '换算单位',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 未审核
CREATE TABLE `sys_order_templates_submission` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(16) DEFAULT NULL COMMENT '编号',
  `amount` int(11) DEFAULT NULL COMMENT '数量',
  `is_valid` tinyint(3) DEFAULT NULL COMMENT '是否启用',
  `pre_edited` int(11) DEFAULT NULL,
  `order_id` bigint(20) DEFAULT NULL COMMENT '订单id',
  `product_id` bigint(20) DEFAULT NULL COMMENT '商品id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1539363 DEFAULT CHARSET=utf8mb4;

-- 已完成
CREATE TABLE `sys_order_templates_finish` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(16) DEFAULT NULL COMMENT '编号',
  `product_name` varchar(32) DEFAULT NULL,
  `product_price` decimal(13,0) DEFAULT NULL COMMENT '商品单价',
  `amount` int(11) DEFAULT NULL COMMENT '数量',
  `is_valid` tinyint(3) DEFAULT NULL COMMENT '是否启用',
  `pre_edited` int(11) DEFAULT NULL,
  `order_id` bigint(20) DEFAULT NULL COMMENT '订单id',
  `product_id` bigint(20) DEFAULT NULL COMMENT '商品id',
  `spec` varchar(32) DEFAULT NULL COMMENT '规格',
  `unit` varchar(32) DEFAULT NULL COMMENT '单位',
  `stock` int(11) DEFAULT NULL COMMENT '换算率',
  `stock_unit` varchar(32) DEFAULT NULL COMMENT '换算单位',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 删除
CREATE TABLE `sys_order_templates_delete` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(16) DEFAULT NULL COMMENT '编号',
  `product_name` varchar(32) DEFAULT NULL,
  `product_price` decimal(13,0) DEFAULT NULL COMMENT '商品单价',
  `amount` int(11) DEFAULT NULL COMMENT '数量',
  `is_valid` tinyint(3) DEFAULT NULL COMMENT '是否启用',
  `pre_edited` int(11) DEFAULT NULL,
  `order_id` bigint(20) DEFAULT NULL COMMENT '订单id',
  `product_id` bigint(20) DEFAULT NULL COMMENT '商品id',
  `spec` varchar(32) DEFAULT NULL COMMENT '规格',
  `unit` varchar(32) DEFAULT NULL COMMENT '单位',
  `stock` int(11) DEFAULT NULL COMMENT '换算率',
  `stock_unit` varchar(32) DEFAULT NULL COMMENT '换算单位',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `sys_setups` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(35) DEFAULT NULL COMMENT '编号组',
  `config_param1` varchar(50) DEFAULT NULL,
  `config_param2` varchar(50) DEFAULT NULL,
  `config_param3` varchar(50) DEFAULT NULL,
  `config_param4` varchar(50) DEFAULT NULL,
  `config_param5` varchar(50) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `is_valid` tinyint(3) DEFAULT NULL COMMENT '是否启用',
  `code_group` int(11) DEFAULT NULL COMMENT '配置组号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 商品关联的店铺状态表
CREATE TABLE `sys_products_status` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `product_id` bigint(20) DEFAULT NULL,
  `shop_status` int(11) DEFAULT NULL COMMENT '商品状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;




