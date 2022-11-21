package no.kristiania.chatapp.db.jdbc;

import jakarta.inject.Inject;
import no.kristiania.chatapp.db.Group;
import no.kristiania.chatapp.db.GroupDao;
import no.kristiania.chatapp.db.User;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class JdbcGroupDao extends AbstractJdbcDao implements GroupDao {
    private final DataSource dataSource;

    @Inject
    public JdbcGroupDao(DataSource dataSource) { this.dataSource = dataSource; }

    @Override
    public void save(Group group) throws SQLException {
        try (var conn = dataSource.getConnection()) {
            var sql = "insert into [group] (group_name) values (?)";
            try (var stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setString(1, group.getGroupName());

                stmt.executeUpdate();

                try (var generatedKeys = stmt.getGeneratedKeys()) {
                    generatedKeys.next();
                    group.setId(generatedKeys.getLong(1));
                }
            }
        }
    }

    @Override
    public List<Group> retrieveAllGroups() throws SQLException {
        try (var conn = dataSource.getConnection()) {
            try (var stmt = conn.prepareStatement("select * from [group]")){
                return collectQueryResult(stmt, JdbcGroupDao::readGroup);
            }
        }
    }
    @Override
    public Group retrieveGroup(long id) throws SQLException {
        try (var conn = dataSource.getConnection()) {
            try (var stmt = conn.prepareStatement("select * from [group] where id = ?")) {
                stmt.setLong(1, id);
                return collectSingleResult(stmt, JdbcGroupDao::readGroup);
            }
        }
    }

    @Override
    public List<Group> retrieveGroupByUserId(long userId) throws SQLException {
        try (var conn = dataSource.getConnection()) {
            try (var stmt = conn.prepareStatement("select [group].id, group_name " +
                    "from [group] inner join user_group_link ugl on [group].id = ugl.group_id " +
                    "where ugl.user_id = ?")) {
                stmt.setLong(1, userId);
                return collectQueryResult(stmt, JdbcGroupDao::readGroup);
            }
        }
    }

    @Override
    public List<User> retrieveAllUsers(long groupId) throws SQLException {
        try (var conn = dataSource.getConnection()) {
            try (var stmt = conn.prepareStatement("select [user].* from [user]" +
                    " inner join user_group_link ugl on [user].id = ugl.user_id" +
                    " where ugl.group_id = ?;"
            )){
                stmt.setLong(1, groupId);
                return collectQueryResult(stmt, JdbcUserDao::readUser);
            }
        }
    }

    private static Group readGroup(ResultSet rs) throws SQLException {
        var group = new Group();
        group.setId(rs.getLong("id"));
        group.setGroupName(rs.getString("group_name"));
        return group;
    }
}
