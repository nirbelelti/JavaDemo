Feature: Create a comment object on a post
  Scenario: Create a comment on a post
  Given a new comment object with the following attributes id  body  postId  userId
  When I create the comment
  Then the object should have the following attributes id  body  postId  userId

  Scenario: override to string
    Given a request to comment on a post with the following data postId 1 userId 1 comment "Hello world I am a comment"
    And the request is processed
    When a request to get a comment by id with the following data id 1
    Then the response should contain the following data postId 1 userId 1 comment "Hello world I am a comment"