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
import com.sun.jersey.api.core.PackagesResourceConfig;
import com.sun.jersey.api.json.JSONConfiguration;
import com.sun.jersey.guice.JerseyServletModule;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;
import com.sun.jersey.spi.container.servlet.ServletContainer;

@Slf4j
@WebListener
public class App extends GuiceServletContextListener {
	// WebListener allows code to be executed upon hearing an event
	// essentially the Guice stuff hears that a context is being loaded
	// and bootstraps in all the Guice config

	private static AutumnConfig myConfig = AutumnConfig.fromResource("autumn.defaults.json","autumn.json");

	@Override
	protected Injector getInjector() {

		// here we tell guice what system will REALLY be our servlet
		// cause Guice is more or less just shimmed in the midddle
		// to provide DI. as you see Jersey will be our Servlet provider
		return Guice.createInjector(new JerseyServletModule() {
			
			@Override
			protected void configureServlets() {
				log.info("AutumnConfig={}",myConfig);
				/*
				 * some of the `autumn.json` config settings are needed to configure jersey
				 * guice passes the init params to jersey as jersey comes up. 
				 */
				Map<String,String> initParams = Maps.newHashMap();
				initParams.put(PackagesResourceConfig.PROPERTY_PACKAGES, myConfig.getPathPackage());
				initParams.put(JSONConfiguration.FEATURE_POJO_MAPPING, myConfig.getPojoMapping().toString());
				
				// add a few more params that cant be set in the JSON
				// posible init params are here: http://jersey.java.net/apidocs/1.17/jersey/constant-values.html
				initParams.put(ServletContainer.FEATURE_FILTER_FORWARD_ON_404, "true");
				
				// by filter-through (instead of serve-with) then requests that guice+jersey
				// cant handle will be chained along to our default `StaticFileServlet.java`
				filter("/*").through(GuiceContainer.class, initParams);
			}
			
			@Provides
			EntityManager provideEntityManager() {
				val jdbc = myConfig.getJdbc();
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
