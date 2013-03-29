package co.ohba.autumn.persistence;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManagerFactory;

import org.eclipse.persistence.internal.jpa.deployment.JPAInitializer;
import org.eclipse.persistence.internal.jpa.deployment.JavaSECMPInitializer;
import org.eclipse.persistence.jpa.PersistenceProvider;

public class AutumnPersistenceProvider extends PersistenceProvider {
	
	List<String> clazzesToAdd;

	@Override
	public JPAInitializer getInitializer(String emName, Map m) {
		ClassLoader classLoader = getClassLoader(emName, m);
        JavaSECMPInitializer jpaInitializer = JavaSECMPInitializer.getJavaSECMPInitializer(classLoader);
        return new AutumnJPAInitializer(jpaInitializer, clazzesToAdd);
	}

	public EntityManagerFactory createEntityManagerFactory(String emName,
			Map<String, String> properties, List<String> clazzes) {
		clazzesToAdd = clazzes;
		return this.createEntityManagerFactory(emName, properties);
	}

}
