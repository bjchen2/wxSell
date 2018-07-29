CREATE TABLE `order_master` (
  `order_id` varchar(32) NOT NULL COMMENT '订单id',
  `user_phone` varchar(32) NOT NULL COMMENT '买家手机号',
  `user_name` varchar(32) NOT NULL COMMENT '买家姓名',
  `user_address` varchar(128) NOT NULL COMMENT '买家地址',
  `user_openid` varchar(64) NOT NULL COMMENT '买家微信openId',
  `order_status` tinyint(3) NOT NULL DEFAULT '0' COMMENT '订单状态，默认0新下单',
  `pay_status` tinyint(3) NOT NULL DEFAULT '0' COMMENT '支付状态，默认0未支付',
  `order_amount` decimal(10,2) NOT NULL COMMENT '订单总金额',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`order_id`),
  KEY `idx_user_openid` (`user_openid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单表';