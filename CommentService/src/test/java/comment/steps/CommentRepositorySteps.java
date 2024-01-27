package comment.steps;

import comment.Comment;
import comment.CommentRepository;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class CommentRepositorySteps {
    int id, postId, userId;
    String body;
    Comment comment = new Comment(id, body, postId, userId);


    @Given("I have a comment without and postId {int} userId {int} and body   {string}")
    public void iHaveACommentWithOutIdAndWithPostIdUserIdAndBody(int postId, int userId, String body) {
        this.postId = 1;
        this.userId = 1;
        this.body = "Comment body";
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

}
