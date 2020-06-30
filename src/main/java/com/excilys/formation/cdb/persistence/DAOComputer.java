package com.excilys.formation.cdb.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import com.excilys.formation.cdb.exception.NoResultException;
import com.excilys.formation.cdb.mapper.ComputerMapper;
import com.excilys.formation.cdb.mapper.DateMapper;
import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.model.Page;

/**
 * SQL computer table access manager
 * 
 * @author nbilly
 *
 */
public class DAOComputer {

	/**
	 * look for all computers
	 * 
	 * @return computers
	 */
	public static List<Computer> findAll() {
		List<Computer> computers;
		computers = new ArrayList<Computer>();
		String request = "select computer.id, computer.name computer_name, computer.introduced, computer.discontinued, "
				+ "company.id company_id, company.name company_name from computer join company on computer.company_id = company.id;";
		try (Connection connection = CDBConnection.getConnection();
				Statement statement= connection.createStatement();
				ResultSet resultSet = statement.executeQuery(request)) {
			while (resultSet.next()) {
				Computer computer = ComputerMapper.mapSQLToComputer(resultSet);
				computers.add(computer);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return computers;
	}

	/**
	 * select count(*) from computer;
	 * @return number of computers
	 */
	public static int getNbComputers() {
		int nbComputers = 0;
		String request = "select count(*) from computer;";
		try (Connection connection = CDBConnection.getConnection();
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery(request)){
			if (resultSet.next()) {
				nbComputers = resultSet.getInt("count(*)");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return nbComputers;
	}

	/**
	 * look for computers with pagination
	 * @param page the page have number of rows jumped and number of rows returned.
	 *             Also the list of computers is returned by page.entities.
	 */
	public static void findComputersPages(Page<Computer> page) {
		List<Computer> computers = new ArrayList<>();
		String request = "select computer.id, computer.name computer_name, computer.introduced, computer.discontinued, "
				+ "company.id company_id, company.name company_name from computer left join company on computer.company_id = company.id limit ?,?;";
		try (Connection con = CDBConnection.getConnection();
				PreparedStatement preparedStatement = con.prepareStatement(request)) {
			preparedStatement.setInt(1, page.getNbRowsJumped());
			preparedStatement.setInt(2, Page.getNbRowsReturned());
			try(ResultSet rs = preparedStatement.executeQuery()){
				while (rs.next()) {
					Computer computer = ComputerMapper.mapSQLToComputer(rs);
					computers.add(computer);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		page.setEntities(computers);
	}

	/**
	 * look for one computer
	 * 
	 * @param computerId
	 * @return a computer
	 * @throws NoResultException
	 */
	public static Computer findById(long computerId) throws NoResultException {
		Computer computer = null;
		String request = "select computer.id, computer.name computer_name, computer.introduced, computer.discontinued, " 
				+ "company.id company_id, company.name company_name from computer left join company on computer.company_id = company.id where computer.id = ?;";
		try (Connection connection = CDBConnection.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(request)){
			preparedStatement.setLong(1, computerId);
			try(ResultSet resultSet = preparedStatement.executeQuery()){
				if (resultSet.next()) {
					computer = ComputerMapper.mapSQLToComputer(resultSet);
				} else {
					throw new NoResultException();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return computer;
	}

	public static void createComputer(Computer computer) {
		String request = "insert into computer(name, introduced, discontinued, company_id) values(?, ?, ?, ?);";
		try (Connection connection = CDBConnection.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(request)){
			preparedStatement.setString(1, computer.getName());
			if (computer.getIntroduced() == null) {
				preparedStatement.setNull(2, Types.DATE);
			} else {
				preparedStatement.setDate(2, DateMapper.sqlDateFromLocalDate(computer.getIntroduced()));
			}
			if (computer.getDiscontinued() == null) {
				preparedStatement.setNull(3, Types.DATE);
			} else {
				preparedStatement.setDate(3, DateMapper.sqlDateFromLocalDate(computer.getDiscontinued()));
			}
			if (computer.getCompany() == null) {
				preparedStatement.setNull(4, Types.BIGINT);
			} else {
				preparedStatement.setLong(4, computer.getCompany().getId());
			}
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void updateComputer(Computer computer) {
		String request = "update computer set name=?, introduced=?, discontinued=?, company_id=? where id=?;";
		try (Connection connection = CDBConnection.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(request)){
			preparedStatement.setString(1, computer.getName());
			if (computer.getIntroduced() == null) {
				preparedStatement.setNull(2, Types.VARCHAR);
			} else {
				preparedStatement.setDate(2, DateMapper.sqlDateFromLocalDate(computer.getIntroduced()));
			}
			if (computer.getDiscontinued() == null) {
				preparedStatement.setNull(3, Types.VARCHAR);
			} else {
				preparedStatement.setDate(3, DateMapper.sqlDateFromLocalDate(computer.getDiscontinued()));
			}
			if (computer.getCompany() == null) {
				preparedStatement.setNull(4, Types.BIGINT);
			} else {
				preparedStatement.setLong(4, computer.getCompany().getId());
			}
			preparedStatement.setLong(5, computer.getId());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void deleteComputerById(long computerId) {
		String request = "delete from computer where id=?;";
		try (Connection connection = CDBConnection.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(request)) {
			preparedStatement.setLong(1, computerId);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
