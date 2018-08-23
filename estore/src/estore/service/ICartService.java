package estore.service;

import java.util.List;

import estore.domain.Cart;

public interface ICartService {

	public void addCart(int uid, int gid);

	public List<Cart> queryCartByUid(int uid);

	public String updateCart(Cart cart);

	public String deleteCartGoodByGid(int uid, int gid);

}
