package rest;

import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.QueryParam;
import org.rabbitmq.GenericReceiver;
import org.rabbitmq.SenderWithResponse;
import user.User;

public class UserResourceService {

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

    private UserResourceService() {
    }

    public static UserResourceService getInstance() {
        return new UserResourceService();
    }

    public String getUsers() {
        allUsersReceiver.start();
        allUsersSender.sendMessage("Get all users");
        String receivedMessage = allUsersReceiver.getReceivedMessage();
        if (receivedMessage != null && !receivedMessage.isEmpty()) {
            return receivedMessage;
        }
        return "No users Found";
    }

    public String getUser(int id) {
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

    public String createUser(String firstName, String lastName,String address){
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

    public String updateUser(int id, String firstName,  String lastName,  String address){
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

    public String deleteUser(int id){
        deleteUserReceiver.start();
        deleteUserSender.sendMessage(String.valueOf(id));
        String receivedMessage = deleteUserReceiver.getReceivedMessage();
        if (receivedMessage != null && !receivedMessage.isEmpty()) {
            // Use the received message instead of the fixed string
            return receivedMessage;
        }
        return "Something went wrong, try again later.";
    }

}
