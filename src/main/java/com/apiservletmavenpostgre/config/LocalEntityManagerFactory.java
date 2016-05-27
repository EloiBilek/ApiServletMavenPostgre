/**
 * 
 */
package com.apiservletmavenpostgre.config;

import java.util.Date;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * @author eloi eloibilek@gmail.com
 */
@WebListener
public class LocalEntityManagerFactory implements ServletContextListener {

	private static EntityManagerFactory emf;
	private static EntityManager em;

	static final Logger LOGGER = Logger.getLogger(LocalEntityManagerFactory.class.getName());

	@Override
	public void contextInitialized(ServletContextEvent event) {
		emf = Persistence.createEntityManagerFactory("persistence_unit_db_api_test");
		LOGGER.info("Starting LocalEntityManagerFactory [" + new Date() + "]");
	}

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		if (em != null)
			em.close();
		LOGGER.info(":::EntityManager closed:::");

		if (emf != null)
			emf.close();
		LOGGER.info(":::EntityManagerFactory closed:::");
	}

	public static EntityManager createEntityManager() {
		if (emf == null) {
			throw new IllegalStateException(":::Context is not initialized yet!:::");
		}
		em = emf.createEntityManager();
		return em;
	}
}
