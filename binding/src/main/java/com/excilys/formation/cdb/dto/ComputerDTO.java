package com.excilys.formation.cdb.dto;

public class ComputerDTO {
	private String id;
	/**
	 * complete name
	 */
	private String name;
	/**
	 * when this computer can be buy in market
	 */
	private String introduced;
	/**
	 * when this computer cannot be buy in market
	 */
	private String discontinued;
	/**
	 * a computer is produced by a company which have an id
	 */
	private CompanyDTO companyDTO;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIntroduced() {
		return introduced;
	}

	public void setIntroduced(String introduced) {
		this.introduced = introduced;
	}

	public String getDiscontinued() {
		return discontinued;
	}

	public void setDiscontinued(String discontinued) {
		this.discontinued = discontinued;
	}

	public CompanyDTO getCompanyDTO() {
		return companyDTO;
	}

	public void setCompanyDTO(CompanyDTO companyDTO) {
		this.companyDTO = companyDTO;
	}

	public ComputerDTO(String id, String name, String introduced, String discontinued, CompanyDTO companyDTO) {
		this.id = id;
		this.name = name;
		this.introduced = introduced;
		this.discontinued = discontinued;
		this.companyDTO = companyDTO;
	}

	public ComputerDTO() {
	}

}
