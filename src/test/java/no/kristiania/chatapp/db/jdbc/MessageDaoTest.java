package no.kristiania.chatapp.db.jdbc;

import no.kristiania.chatapp.db.Group;
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
    private final SampleData sampleData = new SampleData();

    @BeforeEach
    void setup() {
        dao = new JdbcMessageDao(InMemoryDatasource.createTestDataSource());
    }

    @Test
    void ShouldRetrieveAllMessages() throws SQLException {
        // message table has foreign keys user and group, thus it crashes when using the default int value of 0 (because int can't be null)
        var userDaoTest = new UserDaoTest();
        userDaoTest.setup();
        userDaoTest.shouldSaveAndRetrieveUser();

        var groupDaoTest = new GroupDaoTest();
        groupDaoTest.setup();
        groupDaoTest.ShouldSaveAndRetrieveAllGroups();

        var message1 = sampleData.sampleMessage("Hello World!");
        var message2 = sampleData.sampleMessage("World Hello!");

        dao.save(message1);
        dao.save(message2);

        assertThat(dao.retrieveAllMessages())
                .extracting(Message::getMessage)
                .contains(message1.getMessage()
                        , message2.getMessage());

    }
}
