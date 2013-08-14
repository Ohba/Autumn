package co.ohba.autumn.modules;

import co.ohba.autumn.AutumnConfig;
import co.ohba.autumn.FilterChain;
import co.ohba.autumn.HasFilterChains;
import com.google.inject.name.Names;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.guice.web.ShiroWebModule;
import org.apache.shiro.realm.Realm;

import javax.servlet.ServletContext;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

@Slf4j
public class AutumnShiroModule extends ShiroWebModule {

    private final AutumnConfig atmnCnf;

    public AutumnShiroModule(AutumnConfig atmnCnf, ServletContext sc) {
        super(sc);
        this.atmnCnf = atmnCnf;
    }

    protected void configureShiroWeb() {

        bindConstant().annotatedWith(Names.named("shiro.globalSessionTimeout")).to(30000L);
        bindConstant().annotatedWith(Names.named("shiro.securityManager.rememberMeManager.cookie.maxAge")).to(30000L);

        String realmClzz = atmnCnf.getSecurityRealmClass();

        if(realmClzz != null && !realmClzz.equals("")) {

            HasFilterChains filterChainPrvdr = null;

            try {
                Class<?> clazz = Class.forName(realmClzz);
                Constructor<?> ctor = clazz.getConstructor();
                Object realm = ctor.newInstance();
                if(realm instanceof Realm && realm instanceof HasFilterChains){
                    Class<? extends Realm> c = (Class<? extends Realm>) clazz;
                    bindRealm().to(c).asEagerSingleton();
                    filterChainPrvdr = (HasFilterChains) realm;
                } else {
                    log.error("your realm must extend ");
                    return;
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                return;
            } catch (NoSuchMethodException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                return;
            } catch (InvocationTargetException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                return;
            } catch (InstantiationException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                return;
            } catch (IllegalAccessException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                return;
            }

            List<FilterChain> filterChains = filterChainPrvdr.getFilterChains();
            for(FilterChain fc : filterChains){
                addFilterChain(fc.getPattern(), fc.getKeys());
            }

        }

    }

}