package no.kristiania.chatapp.db.jdbc;

import jakarta.inject.Inject;
import no.kristiania.chatapp.db.UserGroupLink;
import no.kristiania.chatapp.db.UserGroupLinkDao;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class JdbcUserGroupLinkDao extends AbstractJdbcDao implements UserGroupLinkDao {
    private final DataSource dataSource;

    @Inject
    public JdbcUserGroupLinkDao(DataSource dataSource){
        this.dataSource = dataSource;
    }

    @Override
    public void save(UserGroupLink link) throws SQLException {
        try (var conn = dataSource.getConnection()) {
            var sql = "insert into user_group_link (user_id, group_id) values (?, ?)";
            try (var stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setLong(1, link.getUserId());
                stmt.setLong(2, link.getGroupId());
                stmt.executeUpdate();

                try(var generatedKeys = stmt.getGeneratedKeys()) {
                    generatedKeys.next();
                    link.setId(generatedKeys.getLong(1));
                }
            }
        }

    }

    @Override
    public List<UserGroupLink> retrieveAllByUserId(long userId) throws SQLException {
        try (var conn = dataSource.getConnection()) {
            try (var stmt = conn.prepareStatement("select * from user_group_link where user_id = ?")) {
                stmt.setLong(1, userId);
                return collectQueryResult(stmt, JdbcUserGroupLinkDao::readUserGroupLink);
            }
        }
    }

    @Override
    public List<UserGroupLink> retrieveAllByGroupId(long groupId) throws SQLException {
        try (var conn = dataSource.getConnection()) {
            try (var stmt = conn.prepareStatement("select * from user_group_link where group_id = ?")) {
                stmt.setLong(1, groupId);
                return collectQueryResult(stmt, JdbcUserGroupLinkDao::readUserGroupLink);
            }
        }    }


    private static UserGroupLink readUserGroupLink(ResultSet rs) throws SQLException {
        var link = new UserGroupLink();
        link.setId(rs.getLong("id"));
        link.setUserId(rs.getLong("user_id"));
        link.setGroupId(rs.getLong("group_id"));
        return link;
    }
}
