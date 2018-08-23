package estore.filter;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * Servlet Filter implementation class EncodeFilter
 */
public class EncodeFilter implements Filter {

    /**
     * Default constructor. 
     */
    public EncodeFilter() {
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
		
		final HttpServletRequest req = (HttpServletRequest)request;
		response.setContentType("text/html;charset=utf-8");
		//获取请求的方式
		String method = req.getMethod();
		if("get".equalsIgnoreCase(method)){
			//get乱码的处理需要对原有的方法进行增强
			
			/**
			 * 对方法进行增强：
			 * 	1.继承
			 * 	2.装饰者模式
			 * 		1.被装饰者和装饰者要实现一样的接口
			 * 		2.被装饰者要作为一个参数传递给装饰者
			 * 		3.对于需要增强的方法增强，。。
			 *  3.动态代理
			 */
			//获取原有request的增强对象
			EncodeRequest encodeRequest = new EncodeRequest(req);
			//将增强后的request传递给servlet
			chain.doFilter(encodeRequest, response);
		}else if("post".equalsIgnoreCase(method)){
			//如果是post请求
			request.setCharacterEncoding("utf-8");
			chain.doFilter(request, response);
		}
		
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
