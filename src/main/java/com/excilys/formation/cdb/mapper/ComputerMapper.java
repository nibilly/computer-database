package com.excilys.formation.cdb.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import com.excilys.formation.cdb.dto.ComputerDTO;
import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.model.Computer;

/**
 * Map a Computer from different types
 * 
 * @author nbilly
 *
 */
public class ComputerMapper {
	/**
	 * Take sql request result and return the company correspondent
	 * 
	 * @param resultSet
	 * @return
	 * @throws SQLException
	 */
	public static Computer mapSQLToComputer(ResultSet resultSet) throws SQLException {
		long id = resultSet.getLong("id");
		String name = resultSet.getString("computer_name");
		LocalDate introduced = DateMapper.localDateFromSqlDate(resultSet.getDate("introduced"));
		LocalDate discontinued = DateMapper.localDateFromSqlDate(resultSet.getDate("discontinued"));
		long companyId = resultSet.getLong("company_id");
		String companyName = resultSet.getString("company_name");
		Company company = new Company(companyId, companyName);
		return new Computer(id, name, introduced, discontinued, company);
	}

	public static ComputerDTO mapComputerDTO(Computer computer) {
		ComputerDTO computerDTO = new ComputerDTO();
		computerDTO.setId(computer.getId() + "");
		computerDTO.setName(computer.getName());
		if (computer.getIntroduced() != null) {
			computerDTO.setIntroduced(computer.getIntroduced().toString());
		} else {
			computerDTO.setIntroduced("");
		}
		if (computer.getDiscontinued() != null) {
			computerDTO.setDiscontinued(computer.getDiscontinued().toString());
		} else {
			computerDTO.setDiscontinued("");
		}
		if (computer.getCompany() != null) {
			computerDTO.setCompanyId(computer.getCompany().getId() + "");
			computerDTO.setCompanyName(computer.getCompany().getName());
		} else {
			computerDTO.setCompanyId("");
			computerDTO.setCompanyName("");
		}
		return computerDTO;
	}
}
