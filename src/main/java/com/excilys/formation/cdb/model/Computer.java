package com.excilys.formation.cdb.model;

import java.time.LocalDate;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.excilys.formation.cdb.service.CompanyService;

/**
 * A computer products by a Company
 * 
 * @author nbilly
 *
 */
public class Computer {
	private static CompanyService companyService = (CompanyService) new ClassPathXmlApplicationContext("beans.xml")
			.getBean("companyService");

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
	private LocalDate introduced;
	/**
	 * when this computer cannot be buy in market
	 */
	private LocalDate discontinued;
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

	public LocalDate getIntroduced() {
		return introduced;
	}

	public void setIntroduced(LocalDate introduced) {
		this.introduced = introduced;
	}

	public LocalDate getDiscontinued() {
		return discontinued;
	}

	public void setDiscontinued(LocalDate discontinued) {
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
	 * 
	 * @param id
	 * @param name
	 * @param introduced
	 * @param discontinued
	 * @param companyId
	 * @param companyName
	 */
	public Computer(long id, String name, LocalDate introduced, LocalDate discontinued, long companyId,
			String companyName) {
		this.id = id;
		this.name = name;
		this.introduced = introduced;
		this.discontinued = discontinued;
		this.company = new Company(companyId, companyName);
	}

	/**
	 * select from software(without id), go find the company in BD from companyId
	 * 
	 * @param name
	 * @param introduced
	 * @param discontinued
	 * @param companyId
	 */
	public Computer(String name, LocalDate introduced, LocalDate discontinued, long companyId) {
		this.name = name;
		this.introduced = introduced;
		this.discontinued = discontinued;
		this.company = companyService.findById(companyId);
	}

	public Computer(Computer other) {
		this(other.getId(), other.getName(), other.getIntroduced(), other.getDiscontinued(), other.getCompany().getId(),
				other.getCompany().getName());
	}

	@Override
	public String toString() {
		return "Computer [id=" + this.id + ", name=" + this.name + ", introduced=" + this.introduced + ", discontinued="
				+ this.discontinued + ", company=" + this.company + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((company == null) ? 0 : company.hashCode());
		result = prime * result + ((discontinued == null) ? 0 : discontinued.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((introduced == null) ? 0 : introduced.hashCode());
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
		Computer other = (Computer) obj;
		if (company == null) {
			if (other.company != null)
				return false;
		} else if (!company.equals(other.company))
			return false;
		if (discontinued == null) {
			if (other.discontinued != null)
				return false;
		} else if (!discontinued.equals(other.discontinued))
			return false;
		if (id != other.id)
			return false;
		if (introduced == null) {
			if (other.introduced != null)
				return false;
		} else if (!introduced.equals(other.introduced))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

}
