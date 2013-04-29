package co.ohba.autumn.sample.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import co.ohba.autumn.sample.pojo.Teammate;

@Path("/teammates")
@Produces("application/json")
public class TeammateResource {

	static final Teammate[] teammates = new Teammate[] {
		new Teammate("@narfdre"), new Teammate("@danlangford"), new Teammate("@david_welch")
	};
	
	@GET
	public Teammate[] getTeammates() {
		return teammates;
	} 
	
}
