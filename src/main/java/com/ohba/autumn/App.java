/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ohba.autumn;

import javax.servlet.annotation.WebListener;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;
import com.sun.jersey.guice.JerseyServletModule;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;

/**
 * 
 * @author kradolferap
 */
@WebListener
public class App extends GuiceServletContextListener {
	// WebListener allows code to be executed upon hearing an event
	// essentially the Guice stuff hears that a context is being loaded
	// and bootstraps in all the Guice config

	private static AutumnConfig myConfig = AutumnConfig.fromResource("config.json");

	@Override
	protected Injector getInjector() {

		// here we tell guice what system will REALLY be our servlet
		// cause Guice is more or less just shimmed in the midddle
		// to provide DI. as you see Jersey will be our Servlet provider
		return Guice.createInjector(new JerseyServletModule() {
			
			@Override
			protected void configureServlets() {
				// guice passes the init params to jersey as jersey comes up. 
				// as any jersey config goes in there
				serve("/*").with(GuiceContainer.class, myConfig.toInitParams());
			}
		});
	}

}
