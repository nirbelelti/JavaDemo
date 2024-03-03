package rest;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
//import post.Post;
import org.rabbitmq.SenderWithResponse;
import org.rabbitmq.GenericReceiver;
//
//import java.io.IOException;
//import java.util.concurrent.TimeoutException;


@Path("/posts")
public class PostResource {
    SenderWithResponse allPostsSender = new SenderWithResponse("getAllPostsQueue", "allPostReplyQueue");

    SenderWithResponse postSender = new SenderWithResponse("createPostQueue", "createPostReplyQueue");
    SenderWithResponse getPostSender = new SenderWithResponse("getPostQueue", "getPostReplyQueue");
    SenderWithResponse updatePostSender = new SenderWithResponse("updatePostQueue", "updatePostReplyQueue");
    SenderWithResponse deletePostSender = new SenderWithResponse("deletePostQueue", "deletePostReplyQueue");
    SenderWithResponse postByUserSender = new SenderWithResponse("getPostByUserQueue", "getPostByUserReplyQueue");



    GenericReceiver allPostsReceiver = new GenericReceiver("allPostReplyQueue");
    GenericReceiver postCreateReceiver = new GenericReceiver("createPostReplyQueue");
    GenericReceiver postReceiver = new GenericReceiver("getPostReplyQueue");
    GenericReceiver updatePostReceiver = new GenericReceiver("updatePostReplyQueue");
    GenericReceiver deletePostReceiver = new GenericReceiver("deletePostReplyQueue");

    GenericReceiver postByUserReceiver = new GenericReceiver("getPostByUserReplyQueue");


    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getPosts() {
        allPostsReceiver.start();
        allPostsSender.sendMessage("Get all Posts");
        String receivedMessage = allPostsReceiver.getReceivedMessage();
        if (receivedMessage != null && !receivedMessage.isEmpty()) {

            return receivedMessage;
        }
        System.out.println(allPostsReceiver.getReceivedMessage());
        return receivedMessage;
    }
    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getPost(@PathParam("id") String id) {
        postReceiver.start();

        String postMessage = String.format( "{ \"id\" : \"%s\" }", id);
        getPostSender.sendMessage(postMessage);

        String receivedMessage = postReceiver.getReceivedMessage();
        if (receivedMessage != null && !receivedMessage.isEmpty()) {
            // Use the received message instead of the fixed string
            return receivedMessage;
        }

        // Fallback to a default message if the received message is not available
        return "Default response if no message received";
    }
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public String createPost(@QueryParam("userId") String userId, @QueryParam("title") String title, @QueryParam("body") String body){
        postCreateReceiver.start();
        //  String PostMessage =  "{ \"color\" : \"Black\", \"type\" : \"FIAT\" }";
        String postMessage = String.format( "{ \"userId\" : \"%s\", \"title\" : \"%s\", \"body\" : \"%s\" }", userId, title, body);
        // String PostMessage = String.format("{"+"color: %s,Name: %s, LastName: %s, Address: %s", "","post.getFirstName()", "post.getLastName()", "post.getAddress()"+"}");
        postSender.sendMessage(postMessage);

        String receivedMessage = postCreateReceiver.getReceivedMessage();
        if (receivedMessage != null && !receivedMessage.isEmpty()) {
            // Use the received message instead of the fixed string
            return receivedMessage;
        }

        // Fallback to a default message if the received message is not available
        return "Default response if no message received";
    }
    @Path("/{id}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public String updatePost(@PathParam("id") String id, @QueryParam("userId") String userId, @QueryParam("title") String title, @QueryParam("body") String body){
        updatePostReceiver.start();
        String PostMessage = String.format( "{ \"id\" : \"%s\", \"userId\" : \"%s\", \"title\" : \"%s\", \"body\" : \"%s\" }", id, userId, title, body);
        updatePostSender.sendMessage(PostMessage);

        String receivedMessage = updatePostReceiver.getReceivedMessage();
        if (receivedMessage != null && !receivedMessage.isEmpty()) {
            // Use the received message instead of the fixed string
            return receivedMessage;
        }

        return "Something went wrong, try again later.";
    }
    @Path("/{id}")
    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    public String deletePost(@PathParam("id") String id){
        deletePostReceiver.start();
        deletePostSender.sendMessage(id);
        String receivedMessage = deletePostReceiver.getReceivedMessage();
        if (receivedMessage != null && !receivedMessage.isEmpty()) {
            // Use the received message instead of the fixed string
            return receivedMessage;
        }
        return "Something went wrong, try again later.";
    }

    @Path("/byUser/{userId}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getPostByUser(@PathParam("userId") String userId) {
        postByUserReceiver.start();

        String postMessage = String.format( "{ \"userId\" : \"%s\" }", userId);
       postByUserSender.sendMessage(postMessage);

        String receivedMessage = postByUserReceiver.getReceivedMessage();
        if (receivedMessage != null && !receivedMessage.isEmpty()) {
            // Use the received message instead of the fixed string
            return receivedMessage;
        }

        // Fallback to a default message if the received message is not available
        return "Default response if no message received";
    }
}

