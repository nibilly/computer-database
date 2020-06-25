package com.excilys.formation.cdb.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

	private static Logger logger = LoggerFactory.getLogger(CompanyService.class);
	
	public static void deleteComputerById(long computerId) {
		logger.info("deleteComputerById");
		DAOComputer.deleteComputerById(computerId);
	}

	public static void updateComputer(Computer computer) {
		logger.info("updateComputer");
		DAOComputer.updateComputer(computer);
	}

	public static void createComputer(Computer computer) {
		logger.info("createComputer");
		DAOComputer.createComputer(computer);
	}

	public static Computer findById(long computerId) throws NoResultException {
		logger.info("findById");
		return DAOComputer.findById(computerId);
	}

	public static List<Computer> findAll() {
		logger.info("findAll");
		return DAOComputer.findAll();
	}

	public static void findComputersPages(Page<Computer> page) {
		logger.info("findComputersPages");
		DAOComputer.findComputersPages(page);
	}
	
	public static int getNbComputers() {
		logger.info("getNbComputers");
		return DAOComputer.getNbComputers();
	}
}
