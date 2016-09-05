<?php
 namespace Home\Model;
/**
 * 会员服务类
 */
use Think\Model;
class CommentsModel extends BaseModel {
	/**
	  * 获取跟帖帖子
	  */
     public function getCommentByPostID($postID){
	 	$m = M('comments');
	 	$condition['comment_post_id'] = $postID;
	 	$condition['status'] = 'publish';//已经发布
		return $m->where($condition)->order('comment_id desc ')->select();
	 }
	 
};
?>