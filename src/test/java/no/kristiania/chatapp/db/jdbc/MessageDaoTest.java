package no.kristiania.chatapp.db.jdbc;

import no.kristiania.chatapp.db.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class MessageDaoTest {

    private MessageDao dao;
    private final SampleData sampleData = new SampleData();
    private final DataSource testDataSource = InMemoryDatasource.createTestDataSource();

    @BeforeEach
    void setup() {
        dao = new JdbcMessageDao(testDataSource);
    }

    @Test
    void shouldRetrieveMessageById() throws SQLException {
        new JdbcUserDao(testDataSource).save(sampleData.sampleUser());
        new JdbcGroupDao(testDataSource).save(sampleData.sampleGroup());

        var message = sampleData.sampleMessage(1L, 1L);
        dao.save(message);

        assertThat(dao.retrieveMessage(message.getId()))
                .hasNoNullFieldsOrProperties()
                .usingRecursiveComparison()
                .isEqualTo(message)
                .isNotSameAs(message);
    }

    @Test
    void shouldRetrieveAllMessages() throws SQLException {
        // message table has foreign keys user and group, thus it crashes when using the default int value of 0 (because int can't be null)
        var userDaoTest = new UserDaoTest();
        userDaoTest.setup();
        userDaoTest.shouldSaveAndRetrieveUser();

        var groupDaoTest = new GroupDaoTest();
        groupDaoTest.setup();
        groupDaoTest.shouldSaveAndRetrieveAllGroups();

        var message1 = sampleData.sampleMessage(1L, 1L);
        var message2 = sampleData.sampleMessage(1L, 1L);

        dao.save(message1);
        dao.save(message2);

        assertThat(dao.retrieveAllMessages())
                .extracting(Message::getMessage)
                .contains(message1.getMessage()
                        , message2.getMessage());

    }

    @Test
    void getMessagesByGroupId() throws SQLException {
        var userDao = new JdbcUserDao(testDataSource);
        var user1 = sampleData.sampleUser();
        userDao.save(user1);

        var groupDao = new JdbcGroupDao(testDataSource);
        var groups = Arrays.asList(sampleData.sampleGroup(), sampleData.sampleGroup());

        for (var group : groups) groupDao.save(group);


        var messages = Arrays.asList(
                sampleData.sampleMessage(user1.getId(), groups.get(0).getId()),
                sampleData.sampleMessage(user1.getId(), groups.get(0).getId()),
                sampleData.sampleMessage(user1.getId(), groups.get(1).getId())
        );

        for(var message : messages) dao.save(message);

        assertThat(dao.getAllMessagesByGroupId(messages.get(0).getGroupId()))
                .extracting(Message::getId)
                .containsOnly(messages.get(0).getId(), messages.get(1).getId());
    }

    @Test
    void shouldRetrieveMessagesByUserId() throws SQLException {
        var userDao = new JdbcUserDao(testDataSource);
        var users = Arrays.asList(sampleData.sampleUser(), sampleData.sampleUser());
        for(var user : users) userDao.save(user);

        var group = sampleData.sampleGroup();
        new JdbcGroupDao(testDataSource).save(group); // test needs at least 1 group in the database to work.

        var messages = Arrays.asList(
                sampleData.sampleMessage(users.get(0).getId(), group.getId()),
                sampleData.sampleMessage(users.get(0).getId(), group.getId()),
                sampleData.sampleMessage(users.get(1).getId(), group.getId()));

        for(var message : messages) dao.save(message);

        assertThat(dao.retrieveAllMessagesByUserId(users.get(0).getId()))
                .extracting(Message :: getId)
                .containsOnly(messages.get(0).getId(), messages.get(1).getId());
    }
}
