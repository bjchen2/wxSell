CREATE TABLE `seller_info`(
  `seller_id` VARCHAR(32) NOT NULL COMMENT '卖家id',
  `username` VARCHAR(32) NOT NULL COMMENT '卖家用户名',
  `password` VARCHAR(32) NOT NULL COMMENT '卖家密码',
  `openid` VARCHAR(64) NOT NULL COMMENT '卖家微信openid',
  `create_time` TIMESTAMP NOT NULL DEFAULT current_timestamp COMMENT '创建时间',
  `update_time` TIMESTAMP NOT NULL DEFAULT current_timestamp ON UPDATE current_timestamp COMMENT '最近更新时间',
  PRIMARY KEY(`seller_id`)
)COMMENT '卖家信息表';