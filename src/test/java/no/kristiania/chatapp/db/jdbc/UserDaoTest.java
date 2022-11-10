package no.kristiania.chatapp.db.jdbc;

import no.kristiania.chatapp.db.InMemoryDatasource;
import no.kristiania.chatapp.db.User;
import no.kristiania.chatapp.db.UserDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

public class UserDaoTest {
    private UserDao dao;

    @BeforeEach
    void setup(){dao = new JdbcUserDao(InMemoryDatasource.createTestDataSource());}

    @Test
    void shouldSaveAndRetrieveUser() throws SQLException{
        var user = new User();
        user.setUsername("Nils");
        user.setPassword("sliN");

        dao.save(user);

        assertThat(dao.retrieveUser(user.getId()))
                .hasNoNullFieldsOrProperties()
                .usingRecursiveComparison()
                .isEqualTo(user)
                .isNotSameAs(user);
    }
}
