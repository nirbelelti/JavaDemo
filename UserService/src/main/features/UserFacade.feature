Feature: User functions

  Scenario: Create a user
    Given request to create a user with the following data parameters "Bob","Sponge","Plankton way 22"
    When the request to created user is sent
    Then the user is created
    And it returns the user id

  Scenario: Get a user
    Given request to create a user with the following data parameters "Bob","Sponge","Plankton way 22"
    And the request to created user is sent
    And the user is created
    When request to get a user with the following user id 1 is sent
    Then the user object is returned

    Scenario: Update a user
    Given request to create a user with the following data parameters "Bob","Sponge","Plankton way 22"
    And the request to created user is sent
    And the user is created
    When request to update a user with the following data parametersuser id 1 "Alice","Wonderland","Rabbit hole 22" is sent
    Then the user object is updated

    Scenario: Delete a user
    Given request to create a user with the following data parameters "Bob","Sponge","Plankton way 22"
    And the request to created user is sent
    And the user is created
    When request to delete a user with the following user id 1 is sent
    Then the user object is deleted

      Scenario: Get all users
    Given request to create a user with the following data parameters "Bob","Sponge","Plankton way 22"
    And the request to created user is sent
    And the user is created
    When request to get all users is sent
    Then the users array is returned
