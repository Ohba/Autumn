package co.ohba.autumn.modules;

import co.ohba.autumn.AutumnConfig;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.crypto.hash.Sha512Hash;
import org.apache.shiro.guice.web.ShiroWebModule;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.subject.PrincipalCollection;

import javax.servlet.ServletContext;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class AutumnShiroModule extends ShiroWebModule {

    private final AutumnConfig atmnCnf;

    public AutumnShiroModule(AutumnConfig atmnCnf, ServletContext sc) {
        super(sc);
        this.atmnCnf = atmnCnf;
    }

    protected void configureShiroWeb() {

        String realmClzz = atmnCnf.getSecurityRealmClass();
        if(realmClzz != null && !realmClzz.equals("")){

            try {
                Class<?> clazz = Class.forName(realmClzz);
                Constructor<?> ctor = clazz.getConstructor();
                Object realm = ctor.newInstance();
                if(realm instanceof Realm){
                    bindRealm().toInstance((Realm) realm);
                } else {

                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            } catch (NoSuchMethodException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            } catch (InvocationTargetException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            } catch (InstantiationException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            } catch (IllegalAccessException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }

        }

//        bindRealm().toInstance(
                // buildSimple() // great for quick debugging
                // buildJDBC() // should we use this if they are making JDBC connection
//                buildAthrzngRealm() // easiest for non-jdbc like Mongo?
//                );

        // DISABLE FOR NOW
        //addFilterChain("/*", AUTHC_BASIC);

    }

    private SimpleAccountRealm buildSimple() {
        SimpleAccountRealm sar = new SimpleAccountRealm();
        //              USER        PASS        ROLES ...
        sar.addAccount( "admin",    "admin",    "admin");
        sar.addAccount( "user",     "user",     "user","guest");
        return sar;
    }

    private JdbcRealm buildJdbc() {
        JdbcRealm jr= new JdbcRealm();
        // TODO fill this all out if you dont like their defaults
        jr.setDataSource( /*...*/ null );
        jr.setAuthenticationQuery( /*...*/ null);
        jr.setUserRolesQuery( /*...*/ null);
        // etc. and there are MORE
        return jr;
    }

    private AuthorizingRealm buildAthrzngRealm() {
        return new AuthorizingRealm(new HashedCredentialsMatcher(Sha512Hash.ALGORITHM_NAME)) {

            @Override
            protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }
        };
    }

}