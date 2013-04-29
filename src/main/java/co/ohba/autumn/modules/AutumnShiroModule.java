package co.ohba.autumn.modules;

import javax.servlet.ServletContext;

import org.apache.shiro.config.Ini;
import org.apache.shiro.guice.web.ShiroWebModule;
import org.apache.shiro.realm.text.IniRealm;

import co.ohba.autumn.AutumnConfig;

import com.google.inject.Provides;
import com.google.inject.Singleton;

public class AutumnShiroModule extends ShiroWebModule {
	
    private final AutumnConfig atmnCnf;

	public AutumnShiroModule(AutumnConfig atmnCnf, ServletContext sc) {
		super(sc);
		this.atmnCnf = atmnCnf;
	}

	protected void configureShiroWeb() {
		
        try {
            bindRealm().toConstructor(IniRealm.class.getConstructor(Ini.class));
        } catch (NoSuchMethodException e) {
            addError(e);
        }
        
        addFilterChain("/*", AUTHC_BASIC);
        
    }

    @Provides @Singleton
    Ini loadShiroIni() {
        return Ini.fromResourcePath("classpath:shiro.ini");
    }
    
}