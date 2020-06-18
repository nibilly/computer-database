package com.excilys.formation.cdb.model;

import java.sql.Date;

import com.excilys.formation.cdb.persistence.DAOCompany;

/**
 * A computer products by a Company
 * @author nbilly
 *
 */
public class Computer {
	/**
	 * identification
	 */
	private long id;
	/**
	 * complete name
	 */
	private String name;
	/**
	 * when this computer can be buy in market
	 */
	private Date introduced;
	/**
	 * when this computer cannot be buy in market
	 */
	private Date discontinued;
	/**
	 * a computer is produced by a company
	 */
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
	 * select from BD
	 * @param id
	 * @param name
	 * @param introduced
	 * @param discontinued
	 * @param companyId
	 */
	public Computer(long id, String name, Date introduced, Date discontinued, long companyId, String companyName) {
		super();
		this.id = id;
		this.name = name;
		this.introduced = introduced;
		this.discontinued = discontinued;
		this.company = new Company(companyId, companyName);
	}
	
	/**
	 * select from software(without id), go find the company in BD from companyId
	 * @param name
	 * @param introduced
	 * @param discontinued
	 * @param companyId
	 */
	public Computer(String name, Date introduced, Date discontinued, long companyId) {
		super();
		this.name = name;
		this.introduced = introduced;
		this.discontinued = discontinued;
		Company company;
		try {
			company = DAOCompany.findById(companyId);
		} catch (NoResultException e) {
			company = null;
		}
		this.company = company;
	}
	
	

	@Override
	public String toString() {
		return "Computer [id=" + this.id + ", name=" + this.name + ", introduced=" + this.introduced + ", discontinued=" + this.discontinued
				+ ", company=" + this.company + "]";
	}

}
