package com.excilys.formation.cdb.mapper;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

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
	public static Computer mapSQLToJava(ResultSet resultSet) throws SQLException {
		long id = resultSet.getLong("id");
		String name = resultSet.getString("computer_name");
		Date introduced = resultSet.getDate("introduced");
		Date discontinued = resultSet.getDate("discontinued");
		long companyId = resultSet.getLong("company_id");
		String companyName = resultSet.getString("company_name");		
		return new Computer(id, name, introduced, discontinued, companyId, companyName);
	}
}
