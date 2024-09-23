package rest;

import config.rabbitmq.GenericReceiver;
import config.rabbitmq.SenderWithResponse;

public class UserResourceService {

    private final SenderWithResponse allUsersSender;
    private final SenderWithResponse userSender;
    private final SenderWithResponse getUserSender;
    private final SenderWithResponse updateUserSender;
    private final SenderWithResponse deleteUserSender;

    private final GenericReceiver allUsersReceiver;
    private final GenericReceiver userCreateReceiver;
    private final GenericReceiver userReceiver;
    private final GenericReceiver updateUserReceiver;
    private final GenericReceiver deleteUserReceiver;

    public UserResourceService(
            SenderWithResponse allUsersSender,
            SenderWithResponse userSender,
            SenderWithResponse getUserSender,
            SenderWithResponse updateUserSender,
            SenderWithResponse deleteUserSender,
            GenericReceiver allUsersReceiver,
            GenericReceiver userCreateReceiver,
            GenericReceiver userReceiver,
            GenericReceiver updateUserReceiver,
            GenericReceiver deleteUserReceiver
    ) {
        this.allUsersSender = allUsersSender;
        this.userSender = userSender;
        this.getUserSender = getUserSender;
        this.updateUserSender = updateUserSender;
        this.deleteUserSender = deleteUserSender;
        this.allUsersReceiver = allUsersReceiver;
        this.userCreateReceiver = userCreateReceiver;
        this.userReceiver = userReceiver;
        this.updateUserReceiver = updateUserReceiver;
        this.deleteUserReceiver = deleteUserReceiver;
    }

    public static UserResourceService getInstance() {
        return new UserResourceService(
                new SenderWithResponse("getAllUsersQueue", "allUserReplyQueue"),
                new SenderWithResponse("createUserQueue", "createUserReplyQueue"),
                new SenderWithResponse("getUserQueue", "getUserReplyQueue"),
                new SenderWithResponse("updateUserQueue", "updateUserReplyQueue"),
                new SenderWithResponse("deleteUserQueue", "deleteUserReplyQueue"),
                new GenericReceiver("allUserReplyQueue"),
                new GenericReceiver("createUserReplyQueue"),
                new GenericReceiver("getUserReplyQueue"),
                new GenericReceiver("updateUserReplyQueue"),
                new GenericReceiver("deleteUserReplyQueue")
        );
    }


    private String sendMessageAndGetResponse(SenderWithResponse sender, GenericReceiver receiver, String message) {
        receiver.start();
        sender.sendMessage(message);
        String receivedMessage = receiver.getReceivedMessage();
        return (receivedMessage != null && !receivedMessage.isEmpty()) ? receivedMessage : "Default response if no message received";
    }

    public String getUsers() {
        return sendMessageAndGetResponse(allUsersSender, allUsersReceiver, "Get all users");
    }

    public String getUser(int id) {
        String userMessage = String.format("{ \"id\" : \"%s\" }", id);
        return sendMessageAndGetResponse(getUserSender, userReceiver, userMessage);
    }

    public String createUser(String firstName, String lastName, String address) {
        String userMessage = String.format("{ \"firstName\" : \"%s\", \"lastName\" : \"%s\", \"address\" : \"%s\" }", firstName, lastName, address);
        return sendMessageAndGetResponse(userSender, userCreateReceiver, userMessage);
    }

    public String updateUser(int id, String firstName, String lastName, String address) {
        String userMessage = String.format("{ \"id\" : \"%s\", \"firstName\" : \"%s\", \"lastName\" : \"%s\", \"address\" : \"%s\" }", id, firstName, lastName, address);
        return sendMessageAndGetResponse(updateUserSender, updateUserReceiver, userMessage);
    }

    public String deleteUser(int id) {
        return sendMessageAndGetResponse(deleteUserSender, deleteUserReceiver, String.valueOf(id));
    }
}