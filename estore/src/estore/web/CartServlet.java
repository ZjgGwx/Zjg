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
	
	// ����service ��
	ICartService cartService = new CartServiceImpl();
	
	public void addCart( HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		//   ͨ��������������֤�û��Ƿ��ǵ�½״̬  ͨ��session �ж�
		// 1. ��ȡ����
		int  gid =Integer.parseInt(request.getParameter("id"));
		User user =(User) request.getSession().getAttribute("user");
		int uid = user.getId();
		// 2. ����service  
		cartService.addCart(uid,gid);
		// 3. ������Ӧ
		response.sendRedirect(request.getContextPath()+"/buyorcart.jsp");
	}
	// �鿴���ﳵ
   public void queryCart( HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
	   // У���½�����ڹ�������ʵ��
	   
	   // 1. ��ȡ���� 
	   User user =(User) request.getSession().getAttribute("user");
	   int uid = user.getId();
	   // 2. ����service ��
	   List<Cart> cartList = cartService.queryCartByUid(uid);
	   // 3. ������Ӧ
	   request.setAttribute("cartList", cartList);
	   request.getRequestDispatcher("/cart.jsp").forward(request, response);
   }
   /**
    * ��������  ajax ����
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
   // ɾ�����ﳵ�е���Ʒ
   public void deleteCartGoodByGid(HttpServletRequest request, HttpServletResponse response){
	   // �� session  �л�ȡ��ǰ�û���Ϣ
	   User user =(User) request.getSession().getAttribute("user");
	   // 1. ��ȡ����
	   int gid = Integer.parseInt(request.getParameter("gid"));
	   int uid = user.getId();
	   
	   // 2. ����service�� 
	   String strJson =  cartService.deleteCartGoodByGid(uid,gid);
	   // 3. ������Ӧ
	   try {
		response.getWriter().write(strJson);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		   
   }
  
}










