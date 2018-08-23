package estore.dao;

import estore.domain.User;

public interface IUserDao {
	/**
	 * 检查电话号码是否被注册过方法
	 * @return
	 */
	public User checkPhone(String phone);

	/**
	 * 注册  插入数据
	 * @param user
	 * @return
	 */
	public int register(User user);

	/**
	 * 登陆
	 * @param phone
	 * @param password
	 * @return
	 */
	public User login(String phone, String password);

}
