package com.excilys.formation.cdb.config;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ContextFactory {
	private static AbstractApplicationContext applicationContext = new ClassPathXmlApplicationContext(
			"persistence-beans.xml");

	public static AbstractApplicationContext getApplicationContext() {
		return applicationContext;
	}
}
