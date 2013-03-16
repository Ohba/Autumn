/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ohba.autumn;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.servlet.annotation.WebListener;

import lombok.val;
import lombok.extern.slf4j.Slf4j;

import org.batoo.jpa.BJPASettings;
import org.batoo.jpa.JPASettings;
import org.batoo.jpa.core.BatooPersistenceProvider;
import org.batoo.jpa.jdbc.DDLMode;
import org.reflections.Reflections;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Provides;
import com.google.inject.servlet.GuiceServletContextListener;
import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPDataSource;
import com.sun.jersey.guice.JerseyServletModule;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;
import com.sun.jersey.spi.container.servlet.ServletContainer;

@Slf4j
@WebListener
public class App extends GuiceServletContextListener {
	// WebListener allows code to be executed upon hearing an event
	// essentially the Guice stuff hears that a context is being loaded
	// and bootstraps in all the Guice config

	private static AutumnConfig myConfig = AutumnConfig.fromResource("autumn.json");

	@Override
	protected Injector getInjector() {

		// here we tell guice what system will REALLY be our servlet
		// cause Guice is more or less just shimmed in the midddle
		// to provide DI. as you see Jersey will be our Servlet provider
		return Guice.createInjector(new JerseyServletModule() {
			
			@Override
			protected void configureServlets() {
				// guice passes the init params to jersey as jersey comes up. 
				// as any jersey config goes in there
				val initParams = myConfig.getJerseyInitParams();
				
				// add a few more params that cant be changed from the JSON
				initParams.put(ServletContainer.FEATURE_FILTER_FORWARD_ON_404, "true");
				
				//serve("/*").with(GuiceContainer.class, initParams);
				filter("/*").through(GuiceContainer.class, initParams);
			}
			
			@Provides
			EntityManager provideEntityManager() {
				val jdbc = myConfig.getJdbc();
				log.info(jdbc.toString());
				Map<String, String> properties = Maps.newHashMap();
				properties.put(JPASettings.JDBC_DRIVER, jdbc.getDriver());
				properties.put(JPASettings.JDBC_URL, jdbc.getUrl());
				properties.put(JPASettings.JDBC_USER, jdbc.getUser());
				properties.put(JPASettings.JDBC_PASSWORD, jdbc.getPassword());
				properties.put(BJPASettings.DDL, DDLMode.CREATE.name());
				properties.put(BJPASettings.DDL, DDLMode.UPDATE.name());
				//properties.put(BJPASettings.DDL, DDLMode.DROP.name());
				
				Reflections reflections = new Reflections(myConfig.getEntityPackage()); 
				Set<Class<?>> entityTypes =  reflections.getTypesAnnotatedWith(Entity.class);
				List<String> entityTypeNames = Lists.newArrayList();
				for(Class<?> entityType : entityTypes) {
					entityTypeNames.add(entityType.getName());
				}
				
				log.info("found the following Entities:{}", entityTypeNames);
				
				EntityManagerFactory emf = new BatooPersistenceProvider().createEntityManagerFactory("batoo", properties , entityTypeNames.toArray(new String[0]));

				return emf.createEntityManager();
			}
			
		});
	}

}
