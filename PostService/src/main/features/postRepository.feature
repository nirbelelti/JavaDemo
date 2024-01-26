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
