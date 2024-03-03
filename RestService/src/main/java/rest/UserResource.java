package rest;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
//import post.Post;
import user.User;
import jakarta.ws.rs.core.Response;
import org.rabbitmq.SenderWithResponse;
import org.rabbitmq.GenericReceiver;

import java.lang.reflect.Array;
//
//import java.io.IOException;
//import java.util.concurrent.TimeoutException;


@Path("/users")
public class UserResource {
    SenderWithResponse allUsersSender = new SenderWithResponse("getAllUsersQueue", "allUserReplyQueue");

    SenderWithResponse userSender = new SenderWithResponse("createUserQueue", "createUserReplyQueue");
    SenderWithResponse getUserSender = new SenderWithResponse("getUserQueue", "getUserReplyQueue");
    SenderWithResponse updateUserSender = new SenderWithResponse("updateUserQueue", "updateUserReplyQueue");
    SenderWithResponse deleteUserSender = new SenderWithResponse("deleteUserQueue", "deleteUserReplyQueue");



    GenericReceiver allUsersReceiver = new GenericReceiver("allUserReplyQueue");
    GenericReceiver userCreateReceiver = new GenericReceiver("createUserReplyQueue");
    GenericReceiver userReceiver = new GenericReceiver("getUserReplyQueue");
    GenericReceiver updateUserReceiver = new GenericReceiver("updateUserReplyQueue");
    GenericReceiver deleteUserReceiver = new GenericReceiver("deleteUserReplyQueue");


    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getUsers() {
        allUsersReceiver.start();
        allUsersSender.sendMessage("Get all users");
        String receivedMessage = allUsersReceiver.getReceivedMessage();
        if (receivedMessage != null && !receivedMessage.isEmpty()) {
            return receivedMessage;
        }
        return "No users Found";
    }
    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getUser(@PathParam("id") String id) {
        userReceiver.start();

        String userMessage = String.format( "{ \"id\" : \"%s\" }", id);
        getUserSender.sendMessage(userMessage);

        String receivedMessage = userReceiver.getReceivedMessage();
        if (receivedMessage != null && !receivedMessage.isEmpty()) {
            // Use the received message instead of the fixed string
            return receivedMessage;
        }

        // Fallback to a default message if the received message is not available
        return "Default response if no message received";
    }
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public String createUser(@QueryParam("firstName") String firstName, @QueryParam("lastName") String lastName, @QueryParam("address") String address){
        userCreateReceiver.start();
        //  String userMessage =  "{ \"color\" : \"Black\", \"type\" : \"FIAT\" }";
        String userMessage = String.format( "{ \"firstName\" : \"%s\", \"lastName\" : \"%s\", \"address\" : \"%s\" }", firstName, lastName, address);
        // String userMessage = String.format("{"+"color: %s,Name: %s, LastName: %s, Address: %s", "","user.getFirstName()", "user.getLastName()", "user.getAddress()"+"}");
        userSender.sendMessage(userMessage);

        String receivedMessage = userCreateReceiver.getReceivedMessage();
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
    public String updateUser(@PathParam("id") String id, @QueryParam("firstName") String firstName, @QueryParam("lastName") String lastName, @QueryParam("address") String address){
        System.out.println("UserResource.updateUser: "+id );
        System.out.println("UserResource.updateUser: "+firstName );
        System.out.println("UserResource.updateUser: "+lastName );
        System.out.println("UserResource.updateUser: "+address );
        updateUserReceiver.start();
        String userMessage = String.format( "{ \"id\" : \"%s\", \"firstName\" : \"%s\", \"lastName\" : \"%s\", \"address\" : \"%s\" }", id, firstName, lastName, address);
        updateUserSender.sendMessage(userMessage);

        String receivedMessage = updateUserReceiver.getReceivedMessage();
        if (receivedMessage != null && !receivedMessage.isEmpty()) {
            // Use the received message instead of the fixed string
            return receivedMessage;
        }

        return "Something went wrong, try again later.";
    }
    @Path("/{id}")
    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    public String deleteUser(@PathParam("id") String id){
        deleteUserReceiver.start();
        deleteUserSender.sendMessage(id);
        String receivedMessage = deleteUserReceiver.getReceivedMessage();
        if (receivedMessage != null && !receivedMessage.isEmpty()) {
            // Use the received message instead of the fixed string
            return receivedMessage;
        }
        return "Something went wrong, try again later.";
    }
}
