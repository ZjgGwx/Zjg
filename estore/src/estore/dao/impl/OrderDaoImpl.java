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

	// 事务查询   更新  删除
	QueryRunner queryRunner = new QueryRunner();
	@Override
	public void updateOrders(Order order) {
		// 事务更新订单表  orders  
		Connection conn = JDBCUtils.getConnection();
		String sql = "insert orders values(?,?,?,?,?,?);";
		Object[] params = {order.getId(),order.getUid(),order.getTotalprice(),
							order.getAddress(),order.getStatus(),order.getCreatetime()};
		try {
			queryRunner.update(conn, sql, params);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException("添加商品信息表异常");
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
			throw new RuntimeException("查询订单信息表异常");
		}
		
		return ordersList;
	}
	// 根据id 查找order
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
	 * 查询orderItems  和  good
	 */
	@Override
	public List<OrderItems> findOrderitemsAndGood(String oid) {
		List<OrderItems> orderList = new ArrayList<OrderItems>();
		QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDatasource());
		String sql = "select * from orderitems oi,goods g where oi.gid=g.id and oi.oid=?;";
//		Object[] params = {oid};
		try {
			List<Map<String, Object>> mapList = queryRunner.query(sql, new MapListHandler(), oid);
			// 遍历map   将数据封装到两个实体类中 
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
			throw new RuntimeException("查询商品及详情异常");
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
			throw new RuntimeException("查询商品详情异常");
		}
		
		return orderItems;
	}
	/**
	 * 删除商品详情 (事务)
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
			throw new RuntimeException("删除商品详情异常");
			
		}
	}
	/**
	 * 删除订单(事务)
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
			throw new RuntimeException("删除商品异常");
		}
	}
	/**
	 * 增加商品库存 (事务)
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
			throw new RuntimeException("增加商品库存异常");
		}
		
	}
	@Override
	public void scanOrderTimer() {
		// 扫描订单任务   如果订单超过30 分钟  就将订单修改成已过期  status 的值设置为3
		QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDatasource());
		String sql = "update orders set status = 3 where status=1 and DATE_ADD(createtime,interval 30 minute)<NOW()";
		try {
			queryRunner.update(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException("扫描过期订单异常");
		}
		
		
		
	}

}
