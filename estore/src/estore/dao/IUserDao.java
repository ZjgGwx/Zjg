package estore.dao;

import estore.domain.User;

public interface IUserDao {
	/**
	 * ���绰�����Ƿ�ע�������
	 * @return
	 */
	public User checkPhone(String phone);

	/**
	 * ע��  ��������
	 * @param user
	 * @return
	 */
	public int register(User user);

	/**
	 * ��½
	 * @param phone
	 * @param password
	 * @return
	 */
	public User login(String phone, String password);

}
