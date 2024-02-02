package user.steps;

import User.UserRepository;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.Assert.assertEquals;


public class UserFacadeTests {
    int id;
    String firstName, lastName, address;

    UserFacade operation = new UserFacade();
    UserRepository repository = new UserRepository();
    @Given("request to create a user with the following data parameters {string},{string},{string}")
    public void requestToCreateAUserWithTheFollowingDataParameters(String firstName, String lastName, String address) {
        this.firstName = "Bob";
        this.lastName = "Sponge";
        this.address = "Plankton way 22";

    }

    @When("the request to created user is sent")
    public void theRequestToCreatedUserIsSent() {
        id = operation.createUser(firstName, lastName, address);

    }

    @Then("the user is created")
    public void theUserIsCreated() {
        assertEquals(repository.getUserById(1).getFirstName(),firstName);
        assertEquals(repository.getUserById(1).getLastName(),lastName);
        assertEquals(repository.getUserById(1).getAddress(),address);
    }
    @And("it returns the user id")
    public void itReturnsTheUserId() {
        assertEquals(1,id);
    }
}
