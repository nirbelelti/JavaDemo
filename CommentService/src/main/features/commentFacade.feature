Feature: Comment Facade
  Scenario: "User comment on a post"
     Given a request to comment on a post with the following data postId 1 userId 1 comment "Hello world I am a comment"
     When the request is processed
     Then the response should be successful

  Scenario: get comment by id
    Given a request to comment on a post with the following data postId 1 userId 1 comment "Hello world I am a comment"
    And the request is processed
    When a request to get a comment by id with the following data id 1
    Then the response should contain the comment with the following data postId 1 userId 1 comment "Hello world I am a comment"

  Scenario: Get comments by postId
    Given a request to comment on a post with the following data postId 1 userId 1 comment "Hello world I am a comment"
    And the request is processed
    When a request to get a comment by id with the following data postId 1
    Then the response should contain the following data commentId 1 postId 1 userId 1 comment "Hello world I am a comment"

   Scenario: update comment
    Given a request to comment on a post with the following data postId 1 userId 1 comment "Hello world I am a comment"
    And the request is processed
    When a request to update a comment with the following data id 1 comment "Hello world I am a comment updated"
    Then the response should be successful

Scenario: delete comment
    Given a request to comment on a post with the following data postId 1 userId 1 comment "Hello world I am a comment"
    And the request is processed
    When a request to delete a comment with the following data id 1
    Then the comment is deleted
