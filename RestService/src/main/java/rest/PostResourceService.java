package rest;

import jakarta.ws.rs.PathParam;
import config.rabbitmq.GenericReceiver;
import config.rabbitmq.SenderWithResponse;

import java.util.ArrayList;

public class PostResourceService {

    private final SenderWithResponse allPostsSender;
    private final SenderWithResponse postSender;
    private final SenderWithResponse getPostSender;
    private final SenderWithResponse updatePostSender;
    private final SenderWithResponse deletePostSender;
    private final SenderWithResponse postByUserSender;

    private final GenericReceiver allPostsReceiver;
    private final GenericReceiver postCreateReceiver;
    private final GenericReceiver postReceiver;
    private final GenericReceiver updatePostReceiver;
    private final GenericReceiver deletePostReceiver;
    private final GenericReceiver postByUserReceiver;

    private final ArrayList<String> result = new ArrayList<>();

    // Constructor for dependency injection
    public PostResourceService(
            SenderWithResponse allPostsSender,
            SenderWithResponse postSender,
            SenderWithResponse getPostSender,
            SenderWithResponse updatePostSender,
            SenderWithResponse deletePostSender,
            SenderWithResponse postByUserSender,
            GenericReceiver allPostsReceiver,
            GenericReceiver postCreateReceiver,
            GenericReceiver postReceiver,
            GenericReceiver updatePostReceiver,
            GenericReceiver deletePostReceiver,
            GenericReceiver postByUserReceiver
    ) {
        this.allPostsSender = allPostsSender;
        this.postSender = postSender;
        this.getPostSender = getPostSender;
        this.updatePostSender = updatePostSender;
        this.deletePostSender = deletePostSender;
        this.postByUserSender = postByUserSender;
        this.allPostsReceiver = allPostsReceiver;
        this.postCreateReceiver = postCreateReceiver;
        this.postReceiver = postReceiver;
        this.updatePostReceiver = updatePostReceiver;
        this.deletePostReceiver = deletePostReceiver;
        this.postByUserReceiver = postByUserReceiver;
    }

    // Factory method with default instances
    public static PostResourceService getInstance() {
        return new PostResourceService(
                new SenderWithResponse("getAllPostsQueue", "allPostReplyQueue"),
                new SenderWithResponse("createPostQueue", "createPostReplyQueue"),
                new SenderWithResponse("getPostQueue", "getPostReplyQueue"),
                new SenderWithResponse("updatePostQueue", "updatePostReplyQueue"),
                new SenderWithResponse("deletePostQueue", "deletePostReplyQueue"),
                new SenderWithResponse("getPostByUserQueue", "getPostByUserReplyQueue"),
                new GenericReceiver("allPostReplyQueue"),
                new GenericReceiver("createPostReplyQueue"),
                new GenericReceiver("getPostReplyQueue"),
                new GenericReceiver("updatePostReplyQueue"),
                new GenericReceiver("deletePostReplyQueue"),
                new GenericReceiver("getPostByUserReplyQueue")
        );
    }

    // Common method to handle message sending and receiving
    private String sendMessageAndGetResponse(SenderWithResponse sender, GenericReceiver receiver, String message) {
        receiver.start();
        sender.sendMessage(message);
        String receivedMessage = receiver.getReceivedMessage();
        return (receivedMessage != null && !receivedMessage.isEmpty()) ? receivedMessage : "Default response if no message received";
    }

    public String getPosts() {
        return sendMessageAndGetResponse(allPostsSender, allPostsReceiver, "Get all Posts");
    }

    public String getPost(@PathParam("id") String id) {
        String postMessage = String.format("{ \"id\" : \"%s\" }", id);
        return sendMessageAndGetResponse(getPostSender, postReceiver, postMessage);
    }

    public String createPost(String userId, String title, String body) {
        String postMessage = String.format("{ \"userId\" : \"%s\", \"title\" : \"%s\", \"body\" : \"%s\" }", userId, title, body);
        return sendMessageAndGetResponse(postSender, postCreateReceiver, postMessage);
    }

    public String updatePost(int id, int userId, String title, String body) {
        String postMessage = String.format("{ \"id\" : \"%s\", \"userId\" : \"%s\", \"title\" : \"%s\", \"body\" : \"%s\" }", id, userId, title, body);
        return sendMessageAndGetResponse(updatePostSender, updatePostReceiver, postMessage);
    }

    public String deletePost(int id) {
        return sendMessageAndGetResponse(deletePostSender, deletePostReceiver, String.valueOf(id));
    }

    public String getPostByUser(int userId) {
        String postMessage = String.format("{ \"userId\" : \"%s\" }", userId);
        return sendMessageAndGetResponse(postByUserSender, postByUserReceiver, postMessage);
    }

    public synchronized String getPostAndComments(int id) {
        String post = getPost(String.valueOf(id));
        String comments = CommentResourceService.getInstance().getCommentsByPost(id);
        while (comments == null)
        {
            System.out.println("Waiting for comments..." );
        }
        System.out.println("Comments: " + comments);

        String response = "";
        String[] parts = post.split("}");
        response += parts[0];
        response += ", \"comments\": ";
        response += comments;
        response += "}";

        return response;
    }
}
