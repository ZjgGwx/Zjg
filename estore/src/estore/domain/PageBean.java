package estore.domain;

import java.util.List;

public class PageBean {
	private int pageSize;
	private int totalCount;
	private int totalSize;
	private int currentPage;
	private List<Good> goodList;
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	public int getTotalSize() {
		return totalSize;
	}
	public void setTotalSize(int totalSize) {
		this.totalSize = totalSize;
	}
	public int getcurrentPage() {
		return currentPage;
	}
	public void setcurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public List<Good> getGoodList() {
		return goodList;
	}
	public void setGoodList(List<Good> goodList) {
		this.goodList = goodList;
	}
	public PageBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	public PageBean(int pageSize, int totalCount, int totalSize, int currentPage, List<Good> goodList) {
		super();
		this.pageSize = pageSize;
		this.totalCount = totalCount;
		this.totalSize = totalSize;
		this.currentPage = currentPage;
		this.goodList = goodList;
	}
	
}
