Feature: Comment CRUD repository
  Scenario: Save comment to repository
    Given I have a comment without and postId 1 userId 1 and body   "Hello world I am a comment"
    When I save the comment
    Then the comment is saved

    Scenario: Find comment by id
    Given I have a comment without and postId 1 userId 1 and body   "Hello world I am a comment"
    And I save the comment
    When I request to find the comment by id
    Then the comment is returned
