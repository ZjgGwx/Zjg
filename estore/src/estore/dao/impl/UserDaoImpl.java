package estore.dao.impl;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import estore.dao.IUserDao;
import estore.domain.User;
import estore.utils.JDBCUtils;

public class UserDaoImpl implements IUserDao{

	QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDatasource());
	
	@Override
	public User checkPhone(String phone) {
		
		String sql = "select * from user where phone = ?";
		User user = null;
		try {
			user = queryRunner.query(sql, new BeanHandler<User>(User.class), phone);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return user;
	}
	
	@Override
	public int register(User user) {
		String  sql = "insert into user value(null,?,?,?,?,?)";
		Object[] params = {user.getEmail(),user.getNickname(),user.getPassword(),user.getRole()
				,user.getPhone()};
		int update =0;
		try {
			update= queryRunner.update(sql, params);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return update;
	}

	@Override
	public User login(String phone, String password) {
		String sql  ="select * from user where phone=? and password =?";
		Object [] params ={phone,password};
		User user= null;
		try {
			user = queryRunner.query(sql, new BeanHandler<User>(User.class), params);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return user;
	}

	
	
}
