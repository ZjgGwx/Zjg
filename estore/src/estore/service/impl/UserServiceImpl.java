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
			// ˵�������Ѿ�����ע��� 
			map.put("isExit", "no");
			
		}else{
			// ˵���������
			map.put("isExit", "yes");
			
		}
		
		return map;
	}
	
	@Override
	public String  sendMessage(String phone) {
		
		//1.������λ���ֵ���֤����֤�� 
		
		Random r = new Random();
		int checkNum = r.nextInt(9000)+1000;
		String  code = checkNum+"";
		
		// 2. ʹ�÷��Ͷ��Ź�������ʵ�ַ��͹���
		try {
			MessageUtils.sendSms(phone, code);
		} catch (ClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// 3.�������ɵ���֤�뷵�ص�  servlet ����  Ȼ�󱣴浽session ��
		return  code;
	}

	@Override
	public boolean  regiset(User user) {
		boolean flag = false ;
		// ����dao �� ���û���Ϣ���浽���ݿ��� 
		int result = userDao.register(user);
		if(result!=0){
			flag = true;
		}
		return flag;
	}

	@Override
	public User login(String phone, String password) {
		// ����dao���������ж�
		User user = userDao.login(phone,password);
		
		return user;
	}
	

}























