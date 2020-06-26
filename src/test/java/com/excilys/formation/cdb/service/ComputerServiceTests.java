package com.excilys.formation.cdb.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
import com.excilys.formation.cdb.persistence.CDBConnection;

class ComputerServiceTests {
	private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	@Test
	void FindAll() {
		List<Computer> computers = ComputerService.findAll();
		assertEquals(false, computers.isEmpty());

	}

	@Test
	void getNbComputers() {
		List<Computer> computers = ComputerService.findAll();
		int nbComputers = ComputerService.getNbComputers();
		assertEquals(computers.size(), nbComputers);
	}

	@Test
	void findComputersPages() {
		List<Computer> computers = ComputerService.findAll();
		Page<Computer> page = new Page<Computer>(1, 1);
		ComputerService.findComputersPages(page);
		assertEquals(computers.size() - 1, page.getEntities().size());
		List<Computer> computersTest = new ArrayList<Computer>();
		for (int i = 1; i < computers.size(); i++) {
			computersTest.add(computers.get(i));
		}
		assertTrue(page.getEntities().containsAll(computersTest));
	}

	@Test
	void findById() {
		List<Computer> computers = ComputerService.findAll();
		try {
			Computer computer = ComputerService.findById(computers.get(0).getId());
			assertEquals(computers.get(0), computer);
		} catch (NoResultException e) {
			e.printStackTrace();
		}
	}

	@Test
	void createComputer() {
		List<Computer> computers = ComputerService.findAll();
		Computer computerTest = computers.get(0);
		computerTest.setId(0L);
		ComputerService.createComputer(computers.get(0));
		String query = "SELECT computer.id, computer.name computer_name, computer.introduced, computer.discontinued,"
				+ " company.id company_id, company.name company_name from computer left join company on computer.company_id = company.id ORDER BY computer.id DESC LIMIT 1";
		try (Connection connection = CDBConnection.getConnection();
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery(query)) {
			if (resultSet.next()) {
				Computer computer = ComputerMapper.mapSQLToComputer(resultSet);
				computerTest.setId(computer.getId());
				assertEquals(computer, computerTest);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Test
	void updateComputer() {
		List<Computer> computers = ComputerService.findAll();
		Computer computer = computers.get(0);
		computer.setIntroduced(LocalDate.parse("1997-01-01", dateTimeFormatter));
		ComputerService.updateComputer(computer);
		try {
			Computer computerBD = ComputerService.findById(computer.getId());
			assertEquals(computer, computerBD);
		} catch (NoResultException e) {
			e.printStackTrace();
		}
	}

	@Test
	void deleteComputerById() {
		List<Computer> computers = ComputerService.findAll();
		boolean isSuppresed = false;
		Computer computer = computers.get(0);
		ComputerService.deleteComputerById(computer.getId());
		try {
			ComputerService.findById(computer.getId());
		} catch (NoResultException e) {
			isSuppresed = true;
		}
		assertEquals(true, isSuppresed);
	}
}
