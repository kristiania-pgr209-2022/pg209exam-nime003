package no.kristiania.chatapp.db;

import java.sql.SQLException;
import java.util.List;

public interface UserGroupLinkDao {

    void save(UserGroupLink link) throws SQLException;

    List<UserGroupLink> retrieveAllByUserId(long userId) throws SQLException;
}
