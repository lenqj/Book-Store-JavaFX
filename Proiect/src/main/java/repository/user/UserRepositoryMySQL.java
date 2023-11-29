package repository.user;
import model.book.BookInterface;
import model.validator.Notification;
import repository.book.BookRepository;
import repository.security.RightsRolesRepository;
import model.User;
import model.builder.UserBuilder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static database.Constants.Tables.*;

public class UserRepositoryMySQL implements UserRepository {

    private final Connection connection;
    private final RightsRolesRepository rightsRolesRepository;

    public UserRepositoryMySQL(Connection connection, RightsRolesRepository rightsRolesRepository) {
        this.connection = connection;
        this.rightsRolesRepository = rightsRolesRepository;
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        try {
            String sql = "SELECT * FROM " + USER + ";";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while(resultSet.next()){
                users.add(getUserFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    @Override
    public Notification<User> findByUsernameAndPassword(String username, String password) {
        Notification<User> findByUsernameAndPasswordNotification = new Notification<>();
        try {
            String sql = "Select * from `" + USER + "` where `username` = ? and `password` = ?;";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            statement.setString(2, password);

            ResultSet userResultSet = statement.executeQuery();
            if (userResultSet.next())
            {
                User user = getUserFromResultSet(userResultSet);
                findByUsernameAndPasswordNotification.setResult(user);
            } else {
                findByUsernameAndPasswordNotification.addError("Invalid username or password!");
                return findByUsernameAndPasswordNotification;
            }

        } catch (SQLException e) {
            System.out.println(e.toString());
            findByUsernameAndPasswordNotification.addError("Something is wrong with the Database!");
        }

        return findByUsernameAndPasswordNotification;
    }

    public User findById(Long id) {
        try {
            String sql = "Select * from `" + USER + "` where `id` = ?";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, id);

            ResultSet userResultSet = statement.executeQuery();
            userResultSet.next();
            return getUserFromResultSet(userResultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Notification<Boolean> save(User user) {
        Notification<Boolean> saveNotification = new Notification<>();
        try {
            PreparedStatement insertUserStatement = connection
                    .prepareStatement("INSERT INTO `user` values (null, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            insertUserStatement.setString(1, user.getUsername());
            insertUserStatement.setString(2, user.getPassword());
            insertUserStatement.setLong(3, user.getMoney());

            if (insertUserStatement.executeUpdate() == 0){
                saveNotification.addError("User already exist!");
                return saveNotification;
            }
            ResultSet rs = insertUserStatement.getGeneratedKeys();
            if (rs.next()) {
                long userId = rs.getLong(1);
                user.setId(userId);
                saveNotification.setResult(Boolean.TRUE);
            }
            rightsRolesRepository.addRolesToUser(user, user.getRoles());
        } catch (SQLException e) {
            saveNotification.addError("Something is wrong with the Database!");
        }
        return saveNotification;
    }

    @Override
    public void removeAll() {
        try {
            Statement statement = connection.createStatement();
            String sql = "DELETE from user where id >= 0";
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean existsByUsername(String email) {
        try {
            String sql = "Select * from `" + USER + "` where `username`= ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, email);
            ResultSet userResultSet = statement.executeQuery();
            return userResultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Notification<User> updateMoney(User user, Long money) {
        Notification<User> userNotification = new Notification<>();
<<<<<<< Updated upstream
=======
        String sql = "UPDATE " + USER + " SET `money`= ? WHERE id = ?;";
>>>>>>> Stashed changes
        try{
            String sql = "UPDATE " + USER + " SET `money`= ? WHERE id = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, money);
            preparedStatement.setLong(2, user.getId());
            int rs = preparedStatement.executeUpdate();
            if (rs == 1){
                user.setMoney(money);
                userNotification.setResult(user);
            }else{
                userNotification.addError("User doesn't exist.");
            }
        } catch (SQLException e) {
            userNotification.addError("Something is wrong with the Database!");
            e.printStackTrace();
        }
        return userNotification;
    }

    public User getUserFromResultSet(ResultSet userResultSet) throws SQLException {
        return new UserBuilder()
                .setId(userResultSet.getLong("id"))
                .setUsername(userResultSet.getString("username"))
                .setPassword(userResultSet.getString("password"))
                .setRoles(rightsRolesRepository.findRolesForUser(userResultSet.getLong("id")))
                .setMoney(userResultSet.getLong("money"))
                .build();

    }

    public Notification<Boolean> deleteUser(User user) {
        Notification<Boolean> deleteUserNotification = new Notification<>();
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("DELETE FROM " + USER + " WHERE id = ?;");
            preparedStatement.setLong(1, user.getId());
            if(preparedStatement.executeUpdate() == 1){
                deleteUserNotification.setResult(Boolean.TRUE);
            }else{
                deleteUserNotification.addError("User can't be deleted.");
                return deleteUserNotification;
            }
        } catch (SQLException e) {
            deleteUserNotification.addError("Something is wrong with the Database!");
            e.printStackTrace();
        }
        return deleteUserNotification;
    }

}