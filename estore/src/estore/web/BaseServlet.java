package estore.web;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BaseServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//BaseServlet   ��Ϊ�����ṩ��servlet  
		String methodName= request.getParameter("methodName");
		
		// ͨ������ķ������鵽��Ӧ�ķ���  ���е���
		try {
			Method method = this.getClass().getMethod(methodName,HttpServletRequest.class, HttpServletResponse.class);
			
			// ���÷���
			method.invoke(this,request,response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

}