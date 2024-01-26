Feature: CRUD of posts in the repository

  Scenario: Create a post
    Given I have a post with userId 1 title "My first post" and body "This is my first post"
    When I create the post
    Then the post should be saved in the repository

  Scenario: get a post by id
    Given I have a post with userId 1 title "My first post" and body "This is my first post"
    When I create the post
    When I request the post by id
    Then the post should be returned

  Scenario: edit a post
    Given I have a post with userId 1 title "My first post" and body "This is my first post"
    When I create the post
    When I edit the post with userId 1 title "My first post edited" and body "This is my first post edited"
    Then the post should be updated in the repository

  Scenario: delete a post
    Given I have a post with userId 1 title "My first post" and body "This is my first post"
    When I create the post
    When I delete the post
    Then the post should be deleted from the repository

  Scenario: get all posts by userId
    Given I have a post with userId 1 title "My first post" and body "This is my first post"
    When I create the post
    When I request all posts by userId
    Then the posts should be returned

  Scenario: get all posts
    Given I have a post with userId 1 title "My first post" and body "This is my first post"
    And I create the post
    And I create other post with userId 2 title "My second post" and body "This is my second post"
    When I request all posts
    Then all posts should be returned
