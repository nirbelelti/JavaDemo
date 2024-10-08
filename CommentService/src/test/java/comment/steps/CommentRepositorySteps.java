package comment.steps;

import comment.Comment;
import comment.CommentRepository;
import io.cucumber.java.AfterAll;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class CommentRepositorySteps {
    int id, postId, userId;
    String body;
    Comment comment = new Comment(id, body, postId, userId);
    ArrayList<Comment> commentList = new ArrayList<>();
    @AfterAll
        public static void after_all(){
            CommentRepository.deleteAll();

    }


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

    @When("I request to update the comment body to {string}")
    public void iRequestToUpdateTheCommentBodyTo(String newBody) {
        newBody = "Hello world I am an updated comment";
        comment.setBody(newBody);
        CommentRepository.update(comment);
    }

    @Then("the comment is updated")
    public void theCommentIsUpdated() {
        comment = CommentRepository.findById(id);
        assertEquals("Hello world I am an updated comment", comment.getBody());
    }

    @When("I request to update the comment userId to {int} ad postId to {int}")
    public void iRequestToUpdateTheCommentUserIdToAdPostIdTo(int newUserId, int newPostId) {
        newUserId = 222;
        newPostId = 333;
        comment.setUserId(newUserId);
        comment.setPostId(newPostId);
        CommentRepository.update(comment);
    }

    @Then("the comment is not updated")
    public void theCommentIsNotUpdated() {
        comment = CommentRepository.findById(id);
        assertEquals(1, comment.getUserId());
        assertEquals(1, comment.getPostId());
    }

    @When("I request to delete the comment")
    public void iRequestToDeleteTheComment() {
        CommentRepository.delete(comment);
    }

    @Then("the comment is deleted")
    public void theCommentIsDeleted() {
        assertNull(CommentRepository.findById(id));
    }

    @And("I have a another comment without and postId {int} userId {int} and body   {string}")
    public void iHaveAAnotherCommentWithoutAndPostIdUserIdAndBody(int userId, int postId, String body) {
        this.postId = 2;
        this.userId = 2;
        this.body = "Hello world I am also a comment";
        comment.setId(id);
        comment.setBody(body);
        comment.setPostId(postId);
        comment.setUserId(userId);
    }

    @When("I request to find all comments")
    public void iRequestToFindAllComments() {
        commentList = CommentRepository.findAll();
    }

    @Then("the comments are returned")
    public void theCommentsAreReturned() {
        assertEquals(2, commentList.size());
    }

    @Given("I have empty repository")
    public void iHaveEmptyRepository() {
        CommentRepository.deleteAll();
    }

    @When("I request to find all comments by postId {int}")
    public void iRequestToFindAllCommentsByPostId(int arg0) {
        commentList = CommentRepository.findAllByPostId(1);
    }

    @When("I request to find all comments by userId {int}")
    public void iRequestToFindAllCommentsByUserId(int arg0) {
        commentList = CommentRepository.findAllByUserId(1);
    }
}
