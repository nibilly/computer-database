package com.excilys.formation.cdb.persistence;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.excilys.formation.cdb.mapper.ComputerMapper;
import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.model.NoResultException;

public class DAOComputer {
	private static List<Computer> computers;

	public static List<Computer> findAll() {
		Connection connection = CDBConnection.getConnection();
		computers = new ArrayList<Computer>();
		Statement statement;
		try {
			statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("select * from computer;");
			while(resultSet.next()) {
				Computer computer = ComputerMapper.mapSQLToJava(resultSet);
				computers.add(computer);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return computers;
	}
	
	public static List<Computer> findComputersBetween(int nbRowsJumped, int nbRowsReturned) {
		Connection connection = CDBConnection.getConnection();
		computers = new ArrayList<Computer>();
		Statement statement;
		try {
			statement = connection.createStatement();
			StringBuffer stringBuffer = new StringBuffer();
			stringBuffer.append("select * from computer");
			stringBuffer.append(" limit ").append(nbRowsJumped).append(",").append(nbRowsReturned).append(";");
			ResultSet resultSet = statement.executeQuery(stringBuffer.toString());
			while(resultSet.next()) {
				Computer computer = ComputerMapper.mapSQLToJava(resultSet);
				computers.add(computer);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return computers;
	}

	public static Computer findById(long computerId) throws NoResultException {
		Computer computer = null;
		if(computers!=null)
		{
			computer = computers.stream().filter(c->c.getId() == computerId).findAny().orElse(null);
		}
		if (computer==null) {
			Connection connection = CDBConnection.getConnection();
			computers = new ArrayList<Computer>();
			Statement statement;
			try {
				statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery("select * from computer where id = " + computerId + ";");
				if(resultSet.next()) {
					computer = ComputerMapper.mapSQLToJava(resultSet);
				}
				else {
					throw new NoResultException();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return computer;
	}
	public static void createComputer(Computer computer) {
		Connection connection = CDBConnection.getConnection();
		Statement statement;
		try {
			statement = connection.createStatement();
			StringBuffer stringBuffer = new StringBuffer();
			stringBuffer.append("insert into computer(name, introduced, discontinued, company_id) values('").append(computer.getName()).append("', ");
			if(computer.getIntroduced()==null){
				stringBuffer.append("null, ");
			}
			else {
				stringBuffer.append("'").append(computer.getIntroduced()).append("', ");
			}
			if(computer.getDiscontinued()==null){
				stringBuffer.append("null, ");
			}
			else {
				stringBuffer.append("'").append(computer.getDiscontinued()).append("', ");
			}
			if(computer.getCompany() == null) {
				stringBuffer.append("null");
			}
			else{
				stringBuffer.append(computer.getCompany().getId());
			}
			stringBuffer.append(");");
			statement.executeUpdate(stringBuffer.toString());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void updateComputer(Computer computer) {
		Connection connection = CDBConnection.getConnection();
		Statement statement;
		try {
			statement = connection.createStatement();
			StringBuffer stringBuffer = new StringBuffer();
			stringBuffer.append("update computer set name='").append(computer.getName()).append("', ");
			stringBuffer.append("introduced=");
			if(computer.getIntroduced()==null){
				stringBuffer.append("null, ");
			}
			else {
				stringBuffer.append("'").append(computer.getIntroduced()).append("', ");
			}
			stringBuffer.append("discontinued=");
			if(computer.getDiscontinued()==null){
				stringBuffer.append("null, ");
			}
			else {
				stringBuffer.append("'").append(computer.getDiscontinued()).append("', ");
			}
			stringBuffer.append("company_id=");
			if(computer.getCompany()==null) {
				stringBuffer.append("null");
			}
			else{
				stringBuffer.append(computer.getCompany().getId());
			}
			stringBuffer.append(" where id=").append(computer.getId()).append(";");
			System.out.println(stringBuffer.toString());
			statement.executeUpdate(stringBuffer.toString());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void deleteComputerById(long computerId) {
		Connection connection = CDBConnection.getConnection();
		Statement statement;
		try {
			statement = connection.createStatement();
			StringBuffer stringBuffer = new StringBuffer();
			stringBuffer.append("delete from computer where id=").append(computerId).append(";");
			statement.executeUpdate(stringBuffer.toString());			
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
}
