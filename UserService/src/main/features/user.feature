Feature: Person crud
  Scenario: Create a person
    Given a user with  parameters first name, last name and address.
    When the user is created in the system
    Then the user is successfully created

   Scenario: Read a person
    Given a user with a first name "Bob", last name "Sponge" and address "Plankton way 22"
    And the user is created in the system
    When the user is called by id in the system
    Then the user is successfully returned by tha database

  Scenario: Update a person
    Given a user with a first name "Bob", last name "Sponge" and address "Plankton way 22"
    And the user is created in the system
    And the user is called by id in the system
    When the user is updated  the first name last and address to name to "Alice", "Wonderland" and "Hole in the ground 22"
    Then the user is successfully updated

  Scenario: Delete a person
    Given a user with a first name "Bob", last name "Sponge" and address "Plankton way 22"
    And the user is created in the system
    When the user is deleted in the system
    Then the user is successfully deleted


