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
		this.id = id;
		this.name = name;
	}
	public Company(Company other) {
		this(other.getId(), other.getName());
	}
	
	@Override
	public String toString() {
		return "Company [id=" + this.id + ", name=" + this.name + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Company other = (Company) obj;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

}
