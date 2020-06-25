package com.excilys.formation.cdb.persistence;

import static org.junit.Assert.assertFalse;
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

class DAOComputerTests {
	private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	@Test
	void FindAll() {
		List<Computer> computers = DAOComputer.findAll();
		assertEquals(false, computers.isEmpty());
		assertTrue(computers.containsAll(ComputerList.computers));
	}

	@Test
	void getNbComputers() {
		int nbComputers = DAOComputer.getNbComputers();
		assertEquals(ComputerList.computers.size(), nbComputers);
	}


	@Test
	void findComputersPages() {
		Page<Computer> page = new Page<Computer>(1, 1);
		DAOComputer.findComputersPages(page);
		assertEquals(ComputerList.computers.size()-1, page.getEntities().size());
		List<Computer> computers = new ArrayList<Computer>();
		for(int i = 1; i<ComputerList.computers.size();i++) {
			computers.add(ComputerList.computers.get(i));
		}
		assertTrue(page.getEntities().containsAll(computers));
	}
	
	@Test
	void findById() {
		try {
			Computer computer = DAOComputer.findById(ComputerList.computers.get(0).getId());
			assertEquals(ComputerList.computers.get(0), computer);
		} catch (NoResultException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	void createComputer(){
		Computer computerTest = new Computer(ComputerList.computers.get(0));
		assertFalse(computerTest == ComputerList.computers.get(0));
		DAOComputer.createComputer(ComputerList.computers.get(0));
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
		List<Computer> computers = DAOComputer.findAll();
		Computer computer = ComputerList.computers.get(1);
		computer.setIntroduced(LocalDate.parse("1998-01-01", dateTimeFormatter));
		DAOComputer.updateComputer(computer);
		try {
			Computer computerBD = DAOComputer.findById(computer.getId());
			assertEquals(computer, computerBD);
			ComputerList.computers.get(1).setIntroduced(LocalDate.parse("1998-01-01", dateTimeFormatter));
		} catch (NoResultException e) {
			e.printStackTrace();
		}
		List<Computer> computers1 = DAOComputer.findAll();
	}
	
	@Test
	void deleteComputerById(){
		List<Computer> computers = DAOComputer.findAll();
		boolean isSuppresed = false;
		Computer computer = ComputerList.computers.get(0);
		DAOComputer.deleteComputerById(computer.getId());
		try {
			DAOComputer.findById(computer.getId());
		} catch (NoResultException e) {
			isSuppresed = true;
			ComputerList.computers.remove(0);
		}
		assertEquals(true, isSuppresed);
		List<Computer> computers1 = DAOComputer.findAll();
	}
}
