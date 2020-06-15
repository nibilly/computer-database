package com.excilys.formation.cdb.model;

public class Company {
	private long id;
	private String name;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Company() {
	}
	/**
	 * select from BD
	 */
	public Company(long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	/**
	 * Create from software
	 */
	public Company(String name) {
		super();
		this.name = name;
	}
	@Override
	public String toString() {
		return "Company [id=" + id + ", name=" + name + "]";
	}

}
