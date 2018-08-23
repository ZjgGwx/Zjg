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
		
		// 调用dao层进行查询操作
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
		// 分页的五个参数
		// 1. 当前页
		
		// 2. 每页的大小
		
		// 3. 商品总数量
		int count = goodDao.findGoodCount();
		
		// 4. 商品详情
		List<Good> goodList = goodDao.findGoodByPage((currentPage-1)*pageSize,pageSize);
		
		// 5. 总页数
		
		// 创建map集合用来返回到前端
		Map<String , Object> map = new HashMap<String , Object>();
		map.put("total", count);
		map.put("rows", goodList);
		String jsonString = JSON.toJSONString(map);
		
		return jsonString;
	}

	/**
	 * 从后台中箱数据库添加商品
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
		
		
		// 2. 当前页
		pageBean.setcurrentPage(currentPage);
		// 3. 每页大小
		int pageSize = 5;
		pageBean.setPageSize(pageSize);
		
		// 1. 所有商品
		List<Good> goodsList = goodDao.findAllGoodsByPage((currentPage-1)*pageSize,pageSize);
		pageBean.setGoodList(goodsList);
		
		// 5. 所有数量
		List<Good> goodList2 = goodDao.findAllGoods();
		int totalCount = goodList2.size();
		pageBean.setTotalCount(totalCount);
		
		// 4. 所有页
		int totalSize=(int) Math.ceil(1.0*(totalCount/pageSize));
		pageBean.setTotalSize(totalSize);
		
		return pageBean;
	}
	

}
