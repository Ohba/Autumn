package co.ohba.autumn.modules;

import co.ohba.autumn.AutumnConfig;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Sha512Hash;
import org.apache.shiro.guice.web.ShiroWebModule;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.subject.PrincipalCollection;

import javax.servlet.ServletContext;

public class AutumnShiroModule extends ShiroWebModule {

    private final AutumnConfig atmnCnf;

    public AutumnShiroModule(AutumnConfig atmnCnf, ServletContext sc) {
        super(sc);
        this.atmnCnf = atmnCnf;
    }

    protected void configureShiroWeb() {

        bindRealm().toInstance(
                // buildSimple() // great for quick debugging
                // buildJDBC() // should we use this if they are making JDBC connection
                buildAthrzngRealm() // easiest for non-jdbc like Mongo?
                );

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
        AuthorizingRealm ar = new AuthorizingRealm(new HashedCredentialsMatcher(Sha512Hash.ALGORITHM_NAME)) {
            @Override
            protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
                SimpleAuthorizationInfo sai = new SimpleAuthorizationInfo();
                // ...
                return sai;
            }

            @Override
            protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
                SimpleAuthenticationInfo sai = new SimpleAuthenticationInfo();
                // ...
                return sai;
            }
        };
        return ar;
    }

}