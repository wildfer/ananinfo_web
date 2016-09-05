CREATE TABLE `aai_system_service` (
  `service_name` varchar(64) NOT NULL DEFAULT '' COMMENT '�����ֵ��÷���',
  `service_ip` varchar(32) DEFAULT NULL COMMENT 'ip',
  `service_port` int(10) DEFAULT NULL COMMENT '�˿�',
  `max_active` int(10) DEFAULT NULL COMMENT '�������',
  `max_idle` int(10) DEFAULT NULL COMMENT '�����ж�������',
  `min_idle` int(10) DEFAULT NULL COMMENT '��С���ж�������',
  `time_out` int(10) DEFAULT NULL COMMENT '��ʱʱ��/��',
  `create_dt` int(10) DEFAULT NULL COMMENT '����ʱ��',
  `update_dt` int(10) DEFAULT NULL,
  PRIMARY KEY (`service_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Զ�̵��÷�������'
;

CREATE TABLE `aai_system_cmd` (
  `cmd_id` varchar(64) NOT NULL DEFAULT '' COMMENT '������',
  `describer` varchar(1000) DEFAULT NULL COMMENT '����',
  `service_name` varchar(64) DEFAULT NULL COMMENT '�����ֵ��÷���',
  `cmd_class` varchar(64) DEFAULT NULL COMMENT '�����������,����',
  `status` int(11) DEFAULT NULL COMMENT '1:��Ч,0:ʧЧ',
  `create_dt` int(10) DEFAULT NULL COMMENT '����ʱ��',
  `update_dt` int(10) DEFAULT NULL,
  PRIMARY KEY (`cmd_id`),
  KEY `service_name` (`service_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='������'
;

CREATE TABLE `aai_system_setting` (
  `skey` varchar(50) NOT NULL COMMENT '����key',
  `svalue` text NOT NULL COMMENT '����value',
  `sname` varchar(100) DEFAULT NULL COMMENT '����',
  PRIMARY KEY (`skey`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='ϵͳ���ñ�'
;


CREATE TABLE `aai_users` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `user_login` varchar(60)  NOT NULL DEFAULT '',
  `user_pass` varchar(255)  NOT NULL DEFAULT '',
  `user_phone` varchar(20) NOT NULL COMMENT '�ֻ��ţ�Ҫ��Ψһ',
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
) ENGINE=InnoDB AUTO_INCREMENT=2  DEFAULT CHARSET=utf8 COMMENT='�û���'
;

CREATE TABLE `aai_usermeta` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) unsigned NOT NULL DEFAULT '0',
  `meta_key` varchar(255) DEFAULT NULL,
  `meta_value` longtext,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `meta_key` (`meta_key`(191))
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8 COMMENT='�û����ӱ�'
;


CREATE TABLE `aai_admin_rules` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pid` int(6) unsigned NOT NULL DEFAULT '0' COMMENT '��ID',
  `title` varchar(50) DEFAULT NULL,
  `module` varchar(50) NOT NULL,
  `action` varchar(50) DEFAULT NULL,
  `data` varchar(200) DEFAULT NULL,
  `dataplus` int(10) DEFAULT '0' COMMENT '������ʾ��ʽ',
  `status` tinyint(1) unsigned NOT NULL DEFAULT '1' COMMENT '0 ɾ�� 1 �˵�+Ȩ�� 2 Ȩ��',
  `orderid` smallint(5) unsigned DEFAULT '10',
  PRIMARY KEY (`id`),
  KEY `pid` (`pid`),
  KEY `module_action_status` (`module`,`action`,`status`)
) ENGINE=InnoDB AUTO_INCREMENT=658 DEFAULT CHARSET=utf8 COMMENT='��̨����'
;
CREATE TABLE `aai_admin_member` (
  `uid` int(11) unsigned NOT NULL,
  `gid` varchar(50) NOT NULL DEFAULT '' COMMENT 'Ȩ����',
  `logintimes` int(11) NOT NULL DEFAULT '0' COMMENT '��½����������������̨',
  `realname` varchar(50) NOT NULL DEFAULT '' COMMENT '��ʵ����',
  `email` varchar(50) NOT NULL DEFAULT '' COMMENT '֪ͨ����',
  `username` varchar(20) NOT NULL DEFAULT '' COMMENT '�û���',
  `status` varchar(10) DEFAULT '0' COMMENT '����',
  PRIMARY KEY (`uid`),
  KEY `gid` (`gid`),
  KEY `realname` (`realname`),
  KEY `media_id` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='��̨�û�Ȩ�ޱ�'
;
CREATE TABLE `aai_admin_group` (
  `gid` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(50) NOT NULL DEFAULT '0',
  `rules` text NOT NULL,
  PRIMARY KEY (`gid`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8 COMMENT='��̨Ȩ����'
;

#################################################################����##############################################################################
CREATE TABLE `aai_post` (
  `id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '����id��������',
  `uid` INT(10) UNSIGNED NOT NULL COMMENT '�����˵�id',
  `img_cover` VARCHAR(255) NOT NULL COMMENT '����ͼƬ',
  `title` TEXT  NOT NULL COMMENT '����',
  `describe` TEXT  NOT NULL COMMENT '����',
  `content` LONGTEXT NOT NULL COMMENT '��������',
  `status` VARCHAR(20) NOT NULL DEFAULT 'publish' COMMENT '����״̬��trash,publish,inherit,draft',
  `sort` INT(10) DEFAULT '0' COMMENT '����',
  `create_date` DATETIME NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '����ʱ��',
  `update_date` DATETIME NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '������ʱ��',
  `comment_status` VARCHAR(20)  NOT NULL DEFAULT 'open' COMMENT '����״̬��open,close',
  `comment_count` INT(10) UNSIGNED NOT NULL DEFAULT '0' COMMENT '���۵�����',
  `like_count` INT(10) UNSIGNED NOT NULL DEFAULT '0' COMMENT '�޵�����',
  `bad_count` INT(10) UNSIGNED NOT NULL DEFAULT '0' COMMENT '�ȵ�����',
  `collect_count` INT(10) UNSIGNED NOT NULL DEFAULT '0' COMMENT '�ղص�����',
  `share_count` INT(10) UNSIGNED NOT NULL DEFAULT '0' COMMENT '���������',
  `view_count` INT(10) UNSIGNED NOT NULL DEFAULT '0' COMMENT '�鿴������',
  `hot` TINYINT(1) UNSIGNED NOT NULL DEFAULT '0' COMMENT '�Ƿ�����',
  `hot_con` VARCHAR(255) NOT NULL DEFAULT '' COMMENT '�����Ƿ�Ϊ�Ƽ���������json���������ֶ�Ϊ��ʱ�ſ������Ƽ�',
  PRIMARY KEY (`id`),
  KEY `idx_uid` (`uid`),
  KEY `idx_hot` (`hot`)
) ENGINE=INNODB AUTO_INCREMENT=2583 DEFAULT CHARSET=utf8 COMMENT='���Ӷ����'
;


CREATE TABLE `aai_postmeta` (
  `id` INT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `post_id` INT(20) UNSIGNED NOT NULL DEFAULT '0',
  `meta_key` VARCHAR(255)  DEFAULT NULL,
  `meta_value` LONGTEXT,
  PRIMARY KEY (`id`),
  KEY `post_id` (`post_id`),
  KEY `meta_key` (`meta_key`)
) ENGINE=INNODB AUTO_INCREMENT=138 DEFAULT CHARSET=utf8 COMMENT='���Ӹ��ӱ�'
;

CREATE TABLE `aai_comments` (
  `comment_id` INT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `comment_post_id` INT(20) UNSIGNED NOT NULL DEFAULT '0',
  `comment_user_id` INT(10) UNSIGNED NOT NULL COMMENT '�����˵�id',
  `comment_user_IP` VARCHAR(100)  NOT NULL DEFAULT '',
  `comment_parent` INT(20) UNSIGNED NOT NULL DEFAULT '0',
  `comment_content`  LONGTEXT NOT NULL COMMENT '��������',
  `comment_status` VARCHAR(20) NOT NULL DEFAULT 'publish' COMMENT '����״̬��trash,publish,inherit,draft',
  `comment_date` DATETIME NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '����ʱ��',
  `comment_date_up` DATETIME NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '������ʱ��',
  PRIMARY KEY (`comment_ID`),
  KEY `comment_post_ID` (`comment_post_ID`),
  KEY `comment_user_id` (`comment_user_id`),
  KEY `comment_status` (`comment_status`),
  KEY `comment_parent` (`comment_parent`)
) ENGINE=INNODB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='�������Ӷ����';


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
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '����id��������',
  `pid` int(10) unsigned NOT NULL COMMENT '����id',
  `uid` int(10) unsigned NOT NULL COMMENT '�û�id',
  `content` text NOT NULL COMMENT '��������',
  `like_count` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '�޵�����',
  `bad_count` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '�ȵ�����',
  `cid` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '���˵�����ID,�ظ�����',
  `c_ip` varchar(20) NOT NULL DEFAULT '' COMMENT 'ip',
  `status` varchar(20) NOT NULL DEFAULT 'publish' COMMENT 'trash,publish,inherit,draft',
  `create_date` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '����ʱ��',
  `update_date` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '������ʱ��',
  PRIMARY KEY (`id`),
  KEY `idx_tid` (`pid`),
  KEY `idx_uid` (`uid`)
) ENGINE=InnoDB AUTO_INCREMENT=3035 DEFAULT CHARSET=utf8 COMMENT='��������'
;

CREATE TABLE `aai_post_tags` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '������',
  `pid` int(10) unsigned NOT NULL COMMENT '����id',
  `tgid` int(10) unsigned NOT NULL COMMENT '��ǩid',
  `create_dt` int(10) unsigned NOT NULL COMMENT '����ʱ��',
  PRIMARY KEY (`id`),
  KEY `idx_tid` (`pid`),
  KEY `idx_uid` (`tgid`)
) ENGINE=InnoDB AUTO_INCREMENT=11261 DEFAULT CHARSET=utf8 COMMENT='���ӱ�ǩ'
;

CREATE TABLE `aai_tags` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `tag_name` varchar(255) NOT NULL DEFAULT '' COMMENT '��ǩ����',
  `type` int(1) NOT NULL DEFAULT '0' COMMENT '����,0��ͨ���ӱ�ǩ��1���ǩ',
  `ishot` int(10) DEFAULT '0',
  `status` int(11) DEFAULT NULL COMMENT '0���ã�1����',
  `create_dt` int(10) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_key` (`tag_name`)
) ENGINE=InnoDB AUTO_INCREMENT=610 DEFAULT CHARSET=utf8 COMMENT='��ǩ��'
;