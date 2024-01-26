Feature: Post CRUD
  Scenario: Create a post object
    Given a user with  parameters userId, title, content.
    When the post is created in the system
    Then the post is successfully created
