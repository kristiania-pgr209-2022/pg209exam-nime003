package no.kristiania.chatapp.db;

import no.kristiania.chatapp.db.jdbc.*;
import org.flywaydb.core.Flyway;
import org.h2.jdbcx.JdbcDataSource;

import javax.sql.DataSource;
import java.sql.SQLException;

public class InMemoryDatasource {
    public static DataSource createTestDataSource() {
        var dataSource = new JdbcDataSource();
        dataSource.setUrl("jdbc:h2:mem:DaoTest;DB_CLOSE_DELAY=-1;MODE=MSSQLServer");
        var flyway = Flyway.configure().dataSource(dataSource).load();
        flyway.migrate();
        return dataSource;
    }

    public void populateDatabase(DataSource dataSource) throws SQLException {
        JdbcUserDao userDao = new JdbcUserDao(dataSource);
        JdbcGroupDao groupDao = new JdbcGroupDao(dataSource);
        JdbcMessageDao messageDao = new JdbcMessageDao(dataSource);
        JdbcUserGroupLinkDao linkDao = new JdbcUserGroupLinkDao(dataSource);
        SampleData sampleData = new SampleData();

        // create list of user and group and save them to the database
        var users = sampleData.sampleUsers(10);
        for (var user : users) userDao.save(user);

        var groups = sampleData.sampleGroups(3);
        for (var group : groups) groupDao.save(group);

        // connects all users and groups
        for (var group : groups) {
            var link = new UserGroupLink();
            link.setGroupId(group.getId());
            for (var user : users) {
                link.setUserId(user.getId());
                linkDao.save(link);
            }
        }

        // create some sample messages
        for (var user : users) {
            for (var group : groups) {
                var sampleMessage = sampleData.sampleMessage(user.getId(), group.getId());
                sampleMessage.setMessage(user.getUsername() + " sample message in " + group.getGroupName());
                messageDao.save(sampleMessage);
            }
        }
    }
}
