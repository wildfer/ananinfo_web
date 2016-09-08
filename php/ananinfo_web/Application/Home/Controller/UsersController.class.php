<?php
namespace Home\Controller;
use Think\Controller;
class UsersController extends BaseController {
	function index() {
		
		$this->display("index");
	}
	/*注册*/
	function register(){
		$user = D('Users');
		if ($user->getByUser()){
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

		//进入登陆状态
		//记录日志
	}
	/*登陆*/
	function login(){
		$verify_code = trim(I('verify_code'));
		if (!$this->check_verify($verify_code)){
			$data['status']  = false;
			$data['content'] = '错误的验证码'.$verify_code;
			$this->ajaxreturn($data);
			exit;
		}else{
			$data['status']  = true;
			$data['content'] = 'zzzz'.$verify_code;
			$this->ajaxreturn($data);
		}
		$this->display("index");
	}

	function verify(){
		$config =    array(
						'fontSize'    =>    50,    // 验证码字体大小 
						'length'      =>    4,     // 验证码位数
						'useNoise'    =>    false, // 关闭验证码杂点
						);
		$Verify = new \Think\Verify($config);
		$Verify->entry();
	}
	// 检测输入的验证码是否正确，$code为用户输入的验证码字符串
	function check_verify($code, $id = ''){
		$verify = new \Think\Verify();
		return $verify->check($code, $id);
	}
}