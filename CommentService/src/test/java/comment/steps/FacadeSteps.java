package comment.steps;

import comment.Comment;
import comment.CommentFacade;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.ArrayList;


public class FacadeSteps {
    int id,postId, userId;
    String body;
    CommentFacade operation = new CommentFacade();
    Comment comment = new Comment(id, body, postId, userId);
    ArrayList<Comment> comments = new ArrayList<>();
    @Given("a request to comment on a post with the following data postId {int} userId {int} comment {string}")
    public void aRequestToCommentOnAPostWithTheFollowingDataPostIdUserIdComment(int postId, int userId, String body) {
        this.postId = 1;
        this.userId = 1;
        this.body = "Hello world I am a comment";

    }

    @When("the request is processed")
    public void theRequestIsProcessed() {
        System.out.println("Processing request" + postId + userId + body);
        id = operation.createComment(postId, userId, body);
        comment.setId(id);
        System.out.println("Comment created with id: " + id);


    }

    @Then("the response should be successful")
    public void theResponseShouldBeSuccessful() {
        assert(id > 0);
    }


    @When("a request to get a comment by id with the following data postId {int}")
    public void aRequestToGetACommentByIdWithTheFollowingDataPostId(int arg0) {
        comments = operation.getCommentByPost(1);
    }

    @Then("the response should contain the following data commentId {int} postId {int} userId {int} comment {string}")
    public void theResponseShouldContainTheFollowingDataCommentIdPostIdUserIdComment(int arg0, int arg1, int arg2, String arg3) {
        comment = comments.get(1);
        assert(comment.getId() == 1);
        assert(comment.getPostId() == 1);
        assert(comment.getUserId() == 1);
        assert(comment.getBody().equals("Hello world I am a comment"));

    }


    @When("a request to get a comment by id with the following data id {int}")
    public void aRequestToGetACommentByIdWithTheFollowingDataId(int arg0) {
        comment = operation.getCommentById(1);
    }

    @Then("the response should contain the comment with the following data postId {int} userId {int} comment {string}")
    public void theResponseShouldContainTheCommentWithTheFollowingDataPostIdUserIdComment(int arg0, int arg1, String arg2) {
        assert(comment.getPostId() == 1);
        assert(comment.getUserId() == 1);
        assert(comment.getBody().equals("Hello world I am a comment"));
    }

    @When("a request to update a comment with the following data id {int} comment {string}")
    public void aRequestToUpdateACommentWithTheFollowingDataIdComment(int arg0, String arg1) {
        comment.setBody("Hello world I am an updated comment");
        operation.updateComment(comment);
    }

    @When("a request to delete a comment with the following data id {int}")
    public void aRequestToDeleteACommentWithTheFollowingDataId(int arg0) {
        operation.deleteComment(1);
    }
}
