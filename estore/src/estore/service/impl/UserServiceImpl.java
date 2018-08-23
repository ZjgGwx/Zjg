package estore.service.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.apache.catalina.connector.Request;

import com.alibaba.fastjson.JSON;
import com.aliyuncs.exceptions.ClientException;

import estore.dao.IUserDao;
import estore.dao.impl.UserDaoImpl;
import estore.domain.User;
import estore.service.IUserService;
import estore.utils.MessageUtils;


public class UserServiceImpl implements IUserService {
	
	IUserDao userDao = new UserDaoImpl();

	@Override
	public Map<String, String> checkPhone(String phone) {
		
		User user = userDao.checkPhone(phone);
		Map<String,String> map = new HashMap<String,String>();
		if(user!=null){
			// 说明号码已经被人注册过 
			map.put("isExit", "no");
			
		}else{
			// 说明号码可用
			map.put("isExit", "yes");
			
		}
		
		return map;
	}
	
	@Override
	public String  sendMessage(String phone) {
		
		//1.生成四位数字的验证码验证码 
		
		Random r = new Random();
		int checkNum = r.nextInt(9000)+1000;
		String  code = checkNum+"";
		
		// 2. 使用发送短信工具类来实现发送功能
		try {
			MessageUtils.sendSms(phone, code);
		} catch (ClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// 3.将将生成的验证码返回到  servlet 层中  然后保存到session 中
		return  code;
	}

	@Override
	public boolean  regiset(User user) {
		boolean flag = false ;
		// 调用dao 层 将用户信息保存到数据库中 
		int result = userDao.register(user);
		if(result!=0){
			flag = true;
		}
		return flag;
	}

	@Override
	public User login(String phone, String password) {
		// 调用dao层来进行判断
		User user = userDao.login(phone,password);
		
		return user;
	}
	

}























