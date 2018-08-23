package estore.service;

import java.util.List;

import estore.domain.Order;

public interface IOrderService {

	public void submitOrder(String address, int id);

	public List<Order> findAllOrders(int uid);

	public Order findOrdersdetail(int uid, String oid);

	public void updateOrderState(String oid, int i);

	public boolean deleteOrderByOid(String oid);

	public void scanOrderTimer();

}
