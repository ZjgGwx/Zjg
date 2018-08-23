package estore.dao;

import estore.domain.OrderItems;

public interface IOrderItemsDao {
	/**
	 * 执行更新商品详情更新
	 * @param orderItems
	 */
	public void updateOrderItems(OrderItems orderItems);

}
