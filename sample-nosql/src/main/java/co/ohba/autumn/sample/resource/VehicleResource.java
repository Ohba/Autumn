package co.ohba.autumn.sample.resource;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import co.ohba.autumn.sample.pojo.Vehicle;

@Path("/cars")
@Produces("application/json")
public class VehicleResource {
	
	@Inject
	private EntityManager em;

	@GET
	public List<Vehicle> queryAll() {
		return em.createQuery("SELECT v FROM Vehicle v", Vehicle.class).getResultList();
	}
  
    @POST
    public Vehicle create(Vehicle newCar){
    	em.getTransaction().begin();
		em.persist(newCar);
		em.getTransaction().commit();
		return newCar;
    }

    @GET
    @Path("/{id}")
    public Vehicle findById(@PathParam("id") Long id){
            return em.find(Vehicle.class, id);
    }
    
	@DELETE
	@Path("/{id}")
	@RolesAllowed("ADMIN")
	public void delete(@PathParam("id") String id) {
		em.getTransaction().begin();
		em.remove(em.find(Vehicle.class, id));
		em.getTransaction().commit();
	}
    
	@GET
	@Path("/make")
	public Vehicle persist(@QueryParam("id") String id,
			@QueryParam("make") String make, @QueryParam("model") String model, @QueryParam("color") String color) {
		Vehicle car = new Vehicle(id, make, model, color);
		em.getTransaction().begin();
		em.persist(car);
		em.getTransaction().commit();
		return car;
	}
	
}
