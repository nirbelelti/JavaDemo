package comment;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class CommentFacade {
    int id, postId, userId;
    String body;

    private Comment comment = new Comment(id, body, postId, userId);
    public int createComment(int postId, int userId, String body) {
        comment.setUserId(userId);
        comment.setPostId(postId);
        comment.setBody(body);
        comment.setId(CommentRepository.insert(comment));
        return comment.getId();
    }

    public Comment getCommentById(int i) {
        return CommentRepository.findById(i);
    }

    public ArrayList<Comment> getCommentByPost(int i) {
        return CommentRepository.findAllByPostId(i);
    }

    public void updateComment(Comment comment) {
        CommentRepository.update(comment);
    }

    public void deleteComment(int i) {
        comment= CommentRepository.findById(i);
        CommentRepository.delete(comment);
    }
}
