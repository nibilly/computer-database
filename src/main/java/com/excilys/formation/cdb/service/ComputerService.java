package com.excilys.formation.cdb.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.formation.cdb.exception.NoResultException;
import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.model.OrderBy;
import com.excilys.formation.cdb.model.Page;
import com.excilys.formation.cdb.persistence.DAOComputer;

/**
 * DAO access
 * 
 * @author nbilly
 *
 */
public class ComputerService {

	private static Logger logger = LoggerFactory.getLogger(ComputerService.class);

	private DAOComputer daoComputer;

	public void setDaoComputer(DAOComputer daoComputer) {
		this.daoComputer = daoComputer;
	}

	public void deleteComputerById(long computerId) {
		logger.info("deleteComputerById");
		daoComputer.deleteComputerById(computerId);
	}

	public void updateComputer(Computer computer) {
		logger.info("updateComputer");
		daoComputer.updateComputer(computer);
	}

	public void createComputer(Computer computer) {
		logger.info("createComputer");
		daoComputer.createComputer(computer);
	}

	public Computer findById(long computerId) throws NoResultException {
		logger.info("findById");
		return daoComputer.findById(computerId);
	}

	public List<Computer> findAll() {
		logger.info("findAll");
		return daoComputer.findAll();
	}

	public void findComputersPages(Page<Computer> page) {
		logger.info("findComputersPages");
		daoComputer.findComputersPages(page);
	}

	public int getNbComputers() {
		logger.info("getNbComputers");
		return daoComputer.getNbComputers();
	}

	public void findComputersPageSearchOrderBy(Page<Computer> page, String search, OrderBy orderBy) {
		logger.info("findComputersPageSearchOrderBy");
		daoComputer.findComputersPageSearchOrderBy(page, search, orderBy);
	}
}
