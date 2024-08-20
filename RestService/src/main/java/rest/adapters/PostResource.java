package rest.adapters;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import rest.PostResourceService;

@Path("/posts")
public class PostResource {
   PostResourceService service = PostResourceService.getInstance();


    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getPosts() {

        return service.getPosts();
    }
    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getPost(@PathParam("id") String id) {
        return service.getPost(id);
    }
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public String createPost(@QueryParam("userId") String userId, @QueryParam("title") String title, @QueryParam("body") String body){
        return service.createPost(userId, title, body);
    }
    @Path("/{id}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public String updatePost(@PathParam("id") int id, @QueryParam("userId") int userId, @QueryParam("title") String title, @QueryParam("body") String body){
        return service.updatePost(id, userId, title, body);
    }
    @Path("/{id}")
    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    public String deletePost(@PathParam("id") int id){
        return service.deletePost(id);
    }

    @Path("/byUser/{userId}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getPostByUser(@PathParam("userId") int userId) {
        return service.getPostByUser(userId);
    }
}

