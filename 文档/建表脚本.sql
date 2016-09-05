CREATE TABLE `aai_system_service` (
  `service_name` varchar(64) NOT NULL DEFAULT '' COMMENT '命令字调用服务',
  `service_ip` varchar(32) DEFAULT NULL COMMENT 'ip',
  `service_port` int(10) DEFAULT NULL COMMENT '端口',
  `max_active` int(10) DEFAULT NULL COMMENT '最大数量',
  `max_idle` int(10) DEFAULT NULL COMMENT '最大空闲对象数量',
  `min_idle` int(10) DEFAULT NULL COMMENT '最小空闲对象数量',
  `time_out` int(10) DEFAULT NULL COMMENT '超时时间/秒',
  `create_dt` int(10) DEFAULT NULL COMMENT '创建时间',
  `update_dt` int(10) DEFAULT NULL,
  PRIMARY KEY (`service_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='远程调用服务配置'
;

CREATE TABLE `aai_system_cmd` (
  `cmd_id` varchar(64) NOT NULL DEFAULT '' COMMENT '命令字',
  `describer` varchar(1000) DEFAULT NULL COMMENT '描述',
  `service_name` varchar(64) DEFAULT NULL COMMENT '命令字调用服务',
  `cmd_class` varchar(64) DEFAULT NULL COMMENT '命令字入口类,备用',
  `status` int(11) DEFAULT NULL COMMENT '1:有效,0:失效',
  `create_dt` int(10) DEFAULT NULL COMMENT '创建时间',
  `update_dt` int(10) DEFAULT NULL,
  PRIMARY KEY (`cmd_id`),
  KEY `service_name` (`service_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='命令字'
;

CREATE TABLE `aai_system_setting` (
  `skey` varchar(50) NOT NULL COMMENT '配置key',
  `svalue` text NOT NULL COMMENT '配置value',
  `sname` varchar(100) DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`skey`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统配置表'
;


CREATE TABLE `aai_users` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `user_login` varchar(60)  NOT NULL DEFAULT '',
  `user_pass` varchar(255)  NOT NULL DEFAULT '',
  `user_phone` varchar(20) NOT NULL COMMENT '手机号，要求唯一',
  `user_nicename` varchar(50)  NOT NULL DEFAULT '',
  `user_email` varchar(100)  NOT NULL DEFAULT '',
  `user_url` varchar(100)  NOT NULL DEFAULT '',
  `user_registered` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `user_activation_key` varchar(255)  NOT NULL DEFAULT '',
  `user_status` int(11) NOT NULL DEFAULT '0',
  `user_note` varchar(250)  NOT NULL DEFAULT '',
  PRIMARY KEY (`id`),
  KEY `user_login_key` (`user_login`),
  KEY `user_nicename` (`user_nicename`),
  KEY `user_email` (`user_email`),
  KEY `user_phone` (`user_phone`)
) ENGINE=InnoDB AUTO_INCREMENT=2  DEFAULT CHARSET=utf8 COMMENT='用户表'
;

CREATE TABLE `aai_usermeta` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) unsigned NOT NULL DEFAULT '0',
  `meta_key` varchar(255) DEFAULT NULL,
  `meta_value` longtext,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `meta_key` (`meta_key`(191))
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8 COMMENT='用户附加表'
;


CREATE TABLE `aai_admin_rules` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pid` int(6) unsigned NOT NULL DEFAULT '0' COMMENT '父ID',
  `title` varchar(50) DEFAULT NULL,
  `module` varchar(50) NOT NULL,
  `action` varchar(50) DEFAULT NULL,
  `data` varchar(200) DEFAULT NULL,
  `dataplus` int(10) DEFAULT '0' COMMENT '报表显示格式',
  `status` tinyint(1) unsigned NOT NULL DEFAULT '1' COMMENT '0 删除 1 菜单+权限 2 权限',
  `orderid` smallint(5) unsigned DEFAULT '10',
  PRIMARY KEY (`id`),
  KEY `pid` (`pid`),
  KEY `module_action_status` (`module`,`action`,`status`)
) ENGINE=InnoDB AUTO_INCREMENT=658 DEFAULT CHARSET=utf8 COMMENT='后台规则'
;
CREATE TABLE `aai_admin_member` (
  `uid` int(11) unsigned NOT NULL,
  `gid` varchar(50) NOT NULL DEFAULT '' COMMENT '权限组',
  `logintimes` int(11) NOT NULL DEFAULT '0' COMMENT '登陆次数，用于锁定后台',
  `realname` varchar(50) NOT NULL DEFAULT '' COMMENT '真实名称',
  `email` varchar(50) NOT NULL DEFAULT '' COMMENT '通知邮箱',
  `username` varchar(20) NOT NULL DEFAULT '' COMMENT '用户名',
  `status` varchar(10) DEFAULT '0' COMMENT '类型',
  PRIMARY KEY (`uid`),
  KEY `gid` (`gid`),
  KEY `realname` (`realname`),
  KEY `media_id` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='后台用户权限表'
;
CREATE TABLE `aai_admin_group` (
  `gid` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(50) NOT NULL DEFAULT '0',
  `rules` text NOT NULL,
  PRIMARY KEY (`gid`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8 COMMENT='后台权限组'
;

#################################################################内容##############################################################################
CREATE TABLE `aai_post` (
  `id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '帖子id，表主键',
  `uid` INT(10) UNSIGNED NOT NULL COMMENT '发帖人的id',
  `img_cover` VARCHAR(255) NOT NULL COMMENT '封面图片',
  `title` TEXT  NOT NULL COMMENT '标题',
  `describe` TEXT  NOT NULL COMMENT '描述',
  `content` LONGTEXT NOT NULL COMMENT '帖子内容',
  `status` VARCHAR(20) NOT NULL DEFAULT 'publish' COMMENT '帖子状态：trash,publish,inherit,draft',
  `sort` INT(10) DEFAULT '0' COMMENT '排序',
  `create_date` DATETIME NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `update_date` DATETIME NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '最后更新时间',
  `comment_status` VARCHAR(20)  NOT NULL DEFAULT 'open' COMMENT '评论状态：open,close',
  `comment_count` INT(10) UNSIGNED NOT NULL DEFAULT '0' COMMENT '评论的总数',
  `like_count` INT(10) UNSIGNED NOT NULL DEFAULT '0' COMMENT '赞的总数',
  `bad_count` INT(10) UNSIGNED NOT NULL DEFAULT '0' COMMENT '踩的总数',
  `collect_count` INT(10) UNSIGNED NOT NULL DEFAULT '0' COMMENT '收藏的总数',
  `share_count` INT(10) UNSIGNED NOT NULL DEFAULT '0' COMMENT '分享的总数',
  `view_count` INT(10) UNSIGNED NOT NULL DEFAULT '0' COMMENT '查看的总数',
  `hot` TINYINT(1) UNSIGNED NOT NULL DEFAULT '0' COMMENT '是否热门',
  `hot_con` VARCHAR(255) NOT NULL DEFAULT '' COMMENT '评判是否为推荐的条件，json串。当该字段为空时才可能是推荐',
  PRIMARY KEY (`id`),
  KEY `idx_uid` (`uid`),
  KEY `idx_hot` (`hot`)
) ENGINE=INNODB AUTO_INCREMENT=2583 DEFAULT CHARSET=utf8 COMMENT='帖子定义表'
;


CREATE TABLE `aai_postmeta` (
  `id` INT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `post_id` INT(20) UNSIGNED NOT NULL DEFAULT '0',
  `meta_key` VARCHAR(255)  DEFAULT NULL,
  `meta_value` LONGTEXT,
  PRIMARY KEY (`id`),
  KEY `post_id` (`post_id`),
  KEY `meta_key` (`meta_key`)
) ENGINE=INNODB AUTO_INCREMENT=138 DEFAULT CHARSET=utf8 COMMENT='帖子附加表'
;

CREATE TABLE `aai_comments` (
  `comment_id` INT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `comment_post_id` INT(20) UNSIGNED NOT NULL DEFAULT '0',
  `comment_user_id` INT(10) UNSIGNED NOT NULL COMMENT '发帖人的id',
  `comment_user_IP` VARCHAR(100)  NOT NULL DEFAULT '',
  `comment_parent` INT(20) UNSIGNED NOT NULL DEFAULT '0',
  `comment_content`  LONGTEXT NOT NULL COMMENT '帖子内容',
  `comment_status` VARCHAR(20) NOT NULL DEFAULT 'publish' COMMENT '帖子状态：trash,publish,inherit,draft',
  `comment_date` DATETIME NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `comment_date_up` DATETIME NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '最后更新时间',
  PRIMARY KEY (`comment_ID`),
  KEY `comment_post_ID` (`comment_post_ID`),
  KEY `comment_user_id` (`comment_user_id`),
  KEY `comment_status` (`comment_status`),
  KEY `comment_parent` (`comment_parent`)
) ENGINE=INNODB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='跟帖帖子定义表';


CREATE TABLE `aai_commentmeta` (
  `meta_id` INT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `comment_id` INT(20) UNSIGNED NOT NULL DEFAULT '0',
  `meta_key` VARCHAR(255)DEFAULT NULL,
  `meta_value` LONGTEXT ,
  PRIMARY KEY (`meta_id`),
  KEY `comment_id` (`comment_id`),
  KEY `meta_key` (`meta_key`(191))
) ENGINE=INNODB DEFAULT CHARSET=utf8 ;

CREATE TABLE `aai_post_comments` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '评论id，表主键',
  `pid` int(10) unsigned NOT NULL COMMENT '帖子id',
  `uid` int(10) unsigned NOT NULL COMMENT '用户id',
  `content` text NOT NULL COMMENT '评论内容',
  `like_count` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '赞的总数',
  `bad_count` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '踩的总数',
  `cid` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '别人的帖子ID,回复别人',
  `c_ip` varchar(20) NOT NULL DEFAULT '' COMMENT 'ip',
  `status` varchar(20) NOT NULL DEFAULT 'publish' COMMENT 'trash,publish,inherit,draft',
  `create_date` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `update_date` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '最后更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_tid` (`pid`),
  KEY `idx_uid` (`uid`)
) ENGINE=InnoDB AUTO_INCREMENT=3035 DEFAULT CHARSET=utf8 COMMENT='帖子评论'
;

CREATE TABLE `aai_post_tags` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '表主键',
  `pid` int(10) unsigned NOT NULL COMMENT '帖子id',
  `tgid` int(10) unsigned NOT NULL COMMENT '标签id',
  `create_dt` int(10) unsigned NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `idx_tid` (`pid`),
  KEY `idx_uid` (`tgid`)
) ENGINE=InnoDB AUTO_INCREMENT=11261 DEFAULT CHARSET=utf8 COMMENT='帖子标签'
;

CREATE TABLE `aai_tags` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `tag_name` varchar(255) NOT NULL DEFAULT '' COMMENT '标签描述',
  `type` int(1) NOT NULL DEFAULT '0' COMMENT '类型,0普通帖子标签，1活动标签',
  `ishot` int(10) DEFAULT '0',
  `status` int(11) DEFAULT NULL COMMENT '0禁用，1开启',
  `create_dt` int(10) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_key` (`tag_name`)
) ENGINE=InnoDB AUTO_INCREMENT=610 DEFAULT CHARSET=utf8 COMMENT='标签表'
;