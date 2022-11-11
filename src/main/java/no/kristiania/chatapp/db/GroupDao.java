package no.kristiania.chatapp.db;

import java.sql.SQLException;
import java.util.List;

public interface GroupDao {
    void save(Group group) throws SQLException;

    List<Group> retrieveAllGroups() throws SQLException;

    Group retrieveGroup(long id) throws SQLException;

}
