package com.excilys.formation.cdb.mapper;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.excilys.formation.cdb.model.Computer;

public class ComputerMapper {
	public static Computer mapSQLToJava(ResultSet resultSet) throws SQLException {
		long id = resultSet.getLong("id");
		String name = resultSet.getString("name");
		Date introduced = resultSet.getDate("introduced");
		Date discontinued = resultSet.getDate("discontinued");
		long companyId = resultSet.getLong("company_id");
		return new Computer(id, name, introduced, discontinued, companyId);
	}
}
