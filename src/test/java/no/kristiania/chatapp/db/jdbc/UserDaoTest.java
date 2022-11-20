package no.kristiania.chatapp.db.jdbc;

import no.kristiania.chatapp.db.InMemoryDatasource;
import no.kristiania.chatapp.db.User;
import no.kristiania.chatapp.db.UserDao;
import org.junit.jupiter.api.*;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class UserDaoTest {
    private static UserDao dao;
    private static final SampleData sampleData = new SampleData();
    private static final DataSource testDataSource = InMemoryDatasource.createTestDataSource();

    private static ArrayList<User> users = new ArrayList<>();

    @BeforeAll
    static void setup() throws SQLException {
        dao = new JdbcUserDao(testDataSource);
        users = sampleData.sampleUsers(5);
        for (var user : users) dao.save(user);
    }

    @Test
    void shouldSaveAndRetrieveUser() throws SQLException{
        var user = users.get(1);

        assertThat(dao.retrieveUser(user.getId()))
                .hasNoNullFieldsOrProperties()
                .usingRecursiveComparison()
                .isEqualTo(user)
                .isNotSameAs(user);
    }

    @Test
    void shouldRetrieveAllUsers() throws SQLException {
        assertThat(dao.retrieveAllUsers())
                .extracting(User::getId)
                .containsAll(users.stream()
                        .map(User::getId)
                        .collect(Collectors.toList()))
                .isNotSameAs(users);
    }
    @Test
    void shouldUpdateUser() throws SQLException {
        var user = users.get(2);
        var userTemp = new User();
        userTemp.setId(user.getId());
        userTemp.setUsername("hanskaare");
        userTemp.setPassword(user.getPassword());
        userTemp.setEmail(user.getEmail());
        dao.updateUser(userTemp);
        assertThat(dao.retrieveUser(user.getId()).getUsername()).isNotSameAs(user.getUsername());
    }
}
