DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
                       `id` int(11) default NULL auto_increment,
                        `username` varchar(32) NOT NULL COMMENT '用户名称',
                        `phone` varchar(32) NOT NULL COMMENT '电话号码',
                        `password` varchar(32) NOT NULL COMMENT '密码',
                        `birthday` varchar(32) default NULL COMMENT '生日',
                        `sex` char(1) default NULL COMMENT '性别',
                        `edu` varchar(256) default NULL COMMENT '教育',
                        `role` char(1) default NULL COMMENT '用户角色',
                        `info` varchar(256) default NULL COMMENT '个人简介',
                        `email` varchar(32) default NULL COMMENT '邮箱',

                        PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `user_record`(
                              `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
                              `phone` VARCHAR(32) NOT NULL COMMENT '电话号码',
                              `date` DATETIME NOT NULL COMMENT '贷款时间',
                              `amount` BIGINT(20) DEFAULT NULL COMMENT '贷款额度',
                              `check` CHAR(1) DEFAULT NULL COMMENT '审查进度',
                              `username` varchar(32) DEFAULT NULL COMMENT '用户姓名',
                              PRIMARY KEY  (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;



CREATE TABLE `loan` (
                        `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
                        `username` VARCHAR(32) NOT NULL COMMENT '用户名称',
                        `phone` VARCHAR(32) NOT NULL COMMENT '电话号码',
                        `check` CHAR(1) DEFAULT NULL COMMENT '审查进度',
                        `checker_name` VARCHAR(32) DEFAULT NULL COMMENT '审查员名字',
                        `role` CHAR(1) DEFAULT NULL COMMENT '审查员级别',
                        `amount` BIGINT(20) DEFAULT NULL COMMENT '贷款额度',
                        `user_email` VARCHAR(32) DEFAULT NULL COMMENT '用户邮箱',
                        `checker_email` VARCHAR(32) DEFAULT NULL COMMENT '审查员邮箱',
                        `user_loan_id` BIGINT(20) DEFAULT NULL  COMMENT '关联用户贷款记录的id',
                        `date` DATETIME NOT NULL COMMENT '贷款时间',
                        `check_date` DATETIME NOT NULL COMMENT '审核时间',

                        PRIMARY KEY  (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;
