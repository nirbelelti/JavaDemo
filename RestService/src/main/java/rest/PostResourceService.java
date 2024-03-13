package rest;

import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.QueryParam;
import org.rabbitmq.GenericReceiver;
import org.rabbitmq.SenderWithResponse;

public class PostResourceService {

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

    private PostResourceService() {
    }
    public static PostResourceService getInstance() {
        return new PostResourceService();
    }

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

    public String createPost(String userId,String title,String body){
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

    public String updatePost(int id, int userId,  String title, String body){
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

    public String deletePost(int id){
        deletePostReceiver.start();
        deletePostSender.sendMessage(String.valueOf(id));
        String receivedMessage = deletePostReceiver.getReceivedMessage();
        if (receivedMessage != null && !receivedMessage.isEmpty()) {
            // Use the received message instead of the fixed string
            return receivedMessage;
        }
        return "Something went wrong, try again later.";
    }

    public String getPostByUser(int userId) {
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
