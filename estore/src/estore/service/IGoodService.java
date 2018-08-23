package estore.service;

import java.util.List;

import estore.domain.Good;
import estore.domain.PageBean;

public interface IGoodService {

	/**
	 * ����������Ʒ
	 * @return
	 */
	public List<Good> findAllGoods();

	/**
	 * ���ҵ�����Ʒ����ϸ��Ϣ
	 * @param id
	 * @return
	 */
	public Good findGoodDetail(String id);

	/**
	 * ��̨����ϵͳ�ķ�ҳ��ѯ
	 * @param pageSize
	 * @param currentPage
	 * @return
	 */
	public String findGoodByPage(int pageSize, int currentPage);

	public void addGood(Good good);

	public String findAllGoodsByRm();

	public PageBean findGoodByPageHead(int currentPage);

}
