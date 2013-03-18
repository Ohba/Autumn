package com.ohba.autumn.persistence;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import javax.persistence.spi.ClassTransformer;
import javax.persistence.spi.PersistenceUnitInfo;

import lombok.extern.slf4j.Slf4j;

import org.eclipse.persistence.internal.jpa.EntityManagerSetupImpl;
import org.eclipse.persistence.internal.jpa.deployment.JPAInitializer;
import org.eclipse.persistence.internal.jpa.deployment.JavaSECMPInitializer;
import org.eclipse.persistence.internal.jpa.deployment.SEPersistenceUnitInfo;

@Slf4j
public class AutumnJPAInitializer extends JPAInitializer {

	JavaSECMPInitializer realInitializer;
	private List<String> clazzesToAdd;
	
	public AutumnJPAInitializer(JavaSECMPInitializer jpaInitializer, List<String> clazzesToAdd) {
		this.realInitializer = jpaInitializer;
		this.clazzesToAdd = clazzesToAdd;
	}
	
	@Override
	public SEPersistenceUnitInfo findPersistenceUnitInfo(String puName, Map m) {
		Properties p = new Properties();
		if(m != null) {
			for(Entry<Object,Object> e : ((Map<Object,Object>)m).entrySet()) {
				p.put(e.getKey(), e.getValue());
			}
		}
		log.warn("puname={}, properties={}",puName,p);
		SEPersistenceUnitInfo pu = realInitializer.findPersistenceUnitInfo(puName, m); //new SEPersistenceUnitInfo();
		//pu.setProperties(p);
		log.warn("pu={}",pu);
		log.warn("pu.managedClasName={}", pu.getManagedClassNames());
		pu.getManagedClassNames().addAll(clazzesToAdd);
		return pu;
	}

	@Override
	public void checkWeaving(Map properties) {
		realInitializer.checkWeaving(properties);
	}

	@Override
	protected ClassLoader createTempLoader(Collection col) {
		throw new RuntimeException("createTempLoader should not have been called");
	}

	@Override
	protected ClassLoader createTempLoader(Collection col,
			boolean shouldOverrideLoadClassForCollectionMembers) {
		throw new RuntimeException("createTempLoader should not have been called");
	}

	@Override
	public void registerTransformer(ClassTransformer transformer,
			PersistenceUnitInfo persistenceUnitInfo, Map properties) {
		realInitializer.registerTransformer(transformer, persistenceUnitInfo, properties);
	}

	@Override
	public boolean isPersistenceUnitUniquelyDefinedByName() {
		return realInitializer.isPersistenceUnitUniquelyDefinedByName();
	}
	
	@Override
	public String createUniquePersistenceUnitName(PersistenceUnitInfo puInfo) {
		return realInitializer.createUniquePersistenceUnitName(puInfo);
	}
	
	@Override
	public EntityManagerSetupImpl extractInitialEmSetupImpl(String puName) {
		return realInitializer.extractInitialEmSetupImpl(puName);
	}
	
	@Override
	public EntityManagerSetupImpl callPredeploy(SEPersistenceUnitInfo persistenceUnitInfo, Map m, String persistenceUnitUniqueName, String sessionName) {
		return realInitializer.callPredeploy(persistenceUnitInfo, m, persistenceUnitUniqueName, sessionName);
	}
	
	@Override
	public ClassLoader getInitializationClassLoader() {
		return realInitializer.getInitializationClassLoader();
	}
	

}
