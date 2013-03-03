/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ohba.autumn;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;
import com.sun.jersey.guice.JerseyServletModule;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;

/**
 *
 * @author kradolferap
 */
public class GuiceConfig extends GuiceServletContextListener {

    @Override
    protected Injector getInjector() {
        return Guice.createInjector(new JerseyServletModule()
        {
            @Override
            protected void configureServlets()
            {
                serve("/*").with(GuiceContainer.class);
            }
        });
    }
    
}
