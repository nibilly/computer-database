package com.excilys.formation.cdb.mapper;

import java.time.LocalDate;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.excilys.formation.cdb.dto.ComputerDTO;
import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.model.Computer;

/**
 * Map a Computer from different types
 * @author nbilly
 *
 */
public class ComputerMapper {
	/**
	 * Take sql request result and return the company correspondent
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
		return new Computer(id, name, introduced, discontinued, companyId, companyName);
	}
	
	public static ComputerDTO mapComputerDTO(Computer computer) {
		return new ComputerDTO(computer.getId()+"", computer.getName(), computer.getIntroduced().toString(), computer.getDiscontinued().toString(), 
				computer.getCompany().getId()+"", computer.getCompany().getName());
	}
}
