package com.excilys.formation.cdb.model;

import java.util.List;

/**
 * a computer or company page for pagination
 * 
 * @author nbilly
 *
 */
public class Page<T> {
	/**
	 * number of rows per list
	 */
	private int nbRowsReturned;
	public static final int NB_ROWS_RETURNED_DEFAULT = 10;
	/**
	 * this page index started at 0
	 */
	private int pageIndex;
	private int pageNumber;
	/**
	 * list of T
	 */
	private List<T> entities;
	/**
	 * number of rows jumped (offset)
	 */
	private int nbRowsJumped;

	private int nbComputerFound;

	private String search;

	private OrderBy orderBy;

	public int getPageIndex() {
		return this.pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public int getPageNumber() {
		return pageIndex + 1;
	}

	public List<T> getEntities() {
		return this.entities;
	}

	public void setEntities(List<T> entities) {
		this.entities = entities;
	}

	public int getNbRowsJumped() {
		return nbRowsJumped;
	}

	public void setNbRowsJumped(int nbRowsJumped) {
		this.nbRowsJumped = nbRowsJumped;
	}

	public int getNbRowsReturned() {
		return nbRowsReturned;
	}

	public void setNbRowsReturned(int nbRowsReturned) {
		this.nbRowsReturned = nbRowsReturned;
	}

	public int getNbComputerFound() {
		return nbComputerFound;
	}

	public void setNbComputerFound(int nbComputerFound) {
		this.nbComputerFound = nbComputerFound;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public OrderBy getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(OrderBy orderBy) {
		this.orderBy = orderBy;
	}

	public Page() {
	}

	public Page(int pageIndex, int nbRowsReturned) {
		this.pageIndex = pageIndex;
		this.nbRowsReturned = nbRowsReturned;
		this.nbRowsJumped = this.pageIndex * this.nbRowsReturned;
	}

	/**
	 * default nbRowsReturned = 10
	 * 
	 * @param pageIndex
	 * @param nbRowsJumped
	 */
	public Page(int pageIndex) {
		this.pageIndex = pageIndex;
		this.nbRowsReturned = 10;
		this.nbRowsJumped = this.pageIndex * this.nbRowsReturned;
	}
}
