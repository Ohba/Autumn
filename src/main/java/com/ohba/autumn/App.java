/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ohba.autumn;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.annotation.WebListener;

import org.apache.bval.guice.ValidationModule;
import org.apache.shiro.guice.aop.ShiroAopModule;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;
import com.ohba.autumn.modules.AtmnJerseyModule;
import com.ohba.autumn.modules.AtmnShiroModule;
import com.ohba.autumn.modules.JpaModule;

@WebListener
public class App extends GuiceServletContextListener {
	// WebListener allows code to be executed upon hearing an event
	// essentially the Guice stuff hears that a context is being loaded
	// and bootstraps in all the Guice config

	private static AutumnConfig atmnCnf = AutumnConfig.fromResource("autumn.defaults.json","autumn.json");
	private ServletContext servletContext;

	@Override
	protected Injector getInjector() {

		return Guice.createInjector(
				
				// here we tell guice what system will REALLY be our servlet
				// cause Guice is more or less just shimmed in the midddle
				// to provide DI. as you see Jersey will be our Servlet provider
				new AtmnJerseyModule(atmnCnf),
				
				//Adding JPA related module. This just helps separate concerns in the code.
				//All JPA related injections will go in that module.
				new JpaModule(atmnCnf),
				
				// already provided in guice is jsr303 hooks
				new ValidationModule(),
				
				// our security config
				new AtmnShiroModule(atmnCnf, servletContext),
				// support security annotations
				new ShiroAopModule()
				
			);
	}
	
	@Override 
    public void contextInitialized(ServletContextEvent servletContextEvent) { 
            this.servletContext = servletContextEvent.getServletContext(); 
            super.contextInitialized(servletContextEvent); 
    } 

}
