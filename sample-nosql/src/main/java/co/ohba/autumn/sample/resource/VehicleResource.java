package co.ohba.autumn.sample.resource;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import lombok.val;
import lombok.extern.slf4j.Slf4j;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.aspectj.lang.annotation.Aspect;

import co.ohba.autumn.sample.pojo.Vehicle;

@Aspect
@Slf4j
@Path("/cars") @Produces("application/json")
public class VehicleResource {
	
	@Inject
	private EntityManager em;

	private void subjectLog() {
    	val s  = SecurityUtils.getSubject();
		log.info("subject: {}", s);
		log.info("hasRole? admin:{} guest:{}", s.hasRole("admin"),s.hasRole("guest"));
		log.info("session: {}", s.getSession());
	}

	@GET
	public List<Vehicle> queryAll() {
		subjectLog();
		return em.createQuery("SELECT v FROM Vehicle v", Vehicle.class).getResultList();
	}
    
	@POST
    public Vehicle create(Vehicle newCar){
		subjectLog();
    	em.getTransaction().begin();
		em.persist(newCar);
		em.getTransaction().commit();
		return newCar;
    }

    @GET @Path("/{id}")
    public Vehicle findById(@PathParam("id") Long id){
    	subjectLog();
        return em.find(Vehicle.class, id);
    }
    
	@DELETE @Path("/{id}")
	@RequiresAuthentication
	@RequiresRoles({"admin"})
	public void delete(@PathParam("id") String id) {
		subjectLog();
		em.getTransaction().begin();
		em.remove(em.find(Vehicle.class, id));
		em.getTransaction().commit();
	}
    
	@GET @Path("/make")
	public Vehicle persist(@QueryParam("id") String id,
			@QueryParam("make") String make, @QueryParam("model") String model, @QueryParam("color") String color) {
		subjectLog();
		Vehicle car = new Vehicle(id, make, model, color);
		em.getTransaction().begin();
		em.persist(car);
		em.getTransaction().commit();
		return car;
	}
	
}
