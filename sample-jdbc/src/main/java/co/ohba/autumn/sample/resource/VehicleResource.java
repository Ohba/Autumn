package co.ohba.autumn.sample.resource;

import co.ohba.autumn.CrudResource;
import co.ohba.autumn.sample.pojo.Vehicle;
import org.apache.shiro.authz.annotation.RequiresRoles;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.*;
import java.util.List;

@Path("/cars")
@Produces("application/json")
public class VehicleResource extends CrudResource<Vehicle, Long> {
	
	@Inject
	private EntityManager em;

    @Override
    public Vehicle create(Vehicle newCar){
    	em.getTransaction().begin();
		em.persist(newCar);
		em.getTransaction().commit();
		return newCar;
    }

    @Override
    public List<Vehicle> get() {
        return em.createQuery("SELECT v FROM Vehicle v", Vehicle.class).getResultList();
    }

    @Override
    public Vehicle get(@PathParam("id") Long id){
            return em.find(Vehicle.class, id);
    }
    
	@Override
	@RequiresRoles("ADMIN")
	public void delete(@PathParam("id") Long id) {
		em.getTransaction().begin();
		em.remove(em.find(Vehicle.class, id));
		em.getTransaction().commit();
	}
    
	@GET
	@Path("/make")
	public Vehicle persist(@QueryParam("id") Long id,
			@QueryParam("make") String make, @QueryParam("model") String model, @QueryParam("color") String color) {
		Vehicle car = new Vehicle(id, make, model, color);
		em.getTransaction().begin();
		em.persist(car);
		em.getTransaction().commit();
		return car;
	}
	
}
