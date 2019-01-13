CREATE DATABASE dogcatlogin;

use dogcatlogin;

CREATE TABLE `sys_session` (
  `id` varchar(200) NOT NULL COMMENT 'Sessoin的id',
  `session` varchar(2000) DEFAULT NULL COMMENT 'Session的序列化对象',
  `username` varchar(32) DEFAULT NULL COMMENT '用户名',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
