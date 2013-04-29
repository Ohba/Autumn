package co.ohba.autumn;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

public class CrudResource<T extends HasID<ID>, ID extends Serializable> {

    // PLEASE OVERRIDE THESE
    protected T         create(T resource)  { throw new NotAllowedException(); }
    protected List<T>   get()               { throw new NotAllowedException(); }
    protected T         get(ID id)          { throw new NotAllowedException(); }
    protected void      update(T resource)  { throw new NotAllowedException(); }
    protected void      delete(ID id)       { throw new NotAllowedException(); }

    static class NotAllowedException extends WebApplicationException{
        public NotAllowedException(){ super(405); }
    }

    @POST
    public Response crudPost(@Context UriInfo uriInfo, T resource) {
        T entity = create(resource);
        Response.ResponseBuilder bldr = null;

        try {
            URL url = new URL( uriInfo.getRequestUri().toString()+"/"+entity.getId() );
            bldr = Response.created(url.toURI()).entity(entity);
        } catch (MalformedURLException | URISyntaxException e) {
            bldr = Response.ok().entity(entity);
        }

        return bldr.build();
    }

    @GET
	public List<T> crudGet() {
		return get();
	}
	
	@GET
	@Path("{id}")
	public T crudGet(@PathParam("id") ID id) {
		return get(id);
	}

    @PUT
    @Path("{id}")
    public void crudPut(@PathParam("id") ID id, T resource) {
        if(!id.equals(resource.getId())){
            // should we throw some error? like BadRequest?
        }
        update(resource);
    }

    @DELETE
    @Path("{id}")
    public void crudDelete(@PathParam("id") ID id) {
        delete(id);
    }
    
}
