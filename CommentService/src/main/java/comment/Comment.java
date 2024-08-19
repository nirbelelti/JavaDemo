package comment;

public class Comment {
    private int id;
    private String body;
    private int postId;
    private int userId;

    public Comment(int id, String body, int postId, int userId) {
        this.id = id;
        this.body = body;
        this.postId = postId;
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public int getPostId() {
        return postId;
    }

    public int getUserId() {
        return userId;
    }



    public void setPostId(int postId) {
        this.postId = postId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "{" +
                "\"id\":" + id +
                ", \"body\":" + body +
                ", \"postId\":" + postId +
                ", \"userId\":" + userId +
                '}';
    }
}
