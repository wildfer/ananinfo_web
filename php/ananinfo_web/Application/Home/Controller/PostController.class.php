<?php
namespace Home\Controller;
use Think\Controller;
class PostController extends BaseController {
	function index() {
		
		$this->display("index");
	}
	/*获取具体帖子*/
	function detail(){
		$postID = I('postid');		

		//帖子明细	
		$post = D('Post');
		$postDetail= $post->getPostByID($postID);
		$this->assign('postDetail',$postDetail);	

		//回复列表
		$comment=D('Comments');
		$commentArr= $comment->getCommentByPostID($postID);
		$this->assign('commentArr',$commentArr);	
	

		$this->display("detail");
	}
	
}