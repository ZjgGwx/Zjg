package estore.service.impl;


import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import estore.dao.ICartDao;
import estore.dao.IGoodDao;
import estore.dao.IOrderDao;
import estore.dao.IOrderItemsDao;
import estore.dao.impl.CartDaoImpl;
import estore.dao.impl.GoodDaoImpl;
import estore.dao.impl.OrderDaoImpl;
import estore.dao.impl.OrderItemsDaoImpl;
import estore.domain.Cart;
import estore.domain.Order;
import estore.domain.OrderItems;
import estore.service.IOrderService;
import estore.utils.JDBCUtils;
import estore.utils.UUIDUtils;

public class OrderServiceImpl implements IOrderService {

	// 创建dao 层 
	private IOrderDao orderDao = new OrderDaoImpl();
	private ICartDao cartDao = new CartDaoImpl();
	private IGoodDao goodDao = new GoodDaoImpl();
	private IOrderItemsDao orderItemsDao = new OrderItemsDaoImpl();
	@Override
	public void submitOrder(String address, int uid) {
		// 因为是连续的操作 ,所以要用事务的方式
		// 1. 开启事务
		Connection conn = JDBCUtils.getConnection();
		try {
			conn.setAutoCommit(false);
			
			// 1. 添加商品订单信息
				// 查找需要的参数  商品总价从购物车中查找
			List<Cart> cartList = cartDao.queryCart(uid);
			double totalprice = 0.0;
			for (Cart cart : cartList) {
				totalprice+= cart.getBuynum()*cart.getGood().getEstoreprice();
			}
				// 创建一个order 来封装数据
			Order order = new Order();
			order.setId(UUIDUtils.getUUID());
			order.setUid(uid);
			order.setTotalprice(totalprice);
			order.setAddress(address);
			order.setStatus(1);// 1.表示代付款  2. 表示已付款  3. 表示已过期
			order.setCreatetime(new Date());
				// 调用dao 层 来添加order 信息
			orderDao.updateOrders(order);
			
			// 2. 修改商品内存
				// 从goods 表中  将商品库存减掉
			for (Cart cart : cartList) {
				int gid =cart.getGood().getId();
				int num = cart.getGood().getNum()-cart.getBuynum();
				goodDao.updateNum(gid,num);
				
				// 3. 添加商品订单详情  (主要是用来查看买的什么商品   数量是多少)
				
					// 创建商品订单详情对象
				OrderItems orderItems = new OrderItems();
				orderItems.setOid(order.getId());
				orderItems.setGid(gid);
				orderItems.setBuynum(cart.getBuynum());
				orderItemsDao.updateOrderItems(orderItems);
			}
			
			
			// 4. 清空购物车
				// 根据uid 清空购物车
			cartDao.cleanCartByUid(uid);
			
			// 提交事务
			conn.commit();
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			// 事务回滚
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}finally{
			// 关闭资源
			JDBCUtils.release();
		}
		
	}
	@Override
	public List<Order> findAllOrders(int uid) {
		// 调用dao 层  进行查询
		List<Order> ordersList = orderDao.findAllOrders(uid);
		
		return ordersList;
	}
	// 读取订单详情
	@Override
	public Order findOrdersdetail(int uid, String oid) {
		// 1. 首先根据查询order
		Order order = orderDao.findOrderById(oid);
		
		// 2. 查询 good 和orderitems 联合查询
		List<OrderItems> orderItemsList = orderDao.findOrderitemsAndGood(oid);
		
		//3. 将orderItemsList 封装到order 中
		order.setOiList(orderItemsList);
		
		return order;
	}
	/**
	 * 支付成功  修改数据库中order 表的订单状态
	 */
	@Override
	public void updateOrderState(String oid, int i) {
		// TODO Auto-generated method stub
		
		orderDao.updateOrderStatus(oid,i);
		
	}
	/**
	 * 删除订单   事务删除
	 */
	@Override
	public boolean deleteOrderByOid(String oid) {
		// 开启事务
		Connection conn = JDBCUtils.getConnection();
		boolean flag = false;
		try {
			conn.setAutoCommit(false);
			
			// 查询购买的数量 和id
			OrderItems orderItems = orderDao.findNum(oid);
			int gid = orderItems.getGid();
			int buynum= orderItems.getBuynum();
			
			// 1. 删除商品详情
			orderDao.deleteOrederItems(oid);
			
			// 2. 删除商品订单
			orderDao.deleteOrderItems(oid);
			
			// 3. 增加库存
			orderDao.addNum(gid,buynum);
			
			conn.commit();
			flag = true;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				conn.rollback();
				flag = false;
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				flag = false;
			}
		}finally {
			JDBCUtils.release();
		}
		return flag;
	}
	/**
	 * 扫描订单是否过期的任务
	 */
	@Override
	public void scanOrderTimer() {
		
		orderDao.scanOrderTimer();
		
	}
	

}
