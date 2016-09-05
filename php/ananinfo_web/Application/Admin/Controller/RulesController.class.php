<?php
namespace Admin\Controller;
use Think\Controller;
class RulesController extends BaseController {
	function index() {
		$this->display("index");
	}
	/*主菜单*/
	function doWork(){
		// $this->assign('leftPid',I('get.menuid'));
		$this->display("Rules/index");
	}
	/*子菜单*/
	function detail(){
		/*获取顶级菜单*/
		// $rule = D('Rules');
		// $this->assign('leftPid',$rule->getTopNavID(I('get.menuid'))['id']);
		// $this->assign('menuid',I('get.menuid'));
		$this->display("Rules/index");
	}
}