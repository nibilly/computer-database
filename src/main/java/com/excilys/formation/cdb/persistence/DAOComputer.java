package com.excilys.formation.cdb.persistence;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import com.excilys.formation.cdb.exception.NoResultException;
import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.model.OrderBy;
import com.excilys.formation.cdb.model.Page;

/**
 * SQL computer table access manager
 * 
 * @author nbilly
 *
 */
public class DAOComputer {
	private EntityManager entityManager;

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	/**
	 * look for all computers
	 * 
	 * @return computers
	 */
	public List<Computer> findAll() {
		List<Computer> computers = null;
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Computer> criteria = builder.createQuery(Computer.class);
		Root<Computer> computerRoot = criteria.from(Computer.class);
		computerRoot.join("company", JoinType.LEFT);
		criteria.select(computerRoot);
		computers = entityManager.createQuery(criteria).getResultList();
		return computers;
	}

	/**
	 * select count(id) from computer;
	 * 
	 * @return number of computers
	 */
	public int getNbComputers() {
		int nbComputers = 0;
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
		Root<Computer> computerRoot = criteria.from(Computer.class);
		criteria.multiselect(builder.count(computerRoot.get("id")));
		nbComputers = entityManager.createQuery(criteria).getSingleResult().intValue();
		return nbComputers;
	}

	/**
	 * look for computers with pagination
	 * 
	 * @param page the page have number of rows jumped and number of rows returned.
	 *             Also the list of computers is returned by page.entities.
	 */
	public void findComputersPages(Page<Computer> page) {
		List<Computer> computers = null;
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Computer> criteria = builder.createQuery(Computer.class);
		Root<Computer> computerRoot = criteria.from(Computer.class);
		computerRoot.join("company", JoinType.LEFT);
		criteria.select(computerRoot);
		computers = entityManager.createQuery(criteria).setFirstResult(page.getNbRowsJumped())
				.setMaxResults(Page.getNbRowsReturned()).getResultList();
		page.setEntities(computers);
		page.setNbComputerFound(getNbComputers());
	}

	/**
	 * look for one computer
	 * 
	 * @param computerId
	 * @return a computer
	 * @throws NoResultException
	 */
	public Computer findById(long computerId) throws NoResultException {
		Computer computer = null;
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Computer> criteria = builder.createQuery(Computer.class);
		Root<Computer> computerRoot = criteria.from(Computer.class);
		computerRoot.join("company", JoinType.LEFT);
		criteria.select(computerRoot);
		criteria.where(builder.equal(computerRoot.get("id"), computerId));
		try {
			computer = entityManager.createQuery(criteria).getSingleResult();
		} catch (javax.persistence.NoResultException e) {
			throw new NoResultException();
		}
		return computer;
	}

	@Transactional
	public void createComputer(Computer computer) {
		entityManager.persist(computer);
	}

	@Transactional
	public void updateComputer(Computer computer) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaUpdate<Computer> criteria = builder.createCriteriaUpdate(Computer.class);
		Root<Computer> computerRoot = criteria.from(Computer.class);
		criteria.set("name", computer.getName());
		criteria.set("introduced", computer.getIntroduced());
		criteria.set("discontinued", computer.getDiscontinued());
		criteria.set("company", computer.getCompany());
		criteria.where(builder.equal(computerRoot.get("id"), computer.getId()));
		entityManager.createQuery(criteria).executeUpdate();
	}

	@Transactional
	public void deleteComputerById(long computerId) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaDelete<Computer> computerCriteria = builder.createCriteriaDelete(Computer.class);
		Root<Computer> computerRoot = computerCriteria.from(Computer.class);
		computerCriteria.where(builder.equal(computerRoot.get("id"), computerId));
		entityManager.createQuery(computerCriteria).executeUpdate();
	}

	public void findComputersPageSearchOrderBy(Page<Computer> page) {
		List<Computer> computers = null;
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Computer> criteria = builder.createQuery(Computer.class);
		Root<Computer> computerRoot = criteria.from(Computer.class);
		Join<Company, Computer> joinCompany = computerRoot.join("company", JoinType.LEFT);
		criteria.select(computerRoot);
		if (page.getSearch() != null && !"".equals(page.getSearch())) {
			Predicate computerName = builder.like(computerRoot.get("name"), "%" + page.getSearch() + "%");
			Predicate companyName = builder.like(joinCompany.get("name"), "%" + page.getSearch() + "%");
			Predicate like = builder.or(computerName, companyName);
			criteria.where(like);
		}
		if (page.getOrderBy() != null) {
			if (page.getOrderBy() == OrderBy.COMPANY_NAME) {
				criteria.orderBy(builder.asc(joinCompany.get(orderMatchAttribute(page.getOrderBy()))));
			} else {
				criteria.orderBy(builder.asc(computerRoot.get(orderMatchAttribute(page.getOrderBy()))));
			}
		}
		computers = entityManager.createQuery(criteria).setFirstResult(page.getNbRowsJumped())
				.setMaxResults(Page.getNbRowsReturned()).getResultList();
		page.setEntities(computers);
		page.setNbComputerFound(nbComputersLike(page));
	}

	private int nbComputersLike(Page<Computer> page) {
		int nbComputers;
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
		Root<Computer> computerRoot = criteria.from(Computer.class);
		Join<Company, Computer> joinCompany = computerRoot.join("company", JoinType.LEFT);
		if (page.getSearch() != null && !"".equals(page.getSearch())) {
			Predicate computerName = builder.like(computerRoot.get("name"), "%" + page.getSearch() + "%");
			Predicate companyName = builder.like(joinCompany.get("name"), "%" + page.getSearch() + "%");
			Predicate like = builder.or(computerName, companyName);
			criteria.where(like);
		}
		criteria.multiselect(builder.count(computerRoot.get("id")));
		nbComputers = entityManager.createQuery(criteria).getSingleResult().intValue();
		return nbComputers;
	}

	private String orderMatchAttribute(OrderBy orderBy) {
		switch (orderBy) {
		case COMPANY_NAME:
			return "name";
		case COMPUTER_NAME:
			return "name";
		case DISCONTINUED:
			return "discontinued";
		case INTRODUCED:
			return "introduced";
		default:
			return null;
		}
	}
}
