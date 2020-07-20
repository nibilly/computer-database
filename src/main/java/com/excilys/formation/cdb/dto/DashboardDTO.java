package com.excilys.formation.cdb.dto;

public class DashboardDTO {
	private String search;
	private String page;
	private String orderBy;
	private String nbRowsReturned;

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public String getNbRowsReturned() {
		return nbRowsReturned;
	}

	public void setNbRowsReturned(String nbRowsReturned) {
		this.nbRowsReturned = nbRowsReturned;
	}

}
