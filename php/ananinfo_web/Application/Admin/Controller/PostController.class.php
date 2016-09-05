<?php
namespace Admin\Controller;
use Think\Controller;
class PostController extends BaseController {
	function index() {
		$post = D('Post');
		$postArr = $post->getPost();
		$this->assign('postArr',$postArr);
		$this->display("index");
	}
	function initAdd(){
		$this->display("add");
	}
	/*新增帖子*/
	function add(){

		$post = D('Post');
		$postdata= array();
		$postdata['uid'] = 0;
		$postdata['img_cover'] = '';
		$postdata['title'] = I('title');
		$postdata['content'] = I('ckeditor_content');
		//$postdata['content'] = $_POST['ckeditor_content'];
		$postdata['status'] = 'publish';
		$postdata['sort'] = 0;
		$postdata['create_date'] = date('Y-m-d H:i:s',time());
		$postdata['update_date'] = date('Y-m-d H:i:s',time());
		$postdata['comment_status'] = 'open';
		$postdata['hot'] = I('hot');
		$postdata['describe'] = I('describe');
		
		/*标题图片提交*/
		// 实例化上传类
		$upload = new \Think\Upload();
		//设置文件类型
		$upload->exts=array('jpg', 'gif', 'png', 'jpeg');
		
		$info   =   $upload->upload();
		$imgUrl = '';
		if(!$info) {// 上传错误提示错误信息
		    $this->error($upload->getError());
		}else{  
		    foreach($info as $file){
		        $imgUrl= '/Uploads/'.$file['savepath'].$file['savename'];
		    }
		}
		$postdata['img_cover'] = $imgUrl;

		$post->add($postdata);

		$this->redirect("index", array('menuid'=>I('get.menuid')));
	}
	/*CKEditor上传文件*/
	function CKEditorUpload(){
		// 实例化上传类
		$upload = new \Think\Upload();
		$info   =   $upload->upload();

		if(!$info) {// 上传错误提示错误信息
			$msg = "上传失败";
		}else{
			foreach($info as $file){
		        $url=  '/Uploads/'.$file['savepath'].$file['savename'];
		    }
		    $msg = $url."上传成功";
		}
		$CKEditorFuncNum = $_GET['CKEditorFuncNum'];
		echo "<script type=\"text/javascript\">window.parent.CKEDITOR.tools.callFunction({$CKEditorFuncNum}, '{$url}', '{$msg}');</script>";
		exit();
	}
}