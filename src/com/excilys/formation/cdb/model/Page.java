package com.excilys.formation.cdb.model;

import java.util.List;

import com.excilys.formation.cdb.persistence.DAOComputer;

/**
 * a computer page for pagination
 * @author nbilly
 *
 */
public class Page {
	/**
	 * number total of computers
	 */
	private static int nbTotalComputers;
	/**
	 * this page number
	 */
	private int pageNumber;
	/**
	 * computers
	 */
	private List<Computer> computers;

	public static int getNbTotalComputers() {
		nbTotalComputers = DAOComputer.getNbComputers();
		return nbTotalComputers;
	}

	public static void setNbTotalComputers(int nbTotalComputers) {
		Page.nbTotalComputers = nbTotalComputers;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public List<Computer> getComputers() {
		return computers;
	}

	public void setComputers(List<Computer> computers) {
		this.computers = computers;
	}

	public Page() {

	}
	
	/**
	 * Create a page
	 * @param pageNumber
	 * @param computers
	 */
	public Page(int pageNumber, List<Computer> computers) {
		this.pageNumber = pageNumber;
		this.computers = computers;
	}

}
