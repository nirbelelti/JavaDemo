Feature: post functions

  Scenario: Create a post
    Given request to create a post with the following data parameters userId 1,"My Title","My post body"
    When the request to created post is sent
    Then the post is created
    And it returns the post id

  Scenario: Get a post
    Given request to create a post with the following data parameters userId 1,"My Title","My post body"
    And the request to created post is sent
    And the post is created
    When request to get a post with the following post id 1 is sent
    Then the post object is returned

  Scenario: Update a post
    Given request to create a post with the following data parameters userId 1,"My Title","My post body"
    And the request to created post is sent
    And the post is created
    When request to update a post with the following data parameters post id 1 userId 1,"Wonderland","Rabbit hole 22" is sent
    Then the post object is updated

  Scenario: Delete a post
    Given request to create a post with the following data parameters userId 1,"My Title","My post body"
    And the request to created post is sent
    And the post is created
    When request to delete a post with the following post id 1 is sent
    Then the post object is deleted

  Scenario: Get all posts
    Given request to create a post with the following data parameters userId 1,"Wonderland","Rabbit hole 22"
    And the request to created post is sent
    And the post is created
    When request to get all posts is sent
    Then the posts array is returned

  Scenario: Get all posts by user
    Given request to create a post with the following data parameters userId 1,"Wonderland","Rabbit hole 22"
    And the request to created post is sent
    And the post is created
    When request to get all posts by user with the following user id 1 is sent
    Then the posts array is returned
