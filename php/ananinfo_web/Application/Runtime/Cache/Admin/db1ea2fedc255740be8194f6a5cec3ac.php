<?php if (!defined('THINK_PATH')) exit();?><html>
    <head>
    <title>Admin Home Page</title>
        <!-- Bootstrap -->
        <link href="/Application/Static/Public/bootstrap/css/bootstrap.min.css" rel="stylesheet" media="screen">
        <link href="/Application/Static/Public/bootstrap/css/bootstrap-responsive.min.css" rel="stylesheet" media="screen">
        <link href="/Application/Static/Public/vendors/easypiechart/jquery.easy-pie-chart.css" rel="stylesheet" media="screen">
        <link href="/Application/Static/Public/assets/styles.css" rel="stylesheet" media="screen">
        <!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
        <!--[if lt IE 9]>
            <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
        <![endif]-->
        <script src="/Application/Static/Public/vendors/modernizr-2.6.2-respond-1.1.0.min.js"></script>
        <script src="/Application/Static/Public/vendors/jquery-1.9.1.min.js"></script>

        <script src="/Application/Static/Public/bootstrap/js/bootstrap.min.js"></script>
        <script src="/Application/Static/Public/vendors/easypiechart/jquery.easy-pie-chart.js"></script>
        <script src="/Application/Static/Public/assets/scripts.js"></script>




         <!-- Bootstrap -->
        <link rel="stylesheet" type="text/css" href="/Application/Static/Public/vendors/bootstrap-wysihtml5/src/bootstrap-wysihtml5.css"></link>
        <script src="/Application/Static/Public/vendors/modernizr-2.6.2-respond-1.1.0.min.js"></script>
         <script src="/Application/Static/Public/vendors/bootstrap-wysihtml5/lib/js/wysihtml5-0.3.0.js"></script>
        <script src="/Application/Static/Public/vendors/bootstrap-wysihtml5/src/bootstrap-wysihtml5.js"></script>
        <script src="/Application/Static/Public/vendors/ckeditor/ckeditor.js"></script>
        <script src="/Application/Static/Public/vendors/ckeditor/adapters/jquery.js"></script>
        <script type="text/javascript" src="/Application/Static/Public/vendors/tinymce/js/tinymce/tinymce.min.js"></script>


        <link href="/Application/Static/Public/vendors/datepicker.css" rel="stylesheet" media="screen">
        <link href="/Application/Static/Public/vendors/uniform.default.css" rel="stylesheet" media="screen">
        <link href="/Application/Static/Public/vendors/chosen.min.css" rel="stylesheet" media="screen">
        <link href="/Application/Static/Public/vendors/wysiwyg/bootstrap-wysihtml5.css" rel="stylesheet" media="screen">
        <script src="/Application/Static/Public/vendors/jquery.uniform.min.js"></script>
        <script src="/Application/Static/Public/vendors/chosen.jquery.min.js"></script>
        <script src="/Application/Static/Public/vendors/bootstrap-datepicker.js"></script>
        <script src="/Application/Static/Public/vendors/wysiwyg/wysihtml5-0.3.0.js"></script>
        <script src="/Application/Static/Public/vendors/wysiwyg/bootstrap-wysihtml5.js"></script>
        <script src="/Application/Static/Public/vendors/wizard/jquery.bootstrap.wizard.min.js"></script>
        <script type="text/javascript" src="/Application/Static/Public/vendors/jquery-validation/dist/jquery.validate.min.js"></script>
        <script src="/Application/Static/Public/assets/form-validation.js"></script>


    </head>
    
    <body>
        <div class="navbar navbar-fixed-top">
            <div class="navbar-inner">
                <div class="container-fluid">
                    <a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse"> <span class="icon-bar"></span>
                     <span class="icon-bar"></span>
                     <span class="icon-bar"></span>
                    </a>
                    <a class="brand" href="#">登陆用户</a>
                    <div class="nav-collapse collapse">
                        <ul class="nav pull-right">
                            <li class="dropdown">
                                <a href="#" role="button" class="dropdown-toggle" data-toggle="dropdown"> <i class="icon-user"></i> Vincent Gabriel <i class="caret"></i>

                                </a>
                                <ul class="dropdown-menu">
                                    <li>
                                        <a tabindex="-1" href="#">Profile</a>
                                    </li>
                                    <li class="divider"></li>
                                    <li>
                                        <a tabindex="-1" href="#">Logout</a>
                                    </li>
                                </ul>
                            </li>
                        </ul>
                        <ul class="nav">
                            <li class="active">
                                    <a href="#">开始</a>
                            </li>
                            <?php if(is_array($rules)): $i = 0; $__LIST__ = $rules;if( count($__LIST__)==0 ) : echo "" ;else: foreach($__LIST__ as $key=>$vo): $mod = ($i % 2 );++$i;?><li class="dropdown">
                                    <a href="<?php echo (getNavUrl($vo)); ?>"  id="<?php echo ($vo["id"]); ?>"><?php echo ($vo["title"]); ?>
                                    </a>
                                </li><?php endforeach; endif; else: echo "" ;endif; ?>
                        </ul>
                    </div>
                    <!--/.nav-collapse -->
                </div>
            </div>
        </div>
<?php if(!empty($leftPid)): ?><div class="span3" id="sidebar">
    <ul class="nav nav-list bs-docs-sidenav nav-collapse collapse">
        <?php if(is_array($rules)): $i = 0; $__LIST__ = $rules;if( count($__LIST__)==0 ) : echo "" ;else: foreach($__LIST__ as $key=>$v): $mod = ($i % 2 );++$i; if($v["id"] == $leftPid): if(is_array($v["children"])): $i = 0; $__LIST__ = $v["children"];if( count($__LIST__)==0 ) : echo "" ;else: foreach($__LIST__ as $key=>$vv): $mod = ($i % 2 );++$i; if($vv["id"] == $menuid): ?><li class="active">
		                	<a href=<?php echo getNavUrl($vv);?>><i class="icon-chevron-right"></i><?php echo ($vv["title"]); ?></a>
		            	</li>
		            <?php else: ?>
						<li>
		                	<a href=<?php echo getNavUrl($vv);?>><i class="icon-chevron-right"></i><?php echo ($vv["title"]); ?></a>
		            	</li><?php endif; endforeach; endif; else: echo "" ;endif; endif; endforeach; endif; else: echo "" ;endif; ?>

        <li>
            <a href="calendar.html"><i class="icon-chevron-right"></i> test2</a>
        </li>
        <li>
            <a href="#"><span class="badge badge-success pull-right">731</span> Orders</a>
        </li>
    </ul>
</div><?php endif; ?>
<div class="container-fluid">
<div class="row-fluid">
	<div class="span9" id="content">
	 <!-- validation -->
		<div class="row-fluid">
		     <!-- block -->
		    <div class="block">
		        <div class="navbar navbar-inner block-header">
		            <div class="muted pull-left">增加帖子内容</div>
		        </div>
		        <div class="block-content collapse in">
		            <div class="span12">
						<!-- BEGIN FORM-->
						<form action="/admin.php?c=Post&a=add&menuid=<?php echo ($menuid); ?>" id="form_sample_1" class="form-horizontal" 
						enctype="multipart/form-data"  method="post">
							<fieldset>
								<div class="control-group">
									<label class="control-label">标题<span class="required">*</span></label>
									<div class="controls">
										<input type="text" name="title" data-required="1" class="span6 m-wrap"/>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label">封面图片<span class="required">*</span></label>
									<div class="controls">
										<input type="file" name="img_cover" data-required="1" class="span6 m-wrap"/>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label">是否热门</label>
									<div class="controls">
										<label class="radio-inline">
											<input type="radio" name="hot" id="hot" value="1" checked>热门 
											<input type="radio" name="hot" id="hot" value="2" >非热门
										</label>
									</div>
								</div>
								<div class="control-group">
                                          <label class="control-label" for="textarea2">描述</label>
                                          <div class="controls">
                                            <textarea  name = 'describe' placeholder="Enter text ..." style="width: 810px; height: 100px"></textarea>
                                          </div>
                                        </div>	
								</fieldset>

						   <div class="block">
						        <div class="navbar navbar-inner block-header">
						            <div class="muted pull-left">内容编辑(CKEditor)</div>
						        </div>
						        <div class="block-content collapse in">
						           <textarea name = "ckeditor_content" id="ckeditor_content"></textarea>
						        </div>
						    </div>
						    <div class="form-actions">
								<button type="submit" class="btn btn-primary">提交</button>
								<a type="button" class="btn" href="/?c=Post&a=index&menuid=<?php echo ($menuid); ?>">取消</a>
							</div>
						</form>
						<!-- END FORM-->
					</div>
				</div>
			</div>
		 	<!-- /block -->
			
		</div>
	</div>
</div>
<script>
	// jQuery(document).ready(function() {   
	// 	   FormValidation.init();
	// 	});
	$(function() {
		$('.textarea').wysihtml5();
	    $( 'textarea#ckeditor_content' ).ckeditor({width:'98%', height: '600px',
	    	//filebrowserImageUploadUrl:'/admin.php?c=Post&a=CKEditorUpload'});
	    	filebrowserImageUploadUrl:"<?php echo getNavUrlEx('Post','CKEditorUpload','');?>"});
	});
</script>


    </body>
</html>