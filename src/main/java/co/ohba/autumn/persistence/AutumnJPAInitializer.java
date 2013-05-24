package co.ohba.autumn.persistence;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.persistence.internal.jpa.EntityManagerSetupImpl;
import org.eclipse.persistence.internal.jpa.deployment.JPAInitializer;
import org.eclipse.persistence.internal.jpa.deployment.JavaSECMPInitializer;
import org.eclipse.persistence.internal.jpa.deployment.SEPersistenceUnitInfo;

import javax.persistence.spi.ClassTransformer;
import javax.persistence.spi.PersistenceUnitInfo;
import java.util.*;
import java.util.Map.Entry;

@Slf4j
public class AutumnJPAInitializer extends JPAInitializer {

	JavaSECMPInitializer realInitializer;
	private List<String> clazzesToAdd;
    private Map<String,SEPersistenceUnitInfo> puMap = new HashMap<>();

    public AutumnJPAInitializer(JavaSECMPInitializer jpaInitializer, List<String> clazzesToAdd) {
		this.realInitializer = jpaInitializer;
		this.clazzesToAdd = clazzesToAdd;
	}
	
	@Override
	public SEPersistenceUnitInfo findPersistenceUnitInfo(String puName, Map m) {
        SEPersistenceUnitInfo pu = puMap.get(puName);
        if(pu==null){
            Properties p = new Properties();
            if(m != null) {
                for(Entry<Object,Object> e : ((Map<Object,Object>)m).entrySet()) {
                    p.put(e.getKey(), e.getValue());
                }
            }
            log.debug("puname={}, properties={}",puName,p);
            pu = realInitializer.findPersistenceUnitInfo(puName, m);
            log.debug("pu={} pu.managedClassNames={}", pu, pu.getManagedClassNames());
            pu.getManagedClassNames().addAll(clazzesToAdd);
            log.debug("pu={} pu.managedClassNames={}", pu, pu.getManagedClassNames());
            puMap.put(puName,pu);
        }

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
