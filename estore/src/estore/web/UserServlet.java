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
    
    // ����service ��
    IUserService userService = new UserServiceImpl();
	Jedis jedis = JedisUtils.getJedis();
    /**
     *   У��ע������Ƿ�ע���
     * @param request
     * @param response
     */
    public void validatePhone(HttpServletRequest request, HttpServletResponse response){
    	
    	String phone = request.getParameter("phone");
    	// ��ȡ���绰���� ����service����  ���к�̨У��
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
      * ���Ͷ�����֤��
      * 
      */
    public void sendMessage(HttpServletRequest request, HttpServletResponse response){
    	// 1. ��ȡ�绰���� 
    	String phone = request.getParameter("phone");
    	// 2. ����service ��    �����ɵ���֤�뷵��
    	String captcha = userService.sendMessage(phone);
    	
    	// 3. ����֤�뱣�浽session ��
    	//request.getSession().setAttribute("captcha", captcha);
    	
    	//  �Ż�  ����֤�뱣�浽  redis ��,���� : ����������Чʱ��
    
    	jedis.setex("captcha", 60, captcha);
    	
    	// 3.������Ӧ     (��Ӧ�ݶ�)
    	
    }
    /**
     * ע�Ṧ��ʵ��
     * @param request
     * @param response
     */
    public void register(HttpServletRequest request, HttpServletResponse response){
    	
    	// 1. ��֤��֤���Ƿ���ȷ
    	//String captcha =(String) request.getSession().getAttribute("captcha");
    	// ��jedis �������õ� ������֤��   
    	String captcha = jedis.get("captcha");
    	String parameter = request.getParameter("captcha");
//    	System.out.println(captcha);
//    	System.out.println(parameter);
    	if(parameter!=null && parameter.equals(captcha)){
    		// ��֤����ȷ
        	// 1. ��ȡ����
        	Map<String, String[]> map = request.getParameterMap();
        	// 2. ʹ�ù����ཫ���� ��װ��������
        	User user = new User();
        	// 3. �����û���Ȩ������
        	user.setRole("admin");
        	//4. ��������м���
        
        	try {
    			BeanUtils.populate(user, map);
    		} catch (Exception e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		} 
        	
        	user.setPassword(MD5Utils.getPassword(user.getPassword()));
        	// 5. ����service��  �������ж�
        	boolean result = userService.regiset(user);
        	if(result){
        		// ע��ɹ�   �ض���login.jsp	
        		try {
        			jedis.close();
					response.sendRedirect(request.getContextPath()+"/login.jsp");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        		
        		
        	}else {
        		// ע��ʧ��
        		String  message  = "ע��ʧ��";
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
    		// ��֤�����   ���ش�����Ϣ 
    		jedis.close();
    		String  message  = "��֤�����";
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
     * ִ�е�½����
     */
    public void login( HttpServletRequest request, HttpServletResponse response){
    		
    	// 1. ��ȡ����
    	String phone = request.getParameter("phone");
    	String password = request.getParameter("password");
    	password=MD5Utils.getPassword(password);
    	
    	// 2. ����service ��
    	User user = userService.login(phone,password);
    	// 3. ������Ӧ
    	
    	if(user!=null){
    		// ��½�ɹ�   ת����index ҳ��
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
    		// �û������������   ת����login ҳ��
    		String message ="�û������������";
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






















