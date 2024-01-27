package comment;

import java.sql.*;

public class CommentRepository {
    public static String url = "jdbc:sqlite:commentdb.db";

    public static void createTable() {

        try (Connection connection = DriverManager.getConnection(url);
             Statement statement = connection.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS comment ("
                    + "	id integer PRIMARY KEY,"
                    + "	body text NOT NULL,"
                    + "	post_id integer NOT NULL,"
                    + "	user_id integer NOT NULL"
                    + ")";
               statement.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static int insert(Comment comment) {
        createTable();
        String sql = "INSERT INTO comment (body, post_id, user_id) VALUES (?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, comment.getBody());
            preparedStatement.setInt(2, comment.getPostId());
            preparedStatement.setInt(3, comment.getUserId());

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                }
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return -1;
    }

}
