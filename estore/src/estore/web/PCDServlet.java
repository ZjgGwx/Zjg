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
		// �鿴ʡ��������Ϣ
		// 1. ��ȡ����
		String pid = request.getParameter("pid");
		
		// 2. ����service ��
		String strJson = pcdService.findPCDByPid(pid);
//		System.out.println(strJson);
		
		// 3. ������Ӧ
		try {
			response.getWriter().write(strJson);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
  
}
