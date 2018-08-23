package estore.filter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import estore.domain.User;

/**
 * Servlet Filter implementation class PrivilegesFilter
 */
public class PrivilegesFilter implements Filter {

	// ����ȫ�ֱ���   ���������Ѿ���ʼ��������   (�����ļ�����Ҫ���ص�ҳ��)
	List<String> privilegesList = new ArrayList<String>();
    /**
     * Default constructor. 
     */
    public PrivilegesFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// �ж��û���Ȩ������
		HttpServletRequest req= (HttpServletRequest)request;
		HttpServletResponse resp = (HttpServletResponse)response;
		
		// �Ż�  1.�����ظ�Ϊ�������н���  ����Ҫ�ж�Ȩ�޵ķŽ������ļ���  
		 	// 1.1 ��ȡ�����·��
			String URI = req.getRequestURI();
			//System.out.println(URI);
			// 2. �������ַ��ȡ  ȥ����Ŀ��
			URI= URI.substring(req.getContextPath().length());
			//System.out.println(URI);
			// 3. �ж������URI��ַ�Ƿ������privilegeList ������
		
		if(privilegesList.contains(URI)){
			// ˵���������ַ��Ҫ�����ж�

			// 1. �����ж��û��Ƿ��¼
			User user = (User)req.getSession().getAttribute("user");
			if(user!=null){
				// 2. �����¼ 
				// 2.1 �ж��û��Ľ�ɫ
				if(user.getRole().equals("admin")){
					// 2.1.1  ����ǹ���ԱȨ��  ����
					chain.doFilter(request, response);
				}else {
					// 2.1.2 ������ǹ���ԱȨ��   ��ʾȨ�޲���
					resp.getWriter().write("Ȩ�޲���");
					return;
				}
							
			}else {
				// 3. û�е�¼  �ض��򵽵�¼ҳ��
				resp.sendRedirect(req.getContextPath()+"/login.jsp");
				return;
			}
		}	else{
			// ˵���������ַ����Ҫ�����ж�  'ֱ�ӷ���
			chain.doFilter(request, response);
			
		}
		
		//chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
		// ���ö�ȡ�����ļ��ķ�ʽ�����û�Ȩ�޵�������й���
		String path  = fConfig.getServletContext().getRealPath("/WEB-INF/classes/privileges.txt");
		// ����һ������  �������������ļ��е���������
		
		// ��ȡ�����ļ�
		try {
			BufferedReader bf = new BufferedReader(new FileReader(new File(path)));
			String line = null;
			while ((line=bf.readLine())!=null){
				privilegesList.add(line);
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		
		
		
		
		
		
	}

}
