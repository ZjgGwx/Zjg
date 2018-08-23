package estore.service.impl;

import java.util.List;

import com.alibaba.fastjson.JSON;

import estore.dao.IPCDDao;
import estore.dao.impl.PCDDaoImpl;
import estore.domain.PCD;
import estore.service.IPCDService;

public class PCDServiceImpl implements IPCDService {

	// ����dao ��
	 private IPCDDao  pcdDao = new PCDDaoImpl();
	@Override
	public String findPCDByPid(String pid) {
		
		// ����dao �� ��ѯʡ����
		List<PCD> pcdList = pcdDao.findPCDByPid(pid);
		// ��list �е�����ת����json
		String strJson = JSON.toJSONString(pcdList);
		
		return strJson;
	}

}
