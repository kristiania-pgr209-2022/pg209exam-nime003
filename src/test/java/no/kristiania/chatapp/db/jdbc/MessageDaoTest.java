package no.kristiania.chatapp.db.jdbc;

import no.kristiania.chatapp.db.*;
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
        groupDaoTest.shouldSaveAndRetrieveAllGroups();

        var message1 = sampleData.sampleMessage("Hello World!");
        var message2 = sampleData.sampleMessage("World Hello!");

        dao.save(message1);
        dao.save(message2);

        assertThat(dao.retrieveAllMessages())
                .extracting(Message::getMessage)
                .contains(message1.getMessage()
                        , message2.getMessage());

    }

    @Test
    void getMessagesByGroupId() {
        var user1 = new User();
        user1 = sampleData.sampleUser();

        var group1 = new Group();
        group1 = sampleData.sampleGroup();

        var message1 = new Message();
        message1 = sampleData.sampleMessage("Jonfinn kom på besøk!");

        var message2 = new Message();
        message2 = sampleData.sampleMessage("Jeg liker ikke Jonfinn!!");

        assertThat(dao.getAllMessagesByGroupId(message1.getGroupId()))
                .contains(message1, message2);
    }
}
