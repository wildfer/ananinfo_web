<?php
namespace Admin\Controller;
use Think\Controller;
class AdminController extends BaseController {
	function index() {
		$user = D('Users');
		$adminArr = $user->getAdminArr();
		$this->assign('adminArr',$adminArr);
		$this->display("index");
	}
	function initAdd(){

		$rule = D('Rules');
		/*获取权限列表*/
		$grouplist = $rule->getAdminGroup();
		$this->assign('grouplist',$grouplist);
		$this->display("add");
	}
	function add(){
		$user = D('Users');
		if ($user->getByLoginAll()){
			$this->error("用户已经存在");	
		}
		if ($user->getByPhone()){
			$this->error("号码已经被使用");	
		}
		/*插入用户表*/
		$userdata= array();
		$userdata['user_login'] = I('user_login');
		$userdata['user_phone'] = I('user_phone');
		$userdata['user_nicename'] = I('realname');
		$userdata['user_email'] = I('email');

		if(I('pass')) {
			$userdata['user_activation_key'] = substr(md5(I('pass') . time()), 5, 5);
			$userdata['user_pass'] = md5(I('pass') . '^' . $userdata['user_activation_key']);
		}
		$userdata['user_registered'] = date('Y-m-d H:i:s',time());
		
		$userinfo = $user->add($userdata);
		//插入用户附加属性
		$usermetadata = array();
		$usermetadata['user_id'] = $userinfo;
		$usermetadata['meta_key'] = 'admin_group_admin';//管理员属性
		$usermetadata['meta_value'] = I('groupgid');
		$user->addmeta($usermetadata);

		$adminArr = $user->getAdminArr();
		$this->assign('adminArr',$adminArr);
		$this->redirect("index", array('menuid'=>I('get.menuid')));
	}
	
}