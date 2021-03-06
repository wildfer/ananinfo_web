<?php
namespace Home\Model;
/**
 * 会员服务类
 */
class UsersModel extends BaseModel {
	
	/**
	  * 获取用户
	  */
     public function getByUser($user=0){
	 	$m = M('users');
	 	$condition= array();
	 	$condition['user_login'] = $user;
		return $m->where($condition)->find();
	 }
	 /**
	  * 获取用户
	  */
     public function getByPhone(){
	 	$m = M('users');
	 	$condition= array();
	 	$condition['user_phone'] = I('user_phone');
		return $m->where($condition)->find();
	 }
	 /**
	  * 获取用户
	  */
     public function getByUserByID($userID=0){
	 	$m = M('users');
	 	$condition= array();
	 	$condition['id'] = $userID;
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
	  * 查询登录名是否存在
	  */
	 public function checkLoginKey($loginName,$id = 0){
	 	$loginName = ($loginName!='')?$loginName:I('loginName');
	 	$rd = array('status'=>-1);
	 	if($loginName=='')return $rd;
	 	$sql = " (loginName ='%s' or userPhone ='%s' or userEmail='%s') ";
	 	$m = M('users');
	    if($id>0){
	 		$sql.=" and userId!=".$id;
	 	}
	 	$rs = $m->where($sql,array($loginName,$loginName,$loginName))->count();
	    if($rs==0)$rd['status'] = 1;
	    return $rd;
	 }
	 
	 /**
	  * 查询并加载用户资料
	  */
	 public function checkAndGetLoginInfo($key){
	 	if($key=='')return array();
	 	$sql = " (loginName ='%s' or userPhone ='%s' or userEmail='%s') and userFlag=1 and userStatus=1 ";
	 	$keyArr = array($key,$key,$key);
	 	$m = M('users');
	 	$rs = $m->where($sql,$keyArr)->find();
	    return $rs;
	 }
	 
    /**
	 * 用户登录验证
	 */
	public function checkLogin(){
		$rv = array('status'=>-1);
		$loginName = I('loginName');
		$userPwd = I('loginPwd');
		$rememberPwd = I('rememberPwd');
		$sql ="SELECT * FROM __PREFIX__users WHERE (loginName='".$loginName."' OR userEmail='".$loginName."' OR userPhone='".$loginName."') AND userFlag=1 and userStatus=1 ";
		$rss = $this->query($sql);
		if(!empty($rss)){
			$rs = $rss[0];
			if($rs['loginPwd']!=md5($userPwd.$rs['loginSecret']))return $rv;
			if($rs['userFlag'] == 1 && $rs['userStatus']==1){
				$data = array();
				$data['lastTime'] = date('Y-m-d H:i:s');
				$data['lastIP'] = get_client_ip();
				$m = M('users');
		    	$m->where(" userId=".$rs['userId'])->data($data)->save();
		    	//如果是店铺则加载店铺信息
		    	if($rs['userType']>=1){
		    		$s = M('shops');
			 		  $shops = $s->where('userId='.$rs['userId']." and shopFlag=1")->find();
			 		  if(!empty($shops))$rs = array_merge($shops,$rs);
		    	}
		    	//记录登录日志
				$data = array();
				$data["userId"] = $rs['userId'];
				$data["loginTime"] = date('Y-m-d H:i:s');
				$data["loginIp"] = get_client_ip();
				M('log_user_logins')->add($data);
			}
			$rv = $rs;
			setcookie("loginName", $loginName, time()+3600*24*60);
			if($rememberPwd == "on"){			
				setcookie("loginPwd", $userPwd, time()+3600*24*60);
			}else{		
				setcookie("loginPwd", "", time()-3600);
			}
		}
		return $rv;
	}
	
	 
	/**
	 * 随机生成一个账号
	 */
	public function randomLoginName($loginName){
		$chars = array("a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z");
		//简单的派字母
		foreach ($chars as $key =>$c){
			$crs = $this->checkLoginKey($loginName."_".$c);
			if($crs['status']==1)return $loginName."_".$c;
		}
		//随机派三位数值
		for($i=0;$i<1000;$i++){
			$crs = $this->checkLoginKey($loginName."_".$i);
			if($crs['status']==1)return $loginName."_".$i;
		}
		return '';
	}
	
	/**
	 * 修改用户密码
	 */
	public function editPass($id){
		$rd = array('status'=>-1);
		$m = M('users');
		$data = array();
		$data["loginPwd"] = I("newPass");
		if($this->checkEmpty($data,true)){
			$rs = $m->where('userId='.$id)->find();
			//核对密码
			if($rs['loginPwd']==md5(I("oldPass").$rs['loginSecret'])){
				$data["loginPwd"] = md5(I("newPass").$rs['loginSecret']);
				$rs = $m->where("userId=".$id)->save($data);
				if(false !== $rs){
					$rd['status']= 1;
				}
			}else{
				$rd['status']= -2;
			}
		}
		return $rd;
	}
	
	/**
	 * 修改用户资料
	 */
	public function editUser($obj){
		$rd = array('status'=>-1);
		$userPhone = I("userPhone");
		$userEmail = I("userEmail");
		$userId = $obj["userId"];
	    //检测账号是否存在
        $crs = $this->checkLoginKey($userPhone,$userId);
        if($crs['status']!=1){
	    	$rd['status'] = -2;
	    	return $rd;
	    }
	    //检测邮箱是否存在
        $crs = $this->checkLoginKey($userEmail,$userId);
        if($crs['status']!=1){
	    	$rd['status'] = -3;
	    	return $rd;
	    }
		$m = M('users');
		$data = array();
		
		$data["userName"] = I("userName");
		$data["userQQ"] = I("userQQ");
		$data["userPhone"] = $userPhone;
		$data["userSex"] = I("userSex",0);
		$data["userEmail"] = $userEmail;
		$data["userPhoto"] = I("userPhoto");
		$rs = $m->where(" userId=".$userId)->data($data)->save();
	    if(false !== $rs){
			$rd['status']= 1;
			session('WST_USER.userName',$data["userName"]);
			session('WST_USER.userQQ',$data["userQQ"]);
			session('WST_USER.userSex',$data["userSex"]);
			session('WST_USER.userPhone',$data["userPhone"]);
			session('WST_USER.userEmail',$data["userEmail"]);
			session('WST_USER.userPhoto',$data["userPhoto"]);
		}
		return $rd;
	}
	
	/**
	 * 重置用户密码
	 */
	public function resetPass(){
		$rs = array('status'=>-1);
    	$reset_userId = (int)session('REST_userId');
    	if($reset_userId==0){
    		$rs['msg'] = '无效的用户！';
    		return $rs;
    	}
    	$m = M('Users');
    	$user = $m->where("userId=".$reset_userId." and userFlag=1 and userStatus=1")->find();
    	if(empty($user)){
    		$rs['msg'] = '无效的用户！';
    		return $rs;
    	}
    	$loginPwd = I('loginPwd');
    	if(trim($loginPwd)==''){
    		$rs['msg'] = '无效的密码！';
    		return $rs;
    	}
    	$data['loginPwd'] = md5($loginPwd.$user["loginSecret"]);
    	$rc = $m->where("userId=".$reset_userId)->save($data);
    	if(false !== $rc){
    	    $rs['status'] =1;
    	}
    	session('REST_userId',null);
    	session('REST_Time',null);
    	session('REST_success',null);
    	session('findPass',null);
    	return $rs;
	}
}