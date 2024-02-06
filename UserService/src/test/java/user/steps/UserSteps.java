package user.steps;

import user.UserRepository;
import user.User;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;


public class UserSteps {
    String firstName;
    String lastName;
    String address;

    int id;




    @Before
    public void beforeScenario() {
        UserRepository.createTable();
    }
//    @After
//    public void afterScenario() {
//        UserRepository.dropTable();
//    }
    User user = new User(id, firstName, lastName, address);
    @Given("a user with  parameters first name, last name and address.")
    public void aUserWithFirstNameLastNameAndAddress() {
        firstName = "Bob";
        lastName = "Sponge";
        address = "Plankton way 22";
    }

    @When("the user is created in the system")
    public void theUserIsCreatedInTheSystem() {
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setAddress(address);
        id = UserRepository.insertUser(user);
        user.setId(id);
    }

    @Then("the user is successfully created")
    public void theUserIsSuccessfullyCreated() {
        assert(user.getFirstName().equals(firstName));
        assert(user.getLastName().equals(lastName));
        assert(user.getAddress().equals(address));
        assert(user.getId() == id);
    }

    @Given("a user with a first name {string}, last name {string} and address {string}")
    public void aUserWithAFirstNameLastNameAndAddress(String firstName, String lastName, String address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
    }

    @When("the user is updated name last name and address in the system")
    public void theUserIsUpdatedNameLastNameAndAddressInTheSystem() {
        user.setFirstName("Alice");
        user.setLastName("Wonderland");
        user.setAddress("Hole in the ground 22");
    }

    @Then("the user is successfully updated")
    public void theUserIsSuccessfullyUpdated() {
        assertEquals(user.getFirstName(), "Alice");
        assertEquals(user.getLastName(), "Wonderland");
        assertEquals(user.getAddress(), "Hole in the ground 22");
    }

    @When("the user is called by id in the system")
    public void theUserIsCalledByIdInTheSystem() {

        user = UserRepository.getUserById(id);
    }

    @Then("the user is successfully returned by tha database")
    public void theUserIsSuccessfullyReturnedByThaDatabase() {
        assertEquals( "Bob",user.getFirstName());
        assertEquals( "Sponge",user.getLastName());
        assertEquals("Plankton way 22",user.getAddress());

    }

    @When("the user is updated  the first name last and address to name to {string}, {string} and {string}")
    public void theUserIsUpdatedTheFirstNameLastAndAddressToNameToAnd(String firstName, String lastName, String address) {
        firstName = "Alice";
        lastName = "Wonderland";
        address = "Hole in the ground 22";
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setAddress(address);
        UserRepository.updateUser(user);
    }

    @When("the user is deleted in the system")
    public void theUserIsDeletedInTheSystem() {
        UserRepository.deleteUser(user);
    }

    @Then("the user is successfully deleted")
    public void theUserIsSuccessfullyDeleted() {
        assertNull(UserRepository.getUserById(id));

    }
}
