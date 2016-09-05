<?php
 namespace Admin\Model;
/**
 * 会员服务类
 */
use Think\Model;
class PostModel extends BaseModel {
	/**
	  * 获取帖子
	  */
     public function getPost(){
	 	$m = M('post');
		return $m->order('id desc')->select();
	 }
	 /**
	  * 新增
	  */
	 public function add($data){
	 	$m = M('post');
		return $m->add($data);
	 } 
};
?>