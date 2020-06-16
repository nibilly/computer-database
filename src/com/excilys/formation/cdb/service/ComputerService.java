package com.excilys.formation.cdb.service;

import java.util.List;

import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.model.NoResultException;
import com.excilys.formation.cdb.persistence.CDBConnection;
import com.excilys.formation.cdb.persistence.DAOComputer;

public class ComputerService {

	/**
	 * Not static to call finalize.
	 */
	public ComputerService() {
	}
	
	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		CDBConnection.CloseConnection();
	}

	public void deleteComputerById(long computerId) {
		DAOComputer.deleteComputerById(computerId);
	}

	public void updateComputer(Computer computer) {
		DAOComputer.updateComputer(computer);
	}

	public void createComputer(Computer computer) {
		DAOComputer.createComputer(computer);
	}

	public Computer findById(long computerId) throws NoResultException {
		return DAOComputer.findById(computerId);
	}

	public List<Computer> findAll() {
		return DAOComputer.findAll();
	}

	public List<Computer> findComputersBetween(int nbRowsJumped, int nbRowsReturned) {
		return DAOComputer.findComputersBetween(nbRowsJumped, nbRowsReturned);
	}
}
