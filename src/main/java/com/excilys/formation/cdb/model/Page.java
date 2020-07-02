package com.excilys.formation.cdb.model;

import java.util.List;

/**
 * a computer or company page for pagination
 * @author nbilly
 *
 */
public class Page<T> {
	/**
	 * number of rows per list
	 */
	private static int nbRowsReturned = 10;
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
	
	private int nbComputerFound;

	public int getPageNumber() {
		return this.pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
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

	public static int getNbRowsReturned() {
		return nbRowsReturned;
	}

	public static void setNbRowsReturned(int nbRowsReturned) {
		Page.nbRowsReturned = nbRowsReturned;
	}

	public int getNbComputerFound() {
		return nbComputerFound;
	}

	public void setNbComputerFound(int nbComputerFound) {
		this.nbComputerFound = nbComputerFound;
	}

	public Page() {
	}

	public Page(int pageNumber, int nbRowsJumped) {
		this.pageNumber = pageNumber;
		this.nbRowsJumped = nbRowsJumped;
	}
}
