package com.ohba.autumn;

import javax.ws.rs.ApplicationPath;

import com.sun.jersey.api.core.PackagesResourceConfig;

@ApplicationPath("/*")
public class App extends PackagesResourceConfig {
	
	private static AutumnConfig myConfig = AutumnConfig.readFrom("config.json");

	public App() {
		super(myConfig.toPropertyBag());
	}
	
}
