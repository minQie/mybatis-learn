# 创建数据库
CREATE DATABASE `mybatis` CHARACTER SET 'utf8' COLLATE 'utf8_unicode_ci';

# 用户表
CREATE TABLE `mybatis`.`user`  (
  `id` bigint(0) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `username` varchar(20) NOT NULL COMMENT '用户名',
  `gender` TINYINT(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '性别',
  `birthday` datetime(0) COMMENT '生日',
  `address` varchar(40) COMMENT '地址',
  `gmt_create` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime(0) COMMENT '更新时间',
  PRIMARY KEY (id)
) COMMENT = '用户表';

# 订单表
CREATE TABLE `mybatis`.`order` (
  `id`              bigint(0) UNSIGNED AUTO_INCREMENT NOT NULl COMMENT '主键',
  `code`            varchar(32) NOT NULL COMMENT '编号',
  `money`           decimal(11, 2) NOT NULL DEFAULT 0 COMMENT '金额',
  `user_id`         bigint(0) UNSIGNED NOT NULL COMMENT '所属用户id',
  `gmt_create`      datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified`    datetime(0) COMMENT '更新时间',
  PRIMARY KEY (id)
) COMMENT = '订单表';

# 不使用外键
# ALTER TABLE `order` ADD CONSTRAINT fk_order_user_id FOREIGN KEY ( user_id ) REFERENCES `user` ( id );