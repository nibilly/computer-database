package com.excilys.formation.cdb.servlet;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ContextFactory {
	private static AbstractApplicationContext applicationContext = new ClassPathXmlApplicationContext("beans.xml");

	public static AbstractApplicationContext getApplicationContext() {
		return applicationContext;
	}

}
