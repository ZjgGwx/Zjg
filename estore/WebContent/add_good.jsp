<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="root" value="${pageContext.request.contextPath}"/>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>添加商品</title>
  	<link rel="stylesheet" type="text/css" href="easyui/themes/default/easyui.css">
	 <link rel="stylesheet" type="text/css" href="easyui/themes/icon.css">
	 <script type="text/javascript" src="easyui/jquery.min.js">
	 </script>
	 <script type="text/javascript" src="easyui/jquery.easyui.min.js">
	 </script>
</head>

<body>
	<!-- 
		表单中有文件上传组件
		需要修改表单的enctype属性，值为“multpart/form-data”
	 -->
	<form action="${root }/goodServlet?methodName=addGood" enctype="multipart/form-data" method="post">
		商品名称：<input class="easyui-textbox"  data-options="width:300" name="name" /><br/>
		市场价：<input class="easyui-textbox"  name="marketprice" data-options="min:0,precision:2"/><br/>
		优惠价：<input class="easyui-textbox" name="estoreprice" data-options="min:0,precision:2" /><br/>
		商品分类：<select  class="easyui-combobox"  name="category">
			<option value="">--请选择--</option>
			<option>衣服</option>
			<option>家具</option>
			<option>电子</option>
		</select><br/>
		库存数量：<input class="easyui-numberbox"   name="num" /><br/>
		图片：<input class="easyui-filebox" name="fileName" /><br/>
		商品描述：<input class="easyui-textbox" name="description" data-options="width:300,multiline:true" /><br>
		<input type="submit" class="easyui-linkbutton" data-options="iconCls:'icon-search'" />
	</form>
</body>
</html>
