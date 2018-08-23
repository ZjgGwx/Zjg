package estore.service.impl;

import java.util.List;

import com.alibaba.fastjson.JSON;

import estore.dao.IPCDDao;
import estore.dao.impl.PCDDaoImpl;
import estore.domain.PCD;
import estore.service.IPCDService;

public class PCDServiceImpl implements IPCDService {

	// 创建dao 层
	 private IPCDDao  pcdDao = new PCDDaoImpl();
	@Override
	public String findPCDByPid(String pid) {
		
		// 调用dao 层 查询省市区
		List<PCD> pcdList = pcdDao.findPCDByPid(pid);
		// 将list 中的数据转换成json
		String strJson = JSON.toJSONString(pcdList);
		
		return strJson;
	}

}
