package estore.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;

import estore.dao.IGoodDao;
import estore.dao.impl.GoodDaoImpl;
import estore.domain.Good;
import estore.domain.Hot;
import estore.domain.PageBean;
import estore.service.IGoodService;

public class GoodServiceImpl implements IGoodService {

	IGoodDao goodDao = new GoodDaoImpl();
	@Override
	public List<Good> findAllGoods() {
		
		// ����dao����в�ѯ����
		List<Good> goodList = goodDao.findAllGoods();
		return goodList;
	}

	@Override
	public Good findGoodDetail(String id) {
		Good good = goodDao.findGoodDetail(id);
		return good;
	}

	@Override
	public String findGoodByPage(int pageSize, int currentPage) {
		// ��ҳ���������
		// 1. ��ǰҳ
		
		// 2. ÿҳ�Ĵ�С
		
		// 3. ��Ʒ������
		int count = goodDao.findGoodCount();
		
		// 4. ��Ʒ����
		List<Good> goodList = goodDao.findGoodByPage((currentPage-1)*pageSize,pageSize);
		
		// 5. ��ҳ��
		
		// ����map�����������ص�ǰ��
		Map<String , Object> map = new HashMap<String , Object>();
		map.put("total", count);
		map.put("rows", goodList);
		String jsonString = JSON.toJSONString(map);
		
		return jsonString;
	}

	/**
	 * �Ӻ�̨�������ݿ������Ʒ
	 */
	@Override
	public void addGood(Good good) {
		
		goodDao.addGood(good);
		
	}

	@Override
	public String findAllGoodsByRm() {
		
		List<Hot> list = goodDao.findAllGoodsByRm();
		String json = JSON.toJSONString(list);
		
		return json;
	}

	@Override
	public PageBean findGoodByPageHead(int currentPage) {

		PageBean pageBean = new PageBean();
		
		
		// 2. ��ǰҳ
		pageBean.setcurrentPage(currentPage);
		// 3. ÿҳ��С
		int pageSize = 5;
		pageBean.setPageSize(pageSize);
		
		// 1. ������Ʒ
		List<Good> goodsList = goodDao.findAllGoodsByPage((currentPage-1)*pageSize,pageSize);
		pageBean.setGoodList(goodsList);
		
		// 5. ��������
		List<Good> goodList2 = goodDao.findAllGoods();
		int totalCount = goodList2.size();
		pageBean.setTotalCount(totalCount);
		
		// 4. ����ҳ
		int totalSize=(int) Math.ceil(1.0*(totalCount/pageSize));
		pageBean.setTotalSize(totalSize);
		
		return pageBean;
	}
	

}
