/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ohba.autumn;

import java.util.List;
import java.util.Map;

import javax.servlet.annotation.WebListener;

import lombok.extern.slf4j.Slf4j;

import org.apache.bval.guice.ValidationModule;

import com.google.common.base.Joiner;
import com.google.common.collect.Maps;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;
import com.ohba.autumn.modules.JpaModule;
import com.sun.jersey.api.core.PackagesResourceConfig;
import com.sun.jersey.api.json.JSONConfiguration;
import com.sun.jersey.guice.JerseyServletModule;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;
import com.sun.jersey.spi.container.servlet.ServletContainer;

@Slf4j
@WebListener
public class App extends GuiceServletContextListener {
	// WebListener allows code to be executed upon hearing an event
	// essentially the Guice stuff hears that a context is being loaded
	// and bootstraps in all the Guice config

	//TODO: fix the spelling error in the following line -dL
	private static final String EXCPETION_MAPPER_PACKAGE = "com.ohba.autumn.jersey";
	
	private static AutumnConfig myConfig = AutumnConfig.fromResource("autumn.defaults.json","autumn.json");

	@Override
	protected Injector getInjector() {

		// here we tell guice what system will REALLY be our servlet
		// cause Guice is more or less just shimmed in the midddle
		// to provide DI. as you see Jersey will be our Servlet provider
		return Guice.createInjector(new JerseyServletModule() {
			
			@Override
			protected void configureServlets() {
				log.info("AutumnConfig={}",myConfig);
				/*
				 * some of the `autumn.json` config settings are needed to configure jersey
				 * guice passes the init params to jersey as jersey comes up. 
				 */
				Map<String,String> initParams = Maps.newHashMap();
				List<String> pathPackages = myConfig.getPathPackage();
				pathPackages.add(EXCPETION_MAPPER_PACKAGE);
				initParams.put(PackagesResourceConfig.PROPERTY_PACKAGES, Joiner.on(';').join(pathPackages));
				initParams.put(JSONConfiguration.FEATURE_POJO_MAPPING, myConfig.getPojoMapping().toString());
				
				// add a few more params that cant be set in the JSON
				// posible init params are here: http://jersey.java.net/apidocs/1.17/jersey/constant-values.html
				initParams.put(ServletContainer.FEATURE_FILTER_FORWARD_ON_404, "true");
				
				//Adding JPA related module. This just helps separate concerns in the code.
				//All JPA related injections will go in that module.
				install(new JpaModule());
				
				install(new ValidationModule());
				
				// by filter-through (instead of serve-with) then requests that guice+jersey
				// cant handle will be chained along to our default `StaticFileServlet.java`
				filter("/*").through(GuiceContainer.class, initParams);
			}
		});
	}

}
