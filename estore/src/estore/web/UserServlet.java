package estore.web;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.connector.Request;
import org.apache.commons.beanutils.BeanUtils;

import com.alibaba.fastjson.JSON;
import com.aliyuncs.http.HttpRequest;
import com.aliyuncs.http.HttpResponse;

import estore.domain.User;
import estore.service.IUserService;
import estore.service.impl.UserServiceImpl;
import estore.utils.JedisUtils;
import estore.utils.MD5Utils;
import redis.clients.jedis.Jedis;

/**
 * Servlet implementation class UserServlet
 */
public class UserServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public UserServlet() {
        // TODO Auto-generated constructor stub
    }
    
    // 创建service 层
    IUserService userService = new UserServiceImpl();
	Jedis jedis = JedisUtils.getJedis();
    /**
     *   校验注册号码是否被注册过
     * @param request
     * @param response
     */
    public void validatePhone(HttpServletRequest request, HttpServletResponse response){
    	
    	String phone = request.getParameter("phone");
    	// 获取到电话号码 调用service层来  进行后台校验
    	Map<String ,String> map = userService.checkPhone(phone);
    	
    	String strJson = JSON.toJSONString(map);
    	System.out.println(strJson);
    	
    	try {
			response.getWriter().write(strJson);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
     /**
      * 发送短信验证码
      * 
      */
    public void sendMessage(HttpServletRequest request, HttpServletResponse response){
    	// 1. 获取电话号码 
    	String phone = request.getParameter("phone");
    	// 2. 调用service 层    将生成的验证码返回
    	String captcha = userService.sendMessage(phone);
    	
    	// 3. 将验证码保存到session 中
    	//request.getSession().setAttribute("captcha", captcha);
    	
    	//  优化  将验证码保存到  redis 中,作用 : 可以设置有效时间
    
    	jedis.setex("captcha", 60, captcha);
    	
    	// 3.给出响应     (响应暂定)
    	
    }
    /**
     * 注册功能实现
     * @param request
     * @param response
     */
    public void register(HttpServletRequest request, HttpServletResponse response){
    	
    	// 1. 验证验证码是否正确
    	//String captcha =(String) request.getSession().getAttribute("captcha");
    	// 从jedis 缓存中拿到 短信验证码   
    	String captcha = jedis.get("captcha");
    	String parameter = request.getParameter("captcha");
//    	System.out.println(captcha);
//    	System.out.println(parameter);
    	if(parameter!=null && parameter.equals(captcha)){
    		// 验证码正确
        	// 1. 获取参数
        	Map<String, String[]> map = request.getParameterMap();
        	// 2. 使用工具类将数据 封装到集合中
        	User user = new User();
        	// 3. 设置用户的权限问题
        	user.setRole("admin");
        	//4. 将密码进行加密
        
        	try {
    			BeanUtils.populate(user, map);
    		} catch (Exception e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		} 
        	
        	user.setPassword(MD5Utils.getPassword(user.getPassword()));
        	// 5. 调用service层  来进行判断
        	boolean result = userService.regiset(user);
        	if(result){
        		// 注册成功   重定向到login.jsp	
        		try {
        			jedis.close();
					response.sendRedirect(request.getContextPath()+"/login.jsp");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        		
        		
        	}else {
        		// 注册失败
        		String  message  = "注册失败";
        		request.setAttribute("message", message);
        		try {
        			jedis.close();
    				request.getRequestDispatcher("/register.jsp").forward(request, response);
    			} catch (Exception e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			}
        	}
    		
    	}else {
    		// 验证码错误   返回错误信息 
    		jedis.close();
    		String  message  = "验证码错误";
    		request.setAttribute("message", message);
    		try {
				request.getRequestDispatcher("/register").forward(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	
    }
    /**
     * 执行登陆功能
     */
    public void login( HttpServletRequest request, HttpServletResponse response){
    		
    	// 1. 获取参数
    	String phone = request.getParameter("phone");
    	String password = request.getParameter("password");
    	password=MD5Utils.getPassword(password);
    	
    	// 2. 调用service 层
    	User user = userService.login(phone,password);
    	// 3. 给出响应
    	
    	if(user!=null){
    		// 登陆成功   转发到index 页面
//    		request.setAttribute("user", user);
    		request.getSession().setAttribute("user", user);
    		try {
				request.getRequestDispatcher("/index.jsp").forward(request, response);
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		
    	}else {
    		// 用户名或密码错误   转发到login 页面
    		String message ="用户名或密码错误";
    		request.setAttribute("message", message);
    		try {
				request.getRequestDispatcher("/login.jsp").forward(request, response);
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		
    	}
    }

}






















