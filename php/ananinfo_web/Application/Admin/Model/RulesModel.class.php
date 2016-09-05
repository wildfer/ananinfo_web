<?php
 namespace Admin\Model;
/**
 * 用户权限类
 */
use Think\Model;
class RulesModel  {
  	/*获取菜单规则*/
	public function getrules($parentId){
		$m = M('admin_rules');
		$condition['pid'] = $parentId;
		$condition['status'] = 1;
		return $m->where($condition)->order('orderid ')->select();
	}
	/*获取主菜单ID*/
	public function getTopNavID( $menuid )
	{
		$m = M('admin_rules');
		$condition['id'] = $menuid;
		$condition['status'] = 1;
		$navDetail = $m->where($condition)->order('orderid ')->find();
		if ( $navDetail['pid']==0 ) {

			krsort($this->nav);
			$this->nav = array_values($this->nav);
			return $navDetail;
		}else{
			return $this->getTopNavID( $navDetail['pid'] );
		}
	}
	/*获取权限列表*/
	public function getAdminGroup(){
		$m = M('admin_group');
		return $m->select();
	}
};
?>