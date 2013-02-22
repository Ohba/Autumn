package com.ohba.autumn.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("/sup")
@Produces("text/plain")
public class TestResouce {
	
	@GET
    public String get() {
        return "Sup! Mang";
    }
}
