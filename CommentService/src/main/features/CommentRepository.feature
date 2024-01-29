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

  Scenario: update comment body
    Given I have a comment without and postId 1 userId 1 and body   "Hello world I am a comment"
    And I save the comment
    And I request to find the comment by id
    When I request to update the comment body to "Hello world I am an updated comment"
    Then the comment is updated

  Scenario: It should not be possible to update a comment userId and postId
    Given I have a comment without and postId 1 userId 1 and body   "Hello world I am a comment"
    And I save the comment
    And I request to find the comment by id
    When I request to update the comment userId to 2 ad postId to 2
    Then the comment is not updated

  Scenario: Delete comment
    Given I have a comment without and postId 1 userId 1 and body   "Hello world I am a comment"
    And I save the comment
    And I request to find the comment by id
    When I request to delete the comment
    Then the comment is deleted

  Scenario: Find all comments
    Given I have empty repository
    And I have a comment without and postId 1 userId 1 and body   "Hello world I am a comment"
    And I save the comment
    And I have a another comment without and postId 1 userId 2 and body   "Hello I am also a comment"
    And I save the comment
    When I request to find all comments
    Then the comments are returned