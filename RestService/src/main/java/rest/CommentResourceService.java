package rest;

import config.rabbitmq.GenericReceiver;
import config.rabbitmq.SenderWithResponse;
import jakarta.ws.rs.PathParam;

public class CommentResourceService {

    private final SenderWithResponse updateCommentSender;
    private final GenericReceiver updateCommentReceiver;
    private final SenderWithResponse commentSender;
    private final GenericReceiver commentCreateReceiver;
    private final SenderWithResponse deleteCommentSender;
    private final GenericReceiver deleteCommentReceiver;
    private final SenderWithResponse getCommentsByPostSender;
    private final GenericReceiver getCommentsByPostReceiver;

    public CommentResourceService(
            SenderWithResponse updateCommentSender,
            GenericReceiver updateCommentReceiver,
            SenderWithResponse commentSender,
            GenericReceiver commentCreateReceiver,
            SenderWithResponse deleteCommentSender,
            GenericReceiver deleteCommentReceiver,
            SenderWithResponse getCommentsByPostSender,
            GenericReceiver getCommentsByPostReceiver
    ) {
        this.updateCommentSender = updateCommentSender;
        this.updateCommentReceiver = updateCommentReceiver;
        this.commentSender = commentSender;
        this.commentCreateReceiver = commentCreateReceiver;
        this.deleteCommentSender = deleteCommentSender;
        this.deleteCommentReceiver = deleteCommentReceiver;
        this.getCommentsByPostSender = getCommentsByPostSender;
        this.getCommentsByPostReceiver = getCommentsByPostReceiver;
    }

    public static CommentResourceService getInstance() {
        return new CommentResourceService(
                new SenderWithResponse("updateCommentQueue", "updateCommentReplyQueue"),
                new GenericReceiver("updateCommentReplyQueue"),
                new SenderWithResponse("createCommentQueue", "createCommentReplyQueue"),
                new GenericReceiver("createCommentReplyQueue"),
                new SenderWithResponse("deleteCommentQueue", "deleteCommentReplyQueue"),
                new GenericReceiver("deleteCommentReplyQueue"),
                new SenderWithResponse("getCommentsByPostQueue", "getCommentsByPostReplyQueue"),
                new GenericReceiver("getCommentsByPostReplyQueue")
        );
    }

    private String sendMessageAndGetResponse(SenderWithResponse sender, GenericReceiver receiver, String message) {
        receiver.start();
        sender.sendMessage(message);
        String receivedMessage = receiver.getReceivedMessage();
        return (receivedMessage != null && !receivedMessage.isEmpty()) ? receivedMessage : "No response received";
    }

    public String createComment(int userId, int postId, String body) {
        String commentMessage = String.format("{ \"userId\" : \"%s\", \"postId\" : \"%s\", \"body\" : \"%s\" }", userId, postId, body);
        return sendMessageAndGetResponse(commentSender, commentCreateReceiver, commentMessage);
    }

    public String updateComment(int id, int userId, int postId, String body) {
        String commentMessage = String.format("{ \"id\" : \"%s\", \"userId\" : \"%s\", \"postId\" : \"%s\", \"body\" : \"%s\" }", id, userId, postId, body);
        return sendMessageAndGetResponse(updateCommentSender, updateCommentReceiver, commentMessage);
    }

    public String deleteComment(@PathParam("id") String id) {
        String commentMessage = String.format("{ \"id\" : \"%s\" }", id);
        return sendMessageAndGetResponse(deleteCommentSender, deleteCommentReceiver, commentMessage);
    }

    public String getCommentsByPost(int postId) {
        String commentMessage = String.format("{ \"postId\" : \"%s\" }", postId);
        return sendMessageAndGetResponse(getCommentsByPostSender, getCommentsByPostReceiver, commentMessage);
    }
}