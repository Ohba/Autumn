package com.ohba.autumn.modules;

import java.util.List;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

import com.google.common.base.Joiner;
import com.google.common.collect.Maps;
import com.ohba.autumn.AutumnConfig;
import com.sun.jersey.api.core.PackagesResourceConfig;
import com.sun.jersey.api.json.JSONConfiguration;
import com.sun.jersey.guice.JerseyServletModule;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;
import com.sun.jersey.spi.container.servlet.ServletContainer;

@Slf4j
public class AtmnJerseyModule extends JerseyServletModule {
	
	//TODO: fix the spelling error in the following line -dL
	private static final String EXCPETION_MAPPER_PACKAGE = "com.ohba.autumn.jersey";

	private final AutumnConfig atmnCnf;

	public AtmnJerseyModule(AutumnConfig atmnCnf) {
		this.atmnCnf = atmnCnf;
	}

	@Override
	protected void configureServlets() {
		/*
		 * some of the `autumn.json` config settings are needed to configure jersey
		 * guice passes the init params to jersey as jersey comes up. 
		 */
		Map<String,String> initParams = Maps.newHashMap();
		List<String> pathPackages = atmnCnf.getPathPackage();
		pathPackages.add(EXCPETION_MAPPER_PACKAGE);
		initParams.put(PackagesResourceConfig.PROPERTY_PACKAGES, Joiner.on(';').join(pathPackages));
		initParams.put(JSONConfiguration.FEATURE_POJO_MAPPING, atmnCnf.getPojoMapping().toString());
		
		// add a few more params that cant be set in the JSON
		// posible init params are here: http://jersey.java.net/apidocs/1.17/jersey/constant-values.html
		initParams.put(ServletContainer.FEATURE_FILTER_FORWARD_ON_404, "true");
		
		// by filter-through (instead of serve-with) then requests that guice+jersey
		// cant handle will be chained along to our default `StaticFileServlet.java`
		filter("/*").through(GuiceContainer.class, initParams);
	}
	
}
