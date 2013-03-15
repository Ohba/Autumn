package com.ohba.autumn.sample.resource;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.batoo.jpa.JPASettings;
import org.batoo.jpa.core.BatooPersistenceProvider;
import org.postgresql.Driver;

import com.google.common.collect.Maps;
import com.ohba.autumn.sample.pojo.Vehicle;

@Path("/cars")
@Produces("application/json")
public class VehicleResource {
	
	private EntityManager em;

	EntityManager getEM(){
		
		if(this.em==null) {
			Map<String, String> properties = Maps.newHashMap();
			properties.put(JPASettings.JDBC_DRIVER, Driver.class.getName());
			properties.put(JPASettings.JDBC_URL, "jdbc:postgresql://ec2-54-243-234-148.compute-1.amazonaws.com:5432/d6bibaokcf97t3?ssl=true&sslfactory=org.postgresql.ssl.NonValidatingFactory");
			properties.put(JPASettings.JDBC_USER, "zortzjzdfebuui");
			properties.put(JPASettings.JDBC_PASSWORD, "v_ySlclXd0pSHbzWKEuzXAbPLo");
			//properties.put(BJPASettings.DDL, DDLMode.DROP.name());

			EntityManagerFactory emf = new BatooPersistenceProvider().createEntityManagerFactory("batoo", properties , new String[]{
					Vehicle.class.getName(), //
			});

			this.em = emf.createEntityManager();
		}
		
		return this.em;
	}
	
    @GET
    public List<Vehicle> getAll(){
    	
//    	getEM().getTransaction().begin();
//    	
//            List<Vehicle> cars = Arrays.asList(new Vehicle(100l,"Toyota","Tundra"), new Vehicle(200l,"Ford","Focus"), new Vehicle(300l,"Dodge","Dart"));
//            for(Vehicle car : cars) {
//            	getEM().persist(car);
//            }
//            
//            getEM().getTransaction().commit();
            
            return getEM().createQuery("SELECT v FROM Vehicle v", Vehicle.class).getResultList();
    }

    @GET
    @Path("/{id}")
    public Vehicle getMake(@PathParam("id") Long id){
            return getEM().find(Vehicle.class, id);
    }
	
}
