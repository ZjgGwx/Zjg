package estore.dao.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;

import estore.dao.IOrderDao;
import estore.domain.Good;
import estore.domain.Order;
import estore.domain.OrderItems;
import estore.utils.JDBCUtils;

public class OrderDaoImpl implements IOrderDao {

	// �����ѯ   ����  ɾ��
	QueryRunner queryRunner = new QueryRunner();
	@Override
	public void updateOrders(Order order) {
		// ������¶�����  orders  
		Connection conn = JDBCUtils.getConnection();
		String sql = "insert orders values(?,?,?,?,?,?);";
		Object[] params = {order.getId(),order.getUid(),order.getTotalprice(),
							order.getAddress(),order.getStatus(),order.getCreatetime()};
		try {
			queryRunner.update(conn, sql, params);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException("�����Ʒ��Ϣ���쳣");
		}
	}
	@Override
	public List<Order> findAllOrders(int uid) {
		QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDatasource());
		String sql = "select * from orders where uid = ?";
		Object [] params = {uid};
		List<Order> ordersList = null;
		try {
			ordersList = queryRunner.query(sql, new BeanListHandler<Order>(Order.class), params);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException("��ѯ������Ϣ���쳣");
		}
		
		return ordersList;
	}
	// ����id ����order
	@Override
	public Order findOrderById(String oid) {
		QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDatasource());
		
		String sql = "select * from orders where id =?;";
		Object[] params = {oid};
		Order order = null;
		try {
		 order = queryRunner.query(sql, new BeanHandler<Order>(Order.class), params);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return order;
	}
	/**
	 * ��ѯorderItems  ��  good
	 */
	@Override
	public List<OrderItems> findOrderitemsAndGood(String oid) {
		List<OrderItems> orderList = new ArrayList<OrderItems>();
		QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDatasource());
		String sql = "select * from orderitems oi,goods g where oi.gid=g.id and oi.oid=?;";
//		Object[] params = {oid};
		try {
			List<Map<String, Object>> mapList = queryRunner.query(sql, new MapListHandler(), oid);
			// ����map   �����ݷ�װ������ʵ������ 
			for (Map<String, Object> map : mapList) {
				OrderItems orderItems = new OrderItems();
				Good good = new Good();
				BeanUtils.populate(good, map);
				BeanUtils.populate(orderItems, map);
				orderItems.setGood(good);
				orderList.add(orderItems);
				
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException("��ѯ��Ʒ�������쳣");
		}
		return orderList;
	}
	@Override
	public void updateOrderStatus(String oid, int i) {
		QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDatasource());
		
		String sql = "update orders set status =? where id=?;";
		Object[] params = {i,oid};
		try {
			queryRunner.update(sql, params);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	@Override
	public OrderItems findNum(String oid) {
		QueryRunner queryRunner = new QueryRunner();
		Connection conn = JDBCUtils.getConnection();
		String sql = "select * from orderitems where oid = ?;";
		Object[] params = {oid};
		OrderItems orderItems =null;
		try {
			orderItems =queryRunner.query(conn,sql, new BeanHandler<OrderItems>(OrderItems.class), params);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException("��ѯ��Ʒ�����쳣");
		}
		
		return orderItems;
	}
	/**
	 * ɾ����Ʒ���� (����)
	 */
	@Override
	public void deleteOrederItems(String oid) {
		
		QueryRunner queryRunner = new QueryRunner();
		Connection conn = JDBCUtils.getConnection();
		String sql = "delete from orderitems where oid = ?;";
		Object [] params = {oid};
		try {
			queryRunner.update(conn, sql, params);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException("ɾ����Ʒ�����쳣");
			
		}
	}
	/**
	 * ɾ������(����)
	 */
	@Override
	public void deleteOrderItems(String oid) {
		QueryRunner queryRunner = new QueryRunner();
		Connection conn = JDBCUtils.getConnection();
		String  sql = "delete from orders where id = ?;";
		Object [] params = {oid};
		try {
			queryRunner.update(conn, sql, params);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException("ɾ����Ʒ�쳣");
		}
	}
	/**
	 * ������Ʒ��� (����)
	 */
	@Override
	public void addNum(int gid, int buynum) {
		QueryRunner queryRunner = new QueryRunner();
		Connection conn = JDBCUtils.getConnection();
		
		String sql = "update goods set num=? where id=?;";
		Object [] params = {buynum,gid};
		try {
			queryRunner.update(conn, sql, params);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException("������Ʒ����쳣");
		}
		
	}
	@Override
	public void scanOrderTimer() {
		// ɨ�趩������   �����������30 ����  �ͽ������޸ĳ��ѹ���  status ��ֵ����Ϊ3
		QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDatasource());
		String sql = "update orders set status = 3 where status=1 and DATE_ADD(createtime,interval 30 minute)<NOW()";
		try {
			queryRunner.update(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException("ɨ����ڶ����쳣");
		}
		
		
		
	}

}
