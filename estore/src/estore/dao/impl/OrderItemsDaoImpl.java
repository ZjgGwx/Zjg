package estore.dao.impl;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;

import estore.dao.IOrderItemsDao;
import estore.domain.OrderItems;
import estore.utils.JDBCUtils;

public class OrderItemsDaoImpl implements IOrderItemsDao {

	QueryRunner queryRunner = new QueryRunner();
	
	// 事务更新商品详情表
	@Override
	public void updateOrderItems(OrderItems orderItems) {
		Connection conn = JDBCUtils.getConnection();
		String sql = "insert into orderitems values(?,?,?)";
		Object [] params = {orderItems.getOid(),orderItems.getGid(),orderItems.getBuynum()};
		try {
			queryRunner.update(conn,sql, params);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException("事务更新商品详情异常");
		}
		
	}

}
