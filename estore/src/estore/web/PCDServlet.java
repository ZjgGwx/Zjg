package estore.web;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import estore.service.IPCDService;
import estore.service.impl.PCDServiceImpl;

/**
 * Servlet implementation class PCDServlet
 */
public class PCDServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
       
	private IPCDService pcdService = new PCDServiceImpl();
	public void findPCDByPid(HttpServletRequest request,HttpServletResponse response){
//		response.setContentType("text/html;charset=utf-8");
		// 查看省市区的信息
		// 1. 获取参数
		String pid = request.getParameter("pid");
		
		// 2. 调用service 层
		String strJson = pcdService.findPCDByPid(pid);
//		System.out.println(strJson);
		
		// 3. 给出响应
		try {
			response.getWriter().write(strJson);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
  
}
