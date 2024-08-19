package rest.adapters;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
//import org.rabbitmq.GenericReceiver;
//import org.rabbitmq.SenderWithResponse;
import rest.CommentResourceService;

@Path("/comments")
public class CommentResource {

    CommentResourceService service = CommentResourceService.getInstance();


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getComments() {
        return "Get all Comments";
    }
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public String createComment(@QueryParam("userId") int userId, @QueryParam("postId") int postId, @QueryParam("body") String body){

        return service.createComment(userId, postId, body);
    }
    @Path("/{id}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public String updateComment(@PathParam("id") int id, @QueryParam("userId") int userId, @QueryParam("postId") int postId, @QueryParam("body") String body){
        return service.updateComment(id, userId, postId, body);
    }
    @Path("/{id}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public String deleteComment(@PathParam("id") int id){
        return service.deleteComment(id);
    }
}
