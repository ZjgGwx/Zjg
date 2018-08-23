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
import org.apache.commons.dbutils.handlers.ScalarHandler;

import estore.dao.IGoodDao;
import estore.domain.Good;
import estore.domain.Hot;
import estore.utils.JDBCUtils;

public class GoodDaoImpl implements IGoodDao {

	QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDatasource());
	@Override
	public List<Good> findAllGoods() {
		String sql = "select * from goods;";
		List<Good> goodList = null;
		try {
			goodList = queryRunner.query(sql, new BeanListHandler<Good>(Good.class));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return goodList;
	}
	@Override
	public Good findGoodDetail(String id) {
		String sql = "select * from goods where id = ?;";
		Object [] params = {id};
		Good good = null;
		 try {
			good = queryRunner.query(sql, new BeanHandler<Good>(Good.class), params);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException("查询商品详细信息异常");
		}
		return good;
	}
	
	// 事务更新商品库存数量
	@Override
	public void updateNum(int gid, int num) {
		QueryRunner qr = new QueryRunner();
		Connection conn = JDBCUtils.getConnection();
		
		String sql = "update goods set num=? where id=?;";
		Object[] params = {num,gid};
		try {
			qr.update(conn, sql, params);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException("更改商品库存异常");
		}
	}
	
	
	@Override
	public List<Good> findGoodByPage(int start, int end) {
		// 分页查询
		
	/*	String sql = "select * from goods limit start,end;";
		Object [] params = {start,end};
		List<Good> query = null;
		try {
			query = queryRunner.query(sql, new BeanListHandler<Good>(Good.class),params);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return query;*/
		String sql = "select * from goods limit ?,?";
		List<Good> query = null;
		try {
			query = queryRunner.query(sql, new BeanListHandler<Good>(Good.class), start,end);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException("分页查询商品信息异常");
		}
		return query;
	}
	
	
	@Override
	public int findGoodCount() {
		String sql ="select count(*) from goods;";
		int count  = 0;
		try {
			Long query = queryRunner.query(sql, new ScalarHandler<Long>());
			count = query.intValue();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return count;
	}
	
	
	@Override
	public void addGood(Good good) {
		String sql ="insert into goods value(null,?,?,?,?,?,?,?)";
		Object [] params ={good.getName(),good.getMarketprice(),good.getEstoreprice(),
				good.getCategory(),good.getNum(),good.getImgurl(),good.getDescription()};
		try {
			queryRunner.update(sql, params);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	@Override
	public List<Hot> findAllGoodsByRm(){
		String sql = "SELECT oi.gid, g.name , SUM(buynum) hot FROM orderitems oi, goods g WHERE oi.gid = "
				+ "g.id GROUP BY oi.gid ORDER BY hot DESC";
		List<Hot> list =new  ArrayList<Hot>();
	
		try {
			List<Map<String, Object>> map = queryRunner.query(sql, new MapListHandler());
			for (Map<String, Object> map2 : map) {
				Hot hot = new Hot();
				BeanUtils.populate(hot, map2);
				list.add(hot);
			}
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	@Override
	public List<Good> findAllGoodsByPage(int i, int pageSize) {
		
		String sql ="select * from goods limit ?,?;";
		Object[] params = {i,pageSize};
		List<Good> goodList =null;
		try {
				goodList = queryRunner.query(sql, new BeanListHandler<Good>(Good.class),params);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return goodList;
	}

}
