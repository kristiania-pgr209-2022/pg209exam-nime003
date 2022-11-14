package no.kristiania.chatapp.db.jdbc;

import no.kristiania.chatapp.db.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.sql.Array;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class UserGroupLinkTest {
    private UserGroupLinkDao dao;
    private final SampleData sampleData = new SampleData();
    private final DataSource dataSource = InMemoryDatasource.createTestDataSource();

    @BeforeEach
    void setup(){
        dao = new JdbcUserGroupLinkDao(dataSource);
    }

    @Disabled("not made yet")
    @Test
    void shouldRetrieveAllUserGroupLinks(){

    }

    @Test
    void shouldRetrieveUserGroupLinksByUserId() throws SQLException {
        var user1 = sampleData.sampleUser();
        var user2 = sampleData.sampleUser();
        var groups = new ArrayList<Group>();
        var user1Links = new ArrayList<UserGroupLink>();
        var user2Links = new ArrayList<UserGroupLink>();

        var userDao = new JdbcUserDao(dataSource);
        userDao.save(user1);
        userDao.save(user2);


        for(int i = 0; i < 3; i++){
            groups.add(sampleData.sampleGroup());
        }

        var groupDao = new JdbcGroupDao(dataSource);
        for (var group : groups){
            groupDao.save(group);

            var link1 = new UserGroupLink();
            link1.setGroupId(group.getId());
            link1.setUserId(user1.getId());
            user1Links.add(link1);

            // this is necessary so that just returning all links don't pass the test.
            var link2 = new UserGroupLink();
            link2.setGroupId(group.getId());
            link2.setUserId(user2.getId());
            user2Links.add(link2);
        }

        for (var link : user1Links) dao.save(link);
        for (var link : user2Links) dao.save(link);

        System.out.println(dao.retrieveAllByUserId(user1.getId()));

        assertThat(dao.retrieveAllByUserId(user1.getId()))
                .usingRecursiveComparison()
                .isEqualTo(user1Links);
    }

    @Disabled("not made yet")
    @Test
    void shouldRetrieveUserGroupLinksByGroupId(){

    }
}
