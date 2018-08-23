<%@ page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="root" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>我的购物车</title>
<%@include file="inc/common_head.jsp"%>
</head>
<body>
	<%@include file="inc/header.jsp"%>
	<div class="block table">
		<div class="AreaR">
			<div class="block box">
				<div class="blank"></div>
				<div id="ur_here">
					当前位置: <a href="index.jsp">首页</a><code>&gt;</code>我的购物车
				</div>
			</div>
			<div class="blank"></div>
			<div class="box">
				<div class="box_1">
					<div class="userCenterBox boxCenterList clearfix"
						style="_height:1%;">
						<h5><span>我的购物车</span></h5>
						<table width="100%" align="center" border="0" cellpadding="5"
							cellspacing="1" bgcolor="#dddddd">
							<tr>
								<th bgcolor="#ffffff">商品名称</th>
								<th bgcolor="#ffffff">市场价</th>
								<th bgcolor="#ffffff">本店价</th>
								<th bgcolor="#ffffff">购买数量</th>
								<th bgcolor="#ffffff">小计</th>
								<th bgcolor="#ffffff" width="160px">操作</th>
							</tr>
							
						<c:set var="totalPrice" value="0"></c:set>	
						<c:set var="savePrice" value="0"></c:set>
						<c:forEach items="${cartList }" var ="cart">
							<tr>
								<td bgcolor="#ffffff" align="center" style="width:300px;">
									<!-- 商品图片 -->
									<a href="javascript:;" target="_blank">
										<img style="width:80px; height:80px;"
										src="${root }${cart.good.imgurl}"
										border="0" title="${cart.good.name }" />
									</a><br />
									<!-- 商品名称 -->
									<a href="javascript:;" target="_blank" class="f6">${cart.good.name }</a>
								</td>
								<td align="center" bgcolor="#ffffff">${cart.good.marketprice }元</td>
								<td align="center" bgcolor="#ffffff">${cart.good.estoreprice }元</td>
								<td align="center" bgcolor="#ffffff">
									<input id="myinput${cart.good.id }"  value="${cart.buynum }" 
									onblur="updateCartBuynum(${cart.good.id},this.value,${cart.good.num })" size="4" class="inputBg" style="text-align:center;" />
								</td>
								<td align="center" id="td${cart.good.id }" bgcolor="#ffffff">${cart.good.estoreprice*cart.buynum }元</td>
								<td align="center" bgcolor="#ffffff">
									<a href="javascript:;" onclick="deleteGoodByGid(${cart.gid},this)" class="f6">删除</a>
								</td>
							</tr>
								<c:set var="totalPrice" value="${totalPrice+cart.good.estoreprice*cart.buynum }"></c:set>
								<c:set var="savePrice" value="${savePrice+(cart.good.marketprice-cart.good.estoreprice)*cart.buynum }"></c:set>
						</c:forEach>
							<tr>
								<td colspan="6" style="text-align:right;padding-right:10px;font-size:25px;">
									购物金额小计&nbsp;<font id="littleTotal" color="red">${totalPrice}</font>元，
									共为您节省了&nbsp;<font id="saveTotal" color="red">${savePrice }</font>元
									<a href="${root }/orderServlet?methodName=goToOrder"><input value="去结算" type="button" class="btn" /></a>
								</td>
							</tr>
						</table>
					</div>
				</div>
			</div>
		</div>
		<div class="blank"></div>
		<div class="blank5"></div>
	</div>
	<%@include file="inc/footer.jsp"%>
</body>

<script type="text/javascript">
function updateCartBuynum(gid,buynum,num){
		buynum = Math.ceil(buynum);
		$("#myinput"+gid).val(buynum);
		//校验buynum的值
		if(buynum<0||buynum>num){
			alert("数量必须在0到库存之间之间");
			return;
		}
			
		$.ajax({
			url:"${root}/cartServlet?methodName=updateCartBuynum",
			data:{"gid":gid,"buynum":buynum},
			dataType:"json",
			success:function(res){
				/*
					{littleTotal:value1,saveTotal:value2,currentTotal:value3}
				*/
				//修改所有商品的总价格
				$("#littleTotal").html(res.littleTotal);
				//修改所有商品的总节省金额
				$("#saveTotal").html(res.saveTotal);
				//修改当前修改的商品的小计
				$("#td"+gid).html(res.currentTotal+"元");
			}
		});
}

	function  deleteGoodByGid(gid,element){
		// 发送ajax 请求 将gid作为参数传递到后台
		$.ajax({
			url:"${root}/cartServlet?methodName=deleteCartGoodByGid",
			async:true,
			data:{gid:gid},
			dataType:"json",
			type:"post",
			success:function(data){
				//删除当前商品这行
				element.parentElement.parentElement.remove();
				//修改页面上所有商品的总价
				$("#littleTotal").html(data.littleTotal);
				//修改页面上所有商品的节省金额
				$("#saveTotal").html(data.saveTotal);
			}
		});
		
	}
</script>
</html>








