package com.ohba.autumn;

import javax.servlet.annotation.WebServlet;
import javax.ws.rs.ApplicationPath;

import com.sun.jersey.api.core.PackagesResourceConfig;

@WebServlet(loadOnStartup=1)
@ApplicationPath("")
public class App extends PackagesResourceConfig{

	public App(){
		super("com.ohba.autumn.rest");
	}
}
