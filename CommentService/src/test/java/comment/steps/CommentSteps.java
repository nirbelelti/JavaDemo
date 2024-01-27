package comment.steps;

import comment.Comment;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.Assert.assertEquals;

public class CommentSteps {

    int id;
    int postId;
    int userId;
    String body;

        Comment comment = new Comment(id, body, postId, userId);
        @Given("a new comment object with the following attributes id  body  postId  userId")
        public void aNewCommentObjectWithTheFollowingAttributesIdBodyPost_idUser_id() {
            id = 1;
            postId = 1;
            userId = 1;
            body = "Comment body";
    }

    @When("I create the comment")
    public void iCreateTheComment() {
        comment.setId(id);
        comment.setBody(body);
        comment.setPostId(postId);
        comment.setUserId(userId);
    }

    @Then("the object should have the following attributes id  body  postId  userId")
    public void theObjectShouldHaveTheFollowingAttributesIdBodyPostIdUserId() {
        assertEquals(id, comment.getId());
        assertEquals(body, comment.getBody());
        assertEquals(postId, comment.getPostId());
        assertEquals(userId, comment.getUserId());
    }
}
