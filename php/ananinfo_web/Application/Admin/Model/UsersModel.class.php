<?php
 namespace Admin\Model;
/**
 * 会员服务类
 */
use Think\Model;
class UsersModel extends BaseModel {
		 /**
	  * 获取指定对象
	  */
     public function getByLogin(){
	 	$m = M('users');
	 	$condition= array();
	 	$condition['user_login'] = I('user_login');
		$condition['user_status'] = 0;
		return $m->where($condition)->find();
	 }

	/**
	  * 获取指定对象
	  */
     public function getByLoginAll(){
	 	$m = M('users');
	 	$condition= array();
	 	$condition['user_login'] = I('user_login');
		return $m->where($condition)->find();
	 }
	 /**
	  * 获取指定对象
	  */
     public function getByPhone(){
	 	$m = M('users');
	 	$condition= array();
	 	$condition['user_phone'] = I('user_phone');
		return $m->where($condition)->find();
	 }
    /**
	  * 新增
	  */
	 public function add($data){
	 	$m = M('users');
		return $m->add($data);
	 } 
	 /**
	  * 新增属性
	  */
	 public function addmeta($data){
	 	$m = M('usermeta');
		return $m->add($data);
	 }
	 /*返回管理员列表*/
	 public function getAdminArr(){
	 	return M()->query("SELECT a.`user_login`,a.`user_phone`,a.`user_nicename`,a.`user_email`,c.`title`,a.`user_registered` 
			FROM aai_users  a 
			INNER JOIN aai_usermeta b ON  b.meta_key = 'admin_group_admin' AND a.id = b.`user_id`
			INNER JOIN aai_admin_group c ON b.`meta_value` = c.`gid`");
		
	 }
     /**
	  * 修改
	  */
	 public function edit(){
	 	$rd = array('status'=>-1);
	 	$id = I('id',0);
	 	//检测账号
	 	if(I("userPhone")!=''){
	        $hasUserPhone = self::checkLoginKey(I("userPhone"),$id);
	        if($hasUserPhone['status']<=0){
		        $rd = array('status'=>-2);
		 		return $rd;
	        }
	 	}
	 	if(I("userEmail")!=''){
		 	$hasUserEmail = self::checkLoginKey(I("userEmail"),$id);
		 	if($hasUserEmail['status']<=0){
		 		$rd = array('status'=>-2);
		 		return $rd;
		 	}
		 	
	 	}
	 	//修改数据
		$m = M('users');
		$data = array();
		$data["userScore"] = I("userScore",0);
		$data["userTotalScore"] = I("userTotalScore",0);
		if($this->checkEmpty($data,true)){	
			$data["userName"] = I("userName");
			$data["userPhoto"] = I("userPhoto");
			$data["userSex"] = I("userSex",0);
		    $data["userQQ"] = I("userQQ");
		    $data["userPhone"] = I("userPhone");
		    $data["userEmail"] = I("userEmail");
			$rs = $m->where("userId=".$id)->save($data);
			if(false !== $rs){
				$rd['status']= 1;
				
			}
		}
		return $rd;
	 } 
	 

	
	  
	 /**
	  * 删除
	  */
	 public function del(){
	 	$rd = array('status'=>-1);
	 	$m = M('users');
	 	$m->userFlag = -1;
		$rs = $m->where(" userId=".I('id'))->save();
		if(false !== $rs){
		   $rd['status']= 1;
		}
		return $rd;
	 }
	 /**
	  * 查询登录关键字
	  */
	 public function checkLoginKey($val,$id = 0){
	 	$rd = array('status'=>-1);
	 	if($val=='')return $rd;
	 	$sql = " (loginName ='%s' or userPhone ='%s' or userEmail='%s') ";
	 	$keyArr = array($val,$val,$val);
	 	if($id>0){
	 		$sql.=" and userId!=".$id;
	 	}
	 	$m = M('users');
	 	$rs = $m->where($sql,$keyArr)->count();
	    if($rs==0)$rd['status'] = 1;
	    return $rd;
	 }
	 
	 
	 
	 /**
	  * 编辑账号状态
	  */
	 public function editUserStatus(){
	 	$rd = array('status'=>-1);
	 	if(I('id',0)==0)return $rd;
	 	$m = M('users');
	 	$m->userStatus = (I('userStatus')==1)?1:0;
	 	$rs = $m->where("userId=".I('id',0))->save();
	    if(false !== $rs){
			$rd['status']= 1;
		}
	 	return $rd;
	 }
	 
	 /**
	  * 修改账号信息
	  */
	 public function editAccount(){
	 	$rd = array('status'=>-1);
	 	if(I('id')=='')return $rd;
	 	$m = M('users');
	 	$loginSecret = $m->where("userId=".I('id'))->getField('loginSecret');
	 	if(I('loginPwd')!='')$m->loginPwd = md5(I('loginPwd').$loginSecret);
	 	$m->userStatus = I('userStatus',0);
	 	$rs = $m->where('userId='.I('id'))->save();
	    if(false !== $rs){
			$rd['status']= 1;
		}
		return $rd;
	 }
};
?>