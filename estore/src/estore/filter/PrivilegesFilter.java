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

	// 定义全局变量   用来保存已经初始化的数据   (配置文件中需要拦截的页面)
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
		// 判断用户的权限问题
		HttpServletRequest req= (HttpServletRequest)request;
		HttpServletResponse resp = (HttpServletResponse)response;
		
		// 优化  1.将拦截改为拦截所有界面  将需要判断权限的放进配置文件中  
		 	// 1.1 获取请求的路径
			String URI = req.getRequestURI();
			//System.out.println(URI);
			// 2. 将请求地址截取  去掉项目名
			URI= URI.substring(req.getContextPath().length());
			//System.out.println(URI);
			// 3. 判断请求的URI地址是否包含在privilegeList 集合中
		
		if(privilegesList.contains(URI)){
			// 说明该请求地址需要进行判断

			// 1. 首先判断用户是否登录
			User user = (User)req.getSession().getAttribute("user");
			if(user!=null){
				// 2. 如果登录 
				// 2.1 判断用户的角色
				if(user.getRole().equals("admin")){
					// 2.1.1  如果是管理员权限  放行
					chain.doFilter(request, response);
				}else {
					// 2.1.2 如果不是管理员权限   提示权限不够
					resp.getWriter().write("权限不够");
					return;
				}
							
			}else {
				// 3. 没有登录  重定向到登录页面
				resp.sendRedirect(req.getContextPath()+"/login.jsp");
				return;
			}
		}	else{
			// 说明该请求地址不需要进行判断  '直接放行
			chain.doFilter(request, response);
			
		}
		
		//chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
		// 利用读取配置文件的方式来对用户权限的问题进行管理
		String path  = fConfig.getServletContext().getRealPath("/WEB-INF/classes/privileges.txt");
		// 创建一个集合  用来保存配置文件中的所有数据
		
		// 读取配置文件
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
