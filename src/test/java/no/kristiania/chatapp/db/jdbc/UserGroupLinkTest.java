package no.kristiania.chatapp.db.jdbc;

import no.kristiania.chatapp.db.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class UserGroupLinkTest {
    private UserGroupLinkDao linkDao;
    private final SampleData sampleData = new SampleData();
    private final DataSource dataSource = InMemoryDatasource.createTestDataSource();

    private final JdbcGroupDao groupDao = new JdbcGroupDao(dataSource);
    private final JdbcUserDao userDao = new JdbcUserDao(dataSource);


    @BeforeEach
    void setup(){
        linkDao = new JdbcUserGroupLinkDao(dataSource);
    }

    @Test
    void shouldRetrieveUserGroupLinksByUserId() throws SQLException {
        var user1 = sampleData.sampleUser();
        var user2 = sampleData.sampleUser();
        var groups = new ArrayList<Group>();
        var user1Links = new ArrayList<UserGroupLink>();
        var user2Links = new ArrayList<UserGroupLink>();

        userDao.save(user1);
        userDao.save(user2);

        for(int i = 0; i < 3; i++){
            groups.add(sampleData.sampleGroup());
        }

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

        for (var link : user1Links) linkDao.save(link);
        for (var link : user2Links) linkDao.save(link);

        System.out.println(linkDao.retrieveAllByUserId(user1.getId()));

        assertThat(linkDao.retrieveAllByUserId(user1.getId()))
                .usingRecursiveComparison()
                .isEqualTo(user1Links);
    }

    @Test
    void shouldRetrieveUserGroupLinksByGroupId() throws SQLException {
        var users = sampleData.sampleUsers(3);
        var groups = sampleData.sampleGroups(3);
        var expectedLinks = new ArrayList<UserGroupLink>();

        for (var group : groups){
            groupDao.save(group);
            for(var user : users){
                userDao.save(user);
                var tempLink = new UserGroupLink();
                tempLink.setGroupId(group.getId());
                tempLink.setUserId(user.getId());
                linkDao.save(tempLink);
                expectedLinks.add(tempLink);
            }
        }

        var expectedGroupId = groups.get(groups.size() - 1).getId();
        expectedLinks = (ArrayList<UserGroupLink>) expectedLinks.stream()
                .filter(link -> expectedGroupId == link.getGroupId()).collect(Collectors.toList());
        var actual = linkDao.retrieveAllByGroupId(expectedGroupId);

        assertThat(actual)
                .usingRecursiveComparison()
                .isEqualTo(expectedLinks)
                .isNotSameAs(expectedLinks);
    }
}
