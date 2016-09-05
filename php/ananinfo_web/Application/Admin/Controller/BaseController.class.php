<?php
namespace Admin\Controller;
use Think\Controller;
class BaseController extends Controller {
	public $memberInfo = null;
	public $uid = null;
	public $order_status = array();
	public function _initialize(){
		// var_dump(ACTION_NAME);
		// if (ACTION_NAME !='login') {
		// 	$this->init_user();
		// }
		
		// $this->before_validate();
		  $this->initRules();
		  $this->initAllrules();
		// $this->_init_operation();
		// $this->_init_setting();
	}
	//验证用户
	private function init_user() {
		$this->redirect('index/login');
		return;
// 		if($auth = cookie('aai_auth')) {
// 			// $auth = explode("\t", authcode($auth, 'DECODE'));
// 			// list($uid, $pwd) = empty($auth) || count($auth) < 2 ? array('', '') : $auth;
// 			// if($uid) {
// 			// 	$user = DB::fetch_first("select * from 55ht_admin_member where uid=:uid", array(':uid'=>$uid));
// 			// 	C::S('uid', $user['uid']);
// 			// 	C::S('realname',$user['realname']);
// 			// 	C::S('username',$user['username']);
// 			// 	C::S('email',$user['email']);
// 			// 	//C::S('admin/status', $user['status']);
// 			// 	//C::S('admin/media_id', $user['media_id']);
// 			// }
			
// 		}
// 		//$this->uid = cookie('uid');
// 		if (!$this->uid) {
// 			echo "ddddd";
// 			$this->redirect('login');
// 		}
	}
	/*初始化所有菜单*/
	private function initAllrules(){
		$rule = D('Rules');
		$rules = $rule->getrules(0);

		foreach ($rules as $k => $v) {
			$chilrenLV_1 =  $rule->getrules($v['id']);
			if ( $chilrenLV_1 ) {
				foreach ($chilrenLV_1 as $kk => $vv) {
					$chilrenLV_2 = $rule->getrules($vv['id']);
					if ($chilrenLV_2) {
						foreach ($chilrenLV_2 as $kkk => $vvv) {
							$chilrenLV_3 = $rule->getrules($vvv['id']);
							$chilrenLV_2[$kkk]['children'] = $chilrenLV_3;
						}
					}
					$chilrenLV_1[$kk]['children'] = $chilrenLV_2;
				}
			}
			$rules[$k]['children'] = $chilrenLV_1;
		}
		$this->assign('rules',$rules);
	}
	/*组装目前使用的菜单*/
	private function initRules(){
		$rule = D('Rules');
		$this->assign('leftPid',$rule->getTopNavID(I('get.menuid'))['id']);	
		$this->assign('menuid',I('get.menuid'));
	}

	
	

}
