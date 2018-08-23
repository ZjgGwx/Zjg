package estore.service;

import java.util.Map;

import estore.domain.User;



public interface IUserService {

	
	// service �ӿ�  �������巽��
	/**
	 * ���绰�����Ƿ�ע���    ����ֵΪmap 
	 * @param phone
	 * @return
	 */
	public Map<String, String> checkPhone(String phone);

	/**
	 * ���Ͷ�����֤�뵽�ֻ���
	 * @param phone
	 */
	public String sendMessage(String phone);

	/**
	 * ִ��ע��
	 * @param user
	 * @return
	 */
	public boolean regiset(User user);

	/**
	 * ִ�е�½����
	 * @param phone
	 * @param password
	 * @return
	 */
	public User login(String phone, String password);

	
	
	
}
