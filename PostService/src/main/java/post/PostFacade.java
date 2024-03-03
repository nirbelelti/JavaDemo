package post;
import java.util.ArrayList;

public class PostFacade {
    private Post post;
    int id, userId;
    String  title, body;

    public PostFacade() {
        this.post = new Post(id, userId, title, body);
    }

    public int createPost(int userId, String title, String body) {
        post.setUserId(userId);
        post.setTitle(title);
        post.setBody(body);
        id = PostRepository.insert(post);
        post.setId(id);
        return id;
    }


    public String getPost(int i) {
        System.out.println("Hej" +i);
        post = PostRepository.getPostById(i);
        return post.toString();
    }

    public boolean updatePost(int id, int userId, String title, String body) {
        post = PostRepository.getPostById(id);
        post.setTitle(title);
        post.setBody(body);
        try {
            PostRepository.update(post);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean deletePost(int i) {
        post = PostRepository.getPostById(i);
        try {
            PostRepository.delete(post);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public ArrayList<Post> allPosts() {
        return PostRepository.getAllPosts();
    }


    public ArrayList<Post> getAllPostsByUser(int userId) {
        return PostRepository.getAllPostsByUserId(userId);
    }
}
