<?php
return array(
	'DB_TYPE'   => 'mysql', // 数据库类型
	'DB_HOST'   => '114.55.28.52', // 服务器地址
	'DB_NAME'   => 'ananinfo', // 数据库名
	'DB_USER'   => 'ananinfo', // 用户名
	'DB_PWD'    => 'wildfer.ananinfo', // 密码
	'DB_PORT'   => 3306, // 端口
	'DB_PREFIX' => 'aai_', // 数据库表前缀 
	'DB_CHARSET'=> 'utf8', // 字符集
	'DB_DEBUG'  =>  TRUE, // 数据库调试模式 开启后可以记录SQL日志 3.2.3新增

	'URL_MODEL' => 0,
	 
	'UPLOAD_PIC' =>array(
		'__USER__' => '',
		'__PASS__' => '',
		'__PATH__' => '',
		),
	'TMPL_PARSE_STRING'=>array(
			'__MAIN_HOST__'=>'http://www.55shantao.cm',
			'__WAP_HOST__'=>'http://m.55shantao.cm',
			//'__BootstrapAdminTheme__' => '/Application/Home/View/Bootstrap-Admin-Theme-master',
			'__MYBoots__' => '/Application/Static/Public', 
			//'__IMG__' => 'Application/Static/Home/images',
			//'__JS__' => 'Application/Static/Home/js',
			//'__JQUERY__' => 'Application/Static/Public/js/jquery/1.11.3/jquery.js',
	),
	//I方法过滤
	'DEFAULT_FILTER' => '', 
);