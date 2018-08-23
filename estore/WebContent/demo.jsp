<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
 <link rel="stylesheet" type="text/css" href="easyui/themes/default/easyui.css">
 <link rel="stylesheet" type="text/css" href="easyui/themes/icon.css">
 <script type="text/javascript" src="easyui/jquery.min.js">
 </script>
 <script type="text/javascript" src="easyui/jquery.easyui.min.js">
 </script>
</head>
<body>

	<table id="dg"></table>

</body>
<script type="text/javascript">
		$('#dg').datagrid({
		    url:'js/combobox.json',
		    toolbar: [{
				iconCls: 'icon-edit',
				handler: function(){alert('edit')}
			},'-',{
				iconCls: 'icon-help',
				handler: function(){alert('help')}
			}],
		    columns:[[
				{field:'cid',title:'Code',width:100},
				{field:'cname',title:'Name',width:100},
			/* 	{field:'price',title:'Price',width:100,align:'right'} */
		    ]]
		});

</script>
</html>