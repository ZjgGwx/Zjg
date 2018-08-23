package estore.utils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class JDBCUtils {

	//创建一个c3p0数据库连接池
	
	private static ComboPooledDataSource dataSource = new ComboPooledDataSource();
	
	
	//创建一个threadLocal对象
	private static ThreadLocal<Connection> local = new ThreadLocal<Connection>();
	
	public static DataSource getDatasource(){
		return dataSource;
	}
	
	/**
	 * 获取连接,从连接池获取
	 */
	public static Connection getConnection(){
		Connection con = null;
		
		//con对象优先从threadLocal中去获取
		con = local.get();
		if(con==null){
			System.out.println("这是当前线程中第一次获取连接对象，第一次应该从数据库连接池获取");
			//这是当前线程中第一次获取连接对象，第一次应该从数据库连接池获取
			try {
				con = dataSource.getConnection();
				//将con存放到threadLocal中
				local.set(con);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			System.out.println("z这是从threadLocal中获取的连接对象");
		}
		return con;
	}
	
	
	public static void release(Connection con,ResultSet rs,Statement st){
		if(rs!=null){
			try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if(st!=null){
			try {
				st.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if(con!=null){
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	//事务开启
	public static void startTransaction(){
		Connection con = getConnection();
		try {
			con.setAutoCommit(false);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//提交事务
	public static void commit(){
		Connection con = getConnection();
		try {
			con.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//事务回滚
	public static void rollback(){
		Connection con = getConnection();
		try {
			con.rollback();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//释放链接对象
	public static void release(){
		Connection con = getConnection();
		try {
			con.close();
			local.remove();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
