package com.ohba.autumn;

import com.ohba.autumn.annotations.AutumnConfigFile;
import javax.ws.rs.ApplicationPath;

import com.sun.jersey.api.core.PackagesResourceConfig;

@AutumnConfigFile(value="Fist Test")
@ApplicationPath("/*")
public class App extends PackagesResourceConfig {

    private static AutumnConfig myConfig = AutumnConfig.readFrom("config.json");

    public App() {
        super(myConfig.toPropertyBag());
        System.out.println(com.ohba.autumn.App.class.getAnnotation(AutumnConfigFile.class).anotherValue());
    }

}