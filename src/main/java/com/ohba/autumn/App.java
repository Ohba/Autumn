package com.ohba.autumn;

import javax.ws.rs.ApplicationPath;

import com.sun.jersey.api.core.PackagesResourceConfig;

@ApplicationPath("/*")
public class App extends PackagesResourceConfig {

	public App() {
		super("com.ohba.autumn.rest");
	}
	
}
