package com.excilys.formation.cdb.metamodel;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import com.excilys.formation.cdb.model.Company;

@StaticMetamodel(Company.class)
public class Company_ {
	public static volatile SingularAttribute<Company, Long> id;
	public static volatile SingularAttribute<Company, String> name;
}
