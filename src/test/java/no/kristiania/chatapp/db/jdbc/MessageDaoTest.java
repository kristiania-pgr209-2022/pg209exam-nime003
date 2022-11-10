package no.kristiania.chatapp.db.jdbc;

import no.kristiania.chatapp.db.InMemoryDatasource;
import no.kristiania.chatapp.db.Message;
import no.kristiania.chatapp.db.MessageDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

public class MessageDaoTest {

    private MessageDao dao;

    @BeforeEach
    void setup() {
        dao = new JdbcMessageDao(InMemoryDatasource.createTestDataSource());
    }

    @Disabled("Disabled due to incorrect test order! We need to initialize and test user and group tables first")
    @Test
    void ShouldRetrieveAllMessages() throws SQLException {
        var message1 = new Message();
        message1.setSenderId(1);
        message1.setGroupId(1);
        message1.setMessage("Hei jeg heter Nils!");

        var message2 = new Message();
        message2.setSenderId(2);
        message2.setGroupId(1);
        message2.setMessage("Hei jeg heter Pål!");


        dao.save(message1);
        dao.save(message2);

        assertThat(dao.retrieveAllMessages())
                .extracting(Message::getMessage)
                .contains("Hei jeg heter Nils!");
    }
}
