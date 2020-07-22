package com.excilys.formation.cdb.persistence;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import com.excilys.formation.cdb.exception.NoResultException;
import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.model.Page;

/**
 * mySQL company table access manager
 * 
 * @author nbilly
 *
 */
public class DAOCompany {

	private EntityManager entityManager;

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	/**
	 * Select * from company;
	 * 
	 * @return all companies
	 */
	public List<Company> findAll() {
		List<Company> companies = null;
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Company> criteria = builder.createQuery(Company.class);
		Root<Company> companyRoot = criteria.from(Company.class);
		Path<Long> idPath = companyRoot.get("id");
		Path<String> namePath = companyRoot.get("name");
		criteria.multiselect(idPath, namePath);
		companies = entityManager.createQuery(criteria).getResultList();
		return companies;
	}

	/**
	 * Select * from company where id = X;
	 * 
	 * @param companyId
	 * @return a company
	 * @throws NoResultException if request returns nothing
	 */
	public Company findById(long companyId) throws NoResultException {
		Company company = null;
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Company> criteria = builder.createQuery(Company.class);
		Root<Company> companyRoot = criteria.from(Company.class);
		Path<Long> idPath = companyRoot.get("id");
		Path<String> namePath = companyRoot.get("name");
		criteria.multiselect(idPath, namePath);
		criteria.where(builder.equal(companyRoot.get("id"), companyId));
		try {
			company = entityManager.createQuery(criteria).getSingleResult();
		} catch (javax.persistence.NoResultException e) {
			throw new NoResultException();
		}
		return company;
	}

	public int getNbCompanies() {
		int nbCompanies = 0;
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
		Root<Company> companyRoot = criteria.from(Company.class);
		criteria.multiselect(builder.count(companyRoot.get("id")));
		nbCompanies = entityManager.createQuery(criteria).getSingleResult().intValue();
		return nbCompanies;
	}

	public void findAllPages(Page<Company> page) {
		List<Company> companies = null;
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Company> criteria = builder.createQuery(Company.class);
		Root<Company> companyRoot = criteria.from(Company.class);
		Path<Long> idPath = companyRoot.get("id");
		Path<String> namePath = companyRoot.get("name");
		criteria.multiselect(idPath, namePath);
		companies = entityManager.createQuery(criteria).setFirstResult(page.getNbRowsJumped())
				.setMaxResults(Page.getNbRowsReturned()).getResultList();
		page.setEntities(companies);
	}

	@Transactional
	public void delete(long companyId) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();

		CriteriaDelete<Computer> computerCriteria = builder.createCriteriaDelete(Computer.class);
		Root<Computer> computerRoot = computerCriteria.from(Computer.class);
		computerCriteria.where(builder.equal(computerRoot.get("company"), companyId));
		entityManager.createQuery(computerCriteria).executeUpdate();

		CriteriaDelete<Company> companyCriteria = builder.createCriteriaDelete(Company.class);
		Root<Company> companyRoot = companyCriteria.from(Company.class);
		companyCriteria.where(builder.equal(companyRoot.get("id"), companyId));
		entityManager.createQuery(companyCriteria).executeUpdate();
	}
}
