package no.kristiania.chatapp.db;

import java.sql.SQLException;

public interface UserDao {
    void save(User user) throws SQLException;

    User retrieveUser(long id) throws SQLException;

}
