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

    public Comment() {
    }

    public int getId() {
        return id;
    }

    public String getBody() {
        return body;
    }

    public int getPostId() {
        return postId;
    }

    public int getUserId() {
        return userId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
