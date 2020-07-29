package com.excilys.formation.cdb.persistence;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.excilys.formation.cdb.model.User;

public class DAOUser {
	private EntityManager entityManager;

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public User findByUsername(String username) throws UsernameNotFoundException {
		User user = null;
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<User> criteria = builder.createQuery(User.class);
		Root<User> userRoot = criteria.from(User.class);
		userRoot.fetch("authorities", JoinType.LEFT);
		criteria.select(userRoot);
		criteria.where(builder.equal(userRoot.get("username"), username));
		try {
			user = entityManager.createQuery(criteria).getSingleResult();
		} catch (javax.persistence.NoResultException e) {
			throw new UsernameNotFoundException(username + " isn't in the database");
		}
		return user;
	}
}
