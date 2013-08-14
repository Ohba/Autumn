package co.ohba.autumn.modules;

import co.ohba.autumn.AutumnConfig;
import co.ohba.autumn.StaticFileServlet;
import com.google.common.base.Joiner;
import com.google.common.collect.Maps;
import com.sun.jersey.api.core.PackagesResourceConfig;
import com.sun.jersey.api.json.JSONConfiguration;
import com.sun.jersey.guice.JerseyServletModule;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;
import com.sun.jersey.spi.container.servlet.ServletContainer;
import org.apache.shiro.guice.web.GuiceShiroFilter;

import java.util.List;
import java.util.Map;

public class AutumnJerseyModule extends JerseyServletModule {
	
	private static final String EXCEPTION_MAPPER_PACKAGE = "co.ohba.autumn.jersey";

	private final AutumnConfig atmnCnf;

	public AutumnJerseyModule(AutumnConfig atmnCnf) {
		this.atmnCnf = atmnCnf;
	}

	@Override
	protected void configureServlets() {

        bindings();
        filters();

    }

    void bindings() {
        bind(StaticFileServlet.class).asEagerSingleton();
        bind(GuiceContainer.class).asEagerSingleton();
        //serve("/*").with(StaticFileServlet.class);

        AutumnShiroModule.bindGuiceFilter(binder());
    }

    void filters() {

		/*
		 * some of the `autumn.json` config settings are needed to configure jersey
		 * guice passes the init params to jersey as jersey comes up. 
		 */
		Map<String,String> initParams = Maps.newHashMap();
		List<String> pathPackages = atmnCnf.getPathPackage();
		pathPackages.add(EXCEPTION_MAPPER_PACKAGE);
		initParams.put(PackagesResourceConfig.PROPERTY_PACKAGES, Joiner.on(';').join(pathPackages));
		initParams.put(JSONConfiguration.FEATURE_POJO_MAPPING, atmnCnf.getPojoMapping().toString());
		
		// add a few more params that cant be set in the JSON
		// posible init params are here: http://jersey.java.net/apidocs/1.17/jersey/constant-values.html
		initParams.put(ServletContainer.FEATURE_FILTER_FORWARD_ON_404, "true");
		
		initParams.put(ServletContainer.JSP_TEMPLATES_BASE_PATH, "views");
		
		filter("/*").through(GuiceShiroFilter.class);
		
		// by filter-through (instead of serve-with) then requests that guice+jersey
		// cant handle will be chained along to our default `StaticFileServlet.java`
		filter("/api/*").through(GuiceContainer.class, initParams);
		
		
	}
	
}
