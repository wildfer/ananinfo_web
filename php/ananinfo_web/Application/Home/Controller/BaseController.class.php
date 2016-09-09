<?php
namespace Home\Controller;
use Think\Controller;
class BaseController extends Controller {
	public $userInfo = null;
	public $uid = null;
	public function _initialize(){
		$this->init_user();
	}
	//验证用户
	private function init_user() {
		$this->uid = cookie('uid');
		if ($this->uid) {
			$user = D('Users');
			$this->userInfo = $user->getByUserByID($this->uid);
		}
		$assign = array(
			'uid' => $this->uid,
			'userInfo' => $this->userInfo,
			);
		$this->assign($assign);
	}
}
