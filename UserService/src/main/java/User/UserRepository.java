package User;//package Person;


import java.sql.*;


public class UserRepository {
    //createTable();

    static String url = "jdbc:sqlite:userdb.db";

    public static void createTable() {


        try (Connection connection = DriverManager.getConnection(url);
             Statement statement = connection.createStatement()) {

            String sql = "CREATE TABLE IF NOT EXISTS user (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "firstName TEXT NOT NULL," +
                    "lastName TEXT NOT NULL," +
                    "address TEXT NOT NULL)";
            statement.executeUpdate(sql);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static int insertUser(User user) {
        createTable();
        String sql = "INSERT INTO user (firstName,lastName, address) VALUES (?, ?,?)";

        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getAddress());

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

    public static User getUserById(int userId) {
        String url = "jdbc:sqlite:userdb.db";
        String sql = "SELECT * FROM user WHERE id = ?";

        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, userId);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                String address = resultSet.getString("address");
                return new User(userId, firstName,lastName, address);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("User not found");

        return null;
    }

    public static void updateUser(User user) {
        String sql = "UPDATE user SET firstName = ?, lastName = ?,address = ? WHERE id = ?";

        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getAddress());
            preparedStatement.setInt(4, user.getId());

            preparedStatement.executeUpdate();
            System.out.println("User updated");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteUser(User user) {
        String sql = "DELETE FROM user WHERE id = ?";

        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, user.getId());

            preparedStatement.executeUpdate();
            System.out.println("User deleted");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void dropTable() {

        try (Connection connection = DriverManager.getConnection(url);
             Statement statement = connection.createStatement()) {

            String sql = "DROP TABLE IF EXISTS user";
            statement.executeUpdate(sql);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
