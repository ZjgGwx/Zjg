package estore.service;

import java.util.List;

import estore.domain.Good;
import estore.domain.PageBean;

public interface IGoodService {

	/**
	 * 查找所有商品
	 * @return
	 */
	public List<Good> findAllGoods();

	/**
	 * 查找单个商品的详细信息
	 * @param id
	 * @return
	 */
	public Good findGoodDetail(String id);

	/**
	 * 后台管理系统的分页查询
	 * @param pageSize
	 * @param currentPage
	 * @return
	 */
	public String findGoodByPage(int pageSize, int currentPage);

	public void addGood(Good good);

	public String findAllGoodsByRm();

	public PageBean findGoodByPageHead(int currentPage);

}
