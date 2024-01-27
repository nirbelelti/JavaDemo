Feature: Create a comment object on a post
  Scenario: Create a comment on a post
  Given a new comment object with the following attributes id  body  postId  userId
  When I create the comment
  Then the object should have the following attributes id  body  postId  userId