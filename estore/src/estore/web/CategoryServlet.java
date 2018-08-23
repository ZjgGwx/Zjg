package estore.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;

import estore.domain.Category;
import estore.service.ICategogyService;
import estore.service.impl.CategoryServiceImpl;

/**
 * Servlet implementation class CategotyServlet
 */
public class CategoryServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	
	// 
	ICategogyService categotyService = new CategoryServiceImpl();
	public void checkCategory(HttpServletRequest request, HttpServletResponse response){
		
		response.setContentType("text/html;charset=utf-8");
		// 1. �����ȡ����
		
		// 2. ����service �� 
	     String strJosn = categotyService.checkCategory();
		// 3.ʹ�ù��� ��list  ת����  json ���

		try {
			response.getWriter().write(strJosn);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
