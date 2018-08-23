<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="root" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>注册</title>
<%@include file="inc/common_head.jsp"%>
</head>
<body>
	<%@include file="inc/header.jsp"%>
	<div class="block block1">
		<div class="blank"></div>
		<div class="usBox">
			<div class="usBox_1">
				<div class="login_tab">
					<ul>
						<li onclick="location.href='login.jsp';">
							<a href="javascript:;">用户登录</a>
						</li>
						<li class="active">用户注册</li>
					</ul>
				</div>
				<form id="registForm" action="${root }/userServlet?methodName=register" method="post" name="formUser"
					onsubmit="return register();">
					<table width="100%" border="0" align="left" cellpadding="5"
						cellspacing="3">
						<caption style="color:red;">${message }</caption>
						<tr>
							<td width="25%" align="right">手机号</td>
							<td width="65%"><input name="phone" type="text"
								id="phone" onblur="check_phone(this.value)"
								class="inputBg" /> <span id="phone_notice"
								style="color:#FF0000"> *</span></td>
						</tr>
						<tr>
							<td width="25%" align="right">用户名（邮箱）</td>
							<td width="65%"><input name="email" type="text"
								id="username" onblur="checkEmail(this.value);"
								class="inputBg" /> <span id="username_notice"
								style="color:#FF0000"> *</span></td>
						</tr>
						<tr>
							<td align="right">昵称</td>
							<td><input name="nickname" type="text"
								id="nickname" onblur="check_nickname(this.value);"
								class="inputBg" /> <span id="nickname_notice"
								style="color:#FF0000"> *</span></td>
						</tr>
						<tr>
							<td align="right">密码</td>
							<td><input name="password" type="password" id="password1"
								onblur="check_password(this.value);"
								onkeyup="checkIntensity(this.value)" class="inputBg" />
								<span style="color:#FF0000"
								id="password_notice"> *</span></td>
						</tr>
						<tr>
							<td align="right">密码强度</td>
							<td>
								<table width="145" border="0" cellspacing="0" cellpadding="1">
									<tr align="center">
										<td width="33%" style="border-bottom:2px solid #ccc;" id="pwd_lower">弱</td>
										<td width="33%" style="border-bottom:2px solid #ccc;" id="pwd_middle">中</td>
										<td width="33%" style="border-bottom:2px solid #ccc;" id="pwd_high">强</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td align="right">确认密码</td>
							<td><input name="confirm_password" type="password" 
								id="conform_password"
								onblur="check_conform_password(this.value);" class="inputBg" />
								<span style="color:#FF0000"
								id="conform_password_notice"> *</span></td>
						</tr>
						<tr>
							<td align="right">验证码</td>
							<td><input type="text" size="8" name="captcha" id="captcha"
								class="inputBg"  onblur="check_captcha(this.value);" /> <span style="color:#FF0000"
								id="captcha_notice"> *</span><input  type="button" onclick ="sendMessage()" id="mybtn"  value="获取手机验证码"></td>
						</tr>
						<!-- <tr>
							<td align="right"></td>
							<td><img src="validatecode.jsp"
								style="vertical-align:middle;cursor:pointer;width:130px;height:35px;margin-top:-2px;"
								onClick="src='validatecode.jsp?'+Math.random()" /></td>
						</tr> -->
						<tr>
							<td>&nbsp;</td>
							<td><label> <input name="agreement" type="checkbox"
									value="1" checked="checked" /> 我已看过并接受《<a
									href="javascript:;" style="color:blue" target="_blank">用户协议</a>》
							</label></td>
						</tr>
						<tr>
							<td>&nbsp;</td>
							<td align="left">
								<input name="Submit" type="submit" value="" class="us_Submit_reg">
							</td>
						</tr>
						<tr>
							<td colspan="2">&nbsp;</td>
						</tr>
					</table>
				</form>
				<div class="blank"></div>
			</div>
		</div>
	</div>
	<%@include file="inc/footer.jsp"%>
</body>
<script type="text/javascript">
		function check_phone(phone){
			var flag = false;
			var regExp = new RegExp("^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\\d{8}$");
			var res = regExp.test(phone);
			
			if(res){
				// 如果号码为正确  发到后台验证
				$.ajax({
					// 发送ajax请求  要采用同步机制,只有获得了后台的验证答案才能执行注册操作
					url:"${root}/userServlet?methodName=validatePhone",
					type:"post",
					data:{"phone":phone},
					dataType:"json",
					async:false,		// 采用同步ajax的方式
					success:function(result){
						if("yes"== result.isExit){
							// yes 代表数据库中没有此号码 可以创建
							$("#phone_notice").html("号码验证通过").css("color","green");
								flag = true;
						}else {
							//表示数据库中已经注册过该号码 不能注册
							/* $("#phone_notice").html("号码已经注册过").css("color","red"); */
							
							$("#phone_notice").html("手机号码已经被注册").css("color","red");
							flag = false;
						}
					}
				});
				
			}else {
				// 如果号码为假,直接提示错误
				$("#phone_notice").html("号码不正确").css("color","red");
				flag=false;
			}
			return flag;
		}
		
		
		// 验证码 function 方法名  sendMessage  提交方法名 sendMessage
		function sendMessage(){
			// 首先获取phone   然后校验phone   调用校验手机号码方法
			var  phone = $("#phone").val();
			var res = check_phone(phone); // 返回的结果为boolean 类型,正确则继续操作
			
			if(res){
				// 异步ajax 请求
				$.ajax({
					url:"${root}/userServlet?methodName=sendMessage",
					data:{"phone":phone},
					success:function (){
						//1. 实现按钮不可点击功能     倒计时  60秒
						$("#mybtn").attr("disabled","disabled");
						var  time = 60;
						// 2. 定时任务 
						var id = setInterval(function(){
							//修改按钮的显示内容
							$("#mybtn").val(time+"s后重新发送");
							if(time>0){
								time--;
							}else{
								//停止定时器，并且设置按钮再次可以被点击
								clearInterval(id);
								$("#mybtn").removeAttr("disabled").val("重新发送");
							}
						}, 1000);
					}
				});
				
			}else {
				$("#captcha_notice").html("号码验证失败").css("color","red");
			}
			
			
		}
		
</script>
	

</html>








