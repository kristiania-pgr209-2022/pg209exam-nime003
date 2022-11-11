package no.kristiania.chatapp.db;

import java.sql.SQLException;
import java.util.List;

public interface MessageDao {
    void save(Message message) throws SQLException;

    List<Message> retrieveAllMessages() throws SQLException;

}
