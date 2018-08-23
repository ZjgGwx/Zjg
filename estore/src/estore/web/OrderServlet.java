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
		// 从购物车跳转到订单页面   功能:在订单界面显示购物车中的信息 
		// 调用cart  中的购物车查看购物车  将信息返回到order 页面
		User user = (User)request.getSession().getAttribute("user");
		int uid = user.getId();
		List<Cart> cartList = cartService.queryCartByUid(uid);
		System.out.println(cartList);
		
		// 转发到order 页面
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
	// 提交订单
	public void submitOrder(HttpServletRequest request,HttpServletResponse response){
		// 设置编码格式
		
			try {
				request.setCharacterEncoding("utf-8");
				response.setContentType("text/html;charset=utf-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new RuntimeException("设置编码异常");
			}
	
		// 获取uid  
		User user = (User)request.getSession().getAttribute("user");
		// 获取参数
		String province = request.getParameter("province");
		String city = request.getParameter("city");
		String district = request.getParameter("district");
		String detailAddress = request.getParameter("detailAddress");
		String zipcode = request.getParameter("zipcode");
		String name = request.getParameter("name");
		String telephone = request.getParameter("telephone");
		
		String address =  province+"省"+city+"市"+district+"区/县"+detailAddress+" 邮编:"+zipcode+
				" 收货人："+name+" 联系方式："+telephone;
		
		orderService.submitOrder(address,user.getId());
		
		// 重定向findAllOrders servlet  
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
		// 转发到 orders.jsp
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
	 * 支付订单-显示订单详情  
	 * 包括  orders  OrderItems  good 的数据
	 * @param request
	 * @param response
	 */
	public void findOrdersdetail(HttpServletRequest request,HttpServletResponse response){
		// 获取参数
		String oid = request.getParameter("id");
		User user = (User)request.getSession().getAttribute("user");
		int uid = user.getId();
//		System.out.println(oid);
		
		// 调用service 层数据  传入订单的id和用户的id
		Order order = orderService.findOrdersdetail(uid,oid);
		
		// 给出响应
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
	 * 微信支付
	 */
	public void wxpay(HttpServletRequest request,HttpServletResponse response){
		//1.获取需要支付的订单号
		String oid = request.getParameter("oid");
		//2.调用微信支付的api获取支付的地址
		String code_url = WXPayUtils.wxpay(oid);
		
		System.out.println(code_url);
		
		//3.将支付的地址响应给浏览器
		try {
			response.getWriter().write(code_url);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void findOrderTradeState(HttpServletRequest request,HttpServletResponse response) throws IOException{
		//1.获取需要查询的订单的单号
		String oid = request.getParameter("oid");
		//2.调用微信支付的接口查询支付的状态
		String trade_state = WXPayUtils.getWXPayResult(oid);
		System.out.println("支付的结果："+trade_state);
		if("SUCCESS".equals(trade_state)){
			//2表示已经支付
			orderService.updateOrderState(oid, 2);
		}
		//3.响应给浏览器
		response.getWriter().write(trade_state);
	}
	
	/**
	 * 删除订单
	 */
	public void deleteOrderByOid(HttpServletRequest request,HttpServletResponse response){
		//获取参数
		User user =(User) request.getSession().getAttribute("user");
		String oid = request.getParameter("oid");
		// 调用service层
		boolean result = orderService.deleteOrderByOid(oid);
		Map<String,String> map = new HashMap<String ,String >();
		
		// 给出响应
		if(result){
			// 删除成功   返回值类型为json   
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
			// 删除失败
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



