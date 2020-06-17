package com.excilys.formation.cdb.service;

import java.util.List;

import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.model.NoResultException;
import com.excilys.formation.cdb.model.Page;
import com.excilys.formation.cdb.persistence.DAOComputer;

/**
 * DAO access
 * @author nbilly
 *
 */
public class ComputerService {

	public static void deleteComputerById(long computerId) {
		DAOComputer.deleteComputerById(computerId);
	}

	public static void updateComputer(Computer computer) {
		DAOComputer.updateComputer(computer);
	}

	public static void createComputer(Computer computer) {
		DAOComputer.createComputer(computer);
	}

	public static Computer findById(long computerId) throws NoResultException {
		return DAOComputer.findById(computerId);
	}

	public static List<Computer> findAll() {
		return DAOComputer.findAll();
	}

	public static void findComputersPages(Page<Computer> page) {
		DAOComputer.findComputersPages(page);
	}
	
	public static int getNbComputers() {
		return DAOComputer.getNbComputers();
	}
}