<?php
namespace Home\Controller;
use Think\Controller;
class IndexController extends BaseController {
    public function index(){
		$assign = array(
			'seo' => array(
				'title' => '安安的购物经验录',
				'keywords' => '购物，经验，海淘，分享',
				'description' => '安安的购物经验录，获得自己商品的地方'
				)
		);

		/*获取热门帖子*/
		$post = D('Post');
		$postHotArr = $post->getHotPost();
		$this->assign('postHotArr',$postHotArr);
		//$this->display("index");


		$this->assign($assign)->display();
	}
}