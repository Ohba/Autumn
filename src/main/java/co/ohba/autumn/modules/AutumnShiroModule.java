package co.ohba.autumn.modules;

import co.ohba.autumn.AutumnConfig;
import org.apache.shiro.guice.web.ShiroWebModule;
import org.apache.shiro.realm.SimpleAccountRealm;

import javax.servlet.ServletContext;

public class AutumnShiroModule extends ShiroWebModule {

    private final AutumnConfig atmnCnf;

    public AutumnShiroModule(AutumnConfig atmnCnf, ServletContext sc) {
        super(sc);
        this.atmnCnf = atmnCnf;
    }

    protected void configureShiroWeb() {

        bindRealm().toInstance(buildSAR());

        addFilterChain("/*", AUTHC_BASIC);

    }

    private SimpleAccountRealm buildSAR() {
        SimpleAccountRealm sar = new SimpleAccountRealm();
        //              USER        PASS        ROLES ...
        sar.addAccount( "admin",    "admin",    "admin");
        sar.addAccount( "user",     "user",     "user","guest");
        return sar;
    }

}