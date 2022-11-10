package no.kristiania.chatapp.db;

import java.sql.SQLException;
import java.util.List;

public interface MessageDao {
    List<Message> retrieveAllMessages() throws SQLException;

    void save(Message message) throws SQLException;
}
