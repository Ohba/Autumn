package co.ohba.autumn.sample.rest;

import co.ohba.autumn.sample.pojo.Teammate;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

@Path("/teammates")
@Produces("application/json")
public class TeammateResource  {

	static final Teammate[] teammates = new Teammate[] {
		new Teammate("@narfdre"), new Teammate("@danlangford"), new Teammate("@david_welch")
	};
	
	@GET
	public Teammate[] getTeammates() {
		return teammates;
	}

    @GET
    @Path("{user}")
    public String getTeammates( @PathParam("user") String user) {
        return "hi "+user;
    }

}
