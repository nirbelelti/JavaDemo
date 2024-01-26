package post;

import java.sql.*;
import java.util.ArrayList;

public class PostRepository {
    static String url = "jdbc:sqlite:postdb.db";

    public static void createTable() {


        try (Connection connection = DriverManager.getConnection(url);
             Statement statement = connection.createStatement()) {

            String sql = "CREATE TABLE IF NOT EXISTS post (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "userId INTEGER NOT NULL," +
                    "title TEXT NOT NULL," +
                    "body TEXT NOT NULL)";
            statement.executeUpdate(sql);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static int insert(Post post) {
        createTable();
        String sql = "INSERT INTO post (userId, title, body) VALUES (?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setInt(1, post.getUserId());
            preparedStatement.setString(2, post.getTitle());
            preparedStatement.setString(3, post.getBody());

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }

    public static Post getPostById(int postId) {
        String sql = "SELECT * FROM post WHERE id = ?";

        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, postId);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                int userId = resultSet.getInt("userId");
                String title = resultSet.getString("title");
                String body= resultSet.getString("body");
                System.out.println("Post found id: " + id +" "+ userId +" "+ title +", "+ body);
                return new Post(id, userId,title, body);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Post not found");

        return null;
    }

    public static void update(Post post) {
        String sql = "UPDATE post SET userID= ?, title = ?,body = ? WHERE id = ?";
        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, post.getUserId());
            preparedStatement.setString(2, post.getTitle());
            preparedStatement.setString(3, post.getBody());
            preparedStatement.setInt(4, post.getId());

            preparedStatement.executeUpdate();
            System.out.println("Post updated");

        } catch (SQLException e) {
            System.out.println("Post not updated");
            e.printStackTrace();
        }
    }

    public static void delete(Post post) {
        String sql = "DELETE FROM post WHERE id = ?";

        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, post.getId());

            preparedStatement.executeUpdate();
            System.out.println("Post deleted");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void dropTable() {

        try (Connection connection = DriverManager.getConnection(url);
             Statement statement = connection.createStatement()) {

            String sql = "DROP TABLE IF EXISTS post";
            statement.executeUpdate(sql);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Post> getAllPostsByUserId(int userId) {
        String sql = "SELECT * FROM post WHERE userId = ?";

        ArrayList<Post> resault = new ArrayList<Post>();
        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, userId);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                int userId1 = resultSet.getInt("userId");
                String title = resultSet.getString("title");
                String body= resultSet.getString("body");
                resault.add(new Post(id, userId1,title, body));
            }

            return resault;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Post not found");

        return null;
    }
}
