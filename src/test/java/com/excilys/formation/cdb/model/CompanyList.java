package com.excilys.formation.cdb.model;

import java.util.ArrayList;
import java.util.List;

public class CompanyList {
	public static List<Company> companies = new ArrayList<Company>() {
		{
			add(new Company(1, "Apple Inc."));
			add(new Company(2, "Thinking Machines"));
		}
	};
}
