package com.excilys.formation.cdb.model;

import java.util.List;

/**
 * a computer page for pagination
 * @author nbilly
 *
 */
public class Page<T> {
	/**
	 * number of rows per list
	 */
	private static final int NB_ROWS_RETURNED = 10;
	/**
	 * this page number
	 */
	private int pageNumber;
	/**
	 * list of T
	 */
	private List<T> entities;
	/**
	 * number of rows jumped (offset)
	 */
	private int nbRowsJumped;

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public List<T> getEntities() {
		return entities;
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

	public static int getNbRowsReturned() {
		return NB_ROWS_RETURNED;
	}

	public Page() {

	}

	public Page(int pageNumber, int nbRowsJumped) {
		super();
		this.pageNumber = pageNumber;
		this.nbRowsJumped = nbRowsJumped;
	}

	

}
