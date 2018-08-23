package estore.dao.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;

import estore.dao.ICartDao;
import estore.domain.Cart;
import estore.domain.Good;
import estore.utils.JDBCUtils;

public class CartDaoImpl implements ICartDao {

	QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDatasource());
	@Override
	public Cart findCartByuidAndgid(int uid, int gid) {
		String sql = "select * from cart where uid=? and gid =?;";
		Object [] params = {uid,gid};
		Cart cart =null;
		try {
			cart = queryRunner.query(sql, new BeanHandler<Cart>(Cart.class), params);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException("查询购物车异常");
		}
		return cart;
	}
	/**
	 * 执行增加功能
	 */
	@Override
	public void save(Cart cart) {
		String sql = "insert into cart values (?,?,?);";
		Object [] params = {cart.getUid(),cart.getGid(),cart.getBuynum()};
		try {
			queryRunner.update(sql, params);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException("增加查询购物车异常");
		}
	}
	/**
	 * 执行更新功能
	 */
	@Override
	public void update(Cart cart) {
		String sql = "update cart set buynum = ? where uid=? and gid = ?;";
		Object [] params = {cart.getBuynum(),cart.getUid(),cart.getGid()};
		try {
			queryRunner.update(sql, params);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException("修改查询购物车异常");
		}
	}
	@Override
	public List<Cart> queryCart(int uid) {
		
		// 定义一个list  将cart 和  good 保存
		List<Cart> list = new ArrayList<Cart>();
		String sql ="select * from cart,goods where goods.id=cart.gid and cart.uid = ?;";
		Object [] params = {uid};
		try {
			List<Map<String, Object>> map = queryRunner.query(sql, new MapListHandler(), params);
			for (Map<String, Object> map2 : map) {
				Cart cart = new Cart();
				Good good = new Good();
				BeanUtils.populate(good, map2);
				BeanUtils.populate(cart, map2);
				cart.setGood(good);
				list.add(cart);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	// 执行删除购物车的功能
	@Override
	public void deleteCartGoodByGid(int uid, int gid) {
		
		String sql = "delete  from cart where uid=? and gid=?;";
		try {
			queryRunner.update(sql, uid,gid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	/**
	 * 根据uid  清空购物车信息  (事务删除)
	 */
	@Override
	public void cleanCartByUid(int uid) {
		QueryRunner  qr = new QueryRunner();
		Connection conn = JDBCUtils.getConnection();
		String sql = "delete from cart where uid = ?";
		Object [] params = {uid};
		try {
			qr.update(conn, sql, params);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException("清空购物车异常");
		}
		
		
	}

}
