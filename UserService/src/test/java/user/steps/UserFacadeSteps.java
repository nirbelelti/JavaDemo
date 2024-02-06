package user.steps;

import user.UserRepository;
import user.UserFacade;
import io.cucumber.java.AfterAll;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.ArrayList;

import static org.junit.Assert.*;


public class UserFacadeSteps {
    int id;
    String firstName, lastName, address;
    String userJson;
    boolean processed = false;

    UserFacade operation = new UserFacade();
    UserRepository repository = new UserRepository();
    ArrayList<String> resultArray = new ArrayList<>();

    @AfterAll
    public static void after_all() {
        UserRepository.dropTable();
    }

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

    @When("request to get a user with the following user id {int} is sent")
    public void requestToGetAUserWithTheFollowingUserIdIsSent(int id) {
        this.id = 1;
       userJson = operation.getUser(id);
    }

    @Then("the user object is returned")
    public void theUserObjectIsReturned() {
        assertEquals(userJson,"User={" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", address='" + address + '\'' +
                '}');
    }

    @When("request to update a user with the following data parametersuser id {int} {string},{string},{string} is sent")
    public void requestToUpdateAUserWithTheFollowingDataParametersuserIdIsSent(int id, String firstName, String lastName, String address) {
        this.id = 1;
        this.firstName = "Alice";
        this.lastName = "Wonderland";
        this.address = "Rabbit hole 22";
        processed = operation.updateUser(id, firstName, lastName, address);
    }

    @Then("the user object is updated")
    public void theUserObjectIsUpdated() {
        assertEquals(firstName, repository.getUserById(1).getFirstName());
        assertEquals(lastName, repository.getUserById(1).getLastName());
        assertEquals(address,repository.getUserById(1).getAddress());
        assertTrue(processed);
    }

    @When("request to delete a user with the following user id {int} is sent")
    public void requestToDeleteAUserWithTheFollowingUserIdIsSent(int id) {
        this.id = 1;
        processed = operation.deleteUser(id);
    }

    @Then("the user object is deleted")
    public void theUserObjectIsDeleted() {
        assertNull(repository.getUserById(1));
        assertTrue(processed);
    }

    @When("request to get all users is sent")
    public void requestToGetAllUsersIsSent() {
         resultArray = repository.getAllUsers();
    }

    @Then("the users array is returned")
    public void theUsersArrayIsReturned() {
        assert(!resultArray.isEmpty());
    }
}
