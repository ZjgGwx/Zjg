package estore.dao;

import java.util.List;

import estore.domain.Order;
import estore.domain.OrderItems;

public interface IOrderDao {

	public void updateOrders(Order order);

	public List<Order> findAllOrders(int uid);

	public Order findOrderById(String oid);

	public List<OrderItems> findOrderitemsAndGood(String oid);

	public void updateOrderStatus(String oid, int i);

	public OrderItems findNum(String oid);

	public void deleteOrederItems(String oid);

	public void deleteOrderItems(String oid);

	public void addNum(int gid, int buynum);

	public void scanOrderTimer();

}
