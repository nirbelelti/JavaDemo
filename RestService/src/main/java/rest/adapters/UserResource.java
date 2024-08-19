package rest.adapters;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import rest.UserResourceService;



@Path("/users")
public class UserResource {

    UserResourceService service = UserResourceService.getInstance();



    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getUsers() {
      return service.getUsers();
    }
    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getUser(@PathParam("id") int id) {
        return service.getUser(id);
    }
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public String createUser(@QueryParam("firstName") String firstName, @QueryParam("lastName") String lastName, @QueryParam("address") String address){

        return service.createUser(firstName, lastName, address);
    }
    @Path("/{id}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public String updateUser(@PathParam("id") int id, @QueryParam("firstName") String firstName, @QueryParam("lastName") String lastName, @QueryParam("address") String address){
        return service.updateUser(id, firstName, lastName, address);
    }
    @Path("/{id}")
    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    public String deleteUser(@PathParam("id") int id){
        return service.deleteUser(id);
    }
}
