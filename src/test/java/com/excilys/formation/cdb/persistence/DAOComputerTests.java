package com.excilys.formation.cdb.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.excilys.formation.cdb.mapper.ComputerMapper;
import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.model.NoResultException;
import com.excilys.formation.cdb.model.Page;

class DAOComputerTests {
	private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	@Test
	void findAll() {
		List<Computer> computers = DAOComputer.findAll();
		assertEquals(false, computers.isEmpty());
	}

	@Test
	void getNbComputers() {
		List<Computer> computers = DAOComputer.findAll();
		int nbComputers = DAOComputer.getNbComputers();
		assertEquals(computers.size(), nbComputers);
	}

	@Test
	void findComputersPages() {
		List<Computer> computers = DAOComputer.findAll();
		Page<Computer> page = new Page<Computer>(1, 1);
		DAOComputer.findComputersPages(page);
		assertEquals(computers.size() - 1, page.getEntities().size());
		List<Computer> computersTest = new ArrayList<Computer>();
		for (int i = 1; i < computers.size(); i++) {
			computersTest.add(computers.get(i));
		}
		assertTrue(page.getEntities().containsAll(computersTest));
	}

	@Test
	void findById() {
		List<Computer> computers = DAOComputer.findAll();
		try {
			Computer computer = DAOComputer.findById(computers.get(0).getId());
			assertEquals(computers.get(0), computer);
		} catch (NoResultException e) {
			e.printStackTrace();
		}
	}

	@Test
	void createComputer() {
		List<Computer> computers = DAOComputer.findAll();
		Computer computerTest = new Computer(computers.get(0));
		assertFalse(computerTest == computers.get(0));
		DAOComputer.createComputer(computers.get(0));
		String query = "SELECT computer.id, computer.name computer_name, computer.introduced, computer.discontinued,"
				+ " company.id company_id, company.name company_name from computer left join company on computer.company_id = company.id ORDER BY computer.id DESC LIMIT 1";
		try (Connection connection = CDBConnection.getConnection();
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery(query)) {
			if (resultSet.next()) {
				Computer computer = ComputerMapper.mapSQLToComputer(resultSet);
				computerTest.setId(computer.getId());
				assertEquals(computer, computerTest);
				computers.add(computer);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Test
	void updateComputer() {
		List<Computer> computers = DAOComputer.findAll();
		Computer computer = computers.get(0);
		computer.setIntroduced(LocalDate.parse("1998-01-01", dateTimeFormatter));
		DAOComputer.updateComputer(computer); 
		try {
			Computer computerBD = DAOComputer.findById(computer.getId());
			assertEquals(computer, computerBD);
		} catch (NoResultException e) {
			e.printStackTrace();
		}
	}

	@Test
	void deleteComputerById() {
		List<Computer> computers = DAOComputer.findAll();
		boolean isSuppresed = false;
		Computer computer = computers.get(0);
		DAOComputer.deleteComputerById(computer.getId());
		try {
			DAOComputer.findById(computer.getId());
		} catch (NoResultException e) {
			isSuppresed = true;
		}
		assertEquals(true, isSuppresed);
	}
}
