package User;

public class UserFacade {
    private User user;
    int id;
    String firstName, lastName, address;

public UserFacade() {
        this.user = new User(id, firstName, lastName, address);
    }

    public int createUser(String firstName, String lastName, String address) {
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setAddress(address);
        id = UserRepository.insertUser(user);
        user.setId(id);
        return id;
    }


    public String getUser(int i) {
    System.out.println("Hej" +i);
    user = UserRepository.getUserById(i);
    return user.toString();
    }

    public boolean updateUser(int id, String firstName, String lastName, String address) {
        user = UserRepository.getUserById(id);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setAddress(address);
        try {
            UserRepository.updateUser(user);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean deleteUser(int i) {
        user = UserRepository.getUserById(i);
        try {
            UserRepository.deleteUser(user);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
