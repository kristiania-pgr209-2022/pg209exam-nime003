package no.kristiania.chatapp.db.jdbc;

import no.kristiania.chatapp.db.Group;
import no.kristiania.chatapp.db.GroupDao;
import no.kristiania.chatapp.db.InMemoryDatasource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

public class GroupDaoTest {

    private GroupDao dao;
    private SampleData sampleData;

    @BeforeEach
    void setup() {
        dao = new JdbcGroupDao(InMemoryDatasource.createTestDataSource());
    }
    
    @Test
    void shouldSaveAndRetrieveAllGroups() throws SQLException {
        var group1 = new Group();
        group1.setGroupName("Nils sin ChatApp-Gruppe");

        var group2 = new Group();
        group2.setGroupName("Klassechat");

        dao.save(group1);
        dao.save(group2);

        assertThat(dao.retrieveAllGroups())
                .extracting(Group::getGroupName)
                .contains(group1.getGroupName()
                        , group2.getGroupName());
    }

    @Test
    void shouldSaveAndRetrieveGroupById() throws SQLException {
        var group = new Group();
        group = sampleData.sampleGroup();

        dao.save(group);

        assertThat(dao.retrieveGroup(group.getId()))
                .hasNoNullFieldsOrProperties()
                .usingRecursiveComparison()
                .isEqualTo(group)
                .isNotSameAs(group);
    }
}
