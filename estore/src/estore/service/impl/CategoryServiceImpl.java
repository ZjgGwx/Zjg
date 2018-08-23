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
		// 使用redis 来保存category 的数据
		Jedis jedis = JedisUtils.getJedis();
		strJson = jedis.get("strJson");
		
		if(strJson==null){
		//  如果redis  中没有该数据  就从数据库中查找    调用dao 层来查询  
		List<Category> list = categoryDao.checkCategory();
		strJson = JSON.toJSONString(list);
		// 将数据保存到redis 中  
		jedis.set("strJson", strJson);
		}
		jedis.close();
		//Long time2 = System.currentTimeMillis();
		//System.out.println(time2-time1);
		
		return strJson;
	}
	
	
	

}
