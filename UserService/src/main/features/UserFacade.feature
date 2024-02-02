Feature: Create a user functionality
  Scenario: Create a user
    Given request to create a user with the following data parameters "Bob","Sponge","Plankton way 22"
    When the request to created user is sent
    Then the user is created
     And it returns the user id
