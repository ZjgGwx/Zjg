package estore.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import estore.dao.IPCDDao;
import estore.domain.PCD;
import estore.utils.JDBCUtils;

public class PCDDaoImpl implements IPCDDao {

	QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDatasource());
	@Override
	public List<PCD> findPCDByPid(String pid) {
		String sql = "select * from province_city_district where pid=?;";
		Object[] params = {pid};
		List<PCD> pcdList = null;
		try {
			pcdList = queryRunner.query(sql, new BeanListHandler<PCD>(PCD.class), params);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return pcdList;
	}

}
