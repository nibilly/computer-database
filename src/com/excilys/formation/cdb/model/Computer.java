package com.excilys.formation.cdb.model;

import java.sql.Date;

import com.excilys.formation.cdb.persistence.DAOCompany;

public class Computer {
	private long id;
	private String name;
	private Date introduced;
	private Date discontinued;
	private Company company;
	
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

	public Date getIntroduced() {
		return introduced;
	}

	public void setIntroduced(Date introduced) {
		this.introduced = introduced;
	}

	public Date getDiscontinued() {
		return discontinued;
	}

	public void setDiscontinued(Date discontinued) {
		this.discontinued = discontinued;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public Computer() {
	}

	/**
	 * select from BD, go find the company from companyId
	 */
	public Computer(long id, String name, Date introduced, Date discontinued, long companyId) {
		super();
		this.id = id;
		this.name = name;
		this.introduced = introduced;
		this.discontinued = discontinued;
		Company company = DAOCompany.findById(companyId);
		this.company = company;
	}

	/**
	 * Create from software, go find the company from companyId
	 */
	public Computer(String name, Date introduced, Date discontinued, long companyId) {
		super();
		this.name = name;
		this.introduced = introduced;
		this.discontinued = discontinued;
		Company company = DAOCompany.findById(companyId);
		this.company = company;
	}

	@Override
	public String toString() {
		return "Computer [id=" + id + ", name=" + name + ", introduced=" + introduced + ", discontinued=" + discontinued
				+ ", company=" + company + "]";
	}

}
