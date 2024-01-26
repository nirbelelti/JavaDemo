package post.steps;

import io.cucumber.java.en.*;
import post.Post;
import static org.junit.Assert.assertEquals;

public class PostSteps {
    int userId;
    String title;
    String content;

    Post post = new Post(userId, title, content);


    @Given("a user with  parameters userId, title, content.")
    public void aUserWithParametersUserIdTitleContent() {
        userId = 1;
        title = "title";
        content = "content";
    }

    ;

    @When("^the post is created in the system")
    public void thePostIsCreatedInTheSystem() {
        post.setUserId(userId);
        post.setTitle(title);
        post.setContent(content);
    }

    @Then("the post is successfully created")
    public void thePostIsSuccessfullyCreated() {
        assertEquals(userId, post.getUserId());
        assertEquals(title, post.getTitle());
        assertEquals(content, post.getContent());
    }
}

