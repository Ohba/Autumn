package com.ohba.autumn.modules;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import lombok.val;
import lombok.extern.slf4j.Slf4j;

import org.eclipse.persistence.config.PersistenceUnitProperties;
import org.reflections.Reflections;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.ohba.autumn.AutumnConfig;
import com.ohba.autumn.AutumnConfig.DataStoreType;
import com.ohba.autumn.AutumnConfig.Jdbc;
import com.ohba.autumn.AutumnConfig.Mongo;
import com.ohba.autumn.persistence.AutumnPersistenceProvider;

@Slf4j
public class JpaModule extends AbstractModule{

	private static AutumnConfig myConfig = AutumnConfig.fromResource("autumn.defaults.json","autumn.json");
	
	@Override
	protected void configure() {
		
	}
	
	@Provides
	public EntityManager provideEntityManager() {
		
		val dsType = myConfig.getDataStoreType();
		
		Map<String, String> properties = Maps.newHashMap();

		if(dsType == DataStoreType.JDBC) {
			Jdbc jdbc = myConfig.getJdbc();
			properties.put(PersistenceUnitProperties.JDBC_DRIVER, jdbc.getDriver());
			properties.put(PersistenceUnitProperties.JDBC_URL, jdbc.getUrl());
			properties.put(PersistenceUnitProperties.JDBC_USER, jdbc.getUser());
			properties.put(PersistenceUnitProperties.JDBC_PASSWORD, jdbc.getPassword());
			
			properties.put(PersistenceUnitProperties.DDL_GENERATION, PersistenceUnitProperties.CREATE_OR_EXTEND);
			properties.put(PersistenceUnitProperties.DDL_GENERATION_MODE, PersistenceUnitProperties.DDL_DATABASE_GENERATION);

		} else if (dsType == DataStoreType.MONGO) {
			Mongo mongo = myConfig.getMongo();
			properties.put("eclipselink.target-database", "org.eclipse.persistence.nosql.adapters.mongo.MongoPlatform");
			properties.put("eclipselink.nosql.connection-spec", "org.eclipse.persistence.nosql.adapters.mongo.MongoConnectionSpec");
			properties.put("eclipselink.nosql.property.mongo.port", mongo.getPort());
			properties.put("eclipselink.nosql.property.mongo.host", mongo.getHost());
			properties.put("eclipselink.nosql.property.mongo.db", mongo.getDb());
		} 
		
				
		Reflections reflections = new Reflections(myConfig.getEntityPackage()); 
		Set<Class<?>> entityTypes =  reflections.getTypesAnnotatedWith(Entity.class);
		List<String> entityTypeNames = Lists.newArrayList();
		for(Class<?> entityType : entityTypes) {
			entityTypeNames.add(entityType.getName());
		}
		
		log.info("found the following Entities:{}", entityTypeNames);
		
//		List<String> entityTypeNames = Arrays.asList("com.ohba.autumn.sample.pojos.Vehicle");
		
		EntityManagerFactory emf = new AutumnPersistenceProvider().createEntityManagerFactory("autumn", properties, entityTypeNames);
		
		return emf.createEntityManager();
	}

}
