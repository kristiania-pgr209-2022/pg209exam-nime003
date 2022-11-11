package no.kristiania.chatapp.db.jdbc;

import jakarta.inject.Inject;
import no.kristiania.chatapp.db.Message;
import no.kristiania.chatapp.db.MessageDao;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class JdbcMessageDao extends AbstractJdbcDao implements MessageDao {
    private final DataSource dataSource;

    @Inject
    public JdbcMessageDao(DataSource dataSource){this.dataSource = dataSource;}


    @Override
    public List<Message> retrieveAllMessages() throws SQLException {
        try (var conn = dataSource.getConnection()){
            try (var stmt = conn.prepareStatement("select * from message")){
                return collectQueryResult(stmt, JdbcMessageDao::readMessage);
            }
        }
    }

    @Override
    public void save(Message message) throws SQLException {
        try (var conn = dataSource.getConnection()) {
            var sql = "insert into message (sender_id, group_id, message) values (?, ?, ?)";
            try (var stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setLong(1, message.getSenderId());
                stmt.setLong(2, message.getGroupId());
                stmt.setString(3, message.getMessage());
                stmt.executeUpdate();

                try (var generatedKeys = stmt.getGeneratedKeys()) {
                    generatedKeys.next();
                    message.setId(generatedKeys.getLong(1));
                    message.setDateTimeSent(generatedKeys.getTimestamp(2));
                }
            }
        }
    }

    private static Message readMessage(ResultSet rs) throws SQLException {
        var message = new Message();
        message.setId(rs.getLong("id"));
        message.setSenderId(rs.getLong("sender_id"));
        message.setGroupId(rs.getLong("group_id"));
        message.setMessage(rs.getString("message"));
        message.setDateTimeSent(rs.getTimestamp("time_sent"));
        return message;
    }
}

