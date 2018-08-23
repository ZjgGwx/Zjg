package estore.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import estore.dao.ICategoryDao;
import estore.domain.Category;
import estore.utils.JDBCUtils;

public class CategoryDaoImpl implements ICategoryDao {

	QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDatasource());
	@Override
	public List<Category> checkCategory() {
		
		String sql = "select * from category";
		List<Category> list = null;
		try {
			list = queryRunner.query(sql, new BeanListHandler<Category>(Category.class));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return list;
	}

}
