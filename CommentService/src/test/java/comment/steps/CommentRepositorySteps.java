package comment.steps;

import comment.Comment;
import comment.CommentRepository;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.Assert.assertEquals;

public class CommentRepositorySteps {
    int id, postId, userId;
    String body;
    Comment comment = new Comment(id, body, postId, userId);


    @Given("I have a comment without and postId {int} userId {int} and body   {string}")
    public void iHaveACommentWithOutIdAndWithPostIdUserIdAndBody(int postId, int userId, String body) {
        this.postId = 1;
        this.userId = 1;
        this.body = "Hello world I am a comment";
        comment.setId(id);
        comment.setBody(body);
        comment.setPostId(postId);
        comment.setUserId(userId);
    }

    @When("I save the comment")
    public void iSaveTheComment() {
       id = CommentRepository.insert(comment);
    }

    @Then("the comment is saved")
    public void theCommentIsSaved() {
        assert(id >0);
    }

    @When("I request to find the comment by id")
    public void iRequestToFindTheCommentById() {
        comment = CommentRepository.findById(id);
    }

    @Then("the comment is returned")
    public void theCommentIsReturned() {
        assertEquals(id, comment.getId());
        assertEquals(body, comment.getBody());
        assertEquals(postId, comment.getPostId());
        assertEquals(userId, comment.getUserId());
    }
}
