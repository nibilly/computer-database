package com.excilys.formation.cdb.validation;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import com.excilys.formation.cdb.dto.ComputerDTO;
import com.excilys.formation.cdb.mapper.DateMapper;
import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.service.CompanyService;
import com.excilys.formation.cdb.service.ComputerService;

public class ComputerValidation {
	private ComputerService computerService;
	private CompanyService companyService;

	public ComputerValidation(ComputerService computerService, CompanyService companyService) {
		this.computerService = computerService;
		this.companyService = companyService;
	}

	public Validation validation(ComputerDTO computerDTO, Computer computer) {
		Validation validation = Validation.NO_ERROR;
		LocalDate introducedDate;
		LocalDate discontinuedDate;
		long companyIdLong;
		if ("".equals(computerDTO.getName())) {
			validation = Validation.NAME_ERROR;
		} else {
			computer.setName(computerDTO.getName());
			try {
				introducedDate = (computerDTO.getIntroduced().equals("")) ? null
						: DateMapper.localDateFromString(computerDTO.getIntroduced());
				computer.setIntroduced(introducedDate);
				try {
					discontinuedDate = (computerDTO.getDiscontinued().equals("")) ? null
							: DateMapper.localDateFromString(computerDTO.getDiscontinued());
					computer.setDiscontinued(discontinuedDate);
					if (introducedDate != null && discontinuedDate != null) {
						if (introducedDate.compareTo(discontinuedDate) >= 0) {
							validation = Validation.DATE_PRECEDENCE_ERROR;
							return validation;
						}
					}
					try {
						companyIdLong = Long.parseLong(computerDTO.getCompanyDTO().getId());
						if (companyIdLong > -1 && companyIdLong < companyService.getNbCompanies()) {
							Company company = companyService.findById(companyIdLong);
							computer.setCompany(company);
						} else {
							throw new NumberFormatException();
						}
					} catch (NumberFormatException e) {
						validation = Validation.COMPANY_ERROR;
					}
				} catch (DateTimeParseException e) {
					validation = Validation.DISCONTINUED_DATE_ERROR;
				}
			} catch (DateTimeParseException e) {
				validation = Validation.INTRODUCED_DATE_ERROR;
			}
		}

		return validation;
	}

	/**
	 * Verif id
	 * 
	 * @param computerDTO
	 * @param computer
	 * @return
	 */
	public Validation editValidation(ComputerDTO computerDTO, Computer computer) {
		Validation validation = Validation.NO_ERROR;
		try {
			long idLong = Long.parseLong(computerDTO.getId());
			if (idLong > 0 && idLong < computerService.getNbComputers()) {
				computer.setId(idLong);
				validation = validation(computerDTO, computer);
			} else {
				throw new NumberFormatException();
			}
		} catch (NumberFormatException e) {
			validation = Validation.ID_ERROR;
		}
		return validation;
	}
}
