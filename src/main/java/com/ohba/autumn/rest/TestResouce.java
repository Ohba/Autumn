package com.ohba.autumn.rest;

import com.ohba.autumn.service.BogusService;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

@Path("/sup")
@Produces("text/plain")
public class TestResouce {
	
    @Inject private BogusService service;
    
    @GET
    public String get() {
        service.printToConsole("This is an injection test");
        return "Sup!";
    }
	
    @GET
    @Path("/{slang}")
    public String getMore(@PathParam("slang") String slang) {
            return "Sup "+slang+"!";
    }
	
}