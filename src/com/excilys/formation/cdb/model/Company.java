package com.excilys.formation.cdb.model;

/**
 * A company products Computers
 * @author nbilly
 *
 */
public class Company {
	/**
	 * identification
	 */
	private long id;
	/**
	 * complete computer name
	 */
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
	@Override
	public String toString() {
		return "Company [id=" + this.id + ", name=" + this.name + "]";
	}

}
