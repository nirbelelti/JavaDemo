package post.steps;

import io.cucumber.java.en.*;
import post.Post;
import static org.junit.Assert.assertEquals;

public class PostSteps {
   int id;
    int userId;
    String title;
    String body;

    Post post = new Post(id,userId, title, body);


    @Given("a user with  parameters userId, title, body.")
    public void aUserWithParametersUserIdTitleBody() {
        userId = 1;
        title = "title";
        body = "body";
    }

    ;

    @When("^the post is created in the system")
    public void thePostIsCreatedInTheSystem() {
        post.setUserId(userId);
        post.setTitle(title);
        post.setBody(body);
    }

    @Then("the post is successfully created")
    public void thePostIsSuccessfullyCreated() {
        assertEquals(userId, post.getUserId());
        assertEquals(title, post.getTitle());
        assertEquals(body, post.getBody());
    }
}

