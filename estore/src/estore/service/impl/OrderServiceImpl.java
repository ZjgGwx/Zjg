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

	// ����dao �� 
	private IOrderDao orderDao = new OrderDaoImpl();
	private ICartDao cartDao = new CartDaoImpl();
	private IGoodDao goodDao = new GoodDaoImpl();
	private IOrderItemsDao orderItemsDao = new OrderItemsDaoImpl();
	@Override
	public void submitOrder(String address, int uid) {
		// ��Ϊ�������Ĳ��� ,����Ҫ������ķ�ʽ
		// 1. ��������
		Connection conn = JDBCUtils.getConnection();
		try {
			conn.setAutoCommit(false);
			
			// 1. �����Ʒ������Ϣ
				// ������Ҫ�Ĳ���  ��Ʒ�ܼ۴ӹ��ﳵ�в���
			List<Cart> cartList = cartDao.queryCart(uid);
			double totalprice = 0.0;
			for (Cart cart : cartList) {
				totalprice+= cart.getBuynum()*cart.getGood().getEstoreprice();
			}
				// ����һ��order ����װ����
			Order order = new Order();
			order.setId(UUIDUtils.getUUID());
			order.setUid(uid);
			order.setTotalprice(totalprice);
			order.setAddress(address);
			order.setStatus(1);// 1.��ʾ������  2. ��ʾ�Ѹ���  3. ��ʾ�ѹ���
			order.setCreatetime(new Date());
				// ����dao �� �����order ��Ϣ
			orderDao.updateOrders(order);
			
			// 2. �޸���Ʒ�ڴ�
				// ��goods ����  ����Ʒ������
			for (Cart cart : cartList) {
				int gid =cart.getGood().getId();
				int num = cart.getGood().getNum()-cart.getBuynum();
				goodDao.updateNum(gid,num);
				
				// 3. �����Ʒ��������  (��Ҫ�������鿴���ʲô��Ʒ   �����Ƕ���)
				
					// ������Ʒ�����������
				OrderItems orderItems = new OrderItems();
				orderItems.setOid(order.getId());
				orderItems.setGid(gid);
				orderItems.setBuynum(cart.getBuynum());
				orderItemsDao.updateOrderItems(orderItems);
			}
			
			
			// 4. ��չ��ﳵ
				// ����uid ��չ��ﳵ
			cartDao.cleanCartByUid(uid);
			
			// �ύ����
			conn.commit();
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			// ����ع�
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}finally{
			// �ر���Դ
			JDBCUtils.release();
		}
		
	}
	@Override
	public List<Order> findAllOrders(int uid) {
		// ����dao ��  ���в�ѯ
		List<Order> ordersList = orderDao.findAllOrders(uid);
		
		return ordersList;
	}
	// ��ȡ��������
	@Override
	public Order findOrdersdetail(int uid, String oid) {
		// 1. ���ȸ��ݲ�ѯorder
		Order order = orderDao.findOrderById(oid);
		
		// 2. ��ѯ good ��orderitems ���ϲ�ѯ
		List<OrderItems> orderItemsList = orderDao.findOrderitemsAndGood(oid);
		
		//3. ��orderItemsList ��װ��order ��
		order.setOiList(orderItemsList);
		
		return order;
	}
	/**
	 * ֧���ɹ�  �޸����ݿ���order ��Ķ���״̬
	 */
	@Override
	public void updateOrderState(String oid, int i) {
		// TODO Auto-generated method stub
		
		orderDao.updateOrderStatus(oid,i);
		
	}
	/**
	 * ɾ������   ����ɾ��
	 */
	@Override
	public boolean deleteOrderByOid(String oid) {
		// ��������
		Connection conn = JDBCUtils.getConnection();
		boolean flag = false;
		try {
			conn.setAutoCommit(false);
			
			// ��ѯ��������� ��id
			OrderItems orderItems = orderDao.findNum(oid);
			int gid = orderItems.getGid();
			int buynum= orderItems.getBuynum();
			
			// 1. ɾ����Ʒ����
			orderDao.deleteOrederItems(oid);
			
			// 2. ɾ����Ʒ����
			orderDao.deleteOrderItems(oid);
			
			// 3. ���ӿ��
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
	 * ɨ�趩���Ƿ���ڵ�����
	 */
	@Override
	public void scanOrderTimer() {
		
		orderDao.scanOrderTimer();
		
	}
	

}
