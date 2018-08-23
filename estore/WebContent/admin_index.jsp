<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="root" value="${pageContext.request.contextPath}"/>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>卖家功能首页</title>
     <link rel="stylesheet" type="text/css" href="easyui/themes/default/easyui.css">
	 <link rel="stylesheet" type="text/css" href="easyui/themes/icon.css">
	 <script type="text/javascript" src="easyui/jquery.min.js">
	 </script>
	 <script type="text/javascript" src="easyui/jquery.easyui.min.js">
	 </script>
  </head>
  
<body class="easyui-layout">

    <div  id= "estore商城后台管理系统" data-options="region:'north',title:'North Title',split:true" style="height:100px;">
    </div>
    
    <div data-options="region:'south',title:'South Title',split:true" style="height:100px;"></div>
    <div data-options="region:'west',title:'West',split:true" style="width:200px;">
    <!-- 树结构 -->
  	 	    	<ul id="tt" class="easyui-tree">
			    <li>
					<span>后台管理</span>
					<ul>
						<li><span>商品管理</span>
							<ul>
								<li><span>查询商品</span></li>
								<li><span>商品添加</span></li>
								<li><span>热门查询</span></li>
							</ul>
						</li>
						<li>
							<span>类型管理</span>
							<ul>
								<li><span>查询类型</span></li>
							</ul>
						</li>
					</ul>
				</li>
			</ul>
    </div>
    <div data-options="region:'center',title:'center title'" style="padding:5px;background:#eee;">
    <!-- 增加一个tabs -->
	    <div id="tabs" class="easyui-tabs" data-options="fit:true"  >
	</div>
    </div>
</body>
<script type="text/javascript">
	// 树的点击事件
	$('#tt').tree({
	onClick: function(node){
		// 添加选项卡  需要进行判断  查看是否已经存在该选项卡  
			var res = $('#tabs').tabs('exists',node.text);
			if(res){
				// 存在  选中选项卡
				$('#tabs').tabs('select',node.text);
			}else {
				// 不存在  添加该选项卡
					if(node.text=="商品查询"){
					}
					$('#tabs').tabs('add',{
					    title:node.text,
					    content:'<table id="dg"></table>',
					    closable:true,
					    tools:[{
							iconCls:'icon-mini-refresh',
							handler:function(){
								
							}
					    }]
					});
					// 添加热门商品查询选项卡结束-----------
					
					if(node.text=="热门查询"){
					}
					$('#tabs').tabs('add',{
					    title:node.text,
					    content:'<table id="rm"></table>',
					    closable:true,
					    tools:[{
							iconCls:'icon-mini-refresh',
							handler:function(){
								
							}
					    }]
					});
			}
			
			// 添加选项卡结束-----------
			/*
				给选项卡添加数据网格
			*/
			$('#dg').datagrid({
					fit:true,
	  	  	  		fitColumns:true,
	  	  	  		rownumbers:true,
	  	  	  		pagination:true,
			    url:'${root}/goodServlet?methodName=findAllGoodsByJson',
			    columns:[[
						{field:'name',title:'商品名',width:100},
	 					{field:'marketprice',title:'商城价',width:100},
	 					{field:'estoreprice',title:'本店价',width:100},
	 					{field:'category',title:'商品类型',width:100},
	 					{field:'num',title:'商品数量',width:100},
	 					{field:'imgurl',title:'图片',width:100,
	 						formatter: function(value,row,index){
 								return "<img src='/imgServer"+value+"' width='50px'>";
 						}
	 					},
	 					
	 					{field:'description',title:'商品描述'}
			    ]]
			});
			/*
			给选项卡添加数据网格
			*/
			$('#rm').datagrid({
				fit:true,
  	  	  		fitColumns:true,
  	  	  		rownumbers:true,
  	  	  		pagination:true,
		    url:'${root}/goodServlet?methodName=findAllGoodsByRm',
		    columns:[[
					{field:'gid',title:'商品编号',width:100},
 					{field:'name',title:'商品名称',width:100},
 					{field:'hot',title:'卖出数量'}
		    ]]
		});
			//----------------
			
		// 分页结束------------- 添加商品开始
				if(node.text=="商品添加"){
  					//创建商品添加的标签页
  					$('#tabs').tabs('add',{
  	  	  			    title:node.text,
  	  	  				href:"add_good.jsp",
  	  	  			    closable:true,
  	  	  			    tools:[{
  	  	  					iconCls:'icon-mini-refresh',
  	  	  					handler:function(){
  	  	  						alert('refresh');
  	  	  					}
  	  	  			    }]
  	  	  			});
  				}
			

		}
	});
	
</script>
</html>




