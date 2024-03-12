package rest;

import comment.Comment;
import org.rabbitmq.GenericReceiver;
import org.rabbitmq.SenderWithResponse;

public class CommentResourceService {
    SenderWithResponse updateCommentSender = new SenderWithResponse("updateCommentQueue", "updateCommentReplyQueue");
    GenericReceiver updateCommentReceiver = new GenericReceiver("updateCommentReplyQueue");
    SenderWithResponse commentSender = new SenderWithResponse("createCommentQueue", "createCommentReplyQueue");
    GenericReceiver commentCreateReceiver = new GenericReceiver("createCommentReplyQueue");

    SenderWithResponse deleteCommentSender = new SenderWithResponse("deleteCommentQueue", "deleteCommentReplyQueue");
    GenericReceiver deleteCommentReceiver = new GenericReceiver("deleteCommentReplyQueue");


    private CommentResourceService() {
    }
    public static CommentResourceService getInstance() {
        return new CommentResourceService();
    }

    public String createComment(int userId, int postId, String body) {
        commentCreateReceiver.start();
        String commentMessage = String.format("{ \"userId\" : \"%s\", \"postId\" : \"%s\", \"body\" : \"%s\" }", userId, postId, body);
        // String PostMessage = String.format("{"+"color: %s,Name: %s, LastName: %s, Address: %s", "","post.getFirstName()", "post.getLastName()", "post.getAddress()"+"}");
        commentSender.sendMessage(commentMessage);
        String receivedMessage = commentCreateReceiver.getReceivedMessage();
        if (receivedMessage != null && !receivedMessage.isEmpty()) {
            return receivedMessage;
        }
        return receivedMessage;
    }

    public String updateComment(int id, int userId,int postId, String body) {
        updateCommentReceiver.start();
        String commentMessage = String.format( "{ \"id\" : \"%s\",\"userId\" : \"%s\", \"postId\" : \"%s\", \"body\" : \"%s\" }",id, userId, postId, body);
        updateCommentSender.sendMessage( commentMessage);
        String receivedMessage = updateCommentReceiver.getReceivedMessage();
        if (receivedMessage != null && !receivedMessage.isEmpty()) {
            Comment comment = new Comment(id, body, postId, userId);
            return comment.toString();
        }
        return receivedMessage ;
    }

    public String deleteComment(int id) {
        deleteCommentReceiver.start();
        String commentMessage = String.format("{ \"id\" : \"%s\" }", id);
        deleteCommentSender.sendMessage(commentMessage);
        String receivedMessage = deleteCommentReceiver.getReceivedMessage();
        if (receivedMessage != null && !receivedMessage.isEmpty()) {
            return receivedMessage;
        }
        return receivedMessage;
    }


}
