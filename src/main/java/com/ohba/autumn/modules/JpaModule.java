package com.ohba.autumn.modules;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import lombok.val;
import lombok.extern.slf4j.Slf4j;

import org.batoo.jpa.BJPASettings;
import org.batoo.jpa.JPASettings;
import org.batoo.jpa.core.BatooPersistenceProvider;
import org.batoo.jpa.jdbc.DDLMode;
import org.reflections.Reflections;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.ohba.autumn.AutumnConfig;

@Slf4j
public class JpaModule extends AbstractModule{

	private static AutumnConfig myConfig = AutumnConfig.fromResource("autumn.defaults.json","autumn.json");
	
	@Override
	protected void configure() {
		
	}
	
	@Provides
	public EntityManager provideEntityManager() {
		val jdbc = myConfig.getJdbc();
		Map<String, String> properties = Maps.newHashMap();
		properties.put(JPASettings.JDBC_DRIVER, jdbc.getDriver());
		properties.put(JPASettings.JDBC_URL, jdbc.getUrl());
		properties.put(JPASettings.JDBC_USER, jdbc.getUser());
		properties.put(JPASettings.JDBC_PASSWORD, jdbc.getPassword());
		properties.put(BJPASettings.DDL, DDLMode.CREATE.name());
		//properties.put(BJPASettings.DDL, DDLMode.UPDATE.name());
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

}
