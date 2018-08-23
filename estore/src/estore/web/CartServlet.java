package estore.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import estore.dao.impl.CartDaoImpl;
import estore.domain.Cart;
import estore.domain.User;
import estore.service.ICartService;
import estore.service.impl.CartServiceImpl;

/**
 * Servlet implementation class CartServlet
 */
public class CartServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	
	// 调用service 层
	ICartService cartService = new CartServiceImpl();
	
	public void addCart( HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		//   通过过滤器首先验证用户是否是登陆状态  通过session 判断
		// 1. 获取参数
		int  gid =Integer.parseInt(request.getParameter("id"));
		User user =(User) request.getSession().getAttribute("user");
		int uid = user.getId();
		// 2. 调用service  
		cartService.addCart(uid,gid);
		// 3. 给出响应
		response.sendRedirect(request.getContextPath()+"/buyorcart.jsp");
	}
	// 查看购物车
   public void queryCart( HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
	   // 校验登陆功能在过滤器中实现
	   
	   // 1. 获取参数 
	   User user =(User) request.getSession().getAttribute("user");
	   int uid = user.getId();
	   // 2. 调用service 层
	   List<Cart> cartList = cartService.queryCartByUid(uid);
	   // 3. 给出响应
	   request.setAttribute("cartList", cartList);
	   request.getRequestDispatcher("/cart.jsp").forward(request, response);
   }
   /**
    * 更改数据  ajax 请求
    */
   public void  updateCartBuynum(HttpServletRequest request, HttpServletResponse response){
	   int gid = Integer.parseInt(request.getParameter("gid"));
	   int count = Integer.parseInt(request.getParameter("buynum"));
	   
	   User user = (User)request.getSession().getAttribute("user");
	   int uid = user.getId();
	   Cart cart = new Cart();
	   cart.setUid(uid);
	   cart.setGid(gid);
	   cart.setBuynum(count);
	   
	   String json = cartService.updateCart(cart);
	   try {
		response.getWriter().write(json);
	   		} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		}
	   
   }
   // 删除购物车中的商品
   public void deleteCartGoodByGid(HttpServletRequest request, HttpServletResponse response){
	   // 从 session  中获取当前用户信息
	   User user =(User) request.getSession().getAttribute("user");
	   // 1. 获取参数
	   int gid = Integer.parseInt(request.getParameter("gid"));
	   int uid = user.getId();
	   
	   // 2. 调用service层 
	   String strJson =  cartService.deleteCartGoodByGid(uid,gid);
	   // 3. 给出响应
	   try {
		response.getWriter().write(strJson);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		   
   }
  
}










