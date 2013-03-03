package com.ohba.autumn.rest;

import java.util.Arrays;
import java.util.Collection;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import com.ohba.autumn.pojo.Vehicle;

@Path("/cars")
@Produces("application/json")
public class VehicleResource {

    @GET
    public Collection<Vehicle> getAll(){
            return Arrays.asList(new Vehicle("Toyota","Tundra"), new Vehicle("Ford","Focus"), new Vehicle("Dodge","Dart"));
    }

    @GET
    @Path("/{make}")
    public Vehicle getMake(@PathParam("make") String make){
            return new Vehicle(make, null);
    }
	
}
