package post.steps;

import io.cucumber.java.en.*;
import post.Post;
import post.PostRepository;

import static org.junit.Assert.assertEquals;

public class PostRepositorySteps {

    int postId;

    int userId = 1;
    String title  = "My first post";
    String body = "This is my first post";

    Post post = new Post(1,userId, title, body);

    @After
    public void afterScenario() {
        PostRepository.dropTable();
    }

    @Given("I have a post with userId {int} title {string} and body {string}")
    public void iHaveAPostWithUserIdTitleAndBody(int userId, String title, String body) {
        assertEquals(userId, post.getUserId());
        assertEquals(title, post.getTitle());
        assertEquals(body, post.getBody());
    }

    @When("I create the post")
    public void iCreateThePost() {
        postId = PostRepository.insert(post);
    }



    @Then("the post should be saved in the repository")
    public void thePostShouldBeSavedInTheRepository() {
        assert(postId != -1);
    }

    @When("I request the post by id")
    public void iRequestThePostById() {
        post = PostRepository.getPostById(postId);
    }

    @Then("the post should be returned")
    public void thePostShouldBeReturned() {
        assertEquals(userId, post.getUserId());
        assertEquals(title, post.getTitle());
        assertEquals(body, post.getBody());
        assertEquals(postId, post.getId());
    }

    @When("I edit the post with userId {int} title {string} and body {string}")
    public void iEditThePostWithUserIdTitleAndBody( int userId, String title, String body ) {
        this.title ="My first post edited";
        this.body =   "This is my first post edited";
        post.setTitle(title);
        post.setBody(body);
        PostRepository.update(post);
    }

    @Then("the post should be updated in the repository")
    public void thePostShouldBeUpdatedInTheRepository() {

       post = PostRepository.getPostById(postId);
        assertEquals(title, post.getTitle());
        assertEquals(body, post.getBody());
    }
}
