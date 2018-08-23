package estore.utils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class JDBCUtils {

	//����һ��c3p0���ݿ����ӳ�
	
	private static ComboPooledDataSource dataSource = new ComboPooledDataSource();
	
	
	//����һ��threadLocal����
	private static ThreadLocal<Connection> local = new ThreadLocal<Connection>();
	
	public static DataSource getDatasource(){
		return dataSource;
	}
	
	/**
	 * ��ȡ����,�����ӳػ�ȡ
	 */
	public static Connection getConnection(){
		Connection con = null;
		
		//con�������ȴ�threadLocal��ȥ��ȡ
		con = local.get();
		if(con==null){
			System.out.println("���ǵ�ǰ�߳��е�һ�λ�ȡ���Ӷ��󣬵�һ��Ӧ�ô����ݿ����ӳػ�ȡ");
			//���ǵ�ǰ�߳��е�һ�λ�ȡ���Ӷ��󣬵�һ��Ӧ�ô����ݿ����ӳػ�ȡ
			try {
				con = dataSource.getConnection();
				//��con��ŵ�threadLocal��
				local.set(con);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			System.out.println("z���Ǵ�threadLocal�л�ȡ�����Ӷ���");
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
	
	
	//������
	public static void startTransaction(){
		Connection con = getConnection();
		try {
			con.setAutoCommit(false);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//�ύ����
	public static void commit(){
		Connection con = getConnection();
		try {
			con.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//����ع�
	public static void rollback(){
		Connection con = getConnection();
		try {
			con.rollback();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//�ͷ����Ӷ���
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
