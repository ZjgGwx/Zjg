package estore.web;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSON;

import estore.domain.Cart;
import estore.domain.Order;
import estore.domain.User;
import estore.service.ICartService;
import estore.service.IOrderService;
import estore.service.impl.CartServiceImpl;
import estore.service.impl.OrderServiceImpl;
import estore.utils.WXPayUtils;

/**
 * Servlet implementation class OrderServlet
 */
public class OrderServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
   
	private ICartService cartService = new CartServiceImpl();
	private IOrderService orderService = new OrderServiceImpl();
	
	public void goToOrder(HttpServletRequest request,HttpServletResponse response){
		// �ӹ��ﳵ��ת������ҳ��   ����:�ڶ���������ʾ���ﳵ�е���Ϣ 
		// ����cart  �еĹ��ﳵ�鿴���ﳵ  ����Ϣ���ص�order ҳ��
		User user = (User)request.getSession().getAttribute("user");
		int uid = user.getId();
		List<Cart> cartList = cartService.queryCartByUid(uid);
		System.out.println(cartList);
		
		// ת����order ҳ��
		request.setAttribute("cartList", cartList);
		try {
			request.getRequestDispatcher("/orders_submit.jsp").forward(request, response);
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}
	// �ύ����
	public void submitOrder(HttpServletRequest request,HttpServletResponse response){
		// ���ñ����ʽ
		
			try {
				request.setCharacterEncoding("utf-8");
				response.setContentType("text/html;charset=utf-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new RuntimeException("���ñ����쳣");
			}
	
		// ��ȡuid  
		User user = (User)request.getSession().getAttribute("user");
		// ��ȡ����
		String province = request.getParameter("province");
		String city = request.getParameter("city");
		String district = request.getParameter("district");
		String detailAddress = request.getParameter("detailAddress");
		String zipcode = request.getParameter("zipcode");
		String name = request.getParameter("name");
		String telephone = request.getParameter("telephone");
		
		String address =  province+"ʡ"+city+"��"+district+"��/��"+detailAddress+" �ʱ�:"+zipcode+
				" �ջ��ˣ�"+name+" ��ϵ��ʽ��"+telephone;
		
		orderService.submitOrder(address,user.getId());
		
		// �ض���findAllOrders servlet  
		try {
			response.sendRedirect(request.getContextPath()+"/orderServlet?methodName=findAllOrders");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void findAllOrders(HttpServletRequest request,HttpServletResponse response){
		User  user =(User) request.getSession().getAttribute("user");
		
		int uid = user.getId();
		List<Order> ordersList =orderService.findAllOrders(uid);
		
		request.setAttribute("ordersList", ordersList);
		// ת���� orders.jsp
		try {
			request.getRequestDispatcher("/orders.jsp").forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	/**
	 * ֧������-��ʾ��������  
	 * ����  orders  OrderItems  good ������
	 * @param request
	 * @param response
	 */
	public void findOrdersdetail(HttpServletRequest request,HttpServletResponse response){
		// ��ȡ����
		String oid = request.getParameter("id");
		User user = (User)request.getSession().getAttribute("user");
		int uid = user.getId();
//		System.out.println(oid);
		
		// ����service ������  ���붩����id���û���id
		Order order = orderService.findOrdersdetail(uid,oid);
		
		// ������Ӧ
		request.setAttribute("order", order);
		try {
			request.getRequestDispatcher("/orders_detail.jsp").forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
	 * ΢��֧��
	 */
	public void wxpay(HttpServletRequest request,HttpServletResponse response){
		//1.��ȡ��Ҫ֧���Ķ�����
		String oid = request.getParameter("oid");
		//2.����΢��֧����api��ȡ֧���ĵ�ַ
		String code_url = WXPayUtils.wxpay(oid);
		
		System.out.println(code_url);
		
		//3.��֧���ĵ�ַ��Ӧ�������
		try {
			response.getWriter().write(code_url);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void findOrderTradeState(HttpServletRequest request,HttpServletResponse response) throws IOException{
		//1.��ȡ��Ҫ��ѯ�Ķ����ĵ���
		String oid = request.getParameter("oid");
		//2.����΢��֧���Ľӿڲ�ѯ֧����״̬
		String trade_state = WXPayUtils.getWXPayResult(oid);
		System.out.println("֧���Ľ����"+trade_state);
		if("SUCCESS".equals(trade_state)){
			//2��ʾ�Ѿ�֧��
			orderService.updateOrderState(oid, 2);
		}
		//3.��Ӧ�������
		response.getWriter().write(trade_state);
	}
	
	/**
	 * ɾ������
	 */
	public void deleteOrderByOid(HttpServletRequest request,HttpServletResponse response){
		//��ȡ����
		User user =(User) request.getSession().getAttribute("user");
		String oid = request.getParameter("oid");
		// ����service��
		boolean result = orderService.deleteOrderByOid(oid);
		Map<String,String> map = new HashMap<String ,String >();
		
		// ������Ӧ
		if(result){
			// ɾ���ɹ�   ����ֵ����Ϊjson   
			map.put("res", "yes");
			String strJson = JSON.toJSONString(map);
			
			System.out.println(strJson);
			try {
				response.getWriter().write(strJson);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}else {
			// ɾ��ʧ��
			map.put("res", "no");
			String strJson = JSON.toJSONString(map);
			try {
				response.getWriter().write(strJson);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}



