package co.ohba.autumn.sample.resource;

import co.ohba.autumn.CrudResource;
import co.ohba.autumn.sample.pojo.Vehicle;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.aspectj.lang.annotation.Aspect;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.*;
import java.util.List;

@Aspect
@Slf4j
@Path("/cars") @Produces("application/json")
public class VehicleResource extends CrudResource<Vehicle,String> {
	
	@Inject
	private EntityManager em;

	private void subjectLog() {
    	Subject s  = SecurityUtils.getSubject();
		log.info("subject: {}", s);
		log.info("hasRole? admin:{} guest:{}", s.hasRole("admin"),s.hasRole("guest"));
		log.info("session: {}", s.getSession());
	}

    @Override
    protected Vehicle create(Vehicle newCar) {
        subjectLog();
        em.getTransaction().begin();
        em.persist(newCar);
        em.getTransaction().commit();
        return newCar;
    }

    @Override
    protected List<Vehicle> get() {
        subjectLog();
        return em.createQuery("SELECT v FROM Vehicle v", Vehicle.class).getResultList();
    }

    @Override
    protected Vehicle get(String id) {
        subjectLog();
        return em.find(Vehicle.class, id);
    }

    @Override
    @RequiresAuthentication
    @RequiresRoles({"admin"})
    protected void delete(String id) {
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
		return create(car);
	}
	
}
