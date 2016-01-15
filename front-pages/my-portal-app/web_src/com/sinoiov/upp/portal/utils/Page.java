package com.sinoiov.upp.portal.utils;

import org.springframework.web.accept.HeaderContentNegotiationStrategy;

/**
 * 分页类
 * 
 * @author jichao
 * 
 */
@SuppressWarnings("all")
public class Page {

	public Page(int pageNo, int pageSize) {
		super();
		this.currentPage = pageNo;
		this.pageSize = pageSize;
	}

	public Page() {
		super();
	}

	/**
	 * 当前页数
	 */
	private int currentPage = 1;

	/**
	 * 每页条数
	 */
	private int pageSize = 5;

	/**
	 * 获取当前条数
	 */
	private int beginPage;

	/**
	 * 总条数
	 */
	private long totalItem;

	/**
	 * 总页数
	 */
	private int countPage;

	/**
	 * 分页条显示多少个页码
	 */
	private int showLine = 5;

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	/**
	 * 获取每页条数
	 */
	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * 总条数
	 */
	public long getTotalItem() {
		return totalItem;
	}

	public void setTotalItem(long totalItem) {
		this.totalItem = totalItem;
	}

	/**
	 * 总页数
	 */
	public int getCountPage() {
		return (int) Math.ceil((double) totalItem / pageSize);
	}

	public void setCountPage(int countPage) {
		this.countPage = countPage;
	}

	/**
	 * 获取当前条数
	 */
	public int getBeginPage() {
		return (currentPage - 1) * pageSize;
	}

	public void setBeginPage(int beginPage) {
		this.beginPage = beginPage;
	}

	public int getShowLine() {
		return showLine;
	}

	public void setShowLine(int showLine) {
		this.showLine = showLine;
	}

	/**
	 * 显示的页面开始数
	 */
	public int getShowPageHead() {
		if (this.getCurrentPage() <= this.getShowLine()) {
			if (getCurrentPage() - this.getShowLine() / 2 >= 1) {
				int head = getCurrentPage() - this.getShowLine() / 2;
				if (head + this.getShowLine() > getCountPage()) {
					return getCountPage() - this.getShowLine() >= 1 ? getCountPage() - this.getShowLine() : 1;
				} else
					return head;
			} else {
				return 1;
			}
		} else {
			if ((this.getCurrentPage() + this.getShowLine() / 2) >= this.getCountPage()) {
				int head = (this.getCurrentPage() - this.getShowLine()) >= 1 ? (this.getCurrentPage() - this.getShowLine()) : this.getCurrentPage();
				if (head + getShowLine() < getCountPage()) {
					return getCountPage() - getShowLine();
				} else {
					return head;
				}
			}
			if (this.getCurrentPage() - (this.getShowLine() / 2) < this.getShowLine()) {
				return this.getCurrentPage() - this.getShowLine() / 2;
			} else {

				int head = this.getCurrentPage() - this.getShowLine() / 2;
				if (getCountPage() - head < getShowLine()) {
					return getCountPage() - getShowLine();
				} else {
					return head;
				}

			}

		}

	}

	/**
	 * 显示的页面结尾数
	 */
	public int getShowPageEnd() {
		int head = this.getShowPageHead()-1;
		int end = head + this.getShowLine();
		if (end >= getCountPage()) {
			return this.getCountPage();
		} else {
			return end;
		}
	}

}