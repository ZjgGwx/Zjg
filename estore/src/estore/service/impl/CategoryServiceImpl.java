package estore.service.impl;

import java.util.List;

import com.alibaba.fastjson.JSON;

import estore.dao.ICategoryDao;
import estore.dao.impl.CategoryDaoImpl;
import estore.domain.Category;
import estore.service.ICategogyService;
import estore.utils.JedisUtils;
import redis.clients.jedis.Jedis;

public class CategoryServiceImpl implements ICategogyService {
	
	ICategoryDao categoryDao = new CategoryDaoImpl();

	@Override
	public String checkCategory() {
		String strJson = "";
		
		//Long time1 = System.currentTimeMillis();
		// ʹ��redis ������category ������
		Jedis jedis = JedisUtils.getJedis();
		strJson = jedis.get("strJson");
		
		if(strJson==null){
		//  ���redis  ��û�и�����  �ʹ����ݿ��в���    ����dao ������ѯ  
		List<Category> list = categoryDao.checkCategory();
		strJson = JSON.toJSONString(list);
		// �����ݱ��浽redis ��  
		jedis.set("strJson", strJson);
		}
		jedis.close();
		//Long time2 = System.currentTimeMillis();
		//System.out.println(time2-time1);
		
		return strJson;
	}
	
	
	

}
