<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>宅急送BOS主界面</title>
<!-- 导入jquery核心类库 -->
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/jquery-1.8.3.js"></script>
<!-- 导入easyui类库 -->
<link id="easyuiTheme" rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/js/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/js/easyui/themes/icon.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/css/default.css">
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/easyui/jquery.easyui.min.js"></script>
<!-- 导入ztree类库 -->
<link rel="stylesheet"
	href="${pageContext.request.contextPath }/js/ztree/zTreeStyle.css"
	type="text/css" />
<script
	src="${pageContext.request.contextPath }/js/ztree/jquery.ztree.all-3.5.js"
	type="text/javascript"></script>
<script
	src="${pageContext.request.contextPath }/js/easyui/locale/easyui-lang-zh_CN.js"
	type="text/javascript"></script>
<script type="text/javascript">
	// 初始化ztree菜单
	$(function() {
		var setting = {
			data : {
				simpleData : { // 简单数据 
					enable : true
				}
			},
			callback : {
				onClick : onClick
			}
		};
		
		// 基本功能菜单加载
		$.ajax({
			url : '${pageContext.request.contextPath}/json/menu.json',
			type : 'POST',
			dataType : 'text',
			success : function(data) {
				var zNodes = eval("(" + data + ")");
				$.fn.zTree.init($("#treeMenu"), setting, zNodes);
			},
			error : function(msg) {
				alert('菜单加载异常!');
			}
		});
		
		// 系统管理菜单加载
		$.ajax({
			url : '${pageContext.request.contextPath}/json/admin.json',
			type : 'POST',
			dataType : 'text',
			success : function(data) {
				var zNodes = eval("(" + data + ")");
				$.fn.zTree.init($("#adminMenu"), setting, zNodes);
			},
			error : function(msg) {
				alert('菜单加载异常!');
			}
		});
		
		// 页面加载后 右下角 弹出窗口
		/**************/
		window.setTimeout(function(){
			$.messager.show({
				title:"消息提示",
				msg:'欢迎登录，超级管理员！ <a href="javascript:void" onclick="top.showAbout();">联系管理员</a>',
				timeout:5000
			});
		},3000);
		/*************/
		
		$("#btnCancel").click(function(){
			$('#editPwdWindow').window('close');
		});
		
		$("#btnEp").click(function(){
			alert("修改密码");
		});
	});

	function onClick(event, treeId, treeNode, clickFlag) {
		// 判断树菜单节点是否含有 page属性
		if (treeNode.page!=undefined && treeNode.page!= "") {
			if ($("#tabs").tabs('exists', treeNode.name)) {// 判断tab是否存在
				$('#tabs').tabs('select', treeNode.name); // 切换tab
			} else {
				// 开启一个新的tab页面
				var content = '<div style="width:100%;height:100%;overflow:hidden;">'
						+ '<iframe src="'
						+ treeNode.page
						+ '" scrolling="auto" style="width:100%;height:100%;border:0;" ></iframe></div>';

				$('#tabs').tabs('add', {
					title : treeNode.name,
					content : content,
					closable : true
				});
			}
		}
	}

	/*******顶部特效 *******/
	/**
	 * 更换EasyUI主题的方法
	 * @param themeName
	 * 主题名称
	 */
	changeTheme = function(themeName) {
		var $easyuiTheme = $('#easyuiTheme');
		var url = $easyuiTheme.attr('href');
		var href = url.substring(0, url.indexOf('themes')) + 'themes/'
				+ themeName + '/easyui.css';
		$easyuiTheme.attr('href', href);
		var $iframe = $('iframe');
		if ($iframe.length > 0) {
			for ( var i = 0; i < $iframe.length; i++) {
				var ifr = $iframe[i];
				$(ifr).contents().find('#easyuiTheme').attr('href', href);
			}
		}
	};
	// 退出登录
	function logoutFun() {
		$.messager
		.confirm('系统提示','您确定要退出本次登录吗?',function(isConfirm) {
			if (isConfirm) {
				location.href = '${pageContext.request.contextPath }/login.jsp';
			}
		});
	}
	// 修改密码
	function editPassword() {
		$('#editPwdWindow').window('open');
	}
	// 版权信息
	function showAbout(){
		$.messager.alert("宅急送 v1.0","管理员邮箱: zqx@itcast.cn");
	}
</script>
</head>
<body class="easyui-layout">
	<!-- 使用div指定区域 -->
	<div title="XX管理系统" data-options="region:'north'" style="height: 100px">北部区域</div>
	<div title="系统菜单" data-options="region:'west'" style="width: 200px">
		西部区域
		<!-- 折叠面板效果 -->
		<div class="easyui-accordion" data-options="fit:true">
			<!-- 每个子div是其中的一个面板 -->
			<div title="面板一">
				棉衣一
				<a href="#" onclick="addTab()">添加</a>
				<a href="#" onclick="removeTab()">删除</a>
			</div>
			<div title="面板二">test2
<ul id="ztree3" class="ztree"></ul>

			</div>
			<div title="面板三">
			<a data-options="iconCls:'icon-help',menu:'#mm'" class="easyui-menubutton">控制面板</a>
			<!-- 使用div制作下拉菜单选项 -->
	<div id="mm">
		<!-- 使用子div制作具体的一个选项 -->
		<div onclick="alert(111)" data-options="iconCls:'icon-edit'">修改密码</div>
		<div>联系管理员</div>
		<div class="menu-sep"></div>
		<div>退出系统</div>
	</div>test3</div>
		</div>
	</div>
	<div data-options="region:'center'">
	
		中心区域
		<!-- 选项卡面板效果 -->
		<div id="tt" class="easyui-tabs" data-options="fit:true">
			<!-- 每个子div是其中的一个面板 -->
			<div data-options="closable:true,iconCls:'icon-help'" title="面板一">棉衣一</div>
			<div title="面板二">test2</div>
			<div title="面板三">test3</div>
		</div>
		<script type="text/javascript">
			function addTab(){
				$("#tt").tabs("add",{title:'这个可是动态的',
					content:'<iframe frameborder="0" width="100%" height="100%" src="page_base_staff.action"></iframe>',
					closable:true,
					iconCls:'icon-search'
				})
			}
			function removeTab(){
				$("#tt").tabs("remove");
			}
		</script>
	</div>
	<div data-options="region:'east'" style="width: 100px">东部区域</div>
	<div data-options="region:'south'" style="height: 50px">南部区域</div>
	
	
	

<script type="text/javascript">
	$(function(){
		var setting3 = {
				data : {
					simpleData : {
						enable : true
					//启用简单json数据描述节点数据 
					}
				},
				callback: {//绑定事件 
					onClick: function(a,b,treeNode){
						var page = treeNode.page;
						if(page != undefined){//需要打开选项卡
							//判断当前选项卡是否已经打开
							var e = $("#tt").tabs("exists",treeNode.name);
							if(e){
								//已经打开
								$("#tt").tabs("select",treeNode.name);
							}else{
								$("#tt")
								.tabs(
										"add",
										{
											title : treeNode.name,
											content : '<iframe frameborder="0" width="100%" height="100%" src="'+page+'"></iframe>',
											closable : true,
											iconCls : 'icon-edit'
										});
							}
						}
					}
				}
			};//设置ztree相关的属性
			var setting2 = {
					data : {
						simpleData : {
							enable : true
						//启用简单json数据描述节点数据 
						}
					},
					callback: {//绑定事件 
						onClick: function(a,b,treeNode){
							var page = treeNode.page;
							if(page != undefined){//需要打开选项卡
								//判断当前选项卡是否已经打开
								var e = $("#tt").tabs("exists",treeNode.name);
								if(e){
									//已经打开
									$("#tt").tabs("select",treeNode.name);
								}else{
									$("#tt")
									.tabs(
											"add",
											{
												title : treeNode.name,
												content : '<iframe frameborder="0" width="100%" height="100%" src="'+page+'"></iframe>',
												closable : true,
												iconCls : 'icon-edit'
											});
								}
							}
						}
					}
				};//设置ztree相关的属性

	    //发送ajax请求获取json数据构造ztree
	    var url = "${pageContext.request.contextPath}/json/menu.json";
		$.post(url,{},function(data){
			//创建ztree
			$.fn.zTree.init($("#ztree3"), setting3, data);
		},'json');
	});

	
	
	
	   $(function () {
		    $('#box').progressbar({
		     value : '40',
		     onChange : function (newValue, oldValue) {
		      console.log('新:' + newValue + ',旧:' + oldValue);
		     },
		    });

		    // 返回属性对象
		    console.log($('#box').progressbar('options'));
		    // 设置组件大小(宽度)
		    $('#box').progressbar('resize','500');
		    // 返回当前进度值
		    console.log($('#box').progressbar('getValue'));
		    // 设置一个新的进度值
		    $('#box').progressbar('setValue','50');
		    // 可以使用$.fn.progressbar.defaults 重写默认值对象。
		    $.fn.progressbar.defaults.value = '60';
		   });
</script>
</body>
</html>