package estore.dao;

import java.util.List;

import estore.domain.Cart;

public interface ICartDao {

	public Cart findCartByuidAndgid(int uid, int gid);

	public void save(Cart cart);

	public void update(Cart cart);

	public List<Cart> queryCart(int uid);

	public void deleteCartGoodByGid(int uid, int gid);

	public void cleanCartByUid(int uid);

}
