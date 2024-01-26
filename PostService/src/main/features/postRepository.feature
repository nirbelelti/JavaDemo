Feature: CRUD of posts in the repository
  Scenario: Create a post
    Given I have a post with userId 1 title "My first post" and body "This is my first post"
    When I create the post
    Then the post should be saved in the repository