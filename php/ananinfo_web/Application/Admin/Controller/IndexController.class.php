<?php
namespace Admin\Controller;
use Think\Controller;
class IndexController extends BaseController {
    public function index(){
    	/*调用三层服务
    	$member = D('Member','Service');
		$data['uid'] = $uid;
		$res = $member->test('updateMember_ex',$data);
		if($res->errorCode) {
		} else {
			$success = true;
		}
		echo $res->errorCode;
*/
		$this->assign($assign)->display("Index/index");
		// $this->assign($assign)->display();
	}

	//登录
	public function login(){
		$this->assign($assign)->display("Bootstrap-Admin-Theme-master/index");
		// $m = D('Users','Logic');
  //   	$rs = $m->login();
  //   	if($rs['status']==0){
  //   		cookie('auth',authcode($uid."\t".$login_password, 'ENCODE'),3600*8);

  //   		unset($rs['staff']);
  //   	}
    	//$this->ajaxReturn($rs);



  //   	} else {
		// 	$this->assign('ver_state',$ver_state);
		// 	$this->assign('message','');
		// 	$this->display("index/login");
		// }

	}
	//退出登录
	public function loginOut(){

	}
}