package user.steps;

import gherkin.lexer.Ar;
import user.User;
import user.UserRepository;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class UserRepositorySteps {
    String firstName;
    String lastName;
    String address;

    int id;

    User user = new User(id, firstName, lastName, address);

    ArrayList<String> resultArray = new ArrayList<>();

    @Given("the database is empty")
    public void theDatabaseIsEmpty() {
        UserRepository.dropTable();
    }

    @When("I create a new user with name {string} last name {string} and address {string}")
    public void iCreateANewUserWithNameLastNameAndAddress(String firstName, String lastName, String address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        User user = new User( 1,firstName, lastName, address);
        UserRepository.insertUser(user);

    }

    @When("I retrieve the user by id")
    public void iRetrieveTheUserById() {
        user = UserRepository.getUserById(1);
    }

    @Then("the retrieved user has the correct details")
    public void theRetrievedUserHasTheCorrectDetails() {
        assertEquals(user.getFirstName(),firstName);
        assertEquals(user.getLastName(),lastName);
        assertEquals(user.getAddress(),address);
    }

    @And("I update the user's address to {string}")
    public void iUpdateTheUserSAddressTo(String address) {
        this.address = "Ocean way 22";
        user.setAddress(address);
        UserRepository.updateUser(user);

    }

    @Then("the user is updated in the database")
    public void theUserIsUpdatedInTheDatabase() {
        user = UserRepository.getUserById(1);
        assertEquals("Blue Ocean 1",user.getAddress());

    }



    @When("I delete the user by passing the user object")
    public void iDeleteTheUserByPassingTheUserObject() {
        UserRepository.deleteUser(user);
    }

    @Then("the user is deleted from the database")
    public void theUserIsDeletedFromTheDatabase() {

        assert(UserRepository.getUserById(1) == null);
        assertEquals(UserRepository.getAllUsers().size(),0);
    }

    @When("I retrieve all users")
    public void iRetrieveAllUsers() {
        resultArray = UserRepository.getAllUsers();
    }

    @Then("the retrieved users are correct")
    public void theRetrievedUsersAreCorrect() {
        assertEquals(2,resultArray.size());
        assertEquals("{id=1, firstName='Bob', lastName='Sponge', address='Plankton way 22'}"+"\n",resultArray.get(0) );
    }
}
