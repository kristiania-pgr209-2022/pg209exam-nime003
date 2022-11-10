package no.kristiania.chatapp.db;

import java.sql.SQLException;

public interface UserDao {
    User retrieveUser(int id) throws SQLException;

    void save(User user) throws SQLException;
}
