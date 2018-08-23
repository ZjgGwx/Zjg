package estore.dao;

import java.util.List;

import estore.domain.Good;
import estore.domain.Hot;

public interface IGoodDao {

	public  List<Good> findAllGoods();

	public Good findGoodDetail(String id);

	public void updateNum(int gid, int num);

	public List<Good> findGoodByPage(int start, int end);

	public int findGoodCount();

	public void addGood(Good good);

	public List<Hot> findAllGoodsByRm();

	public List<Good> findAllGoodsByPage(int i, int pageSize);

}
