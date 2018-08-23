<%@ page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="root" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta name="Generator" content="ECSHOP v2.7.3" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="Keywords" content="" />
<meta name="Description" content="" />
<title>提交订单</title>
<%@include file="inc/common_head.jsp"%>
</head>
<body>
	<%@include file="inc/header.jsp"%>
	<div class="block clearfix"><div class="AreaR">
	<div class="block box"><div class="blank"></div>
		<div id="ur_here">
			当前位置: <a href="index.jsp">首页</a><code>&gt;</code>购物流程
		</div>
	</div><div class="blank"></div><div class="box"><div class="box_1">
	<div class="userCenterBox boxCenterList clearfix" style="_height:1%;">
	<form action="${root }/orderServlet?methodName=submitOrder" method="post">
		<!---------收货人信息开始---------->
		<h5><span>收货人信息</span></h5>
		<table width="100%" align="center" border="0" cellpadding="5"
			cellspacing="1" bgcolor="#dddddd">
			<tr>
				<td bgcolor="#ffffff" align="right" width="120px">区域信息：</td>
				<td bgcolor="#ffffff">
					<!-- 省 -->
					<select id="province"  onchange="addCity()">
						<option value="">-- 请选择省 --</option>
					</select>&nbsp;&nbsp;&nbsp;
					<!-- 市 -->
					<select id="city" onchange="addDistrict()">
						<option value="">-- 请选择市 --</option>
					</select>&nbsp;&nbsp;&nbsp;
					<!-- 县(区) -->
					<select id="district" onchange="addpdistrict()">
						<option value="">-- 请选择县(区) --</option>
					</select>
					<input type="hidden" id="hprovince" name="province" value="">
					<input type="hidden" id="hcity" name="city" value="">
					<input type="hidden" id="hdistrict" name="district" value="">
					
				</td>
			</tr>
			<tr>
				<td bgcolor="#ffffff" align="right">详细地址：</td>
				<td bgcolor="#ffffff">
					<input style="width:347px;" name="detailAddress"/>
				</td>
			</tr>
			<tr>
				<td bgcolor="#ffffff" align="right">邮政编码：</td>
				<td bgcolor="#ffffff"><input name="zipcode"/></td>
			</tr>
			<tr>
				<td bgcolor="#ffffff" align="right">收货人姓名：</td>
				<td bgcolor="#ffffff"><input name="name"/></td>
			</tr>
			<tr>
				<td bgcolor="#ffffff" align="right">联系电话：</td>
				<td bgcolor="#ffffff"><input name="telephone"/></td>
			</tr>
		</table>
		<!---------收货人信息结束---------->
		
		<!----------商品列表开始----------->
		<div class="blank"></div>
		<h5><span>商品列表</span></h5>
		<table width="100%" border="0" cellpadding="5" cellspacing="1"
			bgcolor="#dddddd">
			<tr>
				<th width="30%" align="center">商品名称</th>
				<th width="22%" align="center">市场价格</th>
				<th width="22%" align="center">商品价格</th>
				<th width="15%" align="center">购买数量</th>
				<th align="center">小计</th>
			</tr>
			<c:set var="totalPrice" value="0"></c:set>
			<c:forEach items="${cartList }" var= "cart">
					<tr>
						<td>
							<a href="javascript:;" class="f6">${cart.good.name }</a>
						</td>
						<td>${cart.good.marketprice }元</td>
						<td>${cart.good.estoreprice }元</td>
						<td align="center">${cart.buynum }</td>
						<td>${cart.good.estoreprice*cart.buynum }元</td>
					</tr>
					<c:set var="totalPrice" value="${totalPrice+cart.good.estoreprice*cart.buynum }"></c:set>
			</c:forEach>
		
			<tr>
				<td colspan="5" style="text-align:right;padding-right:10px;font-size:25px;">
					商品总价&nbsp;<font color="red">&yen;${totalPrice}</font>元
					<input  type="submit" value="提交订单" class="btn" />
				</td>
			</tr>
		</table>
		<!----------商品列表结束----------->
	</form>
	</div></div></div></div></div>
	<%@include file="inc/footer.jsp"%>
</body>
<script type="text/javascript">
//页面加载完成  就实现查看省的功能
addProvince();
	function addProvince(){
	$.ajax({
		url:"${root}/pCDServlet?methodName=findPCDByPid",
		data:{"pid":0},
		dataType:"json",
		success:function(res){
			// 返回的json 类型为   数组  [{id:id,pid:pid,name:name},{一条数据},{}]
			// 遍历数据   将省的信息拼接到 省的下拉框中
			var $province = $("#province");
			//var $city = $("#city");
			//$city.html("<option value=''>-- 请选择市--</option>");
			//var $district = $("#district");
			//$district.html("<option value=''>-- 请选择县/(区)--</option>");
			
			for(var i=0;i<res.length;i++){
				//创建option 属性
				var $option= $("<option></option>");
				// 给option属性赋值
				$option.html(res[i].name).val(res[i].id);
				// 将option  追加到select 选择框下面
				$province.append($option);
			}
		}
		
	});
}

	function addCity(){
		
		//获取当前选中的省的下拉列表框中选中的<option>的文本值
		var provincename = $("#province option:selected").html();
		//将省的名字填充到省的隐藏域
		$("#hprovince").val(provincename);
		
		var $city = $("#city");
		$city.html("<option value=''>-- 请选择市--</option>");
		var $district =$("#district");
		$district.html("<option value=''>-- 请选择县/(区)--</option>");
		// 获取省的pid  
		var pid = $("#province").val();
		$.ajax({
			url:"${root}/pCDServlet?methodName=findPCDByPid",
			data:{"pid":pid},
			dataType:"json",
			success:function(res){
				// 返回的json 类型为   数组  [{id:id,pid:pid,name:name},{一条数据},{}]
				// 遍历数据   将市的信息拼接到 省的下拉框中

				for(var i=0;i<res.length;i++){
					//创建option 属性
					var $option= $("<option></option>");
					// 给option属性赋值
					$option.html(res[i].name).val(res[i].id);
					// 将option  追加到select 选择框下面
					$city.append($option);
				}
			}
		});
	}
	
	function  addDistrict(){
		
		//获取当前选中的市的下拉列表框中选中的<option>的文本值
		var cityname = $("#city option:selected").html();
		//将省的名字填充到市的隐藏域
		$("#hcity").val(cityname);
		
		// 获取省的pid  
		var pid = $("#city").val();
		$.ajax({
			url:"${root}/pCDServlet?methodName=findPCDByPid",
			data:{"pid":pid},
			dataType:"json",
			success:function(res){
				// 返回的json 类型为   数组  [{id:id,pid:pid,name:name},{一条数据},{}]
				// 遍历数据   将市的信息拼接到 省的下拉框中
				var $district = $("#district");
				$district.html("<option value=''>-- 请选择县/(区)--</option>");
				for(var i=0;i<res.length;i++){
					//创建option 属性
					var $option= $("<option></option>");
					// 给option属性赋值
					$option.html(res[i].name).val(res[i].id);
					// 将option  追加到select 选择框下面
					$district.append($option);
				}
			}
		});
	}
	
	function addpdistrict(){
		//获取当前选中的县的下拉列表框中选中的<option>的文本值
		var districtname = $("#district option:selected").html();
		//将省的名字填充到xian 区的隐藏域
		$("#hdistrict").val(districtname);
	}
</script>


</html>








