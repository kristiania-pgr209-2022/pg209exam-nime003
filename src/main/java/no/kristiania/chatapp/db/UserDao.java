package no.kristiania.chatapp.db;

import java.sql.SQLException;
import java.util.List;

public interface UserDao {
    void save(User user) throws SQLException;
    void updateUser(User user) throws SQLException;

    User retrieveUser(long id) throws SQLException;

    List<User> retrieveAllUsers() throws SQLException;
}
