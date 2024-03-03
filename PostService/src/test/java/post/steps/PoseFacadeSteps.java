package post.steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import post.Post;
import post.PostFacade;
import post.PostRepository;

import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;

public class PoseFacadeSteps {

    int id, userId;
    String title, body, PostJson;
    boolean processed = false;

    PostFacade operation = new PostFacade();
    PostRepository repository = new PostRepository();
    ArrayList<Post> resultArray = new ArrayList<>();

//    @AfterAll
//    public static void after_all() {
//        PostRepository.dropTable();
//    }

    @Given("request to create a post with the following data parameters userId {int},{string},{string}")
        public void requestToCreateAPostWithTheFollowingDataParametersUserId(int arg0, String arg1, String arg2) {
        this.userId = 1;
        this.title = "My Title";
        this.body = "My post body";

    }

    @When("the request to created post is sent")
    public void theRequestToCreatedPostIsSent() {
        id = operation.createPost(userId, title, body);

    }

    @Then("the post is created")
    public void thePostIsCreated() {
        assertEquals(repository.getPostById(1).getUserId(),userId);
        assertEquals(repository.getPostById(1).getTitle(),title);
        assertEquals(repository.getPostById(1).getBody(),body);
    }
    @And("it returns the post id")
    public void itReturnsThePostId() {
        assertEquals(1,id);
    }

    @When("request to get a post with the following post id {int} is sent")
    public void requestToGetAPostWithTheFollowingPostIdIsSent(int id) {
        this.id = 1;
        PostJson = operation.getPost(id);
    }

    @Then("the post object is returned")
    public void thePostObjectIsReturned() {
        assertEquals("Post={" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", title='" + title + '\'' +
                ", body='" + body + '\'' +
                '}', PostJson);
    }

    @When("request to update a post with the following data parameters post id {int} userId {int},{string},{string} is sent")
    public void requestToUpdateAPostWithTheFollowingDataParametersPostIdIsSent(int id, int userId, String title, String body) {
        this.id = 1;
        this.userId = 1;
        this.title = "Wonderland";
        this.body = "Rabbit hole 22";
        processed = operation.updatePost(id, userId, title, body);
    }

    @Then("the post object is updated")
    public void thePostObjectIsUpdated() {
        assertEquals(userId, repository.getPostById(1).getUserId());
        assertEquals(title, repository.getPostById(1).getTitle());
        assertEquals(body,repository.getPostById(1).getBody());
        assertTrue(processed);
    }

    @When("request to delete a post with the following post id {int} is sent")
    public void requestToDeleteAPostWithTheFollowingPostIdIsSent(int id) {
        this.id = 1;
        processed = operation.deletePost(id);
    }

    @Then("the post object is deleted")
    public void thePostObjectIsDeleted() {
        assertNull(repository.getPostById(1));
        assertTrue(processed);
    }

    @When("request to get all posts is sent")
    public void requestToGetAllPostsIsSent() {
        resultArray = repository.getAllPosts();
    }

    @Then("the posts array is returned")
    public void thePostsArrayIsReturned() {
        assert(!resultArray.isEmpty());
    }

    @When("request to get all posts by user with the following user id {int} is sent")
    public void requestToGetAllPostsByUserWithTheFollowingUserIdIsSent(int userId) {
        userId = 1;
        resultArray = repository.getAllPostsByUserId(userId);
    }
}
