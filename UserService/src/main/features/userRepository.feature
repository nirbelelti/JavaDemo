Feature: Testing User CRUD operations



  Scenario: Retrieve a user
    Given the database is empty
    And I create a new user with name "Bob" last name "Sponge" and address "Plankton way 22"
    When I retrieve the user by id
    Then the retrieved user has the correct details

    Scenario: Retrieve and update a user
      Given the database is empty
      And I create a new user with name "Bob" last name "Sponge" and address "Plankton way 22"
      When I retrieve the user by id
      And I update the user's address to "Blue Ocean 1"
      Then the user is updated in the database

  Scenario: Delete a user
    Given the database is empty
    And I create a new user with name "Bob" last name "Sponge" and address "Plankton way 22"
    And I retrieve the user by id
    When I delete the user by passing the user object
    Then the user is deleted from the database