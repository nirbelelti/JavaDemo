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

    public static int insertUser(UserService user) {
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

    public static UserService getUserById(int userId) {
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
                return new UserService(userId, firstName,lastName, address);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("User not found");

        return null;
    }

    public static void updateUser(UserService user) {
        String sql = "UPDATE user SET firstName = ?, lastName = ?,address = ? WHERE id = ?";

        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getAddress());
            preparedStatement.setInt(4, user.getId());
            System.out.println(preparedStatement);

            preparedStatement.executeUpdate();
            System.out.println("User updated");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteUser(UserService user) {
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

//import java.sql.*;
//
//public class PersonRepository {
//
//    public static void main(String[] args) {
//        createTable();
//
//        // Create a new person
//        Person person = new Person("John Doe", "123 Main St");
//        person.save();
//        System.out.println("Person inserted with ID: " + person.getId());
//
//        // Retrieve and display the person
//        Person retrievedPerson = Person.getPersonById(person.getId());
//        System.out.println("Retrieved Person: " + retrievedPerson);
//
//        // Update the person's address
//        retrievedPerson.setAddress("456 Oak St");
//        retrievedPerson.update();
//        System.out.println("Person updated: " + Person.getPersonById(person.getId()));
//
//        // Delete the person
//        retrievedPerson.delete();
//        System.out.println("Person deleted.");
//
//        // Clean up - Drop the table (optional)
//        dropTable();
//    }
//
//    private static void createTable() {
//        String url = "jdbc:sqlite:persondb.db";
//
//        try (Connection connection = DriverManager.getConnection(url);
//             Statement statement = connection.createStatement()) {
//
//            String sql = "CREATE TABLE IF NOT EXISTS person (" +
//                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
//                    "name TEXT NOT NULL," +
//                    "address TEXT NOT NULL)";
//            statement.executeUpdate(sql);
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private static void dropTable() {
//        String url = "jdbc:sqlite:persondb.db";
//
//        try (Connection connection = DriverManager.getConnection(url);
//             Statement statement = connection.createStatement()) {
//
//            String sql = "DROP TABLE IF EXISTS person";
//            statement.executeUpdate(sql);
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    // Person class representing the data model
//    static class Person {
//        private int id;
//        private String name;
//        private String address;
//
//        public Person(String name, String address) {
//            this.name = name;
//            this.address = address;
//        }
//
//        public Person(int id, String name, String address) {
//            this.id = id;
//            this.name = name;
//            this.address = address;
//        }
//
//        public int getId() {
//            return id;
//        }
//
//        public String getName() {
//            return name;
//        }
//
//        public String getAddress() {
//            return address;
//        }
//
//        public void setAddress(String address) {
//            this.address = address;
//        }
//
//        public void save() {
//            String url = "jdbc:sqlite:persondb.db";
//            String sql = "INSERT INTO person (name, address) VALUES (?, ?)";
//
//            try (Connection connection = DriverManager.getConnection(url);
//                 PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
//
//                preparedStatement.setString(1, this.name);
//                preparedStatement.setString(2, this.address);
//
//                int rowsAffected = preparedStatement.executeUpdate();
//
//                if (rowsAffected > 0) {
//                    ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
//                    if (generatedKeys.next()) {
//                        this.id = generatedKeys.getInt(1);
//                    }
//                }
//
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//
//        public void update() {
//            String url = "jdbc:sqlite:persondb.db";
//            String sql = "UPDATE person SET name = ?, address = ? WHERE id = ?";
//
//            try (Connection connection = DriverManager.getConnection(url);
//                 PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
//
//                preparedStatement.setString(1, this.name);
//                preparedStatement.setString(2, this.address);
//                preparedStatement.setInt(3, this.id);
//
//                preparedStatement.executeUpdate();
//
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//
//        public void delete() {
//            String url = "jdbc:sqlite:persondb.db";
//            String sql = "DELETE FROM person WHERE id = ?";
//
//            try (Connection connection = DriverManager.getConnection(url);
//                 PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
//
//                preparedStatement.setInt(1, this.id);
//
//                preparedStatement.executeUpdate();
//
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//
//        public static Person getPersonById(int personId) {
//            String url = "jdbc:sqlite:persondb.db";
//            String sql = "SELECT * FROM person WHERE id = ?";
//
//            try (Connection connection = DriverManager.getConnection(url);
//                 PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
//
//                preparedStatement.setInt(1, personId);
//
//                ResultSet resultSet = preparedStatement.executeQuery();
//
//                if (resultSet.next()) {
//                    String name = resultSet.getString("name");
//                    String address = resultSet.getString("address");
//                    return new Person(personId, name, address);
//                }
//
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//
//            return null;
//        }
//
//        @Override
//        public String toString() {
//            return "Person{" +
//                    "id=" + id +
//                    ", name='" + name + '\'' +
//                    ", address='" + address + '\'' +
//                    '}';
//        }
//    }
//}
