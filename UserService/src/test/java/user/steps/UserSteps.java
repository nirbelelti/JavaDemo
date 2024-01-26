package user.steps;

import User.UserRepository;
import User.UserService;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;


public class UserSteps {
    String firstName;
    String lastName;
    String address;

    int id;




    @Before
    public void beforeScenario() {
        System.out.println("Before scenario");
        UserRepository.createTable();
    }
    @After
    public void afterScenario() {
        System.out.println("After scenario");
        UserRepository.dropTable();
    }
    UserService user = new UserService(id, firstName, lastName, address);
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
        System.out.println("User id: " + id);
    }

    @Then("the user is successfully created")
    public void theUserIsSuccessfullyCreated() {
        assert(user.getFirstName().equals(firstName));
        assert(user.getLastName().equals(lastName));
        assert(user.getAddress().equals(address));
        assert(user.getId() == id);
        System.out.println("User id: " + id);
    }

    @Given("a user with a first name {string}, last name {string} and address {string}")
    public void aUserWithAFirstNameLastNameAndAddress(String firstName, String lastName, String address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        assert(user.getFirstName().equals(firstName));
        assert(user.getLastName().equals(lastName));
        assert(user.getAddress().equals(address));
    }

    @When("the user is updated name last name and address in the system")
    public void theUserIsUpdatedNameLastNameAndAddressInTheSystem() {
        user.setFirstName("Alice");
        user.setLastName("Wonderland");
        user.setAddress("Hole in the ground 22");
    }

    @Then("the user is successfully updated")
    public void theUserIsSuccessfullyUpdated() {

        assert(user.getFirstName().equals("Alice"));
        assert(user.getLastName().equals("Wonderland"));
        assert(user.getAddress().equals("Hole in the ground 22"));
    }

    @When("the user is called by id in the system")
    public void theUserIsCalledByIdInTheSystem() {

        user = UserRepository.getUserById(id);
    }

    @Then("the user is successfully returned by tha database")
    public void theUserIsSuccessfullyReturnedByThaDatabase() {
        assert(user.getFirstName().equals("Alice"));
        assert(user.getLastName().equals("Wonderland"));
        assert(user.getAddress().equals("Hole in the ground 22"));
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
        assert(UserRepository.getUserById(id) == null);

    }


/////////////////////// Repository steps ////////////////////////////


    @Given("the database is empty")
    public void theDatabaseIsEmpty() {
        UserRepository.dropTable();
    }

    @When("I create a new user with name {string} last name {string} and address {string}")
    public void iCreateANewUserWithNameLastNameAndAddress(String firstName, String lastName, String address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        UserService user = new UserService(1, firstName, lastName, address);
        UserRepository.insertUser(user);

    }

    @Then("the user is inserted with an ID")
    public void theUserIsInsertedWithAnID() {
         assert(UserRepository.getUserById(1).equals(user));
    }

    @When("I retrieve the user by id")
    public void iRetrieveTheUserById() {
        user = UserRepository.getUserById(1);
    }

    @Then("the retrieved user has the correct details")
    public void theRetrievedUserHasTheCorrectDetails() {
        assert(user.getFirstName().equals(firstName));
        assert(user.getLastName().equals(lastName));
        assert(user.getAddress().equals(address));
        assert (user.getId() == id);
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
        assert(user.getAddress().equals("Ocean way 22"));

    }



    @When("I delete the user by passing the user object")
    public void iDeleteTheUserByPassingTheUserObject() {
        UserRepository.deleteUser(user);
    }

    @Then("the user is deleted from the database")
    public void theUserIsDeletedFromTheDatabase() {
        assert(UserRepository.getUserById(1) == null);
    }


//
//    @Given("the database is empty")
//    public void theDatabaseIsEmpty() {
//        repository.dropTable();
//    }
//
////    @When("I create a new user with name {string} and last name {string} and address {string}")
////    public void iCreateANewUserWithNameAndLastNameAndAddress(String arg0, String arg1, String arg2) {
////    }
//
//    @When("I create a new user with name {string} and last name {string} and address {string}")
//    public void iCreateANewUserWithNameAndLastNameAndAddress(String firstName, String lastName, String address) {
//        UserService user = new UserService(1, firstName, lastName, address);
//        UserRepository.insertUser(user);
//    }
//
//    @Then("the user is inserted with an ID")
//    public void theUserIsInsertedWithAnID() {
//       assert(UserRepository.getUserById(1).equals(user));
//
//    }
}
