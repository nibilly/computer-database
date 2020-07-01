package com.excilys.formation.cdb.validation;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import com.excilys.formation.cdb.mapper.DateMapper;
import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.service.CompanyService;
import com.excilys.formation.cdb.service.ComputerService;

public class ComputerValidation {


	public static Validation validation(String name, String introduced, String discontinued, String companyId, Computer computer) {
		Validation validation = Validation.NO_ERROR;
		LocalDate introducedDate;
		LocalDate discontinuedDate;
		long companyIdLong;
		if(name == null || name.equals("")) {
			validation = Validation.NAME_ERROR;
		}
		else {
			computer.setName(name);
			try {
				introducedDate = (introduced.equals(""))?null:DateMapper.localDateFromString(introduced);
				computer.setIntroduced(introducedDate);
				try {
					discontinuedDate = (discontinued.equals(""))?null:DateMapper.localDateFromString(discontinued);
					computer.setDiscontinued(discontinuedDate);
					if(introducedDate != null && discontinuedDate != null) {
						if(introducedDate.compareTo(discontinuedDate) >=0) {
							validation = Validation.DATE_PRECEDENCE_ERROR;
							return validation;
						}
					}
					try {
						companyIdLong = Long.parseLong(companyId);
						if(companyIdLong > -1 && companyIdLong < CompanyService.getNbCompanies()) {
							Company company = CompanyService.findById(companyIdLong);
							computer.setCompany(company);
						}
						else {
							throw new NumberFormatException();
						}
					}
					catch(NumberFormatException e) {
						validation = Validation.COMPANY_ERROR;
					}
				}
				catch(DateTimeParseException e) {
					validation = Validation.DISCONTINUED_DATE_ERROR;
				}
			}
			catch(DateTimeParseException e) {
				validation = Validation.INTRODUCED_DATE_ERROR;
			}
		}

		return validation;
	}

	public static Validation validation(String id, String name, String introduced, String discontinued,
			String companyId, Computer computer) {
		Validation validation = Validation.NO_ERROR;
		try {
			long idLong = Long.parseLong(id);
			if(idLong > 0 && idLong < ComputerService.getNbComputers()) {
				computer.setId(idLong);
				validation = ComputerValidation.validation(name, introduced, discontinued, companyId, computer);
			}
			else {
				throw new NumberFormatException();
			}
		}
		catch(NumberFormatException e) {
			validation = Validation.ID_ERROR;
		}
		return validation;
	}
}
