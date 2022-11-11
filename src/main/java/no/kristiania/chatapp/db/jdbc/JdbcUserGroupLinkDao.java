package no.kristiania.chatapp.db.jdbc;

import jakarta.inject.Inject;
import no.kristiania.chatapp.db.UserGroupLink;
import no.kristiania.chatapp.db.UserGroupLinkDao;

import javax.sql.DataSource;
import java.util.List;

public class JdbcUserGroupLinkDao extends AbstractJdbcDao implements UserGroupLinkDao {
    private final DataSource dataSource;

    @Inject
    public JdbcUserGroupLinkDao(DataSource dataSource){
        this.dataSource = dataSource;
    }

    @Override
    public void save(UserGroupLink link) {

    }

    @Override
    public List<UserGroupLink> retrieveAllByUserId(long userId) {
        return null;
    }
}
