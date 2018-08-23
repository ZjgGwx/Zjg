package estore.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;

import estore.dao.ICartDao;
import estore.dao.impl.CartDaoImpl;
import estore.domain.Cart;
import estore.service.ICartService;

public class CartServiceImpl implements ICartService {

	ICartDao cartDao = new CartDaoImpl();
	@Override
	public void addCart(int uid, int gid) {
		// 1.����uid �� gid ����ѯ���ﳵ���Ƿ��и����͵���Ʒ  
		Cart cart = cartDao.findCartByuidAndgid(uid,gid);
		if(cart == null){
			// 2.���û�� �Ͳ���һ���µ�����   ����һ��cart  ���͵Ĳ���
			cart  = new Cart();
			cart.setBuynum(1);
			cart.setGid(gid);
			cart.setUid(uid);
			cartDao.save(cart);
			
		}else {
			// 3. ���������  ˵��cart ��Ϊ��   �͸��� ֱ�ӽ� ������һ  
			cart.setBuynum(cart.getBuynum()+1);
			cartDao.update(cart);
		}
	}
	@Override
	public List<Cart> queryCartByUid(int uid) {
		//�鿴���ﳵ  ����dao ��������ݲ���
		List<Cart> cartList = cartDao.queryCart(uid);
		return cartList;
	}
	/**
	 * ���¹��ﳵ
	 */
	@Override
	public String updateCart(Cart cart) {
		// ֱ�ӵ���dao��ĸ��·���  �������ݸ���
		cartDao.update(cart);
		
		int uid = cart.getUid();
		int gid = cart.getGid();
		// �鿴���ﳵ��Ϣ
		List<Cart> cartList = cartDao.queryCart(uid);
		double littleTotal = 0.0;
		double saveTotal = 0.0;
		double currentTotal = 0.0;
		for (Cart cart2 : cartList) {
			//������Ʒ��С�� = ����Ʒ�ı����*����������  ���ۼ�
			littleTotal += cart2.getBuynum()*cart2.getGood().getEstoreprice();
			//������Ʒ���ܽ�ʡ��� = ��Ʒ���������*(��Ʒ���г���-��Ʒ�ı����)   ���ۼ�
			saveTotal += cart2.getBuynum()*(cart2.getGood().getMarketprice()-cart2.getGood().getEstoreprice());
		
			//��ǰ��Ʒ��С��   = ��ǰ�޸ĵ���Ʒ     �Ĺ�������*�����
			if(gid==cart2.getGood().getId()){
				
				currentTotal = cart2.getGood().getEstoreprice()*cart2.getBuynum();
			}
		}
		
		String json = "";
		//����һ��map�������������ݣ�����json��ת��
		Map<String, String> map = new HashMap<String, String>();
		map.put("littleTotal", littleTotal+"");
		map.put("saveTotal", saveTotal+"");
		map.put("currentTotal",currentTotal+"");
		json = JSON.toJSONString(map);
//		System.out.println(json);
		return json;

	}
	/**
	 * ����uid  ��gid  ɾ���û��Ķ���
	 */
	@Override
	public String deleteCartGoodByGid(int uid, int gid) {
		// ����dao ��  ɾ�����ݿ�����
		cartDao.deleteCartGoodByGid(uid,gid);
		// ����dao ���ѯ���ﳵ ����  ����ɾ���������ƷС��  ��Ʒ�ܼ�  ��json ���ݵĸ�ʽ����  
		List<Cart> cartList = cartDao.queryCart(uid);
		double littleTotal = 0.0;
		double saveTotal = 0.0;
		double currentTotal = 0.0;
		for (Cart cart2 : cartList) {
			//������Ʒ��С�� = ����Ʒ�ı����*����������  ���ۼ�
			littleTotal += cart2.getBuynum()*cart2.getGood().getEstoreprice();
			//������Ʒ���ܽ�ʡ��� = ��Ʒ���������*(��Ʒ���г���-��Ʒ�ı����)   ���ۼ�
			saveTotal += cart2.getBuynum()*(cart2.getGood().getMarketprice()-cart2.getGood().getEstoreprice());
		
			//��ǰ��Ʒ��С��   = ��ǰ�޸ĵ���Ʒ     �Ĺ�������*�����
			if(gid==cart2.getGood().getId()){
				
				currentTotal = cart2.getGood().getEstoreprice()*cart2.getBuynum();
			}
		}
		
		String json = "";
		//����һ��map�������������ݣ�����json��ת��
		Map<String, String> map = new HashMap<String, String>();
		map.put("littleTotal", littleTotal+"");
		map.put("saveTotal", saveTotal+"");
		map.put("currentTotal",currentTotal+"");
		json = JSON.toJSONString(map);
//		System.out.println(json);
		return json;
		
	}

}
