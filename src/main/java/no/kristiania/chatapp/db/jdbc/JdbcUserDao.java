package no.kristiania.chatapp.db.jdbc;

import jakarta.inject.Inject;
import no.kristiania.chatapp.db.User;
import no.kristiania.chatapp.db.UserDao;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;


public class JdbcUserDao extends AbstractJdbcDao implements UserDao {
    private final DataSource dataSource;

    @Inject
    public JdbcUserDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public User retrieveUser(long id) throws SQLException {
        try (var conn = dataSource.getConnection()) {
            try (var stmt = conn.prepareStatement("select * from [user] where id = ?")){
                stmt.setLong(1, id);
                return collectSingleResult(stmt, JdbcUserDao::readUser);
            }
        }
    }

    @Override
    public List<User> retrieveAllUsers() throws SQLException {
        try (var conn = dataSource.getConnection()) {
            try (var stmt = conn.prepareStatement("select * from [user]")) {
                return collectQueryResult(stmt, JdbcUserDao::readUser);
            }
        }
    }
    @Override
    public void updateUser(User user) throws SQLException {
        try (var conn = dataSource.getConnection()) {
            var sql = "UPDATE [user] set username = ?, email = ?, password = ? WHERE id = ?";
            try (var stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setString(1, user.getUsername());
                stmt.setString(2, user.getEmail());
                stmt.setString(3, user.getPassword());
                stmt.setLong(4, user.getId());
                stmt.executeUpdate();
            }
        }
    }

    @Override
    public void save(User user) throws SQLException {
        try (var conn = dataSource.getConnection()) {
            var sql = "insert into [user] (username, email, password) values (?, ?, ?)";
            try (var stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setString(1, user.getUsername());
                stmt.setString(2, user.getEmail());
                stmt.setString(3, user.getPassword());
                stmt.executeUpdate();

                try (var generatedKeys = stmt.getGeneratedKeys()) {
                    generatedKeys.next();
                    user.setId(generatedKeys.getLong(1));
                }
            }
        }
    }

    static User readUser(ResultSet rs) throws SQLException {
        var user = new User();
        user.setId(rs.getLong("id"));
        user.setUsername(rs.getString("username"));
        user.setEmail(rs.getString("email"));
        user.setPassword(rs.getString("password"));
        return user;

    }
}
