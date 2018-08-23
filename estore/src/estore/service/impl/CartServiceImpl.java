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
		// 1.根据uid 和 gid 来查询购物车中是否有该类型的商品  
		Cart cart = cartDao.findCartByuidAndgid(uid,gid);
		if(cart == null){
			// 2.如果没有 就插入一条新的数据   传递一个cart  类型的参数
			cart  = new Cart();
			cart.setBuynum(1);
			cart.setGid(gid);
			cart.setUid(uid);
			cartDao.save(cart);
			
		}else {
			// 3. 如果有数据  说明cart 不为空   就更新 直接将 数量加一  
			cart.setBuynum(cart.getBuynum()+1);
			cartDao.update(cart);
		}
	}
	@Override
	public List<Cart> queryCartByUid(int uid) {
		//查看购物车  调用dao 层进行数据查找
		List<Cart> cartList = cartDao.queryCart(uid);
		return cartList;
	}
	/**
	 * 更新购物车
	 */
	@Override
	public String updateCart(Cart cart) {
		// 直接调用dao层的更新方法  来将数据更新
		cartDao.update(cart);
		
		int uid = cart.getUid();
		int gid = cart.getGid();
		// 查看购物车信息
		List<Cart> cartList = cartDao.queryCart(uid);
		double littleTotal = 0.0;
		double saveTotal = 0.0;
		double currentTotal = 0.0;
		for (Cart cart2 : cartList) {
			//所有商品的小计 = （商品的本店价*购买数量）  的累加
			littleTotal += cart2.getBuynum()*cart2.getGood().getEstoreprice();
			//所有商品的总节省金额 = 商品购买的数量*(商品的市场价-商品的本店价)   的累加
			saveTotal += cart2.getBuynum()*(cart2.getGood().getMarketprice()-cart2.getGood().getEstoreprice());
		
			//当前商品的小计   = 当前修改的商品     的购买数量*本店价
			if(gid==cart2.getGood().getId()){
				
				currentTotal = cart2.getGood().getEstoreprice()*cart2.getBuynum();
			}
		}
		
		String json = "";
		//声明一个map集合来保存数据，方便json的转换
		Map<String, String> map = new HashMap<String, String>();
		map.put("littleTotal", littleTotal+"");
		map.put("saveTotal", saveTotal+"");
		map.put("currentTotal",currentTotal+"");
		json = JSON.toJSONString(map);
//		System.out.println(json);
		return json;

	}
	/**
	 * 根据uid  和gid  删除用户的订单
	 */
	@Override
	public String deleteCartGoodByGid(int uid, int gid) {
		// 调用dao 层  删除数据库数据
		cartDao.deleteCartGoodByGid(uid,gid);
		// 调用dao 层查询购物车 功能  计算删除过后的商品小计  商品总价  用json 数据的格式返回  
		List<Cart> cartList = cartDao.queryCart(uid);
		double littleTotal = 0.0;
		double saveTotal = 0.0;
		double currentTotal = 0.0;
		for (Cart cart2 : cartList) {
			//所有商品的小计 = （商品的本店价*购买数量）  的累加
			littleTotal += cart2.getBuynum()*cart2.getGood().getEstoreprice();
			//所有商品的总节省金额 = 商品购买的数量*(商品的市场价-商品的本店价)   的累加
			saveTotal += cart2.getBuynum()*(cart2.getGood().getMarketprice()-cart2.getGood().getEstoreprice());
		
			//当前商品的小计   = 当前修改的商品     的购买数量*本店价
			if(gid==cart2.getGood().getId()){
				
				currentTotal = cart2.getGood().getEstoreprice()*cart2.getBuynum();
			}
		}
		
		String json = "";
		//声明一个map集合来保存数据，方便json的转换
		Map<String, String> map = new HashMap<String, String>();
		map.put("littleTotal", littleTotal+"");
		map.put("saveTotal", saveTotal+"");
		map.put("currentTotal",currentTotal+"");
		json = JSON.toJSONString(map);
//		System.out.println(json);
		return json;
		
	}

}
