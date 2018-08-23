package estore.service;

import java.util.Map;

import estore.domain.User;



public interface IUserService {

	
	// service 接口  用来定义方法
	/**
	 * 检查电话号码是否被注册过    返回值为map 
	 * @param phone
	 * @return
	 */
	public Map<String, String> checkPhone(String phone);

	/**
	 * 发送短信验证码到手机号
	 * @param phone
	 */
	public String sendMessage(String phone);

	/**
	 * 执行注册
	 * @param user
	 * @return
	 */
	public boolean regiset(User user);

	/**
	 * 执行登陆功能
	 * @param phone
	 * @param password
	 * @return
	 */
	public User login(String phone, String password);

	
	
	
}
