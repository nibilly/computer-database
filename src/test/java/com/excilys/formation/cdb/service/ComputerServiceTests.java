package com.excilys.formation.cdb.service;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

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
import com.excilys.formation.cdb.model.ComputerList;
import com.excilys.formation.cdb.model.NoResultException;
import com.excilys.formation.cdb.model.Page;
import com.excilys.formation.cdb.persistence.CDBConnection;
import com.excilys.formation.cdb.persistence.DAOComputer;

class ComputerServiceTests {
	private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	@Test
	void FindAll() {
		List<Computer> computers = ComputerService.findAll();
		assertEquals(false, computers.isEmpty());
		assertTrue(computers.containsAll(ComputerList.computers));
		
	}

	@Test
	void getNbComputers() {
		int nbComputers = ComputerService.getNbComputers();
		assertEquals(ComputerList.computers.size(), nbComputers);
	}


	@Test
	void findComputersPages() {
		List<Computer> computers = DAOComputer.findAll();
		Page<Computer> page = new Page<Computer>(1, 1);
		ComputerService.findComputersPages(page);
		assertEquals(ComputerList.computers.size()-1, page.getEntities().size());
		List<Computer> computersTest = new ArrayList<Computer>();
		for(int i = 1; i<ComputerList.computers.size();i++) {
			computersTest.add(ComputerList.computers.get(i));
		}
		assertTrue(page.getEntities().containsAll(computersTest));
	}
	
	@Test
	void findById() {
		try {
			Computer computer = ComputerService.findById(ComputerList.computers.get(0).getId());
			assertEquals(ComputerList.computers.get(0), computer);
		} catch (NoResultException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	void createComputer(){
		Computer computerTest = ComputerList.computers.get(0);
		computerTest.setId(0L);
		ComputerService.createComputer(ComputerList.computers.get(0));
		String query = "SELECT computer.id, computer.name computer_name, computer.introduced, computer.discontinued,"
				+ " company.id company_id, company.name company_name from computer left join company on computer.company_id = company.id ORDER BY computer.id DESC LIMIT 1";
		try(Connection connection = CDBConnection.getConnection();
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery(query)){
			if(resultSet.next()) {
				Computer computer = ComputerMapper.mapSQLToComputer(resultSet);
				computerTest.setId(computer.getId());
				assertEquals(computer, computerTest); 
				ComputerList.computers.add(computer);
			}		
		} catch (SQLException e) {
			e.printStackTrace();
		}		
	}
	
	@Test
	void updateComputer(){
		Computer computer = ComputerList.computers.get(1);
		computer.setIntroduced(LocalDate.parse("1997-01-01", dateTimeFormatter));
		ComputerService.updateComputer(computer);
		try {
			Computer computerBD = ComputerService.findById(computer.getId());
			assertEquals(computer, computerBD);
			ComputerList.computers.get(1).setIntroduced(LocalDate.parse("1997-01-01", dateTimeFormatter));
		} catch (NoResultException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	void deleteComputerById(){
		boolean isSuppresed = false;
		Computer computer = ComputerList.computers.get(0);
		ComputerService.deleteComputerById(computer.getId());
		try {
			ComputerService.findById(computer.getId());
		} catch (NoResultException e) {
			isSuppresed = true;
			ComputerList.computers.remove(0);
		}
		assertEquals(true, isSuppresed);
	}
}
