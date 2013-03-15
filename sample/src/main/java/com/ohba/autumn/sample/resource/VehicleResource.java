package com.ohba.autumn.sample.resource;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import com.ohba.autumn.sample.pojo.Vehicle;

@Path("/cars")
@Produces("application/json")
public class VehicleResource {
	
	@Inject
	private EntityManager em;

    @GET
    public List<Vehicle> queryAll(){
            return em.createQuery("SELECT v FROM Vehicle v", Vehicle.class).getResultList();
    }

    @GET
    @Path("/{id}")
    public Vehicle findById(@PathParam("id") Long id){
            return em.find(Vehicle.class, id);
    }
    
	@GET
	@Path("/make")
	public Vehicle persist(@QueryParam("id") Long id,
			@QueryParam("make") String make, @QueryParam("model") String model) {
		Vehicle car = new Vehicle(id, make, model);
		em.getTransaction().begin();
		em.persist(car);
		em.getTransaction().commit();
		return car;
	}
	
}
