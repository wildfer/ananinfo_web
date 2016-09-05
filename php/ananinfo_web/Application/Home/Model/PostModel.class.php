<?php
 namespace Home\Model;
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
	  * 获取热门帖子
	  */
     public function getHotPost(){
	 	$m = M('post');
	 	$condition['hot'] = 1;
	 	$condition['status'] = 'publish';//已经发布
		return $m->where($condition)->order('id desc')->select();
	 }
	 /**
	  * 获取帖子
	  */
     public function getPostByID($postID){
	 	$m = M('post');
	 	$condition['id'] = $postID;
	 	$condition['status'] = 'publish';//已经发布
		return $m->where($condition)->find();
	 }
};
?>