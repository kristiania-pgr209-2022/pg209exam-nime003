package no.kristiania.chatapp.db.jdbc;

import no.kristiania.chatapp.db.InMemoryDatasource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GroupDaoTest {

    private GroupDao dao;

    @BeforeEach
    void setup() {
        dao = new JdbcGroupDao(InMemoryDatasource.createTestDataSource());
    }
    
    @Test
    void ShouldSaveAndRetrieveAllGroups() {
        var group1 = new Group();
        group1.setGroupName("Nils sin ChatApp-Gruppe");

        var group2 = new Group();
        group2.setGroupName("Klassechat");

        dao.save(group1);
        dao.save(group2);

        assertThat(dao.retrieveAllGroups())
                .extracting(Group::group)
                .contains(group1.getGroupName
                        , group2.getGroupName);
    }
}
